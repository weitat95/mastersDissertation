/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Process
 *  android.util.Log
 */
package android.support.v4.content;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

abstract class ModernAsyncTask<Params, Progress, Result> {
    public static final Executor THREAD_POOL_EXECUTOR;
    private static volatile Executor sDefaultExecutor;
    private static InternalHandler sHandler;
    private static final BlockingQueue<Runnable> sPoolWorkQueue;
    private static final ThreadFactory sThreadFactory;
    private final AtomicBoolean mCancelled;
    private final FutureTask<Result> mFuture;
    private volatile Status mStatus = Status.PENDING;
    private final AtomicBoolean mTaskInvoked;
    private final WorkerRunnable<Params, Result> mWorker;

    static {
        sThreadFactory = new ThreadFactory(){
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "ModernAsyncTask #" + this.mCount.getAndIncrement());
            }
        };
        sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(10);
        sDefaultExecutor = THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(5, 128, 1L, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
    }

    public ModernAsyncTask() {
        this.mCancelled = new AtomicBoolean();
        this.mTaskInvoked = new AtomicBoolean();
        this.mWorker = new WorkerRunnable<Params, Result>(){

            @Override
            public Result call() throws Exception {
                Result Result2;
                ModernAsyncTask.this.mTaskInvoked.set(true);
                Result Result3 = null;
                Result Result4 = Result2 = null;
                Result Result5 = Result3;
                Process.setThreadPriority((int)10);
                Result4 = Result2;
                Result5 = Result3;
                Result4 = Result2 = (Result)ModernAsyncTask.this.doInBackground(this.mParams);
                Result5 = Result2;
                try {
                    Binder.flushPendingCommands();
                    ModernAsyncTask.this.postResult(Result2);
                }
                catch (Throwable throwable) {
                    Result5 = Result4;
                    try {
                        ModernAsyncTask.this.mCancelled.set(true);
                        Result5 = Result4;
                    }
                    catch (Throwable throwable2) {
                        ModernAsyncTask.this.postResult(Result5);
                        throw throwable2;
                    }
                    throw throwable;
                }
                return Result2;
            }
        };
        this.mFuture = new FutureTask<Result>(this.mWorker){

            @Override
            protected void done() {
                try {
                    Object v = this.get();
                    ModernAsyncTask.this.postResultIfNotInvoked(v);
                    return;
                }
                catch (InterruptedException interruptedException) {
                    Log.w((String)"AsyncTask", (Throwable)interruptedException);
                    return;
                }
                catch (ExecutionException executionException) {
                    throw new RuntimeException("An error occurred while executing doInBackground()", executionException.getCause());
                }
                catch (CancellationException cancellationException) {
                    ModernAsyncTask.this.postResultIfNotInvoked(null);
                    return;
                }
                catch (Throwable throwable) {
                    throw new RuntimeException("An error occurred while executing doInBackground()", throwable);
                }
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Handler getHandler() {
        synchronized (ModernAsyncTask.class) {
            if (sHandler != null) return sHandler;
            sHandler = new InternalHandler();
            return sHandler;
        }
    }

    public final boolean cancel(boolean bl) {
        this.mCancelled.set(true);
        return this.mFuture.cancel(bl);
    }

    protected abstract Result doInBackground(Params ... var1);

    public final ModernAsyncTask<Params, Progress, Result> executeOnExecutor(Executor executor, Params ... arrParams) {
        if (this.mStatus != Status.PENDING) {
            switch (4.$SwitchMap$android$support$v4$content$ModernAsyncTask$Status[this.mStatus.ordinal()]) {
                default: {
                    throw new IllegalStateException("We should never reach this state");
                }
                case 1: {
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                }
                case 2: 
            }
            throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
        }
        this.mStatus = Status.RUNNING;
        this.onPreExecute();
        this.mWorker.mParams = arrParams;
        executor.execute(this.mFuture);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    void finish(Result Result2) {
        if (this.isCancelled()) {
            this.onCancelled(Result2);
        } else {
            this.onPostExecute(Result2);
        }
        this.mStatus = Status.FINISHED;
    }

    public final boolean isCancelled() {
        return this.mCancelled.get();
    }

    protected void onCancelled() {
    }

    protected void onCancelled(Result Result2) {
        this.onCancelled();
    }

    protected void onPostExecute(Result Result2) {
    }

    protected void onPreExecute() {
    }

    protected void onProgressUpdate(Progress ... arrProgress) {
    }

    Result postResult(Result Result2) {
        ModernAsyncTask.getHandler().obtainMessage(1, new AsyncTaskResult<Object>(this, Result2)).sendToTarget();
        return Result2;
    }

    void postResultIfNotInvoked(Result Result2) {
        if (!this.mTaskInvoked.get()) {
            this.postResult(Result2);
        }
    }

    private static class AsyncTaskResult<Data> {
        final Data[] mData;
        final ModernAsyncTask mTask;

        AsyncTaskResult(ModernAsyncTask modernAsyncTask, Data ... arrData) {
            this.mTask = modernAsyncTask;
            this.mData = arrData;
        }
    }

    private static class InternalHandler
    extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        public void handleMessage(Message message) {
            AsyncTaskResult asyncTaskResult = (AsyncTaskResult)message.obj;
            switch (message.what) {
                default: {
                    return;
                }
                case 1: {
                    asyncTaskResult.mTask.finish(asyncTaskResult.mData[0]);
                    return;
                }
                case 2: 
            }
            asyncTaskResult.mTask.onProgressUpdate(asyncTaskResult.mData);
        }
    }

    public static enum Status {
        PENDING,
        RUNNING,
        FINISHED;

    }

    private static abstract class WorkerRunnable<Params, Result>
    implements Callable<Result> {
        Params[] mParams;

        WorkerRunnable() {
        }
    }

}

