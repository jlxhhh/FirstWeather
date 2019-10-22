package com.example.miniweather.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniweather.Presenter.BasePresenter;
import com.example.miniweather.Presenter.WeatherPresenter;

import interfaces.heweather.com.interfacesmodule.bean.Base;
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;

public class MainActivity2 extends AppCompatActivity implements BaseView {
    private WeatherPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建presenter
        presenter = new WeatherPresenter();
        //presenter绑定view
        presenter.attachView(this);
      //  presenter.request();

    }

    public void setPresenter(WeatherPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void showError(String msg) {

    }
}
