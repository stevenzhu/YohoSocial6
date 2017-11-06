package com.iyoho.social.Entry;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ab053167 on 2017/10/12.
 */

public class TestListEntry implements Parcelable {

    /**
     * success : 1
     * result : [{"weaid":"1","days":"2017-10-12","week":"星期四","cityno":"beijing","citynm":"北京","cityid":"101010100","temperature":"13℃/6℃","humidity":"0%/0%","weather":"小雨转多云","weather_icon":"http://api.k780.com/upload/weather/d/7.gif","weather_icon1":"http://api.k780.com/upload/weather/n/1.gif","wind":"北风转西北风","winp":"微风","temp_high":"13","temp_low":"6","humi_high":"0","humi_low":"0","weatid":"8","weatid1":"2","windid":"64","winpid":"125"},{"weaid":"1","days":"2017-10-13","week":"星期五","cityno":"beijing","citynm":"北京","cityid":"101010100","temperature":"18℃/8℃","humidity":"0%/0%","weather":"晴转多云","weather_icon":"http://api.k780.com/upload/weather/d/0.gif","weather_icon1":"http://api.k780.com/upload/weather/n/1.gif","wind":"西南风转东北风","winp":"微风","temp_high":"18","temp_low":"8","humi_high":"0","humi_low":"0","weatid":"1","weatid1":"2","windid":"79","winpid":"125"},{"weaid":"1","days":"2017-10-14","week":"星期六","cityno":"beijing","citynm":"北京","cityid":"101010100","temperature":"14℃/9℃","humidity":"0%/0%","weather":"阴转小雨","weather_icon":"http://api.k780.com/upload/weather/d/2.gif","weather_icon1":"http://api.k780.com/upload/weather/n/7.gif","wind":"北风","winp":"微风","temp_high":"14","temp_low":"9","humi_high":"0","humi_low":"0","weatid":"3","weatid1":"8","windid":"20","winpid":"125"},{"weaid":"1","days":"2017-10-15","week":"星期日","cityno":"beijing","citynm":"北京","cityid":"101010100","temperature":"15℃/7℃","humidity":"0%/0%","weather":"阴转多云","weather_icon":"http://api.k780.com/upload/weather/d/2.gif","weather_icon1":"http://api.k780.com/upload/weather/n/1.gif","wind":"东北风转北风","winp":"微风","temp_high":"15","temp_low":"7","humi_high":"0","humi_low":"0","weatid":"3","weatid1":"2","windid":"25","winpid":"125"},{"weaid":"1","days":"2017-10-16","week":"星期一","cityno":"beijing","citynm":"北京","cityid":"101010100","temperature":"16℃/6℃","humidity":"0%/0%","weather":"晴","weather_icon":"http://api.k780.com/upload/weather/d/0.gif","weather_icon1":"http://api.k780.com/upload/weather/n/0.gif","wind":"西南风转西北风","winp":"微风","temp_high":"16","temp_low":"6","humi_high":"0","humi_low":"0","weatid":"1","weatid1":"1","windid":"56","winpid":"125"},{"weaid":"1","days":"2017-10-17","week":"星期二","cityno":"beijing","citynm":"北京","cityid":"101010100","temperature":"15℃/9℃","humidity":"0%/0%","weather":"多云","weather_icon":"http://api.k780.com/upload/weather/d/1.gif","weather_icon1":"http://api.k780.com/upload/weather/n/1.gif","wind":"西南风","winp":"微风","temp_high":"15","temp_low":"9","humi_high":"0","humi_low":"0","weatid":"2","weatid1":"2","windid":"16","winpid":"125"},{"weaid":"1","days":"2017-10-18","week":"星期三","cityno":"beijing","citynm":"北京","cityid":"101010100","temperature":"14℃/8℃","humidity":"0%/0%","weather":"阴","weather_icon":"http://api.k780.com/upload/weather/d/2.gif","weather_icon1":"http://api.k780.com/upload/weather/n/2.gif","wind":"西南风","winp":"微风","temp_high":"14","temp_low":"8","humi_high":"0","humi_low":"0","weatid":"3","weatid1":"3","windid":"16","winpid":"125"}]
     */

