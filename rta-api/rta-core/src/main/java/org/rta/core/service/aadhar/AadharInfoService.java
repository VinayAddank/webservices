package org.rta.core.service.aadhar;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.rta.core.model.AadharModel;
import org.rta.core.model.BiometricresponseModel;

/**
 * AadharInfoService provides details for aadhar
 * 
 * @author Sriram
 *
 */
public interface AadharInfoService {

	public abstract AadharModel getAadhaarDemographicDataBySRDHSecuredeKYC(byte[] encPID, byte[] encSessionKey,
			byte[] encHmac, String uidNum, String agencyName, String agencyCode, String eKYCOption, String timeStamp,
			String udc, String deviceName) throws FileNotFoundException, UnsupportedEncodingException;

	public abstract BiometricresponseModel PIDBlockFingerPrintAuthService(String uid_num, String agency_name,
			String agency_code, String encryptedPid, String encSessionKey, String encHmac, String certificateIdentifier,
			String dataType);

	public abstract Long save(AadharModel aadharModel);

	public abstract void saveOrUpdate(AadharModel aadharModel, String token);

    public AadharModel setInsideApAndMandal(AadharModel am);
}
