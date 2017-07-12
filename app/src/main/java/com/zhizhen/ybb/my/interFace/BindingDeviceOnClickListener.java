package com.zhizhen.ybb.my.interFace;

import android.view.View;

/**
 * Created by tc on 2017/5/19.
 */

public interface BindingDeviceOnClickListener {
    /**
     * 设备列表按钮
     *
     * @param view
     * @param pos          设备列表中位置
     * @param bindingState 此设备绑定状态 1:已绑定 2：未绑定
     */
    void onBindingOnClickListener(View view, int pos,boolean bindingState );
}