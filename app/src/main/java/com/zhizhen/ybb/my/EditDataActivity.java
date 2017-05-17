package com.zhizhen.ybb.my;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.zhizhen.ybb.base.YbBaseApplication;
import com.zhizhen.ybb.bean.BaseBean;
import com.zhizhen.ybb.bean.PersonInfo;
import com.zhizhen.ybb.my.contract.MyContract;
import com.zhizhen.ybb.my.model.EditDataModel;
import com.zhizhen.ybb.my.presenter.EditDataPresenter;
import com.zhizhen.ybb.util.DateUtil;
import com.zhizhen.ybb.util.DialogUtils;
import com.zhizhen.ybb.util.TakePhotosDispose;

import java.io.File;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 编辑资料
 * Created by tc on 2017/5/16.
 */

public class EditDataActivity extends YbBaseActivity<EditDataPresenter, EditDataModel> implements MyContract.EditDataView, View.OnClickListener {

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

    private PersonInfo mPersonInfo;

    private Context context;

    private String path;

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setLeftText(getString(R.string.edit_data))
                .setLeftImage(R.mipmap.tab_back)
                .setTitleBgRes(R.color.blue_313245)
                .setLeftOnClickListener(v -> finish())
                .setRightText(getString(R.string.complete))
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.editPersonalInfo(YbBaseApplication.instance.getToken(),
                                txtName.getText().toString().trim(),
                                txtSex.getText().toString().equals("男") ? "1" : "2",
                                txtAge.getText().toString(), RequestBody.create(MediaType.parse("image/*"), new File(path))
                        );
                    }
                })
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
        mPersonInfo = (PersonInfo) bundle.getSerializable("personInfo");
        txtName.setText(mPersonInfo.getUsername());
        txtSex.setText(mPersonInfo.getSex());
        try {
            txtAge.setText(DateUtil.getAge(mPersonInfo.getBorn()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Glide.with(this).load(mPersonInfo.getPhoto()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageHeadPhoto) {
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
        if (v == linHeadPhoto) {
            DialogUtils.showTakePhotoDialog(this);
        } else if (v == linChoiceSex) {

        } else if (v == linName) {

        } else if (v == linSetAge) {

        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showEditDataInfo(BaseBean baseBean) {

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
                    System.out.println("path = " + path);
                    break;
                default:
                    break;

            }
            Glide.with(this).load(new File(path)).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageHeadPhoto) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imageHeadPhoto.setImageDrawable(circularBitmapDrawable);
                }
            });

        }
    }
}
