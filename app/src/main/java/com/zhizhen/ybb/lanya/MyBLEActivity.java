package com.zhizhen.ybb.lanya;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.util.BLEUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by vonchenchen on 2016/1/29 0029.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MyBLEActivity extends YbBaseActivity {

    private final String TAG = "MyBLEActivity";

    public static final String BLEDEVICE_NAME = "BLEDEVICE_NAME";

    private ListView mBLEDeviceListView;
    private BLEDeviceAdapter mBLEDeviceAdapter;

    private ViewGroup mMainLayout;

    private Handler mHandler;
    private static final long SCAN_PERIOD = 10000; //5 seconds

    private final int RESULTCODE_TRUE_ON_BLUETOOTH = 0;

    private List<BluetoothDevice> mDataList;
    Map<String, Integer> mDevRssiMap;
    private boolean mIsScanning;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothAdapter.LeScanCallback mBLEScanCallback;

    private UartService mService = null;

    private BluetoothDevice mDevice = null;

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText("设备列表")
                .setLeftImage(R.mipmap.tab_back)
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
                .build();
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_myble;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mBLEDeviceListView = (ListView) findViewById(R.id.lv_device);


        mMainLayout = (ViewGroup) findViewById(R.id.rl_main).getParent();


        mHandler = new Handler();

        mDataList = new LinkedList<BluetoothDevice>();
        mDevRssiMap = new HashMap<String, Integer>();

        mBLEDeviceAdapter = new BLEDeviceAdapter(this, mDataList);
        mBLEDeviceListView.setAdapter(mBLEDeviceAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                   // Android M Permission check
                   if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 111);
                        }
              }




        //自动开启蓝牙
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, 1);

        //BLE
        mBLEScanCallback = getBLEScanCallback();
        checkBLEDevice();
        //scanOtherBLEDevice(true);
        scanOtherBLEDevice(!mIsScanning);



        mBLEDeviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BluetoothDevice device = mDataList.get(i);
                mBluetoothAdapter.stopLeScan(mBLEScanCallback);

                String deviceAddress = device.getAddress();
                mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);

                Log.d(TAG, "... onActivityResultdevice.address==" + mDevice + "mserviceValue" + mService);

                mService.connect(deviceAddress);

//                Intent intent = new Intent(MyBLEActivity.this, NormalDeviceActivity.class);
//                intent.putExtra(BluetoothDevice.EXTRA_DEVICE, device.getAddress());
//                intent.putExtra(BLEDEVICE_NAME, device.getName());
//                startActivity(intent);
            }
        });

        service_init();



    }

    private void service_init() {
        Intent bindIntent = new Intent(this, UartService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

        LocalBroadcastManager.getInstance(this).registerReceiver(UARTStatusChangeReceiver, makeGattUpdateIntentFilter());
    }
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
//        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        return intentFilter;
    }

    //UART service connected/disconnected
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            mService = ((UartService.LocalBinder) rawBinder).getService();
            Log.d(TAG, "onServiceConnected mService= " + mService);
            if (!mService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }

        }

        public void onServiceDisconnected(ComponentName classname) {
            ////     mService.disconnect(mDevice);
            mService = null;
        }
    };
    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            final Intent mIntent = intent;
            //*********************//
            if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        Log.d(TAG, "UART_CONNECT_MSG");
                        Toast.makeText(MyBLEActivity.this,"连接成功",Toast.LENGTH_LONG).show();
                    }
                });
            }

            //*********************//
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        Log.d(TAG, "UART_DISCONNECT_MSG");
//                        btnConnectDisconnect.setText("Connect");
//                        edtMessage.setEnabled(false);
//                        btnSend.setEnabled(false);
//                        ((TextView) findViewById(R.id.deviceName)).setText("Not Connected");
//                        listAdapter.add("["+currentDateTimeString+"] Disconnected to: "+ mDevice.getName());
//                        mState = UART_PROFILE_DISCONNECTED;
//                        mService.close();
                        //setUiState();

                    }
                });
            }




//            //*********************//
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {
//                mService.enableTXNotification();
            }
//            //*********************//
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {
                final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
//                            String text = new String(txValue, "UTF-8");
                            String text = BLEUtils.bytesToHexString(txValue);
//                            String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                            LogUtil.e("RX:" + text);
//                            listAdapter.add("["+currentDateTimeString+"] RX: "+text);
//                            messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                            if (text.equalsIgnoreCase("AA0155")) {
                                Toast.makeText(MyBLEActivity.this, "系统时间设置成功", Toast.LENGTH_LONG).show();
                            }
//                            else if(text.startsWith("aa0a03")){
//                                Toast.makeText(MyBLEActivity.this, "正在获取数据", Toast.LENGTH_LONG).show();
//                            }

                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                });

            }
            if(action.equals(UartService.ACTION_DATA_AVAILABLE2)){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MyBLEActivity.this, "正在获取数据", Toast.LENGTH_LONG).show();
                    }
                });
            }
//            //*********************//
//            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)){
//
//                mService.disconnect();
//            }


        }
    };

    @Override
    public void initdata() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 111:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // TODO request success
                }
                break;
        }
    }

    /**
     * check this device and judge if support BLE
     */
    private void checkBLEDevice(){
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, " ble not supported ", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, " ble not supported ", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }

    /**
     * scan other ble device
     * @param enable
     */
    private void scanOtherBLEDevice(boolean enable){
        if(enable){
            mDataList.clear();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //stop scan
                    mIsScanning = false;
                    mBluetoothAdapter.stopLeScan(mBLEScanCallback);


                }
            }, SCAN_PERIOD);
            //start scan
            mIsScanning = true;
            Boolean a=mBluetoothAdapter.startLeScan(mBLEScanCallback);


        }else{
            //stop scan
            mIsScanning = false;
            mBluetoothAdapter.stopLeScan(mBLEScanCallback);


        }
    }

    private BluetoothAdapter.LeScanCallback getBLEScanCallback(){
        return new BluetoothAdapter.LeScanCallback(){
            @Override
            public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {

                Log.i("Activity", device.getAddress());
                addBLEDeviceData(device, rssi);

            }
        };
    }

    private void addBLEDeviceData(BluetoothDevice device, int rssi){
        boolean deviceFound = false;

        for (BluetoothDevice listDev : mDataList) {
            if (listDev.getAddress().equals(device.getAddress())) {
                deviceFound = true;
                break;
            }
        }
        mDevRssiMap.put(device.getAddress(), rssi);
        if(!deviceFound){
            if(device.getName()!=null&&device.getName().startsWith("YZJ")) {
                mDataList.add(device);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBLEDeviceAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULTCODE_TRUE_ON_BLUETOOTH){
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Bluetooth has turned on ", Toast.LENGTH_SHORT).show();

            } else {
                // User did not enable Bluetooth or an error occurred
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, "Problem in BT Turning ON ", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");

        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(UARTStatusChangeReceiver);
        } catch (Exception ignore) {
            Log.e(TAG, ignore.toString());
        }
        unbindService(mServiceConnection);

    }


}
