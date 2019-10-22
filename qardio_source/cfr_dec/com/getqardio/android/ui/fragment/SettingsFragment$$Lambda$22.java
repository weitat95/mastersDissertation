/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.shealth.OnSHealthConnectionErrorListener;
import com.getqardio.android.ui.fragment.SettingsFragment;
import java.lang.invoke.LambdaForm;

final class SettingsFragment$$Lambda$22
implements OnSHealthConnectionErrorListener {
    private final SettingsFragment arg$1;

    private SettingsFragment$$Lambda$22(SettingsFragment settingsFragment) {
        this.arg$1 = settingsFragment;
    }

    public static OnSHealthConnectionErrorListener lambdaFactory$(SettingsFragment settingsFragment) {
        return new SettingsFragment$$Lambda$22(settingsFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void onConnectionError() {
        this.arg$1.lambda$connectToDataStore$20();
    }
}

