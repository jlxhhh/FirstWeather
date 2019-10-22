package com.example.miniweather.Bean;

public class WeatherBean {
    private String temperature = "";
    private String status = "";
    private String wind = "";
    private String lowAndHighTemperature = "";
    private String icon = "";
    private String realTime ="";

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getLowAndHighTemperature() {
        return lowAndHighTemperature;
    }

    public void setLowAndHighTemperature(String lowAndHighTemperature) {
        this.lowAndHighTemperature = lowAndHighTemperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRealTime() {
        return realTime;
    }

    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }
}
