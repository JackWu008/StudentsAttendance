package net.lzzy.studentsattendance.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64DataException;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import net.lzzy.studentsattendance.R;
import net.lzzy.studentsattendance.constants.Constants;
import net.lzzy.studentsattendance.dataAcquisition.LoginTask;
import net.lzzy.studentsattendance.encrypt.PasswordUtils;
import net.lzzy.studentsattendance.utils.PromptlyToast;
import net.lzzy.studentsattendance.utils.UserConfigInfoUtils;
import net.lzzy.studentsattendance.utils.Utils;
import net.lzzy.studentsattendance.view.CustomAlertDialog;
import net.lzzy.studentsattendance.view.CustomDialog;

import java.io.UnsupportedEncodingException;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private EditText edt_account;
    private EditText edt_password;
    private CheckBox cb_memorPass;
    private CheckBox cb_autoLogin;
    private Button btn_landing;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        viewListen();
    }


    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_login_toolbar);
        setSupportActionBar(toolbar);
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_login_back);
        cb_memorPass = (CheckBox) findViewById(R.id.activity_login_cb_memorization_pass);
        cb_autoLogin = (CheckBox) findViewById(R.id.activity_login_cb_automatic_login);
        edt_account = (EditText) findViewById(R.id.activity_login_edt_account);
        edt_password = (EditText) findViewById(R.id.activity_login_edt_password);
        btn_landing = (Button) findViewById(R.id.activity_login_btn_landing);
        cb_autoLogin.setChecked(UserConfigInfoUtils.isAutoLogin(this));
        cb_memorPass.setChecked(UserConfigInfoUtils.isSavePsd(this));
        String pass = UserConfigInfoUtils.getPassword(this);
        try {
            edt_password.setText(PasswordUtils.unlock(pass));
        } catch (UnsupportedEncodingException | Base64DataException e) {
            e.printStackTrace();
        }
        edt_account.setText(UserConfigInfoUtils.getID(this));
        edt_account.setSelection(edt_account.getText().length());
        Utils.popupKeyboard(edt_account);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edt_account.addTextChangedListener(textWatcher);

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!editable.toString().equals("")) {
                btn_landing.setClickable(true);
                btn_landing.setPressed(false);
            } else {
                btn_landing.setClickable(false);
                btn_landing.setPressed(true);
            }
        }
    };

    private void viewListen() {
        btn_landing.setOnClickListener(this);
        cb_autoLogin.setOnCheckedChangeListener(this);
        cb_memorPass.setOnCheckedChangeListener(this);
    }

    public static boolean isConn(Context context) {
        boolean bisConnFlag = false;
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if (network != null) {
            bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
        }
        return bisConnFlag;
    }

    public void setNetworkMethod(final Context context) {
        final CustomAlertDialog custom_dialog = new CustomAlertDialog(LoginActivity.this);
        custom_dialog.setTitle("提示");
        custom_dialog.setMessage("网络连接不可用,是否进行设置?");
        custom_dialog.setPositiveButton("设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                context.startActivity(intent);
            }
        });
        custom_dialog.setNegativeButton("取消", null);
        custom_dialog.show();
    }


    @Override
    public void onClick(View v) {
        String account = edt_account.getText().toString();
        final String password = edt_password.getText().toString();
        if (account.length() == 0 || password.length() == 0) {
            PromptlyToast.getInstance(LoginActivity.this).show("学号或密码不能为空");
            return;
        }


        if (!isConn(LoginActivity.this)) {
            setNetworkMethod(LoginActivity.this);
            return;
        }

        new LoginTask(account, password) {
            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                dialog.dismiss();
                PromptlyToast.getInstance(LoginActivity.this).show(values[0]);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new CustomDialog(LoginActivity.this, R.style.custom_dialog);
                dialog.setTitle("登录中");
                dialog.setCanceledOnTouchOutside(false);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        cancel(true);
                        PromptlyToast.getInstance(LoginActivity.this).show("取消登录");
                    }
                });
                dialog.show();
            }

            @Override
            protected void onPostExecute(Boolean loginResult) {
                super.onPostExecute(loginResult);
                if (!loginResult)
                    return;
                UserConfigInfoUtils.setID(LoginActivity.this, edt_account.getText().toString());
                UserConfigInfoUtils.setSaveAutoLoginState(LoginActivity.this, cb_autoLogin.isChecked());
                UserConfigInfoUtils.setSavePsdState(LoginActivity.this, cb_memorPass.isChecked());
                if (cb_memorPass.isChecked()) {
                    String pass = edt_password.getText().toString();
                    try {
                        UserConfigInfoUtils.setPassword(LoginActivity.this, PasswordUtils.lock(pass));
                    } catch (UnsupportedEncodingException | Base64DataException e) {
                        e.printStackTrace();
                    }
                } else
                    UserConfigInfoUtils.setPassword(LoginActivity.this, "");
                setResult(RESULT_OK);
                dialog.dismiss();
                PromptlyToast.getInstance(LoginActivity.this).show("登录成功!");
                Constants.isLogined = true;
                finish();
            }
        }.execute();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.activity_login_cb_automatic_login:
                if (isChecked)
                    cb_memorPass.setChecked(true);
                break;
            case R.id.activity_login_cb_memorization_pass:
                if (!isChecked)
                    cb_autoLogin.setChecked(false);
                break;
        }
    }


}


