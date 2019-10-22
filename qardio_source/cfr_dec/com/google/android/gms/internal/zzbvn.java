/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.internal.zzbvk;

abstract class zzbvn<R extends Result>
extends zzm<R, zzbvk> {
    public zzbvn(GoogleApiClient googleApiClient) {
        super(zzbvk.API, googleApiClient);
    }
}

