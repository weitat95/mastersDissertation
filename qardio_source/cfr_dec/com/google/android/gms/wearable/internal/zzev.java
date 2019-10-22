/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.internal.zzek;
import com.google.android.gms.wearable.internal.zzep;
import com.google.android.gms.wearable.internal.zzeu;
import com.google.android.gms.wearable.internal.zzey;
import com.google.android.gms.wearable.internal.zzhe;
import com.google.android.gms.wearable.internal.zzhg;
import com.google.android.gms.wearable.internal.zzn;

final class zzev
extends zzn<MessageApi.SendMessageResult> {
    private /* synthetic */ String val$action;
    private /* synthetic */ byte[] zzkrx;
    private /* synthetic */ String zzliv;

    zzev(zzeu zzeu2, GoogleApiClient googleApiClient, String string2, String string3, byte[] arrby) {
        this.zzliv = string2;
        this.val$action = string3;
        this.zzkrx = arrby;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        zzb2 = (zzhg)zzb2;
        String string2 = this.zzliv;
        String string3 = this.val$action;
        byte[] arrby = this.zzkrx;
        ((zzep)((zzd)((Object)zzb2)).zzakn()).zza(new zzhe(this), string2, string3, arrby);
    }

    @Override
    protected final /* synthetic */ Result zzb(Status status) {
        return new zzey(status, -1);
    }
}

