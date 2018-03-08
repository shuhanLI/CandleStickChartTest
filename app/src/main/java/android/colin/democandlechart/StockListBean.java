package android.colin.democandlechart;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/17.
 */

public class StockListBean {
    //    {
//        "Meta Data": {
//        "1. Information": "Intraday (1min) prices and volumes",
//                "2. Symbol": "FB",
//                "3. Last Refreshed": "2018-03-05 16:00:00",
//                "4. Interval": "1min",
//                "5. Output Size": "Compact",
//                "6. Time Zone": "US/Eastern"
//    },
//        "Time Series (1min)": {
//        "2018-03-05 16:00:00": {
//            "1. open": "180.5100",
//                    "2. high": "180.6400",
//                    "3. low": "180.3900",
//                    "4. close": "180.4000",
//                    "5. volume": "869259"
//        },

    public class response {
        @SerializedName("Meta Data")
        private meta metaData;
        @SerializedName("Time Series (1min)")
        private Map<String, eachTime> timeSeries;

        public meta getMetaData() {
            return metaData;
        }

        public void setMetaData(meta metaData) {
            this.metaData = metaData;
        }

        public Map<String, eachTime> getTimeSeries() {
            return timeSeries;
        }

        public void setTimeSeries(Map<String, eachTime> timeSeries) {
            this.timeSeries = timeSeries;
        }
    }

    public class meta {
        @SerializedName("1. Information")
        private String information;
        @SerializedName("2. Symbol")
        private String symbol;
        @SerializedName("3. Last Refreshed")
        private String lastRefresh;
        @SerializedName("4. Interval")
        private String timeInterval;
        @SerializedName("5. Output Size")
        private String output;
        @SerializedName("6. Time Zone")
        private String timeZone;

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getLastRefresh() {
            return lastRefresh;
        }

        public void setLastRefresh(String lastRefresh) {
            this.lastRefresh = lastRefresh;
        }

        public String getTimeInterval() {
            return timeInterval;
        }

        public void setTimeInterval(String timeInterval) {
            this.timeInterval = timeInterval;
        }

        public String getOutput() {
            return output;
        }

        public void setOutput(String output) {
            this.output = output;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }
    }

    public class eachTime {
        @SerializedName("1. open")
        private float open;
        @SerializedName("2. high")
        private float high;
        @SerializedName("3. low")
        private float low;
        @SerializedName("4. close")
        private float close;
        @SerializedName("5. volume")
        private String volume;


        public float getOpen() {
            return open;
        }

        public void setOpen(float Open) {
            open = Open;
        }

        public float getHigh() {
            return high;
        }

        public void setHigh(float High) {
            high = High;
        }

        public float getLow() {
            return low;
        }

        public void setLow(float Low) {
            low = Low;
        }

        public float getClose() {
            return close;
        }

        public void setClose(float Close) {
            close = Close;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String Volume) {
            volume = Volume;
        }

    }
}

