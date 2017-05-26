package com.zhizhen.ybb.home.model;


import com.zhizhen.ybb.R;
import com.zhizhen.ybb.home.contract.HomeContract;

/**
 * Created by psylife00 on 2016/12/19.
 */

public class HomeTabModel implements HomeContract.HomeTabModel {
    @Override
    public String[] getTabs() {
        String[] mTabs = {"健康报告", "我的"};
        return mTabs;
    }

    //R.drawable.tab_home
    @Override
    public int[] getImageIds() {
        int[] imageIds = {R.drawable.health_selector, R.drawable.mine_selector};
        return imageIds;
    }




}
