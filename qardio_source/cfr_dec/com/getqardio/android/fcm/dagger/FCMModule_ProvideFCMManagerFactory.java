/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.fcm.dagger;

import com.getqardio.android.fcm.FCMManager;
import com.getqardio.android.fcm.dagger.FCMModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class FCMModule_ProvideFCMManagerFactory
implements Factory<FCMManager> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final FCMModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !FCMModule_ProvideFCMManagerFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public FCMModule_ProvideFCMManagerFactory(FCMModule fCMModule) {
        if (!$assertionsDisabled && fCMModule == null) {
            throw new AssertionError();
        }
        this.module = fCMModule;
    }

    public static Factory<FCMManager> create(FCMModule fCMModule) {
        return new FCMModule_ProvideFCMManagerFactory(fCMModule);
    }

    @Override
    public FCMManager get() {
        return Preconditions.checkNotNull(this.module.provideFCMManager(), "Cannot return null from a non-@Nullable @Provides method");
    }
}

