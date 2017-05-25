package com.zhizhen.ybb.home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.psylife.wrmvplibrary.base.WRBaseFragment;
import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.helper.FragmentAdapter;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseApplication;
import com.zhizhen.ybb.base.YbBaseFragmentActivity;
import com.zhizhen.ybb.home.contract.HomeContract;
import com.zhizhen.ybb.home.fragment.HomePageFragment;
import com.zhizhen.ybb.home.fragment.MineFragment;
import com.zhizhen.ybb.home.model.HomeTabModel;
import com.zhizhen.ybb.home.presenter.HomeTabPresenter;
import com.zhizhen.ybb.loginpass.WelcomActivity;
import com.zhizhen.ybb.util.SpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by psylife00 on 2016/12/19.
 */

public class HomeActivity extends YbBaseFragmentActivity<HomeTabPresenter, HomeTabModel> implements HomeContract.HomeTabView, TabHost.OnTabChangeListener, WRBaseFragment.OnBackToFirstListener, ViewPager.OnPageChangeListener {
    //    @BindView(R.id.maincontent)
//    FrameLayout maincontent;


    List<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(android.R.id.tabhost)
    FragmentTabHost tabhost;

    private Class fragmentArray[] = {HomePageFragment.class, MineFragment.class};
    private LayoutInflater layoutInflater;

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }

    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            loadRootFragment(R.id.pager, new HomePageFragment());
        }

        /*实现setOnTabChangedListener接口,目的是为监听界面切换），然后实现TabHost里面图片文字的选中状态切换*/
        /*简单来说,是为了当点击下面菜单时,上面的ViewPager能滑动到对应的Fragment*/
        tabhost.setOnTabChangedListener(this);


    }

    @Override
    public void initdata() {
        initPage();
        YbBaseApplication.instance.setToken(SpUtils.getUser(this).getToken());
        YbBaseApplication.instance.setPhone(SpUtils.getString(this, "phone"));
        YbBaseApplication.instance.setDate(SpUtils.getString(this, "date"));
        //添加报文头
        Map map = new HashMap();
//        map.put("Content-Type", "application/x-www-form-urlencoded");
        map.put("dcreatedate", YbBaseApplication.instance.getDate());
        map.put("spid", YbBaseApplication.instance.getPhone());
        RxService.setHeaders(map);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onTabChanged(String s) {
        int position = tabhost.getCurrentTab();
        pager.setCurrentItem(position);//把选中的Tab的位置赋给适配器，让它控制页面切换
    }

    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    @Override
    public void showTabList(String[] mTabs, int[] imageIds) {
        tabhost.setup(this, getSupportFragmentManager(), R.id.pager);//绑定viewpager
        int count = mTabs.length;

        /*新建Tabspec选项卡并设置Tab菜单栏的内容和绑定对应的Fragment*/
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置标签、图标和文字
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(mTabs[i])
                    .setIndicator(getTabItemView(i, mTabs[i], imageIds[i]));
            // 将Tab按钮添加进Tab选项卡中，并绑定Fragment
            tabhost.addTab(tabSpec, fragmentArray[i], null);
            tabhost.setTag(i);
//            tabhost.getTabWidget().getChildAt(i)
//                    .setBackgroundResource(R.drawable.selector_tab_background);//设置Tab被选中的时候颜色改变
        }

    }

    /*初始化Fragment*/
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void initPage() {
        HomePageFragment fragment1 = new HomePageFragment();
        MineFragment fragment2 = new MineFragment();

        fragments.add(fragment1);
        fragments.add(fragment2);

        //绑定Fragment适配器
        pager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments));
        pager.addOnPageChangeListener(this);
        tabhost.getTabWidget().setDividerDrawable(null);
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showError(Throwable e) {

    }

    private View getTabItemView(int i, String text, int id) {
        layoutInflater = LayoutInflater.from(this);//加载布局管理器
        //将xml布局转换为view对象
        View view = layoutInflater.inflate(R.layout.tabcontent, null);
        //利用view对象，找到布局中的组件,并设置内容，然后返回视图
        ImageView mImageView = (ImageView) view
                .findViewById(R.id.image);
        TextView mTextView = (TextView) view.findViewById(R.id.text);
        mImageView.setBackgroundResource(id);
        mTextView.setText(text);
        return view;
    }

    @Override
    public void onBackToFirstFragment() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TabWidget widget = tabhost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);//设置View覆盖子类控件而直接获得焦点
        tabhost.setCurrentTab(position);//根据位置Postion设置当前的Tab
        widget.setDescendantFocusability(oldFocusability);//设置取消分割线

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpUtils.remove(this, "personInfo");
    }

    public void fin(){
        this.finish();
    }
}
