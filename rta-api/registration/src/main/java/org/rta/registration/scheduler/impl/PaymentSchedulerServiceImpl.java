package org.rta.registration.scheduler.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.payment.gateway.TransactionDetailDAO;
import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.model.payment.gateway.TransactionDetailModel;
import org.rta.core.utils.DateUtil;
import org.rta.registration.scheduler.PaymentSchedulerService;
import org.rta.registration.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentSchedulerServiceImpl implements PaymentSchedulerService {

    private static final Logger log = Logger.getLogger(PaymentSchedulerServiceImpl.class);
    private static final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    public TransactionDetailDAO transactionDetailDAO;
    @Autowired
    public PaymentService paymentService;
    @Value("${rta.sbi.verification_URL}")
    public String verification_URL;

    @Override
    @Transactional
    public void sbiVerificationScheduler() {
    /*    log.info(":::::::sbiVerificationScheduler::::::::::");
        List<TransactionDetailEntity> transactionDetailList = transactionDetailDAO.getAllFailed();
        if (transactionDetailList != null)
            log.debug(":::sbiVerificationScheduler::::List::::: " + transactionDetailList.size());
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map = null;
        HttpEntity<MultiValueMap<String, String>> entity = null;
        TransactionDetailModel transactionDetailModel = null;
        for (TransactionDetailEntity transactionDetailEntity : transactionDetailList) {
            try {
                String encryptData = paymentService.verificationWithScheduler(transactionDetailEntity.getPayAmount(),
                        transactionDetailEntity.getTransactionNo());
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                map = new LinkedMultiValueMap<>();
                map.add("encdata", encryptData);
                map.add("merchant_code", "APT_GOVT");
                entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
                ResponseEntity<String> response =
                        restTemplate.exchange(verification_URL, HttpMethod.POST, entity, String.class);
                String decryptData = response.getBody().substring(response.getBody().indexOf("value=") + 7,
                        response.getBody().indexOf("\"/>"));
                transactionDetailModel = new TransactionDetailModel();
                transactionDetailModel.setEncryptRequest(decryptData);
                transactionDetailModel = paymentService.decryptVerificationScheduler(transactionDetailModel);
                if (transactionDetailModel.getStatus().equalsIgnoreCase("SUCCESS")) {
                    transactionDetailEntity.setSbiRefNo(transactionDetailModel.getSbiResponseMap().get("sbi_ref_no"));
                    transactionDetailEntity.setStatusMessage(transactionDetailModel.getSbiResponseMap().get("Status_desc"));
                    transactionDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());;
                    transactionDetailDAO.saveOrUpdate(transactionDetailEntity);
                } else {
                    continue;
                }
            } catch (Exception e) {
            	log.error("Exception in sbiVerificationScheduler : " + e.getMessage());
                log.debug(e);
                continue;
            }
        }
*/    }
}
