package org.rta.security.sbi;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ChecksumMD5 {
    private Logger logger = Logger.getLogger(getClass());

    public void setLog4Path(String s) {
        PropertyConfigurator.configure(s);
    }

    public String getValue(String s) throws Exception {
        String result = "";
        byte[] b = (byte[]) null;
        InputStream fis = new ByteArrayInputStream(s.getBytes());
        byte[] buffer = new byte['?'];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        b = complete.digest();
        for (int i = 0; i < b.length; i++) {
            result = result + Integer.toString((b[i] & 0xFF) + 256, 16).substring(1);
        }
        this.logger.info("Checksum value :" + result);
        return result;
    }
}
