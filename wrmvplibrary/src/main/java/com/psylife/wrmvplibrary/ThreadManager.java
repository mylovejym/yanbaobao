package com.psylife.wrmvplibrary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by psylife00 on 2017/4/12.
 */

public class ThreadManager {
    private static ExecutorService mCacheThreadExecutor = null;
    public ThreadManager(){
        mCacheThreadExecutor = Executors.newCachedThreadPool();
    }
}
