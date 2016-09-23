package com.minji.librarys.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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

    private String[] textTime = {"09/02", "09/03", "09/04", "09/05", "09/06", "09/08", "09/09", "09/22", "09/23", "09/28",};
    private float[] textInLibrary = {0.0f, 1.0f, 2.0f, 4.0f, 2.0f, 7.0f, 4.0f, 5.0f, 6.0f, 2.0f,};
    private float[] textOrder = {2.0f, 3.0f, 3.0f, 1.0f, 8.0f, 3.0f, 4.0f, 1.0f, 8.0f, 3.0f,};

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


        // TODO 获取网络数据，并处理后设置然后显示图表
        setChartDate();


    }

    private void setChartDate() {

        // 该对象才是最后set进LineChart的最终数据源
        LineData lineData = new LineData();

        BarData barData = new BarData();

        // 将10的预约人数和入馆人数数据分别存储在各自的集合中
        ArrayList<Entry> mOrderDates = new ArrayList<>();
        ArrayList<Entry> mInLibraryDates = new ArrayList<>();

        ArrayList<BarEntry> mOrderDatesBar = new ArrayList<>();
        ArrayList<BarEntry> mInLibraryDatesBar = new ArrayList<>();

        // TODO 这里的10需要实时获取
        for (int i = 0; i < 10; i++) {
            // 从数据源中获取数据并创建Entry对象并添加进各自的集合中
            mOrderDates.add(new Entry(textOrder[i], i));
            mInLibraryDates.add(new Entry(textInLibrary[i], i));

            mOrderDatesBar.add(new BarEntry(textOrder[i], i));
            mInLibraryDatesBar.add(new BarEntry(textInLibrary[i], i));

            // 添加X轴的标签文本
            lineData.addXValue(textTime[i]);

            barData.addXValue(textTime[i]);
        }


        // 使用包含了数据的集合与图标文本描述创建LineDataSet对象
        LineDataSet mOrderLineDataSet = new LineDataSet(mOrderDates, "预约人数");
        mOrderLineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet mInLibraryLineDataSet = new LineDataSet(mInLibraryDates, "入馆人数");
        mInLibraryLineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet mOrderLineDataSetBar = new BarDataSet(mOrderDatesBar, "预约人数");
        mOrderLineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        BarDataSet mInLibraryLineDataSetBar = new BarDataSet(mInLibraryDatesBar, "入馆人数");
        mInLibraryLineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);


        // 添加两组包含10个Y轴数据集合的对象实例
        lineData.addDataSet(mOrderLineDataSet);
        lineData.addDataSet(mInLibraryLineDataSet);

        barData.addDataSet(mInLibraryLineDataSetBar);
        barData.addDataSet(mOrderLineDataSetBar);

        mLineChart.setData(lineData);
        mLineChart.invalidate();

        mBarChart.setData(barData);
        mBarChart.invalidate();

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
