/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzapz;
import com.google.android.gms.internal.zzaqc;

public abstract class zzaqa
extends zzapz {
    private boolean zzdtb;

    protected zzaqa(zzaqc zzaqc2) {
        super(zzaqc2);
    }

    public final void initialize() {
        this.zzvf();
        this.zzdtb = true;
    }

    public final boolean isInitialized() {
        return this.zzdtb;
    }

    protected abstract void zzvf();

    protected final void zzxf() {
        if (!this.isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }
}

