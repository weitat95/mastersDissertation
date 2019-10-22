/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.presentation;

import com.getqardio.android.mvp.qardio_base.measurement_details.QBMeasurementDetailsContract;
import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenterModule;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class QBMeasurementDetailsPresenterModule_ProvideViewFactory
implements Factory<QBMeasurementDetailsContract.View> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final QBMeasurementDetailsPresenterModule module;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !QBMeasurementDetailsPresenterModule_ProvideViewFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public QBMeasurementDetailsPresenterModule_ProvideViewFactory(QBMeasurementDetailsPresenterModule qBMeasurementDetailsPresenterModule) {
        if (!$assertionsDisabled && qBMeasurementDetailsPresenterModule == null) {
            throw new AssertionError();
        }
        this.module = qBMeasurementDetailsPresenterModule;
    }

    public static Factory<QBMeasurementDetailsContract.View> create(QBMeasurementDetailsPresenterModule qBMeasurementDetailsPresenterModule) {
        return new QBMeasurementDetailsPresenterModule_ProvideViewFactory(qBMeasurementDetailsPresenterModule);
    }

    @Override
    public QBMeasurementDetailsContract.View get() {
        return Preconditions.checkNotNull(this.module.provideView(), "Cannot return null from a non-@Nullable @Provides method");
    }
}

