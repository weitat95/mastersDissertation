/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.GuardedBy
 */
package com.facebook.common.references;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.ResourceReleaser;
import java.util.IdentityHashMap;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

public class SharedReference<T> {
    @GuardedBy(value="itself")
    private static final Map<Object, Integer> sLiveObjects = new IdentityHashMap<Object, Integer>();
    @GuardedBy(value="this")
    private int mRefCount;
    private final ResourceReleaser<T> mResourceReleaser;
    @GuardedBy(value="this")
    private T mValue;

    public SharedReference(T t, ResourceReleaser<T> resourceReleaser) {
        this.mValue = Preconditions.checkNotNull(t);
        this.mResourceReleaser = Preconditions.checkNotNull(resourceReleaser);
        this.mRefCount = 1;
        SharedReference.addLiveReference(t);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void addLiveReference(Object object) {
        Map<Object, Integer> map = sLiveObjects;
        synchronized (map) {
            Integer n = sLiveObjects.get(object);
            if (n == null) {
                sLiveObjects.put(object, 1);
            } else {
                sLiveObjects.put(object, n + 1);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int decreaseRefCount() {
        synchronized (this) {
            this.ensureValid();
            boolean bl = this.mRefCount > 0;
            Preconditions.checkArgument(bl);
            --this.mRefCount;
            return this.mRefCount;
        }
    }

    private void ensureValid() {
        if (!SharedReference.isValid(this)) {
            throw new NullReferenceException();
        }
    }

    public static boolean isValid(SharedReference<?> sharedReference) {
        return sharedReference != null && sharedReference.isValid();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void removeLiveReference(Object object) {
        Map<Object, Integer> map = sLiveObjects;
        synchronized (map) {
            Integer n = sLiveObjects.get(object);
            if (n == null) {
                FLog.wtf("SharedReference", "No entry in sLiveObjects for value of type %s", object.getClass());
            } else if (n == 1) {
                sLiveObjects.remove(object);
            } else {
                sLiveObjects.put(object, n - 1);
            }
            return;
        }
    }

    public void addReference() {
        synchronized (this) {
            this.ensureValid();
            ++this.mRefCount;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void deleteReference() {
        if (this.decreaseRefCount() == 0) {
            T t;
            synchronized (this) {
                t = this.mValue;
                this.mValue = null;
            }
            this.mResourceReleaser.release(t);
            SharedReference.removeLiveReference(t);
        }
    }

    public T get() {
        synchronized (this) {
            T t = this.mValue;
            return t;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isValid() {
        synchronized (this) {
            int n = this.mRefCount;
            if (n <= 0) return false;
            return true;
        }
    }

    public static class NullReferenceException
    extends RuntimeException {
        public NullReferenceException() {
            super("Null shared reference");
        }
    }

}

