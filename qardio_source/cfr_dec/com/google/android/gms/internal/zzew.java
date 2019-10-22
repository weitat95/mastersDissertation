/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

public class zzew {
    private static final ClassLoader zzale = zzew.class.getClassLoader();

    private zzew() {
    }

    public static <T extends Parcelable> T zza(Parcel parcel, Parcelable.Creator<T> creator) {
        if (parcel.readInt() == 0) {
            return null;
        }
        return (T)((Parcelable)creator.createFromParcel(parcel));
    }

    public static void zza(Parcel parcel, IInterface iInterface) {
        if (iInterface == null) {
            parcel.writeStrongBinder(null);
            return;
        }
        parcel.writeStrongBinder(iInterface.asBinder());
    }

    public static void zza(Parcel parcel, Parcelable parcelable) {
        if (parcelable == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(1);
        parcelable.writeToParcel(parcel, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void zza(Parcel parcel, boolean bl) {
        int n = bl ? 1 : 0;
        parcel.writeInt(n);
    }

    public static boolean zza(Parcel parcel) {
        return parcel.readInt() != 0;
    }

    public static void zzb(Parcel parcel, Parcelable parcelable) {
        if (parcelable == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(1);
        parcelable.writeToParcel(parcel, 1);
    }
}

