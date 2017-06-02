package com.zhizhen.ybb.util;

import com.google.gson.*;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.Dashboard;
import com.zhizhen.ybb.bean.GetStatistics;
import com.zhizhen.ybb.bean.Histogram;
import com.zhizhen.ybb.bean.SitInfo;

import org.json.JSONObject;

import java.util.*;
import java.util.Map.*;

/**
 * 获取数据 解析 json
 * Created by tc on 2017/6/2.
 */

public class DataSex {

    private static Gson gson = new Gson();
    private static String[] keys = {"dashboard", "histogram", "sit_info"};
    private static List<Map<String, Object>> maps = new ArrayList<>();
    private static String data;
    private static List<Dashboard> dashboard = new ArrayList<>();
    private static List<Histogram> histogram = new ArrayList<>();
    private static List<SitInfo> sit_info = new ArrayList<>();
    private static BaseClassBean<GetStatistics> cla = new BaseClassBean<>();
    public static BaseClassBean<GetStatistics> sex(String str) {
        Map<String, Object> map = new HashMap();
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
                }
            }
        }
        GetStatistics getStatistics = new GetStatistics();
        getStatistics.setDashboard(dashboard);
        getStatistics.setHistogram(histogram);
        getStatistics.setSit_info(sit_info);
        cla.setData(getStatistics);
        return cla;
    }

}
