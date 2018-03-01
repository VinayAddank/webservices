package org.rta.security.sbi;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;

public final class SBIKey {
    private static Logger log = Logger.getLogger(SBIKey.class.getName());

    public static SecretKey readSecretKey(URL paramURL) {
        if (log.isDebugEnabled()) {
            log.debug("Reading Secret Key from " + paramURL);
        }
        SecretKey localSecretKey = null;
        try {
            byte[] arrayOfByte = getFileBytes(paramURL);
            if (arrayOfByte != null) {
                DESKeySpec localDESKeySpec = new DESKeySpec(arrayOfByte);
                SecretKeyFactory localSecretKeyFactory = SecretKeyFactory.getInstance("DES");
                localSecretKey = localSecretKeyFactory.generateSecret(localDESKeySpec);
                if (log.isDebugEnabled()) {
                    log.debug("Format of the key   " + localSecretKey.getFormat());
                }
                if (log.isDebugEnabled()) {
                    log.debug("Algorithm  of the key   " + localSecretKey.getAlgorithm());
                }
                if (log.isDebugEnabled()) {
                    log.debug("Reading Secret Key from " + paramURL + " done");
                }
            }
        } catch (InvalidKeySpecException localInvalidKeySpecException) {
            log.error("Error while reading the secret key ", localInvalidKeySpecException);
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            log.error("Error while reading the secret key ", localNoSuchAlgorithmException);
        } catch (InvalidKeyException localInvalidKeyException) {
            log.error("Error while reading the secret key ", localInvalidKeyException);
        } catch (Exception localException) {
            log.error("Error while reading the secret key ", localException);
        }
        return localSecretKey;
    }

    public static SecretKey readSecretKey(String paramString) {
        if (log.isDebugEnabled()) {
            log.debug("Reading Secret Key from " + paramString);
        }
        SecretKey localSecretKey = null;
        try {
            byte[] arrayOfByte = getFileBytes(paramString);
            if (arrayOfByte != null) {
                DESKeySpec localDESKeySpec = new DESKeySpec(arrayOfByte);
                SecretKeyFactory localSecretKeyFactory = SecretKeyFactory.getInstance("DES");
                localSecretKey = localSecretKeyFactory.generateSecret(localDESKeySpec);
                if (log.isDebugEnabled()) {
                    log.debug("Format of the key   " + localSecretKey.getFormat());
                }
                if (log.isDebugEnabled()) {
                    log.debug("Algorithm  of the key   " + localSecretKey.getAlgorithm());
                }
                if (log.isDebugEnabled()) {
                    log.debug("Reading Secret Key from " + paramString + " done");
                }
            }
        } catch (InvalidKeySpecException localInvalidKeySpecException) {
            log.error("Error while reading the secret key ", localInvalidKeySpecException);
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            log.error("Error while reading the secret key ", localNoSuchAlgorithmException);
        } catch (InvalidKeyException localInvalidKeyException) {
            log.error("Error while reading the secret key ", localInvalidKeyException);
        } catch (Exception localException) {
            log.error("Error while reading the secret key ", localException);
        }
        return localSecretKey;
    }

    private static byte[] getFileBytes(URL paramURL) {
        try {
            BufferedInputStream localBufferedInputStream = new BufferedInputStream(paramURL.openStream());

            byte[] arrayOfByte = new byte[localBufferedInputStream.available()];
            localBufferedInputStream.read(arrayOfByte);
            localBufferedInputStream.close();
            return arrayOfByte;
        } catch (IOException localIOException) {
            log.error("Error while reading the bytes from file ", localIOException);
        }
        return null;
    }

    private static byte[] getFileBytes(String paramString) {
        try {
            FileInputStream localFileInputStream = new FileInputStream(paramString);

            byte[] arrayOfByte = new byte[localFileInputStream.available()];
            localFileInputStream.read(arrayOfByte);
            localFileInputStream.close();
            return arrayOfByte;
        } catch (IOException localIOException) {
            log.error("Error while reading the bytes from file ", localIOException);
        }
        return null;
    }

    public static byte[] encrypt(byte[] paramArrayOfByte, Key paramKey, String paramString) {
        try {
            Cipher localCipher = Cipher.getInstance(paramString);
            System.out.println(localCipher);
            localCipher.init(1, paramKey);
            return localCipher.doFinal(paramArrayOfByte);
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            log.error("Error while encrypting ", localNoSuchAlgorithmException);
        } catch (NoSuchPaddingException localNoSuchPaddingException) {
            log.error("Error while encrypting ", localNoSuchPaddingException);
        } catch (InvalidKeyException localInvalidKeyException) {
            log.error("Error while encrypting ", localInvalidKeyException);
        } catch (IllegalStateException localIllegalStateException) {
            log.error("Error while encryting ", localIllegalStateException);
        } catch (IllegalBlockSizeException localIllegalBlockSizeException) {
            log.error("Error while encrypting ", localIllegalBlockSizeException);
        } catch (BadPaddingException localBadPaddingException) {
            log.error("Error while encrypting ", localBadPaddingException);
        }
        return null;
    }

