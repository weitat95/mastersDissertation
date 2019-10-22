/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.util;

import io.reactivex.internal.util.Pow2;

public final class OpenHashSet<T> {
    T[] keys;
    final float loadFactor;
    int mask;
    int maxSize;
    int size;

    public OpenHashSet() {
        this(16, 0.75f);
    }

    public OpenHashSet(int n, float f) {
        this.loadFactor = f;
        n = Pow2.roundToPowerOfTwo(n);
        this.mask = n - 1;
        this.maxSize = (int)((float)n * f);
        this.keys = new Object[n];
    }

    static int mix(int n) {
        return (n *= -1640531527) >>> 16 ^ n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean add(T var1_1) {
        var5_2 = this.keys;
        var4_3 = this.mask;
        var2_4 = OpenHashSet.mix(var1_1.hashCode()) & var4_3;
        var6_5 = var5_2[var2_4];
        var3_6 = var2_4;
        if (var6_5 == null) ** GOTO lbl12
        if (var6_5.equals(var1_1)) {
            return false;
        }
        do {
            if ((var6_5 = var5_2[var2_4 = var2_4 + 1 & var4_3]) != null) continue;
            var3_6 = var2_4;
lbl12:
            // 2 sources
            var5_2[var3_6] = var1_1;
            this.size = var2_4 = this.size + 1;
            if (var2_4 < this.maxSize) return true;
            this.rehash();
            return true;
        } while (!var6_5.equals(var1_1));
        return false;
    }

    public Object[] keys() {
        return this.keys;
    }

    void rehash() {
        T[] arrT = this.keys;
        int n = arrT.length;
        int n2 = n << 1;
        int n3 = n2 - 1;
        Object[] arrobject = new Object[n2];
        for (int i = this.size; i != 0; --i) {
            int n4 = n;
            do {
                n4 = n = n4 - 1;
            } while (arrT[n] == null);
            int n5 = n4 = OpenHashSet.mix(arrT[n].hashCode()) & n3;
            if (arrobject[n4] != null) {
                do {
                    n4 = n5 = n4 + 1 & n3;
                } while (arrobject[n5] != null);
            }
            arrobject[n5] = arrT[n];
        }
        this.mask = n3;
        this.maxSize = (int)((float)n2 * this.loadFactor);
        this.keys = arrobject;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean remove(T t) {
        T[] arrT = this.keys;
        int n = this.mask;
        int n2 = OpenHashSet.mix(t.hashCode()) & n;
        T t2 = arrT[n2];
        if (t2 == null) {
            return false;
        }
        int n3 = n2;
        if (t2.equals(t)) {
            return this.removeEntry(n2, arrT, n);
        }
        do {
            if ((t2 = arrT[n2 = n3 + 1 & n]) == null) return false;
            n3 = n2;
        } while (!t2.equals(t));
        return this.removeEntry(n2, arrT, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean removeEntry(int n, T[] arrT, int n2) {
        --this.size;
        block0: do {
            int n3 = n;
            n = n3 + 1 & n2;
            do {
                T t;
                if ((t = arrT[n]) == null) {
                    arrT[n3] = null;
                    return true;
                }
                int n4 = OpenHashSet.mix(t.hashCode()) & n2;
                if (n3 <= n ? n3 >= n4 || n4 > n : n3 >= n4 && n4 > n) {
                    arrT[n3] = t;
                    continue block0;
                }
                n = n + 1 & n2;
            } while (true);
            break;
        } while (true);
    }
}

