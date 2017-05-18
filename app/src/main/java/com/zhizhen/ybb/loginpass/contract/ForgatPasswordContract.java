package com.zhizhen.ybb.loginpass.contract;

import com.psylife.wrmvplibrary.base.WRBaseModel;
import com.psylife.wrmvplibrary.base.WRBaseView;
import com.zhizhen.ybb.base.YbBasePresenter;
import com.zhizhen.ybb.bean.LoginBean;

import rx.Observable;

/**
 * Created by songxiang on 2017/5/17.
 */

public interface ForgatPasswordContract {
    public abstract class GetPhoneCodePresenter extends YbBasePresenter<GetPhoneCodeModel, GetPhoneCodeView> {
        public abstract void getPhoneCode(String phone);
        public abstract void forgetPass(String phone,String code,String pass);

    }
    interface GetPhoneCodeModel extends WRBaseModel {
        Observable<LoginBean> getPhoneCode(String phone);
        Observable<LoginBean> forgetPass(String phone,String code,String pass);
    }
    interface GetPhoneCodeView extends WRBaseView {
        void showPhoneCode(LoginBean loginBean);
        void showforgetPass(LoginBean loginBean);
    }
}
