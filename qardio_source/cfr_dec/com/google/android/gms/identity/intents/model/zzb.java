/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.identity.intents.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.internal.zzbfn;

public final class zzb
implements Parcelable.Creator<UserAddress> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        String string8 = null;
        String string9 = null;
        String string10 = null;
        String string11 = null;
        String string12 = null;
        String string13 = null;
        boolean bl = false;
        String string14 = null;
        String string15 = null;
        block17 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block17;
                }
                case 2: {
                    string2 = zzbfn.zzq(parcel, n2);
                    continue block17;
                }
                case 3: {
                    string3 = zzbfn.zzq(parcel, n2);
                    continue block17;
                }
                case 4: {
                    string4 = zzbfn.zzq(parcel, n2);
                    continue block17;
                }
                case 5: {
                    string5 = zzbfn.zzq(parcel, n2);
                    continue block17;
                }
                case 6: {
                    string6 = zzbfn.zzq(parcel, n2);
                    continue block17;
                }
                case 7: {
                    string7 = zzbfn.zzq(parcel, n2);
                    continue block17;
                }
                case 8: {
                    string8 = zzbfn.zzq(parcel, n2);
                    continue block17;
                }
                case 9: {
                    string9 = zzbfn.zzq(parcel, n2);
                    continue block17;
                }
                case 10: {
                    string10 = zzbfn.zzq(parcel, n2);
                    continue block17;
                }
                case 11: {
                    string11 = zzbfn.zzq(parcel, n2);
                    continue block17;
                }
                case 12: {
                    string12 = zzbfn.zzq(parcel, n2);
                    continue block17;
                }
                case 13: {
                    string13 = zzbfn.zzq(parcel, n2);
                    continue block17;
                }
                case 14: {
                    bl = zzbfn.zzc(parcel, n2);
                    continue block17;
                }
                case 15: {
                    string14 = zzbfn.zzq(parcel, n2);
                    continue block17;
                }
                case 16: 
            }
            string15 = zzbfn.zzq(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new UserAddress(string2, string3, string4, string5, string6, string7, string8, string9, string10, string11, string12, string13, bl, string14, string15);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new UserAddress[n];
    }
}

