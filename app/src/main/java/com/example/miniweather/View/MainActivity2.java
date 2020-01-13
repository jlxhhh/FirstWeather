package com.example.miniweather.View;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.miniweather.Bean.ForecastBean;
import com.example.miniweather.Presenter.WeatherPresenter;
import com.example.miniweather.R;
import com.example.miniweather.Util.HttpUtil;
import com.example.miniweather.Util.MyLocationListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class MainActivity2 extends AppCompatActivity implements BaseView {
    private WeatherPresenter presenter;
    private String[] address;
    private String weatherId;
    TextView changeCity;
    TextView location;
    TextView updateTime;
    TextView temperature;
    TextView status;
    TextView wind;
    LinearLayout forecast1;
    LinearLayout forecast2;
    LinearLayout forecast3;
    LinearLayout forecast4;
    LinearLayout forecast5;
    LinearLayout forecast6;
    TextView forecastTmp;
    TextView forecastStatus;
    TextView forecastTmpMax;
    TextView forecastTmpMin;
    ConstraintLayout listLayout;
    SwipeRefreshLayout swipeRefreshLayout;


    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initRequestLocation();

        setContentView(R.layout.activity_main2);
        System.out.println("????");
        //创建presenter
        presenter = new WeatherPresenter();
        //presenter绑定view
        setPresenter(presenter);
        presenter.attachView(MainActivity2.this);
        initRequest();
        changeCity = findViewById(R.id.title_change_city);
        changeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, ChooseAreaActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        setSwipeFresh();


    }

    public void setSwipeFresh() {
        swipeRefreshLayout = findViewById(R.id.fresh);
        //设置进度条颜色
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.YELLOW, Color.GREEN, Color.RED);
        //设置下拉触发距离
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        //设置进度条背景颜色
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置圆圈大小
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String weather;
                try {
                    BDLocation bdLocation = mLocationClient.getLastKnownLocation();
                    weather = getCityCode(bdLocation.getDistrict());
                    Log.i("ASDF", getCityCode(bdLocation.getDistrict()));
                } catch (Exception e) {
                    weather = weatherId;
                }
                Toast.makeText(MainActivity2.this, "刷新", Toast.LENGTH_LONG).show();//刷新时要做的事情
                request(weather);
                swipeRefreshLayout.setRefreshing(false);//刷新完成

            }
        });
    }


    public void initRequestLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PERMISSION_GRANTED) {// 没有权限，申请权限。
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);

            mLocationClient = new LocationClient(getApplicationContext());
            //声明LocationClient类
            mLocationClient.registerLocationListener(myListener);
            LocationClientOption option = new LocationClientOption();
            option.setIsNeedAddress(true);
            option.setScanSpan(10 * 1000);
            option.setOpenGps(true);
