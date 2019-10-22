/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzdjc;
import java.util.List;

public final class zzdjg {
    private final List<zzdjc> zzksi;
    private final List<zzdjc> zzksj;
    private final List<zzdjc> zzksk;
    private final List<zzdjc> zzksl;
    private final List<zzdjc> zzktq;
    private final List<zzdjc> zzktr;

    public final String toString() {
        String string2 = String.valueOf(this.zzksi);
        String string3 = String.valueOf(this.zzksj);
        String string4 = String.valueOf(this.zzksk);
        String string5 = String.valueOf(this.zzksl);
        String string6 = String.valueOf(this.zzktq);
        String string7 = String.valueOf(this.zzktr);
        return new StringBuilder(String.valueOf(string2).length() + 102 + String.valueOf(string3).length() + String.valueOf(string4).length() + String.valueOf(string5).length() + String.valueOf(string6).length() + String.valueOf(string7).length()).append("Positive predicates: ").append(string2).append("  Negative predicates: ").append(string3).append("  Add tags: ").append(string4).append("  Remove tags: ").append(string5).append("  Add macros: ").append(string6).append("  Remove macros: ").append(string7).toString();
    }

    public final List<zzdjc> zzbim() {
        return this.zzksi;
    }

    public final List<zzdjc> zzbin() {
        return this.zzksj;
    }

    public final List<zzdjc> zzbio() {
        return this.zzksk;
    }

    public final List<zzdjc> zzbip() {
        return this.zzksl;
    }
}

