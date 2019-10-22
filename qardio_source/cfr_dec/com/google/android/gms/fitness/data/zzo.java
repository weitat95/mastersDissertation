/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.fitness.data.Device;
import com.google.android.gms.internal.zzbfn;

public final class zzo
implements Parcelable.Creator<Device> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = 0;
        String string2 = null;
        int n2 = zzbfn.zzd(parcel);
        int n3 = 0;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        int n4 = 0;
        block9 : while (parcel.dataPosition() < n2) {
            int n5 = parcel.readInt();
            switch (0xFFFF & n5) {
                default: {
                    zzbfn.zzb(parcel, n5);
                    continue block9;
                }
                case 1: {
                    string5 = zzbfn.zzq(parcel, n5);
                    continue block9;
                }
                case 2: {
                    string4 = zzbfn.zzq(parcel, n5);
                    continue block9;
                }
                case 3: {
                    string3 = zzbfn.zzq(parcel, n5);
                    continue block9;
                }
                case 4: {
                    string2 = zzbfn.zzq(parcel, n5);
                    continue block9;
                }
                case 5: {
                    n3 = zzbfn.zzg(parcel, n5);
                    continue block9;
                }
                case 6: {
                    n = zzbfn.zzg(parcel, n5);
                    continue block9;
                }
                case 1000: 
            }
            n4 = zzbfn.zzg(parcel, n5);
        }
        zzbfn.zzaf(parcel, n2);
        return new Device(n4, string5, string4, string3, string2, n3, n);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new Device[n];
    }
}

