/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.zzp;

public final class InstrumentInfo
extends zzbfm {
    public static final Parcelable.Creator<InstrumentInfo> CREATOR = new zzp();
    private String zzlaw;
    private String zzlax;
    private int zzlay;

    private InstrumentInfo() {
    }

    public InstrumentInfo(String string2, String string3, int n) {
        this.zzlaw = string2;
        this.zzlax = string3;
        this.zzlay = n;
    }

    public final int getCardClass() {
        switch (this.zzlay) {
            default: {
                return 0;
            }
            case 1: 
            case 2: 
            case 3: 
        }
        return this.zzlay;
    }

    public final String getInstrumentDetails() {
        return this.zzlax;
    }

    public final String getInstrumentType() {
        return this.zzlaw;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.getInstrumentType(), false);
        zzbfp.zza(parcel, 3, this.getInstrumentDetails(), false);
        zzbfp.zzc(parcel, 4, this.getCardClass());
        zzbfp.zzai(parcel, n);
    }
}

