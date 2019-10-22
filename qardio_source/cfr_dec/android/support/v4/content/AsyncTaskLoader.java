/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.SystemClock
 *  android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader
 *  android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader$android.support.v4.content.AsyncTaskLoader
 *  android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader$android.support.v4.content.AsyncTaskLoader$LoadTask
 */
package android.support.v4.content;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.AsyncTaskLoader.android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.ModernAsyncTask;
import android.support.v4.os.OperationCanceledException;
import android.support.v4.util.TimeUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public abstract class AsyncTaskLoader<D>
extends Loader<D> {
    volatile android.support.v4.content.AsyncTaskLoader$android.support.v4.content.AsyncTaskLoader$LoadTask mCancellingTask;
    private final Executor mExecutor;
    Handler mHandler;
    long mLastLoadCompleteTime = -10000L;
    volatile android.support.v4.content.AsyncTaskLoader$android.support.v4.content.AsyncTaskLoader$LoadTask mTask;
    long mUpdateThrottle;

    public AsyncTaskLoader(Context context) {
        this(context, ModernAsyncTask.THREAD_POOL_EXECUTOR);
    }

    private AsyncTaskLoader(Context context, Executor executor) {
        super(context);
        this.mExecutor = executor;
    }

    public void cancelLoadInBackground() {
    }

    void dispatchOnCancelled(android.support.v4.content.AsyncTaskLoader$android.support.v4.content.AsyncTaskLoader$LoadTask loadTask, D d) {
        this.onCanceled(d);
        if (this.mCancellingTask == loadTask) {
            this.rollbackContentChanged();
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
            this.mCancellingTask = null;
            this.deliverCancellation();
            this.executePendingTask();
        }
    }

    void dispatchOnLoadComplete(android.support.v4.content.AsyncTaskLoader$android.support.v4.content.AsyncTaskLoader$LoadTask loadTask, D d) {
        if (this.mTask != loadTask) {
            this.dispatchOnCancelled((LoadTask)loadTask, d);
            return;
        }
        if (this.isAbandoned()) {
            this.onCanceled(d);
            return;
        }
        this.commitContentChanged();
        this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
        this.mTask = null;
        this.deliverResult(d);
    }

    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(string2, fileDescriptor, printWriter, arrstring);
        if (this.mTask != null) {
            printWriter.print(string2);
            printWriter.print("mTask=");
            printWriter.print((Object)this.mTask);
            printWriter.print(" waiting=");
            printWriter.println(this.mTask.waiting);
        }
        if (this.mCancellingTask != null) {
            printWriter.print(string2);
            printWriter.print("mCancellingTask=");
            printWriter.print((Object)this.mCancellingTask);
            printWriter.print(" waiting=");
            printWriter.println(this.mCancellingTask.waiting);
        }
        if (this.mUpdateThrottle != 0L) {
            printWriter.print(string2);
            printWriter.print("mUpdateThrottle=");
            TimeUtils.formatDuration(this.mUpdateThrottle, printWriter);
            printWriter.print(" mLastLoadCompleteTime=");
            TimeUtils.formatDuration(this.mLastLoadCompleteTime, SystemClock.uptimeMillis(), printWriter);
            printWriter.println();
        }
    }

    void executePendingTask() {
        block5: {
            block4: {
                if (this.mCancellingTask != null || this.mTask == null) break block4;
                if (this.mTask.waiting) {
                    this.mTask.waiting = false;
                    this.mHandler.removeCallbacks((Runnable)this.mTask);
                }
                if (this.mUpdateThrottle <= 0L || SystemClock.uptimeMillis() >= this.mLastLoadCompleteTime + this.mUpdateThrottle) break block5;
                this.mTask.waiting = true;
                this.mHandler.postAtTime((Runnable)this.mTask, this.mLastLoadCompleteTime + this.mUpdateThrottle);
            }
            return;
        }
        this.mTask.executeOnExecutor(this.mExecutor, null);
    }

    public boolean isLoadInBackgroundCanceled() {
        return this.mCancellingTask != null;
    }

    public abstract D loadInBackground();

    @Override
    protected boolean onCancelLoad() {
        block8: {
            block7: {
                if (this.mTask == null) break block7;
                if (!this.mStarted) {
                    this.mContentChanged = true;
                }
                if (this.mCancellingTask == null) break block8;
                if (this.mTask.waiting) {
                    this.mTask.waiting = false;
                    this.mHandler.removeCallbacks((Runnable)this.mTask);
                }
                this.mTask = null;
            }
            return false;
        }
        if (this.mTask.waiting) {
            this.mTask.waiting = false;
            this.mHandler.removeCallbacks((Runnable)this.mTask);
            this.mTask = null;
            return false;
        }
        boolean bl = this.mTask.cancel(false);
        if (bl) {
            this.mCancellingTask = this.mTask;
            this.cancelLoadInBackground();
        }
        this.mTask = null;
        return bl;
    }

    public void onCanceled(D d) {
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        this.cancelLoad();
        this.mTask = new LoadTask();
        this.executePendingTask();
    }

    protected D onLoadInBackground() {
        return this.loadInBackground();
    }

    final class LoadTask
    extends ModernAsyncTask<Void, Void, D>
    implements Runnable {
        private final CountDownLatch mDone = new CountDownLatch(1);
        boolean waiting;

        LoadTask() {
        }

        protected D doInBackground(Void ... object) {
            try {
                object = AsyncTaskLoader.this.onLoadInBackground();
            }
            catch (OperationCanceledException operationCanceledException) {
                if (!this.isCancelled()) {
                    throw operationCanceledException;
                }
                return null;
            }
            return (D)object;
        }

        @Override
        protected void onCancelled(D d) {
            try {
                AsyncTaskLoader.this.dispatchOnCancelled(this, d);
                return;
            }
            finally {
                this.mDone.countDown();
            }
        }

        @Override
        protected void onPostExecute(D d) {
            try {
                AsyncTaskLoader.this.dispatchOnLoadComplete(this, d);
                return;
            }
            finally {
                this.mDone.countDown();
            }
        }

        @Override
        public void run() {
            this.waiting = false;
            AsyncTaskLoader.this.executePendingTask();
        }
    }

}

