package com.minji.librarys.ui;

import android.view.View;

import com.minji.librarys.R;
import com.minji.librarys.base.BaseActivity;
import com.minji.librarys.fragment.FragmentIntegralDetail;
import com.minji.librarys.fragment.FragmentStatementDetail;
import com.minji.librarys.fragment.FragmentUpdateInformation;
import com.minji.librarys.fragment.FragmentMyOrder;
import com.minji.librarys.fragment.FragmentWindowInfromation;

/**
 * Created by user on 2016/8/29.
 */
public class IntegralAndOrderOrStatementActivity extends BaseActivity {

    @Override
    public void onCreateContent() {

        setBackVisibility(View.VISIBLE);
        setSettingVisibility(View.GONE);
        setTitleVisibility(View.VISIBLE);
        setTitleImageVisibility(View.GONE);

        if (stringTitle.equals("积分明细")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_base_content, new FragmentIntegralDetail()).commit();
        }
        if (stringTitle.equals("我的预约")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_base_content, new FragmentMyOrder()).commit();
        }
        if (stringTitle.equals("我的资料")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_base_content, new FragmentUpdateInformation()).commit();
        }
        if (stringTitle.equals("系统消息")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_base_content, new FragmentWindowInfromation()).commit();
        }
        if (stringTitle.equals("报表统计")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_base_content, new FragmentStatementDetail()).commit();
        }
    }
}
