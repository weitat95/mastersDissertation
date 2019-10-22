/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.mvp.common.injection;

import android.content.Context;
import com.getqardio.android.mvp.common.injection.ApplicationModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ApplicationModule_ProvideContextFactory
implements Factory<Context> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final ApplicationModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !ApplicationModule_ProvideContextFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public ApplicationModule_ProvideContextFactory(ApplicationModule applicationModule) {
        if (!$assertionsDisabled && applicationModule == null) {
            throw new AssertionError();
        }
        this.module = applicationModule;
    }

    public static Factory<Context> create(ApplicationModule applicationModule) {
        return new ApplicationModule_ProvideContextFactory(applicationModule);
    }

    @Override
    public Context get() {
        return Preconditions.checkNotNull(this.module.provideContext(), "Cannot return null from a non-@Nullable @Provides method");
    }
}

