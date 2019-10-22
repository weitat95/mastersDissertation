/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  org.json.JSONObject
 */
package com.firebase.jobdispatcher;

import android.os.Bundle;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.TriggerReason;
import java.util.Arrays;
import org.json.JSONObject;

final class JobInvocation
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
    private final TriggerReason triggerReason;

    private JobInvocation(Builder builder) {
        this.tag = builder.tag;
        this.service = builder.service;
        this.trigger = builder.trigger;
        this.retryStrategy = builder.retryStrategy;
        this.recurring = builder.recurring;
        this.lifetime = builder.lifetime;
        this.constraints = builder.constraints;
        this.extras = builder.extras;
        this.replaceCurrent = builder.replaceCurrent;
        this.triggerReason = builder.triggerReason;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (object == null || !this.getClass().equals(object.getClass())) {
                    return false;
                }
                object = (JobInvocation)object;
                if (!this.tag.equals(((JobInvocation)object).tag) || !this.service.equals(((JobInvocation)object).service)) break block5;
            }
            return true;
        }
        return false;
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

    public int hashCode() {
        return this.tag.hashCode() * 31 + this.service.hashCode();
    }

    @Override
    public boolean isRecurring() {
        return this.recurring;
    }

    @Override
    public boolean shouldReplaceCurrent() {
        return this.replaceCurrent;
    }

    public String toString() {
        return "JobInvocation{tag='" + JSONObject.quote((String)this.tag) + '\'' + ", service='" + this.service + '\'' + ", trigger=" + this.trigger + ", recurring=" + this.recurring + ", lifetime=" + this.lifetime + ", constraints=" + Arrays.toString(this.constraints) + ", extras=" + (Object)this.extras + ", retryStrategy=" + this.retryStrategy + ", replaceCurrent=" + this.replaceCurrent + ", triggerReason=" + this.triggerReason + '}';
    }

    static final class Builder {
        private int[] constraints;
        private final Bundle extras = new Bundle();
        private int lifetime;
        private boolean recurring;
        private boolean replaceCurrent;
        private RetryStrategy retryStrategy;
        private String service;
        private String tag;
        private JobTrigger trigger;
        private TriggerReason triggerReason;

        Builder() {
        }

        public Builder addExtras(Bundle bundle) {
            if (bundle != null) {
                this.extras.putAll(bundle);
            }
            return this;
        }

        JobInvocation build() {
            if (this.tag == null || this.service == null || this.trigger == null) {
                throw new IllegalArgumentException("Required fields were not populated.");
            }
            return new JobInvocation(this);
        }

        public Builder setConstraints(int[] arrn) {
            this.constraints = arrn;
            return this;
        }

        public Builder setLifetime(int n) {
            this.lifetime = n;
            return this;
        }

        public Builder setRecurring(boolean bl) {
            this.recurring = bl;
            return this;
        }

        public Builder setReplaceCurrent(boolean bl) {
            this.replaceCurrent = bl;
            return this;
        }

        public Builder setRetryStrategy(RetryStrategy retryStrategy) {
            this.retryStrategy = retryStrategy;
            return this;
        }

        public Builder setService(String string2) {
            this.service = string2;
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

        public Builder setTriggerReason(TriggerReason triggerReason) {
            this.triggerReason = triggerReason;
            return this;
        }
    }

}

