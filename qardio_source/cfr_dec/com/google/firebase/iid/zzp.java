/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.firebase.iid;

import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.iid.MessengerCompat;

final class zzp {
    private final Messenger zzifn;
    private final MessengerCompat zznzi;

    /*
     * Enabled aggressive block sorting
     */
    zzp(IBinder object) throws RemoteException {
        String string2 = object.getInterfaceDescriptor();
        if ("android.os.IMessenger".equals(string2)) {
            this.zzifn = new Messenger((IBinder)object);
            this.zznzi = null;
            return;
        }
        if ("com.google.android.gms.iid.IMessengerCompat".equals(string2)) {
            this.zznzi = new MessengerCompat((IBinder)object);
            this.zzifn = null;
            return;
        }
        object = String.valueOf(string2);
        object = ((String)object).length() != 0 ? "Invalid interface descriptor: ".concat((String)object) : new String("Invalid interface descriptor: ");
        Log.w((String)"MessengerIpcClient", (String)object);
        throw new RemoteException();
    }

    final void send(Message message) throws RemoteException {
        if (this.zzifn != null) {
            this.zzifn.send(message);
            return;
        }
        if (this.zznzi != null) {
            this.zznzi.send(message);
            return;
        }
        throw new IllegalStateException("Both messengers are null");
    }
}

