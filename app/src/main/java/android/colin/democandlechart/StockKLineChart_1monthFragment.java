package android.colin.democandlechart;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Li Shuhan on 2018/4/12.
 */

public class StockKLineChart_1monthFragment extends android.support.v4.app.Fragment {
    private String TAG = "qqq";
    private CombinedChart mChart;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_kline, container, false);

        //setContentView(R.layout.activity_kline);

        mChart = (CombinedChart) view.findViewById(R.id.kline_chart);
        //http://money18.on.cc/chartdata/d1/price/02318_price_d1.txt
        //http://money18.on.cc/chartdata/full/price/00700_price_full.txt

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
        RequestQueue mQueue = Volley.newRequestQueue(this.getActivity());
        mQueue.add(stringRequest);

        return view;
    }

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
        for (int i = DateInfo.size()-21; i < DateInfo.size(); i++) {
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
        Drawable drawable = ContextCompat.getDrawable(this.getActivity(), R.drawable.fade_red);
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


}
