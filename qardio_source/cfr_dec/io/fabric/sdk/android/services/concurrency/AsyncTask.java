/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Process
 *  android.util.Log
 */
package io.fabric.sdk.android.services.concurrency;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import java.util.LinkedList;
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

public abstract class AsyncTask<Params, Progress, Result> {
    private static final int CORE_POOL_SIZE;
    private static final int CPU_COUNT;
    private static final int MAXIMUM_POOL_SIZE;
    public static final Executor SERIAL_EXECUTOR;
    public static final Executor THREAD_POOL_EXECUTOR;
    private static volatile Executor defaultExecutor;
    private static final InternalHandler handler;
    private static final BlockingQueue<Runnable> poolWorkQueue;
    private static final ThreadFactory threadFactory;
    private final AtomicBoolean cancelled;
    private final FutureTask<Result> future;
    private volatile Status status = Status.PENDING;
    private final AtomicBoolean taskInvoked;
    private final WorkerRunnable<Params, Result> worker;

    static {
        CPU_COUNT = Runtime.getRuntime().availableProcessors();
        CORE_POOL_SIZE = CPU_COUNT + 1;
        MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
        threadFactory = new ThreadFactory(){
            private final AtomicInteger count = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "AsyncTask #" + this.count.getAndIncrement());
            }
        };
        poolWorkQueue = new LinkedBlockingQueue<Runnable>(128);
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 1L, TimeUnit.SECONDS, poolWorkQueue, threadFactory);
        SERIAL_EXECUTOR = new SerialExecutor();
        handler = new InternalHandler();
        defaultExecutor = SERIAL_EXECUTOR;
    }

    public AsyncTask() {
        this.cancelled = new AtomicBoolean();
        this.taskInvoked = new AtomicBoolean();
        this.worker = new WorkerRunnable<Params, Result>(){

            @Override
            public Result call() throws Exception {
                AsyncTask.this.taskInvoked.set(true);
                Process.setThreadPriority((int)10);
                return (Result)AsyncTask.this.postResult(AsyncTask.this.doInBackground(this.params));
            }
        };
        this.future = new FutureTask<Result>(this.worker){

            @Override
            protected void done() {
                try {
                    AsyncTask.this.postResultIfNotInvoked(this.get());
                    return;
                }
                catch (InterruptedException interruptedException) {
                    Log.w((String)"AsyncTask", (Throwable)interruptedException);
                    return;
                }
                catch (ExecutionException executionException) {
                    throw new RuntimeException("An error occured while executing doInBackground()", executionException.getCause());
                }
                catch (CancellationException cancellationException) {
                    AsyncTask.this.postResultIfNotInvoked(null);
                    return;
                }
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    private void finish(Result Result2) {
        if (this.isCancelled()) {
            this.onCancelled(Result2);
        } else {
            this.onPostExecute(Result2);
        }
        this.status = Status.FINISHED;
    }

    private Result postResult(Result Result2) {
        handler.obtainMessage(1, new AsyncTaskResult<Object>(this, Result2)).sendToTarget();
        return Result2;
    }

    private void postResultIfNotInvoked(Result Result2) {
        if (!this.taskInvoked.get()) {
            this.postResult(Result2);
        }
    }

    public final boolean cancel(boolean bl) {
        this.cancelled.set(true);
        return this.future.cancel(bl);
    }

    protected abstract Result doInBackground(Params ... var1);

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public final AsyncTask<Params, Progress, Result> executeOnExecutor(Executor var1_1, Params ... var2_2) {
        if (this.status == Status.PENDING) ** GOTO lbl-1000
        switch (4.$SwitchMap$io$fabric$sdk$android$services$concurrency$AsyncTask$Status[this.status.ordinal()]) {
            default: lbl-1000:
            // 2 sources
            {
                this.status = Status.RUNNING;
                this.onPreExecute();
                this.worker.params = var2_2;
                var1_1.execute(this.future);
                return this;
            }
            case 1: {
                throw new IllegalStateException("Cannot execute task: the task is already running.");
            }
            case 2: 
        }
        throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
    }

    public final Status getStatus() {
        return this.status;
    }

    public final boolean isCancelled() {
        return this.cancelled.get();
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

    private static class AsyncTaskResult<Data> {
        final Data[] data;
        final AsyncTask task;

        AsyncTaskResult(AsyncTask asyncTask, Data ... arrData) {
            this.task = asyncTask;
            this.data = arrData;
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
                    asyncTaskResult.task.finish(asyncTaskResult.data[0]);
                    return;
                }
                case 2: 
            }
            asyncTaskResult.task.onProgressUpdate(asyncTaskResult.data);
        }
    }

    private static class SerialExecutor
    implements Executor {
        Runnable active;
        final LinkedList<Runnable> tasks = new LinkedList();

        private SerialExecutor() {
        }

        @Override
        public void execute(final Runnable runnable) {
            synchronized (this) {
                this.tasks.offer(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            runnable.run();
                            return;
                        }
                        finally {
                            SerialExecutor.this.scheduleNext();
                        }
                    }
                });
                if (this.active == null) {
                    this.scheduleNext();
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void scheduleNext() {
            synchronized (this) {
                Runnable runnable;
                this.active = runnable = this.tasks.poll();
                if (runnable != null) {
                    THREAD_POOL_EXECUTOR.execute(this.active);
                }
                return;
            }
        }

    }

    public static enum Status {
        PENDING,
        RUNNING,
        FINISHED;

    }

    private static abstract class WorkerRunnable<Params, Result>
    implements Callable<Result> {
        Params[] params;

        private WorkerRunnable() {
        }
    }

}

