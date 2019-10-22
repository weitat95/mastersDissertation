/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule;
import com.google.gson.Gson;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class RemoteDataSourceModule_ProvideGsonFactory
implements Factory<Gson> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final RemoteDataSourceModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RemoteDataSourceModule_ProvideGsonFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RemoteDataSourceModule_ProvideGsonFactory(RemoteDataSourceModule remoteDataSourceModule) {
        if (!$assertionsDisabled && remoteDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = remoteDataSourceModule;
    }

    public static Factory<Gson> create(RemoteDataSourceModule remoteDataSourceModule) {
        return new RemoteDataSourceModule_ProvideGsonFactory(remoteDataSourceModule);
    }

    @Override
    public Gson get() {
        return Preconditions.checkNotNull(this.module.provideGson(), "Cannot return null from a non-@Nullable @Provides method");
    }
}

