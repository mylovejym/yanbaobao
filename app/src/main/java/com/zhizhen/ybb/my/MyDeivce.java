package com.zhizhen.ybb.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.StringUtils;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.bean.DeviceInfo;
import com.zhizhen.ybb.my.adapter.BankItemAdapter;
import com.zhizhen.ybb.my.adapter.DeviceAdapter;
import com.zhizhen.ybb.my.interFace.BindingDeviceOnClickListener;
import com.zhizhen.ybb.util.SpUtils;
import com.zhizhen.ybb.view.SwitchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 我的设备
 * Created by tc on 2017/5/19.
 */

public class MyDeivce extends YbBaseActivity implements View.OnClickListener {

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_device;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText(getString(R.string.my_device))
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
                SpUtils.putBoolean(MyDeivce.this, "switchView", true);
                view.toggleSwitch(true); // or false
            }

            @Override
            public void toggleToOff(SwitchView view) {
                SpUtils.putBoolean(MyDeivce.this, "switchView", false);
                view.toggleSwitch(false); // or true
            }
        });
    }

    @Override
    public void initdata() {
        List<DeviceInfo> list = new ArrayList<>();
        DeviceInfo deviceInfo;
        for (int i = 0; i < 5; i++) {
            if (i == 3)
                deviceInfo = new DeviceInfo("TRWOMOPD-EYE-1" + i, "1");
            else
                deviceInfo = new DeviceInfo("TRWOMOPD-EYE-1" + i, "2");
            list.add(deviceInfo);
        }
        deviceAdapter = new DeviceAdapter(this, list);
        deviceAdapter.setBindingDeviceOnClickListener(new BindingDeviceOnClickListener() {
            @Override
            public void onBindingOnClickListener(View view, int pos, String bindingState) {
                if (bindingState.equals("1")) {
                    list.get(pos).setDeviceState("2");
                } else {
                    list.get(pos).setDeviceState("1");
                }
                deviceAdapter.refresh(list);
            }
        });
        listDevice.setAdapter(deviceAdapter);
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
}
