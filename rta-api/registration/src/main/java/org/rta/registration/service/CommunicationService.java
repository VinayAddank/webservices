package org.rta.registration.service;

import org.rta.core.enums.NotificationType;
import org.rta.core.model.customer.CustMsgModel;

public interface CommunicationService {
	public String sendSms(CustMsgModel customerModel);

	public String sendEmail(CustMsgModel customerModel);

	public void sendNotificationToCustomer(NotificationType notificationType);

	public CustMsgModel setCustModelForNotificationType(Long vehicleRcId, NotificationType notificationType);
	
	public boolean sendMsg(int msgMode, CustMsgModel customerModel) ;

}
