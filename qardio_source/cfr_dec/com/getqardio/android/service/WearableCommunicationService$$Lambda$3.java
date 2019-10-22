/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.service;

import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;
import timber.log.Timber;

final class WearableCommunicationService$$Lambda$3
implements Consumer {
    private static final WearableCommunicationService$$Lambda$3 instance = new WearableCommunicationService$$Lambda$3();

    private WearableCommunicationService$$Lambda$3() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        Timber.e((Throwable)object);
    }
}

