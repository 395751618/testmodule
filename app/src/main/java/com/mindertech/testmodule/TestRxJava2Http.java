package com.mindertech.testmodule;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mindertech.xxnetwork.XXHttpCall;
import com.mindertech.xxnetwork.XXHttpManager;
import com.mindertech.xxnetwork.XXRxJava2Http;
import com.mindertech.xxnetwork.XXRxJava2HttpManager;
import com.mindertech.xxnetwork.XXSchedulerProvider;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @project testmodule
 * @package：com.mindertech.testmodule
 * @anthor xiangxia
 * @time 2019-12-05 11:56
 * @description 描述
 */
public class TestRxJava2Http extends XXRxJava2Http<TestRxJava2Http.XXRxJava2HttpAsk> {

    @Override
    protected String bindBaseUrl() {
        return "http://stp-api.mindertech.net/";
    }

    @Override
    protected Context bindContext() {
        return XXRxJava2HttpManager.mContext;
    }

    @Override
    protected int bindCacheSize() {
        return 10;
    }

    @Override
    protected int bindTimeout() {
        return 30000;
    }

    @Override
    protected Interceptor[] bindInterceptor() {
        return null;
    }

    /**
     * 登录
     *
     * @author xiangxia
     * @createAt 2019-11-26 17:26
     */
    public Observable<JsonObject> signIn(String email, String password) {
        return http().signIn(email, password).compose(XXSchedulerProvider.<JsonObject>io_main());
    }

    public interface XXRxJava2HttpAsk {
        @FormUrlEncoded
        @POST("auth/local")
        Observable<JsonObject> signIn(@Field("identifier") String identifier, @Field("password") String password);
    }
}
