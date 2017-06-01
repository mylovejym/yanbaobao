package com.zhizhen.ybb.bean;

import java.util.*;

/**
 * 写入数据格式
 * Created by tc on 2017/6/1.
 */

public class DataBean {

    private List<String> measure_degree ;

    private List<String> measure_time ;

    private List<String> duration ;

    public List<String> getMeasure_degree() {
        return measure_degree;
    }

    public void setMeasure_degree(List<String> measure_degree) {
        this.measure_degree = measure_degree;
    }

    public List<String> getMeasure_time() {
        return measure_time;
    }

    public void setMeasure_time(List<String> measure_time) {
        this.measure_time = measure_time;
    }

    public List<String> getDuration() {
        return duration;
    }

    public void setDuration(List<String> duration) {
        this.duration = duration;
    }
}
