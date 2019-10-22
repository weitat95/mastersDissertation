/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 */
package com.getqardio.android.googlefit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.Trigger;
import com.getqardio.android.googlefit.ActivityDataBucket;
import com.getqardio.android.googlefit.ActivityGoalsJobServiceCompat$$Lambda$1;
import com.getqardio.android.googlefit.ActivityGoalsJobServiceCompat$$Lambda$2;
import com.getqardio.android.googlefit.ActivityGoalsJobServiceCompat$$Lambda$3;
import com.getqardio.android.googlefit.GoogleFitApiImpl;
import com.getqardio.android.mvp.activity.Util.ActivityNotificationUtil;
import com.getqardio.android.mvp.activity.Util.ActivityUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class ActivityGoalsJobServiceCompat
extends JobService
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    private JobParameters jobParameters;
    private GoogleApiClient mGoogleApiClient;

    private long getTimeOfAchievement() {
        return PreferenceManager.getDefaultSharedPreferences((Context)this.getApplicationContext()).getLong("goal_time", 0L);
    }

    private void saveTimeOfAchievement(long l) {
        PreferenceManager.getDefaultSharedPreferences((Context)this.getApplicationContext()).edit().putLong("goal_time", l).apply();
    }

    public static void scheduleActivityGoalJob(Context object) {
        object = new FirebaseJobDispatcher(new GooglePlayDriver((Context)object));
        ((FirebaseJobDispatcher)object).mustSchedule(((FirebaseJobDispatcher)object).newJobBuilder().setService(ActivityGoalsJobServiceCompat.class).setTag("REQUEST_ACTIVITY_GOAL_JOB_ID").setRecurring(true).setReplaceCurrent(true).setTrigger(Trigger.executionWindow(0, 900)).build());
    }

    public static void unscheduleActivityGoalJob(Context context) {
        new FirebaseJobDispatcher(new GooglePlayDriver(context)).cancel("REQUEST_ACTIVITY_GOAL_JOB_ID");
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ Boolean lambda$onConnected$0(Integer object, List object2) throws Exception {
        Object object3;
        int n = 0;
        Iterator iterator = object2.iterator();
        while (iterator.hasNext()) {
            object3 = (ActivityDataBucket)iterator.next();
            if (!GoogleFitApiImpl.isSupportedActivity(((ActivityDataBucket)object3).getActivityType())) continue;
            n = (int)((long)n + ((ActivityDataBucket)object3).getDuration());
        }
        if ((Integer)object > 5000 || (n /= 60000) > 30) {
            int[] arrn;
            int[] arrn2 = arrn = new int[3];
            arrn[0] = 2131362101;
            arrn2[1] = 2131362102;
            arrn2[2] = 2131362103;
            n = (Integer)object > 5000 ? 2131362104 : 2131362100;
            object3 = this.getString(n);
            n = (Integer)object > 5000 ? 5000 : 30;
            object = String.format((String)object3, n);
            String string2 = this.getString(arrn[(int)((double)arrn.length * Math.random())]);
            if (!ActivityUtils.isSameDay(this.getTimeOfAchievement(), System.currentTimeMillis())) {
                ActivityNotificationUtil.sendNotification(this.getApplicationContext(), string2, (String)object);
                this.saveTimeOfAchievement(Calendar.getInstance().getTimeInMillis());
            }
        }
        return Boolean.TRUE;
    }

    /* synthetic */ void lambda$onConnected$1(Boolean bl) throws Exception {
        this.mGoogleApiClient.disconnect();
        this.jobFinished(this.jobParameters, Boolean.FALSE);
    }

    /* synthetic */ void lambda$onConnected$2(Throwable throwable) throws Exception {
        this.mGoogleApiClient.disconnect();
        this.jobFinished(this.jobParameters, Boolean.FALSE);
    }

    @Override
    public void onConnected(Bundle object) {
        object = new GoogleFitApiImpl(this.mGoogleApiClient);
        Single.zip(object.fetchCurrentDaySteps(), object.fetchCurrentDayActivity(), ActivityGoalsJobServiceCompat$$Lambda$1.lambdaFactory$(this)).subscribe(ActivityGoalsJobServiceCompat$$Lambda$2.lambdaFactory$(this), ActivityGoalsJobServiceCompat$$Lambda$3.lambdaFactory$(this));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.jobFinished(this.jobParameters, Boolean.FALSE);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onConnectionSuspended(int n) {
        try {
            this.mGoogleApiClient.disconnect();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        this.jobFinished(this.jobParameters, Boolean.FALSE);
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        this.jobParameters = jobParameters;
        this.mGoogleApiClient = new GoogleApiClient.Builder(this.getApplicationContext()).addApi(Fitness.HISTORY_API).addApi(Fitness.RECORDING_API).addScope(new Scope("https://www.googleapis.com/auth/fitness.activity.write")).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        this.mGoogleApiClient.connect();
        return Boolean.TRUE;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}

