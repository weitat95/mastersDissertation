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
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wallet.wobs.LabelValueRow;
import com.google.android.gms.wallet.wobs.TextModuleData;
import com.google.android.gms.wallet.wobs.TimeInterval;
import com.google.android.gms.wallet.wobs.UriData;
import com.google.android.gms.wallet.wobs.WalletObjectMessage;
import com.google.android.gms.wallet.wobs.zzb;
import java.util.ArrayList;

@KeepName
public class CommonWalletObject
extends zzbfm {
    public static final Parcelable.Creator<CommonWalletObject> CREATOR = new zzb();
    String name;
    int state;
    String zzlbj;
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
    String zzwc;

    CommonWalletObject() {
        this.zzlbq = new ArrayList();
        this.zzlbs = new ArrayList();
        this.zzlbv = new ArrayList();
        this.zzlbx = new ArrayList();
        this.zzlby = new ArrayList();
        this.zzlbz = new ArrayList();
    }

    CommonWalletObject(String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, int n, ArrayList<WalletObjectMessage> arrayList, TimeInterval timeInterval, ArrayList<LatLng> arrayList2, String string10, String string11, ArrayList<LabelValueRow> arrayList3, boolean bl, ArrayList<UriData> arrayList4, ArrayList<TextModuleData> arrayList5, ArrayList<UriData> arrayList6) {
        this.zzwc = string2;
        this.zzlbp = string3;
        this.name = string4;
        this.zzlbj = string5;
        this.zzlbl = string6;
        this.zzlbm = string7;
        this.zzlbn = string8;
        this.zzlbo = string9;
        this.state = n;
        this.zzlbq = arrayList;
        this.zzlbr = timeInterval;
        this.zzlbs = arrayList2;
        this.zzlbt = string10;
        this.zzlbu = string11;
        this.zzlbv = arrayList3;
        this.zzlbw = bl;
        this.zzlbx = arrayList4;
        this.zzlby = arrayList5;
        this.zzlbz = arrayList6;
    }

    public static zza zzbkb() {
        return new zza(new CommonWalletObject(), null);
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzwc, false);
        zzbfp.zza(parcel, 3, this.zzlbp, false);
        zzbfp.zza(parcel, 4, this.name, false);
        zzbfp.zza(parcel, 5, this.zzlbj, false);
        zzbfp.zza(parcel, 6, this.zzlbl, false);
        zzbfp.zza(parcel, 7, this.zzlbm, false);
        zzbfp.zza(parcel, 8, this.zzlbn, false);
        zzbfp.zza(parcel, 9, this.zzlbo, false);
        zzbfp.zzc(parcel, 10, this.state);
        zzbfp.zzc(parcel, 11, this.zzlbq, false);
        zzbfp.zza(parcel, 12, this.zzlbr, n, false);
        zzbfp.zzc(parcel, 13, this.zzlbs, false);
        zzbfp.zza(parcel, 14, this.zzlbt, false);
        zzbfp.zza(parcel, 15, this.zzlbu, false);
        zzbfp.zzc(parcel, 16, this.zzlbv, false);
        zzbfp.zza(parcel, 17, this.zzlbw);
        zzbfp.zzc(parcel, 18, this.zzlbx, false);
        zzbfp.zzc(parcel, 19, this.zzlby, false);
        zzbfp.zzc(parcel, 20, this.zzlbz, false);
        zzbfp.zzai(parcel, n2);
    }

    public final class zza {
        private /* synthetic */ CommonWalletObject zzlfq;

        private zza(CommonWalletObject commonWalletObject) {
            this.zzlfq = commonWalletObject;
        }

        /* synthetic */ zza(CommonWalletObject commonWalletObject, com.google.android.gms.wallet.wobs.zza zza2) {
            this(commonWalletObject);
        }

        public final CommonWalletObject zzbkc() {
            return this.zzlfq;
        }

        public final zza zznm(String string2) {
            this.zzlfq.zzwc = string2;
            return this;
        }
    }

}

