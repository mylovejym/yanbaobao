package com.psylife.wrmvplibrary.data.net;


import com.psylife.wrmvplibrary.*;
import com.psylife.wrmvplibrary.utils.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import okhttp3.*;
import okhttp3.OkHttpClient.*;
import okhttp3.Response;
import okhttp3.logging.*;
import retrofit2.*;
import retrofit2.adapter.rxjava.*;
import retrofit2.converter.gson.*;

/**
 * Created by hpw on 16/11/2.
 */

public class RxService {
    private static final int TIMEOUT_READ = 20;
    private static final int TIMEOUT_CONNECTION = 10;
    private static Map<String, String> map;
    private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static CacheInterceptor cacheInterceptor = new CacheInterceptor();
    private static Builder builder = new OkHttpClient().newBuilder();
    private static OkHttpClient okHttpClient = builder
            //SSL证书
            .sslSocketFactory(TrustManager.getUnsafeOkHttpClient())
            .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
            //打印日志
            .addInterceptor(interceptor)
            //设置Cache
            .addNetworkInterceptor(cacheInterceptor)//缓存方面需要加入这个拦截器
            .addInterceptor(cacheInterceptor)
            .addNetworkInterceptor(new RequestInterceptor())
            .cache(HttpCache.getCache())
            //time out
            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            //失败重连
            .retryOnConnectionFailure(true)
            .build();

    public static <T> T createApi(Class<T> clazz) {
        return createApi(clazz, WRCoreApp.getInstance().setBaseUrl());
    }

    public static <T> T createApi(Class<T> clazz, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(clazz);
    }
    public static <T> T createApiString(Class<T> clazz) {
        return createApiString(clazz, WRCoreApp.getInstance().setBaseUrl());
    }

    public static <T> T createApiString(Class<T> clazz, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .build();

        return retrofit.create(clazz);
    }

    /**
     * 请求拦截器，修改请求header
     */
    public static class RequestInterceptor implements Interceptor {

        public RequestInterceptor() {

        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder mBuild = original.newBuilder();
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    mBuild.header(entry.getKey(), entry.getValue());
                }
            }
            Request request = mBuild.build();
            LogUtil.d("request:" + request.toString());
            LogUtil.d("request headers:" + request.headers().toString());
            return chain.proceed(request);
        }
    }

    public static void setHeaders(Map<String, String> headMap) {
        map = headMap;
    }
}

