/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.wobs.LabelValue;
import com.google.android.gms.wallet.wobs.zze;
import java.util.ArrayList;

public final class LabelValueRow
extends zzbfm {
    public static final Parcelable.Creator<LabelValueRow> CREATOR = new zze();
    String zzlfr;
    String zzlfs;
    ArrayList<LabelValue> zzlft;

    LabelValueRow() {
        this.zzlft = new ArrayList();
    }

    LabelValueRow(String string2, String string3, ArrayList<LabelValue> arrayList) {
        this.zzlfr = string2;
        this.zzlfs = string3;
        this.zzlft = arrayList;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzlfr, false);
        zzbfp.zza(parcel, 3, this.zzlfs, false);
        zzbfp.zzc(parcel, 4, this.zzlft, false);
        zzbfp.zzai(parcel, n);
    }
}

