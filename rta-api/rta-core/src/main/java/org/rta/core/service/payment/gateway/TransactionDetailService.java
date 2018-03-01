package org.rta.core.service.payment.gateway;

import java.util.List;

import org.rta.core.entity.payment.invoice.DealerInvoiceEntity;
import org.rta.core.enums.PaymentGatewayType;
import org.rta.core.enums.UserType;
import org.rta.core.exception.NotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.customer.CustMsgModel;
import org.rta.core.model.payment.gateway.TransactionDetailModel;

public interface TransactionDetailService {

    public TransactionDetailModel createPaymentRequest(DealerInvoiceEntity dealerInvoiceEntity, String userName , String challanNo,PaymentGatewayType pgType);

    public TransactionDetailModel secondVehiclePaymentRequest(DealerInvoiceEntity dealerInvoiceEntity, String userName , String ChallanNo,PaymentGatewayType pg);

    public SaveUpdateResponse gatewayResponse(TransactionDetailModel transactionDetailModel, String userName,
            Long vehicleRcId, boolean isSecondVehiclePay, UserType userType);

    public CustMsgModel getCustDtlsForVehicleRC(Long vehicleRcId);

    public TransactionDetailModel paymentVerificationReq(Long vehiclRcId, String userName, Integer payType,PaymentGatewayType pgType);

    public int[] isSBIVerificationForSecondVehicle(Long vehiclRcId);

    /* public void paymentStatusFailed(Long vehicleRcId, String userName, int paymentType);*/ 

    public boolean isAppActive(Long vehicleRcId);

    public List<TransactionDetailModel> getAllTransactionByAppNo(String applicationNo);
    public String getChallanNumber();
    
    
    boolean checkTRSeriesStatus(Long vehicleRcId) throws NotFoundException;
    
    boolean checkPaymentDone(Long vehicleRcId , int paytype);
    
    SaveUpdateResponse gatewayPayUResponse(TransactionDetailModel transactionDetailModel, String userName,
			Long vehicleRcId, boolean isSecondVehiclePay, UserType userType);
    public void updatePayUFaliureResponse(String txnNumber,Long vehicleRcId,String userName,String message);
    
    public String getChallanNumberForFreshRegist(long vehiclRcId , Integer payType);
}
