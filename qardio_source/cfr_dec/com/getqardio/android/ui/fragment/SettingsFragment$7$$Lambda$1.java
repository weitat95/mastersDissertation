/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.SettingsFragment;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class SettingsFragment$7$$Lambda$1
implements Consumer {
    private static final SettingsFragment$7$$Lambda$1 instance = new SettingsFragment$7$$Lambda$1();

    private SettingsFragment$7$$Lambda$1() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        SettingsFragment.7.lambda$onConnected$0((Integer)object);
    }
}

