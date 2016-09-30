package com.minji.librarys.base;

import android.content.Context;

import com.github.mikephil.charting.components.MarkerView;

/**
 * Created by user on 2016/9/29.
 */
public abstract class MyBaseMarkerView extends MarkerView {

    public MyBaseMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
    }

    @Override
    public int getXOffset(float xpos) {
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        return -(getHeight() + getHeight() / 3);
    }
}
