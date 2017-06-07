package com.zhizhen.ybb.my;

import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.psylife.wrmvplibrary.utils.*;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.*;
import com.zhizhen.ybb.my.adapter.*;
import com.zhizhen.ybb.util.SpUtils;

import java.util.*;

import butterknife.*;

/**
 * 设定振动延时
 * Created by tc on 2017/5/19.
 */

public class SetShakingTimeActivity extends YbBaseActivity implements View.OnClickListener {

    @BindView(R.id.lv_sampling)
    ListView listView;

    private List<String> mItemBeans = new ArrayList<>();

    private BankItemAdapter bankItemAdapter;

    private String num = "5s";

    @Override
    public int getLayoutId() {
        return R.layout.activity_sampling;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText(getString(R.string.set_shaking_delayed))
                .setLeftImage(R.mipmap.tab_back)
                .setRightText(getString(R.string.complete))
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
                .setRightOnClickListener(v -> {
                    SpUtils.putString(this, "shakingTime", num); //存储振动延时
                    Intent intent = new Intent(this, EditDataActivity.class);
                    intent.putExtra("shakingTime", num);
                    this.setResult(ParameterSetActivity.SET_SHAKING_DELAYED, intent);
                    this.finish();
                })
                .build();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initdata() {
        Intent bundle = this.getIntent();
        num = bundle.getStringExtra("shakingTime");
        System.out.println("num = " + num);

        if (null == SpUtils.getString(this, "shakingTime")) {
            num = ("" + SpUtils.getString(this, "shakingTime"));
        }

        for (int i = 1; i <= 60; i++) {
            if (i == 5 || i == 30 || i == 60)
                mItemBeans.add(i + "s");
        }
        bankItemAdapter = new BankItemAdapter(this, num, mItemBeans);
        listView.setAdapter(bankItemAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            num = "" + (mItemBeans.get(position));
            bankItemAdapter.refresh("" + num);
        });
    }

    @Override
    public void onClick(View v) {
    }
}
