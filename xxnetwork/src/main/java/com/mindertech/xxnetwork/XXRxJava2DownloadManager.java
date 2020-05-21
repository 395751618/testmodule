package com.mindertech.xxnetwork;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2020-05-11 13:21
 * @description 描述
 */
public class XXRxJava2DownloadManager {

    private static class Holder {
        private static XXRxJava2DownloadManager INSTANCE = new XXRxJava2DownloadManager();
    }

    private XXRxJava2DownloadManager() {

    }

    public static XXRxJava2DownloadManager getInstance() {
        return XXRxJava2DownloadManager.Holder.INSTANCE;
    }

    //-----------------------------
    public static Context mContext;

    private static Map<Class, XXRxJava2Download> sAsk = new HashMap();

    public static <T extends XXRxJava2Download> T self(Class<T> ask) {
        XXRxJava2Download request = sAsk.get(ask);
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
    public static <E, T extends XXRxJava2Download<E>> E ask(Class<T> ask) {
        XXRxJava2Download request = self(ask);
        return (E) request.http();
    }
}
