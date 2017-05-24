package com.zhizhen.ybb.my.presenter;

import com.zhizhen.ybb.my.contract.MyContract;

/**
 * 作者：tc on 2017/5/15.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */
public class FollowPresenterImp extends MyContract.FollowPresenter {

    @Override
    public void onStart() {

    }

    @Override
    public void focusMe(String token) {
        mRxManager.add(mModel
                .focusMe(token)
                .subscribe(
                        data -> {
                            mView.showUrl(data);
                        }, e -> mView.showError(e)
                ));
    }
}
