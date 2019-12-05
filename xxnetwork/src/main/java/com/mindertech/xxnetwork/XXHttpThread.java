package com.mindertech.xxnetwork;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @project testmodule
 * @package：com.mindertech.xxnetwork
 * @anthor xiangxia
 * @time 2019-12-05 09:48
 * @description 描述
 */
final public class XXHttpThread {

    private static UIHandlerPoster mainPoster = null;

    private static UIHandlerPoster getMainPoster() {
        if (mainPoster == null) {
            synchronized (XXHttpThread.class) {
                if (mainPoster == null) {
                    // This time is 1000/60 (60fps)
                    mainPoster = new UIHandlerPoster(Looper.getMainLooper(), 16);
                }
            }
        }
        return mainPoster;
    }

    /**
     * 异步进入主线程,无需等待
     * The child thread asynchronous run relative to the main thread,
     * not blocking the child thread
     *
     * @param runnable Runnable Interface
     */
    public static void runOnMainThreadAsync(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
            return;
        }
        getMainPoster().async(runnable);
    }

    /**
     * 同步进入主线程,等待主线程处理完成后继续执行子线程
     * The child thread relative thread synchronization operation,
     * blocking the child thread,
     * thread for the main thread to complete
     *
     * @param runnable Runnable Interface
     */
    public static void runOnMainThreadSync(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
            return;
        }
        UISyncPost poster = new UISyncPost(runnable);
        getMainPoster().sync(poster);
        poster.waitRun();
    }

    /**
     * Synchronously
     * The child thread relative thread synchronization operation,
     * blocking the child thread,
     * thread for the main thread to complete
     * But the child thread just wait for the waitTime long.
     *
     * @param runnable Runnable Interface
     * @param waitTime wait for the main thread run Time
     * @param cancel on the child thread cancel the runnable task
     */
    public static void runOnMainThreadSync(Runnable runnable, int waitTime, boolean cancel) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
            return;
        }
        UISyncPost poster = new UISyncPost(runnable);
        getMainPoster().sync(poster);
        poster.waitRun(waitTime, cancel);
    }

    public static void dispose() {
        if (mainPoster != null) {
            mainPoster.dispose();
            mainPoster = null;
        }
    }

    static final class UISyncPost {
        private Runnable mRunnable;
        private boolean isEnd = false;

        UISyncPost(Runnable runnable) {
            this.mRunnable = runnable;
        }

        /**
         * Run to doing something
         */
        public void run() {
            if (!isEnd) {
                synchronized (this) {
                    if (!isEnd) {
                        mRunnable.run();
                        isEnd = true;
                        try {
                            this.notifyAll();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        /**
         * Wait to run end
         */
        public void waitRun() {
            if (!isEnd) {
                synchronized (this) {
                    if (!isEnd) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        /**
         * Wait for a period of time to run end
         *
         * @param time wait time
         * @param cancel when wait end cancel the run
         */
        public void waitRun(int time, boolean cancel) {
            if (!isEnd) {
                synchronized (this) {
                    if (!isEnd) {
                        try {
                            this.wait(time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            if (!isEnd && cancel) isEnd = true;
                        }
                    }
                }
            }
        }
    }


    static final class UIHandlerPoster extends Handler {
        private static final int ASYNC = 0x1;
        private static final int SYNC = 0x2;
        private final Queue<Runnable> mAsyncPool;
        private final Queue<UISyncPost> mSyncPool;
        private final int mMaxMillisInsideHandleMessage;
        private boolean isAsyncActive;
        private boolean isSyncActive;

        /**
         * Init this
         *
         * @param looper Handler Looper
         * @param maxMillisInsideHandleMessage The maximum time occupied the main thread each cycle
         */
        UIHandlerPoster(Looper looper, int maxMillisInsideHandleMessage) {
            super(looper);
            this.mMaxMillisInsideHandleMessage = maxMillisInsideHandleMessage;
            mAsyncPool = new LinkedList<>();
            mSyncPool = new LinkedList<>();
        }

        /**
         * Pool clear
         */
        void dispose() {
            this.removeCallbacksAndMessages(null);
            this.mAsyncPool.clear();
            this.mSyncPool.clear();
        }

        void async(Runnable runnable) {
            synchronized (mAsyncPool) {
                mAsyncPool.offer(runnable);
                if (!isAsyncActive) {
                    isAsyncActive = true;
                    if (!sendMessage(obtainMessage(ASYNC))) {
                        throw new RuntimeException("Could not send handler message");
                    }
                }
            }
        }

        void sync(UISyncPost post) {
            synchronized (mSyncPool) {
                mSyncPool.offer(post);
                if (!isSyncActive) {
                    isSyncActive = true;
                    if (!sendMessage(obtainMessage(SYNC))) {
                        throw new RuntimeException("Could not send handler message");
                    }
                }
            }
        }

        /**
         * Run in main thread
         *
         * @param msg call messages
         */
        @Override public void handleMessage(Message msg) {
            if (msg.what == ASYNC) {
                boolean rescheduled = false;
                try {
                    long started = SystemClock.uptimeMillis();
                    while (true) {
                        Runnable runnable = mAsyncPool.poll();
                        if (runnable == null) {
                            synchronized (mAsyncPool) {
                                // Check again, this time in synchronized
                                runnable = mAsyncPool.poll();
                                if (runnable == null) {
                                    isAsyncActive = false;
                                    return;
                                }
                            }
                        }
                        runnable.run();
                        long timeInMethod = SystemClock.uptimeMillis() - started;
                        if (timeInMethod >= mMaxMillisInsideHandleMessage) {
                            if (!sendMessage(obtainMessage(ASYNC))) {
                                throw new RuntimeException("Could not send handler message");
                            }
                            rescheduled = true;
                            return;
                        }
                    }
                } finally {
                    isAsyncActive = rescheduled;
                }
            } else if (msg.what == SYNC) {
                boolean rescheduled = false;
                try {
                    long started = SystemClock.uptimeMillis();
                    while (true) {
                        UISyncPost post = mSyncPool.poll();
                        if (post == null) {
                            synchronized (mSyncPool) {
                                // Check again, this time in synchronized
                                post = mSyncPool.poll();
                                if (post == null) {
                                    isSyncActive = false;
                                    return;
                                }
                            }
                        }
                        post.run();
                        long timeInMethod = SystemClock.uptimeMillis() - started;
                        if (timeInMethod >= mMaxMillisInsideHandleMessage) {
                            if (!sendMessage(obtainMessage(SYNC))) {
                                throw new RuntimeException("Could not send handler message");
                            }
                            rescheduled = true;
                            return;
                        }
                    }
                } finally {
                    isSyncActive = rescheduled;
                }
            } else {
                super.handleMessage(msg);
            }
        }
    }
}
