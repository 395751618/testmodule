package com.mindertech.xxnetwork;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import okhttp3.ResponseBody;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2020-05-11 14:11
 * @description 描述
 */
public class XXNetworkUtils {

    public static File getTempFile(String url, String filePath) {
        File parentFile = new File(filePath).getParentFile();
        String md5 = bytes2HexString(url.getBytes());
        return new File(parentFile.getAbsolutePath(), md5 + ".temp");
    }

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        int len = bytes.length;
        if (len <= 0) {
            return "";
        }
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    public static void saveFile(final ResponseBody responseBody, String url, final String filePath, final XXDownloadCallback callback) {
        boolean downloadSuccss = true;
        final File tempFile = XXNetworkUtils.getTempFile(url, filePath);
        try {
            writeFileToDisk(responseBody, tempFile.getAbsolutePath(), callback);
        } catch (Exception e) {
            e.printStackTrace();
            downloadSuccss = false;
        }

        if (downloadSuccss) {
            final boolean renameSuccess = tempFile.renameTo(new File(filePath));
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (null != callback && renameSuccess) {
                        callback.onFinish(new File(filePath));
                    }
                }
            });
        }
    }

    @SuppressLint("DefaultLocale")
    public static void writeFileToDisk(ResponseBody responseBody, String filePath, final XXDownloadCallback callback) throws IOException {
        long totalByte = responseBody.contentLength();
        long downloadByte = 0;
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        byte[] buffer = new byte[1024 * 4];
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
        long tempFileLen = file.length();
        randomAccessFile.seek(tempFileLen);
        while (true) {
            int len = responseBody.byteStream().read(buffer);
            if (len == -1) {
                break;
            }
            randomAccessFile.write(buffer, 0, len);
            downloadByte += len;
            callbackProgress(tempFileLen + totalByte, tempFileLen + downloadByte, callback);
        }
        randomAccessFile.close();
    }

    public static void callbackProgress(final long totalByte, final long downloadByte, final XXDownloadCallback callback) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                if (null != callback) {
                    callback.onProgress(totalByte, downloadByte, (int) ((downloadByte * 100) / totalByte));
                }
            }
        });
    }
}
