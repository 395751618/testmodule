package com.mindertech.xxnetwork;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2019-12-04 16:21
 * @description 描述
 */
public class XXNetworkManager {

//    private static XXNetworkManager mInstance;
//
//    public static XXNetworkManager getInstance() {
//        if (mInstance == null) {
//            synchronized (XXNetworkManager.class) {
//                if (mInstance == null) {
//                    mInstance = new XXNetworkManager();
//                }
//            }
//        }
//        return mInstance;
//    }

    private static class Holder {
        private static XXNetworkManager INSTANCE = new XXNetworkManager();
    }

    private XXNetworkManager() {

    }

    public static XXNetworkManager getInstance() {
        return Holder.INSTANCE;
    }

    //-----------------------------
    public static Context mContext;

    private static Map<Class, XXHttp> sAsk = new HashMap();

    public static <T extends XXHttp> T self(Class<T> ask) {
        XXHttp request = sAsk.get(ask);
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
    public static <E, T extends XXHttp<E>> E ask(Class<T> ask) {
        XXHttp request = self(ask);
        return (E) request.http();
    }

}
