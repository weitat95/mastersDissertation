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
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.CountrySpecification;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.zzy;
import com.google.android.gms.wallet.zzz;
import java.util.ArrayList;
import java.util.Collection;

public final class MaskedWalletRequest
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<MaskedWalletRequest> CREATOR = new zzz();
    String zzctp;
    String zzkzs;
    String zzlab;
    Cart zzlal;
    boolean zzlcf;
    boolean zzlcg;
    boolean zzlch;
    String zzlci;
    String zzlcj;
    private boolean zzlck;
    boolean zzlcl;
    private CountrySpecification[] zzlcm;
    boolean zzlcn;
    boolean zzlco;
    ArrayList<com.google.android.gms.identity.intents.model.CountrySpecification> zzlcp;
    PaymentMethodTokenizationParameters zzlcq;
    ArrayList<Integer> zzlcr;

    MaskedWalletRequest() {
        this.zzlcn = true;
        this.zzlco = true;
    }

    MaskedWalletRequest(String string2, boolean bl, boolean bl2, boolean bl3, String string3, String string4, String string5, Cart cart, boolean bl4, boolean bl5, CountrySpecification[] arrcountrySpecification, boolean bl6, boolean bl7, ArrayList<com.google.android.gms.identity.intents.model.CountrySpecification> arrayList, PaymentMethodTokenizationParameters paymentMethodTokenizationParameters, ArrayList<Integer> arrayList2, String string6) {
        this.zzlab = string2;
        this.zzlcf = bl;
        this.zzlcg = bl2;
        this.zzlch = bl3;
        this.zzlci = string3;
        this.zzkzs = string4;
        this.zzlcj = string5;
        this.zzlal = cart;
        this.zzlck = bl4;
        this.zzlcl = bl5;
        this.zzlcm = arrcountrySpecification;
        this.zzlcn = bl6;
        this.zzlco = bl7;
        this.zzlcp = arrayList;
        this.zzlcq = paymentMethodTokenizationParameters;
        this.zzlcr = arrayList2;
        this.zzctp = string6;
    }

    public static Builder newBuilder() {
        return new Builder(new MaskedWalletRequest(), null);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzlab, false);
        zzbfp.zza(parcel, 3, this.zzlcf);
        zzbfp.zza(parcel, 4, this.zzlcg);
        zzbfp.zza(parcel, 5, this.zzlch);
        zzbfp.zza(parcel, 6, this.zzlci, false);
        zzbfp.zza(parcel, 7, this.zzkzs, false);
        zzbfp.zza(parcel, 8, this.zzlcj, false);
        zzbfp.zza(parcel, 9, this.zzlal, n, false);
        zzbfp.zza(parcel, 10, this.zzlck);
        zzbfp.zza(parcel, 11, this.zzlcl);
        zzbfp.zza((Parcel)parcel, (int)12, (Parcelable[])this.zzlcm, (int)n, (boolean)false);
        zzbfp.zza(parcel, 13, this.zzlcn);
        zzbfp.zza(parcel, 14, this.zzlco);
        zzbfp.zzc(parcel, 15, this.zzlcp, false);
        zzbfp.zza(parcel, 16, this.zzlcq, n, false);
        zzbfp.zza(parcel, 17, this.zzlcr, false);
        zzbfp.zza(parcel, 18, this.zzctp, false);
        zzbfp.zzai(parcel, n2);
    }

    public final class Builder {
        private /* synthetic */ MaskedWalletRequest zzlcs;

        private Builder(MaskedWalletRequest maskedWalletRequest) {
            this.zzlcs = maskedWalletRequest;
        }

        /* synthetic */ Builder(MaskedWalletRequest maskedWalletRequest, zzy zzy2) {
            this(maskedWalletRequest);
        }

        public final Builder addAllowedCountrySpecificationsForShipping(Collection<com.google.android.gms.identity.intents.model.CountrySpecification> collection) {
            if (collection != null) {
                if (this.zzlcs.zzlcp == null) {
                    this.zzlcs.zzlcp = new ArrayList();
                }
                this.zzlcs.zzlcp.addAll(collection);
            }
            return this;
        }

        public final MaskedWalletRequest build() {
            return this.zzlcs;
        }

        public final Builder setCart(Cart cart) {
            this.zzlcs.zzlal = cart;
            return this;
        }

        public final Builder setCountryCode(String string2) {
            this.zzlcs.zzctp = string2;
            return this;
        }

        public final Builder setCurrencyCode(String string2) {
            this.zzlcs.zzkzs = string2;
            return this;
        }

        public final Builder setEstimatedTotalPrice(String string2) {
            this.zzlcs.zzlci = string2;
            return this;
        }

        public final Builder setMerchantName(String string2) {
            this.zzlcs.zzlcj = string2;
            return this;
        }

        public final Builder setPaymentMethodTokenizationParameters(PaymentMethodTokenizationParameters paymentMethodTokenizationParameters) {
            this.zzlcs.zzlcq = paymentMethodTokenizationParameters;
            return this;
        }

        public final Builder setPhoneNumberRequired(boolean bl) {
            this.zzlcs.zzlcf = bl;
            return this;
        }

        public final Builder setShippingAddressRequired(boolean bl) {
            this.zzlcs.zzlcg = bl;
            return this;
        }
    }

}

