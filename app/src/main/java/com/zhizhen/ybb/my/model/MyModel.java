package com.zhizhen.ybb.my.model;

import com.psylife.wrmvplibrary.data.net.RxService;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.zhizhen.ybb.api.YbbApi;
import com.zhizhen.ybb.bean.BaseClassBean;
import com.zhizhen.ybb.bean.LoginBean;
import com.zhizhen.ybb.bean.PersonInfo;
import com.zhizhen.ybb.my.contract.MyContract;

import rx.Observable;

/**
 * 作者：tc on 2017/5/15.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */
public class MyModel implements MyContract.GetPersonInfoModel {
    @Override
    public Observable<BaseClassBean<PersonInfo>> getPersonInfo(String token) {


        return RxService.createApi(YbbApi.class).getPersonInfo(token).compose(RxUtil.rxSchedulerHelper());
    }
}
