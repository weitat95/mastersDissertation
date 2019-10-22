/*
 * Decompiled with CFR 0.147.
 */
package com.firebase.jobdispatcher;

import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.ObservedUri;
import java.util.List;

public final class Trigger {
    public static final JobTrigger.ImmediateTrigger NOW = new JobTrigger.ImmediateTrigger();

    public static JobTrigger.ContentUriTrigger contentUriTrigger(List<ObservedUri> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Uris must not be null or empty.");
        }
        return new JobTrigger.ContentUriTrigger(list);
    }

    public static JobTrigger.ExecutionWindowTrigger executionWindow(int n, int n2) {
        if (n < 0) {
            throw new IllegalArgumentException("Window start can't be less than 0");
        }
        if (n2 < n) {
            throw new IllegalArgumentException("Window end can't be less than window start");
        }
        return new JobTrigger.ExecutionWindowTrigger(n, n2);
    }
}

