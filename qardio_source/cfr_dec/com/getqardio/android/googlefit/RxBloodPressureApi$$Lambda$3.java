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
import java.util.List;

final class RxBloodPressureApi$$Lambda$3
implements SingleOnSubscribe {
    private final GoogleApiClient arg$1;
    private final DataSet arg$2;
    private final DataSource arg$3;
    private final List arg$4;

    private RxBloodPressureApi$$Lambda$3(GoogleApiClient googleApiClient, DataSet dataSet, DataSource dataSource, List list) {
        this.arg$1 = googleApiClient;
        this.arg$2 = dataSet;
        this.arg$3 = dataSource;
        this.arg$4 = list;
    }

    public static SingleOnSubscribe lambdaFactory$(GoogleApiClient googleApiClient, DataSet dataSet, DataSource dataSource, List list) {
        return new RxBloodPressureApi$$Lambda$3(googleApiClient, dataSet, dataSource, list);
    }

    @LambdaForm.Hidden
    public void subscribe(SingleEmitter singleEmitter) {
        RxBloodPressureApi.lambda$saveBloodPressureHistoryToGoogleFit$4(this.arg$1, this.arg$2, this.arg$3, this.arg$4, singleEmitter);
    }
}

