package com.mindertech.xxnetwork;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.Interceptor;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
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

    public void downloadFile(String url, String fileName, final XXDownloadProgressCallback progressCallback, final XXDownloadCallback resultCallback) {
        http().downloadFile(url).compose(XXSchedulerProvider.<ResponseBody>io_main()).subscribe(new XXDownloadObserver<ResponseBody>() {
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
        Observable<ResponseBody> downloadFile(@Url String url);
    }
}
