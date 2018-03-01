package org.rta.core.service.tempreport.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.payment.gateway.TransactionDetailDAO;
import org.rta.core.dao.payment.gateway.TransactionHistoryDAO;
import org.rta.core.dao.tempreport.TempReportDAO;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleRCHistoryDAO;
import org.rta.core.entity.application.VehicleRCHistoryEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.entity.payment.gateway.TransactionHistoryEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.PaymentType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.service.tempreport.TempReportService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class TempReportServiceImpl implements TempReportService {
	private static final Logger log = Logger.getLogger(TempReportServiceImpl.class);
	@Autowired
	VehicleDAO vehicleDAO;

	@Autowired
	VehicleRCHistoryDAO vehicleRCHistoryDAO;

	@Value("${rta.report.destination}")
	private String reportPath;
	@Autowired
	TransactionHistoryDAO transactionHistoryDAO;
	@Autowired
	TransactionDetailDAO transactionDetailDAO;
	@Autowired
	TempReportDAO tempReportDAO;
    @Autowired
    private AddressDAO addressDAO;
    @Autowired
    private DistrictDAO districtDAO;

	@Transactional
	public Map<Long, String[]> getDealerApplicationSummaryReport(long from, long to) {
		List<VehicleRCEntity> vehicles = vehicleDAO.getVehicleRCCreatedBtwDate(from, to);
		if (vehicles == null || vehicles.size() <= 0)
			return null;

		// indices in array
		int totalIndex = 6;
		int DEALER_USERNAME = 0;
		int DEALER_NAME = 1;
		int TTL_APPL = 2;
		int APPROVE = 3;
		int REJECTED = 4;
		int PENDING = 5;

		Integer[] dealerAppStatus = null;
		String[] dealerInfoAppStatus = null;
		Map<Long, String[]> dealerInfoAndAppStatus = new HashMap<>();
		Map<Long, Integer[]> dealerApplicationSummary = new HashMap<Long, Integer[]>();
		
		for (VehicleRCEntity vehicleRc : vehicles) {
			
			if (vehicleRc.getTrStatus() != Status.APPROVED.getValue())
				continue;
			if (!dealerApplicationSummary.containsKey(vehicleRc.getUserId().getUserId())) {
				dealerInfoAndAppStatus.put(vehicleRc.getUserId().getUserId(), new String[] { "", "", "", "", "", "" });
				dealerApplicationSummary.put(vehicleRc.getUserId().getUserId(), new Integer[] { 0, 0, 0, 0, 0, 0 });
			}
			
			dealerAppStatus = dealerApplicationSummary.get(vehicleRc.getUserId().getUserId());
			dealerAppStatus[TTL_APPL] += 1;
			if (vehicleRc.getPrStatus() == Status.APPROVED.getValue())
				dealerAppStatus[APPROVE] += 1;
			if (vehicleRc.getPrStatus() == Status.REJECTED.getValue())
				dealerAppStatus[REJECTED] += 1;
			if (vehicleRc.getPrStatus() == Status.PENDING.getValue())
				dealerAppStatus[PENDING] += 1;

			dealerInfoAppStatus = dealerInfoAndAppStatus.get(vehicleRc.getUserId().getUserId());
			dealerInfoAppStatus[DEALER_USERNAME] = vehicleRc.getUserId().getUserName();
			dealerInfoAppStatus[DEALER_NAME] = (StringsUtil.isNullOrEmpty(vehicleRc.getUserId().getFirstName())?"":(vehicleRc.getUserId().getFirstName()+" "))+
					 						(StringsUtil.isNullOrEmpty(vehicleRc.getUserId().getMiddleName())?"":vehicleRc.getUserId().getMiddleName())+" "+
					 						(StringsUtil.isNullOrEmpty(vehicleRc.getUserId().getLastName())?"":vehicleRc.getUserId().getLastName());
			dealerInfoAppStatus[TTL_APPL] = dealerAppStatus[TTL_APPL] + "";
			dealerInfoAppStatus[APPROVE] = dealerAppStatus[APPROVE] + "";
			dealerInfoAppStatus[REJECTED] = dealerAppStatus[REJECTED] + "";
			dealerInfoAppStatus[PENDING] = dealerAppStatus[PENDING] + "";
		}

		if (dealerInfoAndAppStatus == null)
			return null;
//		Date fromDt = DateUtil.fromUTCTimeStamp(from*1000);
//		String fromStr = fromDt.getDate() + "-" + fromDt.getMonth() + "-" + fromDt.getYear();
		
		File dir=new File(reportPath);
		if(!dir.exists())
			dir.mkdirs();
		
		String FileNm = reportPath + File.separator + "dealerApplicationSummaryReport_" + from + ".csv";
		generateExcel(FileNm, dealerInfoAndAppStatus);
		return dealerInfoAndAppStatus;

	}

	public void generateExcel(String fileName, Map<Long, String[]> response) {
		String eol = System.getProperty("line.separator");
		Writer writer = null;
		try {
			writer = new FileWriter(fileName);
			writer.append("Dealer UserName").append(',').append("Dealer Name").append(',').append("Application Sent to RTA").append(',')
			.append("APPROVED").append(',').append("REJECTED").append(',')
			.append("PENDING").append(eol);
			for (Long dealerId : response.keySet()) {
				String[] dataFordealerAppStatus = response.get(dealerId);
				writer.append(dataFordealerAppStatus[0]).append(',').append(dataFordealerAppStatus[1]).append(',')
						.append(dataFordealerAppStatus[2]).append(',').append(dataFordealerAppStatus[3]).append(',')
						.append(dataFordealerAppStatus[4]).append(',').append(dataFordealerAppStatus[5]).append(eol);
			}
			writer.flush();
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
	}

	@Override
	@Transactional
	public Map<String, Map<String, Map<String, Integer>>> getRtaOfficeWiseReport(Long from, Long to) {
		Map<String, Map<String, Map<String, Integer>>> dataMap = new HashMap<>();
		List<Object[]> trReleased = tempReportDAO.getByDate4TR(from, to, Status.APPROVED);
		Iterator<Object[]> itr = trReleased.iterator();
		while (itr.hasNext()) {
			Object[] obj = itr.next();
			BigInteger count = (BigInteger) obj[0];
			String date = obj[1].toString();
			String rtaOfficeName = obj[2].toString();

			Map<String, Map<String, Integer>> mapDate = null;
			if (dataMap.containsKey(rtaOfficeName)) {
				mapDate = dataMap.get(rtaOfficeName);
			} else {
				mapDate = new HashMap<>();
			}
			Map<String, Integer> countMap = null;
			if (mapDate.containsKey(date)) {
				countMap = mapDate.get(date);
			} else {
				countMap = new HashMap<>();
			}
			countMap.put("trReleased", count.intValue());
			mapDate.put(date, countMap);
			dataMap.put(rtaOfficeName, mapDate);
		}
		List<Object[]> prReleased = tempReportDAO.getByDate4PR(from, to, Status.APPROVED);
		Iterator<Object[]> itr1 = prReleased.iterator();
		while (itr1.hasNext()) {
			Object[] obj = itr1.next();
			BigInteger count = (BigInteger) obj[0];
			String date = obj[1].toString();
			String rtaOfficeName = obj[2].toString();

			Map<String, Map<String, Integer>> mapDate = null;
			if (dataMap.containsKey(rtaOfficeName)) {
				mapDate = dataMap.get(rtaOfficeName);
			} else {
				mapDate = new HashMap<>();
			}
			Map<String, Integer> countMap = null;
			if (mapDate.containsKey(date)) {
				countMap = mapDate.get(date);
			} else {
				countMap = new HashMap<>();
			}
			countMap.put("prReleased", count.intValue());
			mapDate.put(date, countMap);
			dataMap.put(rtaOfficeName, mapDate);
		}
		List<Object[]> prCancel = tempReportDAO.getByDateFromHist(from, to, Status.REJECTED);
		Iterator<Object[]> itr2 = prCancel.iterator();
		while (itr2.hasNext()) {
			Object[] obj = itr2.next();
			BigInteger count = (BigInteger) obj[0];
			String date = obj[1].toString();
			String rtaOfficeName = obj[2].toString();

			Map<String, Map<String, Integer>> mapDate = null;
			if (dataMap.containsKey(rtaOfficeName)) {
				mapDate = dataMap.get(rtaOfficeName);
			} else {
				mapDate = new HashMap<>();
			}
			Map<String, Integer> countMap = null;
			if (mapDate.containsKey(date)) {
				countMap = mapDate.get(date);
			} else {
				countMap = new HashMap<>();
			}
			countMap.put("prRejected", count.intValue());
			mapDate.put(date, countMap);
			dataMap.put(rtaOfficeName, mapDate);
		}

		String FileNm = reportPath + File.separator + "rtaofficewise_" + from +".csv";
		String eol = System.getProperty("line.separator");
		Writer writer = null;
		try {
			writer = new FileWriter(FileNm);
			writer.append("RTA Office").append(',').append("Date").append(',').append("TR Released")
            .append(',').append("PR Released").append(',').append("PR Rejected").append(eol);
			for (Entry<String, Map<String, Map<String, Integer>>> rtaMap : dataMap.entrySet()) {
				for (Entry<String, Map<String, Integer>> dateMap : rtaMap.getValue().entrySet()) {
					Map<String, Integer> map = dateMap.getValue();
					String trReleased1 = map.containsKey("trReleased") ? map.get("trReleased").toString() : "0";
					String prReleased1 = map.containsKey("prReleased") ? map.get("prReleased").toString() : "0";
					String prRejected = map.containsKey("prRejected") ? map.get("prRejected").toString() : "0";
					writer.append(rtaMap.getKey()).append(',').append(dateMap.getKey()).append(',').append(trReleased1)
							.append(',').append(prReleased1).append(',').append(prRejected).append(eol);
				}
			}

			writer.flush();
			writer.close();
		} catch (IOException ex) {
			log.error("IOException in getRtaOfficeWiseReport : " + ex.getMessage());
			log.debug(ex);
		}
		return dataMap;
	}

	@Override
	@Transactional
	public void getRTOApplication(long from, long to) {
		log.debug(":::::::::getRTOApplication::::::::::");
		try {
			List<VehicleRCHistoryEntity> vehicleRCHistoryAppList = vehicleRCHistoryDAO
					.getApprovedAppByFromAndToDate(from, to);
			HashMap<String, String> map = new HashMap<>();
			for (Object vehicleRCHistoryEntity : vehicleRCHistoryAppList) {
				Object[] vehicleRCHistoryObj = (Object[]) vehicleRCHistoryEntity;
				if(Integer.parseInt(vehicleRCHistoryObj[2].toString()) == UserType.ROLE_DEALER.getValue())
					continue;
				if (Integer.parseInt(vehicleRCHistoryObj[4].toString()) == Status.APPROVED.getValue()) {
					if (map != null && map.containsKey(vehicleRCHistoryObj[0].toString())) {
						String str = map.get(vehicleRCHistoryObj[0].toString());
						map.replace(vehicleRCHistoryObj[0].toString(), vehicleRCHistoryObj[1].toString() + "|"
						+ vehicleRCHistoryObj[2].toString() + "|"+ vehicleRCHistoryObj[3].toString()+ "|" + str.charAt(str.length() - 1));
					} else {
						map.put(vehicleRCHistoryObj[0].toString(),
								vehicleRCHistoryObj[1].toString() + "|" + vehicleRCHistoryObj[2].toString()+ "|" + vehicleRCHistoryObj[3].toString());
					}
				}
				if (Integer.parseInt(vehicleRCHistoryObj[4].toString()) == Status.REJECTED.getValue()) {
					if (map != null && map.containsKey(vehicleRCHistoryObj[0].toString())) {
						String str = map.get(vehicleRCHistoryObj[0].toString());
						map.replace(vehicleRCHistoryObj[0].toString(), str + "|" + vehicleRCHistoryObj[3].toString());
					} else {
						map.put(vehicleRCHistoryObj[0].toString(),
								vehicleRCHistoryObj[1].toString() + "|" + vehicleRCHistoryObj[2].toString()+ "||" + vehicleRCHistoryObj[3].toString());
					}
				}
				log.debug("::getRTOApplication:::::map::::: "+ map);
			}
			Writer writer = null;
			String eol = System.getProperty("line.separator");
			try {
//				Date fromDt = DateUtil.fromUTCTimeStamp(from*1000);
//				String fromStr = fromDt.getDate() + "-" + fromDt.getMonth() + "-" + fromDt.getYear();
				String FilePAth = reportPath + File.separator + "rtosummaryreport_" + from + ".csv";
				writer = new FileWriter(FilePAth);
				writer.append("UserName").append(',')
				.append("Designation").append(',')
				.append("Approved").append(',')
				.append("Rejected").append(eol);
				for (String userName : map.keySet()) {
					String userDetails = map.get(userName);
					String[] rtoData = userDetails.split("\\|");
					String designation = UserType.getUserType(Integer.parseInt(rtoData[1])).getLabel();
					writer.append(rtoData.length >= 1 ? rtoData[0] : "").append(',')
							.append(rtoData.length >= 2 ? designation : "").append(',')
							.append(rtoData.length >= 3 ? rtoData[2] : "").append(',')
							.append(rtoData.length >= 4 ? rtoData[3] : "").append(eol);
				}
				writer.flush();
				writer.close();
			} catch (IOException ex) {
				log.error("IOException in getRTOApplication : " + ex.getMessage());
			}
		} catch (Exception e) {
			log.error("Exception in getRTOApplication : " + e.getMessage());
			log.debug(e);
		}

	}


	@Override
	@Transactional
	public void getTransactionReport(long from, long to) {
		log.debug(":::::::::::getTransactionReport::::start:::::::");
		List<TransactionHistoryEntity> transactionHistoryEntitiesList = transactionHistoryDAO
				.getAllByFromAndToDate(from, to);
		HashMap<String, String> mapHistory = new HashMap<>();
		for (TransactionHistoryEntity transactionHistoryEntity : transactionHistoryEntitiesList) {
			mapHistory.put(transactionHistoryEntity.getTransactionNo(), transactionHistoryEntity.getRequestParameter());
		}
		List<TransactionDetailEntity> transactionDetailEntitiesList = transactionDetailDAO.getAllByFromAndToDate(from,
				to);
		Writer writer = null;
		String eol = System.getProperty("line.separator");
		try {
//			Date fromDt = DateUtil.fromUTCTimeStamp(from*1000);
//			String fromStr = fromDt.getDate() + "-" + fromDt.getMonth() + "-" + fromDt.getYear();
			String FilePAth = reportPath + File.separator + "transactionreport" + from + ".csv";
			writer = new FileWriter(FilePAth);
			writer.append("Application Id").append(',')
			.append("Transaction No").append(',')
			.append("Payment Amount").append(',')
			.append("SBI Ref No").append(',')
			.append("Status").append(',')
			.append("Message").append(',')
			.append("Request Parameter To SBI").append(eol);
			for (TransactionDetailEntity transactionDetailEntity : transactionDetailEntitiesList) {
				writer.append(transactionDetailEntity.getVehicleRcId().getVehicleRcId().toString()).append(',')
						.append(transactionDetailEntity.getTransactionNo()).append(',')
						.append(String.valueOf(transactionDetailEntity.getPayAmount())).append(',')
						.append(transactionDetailEntity.getSbiRefNo()).append(',')
						.append(transactionDetailEntity.getStatus().toString()).append(',')
						.append(transactionDetailEntity.getStatusMessage()).append(',')
						.append(mapHistory.get(transactionDetailEntity.getTransactionNo())).append(eol);
			}

			writer.flush();
			writer.close();
			log.debug(":::::::::::getTransactionReport::::end:::::::");
		} catch (IOException ex) {
			log.error("Exception in getTransactionReport : " + ex.getMessage());
			log.debug(ex);
		}
	}

    @Override
    @Transactional
    public void getTransactionStatusReport(long from, long to) {
        log.debug(":::::::::::getTransactionStatusReport::::start:::::::");
        List<TransactionDetailEntity> transactionDetailEntitiesList =
                transactionDetailDAO.getAllByFromAndToDate(from, to);
        Writer writer = null;
        String eol = System.getProperty("line.separator");
        try {
            List<AddressEntity> addressList = addressDAO.getAllByUserType(UserType.ROLE_DEALER);
            HashMap<Long, Long> mapAddress = new HashMap<>();
            for (AddressEntity addressEntity : addressList) {
                mapAddress.put(addressEntity.getUserId(), addressEntity.getDistrict());
            }
            List<DistrictEntity> districtList = districtDAO.getAll();
            HashMap<Long, DistrictEntity> mapDistrict = new HashMap<>();
            for (DistrictEntity districtEntity : districtList) {
                mapDistrict.put(districtEntity.getDistrictId(), districtEntity);
            }
            
            String FilePAth = reportPath + File.separator + "transactionstatusreport"
                    + DateUtil.extractDateAsStringWithHyphen(DateUtil.toCurrentUTCTimeStamp()) + ".csv";
            writer = new FileWriter(FilePAth);
            writer.append("Application Id").append(',').append("Challan No").append(',').append("Payment Amount")
                    .append(',').append("SBI Ref No").append(',').append("SBI Response Status").append(',')
                    .append("SBI Response Message")
                    .append(',').append("Payment Type").append(',').append("Payment Time").append(',')
                    .append("Created By").append(',').append("District").append(eol);
            for (TransactionDetailEntity transactionDetailEntity : transactionDetailEntitiesList) {
                String paymentType = "";
                String statusFromSBI = "";
                DistrictEntity districtEntity = mapDistrict
                        .get(mapAddress.get(transactionDetailEntity.getVehicleRcId().getUserId().getUserId()));
                if (transactionDetailEntity.getPaymentType() == PaymentType.PAYTAX.getId())
                    paymentType = "Pay Tax";
                else
                    paymentType = "Second Vehicle Rejection Pay Tax";
                switch (Status.getStatus(transactionDetailEntity.getStatus())) {
                    case OPEN:
                        statusFromSBI = "No Response From SBI";
                        break;
                    case PENDING:
                        statusFromSBI = "Pending Transaction";
                        break;
                    case FAILURE:
                        statusFromSBI = "Failure";
                        if (transactionDetailEntity.getStatusMessage() != null
                                && transactionDetailEntity.getStatusMessage().contains("RTA Marked Status Failed"))
                            transactionDetailEntity.setStatusMessage("Pending Transaction");
                        break;
                    case SUCCESS:
                        statusFromSBI = "Success";
                        break;

                }

                writer.append("AP000" + transactionDetailEntity.getVehicleRcId().getVehicleRcId().toString())
                        .append(',')
                        .append(transactionDetailEntity.getTransactionNo()).append(',')
                        .append(String.valueOf(transactionDetailEntity.getPayAmount())).append(',')
                        .append(transactionDetailEntity.getSbiRefNo()).append(',')
                        .append(statusFromSBI).append(',')
                        .append(transactionDetailEntity.getStatusMessage()).append(',')
                        .append(paymentType).append(',')
                        .append(DateUtil.extractDateAsString(transactionDetailEntity.getPaymentTime())).append(',')
                        .append(transactionDetailEntity.getCreatedBy()).append(',')
                        .append(districtEntity.getName()).append(eol);
            }

            writer.flush();
            writer.close();
            log.debug(":::::::::::getTransactionStatusReport::::end:::::::");
        } catch (IOException ex) {
        	log.error("IOException in getTransactionStatusReport : " + ex.getMessage());
			log.debug(ex);
        }           
    }
    
    
    @Override
    @Transactional
    public void getRTORejReport(long from, long to) {
    	
    	List<Object[]> rejObjs=tempReportDAO.getRejHistory(from,to); 
    	String eol = System.getProperty("line.separator");
		Writer writer = null;
		int totalIndex = 9;
		int officer_Name = 0;
		int role = 1;
		int district = 2;
		int office_code = 3;
		int office_name = 4;
		int application = 5;
		int date = 6;
		int document_title = 7;
		int comment = 8;
		
		
		String fileName=reportPath + File.separator + "rtorejectionrpt"
                + DateUtil.extractDateAsStringWithHyphen(DateUtil.toCurrentUTCTimeStamp()) + ".csv";
		
		try {
			writer = new FileWriter(fileName);
			writer.append("Officer Name").append(',').append("Role").append(',').append("district").append(',')
			.append("office_code").append(',').append("office_name").append(',')
			.append("application").append(',').append("date").append(',').append("document_title")
			.append(',').append("comment").append(eol);
			int len;
			for (Object[] record : rejObjs) {
				String[] dataFordealerAppStatus ={"","","","","","","","",""};
				len=0;
				while(len<record.length){
					dataFordealerAppStatus[len]=record[len].toString();
					len++;
				}
				writer.append(dataFordealerAppStatus[officer_Name]).append(',').append(dataFordealerAppStatus[role]).append(',')
						.append(dataFordealerAppStatus[district]).append(',').append(dataFordealerAppStatus[office_code]).append(',')
						.append(dataFordealerAppStatus[office_name]).append(',').append(dataFordealerAppStatus[application])
						.append(',').append(dataFordealerAppStatus[date]).append(',').append(dataFordealerAppStatus[document_title])
						.append(',').append(dataFordealerAppStatus[comment]).append(eol);
			}
			writer.flush();
			writer.close();
		} catch (IOException ex) {
			log.error("IOException in getRTORejReport : " + ex.getMessage());
			log.debug(ex);
		}
    }
}
