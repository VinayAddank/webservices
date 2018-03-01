package org.rta.registration.service.vcr.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.payment.gateway.TransactionDetailDAO;
import org.rta.core.dao.payment.tax.TaxDetailDAO;
import org.rta.core.dao.permit.PermitHeaderDAO;
import org.rta.core.dao.vcr.VcrHistoryDAO;
import org.rta.core.dao.vcr.VcrPaymentLogDAO;
import org.rta.core.dao.vcr.VcrTransactionDetailDAO;
import org.rta.core.dao.vehicle.VehicleBillingDetailsDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.dao.vehicle.cms.VehicleClassDescDAO;
import org.rta.core.entity.payment.tax.TaxDetailEntity;
import org.rta.core.entity.vcr.VcrDetailsEntity;
import org.rta.core.entity.vcr.VcrOffenseHistoryEntity;
import org.rta.core.entity.vcr.VcrPaymentLogEntity;
import org.rta.core.entity.vcr.VcrTransactionDetailEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.MonthType;
import org.rta.core.enums.VcrPaymentStatus;
import org.rta.core.exception.PrNumberExistException;
import org.rta.core.exceptioncode.PrNumberExistExceptionCode;
import org.rta.core.model.payment.gateway.TransactionDetailModel;
import org.rta.core.model.payment.tax.VcrResponseModel;
import org.rta.core.service.payment.tax.TaxDetailService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.StringsUtil;
import org.rta.registration.pan.model.InsertVcrModel;
import org.rta.registration.pan.model.VcrBookingData;
import org.rta.registration.pan.model.VcrOffenseDetails;
import org.rta.registration.service.taxfee.TaxFeeService;
import org.rta.registration.service.vcr.VcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class VcrServiceImpl implements VcrService {
	private static final Logger log = Logger.getLogger(VcrServiceImpl.class);
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private VcrHistoryDAO vcrHistoryDAO;

	@Value(value = "${service.vcr.host}")
	private String HOST;

	@Value(value = "${service.vcr.port}")
	private String PORT;

	@Value(value = "${service.vcr.path}")
	private String ROOT_URL;
	@Autowired
	private VcrPaymentLogDAO vcrPaymentLogDAO;
	@Autowired
	private VehicleDAO vehicleDAO;
	@Autowired
	private TaxDetailDAO taxDetailDAO;
	@Autowired
    public VehicleDetailsDAO vehicleDetailsDAO;
	@Autowired
	public VehicleBillingDetailsDAO vehicleBillingDetailsDAO;
	@Autowired
	private VcrTransactionDetailDAO vcrTransactionDetailDAO;
	
    private StringBuilder getRootURL() {
        StringBuilder url = new StringBuilder("http://").append(HOST);
        if (!StringsUtil.isNullOrEmpty(PORT)) {
            url.append(":").append(PORT);
        }
        url.append("/").append(ROOT_URL);
        return url;
    }
	
    private List<VcrBookingData> getVCRDetailsExt(String docType, String docNumber) {
        ResponseEntity<List<VcrBookingData>> response = restTemplate.exchange(
                getRootURL().append("/getdetails/" + docType + "?docid=").append(docNumber).toString(), HttpMethod.GET,
                null, new ParameterizedTypeReference<List<VcrBookingData>>() {});
        HttpStatus httpStatus = response.getStatusCode();
        if (httpStatus == HttpStatus.OK) {
            if (response.hasBody()) {
                return response.getBody();
            }            
        }
        return null;
    }
    
    @Transactional
    @Override
    public VcrResponseModel saveCalculateVcrDetails(String docType, String docNumber) {
        VcrResponseModel vcrTotalTaxModel = new VcrResponseModel();
        List<VcrBookingData> vcrBookings = getVCRDetailsExt(docType, docNumber);
        if (null != vcrBookings && vcrBookings.size() > 0) {
            String prNumber = vcrBookings.get(0).getRegNo();
            VehicleRCEntity vehicleRCEntity = null;
            if (null != prNumber) {
                vehicleRCEntity = vehicleDAO.getVehicleRCByPRNumber(docNumber);
                if (null == vehicleRCEntity) {
                    throw new PrNumberExistException(PrNumberExistExceptionCode.RC_EXIST, "RC does not exists.");
                }
            } else {
                throw new PrNumberExistException(PrNumberExistExceptionCode.RC_EXIST, "RC does not exists.");
            }
            TaxDetailEntity taxDetailEntity = taxDetailDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
            boolean setPliedAsFlag=true;
            for (VcrBookingData booking : vcrBookings) {
            	 Long bookedDate = null;
                if (null != booking && !VcrPaymentStatus.PAID.getCode().equalsIgnoreCase(booking.getVcrStatus())) {
                    VcrDetailsEntity vcrHistory = vcrHistoryDAO.getVcrDetailsByVcrNumber(booking.getVcrNum());
                    if (vcrHistory == null) {
                        vcrHistory = new VcrDetailsEntity();
                        vcrHistory.setVcrNum(booking.getVcrNum());
                        vcrHistory.setRegNo(booking.getRegNo());
                        vcrHistory.setDlNumber(booking.getDlNumber());
                        vcrHistory.setBookedByMvi(booking.getBookedByMvi());
                        vcrHistory.setBookedDate(booking.getBookedDate());
                        vcrHistory.setBookedTime(booking.getBookedTime());
                        vcrHistory.setPlaceBooked(booking.getPlaceBooked());
                        vcrHistory.setVcrStatus(booking.getVcrStatus());
                        vcrHistory.setIdType(booking.getIdType());
                        vcrHistory.setVehsezdFlg(booking.getVehsezdFlg());
                        vcrHistory.setPliedas(booking.getPliedas());
                        vcrHistory.setVehicleRcId(vehicleRCEntity.getVehicleRcId().toString());
                        List<VcrOffenseHistoryEntity> vcrOffenseHistoryList = new ArrayList<VcrOffenseHistoryEntity>();
                        for (VcrOffenseDetails offense : booking.getOffenseDetails()) {
                            if (booking.getVcrNum() != null
                                    && booking.getVcrNum().equalsIgnoreCase(offense.getVcrNumber())) {
                                VcrOffenseHistoryEntity vcrOffenseHistoryEntity = new VcrOffenseHistoryEntity();
                                vcrOffenseHistoryEntity.setFineAmount(offense.getFineAmount());
                                vcrOffenseHistoryEntity.setOffense(offense.getOffense());
                                vcrOffenseHistoryEntity.setVcrNumber(offense.getVcrNumber());
                                vcrOffenseHistoryList.add(vcrOffenseHistoryEntity);
                                //Add Fine amount
                                double fine = 0.0;
                                if(null != vcrHistory.getVcrFineAmount()){
                                    fine = vcrHistory.getVcrFineAmount() + Double.parseDouble(offense.getFineAmount()); 
                                } else {
                                    fine = Double.parseDouble(offense.getFineAmount());
                                }
                                vcrHistory.setVcrFineAmount(fine);
                            }
                        }
                        vcrHistory.setVcrOffenseHistoryEntity(vcrOffenseHistoryList);
                        //Calculate Tax (if applicable)
                        //Double taxAmount = calculateVcrTax(vcrHistory, vehicleRCEntity);
                        //vcrHistory.setVcrTaxAmount(taxAmount);
                        vcrHistoryDAO.saveOrUpdate(vcrHistory);
                       
						
                       
                        
                    }
                    try {
						bookedDate = DateUtil.toUTCTimeStamp(new SimpleDateFormat("dd/MM/yyyy").parse(vcrHistory.getBookedDate()));
					} catch (ParseException e) {
						log.info(":::::::error in converting date to Long:::");
						return null;
						
					}
                    if(vcrHistory.getPliedas()!="" && setPliedAsFlag){
                    	setPliedAsFlag=false;
                    	vcrTotalTaxModel.setVcrBookedDt(bookedDate);
                    	vcrTotalTaxModel.setPliedAs(vcrHistory.getPliedas());
                    	vcrTotalTaxModel.setVcrFlag(true);
                    	vcrTotalTaxModel.setVehicleSiezed(vcrHistory.getVehsezdFlg());
                    }else if(setPliedAsFlag) {
                    	vcrTotalTaxModel.setVcrBookedDt(bookedDate);
                    	vcrTotalTaxModel.setPliedAs(vcrHistory.getPliedas());
                    	vcrTotalTaxModel.setVcrFlag(true);
                    	vcrTotalTaxModel.setVehicleSiezed(vcrHistory.getVehsezdFlg());
                    }
                    
                    
                    double totalFine = vcrTotalTaxModel.getFine() + vcrHistory.getVcrFineAmount(); 
                    vcrTotalTaxModel.setFine(totalFine);
                   /* double totalTax = vcrTotalTaxModel.getTotalVcrTax() + vcrHistory.getVcrTaxAmount(); 
                    vcrTotalTaxModel.setTotalVcrTax(totalTax);*/
                }
            }     
        }
		log.info("::::: vcr report log ::::: booked date:" + vcrTotalTaxModel.getVcrBookedDt() + " :: PliedAs"
				+ vcrTotalTaxModel.getPliedAs()+ "::::VcrFlag  :"+vcrTotalTaxModel.getVcrFlag());
        return vcrTotalTaxModel;
    }
   
    

	@Transactional
	@Override
	public String updateVcrDetails(String docNumber,String transactionNo, String challanNo) {
		//VcrTransactionDetailEntity vcrTransactionDetailEntity=vcrTransactionDetailDAO.getVcrDetailsByVcrNumber(docNumber);
		/*if(vcrTransactionDetailEntity==null){
			return null;
		}else if(!Status.getStatus(vcrTransactionDetailEntity.getStatus()).getLabel().equals("CLOSED")){
			//throw new PrNumberExistException(PrNumberExistExceptionCode.TRANSACTION, "payment is pending");
			return null;
		}*/
		String responseBody = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<VcrBookingData> listOfVCRNumber= getVCRDetailsExt( "RC",  docNumber);
		for(VcrBookingData booking : listOfVCRNumber){
			Integer vcrSum = null;
		InsertVcrModel insertVcrModel=new InsertVcrModel();
		insertVcrModel.setChallanNo(challanNo);
		insertVcrModel.setPaymentDt(DateUtil.toCurrentUTCTimeStamp().toString());
		for(VcrOffenseDetails offense : booking.getOffenseDetails()){
			vcrSum=vcrSum+ Integer.parseInt(offense.getFineAmount());
		}
		insertVcrModel.setTransactionAmount(vcrSum.toString());
		insertVcrModel.setTransactionDt(DateUtil.toCurrentUTCTimeStamp().toString());
		insertVcrModel.setvCRNo(booking.getVcrNum());
		insertVcrModel.setTransactionNo(transactionNo);
		HttpEntity<InsertVcrModel> httpEntity = new HttpEntity<InsertVcrModel>(insertVcrModel, headers);
		ResponseEntity<String> response = null;
		HttpStatus httpStatus = null;
		try {
			response = restTemplate.exchange(getRootURL().append("/insertVcr").toString(), HttpMethod.POST, httpEntity,
					String.class);
			httpStatus = response.getStatusCode();
		} catch (HttpStatusCodeException ex) {
			httpStatus = ex.getStatusCode();
		}
		 responseBody = null;
		if (httpStatus == HttpStatus.OK) {
			if (response.hasBody()) {
				responseBody = response.getBody();
			}
		}
		VcrPaymentLogEntity vcrPaymentLogEntity = new VcrPaymentLogEntity();
		vcrPaymentLogEntity.setChallanNo(insertVcrModel.getChallanNo());
		vcrPaymentLogEntity.setPaymentDt(insertVcrModel.getPaymentDt());
		vcrPaymentLogEntity.setRegnNo(insertVcrModel.getRegnNo());
		vcrPaymentLogEntity.setTransactionNo(insertVcrModel.getTransactionNo());
		vcrPaymentLogEntity.setVcrNo(insertVcrModel.getvCRNo());
		vcrPaymentLogEntity.setTransactionAmount(insertVcrModel.getTransactionAmount());
		vcrPaymentLogEntity.setVcrApistatusFlag(responseBody);
		vcrPaymentLogEntity.setTransactionDt(insertVcrModel.getTransactionDt());
		vcrPaymentLogEntity.setVcrNo(insertVcrModel.getvCRNo());
		vcrPaymentLogDAO.save(vcrPaymentLogEntity);
		}
		return responseBody;
	}

	
	
	
	
	private int calculateQuarterlyTax(Long date) {
        int quartltyPart = 0;
        if(date==null){
        	date=DateUtil.toCurrentUTCTimeStamp();
        }
        switch (MonthType.getMonthType(DateUtil.getMonth(date))) {
        case JANUARY:
            quartltyPart = 1;
            break;
        case FEBRUARY:
            quartltyPart = 2;
            break;
        case MARCH:
            quartltyPart = 3;
            break;
        case APRIL:
            quartltyPart = 1;
            break;
        case MAY:
            quartltyPart = 2;
            break;
        case JUNE:
            quartltyPart = 3;
            break;
        case JULY:
            quartltyPart = 1;
            break;
        case AUGUST:
            quartltyPart = 2;
            break;
        case SEPTEMBER:
            quartltyPart = 3;
            break;
        case OCTOBER:
            quartltyPart = 1;
            break;
        case NOVEMBER:
            quartltyPart = 2;
            break;
        case DECEMBER:
            quartltyPart = 3;
            break;
        }
        return quartltyPart;
    }
	
	public long taxValidty(int quartelyPart){
		int monthType = calculateQuarterlyTax(null);
		long taxValid = 0;
		if(quartelyPart == 0 || quartelyPart == 3)
			taxValid = DateUtil.toLastDayOfMonth(monthType-1);
		if(quartelyPart == 6)
			taxValid = DateUtil.toLastDayOfMonth((monthType-1) + 3);
		if(quartelyPart == 12)
			taxValid = DateUtil.toLastDayOfMonth((monthType-1) + 9);
		return taxValid;
	}

	@Transactional
	@Override
	public VcrDetailsEntity getVcrDetails(String prNumber) {
		 VcrDetailsEntity vcrHistory = vcrHistoryDAO.getVcrDetailsByVcrNumber(prNumber);
		return vcrHistory;
	}

}
