package com.zhizhen.ybb.home.model;

import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.zhizhen.ybb.api.YbbApi;
import com.zhizhen.ybb.home.contract.HomeContract;

import rx.Observable;

/**
 * Created by psylife00 on 2017/6/3.
 */

public class HomePageModel implements HomeContract.HomePageModel {
    @Override
    public Observable<String> static_data(String token) {
        return RxService.createApiString(YbbApi.class).static_data(token).compose(RxUtil.rxSchedulerHelper());
    }

    @Override
    public Observable<String> static_lateral(String token) {
        return RxService.createApiString(YbbApi.class).static_lateral(token).compose(RxUtil.rxSchedulerHelper());
    }
}
