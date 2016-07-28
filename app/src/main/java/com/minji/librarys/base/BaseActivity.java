package com.minji.librarys.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.minji.librarys.R;

public abstract class BaseActivity extends FragmentActivity {

    private ImageView mBaseTitleImage;
    private ImageView mBaseBack;
    private ImageView mBaseSetting;

    private TextView mBaseTitle;

    private FrameLayout mBaseContetn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        initView();

        onCreateContent();

    }

    public abstract void onCreateContent();

    private void initView() {
        mBaseTitleImage = (ImageView) findViewById(R.id.iv_title_image);
        mBaseBack = (ImageView) findViewById(R.id.iv_title_back);
        mBaseSetting = (ImageView) findViewById(R.id.iv_title_setting);
        mBaseTitle = (TextView) findViewById(R.id.tv_base_title);
        mBaseContetn = (FrameLayout) findViewById(R.id.fl_base_content);
    }

    public void setBackVisibility(int visibility) {
        mBaseBack.setVisibility(visibility);
    }

    public void setTitleImageVisibility(int visibility) {
        mBaseTitleImage.setVisibility(visibility);
    }


    public void setTitleVisibility(int visibility) {
        mBaseTitle.setVisibility(visibility);
    }

    public void setSettingVisibility(int visibility) {
        mBaseSetting.setVisibility(visibility);
    }


    /**
     * 首先将一个xml布局打气压缩成一个View，在将该View添加到Framelayout中
     */
    public View setContent(int id) {
        View inflate = View.inflate(MyApplication.getContext(), id, null);
        mBaseContetn.addView(inflate);
        return inflate;
    }


}
