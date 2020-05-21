package com.mindertech.xxnetwork;

import java.io.File;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2020-05-11 13:53
 * @description 描述
 */
public interface XXDownloadCallback {

    public void onStart(Disposable d);

    public void onProgress(long totalByte, long currentByte, int progress);

    public void onFinish(File file);

    public void onFinish();

    public void onError(String msg);

    public void onSuccess(ResponseBody body);
}
