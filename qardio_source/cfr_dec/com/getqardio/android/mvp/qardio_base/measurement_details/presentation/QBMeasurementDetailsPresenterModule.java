/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.presentation;

import com.getqardio.android.mvp.qardio_base.measurement_details.QBMeasurementDetailsContract;
import com.getqardio.android.mvp.qardio_base.measurement_details.view.QBMeasurementDetailsActivity;

public class QBMeasurementDetailsPresenterModule {
    private final QBMeasurementDetailsActivity view;

    public QBMeasurementDetailsPresenterModule(QBMeasurementDetailsActivity qBMeasurementDetailsActivity) {
        this.view = qBMeasurementDetailsActivity;
    }

    QBMeasurementDetailsContract.View provideView() {
        return this.view;
    }
}

