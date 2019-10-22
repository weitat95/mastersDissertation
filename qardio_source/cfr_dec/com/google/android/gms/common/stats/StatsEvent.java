/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.stats;

import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbfm;

public abstract class StatsEvent
extends zzbfm
implements ReflectedParcelable {
    public abstract int getEventType();

    public abstract long getTimeMillis();

    public String toString() {
        long l = this.getTimeMillis();
        int n = this.getEventType();
        long l2 = this.zzamd();
        String string2 = this.zzame();
        return new StringBuilder(String.valueOf("\t").length() + 51 + String.valueOf("\t").length() + String.valueOf(string2).length()).append(l).append("\t").append(n).append("\t").append(l2).append(string2).toString();
    }

    public abstract long zzamd();

    public abstract String zzame();
}

