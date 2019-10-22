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
import com.getqardio.android.baseble.GattLongWriter;
import com.getqardio.android.baseble.GattQueue;
import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.exceptions.CommandException;
import com.getqardio.android.util.HexUtil;
import java.nio.charset.Charset;
import java.util.UUID;

public class EngineeringCommand
implements GattQueue.GATTCommand {
    private final BluetoothGatt mBluetoothGatt;
    private final byte[] mData;
    private final UUID mDestinationUUID;
    private GattLongWriter mGattLongWriter;

    public EngineeringCommand(BluetoothGatt bluetoothGatt, String string2, UUID uUID) {
        this.mBluetoothGatt = bluetoothGatt;
        this.mData = string2.getBytes(Charset.forName("UTF-8"));
        this.mDestinationUUID = uUID;
        bluetoothGatt = this.mBluetoothGatt.getService(QardioBaseDevice.QARDIO_BASE_SERVICE);
        if (bluetoothGatt == null) {
            this.mGattLongWriter.reset();
            throw new CommandException("Qardio Base service gone, cannot write");
        }
        bluetoothGatt = bluetoothGatt.getCharacteristic(QardioBaseDevice.QARDIO_BASE_ENGINEERING_CHAR);
        this.mGattLongWriter = new GattLongWriter(this.mData, this.mBluetoothGatt, (BluetoothGattCharacteristic)bluetoothGatt, this.getNumForCharacteristic(this.mDestinationUUID));
    }

    public EngineeringCommand(BluetoothGatt bluetoothGatt, UUID uUID) {
        this.mBluetoothGatt = bluetoothGatt;
        this.mDestinationUUID = uUID;
        this.mData = null;
    }

    private void executeReset(BluetoothGattCharacteristic bluetoothGattCharacteristic) throws CommandException {
        byte[] arrby;
        if (this.mBluetoothGatt == null) {
            throw new CommandException("Bluetooth GATT connection lost, cannot execute reset");
        }
        byte[] arrby2 = arrby = new byte[5];
        arrby[0] = 0;
        arrby2[1] = 0;
        arrby2[2] = 4;
        arrby2[3] = 0;
        arrby2[4] = 0;
        arrby[3] = (byte)(this.getNumForCharacteristic(this.mDestinationUUID) << 4);
        bluetoothGattCharacteristic.setValue(arrby);
        this.mBluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
    }

    private int getNumForCharacteristic(UUID uUID) {
        if (uUID.equals(QardioBaseDevice.QARDIO_BASE_WIFI_CONFIG_CHAR)) {
            return 1;
        }
        if (uUID.equals(QardioBaseDevice.QARDIO_BASE_USER_CONFIG_CHAR)) {
            return 2;
        }
        if (uUID.equals(QardioBaseDevice.QARDIO_BASE_CALIBRATION_CHAR)) {
            return 3;
        }
        return 0;
    }

    public boolean continueWrite() {
        if (this.mGattLongWriter != null && this.mGattLongWriter.hasMoreData()) {
            this.mGattLongWriter.write();
            return true;
        }
        if (this.mGattLongWriter != null) {
            this.mGattLongWriter.reset();
        }
        return false;
    }

    @Override
    public void execute() throws CommandException {
        BluetoothGattService bluetoothGattService = this.mBluetoothGatt.getService(QardioBaseDevice.QARDIO_BASE_SERVICE);
        if (bluetoothGattService == null) {
            throw new CommandException("Qardio Base service gone, cannot write");
        }
        bluetoothGattService = bluetoothGattService.getCharacteristic(QardioBaseDevice.QARDIO_BASE_ENGINEERING_CHAR);
        if (this.mData == null) {
            this.executeReset((BluetoothGattCharacteristic)bluetoothGattService);
            return;
        }
        this.mGattLongWriter.write();
    }

    public UUID getDestinationUUID() {
        return this.mDestinationUUID;
    }

    @Override
    public String getName() {
        return "Engineering Command";
    }

    public String toString() {
        if (this.mData != null) {
            String string2 = HexUtil.bytesToHex(this.mData);
            return String.format("%s: val: %s ", this.getName(), string2);
        }
        return "Engineering reset Command";
    }
}

