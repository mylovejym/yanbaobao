package com.zhizhen.ybb.bean;

import android.bluetooth.BluetoothDevice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/9.
 */

public class MyBLEDeviceMap   implements Serializable {
    HashMap<String, MyBLEDevice> BLEMap;
    public MyBLEDeviceMap(){
        BLEMap = new HashMap<>();
    }

    public HashMap<String, MyBLEDevice> getBLEMap() {
        return BLEMap;
    }

    public void setBLEMap(HashMap<String, MyBLEDevice> BLEMap) {
        this.BLEMap = BLEMap;
    }

    public void addDevice(BluetoothDevice device){
        MyBLEDevice myBLEDevice = new MyBLEDevice();
        myBLEDevice.setName(device.getName());
        myBLEDevice.setAddress(device.getAddress());
        BLEMap.put(device.getAddress(), myBLEDevice);
    }

    public List<MyBLEDevice> getMyDeviceList(){
        List<MyBLEDevice> mapValuesList = new ArrayList<MyBLEDevice>(BLEMap.values());
        return mapValuesList;
    }
}
