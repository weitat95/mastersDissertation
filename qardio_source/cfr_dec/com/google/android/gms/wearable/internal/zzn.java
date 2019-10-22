/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.internal.zzhg;

abstract class zzn<R extends Result>
extends zzm<R, zzhg> {
    public zzn(GoogleApiClient googleApiClient) {
        super(Wearable.API, googleApiClient);
    }
}

