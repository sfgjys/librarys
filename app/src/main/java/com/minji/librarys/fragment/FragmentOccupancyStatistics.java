package com.minji.librarys.fragment;

import android.view.View;

import com.minji.librarys.R;
import com.minji.librarys.base.BaseFragment;
import com.minji.librarys.base.ContentPage;
import com.minji.librarys.uitls.ViewsUitls;

import java.util.ArrayList;

/**
 * Created by user on 2016/9/22.
 */
public class FragmentOccupancyStatistics extends BaseFragment {
    @Override
    protected void onSubClassOnCreateView() {
        loadDataAndRefresh();
    }

    @Override
    protected View onCreateSuccessView() {

        View inflate = ViewsUitls.inflate(R.layout.layout_occupancy_statistics);


        return inflate;
    }

    @Override
    protected ContentPage.ResultState onLoad() {
        // 为了进入onCreateSuccessView()方法
        ArrayList<String> list = new ArrayList<>();
        list.add("list");
        return chat(list);
    }
}