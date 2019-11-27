package com.example.miniweather.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.miniweather.Bean.ForecastBean;
import com.example.miniweather.Presenter.WeatherPresenter;
import com.example.miniweather.R;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
                startActivityForResult(intent,1);
            }
        });

    }

    public void initRequest() {
        address = new String[2];
        SharedPreferences sharedPreferences = getSharedPreferences("PreData",MODE_PRIVATE);
        weatherId = sharedPreferences.getString("weatherId","CN101011100");
        address[0]= "https://free-api.heweather.net/s6/weather/now?location="+ weatherId +"&key=cc33b9a52d6e48de852477798980b76e";
        address[1]= "https://free-api.heweather.net/s6/weather/forecast?location="+ weatherId +"&key=cc33b9a52d6e48de852477798980b76e";
        presenter.setModelAndPresenter();
        presenter.request(address);

    }
    public void show(ForecastBean forecastBean){
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
    protected void onStart(){
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
        Log.e("MainActivity_S",msg);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    final String weatherId1 = intent.getStringExtra("county");
                    System.out.println("0000000000000000000000000000000000000000000");
                    System.out.print(weatherId1);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            weatherId = weatherId1;
                            address[0]= "https://free-api.heweather.net/s6/weather/now?location="+ weatherId +"&key=cc33b9a52d6e48de852477798980b76e";
                            address[1]= "https://free-api.heweather.net/s6/weather/forecast?location="+ weatherId +"&key=cc33b9a52d6e48de852477798980b76e";
                            presenter.request(address);
                            SharedPreferences.Editor editor = getSharedPreferences("PreData",MODE_PRIVATE).edit();
                            editor.putString("weatherId",weatherId);
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
