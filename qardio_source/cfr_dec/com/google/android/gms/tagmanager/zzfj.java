/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzdjc;
import com.google.android.gms.internal.zzdjg;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzfj {
    private final Set<zzdjg> zzkit = new HashSet<zzdjg>();
    private final Map<zzdjg, List<zzdjc>> zzkjd = new HashMap<zzdjg, List<zzdjc>>();
    private final Map<zzdjg, List<zzdjc>> zzkje;
    private final Map<zzdjg, List<String>> zzkjf = new HashMap<zzdjg, List<String>>();
    private final Map<zzdjg, List<String>> zzkjg;
    private zzdjc zzkjh;

    public zzfj() {
        this.zzkje = new HashMap<zzdjg, List<zzdjc>>();
        this.zzkjg = new HashMap<zzdjg, List<String>>();
    }

    public final Set<zzdjg> zzbfz() {
        return this.zzkit;
    }

    public final Map<zzdjg, List<zzdjc>> zzbga() {
        return this.zzkjd;
    }

    public final Map<zzdjg, List<String>> zzbgb() {
        return this.zzkjf;
    }

    public final Map<zzdjg, List<String>> zzbgc() {
        return this.zzkjg;
    }

    public final Map<zzdjg, List<zzdjc>> zzbgd() {
        return this.zzkje;
    }

    public final zzdjc zzbge() {
        return this.zzkjh;
    }
}

