/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.firebase.jobdispatcher;

import android.text.TextUtils;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobValidator;
import java.util.List;

public class ValidationEnforcer
implements JobValidator {
    private final JobValidator validator;

    public ValidationEnforcer(JobValidator jobValidator) {
        this.validator = jobValidator;
    }

    private static void ensureNoErrors(List<String> list) {
        if (list != null) {
            throw new ValidationException("JobParameters is invalid", list);
        }
    }

    public final void ensureValid(JobParameters jobParameters) {
        ValidationEnforcer.ensureNoErrors(this.validate(jobParameters));
    }

    @Override
    public List<String> validate(JobParameters jobParameters) {
        return this.validator.validate(jobParameters);
    }

    public static final class ValidationException
    extends RuntimeException {
        private final List<String> errors;

        public ValidationException(String string2, List<String> list) {
            super(string2 + ": " + TextUtils.join((CharSequence)"\n  - ", list));
            this.errors = list;
        }
    }

}

