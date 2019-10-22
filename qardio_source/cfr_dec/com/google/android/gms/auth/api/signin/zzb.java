/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzb
implements Parcelable.Creator<GoogleSignInAccount> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        Uri uri = null;
        String string6 = null;
        long l = 0L;
        String string7 = null;
        ArrayList<Scope> arrayList = null;
        String string8 = null;
        String string9 = null;
        block14 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block14;
                }
                case 1: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block14;
                }
                case 2: {
                    string2 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 3: {
                    string3 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 4: {
                    string4 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 5: {
                    string5 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 6: {
                    uri = (Uri)zzbfn.zza(parcel, n3, Uri.CREATOR);
                    continue block14;
                }
                case 7: {
                    string6 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 8: {
                    l = zzbfn.zzi(parcel, n3);
                    continue block14;
                }
                case 9: {
                    string7 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 10: {
                    arrayList = zzbfn.zzc(parcel, n3, Scope.CREATOR);
                    continue block14;
                }
                case 11: {
                    string8 = zzbfn.zzq(parcel, n3);
                    continue block14;
                }
                case 12: 
            }
            string9 = zzbfn.zzq(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new GoogleSignInAccount(n2, string2, string3, string4, string5, uri, string6, l, string7, arrayList, string8, string9);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new GoogleSignInAccount[n];
    }
}

