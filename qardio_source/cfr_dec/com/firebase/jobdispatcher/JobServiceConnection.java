/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.firebase.jobdispatcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.firebase.jobdispatcher.GooglePlayReceiver;
import com.firebase.jobdispatcher.IJobCallback;
import com.firebase.jobdispatcher.IRemoteJobService;
import com.firebase.jobdispatcher.JobInvocation;
import com.firebase.jobdispatcher.JobParameters;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class JobServiceConnection
implements ServiceConnection {
    private IRemoteJobService binder;
    private final IJobCallback callback;
    private final Context context;
    private final Map<JobInvocation, Boolean> jobStatuses = new HashMap<JobInvocation, Boolean>();
    private boolean wasUnbound = false;

    JobServiceConnection(IJobCallback iJobCallback, Context context) {
        this.callback = iJobCallback;
        this.context = context;
    }

    private static Bundle encodeJob(JobParameters jobParameters) {
        return GooglePlayReceiver.getJobCoder().encode(jobParameters, new Bundle());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void stopJob(boolean bl, JobInvocation jobInvocation) {
        synchronized (this) {
            try {
                this.binder.stop(JobServiceConnection.encodeJob(jobInvocation), bl);
                do {
                    return;
                    break;
                } while (true);
            }
            catch (RemoteException remoteException) {
                Log.e((String)"FJD.ExternalReceiver", (String)"Failed to stop a job", (Throwable)remoteException);
                this.unbind();
                return;
            }
            finally {
            }
        }
    }

    boolean hasJobInvocation(JobInvocation jobInvocation) {
        synchronized (this) {
            boolean bl = this.jobStatuses.containsKey(jobInvocation);
            return bl;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    boolean isConnected() {
        synchronized (this) {
            IRemoteJobService iRemoteJobService = this.binder;
            if (iRemoteJobService == null) return false;
            return true;
        }
    }

    void onJobFinished(JobInvocation jobInvocation) {
        synchronized (this) {
            this.jobStatuses.remove(jobInvocation);
            if (this.jobStatuses.isEmpty()) {
                this.unbind();
            }
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void onServiceConnected(ComponentName var1_1, IBinder var2_2) {
        // MONITORENTER : this
        if (!this.wasUnbound()) {
            this.binder = IRemoteJobService.Stub.asInterface((IBinder)var2_2);
            var2_2 = new HashSet<E>();
            var4_4 = this.jobStatuses.entrySet().iterator();
        } else {
            Log.w((String)"FJD.ExternalReceiver", (String)"Connection have been used already.");
            do {
                // MONITOREXIT : this
                return;
                break;
            } while (true);
        }
        while (var4_4.hasNext()) {
            var1_1 = var4_4.next();
            var3_5 = Boolean.FALSE.equals(var1_1.getValue());
            if (!var3_5) continue;
            try {
                this.binder.start(JobServiceConnection.encodeJob((JobParameters)var1_1.getKey()), this.callback);
                var2_2.add(var1_1.getKey());
            }
            catch (RemoteException var2_3) {
                Log.e((String)"FJD.ExternalReceiver", (String)("Failed to start job " + var1_1.getKey()), (Throwable)var2_3);
                this.unbind();
                return;
            }
        }
        var1_1 = var2_2.iterator();
        do {
            if (!var1_1.hasNext()) ** continue;
            var2_2 = (JobInvocation)var1_1.next();
            this.jobStatuses.put((JobInvocation)var2_2, true);
        } while (true);
    }

    public void onServiceDisconnected(ComponentName componentName) {
        synchronized (this) {
            this.unbind();
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onStop(JobInvocation jobInvocation, boolean bl) {
        synchronized (this) {
            if (!this.wasUnbound()) {
                void var2_2;
                if (Boolean.TRUE.equals(this.jobStatuses.remove(jobInvocation)) && this.isConnected()) {
                    this.stopJob((boolean)var2_2, jobInvocation);
                }
                if (var2_2 == false && this.jobStatuses.isEmpty()) {
                    this.unbind();
                }
            } else {
                Log.w((String)"FJD.ExternalReceiver", (String)"Can't send stop request because service was unbound.");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean startJob(JobInvocation jobInvocation) {
        synchronized (this) {
            boolean bl = this.isConnected();
            if (bl) {
                Boolean bl2 = this.jobStatuses.get(jobInvocation);
                if (Boolean.TRUE.equals(bl2)) {
                    Log.w((String)"FJD.ExternalReceiver", (String)("Received an execution request for already running job " + jobInvocation));
                    this.stopJob(false, jobInvocation);
                }
                try {
                    this.binder.start(JobServiceConnection.encodeJob(jobInvocation), this.callback);
                }
                catch (RemoteException remoteException) {
                    Log.e((String)"FJD.ExternalReceiver", (String)("Failed to start the job " + jobInvocation), (Throwable)remoteException);
                    this.unbind();
                    return false;
                }
            }
            this.jobStatuses.put(jobInvocation, bl);
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void unbind() {
        synchronized (this) {
            if (!this.wasUnbound()) {
                this.binder = null;
                this.wasUnbound = true;
                try {
                    this.context.unbindService((ServiceConnection)this);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    Log.w((String)"FJD.ExternalReceiver", (String)("Error unbinding service: " + illegalArgumentException.getMessage()));
                }
            }
            return;
        }
    }

    boolean wasUnbound() {
        synchronized (this) {
            boolean bl = this.wasUnbound;
            return bl;
        }
    }
}

