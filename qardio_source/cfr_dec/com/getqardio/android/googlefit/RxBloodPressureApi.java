/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.googlefit;

import android.content.Context;
import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.googlefit.RxBloodPressureApi$$Lambda$1;
import com.getqardio.android.googlefit.RxBloodPressureApi$$Lambda$10;
import com.getqardio.android.googlefit.RxBloodPressureApi$$Lambda$11;
import com.getqardio.android.googlefit.RxBloodPressureApi$$Lambda$2;
import com.getqardio.android.googlefit.RxBloodPressureApi$$Lambda$3;
import com.getqardio.android.googlefit.RxBloodPressureApi$$Lambda$4;
import com.getqardio.android.googlefit.RxBloodPressureApi$$Lambda$5;
import com.getqardio.android.googlefit.RxBloodPressureApi$$Lambda$6;
import com.getqardio.android.googlefit.RxBloodPressureApi$$Lambda$7;
import com.getqardio.android.googlefit.RxBloodPressureApi$$Lambda$8;
import com.getqardio.android.googlefit.RxBloodPressureApi$$Lambda$9;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.HistoryApi;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.HealthDataTypes;
import com.google.android.gms.fitness.data.HealthFields;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.functions.Function;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class RxBloodPressureApi {
    private static DataSource getDataSource(GoogleApiClient googleApiClient) {
        return new DataSource.Builder().setAppPackageName(googleApiClient.getContext()).setDataType(HealthDataTypes.TYPE_BLOOD_PRESSURE).setType(0).setName("Qardio-bp").setStreamName("Qardio-Arm").build();
    }

    static /* synthetic */ Boolean lambda$deleteBloodPressureFromGoogleFit$10(Throwable throwable) throws Exception {
        Timber.e(throwable);
        return Boolean.FALSE;
    }

    static /* synthetic */ void lambda$deleteBloodPressureFromGoogleFit$9(long l, GoogleApiClient googleApiClient, SingleEmitter singleEmitter) throws Exception {
        DataDeleteRequest dataDeleteRequest = new DataDeleteRequest.Builder().setTimeInterval(l, l + 1L, TimeUnit.MILLISECONDS).addDataType(HealthDataTypes.TYPE_BLOOD_PRESSURE).build();
        Fitness.HistoryApi.deleteData(googleApiClient, dataDeleteRequest).setResultCallback(RxBloodPressureApi$$Lambda$8.lambdaFactory$(singleEmitter));
    }

    static /* synthetic */ void lambda$null$0(DataSource dataSource, SingleEmitter singleEmitter, Status status) {
        block3: {
            block2: {
                Timber.d("status code = " + status.getStatusCode(), new Object[0]);
                Timber.d("status code = " + status.getStatusMessage(), new Object[0]);
                Timber.d("StreamID = " + dataSource.getStreamIdentifier(), new Object[0]);
                Timber.d("Write status - " + status.toString(), new Object[0]);
                if (singleEmitter.isDisposed()) break block2;
                if (!status.isSuccess()) break block3;
                singleEmitter.onSuccess(true);
            }
            return;
        }
        singleEmitter.onError(new Exception("Unable to write BP data to google fit"));
    }

    static /* synthetic */ void lambda$null$3(DataSource dataSource, SingleEmitter singleEmitter, List list, Status status) {
        block3: {
            block2: {
                Timber.d("status code = " + status.getStatusCode(), new Object[0]);
                Timber.d("status code = " + status.getStatusMessage(), new Object[0]);
                Timber.d("StreamID = " + dataSource.getStreamIdentifier(), new Object[0]);
                Timber.d("Write status - " + status.toString(), new Object[0]);
                if (singleEmitter.isDisposed()) break block2;
                if (!status.isSuccess()) break block3;
                singleEmitter.onSuccess(list.size());
            }
            return;
        }
        singleEmitter.onError(new Exception("Unable to write BP data to google fit"));
    }

    static /* synthetic */ void lambda$null$6(SingleEmitter singleEmitter, DataReadResult dataReadResult) {
        Timber.d("Read data = " + dataReadResult.getDataSet(HealthDataTypes.TYPE_BLOOD_PRESSURE).toString(), new Object[0]);
        if (!singleEmitter.isDisposed()) {
            singleEmitter.onSuccess(dataReadResult);
        }
    }

    static /* synthetic */ void lambda$null$8(SingleEmitter singleEmitter, Status status) {
        Timber.d("delete data = " + status.isSuccess(), new Object[0]);
        if (!singleEmitter.isDisposed()) {
            singleEmitter.onSuccess(status.isSuccess());
        }
    }

    static /* synthetic */ void lambda$readBloodPressureFromGoogleFit$7(GoogleApiClient googleApiClient, SingleEmitter singleEmitter) throws Exception {
        Object object = Calendar.getInstance();
        long l = ((Calendar)object).getTimeInMillis();
        ((Calendar)object).set(1, 2017);
        long l2 = ((Calendar)object).getTimeInMillis();
        object = RxBloodPressureApi.getDataSource(googleApiClient);
        object = new DataReadRequest.Builder().read((DataSource)object).enableServerQueries().setTimeRange(l2, l, TimeUnit.MILLISECONDS).build();
        Fitness.HistoryApi.readData(googleApiClient, (DataReadRequest)object).setResultCallback(RxBloodPressureApi$$Lambda$9.lambdaFactory$(singleEmitter));
    }

    static /* synthetic */ void lambda$saveBloodPressureHistoryToGoogleFit$4(GoogleApiClient googleApiClient, DataSet dataSet, DataSource dataSource, List list, SingleEmitter singleEmitter) throws Exception {
        Fitness.HistoryApi.insertData(googleApiClient, dataSet).setResultCallback(RxBloodPressureApi$$Lambda$10.lambdaFactory$(dataSource, singleEmitter, list));
    }

    static /* synthetic */ Integer lambda$saveBloodPressureHistoryToGoogleFit$5(Throwable throwable) throws Exception {
        Timber.e(throwable);
        return 0;
    }

    static /* synthetic */ void lambda$saveBloodPressureToGoogleFit$1(GoogleApiClient googleApiClient, DataSet dataSet, DataSource dataSource, SingleEmitter singleEmitter) throws Exception {
        Fitness.HistoryApi.insertData(googleApiClient, dataSet).setResultCallback(RxBloodPressureApi$$Lambda$11.lambdaFactory$(dataSource, singleEmitter));
    }

    static /* synthetic */ Boolean lambda$saveBloodPressureToGoogleFit$2(Throwable throwable) throws Exception {
        Timber.e(throwable);
        return Boolean.FALSE;
    }

    public Single<Boolean> deleteBloodPressureFromGoogleFit(GoogleApiClient googleApiClient, long l) {
        return Single.create(RxBloodPressureApi$$Lambda$6.lambdaFactory$(l, googleApiClient)).onErrorReturn(RxBloodPressureApi$$Lambda$7.lambdaFactory$());
    }

    public Single<DataReadResult> readBloodPressureFromGoogleFit(GoogleApiClient googleApiClient) {
        return Single.create(RxBloodPressureApi$$Lambda$5.lambdaFactory$(googleApiClient));
    }

    public Single<Integer> saveBloodPressureHistoryToGoogleFit(GoogleApiClient googleApiClient, List<BPMeasurement> list) {
        DataSource dataSource = RxBloodPressureApi.getDataSource(googleApiClient);
        DataSet dataSet = DataSet.create(dataSource);
        for (int i = 0; i < list.size(); ++i) {
            DataPoint dataPoint = DataPoint.create(dataSource);
            dataPoint.setTimestamp(list.get((int)i).measureDate.getTime(), TimeUnit.MILLISECONDS);
            dataPoint.getValue(HealthFields.FIELD_BLOOD_PRESSURE_SYSTOLIC).setFloat(list.get((int)i).sys.intValue());
            dataPoint.getValue(HealthFields.FIELD_BLOOD_PRESSURE_DIASTOLIC).setFloat(list.get((int)i).dia.intValue());
            dataSet.add(dataPoint);
        }
        Timber.d("write data = " + dataSet.toString(), new Object[0]);
        return Single.create(RxBloodPressureApi$$Lambda$3.lambdaFactory$(googleApiClient, dataSet, dataSource, list)).onErrorReturn(RxBloodPressureApi$$Lambda$4.lambdaFactory$());
    }

    public Single<Boolean> saveBloodPressureToGoogleFit(GoogleApiClient googleApiClient, BPMeasurement bPMeasurement) {
        DataSource dataSource = RxBloodPressureApi.getDataSource(googleApiClient);
        DataSet dataSet = DataSet.create(dataSource);
        DataPoint dataPoint = DataPoint.create(dataSource);
        dataPoint.setTimestamp(bPMeasurement.measureDate.getTime(), TimeUnit.MILLISECONDS);
        dataPoint.getValue(HealthFields.FIELD_BLOOD_PRESSURE_SYSTOLIC).setFloat(bPMeasurement.sys.intValue());
        dataPoint.getValue(HealthFields.FIELD_BLOOD_PRESSURE_DIASTOLIC).setFloat(bPMeasurement.dia.intValue());
        dataSet.add(dataPoint);
        Timber.d("write data = " + dataSet.toString(), new Object[0]);
        return Single.create(RxBloodPressureApi$$Lambda$1.lambdaFactory$(googleApiClient, dataSet, dataSource)).onErrorReturn(RxBloodPressureApi$$Lambda$2.lambdaFactory$());
    }
}

