package org.rta.core.utils;

import java.util.Objects;

/**
 * Class having basic utility methods for processing on strings.
 * 
 * @author rahul.sharma
 *
 */
public class StringsUtil {

    /**
     * Check whether two strings are equal. </br>
     * Strings equality check are case insensitive.
     * 
     * @param str1
     * @param str2
     * @return true if equal else false
     */
    public static boolean isEqualIgnoreCase(String str1, String str2) {
        if (ObjectsUtil.isNull(str1) || ObjectsUtil.isNull(str2)) {
            return false;
        }
        return str1.trim().equalsIgnoreCase(str2.trim());
    }
    
    public static StringBuilder appendIfNotNull(StringBuilder sb, String strToAppend) {
        if (sb == null) {
            return null;
        }
        if (strToAppend != null) {
            sb.append(", ").append(strToAppend);
        }
        return sb;
    }
    
    public static boolean isNullOrEmpty(String str) {
        return (str == null) || str.trim().equals("");
    }
    
    public static String getStartingChars(String str, int numberOfChars) {
        if (Objects.isNull(str)) {
            return null;
        }
        str = str.trim();
        if (str.length() < numberOfChars) {
            throw new IllegalArgumentException("string must be greater than number of chars");
        }
        
        return str.substring(0, numberOfChars);
    }
    
    public static String getLastChars(String str, int numberOfChars) {
        if (Objects.isNull(str)) {
            return null;
        }
        str = str.trim();
        if (str.length() < numberOfChars) {
            throw new IllegalArgumentException("string must be greater than number of chars");
        }
        
        return str.substring(str.length() - numberOfChars, str.length());
    }
}
