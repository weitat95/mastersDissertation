/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.location.zzc;
import com.google.android.gms.location.zzd;
import java.util.Arrays;
import java.util.Comparator;

public class DetectedActivity
extends zzbfm {
    public static final Parcelable.Creator<DetectedActivity> CREATOR;
    private static Comparator<DetectedActivity> zziim;
    private static int[] zziin;
    private static int[] zziio;
    private int zziip;
    private int zziiq;

    static {
        zziim = new zzc();
        zziin = new int[]{9, 10};
        zziio = new int[]{0, 1, 2, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 16, 17};
        CREATOR = new zzd();
    }

    public DetectedActivity(int n, int n2) {
        this.zziip = n;
        this.zziiq = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (DetectedActivity)object;
                if (this.zziip != ((DetectedActivity)object).zziip || this.zziiq != ((DetectedActivity)object).zziiq) break block5;
            }
            return true;
        }
        return false;
    }

    public int getConfidence() {
        return this.zziiq;
    }

    public int getType() {
        int n;
        int n2 = n = this.zziip;
        if (n > 17) {
            n2 = 4;
        }
        return n2;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zziip, this.zziiq});
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        String string2;
        int n = this.getType();
        switch (n) {
            default: {
                string2 = Integer.toString(n);
                break;
            }
            case 0: {
                string2 = "IN_VEHICLE";
                break;
            }
            case 1: {
                string2 = "ON_BICYCLE";
                break;
            }
            case 2: {
                string2 = "ON_FOOT";
                break;
            }
            case 3: {
                string2 = "STILL";
                break;
            }
            case 4: {
                string2 = "UNKNOWN";
                break;
            }
            case 5: {
                string2 = "TILTING";
                break;
            }
            case 7: {
                string2 = "WALKING";
                break;
            }
            case 8: {
                string2 = "RUNNING";
                break;
            }
            case 16: {
                string2 = "IN_ROAD_VEHICLE";
                break;
            }
            case 17: {
                string2 = "IN_RAIL_VEHICLE";
            }
        }
        n = this.zziiq;
        return new StringBuilder(String.valueOf(string2).length() + 48).append("DetectedActivity [type=").append(string2).append(", confidence=").append(n).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zziip);
        zzbfp.zzc(parcel, 2, this.zziiq);
        zzbfp.zzai(parcel, n);
    }
}

