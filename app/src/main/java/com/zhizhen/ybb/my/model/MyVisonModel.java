package com.zhizhen.ybb.my.model;

import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.zhizhen.ybb.api.YbbApi;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.EyesightInfo;
import com.zhizhen.ybb.my.contract.MyContract;

import rx.Observable;

/**
 * Created by sandlovechao on 2017/5/15.
 */

public class MyVisonModel implements MyContract.GetEyesightInfoModel {
    @Override
    public Observable<BaseClassBean<EyesightInfo>> getEyesightInfo(String token) {
        return RxService.createApi(YbbApi.class).getEyesightInfo(token).compose(RxUtil.rxSchedulerHelper());
    }
}
