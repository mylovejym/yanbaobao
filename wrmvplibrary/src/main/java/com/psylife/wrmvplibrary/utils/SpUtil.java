package com.psylife.wrmvplibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;

/**
 * Created by psylife00 on 2016/11/29.
 */

public class SpUtil {
    static SharedPreferences prefs;
    private static final boolean DEFAULT_NO_IMAGE = false;
    private static final boolean DEFAULT_AUTO_SAVE = true;

    public static void init(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static int getThemeIndex(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt("ThemeIndex", 9);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public static void setThemeIndex(Context context, int index) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt("ThemeIndex", index).apply();
    }

    public static boolean getNightModel(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("pNightMode", false);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public static void setNightModel(Context context, boolean nightModel) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean("pNightMode", nightModel).apply();
    }

//    public static boolean getNoImageState() {
//        return prefs.getBoolean(CoreConstants.SP_NO_IMAGE, DEFAULT_NO_IMAGE);
//    }
//
//    public static void setNoImageState(boolean state) {
//        prefs.edit().putBoolean(CoreConstants.SP_NO_IMAGE, state).apply();
//    }
//
//    public static boolean getAutoCacheState() {
//        return prefs.getBoolean(CoreConstants.SP_AUTO_CACHE, DEFAULT_AUTO_SAVE);
//    }
//
//    public static void setAutoCacheState(boolean state) {
//        prefs.edit().putBoolean(CoreConstants.SP_AUTO_CACHE, state).apply();
//    }
}
