/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.firebase.jobdispatcher;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.GooglePlayReceiver;
import com.firebase.jobdispatcher.JobCoder;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.ObservedUri;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import java.util.List;

final class GooglePlayJobWriter {
    private final JobCoder jobCoder = new JobCoder("com.firebase.jobdispatcher.");

    GooglePlayJobWriter() {
    }

    private static int convertConstraintsToLegacyNetConstant(int n) {
        int n2 = 2;
        if ((n & 2) == 2) {
            n2 = 0;
        }
        if ((n & 1) == 1) {
            n2 = 1;
        }
        return n2;
    }

    private static int convertRetryPolicyToLegacyVersion(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 2: 
        }
        return 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void writeConstraintsToBundle(JobParameters jobParameters, Bundle bundle) {
        boolean bl = true;
        int n = Constraint.compact(jobParameters.getConstraints());
        boolean bl2 = (n & 4) == 4;
        bundle.putBoolean("requiresCharging", bl2);
        bl2 = (n & 8) == 8 ? bl : false;
        bundle.putBoolean("requiresIdle", bl2);
        bundle.putInt("requiredNetwork", GooglePlayJobWriter.convertConstraintsToLegacyNetConstant(n));
    }

    private static void writeContentUriTriggerToBundle(Bundle bundle, JobTrigger.ContentUriTrigger contentUriTrigger) {
        bundle.putInt("trigger_type", 3);
        int n = contentUriTrigger.getUris().size();
        int[] arrn = new int[n];
        Uri[] arruri = new Uri[n];
        for (int i = 0; i < n; ++i) {
            ObservedUri observedUri = contentUriTrigger.getUris().get(i);
            arrn[i] = observedUri.getFlags();
            arruri[i] = observedUri.getUri();
        }
        bundle.putIntArray("content_uri_flags_array", arrn);
        bundle.putParcelableArray("content_uri_array", (Parcelable[])arruri);
    }

    private static void writeExecutionWindowTriggerToBundle(JobParameters jobParameters, Bundle bundle, JobTrigger.ExecutionWindowTrigger executionWindowTrigger) {
        bundle.putInt("trigger_type", 1);
        if (jobParameters.isRecurring()) {
            bundle.putLong("period", (long)executionWindowTrigger.getWindowEnd());
            bundle.putLong("period_flex", (long)(executionWindowTrigger.getWindowEnd() - executionWindowTrigger.getWindowStart()));
            return;
        }
        bundle.putLong("window_start", (long)executionWindowTrigger.getWindowStart());
        bundle.putLong("window_end", (long)executionWindowTrigger.getWindowEnd());
    }

    private static void writeImmediateTriggerToBundle(Bundle bundle) {
        bundle.putInt("trigger_type", 2);
        bundle.putLong("window_start", 0L);
        bundle.putLong("window_end", 1L);
    }

    private static void writeRetryStrategyToBundle(JobParameters object, Bundle bundle) {
        object = object.getRetryStrategy();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("retry_policy", GooglePlayJobWriter.convertRetryPolicyToLegacyVersion(((RetryStrategy)object).getPolicy()));
        bundle2.putInt("initial_backoff_seconds", ((RetryStrategy)object).getInitialBackoff());
        bundle2.putInt("maximum_backoff_seconds", ((RetryStrategy)object).getMaximumBackoff());
        bundle.putBundle("retryStrategy", bundle2);
    }

    private static void writeTriggerToBundle(JobParameters jobParameters, Bundle bundle) {
        JobTrigger jobTrigger = jobParameters.getTrigger();
        if (jobTrigger == Trigger.NOW) {
            GooglePlayJobWriter.writeImmediateTriggerToBundle(bundle);
            return;
        }
        if (jobTrigger instanceof JobTrigger.ExecutionWindowTrigger) {
            GooglePlayJobWriter.writeExecutionWindowTriggerToBundle(jobParameters, bundle, (JobTrigger.ExecutionWindowTrigger)jobTrigger);
            return;
        }
        if (jobTrigger instanceof JobTrigger.ContentUriTrigger) {
            GooglePlayJobWriter.writeContentUriTriggerToBundle(bundle, (JobTrigger.ContentUriTrigger)jobTrigger);
            return;
        }
        throw new IllegalArgumentException("Unknown trigger: " + jobTrigger.getClass());
    }

    /*
     * Enabled aggressive block sorting
     */
    public Bundle writeToBundle(JobParameters jobParameters, Bundle bundle) {
        Bundle bundle2;
        bundle.putString("tag", jobParameters.getTag());
        bundle.putBoolean("update_current", jobParameters.shouldReplaceCurrent());
        boolean bl = jobParameters.getLifetime() == 2;
        bundle.putBoolean("persisted", bl);
        bundle.putString("service", GooglePlayReceiver.class.getName());
        GooglePlayJobWriter.writeTriggerToBundle(jobParameters, bundle);
        GooglePlayJobWriter.writeConstraintsToBundle(jobParameters, bundle);
        GooglePlayJobWriter.writeRetryStrategyToBundle(jobParameters, bundle);
        Bundle bundle3 = bundle2 = jobParameters.getExtras();
        if (bundle2 == null) {
            bundle3 = new Bundle();
        }
        bundle.putBundle("extras", this.jobCoder.encode(jobParameters, bundle3));
        return bundle;
    }
}

