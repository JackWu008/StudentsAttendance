package net.lzzy.studentsattendance.dataAcquisition;


import android.os.AsyncTask;

import com.example.get.GetInfoUtils;
import com.example.get.LoginException;

import net.lzzy.studentsattendance.models.MyInfo;

import java.util.Map;

public class GetInfoTask extends AsyncTask<String, Void, Boolean> {

    public GetInfoTask() {
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            Map<String, String> map = GetInfoUtils.getPersonalInfo();
            MyInfo.getInstance().setBaseInfo(map);
            MyInfo.getInstance().setLeanInfo(GetInfoUtils.getMyLeaning(map.get(GetInfoUtils.INFO_ID)));
            MyInfo.getInstance().setComprehensive(GetInfoUtils.getXueFen(map.get(GetInfoUtils.INFO_ID)));
            return true;
        } catch (LoginException e) {
            e.printStackTrace();
        }
        return false;
    }
}
