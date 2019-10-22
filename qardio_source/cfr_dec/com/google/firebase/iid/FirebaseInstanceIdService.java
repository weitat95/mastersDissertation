/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.util.Log
 */
package com.google.firebase.iid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.zzb;
import com.google.firebase.iid.zzx;
import java.util.Queue;

public class FirebaseInstanceIdService
extends zzb {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void handleIntent(Intent object) {
        if ("com.google.firebase.iid.TOKEN_REFRESH".equals(object.getAction())) {
            this.onTokenRefresh();
            return;
        } else {
            String string2 = object.getStringExtra("CMD");
            if (string2 == null) return;
            {
                if (Log.isLoggable((String)"FirebaseInstanceId", (int)3)) {
                    String string3 = String.valueOf((Object)object.getExtras());
                    Log.d((String)"FirebaseInstanceId", (String)new StringBuilder(String.valueOf(string2).length() + 21 + String.valueOf(string3).length()).append("Received command: ").append(string2).append(" - ").append(string3).toString());
                }
                if ("RST".equals(string2) || "RST_FULL".equals(string2)) {
                    FirebaseInstanceId.getInstance().zzciy();
                    return;
                }
                if (!"SYNC".equals(string2)) return;
                {
                    FirebaseInstanceId.getInstance().zzciz();
                    return;
                }
            }
        }
    }

    public void onTokenRefresh() {
    }

    @Override
    protected final Intent zzp(Intent intent) {
        return zzx.zzcjk().zznzs.poll();
    }
}

