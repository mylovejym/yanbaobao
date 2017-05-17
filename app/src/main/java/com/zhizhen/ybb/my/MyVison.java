package com.zhizhen.ybb.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.base.YbBaseApplication;
import com.zhizhen.ybb.bean.BaseBean;
import com.zhizhen.ybb.bean.EyesightBean;
import com.zhizhen.ybb.bean.PersonBean;
import com.zhizhen.ybb.my.contract.MyContract;
import com.zhizhen.ybb.my.model.MyVisonModel;
import com.zhizhen.ybb.my.presenter.MyVisonPresenter;

import butterknife.BindView;

/**
 *
 * Created by sandlovechao on 2017/5/15.
 */

public class MyVison extends YbBaseActivity<MyVisonPresenter, MyVisonModel> implements MyContract.MyVisonView {


    @BindView(R.id.edt_edit_left_eye_degree)
    EditText edtLeftDegree;

    @BindView(R.id.edt_edit_right_eye_degree)
    EditText edtRightDegree;

    @BindView(R.id.edt_edit_left_eye_astigmatism)
    EditText edtLeftAstigmatism;

    @BindView(R.id.edt_edit_right_eye_astigmatism)
    EditText edtRightAstigmatism;

    @BindView(R.id.edt_edit_eye_pupil)
    EditText edtPupil;

    @BindView(R.id.lin_take_photo)
    LinearLayout linTakPhoto;

    private String leftDegree, rightDegree, leftAstigmatism, rightAstigmatism, pupil;


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
                .setRightOnClickListener(v -> mPresenter.addEyesightInfo(YbBaseApplication.getInstance().getToken(), leftDegree, rightDegree, leftAstigmatism, rightAstigmatism, pupil))
                .build();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        leftDegree = edtLeftDegree.getText().toString().trim();
        rightDegree = edtRightDegree.getText().toString().trim();
        leftAstigmatism = edtLeftAstigmatism.getText().toString().trim();
        rightAstigmatism = edtRightAstigmatism.getText().toString().trim();
        pupil = edtPupil.getText().toString().trim();
    }

    @Override
    public void initdata() {
        mPresenter.getEyesightInfo(YbBaseApplication.getInstance().getToken());
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
    public void showEyesightInfo(EyesightBean mEyesightInfo) {
        if (mEyesightInfo.getStatus().equals("0")) {
            edtLeftDegree.setText(mEyesightInfo.getData().get(0).getLeft_eye_degree());
            edtRightDegree.setText(mEyesightInfo.getData().get(0).getRight_eye_degree());
            edtLeftAstigmatism.setText(mEyesightInfo.getData().get(0).getLeft_eye_astigmatism());
            edtRightAstigmatism.setText(mEyesightInfo.getData().get(0).getRight_eye_astigmatism());
            edtPupil.setText(mEyesightInfo.getData().get(0).getPupillary_distance());
        } else {
            Toast.makeText(this, mEyesightInfo.getStatusInfo(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showAddEyesightInfo(BaseBean baseBean) {

        if (baseBean.getStatus().equals("0")) {
            this.finish();
        } else {
            Toast.makeText(this, baseBean.getStatusInfo(), Toast.LENGTH_LONG).show();
        }
    }
}
