/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Pair
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.GuardedBy
 */
package com.facebook.datasource;

import android.util.Pair;
import com.facebook.common.internal.Preconditions;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public abstract class AbstractDataSource<T>
implements DataSource<T> {
    @GuardedBy(value="this")
    private DataSourceStatus mDataSourceStatus = DataSourceStatus.IN_PROGRESS;
    @GuardedBy(value="this")
    private Throwable mFailureThrowable = null;
    @GuardedBy(value="this")
    private boolean mIsClosed = false;
    @GuardedBy(value="this")
    private float mProgress = 0.0f;
    @Nullable
    @GuardedBy(value="this")
    private T mResult = null;
    private final ConcurrentLinkedQueue<Pair<DataSubscriber<T>, Executor>> mSubscribers = new ConcurrentLinkedQueue();

    protected AbstractDataSource() {
    }

    private void notifyDataSubscriber(final DataSubscriber<T> dataSubscriber, Executor executor, final boolean bl, final boolean bl2) {
        executor.execute(new Runnable(){

            @Override
            public void run() {
                if (bl) {
                    dataSubscriber.onFailure(AbstractDataSource.this);
                    return;
                }
                if (bl2) {
                    dataSubscriber.onCancellation(AbstractDataSource.this);
                    return;
                }
                dataSubscriber.onNewResult(AbstractDataSource.this);
            }
        });
    }

    private void notifyDataSubscribers() {
        boolean bl = this.hasFailed();
        boolean bl2 = this.wasCancelled();
        for (Pair<DataSubscriber<T>, Executor> pair : this.mSubscribers) {
            this.notifyDataSubscriber((DataSubscriber)pair.first, (Executor)pair.second, bl, bl2);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean setFailureInternal(Throwable throwable) {
        synchronized (this) {
            block4: {
                if (this.mIsClosed) return false;
                DataSourceStatus dataSourceStatus = this.mDataSourceStatus;
                DataSourceStatus dataSourceStatus2 = DataSourceStatus.IN_PROGRESS;
                if (dataSourceStatus == dataSourceStatus2) break block4;
                return false;
            }
            this.mDataSourceStatus = DataSourceStatus.FAILURE;
            this.mFailureThrowable = throwable;
            return true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean setProgressInternal(float f) {
        boolean bl = false;
        synchronized (this) {
            boolean bl2;
            block4: {
                bl2 = bl;
                if (this.mIsClosed) return bl2;
                DataSourceStatus dataSourceStatus = this.mDataSourceStatus;
                DataSourceStatus dataSourceStatus2 = DataSourceStatus.IN_PROGRESS;
                if (dataSourceStatus == dataSourceStatus2) break block4;
                return bl;
            }
            bl2 = bl;
            if (f < this.mProgress) return bl2;
            this.mProgress = f;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private boolean setResultInternal(@Nullable T t, boolean bl) {
        T t2;
        T t3;
        T t4;
        block13: {
            block12: {
                t2 = null;
                t4 = null;
                t3 = null;
                t4 = t2;
                if (this.mIsClosed) break block12;
                t4 = t2;
                if (this.mDataSourceStatus == DataSourceStatus.IN_PROGRESS) break block13;
            }
            boolean bl2 = false;
            t4 = t;
            // MONITOREXIT : this
            bl = bl2;
            if (t == null) return bl;
            this.closeResult(t);
            return bl2;
        }
        if (bl) {
            t4 = t2;
            this.mDataSourceStatus = DataSourceStatus.SUCCESS;
            t4 = t2;
            this.mProgress = 1.0f;
        }
        t4 = t2;
        if (this.mResult != t) {
            t4 = t2;
            t4 = t3 = (T)this.mResult;
            this.mResult = t;
        }
        bl = true;
        t4 = t3;
        // MONITOREXIT : this
        if (t3 == null) return bl;
        this.closeResult(t3);
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean wasCancelled() {
        synchronized (this) {
            if (!this.isClosed()) return false;
            boolean bl = this.isFinished();
            if (bl) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public boolean close() {
        // MONITORENTER : this
        if (this.mIsClosed) {
            // MONITOREXIT : this
            return false;
        }
        this.mIsClosed = true;
        T t = this.mResult;
        this.mResult = null;
        // MONITOREXIT : this
        if (t != null) {
            this.closeResult(t);
        }
        if (!this.isFinished()) {
            this.notifyDataSubscribers();
        }
        // MONITORENTER : this
        this.mSubscribers.clear();
        // MONITOREXIT : this
        return true;
    }

    protected void closeResult(@Nullable T t) {
    }

    @Nullable
    @Override
    public Throwable getFailureCause() {
        synchronized (this) {
            Throwable throwable = this.mFailureThrowable;
            return throwable;
        }
    }

    @Override
    public float getProgress() {
        synchronized (this) {
            float f = this.mProgress;
            return f;
        }
    }

    @Nullable
    @Override
    public T getResult() {
        synchronized (this) {
            T t = this.mResult;
            return t;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean hasFailed() {
        synchronized (this) {
            DataSourceStatus dataSourceStatus = this.mDataSourceStatus;
            DataSourceStatus dataSourceStatus2 = DataSourceStatus.FAILURE;
            if (dataSourceStatus != dataSourceStatus2) return false;
            return true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean hasResult() {
        synchronized (this) {
            T t = this.mResult;
            if (t == null) return false;
            return true;
        }
    }

    public boolean isClosed() {
        synchronized (this) {
            boolean bl = this.mIsClosed;
            return bl;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean isFinished() {
        synchronized (this) {
            DataSourceStatus dataSourceStatus = this.mDataSourceStatus;
            DataSourceStatus dataSourceStatus2 = DataSourceStatus.IN_PROGRESS;
            if (dataSourceStatus == dataSourceStatus2) return false;
            return true;
        }
    }

    protected void notifyProgressUpdate() {
        for (Pair<DataSubscriber<T>, Executor> pair : this.mSubscribers) {
            final DataSubscriber dataSubscriber = (DataSubscriber)pair.first;
            ((Executor)pair.second).execute(new Runnable(){

                @Override
                public void run() {
                    dataSubscriber.onProgressUpdate(AbstractDataSource.this);
                }
            });
        }
    }

    protected boolean setFailure(Throwable throwable) {
        boolean bl = this.setFailureInternal(throwable);
        if (bl) {
            this.notifyDataSubscribers();
        }
        return bl;
    }

    protected boolean setProgress(float f) {
        boolean bl = this.setProgressInternal(f);
        if (bl) {
            this.notifyProgressUpdate();
        }
        return bl;
    }

    protected boolean setResult(@Nullable T t, boolean bl) {
        if (bl = this.setResultInternal(t, bl)) {
            this.notifyDataSubscribers();
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void subscribe(DataSubscriber<T> dataSubscriber, Executor executor) {
        boolean bl;
        Preconditions.checkNotNull(dataSubscriber);
        Preconditions.checkNotNull(executor);
        synchronized (this) {
            if (this.mIsClosed) {
                return;
            }
            if (this.mDataSourceStatus == DataSourceStatus.IN_PROGRESS) {
                this.mSubscribers.add(Pair.create(dataSubscriber, (Object)executor));
            }
            if (!this.hasResult() && !this.isFinished()) {
                if (!this.wasCancelled()) return;
            }
            bl = true;
        }
        if (!bl) return;
        this.notifyDataSubscriber(dataSubscriber, executor, this.hasFailed(), this.wasCancelled());
    }

    private static enum DataSourceStatus {
        IN_PROGRESS,
        SUCCESS,
        FAILURE;

    }

}

