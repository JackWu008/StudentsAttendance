package net.lzzy.studentsattendance.view;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.lzzy.studentsattendance.R;


public class CustomAlertDialog extends AlertDialog implements View.OnClickListener {
    private TextView tv_title;
    private TextView tv_content;
    private Button btn_negative;
    private Button btn_positive;
    private LinearLayout layout;

    public CustomAlertDialog(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        this.show();
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (width * 0.85);
        lp.height = (int) (height * 0.3);
        lp.gravity = Gravity.CENTER_VERTICAL;
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view = View.inflate(context, R.layout.custom_alert_dialog, null);
        setContentView(view);
        tv_title = (TextView) view.findViewById(R.id.custom_alert_dialog_tv_title);
        tv_content = (TextView) view.findViewById(R.id.custom_alert_dialog_tv_content);
        btn_negative = (Button) view.findViewById(R.id.custom_alert_dialog_btn_negative);
        btn_positive = (Button) view.findViewById(R.id.custom_alert_dialog_btn_positive);
        layout = (LinearLayout) view.findViewById(R.id.custom_alert_dialog_layout);
        setContentView(view);
    }

    @Override
    public void setTitle(CharSequence title) {
        tv_title.setText(title);
    }


    public void addBottomView(View view, LinearLayout.LayoutParams layoutParams) {
        layout.addView(view, layoutParams);

    }

    @Override
    public void setContentView(@NonNull View view, ViewGroup.LayoutParams params) {
        tv_content.setGravity(View.GONE);
        layout.addView(view, params);
    }

    @Override
    public void setMessage(CharSequence message) {
        tv_content.setText(message);
    }


    public void setNegativeButton(CharSequence text, View.OnClickListener listener) {
        btn_negative.setText(text);
        if (listener == null)
            btn_negative.setOnClickListener(this);
        else
            btn_negative.setOnClickListener(listener);
    }



    public void setPositiveButton(CharSequence text, View.OnClickListener listener) {
        btn_positive.setText(text);
        if (listener != null)
            btn_positive.setOnClickListener(listener);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
