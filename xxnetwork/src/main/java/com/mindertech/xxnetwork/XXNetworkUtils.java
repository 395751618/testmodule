package com.mindertech.xxnetwork;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.Date;

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
//        return new File(parentFile.getAbsolutePath(), md5 + ".temp");
        return new File(parentFile.getAbsolutePath(), new Date().toString() + ".temp");
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

    /**
     * 临时文件
     *
     * @author xiangxia
     * @createAt 2020-05-22 15:06
     */
    public static File getTempFilePath(String filePath) {
        File parentFile = new File(filePath).getParentFile();
        return new File(parentFile.getAbsolutePath(), new Date().toString() + ".temp");
    }

    /**
     * 自动计算指定文件或指定文件夹的大小:99plas
     *
     * @author xiangxia
     * @createAt 2020-05-22 15:07
     */
    public static String getAuto99PlasFileOrFilesSize() {
        String filePath = XXRxJava2DownloadManager.mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "xxnetwork";
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //获取文件大小失败!
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 自动计算指定文件或指定文件夹的大小
     *
     * @author xiangxia
     * @createAt 2020-05-22 15:05
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //获取文件大小失败!
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @author xiangxia
     * @createAt 2020-05-22 15:05
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            //获取文件大小不存在!
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @author xiangxia
     * @createAt 2020-05-22 15:05
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @author xiangxia
     * @createAt 2020-05-22 15:04
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 该文件名的文件是否存在
     *
     * @author xiangxia
     * @createAt 2020-05-22 14:53
     */
    public static boolean enableExist99PlasFile(String fileName) {
        if (null == fileName || fileName.isEmpty()) {
            return false;
        }
        String path = XXRxJava2DownloadManager.mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "xxnetwork" + File.separator + fileName;
        File file = new File(path);
        return file.getParentFile().exists();
    }

    public static void save99PlasFile(final ResponseBody responseBody, String url, final String fileName, final XXDownloadProgressCallback progressCallback, final XXDownloadCallback resultCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean downloadSuccss = true;

                String tmpFileName = fileName;
                if (null == tmpFileName || tmpFileName.isEmpty()) {
                    tmpFileName = new Date().toString();
                }

                String path = XXRxJava2DownloadManager.mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "xxnetwork" + File.separator + fileName;

                final File tempFile = XXNetworkUtils.getTempFilePath(path);
                try {
                    writeFileToDisk(responseBody, tempFile.getAbsolutePath(), progressCallback, resultCallback);
                } catch (Exception e) {
                    e.printStackTrace();
                    downloadSuccss = false;
                }

                if (downloadSuccss) {
                    final boolean renameSuccess = tempFile.renameTo(new File(path));
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (null != resultCallback && renameSuccess) {
                                resultCallback.onFinish(new File(path));
                            }
                        }
                    });
                }
            }
        }).start();
    }

    public static void saveFile(final ResponseBody responseBody, String url, final String filePath, final XXDownloadProgressCallback progressCallback, final XXDownloadCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean downloadSuccss = true;
                final File tempFile = XXNetworkUtils.getTempFile(url, filePath);
                try {
                    writeFileToDisk(responseBody, tempFile.getAbsolutePath(), progressCallback, callback);
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
        }).start();
    }

    @SuppressLint("DefaultLocale")
    public static void writeFileToDisk(ResponseBody responseBody, String filePath, final XXDownloadProgressCallback progressCallback, final XXDownloadCallback callback) throws IOException {
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
            callbackProgress(tempFileLen + totalByte, tempFileLen + downloadByte, progressCallback);
        }
        randomAccessFile.close();
    }

    public static void callbackProgress(final long totalByte, final long downloadByte, final XXDownloadProgressCallback progressCallback) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                if (null != progressCallback) {
                    progressCallback.onProgress(totalByte, downloadByte, (int) ((downloadByte * 100) / totalByte));
                }
            }
        });
    }
}
