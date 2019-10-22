/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.fragment.zzc;
import com.google.android.gms.wallet.fragment.zzd;

public final class WalletFragmentInitParams
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<WalletFragmentInitParams> CREATOR = new zzd();
    private String zzebv;
    private MaskedWalletRequest zzler;
    private MaskedWallet zzles;
    private int zzlff;

    private WalletFragmentInitParams() {
        this.zzlff = -1;
    }

    WalletFragmentInitParams(String string2, MaskedWalletRequest maskedWalletRequest, int n, MaskedWallet maskedWallet) {
        this.zzebv = string2;
        this.zzler = maskedWalletRequest;
        this.zzlff = n;
        this.zzles = maskedWallet;
    }

    public static Builder newBuilder() {
        return new Builder(new WalletFragmentInitParams(), null);
    }

    public final String getAccountName() {
        return this.zzebv;
    }

    public final MaskedWallet getMaskedWallet() {
        return this.zzles;
    }

    public final MaskedWalletRequest getMaskedWalletRequest() {
        return this.zzler;
    }

    public final int getMaskedWalletRequestCode() {
        return this.zzlff;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.getAccountName(), false);
        zzbfp.zza(parcel, 3, this.getMaskedWalletRequest(), n, false);
        zzbfp.zzc(parcel, 4, this.getMaskedWalletRequestCode());
        zzbfp.zza(parcel, 5, this.getMaskedWallet(), n, false);
        zzbfp.zzai(parcel, n2);
    }

    public final class Builder {
        private /* synthetic */ WalletFragmentInitParams zzlfg;

        private Builder(WalletFragmentInitParams walletFragmentInitParams) {
            this.zzlfg = walletFragmentInitParams;
        }

        /* synthetic */ Builder(WalletFragmentInitParams walletFragmentInitParams, zzc zzc2) {
            this(walletFragmentInitParams);
        }

        /*
         * Enabled aggressive block sorting
         */
        public final WalletFragmentInitParams build() {
            boolean bl = true;
            boolean bl2 = this.zzlfg.zzles != null && this.zzlfg.zzler == null || this.zzlfg.zzles == null && this.zzlfg.zzler != null;
            zzbq.zza(bl2, "Exactly one of MaskedWallet or MaskedWalletRequest is required");
            bl2 = this.zzlfg.zzlff >= 0 ? bl : false;
            zzbq.zza(bl2, "masked wallet request code is required and must be non-negative");
            return this.zzlfg;
        }

        public final Builder setMaskedWallet(MaskedWallet maskedWallet) {
            this.zzlfg.zzles = maskedWallet;
            return this;
        }

        public final Builder setMaskedWalletRequestCode(int n) {
            this.zzlfg.zzlff = n;
            return this;
        }
    }

}

