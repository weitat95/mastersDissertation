/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.googlefit.GoogleFitApi;
import com.getqardio.android.ui.fragment.SettingsFragment;
import io.reactivex.functions.Action;
import java.lang.invoke.LambdaForm;

final class SettingsFragment$9$$Lambda$3
implements Action {
    private final SettingsFragment.9 arg$1;
    private final GoogleFitApi arg$2;

    private SettingsFragment$9$$Lambda$3(SettingsFragment.9 var1_1, GoogleFitApi googleFitApi) {
        this.arg$1 = var1_1;
        this.arg$2 = googleFitApi;
    }

    public static Action lambdaFactory$(SettingsFragment.9 var0, GoogleFitApi googleFitApi) {
        return new SettingsFragment$9$$Lambda$3(var0, googleFitApi);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$onConnected$1(this.arg$2);
    }
}

