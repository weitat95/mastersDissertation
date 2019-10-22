/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.model;

import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.mvp.qardio_base.measurement_details.model.QBMeasurementDetailsLocalDataSource;
import io.reactivex.Observable;

public class QBMeasurementDetailsRepository {
    private final QBMeasurementDetailsLocalDataSource localDataSource;

    public QBMeasurementDetailsRepository(QBMeasurementDetailsLocalDataSource qBMeasurementDetailsLocalDataSource) {
        this.localDataSource = qBMeasurementDetailsLocalDataSource;
    }

    public Observable<WeightMeasurement> getMeasurementByDate(long l, long l2) {
        return this.localDataSource.getMeasurementByDate(l, l2);
    }

    public Observable<String> saveNewNote(long l, long l2, String string2) {
        return this.localDataSource.saveNewNote(l, l2, string2);
    }
}

