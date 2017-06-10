package com.zhizhen.ybb.my;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.lanya.UartService;
import com.zhizhen.ybb.util.BLEUtils;
import com.zhizhen.ybb.util.SpUtils;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * 参数配置
 * Created by tc on 2017/5/22.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class ParameterSetActivity extends YbBaseActivity implements View.OnClickListener {

    public static final int SET_TIME = 11900;

    public static final int SET_AC_TIME = 11910;

    public static final int SET_SAMPLING = 11920;

    public static final int SET_POSTURE = 11930;

    public static final int SET_SHAKING_NUM = 11940;

    public static final int SET_SHAKING_DELAYED = 11950;

    @BindView(R.id.lin_set_time)
    LinearLayout linSetTime;        //设置系统时间

    @BindView(R.id.lin_set_ac_time)
    LinearLayout linSetACTime;      //设定采集时段

    @BindView(R.id.lin_set_sampling)
    LinearLayout linSetSampling;    //设定采集频率

    @BindView(R.id.lin_set_posture)
    LinearLayout linSetPosture;     //设定标准坐姿

    @BindView(R.id.lin_set_shaking_num)
    LinearLayout linShakingNum;     //设定振动次数

    @BindView(R.id.lin_set_shaking_delayed)
    LinearLayout linShakingDelayed;     //设定振动延时

    @BindView(R.id.txt_time)
    TextView txtTime;       //系统时间

    @BindView(R.id.txt_ac_time)
    TextView txtAcTime;     //采集时段

    @BindView(R.id.txt_sampling)
    TextView txtSampling;   //采集频率

    @BindView(R.id.txt_posture)
    TextView txtPosture;    //标准坐姿

    @BindView(R.id.txt_shaking_num)
    TextView txtShakingNum;     //振动次数

    @BindView(R.id.txt_shaking_delayed)
    TextView txtShakingTime;        //振动延时

    private String time = "", acTime = "", sampling = "", posture = "", shakingNum = "", shakingTime = "";

    private UartService mService = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_parameter_set;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText(getString(R.string.parameter_set))
                .setLeftImage(R.mipmap.tab_back)
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
                .build();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        linSetTime.setOnClickListener(this);
        linSetACTime.setOnClickListener(this);
        linSetSampling.setOnClickListener(this);
        linSetPosture.setOnClickListener(this);
        linShakingNum.setOnClickListener(this);
        linShakingDelayed.setOnClickListener(this);
    }

    @Override
    public void initdata() {
        time = SpUtils.getString(this, "time");
        acTime = SpUtils.getString(this, "startTime") + "-" + SpUtils.getString(this, "endTime");
        sampling = SpUtils.getString(this, "sampling");
        posture = SpUtils.getString(this, "posture");
        shakingNum = SpUtils.getString(this, "shakingNum");
        shakingTime = SpUtils.getString(this, "shakingTime");

        txtTime.setText(time.equals("") ? "去设置" : time);
        txtAcTime.setText(acTime.equals("") ? "未设置" : acTime);
        txtSampling.setText(sampling.equals("") ? "未设置" : sampling);
        txtPosture.setText(posture.equals("") ? "未设置" : posture);
        txtShakingNum.setText(shakingNum.equals("") ? "未设置" : shakingNum);
        txtShakingTime.setText(shakingTime.equals("") ? "未设置" : shakingTime);

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
        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        return intentFilter;
    }

    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            final Intent mIntent = intent;
            //*********************//
            if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
                runOnUiThread(new Runnable() {
                    public void run() {
//                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
//                        Log.d(TAG, "UART_CONNECT_MSG");

                    }
                });
            }

            //*********************//
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
                runOnUiThread(new Runnable() {
                    public void run() {
//                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
//                        Log.d(TAG, "UART_DISCONNECT_MSG");
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


            //*********************//
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {
                mService.enableTXNotification();
            }
            //*********************//
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {

                final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
//                            String text = new String(txValue, "UTF-8");
                            String text = BLEUtils.bytesToHexString(txValue);
                            String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                            LogUtil.e("RX:" + text);
//                            listAdapter.add("["+currentDateTimeString+"] RX: "+text);
//                            messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                            if (text.equalsIgnoreCase("AA0155")) {
                                Toast.makeText(ParameterSetActivity.this, "设置成功", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                });
            }
            //*********************//
            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)) {
//                showMessage("Device doesn't support UART. Disconnecting");
                mService.disconnect();
            }


        }
    };

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

    @Override
    public void onClick(View v) {
        if (v == linSetTime) {
            //设置系统时间
            Intent intent = new Intent(this, SetTimeActivity.class);
            intent.putExtra("time", time);
            this.startActivityForResult(intent, SET_TIME);
        } else if (v == linSetACTime) {
            //设置采集时段
            Intent intent = new Intent(this, SetACTimeActivity.class);
            intent.putExtra("startTime", acTime.split("-")[0]);
            intent.putExtra("endTime", acTime.split("-")[1]);
            this.startActivityForResult(intent, SET_AC_TIME);
        } else if (v == linSetSampling) {
            //设置采集频率
            Intent intent = new Intent(this, SetSamplingActivity.class);
            intent.putExtra("sampling", sampling);
            this.startActivityForResult(intent, SET_SAMPLING);
        } else if (v == linSetPosture) {
            //设置标准坐姿
            Intent intent = new Intent(this, SetPosTureActivity2.class);
            intent.putExtra("posture", posture);
            this.startActivityForResult(intent, SET_POSTURE);
        } else if (v == linShakingNum) {
            //设置振动次数
            Intent intent = new Intent(this, SetShakingNumActivity.class);
            intent.putExtra("shakingNum", shakingNum);
            this.startActivityForResult(intent, SET_SHAKING_NUM);
        } else if (v == linShakingDelayed) {
            //设置振动延时
            Intent intent = new Intent(this, SetShakingTimeActivity.class);
            intent.putExtra("shakingTime", shakingTime);
            this.startActivityForResult(intent, SET_SHAKING_DELAYED);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SET_TIME) {
            //设置系统时间
            time = data.getStringExtra("time");
            txtTime.setText(time);
            mService.writeRXCharacteristic(BLEUtils.getTimeString(time));

        } else if (resultCode == SET_AC_TIME) {
            //设置采集时段
            acTime = data.getStringExtra("startTime") + "-" + data.getStringExtra("endTime");
            txtAcTime.setText(acTime);
            int startH = data.getIntExtra("startH", 0);
            int startm = data.getIntExtra("startm", 0);
            int endH = data.getIntExtra("endH", 0);
            int endm = data.getIntExtra("endm", 0);
            mService.writeRXCharacteristic(BLEUtils.getACTime(startH,startm,endH,endm));

        } else if (resultCode == SET_SAMPLING) {
            //设置采集频率
            sampling = data.getStringExtra("sampling");
            txtSampling.setText(sampling);
            mService.writeRXCharacteristic(BLEUtils.getHz(sampling));
        } else if (resultCode == SET_POSTURE) {
            //设置标准坐姿
            posture = data.getStringExtra("posture");
            txtPosture.setText(posture);
            mService.writeRXCharacteristic(BLEUtils.getPos(posture));
        } else if (resultCode == SET_SHAKING_NUM) {
            //设置振动次数
            shakingNum = data.getStringExtra("shakingNum");
            txtShakingNum.setText(shakingNum);
            mService.writeRXCharacteristic(BLEUtils.getNum(shakingNum));
        } else if (resultCode == SET_SHAKING_DELAYED) {
            //设置振动延时
            shakingTime = data.getStringExtra("shakingTime");
            txtShakingTime.setText(shakingTime);
            mService.writeRXCharacteristic(BLEUtils.getDelay(shakingTime));
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
