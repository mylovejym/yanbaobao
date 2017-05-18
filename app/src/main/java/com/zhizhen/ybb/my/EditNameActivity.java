package com.zhizhen.ybb.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;

import butterknife.BindView;

/**
 * 编辑昵称界面
 * Created by tc on 2017/5/15.
 */

public class EditNameActivity extends YbBaseActivity{

    @BindView(R.id.txt_edit_name)
    TextView txtName;

    @Override
    public int getLayoutId() {
        return R.layout.avtivity_edit_name;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText(getString(R.string.edit_data))
                .setLeftImage(R.mipmap.tab_back)
                .setRightText(getString(R.string.complete))
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
                .setRightOnClickListener(v -> {
                    Intent intent = new Intent(this, EditDataActivity.class);
                    intent.putExtra("name", txtName.getText().toString().trim());
                    this.setResult(EditDataActivity.NAME, intent);
                    this.finish();
                })
                .build();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initdata() {
        Intent bundle = this.getIntent();
        txtName.setText(bundle.getStringExtra("name"));
    }
}
