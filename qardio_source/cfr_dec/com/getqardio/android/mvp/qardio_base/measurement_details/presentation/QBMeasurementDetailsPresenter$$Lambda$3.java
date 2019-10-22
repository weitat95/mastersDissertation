/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.presentation;

import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenter;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class QBMeasurementDetailsPresenter$$Lambda$3
implements Consumer {
    private static final QBMeasurementDetailsPresenter$$Lambda$3 instance = new QBMeasurementDetailsPresenter$$Lambda$3();

    private QBMeasurementDetailsPresenter$$Lambda$3() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        QBMeasurementDetailsPresenter.lambda$onNoteChanged$0((String)object);
    }
}

