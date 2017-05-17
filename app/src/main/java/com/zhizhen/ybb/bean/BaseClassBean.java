package com.zhizhen.ybb.bean;

/**
 * Created by tc on 2017/5/17.
 */

public class BaseClassBean<L> extends BaseBean {

    private L data;

    public L getData() {
        return data;
    }

    public void setData(L data) {
        this.data = data;
    }
}
