/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.SettingsFragment;
import java.lang.invoke.LambdaForm;

final class SettingsFragment$$Lambda$15
implements View.OnClickListener {
    private final SettingsFragment arg$1;

    private SettingsFragment$$Lambda$15(SettingsFragment settingsFragment) {
        this.arg$1 = settingsFragment;
    }

    public static View.OnClickListener lambdaFactory$(SettingsFragment settingsFragment) {
        return new SettingsFragment$$Lambda$15(settingsFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$setupComponents$12(view);
    }
}

