package com.example.get;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */

public class CourseUtils {
    public static List<String> getNodeNum(String raw) {
        List<String> list = new ArrayList<>();
        String aTime = raw;
        while (true) {
            if (aTime == null || !aTime.contains("时间"))
                break;
            list.add(aTime.substring(raw.indexOf("时间"), aTime.indexOf("人)") + 2));
            try {
                aTime = aTime.substring(aTime.indexOf("人)" + 2), aTime.length());
            } catch (StringIndexOutOfBoundsException e) {
                return list;
            }
        }

        return list;
    }

    public static String getCourseName(String raw) {
        if (!raw.contains("课程名称"))
            return "";
        int startIndex = raw.indexOf("课程名称") + 5;
        int endIndex = raw.indexOf(" ", raw.indexOf("课程名称"));
        return raw.substring(startIndex, endIndex);
    }

    public static String getCourseTime(String raw) {
        if (!raw.contains("时间"))
            return "";
        int startIndex = raw.indexOf("时间") + 3;
        int endIndex = raw.indexOf(",", raw.indexOf("时间"));
        return raw.substring(startIndex, endIndex);
    }


    public static String getCourseTeacher(String raw) {
        if (!raw.contains("任课老师："))
            return "";
        int startIndex = raw.indexOf("任课老师：") + 5;
        int endIndex = startIndex + raw.substring(startIndex).indexOf("老师");
        return raw.substring(startIndex, endIndex).trim();
    }

    public static String getCourseClassroom(String raw) {
        if (!raw.contains("课室："))
            return "";
        int startIndex = raw.indexOf("课室：") + 3;
        return raw.substring(startIndex).trim();
    }

    public static String getCourseNod(String raw) {
        int start = raw.indexOf('第');
        if (start == -1)
            return "";
        int end = raw.indexOf('节') + 1;
        return raw.substring(start, end);
    }



}
