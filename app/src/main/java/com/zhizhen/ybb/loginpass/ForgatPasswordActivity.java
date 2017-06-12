package com.zhizhen.ybb.loginpass;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.timeutils.TextUtil;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.bean.LoginBean;
import com.zhizhen.ybb.loginpass.contract.ForgatPasswordContract;
import com.zhizhen.ybb.loginpass.model.ForgatPasswordModel;
import com.zhizhen.ybb.loginpass.presenter.ForgatPasswordPresenter;
import com.zhizhen.ybb.util.AllUtils;
import com.zhizhen.ybb.view.CountDownTimerUtils;

public class ForgatPasswordActivity extends YbBaseActivity<ForgatPasswordPresenter, ForgatPasswordModel> implements ForgatPasswordContract.GetPhoneCodeView{
	private EditText edit_phone,edit_pass,edit_code;
	private Button btn_login;
	private Button btn_getcode;
	@Override
	public int getLayoutId() {
		return R.layout.activity_forgat_password;
	}

	public void setStatusBarColor() {
		StatusBarUtil.setColor(this, this.getResources().getColor(R.color.background_color));
	}

	@Override
	public View getTitleView() {
		return new TitleBuilder(this)
				.setTitleText("找回密码")
				.setTitleTextColor(this, R.color.white)
				.setLeftImage(R.mipmap.tab_back)
				.setTitleBgRes(R.color.background_color)
				.setLeftOnClickListener(v -> finish())
				.build();
	}

	@Override
	public void initView(Bundle savedInstanceState) {
		init();
		onClick();
	}

	@Override
	public void initdata() {

	}

	private void init(){
		edit_code = (EditText) findViewById(R.id.edit_code);
		edit_pass = (EditText) findViewById(R.id.edit_pass);
		btn_getcode = (Button) findViewById(R.id.btn_getcode);
		btn_login = (Button) findViewById(R.id.btn_login);
		edit_phone = (EditText) findViewById(R.id.edit_phone);
	}
	private void onClick(){
		btn_getcode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(TextUtil.isEmpty(edit_phone.getText().toString())){
					Toast.makeText(ForgatPasswordActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
				}else{
					if(AllUtils.isMobileNO(edit_phone.getText().toString().trim())){
						Toast.makeText(ForgatPasswordActivity.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
					}else{
						mPresenter.getPhoneCode(edit_phone.getText().toString().trim());
					}
				}

			}
		});
		btn_login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(TextUtil.isEmpty(edit_phone.getText().toString().trim())){
					Toast.makeText(ForgatPasswordActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
				}else if(TextUtil.isEmpty(edit_code.getText().toString().trim())){
					Toast.makeText(ForgatPasswordActivity.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
				}else if(TextUtil.isEmpty(edit_pass.getText().toString().trim())){
					Toast.makeText(ForgatPasswordActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
				}else if(!TextUtil.isEmpty(edit_phone.getText().toString().trim()) && !TextUtil.isEmpty(edit_code.getText().toString().trim()) && !TextUtil.isEmpty(edit_pass.getText().toString().trim())){
					mPresenter.forgetPass(edit_phone.getText().toString().trim(),edit_code.getText().toString().trim(),edit_pass.getText().toString().trim());
				}
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
		if(loginBean.getStatus().equals("0")){
			Toast.makeText(ForgatPasswordActivity.this,"获取验证码成功",Toast.LENGTH_SHORT).show();
			CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(btn_getcode, 60000, 1000);
			mCountDownTimerUtils.start();
		}else{
			Toast.makeText(ForgatPasswordActivity.this, loginBean.getStatusInfo(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void showforgetPass(LoginBean loginBean) {
		this.stopProgressDialog();
		if(loginBean.getStatus().equals("0")){
			Toast.makeText(ForgatPasswordActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
			finish();
		}else{
			Toast.makeText(ForgatPasswordActivity.this, loginBean.getStatusInfo(), Toast.LENGTH_SHORT).show();
		}
	}
}
