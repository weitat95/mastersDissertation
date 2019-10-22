/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.bluetooth.BluetoothGatt
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.getqardio.android.baseble.commands;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Build;
import com.getqardio.android.baseble.GattQueue;
import java.util.Locale;

public class StateCommand
implements GattQueue.GATTCommand {
    private final byte[] header = new byte[]{0, 0, 10};
    private final BluetoothGatt mBluetoothGatt;
    private final BluetoothGattCharacteristic mCharacteristic;
    private final int mState;

    public StateCommand(BluetoothGattCharacteristic bluetoothGattCharacteristic, BluetoothGatt bluetoothGatt, int n) {
        this.mBluetoothGatt = bluetoothGatt;
        this.mCharacteristic = bluetoothGattCharacteristic;
        this.mState = n;
    }

    @TargetApi(value=21)
    private void enableHighPriority() {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mBluetoothGatt.requestConnectionPriority(1);
        }
    }

    private void write() {
        byte[] arrby = new byte[this.header.length + 1];
        System.arraycopy(this.header, 0, arrby, 0, this.header.length);
        arrby[this.header.length] = (byte)this.mState;
        this.mCharacteristic.setValue(arrby);
        this.enableHighPriority();
        this.mBluetoothGatt.writeCharacteristic(this.mCharacteristic);
    }

    @Override
    public void execute() {
        this.write();
    }

    @Override
    public String getName() {
        return "State Command";
    }

    public int getState() {
        return this.mState;
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "%s with new state: %d", this.getName(), this.mState);
    }
}

