package com.mindertech.xxnetwork;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2019-12-05 11:48
 * @description 描述
 */
public class XXRxJava2HttpManager {

//    private static XXRxJava2HttpManager mInstance;
//
//    public static XXRxJava2HttpManager getInstance() {
//        if (mInstance == null) {
//            synchronized (XXRxJava2HttpManager.class) {
//                if (mInstance == null) {
//                    mInstance = new XXRxJava2HttpManager();
//                }
//            }
//        }
//        return mInstance;
//    }

    private static class Holder {
        private static XXRxJava2HttpManager INSTANCE = new XXRxJava2HttpManager();
    }

    private XXRxJava2HttpManager() {

    }

    public static XXRxJava2HttpManager getInstance() {
        return XXRxJava2HttpManager.Holder.INSTANCE;
    }

    //-----------------------------
    public static Context mContext;

    private static Map<Class, XXRxJava2Http> sAsk = new HashMap();

    public static <T extends XXRxJava2Http> T self(Class<T> ask) {
        XXRxJava2Http request = sAsk.get(ask);
        if (request == null) {
            try {
                request = ask.newInstance();
                sAsk.put(ask, request);
            } catch (Exception var3) {
                var3.printStackTrace();
                throw new Error("instance api error:" + var3.getMessage());
            }
        }
        return (T) request;
    }

    /**
     * 得到自己ask的实例化对象
     */
    public static <E, T extends XXRxJava2Http<E>> E ask(Class<T> ask) {
        XXRxJava2Http request = self(ask);
        return (E) request.http();
    }

}
