/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.googlefit;

import android.content.Context;
import android.support.v4.util.Pair;
import com.getqardio.android.googlefit.ActivityDataBucket;
import com.getqardio.android.googlefit.StepDataBucket;
import com.google.android.gms.fitness.result.DataReadResult;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;

public interface GoogleFitApi {
    public Single<Boolean> deleteBloodPressureMeasurements(Context var1, long var2, long var4);

    public void disconnectFromFit();

    public Completable endGoogleFitSubscriptions();

    public Single<List<ActivityDataBucket>> fetchCurrentDayActivity();

    public Single<Integer> fetchCurrentDaySteps();

    public Single<List<StepDataBucket>> fetchCurrentDayStepsTimeline();

    public Single<List<ActivityDataBucket>> fetchDayHistory(long var1, long var3);

    public Single<List<StepDataBucket>> fetchDayStepsTimeline(long var1, long var3);

    public Single<Pair<List<StepDataBucket>, List<ActivityDataBucket>>> fetchMonthHistory(long var1);

    public Single<DataReadResult> readBloodPressureMeasurements(Context var1, long var2);

    public Single<Integer> saveBloodPressureMeasurements(Context var1, long var2);

    public Single<Boolean> startGoogleFitSubscriptions();
}

