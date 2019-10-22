/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.common.injection.ApplicationModule;
import com.getqardio.android.mvp.common.util.RxEventBus;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ApplicationModule_ProvideRxEventBusFactory
implements Factory<RxEventBus> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final ApplicationModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !ApplicationModule_ProvideRxEventBusFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public ApplicationModule_ProvideRxEventBusFactory(ApplicationModule applicationModule) {
        if (!$assertionsDisabled && applicationModule == null) {
            throw new AssertionError();
        }
        this.module = applicationModule;
    }

    public static Factory<RxEventBus> create(ApplicationModule applicationModule) {
        return new ApplicationModule_ProvideRxEventBusFactory(applicationModule);
    }

    @Override
    public RxEventBus get() {
        return Preconditions.checkNotNull(this.module.provideRxEventBus(), "Cannot return null from a non-@Nullable @Provides method");
    }
}

