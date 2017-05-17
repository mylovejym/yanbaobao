package com.zhizhen.ybb.loginpass.model;

import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.zhizhen.ybb.api.YbbApi;
import com.zhizhen.ybb.bean.LoginBean;
import com.zhizhen.ybb.loginpass.contract.RegisterContract;

import rx.Observable;

/**
 * Created by songxiang on 2017/5/16.
 */

public class RegisterModel implements RegisterContract.GetPhoneCodeModel{
    @Override
    public Observable<LoginBean> getPhoneCode(String phone) {
        return RxService.createApi(YbbApi.class).getPhoneCode(phone).compose(RxUtil.rxSchedulerHelper());
    }

    @Override
    public Observable<LoginBean> getPhoneSuccess(String phone, String code, String pass) {
        return RxService.createApi(YbbApi.class).getPhoneSuccess(phone,code,pass).compose(RxUtil.rxSchedulerHelper());
    }
}
