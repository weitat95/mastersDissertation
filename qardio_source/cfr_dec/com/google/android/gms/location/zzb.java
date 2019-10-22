/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;
import java.util.List;

public final class zzb
implements Parcelable.Creator<ActivityRecognitionResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long l = 0L;
        Bundle bundle = null;
        int n = zzbfn.zzd(parcel);
        int n2 = 0;
        long l2 = 0L;
        ArrayList<DetectedActivity> arrayList = null;
        block7 : while (parcel.dataPosition() < n) {
            int n3 = parcel.readInt();
            switch (0xFFFF & n3) {
                default: {
                    zzbfn.zzb(parcel, n3);
                    continue block7;
                }
                case 1: {
                    arrayList = zzbfn.zzc(parcel, n3, DetectedActivity.CREATOR);
                    continue block7;
                }
                case 2: {
                    l2 = zzbfn.zzi(parcel, n3);
                    continue block7;
                }
                case 3: {
                    l = zzbfn.zzi(parcel, n3);
                    continue block7;
                }
                case 4: {
                    n2 = zzbfn.zzg(parcel, n3);
                    continue block7;
                }
                case 5: 
            }
            bundle = zzbfn.zzs(parcel, n3);
        }
        zzbfn.zzaf(parcel, n);
        return new ActivityRecognitionResult(arrayList, l2, l, n2, bundle);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new ActivityRecognitionResult[n];
    }
}

