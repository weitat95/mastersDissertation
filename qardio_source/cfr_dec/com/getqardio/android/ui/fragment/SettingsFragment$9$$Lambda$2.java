/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;
import timber.log.Timber;

final class SettingsFragment$9$$Lambda$2
implements Consumer {
    private static final SettingsFragment$9$$Lambda$2 instance = new SettingsFragment$9$$Lambda$2();

    private SettingsFragment$9$$Lambda$2() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        Timber.e((Throwable)object);
    }
}

