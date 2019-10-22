/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.injection;

import com.getqardio.android.mvp.common.injection.RemoteDataSourceModule;
import com.getqardio.android.mvp.common.remote.CommonHeadersInterceptor;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class RemoteDataSourceModule_ProvideCommonHeadersInterceptorFactory
implements Factory<CommonHeadersInterceptor> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final RemoteDataSourceModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RemoteDataSourceModule_ProvideCommonHeadersInterceptorFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public RemoteDataSourceModule_ProvideCommonHeadersInterceptorFactory(RemoteDataSourceModule remoteDataSourceModule) {
        if (!$assertionsDisabled && remoteDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = remoteDataSourceModule;
    }

    public static Factory<CommonHeadersInterceptor> create(RemoteDataSourceModule remoteDataSourceModule) {
        return new RemoteDataSourceModule_ProvideCommonHeadersInterceptorFactory(remoteDataSourceModule);
    }

    @Override
    public CommonHeadersInterceptor get() {
        return Preconditions.checkNotNull(this.module.provideCommonHeadersInterceptor(), "Cannot return null from a non-@Nullable @Provides method");
    }
}

