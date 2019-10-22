/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package io.realm.internal.android;

import android.os.Handler;
import android.os.Looper;
import io.realm.internal.Capabilities;
import io.realm.internal.Keep;
import io.realm.internal.RealmNotifier;
import io.realm.internal.SharedRealm;

@Keep
public class AndroidRealmNotifier
extends RealmNotifier {
    private Handler handler;

    public AndroidRealmNotifier(SharedRealm sharedRealm, Capabilities capabilities) {
        super(sharedRealm);
        if (capabilities.canDeliverNotification()) {
            this.handler = new Handler(Looper.myLooper());
            return;
        }
        this.handler = null;
    }

    @Override
    public boolean post(Runnable runnable) {
        return this.handler != null && this.handler.post(runnable);
    }
}

