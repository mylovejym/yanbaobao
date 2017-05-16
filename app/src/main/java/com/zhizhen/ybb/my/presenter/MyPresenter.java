package com.zhizhen.ybb.my.presenter;

import com.zhizhen.ybb.my.contract.MyContract;

/**
 * 作者：tc on 2017/5/15.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */
public class MyPresenter extends MyContract.GetPersonInfoPresenter {
    @Override
    public void getPersonInfo(String token) {
        mRxManager.add(mModel
                .getPersonInfo(token)
                .subscribe(
                        data -> {
                            mView.showPersonInfo(data.getData());
                        }, e -> mView.showError(e)
                ));
    }

    @Override
    public void onStart() {

    }
}
