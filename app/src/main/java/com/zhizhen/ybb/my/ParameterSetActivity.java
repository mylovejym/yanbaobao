package com.zhizhen.ybb.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;

import butterknife.BindView;

/**
 * 参数配置
 * Created by tc on 2017/5/22.
 */

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
//                .setRightText(getString(R.string.complete))
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
//                .setRightOnClickListener(v -> {
//                    Intent intent = new Intent(this, EditDataActivity.class);
//                    intent.putExtra("sex", sex);
//                    this.setResult(EditDataActivity.SEX, intent);
//                    this.finish();
//                })
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

    }

    @Override
    public void onClick(View v) {
        if (v == linSetTime){
            //设置系统时间
            Intent intent = new Intent(this, SetTimeActivity.class);
            this.startActivityForResult(intent, SET_TIME);
        } else if (v == linSetACTime){
            //设置采集时段
            Intent intent = new Intent(this, SetACTimeActivity.class);
            this.startActivityForResult(intent, SET_AC_TIME);
        } else if (v == linSetSampling){
            //设置采集频率
            Intent intent = new Intent(this, SetSamplingActivity.class);
            this.startActivityForResult(intent, SET_SAMPLING);
        } else if (v == linSetPosture){
            //设置标准坐姿
            Intent intent = new Intent(this, SetPosTureActivity.class);
            this.startActivityForResult(intent, SET_POSTURE);
        } else if (v == linShakingNum){
            //设置振动次数
            Intent intent = new Intent(this, SetShakingNumActivity.class);
            this.startActivityForResult(intent, SET_SHAKING_NUM);
        } else if (v == linShakingDelayed){
            //设置振动延时
            Intent intent = new Intent(this, SetShakingTimeActivity.class);
            this.startActivityForResult(intent, SET_SHAKING_DELAYED);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SET_TIME){
            //设置系统时间
            txtTime.setText(data.getStringExtra("time"));
        } else if (resultCode == SET_AC_TIME){
            //设置采集时段
            txtAcTime.setText(data.getStringExtra("startTime") + "-" + data.getStringExtra("endTime"));
        } else if (resultCode == SET_SAMPLING){
            //设置采集频率
            txtSampling.setText(data.getStringExtra("sampling"));
        } else if (resultCode == SET_POSTURE){
            //设置标准坐姿
            txtPosture.setText(data.getStringExtra("posture"));
        } else if (resultCode == SET_SHAKING_NUM){
            //设置振动次数
            txtShakingNum.setText(data.getStringExtra("shakingNum"));
        } else if (resultCode == SET_SHAKING_DELAYED){
            //设置振动延时
            txtShakingTime.setText(data.getStringExtra("shakingTime"));
        }
    }
}
