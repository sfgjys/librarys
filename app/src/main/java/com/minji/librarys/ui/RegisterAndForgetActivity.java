package com.minji.librarys.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minji.librarys.R;
import com.minji.librarys.StringsFiled;
import com.minji.librarys.base.BaseActivity;
import com.minji.librarys.fragment.FragmentPhoneOrEmailFind;

public class RegisterAndForgetActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_provisions_of_height;
    private Button mGetIdentifyingCode;
    private TextView mEmailFind;
    private TextView mPhoneFind;

    private int isClickEmailFind = 0;

    @Override
    public void onCreateContent() {

        setBackVisibility(View.GONE);
        setSettingVisibility(View.GONE);
        setTitleVisibility(View.GONE);
        setTitleImageVisibility(View.VISIBLE);


        Intent intent = getIntent();
        // 具体加载哪个界面
        String distinguishRegisterForget = intent.getStringExtra(StringsFiled.DISTINGUISH_REGISTER_FORGET);
        if (StringsFiled.REGISTER_FRAGMENT.equals(distinguishRegisterForget)) {
            // 注册界面
            setRegisterContent();
        } else if (StringsFiled.FORGET_FRAGMENT.equals(distinguishRegisterForget)) {
            // 密码找回界面
            setForgetContent();
            startForgetContent(StringsFiled.EMAILORFORGETVALUE_PHONE);
        }

    }

    // 开启找回密码具体的Fragment
    private void startForgetContent(String emailOrForget) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString(StringsFiled.EMAILORFORGET, emailOrForget);

        FragmentPhoneOrEmailFind fragmentPhoneOrEmailFind = new FragmentPhoneOrEmailFind();
        fragmentPhoneOrEmailFind.setArguments(bundle);

        fragmentTransaction.replace(R.id.fl_forget_email_or_phone, fragmentPhoneOrEmailFind);
        fragmentTransaction.commit();
    }

    // 密码找回界面
    private void setForgetContent() {
        View mViewForget = setContent(R.layout.layout_forget);
        mEmailFind = (TextView) mViewForget.findViewById(R.id.tv_froget_tab_email_find);
        mPhoneFind = (TextView) mViewForget.findViewById(R.id.tv_froget_tab_phone_find);
        mEmailFind.setOnClickListener(this);
        mPhoneFind.setOnClickListener(this);
    }

    // 注册界面
    private void setRegisterContent() {
        View mViewRegister = setContent(R.layout.layout_register);
        ll_provisions_of_height = (LinearLayout) mViewRegister.findViewById(R.id.ll_provisions_of_height);
        mGetIdentifyingCode = (Button) mViewRegister.findViewById(R.id.bt_register_identifying_code_button);
        amendButtonHeight(mViewRegister);
    }


    /*
    *  alpha: email文字颜色
    *  alpha2：phone文字颜色
    *  tabs_background：email的tab背景
    *  thin_green：phone的tab背景
    * */
    private void clickTabs(int alpha, int tabs_background, int alpha2, int thin_green) {
        mEmailFind.setTextColor(ContextCompat.getColor(RegisterAndForgetActivity.this, alpha));
        mEmailFind.setBackgroundResource(tabs_background);
        mPhoneFind.setTextColor(ContextCompat.getColor(RegisterAndForgetActivity.this, alpha2));
        mPhoneFind.setBackgroundResource(thin_green);
    }

    /**
     * 使用代码修改控件的高度
     */
    private void amendButtonHeight(View mViewRegister) {
        if (ll_provisions_of_height != null && mGetIdentifyingCode != null) {
            ViewTreeObserver viewTreeObserver = ll_provisions_of_height.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ll_provisions_of_height.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    ViewGroup.LayoutParams layoutParams = mGetIdentifyingCode.getLayoutParams();
                    layoutParams.height = ll_provisions_of_height.getHeight();
                    mGetIdentifyingCode.setLayoutParams(layoutParams);
                }
            });
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_froget_tab_email_find:
                isClickEmailFind++;
                if (isClickEmailFind == 1) {
                    clickTabs(R.color.thin_green, R.drawable.tabs_background_press, R.color.white, R.drawable.tabs_background_default);
                    startForgetContent(StringsFiled.EMAILORFORGETVALUE_EMAIL);
                }
                break;
            case R.id.tv_froget_tab_phone_find:
                if (isClickEmailFind != 0) {
                    clickTabs(R.color.white, R.drawable.tabs_background_default, R.color.thin_green, R.drawable.tabs_background_press);
                    startForgetContent(StringsFiled.EMAILORFORGETVALUE_PHONE);
                    isClickEmailFind = 0;
                }
                break;
        }

    }
}
