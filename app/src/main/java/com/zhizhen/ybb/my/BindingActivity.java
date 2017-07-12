package com.zhizhen.ybb.my;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.PersonInfo;
import com.zhizhen.ybb.lanya.UartService;
import com.zhizhen.ybb.my.contract.MyContract;
import com.zhizhen.ybb.my.model.MyModelImp;
import com.zhizhen.ybb.my.presenter.MyPresenterImp;
import com.zhizhen.ybb.view.WaveView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：tc on 2017/5/11.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */
public class BindingActivity extends YbBaseActivity<MyPresenterImp, MyModelImp> implements MyContract.MyView, View.OnClickListener {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0) {
                mWaveView.setText(a + "%");
                if (a != 100) {
                    mWaveView.setTextState("正在连接...");
                } else {
                    mWaveView.setTextState("连接成功");
//                mWaveView.setWaveShiftRatio(1f);
                    mAnimatorSet.end();
                }
            }else if(msg.what==1){
                mWaveView.setTextState("连接失败");
                mAnimatorSet.end();
            }

        }
    };

    private int a = 0;

    @BindView(R.id.costom_round)
    WaveView mWaveView;

    private AnimatorSet mAnimatorSet;

    private String address;

    private UartService mService = null;

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_binding;
    }

    @Override
    public View getTitleView() {
        View view = LayoutInflater.from(this).inflate(R.layout.view_title_bar, null);
        view.findViewById(R.id.image_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return view;
    }

//    Runnable run = new Runnable() {
//        @Override
//        public void run() {
//            mHandler.sendEmptyMessage(0);
//        }
//    };

    Thread thread;

    @Override
    public void initView(Bundle savedInstanceState) {
        address = getIntent().getStringExtra("address");

        mWaveView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        initAnimation();
        mWaveView.setShowWave(true);
        if (mAnimatorSet != null) {
            mAnimatorSet.start();
        }
        service_init();


        thread = new Thread() {
            @Override
            public void run() {

                while (a < 90) {
                    try {
                        if (a == 50) {
                            Thread.sleep(2000);
                        }
                        Thread.sleep(100);
                        mHandler.sendEmptyMessage(0);
                        if(a < 90)
                            a++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
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
//        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
//        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
//        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        return intentFilter;
    }

    //UART service connected/disconnected
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            mService = ((UartService.LocalBinder) rawBinder).getService();
            Log.d(TAG, "onServiceConnected mService= " + mService);
            if (!mService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            mService.connect(address);

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
                        Log.d(TAG, "UART_CONNECT_MSG");
                        Toast.makeText(BindingActivity.this,"连接成功",Toast.LENGTH_LONG).show();
                    }
                });
                a= 100;
                mHandler.sendEmptyMessage(0);
            }

            //*********************//
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
               a=90;
                mHandler.sendEmptyMessage(1);
            }




//            //*********************//
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {
//                mService.enableTXNotification();
            }
//            //*********************//

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

    private void initAnimation() {
        List<Animator> animators = new ArrayList<>();

        // horizontal animation.
        // wave waves infinitely.
        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                mWaveView, "waveShiftRatio", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(1000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        animators.add(waveShiftAnim);

        // vertical animation.
        // water level increases from 0 to center of WaveView
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(
                mWaveView, "waterLevelRatio", 0.4f, 0.4f);
        waterLevelAnim.setDuration(10000);
        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
        animators.add(waterLevelAnim);

        // amplitude animation.
        // wave grows big then grows small, repeatedly
        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
                mWaveView, "amplitudeRatio", 0.0001f, 0.05f);
        amplitudeAnim.setRepeatCount(ValueAnimator.RESTART);
        amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
        amplitudeAnim.setDuration(50 * 100);
        amplitudeAnim.setInterpolator(new LinearInterpolator());
        animators.add(amplitudeAnim);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animators);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showPersonInfo(BaseClassBean<PersonInfo> mPersonInfo) {

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
