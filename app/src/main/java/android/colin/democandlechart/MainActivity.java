package android.colin.democandlechart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = "qqq";
    private CombinedChart mChart;
    private LineChart lineChart;
    private BarChart barChart;
    private Button btn;
    private int itemcount;
    private int linechartcount;
    private LineData lineData;
    private CandleData candleData;
    private CombinedData combinedData;
    private ArrayList<String> xVals;
    private List<CandleEntry> candleEntries = new ArrayList<>();
    private int colorHomeBg;
    private int colorLine;
    private int colorText;
    private int colorMa5;
    private int colorMa10;
    private int colorMa20;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kline);

        mChart = (CombinedChart) findViewById(R.id.kline_chart);      //http://money18.on.cc/chartdata/d1/price/02318_price_d1.txt
        lineChart = (LineChart) findViewById(R.id.lchart);       //http://money18.on.cc/chartdata/full/price/00700_price_full.txt
        barChart = (BarChart) findViewById(R.id.bchart);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://money18.on.cc/chartdata/full/price/00700_price_full.txt",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //System.out.println("-----Main setdata-----:"+response);
                        Model.setData(response);

                        initChart();

                        loadChartData();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        RequestQueue mQueue = Volley.newRequestQueue(this);
        mQueue.add(stringRequest);

    }



