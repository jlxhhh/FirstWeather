package com.example.miniweather.Model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.miniweather.Bean.ForecastBean;
import com.example.miniweather.Bean.NowBean;
import com.example.miniweather.Bean.WeatherBean;
import com.example.miniweather.Presenter.BasePresenter;
import com.example.miniweather.Presenter.WeatherPresenter;
import com.example.miniweather.Util.HttpUtil;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherModel implements BaseModel{
    WeatherPresenter presenter;
    Handler handler = new Handler() {
        ForecastBean forecastBean = new ForecastBean() ;
        public void handleMessage(Message msg) {

           presenter.show((ForecastBean)msg.obj);

        }
    };

    public void request(final String[] address) {
        final  ForecastBean forecastBean = new ForecastBean();
        final Message message = new Message();
        message.what = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil.sendHttpRequest(address[0], new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("WeatherModel","method request() failed");
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("WeatherModel","method SendHttpRequest() run");
                        NowBean nowBean = parseNowData(response.body().string()).getNowBean();
                        forecastBean.setNowBean(nowBean);
                        message.what = message.what + 1;
                        message.obj = forecastBean;
                        if (message.what == 2)
                            handler.sendMessage(message);
                    }
                });
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil.sendHttpRequest(address[1], new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("WeatherModel","method request() failed");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("WeatherModel","method SendHttpRequest() run");
                        ForecastBean forecastBean1 = parseForecastData(response.body().string());
                        forecastBean.setDay1(forecastBean1.getDay1());
                        forecastBean.setDay2(forecastBean1.getDay2());
                        forecastBean.setDay3(forecastBean1.getDay3());
                        forecastBean.setDay4(forecastBean1.getDay4());
                        forecastBean.setDay5(forecastBean1.getDay5());
                        forecastBean.setDay6(forecastBean1.getDay6());
                        forecastBean.setDay7(forecastBean1.getDay7());
                        message.what = message.what + 1;
                        message.obj = forecastBean;
                        if (message.what == 2)
                            handler.sendMessage(message);
                    }
                });
            }
        }).start();
    }


    public ForecastBean parseNowData(String response) {
        ForecastBean forecastBean;
        try {
            Log.i("WeatherModel","method parseData() run");

            JSONObject HeWeatherKey = new JSONObject(response);
            System.out.println("==============HeWeatherKey======");
            System.out.println(HeWeatherKey);
            JSONArray HeWeatherValue = HeWeatherKey.getJSONArray("HeWeather6");
            JSONObject basic = HeWeatherValue.getJSONObject(0).getJSONObject("basic");
            JSONObject update = HeWeatherValue.getJSONObject(0).getJSONObject("update");
            JSONObject now = HeWeatherValue.getJSONObject(0).getJSONObject("now");
            String now_location = basic.getString("location");
            String now_update_time = update.getString("loc").split(" ")[1];
            String now_tmp = now.getString("tmp");
            String now_wind = now.getString("wind_dir");
            String now_status = now.getString("cond_txt");
            NowBean nowBean = new NowBean(now_update_time,now_location,now_tmp,now_wind,now_status);
            System.out.println(now_location);
            System.out.println(now_update_time);
            System.out.println(now_tmp);
            System.out.println(now_wind);
            System.out.println("++++++++++++++++++++");

            forecastBean = new ForecastBean(null,null,null,null,null,null,null,nowBean);
        }catch (Exception e){
            forecastBean =null;
        }
        return forecastBean;
    }
    public ForecastBean parseForecastData(String response) {
        ForecastBean forecastBean;
        try {
            Log.i("WeatherModel","method parseData() run");

            JSONObject HeWeatherKey = new JSONObject(response);
            System.out.println("==============HeWeatherKey======");
            System.out.println(HeWeatherKey);
            JSONArray HeWeatherValue = HeWeatherKey.getJSONArray("HeWeather6");


            System.out.println("++++++++++++++++++++");
            JSONArray daily_forecast = HeWeatherValue.getJSONObject(0).getJSONArray("daily_forecast");
            System.out.println(daily_forecast);
            JSONObject day1 = daily_forecast.getJSONObject(0);
            System.out.println(day1);
            String day1_date = day1.getString("date");
            String day1_tmp_max = day1.getString("tmp_max");
            String day1_tmp_min = day1.getString("tmp_min");
            String day1_status = day1.getString("cond_txt_d");
            WeatherBean weatherBean1 = new WeatherBean(day1_date,day1_tmp_max,day1_tmp_min,day1_status);
            System.out.println(day1_date);
            System.out.println(day1_tmp_max);
            System.out.println(day1_tmp_min);
            System.out.println(day1_status);

            JSONObject day2 = daily_forecast.getJSONObject(1);
            String day2_date = day2.getString("date");
            String day2_tmp_max = day2.getString("tmp_max");
            String day2_tmp_min = day2.getString("tmp_min");
            String day2_status = day2.getString("cond_txt_d");
            WeatherBean weatherBean2 = new WeatherBean(day2_date,day2_tmp_max,day2_tmp_min,day2_status);
            System.out.println("---------------2-------------------");
            JSONObject day3 = daily_forecast.getJSONObject(2);
            String day3_date = day3.getString("date");
            String day3_tmp_max = day3.getString("tmp_max");
            String day3_tmp_min = day3.getString("tmp_min");
            String day3_status = day3.getString("cond_txt_d");
            WeatherBean weatherBean3 = new WeatherBean(day3_date,day3_tmp_max,day3_tmp_min,day3_status);
            System.out.println("-----------------3-----------------");
            JSONObject day4 = daily_forecast.getJSONObject(3);
            String day4_date = day4.getString("date");
            String day4_tmp_max = day4.getString("tmp_max");
            String day4_tmp_min = day4.getString("tmp_min");
            String day4_status = day4.getString("cond_txt_d");
            WeatherBean weatherBean4 = new WeatherBean(day4_date,day4_tmp_max,day4_tmp_min,day4_status);
            System.out.println("----------------4------------------");
            JSONObject day5 = daily_forecast.getJSONObject(4);
            String day5_date = day5.getString("date");
            String day5_tmp_max = day5.getString("tmp_max");
            String day5_tmp_min = day5.getString("tmp_min");
            String day5_status = day5.getString("cond_txt_d");
            WeatherBean weatherBean5 = new WeatherBean(day5_date,day5_tmp_max,day5_tmp_min,day5_status);
            System.out.println("--------------5--------------------");
            JSONObject day6 = daily_forecast.getJSONObject(5);
            String day6_date = day6.getString("date");
            String day6_tmp_max = day6.getString("tmp_max");
            String day6_tmp_min = day6.getString("tmp_min");
            String day6_status = day6.getString("cond_txt_d");
            WeatherBean weatherBean6 = new WeatherBean(day6_date,day6_tmp_max,day6_tmp_min,day6_status);
            System.out.println("--------------6--------------------");
            JSONObject day7 = daily_forecast.getJSONObject(6);
            String day7_date = day7.getString("date");
            String day7_tmp_max = day7.getString("tmp_max");
            String day7_tmp_min = day7.getString("tmp_min");
            String day7_status = day7.getString("cond_txt_d");
            WeatherBean weatherBean7 = new WeatherBean(day7_date,day7_tmp_max,day7_tmp_min,day7_status);
            System.out.println("-----------------7-----------------");
            forecastBean = new ForecastBean(weatherBean1,weatherBean2,weatherBean3,weatherBean4,weatherBean5,weatherBean6,weatherBean7,null);
            System.out.println("----------------------------------");
        }catch (Exception e){
            forecastBean =null;
        }
        return forecastBean;
    }


    public void setPresenter(BasePresenter presenter) {
        this.presenter = (WeatherPresenter) presenter;
    }


}
