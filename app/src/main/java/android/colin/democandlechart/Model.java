package android.colin.democandlechart;

import android.text.TextUtils;
import android.util.Log;

import com.github.mikephil.charting.data.CandleEntry;
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

    public static List<String> getDate(){
        Gson gson = new Gson();
        List<String> dateStr = new ArrayList<>() ;
        StockListBean.response response = gson.fromJson(data, StockListBean.response.class);
        Map<String, StockListBean.eachTime> timeSeries = response.getTimeSeries();
        for (Map.Entry<String,StockListBean.eachTime> pair:timeSeries.entrySet()){//遍历取出键值对，调用getkey()，getvalue()取出key和value。
            dateStr.add(pair.getKey());
            System.out.println("-----KEY-----: "+pair.getKey());
        }
        return dateStr;
    }

    public static List<StockListBean.eachTime> getData() {
        Gson gson = new Gson();
        //return new Gson().fromJson(data, StockListBean.class).getContent();
        System.out.println("-----Model gggetdata-----:"+data);
        StockListBean.response response = gson.fromJson(data, StockListBean.response.class);
        Map<String, StockListBean.eachTime> timeSeries = response.getTimeSeries();
        List<StockListBean.eachTime> retlist = new ArrayList<>() ;
        for (Map.Entry<String,StockListBean.eachTime> pair:timeSeries.entrySet()){//遍历取出键值对，调用getkey()，getvalue()取出key和value。
            System.out.println("-----KEY-----: "+pair.getKey());
            System.out.println("-----OPEN----: "+pair.getValue().getOpen());
            retlist.add(pair.getValue());
        }
        return retlist;
    }

    public static void setData(String d) {
        data = d;
        //Log.d("Model", data);
        System.out.println("-----Model setdata-----:"+data);
    }
//    public static List<StockListBean.StockBean> getMoreData() {
//        return new Gson().fromJson(moreData, StockListBean.class).getContent();
//    }

    public static List<CandleEntry> getCandleEntries() {
        List<StockListBean.eachTime> rawData = getData();
        Log.d("-----TAG Model-----", rawData.get(0).getVolume());
        return getCandleEntries(rawData,0);
    }

    public static List<CandleEntry> getCandleEntries(List<StockListBean.eachTime> rawData, int startIndex) {
        List<CandleEntry> entries = new ArrayList<>();

//        Comparator comparator = new Comparator<StockListBean.StockBean>() {
//            @Override
//            public int compare(StockListBean.StockBean lhs, StockListBean.StockBean rhs) {
//                Date dateL = stringToDate(lhs.getDate(), "yyyy/MM/dd");
//                Date dateR = stringToDate(rhs.getDate(), "yyyy/MM/dd");
//                if (dateL.after(dateR)) {
//                    return 1;
//                }
//                return -1;
//            }
//        };
//        Collections.sort(rawData, comparator);

        for (int i = 0; i < rawData.size(); i++) {
            StockListBean.eachTime stock = rawData.get(i);
            if (stock == null) {
                Log.e("xxx", "第" + i + "StockBean==null");
                continue;
            }
            CandleEntry entry = new CandleEntry(rawData.size()-1-startIndex-i, stock.getHigh(), stock.getLow(), stock.getOpen(), stock.getClose());
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
