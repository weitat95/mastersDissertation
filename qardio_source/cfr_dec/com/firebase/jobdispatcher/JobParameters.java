/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.firebase.jobdispatcher;

import android.os.Bundle;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.RetryStrategy;

public interface JobParameters {
    public int[] getConstraints();

    public Bundle getExtras();

    public int getLifetime();

    public RetryStrategy getRetryStrategy();

    public String getService();

    public String getTag();

    public JobTrigger getTrigger();

    public boolean isRecurring();

    public boolean shouldReplaceCurrent();
}

