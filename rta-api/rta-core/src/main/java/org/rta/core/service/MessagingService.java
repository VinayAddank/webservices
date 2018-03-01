package org.rta.core.service;

import org.rta.core.model.customer.CustMsgModel;

public interface MessagingService {

    public String sendEmail(CustMsgModel customerModel);
    
}
