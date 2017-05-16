package com.zhizhen.ybb.my;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.LogUtil;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.bean.PersonInfo;
import com.zhizhen.ybb.my.contract.MyContract.GetPersonInfoView;
import com.zhizhen.ybb.my.model.MyModel;
import com.zhizhen.ybb.my.presenter.MyPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 作者：tc on 2017/5/15.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */
public class MyActivity extends YbBaseActivity<MyPresenter, MyModel> implements GetPersonInfoView, OnClickListener {

    @BindView(R.id.txt_name)
    TextView txtName;

    @BindView(R.id.image_head_photo)
    ImageView imageHeadPhoto;

    @BindView(R.id.image_sex)
    ImageView imageSex;

    @BindView(R.id.txt_age)
    TextView txtAge;

    @BindView(R.id.rl_vison)
    RelativeLayout rlVison;

    @BindView(R.id.rl_device)
    RelativeLayout rlDevice;

    @BindView(R.id.rl_follow)
    RelativeLayout rlFollow;

    @BindView(R.id.bt_exit)
    Button btExit;

    private Context context;

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        context = this;
    }

    @Override
    public void initdata() {
        this.startProgressDialog(this);
        rlVison.setOnClickListener(this);
        Map map = new HashMap();
        map.put("dcreatedate", "201705151633");
        map.put("spid", "17621159290");
        map.put("Content-Type", "application/x-www-form-urlencoded");
        RxService.setHeaders(map);
        mPresenter.getPersonInfo("s71h2krjydnlf");
    }

    @Override
    public void onClick(View v) {
        if (v == rlVison) {
            startActivity(MyVison.class);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(Throwable e) {
        this.stopProgressDialog();
        Toast.makeText(this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
        LogUtil.d("e=>>>" + e);
    }

    @Override
    public void showPersonInfo(PersonInfo mPersonInfo) {
        this.stopProgressDialog();
        txtName.setText(mPersonInfo.getUsername());
        Glide.with(this).load(mPersonInfo.getPhoto()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageHeadPhoto) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageHeadPhoto.setImageDrawable(circularBitmapDrawable);
            }
        });

    }
}
