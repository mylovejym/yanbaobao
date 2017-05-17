package com.zhizhen.ybb.base;

import android.support.v4.content.ContextCompat;

import com.psylife.wrmvplibrary.base.WRBaseActivity;
import com.psylife.wrmvplibrary.base.WRBaseModel;
import com.psylife.wrmvplibrary.base.WRBasePresenter;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.zhizhen.ybb.R;

/**
 * Created by psylife00 on 2017/1/4.
 */

public abstract class YbBaseFragmentActivity<T extends WRBasePresenter, E extends WRBaseModel>extends WRBaseActivity<T, E> {

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
//        StatusBarUtil.setTransparent(this);
//        StatusBarUtil.setTranslucent(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
