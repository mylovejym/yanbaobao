package com.zhizhen.ybb.bean;

/**
 * Created by tc on 2017/5/19.
 */

public class DeviceInfo {

    private String deviceName;
    private String deviceState; //1:已绑定   2：未绑定

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
    }

    public DeviceInfo(String deviceName, String deviceState) {
        this.deviceName = deviceName;
        this.deviceState = deviceState;
    }

    public DeviceInfo() {
    }
}
