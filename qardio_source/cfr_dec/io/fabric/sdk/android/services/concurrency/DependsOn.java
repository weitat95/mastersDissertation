/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.concurrency;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface DependsOn {
    public Class<?>[] value();
}

