package com.zhizhen.ybb.loginpass.presenter;

import com.zhizhen.ybb.loginpass.contract.ForgatPasswordContract;

/**
 * Created by songxiang on 2017/5/17.
 */

public class ForgatPasswordPresenter extends ForgatPasswordContract.GetPhoneCodePresenter{
    @Override
    public void getPhoneCode(String phone) {
        mRxManager.add(mModel
                .getPhoneCode(phone)
                .subscribe(
                        data -> {
                            mView.showPhoneCode(data);
                        }, e -> mView.showError(e)
                ));
    }

    @Override
    public void forgetPass(String phone, String code, String pass) {
        mRxManager.add(mModel
                .forgetPass(phone,code,pass)
                .subscribe(
                        data -> {
                            mView.showforgetPass(data);
                        }, e -> mView.showError(e)
                ));
    }

    @Override
    public void onStart() {

    }
}
