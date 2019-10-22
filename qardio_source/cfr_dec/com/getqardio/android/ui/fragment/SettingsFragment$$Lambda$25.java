/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 */
package com.getqardio.android.ui.fragment;

import android.widget.CompoundButton;
import com.getqardio.android.ui.fragment.SettingsFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import java.lang.invoke.LambdaForm;

final class SettingsFragment$$Lambda$25
implements GoogleApiClient.OnConnectionFailedListener {
    private final SettingsFragment arg$1;
    private final CompoundButton arg$2;

    private SettingsFragment$$Lambda$25(SettingsFragment settingsFragment, CompoundButton compoundButton) {
        this.arg$1 = settingsFragment;
        this.arg$2 = compoundButton;
    }

    public static GoogleApiClient.OnConnectionFailedListener lambdaFactory$(SettingsFragment settingsFragment, CompoundButton compoundButton) {
        return new SettingsFragment$$Lambda$25(settingsFragment, compoundButton);
    }

    @LambdaForm.Hidden
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.arg$1.lambda$null$22(this.arg$2, connectionResult);
    }
}

