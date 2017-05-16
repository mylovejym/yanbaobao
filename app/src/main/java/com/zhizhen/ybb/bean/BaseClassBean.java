package com.zhizhen.ybb.bean;

/**
 * Created by psylife00 on 2017/4/14.
 */

public class BaseClassBean<L> extends BaseBean {
    L data;

    public L getData() {
        return data;
    }

    public void setData(L data) {
        this.data = data;
    }
}
