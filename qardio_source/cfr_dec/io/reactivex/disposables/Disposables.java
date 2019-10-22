/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.disposables;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.RunnableDisposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;

public final class Disposables {
    public static Disposable disposed() {
        return EmptyDisposable.INSTANCE;
    }

    public static Disposable empty() {
        return Disposables.fromRunnable(Functions.EMPTY_RUNNABLE);
    }

    public static Disposable fromRunnable(Runnable runnable) {
        ObjectHelper.requireNonNull(runnable, "run is null");
        return new RunnableDisposable(runnable);
    }
}

