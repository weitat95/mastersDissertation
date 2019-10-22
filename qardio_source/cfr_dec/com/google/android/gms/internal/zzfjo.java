/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfjp;

public final class zzfjo
implements Cloneable {
    private static final zzfjp zzpne = new zzfjp();
    private int mSize;
    private boolean zzpnf = false;
    private int[] zzpng;
    private zzfjp[] zzpnh;

    zzfjo() {
        this(10);
    }

    private zzfjo(int n) {
        n = zzfjo.idealIntArraySize(n);
        this.zzpng = new int[n];
        this.zzpnh = new zzfjp[n];
        this.mSize = 0;
    }

    private static int idealIntArraySize(int n) {
        int n2 = n << 2;
        n = 4;
        do {
            block4: {
                int n3;
                block3: {
                    n3 = n2;
                    if (n >= 32) break block3;
                    if (n2 > (1 << n) - 12) break block4;
                    n3 = (1 << n) - 12;
                }
                return n3 / 4;
            }
            ++n;
        } while (true);
    }

    private final int zzml(int n) {
        int n2;
        block3: {
            n2 = this.mSize;
            int n3 = 0;
            --n2;
            while (n3 <= n2) {
                int n4 = n3 + n2 >>> 1;
                int n5 = this.zzpng[n4];
                if (n5 < n) {
                    n3 = n4 + 1;
                    continue;
                }
                n2 = n4;
                if (n5 > n) {
                    n2 = n4 - 1;
                    continue;
                }
                break block3;
            }
            n2 = ~n3;
        }
        return n2;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        int n = this.mSize;
        zzfjo zzfjo2 = new zzfjo(n);
        System.arraycopy(this.zzpng, 0, zzfjo2.zzpng, 0, n);
        for (int i = 0; i < n; ++i) {
            if (this.zzpnh[i] == null) continue;
            zzfjo2.zzpnh[i] = (zzfjp)this.zzpnh[i].clone();
        }
        zzfjo2.mSize = n;
        return zzfjo2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        int n;
        if (object == this) return true;
        if (!(object instanceof zzfjo)) {
            return false;
        }
        object = (zzfjo)object;
        if (this.mSize != object.mSize) {
            return false;
        }
        int[] arrn = this.zzpng;
        int[] arrn2 = object.zzpng;
        int n2 = this.mSize;
        for (n = 0; n < n2; ++n) {
            if (arrn[n] == arrn2[n]) continue;
            return false;
        }
        n = 1;
        if (n == 0) return false;
        zzfjp[] arrzzfjp = this.zzpnh;
        object = object.zzpnh;
        n2 = this.mSize;
        n = 0;
        while (n < n2) {
            if (!arrzzfjp[n].equals(object[n])) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public final int hashCode() {
        int n = 17;
        for (int i = 0; i < this.mSize; ++i) {
            n = (n * 31 + this.zzpng[i]) * 31 + this.zzpnh[i].hashCode();
        }
        return n;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    final int size() {
        return this.mSize;
    }

    final void zza(int n, zzfjp zzfjp2) {
        int n2 = this.zzml(n);
        if (n2 >= 0) {
            this.zzpnh[n2] = zzfjp2;
            return;
        }
        if ((n2 ^= 0xFFFFFFFF) < this.mSize && this.zzpnh[n2] == zzpne) {
            this.zzpng[n2] = n;
            this.zzpnh[n2] = zzfjp2;
            return;
        }
        if (this.mSize >= this.zzpng.length) {
            int n3 = zzfjo.idealIntArraySize(this.mSize + 1);
            int[] arrn = new int[n3];
            zzfjp[] arrzzfjp = new zzfjp[n3];
            System.arraycopy(this.zzpng, 0, arrn, 0, this.zzpng.length);
            System.arraycopy(this.zzpnh, 0, arrzzfjp, 0, this.zzpnh.length);
            this.zzpng = arrn;
            this.zzpnh = arrzzfjp;
        }
        if (this.mSize - n2 != 0) {
            System.arraycopy(this.zzpng, n2, this.zzpng, n2 + 1, this.mSize - n2);
            System.arraycopy(this.zzpnh, n2, this.zzpnh, n2 + 1, this.mSize - n2);
        }
        this.zzpng[n2] = n;
        this.zzpnh[n2] = zzfjp2;
        ++this.mSize;
    }

    final zzfjp zzmj(int n) {
        if ((n = this.zzml(n)) < 0 || this.zzpnh[n] == zzpne) {
            return null;
        }
        return this.zzpnh[n];
    }

    final zzfjp zzmk(int n) {
        return this.zzpnh[n];
    }
}

