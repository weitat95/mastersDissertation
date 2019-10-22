/*
 * Decompiled with CFR 0.147.
 */
package android.support.v4.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = n;
        this.map = new LinkedHashMap(0, 0.75f, true);
    }

    private int safeSizeOf(K k, V v) {
        int n = this.sizeOf(k, v);
        if (n < 0) {
            throw new IllegalStateException("Negative size: " + k + "=" + v);
        }
        return n;
    }

    protected V create(K k) {
        return null;
    }

    protected void entryRemoved(boolean bl, K k, V v, V v2) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final V get(K k) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        // MONITORENTER : this
        V v = this.map.get(k);
        if (v != null) {
            ++this.hitCount;
            // MONITOREXIT : this
            return v;
        }
        ++this.missCount;
        // MONITOREXIT : this
        v = this.create(k);
        if (v == null) {
            return null;
        }
        // MONITORENTER : this
        ++this.createCount;
        V v2 = this.map.put(k, v);
        if (v2 != null) {
            this.map.put(k, v2);
        } else {
            this.size += this.safeSizeOf(k, v);
        }
        // MONITOREXIT : this
        if (v2 != null) {
            this.entryRemoved(false, k, v, v2);
            return v2;
        }
        this.trimToSize(this.maxSize);
        return v;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final V put(K k, V v) {
        if (k == null) throw new NullPointerException("key == null || value == null");
        if (v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        // MONITORENTER : this
        ++this.putCount;
        this.size += this.safeSizeOf(k, v);
        V v2 = this.map.put(k, v);
        if (v2 != null) {
            this.size -= this.safeSizeOf(k, v2);
        }
        // MONITOREXIT : this
        if (v2 != null) {
            this.entryRemoved(false, k, v2, v);
        }
        this.trimToSize(this.maxSize);
        return v2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final V remove(K k) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        // MONITORENTER : this
        Object v = this.map.remove(k);
        if (v != null) {
            this.size -= this.safeSizeOf(k, v);
        }
        // MONITOREXIT : this
        if (v == null) return v;
        this.entryRemoved(false, k, v, null);
        return v;
    }

    protected int sizeOf(K k, V v) {
        return 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final String toString() {
        int n = 0;
        synchronized (this) {
            int n2 = this.hitCount + this.missCount;
            if (n2 == 0) return String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", this.maxSize, this.hitCount, this.missCount, n);
            n = this.hitCount * 100 / n2;
            return String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", this.maxSize, this.hitCount, this.missCount, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void trimToSize(int n) {
        do {
            Map.Entry<K, V> entry;
            K k;
            synchronized (this) {
                if (this.size < 0 || this.map.isEmpty() && this.size != 0) {
                    throw new IllegalStateException(this.getClass().getName() + ".sizeOf() is reporting inconsistent results!");
                }
                if (this.size <= n || this.map.isEmpty()) {
                    return;
                }
                entry = this.map.entrySet().iterator().next();
                k = entry.getKey();
                entry = entry.getValue();
                this.map.remove(k);
                this.size -= this.safeSizeOf(k, entry);
                ++this.evictionCount;
            }
            this.entryRemoved(true, k, entry, null);
        } while (true);
    }
}

