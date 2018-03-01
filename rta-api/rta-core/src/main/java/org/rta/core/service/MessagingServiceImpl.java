package org.rta.core.service;

import org.apache.log4j.Logger;
import org.rta.CommunicationModel;
import org.rta.MessageConfig;
import org.rta.SmsEmailService;
import org.rta.core.dao.event.EventLogDAO;
import org.rta.core.model.customer.CustMsgModel;
import org.rta.core.service.application.ApplicationService;
import org.rta.core.service.payment.gateway.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MessagingServiceImpl implements MessagingService {

    private static final Logger log = Logger.getLogger(MessagingServiceImpl.class);
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
    
    @Override
    @Async
    public String sendEmail(CustMsgModel customerModel) {
        return messageService.sendEmail(new CommunicationModel(customerModel.getSubject(), customerModel.getTo(),
                customerModel.getCc(), customerModel.getBcc(), customerModel.getMailContent()), config);
    }

}