// k线图
    private void initChart() {
        colorHomeBg = getResources().getColor(R.color.home_page_bg);
        colorLine = getResources().getColor(R.color.common_divider);
        colorText = getResources().getColor(R.color.text_grey_light);
        colorMa5 = getResources().getColor(R.color.ma5);
        colorMa10 = getResources().getColor(R.color.ma10);
        colorMa20 = getResources().getColor(R.color.ma20);

        mChart.setDescription("");
        mChart.setDrawGridBackground(true);
        mChart.setBackgroundColor(colorHomeBg);
        mChart.setGridBackgroundColor(colorHomeBg);
        mChart.setScaleYEnabled(false);
        mChart.setPinchZoom(true);
        mChart.setDrawValueAboveBar(false);
        mChart.setNoDataText("加载中...");
        mChart.setAutoScaleMinMaxEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE});

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGridColor(colorLine);
        xAxis.setTextColor(colorText);
        xAxis.setSpaceBetweenLabels(4);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(4, false);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setGridColor(colorLine);
        leftAxis.setTextColor(colorText);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        int[] colors = {colorMa5, colorMa10, colorMa20};
        String[] labels = {"MA10", "MA20", "MA50"};
        Legend legend = mChart.getLegend();
        legend.setCustom(colors, labels);
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        legend.setTextColor(Color.BLACK);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
//                CandleEntry candleEntry = (CandleEntry) entry;
//                float change = (candleEntry.getClose() - candleEntry.getOpen()) / candleEntry.getOpen();
//                NumberFormat nf = NumberFormat.getPercentInstance();
//                nf.setMaximumFractionDigits(2);
//                String changePercentage = nf.format(Double.valueOf(String.valueOf(change)));
//                Log.d("qqq", "最高" + candleEntry.getHigh() + " 最低" + candleEntry.getLow() +
//                        " 开盘" + candleEntry.getOpen() + " 收盘" + candleEntry.getClose() +
//                        " 涨跌幅" + changePercentage);
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void loadChartData() {
        mChart.resetTracking();

        candleEntries = Model.getCandleEntries_1month();

        itemcount = candleEntries.size();
        System.out.println("----itemcount : "+itemcount);
        //List<StockListBean.eachTime> stockBeans = Model.getData();
        List<String> DateInfo = Model.getDate();
        xVals = new ArrayList<>();
        for (int i = DateInfo.size()-itemcount; i < DateInfo.size(); i++) {
            xVals.add(DateInfo.get(i));
        }

        combinedData = new CombinedData(xVals);

        /*k line*/
        candleData = generateCandleData();
        combinedData.setData(candleData);

        /*ma10*/
        List<Entry> ma10Entries = Model.getMa10Entries_1month();
        /*ma20*/
        List<Entry> ma20Entries = Model.getMa20Entries_1month();
        /*ma50*/
        List<Entry> ma50Entries = Model.getMa50Entries_1month();

        lineData = generateMultiLineData(
                generateLineDataSet(ma10Entries, colorMa5, "ma10"),
                generateLineDataSet(ma20Entries, colorMa10, "ma20"),
                generateLineDataSet(ma50Entries, colorMa20, "ma50"));

        combinedData.setData(lineData);
        mChart.setData(combinedData);//当前屏幕会显示所有的数据
        mChart.invalidate();
    }

    private BarDataSet generateBarDataSet(List<BarEntry> entries, List<Integer> colorArr, String label){
        BarDataSet set = new BarDataSet(entries, label);
        set.setColors(colorArr);
        barChart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);//设置注解的位置在左上方
        barChart.getLegend().setForm(Legend.LegendForm.CIRCLE);//这是左边显示小图标的形状

        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置
        barChart.getXAxis().setDrawGridLines(false);//不显示网格

        barChart.getAxisRight().setEnabled(false);//右侧不显示Y轴
        barChart.getAxisLeft().setAxisMinValue(0.0f);//设置Y轴显示最小值，不然0下面会有空隙
        barChart.getAxisLeft().setDrawGridLines(false);//不设置Y轴网格


        barChart.animateXY(1000, 2000);//设置动画


        return set;
    }

    private LineDataSet generateLineDataSet(List<Entry> entries, int color, String label) {
        LineDataSet set = new LineDataSet(entries, label);
        set.setColor(color);
        set.setLineWidth(1f);
        //set.setDrawCubic(true);//圆滑曲线
        set.setHighlightEnabled(true);
        set.setDrawHighlightIndicators(true);
        set.setHighLightColor(Color.BLACK);
        set.setLineWidth(2);
        set.setDrawCircles(false);
        set.setDrawCircleHole(false);
        set.setDrawValues(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setDrawFilled(true);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
        set.setFillDrawable(drawable);

        return set;
    }

    private LineData generateMultiLineData(LineDataSet... lineDataSets) {
        List<ILineDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < lineDataSets.length; i++) {
            dataSets.add(lineDataSets[i]);
        }

        List<String> xVals = new ArrayList<String>();
        for (int i = 0; i < itemcount; i++) {
            xVals.add("" + (1990 + i));
        }

        LineData data = new LineData(xVals, dataSets);

        return data;
    }

    private CandleData generateCandleData() {

        CandleDataSet set = new CandleDataSet(candleEntries, "");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setShadowWidth(0.7f);
        set.setDecreasingColor(Color.RED);
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setIncreasingColor(Color.GREEN);
        set.setIncreasingPaintStyle(Paint.Style.STROKE);
        set.setNeutralColor(Color.RED);
        set.setShadowColorSameAsCandle(true);
        set.setHighlightLineWidth(0.5f);
        set.setHighLightColor(Color.WHITE);
        set.setDrawValues(false);

        CandleData candleData = new CandleData(xVals);
        candleData.addDataSet(set);

        return candleData;
    }

    // 分时图
    private void initChartF() {
        lineChart.setScaleEnabled(false);
        lineChart.setDrawBorders(false);
        lineChart.setBorderWidth(1);
        lineChart.setBorderColor(getResources().getColor(R.color.edit_text_underline));
        lineChart.setDescription("");
        Legend lineChartLegend = lineChart.getLegend();
        lineChartLegend.setEnabled(false);
        lineChart.setDrawMarkerViews(true);
        lineChart.setTouchEnabled(true); // 设置是否可以触摸
        lineChart.setDragEnabled(true);// 是否可以拖拽
        CustomMarkerView mv = new CustomMarkerView(this, R.layout.mymarkerview);
        lineChart.setMarkerView(mv);

        lineChart.setScaleXEnabled(true); //是否可以缩放 仅x轴
        lineChart.setScaleYEnabled(true); //是否可以缩放 仅y轴
        lineChart.setPinchZoom(true);  //设置x轴和y轴能否同时缩放。默认是否
        lineChart.setDoubleTapToZoomEnabled(true);//设置是否可以通过双击屏幕放大图表。默认是true
        lineChart.setHighlightPerDragEnabled(true);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true
        lineChart.setDragDecelerationEnabled(true);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        lineChart.setDragDecelerationFrictionCoef(0.99f);//与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。

        barChart.setScaleEnabled(false);
        barChart.setDrawBorders(false);
        barChart.setScaleXEnabled(true); //是否可以缩放 仅x轴
        barChart.setScaleYEnabled(true); //是否可以缩放 仅y轴
        barChart.setPinchZoom(true);  //设置x轴和y轴能否同时缩放。默认是否
      /*  barChart.setBorderWidth(1);
        barChart.setBorderColor(getResources().getColor(R.color.grayLine));*/
        barChart.setDescription("");
        barChart.setMarkerView(mv);
        Legend barChartLegend = barChart.getLegend();
        barChartLegend.setEnabled(false);


        //x轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelsToSkip(59);



        //左边y
        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setLabelCount(5, true);
        axisLeft.setDrawLabels(true);

//        //右边y
        YAxis axisRight = lineChart.getAxisRight();
        axisRight.setEnabled(false);
//        YAxis axisRight = lineChart.getAxisRight();
//        axisRight.setLabelCount(5, true);
//        axisRight.setDrawLabels(true);

        //bar x y轴
        XAxis xAxisBar = barChart.getXAxis();
        xAxisBar.setDrawLabels(false);
        xAxisBar.setDrawGridLines(false);

        YAxis axisLeftBar = barChart.getAxisLeft();
        axisLeftBar.setDrawGridLines(false);


        YAxis axisRightBar = barChart.getAxisRight();
        // axisRightBar.setDrawLabels(false);
        axisRightBar.setDrawGridLines(false);

        //y轴样式
        axisLeft.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat mFormat = new DecimalFormat("#0.00");
                return mFormat.format(value);
            }
        });


