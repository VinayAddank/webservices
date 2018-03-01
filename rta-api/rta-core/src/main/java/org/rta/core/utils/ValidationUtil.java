package org.rta.core.utils;

import org.apache.commons.validator.routines.InetAddressValidator;

public final class ValidationUtil {

    public static boolean validateIPAddress(String ip) {
        if (ip == null) {
            return false;
        }
        return InetAddressValidator.getInstance().isValid(ip);
    }
    
}
