package com.minji.librarys.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.minji.librarys.R;
import com.minji.librarys.base.BaseFragment;
import com.minji.librarys.base.ContentPage;
import com.minji.librarys.uitls.ViewsUitls;

import java.util.ArrayList;

/**
 * Created by user on 2016/9/20.
 */
public class FragmentInLibraryStatistics extends BaseFragment implements View.OnClickListener {

    private View inflate;
    private EditText mInputStartDay;
    private Button mStartDaySearch;
    private LineChart mLineChart;
    private BarChart mBarChart;
    private ImageView mSelectLineChart;
    private ImageView mSelectBarChart;

    private int WHAT_CHART_NO_PRESSED = 2;// 一进入本页面就是柱状没被选中
    private int LINE_CHART_NO_PRESSED = 1;// 此为折线没被选中
    private int BAR_CHART_NO_PRESSED = 2;//  此为柱状没被选中


    @Override
    protected void onSubClassOnCreateView() {
        loadDataAndRefresh();
    }

    @Override
    protected View onCreateSuccessView() {

        inflate = ViewsUitls.inflate(R.layout.layout_in_library_statistics);

        initInflateView();


        return inflate;
    }

    private void initInflateView() {
        mInputStartDay = (EditText) inflate.findViewById(R.id.et_in_library_statistics_start_day_input);
        mStartDaySearch = (Button) inflate.findViewById(R.id.bt_in_library_statistics_start_search);
        amendButtonHeight();

        mLineChart = (LineChart) inflate.findViewById(R.id.line_chart_in_library_statistics);
        mLineChart.setNoDataText("我是折线图");
        mBarChart = (BarChart) inflate.findViewById(R.id.bar_chart_in_library_statistics);
        mBarChart.setNoDataText("我是柱状图");

        mSelectLineChart = (ImageView) inflate.findViewById(R.id.iv_in_library_statistics_skip_broken_line);
        mSelectLineChart.setOnClickListener(this);
        mSelectBarChart = (ImageView) inflate.findViewById(R.id.iv_in_library_statistics_skip_columnar);
        mSelectBarChart.setOnClickListener(this);




    }


    @Override
    protected ContentPage.ResultState onLoad() {
        // 为了进入onCreateSuccessView()方法
        ArrayList<String> list = new ArrayList<>();
        list.add("list");
        return chat(list);
    }

    /**
     * 使用代码修改控件的高度
     */
    private void amendButtonHeight() {
        if (mStartDaySearch != null && mInputStartDay != null) {
            ViewTreeObserver viewTreeObserver = mInputStartDay.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mInputStartDay.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    ViewGroup.LayoutParams layoutParams = mStartDaySearch.getLayoutParams();
                    layoutParams.height = mInputStartDay.getHeight();
                    mStartDaySearch.setLayoutParams(layoutParams);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_in_library_statistics_skip_broken_line:
                if (WHAT_CHART_NO_PRESSED == LINE_CHART_NO_PRESSED) {// 当折线没被选中时点击图标才有用
                    System.out.println("折线被选中");

                    mSelectBarChart.setImageResource(R.mipmap.select_columnar_default);
                    mSelectLineChart.setImageResource(R.mipmap.select_broken_line_pressed);
                    mLineChart.setVisibility(View.VISIBLE);
                    mBarChart.setVisibility(View.GONE);

                    WHAT_CHART_NO_PRESSED = BAR_CHART_NO_PRESSED;
                }
                break;
            case R.id.iv_in_library_statistics_skip_columnar:
                if (WHAT_CHART_NO_PRESSED == BAR_CHART_NO_PRESSED) {// 当柱状没被选中时点击图标才有用
                    System.out.println("柱状被选中");

                    mSelectBarChart.setImageResource(R.mipmap.select_columnar_pressed);
                    mSelectLineChart.setImageResource(R.mipmap.select_broken_line_default);
                    mLineChart.setVisibility(View.GONE);
                    mBarChart.setVisibility(View.VISIBLE);

                    WHAT_CHART_NO_PRESSED = LINE_CHART_NO_PRESSED;
                }
                break;
        }

    }
}
