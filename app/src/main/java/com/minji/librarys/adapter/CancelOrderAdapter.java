package com.minji.librarys.adapter;

import com.minji.librarys.base.BaseHolder;
import com.minji.librarys.base.MyBaseAdapter;
import com.minji.librarys.bean.CancelOrderDetails;
import com.minji.librarys.holder.CancelOrderHolder;

import java.util.List;

/**
 * Created by user on 2016/9/9.
 */
public class CancelOrderAdapter extends MyBaseAdapter<CancelOrderDetails> {

    public CancelOrderAdapter(List<CancelOrderDetails> list) {
        super(list);
    }

    @Override
    public BaseHolder<CancelOrderDetails> getHolder() {
        return new CancelOrderHolder();
    }

    @Override
    public List<CancelOrderDetails> onLoadMore() {
        return null;
    }

    @Override
    public boolean hasMore() {
        return false;
    }
}
