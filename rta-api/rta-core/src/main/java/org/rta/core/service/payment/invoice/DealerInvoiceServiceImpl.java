package org.rta.core.service.payment.invoice;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.hsrp.HSRPFeeDetailsDAO;
import org.rta.core.dao.payment.invoice.DealerInvoiceDAO;
import org.rta.core.dao.payment.registfee.FitnessFeeDAO;
import org.rta.core.dao.payment.registfee.RegFeeDetailDAO;
import org.rta.core.dao.payment.tax.LifeTaxDAO;
import org.rta.core.dao.payment.tax.PeriodicTaxDAO;
import org.rta.core.dao.payment.tax.TaxDetailDAO;
import org.rta.core.dao.vehicle.VehicleBillingDetailsDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.entity.hsrp.HSRPFeeDetailsEntity;
import org.rta.core.entity.payment.invoice.DealerInvoiceEntity;
import org.rta.core.entity.payment.registfee.FitnessFeeDetailsEntity;
import org.rta.core.entity.payment.registfee.RegFeeDetailsEntity;
import org.rta.core.entity.payment.tax.LifeTaxEntity;
import org.rta.core.entity.payment.tax.PeriodicTaxEntity;
import org.rta.core.entity.vehicle.VehicleBillingDetailsEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.enums.Status;
import org.rta.core.enums.master.TaxType;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.NumberParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DealerInvoiceServiceImpl implements DealerInvoiceService {
	private static final Logger log = Logger.getLogger(DealerInvoiceServiceImpl.class);

	@Autowired
	private RegFeeDetailDAO regFeeDetailDAO;
	@Autowired
	private TaxDetailDAO taxDetailDAO;
	@Autowired
	private DealerInvoiceDAO dealerInvoiceDAO;
	@Autowired
	private VehicleBillingDetailsDAO vehicleBillingDetailsDAO;
	@Autowired
	private VehicleDetailsDAO vehicleDetailsDAO;
	@Autowired
	private HSRPFeeDetailsDAO hsrpFeeDetailsDAO;
	@Autowired
	private LifeTaxDAO lifeTaxDAO;
	@Autowired
	private PeriodicTaxDAO periodicTaxDAO;
	@Autowired
	private FitnessFeeDAO fitnessFeeDAO;

	@Override
	@Transactional
	public DealerInvoiceEntity createInvoice(long vehicleRcId, String userName) {
        log.debug("::::::::::::createInvoice::::start::::::::: ");
		DealerInvoiceEntity dealerInvoiceEntity = null;
		VehicleDetailsEntity vehicleDetailEntity = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
		VehicleBillingDetailsEntity vehicleBillingDetailsEntity = vehicleBillingDetailsDAO
				.getByVehicleDetailId(vehicleDetailEntity);
		if (vehicleDetailEntity == null && vehicleBillingDetailsEntity == null)
			throw new IllegalArgumentException("Invalid vehicleRcId !");
        RegFeeDetailsEntity regFeeDetailEntity = regFeeDetailDAO.getByVehicleRcId(vehicleRcId);
        double taxAmount = 0.0d ;
        switch(TaxType.getTaxType(vehicleDetailEntity.getTaxTypeId().getTaxTypeId().intValue())){
		case FIRST_QUARTERLY:
			PeriodicTaxEntity periodicTaxEntity = periodicTaxDAO.getEntityByVehicleRcId(vehicleRcId);
			if(periodicTaxEntity != null)
				taxAmount = periodicTaxEntity.getTaxAmt();
			break;
		case LIFE_TAX:
			LifeTaxEntity lifeTaxEntity = lifeTaxDAO.getEntityByVehicleRcId(vehicleRcId);
			if(lifeTaxEntity != null)
				taxAmount = lifeTaxEntity.getTaxAmt();
			break;
		}
        FitnessFeeDetailsEntity fitnessFeeDetailsEntity = fitnessFeeDAO.getByVehicleRcId(vehicleRcId);
        HSRPFeeDetailsEntity hsrpFeeDetailsEntity = hsrpFeeDetailsDAO.getByVehicleRcId(vehicleRcId); 
        dealerInvoiceEntity = dealerInvoiceDAO.getByVehicleRcId(vehicleRcId);
		if (dealerInvoiceEntity != null) {
			dealerInvoiceEntity.setInvoiceAmt(vehicleBillingDetailsEntity.getInvoiceValue());
			dealerInvoiceEntity.setInvoiceDate(vehicleBillingDetailsEntity.getInvoiceDate());
			dealerInvoiceEntity.setTotalAmount(NumberParser.roundOff(fitnessFeeDetailsEntity == null ? 0 : fitnessFeeDetailsEntity.getTotalFitnessFee() +  regFeeDetailEntity.getTotalFee() + hsrpFeeDetailsEntity.getHSRPFee() + taxAmount,"########.##"));
			dealerInvoiceEntity.setModifiedBy(userName);
			dealerInvoiceEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		} else {
			dealerInvoiceEntity = new DealerInvoiceEntity();
			dealerInvoiceEntity.setUser(vehicleDetailEntity.getVehicleRcId().getUserId());
			dealerInvoiceEntity.setHsrpFee(hsrpFeeDetailsEntity.getHSRPFee());
			dealerInvoiceEntity.setInvoiceAmt(vehicleBillingDetailsEntity.getInvoiceValue());
			dealerInvoiceEntity.setInvoiceDate(vehicleBillingDetailsEntity.getInvoiceDate());
			dealerInvoiceEntity.setInvoiceNo(vehicleBillingDetailsEntity.getInvoiceNumber());
			dealerInvoiceEntity.setTotalAmount(NumberParser.roundOff(fitnessFeeDetailsEntity == null ? 0 : fitnessFeeDetailsEntity.getTotalFitnessFee() +  regFeeDetailEntity.getTotalFee() + hsrpFeeDetailsEntity.getHSRPFee() + taxAmount,"########.##"));
			dealerInvoiceEntity.setVehicleRcId(vehicleDetailEntity.getVehicleRcId());
			dealerInvoiceEntity.setCreatedBy(userName);
			dealerInvoiceEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			dealerInvoiceEntity.setStatus(Status.OPEN.getValue());
        }
		dealerInvoiceDAO.saveOrUpdate(dealerInvoiceEntity);
		return dealerInvoiceEntity;
	}

	@Override
	@Transactional
	public DealerInvoiceEntity secondVehicleTaxInvoice(long vehicleRcId, String userName) {
		DealerInvoiceEntity dealerInvoiceEntity = null;
        dealerInvoiceEntity = dealerInvoiceDAO.getByVehicleRcId(vehicleRcId);
		if (dealerInvoiceEntity == null) {
			throw new IllegalArgumentException("Invalid vehicleRcId !");
		}
		LifeTaxEntity lifeTaxEntity = lifeTaxDAO.getEntityByVehicleRcId(vehicleRcId);
		double totalAmt = lifeTaxEntity.getSecondVehicleTaxAmt() + dealerInvoiceEntity.getTotalAmount();
		dealerInvoiceEntity.setTotalAmount(totalAmt);
		dealerInvoiceEntity.setStatus(Status.OPEN.getValue());
		dealerInvoiceEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		dealerInvoiceEntity.setModifiedBy(userName);
		dealerInvoiceDAO.saveOrUpdate(dealerInvoiceEntity);
		return dealerInvoiceEntity;
	}

}
