/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxRecordingApi$$Lambda$1;
import com.getqardio.android.googlefit.RxRecordingApi$$Lambda$2;
import com.getqardio.android.googlefit.RxRecordingApi$$Lambda$3;
import com.getqardio.android.googlefit.RxRecordingApi$$Lambda$4;
import com.getqardio.android.googlefit.RxRecordingApi$$Lambda$5;
import com.getqardio.android.googlefit.RxRecordingApi$$Lambda$6;
import com.getqardio.android.googlefit.RxRecordingApi$$Lambda$7;
import com.getqardio.android.googlefit.RxRecordingApi$$Lambda$8;
import com.getqardio.android.googlefit.RxRecordingApi$$Lambda$9;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.RecordingApi;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.functions.Function;
import java.util.List;

public class RxRecordingApi {
    private boolean hasSubscriptions;

    static /* synthetic */ Boolean lambda$checkSubscriptions$7(Throwable throwable) throws Exception {
        return Boolean.FALSE;
    }

    static /* synthetic */ void lambda$null$0(SingleEmitter singleEmitter, Status status) {
        if (!singleEmitter.isDisposed()) {
            singleEmitter.onSuccess(status.isSuccess());
        }
    }

    static /* synthetic */ void lambda$null$3(SingleEmitter singleEmitter, Status status) {
        if (!singleEmitter.isDisposed()) {
            singleEmitter.onSuccess(status.isSuccess());
        }
    }

    static /* synthetic */ void lambda$stopRecording$4(GoogleApiClient googleApiClient, DataType dataType, SingleEmitter singleEmitter) throws Exception {
        Fitness.RecordingApi.subscribe(googleApiClient, dataType).setResultCallback(RxRecordingApi$$Lambda$7.lambdaFactory$(singleEmitter));
    }

    private Single<Boolean> startRecording(GoogleApiClient googleApiClient, DataType dataType) {
        return Single.create(RxRecordingApi$$Lambda$1.lambdaFactory$(this, googleApiClient, dataType));
    }

    private Single<Boolean> startRecordingActivitySamples(GoogleApiClient googleApiClient) {
        return this.startRecording(googleApiClient, DataType.TYPE_ACTIVITY_SEGMENT);
    }

    private Single<Boolean> startRecordingActivitySegments(GoogleApiClient googleApiClient) {
        return this.startRecording(googleApiClient, DataType.TYPE_ACTIVITY_SAMPLES);
    }

    private Single<Boolean> startRecordingSteps(GoogleApiClient googleApiClient) {
        return this.startRecording(googleApiClient, DataType.TYPE_STEP_COUNT_DELTA);
    }

    private Single<Boolean> stopRecording(GoogleApiClient googleApiClient, DataType dataType) {
        return Single.create(RxRecordingApi$$Lambda$2.lambdaFactory$(googleApiClient, dataType));
    }

    public Completable cancelSubscriptions(GoogleApiClient googleApiClient) {
        return Completable.fromSingle(Single.zip(this.stopRecording(googleApiClient, DataType.TYPE_ACTIVITY_SAMPLES), this.stopRecording(googleApiClient, DataType.TYPE_ACTIVITY_SEGMENT), this.stopRecording(googleApiClient, DataType.TYPE_STEP_COUNT_DELTA), RxRecordingApi$$Lambda$6.lambdaFactory$(this))).onErrorComplete();
    }

    public Single<Boolean> checkSubscriptions(GoogleApiClient googleApiClient) {
        if (this.hasSubscriptions) {
            return Single.create(RxRecordingApi$$Lambda$3.lambdaFactory$(this));
        }
        return Single.zip(this.startRecordingActivitySamples(googleApiClient), this.startRecordingActivitySegments(googleApiClient), this.startRecordingSteps(googleApiClient), RxRecordingApi$$Lambda$4.lambdaFactory$(this)).onErrorReturn(RxRecordingApi$$Lambda$5.lambdaFactory$());
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ Boolean lambda$cancelSubscriptions$8(Boolean bl, Boolean bl2, Boolean bl3) throws Exception {
        boolean bl4 = bl != false && bl2 != false && bl3 != false;
        this.hasSubscriptions = bl4;
        return this.hasSubscriptions;
    }

    /* synthetic */ void lambda$checkSubscriptions$5(SingleEmitter singleEmitter) throws Exception {
        singleEmitter.onSuccess(this.hasSubscriptions);
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ Boolean lambda$checkSubscriptions$6(Boolean bl, Boolean bl2, Boolean bl3) throws Exception {
        boolean bl4 = bl != false && bl2 != false && bl3 != false;
        this.hasSubscriptions = bl4;
        return this.hasSubscriptions;
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$null$1(DataType dataType, GoogleApiClient googleApiClient, SingleEmitter singleEmitter, ListSubscriptionsResult listSubscriptionsResult) {
        if (listSubscriptionsResult.getSubscriptions(dataType).size() == 0) {
            if (googleApiClient.isConnected()) {
                Fitness.RecordingApi.subscribe(googleApiClient, dataType).setResultCallback(RxRecordingApi$$Lambda$9.lambdaFactory$(singleEmitter));
                return;
            } else {
                if (singleEmitter.isDisposed()) return;
                {
                    singleEmitter.onSuccess(Boolean.FALSE);
                    return;
                }
            }
        } else {
            if (singleEmitter.isDisposed()) return;
            {
                this.hasSubscriptions = Boolean.TRUE;
                singleEmitter.onSuccess(Boolean.TRUE);
                return;
            }
        }
    }

    /* synthetic */ void lambda$startRecording$2(GoogleApiClient googleApiClient, DataType dataType, SingleEmitter singleEmitter) throws Exception {
        Fitness.RecordingApi.listSubscriptions(googleApiClient, dataType).setResultCallback(RxRecordingApi$$Lambda$8.lambdaFactory$(this, dataType, googleApiClient, singleEmitter));
    }
}

