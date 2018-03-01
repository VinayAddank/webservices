package org.rta.core.model.payment.gateway;

import java.util.List;

public class PayUProductInfo {

	private List<TreasureHeadDetails> treasureHeads;
	private List<PayuPaymentPart> paymentParts;

	public List<TreasureHeadDetails> getTreasureHeads() {
		return treasureHeads;
	}

	public void setTreasureHeads(List<TreasureHeadDetails> treasureHeads) {
		this.treasureHeads = treasureHeads;
	}

	public List<PayuPaymentPart> getPaymentParts() {
		return paymentParts;
	}

	public void setPaymentParts(List<PayuPaymentPart> paymentParts) {
		this.paymentParts = paymentParts;
	}

}
