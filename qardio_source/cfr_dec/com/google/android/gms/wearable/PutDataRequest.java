/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.internal.DataItemAssetParcelable;
import com.google.android.gms.wearable.zzh;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PutDataRequest
extends zzbfm {
    public static final Parcelable.Creator<PutDataRequest> CREATOR = new zzh();
    private static final long zzlgu = TimeUnit.MINUTES.toMillis(30L);
    private static final Random zzlgv = new SecureRandom();
    private final Uri mUri;
    private byte[] zzhyw;
    private final Bundle zzlgw;
    private long zzlgx;

    private PutDataRequest(Uri uri) {
        this(uri, new Bundle(), null, zzlgu);
    }

    PutDataRequest(Uri uri, Bundle bundle, byte[] arrby, long l) {
        this.mUri = uri;
        this.zzlgw = bundle;
        this.zzlgw.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
        this.zzhyw = arrby;
        this.zzlgx = l;
    }

    public static PutDataRequest create(String string2) {
        zzc.zzb(string2, "path must not be null");
        return PutDataRequest.zzs(PutDataRequest.zznw(string2));
    }

    private static Uri zznw(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("An empty path was supplied.");
        }
        if (!string2.startsWith("/")) {
            throw new IllegalArgumentException("A path must start with a single / .");
        }
        if (string2.startsWith("//")) {
            throw new IllegalArgumentException("A path must start with a single / .");
        }
        return new Uri.Builder().scheme("wear").path(string2).build();
    }

    public static PutDataRequest zzs(Uri uri) {
        zzc.zzb((Object)uri, "uri must not be null");
        return new PutDataRequest(uri);
    }

    public Map<String, Asset> getAssets() {
        HashMap<String, Asset> hashMap = new HashMap<String, Asset>();
        for (String string2 : this.zzlgw.keySet()) {
            hashMap.put(string2, (Asset)this.zzlgw.getParcelable(string2));
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public byte[] getData() {
        return this.zzhyw;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean isUrgent() {
        return this.zzlgx == 0L;
    }

    public PutDataRequest putAsset(String string2, Asset asset) {
        zzbq.checkNotNull(string2);
        zzbq.checkNotNull(asset);
        this.zzlgw.putParcelable(string2, (Parcelable)asset);
        return this;
    }

    public PutDataRequest setData(byte[] arrby) {
        this.zzhyw = arrby;
        return this;
    }

    public PutDataRequest setUrgent() {
        this.zzlgx = 0L;
        return this;
    }

    public String toString() {
        return this.toString(Log.isLoggable((String)"DataMap", (int)3));
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder("PutDataRequest[");
        Object object = this.zzhyw == null ? "null" : Integer.valueOf(this.zzhyw.length);
        object = String.valueOf(object);
        stringBuilder.append(new StringBuilder(String.valueOf(object).length() + 7).append("dataSz=").append((String)object).toString());
        int n = this.zzlgw.size();
        stringBuilder.append(new StringBuilder(23).append(", numAssets=").append(n).toString());
        object = String.valueOf((Object)this.mUri);
        stringBuilder.append(new StringBuilder(String.valueOf(object).length() + 6).append(", uri=").append((String)object).toString());
        long l = this.zzlgx;
        stringBuilder.append(new StringBuilder(35).append(", syncDeadline=").append(l).toString());
        if (!bl) {
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
        stringBuilder.append("]\n  assets: ");
        object = this.zzlgw.keySet().iterator();
        do {
            if (!object.hasNext()) {
                stringBuilder.append("\n  ]");
                return stringBuilder.toString();
            }
            String string2 = (String)object.next();
            String string3 = String.valueOf((Object)this.zzlgw.getParcelable(string2));
            stringBuilder.append(new StringBuilder(String.valueOf(string2).length() + 7 + String.valueOf(string3).length()).append("\n    ").append(string2).append(": ").append(string3).toString());
        } while (true);
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzc.zzb((Object)parcel, "dest must not be null");
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, (Parcelable)this.getUri(), n, false);
        zzbfp.zza(parcel, 4, this.zzlgw, false);
        zzbfp.zza(parcel, 5, this.getData(), false);
        zzbfp.zza(parcel, 6, this.zzlgx);
        zzbfp.zzai(parcel, n2);
    }
}

