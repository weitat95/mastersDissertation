/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.wearable.internal.zzah;
import com.google.android.gms.wearable.internal.zzaw;
import com.google.android.gms.wearable.internal.zzem;
import com.google.android.gms.wearable.internal.zzfe;
import com.google.android.gms.wearable.internal.zzfo;
import com.google.android.gms.wearable.internal.zzi;
import com.google.android.gms.wearable.internal.zzl;
import java.util.ArrayList;
import java.util.List;

public abstract class zzen
extends zzev
implements zzem {
    public zzen() {
        this.attachInterface((IInterface)this, "com.google.android.gms.wearable.internal.IWearableListener");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (this.zza(n, parcel, parcel2, n2)) {
            return true;
        }
        switch (n) {
            default: {
                return false;
            }
            case 1: {
                this.zzas(zzew.zza(parcel, DataHolder.CREATOR));
                do {
                    return true;
                    break;
                } while (true);
            }
            case 2: {
                this.zza(zzew.zza(parcel, zzfe.CREATOR));
                return true;
            }
            case 3: {
                this.zza(zzew.zza(parcel, zzfo.CREATOR));
                return true;
            }
            case 4: {
                this.zzb(zzew.zza(parcel, zzfo.CREATOR));
                return true;
            }
            case 5: {
                this.onConnectedNodes((List)parcel.createTypedArrayList(zzfo.CREATOR));
                return true;
            }
            case 6: {
                this.zza(zzew.zza(parcel, zzl.CREATOR));
                return true;
            }
            case 7: {
                this.zza(zzew.zza(parcel, zzaw.CREATOR));
                return true;
            }
            case 8: {
                this.zza(zzew.zza(parcel, zzah.CREATOR));
                return true;
            }
            case 9: 
        }
        this.zza(zzew.zza(parcel, zzi.CREATOR));
        return true;
    }
}

