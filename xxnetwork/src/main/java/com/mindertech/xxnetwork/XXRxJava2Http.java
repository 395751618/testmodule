package com.mindertech.xxnetwork;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2019-12-05 11:44
 * @description 描述
 */
public abstract class XXRxJava2Http<T> {

    private T http;
    private static Retrofit retrofit;

    public XXRxJava2Http() {
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

        Interceptor[] otherInterceptors = this.bindInterceptor();
        if (null != otherInterceptors) {
            for (Interceptor otherInterceptor : otherInterceptors) {
                okHttpBuilder.addInterceptor(otherInterceptor);
            }
        }
        Converter.Factory converterFactory = bindConverterFactory();
        if (null != converterFactory) {
            retrofit = new Retrofit.Builder().client(okHttpBuilder.build())
                    .baseUrl(bindBaseUrl())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(converterFactory)
                    .build();
        } else {
            retrofit = new Retrofit.Builder().client(okHttpBuilder.build())
                    .baseUrl(bindBaseUrl())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

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

    /**
     * 添加拦截器
     *
     * @author xiangxia
     * @createAt 2020-04-10 14:55
     */
    protected abstract Interceptor[] bindInterceptor();

    /**
     * 序列化工厂
     *
     * @author xiangxia
     * @createAt 2020-06-29 16:50
     */
    protected abstract Converter.Factory bindConverterFactory();

}