    public static byte[] decrypt(byte[] paramArrayOfByte, Key paramKey, String paramString) {
        try {
            Cipher localCipher = Cipher.getInstance(paramString);
            localCipher.init(2, paramKey);
            return localCipher.doFinal(paramArrayOfByte);
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            log.error("Error while decrypting ", localNoSuchAlgorithmException);
        } catch (NoSuchPaddingException localNoSuchPaddingException) {
            log.error("Error while decrypting ", localNoSuchPaddingException);
        } catch (InvalidKeyException localInvalidKeyException) {
            log.error("Error while decrypting ", localInvalidKeyException);
        } catch (IllegalStateException localIllegalStateException) {
            log.error("Error while decrypting ", localIllegalStateException);
        } catch (IllegalBlockSizeException localIllegalBlockSizeException) {
            log.error("Error while decrypting ", localIllegalBlockSizeException);
        } catch (BadPaddingException localBadPaddingException) {
            log.error("Error while decrypting ", localBadPaddingException);
        }
        return null;
    }

    public static String formatKey(Key paramKey) {
        StringBuffer localStringBuffer = new StringBuffer();
        String str1 = paramKey.getAlgorithm();
        String str2 = paramKey.getFormat();
        byte[] arrayOfByte = paramKey.getEncoded();
        localStringBuffer
                .append("Key[algorithm=" + str1 + ", format=" + str2 + ", bytes=" + arrayOfByte.length + "]\n");
        return localStringBuffer.toString();
    }

    public static boolean writeToFile(String paramString, Key paramKey) {
        try {
            byte[] arrayOfByte = paramKey.getEncoded();

            FileOutputStream localFileOutputStream = new FileOutputStream(paramString);

            localFileOutputStream.write(arrayOfByte);
            localFileOutputStream.close();
            log.info("Writing key data to the file is successful " + paramString);
            return true;
        } catch (IOException localIOException) {
            log.error("Error while writing into file", localIOException);
        } catch (Exception localException) {
            log.error("Error while writing into file", localException);
        }
        return false;
    }

    public static byte[] bytesFromHexString(String paramString) {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        int i = 1;
        int j = 0;
        for (int k = 0; k < paramString.length(); k++) {
            int m = paramString.charAt(k);
            int n;
            switch (m) {
                case 48:
                    n = 0;
                    break;
                case 49:
                    n = 1;
                    break;
                case 50:
                    n = 2;
                    break;
                case 51:
                    n = 3;
                    break;
                case 52:
                    n = 4;
                    break;
                case 53:
                    n = 5;
                    break;
                case 54:
                    n = 6;
                    break;
                case 55:
                    n = 7;
                    break;
                case 56:
                    n = 8;
                    break;
                case 57:
                    n = 9;
                    break;
                case 97:
                    n = 10;
                    break;
                case 98:
                    n = 11;
                    break;
                case 99:
                    n = 12;
                    break;
                case 100:
                    n = 13;
                    break;
                case 101:
                    n = 14;
                    break;
                case 102:
                    n = 15;
                    break;
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                case 69:
                case 70:
                case 71:
                case 72:
                case 73:
                case 74:
                case 75:
                case 76:
                case 77:
                case 78:
                case 79:
                case 80:
                case 81:
                case 82:
                case 83:
                case 84:
                case 85:
                case 86:
                case 87:
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                default:
                    n = 16;
            }
            if (n != 16) {
                if (i != 0) {
                    j = n << 4;
                    i = 0;
                } else {
                    j |= n;
                    i = 1;
                    localByteArrayOutputStream.write(j);
                }
            }
        }
        return localByteArrayOutputStream.toByteArray();
    }

    private static char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String hexStringFromBytes(byte[] paramArrayOfByte) {
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i = 0; i < paramArrayOfByte.length; i++) {
            if ((i > 0) && ((i & 0x1F) == 0)) {
                localStringBuffer.append("\n");
            } else if ((i > 0) && ((i & 0x3) == 0)) {
                localStringBuffer.append("");
            }
            localStringBuffer.append(hexChars[((0xF0 & paramArrayOfByte[i]) >> 4)]);
            localStringBuffer.append(hexChars[(0xF & paramArrayOfByte[i])]);
        }
        return localStringBuffer.toString();
    }
}
