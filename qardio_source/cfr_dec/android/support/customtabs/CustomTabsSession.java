/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.RemoteException
 */
package android.support.customtabs;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.customtabs.ICustomTabsCallback;
import android.support.customtabs.ICustomTabsService;
import java.util.List;

public final class CustomTabsSession {
    private final ICustomTabsCallback mCallback;
    private final ComponentName mComponentName;
    private final Object mLock = new Object();
    private final ICustomTabsService mService;

    CustomTabsSession(ICustomTabsService iCustomTabsService, ICustomTabsCallback iCustomTabsCallback, ComponentName componentName) {
        this.mService = iCustomTabsService;
        this.mCallback = iCustomTabsCallback;
        this.mComponentName = componentName;
    }

    public boolean mayLaunchUrl(Uri uri, Bundle bundle, List<Bundle> list) {
        try {
            boolean bl = this.mService.mayLaunchUrl(this.mCallback, uri, bundle, list);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }
}

