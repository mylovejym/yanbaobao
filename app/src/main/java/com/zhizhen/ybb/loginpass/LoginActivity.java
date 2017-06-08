package com.zhizhen.ybb.loginpass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.timeutils.TextUtil;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.bean.LoginBean;
import com.zhizhen.ybb.home.HomeActivity;
import com.zhizhen.ybb.loginpass.contract.LoginContract;
import com.zhizhen.ybb.loginpass.model.LoginModel;
import com.zhizhen.ybb.loginpass.presenter.LoginPresenter;
import com.zhizhen.ybb.util.DateUtil;
import com.zhizhen.ybb.util.SpUtils;

/**
 * Created by songxiang on 2017/5/16.
 */

public class LoginActivity extends YbBaseActivity<LoginPresenter, LoginModel> implements LoginContract.LoginView{
    private TextView txt_new_user,txt_forget_pass;
    private Intent intent;
    private Button btn_login;
    private EditText edit_phone,edit_pass;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        init();
        onClick();
    }
    private void init(){
        intent = new Intent();
        edit_pass = (EditText) findViewById(R.id.edit_pass);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        btn_login = (Button) findViewById(R.id.btn_login);
        txt_forget_pass = (TextView) findViewById(R.id.txt_forget_pass);
        txt_new_user = (TextView) findViewById(R.id.txt_new_user);
    }
    private void onClick(){
        btn_login.setOnClickListener(v -> {
                if(TextUtil.isEmpty(edit_phone.getText().toString().trim())){
                    Toast.makeText(LoginActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
                }else if(TextUtil.isEmpty(edit_pass.getText().toString().trim())){
                    Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }else if(!TextUtil.isEmpty(edit_phone.getText().toString().trim()) && !TextUtil.isEmpty(edit_pass.getText().toString().trim())){
                    this.startProgressDialog(LoginActivity.this);
                    mPresenter.login(edit_phone.getText().toString().trim(),edit_pass.getText().toString().trim());
                }
        });
        txt_new_user.setOnClickListener(v -> {
            intent.setClass(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });
        txt_forget_pass.setOnClickListener(v -> {
            intent.setClass(LoginActivity.this,ForgatPasswordActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public void initdata() {

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
    public void showContent(LoginBean bean) {
        this.stopProgressDialog();
        if(bean.getStatus().equals("0")){
            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            SpUtils.putBoolean(this,"firstLogin",true);
            SpUtils.setUser(this,bean.getData());
            SpUtils.putString(this, "phone", edit_phone.getText().toString().trim());
            SpUtils.putString(this, "date", DateUtil.getCompleteTime());
            intent.setClass(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
