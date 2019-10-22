/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wearable.internal.zze;
import com.google.android.gms.wearable.internal.zzem;
import com.google.android.gms.wearable.internal.zzeo;
import com.google.android.gms.wearable.internal.zzhk;

public final class zzd
extends zzbfm {
    public static final Parcelable.Creator<zzd> CREATOR = new zze();
    private zzem zzlhx;
    private IntentFilter[] zzlhy;
    private String zzlhz;
    private String zzlia;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    zzd(IBinder object, IntentFilter[] arrintentFilter, String string2, String string3) {
        void var4_8;
        void var2_6;
        void var3_7;
        IInterface iInterface = null;
        if (object != null) {
            void var1_3;
            if (object == null) {
                IInterface iInterface2 = iInterface;
            } else {
                iInterface = object.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableListener");
                if (iInterface instanceof zzem) {
                    zzem zzem2 = (zzem)iInterface;
                } else {
                    zzeo zzeo2 = new zzeo((IBinder)object);
                }
            }
            this.zzlhx = var1_3;
        } else {
            this.zzlhx = null;
        }
        this.zzlhy = var2_6;
        this.zzlhz = var3_7;
        this.zzlia = var4_8;
    }

    public zzd(zzhk zzhk2) {
        this.zzlhx = zzhk2;
        this.zzlhy = zzhk2.zzbkg();
        this.zzlhz = zzhk2.zzbkh();
        this.zzlia = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        IBinder iBinder = this.zzlhx == null ? null : this.zzlhx.asBinder();
        zzbfp.zza(parcel, 2, iBinder, false);
        zzbfp.zza((Parcel)parcel, (int)3, (Parcelable[])this.zzlhy, (int)n, (boolean)false);
        zzbfp.zza(parcel, 4, this.zzlhz, false);
        zzbfp.zza(parcel, 5, this.zzlia, false);
        zzbfp.zzai(parcel, n2);
    }
}

