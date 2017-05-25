package com.zhizhen.ybb.my;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.base.YbBaseApplication;
import com.zhizhen.ybb.bean.BaseBean;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.EyesightInfo;
import com.zhizhen.ybb.my.contract.MyContract;
import com.zhizhen.ybb.my.model.MyVisonModelImp;
import com.zhizhen.ybb.my.presenter.MyVisonPresenterImp;
import com.zhizhen.ybb.util.DialogUtils;
import com.zhizhen.ybb.util.TakePhotosDispose;

import java.io.File;

import butterknife.BindView;

/**
 * 我的视力
 * Created by tc on 2017/5/15.
 */

public class MyVisonActivity extends YbBaseActivity<MyVisonPresenterImp, MyVisonModelImp> implements MyContract.MyVisonView {

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

    @BindView(R.id.image_bill)
    ImageView imageBill;

    private String leftDegree = "", rightDegree = "", leftAstigmatism = "", rightAstigmatism = "", pupil = "";

    private String path;

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
                .setRightOnClickListener(v -> {
                    this.startProgressDialog(this);
                    leftDegree = edtLeftDegree.getText().toString().trim();
                    rightDegree = edtRightDegree.getText().toString().trim();
                    leftAstigmatism = edtLeftAstigmatism.getText().toString().trim();
                    rightAstigmatism = edtRightAstigmatism.getText().toString().trim();
                    pupil = edtPupil.getText().toString().trim();
                    mPresenter.addEyesightInfo(YbBaseApplication.getInstance().getToken(), leftDegree, rightDegree, leftAstigmatism, rightAstigmatism, pupil);
                })

                .build();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        linTakPhoto.setOnClickListener(v -> {
            DialogUtils.showTakePhotoDialog(this);
        });
    }

    @Override
    public void initdata() {
        this.startProgressDialog(this);
        mPresenter.getEyesightInfo(YbBaseApplication.getInstance().getToken());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(Throwable e) {
        this.stopProgressDialog();
        Toast.makeText(this, "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
        LogUtil.d("e=>>>" + e);
    }

    @Override
    public void showEyesightInfo(BaseClassBean<EyesightInfo> mEyesightInfo) {
        this.stopProgressDialog();
        if (mEyesightInfo.getStatus().equals("0")) {
            edtLeftDegree.setText(mEyesightInfo.getData().getLeft_eye_degree());
            edtRightDegree.setText(mEyesightInfo.getData().getRight_eye_degree());
            edtLeftAstigmatism.setText(mEyesightInfo.getData().getLeft_eye_astigmatism());
            edtRightAstigmatism.setText(mEyesightInfo.getData().getRight_eye_astigmatism());
            edtPupil.setText(mEyesightInfo.getData().getPupillary_distance());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TakePhotosDispose.CROPIMAGE:
                case TakePhotosDispose.TAKEPHOTO:
                    path = DialogUtils.currentFileName.getAbsolutePath();
                    break;
                case TakePhotosDispose.PICKPHOTO:
                    // 相册选图
                    Uri selectedImage = data.getData();
                    if (!selectedImage.toString().substring(0, 7).equals("content")) {
                        // 如果路径错误
                        String picturePath = selectedImage.getPath();
                        path = picturePath;
                    } else {
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        path = picturePath;
                    }
                    break;
                default:
                    break;
            }
            System.out.println("path = " + path);
            imageBill.setVisibility(View.VISIBLE);
            linTakPhoto.setVisibility(View.GONE);
            Glide.with(this).load(new File(path)).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageBill));
        }
    }
}
