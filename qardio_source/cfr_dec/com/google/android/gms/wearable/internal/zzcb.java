/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.internal.zzbw;
import com.google.android.gms.wearable.internal.zzch;
import com.google.android.gms.wearable.internal.zzek;
import com.google.android.gms.wearable.internal.zzep;
import com.google.android.gms.wearable.internal.zzgp;
import com.google.android.gms.wearable.internal.zzhg;
import com.google.android.gms.wearable.internal.zzn;

final class zzcb
extends zzn<DataApi.DeleteDataItemsResult> {
    private /* synthetic */ Uri zzjvt;
    private /* synthetic */ int zzljs;

    zzcb(zzbw zzbw2, GoogleApiClient googleApiClient, Uri uri, int n) {
        this.zzjvt = uri;
        this.zzljs = n;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        zzb2 = (zzhg)zzb2;
        Uri uri = this.zzjvt;
        int n = this.zzljs;
        ((zzep)((zzd)((Object)zzb2)).zzakn()).zzb(new zzgp(this), uri, n);
    }

    @Override
    protected final /* synthetic */ Result zzb(Status status) {
        return new zzch(status, 0);
    }
}

