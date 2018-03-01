package org.rta.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingUtil {

	public static final String SHA512 = "SHA-512";

	public static String sha512HashCal(String str) {
		return hashCal(SHA512, str);
	}

	public static String hashCal(String type, String str) {
		byte[] hashseq = str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest algorithm = MessageDigest.getInstance(type);
			algorithm.reset();
			algorithm.update(hashseq);
			byte messageDigest[] = algorithm.digest();
			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1) {
					hexString.append("0");
				}
				hexString.append(hex);
			}

		} catch (NoSuchAlgorithmException nsae) {
		}
		return hexString.toString();
	}
}
