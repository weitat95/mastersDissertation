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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wallet.wobs.LabelValueRow;
import com.google.android.gms.wallet.wobs.LoyaltyPoints;
import com.google.android.gms.wallet.wobs.TextModuleData;
import com.google.android.gms.wallet.wobs.TimeInterval;
import com.google.android.gms.wallet.wobs.UriData;
import com.google.android.gms.wallet.wobs.WalletObjectMessage;
import com.google.android.gms.wallet.zzv;
import java.util.ArrayList;

public final class LoyaltyWalletObject
extends zzbfm {
    public static final Parcelable.Creator<LoyaltyWalletObject> CREATOR = new zzv();
    int state;
    String zzghu;
    String zzlbi;
    String zzlbj;
    String zzlbk;
    String zzlbl;
    String zzlbm;
    String zzlbn;
    String zzlbo;
    String zzlbp;
    ArrayList<WalletObjectMessage> zzlbq;
    TimeInterval zzlbr;
    ArrayList<LatLng> zzlbs;
    String zzlbt;
    String zzlbu;
    ArrayList<LabelValueRow> zzlbv;
    boolean zzlbw;
    ArrayList<UriData> zzlbx;
    ArrayList<TextModuleData> zzlby;
    ArrayList<UriData> zzlbz;
    LoyaltyPoints zzlca;
    String zzwc;

    LoyaltyWalletObject() {
        this.zzlbq = new ArrayList();
        this.zzlbs = new ArrayList();
        this.zzlbv = new ArrayList();
        this.zzlbx = new ArrayList();
        this.zzlby = new ArrayList();
        this.zzlbz = new ArrayList();
    }

    LoyaltyWalletObject(String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, int n, ArrayList<WalletObjectMessage> arrayList, TimeInterval timeInterval, ArrayList<LatLng> arrayList2, String string12, String string13, ArrayList<LabelValueRow> arrayList3, boolean bl, ArrayList<UriData> arrayList4, ArrayList<TextModuleData> arrayList5, ArrayList<UriData> arrayList6, LoyaltyPoints loyaltyPoints) {
        this.zzwc = string2;
        this.zzlbi = string3;
        this.zzlbj = string4;
        this.zzlbk = string5;
        this.zzghu = string6;
        this.zzlbl = string7;
        this.zzlbm = string8;
        this.zzlbn = string9;
        this.zzlbo = string10;
        this.zzlbp = string11;
        this.state = n;
        this.zzlbq = arrayList;
        this.zzlbr = timeInterval;
        this.zzlbs = arrayList2;
        this.zzlbt = string12;
        this.zzlbu = string13;
        this.zzlbv = arrayList3;
        this.zzlbw = bl;
        this.zzlbx = arrayList4;
        this.zzlby = arrayList5;
        this.zzlbz = arrayList6;
        this.zzlca = loyaltyPoints;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzwc, false);
        zzbfp.zza(parcel, 3, this.zzlbi, false);
        zzbfp.zza(parcel, 4, this.zzlbj, false);
        zzbfp.zza(parcel, 5, this.zzlbk, false);
        zzbfp.zza(parcel, 6, this.zzghu, false);
        zzbfp.zza(parcel, 7, this.zzlbl, false);
        zzbfp.zza(parcel, 8, this.zzlbm, false);
        zzbfp.zza(parcel, 9, this.zzlbn, false);
        zzbfp.zza(parcel, 10, this.zzlbo, false);
        zzbfp.zza(parcel, 11, this.zzlbp, false);
        zzbfp.zzc(parcel, 12, this.state);
        zzbfp.zzc(parcel, 13, this.zzlbq, false);
        zzbfp.zza(parcel, 14, this.zzlbr, n, false);
        zzbfp.zzc(parcel, 15, this.zzlbs, false);
        zzbfp.zza(parcel, 16, this.zzlbt, false);
        zzbfp.zza(parcel, 17, this.zzlbu, false);
        zzbfp.zzc(parcel, 18, this.zzlbv, false);
        zzbfp.zza(parcel, 19, this.zzlbw);
        zzbfp.zzc(parcel, 20, this.zzlbx, false);
        zzbfp.zzc(parcel, 21, this.zzlby, false);
        zzbfp.zzc(parcel, 22, this.zzlbz, false);
        zzbfp.zza(parcel, 23, this.zzlca, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

