/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.api.internal;

public final class zzck<L> {
    private final L zzfuk;
    private final String zzfun;

    zzck(L l, String string2) {
        this.zzfuk = l;
        this.zzfun = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof zzck)) {
                    return false;
                }
                object = (zzck)object;
                if (this.zzfuk != ((zzck)object).zzfuk || !this.zzfun.equals(((zzck)object).zzfun)) break block5;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return System.identityHashCode(this.zzfuk) * 31 + this.zzfun.hashCode();
    }
}

