package com.example.miniweather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test {

    public static void main(String[]args){
       // String res = HttpUtil.sendHttpRequest("http://www.baidu.com");
        //System.out.println(res);
        try {

            String response = HttpUtil.sendHttpRequest("hhh");
            System.out.println(response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
