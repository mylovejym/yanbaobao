package com.zhizhen.ybb.my.model;

import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.zhizhen.ybb.api.YbbApi;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.FollowInfo;
import com.zhizhen.ybb.my.contract.MyContract;

import rx.Observable;

/**
 * Created by tc on 2017/5/24.
 */

public class FollowModelImp implements MyContract.FollowlModel {
    @Override
    public Observable<BaseClassBean<FollowInfo>> focusMe(String token) {
        return RxService.createApi(YbbApi.class).focusMe(token).compose(RxUtil.rxSchedulerHelper());
    }
}
