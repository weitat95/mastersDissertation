/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.content.DialogInterface;
import com.getqardio.android.ui.fragment.SettingsFragment;
import java.lang.invoke.LambdaForm;

final class SettingsFragment$$Lambda$1
implements DialogInterface.OnClickListener {
    private final SettingsFragment arg$1;

    private SettingsFragment$$Lambda$1(SettingsFragment settingsFragment) {
        this.arg$1 = settingsFragment;
    }

    public static DialogInterface.OnClickListener lambdaFactory$(SettingsFragment settingsFragment) {
        return new SettingsFragment$$Lambda$1(settingsFragment);
    }

    @LambdaForm.Hidden
    public void onClick(DialogInterface dialogInterface, int n) {
        this.arg$1.lambda$showBlockedPermissionExplanation$0(dialogInterface, n);
    }
}

