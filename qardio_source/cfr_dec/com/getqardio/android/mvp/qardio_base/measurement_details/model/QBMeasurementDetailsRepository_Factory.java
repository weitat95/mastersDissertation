/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.model;

import com.getqardio.android.mvp.qardio_base.measurement_details.model.QBMeasurementDetailsLocalDataSource;
import com.getqardio.android.mvp.qardio_base.measurement_details.model.QBMeasurementDetailsRepository;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class QBMeasurementDetailsRepository_Factory
implements Factory<QBMeasurementDetailsRepository> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<QBMeasurementDetailsLocalDataSource> localDataSourceProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !QBMeasurementDetailsRepository_Factory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public QBMeasurementDetailsRepository_Factory(Provider<QBMeasurementDetailsLocalDataSource> provider) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.localDataSourceProvider = provider;
    }

    public static Factory<QBMeasurementDetailsRepository> create(Provider<QBMeasurementDetailsLocalDataSource> provider) {
        return new QBMeasurementDetailsRepository_Factory(provider);
    }

    @Override
    public QBMeasurementDetailsRepository get() {
        return new QBMeasurementDetailsRepository(this.localDataSourceProvider.get());
    }
}

