package com.zhizhen.ybb.my;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.StringUtils;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.bean.MyBLEDevice;
import com.zhizhen.ybb.bean.MyBLEDeviceMap;
import com.zhizhen.ybb.lanya.UartService;
import com.zhizhen.ybb.my.adapter.DeviceAdapter;
import com.zhizhen.ybb.my.interFace.BindingDeviceOnClickListener;
import com.zhizhen.ybb.util.SpUtils;
import com.zhizhen.ybb.view.SwitchView;

import butterknife.BindView;


/**
 * 我的设备
 * Created by tc on 2017/5/19.
 */

public class MyDeivceActivity extends YbBaseActivity implements View.OnClickListener {

    public static final int SAMPLING = 10090;

    @BindView(R.id.lin_choice_sampling)
    LinearLayout linSampling;

    @BindView(R.id.list_device)
    ListView listDevice;

    @BindView(R.id.txt_sampling)
    TextView txtSampling;

    @BindView(R.id.device_open)
    SwitchView switchView;

    private DeviceAdapter deviceAdapter;
    MyBLEDeviceMap map;
    MyBLEDevice bindDecive;

    private UartService mService = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_device;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText("设备选择")
                .setLeftImage(R.mipmap.tab_back)
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
                .build();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        txtSampling.setText(SpUtils.getString(this, "sampling") + "s");

        linSampling.setOnClickListener(this);
        switchView.setOnClickListener(this);
        if (SpUtils.getBoolean(this, "switchView") != null)
            switchView.toggleSwitch(SpUtils.getBoolean(this, "switchView"));
        switchView.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                SpUtils.putBoolean(MyDeivceActivity.this, "switchView", true);
                view.toggleSwitch(true); // or false
            }

            @Override
            public void toggleToOff(SwitchView view) {
                SpUtils.putBoolean(MyDeivceActivity.this, "switchView", false);
                view.toggleSwitch(false); // or true
            }
        });
        service_init();
    }

    private void service_init() {
        Intent bindIntent = new Intent(this, UartService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
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


    @Override
    public void initdata() {
//        List<DeviceInfo> list = new ArrayList<>();
//        DeviceInfo deviceInfo;
//        for (int i = 0; i < 5; i++) {
//            if (i == 3)
//                deviceInfo = new DeviceInfo("TRWOMOPD-EYE-1" + i, "1");
//            else
//                deviceInfo = new DeviceInfo("TRWOMOPD-EYE-1" + i, "2");
//            list.add(deviceInfo);
//        }
        map =SpUtils.getMyBLEDeviceMap(this);
        bindDecive = SpUtils.getBindBLEDevice(this);


        deviceAdapter = new DeviceAdapter(this, map.getMyDeviceList(), bindDecive,SpUtils.getBoolean(this,"isbinded",false));
        deviceAdapter.setBindingDeviceOnClickListener(new BindingDeviceOnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onBindingOnClickListener(View view, int pos, boolean bindingState) {
                if(bindingState){
//                    SpUtils.setBindBLEDevice(MyDeivceActivity.this,null);
//                    bindDecive = null;
//                    deviceAdapter.setBindDecive(bindDecive);
                    SpUtils.putBoolean(MyDeivceActivity.this, "isbinded",false);
                    deviceAdapter.setIsbinded(false);
                    mService.disconnect();
                }else{
                    MyBLEDevice device = (MyBLEDevice) view.getTag();
                    SpUtils.setBindBLEDevice(MyDeivceActivity.this,device);
                    bindDecive = device;
                    deviceAdapter.setBindDecive(bindDecive);
                    SpUtils.putBoolean(MyDeivceActivity.this, "isbinded",true);
                    deviceAdapter.setIsbinded(true);
//                    mService.connect(bindDecive.getAddress());
                    Intent intent = new Intent(MyDeivceActivity.this, BindingActivity.class);
                    intent.putExtra("address", bindDecive.getAddress());
                    startActivity(intent);
                }
//                if (bindingState.equals("1")) {
//                    list.get(pos).setDeviceState("2");
//                } else {
//                    list.get(pos).setDeviceState("1");
//                }
//                deviceAdapter.refresh(list);
            }
        });
        listDevice.setAdapter(deviceAdapter);
//        listDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(bindDecive!=null&&deviceAdapter.getItem(position).getAddress().equals(bindDecive.getAddress())){
//                    Intent intent = new Intent(MyDeivceActivity.this, ParameterSetActivity.class);
//                    MyDeivceActivity.this.startActivity(intent);
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        if (v == linSampling) {
            Intent intent = new Intent(this, ChoiceSampling.class);
            if (StringUtils.isEmpty(SpUtils.getString(this, "sampling")))
                SpUtils.putString(this, "sampling", "1");
            intent.putExtra("sampling", SpUtils.getString(this, "sampling"));
            this.startActivityForResult(intent, SAMPLING);
        } else if (v == switchView) {
            boolean isOpened = switchView.isOpened();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SAMPLING) {
            SpUtils.putString(this, "sampling", data.getStringExtra("sampling"));
            txtSampling.setText(data.getStringExtra("sampling") + "s");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");


        unbindService(mServiceConnection);

    }
}
