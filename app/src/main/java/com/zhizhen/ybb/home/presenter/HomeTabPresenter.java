package com.zhizhen.ybb.home.presenter;

import com.zhizhen.ybb.home.contract.HomeContract;

/**
 * Created by psylife00 on 2016/12/19.
 */

public class HomeTabPresenter extends HomeContract.HomeTabPresenter {
    @Override
    public void getTabList() {
        mView.showTabList(mModel.getTabs(),mModel.getImageIds());
    }



    @Override
    public void onStart() {
        getTabList();
    }
}
