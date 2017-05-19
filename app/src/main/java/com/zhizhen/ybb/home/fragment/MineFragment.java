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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.psylife.wrmvplibrary.utils.LogUtil;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseApplication;
import com.zhizhen.ybb.base.YbBaseFragment;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.PersonInfo;
import com.zhizhen.ybb.my.ChoiceSexActivity;
import com.zhizhen.ybb.my.EditDataActivity;
import com.zhizhen.ybb.my.MyVison;
import com.zhizhen.ybb.my.contract.MyContract;
import com.zhizhen.ybb.my.model.MyModel;
import com.zhizhen.ybb.my.presenter.MyPresenter;
import com.zhizhen.ybb.util.DateUtil;
import com.zhizhen.ybb.util.SpUtils;

import butterknife.BindView;

/**
 * Created by psylife00 on 2017/5/12.
 */

public class MineFragment extends YbBaseFragment<MyPresenter, MyModel> implements MyContract.MyView, View.OnClickListener {
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

    @BindView(R.id.rl_follow)
    RelativeLayout rlFollow;

    @BindView(R.id.lin_edit_data)
    LinearLayout linEditData;

    @BindView(R.id.bt_exit)
    Button btExit;

    private Context context;

    private PersonInfo mPersonInfo;

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        context = this.getContext();
        rlVison.setOnClickListener(this);
        rlFollow.setOnClickListener(this);
        rlDevice.setOnClickListener(this);
        linEditData.setOnClickListener(this);
        btExit.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getPersonInfo(YbBaseApplication.instance.getToken());
    }

    @Override
    public void onClick(View v) {
        if (v == rlVison) {
            Intent intent = new Intent(this.getContext(), MyVison.class);
            this.getContext().startActivity(intent);
        } else if (v == rlFollow) {
            Intent intent = new Intent(this.getContext(), ChoiceSexActivity.class);
            this.getContext().startActivity(intent);
        } else if (v == linEditData) {
            Intent intent = new Intent(this.getContext(), EditDataActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("personInfo", mPersonInfo);
            intent.putExtras(bundle);
            this.getContext().startActivity(intent);
        } else if(v == btExit){
            SpUtils.remove(getActivity(),"firstLogin");
        }
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(this.getContext(), "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
        LogUtil.d("e=>>>" + e);
    }

    @Override
    public void showPersonInfo(BaseClassBean<PersonInfo> mPersonInfo) {
        this.mPersonInfo = mPersonInfo.getData();
        if (mPersonInfo.getStatus().equals("0")) {
            txtName.setText(mPersonInfo.getData().getUsername());
            try {
                txtAge.setText("年龄："+DateUtil.getAge(mPersonInfo.getData().getBorn()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (mPersonInfo.getData().getSex().equals("1")) {
                imageSex.setImageDrawable(this.getResources().getDrawable(R.mipmap.icon_man));
            } else {
                imageSex.setImageDrawable(this.getResources().getDrawable(R.mipmap.icon_girl));
            }
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
        } else {
            Toast.makeText(this.getContext(), mPersonInfo.getStatusInfo(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void initLazyView() {

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible){
            mPresenter.getPersonInfo(YbBaseApplication.instance.getToken());
        }
    }
}
