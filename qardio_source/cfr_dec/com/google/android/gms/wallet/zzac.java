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
import com.google.android.gms.wallet.CardInfo;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentMethodToken;

public final class zzac
implements Parcelable.Creator<PaymentData> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2 = null;
        int n = zzbfn.zzd(parcel);
        PaymentMethodToken paymentMethodToken = null;
        UserAddress userAddress = null;
        CardInfo cardInfo = null;
        String string3 = null;
        block7 : while (parcel.dataPosition() < n) {
            int n2 = parcel.readInt();
            switch (0xFFFF & n2) {
                default: {
                    zzbfn.zzb(parcel, n2);
                    continue block7;
                }
                case 1: {
                    string3 = zzbfn.zzq(parcel, n2);
                    continue block7;
                }
                case 2: {
                    cardInfo = zzbfn.zza(parcel, n2, CardInfo.CREATOR);
                    continue block7;
                }
                case 3: {
                    userAddress = zzbfn.zza(parcel, n2, UserAddress.CREATOR);
                    continue block7;
                }
                case 4: {
                    paymentMethodToken = zzbfn.zza(parcel, n2, PaymentMethodToken.CREATOR);
                    continue block7;
                }
                case 5: 
            }
            string2 = zzbfn.zzq(parcel, n2);
        }
        zzbfn.zzaf(parcel, n);
        return new PaymentData(string3, cardInfo, userAddress, paymentMethodToken, string2);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new PaymentData[n];
    }
}

