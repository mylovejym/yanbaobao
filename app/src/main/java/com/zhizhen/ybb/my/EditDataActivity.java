package com.zhizhen.ybb.my;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.bean.PersonBean;
import com.zhizhen.ybb.util.DateUtil;

import org.w3c.dom.Text;

import butterknife.BindView;

/**
 * 编辑资料
 * Created by sandlovechao on 2017/5/16.
 */

public class EditDataActivity extends YbBaseActivity implements View.OnClickListener {

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

    private PersonBean mPersonInfo;

    private Context context;

    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancel;
    private Dialog dialog;

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
        context = this;
        linHeadPhoto.setOnClickListener(this);
        linName.setOnClickListener(this);
        linChoiceSex.setOnClickListener(this);
        linSetAge.setOnClickListener(this);

    }

    @Override
    public void initdata() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        mPersonInfo = (PersonBean) bundle.getSerializable("personInfo");
        txtName.setText(mPersonInfo.getData().getUsername());
        txtSex.setText(mPersonInfo.getData().getSex());
        try {
            txtAge.setText(DateUtil.getAge(mPersonInfo.getData().getBorn()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Glide.with(this).load(mPersonInfo.getData().getPhoto()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageHeadPhoto) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageHeadPhoto.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == linHeadPhoto){
            show();
        } if (v == choosePhoto){
            Toast.makeText(this,"点击了从相册选择",Toast.LENGTH_SHORT).show();
        } if (v == takePhoto){
            Toast.makeText(this,"点击了拍照", Toast.LENGTH_SHORT).show();
        } if (v == cancel){
            dialog.dismiss();
        } if (v == choosePhoto){

        } if (v == choosePhoto){

        } if (v == choosePhoto){

        } if (v == choosePhoto){

        }
    }

    public void show() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.view_bottom, null);
        //初始化控件
        choosePhoto = (TextView) inflate.findViewById(R.id.txt_choosePhoto);
        takePhoto = (TextView) inflate.findViewById(R.id.txt_takePhoto);
        cancel = (TextView) inflate.findViewById(R.id.txt_cancel);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        cancel.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }
}
