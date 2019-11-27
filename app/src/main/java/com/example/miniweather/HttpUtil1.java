package com.example.miniweather;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class HttpUtil1 {

    public static String sendHttpRequest(String address){
        HttpURLConnection connection = null;
        try{
    //        URL url = new URL("https://www.baidu.com");

            URL url = new URL(address);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
        //    connection.setRequestProperty("User-Agent", "Mozilla/5.0 ");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoInput(true);
            //设置为true会将请求变为post导致响应为空
            connection.setDoOutput(false);
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null ){
                response.append(line);

            }
            Log.i("ppppp",new Integer(connection.getResponseCode()).toString());
            Log.i("ppppp",response.toString());

            return response.toString();
        }catch (Exception e){
//            Log.d("qq",e.getMessage());
            e.printStackTrace();
            return "REQUEST_FAILED";
        }finally {
            if(connection != null)
                connection.disconnect();
        }

    }
}
