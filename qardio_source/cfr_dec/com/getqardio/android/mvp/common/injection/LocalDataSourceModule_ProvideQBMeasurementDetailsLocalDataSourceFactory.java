/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.mvp.common.injection;

import android.content.Context;
import com.getqardio.android.mvp.common.injection.LocalDataSourceModule;
import com.getqardio.android.mvp.common.local.AccountContextHelper;
import com.getqardio.android.mvp.qardio_base.measurement_details.model.QBMeasurementDetailsLocalDataSource;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class LocalDataSourceModule_ProvideQBMeasurementDetailsLocalDataSourceFactory
implements Factory<QBMeasurementDetailsLocalDataSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Context> contextProvider;
    private final Provider<AccountContextHelper> helperProvider;
    private final LocalDataSourceModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !LocalDataSourceModule_ProvideQBMeasurementDetailsLocalDataSourceFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public LocalDataSourceModule_ProvideQBMeasurementDetailsLocalDataSourceFactory(LocalDataSourceModule localDataSourceModule, Provider<Context> provider, Provider<AccountContextHelper> provider2) {
        if (!$assertionsDisabled && localDataSourceModule == null) {
            throw new AssertionError();
        }
        this.module = localDataSourceModule;
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.contextProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.helperProvider = provider2;
    }

    public static Factory<QBMeasurementDetailsLocalDataSource> create(LocalDataSourceModule localDataSourceModule, Provider<Context> provider, Provider<AccountContextHelper> provider2) {
        return new LocalDataSourceModule_ProvideQBMeasurementDetailsLocalDataSourceFactory(localDataSourceModule, provider, provider2);
    }

    @Override
    public QBMeasurementDetailsLocalDataSource get() {
        return Preconditions.checkNotNull(this.module.provideQBMeasurementDetailsLocalDataSource(this.contextProvider.get(), this.helperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }
}

