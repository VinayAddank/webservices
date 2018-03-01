/**
 * 
 */
package org.rta.core.service.insurance.impl;

import javax.transaction.Transactional;

import org.rta.core.dao.insurance.CfxTxnDtlDAO;
import org.rta.core.entity.insurance.CfxTxnDtlEntity;
import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.Status;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.insurance.CfxTxnDtlModel;
import org.rta.core.service.insurance.CfxTxnDtlService;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author arun.verma
 *
 */
@Service
public class CfxTxnDtlServiceImpl implements CfxTxnDtlService {
    
    @Autowired
    CfxTxnDtlDAO cfxTxnDtlDAO;

    @Override
    @Transactional
    public CfxTxnDtlModel getByRtaCfxTxnId(String rtaCfxTxnId) {
        return null;
    }

    @Override
    @Transactional
    public CfxTxnDtlModel getByCfxTxnId(String cfxTxnId) {
        return null;
    }

    @Override
    @Transactional
    public CfxTxnDtlModel getByCfxPaymentId(String cfxPaymentId) {
        return null;
    }

    @Override
    @Transactional
    public SaveUpdateResponse saveUpdate(CfxTxnDtlModel cfxTxnDtlModel) {
        CfxTxnDtlEntity entity = cfxTxnDtlDAO.getByCfxTxnId(cfxTxnDtlModel.getCfxTxnId());
        if(ObjectsUtil.isNull(entity)){
            entity = new CfxTxnDtlEntity();
        }
        entity.setAmount(cfxTxnDtlModel.getAmount());
        entity.setCfxPaymentId(cfxTxnDtlModel.getCfxTxnId());
        entity.setCfxTxnId(cfxTxnDtlModel.getCfxTxnId());
        entity.setDateTime(cfxTxnDtlModel.getDateTime());
        entity.setDescription(cfxTxnDtlModel.getDescription());
        entity.setPaymentStatus(cfxTxnDtlModel.getPaymentStatus());
        entity.setPgPaymentToken(cfxTxnDtlModel.getPgPaymentToken());
        entity.setPolicyDoc(cfxTxnDtlModel.getPolicyDoc());
        entity.setPolicyNumber(cfxTxnDtlModel.getPolicyNumber());
        entity.setPolicyStatus(cfxTxnDtlModel.getPolicyStatus());
        entity.setPoolingAcNumber(cfxTxnDtlModel.getPoolingAcNumber());
        entity.setRtaCfxTxnId(cfxTxnDtlModel.getRtaCfxTxnId());
        entity.setCustomerName(cfxTxnDtlModel.getCustomerName());
        entity.setCustomerEmail(cfxTxnDtlModel.getCustomerEmail());
        entity.setCustomerPhone(cfxTxnDtlModel.getCustomerPhone());
        TransactionDetailEntity txnDtl = new TransactionDetailEntity();
        txnDtl.setTransactionId(cfxTxnDtlModel.getTransactionDetailId());
        entity.setTransactionDetail(txnDtl);
        VehicleRCEntity vehicleRCEntity = new VehicleRCEntity();
        vehicleRCEntity.setVehicleRcId(cfxTxnDtlModel.getVehicleRcId());
        entity.setVehicleRcId(vehicleRCEntity);
        cfxTxnDtlDAO.saveOrUpdate(entity);
        SaveUpdateResponse response = new SaveUpdateResponse();
        response.setStatus(SaveUpdateResponse.SUCCESS);
        return response;
    }

    @Override
    @Transactional
    public SaveUpdateResponse updatePaymentStatus(String cfxTxnId, String success, String code) {
        CfxTxnDtlEntity entity = cfxTxnDtlDAO.getByCfxTxnId(cfxTxnId);
        if(ObjectsUtil.isNull(entity)){
            throw new IllegalArgumentException("Invalid paymentId !!");
        }
        if(success.equalsIgnoreCase("true")){
            entity.setPaymentStatus(Status.CLOSED.getValue());
            entity.setPolicyStatus(Status.PENDING.getValue());
            cfxTxnDtlDAO.saveOrUpdate(entity);
        }
        SaveUpdateResponse response = new SaveUpdateResponse();
        response.setStatus(SaveUpdateResponse.SUCCESS);
        return response;
    }

    @Override
    public SaveUpdateResponse updatePolicy(String cfxTxnId, String success, String policyNumber, String policyDoc) {
        CfxTxnDtlEntity entity = cfxTxnDtlDAO.getByCfxTxnId(cfxTxnId);
        if(ObjectsUtil.isNull(entity)){
            throw new IllegalArgumentException("Invalid paymentId !!");
        }
        if(success.equalsIgnoreCase("true")){
            entity.setPolicyNumber(policyNumber);
            entity.setPolicyDoc(policyDoc);
            if(!policyDoc.equalsIgnoreCase("InProcess")){
                entity.setPolicyStatus(Status.CLOSED.getValue());
            }
            cfxTxnDtlDAO.saveOrUpdate(entity);
        }
        SaveUpdateResponse response = new SaveUpdateResponse();
        response.setStatus(SaveUpdateResponse.SUCCESS);
        return response;
    }

}
