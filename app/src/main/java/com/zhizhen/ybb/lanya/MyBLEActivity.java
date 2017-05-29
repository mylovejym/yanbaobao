package com.zhizhen.ybb.lanya;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;

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
    private Button mScanButton;
    private ViewGroup mMainLayout;

    private Handler mHandler;
    private static final long SCAN_PERIOD = 10000; //5 seconds

    private final int RESULTCODE_TRUE_ON_BLUETOOTH = 0;

    private List<BluetoothDevice> mDataList;
    Map<String, Integer> mDevRssiMap;
    private boolean mIsScanning;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothAdapter.LeScanCallback mBLEScanCallback;



    @Override
    public int getLayoutId() {
        return R.layout.activity_myble;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mBLEDeviceListView = (ListView) findViewById(R.id.lv_device);
        mScanButton = (Button) findViewById(R.id.btn_scan);

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

        mScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanOtherBLEDevice(!mIsScanning);
            }
        });

        mBLEDeviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BluetoothDevice device = mDataList.get(i);
                mBluetoothAdapter.stopLeScan(mBLEScanCallback);

//                Intent intent = new Intent(MyBLEActivity.this, NormalDeviceActivity.class);
//                intent.putExtra(BluetoothDevice.EXTRA_DEVICE, device.getAddress());
//                intent.putExtra(BLEDEVICE_NAME, device.getName());
//                startActivity(intent);
            }
        });



    }

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
                    mScanButton.setText("start scan");

                }
            }, SCAN_PERIOD);
            //start scan
            mIsScanning = true;
            Boolean a=mBluetoothAdapter.startLeScan(mBLEScanCallback);
            mScanButton.setText("stop scan");

        }else{
            //stop scan
            mIsScanning = false;
            mBluetoothAdapter.stopLeScan(mBLEScanCallback);
            mScanButton.setText("start scan");

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
            mDataList.add(device);
            runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBLEDeviceAdapter.notifyDataSetChanged();
            }});
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


}
