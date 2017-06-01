package com.zhizhen.ybb.bean;

/**
 * 端坐信息
 * Created by tc on 2017/6/1.
 */

public class SitInfo {

    private String sit_time;            //端坐时长（s）

    private String sit_time_percent;    //端坐所占百分比

    public void setSit_time(String sit_time) {
        this.sit_time = sit_time;
    }

    public String getSit_time() {
        return this.sit_time;
    }

    public void setSit_time_percent(String sit_time_percent) {
        this.sit_time_percent = sit_time_percent;
    }

    public String getSit_time_percent() {
        return this.sit_time_percent;
    }


}
