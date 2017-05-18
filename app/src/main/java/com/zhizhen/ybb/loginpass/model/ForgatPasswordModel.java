package com.zhizhen.ybb.loginpass.model;

import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.zhizhen.ybb.api.YbbApi;
import com.zhizhen.ybb.bean.LoginBean;
import com.zhizhen.ybb.loginpass.contract.ForgatPasswordContract;

import rx.Observable;

/**
 * Created by songxiang on 2017/5/17.
 */

public class ForgatPasswordModel  implements ForgatPasswordContract.GetPhoneCodeModel{
    @Override
    public Observable<LoginBean> getPhoneCode(String phone) {
        return RxService.createApi(YbbApi.class).getPhoneCode(phone).compose(RxUtil.rxSchedulerHelper());
    }

    @Override
    public Observable<LoginBean> forgetPass(String phone, String code, String pass) {
        return RxService.createApi(YbbApi.class).forgetPass(phone,code,pass).compose(RxUtil.rxSchedulerHelper());
    }
}
