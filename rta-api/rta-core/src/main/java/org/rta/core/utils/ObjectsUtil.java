package org.rta.core.utils;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Class having basic utility methods for processing on objects.
 * 
 * @author rahul.sharma
 *
 */
public final class ObjectsUtil {

    public static <T> boolean isNull(T t) {
        return Objects.isNull(t);
    }
    
    public static <T> boolean isNotNull(T t) {
        return !isNull(t);
    }
    
    public static <T> boolean isNullOrEmpty(Collection<T> t) {
        return isNull(t) || t.isEmpty();
    }
    
    public static <K,V> boolean isNullOrEmpty(Map<K, V> t) {
        return isNull(t) || t.isEmpty();
    }
    
    
    /**
	 *  Method used for bypassing SSL verification
	 */
	public static void disableSSLVerification() {

		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}

		} };

		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		HostnameVerifier allHostsValid = new HostnameVerifier() {

			public boolean verify(String hostname, SSLSession session) {
				return true;
			}

		};
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}
}
