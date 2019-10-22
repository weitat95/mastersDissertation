/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothDevice
 */
package com.getqardio.android.baseble;

import android.bluetooth.BluetoothDevice;
import com.getqardio.android.baseble.QardioBaseService;
import java.lang.invoke.LambdaForm;

final class QardioBaseService$BaseScanCallback$$Lambda$1
implements Runnable {
    private final QardioBaseService.BaseScanCallback arg$1;
    private final BluetoothDevice arg$2;

    private QardioBaseService$BaseScanCallback$$Lambda$1(QardioBaseService.BaseScanCallback baseScanCallback, BluetoothDevice bluetoothDevice) {
        this.arg$1 = baseScanCallback;
        this.arg$2 = bluetoothDevice;
    }

    public static Runnable lambdaFactory$(QardioBaseService.BaseScanCallback baseScanCallback, BluetoothDevice bluetoothDevice) {
        return new QardioBaseService$BaseScanCallback$$Lambda$1(baseScanCallback, bluetoothDevice);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$connectToDevice$0(this.arg$2);
    }
}

