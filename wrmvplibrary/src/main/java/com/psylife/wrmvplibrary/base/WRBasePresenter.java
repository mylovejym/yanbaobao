package com.psylife.wrmvplibrary.base;


import com.psylife.wrmvplibrary.RxManager;
import com.psylife.wrmvplibrary.ThreadManager;

/**
 * Created by psylife00 on 2016/11/29.
 */

public abstract class WRBasePresenter<M, T> {
    public M mModel;
    public T mView;
    public RxManager mRxManager = new RxManager();
    public ThreadManager mThreadManager = new ThreadManager();

    public void attachVM(T v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void detachVM() {
        mView = null;
        mModel = null;
        mRxManager.clear();
    }

    public abstract void onStart();
}
