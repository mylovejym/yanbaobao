package com.zhizhen.ybb.loginpass.contract;

import com.psylife.wrmvplibrary.base.WRBaseModel;
import com.psylife.wrmvplibrary.base.WRBaseView;
import com.zhizhen.ybb.base.YbBasePresenter;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.UpdateInfo;

import rx.Observable;

/**
 * Created by tc on 2017/7/13.
 */

public interface UpdateContract {

    //登入
    public abstract class UpdatePresenter extends YbBasePresenter<UpdateContract.UpdateModel, UpdateContract.UpdateView> {
        public abstract void checkNewestVersion(String version, String channel);

    }

    interface UpdateModel extends WRBaseModel {
        Observable<BaseClassBean<UpdateInfo>> checkNewestVersion(String version, String channel);

    }

    interface UpdateView extends WRBaseView {
        void showContent(UpdateInfo updateInfo);
    }

}
