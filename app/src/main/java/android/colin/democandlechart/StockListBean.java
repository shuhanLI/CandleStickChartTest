package android.colin.democandlechart;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/17.
 */

public class StockListBean {
/*
JSON
{}chi_name
    values:
{}high
    []values:
{}eng_name
    values:
{}vol
    []values:
{}x_axis
    []labels:
{}low
    []values:
{}price_up_down
    []values:
{}code
    values:
{}open
    []values:
{}price
    []values:
{}sma50
    []values:
{}sma20
    []values:
{}sma10
    []values:
 */
//single value:
    private cname chi_name;
    private ename eng_name;
    private codenum code;
//json array:
    private higharr high;
    private volarr vol;
    private datearr x_axis;
    private lowarr low;
    //private price_up_downarr price_up_down;
    private openarr open;
    private closearr price;
    private sma50arr sma50;
    private sma20arr sma20;
    private sma10arr sma10;

    public ename getEng_name() {
        return eng_name;
    }

    public void setEng_name(ename eng_name) {
        this.eng_name = eng_name;
    }

    public codenum getCode() {
        return code;
    }

    public void setCode(codenum code) {
        this.code = code;
    }

    public higharr getHigh() {
        return high;
    }

    public void setHigh(higharr high) {
        this.high = high;
    }

    public volarr getVol() {
        return vol;
    }

    public void setVol(volarr vol) {
        this.vol = vol;
    }

    public datearr getX_axis() {
        return x_axis;
    }

    public void setX_axis(datearr x_axis) {
        this.x_axis = x_axis;
    }

    public lowarr getLow() {
        return low;
    }

    public void setLow(lowarr low) {
        this.low = low;
    }

    public openarr getOpen() {
        return open;
    }

    public void setOpen(openarr open) {
        this.open = open;
    }

    public closearr getPrice() {
        return price;
    }

    public void setPrice(closearr price) {
        this.price = price;
    }

    public sma50arr getSma50() {
        return sma50;
    }

    public void setSma50(sma50arr sma50) {
        this.sma50 = sma50;
    }

    public sma20arr getSma20() {
        return sma20;
    }

    public void setSma20(sma20arr sma20) {
        this.sma20 = sma20;
    }

    public sma10arr getSma10() {
        return sma10;
    }

    public void setSma10(sma10arr sma10) {
        this.sma10 = sma10;
    }

    public cname getChi_name() {
        return chi_name;
    }

    public void setChi_name(cname chi_name) {
        this.chi_name = chi_name;
    }

    public static class cname{
        private String values;

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}
    public static class ename{
        private String values;

        public String getValues() {
            return values;
        }

        public void setValues(String values) {
            this.values = values;
        }
    }

    public class codenum{
        private String values;

        public String getValues() {
            return values;
        }

        public void setValues(String values) {
            this.values = values;
        }
    }

    public class higharr{
        public List<Float> getValues() {
            System.out.println("-----high in Bean-----"+values);
            return values;
        }

        public void setValues(List<Float> values) {
            this.values = values;
        }

        private List<Float> values;


    }

    public class volarr{
        public List<Float> getValues() {
            return values;
        }

        public void setValues(List<Float> values) {
            this.values = values;
        }

        private List<Float> values;


    }


    public class lowarr{
        public List<Float> getValues() {
            return values;
        }

        public void setValues(List<Float> values) {
            this.values = values;
        }

        private List<Float> values;


    }

    public class datearr{
        public List<String> getLabels() {
            return labels;
        }

        public void setLabels(List<String> labels) {
            this.labels = labels;
        }

        private List<String> labels;


    }

    public class openarr{
        public List<Float> getValues() {
            return values;
        }

        public void setValues(List<Float> values) {
            this.values = values;
        }

        private List<Float> values;


    }

    public class closearr{
        public List<Float> getValues() {
            return values;
        }

        public void setValues(List<Float> values) {
            this.values = values;
        }

        private List<Float> values;


    }

    public class sma50arr{
        public List<Float> getValues() {
            return values;
        }

        public void setValues(List<Float> values) {
            this.values = values;
        }

        private List<Float> values;


    }

    public class sma20arr{
        public List<Float> getValues() {
            return values;
        }

        public void setValues(List<Float> values) {
            this.values = values;
        }

        private List<Float> values;


    }

    public class sma10arr{
        public List<Float> getValues() {
            return values;
        }

        public void setValues(List<Float> values) {
            this.values = values;
        }

        private List<Float> values;


    }



//    public class response {
//        @SerializedName("Meta Data")
//        private meta metaData;
//        @SerializedName("Time Series (Daily)")
//        private Map<String, eachTime> timeSeries;
//
//        public meta getMetaData() {
//            return metaData;
//        }
//
//        public void setMetaData(meta metaData) {
//            this.metaData = metaData;
//        }
//
//        public Map<String, eachTime> getTimeSeries() {
//            return timeSeries;
//        }
//
//        public void setTimeSeries(Map<String, eachTime> timeSeries) {
//            this.timeSeries = timeSeries;
//        }
//    }
//
//    public class meta {
//        @SerializedName("1. Information")
//        private String information;
//        @SerializedName("2. Symbol")
//        private String symbol;
//        @SerializedName("3. Last Refreshed")
//        private String lastRefresh;
//        @SerializedName("4. Interval")
////        private String timeInterval;
////        @SerializedName("5. Output Size")
//        private String output;
//        @SerializedName("6. Time Zone")
//        private String timeZone;
//
//        public String getInformation() {
//            return information;
//        }
//
//        public void setInformation(String information) {
//            this.information = information;
//        }
//
//        public String getSymbol() {
//            return symbol;
//        }
//
//        public void setSymbol(String symbol) {
//            this.symbol = symbol;
//        }
//
//        public String getLastRefresh() {
//            return lastRefresh;
//        }
//
//        public void setLastRefresh(String lastRefresh) {
//            this.lastRefresh = lastRefresh;
//        }
//
////        public String getTimeInterval() {
////            return timeInterval;
////        }
////
////        public void setTimeInterval(String timeInterval) {
////            this.timeInterval = timeInterval;
////        }
//
//        public String getOutput() {
//            return output;
//        }
//
//        public void setOutput(String output) {
//            this.output = output;
//        }
//
//        public String getTimeZone() {
//            return timeZone;
//        }
//
//        public void setTimeZone(String timeZone) {
//            this.timeZone = timeZone;
//        }
//    }
//
//    public class eachTime {
//        @SerializedName("1. open")
//        private float open;
//        @SerializedName("2. high")
//        private float high;
//        @SerializedName("3. low")
//        private float low;
//        @SerializedName("4. close")
//        private float close;
//        @SerializedName("5. volume")
//        private String volume;
//
//
//        public float getOpen() {
//            return open;
//        }
//
//        public void setOpen(float Open) {
//            open = Open;
//        }
//
//        public float getHigh() {
//            return high;
//        }
//
//        public void setHigh(float High) {
//            high = High;
//        }
//
//        public float getLow() {
//            return low;
//        }
//
//        public void setLow(float Low) {
//            low = Low;
//        }
//
//        public float getClose() {
//            return close;
//        }
//
//        public void setClose(float Close) {
//            close = Close;
//        }
//
//        public String getVolume() {
//            return volume;
//        }
//
//        public void setVolume(String Volume) {
//            volume = Volume;
//        }
//
//    }

}

