package org.rta.core.helper.payment.registfee;

import java.util.Collection;

import org.rta.core.entity.payment.registfee.PermitFeeEntity;
import org.rta.core.enums.PermitType;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.payment.registfee.PermitFeeModel;
import org.rta.core.utils.NumberParser;
import org.springframework.stereotype.Component;

@Component
public class PermitFeeHelper implements BaseConverter<PermitFeeEntity, PermitFeeModel> {

    @Override
    public PermitFeeModel convertToModel(PermitFeeEntity source) {
        PermitFeeModel permitFeeModel = new PermitFeeModel();
        permitFeeModel.setPermitFee(NumberParser.numberFormat(source.getPermitFee()));
        permitFeeModel.setOtherFee(NumberParser.numberFormat(source.getOtherPermitFee()));
        permitFeeModel.setPermitServiceCharge(NumberParser.numberFormat(source.getPermitService()));
        permitFeeModel.setTotalPermitFee(NumberParser.numberFormat(source.getTotalpermitFee()));
        System.out.println("::source.getPermitTypeCode():::" +source.getPermitTypeCode());
        if(source.getPermitTypeCode() != null && !source.getPermitTypeCode().equalsIgnoreCase(""))
		try {permitFeeModel.setPermitType(PermitType.valueOf(source.getPermitTypeCode()).getLabel());
		} catch (Exception e) {
		e.printStackTrace();
		}
        return permitFeeModel;
    }

    @Override
    public PermitFeeEntity convertToEntity(PermitFeeModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<PermitFeeModel> convertToModelList(Collection<PermitFeeEntity> source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<PermitFeeEntity> convertToEntityList(Collection<PermitFeeModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
