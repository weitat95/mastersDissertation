/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.presentation;

import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;
import timber.log.Timber;

final class QBMeasurementDetailsPresenter$$Lambda$4
implements Consumer {
    private static final QBMeasurementDetailsPresenter$$Lambda$4 instance = new QBMeasurementDetailsPresenter$$Lambda$4();

    private QBMeasurementDetailsPresenter$$Lambda$4() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        Timber.e((Throwable)object);
    }
}

