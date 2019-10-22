/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.RemoteException
 */
package android.support.customtabs;

import android.app.Service;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.customtabs.CustomTabsSessionToken;
import android.support.customtabs.ICustomTabsCallback;
import android.support.customtabs.ICustomTabsService;
import android.support.v4.util.ArrayMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class CustomTabsService
extends Service {
    private ICustomTabsService.Stub mBinder;
    private final Map<IBinder, IBinder.DeathRecipient> mDeathRecipientMap = new ArrayMap<IBinder, IBinder.DeathRecipient>();

    public CustomTabsService() {
        this.mBinder = new ICustomTabsService.Stub(){

            @Override
            public Bundle extraCommand(String string2, Bundle bundle) {
                return CustomTabsService.this.extraCommand(string2, bundle);
            }

            @Override
            public boolean mayLaunchUrl(ICustomTabsCallback iCustomTabsCallback, Uri uri, Bundle bundle, List<Bundle> list) {
                return CustomTabsService.this.mayLaunchUrl(new CustomTabsSessionToken(iCustomTabsCallback), uri, bundle, list);
            }

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean newSession(ICustomTabsCallback iCustomTabsCallback) {
                IBinder.DeathRecipient deathRecipient;
                final CustomTabsSessionToken customTabsSessionToken = new CustomTabsSessionToken(iCustomTabsCallback);
                try {
                    deathRecipient = new IBinder.DeathRecipient(){

                        public void binderDied() {
                            CustomTabsService.this.cleanUpSession(customTabsSessionToken);
                        }
                    };
                    Map map = CustomTabsService.this.mDeathRecipientMap;
                    synchronized (map) {
                    }
                }
                catch (RemoteException remoteException) {
                    return false;
                }
                {
                    iCustomTabsCallback.asBinder().linkToDeath(deathRecipient, 0);
                    CustomTabsService.this.mDeathRecipientMap.put(iCustomTabsCallback.asBinder(), deathRecipient);
                    return CustomTabsService.this.newSession(customTabsSessionToken);
                }
            }

            @Override
            public int postMessage(ICustomTabsCallback iCustomTabsCallback, String string2, Bundle bundle) {
                return CustomTabsService.this.postMessage(new CustomTabsSessionToken(iCustomTabsCallback), string2, bundle);
            }

            @Override
            public boolean requestPostMessageChannel(ICustomTabsCallback iCustomTabsCallback, Uri uri) {
                return CustomTabsService.this.requestPostMessageChannel(new CustomTabsSessionToken(iCustomTabsCallback), uri);
            }

            @Override
            public boolean updateVisuals(ICustomTabsCallback iCustomTabsCallback, Bundle bundle) {
                return CustomTabsService.this.updateVisuals(new CustomTabsSessionToken(iCustomTabsCallback), bundle);
            }

            @Override
            public boolean warmup(long l) {
                return CustomTabsService.this.warmup(l);
            }

        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean cleanUpSession(CustomTabsSessionToken customTabsSessionToken) {
        try {
            Map<IBinder, IBinder.DeathRecipient> map = this.mDeathRecipientMap;
            synchronized (map) {
            }
        }
        catch (NoSuchElementException noSuchElementException) {
            return false;
        }
        {
            customTabsSessionToken = customTabsSessionToken.getCallbackBinder();
            customTabsSessionToken.unlinkToDeath(this.mDeathRecipientMap.get(customTabsSessionToken), 0);
            this.mDeathRecipientMap.remove(customTabsSessionToken);
            return true;
        }
    }

    protected abstract Bundle extraCommand(String var1, Bundle var2);

    protected abstract boolean mayLaunchUrl(CustomTabsSessionToken var1, Uri var2, Bundle var3, List<Bundle> var4);

    protected abstract boolean newSession(CustomTabsSessionToken var1);

    protected abstract int postMessage(CustomTabsSessionToken var1, String var2, Bundle var3);

    protected abstract boolean requestPostMessageChannel(CustomTabsSessionToken var1, Uri var2);

    protected abstract boolean updateVisuals(CustomTabsSessionToken var1, Bundle var2);

    protected abstract boolean warmup(long var1);

}

