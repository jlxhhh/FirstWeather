package com.example.miniweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        setSharedPreference();
        final String [] cityAndNumber = getData();

        String [] city = getCity(cityAndNumber);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CityActivity.this,android.R.layout.simple_list_item_1,city);
        ListView listView = findViewById(R.id.cityList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("city",cityAndNumber[position]);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    public String[] getCity(String [] strings){
        String [] city = new String[strings.length];
        for (int i = 0;i < strings.length;++i) {
            city[i] = strings[i].split(" ")[0];
        }
        return city;
    }
    public String [] getData(){
        SharedPreferences sharedPreferences = getSharedPreferences("CityList",MODE_PRIVATE);
        Map city = sharedPreferences.getAll();
        int listSize = city.size();
        String [] cityList = new String[listSize];
        Set set = city.keySet();
        int index = 0;
        String key;
        String value;
        for (Iterator iter = set.iterator();iter.hasNext();) {
            key = (String)iter.next();
            value = (String)city.get(key);
            cityList[index++] = key + " " +value;
        }
        return cityList;
    }
    void setSharedPreference(){
        SharedPreferences.Editor editor = getSharedPreferences("CityList",MODE_PRIVATE).edit();
        editor.putString("北京","101010100");
        editor.putString("海淀","101010200");
        editor.putString("朝阳","101010300");
        editor.putString("顺义","101010400");
        editor.putString("怀柔","101010500");
        editor.putString("通州","101010600");
        editor.putString("昌平","101010700");
        editor.putString("延庆","101010800");
        editor.putString("丰台","101010900");
        editor.putString("石景山","101011000");
        editor.putString("大兴","101011100");
        editor.putString("房山","101011200");
        editor.putString("密云","101011300");
        editor.apply();
    }
}
