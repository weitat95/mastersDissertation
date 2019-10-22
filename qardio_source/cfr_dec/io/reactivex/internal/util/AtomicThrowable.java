/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.util;

import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class AtomicThrowable
extends AtomicReference<Throwable> {
    public boolean addThrowable(Throwable throwable) {
        return ExceptionHelper.addThrowable(this, throwable);
    }

    public Throwable terminate() {
        return ExceptionHelper.terminate(this);
    }
}

