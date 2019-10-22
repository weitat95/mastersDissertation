/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothAdapter
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.bluetooth.BluetoothAdapter;
import android.view.View;
import com.getqardio.android.ui.fragment.StartBPFragment;
import java.lang.invoke.LambdaForm;

final class StartBPFragment$$Lambda$10
implements View.OnClickListener {
    private final BluetoothAdapter arg$1;

    private StartBPFragment$$Lambda$10(BluetoothAdapter bluetoothAdapter) {
        this.arg$1 = bluetoothAdapter;
    }

    public static View.OnClickListener lambdaFactory$(BluetoothAdapter bluetoothAdapter) {
        return new StartBPFragment$$Lambda$10(bluetoothAdapter);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        StartBPFragment.lambda$null$6(this.arg$1, view);
    }
}

