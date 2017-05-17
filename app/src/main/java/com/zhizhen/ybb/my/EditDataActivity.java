package com.zhizhen.ybb.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;

import butterknife.BindView;

/**
 * 编辑资料
 * Created by sandlovechao on 2017/5/16.
 */

public class EditDataActivity extends YbBaseActivity implements View.OnClickListener{

    @BindView(R.id.lin_head_photo)
    LinearLayout linHeadPhoto;

    @BindView(R.id.lin_name)
    LinearLayout linName;

    @BindView(R.id.lin_choice_sex)
    LinearLayout linChoiceSex;

    @BindView(R.id.lin_set_age)
    LinearLayout linSetAge;

    @BindView(R.id.txt_name)
    TextView txtName;

    @BindView(R.id.image_head_photo)
    ImageView imageHeadPhoto;

    @BindView(R.id.txt_sex)
    TextView txtSex;

    @BindView(R.id.txt_age)
    TextView txtAge;

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText(getString(R.string.edit_data))
                .setLeftImage(R.mipmap.tab_back)
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
                .build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_data;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initdata() {
        Intent intent = this.getIntent();
        intent.getBundleExtra("");
    }

    @Override
    public void onClick(View v) {

    }
}
