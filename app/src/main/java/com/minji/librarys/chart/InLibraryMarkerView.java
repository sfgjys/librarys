package com.minji.librarys.chart;

import android.content.Context;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.minji.librarys.R;
import com.minji.librarys.base.MyBaseMarkerView;

/**
 * Created by user on 2016/9/29.
 */
public class InLibraryMarkerView extends MyBaseMarkerView {


    public InLibraryMarkerView(Context context, int layoutResource, String[] xValues) {
        super(context, layoutResource, xValues);
    }

    @Override
    public void refreshContent(Entry entry, Highlight highlight) {

        if (highlight.getDataSetIndex() == 1) {
            markerBottomText.setText("入馆: " + (int) entry.getVal() + "");
            markerImage.setImageResource(R.drawable.markerview_image1);
        }
        if (highlight.getDataSetIndex() == 0) {
            markerBottomText.setText("预约: " +(int) entry.getVal() + "");
            markerImage.setImageResource(R.drawable.markerview_image2);
        }
        markerTopText.setText(xValues[entry.getXIndex()]);


        System.out.println(entry.getXIndex() + "---------" + highlight.getDataSetIndex());


    }
}
