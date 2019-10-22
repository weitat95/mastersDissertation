/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.core;

import java.util.concurrent.Executor;

public interface ExecutorSupplier {
    public Executor forBackgroundTasks();

    public Executor forDecode();

    public Executor forLightweightBackgroundTasks();

    public Executor forLocalStorageRead();

    public Executor forLocalStorageWrite();
}

