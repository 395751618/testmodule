package com.mindertech.xxnetwork;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2019-12-05 09:44
 * @description 描述
 */
public interface XXHttpCallback<T> {
    /**
     * 成功回调
     */
    void success(T t);

    /**
     * 失败回调
     *
     * @param code http失败code < 0 ,业务失败code > 0, -1 未知错误
     * @param failure 失败提示信息
     */
    void failure(int code, String failure);
}
