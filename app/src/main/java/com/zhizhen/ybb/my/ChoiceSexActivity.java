package com.zhizhen.ybb.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.base.YbBaseApplication;
import com.zhizhen.ybb.bean.BaseBean;
import com.zhizhen.ybb.bean.EyesightBean;
import com.zhizhen.ybb.my.contract.MyContract;
import com.zhizhen.ybb.my.model.MyVisonModel;
import com.zhizhen.ybb.my.presenter.MyVisonPresenter;

import butterknife.BindView;

/**
 * 选择性别界面
 * Created by sandlovechao on 2017/5/15.
 */

public class ChoiceSexActivity extends YbBaseActivity implements View.OnClickListener {

    @BindView(R.id.rl_choice_man)
    RelativeLayout rlMan;

    @BindView(R.id.rl_choice_girl)
    RelativeLayout rlGirl;

    @BindView(R.id.image_choice_man)
    ImageView imageMan;

    @BindView(R.id.image_choice_girl)
    ImageView imageGirl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_choice_sex;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText(getString(R.string.edit_data))
                .setLeftImage(R.mipmap.tab_back)
                .setRightText(getString(R.string.complete))
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
                .setRightOnClickListener(v -> finish())
                .build();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        rlMan.setOnClickListener(this);
        rlGirl.setOnClickListener(this);
    }

    @Override
    public void initdata() {

    }

    @Override
    public void onClick(View v) {
        if (v == rlMan) {
            imageMan.setVisibility(View.VISIBLE);
            imageGirl.setVisibility(View.GONE);
        } else if (v == rlGirl){
            imageMan.setVisibility(View.GONE);
            imageGirl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
