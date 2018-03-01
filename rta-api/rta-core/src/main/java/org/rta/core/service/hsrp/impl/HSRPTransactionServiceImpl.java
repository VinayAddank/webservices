package org.rta.core.service.hsrp.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.hsrp.HSRPTransactionDAO;
import org.rta.core.dao.payment.gateway.TransactionDetailDAO;
import org.rta.core.entity.hsrp.HSRPTransactionEntity;
import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.entity.payment.invoice.DealerInvoiceEntity;
import org.rta.core.enums.PaymentType;
import org.rta.core.enums.Status;
import org.rta.core.helper.hsrp.HSRPTransactionHelper;
import org.rta.core.model.hsrp.HSRPTransactionModel;
import org.rta.core.service.hsrp.HSRPTransactionService;
import org.rta.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HSRPTransactionServiceImpl implements HSRPTransactionService {

    private static final Logger log = Logger.getLogger(HSRPTransactionServiceImpl.class);

    @Autowired
    private TransactionDetailDAO transactionDetailDAO;
    @Autowired
    private HSRPTransactionDAO hsrpTransactionDAO;
    @Value("${rta.sbi.Credit_Account_HSRP}")
    String creditAccountHSRP;
    @Autowired
    private HSRPTransactionHelper hsrpTransactionHelper;

    @Override
    @Transactional
    public HSRPTransactionModel createHSRPRequest(DealerInvoiceEntity dealerInvoiceEntity, String userName) {
        log.info("::::::::createHSRPRequest:::::start::::: ");
        TransactionDetailEntity transactionDetailEntity = transactionDetailDAO.getByVehicleRcIdNdPaymentType(
                dealerInvoiceEntity.getVehicleRcId().getVehicleRcId(), PaymentType.PAYTAX.getId());
        HSRPTransactionEntity hsrpTransactionEntity =
                hsrpTransactionDAO.getByVehicleRcId(dealerInvoiceEntity.getVehicleRcId().getVehicleRcId());
        if (hsrpTransactionEntity != null) {
            hsrpTransactionEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
            hsrpTransactionEntity.setModifiedBy(userName);
        } else {
            hsrpTransactionEntity = new HSRPTransactionEntity();
            hsrpTransactionEntity.setAmount(dealerInvoiceEntity.getHsrpFee());
            hsrpTransactionEntity.setCreditAccount(creditAccountHSRP);
            hsrpTransactionEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
            hsrpTransactionEntity.setCreatedBy(userName);
            hsrpTransactionEntity.setOtherChargesDescription("HSRP");
            hsrpTransactionEntity.setOtherChargesTID("HSRP" + transactionDetailEntity.getTransactionId().toString());
            hsrpTransactionEntity.setStatus(Status.OPEN.getLabel());
            hsrpTransactionEntity.setTransactionDetail(transactionDetailEntity);
            hsrpTransactionEntity.setVehicleRcId(transactionDetailEntity.getVehicleRcId());
            hsrpTransactionEntity.setRegistrationType(dealerInvoiceEntity.getVehicleRcId().getRegCategory().getRegistrationCategoryId());
            
        }
        hsrpTransactionDAO.saveOrUpdate(hsrpTransactionEntity);
        HSRPTransactionModel hsrpTransactionModel = hsrpTransactionHelper.convertToModel(hsrpTransactionEntity);
        return hsrpTransactionModel;
    }

}
