package com.mindertech.testmodule;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.JsonObject;
import com.mindertech.xxnetwork.XXDefaultObserver;
import com.mindertech.xxnetwork.XXHttpCallback;
import com.mindertech.xxnetwork.XXHttpManager;
import com.mindertech.xxnetwork.XXRxJava2HttpManager;
import com.mindertech.xxnetwork.XXSchedulerProvider;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @project testmodule
 * @package：com.mindertech.testmodule
 * @anthor xiangxia
 * @time 2019-12-04 14:35
 * @description 描述
 */
public class FirstActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_first);

//        XXHttpManager.mContext = this;
//        XXHttpManager.self(TestHttp.class).signIn("xiangxia", "123456").enqueue(new XXHttpCallback<JsonObject>() {
//            @Override
//            public void success(JsonObject jsonObject) {
//                System.out.println("");
//            }
//
//            @Override
//            public void failure(int code, String failure) {
//                System.out.println("");
//            }
//        });

        XXRxJava2HttpManager.mContext = this;
        XXRxJava2HttpManager.self(TestRxJava2Http.class).signIn("xiangxia", "123456").subscribe(new XXDefaultObserver<JsonObject>() {
            @Override
            public void onSuccess(JsonObject response) {
                String string = Thread.currentThread().getName();
                Log.e("-1", string);
            }

            @Override
            public void onFailed(String message) {
                Log.e("-1", message);
            }
        });
    }
}
