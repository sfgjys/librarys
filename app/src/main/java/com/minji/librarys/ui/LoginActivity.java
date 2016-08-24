package com.minji.librarys.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.minji.librarys.R;
import com.minji.librarys.StringsFiled;
import com.minji.librarys.uitls.ViewsUitls;

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
                Intent mainActivity = new Intent(ViewsUitls.getContext(), MainActivity.class);
                startActivity(mainActivity);
                finish();
                break;
        }

    }

}
