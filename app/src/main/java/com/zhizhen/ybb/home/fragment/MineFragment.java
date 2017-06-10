package com.zhizhen.ybb.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseApplication;
import com.zhizhen.ybb.base.YbBaseFragment;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.PersonInfo;
import com.zhizhen.ybb.loginpass.LoginActivity;
import com.zhizhen.ybb.my.EditDataActivity;
import com.zhizhen.ybb.my.FollowActivity;
import com.zhizhen.ybb.my.MyDeivceActivity;
import com.zhizhen.ybb.my.MyVisonActivity;
import com.zhizhen.ybb.my.ParameterSetActivity;
import com.zhizhen.ybb.my.contract.MyContract;
import com.zhizhen.ybb.my.model.MyModelImp;
import com.zhizhen.ybb.my.presenter.MyPresenterImp;
import com.zhizhen.ybb.util.DateUtil;
import com.zhizhen.ybb.util.SpUtils;

import butterknife.BindView;

/**
 * Created by psylife00 on 2017/5/12.
 */

public class MineFragment extends YbBaseFragment<MyPresenterImp, MyModelImp> implements MyContract.MyView, View.OnClickListener {

    public static final int EDIT_DATA = 10023;

    @BindView(R.id.txt_name)
    TextView txtName;

    @BindView(R.id.image_head_photo)
    ImageView imageHeadPhoto;

    @BindView(R.id.image_sex)
    ImageView imageSex;

    @BindView(R.id.txt_age)
    TextView txtAge;

    @BindView(R.id.rl_vison)
    RelativeLayout rlVison;

    @BindView(R.id.rl_device)
    RelativeLayout rlDevice;

    @BindView(R.id.rl_parameter_set)
    RelativeLayout rlParameterSet;

    @BindView(R.id.rl_follow)
    RelativeLayout rlFollow;

    @BindView(R.id.lin_edit_data)
    LinearLayout linEditData;

    @BindView(R.id.bt_exit)
    Button btExit;

    private Context context;

    private PersonInfo mPersonInfo;

    private boolean isLoad = true;// 是否网络加载

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my;
    }

    public void setStatusBarColor() {
        StatusBarUtil.setColor(getActivity(), 0);
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        context = this.getContext();
        rlVison.setOnClickListener(this);
        rlFollow.setOnClickListener(this);
        rlDevice.setOnClickListener(this);
        rlParameterSet.setOnClickListener(this);
        linEditData.setOnClickListener(this);
        btExit.setOnClickListener(this);
    }

    @Override
    public void initData() {
        System.out.println("initData");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != SpUtils.getPersonInfo(this.getActivity())) {
            this.mPersonInfo = SpUtils.getPersonInfo(this.getActivity());
            showView();
        } else {
            loadData();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == linEditData) {
            Intent intent = new Intent(this.getContext(), EditDataActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("personInfo", mPersonInfo);
            intent.putExtras(bundle);
            startActivityForResult(intent, EDIT_DATA);
        } else if (v == rlVison) {
            //我的视力
            Intent intent = new Intent(this.getContext(), MyVisonActivity.class);
            this.getContext().startActivity(intent);
        } else if (v == rlDevice) {
            //我的设备
            Intent intent = new Intent(this.getContext(), MyDeivceActivity.class);
            this.getContext().startActivity(intent);
        } else if (v == rlParameterSet) {
            //参数设置

            Intent intent = new Intent(this.getContext(), ParameterSetActivity.class);
            this.getContext().startActivity(intent);

        } else if (v == rlFollow) {
            //关注
            Intent intent = new Intent(this.getContext(), FollowActivity.class);
            this.getContext().startActivity(intent);
        } else if (v == btExit) {
            //退出
            SpUtils.remove(getActivity(), "firstLogin");
            Intent intent = new Intent(this.getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.getContext().startActivity(intent);
            this.getActivity().finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void showError(Throwable e) {
        this.stopProgressDialog();
        Toast.makeText(this.getContext(), "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
        LogUtil.d("e=>>>" + e);
    }

    @Override
    public void showPersonInfo(BaseClassBean<PersonInfo> mPersonInfos) {
        this.stopProgressDialog();
        this.mPersonInfo = mPersonInfos.getData();
        if (mPersonInfos.getStatus().equals("0")) {
            SpUtils.setPersonInfo(this.getActivity(), mPersonInfo);
            showView();
        } else {
            if (mPersonInfos.getStatus().equals("1009")) {
                Intent intent = new Intent(this.getContext(), LoginActivity.class);
                this.getContext().startActivity(intent);
                this.getActivity().finish();
            }
            Toast.makeText(this.getContext(), mPersonInfos.getStatusInfo(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void initLazyView() {
        System.out.println("initLazyView");
    }

    private void loadData() {
        if (isLoad) {
            this.startProgressDialog(this.getContext());
            mPresenter.getPersonInfo(YbBaseApplication.instance.getToken());
        }

        isLoad = false;
    }

    private void showView() {
        try {
            if (mPersonInfo.getUsername() != null)
                txtName.setText(mPersonInfo.getUsername());

            if (mPersonInfo.getBorn() != null) {
                txtAge.setVisibility(View.VISIBLE);
                txtAge.setText("年龄：" + DateUtil.getAge(mPersonInfo.getBorn()));
            }

            if (mPersonInfo.getSex() != null) {
                imageSex.setVisibility(View.VISIBLE);
                if (mPersonInfo.getSex().equals("1")) {
                    imageSex.setImageDrawable(this.getResources().getDrawable(R.mipmap.icon_man));
                } else {
                    imageSex.setImageDrawable(this.getResources().getDrawable(R.mipmap.icon_girl));
                }
            }

            if (mPersonInfo.getPhoto() != null) {

                RequestManager requestManager = Glide.with(this);
                DrawableTypeRequest drawableTypeRequest = requestManager.load(mPersonInfo.getPhoto());
                drawableTypeRequest.placeholder(R.mipmap.wellcom) //设置占位图
                        .error(R.mipmap.wellcom) //设置错误图片
                        .crossFade(); //设置淡入淡出效果，默认300ms，可以传参
                drawableTypeRequest.asBitmap().centerCrop().into(new BitmapImageViewTarget(imageHeadPhoto) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageHeadPhoto.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == EDIT_DATA) {
            isLoad = true;
            loadData();
        }
    }
}
