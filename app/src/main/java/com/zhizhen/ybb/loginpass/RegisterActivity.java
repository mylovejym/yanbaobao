package com.zhizhen.ybb.loginpass;

import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.psylife.wrmvplibrary.utils.*;
import com.psylife.wrmvplibrary.utils.timeutils.*;
import com.zhizhen.ybb.*;
import com.zhizhen.ybb.base.*;
import com.zhizhen.ybb.bean.*;
import com.zhizhen.ybb.home.*;
import com.zhizhen.ybb.loginpass.contract.*;
import com.zhizhen.ybb.loginpass.model.*;
import com.zhizhen.ybb.loginpass.presenter.*;
import com.zhizhen.ybb.util.*;
import com.zhizhen.ybb.util.DateUtil;
import com.zhizhen.ybb.util.SpUtils;
import com.zhizhen.ybb.view.*;

public class RegisterActivity extends YbBaseActivity<RegisterPresenter, RegisterModel> implements RegisterContract.GetPhoneCodeView {
    private EditText edit_phone, edit_pass, edit_code;
    private Button btn_login;
    private Button btn_getcode;
    private CheckBox checkbox;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.background_color));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this)
                .setTitleText("注冊")
                .setTitleTextColor(this, R.color.white)
                .setLeftImage(R.mipmap.tab_back)
                .setTitleBgRes(R.color.background_color)
                .setLeftOnClickListener(v -> finish())
                .build();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        init();
    }

    @Override
    public void initdata() {
        onClick();
    }

    private void init() {
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        checkbox.setChecked(true);
        edit_code = (EditText) findViewById(R.id.edit_code);
        edit_pass = (EditText) findViewById(R.id.edit_pass);
        btn_getcode = (Button) findViewById(R.id.btn_getcode);
        btn_login = (Button) findViewById(R.id.btn_login);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
    }

    private void onClick() {
        checkbox.setOnClickListener(v -> {
            if (checkbox.isChecked()) {
                btn_login.setBackgroundColor(getResources().getColor(R.color.gray_2c2d3e));
                btn_login.setEnabled(true);
            } else {
                btn_login.setEnabled(false);
                btn_login.setBackgroundColor(getResources().getColor(R.color.gray_c0c0c0));
            }
        });
        btn_getcode.setOnClickListener(v -> {
            if (TextUtil.isEmpty(edit_phone.getText().toString())) {
                Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
            } else {
                if (AllUtils.isMobileNO(edit_phone.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                } else {
                    this.startProgressDialog(this);
                    mPresenter.getPhoneCode(edit_phone.getText().toString().trim());
                }
            }

        });
        btn_login.setOnClickListener(v -> {
            if (TextUtil.isEmpty(edit_phone.getText().toString().trim())) {
                Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            } else if (TextUtil.isEmpty(edit_code.getText().toString().trim())) {
                Toast.makeText(RegisterActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            } else if (TextUtil.isEmpty(edit_pass.getText().toString().trim())) {
                Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            } else if (!TextUtil.isEmpty(edit_phone.getText().toString().trim()) && !TextUtil.isEmpty(edit_code.getText().toString().trim()) && !TextUtil.isEmpty(edit_pass.getText().toString().trim())) {
                this.startProgressDialog(this);
                mPresenter.getPhoneSuccess(edit_phone.getText().toString().trim(), edit_code.getText().toString().trim(), edit_pass.getText().toString().trim());
            }
        });
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(Throwable e) {
        this.stopProgressDialog();
        Toast.makeText(this, "网络错误，请稍后再试", Toast.LENGTH_SHORT).show();
        LogUtil.d("e==================>>>" + e);
    }

    @Override
    public void showPhoneCode(LoginBean loginBean) {
        this.stopProgressDialog();
        if (loginBean.getStatus().equals("0")) {
            Toast.makeText(RegisterActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
            CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(btn_getcode, 60000, 1000);
            mCountDownTimerUtils.start();
        } else {
            Toast.makeText(RegisterActivity.this, loginBean.getStatusInfo(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showPhoneSuccess(LoginBean loginBean) {
        this.stopProgressDialog();
        if (loginBean.getStatus().equals("0")) {
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            mPresenter.login(edit_phone.getText().toString().trim(), edit_pass.getText().toString().trim());
        } else {
            Toast.makeText(RegisterActivity.this, loginBean.getStatusInfo(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showContent(LoginBean bean) {
        this.stopProgressDialog();
        if (bean.getStatus().equals("0")) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            SpUtils.putBoolean(this, "firstLogin", true);
            SpUtils.setUser(this, bean.getData());
            SpUtils.putString(this, "phone", edit_phone.getText().toString().trim());
            SpUtils.putString(this, "date", DateUtil.getCompleteTime());
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }else{
            Toast.makeText(RegisterActivity.this, bean.getStatusInfo(), Toast.LENGTH_SHORT).show();
        }
    }
}
