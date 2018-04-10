package android.colin.democandlechart;

import android.text.TextUtils;
import android.util.Log;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/17.
 */
public class Model {
    public static String data = " ";
    public static String dataF = " ";
    //k line
    public static void setData(String d) {
        data = d;
        System.out.println("-----Model setdata-----:"+data);
    }
    //fenshi
    public static void setDataF(String d) {
        dataF = d;
        System.out.println("-----Model setdataF-----:"+dataF);
    }

    public static List<String> getDate(){
        return new Gson().fromJson(data, StockListBean.class).getX_axis().getLabels();
    }

    public static List<String> getMin(){
        return new Gson().fromJson(dataF, StockListBean.class).getX_axis().getLabels();
    }


    public static List<Entry> getLineEntries(){
        List<Float> rawDataPrice = new Gson().fromJson(dataF, StockDayBean.class).getPrice().getValues();
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < rawDataPrice.size(); i++) {
            Float price = rawDataPrice.get(i);
            if(price!=null) {
                Entry entry = new Entry(price, i);
                entries.add(entry);
            }
            else{
                Entry entry = new Entry(0, i);
                entries.add(entry);
            }
        }
        return entries;
    }

    public static List<BarEntry> getBarEntries(){
        List<Integer> rawDataVol = new Gson().fromJson(dataF, StockDayBean.class).getVol().getValues();
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < rawDataVol.size(); i++) {
            Integer vol = rawDataVol.get(i);
            BarEntry entry = new BarEntry(vol,i);
            entries.add(entry);
        }
        return entries;
    }

    public static List<CandleEntry> getCandleEntries() {
        List<Float> rawDataHigh = new Gson().fromJson(data, StockListBean.class).getHigh().getValues();
        List<Float> rawDataLow = new Gson().fromJson(data, StockListBean.class).getLow().getValues();
        List<Float> rawDataOpen = new Gson().fromJson(data, StockListBean.class).getOpen().getValues();
        List<Float> rawDataClose = new Gson().fromJson(data, StockListBean.class).getHigh().getValues();
        String rawDataEname = new Gson().fromJson(data, StockListBean.class).getEng_name().getValues();
        System.out.println("english name in model: "+rawDataEname);
        System.out.println("high price in model: "+rawDataHigh);
        return getCandleEntries(rawDataHigh, rawDataLow, rawDataOpen, rawDataClose,0);
    }

    public static List<CandleEntry> getCandleEntries(List<Float> rawDataHigh,List<Float> rawDataLow, List<Float> rawDataOpen, List<Float> rawDataClose,int startIndex) {
        List<CandleEntry> entries = new ArrayList<>();


        for (int i = 0; i < rawDataHigh.size(); i++) {
            Float high = rawDataHigh.get(i);
            Float low = rawDataLow.get(i);
            Float open = rawDataOpen.get(i);
            Float close = rawDataClose.get(i);
            if (high == null||low == null) {
                Log.e("xxx", "第" + i + "StockBean==null");
                continue;
            }
            CandleEntry entry = new CandleEntry(startIndex+i, high, low, open, close);
            entries.add(entry);
        }

        return entries;
    }

    /**
     * 根据给定的格式与时间(Date类型的)，返回时间字符串。最为通用。<br>
     * 格式字符串
     *
     * @param timeStr
     * @param format
     * @return
     */
    public static Date stringToDate(String timeStr, String format) {
        if (TextUtils.isEmpty(timeStr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
