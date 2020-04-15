package com.mindertech.xxnetwork;

import android.util.Log;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2019-12-05 14:43
 * @description 描述
 */
public abstract class XXDefaultObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

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

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    abstract public void onFailed(String message);

    public void onFinish() {
        Log.i("0", "onFinish");
    }
}
