package com.mindertech.testmodule;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mindertech.xxnetwork.XXHttp;
import com.mindertech.xxnetwork.XXHttpCall;
import com.mindertech.xxnetwork.XXHttpCallback;
import com.mindertech.xxnetwork.XXNetworkManager;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @project testmodule
 * @package：com.mindertech.testmodule
 * @anthor xiangxia
 * @time 2019-12-04 16:54
 * @description 描述
 */
public class TestHttp extends XXHttp<TestHttp.XXHttpAsk> {

    @Override
    protected String bindBaseUrl() {
        return "http://stp-api.mindertech.net/";
    }

    @Override
    protected Context bindContext() {
        return XXNetworkManager.mContext;
    }

    @Override
    protected int bindCacheSize() {
        return 10;
    }

    @Override
    protected int bindTimeout() {
        return 30000;
    }

    /**
     * 登录
     *
     * @author xiangxia
     * @createAt 2019-11-26 17:26
     */
    public XXHttpCall<JsonObject> signIn(String email, String password) {
        return http().signIn(email, password);
    }

    public interface XXHttpAsk {
        @FormUrlEncoded
        @POST("auth/local")
        XXHttpCall<JsonObject> signIn(@Field("identifier") String identifier, @Field("password") String password);
    }
}
