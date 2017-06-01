package com.zhizhen.ybb.bean;

/**
 * Created by tc on 2017/6/1.
 */

public class Dashboard {
    private String total_time;

    private String pid;

    private String duration;

    private String percent;

    private String date;

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public String getTotal_time() {
        return this.total_time;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return this.pid;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getPercent() {
        return this.percent;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }


}
