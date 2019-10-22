/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.WearableStatusCodes;

public final class zzgd {
    public static Status zzdg(int n) {
        return new Status(n, WearableStatusCodes.getStatusCodeString(n));
    }
}

