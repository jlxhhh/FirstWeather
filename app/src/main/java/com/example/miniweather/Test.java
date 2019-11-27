package com.example.miniweather;

import com.example.miniweather.Util.HttpUtil;

public class Test {

    public static void main(String[]args){
       // String res = HttpUtil1.sendHttpRequest("http://www.baidu.com");
        //System.out.println(res);
        try {
            String cityId = "101010100";
            String address= "https://api.heweather.net/s6/weather/now?location="+cityId+"&key=2b7655aab90b49ef91cb77e25d2c49a5";
            
            String response = HttpUtil1.sendHttpRequest(address);
            System.out.println(response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
