package com.minji.librarys.fragment;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.minji.librarys.R;
import com.minji.librarys.adapter.ReadingRoomAdapter;
import com.minji.librarys.base.BaseFragment;
import com.minji.librarys.base.ContentPage;
import com.minji.librarys.bean.ReadingRoomSeats;
import com.minji.librarys.uitls.ViewsUitls;

import java.util.ArrayList;

/**
 * Created by user on 2016/8/22.
 */
public class FragmentReadingRoom extends BaseFragment<ReadingRoomSeats> implements View.OnClickListener {


    private ArrayList<ReadingRoomSeats> readingRoomSeatses;
    private Window orderSeatWindow;
    private AlertDialog mOrderAlertDialog;

    @Override
    protected void onSubClassOnCreateView() {
        loadDataAndRefresh();
    }

    @Override
    protected ContentPage.ResultState onLoad() {

        readingRoomSeatses = new ArrayList<>();

        for (int i = 0; i < 54; i++) {
            readingRoomSeatses.add(new ReadingRoomSeats("" + i, "2020"));
        }

        return chat(readingRoomSeatses);
    }

    @Override
    protected View onCreateSuccessView() {

        View view = ViewsUitls.inflate(R.layout.layout_reading_room_seats);

        GridView gridView = (GridView) view.findViewById(R.id.gv_raeding_room_seats);

        gridView.setOverScrollMode(View.OVER_SCROLL_NEVER);// 取消顶部阴影
        gridView.setAdapter(new ReadingRoomAdapter(readingRoomSeatses));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showReadingRoomOrderSeatDialog();
            }
        });

        return view;
    }


    private void showReadingRoomOrderSeatDialog() {
        mOrderAlertDialog = new AlertDialog.Builder(getActivity()).create();
        mOrderAlertDialog.setView(new EditText(ViewsUitls.getContext()));// 为了让对话框内的输入框可以使用？
        WindowManager.LayoutParams attributes = mOrderAlertDialog.getWindow().getAttributes();// 获取对话框的属性集
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        attributes.width = (int) (d.getWidth() * 0.9);
        mOrderAlertDialog.show();
        // 设置对话框中自定义内容
        orderSeatWindow = mOrderAlertDialog.getWindow();
        orderSeatWindow.getDecorView().setBackgroundColor(Color.TRANSPARENT);// 获取对话框的根布局，设置其背景为透明，自定义对话框就没有白色的边框了
//        window.setBackgroundDrawableResource(R.drawable.shape_reading_seat_order_dialog_background_all);
        orderSeatWindow.setContentView(R.layout.dialog_select_reading_room_seat_order);

        initDialogView();

    }

    private void initDialogView() {
        ImageView mImageDialogCancel = (ImageView) orderSeatWindow.findViewById(R.id.iv_order_seat_dialog_cancel);
        mImageDialogCancel.setOnClickListener(this);
        EditText mStartTime = (EditText) orderSeatWindow.findViewById(R.id.et_order_seat_dialog_selelct_start_time);
        mStartTime.setOnClickListener(this);
        TextView mCancelOrder = (TextView) orderSeatWindow.findViewById(R.id.tv_order_seat_dialog_cancel_order);
        mCancelOrder.setOnClickListener(this);
        TextView mMakeSureOrder = (TextView) orderSeatWindow.findViewById(R.id.tv_order_seat_dialog_make_sure_order);
        mMakeSureOrder.setOnClickListener(this);
        TextView mOrderPeople = (TextView) orderSeatWindow.findViewById(R.id.tv_order_seat_dialog_people);
        TextView mPeopleNumber = (TextView) orderSeatWindow.findViewById(R.id.tv_order_seat_dialog_number);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_order_seat_dialog_cancel:
            case R.id.tv_order_seat_dialog_cancel_order:
                // 上面两个case都是取消
                mOrderAlertDialog.cancel();
                break;
            case R.id.et_order_seat_dialog_selelct_start_time:
                System.out.println("设置时间");
                break;
            case R.id.tv_order_seat_dialog_make_sure_order:
                System.out.println("请求网络预约");
                break;
        }
    }
}
