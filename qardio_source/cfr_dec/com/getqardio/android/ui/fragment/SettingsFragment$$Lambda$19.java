/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package com.getqardio.android.ui.fragment;

import android.widget.CompoundButton;
import com.getqardio.android.ui.fragment.SettingsFragment;
import java.lang.invoke.LambdaForm;

final class SettingsFragment$$Lambda$19
implements CompoundButton.OnCheckedChangeListener {
    private final SettingsFragment arg$1;

    private SettingsFragment$$Lambda$19(SettingsFragment settingsFragment) {
        this.arg$1 = settingsFragment;
    }

    public static CompoundButton.OnCheckedChangeListener lambdaFactory$(SettingsFragment settingsFragment) {
        return new SettingsFragment$$Lambda$19(settingsFragment);
    }

    @LambdaForm.Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
        this.arg$1.lambda$applyData$17(compoundButton, bl);
    }
}

