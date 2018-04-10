package android.colin.democandlechart;

import java.util.List;
/**
 * Created by yuxuangu on 2018/4/4.
 */

public class StockDayBean {

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Eng_name getEng_name() {
        return eng_name;
    }

    public void setEng_name(Eng_name eng_name) {
        this.eng_name = eng_name;
    }

    public Chi_name getChi_name() {
        return chi_name;
    }

    public void setChi_name(Chi_name chi_name) {
        this.chi_name = chi_name;
    }

    public PrevCPrice getPrevCPrice() {
        return prevCPrice;
    }

    public void setPrevCPrice(PrevCPrice prevCPrice) {
        this.prevCPrice = prevCPrice;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public X_axis getX_axis() {
        return x_axis;
    }

    public void setX_axis(X_axis x_axis) {
        this.x_axis = x_axis;
    }

    private Date date;
    private Eng_name eng_name;
    private Chi_name chi_name;
    private PrevCPrice prevCPrice;
    private Price price;
    private Vol vol;
    private X_axis x_axis;

    class Date {
        private String values;

        public String getValues() {
            return values;
        }

        public void setValues(String values) {
            this.values = values;
        }
    }

    class Eng_name {

        private String values;

        public String getValues() {
            return values;
        }

        public void setValues(String values) {
            this.values = values;
        }


    }

    class Chi_name {
        public String getValues() {
            return values;
        }

        public void setValues(String values) {
            this.values = values;
        }

        private String values;
    }

    class PrevCPrice {
        public float getValues() {
            return values;
        }

        public void setValues(float values) {
            this.values = values;
        }

        private float values;
    }

    class Price {
        public List<Float> getValues() {
            return values;
        }

        public void setValues(List<Float> values) {
            this.values = values;
        }

        private List<Float> values;
    }

    class Vol {
        public List<Integer> getValues() {
            return values;
        }

        public void setValues(List<Integer> values) {
            this.values = values;
        }

        private List<Integer> values;
    }

    class X_axis {
        public List<String> getLabels() {
            return labels;
        }

        public void setLabels(List<String> labels) {
            this.labels = labels;
        }

        private List<String> labels;
    }

}