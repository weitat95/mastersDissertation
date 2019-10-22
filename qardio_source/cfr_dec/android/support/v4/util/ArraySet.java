/*
 * Decompiled with CFR 0.147.
 */
package android.support.v4.util;

import android.support.v4.util.ContainerHelpers;
import android.support.v4.util.MapCollections;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class ArraySet<E>
implements Collection<E>,
Set<E> {
    private static final int[] INT = new int[0];
    private static final Object[] OBJECT = new Object[0];
    static Object[] sBaseCache;
    static int sBaseCacheSize;
    static Object[] sTwiceBaseCache;
    static int sTwiceBaseCacheSize;
    Object[] mArray;
    MapCollections<E, E> mCollections;
    int[] mHashes;
    final boolean mIdentityHashCode;
    int mSize;

    public ArraySet() {
        this(0, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public ArraySet(int n, boolean bl) {
        this.mIdentityHashCode = bl;
        if (n == 0) {
            this.mHashes = INT;
            this.mArray = OBJECT;
        } else {
            this.allocArrays(n);
        }
        this.mSize = 0;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void allocArrays(int n) {
        if (n == 8) {
            synchronized (ArraySet.class) {
                if (sTwiceBaseCache != null) {
                    Object[] arrobject = sTwiceBaseCache;
                    this.mArray = arrobject;
                    sTwiceBaseCache = (Object[])arrobject[0];
                    this.mHashes = (int[])arrobject[1];
                    arrobject[1] = null;
                    arrobject[0] = null;
                    --sTwiceBaseCacheSize;
                    return;
                }
            }
        } else if (n == 4) {
            synchronized (ArraySet.class) {
                if (sBaseCache != null) {
                    Object[] arrobject = sBaseCache;
                    this.mArray = arrobject;
                    sBaseCache = (Object[])arrobject[0];
                    this.mHashes = (int[])arrobject[1];
                    arrobject[1] = null;
                    arrobject[0] = null;
                    --sBaseCacheSize;
                    return;
                }
            }
        }
        this.mHashes = new int[n];
        this.mArray = new Object[n];
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
                            // MONITORENTER : android.support.v4.util.ArraySet.class
                            if (sTwiceBaseCacheSize >= 10) break block9;
                            arrobject[0] = sTwiceBaseCache;
                            arrobject[1] = arrn;
                            --n;
                            break block10;
                        }
                        if (arrn.length != 4) return;
                        // MONITORENTER : android.support.v4.util.ArraySet.class
                        if (sBaseCacheSize >= 10) break block11;
                        arrobject[0] = sBaseCache;
                        arrobject[1] = arrn;
                        --n;
                        break block12;
                    }
                    while (n >= 2) {
                        arrobject[n] = null;
                        --n;
                    }
                    sTwiceBaseCache = arrobject;
                    ++sTwiceBaseCacheSize;
                }
                // MONITOREXIT : android.support.v4.util.ArraySet.class
                return;
            }
            while (n >= 2) {
                arrobject[n] = null;
                --n;
            }
            sBaseCache = arrobject;
            ++sBaseCacheSize;
        }
        // MONITOREXIT : android.support.v4.util.ArraySet.class
    }

    private MapCollections<E, E> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new MapCollections<E, E>(){

                @Override
                protected void colClear() {
                    ArraySet.this.clear();
                }

                @Override
                protected Object colGetEntry(int n, int n2) {
                    return ArraySet.this.mArray[n];
                }

                @Override
                protected Map<E, E> colGetMap() {
                    throw new UnsupportedOperationException("not a map");
                }

                @Override
                protected int colGetSize() {
                    return ArraySet.this.mSize;
                }

                @Override
                protected int colIndexOfKey(Object object) {
                    return ArraySet.this.indexOf(object);
                }

                @Override
                protected int colIndexOfValue(Object object) {
                    return ArraySet.this.indexOf(object);
                }

                @Override
                protected void colPut(E e, E e2) {
                    ArraySet.this.add(e);
                }

                @Override
                protected void colRemoveAt(int n) {
                    ArraySet.this.removeAt(n);
                }

                @Override
                protected E colSetValue(int n, E e) {
                    throw new UnsupportedOperationException("not a map");
                }
            };
        }
        return this.mCollections;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int indexOf(Object object, int n) {
        int n2;
        int n3 = this.mSize;
        if (n3 == 0) {
            return -1;
        }
        int n4 = n2 = ContainerHelpers.binarySearch(this.mHashes, n3, n);
        if (n2 < 0) return n4;
        n4 = n2;
        if (object.equals(this.mArray[n2])) return n4;
        for (n4 = n2 + 1; n4 < n3 && this.mHashes[n4] == n; ++n4) {
            if (!object.equals(this.mArray[n4])) continue;
            return n4;
        }
        --n2;
        while (n2 >= 0) {
            if (this.mHashes[n2] != n) return ~n4;
            if (object.equals(this.mArray[n2])) {
                return n2;
            }
            --n2;
        }
        return ~n4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int indexOfNull() {
        int n;
        int n2 = this.mSize;
        if (n2 == 0) {
            return -1;
        }
        int n3 = n = ContainerHelpers.binarySearch(this.mHashes, n2, 0);
        if (n < 0) return n3;
        n3 = n;
        if (this.mArray[n] == null) return n3;
        for (n3 = n + 1; n3 < n2 && this.mHashes[n3] == 0; ++n3) {
            if (this.mArray[n3] != null) continue;
            return n3;
        }
        --n;
        while (n >= 0) {
            if (this.mHashes[n] != 0) return ~n3;
            if (this.mArray[n] == null) {
                return n;
            }
            --n;
        }
        return ~n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean add(E e) {
        int n;
        int n2;
        int n3;
        int n4 = 8;
        if (e == null) {
            n = 0;
            n3 = this.indexOfNull();
        } else {
            n3 = this.mIdentityHashCode ? System.identityHashCode(e) : e.hashCode();
            n2 = this.indexOf(e, n3);
            n = n3;
            n3 = n2;
        }
        if (n3 >= 0) {
            return false;
        }
        n2 = ~n3;
        if (this.mSize >= this.mHashes.length) {
            if (this.mSize >= 8) {
                n3 = this.mSize + (this.mSize >> 1);
            } else {
                n3 = n4;
                if (this.mSize < 4) {
                    n3 = 4;
                }
            }
            int[] arrn = this.mHashes;
            Object[] arrobject = this.mArray;
            this.allocArrays(n3);
            if (this.mHashes.length > 0) {
                System.arraycopy(arrn, 0, this.mHashes, 0, arrn.length);
                System.arraycopy(arrobject, 0, this.mArray, 0, arrobject.length);
            }
            ArraySet.freeArrays(arrn, arrobject, this.mSize);
        }
        if (n2 < this.mSize) {
            System.arraycopy(this.mHashes, n2, this.mHashes, n2 + 1, this.mSize - n2);
            System.arraycopy(this.mArray, n2, this.mArray, n2 + 1, this.mSize - n2);
        }
        this.mHashes[n2] = n;
        this.mArray[n2] = e;
        ++this.mSize;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> object) {
        this.ensureCapacity(this.mSize + object.size());
        boolean bl = false;
        object = object.iterator();
        while (object.hasNext()) {
            bl |= this.add(object.next());
        }
        return bl;
    }

    @Override
    public void clear() {
        if (this.mSize != 0) {
            ArraySet.freeArrays(this.mHashes, this.mArray, this.mSize);
            this.mHashes = INT;
            this.mArray = OBJECT;
            this.mSize = 0;
        }
    }

    @Override
    public boolean contains(Object object) {
        return this.indexOf(object) >= 0;
    }

    @Override
    public boolean containsAll(Collection<?> object) {
        object = object.iterator();
        while (object.hasNext()) {
            if (this.contains(object.next())) continue;
            return false;
        }
        return true;
    }

    public void ensureCapacity(int n) {
        if (this.mHashes.length < n) {
            int[] arrn = this.mHashes;
            Object[] arrobject = this.mArray;
            this.allocArrays(n);
            if (this.mSize > 0) {
                System.arraycopy(arrn, 0, this.mHashes, 0, this.mSize);
                System.arraycopy(arrobject, 0, this.mArray, 0, this.mSize);
            }
            ArraySet.freeArrays(arrn, arrobject, this.mSize);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        {
            if (!(object instanceof Set)) {
                return false;
            }
            object = (Set)object;
            if (this.size() != object.size()) {
                return false;
            }
            try {
                for (int i = 0; i < this.mSize; ++i) {
                    boolean bl = object.contains(this.valueAt(i));
                    if (bl) continue;
                    return false;
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
    }

    @Override
    public int hashCode() {
        int[] arrn = this.mHashes;
        int n = 0;
        int n2 = this.mSize;
        for (int i = 0; i < n2; ++i) {
            n += arrn[i];
        }
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int indexOf(Object object) {
        int n;
        if (object == null) {
            return this.indexOfNull();
        }
        if (this.mIdentityHashCode) {
            n = System.identityHashCode(object);
            do {
                return this.indexOf(object, n);
                break;
            } while (true);
        }
        n = object.hashCode();
        return this.indexOf(object, n);
    }

    @Override
    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return this.getCollection().getKeySet().iterator();
    }

    @Override
    public boolean remove(Object object) {
        int n = this.indexOf(object);
        if (n >= 0) {
            this.removeAt(n);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> object) {
        boolean bl = false;
        object = object.iterator();
        while (object.hasNext()) {
            bl |= this.remove(object.next());
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public E removeAt(int n) {
        int n2 = 8;
        Object object = this.mArray[n];
        if (this.mSize <= 1) {
            ArraySet.freeArrays(this.mHashes, this.mArray, this.mSize);
            this.mHashes = INT;
            this.mArray = OBJECT;
            this.mSize = 0;
            return (E)object;
        }
        if (this.mHashes.length > 8 && this.mSize < this.mHashes.length / 3) {
            if (this.mSize > 8) {
                n2 = this.mSize + (this.mSize >> 1);
            }
            int[] arrn = this.mHashes;
            Object[] arrobject = this.mArray;
            this.allocArrays(n2);
            --this.mSize;
            if (n > 0) {
                System.arraycopy(arrn, 0, this.mHashes, 0, n);
                System.arraycopy(arrobject, 0, this.mArray, 0, n);
            }
            if (n >= this.mSize) return (E)object;
            {
                System.arraycopy(arrn, n + 1, this.mHashes, n, this.mSize - n);
                System.arraycopy(arrobject, n + 1, this.mArray, n, this.mSize - n);
                return (E)object;
            }
        }
        --this.mSize;
        if (n < this.mSize) {
            System.arraycopy(this.mHashes, n + 1, this.mHashes, n, this.mSize - n);
            System.arraycopy(this.mArray, n + 1, this.mArray, n, this.mSize - n);
        }
        this.mArray[this.mSize] = null;
        return (E)object;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean bl = false;
        for (int i = this.mSize - 1; i >= 0; --i) {
            if (collection.contains(this.mArray[i])) continue;
            this.removeAt(i);
            bl = true;
        }
        return bl;
    }

    @Override
    public int size() {
        return this.mSize;
    }

    @Override
    public Object[] toArray() {
        Object[] arrobject = new Object[this.mSize];
        System.arraycopy(this.mArray, 0, arrobject, 0, this.mSize);
        return arrobject;
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        Object[] arrobject = arrT;
        if (arrT.length < this.mSize) {
            arrobject = (Object[])Array.newInstance(arrT.getClass().getComponentType(), this.mSize);
        }
        System.arraycopy(this.mArray, 0, arrobject, 0, this.mSize);
        if (arrobject.length > this.mSize) {
            arrobject[this.mSize] = null;
        }
        return arrobject;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        if (this.isEmpty()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.mSize * 14);
        stringBuilder.append('{');
        int n = 0;
        do {
            E e;
            if (n >= this.mSize) {
                stringBuilder.append('}');
                return stringBuilder.toString();
            }
            if (n > 0) {
                stringBuilder.append(", ");
            }
            if ((e = this.valueAt(n)) != this) {
                stringBuilder.append(e);
            } else {
                stringBuilder.append("(this Set)");
            }
            ++n;
        } while (true);
    }

    public E valueAt(int n) {
        return (E)this.mArray[n];
    }

}

