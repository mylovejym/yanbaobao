package com.zhizhen.ybb.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.my.adapter.BankItemAdapter;
import com.zhizhen.ybb.util.*;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 设定振动次数
 * Created by tc on 2017/5/19.
 */

public class SetShakingNumActivity extends YbBaseActivity implements View.OnClickListener {

    @BindView(R.id.lv_sampling)
    ListView listView;

    private List<String> mItemBeans = new ArrayList<>();

    private BankItemAdapter bankItemAdapter;

    private String num = "振动1次";

    @Override
    public int getLayoutId() {
        return R.layout.activity_sampling;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText(getString(R.string.set_shaking_num))
                .setLeftImage(R.mipmap.tab_back)
                .setRightText(getString(R.string.complete))
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
                .setRightOnClickListener(v -> {
                    SpUtils.putString(this, "shakingNum", num); //存储振动次数
                    Intent intent = new Intent(this, EditDataActivity.class);
                    intent.putExtra("shakingNum", num);
                    this.setResult(ParameterSetActivity.SET_SHAKING_NUM, intent);
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
        num = bundle.getStringExtra("shakingNum");
        System.out.println("num = " + num);

        for (int i = 1; i <= 3; i++) {
            mItemBeans.add("振动" + i + "次");
        }
        bankItemAdapter = new BankItemAdapter(this, num, mItemBeans);
        listView.setAdapter(bankItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                num = "" + (mItemBeans.get(position));
                bankItemAdapter.refresh("" + num);
            }
        });
    }

    @Override
    public void onClick(View v) {
    }
}
