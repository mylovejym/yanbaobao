package com.psylife.wrmvplibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.psylife.wrmvplibrary.AppManager;
import com.psylife.wrmvplibrary.R;
import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.SpUtil;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TUtil;
import com.psylife.wrmvplibrary.utils.ThemeUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.psylife.wrmvplibrary.widget.CustomProgressDialog;
import com.psylife.wrmvplibrary.widget.SwipeBackLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.rawn_hwang.library.widgit.DefaultLoadingLayout;
import me.rawn_hwang.library.widgit.SmartLoadingLayout;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by psylife00 on 2016/11/29.
 */

public abstract class WRBaseActivity <T extends WRBasePresenter, E extends WRBaseModel> extends SupportActivity {

    protected String TAG;

    public T mPresenter;
    public E mModel;
    protected Context mContext;
    Unbinder binder;

    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;
    private boolean isOpen = true;
    private boolean hasData = false;

    protected DefaultLoadingLayout mLayout;

    private View titleView;
    public TitleBuilder mTitleBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏透明
        setStatusBarColor();
       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();

        setTheme(ThemeUtil.themeArr[SpUtil.getThemeIndex(this)][
                SpUtil.getNightModel(this) ? 1 : 0]);
        this.setContentView(this.getLayoutId());
        binder = ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        this.initView(savedInstanceState);
        if (this instanceof WRBaseView) mPresenter.attachVM(this, mModel);
        initdata();

        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (binder != null) binder.unbind();
        if (mPresenter != null) mPresenter.detachVM();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void setContentView(int layoutResID) {
        View title = getTitleView();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (isOpen()) {
            if(title !=null){
                LinearLayout rootlayout = new LinearLayout(this);
                rootlayout.setOrientation(LinearLayout.VERTICAL);
                rootlayout.addView(title);
                View view = LayoutInflater.from(this).inflate(layoutResID, null);
                rootlayout.addView(view,params);
                mLayout = SmartLoadingLayout.createDefaultLayout(this, view);
                super.setContentView(rootlayout);
            }else {
                LinearLayout rootlayout = new LinearLayout(this);
                rootlayout.setOrientation(LinearLayout.VERTICAL);
                View view = LayoutInflater.from(this).inflate(layoutResID, null);
                rootlayout.addView(view,params);
                mLayout = SmartLoadingLayout.createDefaultLayout(this, view);
                super.setContentView(rootlayout);
            }
        } else {
            super.setContentView(getContainer());
            View view = LayoutInflater.from(this).inflate(layoutResID, null);
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            if(title!=null){
                LinearLayout rootlayout = new LinearLayout(this);
                rootlayout.setOrientation(LinearLayout.VERTICAL);
                rootlayout.addView(title);
                rootlayout.addView(view,params);
                mLayout = SmartLoadingLayout.createDefaultLayout(this, view);
                swipeBackLayout.addView(rootlayout);
            }else{
                mLayout = SmartLoadingLayout.createDefaultLayout(this, view);
                swipeBackLayout.addView(view);
            }

        }
    }

    public void onFirstLoading(){
        mLayout.onLoading();
    }
    public void onLoading(){
        mLayout.setLayoutBackground(R.color.no_color);
        mLayout.onLoading();
    }
    public void onDone(){
        mLayout.onDone();
    }
    public void onEmpty(){
        mLayout.onEmpty();
    }
    public void onError(){
        mLayout.onError();
    }


    public View getTitleView(){
        if(titleView ==null){
//            titleView = LayoutInflater.from(this).inflate(R.layout.include_titlebar, null);
            titleView = initBackTitle("").getRootView();
        }
        return titleView;
    };



    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.theme_black_7f));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        swipeBackLayout.setOnSwipeBackListener((fa, fs) -> ivShadow.setAlpha(1 - fs));
        return container;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public abstract int getLayoutId();

    public abstract void initView(Bundle savedInstanceState);
    public abstract void initdata();

    @Override
    public void onBackPressedSupport() {
        supportFinishAfterTransition();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
        // 设置无动画
//        return new DefaultNoAnimator();
        // 设置自定义动画
        // return new FragmentAnimator(enter,exit,popEnter,popExit);
        // 默认竖向(和安卓5.0以上的动画相同)
//        return super.onCreateFragmentAnimator();
    }

    public void setStatusBarColor() {
        StatusBarUtil.setTransparent(this);
//        StatusBarUtil.setTranslucent(this);
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    private TitleBuilder initBackTitle(String title) {
        mTitleBuilder = new TitleBuilder(this)
                .setTitleText(title)
                .setLeftImage(R.drawable.icon_back)
                .setLeftOnClickListener(v -> {
                    finish();
                });
        return mTitleBuilder;
    }

    public TitleBuilder getmTitleBuilder() {
        return mTitleBuilder;
    }

    /**
     * 跳转页面,无extra简易型
     *
     * @param tarActivity 目标页面
     */
    public void startActivity(Class<? extends Activity> tarActivity, Bundle options) {
        Intent intent = new Intent(this, tarActivity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options);
        } else {
            startActivity(intent);
        }
    }

    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    public void showToast(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    public void showLog(String msg) {
        LogUtil.i(TAG, msg);// TODO: 16/10/12 Log需要自己从新搞一下
    }

    private CustomProgressDialog progressDialog = null;

    /**
     * 开始loading
     *
     * @param context
     */
    public void startProgressDialog(Context context, String hint) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            // progressDialog.setCancelable(false);
            progressDialog.setMessage(hint);
        }

        progressDialog.show();
    }

    public void startProgressDialog(Context context) {
        startProgressDialog(context, "正在加载中...");
    }

    /**
     * 结束loading
     */
    public void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
