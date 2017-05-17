package com.zhizhen.ybb.loginpass.presenter;

import com.zhizhen.ybb.loginpass.contract.LoginContract;

/**
 * Created by psylife00 on 2017/5/11.
 */

public class LoginPresenter extends LoginContract.LoginPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void login(String phone, String pass) {
        mRxManager.add(mModel
                .login(phone,pass)
                .subscribe(
                        data -> {
                            mView.showContent(data);



                        }, e -> mView.showError(e)
                ));
    }

}
