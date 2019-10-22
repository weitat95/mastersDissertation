/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp;

import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;
import timber.log.Timber;

final class MvpApplication$$Lambda$2
implements Consumer {
    private static final MvpApplication$$Lambda$2 instance = new MvpApplication$$Lambda$2();

    private MvpApplication$$Lambda$2() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        Timber.e((Throwable)object);
    }
}

