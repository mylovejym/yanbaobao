package com.zhizhen.ybb.util;

import com.google.gson.*;

import java.util.*;
import java.util.Map.*;

/**
 * Created by tc on 2017/6/2.
 */

public class DataSex {

    private static Gson gson = new Gson();
    private static String[] keys;
    private static List<Map<String, Object>> maps;
    public static void setKey(String[] key){
        for (int i = 0; i < key.length; i++) {
            Map<String, Object> map = new HashMap<>();
            maps.add(map);
        }
    }

    public static void sex(String str, Class cla){
        Map<String, Object> map = new HashMap();
        Map<String, Object> histogramMap = new HashMap();
        Map<String, Object> sit_infoMap = new HashMap();
        Map<String, Object> dashboardMap = new HashMap();
        map = gson.fromJson(str, Map.class);
        for (Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals(keys)) {
                map = gson.fromJson(entry.getValue().toString(), Map.class);
            }
        }
    }

}
