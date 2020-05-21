package com.mindertech.xxnetwork;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2020-05-11 13:56
 * @description 描述
 */
public abstract class XXDownloadObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
        onStart(d);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable throwable) {
        XXApiException exception = XXCustomException.handleException(throwable);
        onFailed(exception.getDisplayMessage());
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    abstract public void onStart(Disposable d);

    abstract public void onSuccess(T response);

    abstract public void onFinish();

    abstract public void onFailed(String message);

}
