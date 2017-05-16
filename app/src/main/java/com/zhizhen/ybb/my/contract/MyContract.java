package com.zhizhen.ybb.my.contract;

import com.psylife.wrmvplibrary.base.WRBaseModel;
import com.psylife.wrmvplibrary.base.WRBaseView;
import com.zhizhen.ybb.base.YbBasePresenter;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.EyesightInfo;
import com.zhizhen.ybb.bean.LoginBean;
import com.zhizhen.ybb.bean.PersonInfo;

import rx.Observable;

/**
 * 作者：tc on 2017/5/15.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */
public interface MyContract {

    //修改个人信息
    public abstract class GetPersonInfoPresenter extends YbBasePresenter<GetPersonInfoModel, GetPersonInfoView> {
        public abstract void getPersonInfo(String token);

    }
    interface GetPersonInfoModel extends WRBaseModel {
        Observable<BaseClassBean<PersonInfo>> getPersonInfo(String token);
    }
    interface GetPersonInfoView extends WRBaseView {
        void showPersonInfo(PersonInfo mPersonInfo);
    }

    abstract class GetEyesightInfoPresenter extends YbBasePresenter<GetEyesightInfoModel, GetEyesightInfoView>{
        public abstract void getEyesightInfo(String token);
    }

    interface GetEyesightInfoModel extends WRBaseModel {
        Observable<BaseClassBean<EyesightInfo>> getEyesightInfo(String token);
    }
    interface GetEyesightInfoView extends WRBaseView {
        void showEyesightInfo(EyesightInfo mPersonInfo);
    }


}