//        axisRight.setValueFormatter(new YAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, YAxis yAxis) {
//                DecimalFormat mFormat = new DecimalFormat("#0.00%");
//                return mFormat.format(value);
//            }
//        });
//
//        axisRight.setStartAtZero(false);
//        axisRight.setDrawGridLines(false);
//        axisRight.setDrawAxisLine(false);
        //背景线
        xAxis.setGridColor(getResources().getColor(R.color.edit_text_underline));
        xAxis.setAxisLineColor(getResources().getColor(R.color.edit_text_underline));
        axisLeft.setGridColor(getResources().getColor(R.color.edit_text_underline));
        //axisRight.setAxisLineColor(getResources().getColor(R.color.edit_text_underline));
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
//                barChart.setHighlightValue(new Highlight(h.getXIndex(), 0));

                barChart.highlightValue(new Highlight(h.getXIndex(), 0));

                // lineChart.setHighlightValue(h);
            }

            @Override
            public void onNothingSelected() {
                barChart.highlightValue(null);
            }
        });
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                lineChart.highlightValue(new Highlight(h.getXIndex(), 0));
                // lineChart.setHighlightValue(new Highlight(h.getXIndex(), 0));//此函数已经返回highlightBValues的变量，并且刷新，故上面方法可以注释
                //barChart.setHighlightValue(h);
            }

            @Override
            public void onNothingSelected() {
                lineChart.highlightValue(null);
            }
        });



    }

    private void loadChartDataF(){
        lineChart.resetTracking();
        barChart.resetTracking();

        List<Entry> lineEntries = Model.getLineEntries();
        List<BarEntry> barEntries = Model.getBarEntries();

        itemcount = barEntries.size();
        linechartcount = lineEntries.size();
        System.out.println("----itemcount : "+itemcount);
        //List<StockListBean.eachTime> stockBeans = Model.getData();
        List<String> MinInfo = Model.getMin();
        xVals = new ArrayList<>();
        for (int i = 0; i < itemcount; i++) {
            xVals.add(MinInfo.get(i));
        }
        List<Integer> colorArray = new ArrayList<>();
        List<Float> priceArr = Model.getPrice();
        colorArray.add(getResources().getColor(R.color.text_grey));
        for(int i = 1; i < linechartcount; i ++){
            System.out.println("----check barchart values-----: barEntries[i-1]: "+ priceArr.get(i-1) + "; barEntries[i]: " + priceArr.get(i-1));
            if(priceArr.get(i)>priceArr.get(i-1)){
                colorArray.add(getResources().getColor(R.color.text_green));
            }
            else{
                colorArray.add(getResources().getColor(R.color.text_red));
            }
        }
        BarDataSet set2 = generateBarDataSet(barEntries, colorArray, "minVol");
        set2.setHighlightEnabled(true);
        //set2.setDrawHighlightIndicators(true);
        set2.setHighLightColor(Color.BLACK);
        BarData data2 = new BarData(xVals, set2);
        barChart.setData(data2);

        LineDataSet set1 = generateLineDataSet(lineEntries, getResources().getColor(R.color.line_chart_color), "minPrice");


        LineData data = new LineData(xVals, set1);
        lineChart.setData(data);



        /*k line*/
        //candleData = generateCandleData();
        //combinedData.setData(candleData);

//        /*ma5*/
//        ArrayList<Entry> ma5Entries = new ArrayList<Entry>();
//        for (int index = 0; index < itemcount; index++) {
//            ma5Entries.add(new Entry(stockBeans.get(index).getMa5(), index));
//        }
//        /*ma10*/
//        ArrayList<Entry> ma10Entries = new ArrayList<Entry>();
//        for (int index = 0; index < itemcount; index++) {
//            ma10Entries.add(new Entry(stockBeans.get(index).getMa10(), index));
//        }
//        /*ma20*/
//        ArrayList<Entry> ma20Entries = new ArrayList<Entry>();
//        for (int index = 0; index < itemcount; index++) {
//            ma20Entries.add(new Entry(stockBeans.get(index).getMa20(), index));
//        }

//        lineData = generateMultiLineData(
//                generateLineDataSet(ma5Entries, colorMa5, "ma5"),
//                generateLineDataSet(ma10Entries, colorMa10, "ma10"),
//                generateLineDataSet(ma20Entries, colorMa20, "ma20"));

        //combinedData.setData(lineData);
        //mChart.setData(combinedData);//当前屏幕会显示所有的数据
        setOffset();

        lineChart.invalidate();
        barChart.invalidate();
    }

    /*设置量表对齐*/
    private void setOffset() {
        float lineLeft = lineChart.getViewPortHandler().offsetLeft();
        float barLeft = barChart.getViewPortHandler().offsetLeft();
        float lineRight = lineChart.getViewPortHandler().offsetRight();
        float barRight = barChart.getViewPortHandler().offsetRight();
        float offsetLeft, offsetRight;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (barLeft < lineLeft) {
            offsetLeft = Utils.convertPixelsToDp(lineLeft-barLeft);
            barChart.setExtraLeftOffset(offsetLeft);
        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft-lineLeft);
            lineChart.setExtraLeftOffset(offsetLeft);
        }
  /*注：setExtra...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (barRight < lineRight) {
            offsetRight = Utils.convertPixelsToDp(lineRight);
            barChart.setExtraRightOffset(offsetRight);
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            lineChart.setExtraRightOffset(offsetRight);
        }

    }


/*
    private void setData(MData mData) {
        if (mData.getDatas().size() == 0) {
            lineChart.setNoDataText("暂无数据");
            return;
        }
        //设置y左右两轴最大最小值
        axisLeft.setAxisMinValue(mData.getMin());
        axisLeft.setAxisMaxValue(mData.getMax());
        axisRight.setAxisMinValue(mData.getPercentMin());
        axisRight.setAxisMaxValue(mData.getPercentMax());


        axisLeftBar.setAxisMaxValue(mData.getVolmax());
        axisLeftBar.setAxisMinValue(0);//即使最小是不是0，也无碍
        axisLeftBar.setShowOnlyMinMax(true);
        axisRightBar.setAxisMaxValue(mData.getVolmax());
        axisRightBar.setAxisMinValue(0);//即使最小是不是0，也无碍
        axisRightBar.setShowOnlyMinMax(true);
        //基准线
        LimitLine ll = new LimitLine(mData.getBaseValue());
        ll.setLineWidth(1f);
        ll.setLineColor(Color.RED);
        ll.enableDashedLine(10f, 10f, 0f);
        ll.setLineWidth(1);
        axisLeft.addLimitLine(ll);


        ArrayList<Entry> lineCJEntries = new ArrayList<Entry>();
        ArrayList<Entry> lineJJEntries = new ArrayList<Entry>();
        ArrayList<String> dateList = new ArrayList<String>();
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < mData.getDatas().size(); i++) {
            //避免数据重复，skip也能正常显示
            if(mData.getDatas().get(i).time.equals("13:30")){
                continue;
            }
            lineCJEntries.add(new Entry(mData.getDatas().get(i).chengjiaojia, i));

            lineJJEntries.add(new Entry(mData.getDatas().get(i).junjia, i));
            barEntries.add(new BarEntry(mData.getDatas().get(i).chengjiaoliang, i));
            dateList.add(mData.getDatas().get(i).time);
        }
        d1 = new LineDataSet(lineCJEntries, "成交价");
        d2 = new LineDataSet(lineJJEntries, "均价");
        barDataSet = new BarDataSet(barEntries, "成交量");

        d1.setCircleRadius(0);
        d2.setCircleRadius(0);
        d1.setColor(Color.BLUE);
        d2.setColor(Color.RED);
        d1.setHighLightColor(Color.BLACK);
        d2.setHighlightEnabled(false);
        d1.setDrawFilled(true);

        barDataSet.setBarSpacePercent(0); //bar空隙
        barDataSet.setHighLightColor(Color.BLACK);
        barDataSet.setHighLightAlpha(255);
        barDataSet.setDrawValues(false);
        //谁为基准
        d1.setAxisDependency(YAxis.AxisDependency.LEFT);
        // d2.setAxisDependency(YAxis.AxisDependency.RIGHT);
        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
        sets.add(d1);
        sets.add(d2);
        LineData cd = new LineData(dateList, sets);
        lineChart.setData(cd);

        BarData barData=new BarData(dateList,barDataSet);
        barChart.setData(barData);
        lineChart.invalidate();//刷新图
        barChart.invalidate();
    }
    */
public class CustomMarkerView extends MarkerView {

    private TextView tvContent;

    public CustomMarkerView (Context context, int layoutResource) {
        super(context, layoutResource);
        // this markerview only displays a textview
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText("" + e.getVal()); // set the entry-value as the display text
    }

    @Override
    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }

    }
}
