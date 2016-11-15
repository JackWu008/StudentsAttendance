package net.lzzy.studentsattendance.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.lzzy.studentsattendance.R;
import net.lzzy.studentsattendance.activitys.AboutActivity;
import net.lzzy.studentsattendance.activitys.HelpActivity;
import net.lzzy.studentsattendance.activitys.InfoActivity;
import net.lzzy.studentsattendance.activitys.LoginActivity;
import net.lzzy.studentsattendance.activitys.MainActivity;
import net.lzzy.studentsattendance.activitys.SettingActivity;
import net.lzzy.studentsattendance.constants.Constants;
import net.lzzy.studentsattendance.dataAcquisition.GetInfoTask;
import net.lzzy.studentsattendance.view.CustomAlertDialog;
import net.lzzy.studentsattendance.view.CustomDialog;

public class MeFragment extends Fragment implements View.OnClickListener, MainActivity.OnMenuClick {
    private View view;
    private TextView tv_name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_me, container, false);
        }
        initView();
        return view;
    }


    private void initView() {
        FrameLayout layout_help = (FrameLayout) view.findViewById(R.id.fragment_me_layout_help);
        FrameLayout layout_landing = (FrameLayout) view.findViewById(R.id.fragment_me_layout_landing);
        FrameLayout layout_exit = (FrameLayout) view.findViewById(R.id.fragment_me_layout_exit);
        FrameLayout layout_about = (FrameLayout) view.findViewById(R.id.fragment_me_layout_about);
        FrameLayout layout_settings = (FrameLayout) view.findViewById(R.id.fragment_me_layout_settings);
        tv_name = (TextView) view.findViewById(R.id.fragment_me_tv_landing);
        layout_landing.setOnClickListener(this);
        layout_exit.setOnClickListener(this);
        layout_about.setOnClickListener(this);
        layout_settings.setOnClickListener(this);
        layout_help.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        updateView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_me_layout_landing:
                if (Constants.isLogined) {
                    if (Constants.hasInfo) {
                        startInfoActivity();
                    } else {
                        final CustomAlertDialog custom_dialog = new CustomAlertDialog(getContext());
                        custom_dialog.setTitle("提示");
                        custom_dialog.setMessage("是否获取个人信息?");
                        custom_dialog.setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                custom_dialog.dismiss();
                                final CustomDialog dialog = new CustomDialog(getContext(), R.style.custom_dialog);
                                dialog.setTitle("获取中");
                                dialog.show();
                                new GetInfoTask() {
                                    @Override
                                    protected void onPostExecute(Boolean aBoolean) {
                                        super.onPostExecute(aBoolean);
                                        dialog.dismiss();
                                        if (aBoolean)
                                            Constants.hasInfo = true;
                                        startInfoActivity();

                                    }
                                }.execute();
                            }
                        });
                        custom_dialog.setNegativeButton("取消", null);
                        custom_dialog.show();
                    }
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.fragment_me_layout_about:
                Intent intent_about = new Intent(getContext(), AboutActivity.class);
                startActivity(intent_about);
                break;
            case R.id.fragment_me_layout_help:
                Intent intent_help = new Intent(getContext(), HelpActivity.class);
                startActivity(intent_help);
                break;
            case R.id.fragment_me_layout_exit:
                final CustomAlertDialog custom_dialog = new CustomAlertDialog(getContext());
                custom_dialog.setTitle("提示");
                custom_dialog.setMessage("确定退出应用?");
                custom_dialog.setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        custom_dialog.dismiss();
                        getActivity().finish();
                        System.exit(0);
                    }
                });
                custom_dialog.setNegativeButton("取消", null);
                custom_dialog.show();
                break;
            case R.id.fragment_me_layout_settings:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
        }
    }

    private void startInfoActivity() {
        Intent intent = new Intent(getContext(), InfoActivity.class);
        startActivity(intent);
    }

    private void updateView() {
        if (!Constants.isLogined)
            tv_name.setText("请点击登录");
        else
            tv_name.setText("我的信息");
    }


    @Override
    public void onResume() {
        super.onResume();
        updateView();

    }

    @Override
    public void onMenuClick() {
        updateView();

    }
}
