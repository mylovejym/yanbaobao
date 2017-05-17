package com.zhizhen.ybb.loginpass.contract;

import com.psylife.wrmvplibrary.base.WRBaseModel;
import com.psylife.wrmvplibrary.base.WRBaseView;
import com.zhizhen.ybb.base.YbBasePresenter;
import com.zhizhen.ybb.bean.LoginBean;

import rx.Observable;

/**
 * Created by songxiang on 2017/5/16.
 */

public interface LoginContract {
    //登入
    public abstract class LoginPresenter extends YbBasePresenter<LoginModel, LoginView> {
        public abstract void login(String phone,String pass);

    }
    interface LoginModel extends WRBaseModel {
        Observable<LoginBean> login(String phone, String pass);

    }
    interface LoginView extends WRBaseView {
        void showContent(LoginBean bean);
    }
}
