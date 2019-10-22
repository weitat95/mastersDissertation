/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.text.TextUtils
 */
package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.gms.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbf;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzca;

@Deprecated
public final class zzbz {
    private static final Object sLock = new Object();
    private static zzbz zzfty;
    private final String mAppId;
    private final Status zzftz;
    private final boolean zzfua;
    private final boolean zzfub;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private zzbz(Context context) {
        void var5_7;
        String string2;
        boolean bl = true;
        boolean bl2 = true;
        Resources resources = context.getResources();
        int n = resources.getIdentifier("google_app_measurement_enable", "integer", resources.getResourcePackageName(R.string.common_google_play_services_unknown_issue));
        if (n != 0) {
            bl = resources.getInteger(n) != 0;
            if (bl) {
                bl2 = false;
            }
            this.zzfub = bl2;
        } else {
            this.zzfub = false;
        }
        this.zzfua = bl;
        String string3 = string2 = zzbf.zzcp(context);
        if (string2 == null) {
            String string4 = new zzca(context).getString("google_app_id");
        }
        if (TextUtils.isEmpty((CharSequence)var5_7)) {
            this.zzftz = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.mAppId = null;
            return;
        }
        this.mAppId = var5_7;
        this.zzftz = Status.zzfni;
    }

    public static String zzajh() {
        return zzbz.zzfz((String)"getGoogleAppId").mAppId;
    }

    public static boolean zzaji() {
        return zzbz.zzfz((String)"isMeasurementExplicitlyDisabled").zzfub;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Status zzck(Context object) {
        zzbq.checkNotNull(object, "Context must not be null.");
        Object object2 = sLock;
        synchronized (object2) {
            if (zzfty != null) return zzbz.zzfty.zzftz;
            zzfty = new zzbz((Context)object);
            return zzbz.zzfty.zzftz;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static zzbz zzfz(String object) {
        Object object2 = sLock;
        synchronized (object2) {
            if (zzfty != null) return zzfty;
            throw new IllegalStateException(new StringBuilder(String.valueOf(object).length() + 34).append("Initialize must be called before ").append((String)object).append(".").toString());
        }
    }
}

