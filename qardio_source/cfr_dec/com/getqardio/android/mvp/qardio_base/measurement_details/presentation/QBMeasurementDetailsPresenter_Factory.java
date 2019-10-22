/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.presentation;

import com.getqardio.android.mvp.qardio_base.measurement_details.QBMeasurementDetailsContract;
import com.getqardio.android.mvp.qardio_base.measurement_details.model.QBMeasurementDetailsRepository;
import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenter;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class QBMeasurementDetailsPresenter_Factory
implements Factory<QBMeasurementDetailsPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<QBMeasurementDetailsRepository> repositoryProvider;
    private final Provider<QBMeasurementDetailsContract.View> viewProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !QBMeasurementDetailsPresenter_Factory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public QBMeasurementDetailsPresenter_Factory(Provider<QBMeasurementDetailsRepository> provider, Provider<QBMeasurementDetailsContract.View> provider2) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.repositoryProvider = provider;
        if (!$assertionsDisabled && provider2 == null) {
            throw new AssertionError();
        }
        this.viewProvider = provider2;
    }

    public static Factory<QBMeasurementDetailsPresenter> create(Provider<QBMeasurementDetailsRepository> provider, Provider<QBMeasurementDetailsContract.View> provider2) {
        return new QBMeasurementDetailsPresenter_Factory(provider, provider2);
    }

    @Override
    public QBMeasurementDetailsPresenter get() {
        return new QBMeasurementDetailsPresenter(this.repositoryProvider.get(), this.viewProvider.get());
    }
}

