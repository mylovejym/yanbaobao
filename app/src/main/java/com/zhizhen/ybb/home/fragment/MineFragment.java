package com.zhizhen.ybb.home.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseApplication;
import com.zhizhen.ybb.base.YbBaseFragment;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.PersonInfo;
import com.zhizhen.ybb.lanya.UartService;
import com.zhizhen.ybb.loginpass.LoginActivity;
import com.zhizhen.ybb.my.EditDataActivity;
import com.zhizhen.ybb.my.FollowActivity;
import com.zhizhen.ybb.my.MyVisonActivity;
import com.zhizhen.ybb.my.ParameterSetActivity;
import com.zhizhen.ybb.my.contract.MyContract;
import com.zhizhen.ybb.my.model.MyModelImp;
import com.zhizhen.ybb.my.presenter.MyPresenterImp;
import com.zhizhen.ybb.util.BLEUtils;
import com.zhizhen.ybb.util.DateUtil;
import com.zhizhen.ybb.util.GlideCircleTransform;
import com.zhizhen.ybb.util.SpUtils;

import static com.zhizhen.ybb.util.Utils.hexStringToBytes;

/**
 * Created by psylife00 on 2017/5/12.
 */

public class MineFragment extends YbBaseFragment<MyPresenterImp, MyModelImp> implements MyContract.MyView, View.OnClickListener {

    public static final int EDIT_DATA = 10023;

//    @BindView(R.id.txt_name)
    TextView txtName;

//    @BindView(R.id.image_head_photo)
    ImageView imageHeadPhoto;

//    @BindView(R.id.image_sex)
    ImageView imageSex;

//    @BindView(R.id.txt_age)
    TextView txtAge;

//    @BindView(R.id.rl_vison)
    RelativeLayout rlVison;

//    @BindView(R.id.rl_device)
    RelativeLayout rlDevice;

//    @BindView(R.id.rl_parameter_set)
    RelativeLayout rlParameterSet;

//    @BindView(R.id.rl_follow)
    RelativeLayout rlFollow;

//    @BindView(R.id.lin_edit_data)
    LinearLayout linEditData;

//    @BindView(R.id.bt_exit)
    Button btExit;

    TextView state_tv;

    private Context context;

    private PersonInfo mPersonInfo;

    private boolean isLoad = true;// 是否网络加载

    private UartService mService = null;

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(getActivity(), 0);
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        txtName = (TextView) view.findViewById(R.id.txt_name);
        imageHeadPhoto = (ImageView) view.findViewById(R.id.image_head_photo);
        imageSex = (ImageView) view.findViewById(R.id.image_sex);
        txtAge = (TextView) view.findViewById(R.id.txt_age);
        rlVison = (RelativeLayout) view.findViewById(R.id.rl_vison);
        rlDevice = (RelativeLayout) view.findViewById(R.id.rl_device);
        rlParameterSet = (RelativeLayout) view.findViewById(R.id.rl_parameter_set);
        rlFollow = (RelativeLayout) view.findViewById(R.id.rl_follow);
        linEditData = (LinearLayout) view.findViewById(R.id.lin_edit_data);
        btExit = (Button) view.findViewById(R.id.bt_exit);
        state_tv = (TextView) view.findViewById(R.id.state_tv);

