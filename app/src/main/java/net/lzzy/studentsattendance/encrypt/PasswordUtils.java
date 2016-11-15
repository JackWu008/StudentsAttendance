package net.lzzy.studentsattendance.encrypt;

import android.util.Base64DataException;

import java.io.UnsupportedEncodingException;


public class PasswordUtils {
    public static String lock(String text) throws UnsupportedEncodingException, Base64DataException {
        return Base64Add.base64Lock(Base64Utils.encodeBase64(text));
    }

    public static String unlock(String text) throws UnsupportedEncodingException, Base64DataException {
        return Base64Utils.decodeBase64(Base64Add.base64Unlock(text));
    }

}
