package org.rta.registration.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.CommunicationModel;
import org.rta.MessageConfig;
import org.rta.SmsEmailService;
import org.rta.core.dao.event.EventLogDAO;
import org.rta.core.entity.event.EventLogEntity;
import org.rta.core.enums.NotificationType;
import org.rta.core.model.customer.CustMsgModel;
import org.rta.core.service.application.ApplicationService;
import org.rta.core.service.payment.gateway.TransactionDetailService;
import org.rta.registration.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommunicationServiceImpl implements CommunicationService {

	private static final Logger log = Logger.getLogger(CommunicationServiceImpl.class);
	@Autowired
	SmsEmailService messageService;
	@Autowired
	MessageConfig config;

	@Autowired
	TransactionDetailService transactionDetailService;
	@Autowired
	ApplicationService applicatioService;
	
	@Autowired
	EventLogDAO eventLogDAO;

	@Value("${attachments.downloaded.path}")
	private String attachmentDocPath;

	public final static short SEND_SMS_EMAIL = 0;
	public final static short SEND_SMS = 1;
	public final static short SEND_EMAIL = 2;


	public String sendSms(CustMsgModel customerModel) {
		CommunicationModel model = new CommunicationModel(customerModel.getSmsMsg(), customerModel.getMobileNo());
		return messageService.sendSms(model, config);
	}

	public String sendEmail(CustMsgModel customerModel) {
		CommunicationModel model = new CommunicationModel(customerModel.getSubject(), customerModel.getTo(),
				customerModel.getCc(), customerModel.getBcc(), customerModel.getMailContent());
		model.setAttachments(customerModel.getAttachments());
		model.setCc(customerModel.getCc());
		model.setBcc(customerModel.getBcc());
		return messageService.sendEmail(model, config);
	}

	public CustMsgModel setCustModelForNotificationType(Long vehicleRcId, NotificationType notificationType) {
		CustMsgModel customerModel = null;
		try{
		if (notificationType == NotificationType.NOTIFICATION_FOR_PR_GEN)
			customerModel = applicatioService.getCustDtlsForVehicleRCPR(vehicleRcId,NotificationType.NOTIFICATION_FOR_PR_GEN);
		else if (notificationType == NotificationType.NOTIFICATION_FOR_TR_GEN)
			customerModel = transactionDetailService.getCustDtlsForVehicleRC(vehicleRcId);
		else if (notificationType == NotificationType.NOTIFICATION_FOR_APP_REJECTION)
			customerModel =  applicatioService.getCustDtlsForVehicleRCPR(vehicleRcId,NotificationType.NOTIFICATION_FOR_APP_REJECTION);
		else if (notificationType == NotificationType.NOTIFICATION_FOR_UPLOAD_CONSENT)
			customerModel =  applicatioService.getCustDtlsForVehicleRCPR(vehicleRcId,NotificationType.NOTIFICATION_FOR_UPLOAD_CONSENT);
		// FINANCER Fresh rc 
		else if (notificationType == NotificationType.NOTIFICATION_FOR_FINANCER_FRESH)
			customerModel =  applicatioService.getCustDtlsForVehicleRCPR(vehicleRcId,NotificationType.NOTIFICATION_FOR_FINANCER_FRESH);
		
		if (customerModel == null)
			throw new IllegalArgumentException("No Data received");
		}
		catch(Exception e){
		    log.error("Exception in setCustModelForNotificationType : " + e.getMessage());
			log.debug(e);
			return null;
		}
		// customerModel.setAttachments(attchments);
		return customerModel;
	}

	@Transactional
	public boolean sendMsg(int msgMode, CustMsgModel customerModel) {
		try {
			if (msgMode == SEND_SMS)
				sendSms(customerModel);
			if (msgMode == SEND_EMAIL)
				sendEmail(customerModel);
			if (msgMode == SEND_SMS_EMAIL) {
				sendSms(customerModel);
				sendEmail(customerModel);
			}
		} catch (IllegalArgumentException e) {
			return false;
		}

		return true;

	}

	@Transactional
	public void sendNotificationToCustomer(NotificationType notificationType) {

		List<String> attachmnets = null;
		List<EventLogEntity> vehiclesEvent = null;
		CustMsgModel custModel = null;

		if (notificationType == NotificationType.NOTIFICATION_FOR_PR_GEN)
			vehiclesEvent = eventLogDAO.getPendingNotificationForPr();
		else if (notificationType == NotificationType.NOTIFICATION_FOR_TR_GEN)
			vehiclesEvent = eventLogDAO.getPendingNotificationForTr();

		log.info(":::::::::::::::::::::::::::Number of applications for "+ notificationType.getLabel() +" ::::: "+ vehiclesEvent.size());
		// vehiclesEvent.parallelStream().forEach(vehicleEvnt->
		for (EventLogEntity vehicleEvnt : vehiclesEvent) {
			log.debug(":::::::::::::::::::::::::::"+notificationType.getLabel() + "for vehicleRcID : "+ vehicleEvnt.getVehicleRcId());
			custModel = setCustModelForNotificationType(vehicleEvnt.getVehicleRcId(), notificationType);
			if(custModel==null)
				continue;			
			switch (notificationType) {

			case NOTIFICATION_FOR_TR_GEN:
				log.debug(":::::::::::::::::::Tr Notification:::::::::::::");
				if (vehicleEvnt.getTrAttachement() != null)
					attachmnets = getAttachmentsFrmURL(vehicleEvnt.getTrAttachement());
				custModel.setAttachments(attachmnets);
				if (!vehicleEvnt.getTrSmsNotified()  && vehicleEvnt.getTrSmsIteration()<2) {
				vehicleEvnt.setTrSmsNotified(sendMsg(SEND_SMS, custModel));				
					vehicleEvnt.setTrSmsIteration(vehicleEvnt.getTrSmsIteration()+1);				
					log.debug("Tr Notification for vehicleRcId " + vehicleEvnt.getVehicleRcId()+ " for sms type response=" + vehicleEvnt.getTrSmsNotified());
				}
				
				if (!vehicleEvnt.getTrEmailNotified() && vehicleEvnt.getTrEmailIteration()<2) {
					vehicleEvnt.setTrEmailNotified(sendMsg(SEND_EMAIL, custModel));
					vehicleEvnt.setTrEmailIteration(vehicleEvnt.getTrEmailIteration()+1);
					log.debug("Tr Notification for vehicleRcId " + vehicleEvnt.getVehicleRcId()+ " for email type response=" + vehicleEvnt.getTrEmailNotified());
				}
				break;

			case NOTIFICATION_FOR_PR_GEN:
				log.debug(":::::::::::::::::::Pr Notification:::::::::::::");
				if (vehicleEvnt.getPrAttachement() != null  && vehicleEvnt.getPrSmsIteration()<2)
					attachmnets = getAttachmentsFrmURL(vehicleEvnt.getPrAttachement());
				custModel.setAttachments(attachmnets);
				if (!vehicleEvnt.getPrSmsNotified() &&  vehicleEvnt.getPrSmsIteration()<2) {
					vehicleEvnt.setPrSmsNotified(sendMsg(SEND_SMS, custModel));
					vehicleEvnt.setPrSmsIteration(vehicleEvnt.getPrSmsIteration()+1);
					log.debug("Pr Notification for vehicleRcId " + vehicleEvnt.getVehicleRcId()+ " for sms type response=" + vehicleEvnt.getPrSmsNotified());
				}
				if (!vehicleEvnt.getPrEmailNotified()  && vehicleEvnt.getPrEmailIteration()<2) {
					vehicleEvnt.setPrEmailNotified(sendMsg(SEND_EMAIL, custModel));
					vehicleEvnt.setPrEmailIteration(vehicleEvnt.getPrEmailIteration()+1);
					log.debug("Pr Notification for vehicleRcId " + vehicleEvnt.getVehicleRcId()
							+ " for email type response=" + vehicleEvnt.getPrEmailNotified());
				}
				break;

			}
			eventLogDAO.update(vehicleEvnt);

		}

	}

	private List<String> getAttachmentsFrmURL(String attchUrl) {
		String[] filePathNm = attchUrl.split(",");
		List<String> docAttach = new ArrayList<>(1);

		if (filePathNm.length < 1)
			new IllegalArgumentException("Attachments file name incorrect In DB");
		try {
			saveUrl(attachmentDocPath + File.separator + filePathNm[1], filePathNm[0]);
			docAttach.add(attachmentDocPath + File.separator + filePathNm[1] + "," + filePathNm[1].replace("%", "/"));
		} catch (IOException e) {
			log.error("IOException in getAttachmentsFrmURL : " + e.getMessage());
			log.debug(e);
			return null;
		}

		return docAttach;
	}

	public static void saveUrl(final String filename, final String urlString)
			throws MalformedURLException, IOException {
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			in = new BufferedInputStream(new URL(urlString).openStream());
			fout = new FileOutputStream(filename);
			final byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
		} catch (Exception e) {
			log.error("Exception in saveUrl : " + e.getMessage());
			log.debug(e);
		} finally {
			if (in != null) {
				in.close();
			}
			if (fout != null) {
				fout.close();
			}
		}
	}

}
