package org.rta.core.entity.applicant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "aadhaar_logs")
public class AadhaarLogEntity extends BaseEntity {

    private static final long serialVersionUID = 3173345305933275752L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aadhaar_log_seq")
    @SequenceGenerator(name = "aadhaar_log_seq", sequenceName = "aadhaar_log_seq", allocationSize = 1)
    private Long logId;

    @Column(name = "req_uid")
    private String reqUid;
    
    @Column(name = "req_tid")
    private String reqTid;

    @Column(name = "req_provider")
    private String reqProvider;

    @Column(name = "req_time")
    private String reqTime;

    @Column(name = "req_agency_name")
    private String reqAgencyName;

    @Column(name = "req_agency_code")
    private String reqAgencyCode;

    @Column(name = "req_device_name")
    private String reqDeviceName;

    @Column(name = "req_device_code")
    private String reqDeviceCode;

    @Column(name = "req_server_date_time")
    private String reqServerDateTime;
    
    @Column(name = "req_client_date_time")
    private String reqClientDateTime;
   
    @Column(name = "req_rds_ver")
    private String reqRdsVer;
    
    @Column(name = "req_consent_me")
    private String reqConsentMe;
    
    @Column(name = "req_service")
    private String reqService;
     
    @Column(name = "req_attempt_type")
    private String reqAttemptType;
    
    @Column(name = "req_auth_type")
    private String reqAuthType;

    @Column(name = "req_rds_id")
    private String reqRdsId;
    
    @Column(name = "req_end_point_url")
    private String reqEndPointUrl;

    @Column(name = "req_dp_id")
    private String reqDpId;
    
    @Column(name = "req_dc")
    private String reqDc;
    
    @Column(name = "req_mi")
    private String reqMi;
    
/*    @Column(name = "req_mc")
    private String reqMc;*/
    
    @Column(name = "resp_ksa_kua_txn")
    private String respKsaKuaTxn;

    @Column(name = "resp_auth_error_code")
    private String respAuthErrorCode;

    @Column(name = "resp_auth_date")
    private String respAuthDate;

    @Column(name = "resp_auth_status")
    private String respAuthStatus;

    @Column(name = "resp_auth_transaction_code")
    private String respAuthTransactionCode;

    @Column(name = "resp_name")
    private String respName;

    @Column(name = "resp_mandal")
    private String respMandal;

    @Column(name = "resp_state_code")
    private String respStatecode;
    
    @Column(name = "resp_time")
    private String respTime;


    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getReqUid() {
        return reqUid;
    }

    public void setReqUid(String reqUid) {
        this.reqUid = reqUid;
    }

    public String getReqTid() {
        return reqTid;
    }

    public void setReqTid(String reqTid) {
        this.reqTid = reqTid;
    }

    public String getReqProvider() {
        return reqProvider;
    }

    public void setReqProvider(String reqProvider) {
        this.reqProvider = reqProvider;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getReqAgencyName() {
        return reqAgencyName;
    }

    public void setReqAgencyName(String reqAgencyName) {
        this.reqAgencyName = reqAgencyName;
    }

    public String getReqAgencyCode() {
        return reqAgencyCode;
    }

    public void setReqAgencyCode(String reqAgencyCode) {
        this.reqAgencyCode = reqAgencyCode;
    }

    public String getReqDeviceName() {
        return reqDeviceName;
    }

    public void setReqDeviceName(String reqDeviceName) {
        this.reqDeviceName = reqDeviceName;
    }

    public String getReqDeviceCode() {
        return reqDeviceCode;
    }

    public void setReqDeviceCode(String reqDeviceCode) {
        this.reqDeviceCode = reqDeviceCode;
    }

    public String getReqServerDateTime() {
        return reqServerDateTime;
    }

    public void setReqServerDateTime(String reqServerDateTime) {
        this.reqServerDateTime = reqServerDateTime;
    }

    public String getReqClientDateTime() {
        return reqClientDateTime;
    }

    public void setReqClientDateTime(String reqClientDateTime) {
        this.reqClientDateTime = reqClientDateTime;
    }

    public String getRespKsaKuaTxn() {
        return respKsaKuaTxn;
    }

    public void setRespKsaKuaTxn(String respKsaKuaTxn) {
        this.respKsaKuaTxn = respKsaKuaTxn;
    }

    public String getRespAuthErrorCode() {
        return respAuthErrorCode;
    }

    public void setRespAuthErrorCode(String respAuthErrorCode) {
        this.respAuthErrorCode = respAuthErrorCode;
    }

    public String getRespAuthDate() {
        return respAuthDate;
    }

    public void setRespAuthDate(String respAuthDate) {
        this.respAuthDate = respAuthDate;
    }

    public String getRespAuthStatus() {
        return respAuthStatus;
    }

    public void setRespAuthStatus(String respAuthStatus) {
        this.respAuthStatus = respAuthStatus;
    }

    public String getRespAuthTransactionCode() {
        return respAuthTransactionCode;
    }

    public void setRespAuthTransactionCode(String respAuthTransactionCode) {
        this.respAuthTransactionCode = respAuthTransactionCode;
    }

    public String getRespName() {
        return respName;
    }

    public void setRespName(String respName) {
        this.respName = respName;
    }

    public String getRespMandal() {
        return respMandal;
    }

    public void setRespMandal(String respMandal) {
        this.respMandal = respMandal;
    }

    public String getRespStatecode() {
        return respStatecode;
    }

    public void setRespStatecode(String respStatecode) {
        this.respStatecode = respStatecode;
    }

    public String getReqRdsVer() {
        return reqRdsVer;
    }

    public void setReqRdsVer(String reqRdsVer) {
        this.reqRdsVer = reqRdsVer;
    }

    public String getReqConsentMe() {
        return reqConsentMe;
    }

    public void setReqConsentMe(String reqConsentMe) {
        this.reqConsentMe = reqConsentMe;
    }

    public String getReqService() {
        return reqService;
    }

    public void setReqService(String reqService) {
        this.reqService = reqService;
    }

    public String getReqAttemptType() {
        return reqAttemptType;
    }

    public void setReqAttemptType(String reqAttemptType) {
        this.reqAttemptType = reqAttemptType;
    }

    public String getReqAuthType() {
        return reqAuthType;
    }

    public void setReqAuthType(String reqAuthType) {
        this.reqAuthType = reqAuthType;
    }

    public String getReqRdsId() {
        return reqRdsId;
    }

    public void setReqRdsId(String reqRdsId) {
        this.reqRdsId = reqRdsId;
    }

    public String getReqEndPointUrl() {
        return reqEndPointUrl;
    }

    public void setReqEndPointUrl(String reqEndPointUrl) {
        this.reqEndPointUrl = reqEndPointUrl;
    }

    public String getRespTime() {
        return respTime;
    }

    public void setRespTime(String respTime) {
        this.respTime = respTime;
    }

    public String getReqDpId() {
        return reqDpId;
    }

    public void setReqDpId(String reqDpId) {
        this.reqDpId = reqDpId;
    }

    public String getReqDc() {
        return reqDc;
    }

    public void setReqDc(String reqDc) {
        this.reqDc = reqDc;
    }

    public String getReqMi() {
        return reqMi;
    }

    public void setReqMi(String reqMi) {
        this.reqMi = reqMi;
    }

}
