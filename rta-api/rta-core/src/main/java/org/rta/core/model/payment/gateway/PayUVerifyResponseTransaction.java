package org.rta.core.model.payment.gateway;

public class PayUVerifyResponseTransaction {

	private String merchantTransactionId;
	private PayUTransactionDetails postBackParam;

	public String getMerchantTransactionId() {
		return merchantTransactionId;
	}

	public void setMerchantTransactionId(String merchantTransactionId) {
		this.merchantTransactionId = merchantTransactionId;
	}

	public PayUTransactionDetails getPostBackParam() {
		return postBackParam;
	}

	public void setPostBackParam(PayUTransactionDetails postBackParam) {
		this.postBackParam = postBackParam;
	}

}
