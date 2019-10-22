/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.model;

import com.getqardio.android.mvp.qardio_base.measurement_details.model.QBMeasurementDetailsLocalDataSource;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.lang.invoke.LambdaForm;

final class QBMeasurementDetailsLocalDataSource$$Lambda$1
implements ObservableOnSubscribe {
    private final QBMeasurementDetailsLocalDataSource arg$1;
    private final long arg$2;
    private final long arg$3;

    private QBMeasurementDetailsLocalDataSource$$Lambda$1(QBMeasurementDetailsLocalDataSource qBMeasurementDetailsLocalDataSource, long l, long l2) {
        this.arg$1 = qBMeasurementDetailsLocalDataSource;
        this.arg$2 = l;
        this.arg$3 = l2;
    }

    public static ObservableOnSubscribe lambdaFactory$(QBMeasurementDetailsLocalDataSource qBMeasurementDetailsLocalDataSource, long l, long l2) {
        return new QBMeasurementDetailsLocalDataSource$$Lambda$1(qBMeasurementDetailsLocalDataSource, l, l2);
    }

    @LambdaForm.Hidden
    public void subscribe(ObservableEmitter observableEmitter) {
        this.arg$1.lambda$getMeasurementByDate$0(this.arg$2, this.arg$3, observableEmitter);
    }
}

