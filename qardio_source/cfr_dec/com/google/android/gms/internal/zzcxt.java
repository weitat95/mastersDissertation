/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.os.WorkSource
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.stats.zzc;
import com.google.android.gms.common.stats.zze;
import com.google.android.gms.common.util.zzu;
import com.google.android.gms.common.util.zzy;
import java.util.HashMap;
import java.util.Map;

public final class zzcxt {
    private static boolean DEBUG;
    private static String TAG;
    private static String zzkcd;
    private final Context mContext;
    private final String zzgdn;
    private final String zzgdp;
    private final PowerManager.WakeLock zzkce;
    private WorkSource zzkcf;
    private final int zzkcg;
    private final String zzkch;
    private boolean zzkci;
    private final Map<String, Integer[]> zzkcj;
    private int zzkck;

    static {
        TAG = "WakeLock";
        zzkcd = "*gcore*:";
        DEBUG = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public zzcxt(Context context, int n, String string2) {
        String string3 = context == null ? null : context.getPackageName();
        this(context, 1, string2, null, string3);
    }

    @SuppressLint(value={"UnwrappedWakeLock"})
    private zzcxt(Context context, int n, String string2, String string3, String string4) {
        this(context, 1, string2, null, string4, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @SuppressLint(value={"UnwrappedWakeLock"})
    private zzcxt(Context context, int n, String string2, String string3, String string4, String string5) {
        this.zzkci = true;
        this.zzkcj = new HashMap<String, Integer[]>();
        zzbq.zzh(string2, "Wake lock name can NOT be empty");
        this.zzkcg = n;
        this.zzkch = null;
        this.zzgdp = null;
        this.mContext = context.getApplicationContext();
        if (!"com.google.android.gms".equals(context.getPackageName())) {
            string3 = String.valueOf(zzkcd);
            string5 = String.valueOf(string2);
            string3 = string5.length() != 0 ? string3.concat(string5) : new String(string3);
            this.zzgdn = string3;
        } else {
            this.zzgdn = string2;
        }
        this.zzkce = ((PowerManager)context.getSystemService("power")).newWakeLock(n, string2);
        if (!zzy.zzcy(this.mContext)) return;
        string2 = string4;
        if (zzu.zzgs(string4)) {
            string2 = context.getPackageName();
        }
        this.zzkcf = zzy.zzaa(context, string2);
        context = this.zzkcf;
        if (context == null) return;
        if (!zzy.zzcy(this.mContext)) return;
        if (this.zzkcf != null) {
            this.zzkcf.add((WorkSource)context);
        } else {
            this.zzkcf = context;
        }
        context = this.zzkcf;
        try {
            this.zzkce.setWorkSource((WorkSource)context);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.wtf((String)TAG, (String)illegalArgumentException.toString());
            return;
        }
    }

    private final String zzkz(String string2) {
        if (this.zzkci) {
            if (!TextUtils.isEmpty(null)) {
                return null;
            }
            return this.zzkch;
        }
        return this.zzkch;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void acquire(long l) {
        block11: {
            String string2 = this.zzkz(null);
            synchronized (this) {
                block10: {
                    block9: {
                        boolean bl;
                        if (!(this.zzkcj.isEmpty() && this.zzkck <= 0 || this.zzkce.isHeld())) {
                            this.zzkcj.clear();
                            this.zzkck = 0;
                        }
                        if (!this.zzkci) break block9;
                        Integer[] arrinteger = this.zzkcj.get(string2);
                        if (arrinteger == null) {
                            this.zzkcj.put(string2, new Integer[]{1});
                            bl = true;
                        } else {
                            arrinteger[0] = arrinteger[0] + 1;
                            bl = false;
                        }
                        if (bl) break block10;
                    }
                    if (this.zzkci || this.zzkck != 0) break block11;
                }
                zze.zzamf();
                zze.zza(this.mContext, zzc.zza(this.zzkce, string2), 7, this.zzgdn, string2, null, this.zzkcg, zzy.zzb(this.zzkcf), 1000L);
                ++this.zzkck;
            }
        }
        this.zzkce.acquire(1000L);
    }

    public final boolean isHeld() {
        return this.zzkce.isHeld();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void release() {
        block14: {
            String string2 = this.zzkz(null);
            synchronized (this) {
                block13: {
                    block12: {
                        boolean bl;
                        if (!this.zzkci) break block12;
                        Integer[] arrinteger = this.zzkcj.get(string2);
                        if (arrinteger == null) {
                            bl = false;
                        } else if (arrinteger[0] == 1) {
                            this.zzkcj.remove(string2);
                            bl = true;
                        } else {
                            arrinteger[0] = arrinteger[0] - 1;
                            bl = false;
                        }
                        if (bl) break block13;
                    }
                    if (this.zzkci || this.zzkck != 1) break block14;
                }
                zze.zzamf();
                zze.zza(this.mContext, zzc.zza(this.zzkce, string2), 8, this.zzgdn, string2, null, this.zzkcg, zzy.zzb(this.zzkcf));
                --this.zzkck;
            }
        }
        try {
            this.zzkce.release();
            return;
        }
        catch (RuntimeException runtimeException) {
            return;
        }
    }

    public final void setReferenceCounted(boolean bl) {
        this.zzkce.setReferenceCounted(false);
        this.zzkci = false;
    }
}

