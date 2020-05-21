package com.mindertech.testmodule;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.mindertech.xxnetwork.XXDownloadCallback;
import com.mindertech.xxnetwork.XXDownloadProgressCallback;
import com.mindertech.xxnetwork.XXNetworkUtils;
import com.mindertech.xxnetwork.XXRxJava2DownloadManager;

import java.io.File;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @project testmodule
 * @package：com.mindertech.testmodule
 * @anthor xiangxia
 * @time 2019-12-04 14:35
 * @description 描述
 */
public class FirstActivity extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_first);

        imageView = findViewById(R.id.image_view);

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

//        XXRxJava2HttpManager.mContext = this;
//        XXRxJava2HttpManager.self(TestRxJava2Http.class).signIn("xiangxia", "123456").subscribe(new XXDefaultObserver<JsonObject>() {
//            @Override
//            public void onSuccess(JsonObject response) {
//                String string = Thread.currentThread().getName();
//                Log.e("-1", string);
//            }
//
//            @Override
//            public void onFailed(String message) {
//                Log.e("-1", message);
//            }
//        });

        XXRxJava2DownloadManager.mContext = this;

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();

        downloadFile1();

    }

    private void downloadFile1() {
        String path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "Fq4X-aGyA-DEZtwMwDX7OOYvgZmR.jpg";


        String url = "http://dev-cdn2.mindertech.net/2020/0218/FpeUDGc7ms9Yj9vVsAy3z2h5djq0.jpg!origin";
        XXRxJava2DownloadManager.self(TestRxJava2Download.class).downloadFile1(url,
                path, null, new XXDownloadCallback() {
                    @Override
                    public void onStart(Disposable d) {
                        Log.i("onStart:", "");
                    }

                    @Override
                    public void onFinish(File file) {
                        Log.i("onFinish:", "file");
                        if (null != file) {
                            imageView.setImageURI(Uri.fromFile(file));
                        }
                    }

                    @Override
                    public void onFinish() {
                        Log.i("onFinish:", "");
                    }

                    @Override
                    public void onError(String msg) {
                        Log.i("onError:", "");
                    }

                    @Override
                    public void onSuccess(ResponseBody body) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                XXNetworkUtils.saveFile(body, url, path, new XXDownloadProgressCallback() {
                                    @Override
                                    public void onProgress(long totalByte, long currentByte, int progress) {

                                    }
                                }, new XXDownloadCallback() {
                                    @Override
                                    public void onStart(Disposable d) {

                                    }

                                    @Override
                                    public void onFinish(File file) {
                                        if (null != file) {
                                            imageView.setImageURI(Uri.fromFile(file));
                                        }
                                    }

                                    @Override
                                    public void onFinish() {

                                    }

                                    @Override
                                    public void onError(String msg) {

                                    }

                                    @Override
                                    public void onSuccess(ResponseBody body) {

                                    }
                                });
                            }
                        }).start();

                    }
                });
    }

    private void downloadFile() {
        String path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "Fq4X-aGyA-DEZtwMwDX7OOYvgZmR.jpg";


        String url = "http://dev-cdn2.mindertech.net/2020/0218/FpeUDGc7ms9Yj9vVsAy3z2h5djq0.jpg!origin";
        XXRxJava2DownloadManager.self(TestRxJava2Download.class).downloadFile(url,
                path, new XXDownloadCallback() {
                    @Override
                    public void onStart(Disposable d) {
                        Log.i("onStart:", "");
                    }

                    @Override
                    public void onFinish(File file) {
                        Log.i("onFinish:", "file");
                        if (null != file) {
                            imageView.setImageURI(Uri.fromFile(file));
                        }
                    }

                    @Override
                    public void onFinish() {
                        Log.i("onFinish:", "");
                    }

                    @Override
                    public void onError(String msg) {
                        Log.i("onError:", "");
                    }

                    @Override
                    public void onSuccess(ResponseBody body) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                XXNetworkUtils.saveFile(body, url, path, new XXDownloadProgressCallback() {
                                    @Override
                                    public void onProgress(long totalByte, long currentByte, int progress) {

                                    }
                                }, new XXDownloadCallback() {
                                    @Override
                                    public void onStart(Disposable d) {

                                    }

                                    @Override
                                    public void onFinish(File file) {
                                        if (null != file) {
                                            imageView.setImageURI(Uri.fromFile(file));
                                        }
                                    }

                                    @Override
                                    public void onFinish() {

                                    }

                                    @Override
                                    public void onError(String msg) {

                                    }

                                    @Override
                                    public void onSuccess(ResponseBody body) {

                                    }
                                });
                            }
                        }).start();

                    }
                });
    }
}
