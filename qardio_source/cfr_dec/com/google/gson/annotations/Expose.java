/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface Expose {
    public boolean deserialize() default true;

    public boolean serialize() default true;
}

