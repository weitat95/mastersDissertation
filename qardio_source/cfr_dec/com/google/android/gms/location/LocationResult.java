/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.location.zzx;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class LocationResult
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<LocationResult> CREATOR;
    static final List<Location> zziju;
    private final List<Location> zzijv;

    static {
        zziju = Collections.emptyList();
        CREATOR = new zzx();
    }

    LocationResult(List<Location> list) {
        this.zzijv = list;
    }

    public final boolean equals(Object iterator) {
        if (!(iterator instanceof LocationResult)) {
            return false;
        }
        iterator = (LocationResult)((Object)iterator);
        if (((LocationResult)iterator).zzijv.size() != this.zzijv.size()) {
            return false;
        }
        iterator = ((LocationResult)iterator).zzijv.iterator();
        Iterator<Location> iterator2 = this.zzijv.iterator();
        while (iterator.hasNext()) {
            Location location = iterator2.next();
            Location location2 = iterator.next();
            if (location.getTime() == location2.getTime()) continue;
            return false;
        }
        return true;
    }

    public final List<Location> getLocations() {
        return this.zzijv;
    }

    public final int hashCode() {
        Iterator<Location> iterator = this.zzijv.iterator();
        int n = 17;
        while (iterator.hasNext()) {
            long l = iterator.next().getTime();
            n = (int)(l ^ l >>> 32) + n * 31;
        }
        return n;
    }

    public final String toString() {
        String string2 = String.valueOf(this.zzijv);
        return new StringBuilder(String.valueOf(string2).length() + 27).append("LocationResult[locations: ").append(string2).append("]").toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.getLocations(), false);
        zzbfp.zzai(parcel, n);
    }
}

