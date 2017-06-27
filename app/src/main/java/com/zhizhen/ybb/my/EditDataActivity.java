package com.zhizhen.ybb.my;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseActivity;
import com.zhizhen.ybb.base.YbBaseApplication;
import com.zhizhen.ybb.bean.BaseBean;
import com.zhizhen.ybb.bean.PersonInfo;
import com.zhizhen.ybb.home.HomeActivity2;
import com.zhizhen.ybb.home.fragment.MineFragment;
import com.zhizhen.ybb.my.contract.MyContract;
import com.zhizhen.ybb.my.model.EditDataModelImp;
import com.zhizhen.ybb.my.presenter.EditDataPresenterImp;
import com.zhizhen.ybb.util.DialogUtils;
import com.zhizhen.ybb.util.GlideCircleTransform;
import com.zhizhen.ybb.util.TakePhotosDispose;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 编辑资料
 * Created by tc on 2017/5/16.
 */
@RuntimePermissions
public class EditDataActivity extends YbBaseActivity<EditDataPresenterImp, EditDataModelImp> implements MyContract.EditDataView, View.OnClickListener {

    public static final int NAME = 10086;
    public static final int SEX = 10010;

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

    private String both;

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
                .setRightOnClickListener(v -> {

                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    builder.setType(MultipartBody.FORM);
                    builder.addFormDataPart("token", YbBaseApplication.instance.getToken());
                    if (txtName.getText().toString() != null)
                        builder.addFormDataPart("username", txtName.getText().toString().trim());
                    if (txtSex.getText().toString() != null)
                        builder.addFormDataPart("sex", txtSex.getText().toString().equals("男") ? "1" : "2");
                    if (both != null)
                        builder.addFormDataPart("born", both);
                    if (path != null) {
                        System.out.println("传文件");
                        File file = new File(path);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), file);
                        builder.addFormDataPart("photo", file.getName(), requestBody);
                    }
                    this.startProgressDialog(this);
                    mPresenter.editPersonalInfo(builder.build());
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

//        Map map = new HashMap();
//        map.put("Content-Type", "multipart/form-data");
//        map.put("dcreatedate", YbBaseApplication.instance.getDate());
//        map.put("spid", YbBaseApplication.instance.getPhone());
//        RxService.setHeaders(map);
    }

    @Override
    public void initdata() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        mPersonInfo = (PersonInfo) bundle.getSerializable("personInfo");
        if (mPersonInfo.getUsername() != null)
            txtName.setText(mPersonInfo.getUsername());

        if (mPersonInfo.getSex() != null)
            txtSex.setText(mPersonInfo.getSex().equals("1") ? "男" : "女");

        if (mPersonInfo.getBorn() != null) {
            try {
                both = mPersonInfo.getBorn();
                txtAge.setText(both);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            both = txtAge.getText().toString().trim();
        }

        if (mPersonInfo.getPhoto() != null)
            Glide.with(this).load(mPersonInfo.getPhoto())
                    .placeholder(R.mipmap.wellcom) //设置占位图
                    .error(R.mipmap.wellcom) //设置错误图片
                    .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                    .transform(new GlideCircleTransform(context)).into(imageHeadPhoto);
    }

    @Override
    public void onClick(View v) {
        if (v == linHeadPhoto) {
            EditDataActivityPermissionsDispatcher.showCameraWithCheck(this);
        } else if (v == linChoiceSex) {
            Intent intent = new Intent(this, ChoiceSexActivity.class);
            intent.putExtra("sex", mPersonInfo.getSex());
            startActivityForResult(intent, SEX);
        } else if (v == linName) {
            Intent intent = new Intent(this, EditNameActivity.class);
            intent.putExtra("name", mPersonInfo.getUsername());
            startActivityForResult(intent, NAME);
        } else if (v == linSetAge) {
            //时间选择器
            showTime();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
        this.stopProgressDialog();
    }

    @Override
    public void showEditDataInfo(BaseBean baseBean) {
        this.stopProgressDialog();
        if (baseBean.getStatus().equals("0")) {
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity2.class);
            this.setResult(MineFragment.EDIT_DATA, intent);
            this.finish();
        } else
            Toast.makeText(this, baseBean.getStatusInfo(), Toast.LENGTH_SHORT).show();
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

            Glide.with(this).load(new File(path))
                    .placeholder(R.mipmap.wellcom) //设置占位图
                    .error(R.mipmap.wellcom) //设置错误图片
                    .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                    .transform(new GlideCircleTransform(context)).into(imageHeadPhoto);

        } else if (resultCode == SEX) {
            txtSex.setText(data.getStringExtra("sex").equals("1") ? "男" : "女");
            mPersonInfo.setSex(data.getStringExtra("sex"));
        } else if (resultCode == NAME) {
            txtName.setText(data.getStringExtra("name"));
            mPersonInfo.setSex(data.getStringExtra("name"));
        }
    }

    private void showTime() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            if (mPersonInfo.getBorn() != null) {
                // 指定一个日期
                Date deTime = dateFormat.parse(both);
                calendar.setTime(deTime);
            }

            //时间选择器
            TimePickerView pvTime = new TimePickerView.Builder(this, (date, v1) -> {
                both = dateFormat.format(date);
                try {
                    txtAge.setText(both);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            })
                    .setType(new boolean[]{true, true, true, false, false, false})
                    .setTextColorCenter(this.getResources().getColor(R.color.blue_b3007aff))
                    .setTitleSize(R.dimen.txt_size)
                    .setLineSpacingMultiplier(2.0f)
                    .isCenterLabel(true)
                    .build();
            pvTime.setDate(calendar);//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
            pvTime.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注释请求的权限后面跟着权限获取后执行的方法
     */
    @NeedsPermission({Manifest.permission.CAMERA,  Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showCamera() {
        DialogUtils.showTakePhotoDialog(this);
    }

    /**
     * 一般用于展示用户点击取消后向用户说明原因
     * 在positiveButton上一般帮一个监听器执行request.proceed()方法，在取消按钮上帮一个监听器执行request.cancel()方法
     *
     * @param request
     */
    @OnShowRationale({Manifest.permission.CAMERA,  Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("设置头像需获取相关权限")
                .setPositiveButton("确定", (dialog, button) -> request.proceed())
                .setNegativeButton("取消", (dialog, button) -> request.cancel())
                .show();
    }

    /**
     * 用户拒绝后执行的方法
     */
    @OnPermissionDenied({Manifest.permission.CAMERA,  Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeniedForCamera() {
        Toast.makeText(this, "拒绝设置相关权限", Toast.LENGTH_SHORT).show();
    }

    /**
     * 用户点击不再询问后执行的方法
     */
    @OnNeverAskAgain({Manifest.permission.CAMERA,  Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForCamera() {
        Toast.makeText(this, "要使用此功能需设置相关权限", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EditDataActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


}
