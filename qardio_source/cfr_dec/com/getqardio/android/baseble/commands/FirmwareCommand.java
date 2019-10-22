/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothGatt
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.util.Log
 */
package com.getqardio.android.baseble.commands;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;
import com.getqardio.android.baseble.GattQueue;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class FirmwareCommand
implements GattQueue.GATTCommand {
    private final byte[] header = new byte[]{0, 0, 9};
    private final BluetoothGatt mBluetoothGatt;
    private final BluetoothGattCharacteristic mCharacteristic;
    private final String mIpAddress;

    public FirmwareCommand(BluetoothGattCharacteristic bluetoothGattCharacteristic, BluetoothGatt bluetoothGatt, String string2) {
        this.mBluetoothGatt = bluetoothGatt;
        this.mCharacteristic = bluetoothGattCharacteristic;
        this.mIpAddress = string2;
    }

    private void write(String string2) {
        try {
            byte[] arrby = InetAddress.getByName(string2).getAddress();
            byte[] arrby2 = new byte[this.header.length + arrby.length];
            System.arraycopy(this.header, 0, arrby2, 0, this.header.length);
            System.arraycopy(arrby, 0, arrby2, this.header.length, arrby.length);
            this.mCharacteristic.setValue(arrby2);
            this.mBluetoothGatt.writeCharacteristic(this.mCharacteristic);
            return;
        }
        catch (UnknownHostException unknownHostException) {
            Log.e((String)"FirmwareCommand", (String)String.format("Could not write tftp ip address: %s to base: %s", string2, unknownHostException.getMessage()));
            return;
        }
    }

    @Override
    public void execute() {
        this.write(this.mIpAddress);
    }

    @Override
    public String getName() {
        return "Firmware Command";
    }

    public String toString() {
        return this.getName();
    }
}

