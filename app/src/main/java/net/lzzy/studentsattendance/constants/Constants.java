package net.lzzy.studentsattendance.constants;

import java.util.HashMap;

public class Constants {
    public static HashMap<Integer, String> weekMap;

    static {
        weekMap = new HashMap<>();
        weekMap.put(0, "一");
        weekMap.put(1, "二");
        weekMap.put(2, "三");
        weekMap.put(3, "四");
        weekMap.put(4, "五");
        weekMap.put(5, "六");
        weekMap.put(6, "天");
    }

    public static boolean hasInfo = false;
    public static boolean isLogined = false;

}
