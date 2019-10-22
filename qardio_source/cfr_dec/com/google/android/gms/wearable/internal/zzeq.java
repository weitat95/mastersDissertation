/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.internal.zzd;
import com.google.android.gms.wearable.internal.zzek;
import com.google.android.gms.wearable.internal.zzep;

public final class zzeq
extends zzeu
implements zzep {
    zzeq(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wearable.internal.IWearableService");
    }

    @Override
    public final void zza(zzek zzek2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzek2);
        this.zzb(8, parcel);
    }

    @Override
    public final void zza(zzek zzek2, PutDataRequest putDataRequest) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzek2);
        zzew.zza(parcel, putDataRequest);
        this.zzb(6, parcel);
    }

    @Override
    public final void zza(zzek zzek2, zzd zzd2) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzek2);
        zzew.zza(parcel, zzd2);
        this.zzb(16, parcel);
    }

    @Override
    public final void zza(zzek zzek2, String string2, String string3, byte[] arrby) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzek2);
        parcel.writeString(string2);
        parcel.writeString(string3);
        parcel.writeByteArray(arrby);
        this.zzb(12, parcel);
    }

    @Override
    public final void zzb(zzek zzek2, Uri uri, int n) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, zzek2);
        zzew.zza(parcel, (Parcelable)uri);
        parcel.writeInt(n);
        this.zzb(41, parcel);
    }
}

