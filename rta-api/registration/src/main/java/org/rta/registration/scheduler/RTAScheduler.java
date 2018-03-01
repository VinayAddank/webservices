package org.rta.registration.scheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.rta.core.enums.NotificationType;
import org.rta.core.model.customer.CustMsgModel;
import org.rta.core.service.application.ApplicationService;
import org.rta.core.service.tempreport.TempReportService;
import org.rta.core.utils.DateUtil;
import org.rta.registration.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

public class RTAScheduler {
	private static final Logger log = Logger.getLogger(RTAScheduler.class);
	@Autowired
	private CommunicationService commService;

	@Autowired
	private HSRPSchedulerService rtaSchedulerService;
	
	@Autowired
	private TempReportService tempService;
	
    @Autowired
    private PaymentSchedulerService paymentSchedulerService;

	@Value("${rta.reports.mail.receiver}")
	String mailreceivers;
	
	@Value("${rta.report.destination}")
	private String reportPath;
	
    @Autowired
    private ApplicationService applicationService;


	public RTAScheduler() {

	}
	@Scheduled(cron = "${rta.scheduler.notification.pr}")
	public void messagingSchedulerForPr() {
		log.info("::::::::::::::Pr scheduler calling at" + new Date());
		commService.sendNotificationToCustomer(NotificationType.NOTIFICATION_FOR_PR_GEN);
	}

	@Scheduled(cron = "${rta.scheduler.notification.tr}")
	public void messagingSchedulerForTr() {
		log.info(":::::::::::::::Tr scheduler calling at " + new Date());
		commService.sendNotificationToCustomer(NotificationType.NOTIFICATION_FOR_TR_GEN);
	}

	@Scheduled(cron = "${hsrp.updatedata}")
	public void updateHSRPTable() {
		log.info("::updateHSRPTable:::");
        rtaSchedulerService.updateHSRPDetailTable(null, null);
    }
	
    @Scheduled(cron = "${hsrp.postdata.tr.time}")
	public void postTRDataToHSRP() {
		log.info("::::::::::::postTRDataToHSRP:::::::::::");
        rtaSchedulerService.postTRDataToHSRP(null, null);
    }

    @Scheduled(cron = "${hsrp.postdata.pr.time}")
	public void postPRDataToHSRP() {
		log.info("::::::postPRDataToHSRP::::::::");
        rtaSchedulerService.postPRDataToHSRP(null, null);
    }
	
	@Scheduled(cron = "${rta.scheduler.reportgen}")
	public void reportScheduler() {
		log.info("::::::::::::::Report Scheduler:::::::::::::" + new Date());
		Date dateToday=new Date();
		Calendar startTime=Calendar.getInstance();
		startTime.setTime(dateToday);
		startTime.roll(Calendar.DATE, -1);
		startTime.set(Calendar.HOUR_OF_DAY, 0);
		startTime.set(Calendar.MINUTE, 0);
		startTime.set(Calendar.SECOND, 0);
		
		Calendar totalTime=Calendar.getInstance();
		totalTime.set(2016, 10, 1);
		totalTime.set(Calendar.HOUR_OF_DAY, 0);
		totalTime.set(Calendar.MINUTE, 0);
		totalTime.set(Calendar.SECOND, 0);

		Calendar endTime=Calendar.getInstance();
		endTime.setTime(dateToday);
//		endTime.roll(Calendar.DATE, -1);
		endTime.set(Calendar.HOUR_OF_DAY, 0);
		endTime.set(Calendar.MINUTE, 0);
		endTime.set(Calendar.SECOND, 0);
		log.info("::::::::::::::Report Generation Between"+startTime.getTime()+":"+endTime.getTime());
		long totalT = DateUtil.toUTCTimeStamp(totalTime.getTime());
		long startT =DateUtil.toUTCTimeStamp(startTime.getTime());
		long endT =DateUtil.toUTCTimeStamp(endTime.getTime());
		startT=startT/1000;
		endT=endT/1000;
//		System.out.println(DateUtil.fromUTCTimeStamp(startT)+":"+DateUtil.fromUTCTimeStamp(endT));
		Map<Long, String[]> response = tempService.getDealerApplicationSummaryReport(startT, endT);
		Map<String, Map<String, Map<String, Integer>>> data = tempService.getRtaOfficeWiseReport(startT, endT);
		tempService.getRTOApplication(totalT, endT);
		CustMsgModel emailRptModel=getCustModelToSendEmail(startT,endT);
		
		List<String> attachments=new ArrayList<String>();
		if(response!=null)
		attachments.add(reportPath + File.separator + "dealerApplicationSummaryReport_" + startT + ".csv,dealerApplicationSummaryReport_" + startT + ".csv");
		if(data!=null)
		attachments.add(reportPath + File.separator + "rtaofficewise_" + startT + ".csv,rtaofficewise_" + startT + ".csv");
		attachments.add( reportPath + File.separator + "rtosummaryreport_" + startT + ".csv,rtosummaryreport_" + startT + ".csv");
		emailRptModel.setAttachments(attachments);
		
		commService.sendEmail(emailRptModel);
		
	}
	
	private CustMsgModel getCustModelToSendEmail(long from,long to){
		CustMsgModel custMsgModel=new CustMsgModel();
		custMsgModel = new CustMsgModel();
		Date date=DateUtil.fromUTCTimeStamp(from*1000);
		Date toDate=DateUtil.fromUTCTimeStamp(to*1000);
        custMsgModel.setMailContent("Please Find attached files for status Review from-to ("+date+"-"+toDate+")");
		custMsgModel.setBcc("");
//		custMsgModel.setCc(vehicleEntity.getUserId().getEmail());
		custMsgModel.setSubject("Daily RTA Progress Reports");
		custMsgModel.setTo(mailreceivers);
		return custMsgModel;
	
	} 
// To Do sandeep	
    /*@Scheduled(cron = "${sbi.verification.failed}")
    public void sbiVerificationScheduler() {
        log.info("::::::sbiVerificationScheduler::::::::");
        paymentSchedulerService.sbiVerificationScheduler();
    }*/

    @Scheduled(cron = "${rta.scheduler.openedapp}")
    public void openApplicationScheduler() {
        log.info("scheduler for opened application started");
        try {
            applicationService.updateOpenedApplications1();
        } catch (Exception e) {
            log.error("couldn't update open applications", e);
        }
        /*
         * RTA-3235 Revocation of Suspension and in Migration - After suspension period completed RC must not be automatically revocated citizen must apply for revocation of suspension
         * 
         * try {
            applicationService.revocateSuspensionExpiredVehicles();
        } catch (Exception e) {
            log.error("couldn't revocate expired suspension vehicles ", e);
        }*/
    	log.info("scheduler for opened application completed");
    }
    

}
