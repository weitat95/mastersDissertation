/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import java.util.ArrayList;

public abstract class zzg<T>
extends AbstractDataBuffer<T> {
    private boolean zzfwo = false;
    private ArrayList<Integer> zzfwp;

    protected zzg(DataHolder dataHolder) {
        super(dataHolder);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzakb() {
        synchronized (this) {
            if (!this.zzfwo) {
                int n = this.zzfqt.zzfwg;
                this.zzfwp = new ArrayList();
                if (n > 0) {
                    this.zzfwp.add(0);
                    String string2 = this.zzaka();
                    int n2 = this.zzfqt.zzbz(0);
                    String string3 = this.zzfqt.zzd(string2, 0, n2);
                    for (n2 = 1; n2 < n; ++n2) {
                        int n3 = this.zzfqt.zzbz(n2);
                        String string4 = this.zzfqt.zzd(string2, n2, n3);
                        if (string4 == null) {
                            throw new NullPointerException(new StringBuilder(String.valueOf(string2).length() + 78).append("Missing value for markerColumn: ").append(string2).append(", at row: ").append(n2).append(", for window: ").append(n3).toString());
                        }
                        if (string4.equals(string3)) continue;
                        this.zzfwp.add(n2);
                        string3 = string4;
                    }
                }
                this.zzfwo = true;
            }
            return;
        }
    }

    private final int zzcc(int n) {
        if (n < 0 || n >= this.zzfwp.size()) {
            throw new IllegalArgumentException(new StringBuilder(53).append("Position ").append(n).append(" is out of bounds for this buffer").toString());
        }
        return this.zzfwp.get(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final T get(int n) {
        int n2;
        this.zzakb();
        int n3 = this.zzcc(n);
        if (n < 0 || n == this.zzfwp.size()) {
            n2 = 0;
            return this.zzl(n3, n2);
        }
        int n4 = n == this.zzfwp.size() - 1 ? this.zzfqt.zzfwg - this.zzfwp.get(n) : this.zzfwp.get(n + 1) - this.zzfwp.get(n);
        n2 = n4;
        if (n4 != 1) return this.zzl(n3, n2);
        n = this.zzcc(n);
        this.zzfqt.zzbz(n);
        n2 = n4;
        return this.zzl(n3, n2);
    }

    @Override
    public int getCount() {
        this.zzakb();
        return this.zzfwp.size();
    }

    protected abstract String zzaka();

    protected abstract T zzl(int var1, int var2);
}

