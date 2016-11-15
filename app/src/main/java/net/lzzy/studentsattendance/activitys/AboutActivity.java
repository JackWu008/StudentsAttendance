package net.lzzy.studentsattendance.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.lzzy.studentsattendance.R;


public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_about_back);
        TextView tv_content= (TextView) findViewById(R.id.activity_about_tv_content);
        tv_content.setText("制作团队：lzzy\n团队成员：苏浩东、吴昌喜、黄宾棠");
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
