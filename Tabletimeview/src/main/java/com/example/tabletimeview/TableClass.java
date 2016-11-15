package com.example.tabletimeview;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/10/10.
 */
public class TableClass {
    private int week;
    private int aClass;

    public TableClass(int week, int aClass) {
        this.aClass = aClass;
        this.week = week;
    }

    public int getaClass() {
        return aClass;
    }

    public int getWeek() {
        return week;
    }
}
