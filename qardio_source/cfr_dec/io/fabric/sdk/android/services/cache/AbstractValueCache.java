/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.cache;

import android.content.Context;
import io.fabric.sdk.android.services.cache.ValueCache;
import io.fabric.sdk.android.services.cache.ValueLoader;

public abstract class AbstractValueCache<T>
implements ValueCache<T> {
    private final ValueCache<T> childCache;

    public AbstractValueCache(ValueCache<T> valueCache) {
        this.childCache = valueCache;
    }

    private void cache(Context context, T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        this.cacheValue(context, t);
    }

    protected abstract void cacheValue(Context var1, T var2);

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final T get(Context context, ValueLoader<T> valueLoader) throws Exception {
        synchronized (this) {
            void var3_9;
            T t;
            T t2 = t = this.getCached(context);
            if (t == null) {
                void var2_2;
                void var2_4;
                if (this.childCache != null) {
                    valueLoader = this.childCache.get(context, (ValueLoader<T>)var2_2);
                } else {
                    T t3 = var2_2.load(context);
                }
                this.cache(context, var2_4);
                void var3_8 = var2_4;
            }
            return var3_9;
        }
    }

    protected abstract T getCached(Context var1);
}

