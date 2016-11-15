package net.lzzy.studentsattendance.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import net.lzzy.studentsattendance.R;
import net.lzzy.studentsattendance.utils.UserConfigInfoUtils;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_setting_toolbar);
        setSupportActionBar(toolbar);
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_setting_back);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Switch courseSwitch = (Switch) findViewById(R.id.setting_switch);
        courseSwitch.setChecked(UserConfigInfoUtils.getCourseTableMode(this));
        courseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserConfigInfoUtils.setCourseTableMode(SettingActivity.this, isChecked);
            }
        });


    }


}
