package com.zhizhen.ybb.loginpass.contract;

import com.psylife.wrmvplibrary.base.WRBaseModel;
import com.psylife.wrmvplibrary.base.WRBaseView;
import com.zhizhen.ybb.base.YbBasePresenter;
import com.zhizhen.ybb.bean.LoginBean;

import rx.Observable;

/**
 * Created by songxiang on 2017/5/16.
 */

public interface RegisterContract {
    public abstract class GetPhoneCodePresenter extends YbBasePresenter<GetPhoneCodeModel, GetPhoneCodeView> {
        public abstract void getPhoneCode(String phone);
        public abstract void getPhoneSuccess(String phone,String code,String pass);
        public abstract void login(String phone,String pass);
    }
    interface GetPhoneCodeModel extends WRBaseModel {
        Observable<LoginBean> getPhoneCode(String phone);
        Observable<LoginBean> getPhoneSuccess(String phone,String code,String pass);
        Observable<LoginBean> login(String phone, String pass);
    }
    interface GetPhoneCodeView extends WRBaseView {
        void showPhoneCode(LoginBean loginBean);
        void showPhoneSuccess(LoginBean loginBean);
        void showContent(LoginBean bean);
    }
}
