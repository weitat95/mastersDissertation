/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.SettingsFragment;
import com.getqardio.android.ui.widget.CustomSeekBar;
import java.lang.invoke.LambdaForm;

final class SettingsFragment$$Lambda$10
implements CustomSeekBar.OnProgressChangedListener {
    private final SettingsFragment arg$1;

    private SettingsFragment$$Lambda$10(SettingsFragment settingsFragment) {
        this.arg$1 = settingsFragment;
    }

    public static CustomSeekBar.OnProgressChangedListener lambdaFactory$(SettingsFragment settingsFragment) {
        return new SettingsFragment$$Lambda$10(settingsFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void onProgressChanged(CustomSeekBar customSeekBar, int n) {
        this.arg$1.lambda$setupComponents$7(customSeekBar, n);
    }
}

