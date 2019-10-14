package com.example.miniweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.air.forecast.AirForecast;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow;
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.Forecast;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;
import interfaces.heweather.com.interfacesmodule.view.HeWeather.OnResultAirNowBeansListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherStatus {
    private static WeatherStatus weatherStatus;
    final private static String TAG = "WeatherStatus";
    private String temperature = "";
    private String status = "";
    private String wind = "";
    private String lowAndHighTemperature = "";
    private String icon = "";
    private String realTime ="";
    public static HashMap<String,String> map = new HashMap<String,String>(){{
        put("晴","sunny");
        put("小雨","rainy");
        put("中雨","rainy");
        put("大雨","rainy");
        put("暴雨","rainy");
        put("阴","overcast");
        put("多云","cloudy");
    }
    };
    public String getRealTime() {
        return realTime;
    }

    public String getIcon() {
        return icon;
    }

    public String getLowAndHighTemperature() {
        return lowAndHighTemperature;
    }

    public String getStatus() {
        return status;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWind() {
        return wind;
    }

    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setLowAndHighTemperature(String lowAndHighTemperature) {
        this.lowAndHighTemperature = lowAndHighTemperature;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
    public static WeatherStatus getCurrentWeather2(final Context context){
        return  new WeatherStatus().getCurrentWeather2NotStatic(context);
    }


    public  WeatherStatus getCurrentWeather2NotStatic(final Context context){
        weatherStatus =  new WeatherStatus();
        //初始化
        HeConfig.init("HE1910071425251130","2da1708c928a425bbe6f4e5c40bc7cf9");
        HeConfig.switchToFreeServerNode();
        //获取天气情况
        HeWeather.getWeatherNow(context, "朝阳,北京", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherNowBeanListener() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG,"onError:hhh");
            }
            @Override
            public void onSuccess(Now now) {
                Log.i(TAG,"weather:"+new Gson().toJson(now));
                weatherStatus.setTemperature(now.getNow().getFl());
                weatherStatus.setStatus(now.getNow().getCond_txt());
                weatherStatus.setWind(now.getNow().getWind_dir());
                weatherStatus.setIcon(getIcon(now.getNow().getCond_txt()));
                weatherStatus.setRealTime(now.getNow().getCond_txt()+"(实时)");

            }
        });
        Log.i(TAG,weatherStatus.getRealTime());
        HeWeather.getWeatherForecast(context, "朝阳,北京", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherForecastBeanListener() {
            @Override
            public void onError(Throwable throwable) {
            }
            @Override
            public void onSuccess(Forecast forecast) {
             //   Log.i(TAG,new Gson().toJson(forecast.getDaily_forecast()));
               String max = forecast.getDaily_forecast().get(0).getTmp_max();
               String min = forecast.getDaily_forecast().get(0).getTmp_min();
               weatherStatus.setLowAndHighTemperature(min+"℃"+" ~ "+max+"℃");
            }
        });
      //  Log.i(TAG,weatherStatus.toString());
      //  Log.i(TAG,weatherStatus.getLowAndHighTemperature());
        return weatherStatus;
    }

    public static WeatherStatus getCurrentWeather1(){
        //中华万年历
        Log.i("MainActivity","获取天气状况");
        WeatherStatus weatherStatus = null;
        //中国万年历天气
        String address = "http://wthrcdn.etouch.cn/weather_mini?citykey=101011100";

        try {
            Log.d("MainActivity","1");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(address)
                    .build();
          //  Log.d("MainActivity","2");
            Response response = client.newCall(request).execute();
          //  Log.d("MainActivity","3");
            String responseData = response.body().string();

            weatherStatus =  parseJSONWithJSONObject(responseData);
         //   Log.d("MainActivity","4");
        } catch (Exception e) {
            Log.d("MianActivity",e.getMessage());
            weatherStatus = null;
            e.printStackTrace();
        }
        //获取数据失败
        if(weatherStatus == null){
            Log.w("MainActivity","无法联网，获取实时数据失败");
            weatherStatus = new WeatherStatus();
            weatherStatus.setTemperature("28");
            weatherStatus.setStatus("晴天");
            weatherStatus.setWind("西风");
            weatherStatus.setLowAndHighTemperature("25~30℃");
            weatherStatus.setIcon("sunny");
            weatherStatus.setRealTime("晴天(非实时)");
        }
        Log.i("MainActivity","获取天气状况完成");
        return weatherStatus;
    }
    public static WeatherStatus parseJSONWithJSONObject(String jsonData){
        Log.i("MainActivity","开始解析天气json");
        WeatherStatus weatherStatus = null;
        try {
            System.out.println(jsonData);
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject data = jsonObject.getJSONObject("data");
            String temerature = data.getString("wendu");
            String status = data.getJSONArray("forecast").getJSONObject(0).getString("type");
            String wind = data.getJSONArray("forecast").getJSONObject(0).getString("fengxiang");
            String lowAndHighTemperature = data.getJSONArray("forecast").getJSONObject(0).getString("low").substring(2)+" ~"+data.getJSONArray("forecast").getJSONObject(0).getString("high").substring(2);
            String icon = getIcon(status);
            String realTime = status + "(实时)";
//            Log.d("MainActivity",temerature);
//            Log.d("MainActivity",status);
//            Log.d("MainActivity",wind);
//            Log.d("MainActivity",lowAndHighTemperature);
//            Log.d("MainActivity",icon);
//            Log.d("MainActivity",realTime);
            weatherStatus = new WeatherStatus();
            weatherStatus.setTemperature(temerature);
            weatherStatus.setStatus(status);
            weatherStatus.setWind(wind);
            weatherStatus.setLowAndHighTemperature(lowAndHighTemperature);
            weatherStatus.setIcon(icon);
            weatherStatus.setRealTime(realTime);
        } catch (JSONException e) {
            weatherStatus = null;
            e.printStackTrace();
            Log.w("MainActivity","解析天气Json失败");
        }
        Log.i("MainActivity","解析天气json完成");
        return weatherStatus;
    }
    public static String getIcon(String status){
        if(map.get(status) == null) {

            Log.w("MainActivity","不能找到天气图标2");
            return "sunny";
        }
        else
            return map.get(status);
    }

}
