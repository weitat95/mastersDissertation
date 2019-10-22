/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.util.Log
 */
package com.firebase.jobdispatcher;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import com.firebase.jobdispatcher.GooglePlayReceiver;
import com.firebase.jobdispatcher.IJobCallback;
import com.firebase.jobdispatcher.JobInvocation;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobServiceConnection;

class ExecutionDelegator {
    private static final SimpleArrayMap<String, JobServiceConnection> serviceConnections = new SimpleArrayMap();
    private final Context context;
    private final IJobCallback execCallback = new IJobCallback.Stub(){

        @Override
        public void jobFinished(Bundle object, int n) {
            object = GooglePlayReceiver.getJobCoder().decode((Bundle)object);
            if (object == null) {
                Log.wtf((String)"FJD.ExternalReceiver", (String)"jobFinished: unknown invocation provided");
                return;
            }
            ExecutionDelegator.this.onJobFinishedMessage(((JobInvocation.Builder)object).build(), n);
        }
    };
    private final JobFinishedCallback jobFinishedCallback;

    ExecutionDelegator(Context context, JobFinishedCallback jobFinishedCallback) {
        this.context = context;
        this.jobFinishedCallback = jobFinishedCallback;
    }

    private Intent createBindIntent(JobParameters jobParameters) {
        Intent intent = new Intent("com.firebase.jobdispatcher.ACTION_EXECUTE");
        intent.setClassName(this.context, jobParameters.getService());
        return intent;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onJobFinishedMessage(JobInvocation jobInvocation, int n) {
        SimpleArrayMap<String, JobServiceConnection> simpleArrayMap = serviceConnections;
        synchronized (simpleArrayMap) {
            JobServiceConnection jobServiceConnection = serviceConnections.get(jobInvocation.getService());
            if (jobServiceConnection != null) {
                jobServiceConnection.onJobFinished(jobInvocation);
                if (jobServiceConnection.wasUnbound()) {
                    serviceConnections.remove(jobInvocation.getService());
                }
            }
        }
        this.jobFinishedCallback.onJobFinished(jobInvocation, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void stopJob(JobInvocation jobInvocation, boolean bl) {
        SimpleArrayMap<String, JobServiceConnection> simpleArrayMap = serviceConnections;
        synchronized (simpleArrayMap) {
            JobServiceConnection jobServiceConnection = serviceConnections.get(jobInvocation.getService());
            if (jobServiceConnection != null) {
                jobServiceConnection.onStop(jobInvocation, bl);
                if (jobServiceConnection.wasUnbound()) {
                    serviceConnections.remove(jobInvocation.getService());
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void executeJob(JobInvocation jobInvocation) {
        if (jobInvocation == null) {
            return;
        }
        SimpleArrayMap<String, JobServiceConnection> simpleArrayMap = serviceConnections;
        synchronized (simpleArrayMap) {
            JobServiceConnection jobServiceConnection;
            JobServiceConnection jobServiceConnection2 = serviceConnections.get(jobInvocation.getService());
            if (jobServiceConnection2 != null && !jobServiceConnection2.wasUnbound()) {
                jobServiceConnection = jobServiceConnection2;
                if (jobServiceConnection2.hasJobInvocation(jobInvocation)) {
                    jobServiceConnection = jobServiceConnection2;
                    if (!jobServiceConnection2.isConnected()) {
                        return;
                    }
                }
            } else {
                jobServiceConnection = new JobServiceConnection(this.execCallback, this.context);
                serviceConnections.put(jobInvocation.getService(), jobServiceConnection);
            }
            if (!jobServiceConnection.startJob(jobInvocation) && !this.context.bindService(this.createBindIntent(jobInvocation), (ServiceConnection)jobServiceConnection, 1)) {
                Log.e((String)"FJD.ExternalReceiver", (String)("Unable to bind to " + jobInvocation.getService()));
                jobServiceConnection.unbind();
            }
            return;
        }
    }

    static interface JobFinishedCallback {
        public void onJobFinished(JobInvocation var1, int var2);
    }

}

