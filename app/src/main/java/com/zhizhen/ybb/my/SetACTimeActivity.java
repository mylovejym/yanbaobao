package com.zhizhen.ybb.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

/**
 * 设定采集时段
 * Created by sandlovechao on 2017/5/22.
 */

public class SetACTimeActivity extends YbBaseActivity {

    private static final int START_TIME = 0;

    private static final int END_TIME = 1;

    @BindView(R.id.rl_start_time)
    RelativeLayout rlStartTime;

    @BindView(R.id.rl_end_time)
    RelativeLayout rlEndTime;

    @BindView(R.id.txt_start_time)
    TextView txtStartTime;

    @BindView(R.id.txt_end_time)
    TextView txtEndTime;

    @Override
    public int getLayoutId() {
        return R.layout.avtivity_set_ac_time;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText(getString(R.string.set_ac_time))
                .setLeftImage(R.mipmap.tab_back)
                .setRightText(getString(R.string.complete))
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
                .setRightOnClickListener(v -> {
                    Intent intent = new Intent(this, EditDataActivity.class);
                    intent.putExtra("startTime", txtStartTime.getText().toString().trim());
                    intent.putExtra("endTime", txtEndTime.getText().toString().trim());
                    this.setResult(ParameterSetActivity.SET_AC_TIME, intent);
                    this.finish();
                })
                .build();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        rlStartTime.setOnClickListener(v -> {
            showTime(txtStartTime.getText().toString(), START_TIME);
        });

        rlEndTime.setOnClickListener(v -> {
            showTime(txtEndTime.getText().toString(), END_TIME);
        });
    }

    @Override
    public void initdata() {
//        Intent bundle = this.getIntent();
//        txtStartTime.setText(bundle.getStringExtra("startTime"));
//        txtEndTime.setText(bundle.getStringExtra("endTime"));
    }

    private void showTime(String deTime, int type) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            // 指定一个日期
            Date d = dateFormat.parse(deTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            //时间选择器
            TimePickerView pvTime = new TimePickerView.Builder(this, (date, v1) -> {
                if (type == START_TIME) {
                    txtStartTime.setText(dateFormat.format(date));
                } else if (type == END_TIME) {
                    txtEndTime.setText(dateFormat.format(date));
                }
            })
                    .setType(new boolean[]{true, true, true, false, false, false})
                    .setTextColorCenter(this.getResources().getColor(R.color.blue_b3007aff))
                    .setTitleSize(R.dimen.txt_size)
                    .isCenterLabel(true)
                    .build();
            pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
            pvTime.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
