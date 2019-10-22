/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson;

import com.google.gson.internal.$Gson$Preconditions;
import java.lang.reflect.Field;

public final class FieldAttributes {
    private final Field field;

    public FieldAttributes(Field field) {
        $Gson$Preconditions.checkNotNull(field);
        this.field = field;
    }
}

