package com.zhizhen.ybb.base;

import com.psylife.wrmvplibrary.WRCoreApp;

/**
 * 作者：tc on 2017/5/11.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */
public class YbBaseApplication extends WRCoreApp {

    public static String token = "3tylk7dfq5f0w";

    @Override
    public String setBaseUrl() {
        return "http://optometry.zonetime.net";
    }
}
