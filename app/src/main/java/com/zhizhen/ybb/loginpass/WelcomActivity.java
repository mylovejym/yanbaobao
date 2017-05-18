package com.zhizhen.ybb.loginpass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.zhizhen.ybb.R;
import com.zhizhen.ybb.home.HomeActivity;
import com.zhizhen.ybb.util.SpUtils;

public class WelcomActivity extends Activity {
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		new Handler(new Handler.Callback() {
			// 处理接收到的消息的方法
			@Override
			public boolean handleMessage(Message arg0) {
				boolean isFirstLogin = SpUtils.getBoolean(WelcomActivity.this,"firstLogin");
				if (isFirstLogin) {
                    Intent intent = new Intent();
                    intent.setClass(WelcomActivity.this,
                            HomeActivity.class);
                    startActivity(intent);
                    finish();
				} else {
                    Intent intent = new Intent();
                    intent.setClass(WelcomActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                    finish();
				}
				return false;
			}
		}).sendEmptyMessageDelayed(0, 3000); // 表示延时三秒进行任务的执行
	}

}
