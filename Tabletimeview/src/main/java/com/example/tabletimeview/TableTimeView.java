package com.example.tabletimeview;

import android.content.Context;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/10/10.
 */
public class TableTimeView extends ScrollView implements View.OnClickListener {
    private TextView[][] tables = new TextView[7][6];
    private OnTableClassClickListener listener;

    public TableTimeView(Context context) {
        super(context);
        setVerticalScrollBarEnabled(false);
        addView(View.inflate(context, R.layout.table_time, null));
        initView();


    }


    public TextView getCourseTextView(int week, int aClass) {
        return tables[week - 1][aClass - 1];
    }


    public void setData(String[][] data) {
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 6; j++)
                tables[i][j].setText(data[i][j]);
    }

    public void initView() {
        tables[0][0] = (TextView) this.findViewById(R.id.table_tv_1_1);
        tables[0][1] = (TextView) this.findViewById(R.id.table_tv_1_2);
        tables[0][2] = (TextView) this.findViewById(R.id.table_tv_1_3);
        tables[0][3] = (TextView) this.findViewById(R.id.table_tv_1_4);
        tables[0][4] = (TextView) this.findViewById(R.id.table_tv_1_5);
        tables[0][5] = (TextView) this.findViewById(R.id.table_tv_1_6);
        tables[1][0] = (TextView) this.findViewById(R.id.table_tv_2_1);
        tables[1][1] = (TextView) this.findViewById(R.id.table_tv_2_2);
        tables[1][2] = (TextView) this.findViewById(R.id.table_tv_2_3);
        tables[1][3] = (TextView) this.findViewById(R.id.table_tv_2_4);
        tables[1][4] = (TextView) this.findViewById(R.id.table_tv_2_5);
        tables[1][5] = (TextView) this.findViewById(R.id.table_tv_2_6);
        tables[2][0] = (TextView) this.findViewById(R.id.table_tv_3_1);
        tables[2][1] = (TextView) this.findViewById(R.id.table_tv_3_2);
        tables[2][2] = (TextView) this.findViewById(R.id.table_tv_3_3);
        tables[2][3] = (TextView) this.findViewById(R.id.table_tv_3_4);
        tables[2][4] = (TextView) this.findViewById(R.id.table_tv_3_5);
        tables[2][5] = (TextView) this.findViewById(R.id.table_tv_3_6);
        tables[3][0] = (TextView) this.findViewById(R.id.table_tv_4_1);
        tables[3][1] = (TextView) this.findViewById(R.id.table_tv_4_2);
        tables[3][2] = (TextView) this.findViewById(R.id.table_tv_4_3);
        tables[3][3] = (TextView) this.findViewById(R.id.table_tv_4_4);
        tables[3][4] = (TextView) this.findViewById(R.id.table_tv_4_5);
        tables[3][5] = (TextView) this.findViewById(R.id.table_tv_4_6);
        tables[4][0] = (TextView) this.findViewById(R.id.table_tv_5_1);
        tables[4][1] = (TextView) this.findViewById(R.id.table_tv_5_2);
        tables[4][2] = (TextView) this.findViewById(R.id.table_tv_5_3);
        tables[4][3] = (TextView) this.findViewById(R.id.table_tv_5_4);
        tables[4][4] = (TextView) this.findViewById(R.id.table_tv_5_5);
        tables[4][5] = (TextView) this.findViewById(R.id.table_tv_5_6);
        tables[5][0] = (TextView) this.findViewById(R.id.table_tv_6_1);
        tables[5][1] = (TextView) this.findViewById(R.id.table_tv_6_2);
        tables[5][2] = (TextView) this.findViewById(R.id.table_tv_6_3);
        tables[5][3] = (TextView) this.findViewById(R.id.table_tv_6_4);
        tables[5][4] = (TextView) this.findViewById(R.id.table_tv_6_5);
        tables[5][5] = (TextView) this.findViewById(R.id.table_tv_6_6);
        tables[6][0] = (TextView) this.findViewById(R.id.table_tv_7_1);
        tables[6][1] = (TextView) this.findViewById(R.id.table_tv_7_2);
        tables[6][2] = (TextView) this.findViewById(R.id.table_tv_7_3);
        tables[6][3] = (TextView) this.findViewById(R.id.table_tv_7_4);
        tables[6][4] = (TextView) this.findViewById(R.id.table_tv_7_5);
        tables[6][5] = (TextView) this.findViewById(R.id.table_tv_7_6);

    }

    public void setTableClassOnCLickListener(OnTableClassClickListener listener) {
        this.listener = listener;
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 6; j++) {
                tables[i][j].setOnClickListener(this);
                tables[i][j].setTag(R.integer.KEY_A_CLASS_TEXT, new TableClass(i + 1, j + 1));
            }
    }

    @Override
    public void onClick(View view) {
        TableClass tableClass = (TableClass) view.getTag(R.integer.KEY_A_CLASS_TEXT);
        listener.onClick(view, tableClass.getWeek(), tableClass.getaClass());
    }
}
