
/*
 * Copyright (c) 2015, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.zhizhen.ybb.lanya;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.zhizhen.ybb.api.YbbApi;
import com.zhizhen.ybb.base.YbBaseApplication;
import com.zhizhen.ybb.bean.BLEData;
import com.zhizhen.ybb.bean.BLEDataQueue;
import com.zhizhen.ybb.util.BLEUtils;
import com.zhizhen.ybb.util.SpUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.UUID;

import static com.zhizhen.ybb.util.Utils.hexStringToBytes;

/**
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class UartService extends Service {
    private final static String TAG = UartService.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final static String ACTION_GATT_CONNECTED =
            "com.nordicsemi.nrfUART.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.nordicsemi.nrfUART.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.nordicsemi.nrfUART.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.nordicsemi.nrfUART.ACTION_DATA_AVAILABLE";
    public final static String ACTION_DATA_AVAILABLE2 =
            "com.nordicsemi.nrfUART.ACTION_DATA_AVAILABLE2";
    public final static String EXTRA_DATA =
            "com.nordicsemi.nrfUART.EXTRA_DATA";
    public final static String DEVICE_DOES_NOT_SUPPORT_UART =
            "com.nordicsemi.nrfUART.DEVICE_DOES_NOT_SUPPORT_UART";

    public final static String ACTION_UP_DATA =
            "com.nordicsemi.nrfUART.ACTION_UP_DATA";
    public final static String ACTION_GATT_CONNECTING =
            "com.nordicsemi.nrfUART.ACTION_GATT_CONNECTING";
    
    public static final UUID TX_POWER_UUID = UUID.fromString("00001804-0000-1000-8000-00805f9b34fb");
    public static final UUID TX_POWER_LEVEL_UUID = UUID.fromString("00002a07-0000-1000-8000-00805f9b34fb");
    public static final UUID CCCD = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID FIRMWARE_REVISON_UUID = UUID.fromString("00002a26-0000-1000-8000-00805f9b34fb");
    public static final UUID DIS_UUID = UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb");
    public static final UUID RX_SERVICE_UUID = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID RX_CHAR_UUID = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID TX_CHAR_UUID = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");

    MyServiceConnection mServiceConnection = new MyServiceConnection();
    private BLEDataQueue bleData = new BLEDataQueue();

    private int state = 0;//0失败，1连接中，2连接

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        UartService.this.bindService(new Intent(UartService.this,MsgAidlService.class),mServiceConnection, Context.BIND_IMPORTANT);
//        return super.onStartCommand(intent, flags, startId);
//        test();
//        startTimer();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        this.registerReceiver(bluetoothStatusChangeReceiver, filter);


        return START_STICKY;
    }

    private final BroadcastReceiver bluetoothStatusChangeReceiver
            = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtil.e(TAG, "onReceive---------");
            switch(intent.getAction()){
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    switch(blueState){
                        case BluetoothAdapter.STATE_TURNING_ON:
                            LogUtil.e("onReceive---------STATE_TURNING_ON");
                            if(SpUtils.getBindBLEDevice(UartService.this)!=null&&  SpUtils.getBoolean(UartService.this, "isbinded",false)){
                                LogUtil.e("aaaaaaa:连接" );
                                connect(SpUtils.getBindBLEDevice(UartService.this).getAddress());
                            }
                            break;
                        case BluetoothAdapter.STATE_ON:
                            LogUtil.e("onReceive---------STATE_ON");
                            if(SpUtils.getBindBLEDevice(UartService.this)!=null&&  SpUtils.getBoolean(UartService.this, "isbinded",false)){
                                LogUtil.e("aaaaaaa:连接" );
                                connect(SpUtils.getBindBLEDevice(UartService.this).getAddress());
                            }
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            LogUtil.e("onReceive---------STATE_TURNING_OFF");
                            mBluetoothGatt = null;
                            break;
                        case BluetoothAdapter.STATE_OFF:
                            LogUtil.e("onReceive---------STATE_OFF");
                            mBluetoothGatt = null;
                            break;
                    }
                    break;
            }

        }
    };

    Timer timer;
    BLEData data;

    Handler handler = new Handler();

//    boolean isstart = false;

    private void startTimer(){
//        if(timer!= null) {
//            timer.cancel();
//        }
//        mBluetoothGatt.getConnectionState()
//        if(isstart){
//            return;
//        }
//        isstart = true;
        handler.removeCallbacksAndMessages(null);
        writeRXCharacteristic(hexStringToBytes("AA03030155"));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rundata();
            }
        },60*1000);
//        writeRXCharacteristic(hexStringToBytes("AA03030055"));
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
////                System.out.println("task   run:"+getCurrentTime());
//                rundata();
//            }
//        };
//        timer = new Timer();
//        timer.schedule(task, 60*1000,60*1000);

    }

    private void rundata(){
        if(data == null) {
            data = new BLEData();
            data.copy(bleData);
        }
        if(data.getMeasure_time()==null||data.getMeasure_time().size()==0){
            if(isBleConnect()) {
                writeRXCharacteristic(hexStringToBytes("AA03030155"));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rundata();
                    }
                }, 60 * 1000);
            }
            return;
        }
        Gson mGson = new Gson();
        String json = mGson.toJson(data);
        LogUtil.e("json:"+json);
        RxService.createApi(YbbApi.class).addHardwareData(data.getMeasure_degree().size(),YbBaseApplication.instance.getToken(),json).compose(RxUtil.rxSchedulerHelper()).subscribe(baseBean->{
            if (baseBean.getStatus().equals("0")) {
                LogUtil.e("cccccccccccccccccccccccccccggggggggggggggggggggggg");
//                Intent intent2 = new Intent(ACTION_UP_DATA);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(intent2);
                broadcastUpdate(ACTION_UP_DATA);
                data =null;
                if(bleData.QueueLength()>0){
                    handler.removeCallbacksAndMessages(null);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rundata();
                        }
                    },2*1000);


                }else {

                        if(isBleConnect()) {
                            writeRXCharacteristic(hexStringToBytes("AA03030155"));
                            handler.removeCallbacksAndMessages(null);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    rundata();
                                }
                            }, 60 * 1000);
                        }



                }
            }

        },e->{

                if(isBleConnect()) {
                writeRXCharacteristic(hexStringToBytes("AA03030155"));
                    handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rundata();
                    }
                }, 60 * 1000);
                }
        });
    }

    String[] testStr= new String[]{"AA0A0320D4763A0024FF0055","AA0A0320D4763C0002000055", "AA0A0320D476500014FF0055", "AA0A0320D476520002000055"};

    private void test(){
        for(String text : testStr){
            String str = text.substring(6,14);
            long longStr = Long.parseLong(str, 16)+946656000;
//            int time = longStr.intValue();
                LogUtil.e("sssssstime:"+longStr);
            bleData.enQueueMeasure_time(""+longStr);
            String str2 = text.substring(14,18);
            Integer intStr = Integer.parseInt(str2, 16);
            short duration = intStr.shortValue();
                LogUtil.e("ssssssduration:"+duration);
            bleData.enQueueDuration(""+duration);
            String str3 = text.substring(18,20);
            byte[] bs= hexStringToBytes(str3);
            int degree = bs[0];
                LogUtil.e("ssssssdegree:"+degree);
            bleData.enQueueMeasure_degree(""+degree);

        }
    }


  boolean isfirst;

    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                state = 2;
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);
//                startTimer();
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                state = 0;
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction);
            }
        }

        Handler handler = new Handler();
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
            	Log.w(TAG, "mBluetoothGatt = " + mBluetoothGatt );
            	
//                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                enableTXNotification();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isfirst =true;
                        writeRXCharacteristic(BLEUtils.getTimeString(Calendar.getInstance().getTime()));
                    }
                },1000);

            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {

                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    };

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    boolean isfirstData;

    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);

        // This is handling for the notification on TX Character of NUS service
        if (TX_CHAR_UUID.equals(characteristic.getUuid())) {
        	
           // Log.d(TAG, String.format("Received TX: %d",characteristic.getValue() ));
            String text = BLEUtils.bytesToHexString(characteristic.getValue());
            LogUtil.e("RX2:"+text);
            if(text.startsWith("aa0a03")){
                if(isfirstData){
                    isfirstData = false;

                    Intent intent2 = new Intent(ACTION_DATA_AVAILABLE2);
                    intent2.putExtra(EXTRA_DATA, characteristic.getValue());
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent2);
                }
                String str = text.substring(6,14);
                Long longStr = Long.parseLong(str, 16)+946656000;
//                int time = longStr.intValue();
//                LogUtil.e("sssssstime:"+time);
                bleData.enQueueMeasure_time(""+longStr);
                String str2 = text.substring(14,18);
                Integer intStr = Integer.parseInt(str2, 16);
                short duration = intStr.shortValue();
//                LogUtil.e("ssssssduration:"+duration);
                bleData.enQueueDuration(""+duration);
                String str3 = text.substring(18,20);
                byte[] bs= hexStringToBytes(str3);
                int degree = bs[0];
//                LogUtil.e("ssssssdegree:"+degree);
                bleData.enQueueMeasure_degree(""+degree);



            }else {
                if(isfirst&&text.equalsIgnoreCase("AA0155")){
                    isfirst = false;
                    isfirstData = true;
                    startTimer();
                }



            }
            intent.putExtra(EXTRA_DATA, characteristic.getValue());
        } else {
        	
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }

    public class LocalBinder extends Binder {
        public UartService getService() {
            return UartService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
//        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    public int getState() {
        return state;
    }

    public int getmConnectionState() {
        return mConnectionState;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     *
     * @return Return true if the connection is initiated successfully. The connection result
     *         is reported asynchronously through the
     *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     *         callback.
     */
    public boolean connect(final String address) {

        state = 1;
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            state = 0;
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                broadcastUpdate(ACTION_GATT_CONNECTING);
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                state = 0;
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            state = 0;
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        broadcastUpdate(ACTION_GATT_CONNECTING);
        mConnectionState = STATE_CONNECTING;
        return true;
    }


    public boolean isBlueOpen(){
        if (mBluetoothAdapter == null ||!mBluetoothAdapter.isEnabled()) {
            Log.w(TAG, "BluetoothAdapter not initialized.");
            return false;
        }else{
            return true;
        }
    }


    public boolean isBleConnect(){
        if(mBluetoothDeviceAddress ==null)
            return false;
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(mBluetoothDeviceAddress);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        Log.w(TAG, "State::::"+mBluetoothManager.getConnectionState(device,BluetoothProfile.GATT));
        if(mBluetoothManager.getConnectionState(device,BluetoothProfile.GATT)== BluetoothProfile.STATE_CONNECTED){

            return  true;
        }
        return false;
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
       // mBluetoothGatt.close();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        Log.w(TAG, "mBluetoothGatt closed");
        mBluetoothDeviceAddress = null;
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    /**
     * Enables or disables notification on a give characteristic.
     *

    */
    
    /**
     * Enable Notification on TX characteristic
     *
     * @return 
     */
    public void enableTXNotification()
    { 
    	/*
    	if (mBluetoothGatt == null) {
    		showMessage("mBluetoothGatt null" + mBluetoothGatt);
    		broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
    		return;
    	}
    		*/
    	BluetoothGattService RxService = mBluetoothGatt.getService(RX_SERVICE_UUID);
    	if (RxService == null) {
            showMessage("Rx service not found!");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            return;
        }
    	BluetoothGattCharacteristic TxChar = RxService.getCharacteristic(TX_CHAR_UUID);
        if (TxChar == null) {
            showMessage("Tx charateristic not found!");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(TxChar,true);
        
        BluetoothGattDescriptor descriptor = TxChar.getDescriptor(CCCD);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        mBluetoothGatt.writeDescriptor(descriptor);
    	
    }
    
    public void writeRXCharacteristic(byte[] value)
    {
    
    	
    	BluetoothGattService RxService = mBluetoothGatt.getService(RX_SERVICE_UUID);
    	showMessage("mBluetoothGatt null"+ mBluetoothGatt);
    	if (RxService == null) {
            showMessage("Rx service not found!");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            return;
        }
    	BluetoothGattCharacteristic RxChar = RxService.getCharacteristic(RX_CHAR_UUID);
        if (RxChar == null) {
            showMessage("Rx charateristic not found!");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            return;
        }
        RxChar.setValue(value);
    	boolean status = mBluetoothGatt.writeCharacteristic(RxChar);
    	
        Log.d(TAG, "write TXchar - status=" + status);
    }
    
    private void showMessage(String msg) {
        Log.e(TAG, msg);
    }
    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();
    }

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {



//	      Toast.makeText(MsgService.this,name+"连接成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
//	      Toast.makeText(MsgService.this,TAG+"断开连接",Toast.LENGTH_SHORT).show();

            UartService.this.startService(new Intent(UartService.this,MsgAidlService.class));

            UartService.this.bindService(new Intent(UartService.this,MsgAidlService.class),mServiceConnection, Context.BIND_IMPORTANT);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothStatusChangeReceiver);
    }
}
