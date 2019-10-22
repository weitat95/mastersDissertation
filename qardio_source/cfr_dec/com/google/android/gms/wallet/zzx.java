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
import com.google.android.gms.wallet.InstrumentInfo;
import com.google.android.gms.wallet.LoyaltyWalletObject;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.OfferWalletObject;
import com.google.android.gms.wallet.zza;

public final class zzx
implements Parcelable.Creator<MaskedWallet> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        InstrumentInfo[] arrinstrumentInfo = null;
        int n = zzbfn.zzd(parcel);
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        OfferWalletObject[] arrofferWalletObject = null;
        LoyaltyWalletObject[] arrloyaltyWalletObject = null;
        zza zza2 = null;
        zza zza3 = null;
        String string2 = null;
        String[] arrstring = null;
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
                    arrstring = zzbfn.zzaa(parcel, n2);
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
                    arrloyaltyWalletObject = zzbfn.zzb(parcel, n2, LoyaltyWalletObject.CREATOR);
                    continue block13;
                }
                case 9: {
                    arrofferWalletObject = zzbfn.zzb(parcel, n2, OfferWalletObject.CREATOR);
                    continue block13;
                }
                case 10: {
                    userAddress2 = zzbfn.zza(parcel, n2, UserAddress.CREATOR);
                    continue block13;
                }
                case 11: {
                    userAddress = zzbfn.zza(parcel, n2, UserAddress.CREATOR);
                    continue block13;
                }
                case 12: 
            }
            arrinstrumentInfo = zzbfn.zzb(parcel, n2, InstrumentInfo.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new MaskedWallet(string4, string3, arrstring, string2, zza3, zza2, arrloyaltyWalletObject, arrofferWalletObject, userAddress2, userAddress, arrinstrumentInfo);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new MaskedWallet[n];
    }
}

