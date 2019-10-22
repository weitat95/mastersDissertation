/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxHistoryApi$$Lambda$1;
import com.getqardio.android.googlefit.RxHistoryApi$$Lambda$10;
import com.getqardio.android.googlefit.RxHistoryApi$$Lambda$2;
import com.getqardio.android.googlefit.RxHistoryApi$$Lambda$3;
import com.getqardio.android.googlefit.RxHistoryApi$$Lambda$4;
import com.getqardio.android.googlefit.RxHistoryApi$$Lambda$5;
import com.getqardio.android.googlefit.RxHistoryApi$$Lambda$6;
import com.getqardio.android.googlefit.RxHistoryApi$$Lambda$7;
import com.getqardio.android.googlefit.RxHistoryApi$$Lambda$8;
import com.getqardio.android.googlefit.RxHistoryApi$$Lambda$9;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.HistoryApi;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.internal.zzbfm;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class RxHistoryApi {
    static /* synthetic */ void lambda$fetchCurrentDayActivity$5(GoogleApiClient googleApiClient, SingleEmitter singleEmitter) throws Exception {
        Object object = Calendar.getInstance();
        long l = ((Calendar)object).getTimeInMillis();
        ((Calendar)object).set(11, 0);
        ((Calendar)object).set(12, 0);
        ((Calendar)object).set(13, 0);
        long l2 = ((Calendar)object).getTimeInMillis();
        object = new DataReadRequest.Builder().aggregate(DataType.TYPE_ACTIVITY_SEGMENT, DataType.AGGREGATE_ACTIVITY_SUMMARY).bucketByActivitySegment(1, TimeUnit.MINUTES).setTimeRange(l2, l, TimeUnit.MILLISECONDS).enableServerQueries().build();
        Fitness.HistoryApi.readData(googleApiClient, (DataReadRequest)object).setResultCallback(RxHistoryApi$$Lambda$8.lambdaFactory$(singleEmitter));
    }

    static /* synthetic */ void lambda$fetchCurrentDaySteps$3(GoogleApiClient googleApiClient, SingleEmitter singleEmitter) throws Exception {
        Fitness.HistoryApi.readDailyTotal(googleApiClient, DataType.TYPE_STEP_COUNT_DELTA).setResultCallback(RxHistoryApi$$Lambda$9.lambdaFactory$(singleEmitter));
    }

    static /* synthetic */ void lambda$fetchDayActivityHistory$7(long l, long l2, GoogleApiClient googleApiClient, SingleEmitter singleEmitter) throws Exception {
        DataReadRequest dataReadRequest = new DataReadRequest.Builder().aggregate(DataType.TYPE_ACTIVITY_SEGMENT, DataType.AGGREGATE_ACTIVITY_SUMMARY).bucketByActivitySegment(1, TimeUnit.MINUTES).setTimeRange(l, l2, TimeUnit.MILLISECONDS).enableServerQueries().build();
        Fitness.HistoryApi.readData(googleApiClient, dataReadRequest).setResultCallback(RxHistoryApi$$Lambda$7.lambdaFactory$(singleEmitter));
    }

    static /* synthetic */ void lambda$fetchDayStepsTimeline$9(long l, long l2, GoogleApiClient googleApiClient, SingleEmitter singleEmitter) throws Exception {
        zzbfm zzbfm2 = new DataSource.Builder().setDataType(DataType.TYPE_STEP_COUNT_DELTA).setType(1).setStreamName("estimated_steps").setAppPackageName("com.google.android.gms").build();
        zzbfm2 = new DataReadRequest.Builder().aggregate((DataSource)zzbfm2, DataType.AGGREGATE_STEP_COUNT_DELTA).bucketByTime(1, TimeUnit.HOURS).setTimeRange(l, l2, TimeUnit.MILLISECONDS).enableServerQueries().build();
        Fitness.HistoryApi.readData(googleApiClient, (DataReadRequest)zzbfm2).setResultCallback(RxHistoryApi$$Lambda$6.lambdaFactory$(singleEmitter));
    }

    static /* synthetic */ void lambda$fetchMonthHistory$1(long l, GoogleApiClient googleApiClient, SingleEmitter singleEmitter) throws Exception {
        Object object = Calendar.getInstance();
        ((Calendar)object).setTimeInMillis(l);
        ((Calendar)object).add(2, 1);
        ((Calendar)object).set(5, ((Calendar)object).getActualMinimum(5));
        long l2 = ((Calendar)object).getTimeInMillis();
        object = new DataSource.Builder().setDataType(DataType.TYPE_STEP_COUNT_DELTA).setType(1).setStreamName("estimated_steps").setAppPackageName("com.google.android.gms").build();
        object = new DataReadRequest.Builder().aggregate((DataSource)object, DataType.AGGREGATE_STEP_COUNT_DELTA).aggregate(DataType.TYPE_ACTIVITY_SEGMENT, DataType.AGGREGATE_ACTIVITY_SUMMARY).bucketByTime(1, TimeUnit.DAYS).setTimeRange(l, l2, TimeUnit.MILLISECONDS).enableServerQueries().build();
        Fitness.HistoryApi.readData(googleApiClient, (DataReadRequest)object).setResultCallback(RxHistoryApi$$Lambda$10.lambdaFactory$(singleEmitter));
    }

    static /* synthetic */ void lambda$null$0(SingleEmitter singleEmitter, DataReadResult dataReadResult) {
        block3: {
            block2: {
                Timber.d("got " + dataReadResult.getBuckets().size() + "  buckets of data", new Object[0]);
                if (singleEmitter.isDisposed()) break block2;
                if (!dataReadResult.getStatus().isSuccess()) break block3;
                singleEmitter.onSuccess(dataReadResult.getBuckets());
            }
            return;
        }
        singleEmitter.onError(new Exception("code = " + dataReadResult.getStatus().getStatusCode() + " message = " + dataReadResult.getStatus().getStatusMessage()));
    }

    static /* synthetic */ void lambda$null$2(SingleEmitter singleEmitter, DailyTotalResult dailyTotalResult) {
        if (dailyTotalResult.getTotal() != null && !dailyTotalResult.getTotal().isEmpty()) {
            Timber.d("got Total " + dailyTotalResult.getTotal().getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt() + " steps", new Object[0]);
        }
        if (!singleEmitter.isDisposed()) {
            singleEmitter.onSuccess(dailyTotalResult.getTotal());
        }
    }

    static /* synthetic */ void lambda$null$4(SingleEmitter singleEmitter, DataReadResult dataReadResult) {
        block3: {
            block2: {
                Timber.d("got " + dataReadResult.getBuckets().size() + "  buckets of data", new Object[0]);
                if (singleEmitter.isDisposed()) break block2;
                if (!dataReadResult.getStatus().isSuccess()) break block3;
                singleEmitter.onSuccess(dataReadResult.getBuckets());
            }
            return;
        }
        singleEmitter.onError(new Exception("code = " + dataReadResult.getStatus().getStatusCode() + " message = " + dataReadResult.getStatus().getStatusMessage()));
    }

    static /* synthetic */ void lambda$null$6(SingleEmitter singleEmitter, DataReadResult dataReadResult) {
        block3: {
            block2: {
                Timber.d("got " + dataReadResult.getBuckets().size() + "  buckets of data", new Object[0]);
                if (singleEmitter.isDisposed()) break block2;
                if (!dataReadResult.getStatus().isSuccess()) break block3;
                singleEmitter.onSuccess(dataReadResult.getBuckets());
            }
            return;
        }
        singleEmitter.onError(new Exception("code = " + dataReadResult.getStatus().getStatusCode() + " message = " + dataReadResult.getStatus().getStatusMessage()));
    }

    static /* synthetic */ void lambda$null$8(SingleEmitter singleEmitter, DataReadResult dataReadResult) {
        block3: {
            block2: {
                Timber.d("got " + dataReadResult.getBuckets().size() + "  buckets of data", new Object[0]);
                if (singleEmitter.isDisposed()) break block2;
                if (!dataReadResult.getStatus().isSuccess()) break block3;
                singleEmitter.onSuccess(dataReadResult.getBuckets());
            }
            return;
        }
        singleEmitter.onError(new Exception("code = " + dataReadResult.getStatus().getStatusCode() + " message = " + dataReadResult.getStatus().getStatusMessage()));
    }

    Single<List<Bucket>> fetchCurrentDayActivity(GoogleApiClient googleApiClient) {
        return Single.create(RxHistoryApi$$Lambda$3.lambdaFactory$(googleApiClient));
    }

    Single<DataSet> fetchCurrentDaySteps(GoogleApiClient googleApiClient) {
        return Single.create(RxHistoryApi$$Lambda$2.lambdaFactory$(googleApiClient));
    }

    public Single<List<Bucket>> fetchDayActivityHistory(GoogleApiClient googleApiClient, long l, long l2) {
        return Single.create(RxHistoryApi$$Lambda$4.lambdaFactory$(l, l2, googleApiClient));
    }

    Single<List<Bucket>> fetchDayStepsTimeline(GoogleApiClient googleApiClient, long l, long l2) {
        return Single.create(RxHistoryApi$$Lambda$5.lambdaFactory$(l, l2, googleApiClient));
    }

    Single<List<Bucket>> fetchMonthHistory(GoogleApiClient googleApiClient, long l) {
        return Single.create(RxHistoryApi$$Lambda$1.lambdaFactory$(l, googleApiClient));
    }
}

