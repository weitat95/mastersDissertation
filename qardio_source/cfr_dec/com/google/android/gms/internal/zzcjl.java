/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;

abstract class zzcjl
extends zzcjk {
    private boolean zzdtb;

    zzcjl(zzcim zzcim2) {
        super(zzcim2);
        this.zziwf.zzb(this);
    }

    public final void initialize() {
        if (this.zzdtb) {
            throw new IllegalStateException("Can't initialize twice");
        }
        if (!this.zzaxz()) {
            this.zziwf.zzbak();
            this.zzdtb = true;
        }
    }

    final boolean isInitialized() {
        return this.zzdtb;
    }

    protected abstract boolean zzaxz();

    protected void zzayy() {
    }

    public final void zzazw() {
        if (this.zzdtb) {
            throw new IllegalStateException("Can't initialize twice");
        }
        this.zzayy();
        this.zziwf.zzbak();
        this.zzdtb = true;
    }

    protected final void zzxf() {
        if (!this.isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }
}

