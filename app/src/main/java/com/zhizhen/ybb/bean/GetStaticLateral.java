package com.zhizhen.ybb.bean;

import java.util.List;

/**
 * Created by psylife00 on 2017/7/3.
 */

public class GetStaticLateral {
    private List<StaticLateral> static_lateral;

    String min_time;
    String max_time;

    public List<StaticLateral> getStatic_lateral() {
        return static_lateral;
    }

    public void setStatic_lateral(List<StaticLateral> static_lateral) {
        this.static_lateral = static_lateral;
    }

    public String getMin_time() {
        return min_time;
    }

    public void setMin_time(String min_time) {
        this.min_time = min_time;
    }

    public String getMax_time() {
        return max_time;
    }

    public void setMax_time(String max_time) {
        this.max_time = max_time;
    }
}
