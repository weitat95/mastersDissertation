/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.internal.zzbwb;

abstract class zzbwe<R extends Result>
extends zzm<R, zzbwb> {
    public zzbwe(GoogleApiClient googleApiClient) {
        super(zzbwb.API, googleApiClient);
    }
}

