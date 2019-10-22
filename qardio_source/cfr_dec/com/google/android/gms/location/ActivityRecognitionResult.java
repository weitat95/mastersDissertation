/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbfr;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.zzb;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ActivityRecognitionResult
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<ActivityRecognitionResult> CREATOR = new zzb();
    private Bundle extras;
    private List<DetectedActivity> zziii;
    private long zziij;
    private long zziik;
    private int zziil;

    /*
     * Enabled aggressive block sorting
     */
    public ActivityRecognitionResult(List<DetectedActivity> list, long l, long l2, int n, Bundle bundle) {
        boolean bl = true;
        boolean bl2 = list != null && list.size() > 0;
        zzbq.checkArgument(bl2, "Must have at least 1 detected activity");
        bl2 = l > 0L && l2 > 0L ? bl : false;
        zzbq.checkArgument(bl2, "Must set times");
        this.zziii = list;
        this.zziij = l;
        this.zziik = l2;
        this.zziil = n;
        this.extras = bundle;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static ActivityRecognitionResult extractResult(Intent var0) {
        if (!ActivityRecognitionResult.hasResult((Intent)var0)) ** GOTO lbl-1000
        var1_1 = var0.getExtras().get("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
        if (var1_1 instanceof byte[]) {
            var1_1 = zzbfr.zza((byte[])var1_1, ActivityRecognitionResult.CREATOR);
        } else if (var1_1 instanceof ActivityRecognitionResult) {
            var1_1 = (ActivityRecognitionResult)var1_1;
        } else lbl-1000:
        // 2 sources
        {
            var1_1 = null;
        }
        if (var1_1 != null) {
            return var1_1;
        }
        if ((var0 = ActivityRecognitionResult.zzl((Intent)var0)) == null) return null;
        if (var0.isEmpty() == false) return (ActivityRecognitionResult)var0.get(var0.size() - 1);
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean hasResult(Intent object) {
        block5: {
            block4: {
                if (object == null) break block4;
                boolean bl = object == null ? false : object.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
                if (bl) {
                    return true;
                }
                if ((object = ActivityRecognitionResult.zzl((Intent)object)) != null && !object.isEmpty()) break block5;
            }
            return false;
        }
        return true;
    }

    private static boolean zzc(Bundle bundle, Bundle bundle2) {
        if (bundle == null && bundle2 == null) {
            return true;
        }
        if (bundle == null && bundle2 != null || bundle != null && bundle2 == null) {
            return false;
        }
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (String string2 : bundle.keySet()) {
            if (!bundle2.containsKey(string2)) {
                return false;
            }
            if (!(bundle.get(string2) == null ? bundle2.get(string2) != null : (bundle.get(string2) instanceof Bundle ? !ActivityRecognitionResult.zzc(bundle.getBundle(string2), bundle2.getBundle(string2)) : !bundle.get(string2).equals(bundle2.get(string2))))) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static List<ActivityRecognitionResult> zzl(Intent intent) {
        if (intent == null) {
            return null;
        }
        boolean bl = intent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT_LIST");
        if (bl) return zzbfr.zzb(intent, "com.google.android.location.internal.EXTRA_ACTIVITY_RESULT_LIST", CREATOR);
        return null;
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
                object = (ActivityRecognitionResult)object;
                if (this.zziij != ((ActivityRecognitionResult)object).zziij || this.zziik != ((ActivityRecognitionResult)object).zziik || this.zziil != ((ActivityRecognitionResult)object).zziil || !zzbg.equal(this.zziii, ((ActivityRecognitionResult)object).zziii) || !ActivityRecognitionResult.zzc(this.extras, ((ActivityRecognitionResult)object).extras)) break block5;
            }
            return true;
        }
        return false;
    }

    public List<DetectedActivity> getProbableActivities() {
        return this.zziii;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zziij, this.zziik, this.zziil, this.zziii, this.extras});
    }

    public String toString() {
        String string2 = String.valueOf(this.zziii);
        long l = this.zziij;
        long l2 = this.zziik;
        return new StringBuilder(String.valueOf(string2).length() + 124).append("ActivityRecognitionResult [probableActivities=").append(string2).append(", timeMillis=").append(l).append(", elapsedRealtimeMillis=").append(l2).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zziii, false);
        zzbfp.zza(parcel, 2, this.zziij);
        zzbfp.zza(parcel, 3, this.zziik);
        zzbfp.zzc(parcel, 4, this.zziil);
        zzbfp.zza(parcel, 5, this.extras, false);
        zzbfp.zzai(parcel, n);
    }
}

