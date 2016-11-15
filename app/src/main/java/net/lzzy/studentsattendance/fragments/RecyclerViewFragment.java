package net.lzzy.studentsattendance.fragments;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.get.CourseUtils;

import net.lzzy.studentsattendance.models.ATimeLean;
import net.lzzy.studentsattendance.adapter.CourseAdapter;
import net.lzzy.studentsattendance.R;
import net.lzzy.studentsattendance.constants.Constants;
import net.lzzy.studentsattendance.constants.DbConstance;
import net.lzzy.studentsattendance.utils.CourseFactory;
import net.lzzy.studentsattendance.utils.LocalCourseUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.get.CourseUtils.getCourseClassroom;
import static com.example.get.CourseUtils.getCourseNod;
import static com.example.get.CourseUtils.getCourseTeacher;
import static com.example.get.CourseUtils.getCourseTime;




public class RecyclerViewFragment extends Fragment {


    private List<ATimeLean> aTimeCourseList;
    private FrameLayout frameLayout;

    private class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return aTimeCourseList.size();
        }

        @Override
        public ATimeLean getItem(int position) {
            return aTimeCourseList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_course, null);
            TextView tv_name = (TextView) convertView.findViewById(R.id.course_item_name);
            TextView tv_time = (TextView) convertView.findViewById(R.id.course_item_time);
            TextView tv_nod = (TextView) convertView.findViewById(R.id.course_item_node);
            tv_name.setText(getItem(position).getName());
            tv_time.setText(getItem(position).getTime());
            tv_nod.setText(getItem(position).getNode());
            return convertView;
        }


    }


    private RecyclerView lv;

    public static RecyclerViewFragment newInstance(String WEEk) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString(DbConstance.COL_COURSE_RAW, WEEk);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        frameLayout = new FrameLayout(getContext());
        frameLayout.setBackgroundColor(getContext().getResources().getColor(R.color.colorGeneraActionBackground));
        return frameLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String week = getArguments().getString(DbConstance.COL_COURSE_RAW);
        if (week == null) {
            TextView tv_empty = new TextView(getContext());
            tv_empty.setText("哇塞！这周居然没课！");
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            tv_empty.setLayoutParams(params);
            frameLayout.addView(tv_empty);
            return;
        }
        lv = new RecyclerView(getContext());
        frameLayout.addView(lv);
        aTimeCourseList = new ArrayList<>();
        TableFragment.LocalCourse localCourses = CourseFactory.getInstance(getContext()).getCourse(week);
        if (localCourses == null || !localCourses.getRaw().contains("时间")) {
            TextView tv_empty = new TextView(getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            tv_empty.setLayoutParams(params);
            tv_empty.setTextSize(15);
            tv_empty.setTextColor(getActivity().getResources().getColor(android.R.color.black));
            tv_empty.setText("哇塞！这周居然没课！");
            frameLayout.addView(tv_empty);
            return;
        }
        String[][] aWeekRaw = LocalCourseUtils.getAWeekRaw(localCourses.getRaw());
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 6; j++) {
                if (!aWeekRaw[i][j].contains("时间"))
                    continue;
                List<String> list = CourseUtils.getNodeNum(aWeekRaw[i][j]);
                for (int k = 0; k < list.size(); k++) {
                    ATimeLean lean = new ATimeLean();
                    lean.setNode(getCourseNod(list.get(k)));
                    lean.setName(CourseUtils.getCourseName(list.get(k)));
                    lean.setTime(getCourseTime(list.get(k)));
                    lean.setClassroom("教室：" + getCourseClassroom(list.get(k)));
                    lean.setTeacher("教师：" + getCourseTeacher(list.get(k)));
                    lean.setWeek("星期" + Constants.weekMap.get(i));
                    aTimeCourseList.add(lean);
                }

            }
        CourseAdapter courseAdapter = new CourseAdapter(getContext(), aTimeCourseList);
        lv.setAdapter(courseAdapter);
        lv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }


            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 25;
                outRect.left = 20;
                outRect.right = 20;
                outRect.bottom = 25;
            }
        });
        lv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
