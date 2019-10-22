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
import com.getqardio.android.ui.fragment.WeightLastMeasurementFragment;
import java.lang.invoke.LambdaForm;

final class WeightLastMeasurementFragment$$Lambda$7
implements View.OnClickListener {
    private final BluetoothAdapter arg$1;

    private WeightLastMeasurementFragment$$Lambda$7(BluetoothAdapter bluetoothAdapter) {
        this.arg$1 = bluetoothAdapter;
    }

    public static View.OnClickListener lambdaFactory$(BluetoothAdapter bluetoothAdapter) {
        return new WeightLastMeasurementFragment$$Lambda$7(bluetoothAdapter);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        WeightLastMeasurementFragment.lambda$null$0(this.arg$1, view);
    }
}

