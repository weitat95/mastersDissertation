/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzarl;
import com.google.android.gms.internal.zzarm;
import com.google.android.gms.internal.zzarv;
import java.util.HashSet;
import java.util.Set;

public final class zzard {
    private final zzaqc zzdoh;
    private volatile Boolean zzdvl;
    private String zzdvm;
    private Set<Integer> zzdvn;

    protected zzard(zzaqc zzaqc2) {
        zzbq.checkNotNull(zzaqc2);
        this.zzdoh = zzaqc2;
    }

    public static boolean zzyq() {
        return zzarl.zzdvx.get();
    }

    public static int zzyr() {
        return zzarl.zzdwu.get();
    }

    public static long zzys() {
        return zzarl.zzdwf.get();
    }

    public static long zzyt() {
        return zzarl.zzdwi.get();
    }

    public static int zzyu() {
        return zzarl.zzdwk.get();
    }

    public static int zzyv() {
        return zzarl.zzdwl.get();
    }

    public static String zzyw() {
        return zzarl.zzdwn.get();
    }

    public static String zzyx() {
        return zzarl.zzdwm.get();
    }

    public static String zzyy() {
        return zzarl.zzdwo.get();
    }

    public static long zzza() {
        return zzarl.zzdxc.get();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean zzyp() {
        if (this.zzdvl == null) {
            synchronized (this) {
                if (this.zzdvl == null) {
                    Object object = this.zzdoh.getContext().getApplicationInfo();
                    String string2 = zzs.zzamo();
                    if (object != null) {
                        object = ((ApplicationInfo)object).processName;
                        boolean bl = object != null && ((String)object).equals(string2);
                        this.zzdvl = bl;
                    }
                    if ((this.zzdvl == null || !this.zzdvl.booleanValue()) && "com.google.android.gms.analytics".equals(string2)) {
                        this.zzdvl = Boolean.TRUE;
                    }
                    if (this.zzdvl == null) {
                        this.zzdvl = Boolean.TRUE;
                        this.zzdoh.zzwt().zzdy("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzdvl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final Set<Integer> zzyz() {
        String string2 = zzarl.zzdwx.get();
        if (this.zzdvn != null && this.zzdvm != null) {
            if (this.zzdvm.equals(string2)) return this.zzdvn;
        }
        String[] arrstring = TextUtils.split((String)string2, (String)",");
        HashSet<Integer> hashSet = new HashSet<Integer>();
        int n = arrstring.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.zzdvm = string2;
                this.zzdvn = hashSet;
                return this.zzdvn;
            }
            String string3 = arrstring[n2];
            try {
                hashSet.add(Integer.parseInt(string3));
            }
            catch (NumberFormatException numberFormatException) {}
            ++n2;
        } while (true);
    }
}