        context = this.getContext();
        rlVison.setOnClickListener(this);
        rlFollow.setOnClickListener(this);
        rlDevice.setOnClickListener(this);
        rlParameterSet.setOnClickListener(this);
        linEditData.setOnClickListener(this);
        btExit.setOnClickListener(this);
        service_init();
    }

    @Override
    public void initData() {
        System.out.println("initData");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != SpUtils.getPersonInfo(this.getActivity())) {
            this.mPersonInfo = SpUtils.getPersonInfo(this.getActivity());
            showView();
        } else {
            loadData();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == linEditData) {
            Intent intent = new Intent(this.getContext(), EditDataActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("personInfo", mPersonInfo);
            intent.putExtras(bundle);
            startActivityForResult(intent, EDIT_DATA);
        } else if (v == rlVison) {
            //我的视力
            Intent intent = new Intent(this.getContext(), MyVisonActivity.class);
            this.getContext().startActivity(intent);
        } else if (v == rlDevice) {
            //我的设备
//            Intent intent = new Intent(this.getContext(), MyDeivceActivity.class);
//            this.getContext().startActivity(intent);
            if(SpUtils.getBindBLEDevice(getActivity()) == null){
                Toast.makeText(getActivity(),"请先绑定设备",Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this.getContext(), ParameterSetActivity.class);
            this.getContext().startActivity(intent);
        } else if (v == rlParameterSet) {
            //参数设置

            Intent intent = new Intent(this.getContext(), ParameterSetActivity.class);
            this.getContext().startActivity(intent);

        } else if (v == rlFollow) {
            //关注
            Intent intent = new Intent(this.getContext(), FollowActivity.class);
            this.getContext().startActivity(intent);
        } else if (v == btExit) {
            //退出
            SpUtils.clean(getActivity());
            Intent intent = new Intent(this.getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.getContext().startActivity(intent);
            this.getActivity().finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void showError(Throwable e) {
        this.stopProgressDialog();
        Toast.makeText(this.getContext(), "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
        LogUtil.d("e=>>>" + e);
    }

    @Override
    public void showPersonInfo(BaseClassBean<PersonInfo> mPersonInfos) {
        this.stopProgressDialog();
        this.mPersonInfo = mPersonInfos.getData();
        if (mPersonInfos.getStatus().equals("0")) {
            SpUtils.setPersonInfo(this.getActivity(), mPersonInfo);
            showView();
        } else {
            if (mPersonInfos.getStatus().equals("1009")) {
                Intent intent = new Intent(this.getContext(), LoginActivity.class);
                this.getContext().startActivity(intent);
                this.getActivity().finish();
            }
            Toast.makeText(this.getContext(), mPersonInfos.getStatusInfo(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void initLazyView() {
        System.out.println("initLazyView");
    }

    private void loadData() {
        if (isLoad) {
            this.startProgressDialog(this.getContext());
            mPresenter.getPersonInfo(YbBaseApplication.instance.getToken());
        }

        isLoad = false;
    }

    private void showView() {
        try {
            if (mPersonInfo.getUsername() != null)
                txtName.setText(mPersonInfo.getUsername());

            if (mPersonInfo.getBorn() != null) {
                txtAge.setVisibility(View.VISIBLE);
                txtAge.setText("年龄：" + DateUtil.getAge(mPersonInfo.getBorn()));
            }

            if (mPersonInfo.getSex() != null) {
                imageSex.setVisibility(View.VISIBLE);
                if (mPersonInfo.getSex().equals("1")) {
                    imageSex.setImageDrawable(this.getResources().getDrawable(R.mipmap.icon_man));
                } else {
                    imageSex.setImageDrawable(this.getResources().getDrawable(R.mipmap.icon_girl));
                }
            }

            if (mPersonInfo.getPhoto() != null) {

                Glide.with(this).load(mPersonInfo.getPhoto())
                        .placeholder(R.mipmap.wellcom) //设置占位图
                        .error(R.mipmap.wellcom) //设置错误图片
                        .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                        .transform(new GlideCircleTransform(context)).into(imageHeadPhoto);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == EDIT_DATA) {
            isLoad = true;
            loadData();
        }
    }


    private void service_init() {
        Intent bindIntent = new Intent(getActivity(), UartService.class);
        getActivity().bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(UARTStatusChangeReceiver, makeGattUpdateIntentFilter());
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTING);
        return intentFilter;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            mService = ((UartService.LocalBinder) rawBinder).getService();
            Log.d(TAG, "onServiceConnected mService= " + mService);
            mService.initialize();


            if (mService.isBleConnect()) {
                mService.writeRXCharacteristic(hexStringToBytes("AA03060055"));

            }
            if(mService.getmConnectionState()==0){
                state_tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                state_tv.setText("连接失败");
            }else if(mService.getmConnectionState()==1){
                state_tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                state_tv.setText("正在连接");
            }



            if(SpUtils.getBindBLEDevice(getActivity()) == null||!SpUtils.getBoolean(getContext(), "isbinded", false)){
                state_tv.setText("未绑定");

            }


        }

        public void onServiceDisconnected(ComponentName classname) {
            ////     mService.disconnect(mDevice);
            mService = null;
        }
    };



    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            LogUtil.e("actionactionactionactionaction:" + action);

            final Intent mIntent = intent;
            //*********************//
            if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
//                conn_text.setVisibility(View.GONE);
//                conn_text.setText("已连接");
                mService.writeRXCharacteristic(hexStringToBytes("AA03060055"));
            }

            //*********************//
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
//                conn_text.setVisibility(View.VISIBLE);
                state_tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                state_tv.setText("连接失败");


            }


            //*********************//
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {

            }
            if (action.equals(UartService.ACTION_GATT_CONNECTING)) {
                state_tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                state_tv.setText("正在连接");
            }
            //*********************//
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {

                final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);

                try {
//                            String text = new String(txValue, "UTF-8");
                    String text = BLEUtils.bytesToHexString(txValue);
//                            String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    LogUtil.e("RX:" + text);
//                            listAdapter.add("["+currentDateTimeString+"] RX: "+text);
//                            messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                    if(text.startsWith("aa0306")){
                        String str = text.substring(6,8);
                        LogUtil.e("str:" + str);
                        int d =Integer.parseInt(str, 16);
                        if(getActivity()!=null)
                        state_tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_00aded));
                        state_tv.setText("电量"+d+"%");
                    }

                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }


            }
            //*********************//
            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)) {
//                showMessage("Device doesn't support UART. Disconnecting");

            }


        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(UARTStatusChangeReceiver);
        } catch (Exception ignore) {
            Log.e(TAG, ignore.toString());
        }
        try {
            getActivity().unbindService(mServiceConnection);
        } catch (Exception ignore) {
            Log.e(TAG, ignore.toString());
        }
    }
}
