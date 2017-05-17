package com.zhizhen.ybb.loginpass.presenter;

import com.zhizhen.ybb.loginpass.contract.RegisterContract;

/**
 * Created by songxiang on 2017/5/16.
 */

public class RegisterPresenter extends RegisterContract.GetPhoneCodePresenter{
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
    public void getPhoneSuccess(String phone, String code, String pass) {
        mRxManager.add(mModel
                .getPhoneSuccess(phone,code,pass)
                .subscribe(
                        data -> {
                            mView.showPhoneSuccess(data);
                        }, e -> mView.showError(e)
                ));
    }

    @Override
    public void onStart() {

    }
}
