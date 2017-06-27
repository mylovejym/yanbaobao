package com.zhizhen.ybb.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.psylife.wrmvplibrary.base.WRBaseFragment;
import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.zhizhen.ybb.R;
import com.zhizhen.ybb.base.YbBaseApplication;
import com.zhizhen.ybb.base.YbBaseFragmentActivity;
import com.zhizhen.ybb.home.contract.HomeContract;
import com.zhizhen.ybb.home.fragment.HomePageFragment;
import com.zhizhen.ybb.home.fragment.MineFragment;
import com.zhizhen.ybb.home.model.HomeTabModel;
import com.zhizhen.ybb.home.presenter.HomeTabPresenter;
import com.zhizhen.ybb.lanya.MsgAidlService;
import com.zhizhen.ybb.lanya.UartService;
import com.zhizhen.ybb.util.SpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by psylife00 on 2017/6/26.
 */

public class HomeActivity2 extends YbBaseFragmentActivity<HomeTabPresenter, HomeTabModel> implements HomeContract.HomeTabView, TabHost.OnTabChangeListener, WRBaseFragment.OnBackToFirstListener {
    @BindView(R.id.realtabcontent)
    FrameLayout realtabcontent;
    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(android.R.id.tabhost)
    FragmentTabHost mTabHost;

    String[] mTabs = {"健康报告", "我的"};
    int[] imageIds = {R.drawable.health_selector, R.drawable.mine_selector};

    private Class fragmentArray[] = {HomePageFragment.class, MineFragment.class};

    //定义一个布局
    private LayoutInflater layoutInflater;
    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.blue_313245));
    }
    @Override
    public View getTitleView() {
        return null;
    }

    @Override
    public void onTabChanged(String tabId) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showTabList(String[] mTabs, int[] imageIds) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.main_tab_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
//实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        //实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        //得到fragment的个数
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTabs[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
//            //设置Tab按钮的背景
//            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }
        startService(new Intent(this, UartService.class));
        startService(new Intent(this, MsgAidlService.class));
    }

    @Override
    public void initdata() {
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

    @Override
    public void onBackToFirstFragment() {

    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index){
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(imageIds[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTabs[index]);

        return view;
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
