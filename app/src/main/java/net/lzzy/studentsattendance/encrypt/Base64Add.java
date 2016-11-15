package net.lzzy.studentsattendance.encrypt;

import java.util.HashMap;
import java.util.Map;


public class Base64Add {
    public static char[] org = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/', '='};
    public static char[] convert = new char[]{'H', 'Y', ')', '!', 'd', '3', 'A', 'J', 'G', 'I', '(', '4', '?', 'c', '9', 'q', '/', '0', 'k', 'p', 'X', 'C', 'V', 'K', 'B', 'M', 'Z', 'l', 'N', 'w', 't', 'b', 'm', 'W', '1', 'L', 's', 'f', 'g', 'v', '5', 'T', 'P', 'D', 'U', 'e', '2', 'n', 'E', '8', '7', '6', 'R', 'i', ',', 'y', 'j', '@', 'z', 'F', 'x', 'O', '%', '-', ':'};
    private static Map<Character, Character> map_encode;
    private static Map<Character, Character> map_decode;

    static {
        map_encode = new HashMap<>();
        for (int i = 0; i < org.length; i++) {
            map_encode.put(org[i], convert[i]);
        }
        map_decode = new HashMap<>();
        for (int i = 0; i < org.length; i++) {
            map_decode.put(convert[i], org[i]);
        }

    }

    public static String base64Lock(String base64){
        StringBuffer lockStrBuf = new StringBuffer();
        for (int i = 0; i < base64.length(); i++) {
            lockStrBuf.append(map_encode.get(base64.charAt(i)));
        }

        String string = lockStrBuf.toString();
        return string;


    }

    public static String base64Unlock(String base64) {
        StringBuffer lockStrBuf = new StringBuffer();
        for (int i = 0; i < base64.length(); i++) {
            lockStrBuf.append(map_decode.get(base64.charAt(i)));
        }
        String string = lockStrBuf.toString();
        return string;

    }


}
