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

    public static List<Float> getPrice(){
        return new Gson().fromJson(dataF, StockDayBean.class).getPrice().getValues();
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
                //Entry entry = new Entry(rawDataPrice.get(i-1), i);
                //entries.add(entry);
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

    //1 month k line chart candle entries
    public static List<CandleEntry> getCandleEntries_1month() {
        List<Float> rawDataHigh = new Gson().fromJson(data, StockListBean.class).getHigh().getValues();
        List<Float> rawDataLow = new Gson().fromJson(data, StockListBean.class).getLow().getValues();
        List<Float> rawDataOpen = new Gson().fromJson(data, StockListBean.class).getOpen().getValues();
        List<Float> rawDataClose = new Gson().fromJson(data, StockListBean.class).getHigh().getValues();
        String rawDataEname = new Gson().fromJson(data, StockListBean.class).getEng_name().getValues();
        return getCandleEntries(rawDataHigh, rawDataLow, rawDataOpen, rawDataClose,rawDataHigh.size()-21);
    }

    //3 months k line chart candle entries
    public static List<CandleEntry> getCandleEntries_3month() {
        List<Float> rawDataHigh = new Gson().fromJson(data, StockListBean.class).getHigh().getValues();
        List<Float> rawDataLow = new Gson().fromJson(data, StockListBean.class).getLow().getValues();
        List<Float> rawDataOpen = new Gson().fromJson(data, StockListBean.class).getOpen().getValues();
        List<Float> rawDataClose = new Gson().fromJson(data, StockListBean.class).getHigh().getValues();
        String rawDataEname = new Gson().fromJson(data, StockListBean.class).getEng_name().getValues();
        return getCandleEntries(rawDataHigh, rawDataLow, rawDataOpen, rawDataClose,rawDataHigh.size()-60);
    }

//1 year k line chart candle entries
    public static List<CandleEntry> getCandleEntries_1year() {
        List<Float> rawDataHigh = new Gson().fromJson(data, StockListBean.class).getHigh().getValues();
        List<Float> rawDataLow = new Gson().fromJson(data, StockListBean.class).getLow().getValues();
        List<Float> rawDataOpen = new Gson().fromJson(data, StockListBean.class).getOpen().getValues();
        List<Float> rawDataClose = new Gson().fromJson(data, StockListBean.class).getHigh().getValues();
        String rawDataEname = new Gson().fromJson(data, StockListBean.class).getEng_name().getValues();
        return getCandleEntries(rawDataHigh, rawDataLow, rawDataOpen, rawDataClose,rawDataHigh.size()-246);
    }

    //3 year k line chart candle entries
    public static List<CandleEntry> getCandleEntries_3year() {
        List<Float> rawDataHigh = new Gson().fromJson(data, StockListBean.class).getHigh().getValues();
        List<Float> rawDataLow = new Gson().fromJson(data, StockListBean.class).getLow().getValues();
        List<Float> rawDataOpen = new Gson().fromJson(data, StockListBean.class).getOpen().getValues();
        List<Float> rawDataClose = new Gson().fromJson(data, StockListBean.class).getHigh().getValues();
        String rawDataEname = new Gson().fromJson(data, StockListBean.class).getEng_name().getValues();
        return getCandleEntries(rawDataHigh, rawDataLow, rawDataOpen, rawDataClose,0);
    }

    public static List<CandleEntry> getCandleEntries(List<Float> rawDataHigh,List<Float> rawDataLow, List<Float> rawDataOpen, List<Float> rawDataClose,int startIndex) {
        List<CandleEntry> entries = new ArrayList<>();


        for (int i = startIndex; i < rawDataHigh.size(); i++) {
            Float high = rawDataHigh.get(i);
            Float low = rawDataLow.get(i);
            Float open = rawDataOpen.get(i);
            Float close = rawDataClose.get(i);
            if (high == null||low == null) {
                Log.e("xxx", "第" + i + "StockBean==null");
                continue;
            }
            CandleEntry entry = new CandleEntry(i-startIndex, high, low, open, close);
            entries.add(entry);
        }

        return entries;
    }

    //1 month ma lines
    public static List<Entry> getMa10Entries_1month(){
        List<Float> ma10Entries = new Gson().fromJson(data, StockListBean.class).getSma10().getValues();
        return getMa10Entries(ma10Entries, ma10Entries.size()-21);
    }

    public static List<Entry> getMa20Entries_1month(){
        List<Float> ma20Entries = new Gson().fromJson(data, StockListBean.class).getSma20().getValues();
        return getMa20Entries(ma20Entries, ma20Entries.size()-21);
    }

    public static List<Entry> getMa50Entries_1month(){
        List<Float> ma50Entries = new Gson().fromJson(data, StockListBean.class).getSma50().getValues();
        return getMa10Entries(ma50Entries, ma50Entries.size()-21);
    }

    //3 months ma lines
    public static List<Entry> getMa10Entries_3month(){
        List<Float> ma10Entries = new Gson().fromJson(data, StockListBean.class).getSma10().getValues();
        return getMa10Entries(ma10Entries, ma10Entries.size()-60);
    }

    public static List<Entry> getMa20Entries_3month(){
        List<Float> ma20Entries = new Gson().fromJson(data, StockListBean.class).getSma20().getValues();
        return getMa20Entries(ma20Entries, ma20Entries.size()-60);
    }

    public static List<Entry> getMa50Entries_3month(){
        List<Float> ma50Entries = new Gson().fromJson(data, StockListBean.class).getSma50().getValues();
        return getMa10Entries(ma50Entries, ma50Entries.size()-60);
    }

    //1 year ma lines
    public static List<Entry> getMa10Entries_1year(){
        List<Float> ma10Entries = new Gson().fromJson(data, StockListBean.class).getSma10().getValues();
        return getMa10Entries(ma10Entries, ma10Entries.size()-246);
    }

    public static List<Entry> getMa20Entries_1year(){
        List<Float> ma20Entries = new Gson().fromJson(data, StockListBean.class).getSma20().getValues();
        return getMa20Entries(ma20Entries, ma20Entries.size()-246);
    }

    public static List<Entry> getMa50Entries_1year(){
        List<Float> ma50Entries = new Gson().fromJson(data, StockListBean.class).getSma50().getValues();
        return getMa10Entries(ma50Entries, ma50Entries.size()-246);
    }

    //1 year ma lines
    public static List<Entry> getMa10Entries_3year(){
        List<Float> ma10Entries = new Gson().fromJson(data, StockListBean.class).getSma10().getValues();
        return getMa10Entries(ma10Entries, 0);
    }

    public static List<Entry> getMa20Entries_3year(){
        List<Float> ma20Entries = new Gson().fromJson(data, StockListBean.class).getSma20().getValues();
        return getMa20Entries(ma20Entries, 0);
    }

    public static List<Entry> getMa50Entries_3year(){
        List<Float> ma50Entries = new Gson().fromJson(data, StockListBean.class).getSma50().getValues();
        return getMa10Entries(ma50Entries, 0);
    }





    public static List<Entry> getMa10Entries(List<Float> ma10Entries, int startIndex){

        List<Entry> entries = new ArrayList<>();
        for (int i = startIndex; i < ma10Entries.size(); i++) {
            Float ma10 = ma10Entries.get(i);
            Entry entry = new Entry(ma10,i-startIndex);
            entries.add(entry);
        }
        return entries;
    }

    public static List<Entry> getMa20Entries(List<Float> ma20Entries, int startIndex){
        List<Entry> entries = new ArrayList<>();
        for (int i = startIndex; i < ma20Entries.size(); i++) {
            Float ma20 = ma20Entries.get(i);
            Entry entry = new Entry(ma20,i-startIndex);
            entries.add(entry);
        }
        return entries;
    }

    public static List<Entry> getMa50Entries(List<Float> ma50Entries, int startIndex){
        List<Entry> entries = new ArrayList<>();
        for (int i = startIndex; i < ma50Entries.size(); i++) {
            Float ma50 = ma50Entries.get(i);
            Entry entry = new Entry(ma50,i-startIndex);
            entries.add(entry);
        }
        return entries;
    }

}
