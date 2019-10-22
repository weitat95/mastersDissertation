/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothGatt
 *  android.bluetooth.BluetoothGattCharacteristic
 */
package com.getqardio.android.baseble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

public class GattLongWriter {
    private final byte[] header = new byte[]{0, 0, 4, 0, 0};
    private final BluetoothGatt mBluetoothGatt;
    private final BluetoothGattCharacteristic mCharacteristic;
    private byte[] mData;
    private final int mDs;
    private int offset = 0;

    public GattLongWriter(byte[] arrby, BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int n) {
        this.mBluetoothGatt = bluetoothGatt;
        this.mData = arrby;
        this.mCharacteristic = bluetoothGattCharacteristic;
        this.mDs = n;
    }

    public boolean hasMoreData() {
        return this.mData != null && this.offset < this.mData.length;
    }

    public void reset() {
        this.mData = null;
        this.offset = 0;
    }

    public void write() {
        int n = 15;
        synchronized (this) {
            this.header[3] = (byte)(this.mDs << 4 | this.offset >> 8 & 0xF);
            this.header[4] = (byte)(this.offset & 0xFF);
            if (this.mData.length - this.offset < 15) {
                n = this.mData.length - this.offset;
            }
            byte[] arrby = new byte[this.header.length + n];
            System.arraycopy(this.header, 0, arrby, 0, this.header.length);
            System.arraycopy(this.mData, this.offset, arrby, this.header.length, n);
            byte[] arrby2 = new byte[n];
            System.arraycopy(this.mData, this.offset, arrby2, 0, n);
            this.mCharacteristic.setValue(arrby);
            this.mBluetoothGatt.writeCharacteristic(this.mCharacteristic);
            this.offset += n;
            return;
        }
    }
}

