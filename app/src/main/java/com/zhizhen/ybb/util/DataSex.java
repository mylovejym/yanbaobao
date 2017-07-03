package com.zhizhen.ybb.util;

import com.google.gson.Gson;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.Dashboard;
import com.zhizhen.ybb.bean.GetStaticLateral;
import com.zhizhen.ybb.bean.GetStatistics;
import com.zhizhen.ybb.bean.Histogram;
import com.zhizhen.ybb.bean.SitInfo;
import com.zhizhen.ybb.bean.StaticLateral;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 获取数据 解析 json
 * Created by tc on 2017/6/2.
 */

public class DataSex {


    private static String[] keys = {"dashboard", "histogram", "sit_info", "static_lateral","max_time"};
    public  static BaseClassBean<GetStatistics> sex(String str) {
        Gson gson = new Gson();
        List<Map<String, Object>> maps = new ArrayList<>();
        String data = null;
        List<Dashboard> dashboard = new ArrayList<>();
        List<Histogram> histogram = new ArrayList<>();
        List<StaticLateral> static_lateral= new ArrayList<>();
        List<SitInfo> sit_info = new ArrayList<>();
        Map<String, Object> map = new LinkedHashMap();
        String max_time = "";
        BaseClassBean<GetStatistics> cla = new BaseClassBean<>();

        map = gson.fromJson(str, Map.class);
        for (Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("data")) {
                data = entry.getValue().toString();
            } else if (entry.getKey().equals("status")) {
                cla.setStatus("" + (int) Double.parseDouble(entry.getValue().toString()));
            } else if (entry.getKey().equals("statusInfo")) {
                cla.setStatusInfo(entry.getValue().toString());
            }
        }
        map = gson.fromJson(data, Map.class);
        for (Entry<String, Object> entry : map.entrySet()) {
            for (int i = 0; i < keys.length; i++) {
                if (entry.getKey().equals(keys[i])) {
                    if (keys[i].equals("sit_info")) {
                        SitInfo sitInfo = gson.fromJson(entry.getValue().toString(), SitInfo.class);
                        sit_info.add(sitInfo);
                    }else if(keys[i].equals("max_time")){
                        max_time = entry.getValue().toString();
                    } else {
                        map = gson.fromJson(entry.getValue().toString(), Map.class);
                        maps.add(i, map);
                    }
                }
            }
        }
        for (int i = 0; i < maps.size(); i++) {
            for (Entry<String, Object> entry : maps.get(i).entrySet()) {
                if (i == 0) {
                    Dashboard d = gson.fromJson(entry.getValue().toString(), Dashboard.class);
                    d.setDegree_interval(entry.getKey());
                    dashboard.add(d);
                } else if (i == 1) {
                    Histogram d = gson.fromJson(entry.getValue().toString(), Histogram.class);
                    d.setDegree_interval(entry.getKey());
                    histogram.add(d);
                }else if(i ==2){
                    StaticLateral s =  gson.fromJson(entry.getValue().toString(), StaticLateral.class);
                    s.setDegree_interval(entry.getKey());
                    static_lateral.add(s);
                }
            }
        }
        GetStatistics getStatistics = new GetStatistics();
        getStatistics.setDashboard(dashboard);
        getStatistics.setHistogram(histogram);
        getStatistics.setStatic_lateral(static_lateral);
        getStatistics.setSit_info(sit_info);
        getStatistics .setMax_time(max_time);
        cla.setData(getStatistics);
        return cla;
    }



    public  static BaseClassBean<GetStaticLateral> sex2(String str) {
        Gson gson = new Gson();
//        List<Map<String, Object>> maps = new ArrayList<>();
        String data = null;
        List<StaticLateral> static_lateral= new ArrayList<>();
        Map<String, Object> map = new LinkedHashMap();
        String max_time = "";
        String min_time = "";
        BaseClassBean<GetStaticLateral> cla = new BaseClassBean<>();
        String aa="";

        map = gson.fromJson(str, Map.class);
        for (Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("data")) {
//                Object a = entry.getValue();
//                aa= a.toString();
//                String bb = a.toString();
//                data = bb;
                data = entry.getValue().toString();

//                map = gson.fromJson(entry.getValue().toString(), Map.class);
            } else if (entry.getKey().equals("status")) {
                cla.setStatus("" + (int) Double.parseDouble(entry.getValue().toString()));
            } else if (entry.getKey().equals("statusInfo")) {
                cla.setStatusInfo(entry.getValue().toString());
            }
        }

        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONObject objdata =jsonObject.getJSONObject("data");
            Iterator<String> keys = objdata.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                if(key.equals("max_time")){
                    max_time = objdata.getString(key);

                }else if(key.equals("min_time")){
                    min_time = objdata.getString(key);
                }else {
                    Object a = objdata.get(key);
                    StaticLateral s = gson.fromJson(objdata.getString(key), StaticLateral.class);
                    s.setDegree_interval(key);
                    static_lateral.add(s);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        map = gson.fromJson(aa, Map.class);
//        for (Entry<String, Object> entry : map.entrySet()) {
//
//                if (entry.getKey().equals("max_time")){
//                    max_time = entry.getValue().toString();
//
//                }else if(entry.getKey().equals("min_time")){
//                    min_time = entry.getValue().toString();
//                }else {
//                    StaticLateral s = gson.fromJson(entry.getValue().toString(), StaticLateral.class);
//                    s.setDegree_interval(entry.getKey());
//                    static_lateral.add(s);
//                }
//
//        }


         GetStaticLateral getStaticLateral = new GetStaticLateral();
            getStaticLateral.setStatic_lateral(static_lateral);
            getStaticLateral.setMin_time(min_time);;
            getStaticLateral .setMax_time(max_time);
        cla.setData(getStaticLateral);
        return cla;
    }

}
