package com.zhizhen.ybb.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/9.
 */

public class MyBLEDevice  implements Serializable {
    String name;
    String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
