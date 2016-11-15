package net.lzzy.studentsattendance.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.get.GetInfoUtils;

import net.lzzy.studentsattendance.models.MyInfo;
import net.lzzy.studentsattendance.R;

import java.util.Map;

public class BasicInfoFragment extends Fragment {
    private TextView tv_name;
    private TextView tv_id;
    private TextView tv_born_date;
    private TextView tv_state;
    private TextView tv_college;
    private TextView tv_entrance_time;
    private TextView tv_grade;
    private TextView tv_height;
    private TextView tv_phone;
    private TextView tv_weight;
    private TextView tv_sex;
    private TextView tv_home_address;
    private TextView tv_class;
    private TextView tv_professional;
    private TextView tv_nation;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_basic_info, container, false);
        initView();
        displayPersonInfo(MyInfo.getInstance().getBaseInfo());
        return view;
    }

    private void initView() {
        tv_name = (TextView) view.findViewById(R.id.activity_info_tv_name);
        tv_id = (TextView) view.findViewById(R.id.activity_info_tv_id);
        tv_born_date = (TextView) view.findViewById(R.id.activity_info_tv_bornDate);
        tv_state = (TextView) view.findViewById(R.id.activity_info_tv_state);
        tv_college = (TextView) view.findViewById(R.id.activity_info_tv_college);
        tv_entrance_time = (TextView) view.findViewById(R.id.activity_info_tv_entranceTime);
        tv_grade = (TextView) view.findViewById(R.id.activity_info_tv_grade);
        tv_height = (TextView) view.findViewById(R.id.activity_info_tv_height);
        tv_phone = (TextView) view.findViewById(R.id.activity_info_tv_phone);
        tv_weight = (TextView) view.findViewById(R.id.activity_info_tv_weight);
        tv_sex = (TextView) view.findViewById(R.id.activity_info_tv_sex);
        tv_home_address = (TextView) view.findViewById(R.id.activity_info_tv_homeAddress);
        tv_class = (TextView) view.findViewById(R.id.activity_info_tv_squad);
        tv_professional = (TextView) view.findViewById(R.id.activity_info_tv_professional);
        tv_nation = (TextView) view.findViewById(R.id.activity_info_tv_nation);

    }


    private void displayPersonInfo(Map<String, String> map) {
        tv_name.setText(map.get(GetInfoUtils.INFO_NAME));
        tv_born_date.setText(map.get(GetInfoUtils.INFO_BIRTHDAY));
        tv_college.setText(map.get(GetInfoUtils.INFO_SERIES));
        tv_entrance_time.setText(map.get(GetInfoUtils.INFO_ENTER_SCHOOL_TIME));
        tv_grade.setText(map.get(GetInfoUtils.INFO_GRADE));
        tv_id.setText(map.get(GetInfoUtils.INFO_ID));
        tv_home_address.setText(map.get(GetInfoUtils.INFO_HOME_ADDRESS));
        tv_height.setText(map.get(GetInfoUtils.INFO_HEIGHT));
        tv_nation.setText(map.get(GetInfoUtils.INFO_NATION));
        tv_phone.setText(map.get(GetInfoUtils.INFO_PHONE));
        tv_weight.setText(map.get(GetInfoUtils.INFO_WEIGHT));
        tv_sex.setText(map.get(GetInfoUtils.INFO_SEX));
        tv_class.setText(map.get(GetInfoUtils.INFO_ClASS));
        tv_professional.setText(map.get(GetInfoUtils.INFO_PROFESSION));
        tv_state.setText(map.get(GetInfoUtils.INFO_STATE));
    }
}
