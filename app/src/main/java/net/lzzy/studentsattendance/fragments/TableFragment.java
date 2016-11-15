package net.lzzy.studentsattendance.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.tabletimeview.TableTimeView;

import net.lzzy.sql.Sqlable;
import net.lzzy.studentsattendance.R;
import net.lzzy.studentsattendance.constants.DbConstance;
import net.lzzy.studentsattendance.utils.CourseFactory;
import net.lzzy.studentsattendance.utils.LocalCourseUtils;

import java.util.HashMap;
import java.util.Map;


public class TableFragment extends Fragment {

    private TableTimeView tableTimeView;

    public static TableFragment newInstance(String WEEk) {//静态工厂方泿
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        args.putString(DbConstance.COL_COURSE_RAW, WEEk);
        fragment.setArguments(args);
        return fragment;
    }

    public void showCourse(LocalCourse course) {

//        String[][] aWeekRawCourse = LocalCourseUtils.getCourseData(localCourse.getRaw());
//        tableTimeView.setData(LocalCourseUtils.getDisplayFormat(aWeekRawCourse));
//        weekView.setClickPosition(week - 1);
//
//        LocalCourse localCourse = CourseFactory.getInstance(getContext()).getByWeek(getArguments().getString(DbConstance.COL_COURSE_WEEK));
//        if (localCourse == null)
//            return;


        String[][] aWeekRawCourse = LocalCourseUtils.getAWeekRaw(course.getRaw());
        tableTimeView.setData(LocalCourseUtils.getDisplayFormat(aWeekRawCourse));

    }


//    private void heightLightCurrentWeek() {
//        int w = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
//        TextView tv;
//        if (w == 1)
//            for (int i = 1; i <= 6; i++) {
//                tv = tableTimeView.getCourseTextView(7, i);
//                tv.setBackgroundColor(getContext().getResources().getColor(R.color.colorTable));
//            }
//        else
//            for (int i = 1; i <= 6; i++) {
//                tv = tableTimeView.getCourseTextView(w - 1, i);
//                tv.setBackgroundColor(getContext().getResources().getColor(R.color.colorTable));
//            }
//
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        FrameLayout f = (FrameLayout) view.findViewById(R.id.child_layout);
        tableTimeView = new TableTimeView(getActivity());
        f.addView(tableTimeView);
        String week = getArguments().getString(DbConstance.COL_COURSE_RAW);
        if (week == null)
            return view;
        LocalCourse localCourse = CourseFactory.getInstance(getContext()).getCourse(week);
        if (localCourse != null) {
            tableTimeView.setData(LocalCourseUtils.getDisplayFormat(LocalCourseUtils.getAWeekRaw(localCourse.getRaw())));
        }
        return view;
    }

    public static class LocalCourse extends Sqlable {
        private String raw;
        private String week;

        public void setWeekRaw(String raw) {
            this.setRaw(raw);
        }


        public String getRaw() {
            return raw;
        }

        @Override
        public String getTableName() {
            return DbConstance.TABLE_COURSE;
        }

        @Override
        public String getKeyVal() {
            return getWeek();
        }

        public void setWeek(String week) {
            this.week = week;
        }

        @Override
        public String getKeyCol() {
            return DbConstance.COL_COURSE_WEEK;
        }

        @Override
        public Map<String, Object> getMap() {
            Map<String, Object> map = new HashMap<>();
            map.put(DbConstance.COL_COURSE_RAW, getRaw());
            map.put(DbConstance.COL_COURSE_WEEK, getWeek());
            return map;
        }

        @Override
        public void setCursor(Cursor cursor) {
            setWeek(cursor.getString(cursor.getColumnIndex(DbConstance.COL_COURSE_WEEK)));
            setRaw(cursor.getString(cursor.getColumnIndex(DbConstance.COL_COURSE_RAW)));

        }


        public void setRaw(String raw) {
            this.raw = raw;
        }

        public String getWeek() {
            return week;
        }
    }
}

