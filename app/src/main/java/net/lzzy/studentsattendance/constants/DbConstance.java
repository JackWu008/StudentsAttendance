package net.lzzy.studentsattendance.constants;

import java.util.ArrayList;
import java.util.List;


public class DbConstance {
    public static final String DB_NAME = "course.db";


    public static final String TABLE_COURSE = "course";
    public static final String COL_COURSE_RAW = "raw";
    public static final String COL_COURSE_WEEK = "week";
    public static final List<String> sqls = new ArrayList<>();

    static {
        String buffer = "create table " + TABLE_COURSE + "(" +
                COL_COURSE_WEEK + " INT," +
                COL_COURSE_RAW + " TEXT)";
        sqls.add(buffer);

    }


}
