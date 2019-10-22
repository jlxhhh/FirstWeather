package com.example.miniweather.Bean;

public class ForecastBean {
    private WeatherBean today;
    private WeatherBean oneDay;
    private WeatherBean twoDay;
    private WeatherBean threeDay;
    private WeatherBean fourDay;
    private WeatherBean fiveDay;
    private WeatherBean sixDay;
    private WeatherBean sevenDay;

    public WeatherBean getToday() {
        return today;
    }

    public void setToday(WeatherBean today) {
        this.today = today;
    }

    public WeatherBean getOneDay() {
        return oneDay;
    }

    public void setOneDay(WeatherBean oneDay) {
        this.oneDay = oneDay;
    }

    public WeatherBean getTwoDay() {
        return twoDay;
    }

    public void setTwoDay(WeatherBean twoDay) {
        this.twoDay = twoDay;
    }

    public WeatherBean getThreeDay() {
        return threeDay;
    }

    public void setThreeDay(WeatherBean threeDay) {
        this.threeDay = threeDay;
    }

    public WeatherBean getFourDay() {
        return fourDay;
    }

    public void setFourDay(WeatherBean fourDay) {
        this.fourDay = fourDay;
    }

    public WeatherBean getFiveDay() {
        return fiveDay;
    }

    public void setFiveDay(WeatherBean fiveDay) {
        this.fiveDay = fiveDay;
    }

    public WeatherBean getSixDay() {
        return sixDay;
    }

    public void setSixDay(WeatherBean sixDay) {
        this.sixDay = sixDay;
    }

    public WeatherBean getSevenDay() {
        return sevenDay;
    }

    public void setSevenDay(WeatherBean sevenDay) {
        this.sevenDay = sevenDay;
    }
}
