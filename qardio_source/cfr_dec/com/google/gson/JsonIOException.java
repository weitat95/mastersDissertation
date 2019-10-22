/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson;

import com.google.gson.JsonParseException;

public final class JsonIOException
extends JsonParseException {
    public JsonIOException(String string2) {
        super(string2);
    }

    public JsonIOException(Throwable throwable) {
        super(throwable);
    }
}

