/*
 * Decompiled with CFR 0.147.
 */
package android.support.v4.util;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.ContainerHelpers;
import java.util.ConcurrentModificationException;
import java.util.Map;

public class SimpleArrayMap<K, V> {
    static Object[] mBaseCache;
    static int mBaseCacheSize;
    static Object[] mTwiceBaseCache;
    static int mTwiceBaseCacheSize;
    Object[] mArray;
    int[] mHashes;
    int mSize;

    public SimpleArrayMap() {
        this.mHashes = ContainerHelpers.EMPTY_INTS;
        this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        this.mSize = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public SimpleArrayMap(int n) {
        if (n == 0) {
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            this.allocArrays(n);
        }
        this.mSize = 0;
    }

    public SimpleArrayMap(SimpleArrayMap<K, V> simpleArrayMap) {
        this();
        if (simpleArrayMap != null) {
            this.putAll(simpleArrayMap);
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void allocArrays(int n) {
        if (n == 8) {
            synchronized (ArrayMap.class) {
                if (mTwiceBaseCache != null) {
                    Object[] arrobject = mTwiceBaseCache;
                    this.mArray = arrobject;
                    mTwiceBaseCache = (Object[])arrobject[0];
                    this.mHashes = (int[])arrobject[1];
                    arrobject[1] = null;
                    arrobject[0] = null;
                    --mTwiceBaseCacheSize;
                    return;
                }
            }
        } else if (n == 4) {
            synchronized (ArrayMap.class) {
                if (mBaseCache != null) {
                    Object[] arrobject = mBaseCache;
                    this.mArray = arrobject;
                    mBaseCache = (Object[])arrobject[0];
                    this.mHashes = (int[])arrobject[1];
                    arrobject[1] = null;
                    arrobject[0] = null;
                    --mBaseCacheSize;
                    return;
                }
            }
        }
        this.mHashes = new int[n];
        this.mArray = new Object[n << 1];
    }

    private static int binarySearchHashes(int[] arrn, int n, int n2) {
        try {
            n = ContainerHelpers.binarySearch(arrn, n, n2);
            return n;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new ConcurrentModificationException();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static void freeArrays(int[] arrn, Object[] arrobject, int n) {
        block11: {
            block12: {
                block9: {
                    block10: {
                        block8: {
                            if (arrn.length != 8) break block8;
                            // MONITORENTER : android.support.v4.util.ArrayMap.class
                            if (mTwiceBaseCacheSize >= 10) break block9;
                            arrobject[0] = mTwiceBaseCache;
                            arrobject[1] = arrn;
                            break block10;
                        }
                        if (arrn.length != 4) return;
                        // MONITORENTER : android.support.v4.util.ArrayMap.class
                        if (mBaseCacheSize >= 10) break block11;
                        arrobject[0] = mBaseCache;
                        arrobject[1] = arrn;
                        break block12;
                    }
                    for (n = (n << 1) - 1; n >= 2; --n) {
                        arrobject[n] = null;
                    }
                    mTwiceBaseCache = arrobject;
                    ++mTwiceBaseCacheSize;
                }
                // MONITOREXIT : android.support.v4.util.ArrayMap.class
                return;
            }
            for (n = (n << 1) - 1; n >= 2; --n) {
                arrobject[n] = null;
            }
            mBaseCache = arrobject;
            ++mBaseCacheSize;
        }
        // MONITOREXIT : android.support.v4.util.ArrayMap.class
    }

    public void clear() {
        if (this.mSize > 0) {
            int[] arrn = this.mHashes;
            Object[] arrobject = this.mArray;
            int n = this.mSize;
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
            SimpleArrayMap.freeArrays(arrn, arrobject, n);
        }
        if (this.mSize > 0) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean containsKey(Object object) {
        return this.indexOfKey(object) >= 0;
    }

    public boolean containsValue(Object object) {
        return this.indexOfValue(object) >= 0;
    }

    public void ensureCapacity(int n) {
        int n2 = this.mSize;
        if (this.mHashes.length < n) {
            int[] arrn = this.mHashes;
            Object[] arrobject = this.mArray;
            this.allocArrays(n);
            if (this.mSize > 0) {
                System.arraycopy(arrn, 0, this.mHashes, 0, n2);
                System.arraycopy(arrobject, 0, this.mArray, 0, n2 << 1);
            }
            SimpleArrayMap.freeArrays(arrn, arrobject, n2);
        }
        if (this.mSize != n2) {
            throw new ConcurrentModificationException();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SimpleArrayMap)) {
            if (!(object instanceof Map)) return false;
            object = (Map)object;
            if (this.size() != object.size()) {
                return false;
            }
        } else {
            object = (SimpleArrayMap)object;
            if (this.size() != ((SimpleArrayMap)object).size()) {
                return false;
            }
            int n = 0;
            try {
                while (n < this.mSize) {
                    K k = this.keyAt(n);
                    V v = this.valueAt(n);
                    V v2 = ((SimpleArrayMap)object).get(k);
                    if (v == null) {
                        if (v2 != null) return false;
                        if (!((SimpleArrayMap)object).containsKey(k)) {
                            return false;
                        }
                    } else {
                        boolean bl = v.equals(v2);
                        if (!bl) {
                            return false;
                        }
                    }
                    ++n;
                }
                return true;
            }
            catch (NullPointerException nullPointerException) {
                return false;
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }
        int n = 0;
        try {
            while (n < this.mSize) {
                K k = this.keyAt(n);
                V v = this.valueAt(n);
                Object v3 = object.get(k);
                if (v == null) {
                    if (v3 != null) return false;
                    if (!object.containsKey(k)) {
                        return false;
                    }
                } else {
                    boolean bl = v.equals(v3);
                    if (!bl) {
                        return false;
                    }
                }
                ++n;
            }
            return true;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public V get(Object object) {
        int n = this.indexOfKey(object);
        if (n >= 0) {
            return (V)this.mArray[(n << 1) + 1];
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int[] arrn = this.mHashes;
        Object[] arrobject = this.mArray;
        int n = 0;
        int n2 = 0;
        int n3 = 1;
        int n4 = this.mSize;
        while (n2 < n4) {
            Object object = arrobject[n3];
            int n5 = arrn[n2];
            int n6 = object == null ? 0 : object.hashCode();
            n += n6 ^ n5;
            ++n2;
            n3 += 2;
        }
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    int indexOf(Object object, int n) {
        int n2;
        int n3 = this.mSize;
        if (n3 == 0) {
            return -1;
        }
        int n4 = n2 = SimpleArrayMap.binarySearchHashes(this.mHashes, n3, n);
        if (n2 < 0) return n4;
        n4 = n2;
        if (object.equals(this.mArray[n2 << 1])) return n4;
        for (n4 = n2 + 1; n4 < n3 && this.mHashes[n4] == n; ++n4) {
            if (!object.equals(this.mArray[n4 << 1])) continue;
            return n4;
        }
        --n2;
        while (n2 >= 0) {
            if (this.mHashes[n2] != n) return ~n4;
            if (object.equals(this.mArray[n2 << 1])) {
                return n2;
            }
            --n2;
        }
        return ~n4;
    }

    public int indexOfKey(Object object) {
        if (object == null) {
            return this.indexOfNull();
        }
        return this.indexOf(object, object.hashCode());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    int indexOfNull() {
        int n;
        int n2 = this.mSize;
        if (n2 == 0) {
            return -1;
        }
        int n3 = n = SimpleArrayMap.binarySearchHashes(this.mHashes, n2, 0);
        if (n < 0) return n3;
        n3 = n;
        if (this.mArray[n << 1] == null) return n3;
        for (n3 = n + 1; n3 < n2 && this.mHashes[n3] == 0; ++n3) {
            if (this.mArray[n3 << 1] != null) continue;
            return n3;
        }
        --n;
        while (n >= 0) {
            if (this.mHashes[n] != 0) return ~n3;
            if (this.mArray[n << 1] == null) {
                return n;
            }
            --n;
        }
        return ~n3;
    }

    int indexOfValue(Object object) {
        int n = this.mSize * 2;
        Object[] arrobject = this.mArray;
        if (object == null) {
            for (int i = 1; i < n; i += 2) {
                if (arrobject[i] != null) continue;
                return i >> 1;
            }
        } else {
            for (int i = 1; i < n; i += 2) {
                if (!object.equals(arrobject[i])) continue;
                return i >> 1;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    public K keyAt(int n) {
        return (K)this.mArray[n << 1];
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public V put(K object, V v) {
        int n;
        int n2;
        void var2_3;
        int n3 = 8;
        int n4 = this.mSize;
        if (object == null) {
            n = 0;
            n2 = this.indexOfNull();
        } else {
            n = object.hashCode();
            n2 = this.indexOf(object, n);
        }
        if (n2 >= 0) {
            n2 = (n2 << 1) + 1;
            Object object2 = this.mArray[n2];
            this.mArray[n2] = var2_3;
            return (V)object2;
        }
        int n5 = ~n2;
        if (n4 >= this.mHashes.length) {
            if (n4 >= 8) {
                n2 = n4 + (n4 >> 1);
            } else {
                n2 = n3;
                if (n4 < 4) {
                    n2 = 4;
                }
            }
            int[] arrn = this.mHashes;
            Object[] arrobject = this.mArray;
            this.allocArrays(n2);
            if (n4 != this.mSize) {
                throw new ConcurrentModificationException();
            }
            if (this.mHashes.length > 0) {
                System.arraycopy(arrn, 0, this.mHashes, 0, arrn.length);
                System.arraycopy(arrobject, 0, this.mArray, 0, arrobject.length);
            }
            SimpleArrayMap.freeArrays(arrn, arrobject, n4);
        }
        if (n5 < n4) {
            System.arraycopy(this.mHashes, n5, this.mHashes, n5 + 1, n4 - n5);
            System.arraycopy(this.mArray, n5 << 1, this.mArray, n5 + 1 << 1, this.mSize - n5 << 1);
        }
        if (n4 == this.mSize && n5 < this.mHashes.length) {
            this.mHashes[n5] = n;
            this.mArray[n5 << 1] = object;
            this.mArray[(n5 << 1) + 1] = var2_3;
            ++this.mSize;
            return null;
        }
        throw new ConcurrentModificationException();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void putAll(SimpleArrayMap<? extends K, ? extends V> simpleArrayMap) {
        int n = simpleArrayMap.mSize;
        this.ensureCapacity(this.mSize + n);
        if (this.mSize == 0) {
            if (n <= 0) return;
            {
                System.arraycopy(simpleArrayMap.mHashes, 0, this.mHashes, 0, n);
                System.arraycopy(simpleArrayMap.mArray, 0, this.mArray, 0, n << 1);
                this.mSize = n;
            }
            return;
        } else {
            for (int i = 0; i < n; ++i) {
                this.put(simpleArrayMap.keyAt(i), simpleArrayMap.valueAt(i));
            }
        }
    }

    public V remove(Object object) {
        int n = this.indexOfKey(object);
        if (n >= 0) {
            return this.removeAt(n);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public V removeAt(int n) {
        int n2 = 8;
        Object object = this.mArray[(n << 1) + 1];
        int n3 = this.mSize;
        if (n3 <= 1) {
            SimpleArrayMap.freeArrays(this.mHashes, this.mArray, n3);
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            n2 = 0;
        } else {
            int n4 = n3 - 1;
            if (this.mHashes.length > 8 && this.mSize < this.mHashes.length / 3) {
                if (n3 > 8) {
                    n2 = n3 + (n3 >> 1);
                }
                int[] arrn = this.mHashes;
                Object[] arrobject = this.mArray;
                this.allocArrays(n2);
                if (n3 != this.mSize) {
                    throw new ConcurrentModificationException();
                }
                if (n > 0) {
                    System.arraycopy(arrn, 0, this.mHashes, 0, n);
                    System.arraycopy(arrobject, 0, this.mArray, 0, n << 1);
                }
                n2 = n4;
                if (n < n4) {
                    System.arraycopy(arrn, n + 1, this.mHashes, n, n4 - n);
                    System.arraycopy(arrobject, n + 1 << 1, this.mArray, n << 1, n4 - n << 1);
                    n2 = n4;
                }
            } else {
                if (n < n4) {
                    System.arraycopy(this.mHashes, n + 1, this.mHashes, n, n4 - n);
                    System.arraycopy(this.mArray, n + 1 << 1, this.mArray, n << 1, n4 - n << 1);
                }
                this.mArray[n4 << 1] = null;
                this.mArray[(n4 << 1) + 1] = null;
                n2 = n4;
            }
        }
        if (n3 != this.mSize) {
            throw new ConcurrentModificationException();
        }
        this.mSize = n2;
        return (V)object;
    }

    public V setValueAt(int n, V v) {
        n = (n << 1) + 1;
        Object object = this.mArray[n];
        this.mArray[n] = v;
        return (V)object;
    }

    public int size() {
        return this.mSize;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        if (this.isEmpty()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.mSize * 28);
        stringBuilder.append('{');
        int n = 0;
        do {
            K k;
            if (n >= this.mSize) {
                stringBuilder.append('}');
                return stringBuilder.toString();
            }
            if (n > 0) {
                stringBuilder.append(", ");
            }
            if ((k = this.keyAt(n)) != this) {
                stringBuilder.append(k);
            } else {
                stringBuilder.append("(this Map)");
            }
            stringBuilder.append('=');
            V v = this.valueAt(n);
            if (v != this) {
                stringBuilder.append(v);
            } else {
                stringBuilder.append("(this Map)");
            }
            ++n;
        } while (true);
    }

    public V valueAt(int n) {
        return (V)this.mArray[(n << 1) + 1];
    }
}

