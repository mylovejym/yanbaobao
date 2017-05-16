package com.zhizhen.ybb.my.presenter;

import com.zhizhen.ybb.my.contract.MyContract;

/**
 * Created by sandlovechao on 2017/5/15.
 */

public class MyVisonPresenter extends MyContract.GetEyesightInfoPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void getEyesightInfo(String token) {
        mRxManager.add(mModel
                .getEyesightInfo(token)
                .subscribe(
                        data -> {
                            mView.showEyesightInfo(data.getData());
                        }, e -> mView.showError(e)
                ));
    }
}
