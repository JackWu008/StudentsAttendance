package net.lzzy.studentsattendance.fragments;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.get.GetInfoUtils;

import net.lzzy.studentsattendance.R;
import net.lzzy.studentsattendance.activitys.MainActivity;
import net.lzzy.studentsattendance.constants.Constants;
import net.lzzy.studentsattendance.models.MyInfo;
import net.lzzy.studentsattendance.network.AttendanceService;
import net.lzzy.studentsattendance.network.MyWifiManager;


public class AttendanceFragment extends Fragment implements MainActivity.OnMenuClick, View.OnClickListener, MyWifiManager.OnConnectSuccess {
    private View view;
    private Button btn_connect;
    private MyWifiManager myWifiManager;
    private Button btn_start;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (myWifiManager == null)
            myWifiManager = new MyWifiManager(getContext(), this);
        if (handler == null)
            handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 1)
                        Toast.makeText(getContext(), "签到成功", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getContext(), "签到失败 " + msg.getData().getString("EXTRA_INFO"), Toast.LENGTH_SHORT).show();
                    }
                }
            };
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyInfo.getInstance().getBaseInfo() != null)
            btn_start.setEnabled(myWifiManager.isConnectCorrect());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_attendance, container, false);
        btn_connect = (Button) view.findViewById(R.id.fragment_attendance_btn_connect);
        btn_connect.setOnClickListener(this);

        btn_start = (Button) view.findViewById(R.id.fragment_attendance_btn_start);
        btn_start.setEnabled(false);
        btn_start.setOnClickListener(this);
        return view;
    }


    Handler handler;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_attendance_btn_start:
                new AttendanceService(myWifiManager.getServeIp(), 12369, MyInfo.getInstance().getBaseInfo().get(GetInfoUtils.INFO_ID), MyInfo.getInstance().getBaseInfo().get(GetInfoUtils.INFO_NAME), handler).start();
                break;
            case R.id.fragment_attendance_btn_connect:
                Log.i("INFO_START", myWifiManager.getManager().getWifiState() + "");
                if (!Constants.isLogined) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!myWifiManager.isConnectCorrect())
                    Toast.makeText(getContext(), "尝试连接", Toast.LENGTH_SHORT).show();
                myWifiManager.startAttend();
                break;

        }

    }

    @Override
    public void onConnectSuccess(boolean state) {
        if (MyInfo.getInstance().getBaseInfo() != null)
            btn_start.setEnabled(state);
    }

    @Override
    public void isWifiChange() {

        if (myWifiManager.getManager().getWifiState() != WifiManager.WIFI_STATE_ENABLED) {
            btn_connect.setText("打开WIFI");
        } else {
            Log.i("ATTEND_UDHF", myWifiManager.getManager().getConnectionInfo().getSSID());
            if (!myWifiManager.isConnectCorrect())
                btn_connect.setText("连接教师端");
            else
                btn_connect.setText("已连接");
        }


    }

    @Override
    public void onMenuClick() {

    }
}
