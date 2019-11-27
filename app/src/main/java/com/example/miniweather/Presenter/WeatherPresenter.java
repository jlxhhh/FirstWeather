package com.example.miniweather.Presenter;

import com.example.miniweather.Bean.ForecastBean;
import com.example.miniweather.Model.WeatherModel;

public class WeatherPresenter extends BasePresenter {

    public void setModelAndPresenter(){
        attachModel(new WeatherModel());
        model.setPresenter(this);
    }
    public void request(String []address) {
        model.request(address);
    }
    public void show(ForecastBean forecastBean){

        view.show(forecastBean);
    }

}
