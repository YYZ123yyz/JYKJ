package www.jykj.com.jykj_zxyl.util;

import android.os.Bundle;

import java.security.MessageDigest;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-07-16 10:49
 */
public class StringUtils {

    public StringUtils() {
    }

    public static boolean isNotEmpty(String s) {
        return null != s && s.length() > 0 && !"".equals(s) && !"null".equals(s);
    }

    public static String getBindleString(Bundle bundle, String key) {
        String result = "";
        if (bundle != null && bundle.getString(key) != null) {
            result = bundle.getString(key);
        }

        return result;
    }

    public static String md5Encrypt(String string) {
        String result = null;

        try {
            char[] charArray = string.toCharArray();
            byte[] byteArray = new byte[charArray.length];

            for(int i = 0; i < charArray.length; ++i) {
                byteArray[i] = (byte)charArray[i];
            }

            StringBuilder hexValue = new StringBuilder();
            byte[] md5Bytes = MessageDigest.getInstance("MD5").digest(byteArray);
            byte[] var6 = md5Bytes;
            int var7 = md5Bytes.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                byte md5Byte = var6[var8];
                int val = md5Byte & 255;
                if (val < 16) {
                    hexValue.append("0");
                }

                hexValue.append(Integer.toHexString(val));
            }

            result = hexValue.toString();
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return result;
    }

    public static String removeBlank(String str) {
        StringBuilder sb = new StringBuilder();
        char c = 32;

        for(int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (ch != c) {
                sb.append(ch);
            }
        }

        return sb.toString();
    }
}
