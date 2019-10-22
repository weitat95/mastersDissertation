/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Log
 */
package android.support.customtabs;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.ICustomTabsCallback;
import android.util.Log;

public class CustomTabsSessionToken {
    private final CustomTabsCallback mCallback;
    private final ICustomTabsCallback mCallbackBinder;

    CustomTabsSessionToken(ICustomTabsCallback iCustomTabsCallback) {
        this.mCallbackBinder = iCustomTabsCallback;
        this.mCallback = new CustomTabsCallback(){

            @Override
            public void extraCallback(String string2, Bundle bundle) {
                try {
                    CustomTabsSessionToken.this.mCallbackBinder.extraCallback(string2, bundle);
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.e((String)"CustomTabsSessionToken", (String)"RemoteException during ICustomTabsCallback transaction");
                    return;
                }
            }

            @Override
            public void onMessageChannelReady(Bundle bundle) {
                try {
                    CustomTabsSessionToken.this.mCallbackBinder.onMessageChannelReady(bundle);
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.e((String)"CustomTabsSessionToken", (String)"RemoteException during ICustomTabsCallback transaction");
                    return;
                }
            }

            @Override
            public void onNavigationEvent(int n, Bundle bundle) {
                try {
                    CustomTabsSessionToken.this.mCallbackBinder.onNavigationEvent(n, bundle);
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.e((String)"CustomTabsSessionToken", (String)"RemoteException during ICustomTabsCallback transaction");
                    return;
                }
            }

            @Override
            public void onPostMessage(String string2, Bundle bundle) {
                try {
                    CustomTabsSessionToken.this.mCallbackBinder.onPostMessage(string2, bundle);
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.e((String)"CustomTabsSessionToken", (String)"RemoteException during ICustomTabsCallback transaction");
                    return;
                }
            }
        };
    }

    public boolean equals(Object object) {
        if (!(object instanceof CustomTabsSessionToken)) {
            return false;
        }
        return ((CustomTabsSessionToken)object).getCallbackBinder().equals((Object)this.mCallbackBinder.asBinder());
    }

    IBinder getCallbackBinder() {
        return this.mCallbackBinder.asBinder();
    }

    public int hashCode() {
        return this.getCallbackBinder().hashCode();
    }

}

