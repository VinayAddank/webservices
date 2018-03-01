package org.rta.security.sbi;


import java.io.File;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class GenerateSecretKey {
    private static Logger log = Logger.getLogger(GenerateSecretKey.class.getName());

    public static void main(String[] paramArrayOfString) throws Exception {
        System.out.println("inside");
        if (paramArrayOfString.length == 0) {
            System.out.println("Usage : java GenerateSecretKey <corporatecode (manadatory)> <path(optional)>");
            System.exit(0);
        }
        String str1 = null;
        if (paramArrayOfString.length > 0) {
            str1 = paramArrayOfString[0];
        }
        String str2 = str1;
        String str3 = "";
        if (paramArrayOfString.length > 1) {
            str3 = paramArrayOfString[1];
            str2 = str3 + File.separatorChar + str2;
            str2 = str2.replace('\\', '/');
        }
        String str4 = System.getProperty("user.dir", ".");
        String str5 = str4 + File.separatorChar + "properties" + File.separatorChar + "log4j.properties";

        str5 = str5.replace('\\', '/');
        PropertyConfigurator.configure(str5);
        if (log.isDebugEnabled()) {
            log.debug("Inside the main method of generating secret key");
        }
        KeyGenerator localKeyGenerator = KeyGenerator.getInstance("DES");
        localKeyGenerator.init(56);
        SecretKey localSecretKey = localKeyGenerator.generateKey();
        log.info("Generated Key: " + SBIKey.formatKey(localSecretKey));
        if (SBIKey.writeToFile(str2 + ".key", localSecretKey)) {
            log.info(str2 + ".key Successfully genrated");
        } else {
            log.error(str2 + ".key could not be genrated");
        }
        log.info("Generated DES Key done");
    }
}
