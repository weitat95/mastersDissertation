/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.SettingsFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import java.lang.invoke.LambdaForm;

final class SettingsFragment$$Lambda$26
implements GoogleApiClient.OnConnectionFailedListener {
    private final SettingsFragment arg$1;

    private SettingsFragment$$Lambda$26(SettingsFragment settingsFragment) {
        this.arg$1 = settingsFragment;
    }

    public static GoogleApiClient.OnConnectionFailedListener lambdaFactory$(SettingsFragment settingsFragment) {
        return new SettingsFragment$$Lambda$26(settingsFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.arg$1.lambda$null$13(connectionResult);
    }
}

