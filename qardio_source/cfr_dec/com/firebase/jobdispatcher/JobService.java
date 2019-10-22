/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.firebase.jobdispatcher;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import com.firebase.jobdispatcher.GooglePlayReceiver;
import com.firebase.jobdispatcher.IJobCallback;
import com.firebase.jobdispatcher.IRemoteJobService;
import com.firebase.jobdispatcher.JobInvocation;
import com.firebase.jobdispatcher.JobParameters;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Locale;

public abstract class JobService
extends Service {
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final IRemoteJobService.Stub binder;
    private final SimpleArrayMap<String, JobCallback> runningJobs = new SimpleArrayMap(1);

    public JobService() {
        this.binder = new IRemoteJobService.Stub(){

            @Override
            public void start(Bundle object, IJobCallback iJobCallback) {
                object = GooglePlayReceiver.getJobCoder().decode((Bundle)object);
                if (object == null) {
                    Log.wtf((String)"FJD.JobService", (String)"start: unknown invocation provided");
                    return;
                }
                JobService.this.start(((JobInvocation.Builder)object).build(), iJobCallback);
            }

            @Override
            public void stop(Bundle object, boolean bl) {
                object = GooglePlayReceiver.getJobCoder().decode((Bundle)object);
                if (object == null) {
                    Log.wtf((String)"FJD.JobService", (String)"stop: unknown invocation provided");
                    return;
                }
                JobService.this.stop(((JobInvocation.Builder)object).build(), bl);
            }
        };
    }

    protected final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(fileDescriptor, printWriter, arrstring);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void jobFinished(JobParameters object, boolean bl) {
        if (object == null) {
            Log.e((String)"FJD.JobService", (String)"jobFinished called with a null JobParameters");
            return;
        }
        SimpleArrayMap<String, JobCallback> simpleArrayMap = this.runningJobs;
        synchronized (simpleArrayMap) {
            object = this.runningJobs.remove(object.getTag());
            if (object != null) {
                int n = bl ? 1 : 0;
                ((JobCallback)object).sendResult(n);
            }
            return;
        }
    }

    public final IBinder onBind(Intent intent) {
        return this.binder;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public final void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    public final void onStart(Intent intent, int n) {
    }

    public final int onStartCommand(Intent intent, int n, int n2) {
        this.stopSelf(n2);
        return 2;
    }

    public abstract boolean onStartJob(JobParameters var1);

    public abstract boolean onStopJob(JobParameters var1);

    public final void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean onUnbind(Intent intent) {
        SimpleArrayMap<String, JobCallback> simpleArrayMap = this.runningJobs;
        synchronized (simpleArrayMap) {
            int n = this.runningJobs.size() - 1;
            while (n >= 0) {
                JobCallback jobCallback = this.runningJobs.remove(this.runningJobs.keyAt(n));
                if (jobCallback != null) {
                    int n2 = this.onStopJob(jobCallback.job) ? 1 : 2;
                    jobCallback.sendResult(n2);
                }
                --n;
            }
            return super.onUnbind(intent);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void start(final JobParameters jobParameters, IJobCallback iJobCallback) {
        SimpleArrayMap<String, JobCallback> simpleArrayMap = this.runningJobs;
        synchronized (simpleArrayMap) {
            if (this.runningJobs.containsKey(jobParameters.getTag())) {
                Log.w((String)"FJD.JobService", (String)String.format(Locale.US, "Job with tag = %s was already running.", jobParameters.getTag()));
                return;
            }
            this.runningJobs.put(jobParameters.getTag(), new JobCallback(jobParameters, iJobCallback));
            mainHandler.post(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    SimpleArrayMap simpleArrayMap = JobService.this.runningJobs;
                    synchronized (simpleArrayMap) {
                        JobCallback jobCallback;
                        if (!JobService.this.onStartJob(jobParameters) && (jobCallback = (JobCallback)JobService.this.runningJobs.remove(jobParameters.getTag())) != null) {
                            jobCallback.sendResult(0);
                        }
                        return;
                    }
                }
            });
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void stop(final JobParameters jobParameters, final boolean bl) {
        SimpleArrayMap<String, JobCallback> simpleArrayMap = this.runningJobs;
        synchronized (simpleArrayMap) {
            final JobCallback jobCallback = this.runningJobs.remove(jobParameters.getTag());
            if (jobCallback != null) {
                mainHandler.post(new Runnable(){

                    /*
                     * Enabled aggressive block sorting
                     */
                    @Override
                    public void run() {
                        boolean bl2 = JobService.this.onStopJob(jobParameters);
                        if (bl) {
                            JobCallback jobCallback2 = jobCallback;
                            int n = bl2 ? 1 : 0;
                            jobCallback2.sendResult(n);
                        }
                    }
                });
                return;
            }
            if (Log.isLoggable((String)"FJD.JobService", (int)3)) {
                Log.d((String)"FJD.JobService", (String)"Provided job has already been executed.");
            }
            return;
        }
    }

    private static final class JobCallback {
        final JobParameters job;
        final IJobCallback remoteCallback;

        private JobCallback(JobParameters jobParameters, IJobCallback iJobCallback) {
            this.job = jobParameters;
            this.remoteCallback = iJobCallback;
        }

        void sendResult(int n) {
            try {
                this.remoteCallback.jobFinished(GooglePlayReceiver.getJobCoder().encode(this.job, new Bundle()), n);
                return;
            }
            catch (RemoteException remoteException) {
                Log.e((String)"FJD.JobService", (String)"Failed to send result to driver", (Throwable)remoteException);
                return;
            }
        }
    }

}

