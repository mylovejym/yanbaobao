package com.zhizhen.ybb.loginpass.presenter;

import com.zhizhen.ybb.loginpass.contract.UpdateContract;

/**
 * Created by tc on 2017/7/13.
 */

public class UpdatePresenter extends UpdateContract.UpdatePresenter {
    @Override
    public void checkNewestVersion(String version, String channel) {
        mRxManager.add(mModel
                .checkNewestVersion(version, channel)
                .subscribe(
                        data -> {
                            mView.showContent(data.getData());

                        }, e -> mView.showError(e)
                ));
    }

    @Override
    public void onStart() {

    }
}
