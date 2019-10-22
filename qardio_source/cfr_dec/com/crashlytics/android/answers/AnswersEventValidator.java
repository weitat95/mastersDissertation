/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.answers;

import io.fabric.sdk.android.Fabric;
import java.util.Locale;
import java.util.Map;

class AnswersEventValidator {
    boolean failFast;
    final int maxNumAttributes;
    final int maxStringLength;

    public AnswersEventValidator(int n, int n2, boolean bl) {
        this.maxNumAttributes = n;
        this.maxStringLength = n2;
        this.failFast = bl;
    }

    private void logOrThrowException(RuntimeException runtimeException) {
        if (this.failFast) {
            throw runtimeException;
        }
        Fabric.getLogger().e("Answers", "Invalid user input detected", runtimeException);
    }

    public boolean isFullMap(Map<String, Object> map, String string2) {
        if (map.size() >= this.maxNumAttributes && !map.containsKey(string2)) {
            this.logOrThrowException(new IllegalArgumentException(String.format(Locale.US, "Limit of %d attributes reached, skipping attribute", this.maxNumAttributes)));
            return true;
        }
        return false;
    }

    public boolean isNull(Object object, String string2) {
        if (object == null) {
            this.logOrThrowException(new NullPointerException(string2 + " must not be null"));
            return true;
        }
        return false;
    }

    public String limitStringLength(String string2) {
        String string3 = string2;
        if (string2.length() > this.maxStringLength) {
            this.logOrThrowException(new IllegalArgumentException(String.format(Locale.US, "String is too long, truncating to %d characters", this.maxStringLength)));
            string3 = string2.substring(0, this.maxStringLength);
        }
        return string3;
    }
}

