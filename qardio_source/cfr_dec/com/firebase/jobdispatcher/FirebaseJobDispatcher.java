/*
 * Decompiled with CFR 0.147.
 */
package com.firebase.jobdispatcher;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobValidator;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.ValidationEnforcer;

public final class FirebaseJobDispatcher {
    private final Driver driver;
    private final RetryStrategy.Builder retryStrategyBuilder;
    private final ValidationEnforcer validator;

    public FirebaseJobDispatcher(Driver driver) {
        this.driver = driver;
        this.validator = new ValidationEnforcer(driver.getValidator());
        this.retryStrategyBuilder = new RetryStrategy.Builder(this.validator);
    }

    public int cancel(String string2) {
        if (!this.driver.isAvailable()) {
            return 2;
        }
        return this.driver.cancel(string2);
    }

    public void mustSchedule(Job job) {
        if (this.schedule(job) != 0) {
            throw new ScheduleFailedException();
        }
    }

    public Job.Builder newJobBuilder() {
        return new Job.Builder(this.validator);
    }

    public int schedule(Job job) {
        if (!this.driver.isAvailable()) {
            return 2;
        }
        return this.driver.schedule(job);
    }

    public static final class ScheduleFailedException
    extends RuntimeException {
    }

}

