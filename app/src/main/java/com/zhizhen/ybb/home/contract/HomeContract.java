package com.zhizhen.ybb.home.contract;

import com.psylife.wrmvplibrary.base.WRBaseModel;
import com.psylife.wrmvplibrary.base.WRBaseView;
import com.zhizhen.ybb.base.YbBasePresenter;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.GetStatistics;

import rx.Observable;

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

    abstract class HomePagePresenter extends YbBasePresenter<HomePageModel, HomePageView> {
        public abstract void static_data(String token);
    }

    interface HomePageModel extends WRBaseModel {
        Observable<String> static_data(String token);
    }

    interface HomePageView extends WRBaseView {
        void showData(BaseClassBean<GetStatistics> mPersonBean);
    }


}
