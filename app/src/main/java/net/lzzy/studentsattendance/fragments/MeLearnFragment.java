package net.lzzy.studentsattendance.fragments;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.get.MyLean;

import net.lzzy.studentsattendance.models.MyInfo;
import net.lzzy.studentsattendance.adapter.MyLeanAdapter;
import net.lzzy.studentsattendance.R;

import java.util.List;

public class MeLearnFragment extends Fragment {
    private RecyclerView lv_learn;
    private TextView tv_title;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_learn, container, false);
        lv_learn = (RecyclerView) view.findViewById(R.id.fragment_me_learn);
        tv_title = (TextView) view.findViewById(R.id.fragment_me_learn_total);
        lv_learn.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 20;
                outRect.left = 30;
                outRect.right = 30;
                outRect.bottom = 20;
            }
        });
        lv_learn.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<MyLean> list = MyInfo.getInstance().getLeanInfo();
        lv_learn.setAdapter(new MyLeanAdapter(getContext(), list));
        tv_title.setText("共修:" + MyLean.totalCourse + "门课程,已修" + MyLean.totalScore + "学分," + "其中有" + MyLean.failAmount + "门不及格");
    }
}
