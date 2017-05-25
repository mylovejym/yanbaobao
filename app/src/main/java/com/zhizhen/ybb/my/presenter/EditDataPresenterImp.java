package com.zhizhen.ybb.my.presenter;

import com.zhizhen.ybb.my.contract.MyContract;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by tc on 2017/5/17.
 */

public class EditDataPresenterImp extends MyContract.EditDataPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void editPersonalInfo(MultipartBody photo) {
        mRxManager.add(mModel
                .editPersonalInfo(photo)
                .subscribe(
                        data -> mView.showEditDataInfo(data), e -> mView.showError(e)
                ));
    }
}
