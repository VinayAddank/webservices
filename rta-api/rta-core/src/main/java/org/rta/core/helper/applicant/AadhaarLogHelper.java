package org.rta.core.helper.applicant;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.applicant.AadhaarLogEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.AadhaarLogModel;

public class AadhaarLogHelper implements BaseConverter<AadhaarLogEntity, AadhaarLogModel> {

    @Override
    public AadhaarLogEntity convertToEntity(AadhaarLogModel source) {
        AadhaarLogEntity entity = new AadhaarLogEntity();

        entity.setReqUid(source.getReqUid());
        entity.setReqEndPointUrl(source.getReqEndPointUrl());
        entity.setReqTid(source.getReqTid());
        entity.setReqProvider(source.getReqProvider());
        entity.setReqAgencyCode(source.getReqAgencyCode());
        entity.setReqAgencyName(source.getReqAgencyName());
        entity.setReqDeviceCode(source.getReqDeviceCode());
        entity.setReqDeviceName(source.getReqDeviceName());
        entity.setReqTime(source.getReqTime());
        entity.setReqServerDateTime(source.getReqServerDateTime());
        entity.setReqClientDateTime(source.getReqClientDateTime());
        entity.setReqClientDateTime(source.getReqClientDateTime());
        entity.setReqRdsVer(source.getReqRdsVer());
        entity.setReqConsentMe(source.getReqConsentMe());
        entity.setReqService(source.getReqService());
        entity.setReqAttemptType(source.getReqAttemptType());
        entity.setReqAuthType(source.getReqAuthType());
        entity.setReqRdsId(source.getReqRdsId());
        entity.setCreatedBy(source.getCreatedBy());
        entity.setCreatedOn(source.getCreatedOn());
        entity.setReqDpId(source.getReqDpId());
        entity.setReqDc(source.getReqDc());
        entity.setReqMi(source.getReqMi());

        entity.setRespName(source.getRespName());
        entity.setRespTime(source.getRespTime());
        entity.setRespStatecode(source.getRespStatecode());
        entity.setRespMandal(source.getRespMandal());
        entity.setRespKsaKuaTxn(source.getRespKsaKuaTxn());
        entity.setRespAuthErrorCode(source.getRespAuthErrorCode());
        entity.setRespAuthStatus(source.getRespAuthStatus());
        entity.setRespAuthDate(source.getRespAuthDate());
        entity.setRespAuthTransactionCode(source.getRespAuthTransactionCode());

        return entity;
    }

    @Override
    public Collection<AadhaarLogEntity> convertToEntityList(Collection<AadhaarLogModel> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
    }

    @Override
    public AadhaarLogModel convertToModel(AadhaarLogEntity source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<AadhaarLogModel> convertToModelList(Collection<AadhaarLogEntity> source) {
        // TODO Auto-generated method stub
        return null;
    }
}
