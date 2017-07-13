package com.zhizhen.ybb.loginpass.model;

import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.zhizhen.ybb.api.YbbApi;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.UpdateInfo;
import com.zhizhen.ybb.loginpass.contract.UpdateContract;

import rx.Observable;

/**
 * Created by tc on 2017/7/13.
 */

public class UpdateModel implements UpdateContract.UpdateModel {
    @Override
    public Observable<BaseClassBean<UpdateInfo>> checkNewestVersion(String version, String channel) {
        return RxService.createApi(YbbApi.class).checkNewestVersion(version,channel).compose(RxUtil.rxSchedulerHelper());
    }
}
