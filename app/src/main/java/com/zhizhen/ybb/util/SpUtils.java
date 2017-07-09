package com.zhizhen.ybb.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zhizhen.ybb.bean.MyBLEDevice;
import com.zhizhen.ybb.bean.MyBLEDeviceMap;
import com.zhizhen.ybb.bean.PersonInfo;
import com.zhizhen.ybb.bean.UserBean;

/**
 * SharedPreference操作类
 * Created by devilwwj on 16/1/23.
 */
public class SpUtils {
    public static final String spFileName = "ShiGuangApp";
    private static SharedPreferences mSharedPreferences;

    public synchronized static void setUser(Context ctx, UserBean user) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(spFileName,
                    Context.MODE_PRIVATE);
        }
        Gson gson = new Gson();
        String json = gson.toJson(user);
        mSharedPreferences.edit().putString("user", json).commit();
    }

    public static UserBean getUser(Context ctx) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(spFileName,
                    Context.MODE_PRIVATE);
        }
        UserBean user = null;
        String json = mSharedPreferences.getString("user", null);
        try {
            Gson gson = new Gson();
            user = gson.fromJson(json, UserBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();

        }
        return user;
    }

    public synchronized static void setMyBLEDeviceMap(Context ctx, MyBLEDeviceMap myBLEDeviceMap) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(spFileName,
                    Context.MODE_PRIVATE);
        }
        Gson gson = new Gson();
        String json = gson.toJson(myBLEDeviceMap);
        mSharedPreferences.edit().putString("myBLEDeviceMap", json).commit();
    }

    public static MyBLEDeviceMap getMyBLEDeviceMap(Context ctx) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(spFileName,
                    Context.MODE_PRIVATE);
        }
        MyBLEDeviceMap myBLEDeviceMap = null;
        String json = mSharedPreferences.getString("myBLEDeviceMap", null);
        try {
            Gson gson = new Gson();
            myBLEDeviceMap = gson.fromJson(json, MyBLEDeviceMap.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();

        }
        if(myBLEDeviceMap==null){
            myBLEDeviceMap = new MyBLEDeviceMap();
        }
        return myBLEDeviceMap;
    }
    public static void setBindBLEDevice(Context ctx, MyBLEDevice myBLEDevice){
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(spFileName,
                    Context.MODE_PRIVATE);
        }
        if(myBLEDevice == null){
            mSharedPreferences.edit().putString("BindBLEDevice", "").commit();
        }
        Gson gson = new Gson();
        String json = gson.toJson(myBLEDevice);
        mSharedPreferences.edit().putString("BindBLEDevice", json).commit();
    }

    public static MyBLEDevice getBindBLEDevice(Context ctx) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(spFileName,
                    Context.MODE_PRIVATE);
        }
        MyBLEDevice myBLEDevice = null;
        String json = mSharedPreferences.getString("BindBLEDevice", null);
        if(json.equals("")){
            json =null;
        }
        try {
            Gson gson = new Gson();
            myBLEDevice = gson.fromJson(json, MyBLEDevice.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();

        }
        return myBLEDevice;
    }


    public synchronized static void setPersonInfo(Context ctx, PersonInfo personInfo) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(spFileName,
                    Context.MODE_PRIVATE);
        }
        Gson gson = new Gson();
        String json = gson.toJson(personInfo);
        mSharedPreferences.edit().putString("personInfo", json).commit();
    }

    public static PersonInfo getPersonInfo(Context ctx) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(spFileName,
                    Context.MODE_PRIVATE);
        }
        PersonInfo personInfo = null;
        String json = mSharedPreferences.getString("personInfo", null);
        try {
            Gson gson = new Gson();
            personInfo = gson.fromJson(json, PersonInfo.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();

        }
        return personInfo;
    }

    public static String getString(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        String result = setPreferences.getString(strKey, "");
        return result;
    }

    public static String getString(Context context, String strKey,
                                   String strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        String result = setPreferences.getString(strKey, strDefault);
        return result;
    }

    public static void putString(Context context, String strKey, String strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putString(strKey, strData);
        editor.commit();
    }

    public static Boolean getBoolean(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, false);
        return result;
    }

    public static Boolean getBoolean(Context context, String strKey,
                                     Boolean strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, strDefault);
        return result;
    }


    public static void putBoolean(Context context, String strKey,
                                  Boolean strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean(strKey, strData);
        editor.commit();
    }

    public static int getInt(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        int result = setPreferences.getInt(strKey, -1);
        return result;
    }

    public static int getInt(Context context, String strKey, int strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        int result = setPreferences.getInt(strKey, strDefault);
        return result;
    }

    public static void putInt(Context context, String strKey, int strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putInt(strKey, strData);
        editor.commit();
    }

    public static long getLong(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        long result = setPreferences.getLong(strKey, -1);
        return result;
    }

    public static long getLong(Context context, String strKey, long strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        long result = setPreferences.getLong(strKey, strDefault);
        return result;
    }

    public static void putLong(Context context, String strKey, long strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putLong(strKey, strData);
        editor.commit();
    }

    public static float getFloat(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        float result = setPreferences.getFloat(strKey, -1);
        return result;
    }

    public static float getFloat(Context context, String strKey, float strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        float result = setPreferences.getFloat(strKey, strDefault);
        return result;
    }

    public static void putFloat(Context context, String strKey, float strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putFloat(strKey, strData);
        editor.commit();
    }

    public static void remove(Context context, String strKey) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.remove(strKey);
        editor.commit();
    }

    public static void clean(Context context) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
