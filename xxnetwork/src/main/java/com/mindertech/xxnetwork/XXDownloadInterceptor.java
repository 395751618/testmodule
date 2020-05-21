package com.mindertech.xxnetwork;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2020-05-09 16:17
 * @description 描述
 */
public class XXDownloadInterceptor implements Interceptor {

    private XXDownloadListener listener;

    public XXDownloadInterceptor(XXDownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new XXDownloadResponseBody(originalResponse.body(), listener))
                .build();
    }
}
