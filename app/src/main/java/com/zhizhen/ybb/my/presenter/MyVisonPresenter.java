package com.zhizhen.ybb.my.presenter;

import com.zhizhen.ybb.my.contract.MyContract;

/**
 * Created by sandlovechao on 2017/5/15.
 */

public class MyVisonPresenter extends MyContract.MyVisonPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void getEyesightInfo(String token) {
        mRxManager.add(mModel
                .getEyesightInfo(token)
                .subscribe(
                        data -> {
                            mView.showEyesightInfo(data);
                        }, e -> mView.showError(e)
                ));
    }

    @Override
    public void addEyesightInfo(String token, String left_eye_degree, String right_eye_degree, String left_eye_astigmatism, String right_eye_astigmatism, String pupillary_distance) {
        mRxManager.add(mModel
                .getEyesightInfo(token)
                .subscribe(
                        data -> {
                            mView.showEyesightInfo(data);
                        }, e -> mView.showError(e)
                ));
    }
}
