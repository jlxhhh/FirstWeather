package com.example.miniweather.Bean;

public class NowBean {
    private String time ;
    private String location;
    private String temperature;
    private String wind;
    private String status;


    public NowBean(String time, String location, String temperature, String wind,String status) {
        this.time = time;
        this.location = location;
        this.temperature = temperature;
        this.wind = wind;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
