package net.lzzy.studentsattendance.encrypt;

import android.util.Base64;
import android.util.Base64DataException;

import java.io.UnsupportedEncodingException;


public class Base64Utils {

    public static String encodeBase64(String str) throws UnsupportedEncodingException, Base64DataException {
        return Base64.encodeToString(str.getBytes("UTF-8"), Base64.DEFAULT).replace("\n", "");
    }

    public static String decodeBase64(String str) throws UnsupportedEncodingException, Base64DataException {
        return new String(Base64.decode(str, Base64.DEFAULT), "UTF-8");

    }


}
