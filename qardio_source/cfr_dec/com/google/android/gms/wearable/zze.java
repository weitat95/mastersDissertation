/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wearable.Asset;

public final class zze
implements Parcelable.Creator<Asset> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        Uri uri = null;
        ParcelFileDescriptor parcelFileDescriptor = null;
        String string2 = null;
        byte[] arrby = null;
        block6 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block6;
                }
                case 2: {
                    arrby = zzbfn.zzt(parcel, n2);
                    continue block6;
                }
                case 3: {
                    string2 = zzbfn.zzq(parcel, n2);
                    continue block6;
                }
                case 4: {
                    parcelFileDescriptor = (ParcelFileDescriptor)zzbfn.zza(parcel, n2, ParcelFileDescriptor.CREATOR);
                    continue block6;
                }
                case 5: 
            }
            uri = (Uri)zzbfn.zza(parcel, n2, Uri.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new Asset(arrby, string2, parcelFileDescriptor, uri);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new Asset[n];
    }
}

