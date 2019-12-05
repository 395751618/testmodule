package com.mindertech.xxnetwork;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2019-12-05 09:43
 * @description 描述
 */
public interface XXHttpCall<T> {

    //取消
    void cancel();
    //克隆
    XXHttpCall<T> clone();
    //执行
    void enqueue(XXHttpCallback<T> callback);
}
