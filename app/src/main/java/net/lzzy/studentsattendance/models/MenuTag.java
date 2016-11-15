package net.lzzy.studentsattendance.models;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MenuTag {
    private TextView tv;
    private ImageView iv;
    private Fragment fragment;
    private int normalImgRes;
    private int normalTvRes;
    private int pressedImgRes;
    private int pressedTvRes;
    private View bindActionView;

    public void setBindActionView(View bindActionView) {
        this.bindActionView = bindActionView;
    }

    public View getBindActionView() {
        return bindActionView;
    }

    public TextView getTv() {
        return tv;
    }

    public void setPressedTvRes(int pressedTvRes) {
        this.pressedTvRes = pressedTvRes;
    }

    public int getUnPressedTvRes() {
        return pressedTvRes;
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }

    public ImageView getIv() {
        return iv;
    }

    public void setIv(ImageView iv) {
        this.iv = iv;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getPressedImgRes() {
        return normalImgRes;
    }

    public void setNormalImgRes(int normalImgRes) {
        this.normalImgRes = normalImgRes;
    }

    public int getPressedTvRes() {
        return normalTvRes;
    }

    public void setNormalTvRes(int normalTvRes) {
        this.normalTvRes = normalTvRes;
    }

    public int getUnPressedImgRes() {
        return pressedImgRes;
    }

    public void setPressedImgRes(int pressedImgRes) {
        this.pressedImgRes = pressedImgRes;
    }
}
