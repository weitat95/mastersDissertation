/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.internal.DataItemAssetParcelable;
import com.google.android.gms.wearable.internal.zzde;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zzdd
extends zzbfm
implements DataItem {
    public static final Parcelable.Creator<zzdd> CREATOR = new zzde();
    private final Uri mUri;
    private byte[] zzhyw;
    private final Map<String, DataItemAsset> zzlkc;

    zzdd(Uri object, Bundle bundle, byte[] arrby) {
        this.mUri = object;
        object = new HashMap();
        bundle.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
        for (String string2 : bundle.keySet()) {
            object.put(string2, (DataItemAssetParcelable)bundle.getParcelable(string2));
        }
        this.zzlkc = object;
        this.zzhyw = arrby;
    }

    @Override
    public final byte[] getData() {
        return this.zzhyw;
    }

    @Override
    public final Uri getUri() {
        return this.mUri;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String toString() {
        boolean bl = Log.isLoggable((String)"DataItem", (int)3);
        StringBuilder stringBuilder = new StringBuilder("DataItemParcelable[");
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        Object object = this.zzhyw == null ? "null" : Integer.valueOf(this.zzhyw.length);
        object = String.valueOf(object);
        stringBuilder.append(new StringBuilder(String.valueOf(object).length() + 8).append(",dataSz=").append((String)object).toString());
        int n = this.zzlkc.size();
        stringBuilder.append(new StringBuilder(23).append(", numAssets=").append(n).toString());
        object = String.valueOf((Object)this.mUri);
        stringBuilder.append(new StringBuilder(String.valueOf(object).length() + 6).append(", uri=").append((String)object).toString());
        if (!bl) {
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
        stringBuilder.append("]\n  assets: ");
        object = this.zzlkc.keySet().iterator();
        do {
            if (!object.hasNext()) {
                stringBuilder.append("\n  ]");
                return stringBuilder.toString();
            }
            String string2 = object.next();
            String string3 = String.valueOf(this.zzlkc.get(string2));
            stringBuilder.append(new StringBuilder(String.valueOf(string2).length() + 7 + String.valueOf(string3).length()).append("\n    ").append(string2).append(": ").append(string3).toString());
        } while (true);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, (Parcelable)this.getUri(), n, false);
        Bundle bundle = new Bundle();
        bundle.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
        for (Map.Entry<String, DataItemAsset> entry : this.zzlkc.entrySet()) {
            bundle.putParcelable(entry.getKey(), (Parcelable)new DataItemAssetParcelable(entry.getValue()));
        }
        zzbfp.zza(parcel, 4, bundle, false);
        zzbfp.zza(parcel, 5, this.getData(), false);
        zzbfp.zzai(parcel, n2);
    }
}

