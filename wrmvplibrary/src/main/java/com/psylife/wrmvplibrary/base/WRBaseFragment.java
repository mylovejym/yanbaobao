package com.psylife.wrmvplibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.psylife.wrmvplibrary.R;
import com.psylife.wrmvplibrary.utils.LogUtil;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.rawn_hwang.library.widgit.DefaultLoadingLayout;
import me.rawn_hwang.library.widgit.SmartLoadingLayout;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by hpw on 16/10/27.
 */

public abstract class WRBaseFragment<T extends WRBasePresenter, E extends WRBaseModel> extends SupportFragment {
    protected String TAG;
    protected OnBackToFirstListener _mBackToFirstListener;

    public T mPresenter;
    public E mModel;
    protected Context mContext;
    protected Activity mActivity;
    Unbinder binder;

    protected DefaultLoadingLayout mLayout;
    private View titleView;
    public TitleBuilder mTitleBuilder;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
        if (context instanceof OnBackToFirstListener) {
            _mBackToFirstListener = (OnBackToFirstListener) context;
        }
    }

    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(this.rootView!=null){
            return this.rootView;
        }
        View title = getTitleView();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (getLayoutView() != null) {
            if(title !=null) {
                View view = getLayoutView();
                LinearLayout rootlayout = new LinearLayout(mContext);
                rootlayout.removeAllViews();
                rootlayout.setOrientation(LinearLayout.VERTICAL);
                rootlayout.addView(title);
                rootlayout.addView(view,params);
                mLayout = SmartLoadingLayout.createDefaultLayout(mActivity, view);
                this.rootView = rootlayout;
                return rootlayout;
            }
            this.rootView = getLayoutView();
            return this.rootView;
        } else {
            if(title !=null) {
                View view = inflater.inflate(getLayoutId(), null);
                LinearLayout rootlayout = new LinearLayout(mContext);
                rootlayout.removeAllViews();
                rootlayout.setOrientation(LinearLayout.VERTICAL);
                rootlayout.addView(title);
                rootlayout.addView(view,params);
                mLayout = SmartLoadingLayout.createDefaultLayout(mActivity, view);
                this.rootView = rootlayout;
                return rootlayout;
            }
            this.rootView = inflater.inflate(getLayoutId(), null);
            return this.rootView;
        }
    }
    public View getTitleView(){
        if(titleView ==null){
//            titleView = LayoutInflater.from(mActivity).inflate(R.layout.include_titlebar, null);
            titleView = initBackTitle("").getRootView();
        }
        return titleView;
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //设置状态栏透明
//        setStatusBarColor();
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TAG = getClass().getSimpleName();
        binder = ButterKnife.bind(this, view);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        initUI(view, savedInstanceState);
        if (this instanceof WRBaseView) mPresenter.attachVM(this, mModel);
        getBundle(getArguments());
        initData();
        super.onViewCreated(view, savedInstanceState);
    }

    public void onLoading(){
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binder != null) binder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _mBackToFirstListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachVM();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = _mActivity.getFragmentAnimator();
        fragmentAnimator.setEnter(0);
        fragmentAnimator.setExit(0);
        return fragmentAnimator;
    }

    public abstract int getLayoutId();

    public View getLayoutView() {
        return null;
    }

    /**
     * 得到Activity传进来的值
     */
    public void getBundle(Bundle bundle) {

    }

    /**
     * 初始化控件
     */
    public abstract void initUI(View view, @Nullable Bundle savedInstanceState);

    /**
     * 在监听器之前把数据准备好
     */
    public void initData() {

    }

    public void setStatusBarColor() {
        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), null);
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    private TitleBuilder initBackTitle(String title) {
        mTitleBuilder = new TitleBuilder(mActivity)
                .setTitleText(title)
                .setLeftImage(R.mipmap.ic_back)
                .setLeftOnClickListener(v -> {
                    mActivity.finish();
                });
        return mTitleBuilder;
    }

    public TitleBuilder getmTitleBuilder() {
        return mTitleBuilder;
    }

    /**
     * 处理回退事件
     * 如果是孩子fragment需要重写onBackPressedSupport(){_mBackToFirstListener.onBackToFirstFragment();return true;}
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if(_mBackToFirstListener!=null)
            _mBackToFirstListener.onBackToFirstFragment();
            _mActivity.finish();
        }
        return true;
    }

    public interface OnBackToFirstListener {
        void onBackToFirstFragment();
    }

    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg, Toast.LENGTH_SHORT);
    }

    public void showLog(String msg) {
        LogUtil.i(TAG, msg);// TODO: 16/10/12 Log需要自己从新搞一下
    }
}
