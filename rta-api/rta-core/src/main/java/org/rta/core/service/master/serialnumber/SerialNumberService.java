package org.rta.core.service.master.serialnumber;

import org.rta.core.enums.SerialNumberType;

public interface SerialNumberService {

    public long getSerialNumber(String rtaOfficeCode, SerialNumberType type);

}
