package com.mindertech.xxnetwork;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2020-05-21 17:40
 * @description 描述
 */
public interface XXDownloadProgressCallback {

    public void onProgress(long totalByte, long currentByte, int progress);
}
