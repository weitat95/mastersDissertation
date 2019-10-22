/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.GuardedBy
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.imagepipeline.cache;

import com.facebook.imagepipeline.cache.ValueDescriptor;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class CountingLruMap<K, V> {
    @GuardedBy(value="this")
    private final LinkedHashMap<K, V> mMap = new LinkedHashMap();
    @GuardedBy(value="this")
    private int mSizeInBytes = 0;
    private final ValueDescriptor<V> mValueDescriptor;

    public CountingLruMap(ValueDescriptor<V> valueDescriptor) {
        this.mValueDescriptor = valueDescriptor;
    }

    private int getValueSizeInBytes(V v) {
        if (v == null) {
            return 0;
        }
        return this.mValueDescriptor.getSizeInBytes(v);
    }

    @Nullable
    public V get(K object) {
        synchronized (this) {
            object = this.mMap.get(object);
            return (V)object;
        }
    }

    public int getCount() {
        synchronized (this) {
            int n = this.mMap.size();
            return n;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public K getFirstKey() {
        synchronized (this) {
            K k;
            block6: {
                boolean bl = this.mMap.isEmpty();
                if (!bl) break block6;
                k = null;
                do {
                    return k;
                    break;
                } while (true);
            }
            k = this.mMap.keySet().iterator().next();
            return k;
        }
    }

    public int getSizeInBytes() {
        synchronized (this) {
            int n = this.mSizeInBytes;
            return n;
        }
    }

    @Nullable
    public V put(K k, V v) {
        synchronized (this) {
            Object v2 = this.mMap.remove(k);
            this.mSizeInBytes -= this.getValueSizeInBytes(v2);
            this.mMap.put(k, v);
            this.mSizeInBytes += this.getValueSizeInBytes(v);
            return v2;
        }
    }

    @Nullable
    public V remove(K object) {
        synchronized (this) {
            object = this.mMap.remove(object);
            this.mSizeInBytes -= this.getValueSizeInBytes(object);
            return (V)object;
        }
    }
}

