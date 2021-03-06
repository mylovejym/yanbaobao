package com.zhizhen.ybb.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.my.adapter.BankItemAdapter;
import com.zhizhen.ybb.util.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by psylife00 on 2017/6/4.
 */

public class SetPosTureActivity2 extends YbBaseActivity implements View.OnClickListener {

    @BindView(R.id.lv_sampling)
    ListView listView;

    private List<String> mItemBeans = new ArrayList<>();

    private BankItemAdapter bankItemAdapter;

    private String posture = "10°";

    @Override
    public int getLayoutId() {
        return R.layout.activity_sampling;
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
                    SpUtils.putString(this, "posture", posture);
                    Intent intent = new Intent(this, EditDataActivity.class);
                    intent.putExtra("posture", posture);
                    this.setResult(ParameterSetActivity.SET_POSTURE, intent);
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
        posture = bundle.getStringExtra("posture");
        System.out.println("posture = " + posture);

        for (int i = 10; i < 21; i += 5) {
            mItemBeans.add(i + "°");
        }
        bankItemAdapter = new BankItemAdapter(this, posture, mItemBeans);
        listView.setAdapter(bankItemAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            posture = "" + (mItemBeans.get(position));
            bankItemAdapter.refresh("" + posture);
        });
    }

    @Override
    public void onClick(View v) {
    }
}
