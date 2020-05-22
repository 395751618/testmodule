package com.mindertech.testmodule;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.mindertech.xxnetwork.FileRxJava2Download;
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

//        downloadFile1();
        download99PlasFile();

    }

    private void download99PlasFile() {
        String url = "http://dev-cdn2.mindertech.net/2020/0522/FnRr3mWoTBmjRaOiQxav6m2eE70p.doc?e=1590126702&token=ir9ieoSKTdB5ypBwUuviE2GvFOBortp_rhOmlTP2:6u-0kN_IA4Gv6w2NZj8upBMYUeE=";
        XXRxJava2DownloadManager.self(FileRxJava2Download.class).downloadFile(url, "abc123.doc", new XXDownloadProgressCallback() {
            @Override
            public void onProgress(long totalByte, long currentByte, int progress) {
                System.out.println("totalByte:" + totalByte + " currentByte:" + currentByte + " progress:" + progress);
            }
        }, new XXDownloadCallback() {
            @Override
            public void onStart(Disposable d) {

            }

            @Override
            public void onFinish(File file) {
                Log.i("onFinish:", "file");
            }

            @Override
            public void onFinish() {
                Log.i("onFinish:", "ok");
            }

            @Override
            public void onFailure(String msg) {
                Log.i("onFailure:", "msg");
            }
        });
    }

    private void downloadFile1() {
        String path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "xxnetwork" + File.separator + "abc123.doc";

//        String url = "http://dev-cdn2.mindertech.net/2020/0218/FpeUDGc7ms9Yj9vVsAy3z2h5djq0.jpg!origin";
        String url = "http://dev-cdn2.mindertech.net/2020/0522/FnRr3mWoTBmjRaOiQxav6m2eE70p.doc?e=1590126702&token=ir9ieoSKTdB5ypBwUuviE2GvFOBortp_rhOmlTP2:6u-0kN_IA4Gv6w2NZj8upBMYUeE=";
//        String url = "http://dev-cdn2.mindertech.net/2020/0509/FhxrSPOlDPI2oB8Qm9pQJxBwCAv7.pdf?e=1590119895&token=ir9ieoSKTdB5ypBwUuviE2GvFOBortp_rhOmlTP2:CAl_1Ukeaph33jjp6D2O8oltQxc=";
                XXRxJava2DownloadManager.self(TestRxJava2Download.class).downloadFile1(url,
                        path, new XXDownloadProgressCallback() {
                            @Override
                            public void onProgress(long totalByte, long currentByte, int progress) {
                                System.out.println("totalByte:" + totalByte + " currentByte:" + currentByte + " progress:" + progress);
                            }
                        }, new XXDownloadCallback() {
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
                            public void onFailure(String msg) {
                                Log.i("onError:", "");
                            }

                        });
    }

    private void downloadFile() {
        String path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "123456.jpg";


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
                    public void onFailure(String msg) {
                        Log.i("onError:", "");
                    }

                });
    }
}
