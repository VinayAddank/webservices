package org.rta.security.sbi;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.crypto.SecretKey;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Encrypt {
    private static Logger log = Logger.getLogger(Encrypt.class.getName());
    private byte[] fileData;
    private SecretKey secretkey = null;
    private String strError = "";
    private static String XFORMAT = "DES/ECB/PKCS5Padding";
    private String strLog4JPath = null;

    public String encryptFile(String paramString) {
        String str = null;
        try {
            this.fileData = paramString.getBytes();
            byte[] arrayOfByte = SBIKey.encrypt(this.fileData, this.secretkey, XFORMAT);
            str = SBIKey.hexStringFromBytes(arrayOfByte);
            System.out.println(str);
            if (str != null) {
                log.info("Encrypted Data Successfull");
                return str;
            }
            this.strError = "Error in encrypting";
            log.error("Error in encrypting");
            return str;
        } catch (Exception localException) {
            this.strError = "Error in encrypting";
            log.error("Error in encrypting");
        }
        return str;
    }

    public void setLog4Path(String paramString) {
        this.strLog4JPath = paramString;
        PropertyConfigurator.configure(paramString);
    }

    public String decryptFile(String paramString) {
        String str = null;
        try {
            byte[] arrayOfByte1 = SBIKey.bytesFromHexString(paramString);
            byte[] arrayOfByte2 = SBIKey.decrypt(arrayOfByte1, this.secretkey, XFORMAT);
            str = new String(arrayOfByte2);
            if (arrayOfByte2 != null) {
                log.info("Encrypted Data Successfull");
                return str;
            }
            this.strError = "Error in encrypting";
            log.error("Error in encrypting");
            return str;
        } catch (Exception localException) {
            this.strError = "Error in decrypting ";
            log.error("Error in decrypting");
        }
        return str;
    }

    public void setSecretkey(String paramString) {
        this.secretkey = SBIKey.readSecretKey(paramString);
    }

    public void setSecretkeyURL(URL paramURL) {
        this.secretkey = SBIKey.readSecretKey(paramURL);
    }

    public String getSecretkey() {
        return this.secretkey.toString();
    }

    public String getError() {
        return this.strError;
    }

    public static void main(String[] paramArrayOfString) {
        PropertyConfigurator.configure("/opt/sbi/datamatic/lib/datamatic_log4j.properties");

        URL localURL = null;
        String str1 = null;
        int i = 0;
        Encrypt localEncrypt = new Encrypt();

        localEncrypt.setLog4Path("/opt/sbi/datamatic/lib/datamatic_log4j.properties");
        localEncrypt.setSecretkey("//opt//bv1to1//CLASSES//IIT_BOMBAY.key");
        localEncrypt.fileDecrypt("//opt//sbi//script-root-prodn//sbi//home//IIT_BOMBAY12-DEC-2005.dat");
        if (paramArrayOfString.length == 0) {
            System.out.println("Usage : java merchant.encryption.EncryptFile <data to encrypt>");
            System.exit(0);
        } else if (paramArrayOfString.length > 1) {
            if (paramArrayOfString[1].equals("D")) {
                i = 1;
            }
        }
        System.out.println("sruuuuuuuuuu" + paramArrayOfString[0]);
        localURL = Encrypt.class.getResource("MBPT.key");
        String str2 = "";
        for (int j = 0; j < paramArrayOfString.length; j++) {
            str2 = str2 + paramArrayOfString[j] + " ";
        }
        System.out.println(str2);
        if (i == 0) {
            localEncrypt.fileData = str2.getBytes();
        } else {
            localEncrypt.fileData = str2.getBytes();
        }
        if (localURL != null) {
            localEncrypt.setSecretkeyURL(localURL);
            if (i != 0) {
                str1 = localEncrypt.decryptFile("priya");
            } else {
                str1 = localEncrypt.encryptFile("priya");
            }
            System.out.println("Encryption successful for data " + paramArrayOfString[0] + "..." + str1);
            if (str1.equalsIgnoreCase(null)) {
                System.out.println(localEncrypt.getError());
            }
        } else {
            System.out.println("Key file does not exist in //opt//bv1to1//CLASSES//MBPT.key");
        }
    }

    public void fileDecrypt(String paramString) {
        String str1 = paramString.substring(0, paramString.indexOf('.')) + ".txt";
        String str2 = new String();
        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
            PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(str1)));
            while ((str2 = localBufferedReader.readLine()) != null) {
                System.out.println("record= " + new String(str2));
                String str3 = decryptFile(str2);
                System.out.println("strdecrypt= " + str3);
                localPrintWriter.println(str3);
            }
            localBufferedReader.close();
            localPrintWriter.close();
        } catch (IOException localIOException) {
            log.error("Error in file operation ", localIOException);
        }
    }
}
