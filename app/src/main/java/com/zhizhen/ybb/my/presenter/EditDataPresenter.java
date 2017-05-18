package com.zhizhen.ybb.my.presenter;

import com.zhizhen.ybb.my.contract.MyContract;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by tc on 2017/5/17.
 */

public class EditDataPresenter extends MyContract.EditDataPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void editPersonalInfo(String token, String username, String sex, String born, RequestBody photo) {
        mRxManager.add(mModel
                .editPersonalInfo(token, username, sex, born, photo)
                .subscribe(
                        data -> {
                            mView.showEditDataInfo(data);
                        }, e -> mView.showError(e)
                ));
    }
}
