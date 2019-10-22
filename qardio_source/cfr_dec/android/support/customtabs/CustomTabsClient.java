/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.RemoteException
 *  android.text.TextUtils
 */
package android.support.customtabs;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.customtabs.ICustomTabsCallback;
import android.support.customtabs.ICustomTabsService;
import android.text.TextUtils;

public class CustomTabsClient {
    private final ICustomTabsService mService;
    private final ComponentName mServiceComponentName;

    CustomTabsClient(ICustomTabsService iCustomTabsService, ComponentName componentName) {
        this.mService = iCustomTabsService;
        this.mServiceComponentName = componentName;
    }

    public static boolean bindCustomTabsService(Context context, String string2, CustomTabsServiceConnection customTabsServiceConnection) {
        Intent intent = new Intent("android.support.customtabs.action.CustomTabsService");
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            intent.setPackage(string2);
        }
        return context.bindService(intent, (ServiceConnection)customTabsServiceConnection, 33);
    }

    public CustomTabsSession newSession(CustomTabsCallback object) {
        object = new ICustomTabsCallback.Stub((CustomTabsCallback)object){
            private Handler mHandler = new Handler(Looper.getMainLooper());
            final /* synthetic */ CustomTabsCallback val$callback;
            {
                this.val$callback = customTabsCallback;
            }

            @Override
            public void extraCallback(final String string2, final Bundle bundle) throws RemoteException {
                if (this.val$callback == null) {
                    return;
                }
                this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        val$callback.extraCallback(string2, bundle);
                    }
                });
            }

            @Override
            public void onMessageChannelReady(final Bundle bundle) throws RemoteException {
                if (this.val$callback == null) {
                    return;
                }
                this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        val$callback.onMessageChannelReady(bundle);
                    }
                });
            }

            @Override
            public void onNavigationEvent(final int n, final Bundle bundle) {
                if (this.val$callback == null) {
                    return;
                }
                this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        val$callback.onNavigationEvent(n, bundle);
                    }
                });
            }

            @Override
            public void onPostMessage(final String string2, final Bundle bundle) throws RemoteException {
                if (this.val$callback == null) {
                    return;
                }
                this.mHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        val$callback.onPostMessage(string2, bundle);
                    }
                });
            }

        };
        try {
            boolean bl = this.mService.newSession((ICustomTabsCallback)object);
            if (!bl) {
                return null;
            }
        }
        catch (RemoteException remoteException) {
            return null;
        }
        return new CustomTabsSession(this.mService, (ICustomTabsCallback)object, this.mServiceComponentName);
    }

    public boolean warmup(long l) {
        try {
            boolean bl = this.mService.warmup(l);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

}

