/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothGatt
 */
package com.getqardio.android.baseble.commands;

import android.bluetooth.BluetoothGatt;
import com.getqardio.android.baseble.commands.BaseEngineeringCommand;
import com.getqardio.android.util.ByteUtil;
import java.nio.ByteBuffer;

public class SelectUserCommand
extends BaseEngineeringCommand {
    private final int mUserId;

    public SelectUserCommand(BluetoothGatt bluetoothGatt, int n) {
        super(bluetoothGatt);
        this.mUserId = n;
    }

    private byte[] createCommandWithUserId(byte[] arrby, int n) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(arrby.length + 4);
        byteBuffer.put(arrby);
        byteBuffer.put(ByteUtil.littleEndian(n));
        return byteBuffer.array();
    }

    @Override
    public void execute() {
        this.writeToEngineering(this.createCommandWithUserId(this.createCommand(20), this.mUserId));
    }

    @Override
    public String getName() {
        return "SelectUserCommand";
    }
}

