package com.minji.librarys.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.minji.librarys.R;
import com.minji.librarys.StringsFiled;
import com.minji.librarys.http.OkHttpManger;
import com.minji.librarys.uitls.SharedPreferencesUtil;
import com.minji.librarys.uitls.StringUtils;
import com.minji.librarys.uitls.ToastUtil;
import com.minji.librarys.uitls.ViewsUitls;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends FragmentActivity implements View.OnClickListener {

    private EditText mEtUser;
    private EditText mEtPassWord;

    private TextView mTvRegister;
    private TextView mTvForget;

    private CheckBox mRbRemember;

    private Button mBLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        SharedPreferencesUtil.saveStirng(this, StringsFiled.CLIENTID, "");
        System.out.println("===================");
        PushManager.getInstance().initialize(this.getApplicationContext());

        initView();
    }

    private void initView() {
        mEtPassWord = (EditText) findViewById(R.id.et_login_password);
        mEtUser = (EditText) findViewById(R.id.et_login_user);
        mRbRemember = (CheckBox) findViewById(R.id.cb_login_remember_password);

        mTvRegister = (TextView) findViewById(R.id.tv_login_register_user);
        mTvRegister.setOnClickListener(this);
        mTvForget = (TextView) findViewById(R.id.tv_login_forget_password);
        mTvForget.setOnClickListener(this);
        mBLogin = (Button) findViewById(R.id.bt_login_button);
        mBLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        Intent registerAndForget = new Intent(ViewsUitls.getContext(), RegisterAndForgetActivity.class);

        switch (v.getId()) {
            case R.id.tv_login_register_user:
                registerAndForget.putExtra(StringsFiled.DISTINGUISH_REGISTER_FORGET, StringsFiled.REGISTER_FRAGMENT);
                startActivity(registerAndForget);
                break;
            case R.id.tv_login_forget_password:
                registerAndForget.putExtra(StringsFiled.DISTINGUISH_REGISTER_FORGET, StringsFiled.FORGET_FRAGMENT);
                startActivity(registerAndForget);
                break;
            case R.id.bt_login_button:

                String userName = mEtUser.getText().toString().trim();
                String passWord = mEtPassWord.getText().toString().trim();
                String cid = SharedPreferencesUtil.getString(this, StringsFiled.CLIENTID, "");

                if (!StringUtils.isEmpty(cid)) {


                    if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(passWord)) {
                        System.out.println("userName: " + userName + "  passWord: " + passWord + "  cid: " + cid);

                        OkHttpClient okHttpClient = OkHttpManger.getInstance().getOkHttpClient();
                        RequestBody formBody = new FormBody.Builder()
                                .add("username", userName).add("password", passWord).add("cid", userName).add("rememberMe", "false")
                                .build();
                        Request request = new Request.Builder()
                                .url("http://192.168.1.40:8080/library-seat/user/ajaxlogin")
                                .post(formBody)
                                .build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                System.out.println("=========================onFailure=============================");
                                ViewsUitls.runInMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.showToast(LoginActivity.this, "服务器正忙,请稍候");
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String result = response.body().string().toString();
                                System.out.println(result + "      +++++++++++++++++++++++++++++++++");
                                ViewsUitls.runInMainThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            JSONObject jsonObject = new JSONObject(result);
                                            String message = jsonObject.optString("message");
                                            if (result.contains("true")) {
                                                SharedPreferencesUtil.saveStirng(getApplicationContext(), StringsFiled.USERID, jsonObject.optString("userId"));
                                                Intent mainActivity = new Intent(ViewsUitls.getContext(), MainActivity.class);
                                                startActivity(mainActivity);
                                                finish();
                                            }
                                            ToastUtil.showToast(LoginActivity.this, message);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        });

                    } else {
                        ToastUtil.showToast(this, "账户密码不可为空");
                    }
                } else {
                    ToastUtil.showToast(this, "id:0x000000");
                }

                break;
        }

    }

}
