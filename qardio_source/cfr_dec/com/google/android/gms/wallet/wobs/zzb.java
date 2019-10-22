/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wallet.wobs.CommonWalletObject;
import com.google.android.gms.wallet.wobs.LabelValueRow;
import com.google.android.gms.wallet.wobs.TextModuleData;
import com.google.android.gms.wallet.wobs.TimeInterval;
import com.google.android.gms.wallet.wobs.UriData;
import com.google.android.gms.wallet.wobs.WalletObjectMessage;
import java.util.ArrayList;

public final class zzb
implements Parcelable.Creator<CommonWalletObject> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        String string8 = null;
        String string9 = null;
        int n2 = 0;
        ArrayList<WalletObjectMessage> arrayList = new ArrayList();
        TimeInterval timeInterval = null;
        ArrayList<LatLng> arrayList2 = new ArrayList();
        String string10 = null;
        String string11 = null;
        ArrayList<LabelValueRow> arrayList3 = new ArrayList();
        boolean bl = false;
        ArrayList<UriData> arrayList4 = new ArrayList();
        ArrayList<TextModuleData> arrayList5 = new ArrayList();
        ArrayList<UriData> arrayList6 = new ArrayList();
        block21 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block21;
                }
                case 2: {
                    string2 = zzbfn.zzq(parcel, n3);
                    continue block21;
                }
                case 3: {
                    string3 = zzbfn.zzq(parcel, n3);
                    continue block21;
                }
                case 4: {
                    string4 = zzbfn.zzq(parcel, n3);
                    continue block21;
                }
                case 5: {
                    string5 = zzbfn.zzq(parcel, n3);
                    continue block21;
                }
                case 6: {
                    string6 = zzbfn.zzq(parcel, n3);
                    continue block21;
                }
                case 7: {
                    string7 = zzbfn.zzq(parcel, n3);
                    continue block21;
                }
                case 8: {
                    string8 = zzbfn.zzq(parcel, n3);
                    continue block21;
                }
                case 9: {
                    string9 = zzbfn.zzq(parcel, n3);
                    continue block21;
                }
                case 10: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block21;
                }
                case 11: {
                    arrayList = zzbfn.zzc(parcel, n3, WalletObjectMessage.CREATOR);
                    continue block21;
                }
                case 12: {
                    timeInterval = zzbfn.zza(parcel, n3, TimeInterval.CREATOR);
                    continue block21;
                }
                case 13: {
                    arrayList2 = zzbfn.zzc(parcel, n3, LatLng.CREATOR);
                    continue block21;
                }
                case 14: {
                    string10 = zzbfn.zzq(parcel, n3);
                    continue block21;
                }
                case 15: {
                    string11 = zzbfn.zzq(parcel, n3);
                    continue block21;
                }
                case 16: {
                    arrayList3 = zzbfn.zzc(parcel, n3, LabelValueRow.CREATOR);
                    continue block21;
                }
                case 17: {
                    bl = zzbfn.zzc(parcel, n3);
                    continue block21;
                }
                case 18: {
                    arrayList4 = zzbfn.zzc(parcel, n3, UriData.CREATOR);
                    continue block21;
                }
                case 19: {
                    arrayList5 = zzbfn.zzc(parcel, n3, TextModuleData.CREATOR);
                    continue block21;
                }
                case 20: 
            }
            arrayList6 = zzbfn.zzc(parcel, n3, UriData.CREATOR);
        }
        zzbfn.zzaf(parcel, n);
        return new CommonWalletObject(string2, string3, string4, string5, string6, string7, string8, string9, n2, arrayList, timeInterval, arrayList2, string10, string11, arrayList3, bl, arrayList4, arrayList5, arrayList6);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new CommonWalletObject[n];
    }
}

