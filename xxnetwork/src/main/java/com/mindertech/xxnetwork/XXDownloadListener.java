package com.mindertech.xxnetwork;

import okhttp3.ResponseBody;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2020-05-09 16:17
 * @description 描述
 */
public interface XXDownloadListener {
    public void onSaveFile(ResponseBody responseBody);
}
