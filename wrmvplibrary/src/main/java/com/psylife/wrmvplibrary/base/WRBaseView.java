package com.psylife.wrmvplibrary.base;

import android.content.Context;

/**
 * Created by psylife00 on 2016/11/29.
 */

public interface WRBaseView {
    Context getContext();

    void showError(Throwable e);
}
