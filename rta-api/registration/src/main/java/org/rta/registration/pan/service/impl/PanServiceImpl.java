package org.rta.registration.pan.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.apache.log4j.Logger;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.util.encoders.Base64;
import org.rta.registration.pan.model.PanDetailsModel;
import org.rta.registration.pan.service.APIBased.DummyHostnameVerifier;
import org.rta.registration.pan.service.APIBased.DummyTrustManager;
import org.rta.registration.pan.service.PanService;
import org.springframework.stereotype.Service;

@SuppressWarnings("restriction")
@Service
public class PanServiceImpl implements PanService {

	private String userName = "V0144001^";

	private String Password = "APRTA@123";

	@SuppressWarnings("restriction")
	@Override
	public PanDetailsModel getPanDetails(String PanNumber) throws KeyStoreException, FileNotFoundException, Exception {

		final Logger log = Logger.getLogger(PanServiceImpl.class);

		PanDetailsModel pdModel = new PanDetailsModel();
		KeyStore keystore = KeyStore.getInstance("jks");
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream input = classloader.getResourceAsStream("oupt.jks");
		try {
			char[] password = Password.toCharArray();
			keystore.load(input, password);
		} catch (IOException e) {
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (CertificateException e1) {
			e1.printStackTrace();
		}
		Enumeration e = keystore.aliases();
		String alias = "";
		if (e != null) {
			while (e.hasMoreElements()) {
				String n = (String) e.nextElement();
				if (keystore.isKeyEntry(n)) {
					alias = n;
				}
			}
		}
		PrivateKey privateKey = null;
		try {
			privateKey = (PrivateKey) keystore.getKey(alias, Password.toCharArray());
		} catch (UnrecoverableKeyException | NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		String userId = userName;
		String urlOfNsdl = "https://59.163.46.2/TIN/PanInquiryBackEnd";
		String data = userId.concat(PanNumber);
		X509Certificate myPubCert = (X509Certificate) keystore.getCertificate(alias);
		byte[] dataToSign = data.getBytes();
		CMSSignedDataGenerator sgen = new CMSSignedDataGenerator();
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		sgen.addSigner(privateKey, myPubCert, CMSSignedDataGenerator.DIGEST_SHA1);
		Certificate[] certChain = keystore.getCertificateChain(alias);
		ArrayList certList = new ArrayList();
		CertStore certs = null;
		for (int i = 0; i < certChain.length; i++)
			certList.add(certChain[i]);
		try {
			sgen.addCertificatesAndCRLs(
					CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC"));
		} catch (CertStoreException | InvalidAlgorithmParameterException | NoSuchAlgorithmException
				| NoSuchProviderException | CMSException e1) {
			e1.printStackTrace();
		}
		CMSSignedData csd = null;
		try {
			csd = sgen.generate(new CMSProcessableByteArray(dataToSign), true, "BC");
		} catch (NoSuchAlgorithmException | NoSuchProviderException | CMSException e1) {
			e1.printStackTrace();
		}
		byte[] signedData = null;
		try {
			signedData = csd.getEncoded();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] signedData64 = Base64.encode(signedData);
		String signature1 = new String(signedData64);
		String signature = signature1;
		Date startTime = null;
		Calendar c1 = Calendar.getInstance();
		startTime = c1.getTime();
		Date connectionStartTime = null;
		String logMsg = "\n-";
		BufferedWriter out = null;
		FileWriter fstream = null;
		Calendar c = Calendar.getInstance();
		long nonce = c.getTimeInMillis();
		try {
			fstream = new FileWriter("API_PAN_verification.logs", true);
			out = new BufferedWriter(fstream);
		} catch (Exception e1) {
			logMsg += "::Exception: " + e1.getMessage() + " ::Program Start Time:" + startTime + "::nonce= " + nonce;
			out.write(logMsg);
			out.close();
		}
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContext.getInstance("SSL");
			sslcontext.init(new KeyManager[0], new TrustManager[] { new DummyTrustManager() }, new SecureRandom());
		} catch (NoSuchAlgorithmException e2) {
			logMsg += "::Exception: " + e2.getMessage() + " ::Program Start Time:" + startTime + "::nonce= " + nonce;
			e2.printStackTrace(System.err);
			out.write(logMsg);
			out.close();
		} catch (KeyManagementException e3) {
			logMsg += "::Exception: " + e3.getMessage() + " ::Program Start Time:" + startTime + "::nonce= " + nonce;
			e3.printStackTrace(System.err);
			out.write(logMsg);
			out.close();
		}
		SSLSocketFactory factory = sslcontext.getSocketFactory();
		String urlParameters = "data=";
		try {
			urlParameters = urlParameters + URLEncoder.encode(data, "UTF-8") + "&signature="
					+ URLEncoder.encode(signature, "UTF-8");
		} catch (Exception e1) {
			logMsg += "::Exception: " + e1.getMessage() + " ::Program Start Time:" + startTime + "::nonce= " + nonce;
			e1.printStackTrace();
			out.write(logMsg);
			out.close();
		}
		try {
			URL url;
			HttpsURLConnection connection;
			InputStream is = null;
			String ip = urlOfNsdl;
			url = new URL(ip);
			log.info("Pan url is:" + ip);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setSSLSocketFactory(factory);
			connection.setHostnameVerifier(new DummyHostnameVerifier());
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			osw.write(urlParameters);
			osw.flush();
			connectionStartTime = new Date();
			logMsg += "::Request Sent At: " + connectionStartTime;
			logMsg += "::Request Data: " + data;
			osw.close();
			is = connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String line = null;
			line = in.readLine();
			String[] strs = line.split("[\\^]");
			if (strs.length > 1) {
				pdModel.setStatus(Integer.parseInt(strs[0]));
				pdModel.setPanNumber(strs[1]);
				pdModel.setIsValid(strs[2]);
				if (strs.length > 3) {
					pdModel.setLastName(strs[3]);
					pdModel.setFirstName(strs[4]);
					pdModel.setMiddleName(strs[5]);
					pdModel.setSalutation(strs[6]);
					pdModel.setPanUpdatedDateAtITDEnd(strs[7]);
				}
			}
			is.close();
			in.close();
		} catch (ConnectException e4) {
			logMsg += "::Exception: " + e4.getMessage() + "::Program Start Time:" + startTime + "::nonce= " + nonce;
			out.write(logMsg);
			out.close();
		} catch (Exception e1) {
			logMsg += "::Exception: " + e1.getMessage() + "::Program Start Time:" + startTime + "::nonce= " + nonce;
			out.write(logMsg);
			out.close();
			e1.printStackTrace();
			return pdModel;
		}
		out.write(logMsg);
		out.close();
		log.info(":::::::::::::::Pan Details are End:::::::::::::::::");
		return pdModel;
	}

}
