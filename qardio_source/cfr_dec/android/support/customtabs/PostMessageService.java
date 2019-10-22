/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.RemoteException
 */
package android.support.customtabs;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.customtabs.ICustomTabsCallback;
import android.support.customtabs.IPostMessageService;

public class PostMessageService
extends Service {
    private IPostMessageService.Stub mBinder = new IPostMessageService.Stub(){

        @Override
        public void onMessageChannelReady(ICustomTabsCallback iCustomTabsCallback, Bundle bundle) throws RemoteException {
            iCustomTabsCallback.onMessageChannelReady(bundle);
        }

        @Override
        public void onPostMessage(ICustomTabsCallback iCustomTabsCallback, String string2, Bundle bundle) throws RemoteException {
            iCustomTabsCallback.onPostMessage(string2, bundle);
        }
    };

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

}

