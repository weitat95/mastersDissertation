/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 */
package com.getqardio.shared.wearable;

import android.os.Bundle;
import android.os.Parcel;

public class Utils {
    public static byte[] bundle2Bytes(Bundle arrby) {
        Parcel parcel = Parcel.obtain();
        try {
            parcel.writeBundle((Bundle)arrby);
            arrby = parcel.marshall();
            return arrby;
        }
        finally {
            parcel.recycle();
        }
    }

    public static Bundle bytes2Bundle(byte[] bundle) {
        Parcel parcel = Parcel.obtain();
        try {
            parcel.unmarshall((byte[])bundle, 0, ((byte[])bundle).length);
            parcel.setDataPosition(0);
            bundle = parcel.readBundle();
            return bundle;
        }
        finally {
            parcel.recycle();
        }
    }
}

