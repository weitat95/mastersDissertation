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
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.identity.intents.model.zzb;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;

public final class UserAddress
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<UserAddress> CREATOR = new zzb();
    private String name;
    private String phoneNumber;
    private String zzctp;
    private String zziec;
    private String zzied;
    private String zziee;
    private String zzief;
    private String zzieg;
    private String zzieh;
    private String zziei;
    private String zziej;
    private String zziek;
    private boolean zziel;
    private String zziem;
    private String zzien;

    UserAddress() {
    }

    UserAddress(String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, String string12, String string13, boolean bl, String string14, String string15) {
        this.name = string2;
        this.zziec = string3;
        this.zzied = string4;
        this.zziee = string5;
        this.zzief = string6;
        this.zzieg = string7;
        this.zzieh = string8;
        this.zziei = string9;
        this.zzctp = string10;
        this.zziej = string11;
        this.zziek = string12;
        this.phoneNumber = string13;
        this.zziel = bl;
        this.zziem = string14;
        this.zzien = string15;
    }

    public final String getAddress1() {
        return this.zziec;
    }

    public final String getAddress2() {
        return this.zzied;
    }

    public final String getAddress3() {
        return this.zziee;
    }

    public final String getAddress4() {
        return this.zzief;
    }

    public final String getAddress5() {
        return this.zzieg;
    }

    public final String getAdministrativeArea() {
        return this.zzieh;
    }

    public final String getCountryCode() {
        return this.zzctp;
    }

    public final String getLocality() {
        return this.zziei;
    }

    public final String getName() {
        return this.name;
    }

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final String getPostalCode() {
        return this.zziej;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.name, false);
        zzbfp.zza(parcel, 3, this.zziec, false);
        zzbfp.zza(parcel, 4, this.zzied, false);
        zzbfp.zza(parcel, 5, this.zziee, false);
        zzbfp.zza(parcel, 6, this.zzief, false);
        zzbfp.zza(parcel, 7, this.zzieg, false);
        zzbfp.zza(parcel, 8, this.zzieh, false);
        zzbfp.zza(parcel, 9, this.zziei, false);
        zzbfp.zza(parcel, 10, this.zzctp, false);
        zzbfp.zza(parcel, 11, this.zziej, false);
        zzbfp.zza(parcel, 12, this.zziek, false);
        zzbfp.zza(parcel, 13, this.phoneNumber, false);
        zzbfp.zza(parcel, 14, this.zziel);
        zzbfp.zza(parcel, 15, this.zziem, false);
        zzbfp.zza(parcel, 16, this.zzien, false);
        zzbfp.zzai(parcel, n);
    }
}

