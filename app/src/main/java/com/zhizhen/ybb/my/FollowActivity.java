package com.zhizhen.ybb.my;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.base.YbBaseApplication;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.FollowInfo;
import com.zhizhen.ybb.my.contract.MyContract;
import com.zhizhen.ybb.my.model.FollowModelImp;
import com.zhizhen.ybb.my.presenter.FollowPresenterImp;

import butterknife.BindView;

/**
 * Created by tc on 2017/5/24.
 */

public class FollowActivity extends YbBaseActivity<FollowPresenterImp, FollowModelImp> implements MyContract.FollowView {

    @BindView(R.id.image_follow)
    ImageView image;

    @Override
    public int getLayoutId() {
        return R.layout.activity_follow;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText(getString(R.string.follow))
                .setLeftImage(R.mipmap.tab_back)
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
                .build();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initdata() {
        mPresenter.focusMe(YbBaseApplication.getInstance().getToken());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUrl(BaseClassBean<FollowInfo> mInfo) {
        System.out.println(mInfo.getData().getSaoma());
        if (mInfo.getStatus().equals("0")) {
            Glide.with(this).load(mInfo.getData().getSaoma()).into(image);
        } else {
            Toast.makeText(this, mInfo.getStatusInfo(), Toast.LENGTH_SHORT).show();
        }
    }
}
