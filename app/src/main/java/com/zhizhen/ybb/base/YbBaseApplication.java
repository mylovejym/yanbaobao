package com.zhizhen.ybb.base;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.psylife.wrmvplibrary.WRCoreApp;
import com.zhizhen.ybb.util.SpUtils;

/**
 * 作者：tc on 2017/5/11.
 * 邮箱：qw805880101@qq.com
 * 版本：v1.0
 */
public class YbBaseApplication extends WRCoreApp {
    private String token;
    private String phone;
    private String date;
    public static Context context;
    public static YbBaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }
    public static YbBaseApplication getInstance() {
        return instance;
    }
    @Override
    public String setBaseUrl() {
//        return "http://optometry.zonetime.net";

        return "http://ybb.yanzhuanjia.cn";
    }
    public String getToken(){
        if(token == null){
            token = SpUtils.getString(context, "token", null);
        }
        return token;
    }
    public void setToken(String token){
        this.token =token;
        SpUtils.putString(context, "token", token);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
