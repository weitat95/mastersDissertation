/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.exceptions;

import io.reactivex.internal.util.ExceptionHelper;

public final class Exceptions {
    public static RuntimeException propagate(Throwable throwable) {
        throw ExceptionHelper.wrapOrThrow(throwable);
    }

    public static void throwIfFatal(Throwable throwable) {
        if (throwable instanceof VirtualMachineError) {
            throw (VirtualMachineError)throwable;
        }
        if (throwable instanceof ThreadDeath) {
            throw (ThreadDeath)throwable;
        }
        if (throwable instanceof LinkageError) {
            throw (LinkageError)throwable;
        }
    }
}

