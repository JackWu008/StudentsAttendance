package net.lzzy.studentsattendance.utils;

import com.example.get.CourseUtils;



public class LocalCourseUtils {


    public static String[][] getDisplayFormat(String[][] raw) {
        String[][] aWeekDisplayCourse = new String[7][6];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 6; j++)
                aWeekDisplayCourse[i][j] = CourseUtils.getCourseNod(raw[i][j]) + "\n" + CourseUtils.getCourseTime(raw[i][j]) + "\n" + CourseUtils.getCourseName(raw[i][j]);
        return aWeekDisplayCourse;

    }


    public static String[][] getAWeekRaw(String raw) {
        String[] week;
        String[][] data = new String[7][6];

        week = raw.split("@@");
        for (int i = 0; i < 7; i++) {
            String[] aClass = week[i].split("##");
            for (int j = 0; j < 6; j++)
                data[i][j] = aClass[j];
        }
        return data;
    }


}
