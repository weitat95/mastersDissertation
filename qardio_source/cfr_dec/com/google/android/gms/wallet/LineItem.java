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
import com.google.android.gms.wallet.zzs;
import com.google.android.gms.wallet.zzt;

public final class LineItem
extends zzbfm {
    public static final Parcelable.Creator<LineItem> CREATOR = new zzt();
    String description;
    String zzkzr;
    String zzkzs;
    String zzlbe;
    String zzlbf;
    int zzlbg;

    LineItem() {
        this.zzlbg = 0;
    }

    LineItem(String string2, String string3, String string4, String string5, int n, String string6) {
        this.description = string2;
        this.zzlbe = string3;
        this.zzlbf = string4;
        this.zzkzr = string5;
        this.zzlbg = n;
        this.zzkzs = string6;
    }

    public static Builder newBuilder() {
        return new Builder(new LineItem(), null);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.description, false);
        zzbfp.zza(parcel, 3, this.zzlbe, false);
        zzbfp.zza(parcel, 4, this.zzlbf, false);
        zzbfp.zza(parcel, 5, this.zzkzr, false);
        zzbfp.zzc(parcel, 6, this.zzlbg);
        zzbfp.zza(parcel, 7, this.zzkzs, false);
        zzbfp.zzai(parcel, n);
    }

    public final class Builder {
        private /* synthetic */ LineItem zzlbh;

        private Builder(LineItem lineItem) {
            this.zzlbh = lineItem;
        }

        /* synthetic */ Builder(LineItem lineItem, zzs zzs2) {
            this(lineItem);
        }

        public final LineItem build() {
            return this.zzlbh;
        }

        public final Builder setCurrencyCode(String string2) {
            this.zzlbh.zzkzs = string2;
            return this;
        }

        public final Builder setDescription(String string2) {
            this.zzlbh.description = string2;
            return this;
        }

        public final Builder setQuantity(String string2) {
            this.zzlbh.zzlbe = string2;
            return this;
        }

        public final Builder setRole(int n) {
            this.zzlbh.zzlbg = n;
            return this;
        }

        public final Builder setTotalPrice(String string2) {
            this.zzlbh.zzkzr = string2;
            return this;
        }

        public final Builder setUnitPrice(String string2) {
            this.zzlbh.zzlbf = string2;
            return this;
        }
    }

}

