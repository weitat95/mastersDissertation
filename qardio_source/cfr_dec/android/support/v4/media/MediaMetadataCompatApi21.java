/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.media.MediaMetadata
 *  android.os.Parcel
 */
package android.support.v4.media;

import android.media.MediaMetadata;
import android.os.Parcel;

class MediaMetadataCompatApi21 {
    public static void writeToParcel(Object object, Parcel parcel, int n) {
        ((MediaMetadata)object).writeToParcel(parcel, n);
    }
}

