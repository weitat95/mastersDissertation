/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxConfigApi$$Lambda$1;
import com.getqardio.android.googlefit.RxConfigApi$$Lambda$2;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.ConfigApi;
import com.google.android.gms.fitness.Fitness;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;

public class RxConfigApi {
    static /* synthetic */ void lambda$disconnectFromFit$1(GoogleApiClient googleApiClient, CompletableEmitter completableEmitter) throws Exception {
        Fitness.ConfigApi.disableFit(googleApiClient).setResultCallback(RxConfigApi$$Lambda$2.lambdaFactory$(completableEmitter));
    }

    static /* synthetic */ void lambda$null$0(CompletableEmitter completableEmitter, Status status) {
        if (!completableEmitter.isDisposed()) {
            completableEmitter.onComplete();
        }
    }

    Completable disconnectFromFit(GoogleApiClient googleApiClient) {
        return Completable.create(RxConfigApi$$Lambda$1.lambdaFactory$(googleApiClient));
    }
}

