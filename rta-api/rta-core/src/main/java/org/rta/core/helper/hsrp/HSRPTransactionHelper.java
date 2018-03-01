package org.rta.core.helper.hsrp;

import java.util.Collection;

import org.rta.core.entity.hsrp.HSRPTransactionEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.hsrp.HSRPTransactionModel;
import org.springframework.stereotype.Component;

@Component
public class HSRPTransactionHelper implements BaseConverter<HSRPTransactionEntity, HSRPTransactionModel> {

    @Override
    public HSRPTransactionModel convertToModel(HSRPTransactionEntity source) {
        HSRPTransactionModel hsrpTransactionModel = new HSRPTransactionModel();
        hsrpTransactionModel.setAmount(source.getAmount());
        hsrpTransactionModel.setCreditAccount(source.getCreditAccount());
        hsrpTransactionModel.setHsrpTransactionId(source.getHsrpTransactionId());
        hsrpTransactionModel.setOtherChargesDescription(source.getOtherChargesDescription());
        hsrpTransactionModel.setOtherChargesTID(source.getOtherChargesTID());
        return hsrpTransactionModel;
    }

    @Override
    public HSRPTransactionEntity convertToEntity(HSRPTransactionModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<HSRPTransactionModel> convertToModelList(Collection<HSRPTransactionEntity> source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<HSRPTransactionEntity> convertToEntityList(Collection<HSRPTransactionModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
