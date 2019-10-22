/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 */
package com.getqardio.android.ui.fragment;

import android.widget.CompoundButton;
import com.getqardio.android.ui.fragment.SettingsFragment;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class SettingsFragment$9$$Lambda$1
implements Consumer {
    private final SettingsFragment.9 arg$1;
    private final CompoundButton arg$2;

    private SettingsFragment$9$$Lambda$1(SettingsFragment.9 var1_1, CompoundButton compoundButton) {
        this.arg$1 = var1_1;
        this.arg$2 = compoundButton;
    }

    public static Consumer lambdaFactory$(SettingsFragment.9 var0, CompoundButton compoundButton) {
        return new SettingsFragment$9$$Lambda$1(var0, compoundButton);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$onConnected$0(this.arg$2, (Boolean)object);
    }
}

