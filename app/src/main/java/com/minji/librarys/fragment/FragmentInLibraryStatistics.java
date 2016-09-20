package com.minji.librarys.fragment;

import android.view.View;

import com.minji.librarys.base.BaseFragment;
import com.minji.librarys.base.ContentPage;

/**
 * Created by user on 2016/9/20.
 */
public class FragmentInLibraryStatistics extends BaseFragment {
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
        return chat(null);
    }
}
