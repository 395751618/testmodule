package com.mindertech.xxnetwork;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2019-12-05 13:42
 * @description 描述
 */
public class XXCustomException {

    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;

    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;

    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = 1002;

    /**
     * 连接错误
     */
    public static final  int CONNECT_ERROR = 1003;

    /**
     * 连接超时
     */
    public static final int CONNECT_TIMEOUT = 1003;

    /**
     * 协议错误
     */
    public static final int HTTP_ERROR = 1004;

    /**
     * 组装Exception
     *
     * @author xiangxia
     * @createAt 2019-12-05 17:27
     */
    public static XXApiException handleException(Throwable e) {
        XXApiException ex;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //解析错误
            ex = new XXApiException(PARSE_ERROR, e.getMessage());
            return ex;
        } else if (e instanceof ConnectException) {
            //网络错误
            ex = new XXApiException(NETWORK_ERROR, e.getMessage());
            return ex;
        } else if (e instanceof UnknownHostException) {
            //连接错误
            ex = new XXApiException(CONNECT_ERROR, e.getMessage());
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            //连接超时
            ex = new XXApiException(CONNECT_TIMEOUT, e.getMessage());
            return ex;
        } else {
            //未知错误
            ex = new XXApiException(UNKNOWN, e.getMessage());
            return ex;
        }
    }
}