//        option.setOnceLocation(true);
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            mLocationClient.setLocOption(option);
            mLocationClient.start();
        }
    }

    public String getCityCode(String city) {
        String address = "https://search.heweather.net/find?location=" + city + "&key=cc33b9a52d6e48de852477798980b76e";
        final boolean[] requestEnd = new boolean[1];
        final String[] cityCode = new String[1];
        while (!requestEnd[0]) {
            HttpUtil.sendHttpRequest(address, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseBody = response.body().string();
                    JSONObject HeWeatherKey = null;
                    try {
                        HeWeatherKey = new JSONObject(responseBody);
                        JSONArray HeWeatherValue = HeWeatherKey.getJSONArray("HeWeather6");
                        JSONArray basic = HeWeatherValue.getJSONObject(0).getJSONArray("basic");
                        JSONObject cid = basic.getJSONObject(0);
                        cityCode[0] = cid.getString("cid");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    requestEnd[0] = true;
                }
            });
        }
        return cityCode[0];
    }


    public void request(String addressId) {
        address[0] = "https://free-api.heweather.net/s6/weather/now?location=" + addressId + "&key=cc33b9a52d6e48de852477798980b76e";
        address[1] = "https://free-api.heweather.net/s6/weather/forecast?location=" + addressId + "&key=cc33b9a52d6e48de852477798980b76e";
        presenter.setModelAndPresenter();
        presenter.request(address);
        SharedPreferences.Editor editor = getSharedPreferences("PreData", MODE_PRIVATE).edit();
        editor.putString("weatherId", addressId);
        editor.apply();
    }

    public void initRequest() {
        address = new String[2];
        SharedPreferences sharedPreferences = getSharedPreferences("PreData", MODE_PRIVATE);
        weatherId = sharedPreferences.getString("weatherId", "CN101011100");

        request(weatherId);

    }

    public void show(ForecastBean forecastBean) {
        System.out.println("===========hhhhhhhhhhhhh===============");
        Log.i("LLLL", forecastBean.getNowBean().getLocation());
        location = findViewById(R.id.title_location);
        location.setText(forecastBean.getNowBean().getLocation());

        updateTime = findViewById(R.id.title_time);
        updateTime.setText(forecastBean.getNowBean().getTime());

        temperature = findViewById(R.id.temperature);
        temperature.setText(forecastBean.getNowBean().getTemperature() + "℃");

        wind = findViewById(R.id.wind);
        wind.setText(forecastBean.getNowBean().getWind());

        status = findViewById(R.id.status);
        status.setText(forecastBean.getNowBean().getStatus());

        forecast1 = findViewById(R.id.forecast1);
        forecastTmp = forecast1.findViewById(R.id.forecast_tmp);
        forecastStatus = forecast1.findViewById(R.id.forecast_status);
        forecastTmpMax = forecast1.findViewById(R.id.forecast_tmp_max);
        forecastTmpMin = forecast1.findViewById(R.id.forecast_tmp_min);
        forecastTmp.setText(forecastBean.getDay1().getDate());
        forecastStatus.setText(forecastBean.getDay1().getStatus());
        forecastTmpMax.setText(forecastBean.getDay1().getTmp_max());
        forecastTmpMin.setText(forecastBean.getDay1().getTmp_min());

        forecast2 = findViewById(R.id.forecast2);
        forecastTmp = forecast2.findViewById(R.id.forecast_tmp);
        forecastStatus = forecast2.findViewById(R.id.forecast_status);
        forecastTmpMax = forecast2.findViewById(R.id.forecast_tmp_max);
        forecastTmpMin = forecast2.findViewById(R.id.forecast_tmp_min);
        forecastTmp.setText(forecastBean.getDay2().getDate());
        forecastStatus.setText(forecastBean.getDay2().getStatus());
        forecastTmpMax.setText(forecastBean.getDay2().getTmp_max());
        forecastTmpMin.setText(forecastBean.getDay2().getTmp_min());

        forecast3 = findViewById(R.id.forecast3);
        forecastTmp = forecast3.findViewById(R.id.forecast_tmp);
        forecastStatus = forecast3.findViewById(R.id.forecast_status);
        forecastTmpMax = forecast3.findViewById(R.id.forecast_tmp_max);
        forecastTmpMin = forecast3.findViewById(R.id.forecast_tmp_min);
        forecastTmp.setText(forecastBean.getDay3().getDate());
        forecastStatus.setText(forecastBean.getDay3().getStatus());
        forecastTmpMax.setText(forecastBean.getDay3().getTmp_max());
        forecastTmpMin.setText(forecastBean.getDay3().getTmp_min());

        forecast4 = findViewById(R.id.forecast4);
        forecastTmp = forecast4.findViewById(R.id.forecast_tmp);
        forecastStatus = forecast4.findViewById(R.id.forecast_status);
        forecastTmpMax = forecast4.findViewById(R.id.forecast_tmp_max);
        forecastTmpMin = forecast4.findViewById(R.id.forecast_tmp_min);
        forecastTmp.setText(forecastBean.getDay4().getDate());
        forecastStatus.setText(forecastBean.getDay4().getStatus());
        forecastTmpMax.setText(forecastBean.getDay4().getTmp_max());
        forecastTmpMin.setText(forecastBean.getDay4().getTmp_min());

        forecast5 = findViewById(R.id.forecast5);
        forecastTmp = forecast5.findViewById(R.id.forecast_tmp);
        forecastStatus = forecast5.findViewById(R.id.forecast_status);
        forecastTmpMax = forecast5.findViewById(R.id.forecast_tmp_max);
        forecastTmpMin = forecast5.findViewById(R.id.forecast_tmp_min);
        forecastTmp.setText(forecastBean.getDay5().getDate());
        forecastStatus.setText(forecastBean.getDay5().getStatus());
        forecastTmpMax.setText(forecastBean.getDay5().getTmp_max());
        forecastTmpMin.setText(forecastBean.getDay5().getTmp_min());

        forecast6 = findViewById(R.id.forecast6);
        forecastTmp = forecast6.findViewById(R.id.forecast_tmp);
        forecastStatus = forecast6.findViewById(R.id.forecast_status);
        forecastTmpMax = forecast6.findViewById(R.id.forecast_tmp_max);
        forecastTmpMin = forecast6.findViewById(R.id.forecast_tmp_min);
        forecastTmp.setText(forecastBean.getDay6().getDate());
        forecastStatus.setText(forecastBean.getDay6().getStatus());
        forecastTmpMax.setText(forecastBean.getDay6().getTmp_max());
        forecastTmpMin.setText(forecastBean.getDay6().getTmp_min());

        listLayout = findViewById(R.id.list_layout);
        String nowStatus = forecastBean.getNowBean().getStatus();
        if(nowStatus.equals("晴"))
            listLayout.setBackground(this.getResources().getDrawable(R.drawable.bg2));
        else if(nowStatus.equals("阴"))
            listLayout.setBackground(this.getResources().getDrawable(R.drawable.bg3));

    }

    public void setPresenter(WeatherPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachModel();
        presenter.detachView();
    }

    @Override
    public void showError(String msg) {
        Log.e("MainActivity_S", msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    final String weatherId1 = intent.getStringExtra("county");
                    System.out.println("0000000000000000000000000000000000000000000");
                    Log.i("WEA", weatherId);
                    System.out.print(weatherId1);

                    weatherId = weatherId1;
                    Log.i("LLLL", weatherId);
                    request(weatherId);

                    SharedPreferences.Editor editor = getSharedPreferences("PreData", MODE_PRIVATE).edit();
                    editor.putString("weatherId", weatherId);
                    editor.apply();
                    System.out.println("weatehrweathersadassdasasddassddasd");

                }
                break;
            default:
        }
    }
}
