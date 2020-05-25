package com.mindertech.xxnetwork;

import android.content.Context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.Interceptor;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2020-05-22 14:02
 * @description 描述
 */
public class FileRxJava2Download extends XXRxJava2Download<FileRxJava2Download.FileRxJava2DownloadAsk> {

    @Override
    protected String bindBaseUrl() {
        return "https://www.baidu.com";
    }

    @Override
    protected Context bindContext() {
        return XXRxJava2DownloadManager.mContext;
    }

    @Override
    protected int bindCacheSize() {
        return 10;
    }

    @Override
    protected int bindTimeout() {
        return 300000;
    }

    @Override
    protected Interceptor[] bindInterceptor() {
        return new Interceptor[0];
    }

    public void download99PlasFile(String url, String fileName, String referer, String language, final XXDownloadProgressCallback progressCallback, final XXDownloadCallback resultCallback) {

        File file = XXNetworkUtils.enableExist99PlasFile(fileName);
        if (null != file) {
            if (null != progressCallback) {
                long size = 0;
                try {
                    size = XXNetworkUtils.getFileSize(file);
                    progressCallback.onProgress(size,size,100);
                } catch (Exception e) {
                    progressCallback.onProgress(0,0,100);
                }
            }
            if (null != resultCallback) {
                resultCallback.onFinish(file);
            }
            return;
        }

        Map map = new HashMap();
        map.put("Referer", referer);
        map.put("Accept-Language", language);

        http().downloadFile(url, map).compose(XXSchedulerProvider.<ResponseBody>io_main()).subscribe(new XXDownloadObserver<ResponseBody>() {
            @Override
            public void onStart(Disposable d) {

            }

            @Override
            public void onSuccess(ResponseBody response) {
                XXNetworkUtils.save99PlasFile(response, url, fileName, progressCallback, resultCallback);
            }

            @Override
            public void onFinish() {
                if (null != resultCallback) {
                    resultCallback.onFinish();
                }
            }

            @Override
            public void onFailed(String message) {
                if (null != resultCallback) {
                    resultCallback.onFailure(message);
                }
            }
        });
    }

    public interface FileRxJava2DownloadAsk {

        @Streaming
        @GET
        Observable<ResponseBody> downloadFile1(@Url String url, @Header("Referer") String referer, @Header("Accept-Language") String language);

        @Streaming
        @GET
        Observable<ResponseBody> downloadFile(@Url String url, @HeaderMap Map<String, String> headers);
    }
}
