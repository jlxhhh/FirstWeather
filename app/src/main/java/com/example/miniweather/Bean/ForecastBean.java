package com.example.miniweather.Bean;

public class ForecastBean {
    private WeatherBean day1;
    private WeatherBean day2;
    private WeatherBean day3;
    private WeatherBean day4;
    private WeatherBean day5;
    private WeatherBean day6;
    private WeatherBean day7;
    private NowBean nowBean;
    public ForecastBean(WeatherBean day1, WeatherBean day2, WeatherBean day3, WeatherBean day4, WeatherBean day5, WeatherBean day6, WeatherBean day7, NowBean nowBean) {

        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
        this.day4 = day4;
        this.day5 = day5;
        this.day6 = day6;
        this.day7 = day7;
        this.nowBean = nowBean;
    }

    public ForecastBean() {
    }

    public NowBean getNowBean() {
        return nowBean;
    }

    public void setNowBean(NowBean nowBean) {
        this.nowBean = nowBean;
    }


    public WeatherBean getDay1() {
        return day1;
    }

    public void setDay1(WeatherBean day1) {
        this.day1 = day1;
    }

    public WeatherBean getDay2() {
        return day2;
    }

    public void setDay2(WeatherBean day2) {
        this.day2 = day2;
    }

    public WeatherBean getDay3() {
        return day3;
    }

    public void setDay3(WeatherBean day3) {
        this.day3 = day3;
    }

    public WeatherBean getDay4() {
        return day4;
    }

    public void setDay4(WeatherBean day4) {
        this.day4 = day4;
    }

    public WeatherBean getDay5() {
        return day5;
    }

    public void setDay5(WeatherBean day5) {
        this.day5 = day5;
    }

    public WeatherBean getDay6() {
        return day6;
    }

    public void setDay6(WeatherBean day6) {
        this.day6 = day6;
    }

    public WeatherBean getDay7() {
        return day7;
    }

    public void setDay7(WeatherBean day7) {
        this.day7 = day7;
    }
}
