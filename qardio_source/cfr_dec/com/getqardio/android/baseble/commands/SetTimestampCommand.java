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

public class SetTimestampCommand
extends BaseEngineeringCommand {
    private final long mTimestamp;

    public SetTimestampCommand(BluetoothGatt bluetoothGatt, long l) {
        super(bluetoothGatt);
        this.mTimestamp = l;
    }

    private byte[] createCommandWithTimestamp(byte[] arrby, int n) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(arrby.length + 4);
        byteBuffer.put(arrby);
        byteBuffer.put(ByteUtil.littleEndian(n));
        return byteBuffer.array();
    }

    @Override
    public void execute() {
        this.writeToEngineering(this.createCommandWithTimestamp(this.createCommand(12), (int)(this.mTimestamp / 1000L)));
    }

    @Override
    public String getName() {
        return "SetTimestampCommand";
    }
}

