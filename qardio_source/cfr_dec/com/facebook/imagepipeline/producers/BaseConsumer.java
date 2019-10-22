/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.producers;

import com.facebook.common.logging.FLog;
import com.facebook.imagepipeline.producers.Consumer;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public abstract class BaseConsumer<T>
implements Consumer<T> {
    private boolean mIsFinished = false;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onCancellation() {
        synchronized (this) {
            boolean bl = this.mIsFinished;
            if (!bl) {
                this.mIsFinished = true;
                try {
                    this.onCancellationImpl();
                }
                catch (Exception exception) {
                    this.onUnhandledException(exception);
                }
            }
            return;
        }
    }

    protected abstract void onCancellationImpl();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onFailure(Throwable throwable) {
        synchronized (this) {
            boolean bl = this.mIsFinished;
            if (!bl) {
                this.mIsFinished = true;
                try {
                    this.onFailureImpl(throwable);
                }
                catch (Exception exception) {
                    this.onUnhandledException(exception);
                }
            }
            return;
        }
    }

    protected abstract void onFailureImpl(Throwable var1);

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onNewResult(@Nullable T t, boolean bl) {
        synchronized (this) {
            boolean bl2 = this.mIsFinished;
            if (!bl2) {
                void var2_3;
                this.mIsFinished = var2_3;
                try {
                    this.onNewResultImpl(t, (boolean)var2_3);
                }
                catch (Exception exception) {
                    this.onUnhandledException(exception);
                }
            }
            return;
        }
    }

    protected abstract void onNewResultImpl(T var1, boolean var2);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void onProgressUpdate(float f) {
        synchronized (this) {
            block8: {
                boolean bl = this.mIsFinished;
                if (!bl) break block8;
                do {
                    return;
                    break;
                } while (true);
            }
            try {
                this.onProgressUpdateImpl(f);
                return;
            }
            catch (Exception exception) {
                this.onUnhandledException(exception);
                return;
            }
            finally {
            }
        }
    }

    protected void onProgressUpdateImpl(float f) {
    }

    protected void onUnhandledException(Exception exception) {
        FLog.wtf(this.getClass(), "unhandled exception", exception);
    }
}

