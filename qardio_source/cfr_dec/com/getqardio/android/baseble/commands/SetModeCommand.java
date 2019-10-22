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

public class SetModeCommand
extends BaseEngineeringCommand {
    private final int mMode;
    private final int mUserId;

    public SetModeCommand(BluetoothGatt bluetoothGatt, int n, int n2) {
        super(bluetoothGatt);
        this.mUserId = n;
        this.mMode = n2;
    }

    private byte[] createCommandWithUserIdAndMode(byte[] arrby, int n, int n2) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(arrby.length + 4 + 1);
        byteBuffer.put(arrby);
        byteBuffer.put(ByteUtil.littleEndian(n));
        byteBuffer.put((byte)n2);
        return byteBuffer.array();
    }

    @Override
    public void execute() {
        this.writeToEngineering(this.createCommandWithUserIdAndMode(this.createCommand(21), this.mUserId, this.mMode));
    }

    @Override
    public String getName() {
        return "SetModeCommand";
    }
}

