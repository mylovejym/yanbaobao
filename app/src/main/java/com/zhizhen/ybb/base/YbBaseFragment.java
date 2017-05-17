package com.zhizhen.ybb.base;

import com.psylife.wrmvplibrary.base.WRBaseLazyFragment;
import com.psylife.wrmvplibrary.base.WRBasePresenter;

import com.psylife.wrmvplibrary.base.WRBaseFragment;

import com.psylife.wrmvplibrary.base.WRBaseLazyFragment;
import com.psylife.wrmvplibrary.base.WRBaseModel;
import com.psylife.wrmvplibrary.base.WRBasePresenter;

/**

 * 作者：tc on 2017/5/16.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */


public abstract class YbBaseFragment<T extends WRBasePresenter, E extends WRBaseModel> extends WRBaseLazyFragment<T,E> {
    public void onResume() {
        super.onResume();

    }
    public void onPause() {
        super.onPause();

    }

}
