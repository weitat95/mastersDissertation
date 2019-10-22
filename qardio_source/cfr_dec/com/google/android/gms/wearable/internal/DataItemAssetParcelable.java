/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.internal.zzda;

@KeepName
public class DataItemAssetParcelable
extends zzbfm
implements ReflectedParcelable,
DataItemAsset {
    public static final Parcelable.Creator<DataItemAssetParcelable> CREATOR = new zzda();
    private final String zzbhb;
    private final String zzbuz;

    public DataItemAssetParcelable(DataItemAsset dataItemAsset) {
        this.zzbuz = zzbq.checkNotNull(dataItemAsset.getId());
        this.zzbhb = zzbq.checkNotNull(dataItemAsset.getDataItemKey());
    }

    DataItemAssetParcelable(String string2, String string3) {
        this.zzbuz = string2;
        this.zzbhb = string3;
    }

    @Override
    public String getDataItemKey() {
        return this.zzbhb;
    }

    @Override
    public String getId() {
        return this.zzbuz;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DataItemAssetParcelable[");
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        if (this.zzbuz == null) {
            stringBuilder.append(",noid");
        } else {
            stringBuilder.append(",");
            stringBuilder.append(this.zzbuz);
        }
        stringBuilder.append(", key=");
        stringBuilder.append(this.zzbhb);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.getId(), false);
        zzbfp.zza(parcel, 3, this.getDataItemKey(), false);
        zzbfp.zzai(parcel, n);
    }
}

