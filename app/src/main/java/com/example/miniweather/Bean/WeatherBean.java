package com.example.miniweather.Bean;

public class WeatherBean {
    private String date;
    private String tmp_max;
    private String tmp_min;
    private String status;

    public WeatherBean(String date, String tmp_max, String tmp_min, String status) {
        this.date = date;
        this.tmp_max = tmp_max;
        this.tmp_min = tmp_min;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTmp_max() {
        return tmp_max;
    }

    public void setTmp_max(String tmp_max) {
        this.tmp_max = tmp_max;
    }

    public String getTmp_min() {
        return tmp_min;
    }

    public void setTmp_min(String tmp_min) {
        this.tmp_min = tmp_min;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
