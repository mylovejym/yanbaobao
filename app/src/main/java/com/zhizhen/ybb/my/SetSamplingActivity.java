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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 设定采集频率
 * Created by tc on 2017/5/19.
 */

public class SetSamplingActivity extends YbBaseActivity implements View.OnClickListener {

    @BindView(R.id.lv_sampling)
    ListView listView;

    private List<String> mItemBeans = new ArrayList<>();

    private BankItemAdapter bankItemAdapter;

    private String sampling = "1";

    @Override
    public int getLayoutId() {
        return R.layout.activity_sampling;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText(getString(R.string.set_sampling))
                .setLeftImage(R.mipmap.tab_back)
                .setRightText(getString(R.string.complete))
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
                .setRightOnClickListener(v -> {
                    Intent intent = new Intent(this, EditDataActivity.class);
                    intent.putExtra("sampling", sampling);
                    this.setResult(ParameterSetActivity.SET_SAMPLING, intent);
                    this.finish();
                })
                .build();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initdata() {
//        Intent bundle = this.getIntent();
//        sampling = bundle.getStringExtra("sampling");
        for (int i = 1; i <= 4; i++) {
            if (i == 1 || i == 2 || i == 4)
                mItemBeans.add("每" + i + "秒一次");
        }
        bankItemAdapter = new BankItemAdapter(this, sampling, mItemBeans);
        listView.setAdapter(bankItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sampling = "" + (mItemBeans.get(position));
                bankItemAdapter.refresh("" + sampling);
            }
        });
    }

    @Override
    public void onClick(View v) {
    }
}
