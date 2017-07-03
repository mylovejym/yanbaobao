package com.zhizhen.ybb.home.presenter;

import com.zhizhen.ybb.home.contract.HomeContract;
import com.zhizhen.ybb.util.DataSex;

/**
 * Created by psylife00 on 2017/6/3.
 */

public class HomePagePresenter extends HomeContract.HomePagePresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void static_data(String token) {
        if(mModel==null)
            return;
        mRxManager.add(mModel
                .static_data(token)
                .subscribe(
                        str -> {
                            mView.showData(DataSex.sex(str));
                        }, e -> mView.showError(e)
                ));

    }

    @Override
    public void static_lateral(String token) {
        if(mModel==null)
            return;
        mRxManager.add(mModel
                .static_lateral(token)
                .subscribe(
                        str -> {
                            mView.showDatalateral(DataSex.sex2(str));
                        }, e -> mView.showError(e)
                ));
    }
}
