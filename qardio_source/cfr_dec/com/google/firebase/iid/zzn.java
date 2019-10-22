/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.RemoteException
 *  android.util.Log
 *  android.util.SparseArray
 */
package com.google.firebase.iid;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import com.google.firebase.iid.zzi;
import com.google.firebase.iid.zzk;
import com.google.firebase.iid.zzo;
import com.google.firebase.iid.zzp;
import com.google.firebase.iid.zzr;
import java.util.Queue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

final class zzn
implements Runnable {
    private final zzk zznzg;

    zzn(zzk zzk2) {
        this.zznzg = zzk2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final void run() {
        zzk zzk2 = this.zznzg;
        do {
            String string2;
            // MONITORENTER : zzk2
            if (zzk2.state != 2) {
                // MONITOREXIT : zzk2
                return;
            }
            if (zzk2.zznzd.isEmpty()) {
                zzk2.zzcjc();
                // MONITOREXIT : zzk2
                return;
            }
            zzr<?> zzr2 = zzk2.zznzd.poll();
            zzk2.zznze.put(zzr2.zzift, zzr2);
            zzi.zzb(zzk2.zznzf).schedule(new zzo(zzk2, zzr2), 30L, TimeUnit.SECONDS);
            // MONITOREXIT : zzk2
            if (Log.isLoggable((String)"MessengerIpcClient", (int)3)) {
                string2 = String.valueOf(zzr2);
                Log.d((String)"MessengerIpcClient", (String)new StringBuilder(String.valueOf(string2).length() + 8).append("Sending ").append(string2).toString());
            }
            string2 = zzi.zza(zzk2.zznzf);
            Messenger messenger = zzk2.zznzb;
            Message message = Message.obtain();
            message.what = zzr2.what;
            message.arg1 = zzr2.zzift;
            message.replyTo = messenger;
            messenger = new Bundle();
            messenger.putBoolean("oneWay", zzr2.zzcje());
            messenger.putString("pkg", string2.getPackageName());
            messenger.putBundle("data", zzr2.zznzj);
            message.setData((Bundle)messenger);
            try {
                zzk2.zznzc.send(message);
                continue;
            }
            catch (RemoteException remoteException) {
                zzk2.zzm(2, remoteException.getMessage());
                continue;
            }
            break;
        } while (true);
    }
}

