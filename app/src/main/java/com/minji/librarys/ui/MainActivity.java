package com.minji.librarys.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.minji.librarys.R;
import com.minji.librarys.StringsFiled;
import com.minji.librarys.base.BaseActivity;
import com.minji.librarys.uitls.SystemTime;
import com.minji.librarys.uitls.ViewsUitls;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public void onCreateContent() {

        setBackVisibility(View.GONE);
        setSettingVisibility(View.GONE);
        setTitleVisibility(View.GONE);
        setTitleImageVisibility(View.VISIBLE);

        View view = setContent(R.layout.activity_main);

        initChildView(view);

    }

    private void initChildView(View view) {
        view.findViewById(R.id.mi_main_outside_reservation).setOnClickListener(this);
        view.findViewById(R.id.mi_main_booking_rules).setOnClickListener(this);
        view.findViewById(R.id.mi_main_me_center).setOnClickListener(this);
        view.findViewById(R.id.mi_main_serach_seat).setOnClickListener(this);
        view.findViewById(R.id.mi_main_stadium_seats).setOnClickListener(this);
        view.findViewById(R.id.mi_title_setting).setOnClickListener(this);
        TextView mTimeNumber = (TextView) view.findViewById(R.id.tv_main_item_tiem_number);
        mTimeNumber.setText(SystemTime.getTimer());
        TextView mWeek = (TextView) view.findViewById(R.id.tv_main_item_tiem_week);
        mWeek.setText(SystemTime.getTimerWeek());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mi_main_outside_reservation:
                Intent selectReading = new Intent(ViewsUitls.getContext(), SelectAreasActivity.class);
                selectReading.putExtra(StringsFiled.ACTIVITY_TITLE, "选择阅览室");
                startActivity(selectReading);
                break;
            case R.id.mi_main_me_center:
                Intent myCenter = new Intent(ViewsUitls.getContext(), MyCenterActivity.class);
                myCenter.putExtra(StringsFiled.ACTIVITY_TITLE, "个人中心");
                startActivity(myCenter);
                break;
            case R.id.mi_main_serach_seat:
                break;
            case R.id.mi_main_booking_rules:
                break;
            case R.id.mi_main_stadium_seats:
                break;
            case R.id.mi_title_setting:
                break;
        }

    }
}
