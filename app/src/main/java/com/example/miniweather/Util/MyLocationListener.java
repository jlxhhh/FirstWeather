package com.example.miniweather.Util;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

public class MyLocationListener extends BDAbstractLocationListener {
    String district = null;
    public String getDistrict(){
        //仅第一次进入的时候自动定位
        String d = district;
        district =null;
        return d;
    }
    @Override
    public void onReceiveLocation(BDLocation location) {

        // TODO Auto-generated method stub
        if (null != location && location.getLocType() != BDLocation.TypeServerError) {
            district = location.getDistrict();
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nlocType : ");// 定位类型
            sb.append(location.getLocType());
            sb.append("\nlocType description : ");// *****对应的定位类型说明*****
            sb.append(location.getLocTypeDescription());
            sb.append("\nlatitude : ");// 纬度
            sb.append(location.getLatitude());
            sb.append("\nlongtitude : ");// 经度
            sb.append(location.getLongitude());
            sb.append("\nradius : ");// 半径
            sb.append(location.getRadius());
            sb.append("\nCountryCode : ");// 国家码
            sb.append(location.getCountryCode());
            sb.append("\nProvince : ");// 获取省份
            sb.append(location.getProvince());
            sb.append("\nCountry : ");// 国家名称
            sb.append(location.getCountry());
            sb.append("\ncitycode : ");// 城市编码
            sb.append(location.getCityCode());
            sb.append("\ncity : ");// 城市
            sb.append(location.getCity());
            sb.append("\nDistrict : ");// 区
            sb.append(location.getDistrict());
            sb.append("\nTown : ");// 获取镇信息
            sb.append(location.getTown());
            sb.append("\nStreet : ");// 街道
            sb.append(location.getStreet());
            sb.append("\naddr : ");// 地址信息
            sb.append(location.getAddrStr());
            System.out.println(sb);
        }
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {
        super.onConnectHotSpotMessage(s, i);
    }

    @Override
    public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {
        super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage);
        StringBuffer sb = new StringBuffer(256);
        sb.append("诊断结果: ");
        if (locType == BDLocation.TypeNetWorkLocation) {
            if (diagnosticType == 1) {
                sb.append("网络定位成功，没有开启GPS，建议打开GPS会更好");
                sb.append("\n" + diagnosticMessage);
            } else if (diagnosticType == 2) {
                sb.append("网络定位成功，没有开启Wi-Fi，建议打开Wi-Fi会更好");
                sb.append("\n" + diagnosticMessage);
            }
        } else if (locType == BDLocation.TypeOffLineLocationFail) {
            if (diagnosticType == 3) {
                sb.append("定位失败，请您检查您的网络状态");
                sb.append("\n" + diagnosticMessage);
            }
        } else if (locType == BDLocation.TypeCriteriaException) {
            if (diagnosticType == 4) {
                sb.append("定位失败，无法获取任何有效定位依据");
                sb.append("\n" + diagnosticMessage);
            } else if (diagnosticType == 5) {
                sb.append("定位失败，无法获取有效定位依据，请检查运营商网络或者Wi-Fi网络是否正常开启，尝试重新请求定位");
                sb.append(diagnosticMessage);
            } else if (diagnosticType == 6) {
                sb.append("定位失败，无法获取有效定位依据，请尝试插入一张sim卡或打开Wi-Fi重试");
                sb.append("\n" + diagnosticMessage);
            } else if (diagnosticType == 7) {
                sb.append("定位失败，飞行模式下无法获取有效定位依据，请关闭飞行模式重试");
                sb.append("\n" + diagnosticMessage);
            } else if (diagnosticType == 9) {
                sb.append("定位失败，无法获取任何有效定位依据");
                sb.append("\n" + diagnosticMessage);
            }
        } else if (locType == BDLocation.TypeServerError) {
            if (diagnosticType == 8) {
                sb.append("定位失败，请确认您定位的开关打开状态，是否赋予APP定位权限");
                sb.append("\n" + diagnosticMessage);
            }
        }

    }

}