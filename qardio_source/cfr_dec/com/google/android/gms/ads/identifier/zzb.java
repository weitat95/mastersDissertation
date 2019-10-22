/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.util.Log
 */
package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.common.zzp;

public final class zzb {
    private SharedPreferences zzani;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public zzb(Context object) {
        try {
            object = zzp.getRemoteContext(object);
            object = object == null ? null : object.getSharedPreferences("google_ads_flags", 0);
            this.zzani = object;
            return;
        }
        catch (Throwable throwable) {
            Log.w((String)"GmscoreFlag", (String)"Error while getting SharedPreferences ", (Throwable)throwable);
            this.zzani = null;
            return;
        }
    }

    public final boolean getBoolean(String string2, boolean bl) {
        block3: {
            try {
                if (this.zzani != null) break block3;
                return false;
            }
            catch (Throwable throwable) {
                Log.w((String)"GmscoreFlag", (String)"Error while reading from SharedPreferences ", (Throwable)throwable);
                return false;
            }
        }
        bl = this.zzani.getBoolean(string2, false);
        return bl;
    }

    final float getFloat(String string2, float f) {
        block3: {
            try {
                if (this.zzani != null) break block3;
                return 0.0f;
            }
            catch (Throwable throwable) {
                Log.w((String)"GmscoreFlag", (String)"Error while reading from SharedPreferences ", (Throwable)throwable);
                return 0.0f;
            }
        }
        f = this.zzani.getFloat(string2, 0.0f);
        return f;
    }

    final String getString(String string2, String string3) {
        block3: {
            try {
                if (this.zzani != null) break block3;
                return string3;
            }
            catch (Throwable throwable) {
                Log.w((String)"GmscoreFlag", (String)"Error while reading from SharedPreferences ", (Throwable)throwable);
                return string3;
            }
        }
        string2 = this.zzani.getString(string2, string3);
        return string2;
    }
}

