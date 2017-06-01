package com.zhizhen.ybb.bean;

/**
 *
 * 仪表盘所需数据
 *
 * Created by tc on 2017/6/1.
 */

public class Dashboard {

    private String degree_interval;  //度数区间 1代表【0°，10°】 2代表【10°，20°】 3代表【20°，30°】依次类推

    private String total_time;       //当天所存数据总时间

    private String pid;             //用户ID

    private String duration;

    private String percent;         //【0°，10°】所占时长

    private String date;            //记录日期

    public String getDegree_interval() {
        return degree_interval;
    }

    public void setDegree_interval(String degree_interval) {
        this.degree_interval = degree_interval;
    }

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
