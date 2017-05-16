package com.zhizhen.ybb.my;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.bean.BaseBean;
import com.zhizhen.ybb.bean.EyesightBean;
import com.zhizhen.ybb.bean.PersonBean;
import com.zhizhen.ybb.my.contract.MyContract;
import com.zhizhen.ybb.my.model.MyVisonModel;
import com.zhizhen.ybb.my.presenter.MyVisonPresenter;

/**
 * Created by sandlovechao on 2017/5/15.
 */

public class MyVison extends YbBaseActivity<MyVisonPresenter, MyVisonModel> implements MyContract.MyVisonView {
    @Override
    public int getLayoutId() {
        return R.layout.avtivity_my_vison;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText(getString(R.string.my_vison))
                .setLeftImage(R.mipmap.tab_back)
                .setRightText(getString(R.string.complete))
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
                .build();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initdata() {
        mPresenter.getEyesightInfo("s71h2krjydnlf");
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
        LogUtil.d("e=>>>" + e);
    }

    @Override
    public void showEyesightInfo(EyesightBean mPersonInfo) {
        System.out.println(mPersonInfo.toString());
    }

    @Override
    public void showAddEyesightInfo(BaseBean baseBean) {
        System.out.println(baseBean.getStatus());
    }
}
