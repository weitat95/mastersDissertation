/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.view;

import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenter;
import com.getqardio.android.mvp.qardio_base.measurement_details.view.QBMeasurementDetailsActivity;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class QBMeasurementDetailsActivity_MembersInjector
implements MembersInjector<QBMeasurementDetailsActivity> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<QBMeasurementDetailsPresenter> presenterProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !QBMeasurementDetailsActivity_MembersInjector.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public QBMeasurementDetailsActivity_MembersInjector(Provider<QBMeasurementDetailsPresenter> provider) {
        if (!$assertionsDisabled && provider == null) {
            throw new AssertionError();
        }
        this.presenterProvider = provider;
    }

    public static MembersInjector<QBMeasurementDetailsActivity> create(Provider<QBMeasurementDetailsPresenter> provider) {
        return new QBMeasurementDetailsActivity_MembersInjector(provider);
    }

    @Override
    public void injectMembers(QBMeasurementDetailsActivity qBMeasurementDetailsActivity) {
        if (qBMeasurementDetailsActivity == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        qBMeasurementDetailsActivity.presenter = this.presenterProvider.get();
    }
}

