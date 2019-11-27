package com.example.miniweather.View;

import android.content.Context;
import android.util.AttributeSet;

import com.example.miniweather.Bean.ForecastBean;
import com.example.miniweather.Presenter.BasePresenter;

public interface BaseView {
    void showError(String msg);
    public abstract void show(ForecastBean forecastBean);

}
