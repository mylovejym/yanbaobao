package com.zhizhen.ybb.bean;

import java.util.*;

/**
 * 获取统计数据
 * Created by tc on 2017/6/1.
 */

public class GetStatistics {

    private List<Dashboard> dashboard;
    private List<Histogram> histogram;
    private List<SitInfo> sit_info;

    public List<Dashboard> getDashboard() {
        return dashboard;
    }

    public void setDashboard(List<Dashboard> dashboard) {
        this.dashboard = dashboard;
    }

    public List<Histogram> getHistogram() {
        return histogram;
    }

    public void setHistogram(List<Histogram> histogram) {
        this.histogram = histogram;
    }

    public List<SitInfo> getSit_info() {
        return sit_info;
    }

    public void setSit_info(List<SitInfo> sit_info) {
        this.sit_info = sit_info;
    }
}
