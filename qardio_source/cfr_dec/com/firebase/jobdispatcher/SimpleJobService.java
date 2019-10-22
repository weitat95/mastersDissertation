/*
 * Decompiled with CFR 0.147.
 */
package com.firebase.jobdispatcher;

import android.support.v4.util.SimpleArrayMap;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public abstract class SimpleJobService
extends JobService {
    private final SimpleArrayMap<JobParameters, Object> runningJobs = new SimpleArrayMap();
}

