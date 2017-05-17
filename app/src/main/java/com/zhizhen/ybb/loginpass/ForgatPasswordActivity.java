package com.zhizhen.ybb.loginpass;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.my.model.MyModel;
import com.zhizhen.ybb.my.presenter.MyPresenter;

public class ForgatPasswordActivity extends YbBaseActivity<MyPresenter, MyModel> {
	private ImageView img_back;
	private TextView txt_back;
	@Override
	public int getLayoutId() {
		return R.layout.activity_forgat_password;
	}

	@Override
	public void initView(Bundle savedInstanceState) {
		init();
		onClick();
	}

	@Override
	public void initdata() {

	}

	@Override
	public View getTitleView() {
		return null;
	}

	private void init(){
		img_back = (ImageView) findViewById(R.id.img_back);
		txt_back = (TextView) findViewById(R.id.txt_back);
	}
	private void onClick(){
		img_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		txt_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