    private String success;
    private List<ResultBean> result;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Parcelable {
        /**
         * weaid : 1
         * days : 2017-10-12
         * week : 星期四
         * cityno : beijing
         * citynm : 北京
         * cityid : 101010100
         * temperature : 13℃/6℃
         * humidity : 0%/0%
         * weather : 小雨转多云
         * weather_icon : http://api.k780.com/upload/weather/d/7.gif
         * weather_icon1 : http://api.k780.com/upload/weather/n/1.gif
         * wind : 北风转西北风
         * winp : 微风
         * temp_high : 13
         * temp_low : 6
         * humi_high : 0
         * humi_low : 0
         * weatid : 8
         * weatid1 : 2
         * windid : 64
         * winpid : 125
         */

        private String weaid;
        private String days;
        private String week;
        private String cityno;
        private String citynm;
        private String cityid;
        private String temperature;
        private String humidity;
        private String weather;
        private String weather_icon;
        private String weather_icon1;
        private String wind;
        private String winp;
        private String temp_high;
        private String temp_low;
        private String humi_high;
        private String humi_low;
        private String weatid;
        private String weatid1;
        private String windid;
        private String winpid;

        public String getWeaid() {
            return weaid;
        }

        public void setWeaid(String weaid) {
            this.weaid = weaid;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getCityno() {
            return cityno;
        }

        public void setCityno(String cityno) {
            this.cityno = cityno;
        }

        public String getCitynm() {
            return citynm;
        }

        public void setCitynm(String citynm) {
            this.citynm = citynm;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWeather_icon() {
            return weather_icon;
        }

        public void setWeather_icon(String weather_icon) {
            this.weather_icon = weather_icon;
        }

        public String getWeather_icon1() {
            return weather_icon1;
        }

        public void setWeather_icon1(String weather_icon1) {
            this.weather_icon1 = weather_icon1;
        }

        public String getWind() {
            return wind;
        }

        public void setWind(String wind) {
            this.wind = wind;
        }

        public String getWinp() {
            return winp;
        }

        public void setWinp(String winp) {
            this.winp = winp;
        }

        public String getTemp_high() {
            return temp_high;
        }

        public void setTemp_high(String temp_high) {
            this.temp_high = temp_high;
        }

        public String getTemp_low() {
            return temp_low;
        }

        public void setTemp_low(String temp_low) {
            this.temp_low = temp_low;
        }

        public String getHumi_high() {
            return humi_high;
        }

        public void setHumi_high(String humi_high) {
            this.humi_high = humi_high;
        }

        public String getHumi_low() {
            return humi_low;
        }

        public void setHumi_low(String humi_low) {
            this.humi_low = humi_low;
        }

        public String getWeatid() {
            return weatid;
        }

        public void setWeatid(String weatid) {
            this.weatid = weatid;
        }

        public String getWeatid1() {
            return weatid1;
        }

        public void setWeatid1(String weatid1) {
            this.weatid1 = weatid1;
        }

        public String getWindid() {
            return windid;
        }

        public void setWindid(String windid) {
            this.windid = windid;
        }

        public String getWinpid() {
            return winpid;
        }

        public void setWinpid(String winpid) {
            this.winpid = winpid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.weaid);
            dest.writeString(this.days);
            dest.writeString(this.week);
            dest.writeString(this.cityno);
            dest.writeString(this.citynm);
            dest.writeString(this.cityid);
            dest.writeString(this.temperature);
            dest.writeString(this.humidity);
            dest.writeString(this.weather);
            dest.writeString(this.weather_icon);
            dest.writeString(this.weather_icon1);
            dest.writeString(this.wind);
            dest.writeString(this.winp);
            dest.writeString(this.temp_high);
            dest.writeString(this.temp_low);
            dest.writeString(this.humi_high);
            dest.writeString(this.humi_low);
            dest.writeString(this.weatid);
            dest.writeString(this.weatid1);
            dest.writeString(this.windid);
            dest.writeString(this.winpid);
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.weaid = in.readString();
            this.days = in.readString();
            this.week = in.readString();
            this.cityno = in.readString();
            this.citynm = in.readString();
            this.cityid = in.readString();
            this.temperature = in.readString();
            this.humidity = in.readString();
            this.weather = in.readString();
            this.weather_icon = in.readString();
            this.weather_icon1 = in.readString();
            this.wind = in.readString();
            this.winp = in.readString();
            this.temp_high = in.readString();
            this.temp_low = in.readString();
            this.humi_high = in.readString();
            this.humi_low = in.readString();
            this.weatid = in.readString();
            this.weatid1 = in.readString();
            this.windid = in.readString();
            this.winpid = in.readString();
        }

        public static final Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel source) {
                return new ResultBean(source);
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.success);
        dest.writeList(this.result);
    }

    public TestListEntry() {
    }

    protected TestListEntry(Parcel in) {
        this.success = in.readString();
        this.result = new ArrayList<ResultBean>();
        in.readList(this.result, ResultBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<TestListEntry> CREATOR = new Parcelable.Creator<TestListEntry>() {
        @Override
        public TestListEntry createFromParcel(Parcel source) {
            return new TestListEntry(source);
        }

        @Override
        public TestListEntry[] newArray(int size) {
            return new TestListEntry[size];
        }
    };
}
