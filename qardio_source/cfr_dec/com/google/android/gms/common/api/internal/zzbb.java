/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzba;
import com.google.android.gms.common.internal.zzaf;

final class zzbb
implements zzaf {
    private /* synthetic */ zzba zzfsj;

    zzbb(zzba zzba2) {
        this.zzfsj = zzba2;
    }

    @Override
    public final boolean isConnected() {
        return ((GoogleApiClient)this.zzfsj).isConnected();
    }

    @Override
    public final Bundle zzafi() {
        return null;
    }
}

