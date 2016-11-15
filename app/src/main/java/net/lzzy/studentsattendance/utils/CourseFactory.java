package net.lzzy.studentsattendance.utils;

import android.content.Context;

import net.lzzy.sql.Repository;
import net.lzzy.studentsattendance.constants.DbConstance;
import net.lzzy.studentsattendance.fragments.TableFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class CourseFactory {
    private Repository<TableFragment.LocalCourse> repository;
    private List<TableFragment.LocalCourse> localCourses;
    private static CourseFactory constance;

    public static CourseFactory getInstance(Context context) {
        if (constance == null)
            constance = new CourseFactory(context);
        return constance;

    }

    public TableFragment.LocalCourse getCourse(String week) {
        try {
            List<TableFragment.LocalCourse> courses = repository.getByKeyWord(week, new String[]{DbConstance.COL_COURSE_WEEK}, null, null, true);
            if (courses.size() > 0)
                return courses.get(0);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CourseFactory(Context context) {
        try {
            localCourses = new ArrayList<>();
            repository = new Repository<>(context, DbConstance.sqls, DbConstance.DB_NAME, 1, TableFragment.LocalCourse.class);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

    }


    public CourseFactory getAllCourses() {

        try {
            localCourses.clear();
            localCourses.addAll(repository.getByKeyWord(null, null, null, null, true));

        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return this;

    }

    public boolean isRepeat(TableFragment.LocalCourse localCourse) {
        if (repository.getCount(new String[]{DbConstance.COL_COURSE_WEEK}, new String[]{localCourse.getWeek()}, true) > 0)
            return true;
        return false;
    }

    private int getCourseWeek(Date date) {
        String time = new SimpleDateFormat("yyyy-MM-dd").format(date);
        List<TableFragment.LocalCourse> list;
        try {
            list = repository.getByKeyWord(time, new String[]{DbConstance.COL_COURSE_RAW}, null, null, false);
            if (list.size() > 0)
                return Integer.valueOf(list.get(0).getWeek());
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public int getWeek(Date date, int time) {
        int week = getCourseWeek(date);

        if (time < 0)
            return -1;
        if (week == -1) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
            week = getWeek(calendar.getTime(), --time);
        }

        return week;
    }

    public int getCourseCount() {
        return repository.getCount(null, null, true);

    }

    public TableFragment.LocalCourse getByWeek(String id) {
        try {
            return repository.getByKeyWord(id, new String[]{DbConstance.COL_COURSE_WEEK}, null, new String[]{}, true).get(0);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(TableFragment.LocalCourse localCourse) {
        repository.update(localCourse);
    }

    public void deleteAll() {
        repository.deleteAll(DbConstance.DB_NAME);
    }

    public void addCourse(TableFragment.LocalCourse localCourse) {
        if (isRepeat(localCourse)) {
            update(localCourse);
            return;
        }
        localCourses.add(localCourse);
        repository.insert(localCourse);

    }


    public List<TableFragment.LocalCourse> getLocalCourses() {
        return localCourses;
    }
}
