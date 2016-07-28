package com.minji.librarys.ui;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;

import com.minji.librarys.R;
import com.minji.librarys.StringsFiled;
import com.minji.librarys.base.BaseActivity;

public class RegisterAndForgetActivity extends BaseActivity {

    private LinearLayout ll_provisions_of_height;
    private Button mGetIdentifyingCode;

    @Override
    public void onCreateContent() {

        setBackVisibility(View.GONE);
        setSettingVisibility(View.GONE);
        setTitleVisibility(View.GONE);
        setTitleImageVisibility(View.VISIBLE);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Intent intent = getIntent();
        String distinguishRegisterForget = intent.getStringExtra(StringsFiled.DISTINGUISH_REGISTER_FORGET);
        if (StringsFiled.REGISTER_FRAGMENT.equals(distinguishRegisterForget)) {
            View mViewRegister = setContent(R.layout.layout_register);
            ll_provisions_of_height = (LinearLayout) mViewRegister.findViewById(R.id.ll_provisions_of_height);
            mGetIdentifyingCode = (Button) mViewRegister.findViewById(R.id.bt_register_identifying_code_button);
            amendButtonHeight(mViewRegister);

        } else if (StringsFiled.FORGET_FRAGMENT.equals(distinguishRegisterForget)) {
            View mViewForget = setContent(R.layout.layout_forget);
        }


    }

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


}
