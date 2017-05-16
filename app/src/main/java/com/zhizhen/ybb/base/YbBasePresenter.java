package com.zhizhen.ybb.base;

import com.psylife.wrmvplibrary.base.WRBasePresenter;
import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.zhizhen.ybb.api.YbbApi;

import rx.Observable;
import rx.functions.Action1;

/**
 * 作者：tc on 2017/5/15.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */
public abstract class YbBasePresenter<M, T> extends WRBasePresenter<M, T> {

    public void getToken(final Action1<Object> onNext, final Action1<Throwable> onError) {
        mRxManager.add(getToken(4,"ehfjicofijsue284875893jr8hsi82h4").subscribe(onNext,onError
                ));
    }

    public Observable<Object> getToken(int api_id, String api_appkey) {
        return RxService.createApi(YbbApi.class).login(null).compose(RxUtil.rxSchedulerHelper());
    }
}

