/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothGatt
 */
package com.getqardio.android.baseble.commands;

import android.bluetooth.BluetoothGatt;
import com.getqardio.android.baseble.commands.BaseEngineeringCommand;

public class ResetCommand
extends BaseEngineeringCommand {
    public ResetCommand(BluetoothGatt bluetoothGatt) {
        super(bluetoothGatt);
    }

    @Override
    public void execute() {
        this.writeToEngineering(this.createCommand(17));
    }

    @Override
    public String getName() {
        return "ResetCommand";
    }
}

