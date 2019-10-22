/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.presentation;

import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenter;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class QBMeasurementDetailsPresenter$$Lambda$1
implements Consumer {
    private final QBMeasurementDetailsPresenter arg$1;

    private QBMeasurementDetailsPresenter$$Lambda$1(QBMeasurementDetailsPresenter qBMeasurementDetailsPresenter) {
        this.arg$1 = qBMeasurementDetailsPresenter;
    }

    public static Consumer lambdaFactory$(QBMeasurementDetailsPresenter qBMeasurementDetailsPresenter) {
        return new QBMeasurementDetailsPresenter$$Lambda$1(qBMeasurementDetailsPresenter);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        QBMeasurementDetailsPresenter.access$lambda$0(this.arg$1, (WeightMeasurement)object);
    }
}

