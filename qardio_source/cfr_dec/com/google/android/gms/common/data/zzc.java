/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import java.util.Arrays;

public class zzc {
    protected final DataHolder zzfqt;
    protected int zzfvx;
    private int zzfvy;

    public zzc(DataHolder dataHolder, int n) {
        this.zzfqt = zzbq.checkNotNull(dataHolder);
        this.zzbx(n);
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = bl = false;
        if (object instanceof zzc) {
            object = (zzc)object;
            bl2 = bl;
            if (zzbg.equal(((zzc)object).zzfvx, this.zzfvx)) {
                bl2 = bl;
                if (zzbg.equal(((zzc)object).zzfvy, this.zzfvy)) {
                    bl2 = bl;
                    if (((zzc)object).zzfqt == this.zzfqt) {
                        bl2 = true;
                    }
                }
            }
        }
        return bl2;
    }

    protected final byte[] getByteArray(String string2) {
        return this.zzfqt.zzg(string2, this.zzfvx, this.zzfvy);
    }

    protected final int getInteger(String string2) {
        return this.zzfqt.zzc(string2, this.zzfvx, this.zzfvy);
    }

    protected final String getString(String string2) {
        return this.zzfqt.zzd(string2, this.zzfvx, this.zzfvy);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzfvx, this.zzfvy, this.zzfqt});
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void zzbx(int n) {
        boolean bl = n >= 0 && n < this.zzfqt.zzfwg;
        zzbq.checkState(bl);
        this.zzfvx = n;
        this.zzfvy = this.zzfqt.zzbz(this.zzfvx);
    }
}

