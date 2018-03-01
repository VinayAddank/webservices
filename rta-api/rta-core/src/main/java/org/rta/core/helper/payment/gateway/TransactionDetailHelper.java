package org.rta.core.helper.payment.gateway;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.enums.PaymentGatewayType;
import org.rta.core.enums.Status;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.payment.gateway.TransactionDetailModel;
import org.rta.core.utils.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class TransactionDetailHelper implements BaseConverter<TransactionDetailEntity, TransactionDetailModel> {

    @Override
    public TransactionDetailModel convertToModel(TransactionDetailEntity source) {
        TransactionDetailModel transactionDetailModel = new TransactionDetailModel();
        transactionDetailModel.setTransactionId(source.getTransactionId());
        transactionDetailModel.setAmount(source.getPayAmount());
        transactionDetailModel.setFeeAmt(source.getFeeAmt());
        transactionDetailModel.setServiceCharge(source.getServiceCharge());
        transactionDetailModel.setTaxAmt(source.getTaxAmt());
        transactionDetailModel.setPostalCharge(source.getPostalCharge());
        transactionDetailModel.setHsrpAmt(source.getHsrpAmt());
        transactionDetailModel.setTransactionNo(source.getTransactionNo());
        transactionDetailModel.setPgType(source.getPgType());
        transactionDetailModel.setRegType(source.getVehicleRcId().getRegCategory().getRegistrationCategoryId());
        transactionDetailModel.setCessFee(source.getCessFee());
        return transactionDetailModel;
    }

    @Override
    public TransactionDetailEntity convertToEntity(TransactionDetailModel source) {
        TransactionDetailEntity transactionDetailEntity = new TransactionDetailEntity();
        transactionDetailEntity.setPayAmount(source.getAmount());
        transactionDetailEntity.setTransactionNo(source.getTransactionNo());
        return transactionDetailEntity;
    }

    @Override
    public Collection<TransactionDetailModel> convertToModelList(Collection<TransactionDetailEntity> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToVerifySBIModel(s)).collect(Collectors.toList());
    }

    public TransactionDetailModel convertToVerifySBIModel(TransactionDetailEntity source) {
        if (source == null) {
            return null;
        }
        TransactionDetailModel tm = new TransactionDetailModel();
        tm.setAmount(source.getPayAmount());
        tm.setMessage(source.getStatusMessage());
        if (tm.getMessage() != null && tm.getMessage().contains("RTA Marked Status Failed"))
            tm.setMessage("Pending Transaction (Cancel)");
        tm.setSbiRefNo(source.getSbiRefNo());
        String responseStatus = "";
        switch (Status.getStatus(source.getStatus())) {
            case OPEN:
                responseStatus = "No Response From SBI";
                break;
            case PENDING:
                responseStatus = "Pending From SBI End";
                break;
            case FAILURE:
                responseStatus = "Failed From SBI End";
                break;
            case SUCCESS:
                responseStatus = "TR Genrated";
                break;
                
        }
        tm.setStatus(responseStatus);
        tm.setTransactionNo(source.getTransactionNo() + "1");
        tm.setVehicleRcId(source.getVehicleRcId().getVehicleRcId().toString());
        tm.setCreatedBy(source.getCreatedBy());
        tm.setPaymentDate(DateUtil.extractDateAsStringWithHyphen(source.getPaymentTime()));
        return tm;
    }

    @Override
    public Collection<TransactionDetailEntity> convertToEntityList(Collection<TransactionDetailModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
