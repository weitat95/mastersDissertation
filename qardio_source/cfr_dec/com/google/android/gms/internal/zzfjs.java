/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjr;
import com.google.android.gms.internal.zzfjt;
import java.io.IOException;

public abstract class zzfjs {
    protected volatile int zzpfd = -1;

    public static final <T extends zzfjs> T zza(T t, byte[] arrby) throws zzfjr {
        return zzfjs.zza(t, arrby, 0, arrby.length);
    }

    private static <T extends zzfjs> T zza(T t, byte[] object, int n, int n2) throws zzfjr {
        try {
            object = zzfjj.zzn((byte[])object, 0, n2);
            t.zza((zzfjj)object);
            ((zzfjj)object).zzkp(0);
        }
        catch (zzfjr zzfjr2) {
            throw zzfjr2;
        }
        catch (IOException iOException) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", iOException);
        }
        return t;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return this.zzdag();
    }

    public String toString() {
        return zzfjt.zzd(this);
    }

    public abstract zzfjs zza(zzfjj var1) throws IOException;

    public void zza(zzfjk zzfjk2) throws IOException {
    }

    public zzfjs zzdag() throws CloneNotSupportedException {
        return (zzfjs)super.clone();
    }

    public final int zzdam() {
        if (this.zzpfd < 0) {
            this.zzho();
        }
        return this.zzpfd;
    }

    public final int zzho() {
        int n;
        this.zzpfd = n = this.zzq();
        return n;
    }

    protected int zzq() {
        return 0;
    }
}

