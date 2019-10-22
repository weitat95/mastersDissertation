/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.googlefit;

import android.content.Context;
import android.support.v4.util.Pair;
import com.getqardio.android.googlefit.RxHeartRateApi$$Lambda$1;
import com.getqardio.android.googlefit.RxHeartRateApi$$Lambda$10;
import com.getqardio.android.googlefit.RxHeartRateApi$$Lambda$11;
import com.getqardio.android.googlefit.RxHeartRateApi$$Lambda$2;
import com.getqardio.android.googlefit.RxHeartRateApi$$Lambda$3;
import com.getqardio.android.googlefit.RxHeartRateApi$$Lambda$4;
import com.getqardio.android.googlefit.RxHeartRateApi$$Lambda$6;
import com.getqardio.android.googlefit.RxHeartRateApi$$Lambda$7;
import com.getqardio.android.googlefit.RxHeartRateApi$$Lambda$8;
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
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.functions.Function;
import java.util.List;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class RxHeartRateApi {
    private static DataSource getDataSource(GoogleApiClient googleApiClient) {
        return new DataSource.Builder().setAppPackageName(googleApiClient.getContext()).setDataType(DataType.TYPE_HEART_RATE_BPM).setType(0).setName("Qardio-bp").setStreamName("Qardio-Arm").build();
    }

    static /* synthetic */ Boolean lambda$deleteBloodPressureFromGoogleFit$10(Throwable throwable) throws Exception {
        Timber.e(throwable);
        return Boolean.FALSE;
    }

    static /* synthetic */ void lambda$deleteBloodPressureFromGoogleFit$9(long l, GoogleApiClient googleApiClient, SingleEmitter singleEmitter) throws Exception {
        DataDeleteRequest dataDeleteRequest = new DataDeleteRequest.Builder().setTimeInterval(l, l + 1L, TimeUnit.MILLISECONDS).addDataType(DataType.TYPE_HEART_RATE_BPM).build();
        Fitness.HistoryApi.deleteData(googleApiClient, dataDeleteRequest).setResultCallback(RxHeartRateApi$$Lambda$8.lambdaFactory$(singleEmitter));
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

    static /* synthetic */ void lambda$null$8(SingleEmitter singleEmitter, Status status) {
        Timber.d("delete data = " + status.isSuccess(), new Object[0]);
        if (!singleEmitter.isDisposed()) {
            singleEmitter.onSuccess(status.isSuccess());
        }
    }

    static /* synthetic */ void lambda$saveHeartRateToGoogleFit$1(GoogleApiClient googleApiClient, DataSet dataSet, DataSource dataSource, SingleEmitter singleEmitter) throws Exception {
        Fitness.HistoryApi.insertData(googleApiClient, dataSet).setResultCallback(RxHeartRateApi$$Lambda$11.lambdaFactory$(dataSource, singleEmitter));
    }

    static /* synthetic */ Boolean lambda$saveHeartRateToGoogleFit$2(Throwable throwable) throws Exception {
        Timber.e(throwable);
        return Boolean.FALSE;
    }

    static /* synthetic */ void lambda$saveHeartRateToGoogleFit$4(GoogleApiClient googleApiClient, DataSet dataSet, DataSource dataSource, List list, SingleEmitter singleEmitter) throws Exception {
        Fitness.HistoryApi.insertData(googleApiClient, dataSet).setResultCallback(RxHeartRateApi$$Lambda$10.lambdaFactory$(dataSource, singleEmitter, list));
    }

    static /* synthetic */ Integer lambda$saveHeartRateToGoogleFit$5(Throwable throwable) throws Exception {
        Timber.e(throwable);
        return 0;
    }

    public Single<Boolean> deleteBloodPressureFromGoogleFit(GoogleApiClient googleApiClient, long l) {
        return Single.create(RxHeartRateApi$$Lambda$6.lambdaFactory$(l, googleApiClient)).onErrorReturn(RxHeartRateApi$$Lambda$7.lambdaFactory$());
    }

    public Single<Boolean> saveHeartRateToGoogleFit(GoogleApiClient googleApiClient, float f, long l) {
        DataSource dataSource = RxHeartRateApi.getDataSource(googleApiClient);
        DataSet dataSet = DataSet.create(dataSource);
        DataPoint dataPoint = DataPoint.create(dataSource);
        dataPoint.setTimestamp(l, TimeUnit.MILLISECONDS);
        dataPoint.getValue(Field.FIELD_BPM).setFloat(f);
        dataSet.add(dataPoint);
        Timber.d("write data = " + dataSet.toString(), new Object[0]);
        return Single.create(RxHeartRateApi$$Lambda$1.lambdaFactory$(googleApiClient, dataSet, dataSource)).onErrorReturn(RxHeartRateApi$$Lambda$2.lambdaFactory$());
    }

    public Single<Integer> saveHeartRateToGoogleFit(GoogleApiClient googleApiClient, List<Pair<Long, Float>> list) {
        DataSource dataSource = RxHeartRateApi.getDataSource(googleApiClient);
        DataSet dataSet = DataSet.create(dataSource);
        for (int i = 0; i < list.size(); ++i) {
            DataPoint dataPoint = DataPoint.create(dataSource);
            dataPoint.setTimestamp((Long)list.get((int)i).first, TimeUnit.MILLISECONDS);
            dataPoint.getValue(Field.FIELD_BPM).setFloat(((Float)list.get((int)i).second).floatValue());
            dataSet.add(dataPoint);
        }
        Timber.d("write data = " + dataSet.toString(), new Object[0]);
        return Single.create(RxHeartRateApi$$Lambda$3.lambdaFactory$(googleApiClient, dataSet, dataSource, list)).onErrorReturn(RxHeartRateApi$$Lambda$4.lambdaFactory$());
    }
}

