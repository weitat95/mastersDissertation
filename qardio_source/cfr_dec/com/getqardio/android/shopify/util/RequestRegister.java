/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.util;

import io.reactivex.disposables.Disposable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public final class RequestRegister<T>
implements Disposable {
    private final Map<T, Disposable> disposablePool = new ConcurrentHashMap<T, Disposable>();
    private final AtomicBoolean disposed = new AtomicBoolean();

    /*
     * Enabled aggressive block sorting
     */
    public void add(T object, Disposable disposable) {
        if (this.disposed.get()) {
            disposable.dispose();
            return;
        } else {
            if ((object = this.disposablePool.put(object, disposable)) == null) return;
            {
                object.dispose();
                return;
            }
        }
    }

    public void delete(T object) {
        if ((object = this.disposablePool.remove(object)) != null) {
            object.dispose();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void deleteAll() {
        Map<T, Disposable> map = this.disposablePool;
        synchronized (map) {
            Iterator<Disposable> iterator = this.disposablePool.values().iterator();
            do {
                if (!iterator.hasNext()) {
                    this.disposablePool.clear();
                    return;
                }
                iterator.next().dispose();
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void dispose() {
        if (this.disposed.compareAndSet(false, true)) {
            Map<T, Disposable> map = this.disposablePool;
            synchronized (map) {
                Iterator<Disposable> iterator = this.disposablePool.values().iterator();
                while (iterator.hasNext()) {
                    iterator.next().dispose();
                }
                this.disposablePool.clear();
            }
        }
    }

    @Override
    public boolean isDisposed() {
        return this.disposed.get();
    }
}

