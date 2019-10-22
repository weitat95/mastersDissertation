/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.model;

import android.content.Context;
import android.database.Cursor;
import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.mvp.common.local.AccountContextHelper;
import com.getqardio.android.mvp.qardio_base.measurement_details.model.QBMeasurementDetailsLocalDataSource$$Lambda$1;
import com.getqardio.android.provider.MeasurementHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

public class QBMeasurementDetailsLocalDataSource {
    private final AccountContextHelper accountContextHelper;
    private final Context context;

    public QBMeasurementDetailsLocalDataSource(Context context, AccountContextHelper accountContextHelper) {
        this.context = context;
        this.accountContextHelper = accountContextHelper;
    }

    public Observable<WeightMeasurement> getMeasurementByDate(long l, long l2) {
        return Observable.create(QBMeasurementDetailsLocalDataSource$$Lambda$1.lambdaFactory$(this, l, l2));
    }

    /* synthetic */ void lambda$getMeasurementByDate$0(long l, long l2, ObservableEmitter observableEmitter) throws Exception {
        Cursor cursor = MeasurementHelper.Weight.getMeasurementsLoaderByDate(this.context, l, l2, null).loadInBackground();
        if (cursor.moveToFirst()) {
            observableEmitter.onNext(MeasurementHelper.Weight.parseMeasurement(cursor));
            observableEmitter.onComplete();
            return;
        }
        observableEmitter.onError(new RuntimeException(String.format("Not measurement for userId - %d, measurementDate - %d", l, l2)));
    }

    public Observable<String> saveNewNote(long l, long l2, String string2) {
        MeasurementHelper.Weight.changeMeasurementNote(this.context, l, l2, string2, true);
        return Observable.just(string2);
    }
}

