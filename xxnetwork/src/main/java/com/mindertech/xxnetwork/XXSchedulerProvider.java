package com.mindertech.xxnetwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.xml.transform.Transformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2019-12-05 14:05
 * @description 描述
 */
public class XXSchedulerProvider {

    @Nullable
    private static XXSchedulerProvider INSTANCE;

    private XXSchedulerProvider() {
    }

    public static synchronized XXSchedulerProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new XXSchedulerProvider();
        }
        return INSTANCE;
    }

    public <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T,T> io_main(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return  upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
