/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;
import timber.log.Timber;

final class SelectLocationTagFragment$$Lambda$5
implements Consumer {
    private static final SelectLocationTagFragment$$Lambda$5 instance = new SelectLocationTagFragment$$Lambda$5();

    private SelectLocationTagFragment$$Lambda$5() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        Timber.e((Throwable)object);
    }
}

