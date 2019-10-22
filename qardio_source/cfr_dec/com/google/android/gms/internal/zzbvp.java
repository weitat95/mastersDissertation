/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbvn;

abstract class zzbvp
extends zzbvn<Status> {
    zzbvp(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected final /* synthetic */ Result zzb(Status status) {
        boolean bl = !status.isSuccess();
        zzbq.checkArgument(bl);
        return status;
    }
}

