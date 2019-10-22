/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Message
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.RemoteException
 */
package com.google.android.gms.iid;

import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.iid.zzi;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzew;

public final class zzj
extends zzeu
implements zzi {
    zzj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.iid.IMessengerCompat");
    }

    @Override
    public final void send(Message message) throws RemoteException {
        Parcel parcel = this.zzbe();
        zzew.zza(parcel, (Parcelable)message);
        this.zzc(1, parcel);
    }
}

