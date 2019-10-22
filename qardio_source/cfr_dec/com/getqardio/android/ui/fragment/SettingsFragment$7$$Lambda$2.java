/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class SettingsFragment$7$$Lambda$2
implements Consumer {
    private static final SettingsFragment$7$$Lambda$2 instance = new SettingsFragment$7$$Lambda$2();

    private SettingsFragment$7$$Lambda$2() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        ((Throwable)object).printStackTrace();
    }
}

