package com.zhizhen.ybb.loginpass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.home.HomeActivity;
import com.zhizhen.ybb.util.SpUtils;

public class WelcomeActivity extends YbBaseActivity {
	@Override
	public int getLayoutId() {
		return R.layout.welcom;
	}

	public void setStatusBarColor() {
		StatusBarUtil.setColor(this, this.getResources().getColor(R.color.background_color));
	}

    @Override
    public View getTitleView() {
        return null;
    }

	@Override
	public void initView(Bundle savedInstanceState) {
		// 处理接收到的消息的方法
		new Handler(arg0 -> {
            boolean isFirstLogin = SpUtils.getBoolean(WelcomeActivity.this,"firstLogin");
            if (isFirstLogin) {
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this,
                        HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }
            return false;
        }).sendEmptyMessageDelayed(0, 3000); // 表示延时三秒进行任务的执行
	}

	@Override
	public void initdata() {

	}

}
