package net.lzzy.studentsattendance.models;

import com.example.get.MyLean;
import com.example.get.Comprehensive;

import java.util.List;
import java.util.Map;


public class MyInfo {
    private Map<String, String> baseInfo;
    private List<MyLean> leanInfo;
    private List<Comprehensive> comprehensive;

    public List<MyLean> getLeanInfo() {
        return leanInfo;
    }

    public Map<String, String> getBaseInfo() {
        return baseInfo;
    }

    public List<Comprehensive> getComprehensive() {
        return comprehensive;
    }

    public void setLeanInfo(List<MyLean> leanInfo) {
        this.leanInfo = leanInfo;
    }

    public void setBaseInfo(Map<String, String> baseInfo) {
        this.baseInfo = baseInfo;
    }

    private static MyInfo instance;

    public void setComprehensive(List<Comprehensive> comprehensive) {
        this.comprehensive = comprehensive;
    }

    public static MyInfo getInstance() {
        if (instance == null)
            instance = new MyInfo();
        return instance;
    }


}
