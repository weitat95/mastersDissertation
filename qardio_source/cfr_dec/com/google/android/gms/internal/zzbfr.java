/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfq;
import java.io.Serializable;
import java.util.ArrayList;

public final class zzbfr {
    public static <T extends zzbfq> T zza(byte[] object, Parcelable.Creator<T> creator) {
        zzbq.checkNotNull(creator);
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(object, 0, ((byte[])object).length);
        parcel.setDataPosition(0);
        object = (zzbfq)creator.createFromParcel(parcel);
        parcel.recycle();
        return (T)object;
    }

    public static <T extends zzbfq> ArrayList<T> zzb(Intent object, String object2, Parcelable.Creator<T> creator) {
        if ((object2 = (ArrayList)object.getSerializableExtra((String)object2)) == null) {
            return null;
        }
        object = new ArrayList(((ArrayList)object2).size());
        object2 = (ArrayList)object2;
        int n = ((ArrayList)object2).size();
        for (int i = 0; i < n; ++i) {
            Object e = ((ArrayList)object2).get(i);
            ((ArrayList)object).add(zzbfr.zza((byte[])e, creator));
        }
        return object;
    }
}

