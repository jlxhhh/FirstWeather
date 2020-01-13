package com.example.miniweather.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.miniweather.Bean.ForecastBean;
import com.example.miniweather.Presenter.WeatherPresenter;
import com.example.miniweather.R;
import com.example.miniweather.Util.MyLocationListener;


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
    SwipeRefreshLayout swipeRefreshLayout;


    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();

        option.setIsNeedAddress(true);
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BDLocation bdLocation = mLocationClient.getLastKnownLocation();
        System.out.println("+++++++++++++++++++24342+++++++++");
        System.out.println(bdLocation);



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

                BDLocation bdLocation = mLocationClient.getLastKnownLocation();
                System.out.println("+++++++++++++++++++1111111111111+++++++++");
                System.out.println(bdLocation.getCityCode());

                Toast.makeText(MainActivity2.this, "刷新", Toast.LENGTH_LONG).show();//刷新时要做的事情
                request("CN101011100");
                swipeRefreshLayout.setRefreshing(false);//刷新完成



            }
        });
    }



    /**
     * 初始化定位参数配置
     */

    private void initLocationOption() {
//定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动

//声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();

//注册监听函数
        mLocationClient.registerLocationListener(myListener);
//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("gcj02");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000);
//可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
//可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
//可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
//可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
//可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(3000,1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        mLocationClient.setLocOption(locationOption);
//开始定位
        mLocationClient.start();
    }













    public void request(String addressId) {
        address[0] = "https://free-api.heweather.net/s6/weather/now?location=" + addressId + "&key=cc33b9a52d6e48de852477798980b76e";
        address[1] = "https://free-api.heweather.net/s6/weather/forecast?location=" + addressId + "&key=cc33b9a52d6e48de852477798980b76e";
        presenter.setModelAndPresenter();
        presenter.request(address);
    }

    public void initRequest() {
        address = new String[2];
        SharedPreferences sharedPreferences = getSharedPreferences("PreData", MODE_PRIVATE);
        weatherId = sharedPreferences.getString("weatherId", "CN101011100");
        request(weatherId);

    }

    public void show(ForecastBean forecastBean) {
        System.out.println("===========hhhhhhhhhhhhh===============");
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
                    System.out.print(weatherId1);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            request(weatherId1);
                            SharedPreferences.Editor editor = getSharedPreferences("PreData", MODE_PRIVATE).edit();
                            editor.putString("weatherId", weatherId);
                            editor.apply();
                            System.out.println("weatehrweathersadassdasasddassddasd");
                        }
                    }).start();

                }
                break;
            default:
        }
    }
}
