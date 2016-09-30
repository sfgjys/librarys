package com.minji.librarys.chart;

import android.content.Context;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.minji.librarys.base.MyBaseMarkerView;

/**
 * Created by user on 2016/9/29.
 */
public class BaseMarkerView extends MyBaseMarkerView {

    public BaseMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
    }

    @Override
    public void refreshContent(Entry entry, Highlight highlight) {

    }
}
