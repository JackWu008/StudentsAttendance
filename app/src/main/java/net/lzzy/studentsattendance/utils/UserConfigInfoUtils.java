package net.lzzy.studentsattendance.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class UserConfigInfoUtils {
    private static final String USER_CONFIG = "user_config";
    private static final String KEY_IS_SAVE_PSD = "isSavePsd";
    private static final String KEY_IS_AUTO_LOGIN = "isLogin";
    private static final String KEY_ID = "id";
    private static final String KEY_COURSE_MODE = "course_mode";
    private static final String KEY_PASSWORD = "psd";
    public static boolean MODE_IS_CHANGE = false;

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharePreference(context).edit();

    }

    public static void setCourseTableMode(Context context, boolean state) {
        getEditor(context).putBoolean(KEY_COURSE_MODE, state).apply();
    }

    public static boolean getCourseTableMode(Context context) {
        return getSharePreference(context).getBoolean(KEY_COURSE_MODE, false);

    }

    private static SharedPreferences getSharePreference(Context context) {
        return context.getSharedPreferences(USER_CONFIG, Context.MODE_APPEND);

    }

    public static void setID(Context context, String id) {
        getEditor(context).putString(KEY_ID, id).apply();
    }

    public static String getID(Context context) {
        return getSharePreference(context).getString(KEY_ID, "");
    }

    public static void setPassword(Context context, String psd) {
        getEditor(context).putString(KEY_PASSWORD, psd).apply();
    }

    public static String getPassword(Context context) {
        return getSharePreference(context).getString(KEY_PASSWORD, "");
    }


    public static boolean isAutoLogin(Context context) {
        return getSharePreference(context).getBoolean(KEY_IS_AUTO_LOGIN, false);

    }

    public static boolean isSavePsd(Context context) {
        return getSharePreference(context).getBoolean(KEY_IS_SAVE_PSD, false);
    }

    public static void setSaveAutoLoginState(Context context, boolean state) {
        getEditor(context).putBoolean(KEY_IS_AUTO_LOGIN, state).apply();

    }

    public static void setSavePsdState(Context context, boolean state) {

        getEditor(context).putBoolean(KEY_IS_SAVE_PSD, state).apply();
    }

    public static void clearLoginInfo(Context context) {
        getEditor(context).clear().apply();

    }


}
