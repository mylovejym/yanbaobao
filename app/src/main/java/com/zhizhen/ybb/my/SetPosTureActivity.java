package com.zhizhen.ybb.my;

import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.bigkoo.pickerview.*;
import com.psylife.wrmvplibrary.utils.*;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.*;

import java.util.*;

import butterknife.*;

/**
 * 设定前倾提醒(废弃)
 * Created by tc on 2017/5/22.
 */

public class SetPosTureActivity extends YbBaseActivity {

    @BindView(R.id.txt_posture)
    TextView txtPosture;

    @Override
    public int getLayoutId() {
        return R.layout.avtivity_set_posture;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText(getString(R.string.set_posture))
                .setLeftImage(R.mipmap.tab_back)
                .setRightText(getString(R.string.complete))
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
                .setRightOnClickListener(v -> {
                    Intent intent = new Intent(this, EditDataActivity.class);
                    intent.putExtra("posture", txtPosture.getText().toString().trim());
                    this.setResult(ParameterSetActivity.SET_POSTURE, intent);
                    this.finish();
                })
                .build();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        txtPosture.setOnClickListener(v -> {
            showTime();
        });
    }



    @Override
    public void initdata() {
//        Intent bundle = this.getIntent();
//        txtPosture.setText(bundle.getStringExtra("posture"));
    }

    private void showTime() {
        List<String> item_1 = new ArrayList<>();
        List<String> item_2 = new ArrayList<>();
        List<String> item_3 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            item_1.add("+" + i + "°");
            item_3.add("-" + i + "°");
        }
        item_2.add("-");


        //条件选择器
        OptionsPickerView pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                txtPosture.setText(item_1.get(options1) + "-" + item_3.get(options3));
            }
        })
                .setTextColorCenter(this.getResources().getColor(R.color.blue_b3007aff))
                .setTitleSize(R.dimen.txt_size)
                .setLineSpacingMultiplier(2.0f)
                .build();
        pvOptions.setNPicker(item_1, item_2, item_3);
        pvOptions.show();
    }
}
