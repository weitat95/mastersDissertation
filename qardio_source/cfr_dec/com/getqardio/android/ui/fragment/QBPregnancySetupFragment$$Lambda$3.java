/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;
import timber.log.Timber;

final class QBPregnancySetupFragment$$Lambda$3
implements Consumer {
    private static final QBPregnancySetupFragment$$Lambda$3 instance = new QBPregnancySetupFragment$$Lambda$3();

    private QBPregnancySetupFragment$$Lambda$3() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        Timber.e((Throwable)object);
    }
}

