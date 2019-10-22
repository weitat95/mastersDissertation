/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.location;

import com.google.android.gms.location.DetectedActivity;
import java.util.Comparator;

final class zzc
implements Comparator<DetectedActivity> {
    zzc() {
    }

    @Override
    public final /* synthetic */ int compare(Object object, Object object2) {
        int n;
        object = (DetectedActivity)object;
        object2 = (DetectedActivity)object2;
        int n2 = n = Integer.valueOf(((DetectedActivity)object2).getConfidence()).compareTo(((DetectedActivity)object).getConfidence());
        if (n == 0) {
            n2 = Integer.valueOf(((DetectedActivity)object).getType()).compareTo(((DetectedActivity)object2).getType());
        }
        return n2;
    }
}

