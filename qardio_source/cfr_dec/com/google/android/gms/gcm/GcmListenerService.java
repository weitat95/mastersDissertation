/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.util.Log
 */
package com.google.android.gms.gcm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.gcm.zza;
import com.google.android.gms.iid.zzb;
import java.util.Iterator;
import java.util.Set;

public class GcmListenerService
extends zzb {
    static void zzq(Bundle object) {
        object = object.keySet().iterator();
        while (object.hasNext()) {
            String string2 = (String)object.next();
            if (string2 == null || !string2.startsWith("google.c.")) continue;
            object.remove();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void handleIntent(Intent object) {
        String string2;
        if (!"com.google.android.c2dm.intent.RECEIVE".equals(object.getAction())) {
            object = ((String)(object = String.valueOf(object.getAction()))).length() != 0 ? "Unknown intent action: ".concat((String)object) : new String("Unknown intent action: ");
            Log.w((String)"GcmListenerService", (String)object);
            return;
        }
        String string3 = string2 = object.getStringExtra("message_type");
        if (string2 == null) {
            string3 = "gcm";
        }
        int n = -1;
        switch (string3.hashCode()) {
            case 102161: {
                if (!string3.equals("gcm")) break;
                n = 0;
                break;
            }
            case -2062414158: {
                if (!string3.equals("deleted_messages")) break;
                n = 1;
                break;
            }
            case 814800675: {
                if (!string3.equals("send_event")) break;
                n = 2;
                break;
            }
            case 814694033: {
                if (!string3.equals("send_error")) break;
                n = 3;
                break;
            }
        }
        switch (n) {
            default: {
                object = String.valueOf(string3);
                object = ((String)object).length() != 0 ? "Received message with unknown type: ".concat((String)object) : new String("Received message with unknown type: ");
            }
            case 0: {
                object = object.getExtras();
                object.remove("message_type");
                object.remove("android.support.content.wakelockid");
                n = "1".equals(zza.zze((Bundle)object, "gcm.n.e")) || zza.zze((Bundle)object, "gcm.n.icon") != null ? 1 : 0;
                if (n != 0) {
                    if (!zza.zzdk((Context)this)) {
                        zza.zzdj((Context)this).zzs((Bundle)object);
                        return;
                    }
                    zza.zzr((Bundle)object);
                }
                string3 = object.getString("from");
                object.remove("from");
                GcmListenerService.zzq((Bundle)object);
                this.onMessageReceived(string3, (Bundle)object);
                return;
            }
            case 1: {
                this.onDeletedMessages();
                return;
            }
            case 2: {
                this.onMessageSent(object.getStringExtra("google.message_id"));
                return;
            }
            case 3: {
                string3 = string2 = object.getStringExtra("google.message_id");
                if (string2 == null) {
                    string3 = object.getStringExtra("message_id");
                }
                this.onSendError(string3, object.getStringExtra("error"));
                return;
            }
        }
        Log.w((String)"GcmListenerService", (String)object);
    }

    public void onDeletedMessages() {
    }

    public void onMessageReceived(String string2, Bundle bundle) {
    }

    public void onMessageSent(String string2) {
    }

    public void onSendError(String string2, String string3) {
    }
}

