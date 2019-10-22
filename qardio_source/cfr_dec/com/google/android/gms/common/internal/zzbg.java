/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.zzbh;
import com.google.android.gms.common.internal.zzbi;

public final class zzbg {
    public static boolean equal(Object object, Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }

    public static zzbi zzx(Object object) {
        return new zzbi(object, null);
    }
}

