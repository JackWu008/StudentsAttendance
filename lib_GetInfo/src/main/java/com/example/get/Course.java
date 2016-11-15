package com.example.get;

/**
 * Created by Administrator on 2016/10/9.
 */
public class Course {
    private String EVENTVALIDATION;
    private String VIEWSTATE;
    private String week = "-";
    private String info;
    private boolean haveInfo = true;
    private String[][] formatInfo = new String[7][6];
    private String[][] courseName = new String[7][6];
    private String[][] courseTime = new String[7][6];
    public static final String[][] FORMAT = new String[7][6];

    static {
        for (int j = 0; j < 7; j++)
            for (int i = 0; i < 6; i++) {
                FORMAT[j][i] = "Label_" + (j + 1) + "_" + (i + 1);
            }
    }

    public void setHaveCourse(boolean haveInfo) {
        this.haveInfo = haveInfo;
    }

    public boolean isHaveInfo() {
        return haveInfo;
    }


    public String getCourseName(int week, int aClass) {
        if (week < 1 || week > 7 || aClass < 1 || aClass > 6)
            return null;
        return courseName[week - 1][aClass - 1];

    }

    public void setCourseTime(int week, int aClass, String name) {
        if (week < 1 || week > 7 || aClass < 1 || aClass > 6)
            return;
        courseTime[week - 1][aClass - 1] = name;
    }

    public String getCourseTime(int week, int aClass) {
        if (week < 1 || week > 7 || aClass < 1 || aClass > 6)
            return null;
        return courseTime[week - 1][aClass - 1];

    }

    public String[][] getCourseName() {
        return courseName;
    }


    public String[][] getRawInfo() {
        return formatInfo;
    }

    public void setCourseName(int week, int aClass, String name) {
        if (week < 1 || week > 7 || aClass < 1 || aClass > 6)
            return;
        courseName[week - 1][aClass - 1] = name;
    }

    public void setRawCourse(int week, int aClass, String info) {
        if (week < 1 || week > 7 || aClass < 1 || aClass > 6)
            return;
        formatInfo[week - 1][aClass - 1] = info;
    }


    public void setVIEWSTATE(String VIEWSTATE) {
        this.VIEWSTATE = VIEWSTATE;
    }

    public void setEVENTVALIDATION(String EVENTVALIDATION) {
        this.EVENTVALIDATION = EVENTVALIDATION;
    }

    public String getVIEWSTATE() {
        return VIEWSTATE;
    }

    public String getEVENTVALIDATION() {
        return EVENTVALIDATION;
    }

    public Course() {

    }

    public String getRawCourse(int week, int aClass) {
        if (week < 1 || week > 7 || aClass < 1 || aClass > 6)
            return null;
        return formatInfo[week - 1][aClass - 1];
    }

    public String getWeek() {
        return week;
    }

    public String getInfo() {
        return info;
    }

    public String[][] getFormat() {
        return FORMAT;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
