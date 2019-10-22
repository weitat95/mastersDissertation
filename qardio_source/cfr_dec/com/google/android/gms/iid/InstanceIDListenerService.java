/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.util.Log
 */
package com.google.android.gms.iid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.zzb;
import com.google.android.gms.iid.zzo;

public class InstanceIDListenerService
extends zzb {
    static void zza(Context context, zzo zzo2) {
        zzo2.zzavj();
        zzo2 = new Intent("com.google.android.gms.iid.InstanceID");
        zzo2.putExtra("CMD", "RST");
        zzo2.setClassName(context, "com.google.android.gms.gcm.GcmReceiver");
        context.sendBroadcast((Intent)zzo2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void handleIntent(Intent intent) {
        if (!"com.google.android.gms.iid.InstanceID".equals(intent.getAction())) return;
        Object object = null;
        String string2 = intent.getStringExtra("subtype");
        if (string2 != null) {
            object = new Bundle();
            object.putString("subtype", string2);
        }
        object = InstanceID.getInstance((Context)this, object);
        String string3 = intent.getStringExtra("CMD");
        if (Log.isLoggable((String)"InstanceID", (int)3)) {
            Log.d((String)"InstanceID", (String)new StringBuilder(String.valueOf(string2).length() + 34 + String.valueOf(string3).length()).append("Service command. subtype:").append(string2).append(" command:").append(string3).toString());
        }
        if ("gcm.googleapis.com/refresh".equals(intent.getStringExtra("from"))) {
            InstanceID.zzavg().zzia(string2);
            this.onTokenRefresh();
            return;
        }
        if ("RST".equals(string3)) {
            ((InstanceID)object).zzavf();
            this.onTokenRefresh();
            return;
        }
        if ("RST_FULL".equals(string3)) {
            if (InstanceID.zzavg().isEmpty()) return;
            {
                InstanceID.zzavg().zzavj();
                this.onTokenRefresh();
                return;
            }
        }
        if (!"SYNC".equals(string3)) {
            return;
        }
        InstanceID.zzavg().zzia(string2);
        this.onTokenRefresh();
    }

    public void onTokenRefresh() {
    }
}

