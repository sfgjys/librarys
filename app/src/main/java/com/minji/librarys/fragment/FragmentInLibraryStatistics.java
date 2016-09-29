package com.minji.librarys.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
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

        setChartStyle();


        mBarChart = (BarChart) inflate.findViewById(R.id.bar_chart_in_library_statistics);
        mBarChart.setNoDataText("我是柱状图");
        mBarChart.setNoDataTextDescription("我是第二行描述");

        mSelectLineChart = (ImageView) inflate.findViewById(R.id.iv_in_library_statistics_skip_broken_line);
        mSelectLineChart.setOnClickListener(this);
        mSelectBarChart = (ImageView) inflate.findViewById(R.id.iv_in_library_statistics_skip_columnar);
        mSelectBarChart.setOnClickListener(this);


        // TODO 获取网络数据，并处理后设置然后显示图表
        setChartDate();


    }

    private void setChartStyle() {

        // TODO 对图表样式进行设置
        // 图表描述字的设置
        mLineChart.setDescription("");//描述文字默认在图表右下角
        mLineChart.setDescriptionColor(0xffffffff);// 图表右下角的描述文字的颜色
        // mLineChart.setDescriptionPosition(0.5f, 0.5f);// 同过XY参数手动去设置描述文字所在位置，参数是像素
        // mLineChart.setDescriptionTypeface(Typeface.DEFAULT_BOLD);// 设置描述文字的 Typeface。文本样式
        mLineChart.setDescriptionTextSize(6f); // 设置描述文字大小，以像素为单位，最小6f，最大16f。

        // 网格的背景颜色(这个只设置网格的背景，不是整个图表的背景)
        mLineChart.setDrawGridBackground(false);// 这个是设置是对下面这个设置是否生效而言的
        mLineChart.setGridBackgroundColor(0xffffffff);// 设置图表中网格的背景颜色只用setDrawGridBackground为true该设置才能生效

        // 网格的边框
        mLineChart.setDrawBorders(false);  // 这个是设置是对下面两个设置是否生效而言的
        mLineChart.setBorderColor(0xff00ff00); //设置网格边框线的颜色。
        mLineChart.setBorderWidth(2f); // 设置网格边界线的宽度，单位 dp。


        // 图列设置
        Legend legend = mLineChart.getLegend();// 获取图列对象
        legend.setEnabled(false);// 设置Legend启用或禁用
        legend.setTextColor(Color.BLACK);// 设置图例标签文字的颜色。
        legend.setTextSize(10f);// 设置图例标签文字大小。
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);//设置图列位置
        legend.setForm(Legend.LegendForm.CIRCLE);// 设置图列前图案样式


        // X轴的设置
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
        xAxis.setEnabled(true);// 设置是否禁用X轴(包括一些相关的竖线)
        // 上面xAxis.setEnabled(false)代码设置了false,所以下面第一行即使设置为true也不会绘制AxisLine
//        xAxis.setDrawAxisLine(true);
        // 前面xAxis.setEnabled(false);则下面绘制的Grid不会有"竖的线"（与X轴有关）
        xAxis.setDrawGridLines(false); // 设置X轴上的相关竖线是否绘制
        xAxis.setDrawLabels(true); // 设置X轴上的标签文字是否绘制
        xAxis.setTypeface(Typeface.DEFAULT_BOLD); // 设置X轴上标签的字体
        xAxis.setTextColor(Color.BLACK);//设置X轴标签的颜色。
//        xAxis.setTextSize(24f);//设置X轴标签的文字大小。
//        xAxis.setGridLineWidth(10f);//设置X轴网格线的宽度。
        xAxis.setGridColor(ContextCompat.getColor(getActivity(), R.color.my_darker_gray));//设置X轴上网格线(竖线)颜色。
        xAxis.setAxisLineColor(Color.BLACK);//设置 X轴 轴线的颜色。
        xAxis.setAxisLineWidth(1f);// 设置 X轴 轴线的宽度。
        xAxis.enableGridDashedLine(10f, 10f, 0f);// 设置X轴上的网格线为虚线模式
//        xAxis.setSpaceBetweenLabels(1);//设置标签字符间的空隙，默认characters间隔是4 。(有可能根据屏幕宽度有一定关系)


        // Y轴的设置
        YAxis leftAxis = mLineChart.getAxisLeft();
        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setEnabled(false);// 设置最右边的Y轴不启用

//        leftAxis. setAxisMaxValue(float max) ;// 设置该轴的最大值。 如果设置了，这个值将不会是根据提供的数据计算出来的。
//        leftAxis. resetAxisMaxValue();// 调用此方法撤销先前设置的最大值。 通过这样做，你将再次允许轴自动计算出它的最大值。
        leftAxis.setAxisMinValue(0.0f);// 设置该轴的自定义最小值。 如果设置了，这个值将不会是根据提供的数据计算出来的。
//        leftAxis.resetAxisMinValue();// 调用此撤销先前设置的最小值。 通过这样做，你将再次允许轴自动计算它的最小值。
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setAxisLineColor(Color.BLACK);
        leftAxis.setTypeface(Typeface.DEFAULT_BOLD);
        leftAxis.setDrawGridLines(true);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setAxisLineWidth(1f);
