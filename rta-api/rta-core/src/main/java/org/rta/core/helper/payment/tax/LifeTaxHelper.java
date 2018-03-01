package org.rta.core.helper.payment.tax;

import java.util.Collection;

import org.rta.core.entity.payment.tax.LifeTaxEntity;
import org.rta.core.enums.master.TaxType;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.payment.tax.TaxDetailModel;
import org.rta.core.utils.NumberParser;
import org.springframework.stereotype.Component;

@Component
public class LifeTaxHelper implements BaseConverter<LifeTaxEntity, TaxDetailModel> {

	@Override
	public TaxDetailModel convertToModel(LifeTaxEntity source) {
		TaxDetailModel td = new TaxDetailModel();
		td.setTaxAmt(NumberParser.numberFormat(source.getTaxAmt()));
		td.setTaxPercentage(NumberParser.numberFormat(source.getTaxPercent()));
		td.setTaxType(TaxType.LIFE_TAX.getLabel());
		td.setTotalAmt(NumberParser.numberFormat(source.getTotalTax()));
		td.setSecondVehicleTaxAmt(NumberParser.numberFormat(source.getSecondVehicleTaxAmt()));
		td.setTaxValidUpto(source.getTaxValidUpto());
		return td;
	}

	@Override
	public LifeTaxEntity convertToEntity(TaxDetailModel source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<TaxDetailModel> convertToModelList(Collection<LifeTaxEntity> source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<LifeTaxEntity> convertToEntityList(Collection<TaxDetailModel> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
