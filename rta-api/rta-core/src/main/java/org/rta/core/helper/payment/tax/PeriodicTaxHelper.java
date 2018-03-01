package org.rta.core.helper.payment.tax;

import java.util.Collection;

import org.rta.core.entity.payment.tax.PeriodicTaxEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.payment.tax.TaxDetailModel;
import org.rta.core.utils.NumberParser;
import org.springframework.stereotype.Component;

@Component
public class PeriodicTaxHelper implements BaseConverter<PeriodicTaxEntity, TaxDetailModel> {

	
	@Override
	public TaxDetailModel convertToModel(PeriodicTaxEntity source) {
		TaxDetailModel td = new TaxDetailModel();
		td.setTaxAmt(NumberParser.numberFormat(source.getTaxAmt()));
		td.setTaxPercentage("0");
		td.setTaxType(source.getTaxTypeId().getName());
		td.setTotalAmt(NumberParser.numberFormat(source.getTotalAmt()));
		td.setTaxValidUpto(source.getTaxValidUpto());
		return td;
	}

	@Override
	public PeriodicTaxEntity convertToEntity(TaxDetailModel source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<TaxDetailModel> convertToModelList(Collection<PeriodicTaxEntity> source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<PeriodicTaxEntity> convertToEntityList(Collection<TaxDetailModel> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
