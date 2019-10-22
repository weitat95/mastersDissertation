/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzchr;
import com.google.android.gms.internal.zzchs;
import java.util.List;
import java.util.Map;

final class zzcht
implements Runnable {
    private final String mPackageName;
    private final int zzcbc;
    private final Throwable zzdfl;
    private final zzchs zzjch;
    private final byte[] zzjci;
    private final Map<String, List<String>> zzjcj;

    private zzcht(String string2, zzchs zzchs2, int n, Throwable throwable, byte[] arrby, Map<String, List<String>> map) {
        zzbq.checkNotNull(zzchs2);
        this.zzjch = zzchs2;
        this.zzcbc = n;
        this.zzdfl = throwable;
        this.zzjci = arrby;
        this.mPackageName = string2;
        this.zzjcj = map;
    }

    /* synthetic */ zzcht(String string2, zzchs zzchs2, int n, Throwable throwable, byte[] arrby, Map map, zzchr zzchr2) {
        this(string2, zzchs2, n, throwable, arrby, map);
    }

    @Override
    public final void run() {
        this.zzjch.zza(this.mPackageName, this.zzcbc, this.zzdfl, this.zzjci, this.zzjcj);
    }
}

