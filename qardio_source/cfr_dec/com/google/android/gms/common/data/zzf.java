/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.database.CursorWindow
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzbfn;

public final class zzf
implements Parcelable.Creator<DataHolder> {
    public final /* synthetic */ Object createFromParcel(Parcel object) {
        int n = 0;
        Bundle bundle = null;
        int n2 = zzbfn.zzd((Parcel)object);
        CursorWindow[] arrcursorWindow = null;
        String[] arrstring = null;
        int n3 = 0;
        block7 : while (object.dataPosition() < n2) {
            int n4 = object.readInt();
            switch (0xFFFF & n4) {
                default: {
                    zzbfn.zzb((Parcel)object, n4);
                    continue block7;
                }
                case 1: {
                    arrstring = zzbfn.zzaa((Parcel)object, n4);
                    continue block7;
                }
                case 2: {
                    arrcursorWindow = (CursorWindow[])zzbfn.zzb((Parcel)object, n4, CursorWindow.CREATOR);
                    continue block7;
                }
                case 3: {
                    n = zzbfn.zzg((Parcel)object, n4);
                    continue block7;
                }
                case 4: {
                    bundle = zzbfn.zzs((Parcel)object, n4);
                    continue block7;
                }
                case 1000: 
            }
            n3 = zzbfn.zzg((Parcel)object, n4);
        }
        zzbfn.zzaf((Parcel)object, n2);
        object = new DataHolder(n3, arrstring, arrcursorWindow, n, bundle);
        ((DataHolder)object).zzajz();
        return object;
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new DataHolder[n];
    }
}

