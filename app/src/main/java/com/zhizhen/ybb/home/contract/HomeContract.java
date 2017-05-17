package com.zhizhen.ybb.home.contract;

import com.psylife.wrmvplibrary.base.WRBaseModel;
import com.psylife.wrmvplibrary.base.WRBaseView;
import com.zhizhen.ybb.base.YbBasePresenter;

/**
 * Created by psylife00 on 2016/12/19.
 */

public interface HomeContract {



    //主页接口
    abstract class HomeTabPresenter extends YbBasePresenter<HomeTabModel, HomeTabView> {
        public abstract void getTabList();

    }
    interface HomeTabModel extends WRBaseModel {
        String[] getTabs();
        int[] getImageIds();
    }
    interface HomeTabView extends WRBaseView {
        void showTabList(String[] mTabs, int[] imageIds);
    }


}
