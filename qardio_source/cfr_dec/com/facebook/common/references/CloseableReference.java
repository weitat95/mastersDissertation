/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.GuardedBy
 */
package com.facebook.common.references;

import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.common.references.SharedReference;
import java.io.Closeable;
import java.io.IOException;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public final class CloseableReference<T>
implements Closeable,
Cloneable {
    private static final ResourceReleaser<Closeable> DEFAULT_CLOSEABLE_RELEASER;
    private static Class<CloseableReference> TAG;
    @GuardedBy(value="this")
    private boolean mIsClosed = false;
    private final SharedReference<T> mSharedReference;

    static {
        TAG = CloseableReference.class;
        DEFAULT_CLOSEABLE_RELEASER = new ResourceReleaser<Closeable>(){

            @Override
            public void release(Closeable closeable) {
                try {
                    Closeables.close(closeable, true);
                    return;
                }
                catch (IOException iOException) {
                    return;
                }
            }
        };
    }

    private CloseableReference(SharedReference<T> sharedReference) {
        this.mSharedReference = Preconditions.checkNotNull(sharedReference);
        sharedReference.addReference();
    }

    private CloseableReference(T t, ResourceReleaser<T> resourceReleaser) {
        this.mSharedReference = new SharedReference<T>(t, resourceReleaser);
    }

    @Nullable
    public static <T> CloseableReference<T> cloneOrNull(@Nullable CloseableReference<T> closeableReference) {
        if (closeableReference != null) {
            return closeableReference.cloneOrNull();
        }
        return null;
    }

    public static void closeSafely(@Nullable CloseableReference<?> closeableReference) {
        if (closeableReference != null) {
            closeableReference.close();
        }
    }

    public static boolean isValid(@Nullable CloseableReference<?> closeableReference) {
        return closeableReference != null && closeableReference.isValid();
    }

    @Nullable
    public static <T extends Closeable> CloseableReference<T> of(@Nullable T t) {
        if (t == null) {
            return null;
        }
        return new CloseableReference<Closeable>(t, DEFAULT_CLOSEABLE_RELEASER);
    }

    @Nullable
    public static <T> CloseableReference<T> of(@Nullable T t, ResourceReleaser<T> resourceReleaser) {
        if (t == null) {
            return null;
        }
        return new CloseableReference<T>(t, resourceReleaser);
    }

    public CloseableReference<T> clone() {
        synchronized (this) {
            Preconditions.checkState(this.isValid());
            CloseableReference<T> closeableReference = new CloseableReference<T>(this.mSharedReference);
            return closeableReference;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public CloseableReference<T> cloneOrNull() {
        synchronized (this) {
            if (!this.isValid()) return null;
            return new CloseableReference<T>(this.mSharedReference);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        synchronized (this) {
            if (this.mIsClosed) {
                return;
            }
            this.mIsClosed = true;
        }
        this.mSharedReference.deleteReference();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void finalize() throws Throwable {
        block9: {
            synchronized (this) {
                if (!this.mIsClosed) break block9;
            }
            super.finalize();
            return;
        }
        try {
            FLog.w(TAG, "Finalized without closing: %x %x (type = %s)", System.identityHashCode(this), System.identityHashCode(this.mSharedReference), this.mSharedReference.get().getClass().getSimpleName());
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public T get() {
        synchronized (this) {
            boolean bl = !this.mIsClosed;
            Preconditions.checkState(bl);
            T t = this.mSharedReference.get();
            return t;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getValueHash() {
        synchronized (this) {
            if (!this.isValid()) return 0;
            return System.identityHashCode(this.mSharedReference.get());
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isValid() {
        synchronized (this) {
            boolean bl = this.mIsClosed;
            if (bl) return false;
            return true;
        }
    }

}

