/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 */
package com.google.android.gms.wallet.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.google.android.gms.R;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.fragment.zzg;

public final class WalletFragmentStyle
extends zzbfm {
    public static final Parcelable.Creator<WalletFragmentStyle> CREATOR = new zzg();
    private Bundle zzlfj;
    private int zzlfk;

    public WalletFragmentStyle() {
        this.zzlfj = new Bundle();
        this.zzlfj.putInt("buyButtonAppearanceDefault", 4);
        this.zzlfj.putInt("maskedWalletDetailsLogoImageTypeDefault", 3);
    }

    WalletFragmentStyle(Bundle bundle, int n) {
        this.zzlfj = bundle;
        this.zzlfk = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(TypedArray typedArray, int n, String string2) {
        long l;
        if (this.zzlfj.containsKey(string2) || (typedArray = typedArray.peekValue(n)) == null) {
            return;
        }
        Bundle bundle = this.zzlfj;
        switch (typedArray.type) {
            default: {
                n = typedArray.type;
                throw new IllegalArgumentException(new StringBuilder(38).append("Unexpected dimension type: ").append(n).toString());
            }
            case 16: {
                l = WalletFragmentStyle.zzfa(typedArray.data);
                break;
            }
            case 5: {
                l = WalletFragmentStyle.zzr(128, typedArray.data);
            }
        }
        bundle.putLong(string2, l);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(TypedArray typedArray, int n, String string2, String string3) {
        if (this.zzlfj.containsKey(string2) || this.zzlfj.containsKey(string3) || (typedArray = typedArray.peekValue(n)) == null) {
            return;
        }
        if (typedArray.type >= 28 && typedArray.type <= 31) {
            this.zzlfj.putInt(string2, typedArray.data);
            return;
        }
        this.zzlfj.putInt(string3, typedArray.resourceId);
    }

    private static long zzb(int n, float f) {
        switch (n) {
            default: {
                throw new IllegalArgumentException(new StringBuilder(30).append("Unrecognized unit: ").append(n).toString());
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
        }
        return WalletFragmentStyle.zzr(n, Float.floatToIntBits(f));
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzb(TypedArray typedArray, int n, String string2) {
        if (this.zzlfj.containsKey(string2) || (typedArray = typedArray.peekValue(n)) == null) {
            return;
        }
        this.zzlfj.putInt(string2, typedArray.data);
    }

    private static long zzfa(int n) {
        if (n < 0) {
            if (n == -1 || n == -2) {
                return WalletFragmentStyle.zzr(129, n);
            }
            throw new IllegalArgumentException(new StringBuilder(39).append("Unexpected dimension value: ").append(n).toString());
        }
        return WalletFragmentStyle.zzb(0, n);
    }

    private static long zzr(int n, int n2) {
        return (long)n << 32 | (long)n2 & 0xFFFFFFFFL;
    }

    public final WalletFragmentStyle setMaskedWalletDetailsBackgroundColor(int n) {
        this.zzlfj.remove("maskedWalletDetailsBackgroundResource");
        this.zzlfj.putInt("maskedWalletDetailsBackgroundColor", n);
        return this;
    }

    public final WalletFragmentStyle setMaskedWalletDetailsButtonBackgroundColor(int n) {
        this.zzlfj.remove("maskedWalletDetailsButtonBackgroundResource");
        this.zzlfj.putInt("maskedWalletDetailsButtonBackgroundColor", n);
        return this;
    }

    public final WalletFragmentStyle setMaskedWalletDetailsButtonTextAppearance(int n) {
        this.zzlfj.putInt("maskedWalletDetailsButtonTextAppearance", n);
        return this;
    }

    public final WalletFragmentStyle setMaskedWalletDetailsHeaderTextAppearance(int n) {
        this.zzlfj.putInt("maskedWalletDetailsHeaderTextAppearance", n);
        return this;
    }

    public final WalletFragmentStyle setMaskedWalletDetailsTextAppearance(int n) {
        this.zzlfj.putInt("maskedWalletDetailsTextAppearance", n);
        return this;
    }

    public final WalletFragmentStyle setStyleResourceId(int n) {
        this.zzlfk = n;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzlfj, false);
        zzbfp.zzc(parcel, 3, this.zzlfk);
        zzbfp.zzai(parcel, n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final int zza(String string2, DisplayMetrics displayMetrics, int n) {
        int n2;
        if (!this.zzlfj.containsKey(string2)) return n;
        long l = this.zzlfj.getLong(string2);
        int n3 = (int)(l >>> 32);
        n = n2 = (int)l;
        switch (n3) {
            default: {
                throw new IllegalStateException(new StringBuilder(36).append("Unexpected unit or type: ").append(n3).toString());
            }
            case 128: {
                n = TypedValue.complexToDimensionPixelSize((int)n2, (DisplayMetrics)displayMetrics);
            }
            case 129: {
                return n;
            }
            case 0: {
                n = 0;
                do {
                    return Math.round(TypedValue.applyDimension((int)n, (float)Float.intBitsToFloat(n2), (DisplayMetrics)displayMetrics));
                    break;
                } while (true);
            }
            case 1: {
                n = 1;
                return Math.round(TypedValue.applyDimension((int)n, (float)Float.intBitsToFloat(n2), (DisplayMetrics)displayMetrics));
            }
            case 2: {
                n = 2;
                return Math.round(TypedValue.applyDimension((int)n, (float)Float.intBitsToFloat(n2), (DisplayMetrics)displayMetrics));
            }
            case 3: {
                n = 3;
                return Math.round(TypedValue.applyDimension((int)n, (float)Float.intBitsToFloat(n2), (DisplayMetrics)displayMetrics));
            }
            case 4: {
                n = 4;
                return Math.round(TypedValue.applyDimension((int)n, (float)Float.intBitsToFloat(n2), (DisplayMetrics)displayMetrics));
            }
            case 5: 
        }
        n = 5;
        return Math.round(TypedValue.applyDimension((int)n, (float)Float.intBitsToFloat(n2), (DisplayMetrics)displayMetrics));
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzeo(Context context) {
        int n = this.zzlfk <= 0 ? R.style.WalletFragmentDefaultStyle : this.zzlfk;
        context = context.obtainStyledAttributes(n, R.styleable.WalletFragmentStyle);
        this.zza((TypedArray)context, R.styleable.WalletFragmentStyle_buyButtonWidth, "buyButtonWidth");
        this.zza((TypedArray)context, R.styleable.WalletFragmentStyle_buyButtonHeight, "buyButtonHeight");
        this.zzb((TypedArray)context, R.styleable.WalletFragmentStyle_buyButtonText, "buyButtonText");
        this.zzb((TypedArray)context, R.styleable.WalletFragmentStyle_buyButtonAppearance, "buyButtonAppearance");
        this.zzb((TypedArray)context, R.styleable.WalletFragmentStyle_maskedWalletDetailsTextAppearance, "maskedWalletDetailsTextAppearance");
        this.zzb((TypedArray)context, R.styleable.WalletFragmentStyle_maskedWalletDetailsHeaderTextAppearance, "maskedWalletDetailsHeaderTextAppearance");
        this.zza((TypedArray)context, R.styleable.WalletFragmentStyle_maskedWalletDetailsBackground, "maskedWalletDetailsBackgroundColor", "maskedWalletDetailsBackgroundResource");
        this.zzb((TypedArray)context, R.styleable.WalletFragmentStyle_maskedWalletDetailsButtonTextAppearance, "maskedWalletDetailsButtonTextAppearance");
        this.zza((TypedArray)context, R.styleable.WalletFragmentStyle_maskedWalletDetailsButtonBackground, "maskedWalletDetailsButtonBackgroundColor", "maskedWalletDetailsButtonBackgroundResource");
        this.zzb((TypedArray)context, R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoTextColor, "maskedWalletDetailsLogoTextColor");
        this.zzb((TypedArray)context, R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoImageType, "maskedWalletDetailsLogoImageType");
        context.recycle();
    }
}

