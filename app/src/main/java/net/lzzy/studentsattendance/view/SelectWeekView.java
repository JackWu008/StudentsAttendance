package net.lzzy.studentsattendance.view;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import net.lzzy.studentsattendance.R;

public class SelectWeekView implements AdapterView.OnItemClickListener {
    private PopupWindow pop;
    private Context context;
    private AdapterView.OnItemClickListener itemClickListener;
    private int clickPosition;
    private Button showButton;
    private ImageView arrow;
    private final int popWidth;


    public SelectWeekView(final Context context, AdapterView.OnItemClickListener itemClickListener, Button showButton, final ImageView arrow) {
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.showButton = showButton;
        this.arrow = arrow;
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pop_course_week, null);
        ListView list = (ListView) view.findViewById(R.id.pop_course_week_lv);
        popWidth = (int) (width * 0.35);
        int popHeight = (int) (height * 0.4);
        pop = new PopupWindow(view, popWidth, popHeight, true);
        list.setOnItemClickListener(this);
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        String[] strings = new String[20];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = "第" + (i + 1) + "周";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, 0, strings) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {

                if (convertView == null)
                    convertView = View.inflate(SelectWeekView.this.context, R.layout.custom_week_item, null);
                TextView tv = (TextView) convertView;
                if (clickPosition == position) {
                    convertView.setBackgroundColor(context.getResources().getColor(R.color.colorGeneralWindowBackground));
                    tv.setTextColor(context.getResources().getColor(android.R.color.white));
                } else {
                    convertView.setBackgroundColor(Color.WHITE);
                    tv.setTextColor(context.getResources().getColor(R.color.colorGeneralWindowBackground));
                }
                tv.setText(getItem(position));
                return convertView;
            }
        };
        list.setAdapter(adapter);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                arrow.setImageResource(R.drawable.ic_course_arrow_next);
            }
        });
    }


    public void show() {
        pop.showAsDropDown(showButton, -popWidth / 2 + showButton.getWidth() / 2, 0);
    }

    public void setClickPosition(int clickPosition) {
        this.clickPosition = clickPosition;
        showButton.setText("第" + (clickPosition + 1) + "周");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pop.dismiss();
        setClickPosition(position);
        arrow.setImageResource(R.drawable.ic_course_arrow_next);
        itemClickListener.onItemClick(parent, view, position, id);

    }
}
