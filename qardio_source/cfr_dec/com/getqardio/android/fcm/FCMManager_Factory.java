/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 */
package com.getqardio.android.fcm;

import android.content.SharedPreferences;
import com.getqardio.android.fcm.FCMManager;
import com.getqardio.android.fcm.api.PushNotificationApi;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class FCMManager_Factory
implements Factory<FCMManager> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<PushNotificationApi> apiProvider;
    private final Provider<SharedPreferences> prefsProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !FCMManager_Factory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public FCMManager_Factory(Provider<PushNotificationApi> provider, Provider<SharedPreferences> provider2) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.apiProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.prefsProvider = provider2;
    }

    public static Factory<FCMManager> create(Provider<PushNotificationApi> provider, Provider<SharedPreferences> provider2) {
        return new FCMManager_Factory(provider, provider2);
    }

    @Override
    public FCMManager get() {
        return new FCMManager(this.apiProvider.get(), this.prefsProvider.get());
    }
}

