/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 */
package com.google.android.gms.wallet.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;
import com.google.android.gms.wallet.fragment.zze;
import com.google.android.gms.wallet.fragment.zzf;

public final class WalletFragmentOptions
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<WalletFragmentOptions> CREATOR = new zzf();
    private int mTheme;
    private int zzgir;
    private int zzlea;
    private WalletFragmentStyle zzlfh;

    private WalletFragmentOptions() {
        this.zzlea = 3;
        this.zzlfh = new WalletFragmentStyle();
    }

    WalletFragmentOptions(int n, int n2, WalletFragmentStyle walletFragmentStyle, int n3) {
        this.zzlea = n;
        this.mTheme = n2;
        this.zzlfh = walletFragmentStyle;
        this.zzgir = n3;
    }

    public static Builder newBuilder() {
        return new Builder(new WalletFragmentOptions(), null);
    }

    public static WalletFragmentOptions zza(Context context, AttributeSet object) {
        object = context.obtainStyledAttributes(object, R.styleable.WalletFragmentOptions);
        int n = object.getInt(R.styleable.WalletFragmentOptions_appTheme, 0);
        int n2 = object.getInt(R.styleable.WalletFragmentOptions_environment, 1);
        int n3 = object.getResourceId(R.styleable.WalletFragmentOptions_fragmentStyle, 0);
        int n4 = object.getInt(R.styleable.WalletFragmentOptions_fragmentMode, 1);
        object.recycle();
        object = new WalletFragmentOptions();
        object.mTheme = n;
        object.zzlea = n2;
        object.zzlfh = new WalletFragmentStyle().setStyleResourceId(n3);
        object.zzlfh.zzeo(context);
        object.zzgir = n4;
        return object;
    }

    public final int getEnvironment() {
        return this.zzlea;
    }

    public final WalletFragmentStyle getFragmentStyle() {
        return this.zzlfh;
    }

    public final int getMode() {
        return this.zzgir;
    }

    public final int getTheme() {
        return this.mTheme;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.getEnvironment());
        zzbfp.zzc(parcel, 3, this.getTheme());
        zzbfp.zza(parcel, 4, this.getFragmentStyle(), n, false);
        zzbfp.zzc(parcel, 5, this.getMode());
        zzbfp.zzai(parcel, n2);
    }

    public final void zzeo(Context context) {
        if (this.zzlfh != null) {
            this.zzlfh.zzeo(context);
        }
    }

    public final class Builder {
        private /* synthetic */ WalletFragmentOptions zzlfi;

        private Builder(WalletFragmentOptions walletFragmentOptions) {
            this.zzlfi = walletFragmentOptions;
        }

        /* synthetic */ Builder(WalletFragmentOptions walletFragmentOptions, zze zze2) {
            this(walletFragmentOptions);
        }

        public final WalletFragmentOptions build() {
            return this.zzlfi;
        }

        public final Builder setEnvironment(int n) {
            this.zzlfi.zzlea = n;
            return this;
        }

        public final Builder setFragmentStyle(WalletFragmentStyle walletFragmentStyle) {
            this.zzlfi.zzlfh = walletFragmentStyle;
            return this;
        }

        public final Builder setMode(int n) {
            this.zzlfi.zzgir = n;
            return this;
        }

        public final Builder setTheme(int n) {
            this.zzlfi.mTheme = n;
            return this;
        }
    }

}

