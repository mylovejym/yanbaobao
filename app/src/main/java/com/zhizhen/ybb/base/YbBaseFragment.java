package com.zhizhen.ybb.base;

import android.content.Context;

import com.psylife.wrmvplibrary.base.WRBaseLazyFragment;
import com.psylife.wrmvplibrary.base.WRBasePresenter;

import com.psylife.wrmvplibrary.base.WRBaseFragment;

import com.psylife.wrmvplibrary.base.WRBaseLazyFragment;
import com.psylife.wrmvplibrary.base.WRBaseModel;
import com.psylife.wrmvplibrary.base.WRBasePresenter;
import com.psylife.wrmvplibrary.widget.CustomProgressDialog;

/**

 * 作者：tc on 2017/5/16.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */


public abstract class YbBaseFragment<T extends WRBasePresenter, E extends WRBaseModel> extends WRBaseLazyFragment<T,E> {

    private CustomProgressDialog progressDialog = null;

    public void onResume() {
        super.onResume();

    }
    public void onPause() {
        super.onPause();
    }

    /**
     * 开始loading
     *
     * @param context
     */
    public void startProgressDialog(Context context, String hint) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            // progressDialog.setCancelable(false);
            progressDialog.setMessage(hint);
        }

        progressDialog.show();
    }

    public void startProgressDialog(Context context) {
        startProgressDialog(context, "正在加载中...");
    }

    /**
     * 结束loading
     */
    public void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
