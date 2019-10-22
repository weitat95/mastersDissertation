/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.job.JobInfo
 *  android.app.job.JobInfo$Builder
 *  android.app.job.JobParameters
 *  android.app.job.JobScheduler
 *  android.app.job.JobService
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 */
package com.getqardio.android.googlefit;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.getqardio.android.googlefit.ActivityDataBucket;
import com.getqardio.android.googlefit.ActivityGoalsJobService$$Lambda$1;
import com.getqardio.android.googlefit.ActivityGoalsJobService$$Lambda$2;
import com.getqardio.android.googlefit.ActivityGoalsJobService$$Lambda$3;
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

public class ActivityGoalsJobService
extends JobService
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    private JobParameters jobParameters;
    private GoogleApiClient mGoogleApiClient;

    public static boolean areJobsScheduled(Context object) {
        boolean bl;
        block1: {
            object = (JobScheduler)object.getSystemService("jobscheduler");
            boolean bl2 = false;
            object = object.getAllPendingJobs().iterator();
            do {
                bl = bl2;
                if (!object.hasNext()) break block1;
            } while (((JobInfo)object.next()).getId() != 20480);
            bl = true;
        }
        return bl;
    }

    private long getTimeOfAchievement() {
        return PreferenceManager.getDefaultSharedPreferences((Context)this.getApplicationContext()).getLong("goal_time", 0L);
    }

    private void saveTimeOfAchievement(long l) {
        PreferenceManager.getDefaultSharedPreferences((Context)this.getApplicationContext()).edit().putLong("goal_time", l).apply();
    }

    public static void scheduleActivityGoalJob(Context context) {
        ((JobScheduler)context.getSystemService("jobscheduler")).schedule(new JobInfo.Builder(20480, new ComponentName(context, ActivityGoalsJobService.class)).setPeriodic(900000L).build());
    }

    public static void unscheduleActivityGoalJob(Context context) {
        ((JobScheduler)context.getSystemService("jobscheduler")).cancel(20480);
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
        this.jobFinished(this.jobParameters, Boolean.FALSE.booleanValue());
    }

    /* synthetic */ void lambda$onConnected$2(Throwable throwable) throws Exception {
        this.mGoogleApiClient.disconnect();
        this.jobFinished(this.jobParameters, Boolean.FALSE.booleanValue());
    }

    @Override
    public void onConnected(Bundle object) {
        object = new GoogleFitApiImpl(this.mGoogleApiClient);
        Single.zip(object.fetchCurrentDaySteps(), object.fetchCurrentDayActivity(), ActivityGoalsJobService$$Lambda$1.lambdaFactory$(this)).subscribe(ActivityGoalsJobService$$Lambda$2.lambdaFactory$(this), ActivityGoalsJobService$$Lambda$3.lambdaFactory$(this));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.jobFinished(this.jobParameters, Boolean.FALSE.booleanValue());
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
        catch (Exception exception) {}
        this.jobFinished(this.jobParameters, Boolean.FALSE.booleanValue());
    }

    public boolean onStartJob(JobParameters jobParameters) {
        this.jobParameters = jobParameters;
        this.mGoogleApiClient = new GoogleApiClient.Builder(this.getApplicationContext()).addApi(Fitness.HISTORY_API).addApi(Fitness.RECORDING_API).addScope(new Scope("https://www.googleapis.com/auth/fitness.activity.write")).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        this.mGoogleApiClient.connect();
        return true;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}

