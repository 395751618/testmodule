package com.mindertech.testmodule;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.gson.JsonObject;
import com.mindertech.xxnetwork.XXHttpCallback;
import com.mindertech.xxnetwork.XXNetworkManager;

import org.json.JSONObject;

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

        XXNetworkManager.mContext = this;
        XXNetworkManager.self(TestHttp.class).signIn("xiangxia", "123456").enqueue(new XXHttpCallback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject) {
                System.out.println("");
            }

            @Override
            public void failure(int code, String failure) {
                System.out.println("");
            }
        });
    }
}
