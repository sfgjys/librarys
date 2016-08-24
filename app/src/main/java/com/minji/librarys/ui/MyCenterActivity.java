package com.minji.librarys.ui;

import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.minji.librarys.R;
import com.minji.librarys.base.BaseActivity;
import com.minji.librarys.fragment.FragmentMyCenterNormal;
import com.minji.librarys.fragment.FragmentMyCenterUpdateInformation;

/**
 * Created by user on 2016/8/23.
 */
public class MyCenterActivity extends BaseActivity {

    private FragmentMyCenterNormal mFragmentMyCenterNormal;
    private FragmentMyCenterUpdateInformation mFragmentMyCenterUpdate;

    @Override
    public void onCreateContent() {

        setBackVisibility(View.VISIBLE);
        setSettingVisibility(View.GONE);
        setTitleVisibility(View.VISIBLE);
        setTitleImageVisibility(View.GONE);

        View view = setContent(R.layout.layout_my_center);

        showBottomFragment();


    }

    public void showButtomFragmentNormal() {
        getSupportFragmentManager().beginTransaction().show(mFragmentMyCenterNormal).hide(mFragmentMyCenterUpdate).commit();
    }

    public void showButtomFragmentUpdate() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mFragmentMyCenterUpdate == null) {
            mFragmentMyCenterUpdate = new FragmentMyCenterUpdateInformation();
            fragmentTransaction.hide(mFragmentMyCenterNormal).add(R.id.fl_my_center_bottom_part_contents, mFragmentMyCenterUpdate, "bottom_update").commit();
        } else {
            fragmentTransaction.hide(mFragmentMyCenterNormal).show(mFragmentMyCenterUpdate).commit();
        }
    }

    private void showBottomFragment() {
        if (savedInstanceState == null) {// 第一次进入该页面
            mFragmentMyCenterNormal = new FragmentMyCenterNormal();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_my_center_bottom_part_contents, mFragmentMyCenterNormal, "bottom_normal").commit();
        } else {// 已经进入过该页面
            // 该FragmentMyCenterNormal是第一次进入时new的对象，只是获取出来了
            FragmentMyCenterNormal fragmentMyCenterNormal = (FragmentMyCenterNormal) getSupportFragmentManager().findFragmentByTag("bottom_normal");
            FragmentMyCenterUpdateInformation fragmentMyCenterUpdateInformation = (FragmentMyCenterUpdateInformation) getSupportFragmentManager().findFragmentByTag("bottom_update");
            if (fragmentMyCenterNormal != null) {
                getSupportFragmentManager().beginTransaction().show(fragmentMyCenterNormal);
                mFragmentMyCenterNormal = fragmentMyCenterNormal;
            } else {
                mFragmentMyCenterNormal = new FragmentMyCenterNormal();
                getSupportFragmentManager().beginTransaction().add(R.id.fl_my_center_bottom_part_contents, mFragmentMyCenterNormal, "bottom_normal").commit();
            }
            if (fragmentMyCenterUpdateInformation != null) {
                getSupportFragmentManager().beginTransaction().hide(fragmentMyCenterUpdateInformation);
                mFragmentMyCenterUpdate = fragmentMyCenterUpdateInformation;
            }
        }
    }

    // 重写finish方法
    @Override
    public void finish() {
        if (mFragmentMyCenterNormal != null && mFragmentMyCenterNormal.isHidden()) {
            showButtomFragmentNormal();
        } else {
            super.finish();
        }
    }
}
