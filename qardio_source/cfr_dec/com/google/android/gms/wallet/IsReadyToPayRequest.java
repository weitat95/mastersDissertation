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
import com.google.android.gms.wallet.zzq;
import com.google.android.gms.wallet.zzr;
import java.util.ArrayList;

public final class IsReadyToPayRequest
extends zzbfm {
    public static final Parcelable.Creator<IsReadyToPayRequest> CREATOR = new zzr();
    ArrayList<Integer> zzkzm;
    private String zzlaz;
    private String zzlba;
    ArrayList<Integer> zzlbb;
    boolean zzlbc;

    IsReadyToPayRequest() {
    }

    IsReadyToPayRequest(ArrayList<Integer> arrayList, String string2, String string3, ArrayList<Integer> arrayList2, boolean bl) {
        this.zzkzm = arrayList;
        this.zzlaz = string2;
        this.zzlba = string3;
        this.zzlbb = arrayList2;
        this.zzlbc = bl;
    }

    public static Builder newBuilder() {
        return new Builder(new IsReadyToPayRequest(), null);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzkzm, false);
        zzbfp.zza(parcel, 4, this.zzlaz, false);
        zzbfp.zza(parcel, 5, this.zzlba, false);
        zzbfp.zza(parcel, 6, this.zzlbb, false);
        zzbfp.zza(parcel, 7, this.zzlbc);
        zzbfp.zzai(parcel, n);
    }

    public final class Builder {
        private /* synthetic */ IsReadyToPayRequest zzlbd;

        private Builder(IsReadyToPayRequest isReadyToPayRequest) {
            this.zzlbd = isReadyToPayRequest;
        }

        /* synthetic */ Builder(IsReadyToPayRequest isReadyToPayRequest, zzq zzq2) {
            this(isReadyToPayRequest);
        }

        public final Builder addAllowedCardNetwork(int n) {
            if (this.zzlbd.zzkzm == null) {
                this.zzlbd.zzkzm = new ArrayList();
            }
            this.zzlbd.zzkzm.add(n);
            return this;
        }

        public final IsReadyToPayRequest build() {
            return this.zzlbd;
        }
    }

}

