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

import com.example.get.Comprehensive;

import net.lzzy.studentsattendance.models.MyInfo;
import net.lzzy.studentsattendance.R;

import java.util.List;


public class ComprehensiveFragment extends Fragment {

    private RecyclerView listView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_comprehensive, container, false);
        listView = (RecyclerView) view.findViewById(R.id.fragment_comprehensive_rv);

        listView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 20;
                outRect.bottom = 20;
                outRect.left = 20;
                outRect.right = 20;
            }
        });
        initView();
        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        List<Comprehensive> comprehensives;

        public MyAdapter(List<Comprehensive> comprehensives) {
            this.comprehensives = comprehensives;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_time;
            TextView tv_reason;
            TextView tv_state;
            TextView tv_score;
            TextView tv_note;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_time = (TextView) itemView.findViewById(R.id.item_comprehensive_time);
                tv_reason = (TextView) itemView.findViewById(R.id.item_comprehensive_reason);
                tv_score = (TextView) itemView.findViewById(R.id.item_comprehensive_score);
                tv_note = (TextView) itemView.findViewById(R.id.item_comprehensive_note);
                tv_state = (TextView) itemView.findViewById(R.id.item_comprehensive_state);
            }
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(getActivity().getLayoutInflater().inflate(R.layout.item_comprehensive, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv_state.setText(comprehensives.get(position).getState());
            holder.tv_reason.setText(comprehensives.get(position).getResone());
            holder.tv_score.setText(comprehensives.get(position).getScore());
            holder.tv_time.setText(comprehensives.get(position).getTime());
            holder.tv_note.setText(comprehensives.get(position).getNote());
        }


        @Override
        public int getItemCount() {
            return comprehensives.size();
        }
    }

    private void initView() {
        TextView textView = (TextView) view.findViewById(R.id.fragment_comprehensive_tv);
        textView.setText("目前积分:"+Comprehensive.toalscore);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(new MyAdapter(MyInfo.getInstance().getComprehensive()));
    }
}
