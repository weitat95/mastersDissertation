/*
 * Decompiled with CFR 0.147.
 */
package com.firebase.jobdispatcher;

import com.firebase.jobdispatcher.JobParameters;
import java.util.List;

public interface JobValidator {
    public List<String> validate(JobParameters var1);
}

