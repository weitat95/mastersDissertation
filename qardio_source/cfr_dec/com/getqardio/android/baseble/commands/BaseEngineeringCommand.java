/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothGatt
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.bluetooth.BluetoothGattService
 */
package com.getqardio.android.baseble.commands;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import com.getqardio.android.baseble.GattQueue;
import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.exceptions.CommandException;
import java.nio.ByteBuffer;
import java.util.UUID;

abstract class BaseEngineeringCommand
implements GattQueue.GATTCommand {
    private BluetoothGatt mBluetoothGatt;

    public BaseEngineeringCommand(BluetoothGatt bluetoothGatt) {
        this.mBluetoothGatt = bluetoothGatt;
    }

    protected byte[] createCommand(int n) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(3);
        byteBuffer.put(2, (byte)n);
        return byteBuffer.array();
    }

    protected void writeToEngineering(byte[] arrby) {
        if (this.mBluetoothGatt == null) {
            throw new CommandException("Bluetooth Gatt connection not available");
        }
        BluetoothGattService bluetoothGattService = this.mBluetoothGatt.getService(QardioBaseDevice.QARDIO_BASE_SERVICE);
        if (bluetoothGattService == null) {
            throw new CommandException("Qardio Base service gone, cannot write");
        }
        bluetoothGattService = bluetoothGattService.getCharacteristic(QardioBaseDevice.QARDIO_BASE_ENGINEERING_CHAR);
        bluetoothGattService.setValue(arrby);
        this.mBluetoothGatt.writeCharacteristic((BluetoothGattCharacteristic)bluetoothGattService);
    }
}

