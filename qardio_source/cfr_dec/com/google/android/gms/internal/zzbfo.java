/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;

public final class zzbfo
extends RuntimeException {
    public zzbfo(String string2, Parcel parcel) {
        int n = parcel.dataPosition();
        int n2 = parcel.dataSize();
        super(new StringBuilder(String.valueOf(string2).length() + 41).append(string2).append(" Parcel: pos=").append(n).append(" size=").append(n2).toString());
    }
}

