package com.mindertech.xxnetwork;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

import retrofit2.Response;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2019-12-05 09:43
 * @description 描述
 */
public class XXHttpCallAdapter<T> implements XXHttpCall<T> {
    private final retrofit2.Call call;

    XXHttpCallAdapter(retrofit2.Call call) {
        this.call = call;
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    @Override
    public XXHttpCall<T> clone() {
        return new XXHttpCallAdapter<>(call.clone());
    }

    @Override
    public void enqueue(final XXHttpCallback<T> callback) {

        call.enqueue(new retrofit2.Callback<T>() {
            @Override public void onResponse(retrofit2.Call<T> call, final Response<T> response) {
                XXHttpThread.runOnMainThreadAsync(new Runnable() {
                    @Override public void run() {
                        int code = 0;
                        String msg = "";
                        if (response != null) {
                            code = -response.code();
                            if (code > -300 && code <= -200) {
                                callback.success(response.body());
                                return;
                            }
                        }
                        if (null == msg || msg.length() == 0 || msg.equals("null")) {
                            msg = "数据异常";
                        }
                        callback.failure(code, msg);
                    }
                });
            }

            @Override public void onFailure(retrofit2.Call<T> call, final Throwable t) {
                XXHttpThread.runOnMainThreadAsync(new Runnable() {
                    @Override public void run() {
                        t.printStackTrace();
                        if (t instanceof SocketTimeoutException) {
                            callback.failure(-1, "网络超时");
                        } else if (t instanceof ConnectException) {
                            callback.failure(-2, "连接异常");
                        } else {
                            callback.failure(-3, "网络异常");
                        }
                    }
                });
            }
        });
    }
}
