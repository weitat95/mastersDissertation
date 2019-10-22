/*
 * Decompiled with CFR 0.147.
 */
package com.firebase.jobdispatcher;

import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobValidator;

public interface Driver {
    public int cancel(String var1);

    public JobValidator getValidator();

    public boolean isAvailable();

    public int schedule(Job var1);
}

