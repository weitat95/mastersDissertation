/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcgx;
import java.util.Iterator;
import java.util.Set;

final class zzcgy
implements Iterator<String> {
    private Iterator<String> zzizr;
    private /* synthetic */ zzcgx zzizs;

    zzcgy(zzcgx zzcgx2) {
        this.zzizs = zzcgx2;
        this.zzizr = zzcgx.zza(this.zzizs).keySet().iterator();
    }

    @Override
    public final boolean hasNext() {
        return this.zzizr.hasNext();
    }

    @Override
    public final /* synthetic */ Object next() {
        return this.zzizr.next();
    }

    @Override
    public final void remove() {
        throw new UnsupportedOperationException("Remove not supported");
    }
}

