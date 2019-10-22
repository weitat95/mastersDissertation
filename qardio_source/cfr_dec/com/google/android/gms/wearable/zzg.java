/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wearable.ConnectionConfiguration;

public final class zzg
implements Parcelable.Creator<ConnectionConfiguration> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2 = null;
        boolean bl = false;
        int n = zzbfn.zzd(parcel);
        String string3 = null;
        boolean bl2 = false;
        boolean bl3 = false;
        int n2 = 0;
        int n3 = 0;
        String string4 = null;
        String string5 = null;
        block11 : while (parcel.dataPosition() < n) {
            int n4 = parcel.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb(parcel, n4);
                    continue block11;
                }
                case 2: {
                    string5 = zzbfn.zzq(parcel, n4);
                    continue block11;
                }
                case 3: {
                    string4 = zzbfn.zzq(parcel, n4);
                    continue block11;
                }
                case 4: {
                    n3 = zzbfn.zzg(parcel, n4);
                    continue block11;
                }
                case 5: {
                    n2 = zzbfn.zzg(parcel, n4);
                    continue block11;
                }
                case 6: {
                    bl3 = zzbfn.zzc(parcel, n4);
                    continue block11;
                }
                case 7: {
                    bl2 = zzbfn.zzc(parcel, n4);
                    continue block11;
                }
                case 8: {
                    string3 = zzbfn.zzq(parcel, n4);
                    continue block11;
                }
                case 9: {
                    bl = zzbfn.zzc(parcel, n4);
                    continue block11;
                }
                case 10: 
            }
            string2 = zzbfn.zzq(parcel, n4);
        }
        zzbfn.zzaf(parcel, n);
        return new ConnectionConfiguration(string5, string4, n3, n2, bl3, bl2, string3, bl, string2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new ConnectionConfiguration[n];
    }
}