//        leftAxis.setValueFormatter(new PercentFormatter());// 设置Y轴的标签值的格式(此处是%格式)


        // 触摸监听交互
        mLineChart.setTouchEnabled(true);   //启用/禁用与图表的所有可能的触摸交互。
        mLineChart.setDragEnabled(true);    // 缩放后是否可以拖动（平移）图表。
        mLineChart.setScaleEnabled(true);   // 图表上的两个轴是否可以进行缩放
        mLineChart.setScaleXEnabled(true);  //在x轴上是否可以进行缩放
        mLineChart.setScaleYEnabled(true);  // 在y轴是否可以进行缩放
        mLineChart.setPinchZoom(false);     // 设置图表x轴和y轴是否可以一起缩放(true)，还是可分别缩放(falses)。
        mLineChart.setDoubleTapToZoomEnabled(false);    // 设置为false以禁止通过在其上双击缩放图表。
        mLineChart.setHighlightPerDragEnabled(true); // 设置为true时,且图表已经缩小到最小时，在图表上进行拖动，吐出显示的值会随着拖动而改变。 默认值：true
        mLineChart.setHighlightPerTapEnabled(true);  // 设置Values(节点)被点击时是否会凸出显示(进行交互反应)   设置为false后值仍然可以通过拖动或编程方式突出显示。 默认值：true

        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                // entry可以获取X轴和Y轴对应的值  i代表的是哪组折线
                System.out.println("onValueSelected " + "i: " + i + " value: " + entry.getVal() + "-" + entry.getXIndex() + " highlight: " + highlight.getStackIndex());

            }

            @Override
            public void onNothingSelected() {
                System.out.println("onNothingSelected");
            }
        });
    }

    private void setChartDate() {

        // 该对象才是最后set进LineChart的最终数据源
        LineData lineData = new LineData();

//        BarData barData = new BarData();

        // 将10的预约人数和入馆人数数据分别存储在各自的集合中
        ArrayList<Entry> mOrderDates = new ArrayList<>();
        ArrayList<Entry> mInLibraryDates = new ArrayList<>();

//        ArrayList<BarEntry> mOrderDatesBar = new ArrayList<>();
//        ArrayList<BarEntry> mInLibraryDatesBar = new ArrayList<>();

        // TODO 这里的10需要实时获取
        for (int i = 0; i < 10; i++) {
            // 从数据源中获取数据并创建Entry对象并添加进各自的集合中
            mOrderDates.add(new Entry(textOrder[i], i));
            mInLibraryDates.add(new Entry(textInLibrary[i], i));

//            mOrderDatesBar.add(new BarEntry(textOrder[i], i));
//            mInLibraryDatesBar.add(new BarEntry(textInLibrary[i], i));

            // 添加X轴的标签文本
            lineData.addXValue(textTime[i]);

//            barData.addXValue(textTime[i]);
        }


        // 使用包含了数据的集合与图标文本描述创建LineDataSet对象
        LineDataSet mOrderLineDataSet = new LineDataSet(mOrderDates, "预约人数");
        mOrderLineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        mOrderLineDataSet.setLineWidth(2f);// 设置该组数据折线的线的宽度
        mOrderLineDataSet.setCircleRadius(3f);// 设置该组数据折线上的节点圆圈的大小
        mOrderLineDataSet.setDrawValues(false); // 设置是否在点上绘制Value
        mOrderLineDataSet.setHighLightColor(Color.TRANSPARENT); // 设置点击某个点时，横竖两条线的颜色
        mOrderLineDataSet.setColor(Color.BLACK);
        mOrderLineDataSet.setCircleColor(Color.BLACK);


        LineDataSet mInLibraryLineDataSet = new LineDataSet(mInLibraryDates, "入馆人数");
        mInLibraryLineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        mInLibraryLineDataSet.setLineWidth(2f);// 设置该组数据折线的线的宽度
        mInLibraryLineDataSet.setCircleRadius(3f);// 设置该组数据折线上的节点圆圈的大小
        mInLibraryLineDataSet.setDrawValues(false); // 设置是否在点上绘制Value
        mInLibraryLineDataSet.setHighLightColor(Color.TRANSPARENT); // 设置点击某个点时，横竖两条线的颜色
        mInLibraryLineDataSet.setColor(Color.RED);
        mInLibraryLineDataSet.setCircleColor(Color.RED);


//        BarDataSet mOrderLineDataSetBar = new BarDataSet(mOrderDatesBar, "预约人数");
//        mOrderLineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
//        BarDataSet mInLibraryLineDataSetBar = new BarDataSet(mInLibraryDatesBar, "入馆人数");
//        mInLibraryLineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);


        // 添加两组包含10个Y轴数据集合的对象实例
        lineData.addDataSet(mOrderLineDataSet);
        lineData.addDataSet(mInLibraryLineDataSet);

//        barData.addDataSet(mInLibraryLineDataSetBar);
//        barData.addDataSet(mOrderLineDataSetBar);

        mLineChart.setData(lineData);
        mLineChart.invalidate();

//        mBarChart.setData(barData);
//        mBarChart.invalidate();

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
