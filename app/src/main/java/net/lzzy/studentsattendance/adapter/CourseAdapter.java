package net.lzzy.studentsattendance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.lzzy.studentsattendance.models.ATimeLean;
import net.lzzy.studentsattendance.R;

import java.util.List;


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private Context context;
    private List<ATimeLean> leanList;

    public CourseAdapter(Context context, List<ATimeLean> leanList) {
        this.context = context;
        this.leanList = leanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_course, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_name.setText(leanList.get(position).getName());
        holder.tv_time.setText(leanList.get(position).getTime());
        holder.tv_node.setText(leanList.get(position).getNode());
        holder.tv_teacher.setText(leanList.get(position).getTeacher());
        holder.tv_classroom.setText(leanList.get(position).getClassroom());
        holder.tv_week.setText(leanList.get(position).getWeek());
    }

    @Override
    public int getItemCount() {
        return leanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_time;
        TextView tv_classroom;
        TextView tv_teacher;
        TextView tv_node;
        TextView tv_week;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_week = (TextView) itemView.findViewById(R.id.course_item_tv_week);
            tv_name = (TextView) itemView.findViewById(R.id.course_item_name);
            tv_time = (TextView) itemView.findViewById(R.id.course_item_time);
            tv_classroom = (TextView) itemView.findViewById(R.id.course_item_classroom);
            tv_node = (TextView) itemView.findViewById(R.id.course_item_node);
            tv_teacher = (TextView) itemView.findViewById(R.id.course_item_teacher);
        }
    }


}
