/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 */
package com.getqardio.android.googlefit;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.util.Pair;
import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.device_related_services.fit.GoogleFitDataHelper;
import com.getqardio.android.googlefit.ActivityDataBucket;
import com.getqardio.android.googlefit.GoogleFitApi;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$1;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$10;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$11;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$12;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$13;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$14;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$15;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$16;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$17;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$18;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$19;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$2;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$20;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$21;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$3;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$4;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$5;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$6;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$7;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$8;
import com.getqardio.android.googlefit.GoogleFitApiImpl$$Lambda$9;
import com.getqardio.android.googlefit.RxBloodPressureApi;
import com.getqardio.android.googlefit.RxConfigApi;
import com.getqardio.android.googlefit.RxHeartRateApi;
import com.getqardio.android.googlefit.RxHistoryApi;
import com.getqardio.android.googlefit.RxRecordingApi;
import com.getqardio.android.googlefit.StepDataBucket;
import com.getqardio.android.provider.MeasurementHelper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.result.DataReadResult;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class GoogleFitApiImpl
implements GoogleFitApi {
    private RxBloodPressureApi bloodPressureApi;
    private RxConfigApi configApi;
    private CompositeDisposable disposables = new CompositeDisposable();
    private GoogleApiClient googleApiClient;
    private RxHeartRateApi heartRateApi;
    private RxHistoryApi historyApi;
    private RxRecordingApi recordingApi;

    public GoogleFitApiImpl(GoogleApiClient googleApiClient) {
        this.configApi = new RxConfigApi();
        this.historyApi = new RxHistoryApi();
        this.recordingApi = new RxRecordingApi();
        this.bloodPressureApi = new RxBloodPressureApi();
        this.heartRateApi = new RxHeartRateApi();
        this.googleApiClient = googleApiClient;
    }

    private Single<Boolean> checkIfGoogleFitSwitchEnabled(Context context, long l) {
        return Single.fromCallable(GoogleFitApiImpl$$Lambda$17.lambdaFactory$(context, l)).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void dumpDataSet(DataSet iterator) {
        if (((DataSet)((Object)iterator)).getDataPoints().size() == 0) {
            return;
        }
        Timber.d("Data returned for Data type: " + ((DataSet)((Object)iterator)).getDataType().getName(), new Object[0]);
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        iterator = ((DataSet)((Object)iterator)).getDataPoints().iterator();
        block0 : while (iterator.hasNext()) {
            DataPoint dataPoint = iterator.next();
            Timber.d("Data point:", new Object[0]);
            Timber.d("\tType: " + dataPoint.getDataType().getName(), new Object[0]);
            Timber.d("\tStart: " + dateFormat.format(dataPoint.getStartTime(TimeUnit.MILLISECONDS)), new Object[0]);
            Timber.d("\tEnd: " + dateFormat.format(dataPoint.getEndTime(TimeUnit.MILLISECONDS)), new Object[0]);
            Iterator<Field> iterator2 = dataPoint.getDataType().getFields().iterator();
            do {
                if (!iterator2.hasNext()) continue block0;
                Field field = iterator2.next();
                if (Field.FIELD_ACTIVITY.getName().equals(field.getName())) {
                    Timber.d("\tField: " + field.getName() + " Value: " + dataPoint.getValue(field).asActivity(), new Object[0]);
                    continue;
                }
                Timber.d("\tField: " + field.getName() + " Value: " + dataPoint.getValue(field), new Object[0]);
            } while (true);
            break;
        }
        return;
    }

    public static boolean isSupportedActivity(String string2) {
        return "walking".equals(string2) || "walking.fitness".equals(string2) || "walking.treadmill".equals(string2) || "walking.stroller".equals(string2) || "walking.nordic".equals(string2) || "running".equals(string2) || "running.jogging".equals(string2) || "running.treadmill".equals(string2) || "biking".equals(string2) || "biking.hand".equals(string2) || "biking.mountain".equals(string2) || "biking.road".equals(string2) || "biking.spinning".equals(string2) || "biking.stationary".equals(string2) || "biking.utility".equals(string2);
    }

    static /* synthetic */ Boolean lambda$checkIfGoogleFitSwitchEnabled$20(Context context, long l) throws Exception {
        return GoogleFitDataHelper.Weight.hasGoogleFitIntegration(context, l);
    }

    static /* synthetic */ List lambda$fetchCurrentDayActivity$5(Throwable throwable) throws Exception {
        throwable.printStackTrace();
        Timber.d("***************** -> end fetchCurrentDayActivity *****************", new Object[0]);
        return new ArrayList();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static /* synthetic */ Integer lambda$fetchCurrentDaySteps$2(DataSet dataSet) throws Exception {
        int n = 0;
        if (dataSet.isEmpty()) {
            do {
                return n;
                break;
            } while (true);
        }
        n = dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
        return n;
    }

    static /* synthetic */ Integer lambda$fetchCurrentDaySteps$3(Throwable throwable) throws Exception {
        throwable.printStackTrace();
        return 0;
    }

    static /* synthetic */ List lambda$fetchCurrentDayStepsTimeline$9(Throwable throwable) throws Exception {
        throwable.printStackTrace();
        Timber.d("***************** -> end fetchCurrentDayStepsTimeline *****************", new Object[0]);
        return new ArrayList();
    }

    static /* synthetic */ List lambda$fetchDayHistory$7(Throwable throwable) throws Exception {
        throwable.printStackTrace();
        Timber.d("***************** -> end fetchDayHistory *****************", new Object[0]);
        return new ArrayList();
    }

    static /* synthetic */ List lambda$fetchDayStepsTimeline$11(Throwable throwable) throws Exception {
        throwable.printStackTrace();
        Timber.d("***************** -> end fetchDayStepsTimeline *****************", new Object[0]);
        return new ArrayList();
    }

    static /* synthetic */ Pair lambda$fetchMonthHistory$1(Throwable throwable) throws Exception {
        throwable.printStackTrace();
        return new Pair(new ArrayList(0), new ArrayList(0));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static /* synthetic */ List lambda$null$12(Context context, long l) throws Exception {
        ArrayList<BPMeasurement> arrayList = new ArrayList<BPMeasurement>(0);
        if ((context = MeasurementHelper.BloodPressure.getMeasurementsForSHealth(context, l)) == null) return arrayList;
        {
            try {
                if (!context.moveToFirst()) return arrayList;
                {
                    boolean bl;
                    do {
                        arrayList.add(MeasurementHelper.BloodPressure.parseMeasurement((Cursor)context));
                        context.moveToNext();
                    } while (!(bl = context.isAfterLast()));
                    return arrayList;
                }
            }
            finally {
                if (context != null) {
                    context.close();
                }
            }
        }
    }

    static /* synthetic */ SingleSource lambda$saveBloodPressureMeasurements$13(Context context, long l, Boolean bl) throws Exception {
        if (bl.booleanValue()) {
            return Single.fromCallable(GoogleFitApiImpl$$Lambda$21.lambdaFactory$(context, l));
        }
        return Single.just(new ArrayList(0));
    }

    private static void printDataInBuckets(List<Bucket> object) {
        if (object.size() > 0) {
            Timber.d("Number of returned DataSets is: " + object.size(), new Object[0]);
            object = object.iterator();
            while (object.hasNext()) {
                Iterator<DataSet> iterator = ((Bucket)object.next()).getDataSets().iterator();
                while (iterator.hasNext()) {
                    GoogleFitApiImpl.dumpDataSet(iterator.next());
                }
            }
        }
    }

    private Pair<List<StepDataBucket>, List<ActivityDataBucket>> transform(List<Bucket> object) {
        ArrayList<StepDataBucket> arrayList = new ArrayList<StepDataBucket>();
        ArrayList<ActivityDataBucket> arrayList2 = new ArrayList<ActivityDataBucket>();
        object = object.iterator();
        while (object.hasNext()) {
            for (DataSet dataSet : ((Bucket)object.next()).getDataSets()) {
                arrayList2.addAll(this.transformToActivityBuckets(dataSet));
                arrayList.addAll(this.transformToStepBuckets(dataSet));
            }
        }
        return new Pair<List<StepDataBucket>, List<ActivityDataBucket>>(arrayList, arrayList2);
    }

    private List<ActivityDataBucket> transformToActivityBuckets(DataSet object) {
        ArrayList<ActivityDataBucket> arrayList = new ArrayList<ActivityDataBucket>();
        for (DataPoint dataPoint : ((DataSet)object).getDataPoints()) {
            for (Field field : dataPoint.getDataType().getFields()) {
                if (!Field.FIELD_ACTIVITY.getName().equals(field.getName())) continue;
                arrayList.add(new ActivityDataBucket(dataPoint.getValue(Field.FIELD_DURATION).asInt(), dataPoint.getStartTime(TimeUnit.MILLISECONDS), dataPoint.getEndTime(TimeUnit.MILLISECONDS), dataPoint.getValue(Field.FIELD_ACTIVITY).asActivity()));
            }
        }
        return arrayList;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private List<StepDataBucket> transformToStepBuckets(DataSet object) {
        if (object == null) return new ArrayList<StepDataBucket>(0);
        if (((DataSet)object).isEmpty()) {
            return new ArrayList<StepDataBucket>(0);
        }
        ArrayList<StepDataBucket> arrayList = new ArrayList<StepDataBucket>();
        Iterator<DataPoint> iterator = ((DataSet)object).getDataPoints().iterator();
        block0: do {
            object = arrayList;
            if (!iterator.hasNext()) return object;
            object = iterator.next();
            Iterator<Field> iterator2 = ((DataPoint)object).getDataType().getFields().iterator();
            do {
                if (!iterator2.hasNext()) continue block0;
                Field field = iterator2.next();
                if (!Field.FIELD_STEPS.getName().equals(field.getName())) continue;
                arrayList.add(new StepDataBucket(((DataPoint)object).getStartTime(TimeUnit.MILLISECONDS), ((DataPoint)object).getEndTime(TimeUnit.MILLISECONDS), ((DataPoint)object).getValue(Field.FIELD_STEPS).asInt()));
            } while (true);
            break;
        } while (true);
    }

    @Override
    public Single<Boolean> deleteBloodPressureMeasurements(Context context, long l, long l2) {
        return this.checkIfGoogleFitSwitchEnabled(context, l2).flatMap(GoogleFitApiImpl$$Lambda$16.lambdaFactory$(this, l));
    }

    @Override
    public void disconnectFromFit() {
        this.configApi.disconnectFromFit(this.googleApiClient).subscribe();
    }

    @Override
    public Completable endGoogleFitSubscriptions() {
        return this.recordingApi.cancelSubscriptions(this.googleApiClient);
    }

    @Override
    public Single<List<ActivityDataBucket>> fetchCurrentDayActivity() {
        if (this.googleApiClient.isConnected()) {
            return this.historyApi.fetchCurrentDayActivity(this.googleApiClient).map(GoogleFitApiImpl$$Lambda$5.lambdaFactory$(this)).onErrorReturn(GoogleFitApiImpl$$Lambda$6.lambdaFactory$());
        }
        return Single.just(new ArrayList());
    }

    @Override
    public Single<Integer> fetchCurrentDaySteps() {
        if (this.googleApiClient.isConnected()) {
            return this.historyApi.fetchCurrentDaySteps(this.googleApiClient).map(GoogleFitApiImpl$$Lambda$3.lambdaFactory$()).onErrorReturn(GoogleFitApiImpl$$Lambda$4.lambdaFactory$());
        }
        return Single.just(0);
    }

    @Override
    public Single<List<StepDataBucket>> fetchCurrentDayStepsTimeline() {
        if (this.googleApiClient.isConnected()) {
            Calendar calendar = Calendar.getInstance();
            long l = calendar.getTimeInMillis();
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            long l2 = calendar.getTimeInMillis();
            return this.historyApi.fetchDayStepsTimeline(this.googleApiClient, l2, l).map(GoogleFitApiImpl$$Lambda$9.lambdaFactory$(this)).onErrorReturn(GoogleFitApiImpl$$Lambda$10.lambdaFactory$());
        }
        return Single.just(new ArrayList());
    }

    @Override
    public Single<List<ActivityDataBucket>> fetchDayHistory(long l, long l2) {
        if (this.googleApiClient.isConnected()) {
            return this.historyApi.fetchDayActivityHistory(this.googleApiClient, l, l2).map(GoogleFitApiImpl$$Lambda$7.lambdaFactory$(this)).onErrorReturn(GoogleFitApiImpl$$Lambda$8.lambdaFactory$());
        }
        return Single.just(new ArrayList());
    }

    @Override
    public Single<List<StepDataBucket>> fetchDayStepsTimeline(long l, long l2) {
        if (this.googleApiClient.isConnected()) {
            return this.historyApi.fetchDayStepsTimeline(this.googleApiClient, l, l2).map(GoogleFitApiImpl$$Lambda$11.lambdaFactory$(this)).onErrorReturn(GoogleFitApiImpl$$Lambda$12.lambdaFactory$());
        }
        return Single.just(new ArrayList());
    }

    @Override
    public Single<Pair<List<StepDataBucket>, List<ActivityDataBucket>>> fetchMonthHistory(long l) {
        if (this.googleApiClient.isConnected()) {
            return this.historyApi.fetchMonthHistory(this.googleApiClient, l).map(GoogleFitApiImpl$$Lambda$1.lambdaFactory$(this)).onErrorReturn(GoogleFitApiImpl$$Lambda$2.lambdaFactory$());
        }
        return Single.just(new Pair(new ArrayList(0), new ArrayList(0)));
    }

    /* synthetic */ SingleSource lambda$deleteBloodPressureMeasurements$19(long l, Boolean bl) throws Exception {
        if (bl.booleanValue()) {
            return this.bloodPressureApi.deleteBloodPressureFromGoogleFit(this.googleApiClient, l).flatMap(GoogleFitApiImpl$$Lambda$18.lambdaFactory$(this, l));
        }
        return Single.just(Boolean.FALSE);
    }

    /* synthetic */ List lambda$fetchCurrentDayActivity$4(List object) throws Exception {
        Timber.d("***************** -> begin fetchCurrentDayActivity *****************", new Object[0]);
        GoogleFitApiImpl.printDataInBuckets((List<Bucket>)object);
        Timber.d("***************** -> end fetchCurrentDayActivity *****************", new Object[0]);
        ArrayList<ActivityDataBucket> arrayList = new ArrayList<ActivityDataBucket>();
        object = object.iterator();
        while (object.hasNext()) {
            Iterator<DataSet> iterator = ((Bucket)object.next()).getDataSets().iterator();
            while (iterator.hasNext()) {
                arrayList.addAll(this.transformToActivityBuckets(iterator.next()));
            }
        }
        return arrayList;
    }

    /* synthetic */ List lambda$fetchCurrentDayStepsTimeline$8(List object) throws Exception {
        Timber.d("***************** -> begin fetchCurrentDayStepsTimeline *****************", new Object[0]);
        GoogleFitApiImpl.printDataInBuckets((List<Bucket>)object);
        Timber.d("***************** -> end fetchCurrentDayStepsTimeline *****************", new Object[0]);
        ArrayList<StepDataBucket> arrayList = new ArrayList<StepDataBucket>();
        if (object.size() > 0) {
            Timber.d("Number of returned DataSets is: " + object.size(), new Object[0]);
            object = object.iterator();
            while (object.hasNext()) {
                for (DataSet dataSet : ((Bucket)object.next()).getDataSets()) {
                    arrayList.addAll(this.transformToStepBuckets(dataSet));
                    Timber.d("Data returned for Data type: " + dataSet.getDataType().getName(), new Object[0]);
                }
            }
        }
        return arrayList;
    }

    /* synthetic */ List lambda$fetchDayHistory$6(List object) throws Exception {
        Timber.d("***************** -> begin fetchDayHistory *****************", new Object[0]);
        GoogleFitApiImpl.printDataInBuckets((List<Bucket>)object);
        Timber.d("***************** -> end fetchDayHistory *****************", new Object[0]);
        ArrayList<ActivityDataBucket> arrayList = new ArrayList<ActivityDataBucket>();
        object = object.iterator();
        while (object.hasNext()) {
            Iterator<DataSet> iterator = ((Bucket)object.next()).getDataSets().iterator();
            while (iterator.hasNext()) {
                arrayList.addAll(this.transformToActivityBuckets(iterator.next()));
            }
        }
        return arrayList;
    }

    /* synthetic */ List lambda$fetchDayStepsTimeline$10(List object) throws Exception {
        Timber.d("***************** -> begin fetchDayStepsTimeline *****************", new Object[0]);
        GoogleFitApiImpl.printDataInBuckets((List<Bucket>)object);
        Timber.d("***************** -> end fetchDayStepsTimeline *****************", new Object[0]);
        ArrayList<StepDataBucket> arrayList = new ArrayList<StepDataBucket>();
        if (object.size() > 0) {
            Timber.d("Number of returned DataSets is: " + object.size(), new Object[0]);
            object = object.iterator();
            while (object.hasNext()) {
                Iterator<DataSet> iterator = ((Bucket)object.next()).getDataSets().iterator();
                while (iterator.hasNext()) {
                    arrayList.addAll(this.transformToStepBuckets(iterator.next()));
                }
            }
        }
        return arrayList;
    }

    /* synthetic */ Pair lambda$fetchMonthHistory$0(List list) throws Exception {
        Timber.d("***************** -> begin fetchMonthHistory *****************", new Object[0]);
        GoogleFitApiImpl.printDataInBuckets(list);
        Timber.d("***************** -> end fetchMonthHistory *****************", new Object[0]);
        return this.transform(list);
    }

    /* synthetic */ SingleSource lambda$null$14(List list, Integer serializable) throws Exception {
        serializable = new ArrayList(list.size());
        for (int i = 0; i < list.size(); ++i) {
            serializable.add(new Pair<Long, Float>(((BPMeasurement)list.get((int)i)).measureDate.getTime(), Float.valueOf(((BPMeasurement)list.get((int)i)).pulse.floatValue())));
        }
        return this.heartRateApi.saveHeartRateToGoogleFit(this.googleApiClient, (List<Pair<Long, Float>>)((Object)serializable));
    }

    /* synthetic */ SingleSource lambda$null$16(BPMeasurement bPMeasurement, Boolean bl) throws Exception {
        return this.heartRateApi.saveHeartRateToGoogleFit(this.googleApiClient, bPMeasurement.pulse.intValue(), bPMeasurement.measureDate.getTime());
    }

    /* synthetic */ SingleSource lambda$null$18(long l, Boolean bl) throws Exception {
        return this.heartRateApi.deleteBloodPressureFromGoogleFit(this.googleApiClient, l);
    }

    /* synthetic */ SingleSource lambda$saveBloodPressureMeasurement$17(BPMeasurement bPMeasurement, Boolean bl) throws Exception {
        if (bl.booleanValue()) {
            Timber.d("isEnabled = " + bl, new Object[0]);
            return this.bloodPressureApi.saveBloodPressureToGoogleFit(this.googleApiClient, bPMeasurement).flatMap(GoogleFitApiImpl$$Lambda$19.lambdaFactory$(this, bPMeasurement));
        }
        return Single.just(Boolean.FALSE);
    }

    /* synthetic */ SingleSource lambda$saveBloodPressureMeasurements$15(List list) throws Exception {
        if (list.size() > 0) {
            return this.bloodPressureApi.saveBloodPressureHistoryToGoogleFit(this.googleApiClient, list).flatMap(GoogleFitApiImpl$$Lambda$20.lambdaFactory$(this, list));
        }
        return Single.just(0);
    }

    @Override
    public Single<DataReadResult> readBloodPressureMeasurements(Context context, long l) {
        return this.bloodPressureApi.readBloodPressureFromGoogleFit(this.googleApiClient);
    }

    public Single<Boolean> saveBloodPressureMeasurement(Context context, long l, BPMeasurement bPMeasurement) {
        return this.checkIfGoogleFitSwitchEnabled(context, l).flatMap(GoogleFitApiImpl$$Lambda$15.lambdaFactory$(this, bPMeasurement));
    }

    @Override
    public Single<Integer> saveBloodPressureMeasurements(Context context, long l) {
        return this.checkIfGoogleFitSwitchEnabled(context, l).flatMap(GoogleFitApiImpl$$Lambda$13.lambdaFactory$(context, l)).flatMap(GoogleFitApiImpl$$Lambda$14.lambdaFactory$(this));
    }

    @Override
    public Single<Boolean> startGoogleFitSubscriptions() {
        return this.recordingApi.checkSubscriptions(this.googleApiClient);
    }
}

