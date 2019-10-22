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
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.LineItem;
import com.google.android.gms.wallet.zzf;
import com.google.android.gms.wallet.zzg;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Cart
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<Cart> CREATOR = new zzg();
    String zzkzr;
    String zzkzs;
    ArrayList<LineItem> zzkzt;

    Cart() {
        this.zzkzt = new ArrayList();
    }

    Cart(String string2, String string3, ArrayList<LineItem> arrayList) {
        this.zzkzr = string2;
        this.zzkzs = string3;
        this.zzkzt = arrayList;
    }

    public static Builder newBuilder() {
        return new Builder(new Cart(), null);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzkzr, false);
        zzbfp.zza(parcel, 3, this.zzkzs, false);
        zzbfp.zzc(parcel, 4, this.zzkzt, false);
        zzbfp.zzai(parcel, n);
    }

    public final class Builder {
        private /* synthetic */ Cart zzkzu;

        private Builder(Cart cart) {
            this.zzkzu = cart;
        }

        /* synthetic */ Builder(Cart cart, zzf zzf2) {
            this(cart);
        }

        public final Builder addLineItem(LineItem lineItem) {
            this.zzkzu.zzkzt.add(lineItem);
            return this;
        }

        public final Cart build() {
            return this.zzkzu;
        }

        public final Builder setCurrencyCode(String string2) {
            this.zzkzu.zzkzs = string2;
            return this;
        }

        public final Builder setLineItems(List<LineItem> list) {
            this.zzkzu.zzkzt.clear();
            this.zzkzu.zzkzt.addAll(list);
            return this;
        }

        public final Builder setTotalPrice(String string2) {
            this.zzkzu.zzkzr = string2;
            return this;
        }
    }

}

