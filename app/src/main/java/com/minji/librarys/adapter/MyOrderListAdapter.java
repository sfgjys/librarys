package com.minji.librarys.adapter;

import com.minji.librarys.base.BaseHolder;
import com.minji.librarys.base.MyBaseAdapter;
import com.minji.librarys.bean.MyOrderListDetails;
import com.minji.librarys.holder.MyOrderListHolder;

import java.util.List;

/**
 * Created by user on 2016/9/9.
 */
public class MyOrderListAdapter extends MyBaseAdapter<MyOrderListDetails> {

    public MyOrderListAdapter(List<MyOrderListDetails> list) {
        super(list);
    }

    @Override
    public BaseHolder<MyOrderListDetails> getHolder() {
        return new MyOrderListHolder();
    }

    @Override
    public List<MyOrderListDetails> onLoadMore() {
        return null;
    }

    @Override
    public boolean hasMore() {
        return false;
    }
}
