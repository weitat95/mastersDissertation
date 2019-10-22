/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothGatt
 *  android.bluetooth.BluetoothGattCharacteristic
 */
package com.getqardio.android.baseble.commands;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import com.getqardio.android.baseble.GattQueue;
import com.getqardio.android.baseble.QardioBaseDevice;
import java.security.SecureRandom;
import java.util.Random;

public class UniqueIdCommand
implements GattQueue.GATTCommand {
    private static final char[] VALID_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879".toCharArray();
    private final byte[] header = new byte[]{0, 0, 16};
    private final BluetoothGatt mBluetoothGatt;
    private final BluetoothGattCharacteristic mCharacteristic;
    private final boolean mForceWrite;

    public UniqueIdCommand(BluetoothGattCharacteristic bluetoothGattCharacteristic, BluetoothGatt bluetoothGatt, boolean bl) {
        this.mBluetoothGatt = bluetoothGatt;
        this.mCharacteristic = bluetoothGattCharacteristic;
        this.mForceWrite = bl;
    }

    private static String csRandomAlphaNumericString(int n) {
        SecureRandom secureRandom = new SecureRandom();
        Random random = new Random();
        char[] arrc = new char[n];
        for (int i = 0; i < n; ++i) {
            if (i % 10 == 0) {
                random.setSeed(secureRandom.nextLong());
            }
            arrc[i] = VALID_CHARACTERS[random.nextInt(VALID_CHARACTERS.length)];
        }
        return new String(arrc);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void write(boolean bl) {
        String string2 = UniqueIdCommand.csRandomAlphaNumericString(16);
        int n = bl ? QardioBaseDevice.BaseBoolean.TRUE.getValue() : QardioBaseDevice.BaseBoolean.FALSE.getValue();
        byte[] arrby = new byte[this.header.length + 16 + 1];
        System.arraycopy(this.header, 0, arrby, 0, this.header.length);
        arrby[this.header.length] = (byte)n;
        System.arraycopy(string2.getBytes(), 0, arrby, this.header.length + 1, string2.length());
        this.mCharacteristic.setValue(arrby);
        this.mBluetoothGatt.writeCharacteristic(this.mCharacteristic);
    }

    @Override
    public void execute() {
        this.write(this.mForceWrite);
    }

    @Override
    public String getName() {
        return "Unique Id Command";
    }

    public String toString() {
        return this.getName();
    }
}

