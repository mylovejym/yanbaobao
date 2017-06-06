package com.zhizhen.ybb.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.ItemListen;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 设定采集时段
 * Created by tc on 2017/5/22.
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

    int startH =8;
    int startm;
    int endH=22;
    int endm;

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
                    intent.putExtra("startH",startH);
                    intent.putExtra("startm",startm);
                    intent.putExtra("endH",endH);
                    intent.putExtra("endm",endm);
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

        List<String> item_1 = new ArrayList<>();
        List<String> item_2 = new ArrayList<>();
        List<String> item_3 = new ArrayList<>();
        item_1.add("上午");
        item_1.add("下午");
        for (int i = 0; i <= 59; i++) {
            if (i < 24) {
                item_2.add(i < 10 ? "0" + i : "" + i);
            }
            item_3.add(i < 10 ? "0" + i : "" + i);
        }


        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, (options1, option2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            if (type == START_TIME) {
                txtStartTime.setText(item_1.get(options1).equals("上午") ? "早上" + item_2.get(option2) + ":" + item_3.get(options3) : "晚上" + item_2.get(option2) + ":" + item_3.get(options3));
//                if(item_1.get(options1).equals("上午")){
//                    startH = Integer.parseInt(item_2.get(option2));
//                    startm = Integer.parseInt(item_3.get(options3));
//                }else{
//                    startH = Integer.parseInt(item_2.get(option2))+12;
//                    startm = Integer.parseInt(item_3.get(options3));
//                }
                startH = Integer.parseInt(item_2.get(option2));
                startm = Integer.parseInt(item_3.get(options3));
            } else if (type == END_TIME) {
                txtEndTime.setText(item_1.get(options1).equals("上午") ? "早上" + item_2.get(option2) + ":" + item_3.get(options3) : "晚上" + item_2.get(option2) + ":" + item_3.get(options3));
//                if(item_1.get(options1).equals("上午")){
//                    endH = Integer.parseInt(item_2.get(option2));
//                    endm = Integer.parseInt(item_3.get(options3));
//                }else{
//                    endH = Integer.parseInt(item_2.get(option2))+12;
//                    endm = Integer.parseInt(item_3.get(options3));
//                }
                endH = Integer.parseInt(item_2.get(option2));
                endm = Integer.parseInt(item_3.get(options3));
            }
        })
                .setTextColorCenter(this.getResources().getColor(R.color.blue_b3007aff))
                .setTitleSize(R.dimen.txt_size)
                .setLineSpacingMultiplier(2.0f)
                .build();
        String[] time = deTime.substring(2, deTime.length()).split(":");
        pvOptions.setSelectOptions(deTime.contains("早") ? 0 : 1, Integer.parseInt(time[0]), Integer.parseInt(time[1]));
        pvOptions.setItemListen(new ItemListen() {
            @Override
            public void onItemListenView_1(int index) {
                if (index == 0) {
                    if (pvOptions.getCurrentItem_2() > 12)
                        pvOptions.setSelectOptions(0, 0, pvOptions.getCurrentItem_3());
                    else
                        pvOptions.setSelectOptions(0, pvOptions.getCurrentItem_2(), pvOptions.getCurrentItem_3());
                }

                if (index == 1) {
                    if (pvOptions.getCurrentItem_2() > 12)
                        pvOptions.setSelectOptions(1, pvOptions.getCurrentItem_2(), pvOptions.getCurrentItem_3());
                    else
                        pvOptions.setSelectOptions(1, 13, pvOptions.getCurrentItem_3());
                }
            }

            @Override
            public void onItemListenView_2(int index) {
                if (pvOptions.getCurrentItem_2() > 12) {
                    if (pvOptions.getCurrentItem_1() == 0) {
                        pvOptions.setSelectOptions(1, pvOptions.getCurrentItem_2(), pvOptions.getCurrentItem_3());
                    }
                }
                if (pvOptions.getCurrentItem_2() < 12) {
                    if (pvOptions.getCurrentItem_1() == 1)
                        pvOptions.setSelectOptions(0, pvOptions.getCurrentItem_2(), pvOptions.getCurrentItem_3());
                }
            }

            @Override
            public void onItemListenView_3(int index) {
            }
        });
        pvOptions.setNPicker(item_1, item_2, item_3);
        pvOptions.show();

    }

}
