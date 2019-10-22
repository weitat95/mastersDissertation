/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Messenger
 *  android.util.Log
 *  android.util.Pair
 */
package com.firebase.jobdispatcher;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import android.util.Pair;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.ExecutionDelegator;
import com.firebase.jobdispatcher.GooglePlayCallbackExtractor;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.GooglePlayMessageHandler;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobCallback;
import com.firebase.jobdispatcher.JobCoder;
import com.firebase.jobdispatcher.JobInvocation;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.JobValidator;
import com.firebase.jobdispatcher.ValidationEnforcer;

public class GooglePlayReceiver
extends Service
implements ExecutionDelegator.JobFinishedCallback {
    private static final SimpleArrayMap<String, SimpleArrayMap<String, JobCallback>> callbacks;
    private static final JobCoder prefixedCoder;
    private final GooglePlayCallbackExtractor callbackExtractor = new GooglePlayCallbackExtractor();
    Driver driver;
    private ExecutionDelegator executionDelegator;
    private int latestStartId;
    Messenger serviceMessenger;
    ValidationEnforcer validationEnforcer;

    static {
        prefixedCoder = new JobCoder("com.firebase.jobdispatcher.");
        callbacks = new SimpleArrayMap(1);
    }

    private Driver getGooglePlayDriver() {
        synchronized (this) {
            if (this.driver == null) {
                this.driver = new GooglePlayDriver(this.getApplicationContext());
            }
            Driver driver = this.driver;
            return driver;
        }
    }

    static JobCoder getJobCoder() {
        return prefixedCoder;
    }

    private Messenger getServiceMessenger() {
        synchronized (this) {
            if (this.serviceMessenger == null) {
                this.serviceMessenger = new Messenger((Handler)new GooglePlayMessageHandler(Looper.getMainLooper(), this));
            }
            Messenger messenger = this.serviceMessenger;
            return messenger;
        }
    }

    private ValidationEnforcer getValidationEnforcer() {
        synchronized (this) {
            if (this.validationEnforcer == null) {
                this.validationEnforcer = new ValidationEnforcer(this.getGooglePlayDriver().getValidator());
            }
            ValidationEnforcer validationEnforcer = this.validationEnforcer;
            return validationEnforcer;
        }
    }

    private static boolean needsToBeRescheduled(JobParameters jobParameters, int n) {
        return jobParameters.isRecurring() && jobParameters.getTrigger() instanceof JobTrigger.ContentUriTrigger && n != 1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void onSchedule(Job job) {
        SimpleArrayMap<String, SimpleArrayMap<String, JobCallback>> simpleArrayMap = callbacks;
        synchronized (simpleArrayMap) {
            SimpleArrayMap<String, JobCallback> simpleArrayMap2 = callbacks.get(job.getService());
            if (simpleArrayMap2 == null) {
                return;
            }
            if (simpleArrayMap2.get(job.getTag()) == null) {
                return;
            }
            ExecutionDelegator.stopJob(new JobInvocation.Builder().setTag(job.getTag()).setService(job.getService()).setTrigger(job.getTrigger()).build(), false);
            return;
        }
    }

    private void reschedule(JobInvocation jobParameters) {
        jobParameters = new Job.Builder(this.getValidationEnforcer(), jobParameters).setReplaceCurrent(true).build();
        this.getGooglePlayDriver().schedule((Job)jobParameters);
    }

    private static void sendResultSafely(JobCallback jobCallback, int n) {
        try {
            jobCallback.jobFinished(n);
            return;
        }
        catch (Throwable throwable) {
            Log.e((String)"FJD.GooglePlayReceiver", (String)"Encountered error running callback", (Throwable)throwable.getCause());
            return;
        }
    }

    ExecutionDelegator getExecutionDelegator() {
        synchronized (this) {
            if (this.executionDelegator == null) {
                this.executionDelegator = new ExecutionDelegator((Context)this, this);
            }
            ExecutionDelegator executionDelegator = this.executionDelegator;
            return executionDelegator;
        }
    }

    public IBinder onBind(Intent intent) {
        if (intent == null || Build.VERSION.SDK_INT < 21 || !"com.google.android.gms.gcm.ACTION_TASK_READY".equals(intent.getAction())) {
            return null;
        }
        return this.getServiceMessenger().getBinder();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onJobFinished(JobInvocation jobInvocation, int n) {
        SimpleArrayMap<String, SimpleArrayMap<String, JobCallback>> simpleArrayMap = callbacks;
        synchronized (simpleArrayMap) {
            JobCallback jobCallback;
            SimpleArrayMap<String, JobCallback> simpleArrayMap2;
            block17: {
                block16: {
                    simpleArrayMap2 = callbacks.get(jobInvocation.getService());
                    if (simpleArrayMap2 != null) break block16;
                    if (!callbacks.isEmpty()) return;
                    this.stopSelf(this.latestStartId);
                    return;
                }
                jobCallback = simpleArrayMap2.remove(jobInvocation.getTag());
                if (jobCallback != null) break block17;
                if (!callbacks.isEmpty()) return;
                this.stopSelf(this.latestStartId);
                return;
            }
            try {
                if (simpleArrayMap2.isEmpty()) {
                    callbacks.remove(jobInvocation.getService());
                }
                if (GooglePlayReceiver.needsToBeRescheduled(jobInvocation, n)) {
                    this.reschedule(jobInvocation);
                } else {
                    if (Log.isLoggable((String)"FJD.GooglePlayReceiver", (int)2)) {
                        Log.v((String)"FJD.GooglePlayReceiver", (String)("sending jobFinished for " + jobInvocation.getTag() + " = " + n));
                    }
                    GooglePlayReceiver.sendResultSafely(jobCallback, n);
                }
                return;
            }
            finally {
                if (callbacks.isEmpty()) {
                    this.stopSelf(this.latestStartId);
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final int onStartCommand(Intent object, int n, int n2) {
        block20: {
            String string2;
            block19: {
                block18: {
                    super.onStartCommand(object, n, n2);
                    if (object != null) break block18;
                    Log.w((String)"FJD.GooglePlayReceiver", (String)"Null Intent passed, terminating");
                    object = callbacks;
                    synchronized (object) {
                        this.latestStartId = n2;
                        if (!callbacks.isEmpty()) return 2;
                        this.stopSelf(this.latestStartId);
                        return 2;
                    }
                }
                string2 = object.getAction();
                if (!"com.google.android.gms.gcm.ACTION_TASK_READY".equals(string2)) break block19;
                this.getExecutionDelegator().executeJob(this.prepareJob((Intent)object));
                object = callbacks;
                synchronized (object) {
                    this.latestStartId = n2;
                    if (!callbacks.isEmpty()) return 2;
                    this.stopSelf(this.latestStartId);
                    return 2;
                }
            }
            boolean bl = "com.google.android.gms.gcm.SERVICE_ACTION_INITIALIZE".equals(string2);
            if (!bl) break block20;
            object = callbacks;
            synchronized (object) {
                this.latestStartId = n2;
                if (!callbacks.isEmpty()) return 2;
                this.stopSelf(this.latestStartId);
                return 2;
            }
        }
        try {
            Log.e((String)"FJD.GooglePlayReceiver", (String)"Unknown action received, terminating");
            return 2;
        }
        finally {
            object = callbacks;
            synchronized (object) {
                this.latestStartId = n2;
                if (callbacks.isEmpty()) {
                    this.stopSelf(this.latestStartId);
                }
            }
        }
    }

    JobInvocation prepareJob(Intent pair) {
        if ((pair = pair.getExtras()) == null) {
            Log.e((String)"FJD.GooglePlayReceiver", (String)"No data provided, terminating");
            return null;
        }
        if ((pair = this.callbackExtractor.extractCallback((Bundle)pair)) == null) {
            Log.i((String)"FJD.GooglePlayReceiver", (String)"no callback found");
            return null;
        }
        return this.prepareJob((JobCallback)pair.first, (Bundle)pair.second);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    JobInvocation prepareJob(JobCallback jobCallback, Bundle object) {
        JobInvocation jobInvocation = prefixedCoder.decodeIntentBundle((Bundle)object);
        if (jobInvocation == null) {
            Log.e((String)"FJD.GooglePlayReceiver", (String)"unable to decode job");
            GooglePlayReceiver.sendResultSafely(jobCallback, 2);
            return null;
        }
        SimpleArrayMap<String, SimpleArrayMap<String, JobCallback>> simpleArrayMap = callbacks;
        synchronized (simpleArrayMap) {
            SimpleArrayMap<String, JobCallback> simpleArrayMap2 = callbacks.get(jobInvocation.getService());
            object = simpleArrayMap2;
            if (simpleArrayMap2 == null) {
                object = new SimpleArrayMap(1);
                callbacks.put(jobInvocation.getService(), (SimpleArrayMap<String, JobCallback>)object);
            }
            object.put(jobInvocation.getTag(), jobCallback);
            return jobInvocation;
        }
    }
}

