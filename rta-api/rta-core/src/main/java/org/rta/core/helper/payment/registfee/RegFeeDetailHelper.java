package org.rta.core.helper.payment.registfee;

import java.util.Collection;

import org.rta.core.entity.payment.registfee.RegFeeDetailsEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.payment.registfee.RegFeeDetailModel;
import org.rta.core.utils.NumberParser;
import org.springframework.stereotype.Component;

@Component
public class RegFeeDetailHelper implements BaseConverter<RegFeeDetailsEntity, RegFeeDetailModel> {

    @Override
    public RegFeeDetailModel convertToModel(RegFeeDetailsEntity source) {
        RegFeeDetailModel regFeeModel = new RegFeeDetailModel();
        regFeeModel.setImportedFee(NumberParser.numberFormat(source.getImportedFee()));
        regFeeModel.setPostalCharge(NumberParser.numberFormat(source.getPostalCharge()));
        regFeeModel.setRegFee(NumberParser.numberFormat(source.getPrFee()));
        regFeeModel.setRegFeeDtlId(source.getRegFeeDtlId());
        regFeeModel.setServiceCharge(NumberParser.numberFormat(source.getPrServiceCharge()));
        regFeeModel.setSmartCardFee(NumberParser.numberFormat(source.getSmartCardFee()));
        regFeeModel.setSpecialNumberFee(NumberParser.numberFormat(source.getSpecialNumberFee()));
        regFeeModel.setTotalRegFee(NumberParser.numberFormat(source.getTotalFee()));
        regFeeModel.setTrFee(NumberParser.numberFormat(source.getTrFee()));
        regFeeModel.setTrServiceCharges(NumberParser.numberFormat(source.getTrServiceCharge()));
        regFeeModel.setHpaFee(NumberParser.numberFormat(source.getHpaFee()));
        return regFeeModel;
    }

    @Override
    public RegFeeDetailsEntity convertToEntity(RegFeeDetailModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<RegFeeDetailModel> convertToModelList(Collection<RegFeeDetailsEntity> source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<RegFeeDetailsEntity> convertToEntityList(Collection<RegFeeDetailModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
