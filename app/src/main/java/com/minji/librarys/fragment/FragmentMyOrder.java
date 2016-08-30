package com.minji.librarys.fragment;

import android.view.View;

import com.minji.librarys.base.BaseFragment;
import com.minji.librarys.base.ContentPage;

import java.util.ArrayList;

/**
 * Created by user on 2016/8/29.
 */
public class FragmentMyOrder extends BaseFragment {
    @Override
    protected void onSubClassOnCreateView() {
        loadDataAndRefresh();
    }

    @Override
    protected View onCreateSuccessView() {
        return null;
    }

    @Override
    protected ContentPage.ResultState onLoad() {
        ArrayList<Object> objects = new ArrayList<>();

        return chat(objects);
    }
}
