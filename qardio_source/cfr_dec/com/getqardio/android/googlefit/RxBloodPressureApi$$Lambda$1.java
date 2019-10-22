/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxBloodPressureApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.lang.invoke.LambdaForm;

final class RxBloodPressureApi$$Lambda$1
implements SingleOnSubscribe {
    private final GoogleApiClient arg$1;
    private final DataSet arg$2;
    private final DataSource arg$3;

    private RxBloodPressureApi$$Lambda$1(GoogleApiClient googleApiClient, DataSet dataSet, DataSource dataSource) {
        this.arg$1 = googleApiClient;
        this.arg$2 = dataSet;
        this.arg$3 = dataSource;
    }

    public static SingleOnSubscribe lambdaFactory$(GoogleApiClient googleApiClient, DataSet dataSet, DataSource dataSource) {
        return new RxBloodPressureApi$$Lambda$1(googleApiClient, dataSet, dataSource);
    }

    @LambdaForm.Hidden
    public void subscribe(SingleEmitter singleEmitter) {
        RxBloodPressureApi.lambda$saveBloodPressureToGoogleFit$1(this.arg$1, this.arg$2, this.arg$3, singleEmitter);
    }
}

