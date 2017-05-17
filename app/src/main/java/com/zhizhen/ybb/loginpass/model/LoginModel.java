package com.zhizhen.ybb.loginpass.model;

import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.zhizhen.ybb.api.YbbApi;
import com.zhizhen.ybb.bean.LoginBean;
import com.zhizhen.ybb.loginpass.contract.LoginContract;

import rx.Observable;

/**
 * Created by songxiang on 2017/5/16.
 */

public class LoginModel  implements LoginContract.LoginModel{

    @Override
    public Observable<LoginBean> login(String phone, String pass) {
        return RxService.createApi(YbbApi.class).login(phone,pass).compose(RxUtil.rxSchedulerHelper());
    }
}
