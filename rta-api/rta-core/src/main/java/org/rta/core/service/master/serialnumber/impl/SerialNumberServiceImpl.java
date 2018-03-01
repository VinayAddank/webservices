package org.rta.core.service.master.serialnumber.impl;

import org.rta.core.dao.office.RtaOfficeSerialNumberDAO;
import org.rta.core.entity.office.RTAOfficeSerialNumberEntity;
import org.rta.core.enums.SerialNumberType;
import org.rta.core.service.master.serialnumber.SerialNumberService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SerialNumberServiceImpl implements SerialNumberService {

    @Autowired
    private RtaOfficeSerialNumberDAO rtaOfficeSerialNumberDAO;
    
    @Override
    @Transactional
    public long getSerialNumber(String rtaOfficeCode, SerialNumberType type) {
        Long currentTime = DateUtil.toCurrentUTCTimeStamp();
        Integer year = Integer.parseInt(DateUtil.getYear(currentTime));
        RTAOfficeSerialNumberEntity serialNumberEntity = rtaOfficeSerialNumberDAO.getSerialNumber(rtaOfficeCode, year, type);
        long serialNumber;
        if (ObjectsUtil.isNull(serialNumberEntity)) {
            serialNumber = Long.valueOf(1);
            serialNumberEntity = new RTAOfficeSerialNumberEntity();
            serialNumberEntity.setYear(year);
            serialNumberEntity.setRtaOfficeCode(rtaOfficeCode);
            serialNumberEntity.setCreatedBy("admin");
            serialNumberEntity.setCreatedOn(currentTime);
            serialNumberEntity.setModifiedBy("admin");
            serialNumberEntity.setModifiedOn(currentTime);
            serialNumberEntity.setSerialNumber(serialNumber);
            serialNumberEntity.setSerialNumberType(type);
        } else {
            serialNumberEntity.setModifiedBy("admin");
            serialNumberEntity.setModifiedOn(currentTime);
            serialNumber = serialNumberEntity.getSerialNumber() + 1;
            serialNumberEntity.setSerialNumber(serialNumber);
        }
        rtaOfficeSerialNumberDAO.saveOrUpdate(serialNumberEntity);
        return serialNumber;
    }
    
}
