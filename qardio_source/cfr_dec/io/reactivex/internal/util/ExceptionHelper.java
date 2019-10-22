/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.util;

import io.reactivex.exceptions.CompositeException;
import java.util.concurrent.atomic.AtomicReference;

public final class ExceptionHelper {
    public static final Throwable TERMINATED = new Termination();

    /*
     * Enabled aggressive block sorting
     */
    public static <T> boolean addThrowable(AtomicReference<Throwable> atomicReference, Throwable throwable) {
        Throwable throwable2;
        Throwable throwable3;
        do {
            if ((throwable3 = atomicReference.get()) == TERMINATED) {
                return false;
            }
            if (throwable3 == null) {
                throwable2 = throwable;
                continue;
            }
            throwable2 = new CompositeException(throwable3, throwable);
        } while (!atomicReference.compareAndSet(throwable3, throwable2));
        return true;
    }

    public static <T> Throwable terminate(AtomicReference<Throwable> atomicReference) {
        Throwable throwable;
        Throwable throwable2 = throwable = atomicReference.get();
        if (throwable != TERMINATED) {
            throwable2 = atomicReference.getAndSet(TERMINATED);
        }
        return throwable2;
    }

    public static RuntimeException wrapOrThrow(Throwable throwable) {
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        if (throwable instanceof RuntimeException) {
            return (RuntimeException)throwable;
        }
        return new RuntimeException(throwable);
    }

    static final class Termination
    extends Throwable {
        Termination() {
            super("No further exceptions");
        }

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    }

}

