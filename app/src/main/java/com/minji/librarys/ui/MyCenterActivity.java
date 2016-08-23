package com.minji.librarys.ui;

import android.view.View;

import com.minji.librarys.R;
import com.minji.librarys.base.BaseActivity;

/**
 * Created by user on 2016/8/23.
 */
public class MyCenterActivity extends BaseActivity {
    @Override
    public void onCreateContent() {

        setBackVisibility(View.VISIBLE);
        setSettingVisibility(View.GONE);
        setTitleVisibility(View.VISIBLE);
        setTitleImageVisibility(View.GONE);

        View view = setContent(R.layout.layout_my_center);
    }
}
