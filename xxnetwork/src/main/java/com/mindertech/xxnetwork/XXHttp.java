package com.mindertech.xxnetwork;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2019-12-04 15:50
 * @description 描述
 */
public abstract class XXHttp<T> {

    private T http;
    private static Retrofit retrofit;

    public XXHttp() {
        init();
    }

    public void init() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        int cacheSize = bindCacheSize() * 1024 * 1024;//10 * 1024 * 1024; // 10 MiB
        Context context = bindContext();
        if (null == context) {
            Log.e("-1", "context is null");
        }
        File file = context.getCacheDir();
        Cache cache = new Cache(file, cacheSize);

        int timeout = bindTimeout() / 2;
        okHttpBuilder.connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeout * 2, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .cache(cache);

        retrofit = new Retrofit.Builder().client(okHttpBuilder.build())
                    .baseUrl(bindBaseUrl())
                    .addCallAdapterFactory(new XXHttpCallFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        this.http = (T) retrofit.create(this.getTClass());
    }

    private Class getTClass() {
        return (Class) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T http() {
        return this.http;
    }

    /**
     * 请求地址
     *
     * @author xiangxia
     * @createAt 2019-12-04 16:18
     */
    protected abstract String bindBaseUrl();

    protected abstract Context bindContext();

    /**
     * 缓存大小，单位：Mib
     *
     * @author xiangxia
     * @createAt 2019-12-04 16:16
     */
    protected abstract int bindCacheSize();

    /**
     * 超时时间，单位：毫秒
     *
     * @author xiangxia
     * @createAt 2019-12-04 16:18
     */
    protected abstract int bindTimeout();
}
