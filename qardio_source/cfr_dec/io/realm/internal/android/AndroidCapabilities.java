/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package io.realm.internal.android;

import android.os.Looper;
import io.realm.internal.Capabilities;

public class AndroidCapabilities
implements Capabilities {
    private final boolean isIntentServiceThread;
    private final Looper looper = Looper.myLooper();

    public AndroidCapabilities() {
        this.isIntentServiceThread = AndroidCapabilities.isIntentServiceThread();
    }

    private boolean hasLooper() {
        return this.looper != null;
    }

    private static boolean isIntentServiceThread() {
        String string2 = Thread.currentThread().getName();
        return string2 != null && string2.startsWith("IntentService[");
    }

    @Override
    public boolean canDeliverNotification() {
        return this.hasLooper() && !this.isIntentServiceThread;
    }
}

