package com.example.miniweather.Model;

import com.example.miniweather.Presenter.BasePresenter;
import com.example.miniweather.Presenter.WeatherPresenter;

import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;

public interface BaseModel {
    public abstract void request(String[] address);
    public abstract void setPresenter(BasePresenter presenter);

}
