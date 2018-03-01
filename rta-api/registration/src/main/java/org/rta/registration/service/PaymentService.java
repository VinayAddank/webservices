package org.rta.registration.service;

import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.UserType;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.hsrp.HSRPTransactionModel;
import org.rta.core.model.payment.gateway.TransactionDetailModel;
import org.rta.core.model.user.DealerModel;
import org.springframework.util.MultiValueMap;

public interface PaymentService {

    public TransactionDetailModel encryptedSBIParameter(TransactionDetailModel transactionDetailModel,
            HSRPTransactionModel hsrpTransactionModel);

    public TransactionDetailModel secondVehicleEncryptedRequest(TransactionDetailModel transactionDetailModel);

    public TransactionDetailModel decryptSBIResponse(TransactionDetailModel transactionDetailModel);

    public TransactionDetailModel decryptSecondVehicleSBIResponse(TransactionDetailModel transactionDetailModel);

    /*
     * public TransactionDetailModel payVerification(TransactionDetailModel transactionDetailModel);
     */

    public TransactionDetailModel payVerification(Long vehicleRcId, TransactionDetailModel transactionDetailModel,
            Integer payType);

    public TransactionDetailModel decryptVerificationSBIResponse(TransactionDetailModel transactionDetailModel);

    public TransactionDetailModel adminSBIVerification(TransactionDetailModel transactionDetailModel);

    public TransactionDetailModel adminDecryptVerificationSBI(TransactionDetailModel transactionDetailModel);

    public String verificationWithScheduler(Double amount, String transactionNo);

    public TransactionDetailModel decryptVerificationScheduler(TransactionDetailModel transactionDetailModel);

    public SaveUpdateResponse verifyPendingTxn(long vehicleRcId, Integer payType, String userName, UserType userType);
    
    public TransactionDetailModel getPaymentParameter(TransactionDetailModel transactionDetailModel);
    
    public TransactionDetailModel getVerificationEncrytData(TransactionDetailModel transactionDetailModel);
    
    
    /**
     * @author ankur.goyal
     * PAYU
     */
    public TransactionDetailModel encryptedPayUParameter(TransactionDetailModel transactionDetailModel,
			HSRPTransactionModel hsrpTransactionModel, DealerModel dealerModel );
	
    public TransactionDetailModel decryptPayUResponse(TransactionDetailModel transactionDetailModel);

    public MultiValueMap<String, String> getPayUVerifyReqParams(TransactionDetailModel transactionDetailModel);

	TransactionDetailModel payUVerificationReq(Long vehicleRcId, TransactionDetailModel transactionDetailModel,
			Integer payType);

	boolean checkResponsePayUHash(TransactionDetailModel transactionDetailModel);
	
	public TransactionDetailModel payuSecondVehicleEncryptedRequest(TransactionDetailModel transactionDetailModel,DealerModel dealerModel);

	public TransactionDetailModel decryptPaymentResponse(TransactionDetailModel transactionDetailModel);
}
