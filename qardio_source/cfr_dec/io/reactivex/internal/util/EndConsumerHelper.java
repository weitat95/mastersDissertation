/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.util;

import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.ProtocolViolationException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class EndConsumerHelper {
    public static String composeMessage(String string2) {
        return "It is not allowed to subscribe with a(n) " + string2 + " multiple times. " + "Please create a fresh instance of " + string2 + " and subscribe that to the target source instead.";
    }

    public static void reportDoubleSubscription(Class<?> class_) {
        RxJavaPlugins.onError(new ProtocolViolationException(EndConsumerHelper.composeMessage(class_.getName())));
    }

    public static boolean setOnce(AtomicReference<Disposable> atomicReference, Disposable disposable, Class<?> class_) {
        ObjectHelper.requireNonNull(disposable, "next is null");
        if (!atomicReference.compareAndSet(null, disposable)) {
            disposable.dispose();
            if (atomicReference.get() != DisposableHelper.DISPOSED) {
                EndConsumerHelper.reportDoubleSubscription(class_);
            }
            return false;
        }
        return true;
    }
}

