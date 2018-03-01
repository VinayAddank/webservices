package org.rta.core.service.aadhar;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.RemoteException;

import org.hibernate.service.spi.ServiceException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.model.AadharModel;

/**
 * AadharInfoService provides details for aadhar
 * 
 * @author Sriram
 *
 */
public interface AadharTCSInfoService {

    public AadharModel AADHAAR_EKYC_FINGER_AUTHENTICATION(URL endpoint, String uid, String tid, String udc, String rdsId,
            String ip, String srt, String crt, String skey, String pid, String hmac, String ci, String bt,
            String pincode, String version, String scheme, String department, String service, String dpId,
            String rdsVer, String consentme, String userName, String deviceName, String attemptType, String dc,
            String mi, String mc) throws RemoteException, FileNotFoundException, UnsupportedEncodingException,
            ServiceException, javax.xml.rpc.ServiceException;

    public AadharModel getAadharDetails(Long aadharNumber) throws NotFoundException;
}
