package com.minji.librarys.adapter;

import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minji.librarys.R;
import com.minji.librarys.bean.ReadingRoomSeats;
import com.minji.librarys.uitls.ViewsUitls;

import java.util.ArrayList;

/**
 * Created by user on 2016/8/22.
 */
public class ReadingRoomAdapter extends BaseAdapter {

    private final ArrayList<ReadingRoomSeats> readingRoomSeatses;

    public ReadingRoomAdapter(ArrayList<ReadingRoomSeats> readingRoomSeatses) {
        this.readingRoomSeatses = readingRoomSeatses;
    }

    @Override
    public int getCount() {
        return readingRoomSeatses.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = new TextView(ViewsUitls.getContext());
        textView.setTextColor(ContextCompat.getColor(ViewsUitls.getContext(), R.color.white));
        textView.setBackgroundResource(R.drawable.shape_select_area_reading_room_seat_state);
        textView.setPadding(ViewsUitls.dptopx(6), ViewsUitls.dptopx(6), ViewsUitls.dptopx(6), ViewsUitls.dptopx(6));
        textView.setText(readingRoomSeatses.get(position).getSeatsNumber());
        TextPaint textPaint = textView.getPaint();
        textPaint.setFakeBoldText(true);

        textView.setGravity(Gravity.CENTER);


        return textView;
    }
}
