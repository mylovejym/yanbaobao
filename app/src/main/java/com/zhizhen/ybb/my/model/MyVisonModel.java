package com.zhizhen.ybb.my.model;

import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.zhizhen.ybb.api.YbbApi;
import com.zhizhen.ybb.bean.BaseBean;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.EyesightInfo;
import com.zhizhen.ybb.my.contract.MyContract;

import rx.Observable;

/**
 * Created by tc on 2017/5/15.
 */

public class MyVisonModel implements MyContract.MyVisonModel {
    @Override
    public Observable<BaseClassBean<EyesightInfo>> getEyesightInfo(String token) {
        return RxService.createApi(YbbApi.class).getEyesightInfo(token).compose(RxUtil.rxSchedulerHelper());
    }

    @Override
    public Observable<BaseBean> addEyesightInfo(String token, String left_eye_degree, String right_eye_degree, String left_eye_astigmatism, String right_eye_astigmatism, String pupillary_distance) {
        return RxService.createApi(YbbApi.class).addEyesightInfo(token, left_eye_degree, right_eye_degree, left_eye_astigmatism, right_eye_astigmatism, pupillary_distance).compose(RxUtil.rxSchedulerHelper());
    }
}
