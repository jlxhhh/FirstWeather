package com.example.miniweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miniweather.Presenter.WeatherPresenter;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    protected WeatherStatus weatherStatus;
    protected WeatherDate weatherDate;
    public  HashMap<String,Integer> map = new HashMap<String,Integer>(){{
        put("sunny",R.mipmap.sunny);
        put("cloudy",R.mipmap.cloudy);
        put("rainy",R.mipmap.rainy);
        put("overcast",R.mipmap.overcast);

    }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Weather weather = Weather.getCurrentWeather();
//                Log.d("MainActivity","#########");
//                Log.d("MainActivity",weather.getTemperature());
//                Log.d("MainActivity",weather.getStatus());
//                Log.d("MainActivity",weather.getWind());
//                Log.d("MainActivity",weather.getLowAndHighTemperature());
//                Log.d("MainActivity",weather.getIcon());
//                Log.d("MainActivity",weather.getRealTime());
//                WeatherDate time = WeatherDate.getCurrentDate();
//                Log.d("MainActivity",time.getSolor_date());
//                Log.d("MainActivity",WeatherDate.getWeek());
//                Log.d("MainActivity",time.getLunar_data());
//            }
//        }).start();

        //设置定时器
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                setWeatherDate();
                setWeatherStatus();
            }
        };
        timer.schedule(timerTask,0,30*60*1000);
        TextView changeCity = findViewById(R.id.changeCity);
        changeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CityActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    final String city = intent.getStringExtra("city");
                    Log.i("qqq",city);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            weatherStatus = WeatherStatus.getCurrentWeather1(city);
                            updateWeatherStatusView();
                        }
                    }).start();
                    SharedPreferences.Editor editor = getSharedPreferences("PreData",MODE_PRIVATE).edit();
                    editor.putString("city",city);
                    editor.apply();
                }
                break;
                default:
        }
    }

    protected void setWeatherDate(){
        //另起一个线程来获取时间
        new Thread(new Runnable() {
            @Override
            public void run() {
                weatherDate = WeatherDate.getCurrentDate();
                updateWeatherDateView();
            }
        }).start();

    }
    protected void updateWeatherDateView(){
        //在主线程更新时间
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView weatherDateView = (TextView)findViewById(R.id.weatherDate);
                String text = weatherDate.getSolor_date()+"        "+weatherDate.getWeek()+"       "+weatherDate.getLunar_data();
                weatherDateView.setText(text);
            }
        });
    }
    protected void setWeatherStatus(){
        new Thread(new Runnable() {
            @Override
            public void run() {

//                weatherStatus = WeatherStatus.getCurrentWeather2(MainActivity.this);
//                Log.i("test",weatherStatus.getLowAndHighTemperature());
//             //   System.out.println(weatherStatus.getStatus());
//                if(weatherStatus == null) {
//
//                    weatherStatus = WeatherStatus.getCurrentWeather1();
//                }

                SharedPreferences sharedPreferences = getSharedPreferences("PreData",MODE_PRIVATE);
                String city = sharedPreferences.getString("city","大兴 101011100");
              //  Log.i("test",city);
                weatherStatus = WeatherStatus.getCurrentWeather1(city);
                updateWeatherStatusView();
            }
        }).start();
    }
    protected void updateWeatherStatusView(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView temperatureView = (TextView)findViewById(R.id.temperature);
                temperatureView.setText(weatherStatus.getTemperature());

                TextView statusView = (TextView)findViewById(R.id.status);
                statusView.setText(weatherStatus.getStatus());

                TextView windView = (TextView)findViewById(R.id.wind);
                windView.setText(weatherStatus.getWind());

                TextView lowAndHighTemperatureView = (TextView)findViewById(R.id.lowAndHighTemperature);
                lowAndHighTemperatureView.setText(weatherStatus.getLowAndHighTemperature());

                ImageView iconView = (ImageView)findViewById(R.id.icon);
                iconView.setImageResource(getIconId(weatherStatus.getIcon()));

                TextView realTimeView = (TextView)findViewById(R.id.realTime);
                realTimeView.setText(weatherStatus.getRealTime());

                TextView cityView = findViewById(R.id.city);
                cityView.setText(weatherStatus.getCity().split(" ")[0]);
                Log.i("xxx",weatherStatus.getCity().split(" ")[0]);
            }
        });
    }
    protected int getIconId(String iconName){
        if(map.get(iconName) == null){
            Log.w("MainActivity","不能找到天气图标2");
            return R.mipmap.sunny;
        }else
            return map.get(iconName);

    }


}
