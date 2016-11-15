package net.lzzy.studentsattendance.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Toast;

import net.lzzy.studentsattendance.R;


public class PromptlyToast {
    private Context context;
    private final TextView tv;

    private PromptlyToast(Context context) {
        this.context = context;
        tv = new TextView(context);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setPadding(10, 10, 10, 10);
        tv.setTextSize(15);
    }

    private Toast toast;
    private static PromptlyToast instance;

    public static PromptlyToast getInstance(Context context) {
        if (instance == null)
            instance = new PromptlyToast(context);
        return instance;
    }

    public void show(String text, int backColor, int textColor) {
        createAndShow(text, backColor, textColor);
    }


    public void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

    private void createAndShow(String text, int backColor, int textColor) {
        cancel();
        toast = new Toast(context);
        tv.setText(text);
        tv.setTextColor(textColor);
        tv.setBackgroundColor(backColor);
        toast.setView(tv);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public void show(String text) {
        createAndShow(text, context.getResources().getColor(R.color.colorToastBackground), context.getResources().getColor(R.color.colorToastText));
    }


}
