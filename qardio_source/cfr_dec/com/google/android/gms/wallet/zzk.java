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
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.InstrumentInfo;
import com.google.android.gms.wallet.PaymentMethodToken;
import com.google.android.gms.wallet.ProxyCard;
import com.google.android.gms.wallet.zza;

public final class zzk
implements Parcelable.Creator<FullWallet> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        PaymentMethodToken paymentMethodToken = null;
        int n = zzbfn.zzd(parcel);
        InstrumentInfo[] arrinstrumentInfo = null;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        String[] arrstring = null;
        zza zza2 = null;
        zza zza3 = null;
        String string2 = null;
        ProxyCard proxyCard = null;
        String string3 = null;
        String string4 = null;
        block13 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block13;
                }
                case 2: {
                    string4 = zzbfn.zzq(parcel, n2);
                    continue block13;
                }
                case 3: {
                    string3 = zzbfn.zzq(parcel, n2);
                    continue block13;
                }
                case 4: {
                    proxyCard = zzbfn.zza(parcel, n2, ProxyCard.CREATOR);
                    continue block13;
                }
                case 5: {
                    string2 = zzbfn.zzq(parcel, n2);
                    continue block13;
                }
                case 6: {
                    zza3 = zzbfn.zza(parcel, n2, zza.CREATOR);
                    continue block13;
                }
                case 7: {
                    zza2 = zzbfn.zza(parcel, n2, zza.CREATOR);
                    continue block13;
                }
                case 8: {
                    arrstring = zzbfn.zzaa(parcel, n2);
                    continue block13;
                }
                case 9: {
                    userAddress2 = zzbfn.zza(parcel, n2, UserAddress.CREATOR);
                    continue block13;
                }
                case 10: {
                    userAddress = zzbfn.zza(parcel, n2, UserAddress.CREATOR);
                    continue block13;
                }
                case 11: {
                    arrinstrumentInfo = zzbfn.zzb(parcel, n2, InstrumentInfo.CREATOR);
                    continue block13;
                }
                case 12: 
            }
            paymentMethodToken = zzbfn.zza(parcel, n2, PaymentMethodToken.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new FullWallet(string4, string3, proxyCard, string2, zza3, zza2, arrstring, userAddress2, userAddress, arrinstrumentInfo, paymentMethodToken);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new FullWallet[n];
    }
}

