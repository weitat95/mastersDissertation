/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.text.TextUtils
 *  org.json.JSONException
 */
package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.zzbq;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;

public final class zzz {
    private static final Lock zzeiu = new ReentrantLock();
    private static zzz zzeiv;
    private final Lock zzeiw = new ReentrantLock();
    private final SharedPreferences zzeix;

    private zzz(Context context) {
        this.zzeix = context.getSharedPreferences("com.google.android.gms.signin", 0);
    }

    public static zzz zzbt(Context object) {
        zzbq.checkNotNull(object);
        zzeiu.lock();
        try {
            if (zzeiv == null) {
                zzeiv = new zzz(object.getApplicationContext());
            }
            object = zzeiv;
            return object;
        }
        finally {
            zzeiu.unlock();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final GoogleSignInAccount zzex(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        if ((object = this.zzez(zzz.zzp("googleSignInAccount", (String)object))) == null) return null;
        try {
            return GoogleSignInAccount.zzeu((String)object);
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    private final String zzez(String string2) {
        this.zzeiw.lock();
        try {
            string2 = this.zzeix.getString(string2, null);
            return string2;
        }
        finally {
            this.zzeiw.unlock();
        }
    }

    private static String zzp(String string2, String string3) {
        return new StringBuilder(String.valueOf(string2).length() + String.valueOf(":").length() + String.valueOf(string3).length()).append(string2).append(":").append(string3).toString();
    }

    public final GoogleSignInAccount zzabt() {
        return this.zzex(this.zzez("defaultGoogleSignInAccount"));
    }
}

