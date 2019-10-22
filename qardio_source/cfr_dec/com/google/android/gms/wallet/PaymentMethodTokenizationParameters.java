/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.zzag;
import com.google.android.gms.wallet.zzah;

public final class PaymentMethodTokenizationParameters
extends zzbfm {
    public static final Parcelable.Creator<PaymentMethodTokenizationParameters> CREATOR = new zzah();
    Bundle zzeac = new Bundle();
    int zzldi;

    private PaymentMethodTokenizationParameters() {
    }

    PaymentMethodTokenizationParameters(int n, Bundle bundle) {
        this.zzldi = n;
        this.zzeac = bundle;
    }

    public static Builder newBuilder() {
        return new Builder(new PaymentMethodTokenizationParameters(), null);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.zzldi);
        zzbfp.zza(parcel, 3, this.zzeac, false);
        zzbfp.zzai(parcel, n);
    }

    public final class Builder {
        private /* synthetic */ PaymentMethodTokenizationParameters zzldk;

        private Builder(PaymentMethodTokenizationParameters paymentMethodTokenizationParameters) {
            this.zzldk = paymentMethodTokenizationParameters;
        }

        /* synthetic */ Builder(PaymentMethodTokenizationParameters paymentMethodTokenizationParameters, zzag zzag2) {
            this(paymentMethodTokenizationParameters);
        }

        public final Builder addParameter(String string2, String string3) {
            zzbq.zzh(string2, "Tokenization parameter name must not be empty");
            zzbq.zzh(string3, "Tokenization parameter value must not be empty");
            this.zzldk.zzeac.putString(string2, string3);
            return this;
        }

        public final PaymentMethodTokenizationParameters build() {
            return this.zzldk;
        }

        public final Builder setPaymentMethodTokenizationType(int n) {
            this.zzldk.zzldi = n;
            return this;
        }
    }

}

