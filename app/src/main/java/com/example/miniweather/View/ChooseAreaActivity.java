package com.example.miniweather.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniweather.DB.City;
import com.example.miniweather.DB.County;
import com.example.miniweather.DB.Province;
import com.example.miniweather.Util.HttpUtil;
import com.example.miniweather.R;
import com.example.miniweather.Util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaActivity extends AppCompatActivity {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<String>();
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private Province selectedProvince;
    private City selectedCity;
    private County selectedCounty;
    private int currentLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_area);
        titleText = (TextView)findViewById(R.id.title);
        backButton = (Button)findViewById(R.id.back_button);
        listView = (ListView)findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(ChooseAreaActivity.this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    selectedCounty = countyList.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("county",selectedCounty.getWeatherId());
                    System.out.println("77777777777777777777777777777777777777");
                    System.out.println(selectedCounty.getWeatherId());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if(currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        Log.i("ABC","===================");
        queryProvinces();
    }
    private void queryFromServer(String address,final String type) {
        showProgressDialog();

        HttpUtil.sendHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseText = response.body().string();
                boolean result = false;
                if (type.equals("province")) {

                    result = Utility.handleProvinceResponse(responseText);

                } else if(type.equals("city")) {
                    result = Utility.handleCityResponse(responseText,selectedProvince.getId());
                } else if(type.equals("county")) {
                    result = Utility.handleCountyResponse(responseText,selectedCity.getId());
                }
                if (result) {
                    ChooseAreaActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if (type.equals("province"))
                                queryProvinces();
                            else if (type.equals("city"))
                                queryCities();
                            else if (type.equals("county"))
                                queryCounties();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                ChooseAreaActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    private void queryProvinces() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province:provinceList)
                dataList.add(province.getProvinceName());
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            Log.i("ABC","1");
            String address = "http://guolin.tech/api/china";
            Log.i("ABC","1");
            queryFromServer(address,"province");
            Log.i("ABC","3");
        }
    }

    private void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceid = ?",String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city:cityList)
                dataList.add(city.getCityName());
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address,"city");
        }
    }

    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid = ?",String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList)
                dataList.add(county.getCountyName());
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address,"county");
        }


    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载中......");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    private void closeProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

}
