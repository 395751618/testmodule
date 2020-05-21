package com.mindertech.testmodule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.mindertech.xxnetwork.XXDownloadCallback;
import com.mindertech.xxnetwork.XXDownloadInterceptor;
import com.mindertech.xxnetwork.XXDownloadListener;
import com.mindertech.xxnetwork.XXDownloadObserver;
import com.mindertech.xxnetwork.XXNetworkUtils;
import com.mindertech.xxnetwork.XXRxJava2Download;
import com.mindertech.xxnetwork.XXRxJava2DownloadManager;
import com.mindertech.xxnetwork.XXRxJava2Http;
import com.mindertech.xxnetwork.XXRxJava2HttpManager;
import com.mindertech.xxnetwork.XXSchedulerProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.Interceptor;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @project testmodule
 * @package：com.mindertech.testmodule
 * @anthor xiangxia
 * @time 2020-05-09 16:31
 * @description 描述
 */
public class TestRxJava2Download extends XXRxJava2Download<TestRxJava2Download.TestRxJava2DownloadAsk> {

    @Override
    protected String bindBaseUrl() {
        return "http://www.baidu.com/";
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
//        return new Interceptor[]{new XXDownloadInterceptor(new XXDownloadListener() {
//            @Override
//            public void onSaveFile(ResponseBody responseBody) {
//
//            }
//        })};
    }

    @Override
    protected XXDownloadListener downloadListener() {
        return null;
//        return new XXDownloadListener() {
//            @Override
//            public void onSaveFile(ResponseBody responseBody) {
//
//            }
//        };
    }

    public void downloadFile1(String url, String path, final XXDownloadCallback callback) {
        http().downloadFile(url).compose(XXSchedulerProvider.<ResponseBody>io_main()).subscribe(new XXDownloadObserver<ResponseBody>() {
            @Override
            public void onStart(Disposable d) {
                Log.d("xxxxxxxxxxxxonStart", "Disposable");
                if (null != callback) {
                    callback.onStart(d);
                }
            }

            @Override
            public void onSuccess(ResponseBody response) {
                Log.d("xxxxxxxxxxxxonSuccess", "ResponseBody");

                XXNetworkUtils.saveFile(response, url, path, callback);

//                saveFile(response, callback);
//                if (null != callback) {
//                    callback.onSuccess(response);
//                }

            }

            @Override
            public void onFinish() {
                Log.d("xxxxxxxxxxxxonFinish", "");
                if (null != callback) {
                    callback.onFinish();
                }
            }

            @Override
            public void onFailed(String message) {
                Log.d("xxxxxxxxxxxxonFailed", "message");
                if (null != callback) {
                    callback.onError(message);
                }
            }
        });
    }

    /**
     * 下载文件
     *
     * @author xiangxia
     * @createAt 2020-05-09 16:02
     */
    public void downloadFile(String url, String path, final XXDownloadCallback callback) {
        http().downloadFile(url).compose(XXSchedulerProvider.<ResponseBody>io_main()).subscribe(new XXDownloadObserver<ResponseBody>() {
            @Override
            public void onStart(Disposable d) {
                Log.d("xxxxxxxxxxxxonStart", "Disposable");
                if (null != callback) {
                    callback.onStart(d);
                }
            }

            @Override
            public void onSuccess(ResponseBody response) {
                Log.d("xxxxxxxxxxxxonSuccess", "ResponseBody");

//                XXNetworkUtils.saveFile(response, url, path, callback);

//                saveFile(response, callback);
                if (null != callback) {
                    callback.onSuccess(response);
                }

            }

            @Override
            public void onFinish() {
                Log.d("xxxxxxxxxxxxonFinish", "000");
                if (null != callback) {
                    callback.onFinish(null);
                }
            }

            @Override
            public void onFailed(String message) {
                Log.d("xxxxxxxxxxxxonFailed", "message");
                if (null != callback) {
                    callback.onError(message);
                }
            }
        });
    }

    public interface  TestRxJava2DownloadAsk {
        @Streaming
        @GET
        Observable<ResponseBody> downloadFile(@Url String url);
    }
}
