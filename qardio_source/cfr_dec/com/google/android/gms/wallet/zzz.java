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
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.CountrySpecification;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import java.util.ArrayList;

public final class zzz
implements Parcelable.Creator<MaskedWalletRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        String string2 = null;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        Cart cart = null;
        boolean bl4 = false;
        boolean bl5 = false;
        CountrySpecification[] arrcountrySpecification = null;
        boolean bl6 = true;
        boolean bl7 = true;
        ArrayList<com.google.android.gms.identity.intents.model.CountrySpecification> arrayList = null;
        PaymentMethodTokenizationParameters paymentMethodTokenizationParameters = null;
        ArrayList<Integer> arrayList2 = null;
        String string6 = null;
        block19 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block19;
                }
                case 2: {
                    string2 = zzbfn.zzq(parcel, n2);
                    continue block19;
                }
                case 3: {
                    bl = zzbfn.zzc(parcel, n2);
                    continue block19;
                }
                case 4: {
                    bl2 = zzbfn.zzc(parcel, n2);
                    continue block19;
                }
                case 5: {
                    bl3 = zzbfn.zzc(parcel, n2);
                    continue block19;
                }
                case 6: {
                    string3 = zzbfn.zzq(parcel, n2);
                    continue block19;
                }
                case 7: {
                    string4 = zzbfn.zzq(parcel, n2);
                    continue block19;
                }
                case 8: {
                    string5 = zzbfn.zzq(parcel, n2);
                    continue block19;
                }
                case 9: {
                    cart = zzbfn.zza(parcel, n2, Cart.CREATOR);
                    continue block19;
                }
                case 10: {
                    bl4 = zzbfn.zzc(parcel, n2);
                    continue block19;
                }
                case 11: {
                    bl5 = zzbfn.zzc(parcel, n2);
                    continue block19;
                }
                case 12: {
                    arrcountrySpecification = zzbfn.zzb(parcel, n2, CountrySpecification.CREATOR);
                    continue block19;
                }
                case 13: {
                    bl6 = zzbfn.zzc(parcel, n2);
                    continue block19;
                }
                case 14: {
                    bl7 = zzbfn.zzc(parcel, n2);
                    continue block19;
                }
                case 15: {
                    arrayList = zzbfn.zzc(parcel, n2, com.google.android.gms.identity.intents.model.CountrySpecification.CREATOR);
                    continue block19;
                }
                case 16: {
                    paymentMethodTokenizationParameters = zzbfn.zza(parcel, n2, PaymentMethodTokenizationParameters.CREATOR);
                    continue block19;
                }
                case 17: {
                    arrayList2 = zzbfn.zzab(parcel, n2);
                    continue block19;
                }
                case 18: 
            }
            string6 = zzbfn.zzq(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new MaskedWalletRequest(string2, bl, bl2, bl3, string3, string4, string5, cart, bl4, bl5, arrcountrySpecification, bl6, bl7, arrayList, paymentMethodTokenizationParameters, arrayList2, string6);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new MaskedWalletRequest[n];
    }
}

