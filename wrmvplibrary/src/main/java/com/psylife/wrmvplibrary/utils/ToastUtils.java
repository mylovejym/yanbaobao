package com.psylife.wrmvplibrary.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by psylife00 on 2016/11/29.
 */

public class ToastUtils {


    public static void showToast(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public static void showToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }
}
