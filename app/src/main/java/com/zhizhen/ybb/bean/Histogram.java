package com.zhizhen.ybb.bean;

/**
 * Created by tc on 2017/6/1.
 */

public class Histogram {

    private String pid;

    private String mild_time;

    private String middle_time;

    private String serious_time;

    private String mild_time_percent;

    private String middle_time_percent;

    private String serious_time_percent;

    private String date;

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return this.pid;
    }

    public void setMild_time(String mild_time) {
        this.mild_time = mild_time;
    }

    public String getMild_time() {
        return this.mild_time;
    }

    public void setMiddle_time(String middle_time) {
        this.middle_time = middle_time;
    }

    public String getMiddle_time() {
        return this.middle_time;
    }

    public void setSerious_time(String serious_time) {
        this.serious_time = serious_time;
    }

    public String getSerious_time() {
        return this.serious_time;
    }

    public void setMild_time_percent(String mild_time_percent) {
        this.mild_time_percent = mild_time_percent;
    }

    public String getMild_time_percent() {
        return this.mild_time_percent;
    }

    public void setMiddle_time_percent(String middle_time_percent) {
        this.middle_time_percent = middle_time_percent;
    }

    public String getMiddle_time_percent() {
        return this.middle_time_percent;
    }

    public void setSerious_time_percent(String serious_time_percent) {
        this.serious_time_percent = serious_time_percent;
    }

    public String getSerious_time_percent() {
        return this.serious_time_percent;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }


}
