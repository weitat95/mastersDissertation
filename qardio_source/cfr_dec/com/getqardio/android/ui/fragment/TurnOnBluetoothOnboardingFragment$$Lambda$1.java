/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.TurnOnBluetoothOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class TurnOnBluetoothOnboardingFragment$$Lambda$1
implements View.OnClickListener {
    private final TurnOnBluetoothOnboardingFragment arg$1;

    private TurnOnBluetoothOnboardingFragment$$Lambda$1(TurnOnBluetoothOnboardingFragment turnOnBluetoothOnboardingFragment) {
        this.arg$1 = turnOnBluetoothOnboardingFragment;
    }

    public static View.OnClickListener lambdaFactory$(TurnOnBluetoothOnboardingFragment turnOnBluetoothOnboardingFragment) {
        return new TurnOnBluetoothOnboardingFragment$$Lambda$1(turnOnBluetoothOnboardingFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(view);
    }
}

