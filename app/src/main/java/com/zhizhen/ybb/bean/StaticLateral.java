package com.zhizhen.ybb.bean;

/**
 * Created by psylife00 on 2017/6/28.
 */

public class StaticLateral {
    /**
     * timeNum : 1
     * pid : 223
     * measureDate : 2017-05-29
     * degree : 0
     * duration : 0
     * average : 0
     */

    private String timeNum;
    private String pid;
    private String measureDate;
    private String degree;
    private String duration;
    private String average;

    private String degree_interval;         //1代表【8:00-8:10】 //2代表【8:10-8:20】

    public String getTimeNum() {
        return timeNum;
    }

    public void setTimeNum(String timeNum) {
        this.timeNum = timeNum;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMeasureDate() {
        return measureDate;
    }

    public void setMeasureDate(String measureDate) {
        this.measureDate = measureDate;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAverage() {
        return average;
    }
    public float getAverageforFloat(){
        float d=0;
        try {
           d= Float.parseFloat(average);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return d;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getDegree_interval() {
        return degree_interval;
    }

    public void setDegree_interval(String degree_interval) {
        this.degree_interval = degree_interval;
    }
}
