package net.lzzy.studentsattendance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.get.MyLean;

import net.lzzy.studentsattendance.R;

import java.util.List;



public class MyLeanAdapter extends RecyclerView.Adapter<MyLeanAdapter.ViewHolder> {
    private Context context;
    private List<MyLean> list;

    public MyLeanAdapter(Context context, List<MyLean> list) {
        this.context = context;
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_state;
        TextView tv_score;
        TextView tv_type;
        TextView tv_term;

        ViewHolder(View convertView) {
            super(convertView);
            tv_name = (TextView) convertView.findViewById(R.id.item_my_lean_name);
            tv_state = (TextView) convertView.findViewById(R.id.item_my_lean_state);
            tv_score = (TextView) convertView.findViewById(R.id.item_my_lean_score);
            tv_type = (TextView) convertView.findViewById(R.id.item_my_lean_type);
            tv_term = (TextView) convertView.findViewById(R.id.item_my_lean_semester);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_lean, parent, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_name.setText(list.get(position).getSourse());
        holder.tv_score.setText(list.get(position).getScore());
        holder.tv_state.setText(list.get(position).getAchievement());
        holder.tv_term.setText(list.get(position).getTerm());
        holder.tv_type.setText(list.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
