/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.events;

import java.io.IOException;

public interface EventTransform<T> {
    public byte[] toBytes(T var1) throws IOException;
}

