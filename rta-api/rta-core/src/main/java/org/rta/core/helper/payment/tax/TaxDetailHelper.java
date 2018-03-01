package org.rta.core.helper.payment.tax;

import java.util.Collection;

import org.rta.core.entity.payment.tax.TaxDetailEntity;
import org.rta.core.entity.payment.tax.TaxDetailHistoryEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.helper.master.TaxMasterHelper;
import org.rta.core.helper.master.TaxTypHelper;
import org.rta.core.model.payment.tax.TaxDetailModel;
import org.rta.core.utils.NumberParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaxDetailHelper implements BaseConverter<TaxDetailEntity, TaxDetailModel> {

	@Autowired
	private TaxTypHelper taxTypHelper;
	@Autowired
	private TaxMasterHelper taxMasterHelper;

	@Override
	public TaxDetailModel convertToModel(TaxDetailEntity source) {
		TaxDetailModel td = new TaxDetailModel();
		//td.setTaxTypeModel(taxTypHelper.convertToModel(source.getTaxType()));
		td.setHpaFee(NumberParser.numberFormat(source.getHpaFee()));
		td.setPenalty(NumberParser.numberFormat(source.getPenalty()));
		td.setTaxAmt(NumberParser.numberFormat(source.getTaxAmt()));
		td.setTaxDtlId(source.getTaxDtlId());
		td.setTaxPercentage(NumberParser.numberFormat((source.getTaxMasterType().getTaxPercent())));
		td.setTotalAmt(NumberParser.numberFormat(source.getTotalAmt()));
		td.setTrFee(NumberParser.numberFormat(source.getTrFee()));
		td.setTrServiceCharge(NumberParser.numberFormat(source.getTrServiceCharge()));
		//td.setTaxMasterModel(taxMasterHelper.convertToModel(source.getTaxMasterType()));
		td.setHsrpFee(NumberParser.numberFormat(source.getHsrpFee()));
		td.setTotalTaxAmt(NumberParser.numberFormat(source.getTaxAmt()));
		td.setTaxValidUpto(source.getTaxValidUpto());
		try {td.setCessFee(NumberParser.numberFormat(source.getCessFee()));} catch (Exception e) {e.printStackTrace();}
		td.setSecondVehicleTaxAmt(NumberParser.numberFormat(source.getSecondVehicleTaxAmt()));
		td.setSecondVehicleTax(NumberParser.numberFormat(source.getSecondVehicleTax()));
		return td;
	}

	@Override
	public TaxDetailEntity convertToEntity(TaxDetailModel source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<TaxDetailModel> convertToModelList(Collection<TaxDetailEntity> source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<TaxDetailEntity> convertToEntityList(Collection<TaxDetailModel> source) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public TaxDetailHistoryEntity convertToHistoryEntity(TaxDetailEntity source) {
		TaxDetailHistoryEntity tdh = new TaxDetailHistoryEntity();
		tdh.setHpaFee(source.getHpaFee());
		tdh.setHsrpFee(source.getHsrpFee());
		tdh.setPenalty(source.getPenalty());
		tdh.setSecondVehicleTax(source.getSecondVehicleTax());
		tdh.setSecondVehicleTaxAmt(source.getSecondVehicleTaxAmt());
		tdh.setSecondVehicleTaxOn(source.getSecondVehicleTaxOn());
		tdh.setTaxAmt(source.getTaxAmt());
		tdh.setTaxMasterType(source.getTaxMasterType().getTaxMasterId());
		tdh.setTaxType(source.getTaxType().getTaxTypeId());
		tdh.setTaxValidUpto(source.getTaxValidUpto());
		tdh.setTotalAmt(source.getTotalAmt());
		tdh.setTrFee(source.getTrFee());
		tdh.setTrServiceCharge(source.getTrServiceCharge());
		tdh.setVehicleRcId(source.getVehicleRcId().getVehicleRcId());
		try {
			tdh.setGreenTaxAmt(source.getGreenTaxAmt());
			tdh.setGreenTaxValidTo(source.getGreenTaxValidTo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tdh;
	}

}
