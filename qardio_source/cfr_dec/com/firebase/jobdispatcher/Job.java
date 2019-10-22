/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.firebase.jobdispatcher;

import android.os.Bundle;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.firebase.jobdispatcher.ValidationEnforcer;

public final class Job
implements JobParameters {
    private final int[] constraints;
    private final Bundle extras;
    private final int lifetime;
    private final boolean recurring;
    private final boolean replaceCurrent;
    private final RetryStrategy retryStrategy;
    private final String service;
    private final String tag;
    private final JobTrigger trigger;

    /*
     * Enabled aggressive block sorting
     */
    private Job(Builder builder) {
        this.service = builder.serviceClassName;
        int[] arrn = builder.extras == null ? null : new Bundle(builder.extras);
        this.extras = arrn;
        this.tag = builder.tag;
        this.trigger = builder.trigger;
        this.retryStrategy = builder.retryStrategy;
        this.lifetime = builder.lifetime;
        this.recurring = builder.recurring;
        arrn = builder.constraints != null ? builder.constraints : new int[0];
        this.constraints = arrn;
        this.replaceCurrent = builder.replaceCurrent;
    }

    @Override
    public int[] getConstraints() {
        return this.constraints;
    }

    @Override
    public Bundle getExtras() {
        return this.extras;
    }

    @Override
    public int getLifetime() {
        return this.lifetime;
    }

    @Override
    public RetryStrategy getRetryStrategy() {
        return this.retryStrategy;
    }

    @Override
    public String getService() {
        return this.service;
    }

    @Override
    public String getTag() {
        return this.tag;
    }

    @Override
    public JobTrigger getTrigger() {
        return this.trigger;
    }

    @Override
    public boolean isRecurring() {
        return this.recurring;
    }

    @Override
    public boolean shouldReplaceCurrent() {
        return this.replaceCurrent;
    }

    public static final class Builder
    implements JobParameters {
        private int[] constraints;
        private Bundle extras;
        private int lifetime = 1;
        private boolean recurring = false;
        private boolean replaceCurrent = false;
        private RetryStrategy retryStrategy;
        private String serviceClassName;
        private String tag;
        private JobTrigger trigger = Trigger.NOW;
        private final ValidationEnforcer validator;

        Builder(ValidationEnforcer validationEnforcer) {
            this.retryStrategy = RetryStrategy.DEFAULT_EXPONENTIAL;
            this.validator = validationEnforcer;
        }

        Builder(ValidationEnforcer validationEnforcer, JobParameters jobParameters) {
            this.retryStrategy = RetryStrategy.DEFAULT_EXPONENTIAL;
            this.validator = validationEnforcer;
            this.tag = jobParameters.getTag();
            this.serviceClassName = jobParameters.getService();
            this.trigger = jobParameters.getTrigger();
            this.recurring = jobParameters.isRecurring();
            this.lifetime = jobParameters.getLifetime();
            this.constraints = jobParameters.getConstraints();
            this.extras = jobParameters.getExtras();
            this.retryStrategy = jobParameters.getRetryStrategy();
        }

        public Job build() {
            this.validator.ensureValid(this);
            return new Job(this);
        }

        @Override
        public int[] getConstraints() {
            if (this.constraints == null) {
                return new int[0];
            }
            return this.constraints;
        }

        @Override
        public Bundle getExtras() {
            return this.extras;
        }

        @Override
        public int getLifetime() {
            return this.lifetime;
        }

        @Override
        public RetryStrategy getRetryStrategy() {
            return this.retryStrategy;
        }

        @Override
        public String getService() {
            return this.serviceClassName;
        }

        @Override
        public String getTag() {
            return this.tag;
        }

        @Override
        public JobTrigger getTrigger() {
            return this.trigger;
        }

        @Override
        public boolean isRecurring() {
            return this.recurring;
        }

        public Builder setRecurring(boolean bl) {
            this.recurring = bl;
            return this;
        }

        public Builder setReplaceCurrent(boolean bl) {
            this.replaceCurrent = bl;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder setService(Class<? extends JobService> object) {
            object = object == null ? null : ((Class)object).getName();
            this.serviceClassName = object;
            return this;
        }

        public Builder setTag(String string2) {
            this.tag = string2;
            return this;
        }

        public Builder setTrigger(JobTrigger jobTrigger) {
            this.trigger = jobTrigger;
            return this;
        }

        @Override
        public boolean shouldReplaceCurrent() {
            return this.replaceCurrent;
        }
    }

}

