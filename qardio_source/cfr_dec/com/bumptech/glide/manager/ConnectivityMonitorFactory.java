/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.bumptech.glide.manager;

import android.content.Context;
import com.bumptech.glide.manager.ConnectivityMonitor;
import com.bumptech.glide.manager.DefaultConnectivityMonitor;
import com.bumptech.glide.manager.NullConnectivityMonitor;

public class ConnectivityMonitorFactory {
    /*
     * Enabled aggressive block sorting
     */
    public ConnectivityMonitor build(Context context, ConnectivityMonitor.ConnectivityListener connectivityListener) {
        boolean bl = context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0;
        if (bl) {
            return new DefaultConnectivityMonitor(context, connectivityListener);
        }
        return new NullConnectivityMonitor();
    }
}

