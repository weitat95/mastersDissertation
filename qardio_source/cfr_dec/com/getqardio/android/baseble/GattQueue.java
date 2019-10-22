/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothGatt
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.bluetooth.BluetoothGattDescriptor
 *  android.bluetooth.BluetoothGattService
 */
package com.getqardio.android.baseble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.util.GattUtil;
import java.util.LinkedList;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class GattQueue {
    private static final UUID CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    private final GattCallback mCallback;
    private final Executor mCommandExecutor;
    private final Semaphore mCommandLock;
    private final LinkedList<GATTCommand> mCommandQueue;
    private BluetoothGatt mGatt;

    GattQueue(GattCallback gattCallback) {
        this.mCallback = gattCallback;
        this.mCommandQueue = new LinkedList();
        this.mCommandExecutor = Executors.newSingleThreadExecutor();
        this.mCommandLock = new Semaphore(1, true);
    }

    private void printQueue() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.mCommandQueue.size(); ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.mCommandQueue.get(i).getName());
        }
    }

    private void readCharacteristic(UUID uUID, UUID uUID2) {
        if (this.mGatt == null) {
            this.dequeueCommand();
            return;
        }
        if ((uUID = this.mGatt.getService(uUID)) == null) {
            this.dequeueCommand();
            this.mCallback.onGattCommandFailed("Qardio Base service gone, cannot read");
            return;
        }
        uUID = GattUtil.getCharacteristic((BluetoothGattService)uUID, uUID2);
        if (this.mGatt != null && this.mCommandQueue != null) {
            try {
                this.mGatt.readCharacteristic((BluetoothGattCharacteristic)uUID);
                return;
            }
            catch (NullPointerException nullPointerException) {
                this.dequeueCommand();
                return;
            }
        }
        this.dequeueCommand();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setNotificationForCharacteristic(UUID arrby, UUID uUID, boolean bl) {
        if (this.mGatt != null && (arrby = this.mGatt.getService((UUID)arrby)) != null && (arrby = GattUtil.getCharacteristic((BluetoothGattService)arrby, uUID)) != null) {
            this.mGatt.setCharacteristicNotification((BluetoothGattCharacteristic)arrby, bl);
            uUID = arrby.getDescriptor(CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID);
            if (uUID != null) {
                if (bl) {
                    arrby = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
                } else {
                    arrby[0] = 0;
                    (arrby = new byte[2])[1] = 0;
                }
                uUID.setValue(arrby);
                this.mGatt.writeDescriptor((BluetoothGattDescriptor)uUID);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void addGattCommand(GATTCommand object) {
        LinkedList<GATTCommand> linkedList = this.mCommandQueue;
        synchronized (linkedList) {
            this.mCommandQueue.add((GATTCommand)object);
            this.printQueue();
            object = new CommandRunnable((GATTCommand)object);
            this.mCommandExecutor.execute((Runnable)object);
            return;
        }
    }

    void addReadGattCommand(UUID uUID, UUID uUID2) {
        this.addGattCommand(new ReadGATTCommand(uUID, uUID2));
    }

    void addSetNotificationsCommand(UUID uUID, boolean bl) {
        this.addGattCommand(new SetNotificationGATTCommand(QardioBaseDevice.QARDIO_BASE_SERVICE, uUID, bl));
    }

    void clearCommands() {
        this.mCommandQueue.clear();
        this.mCommandLock.release();
    }

    void dequeueCommand() {
        if (!this.mCommandQueue.isEmpty()) {
            GATTCommand gATTCommand = this.mCommandQueue.pop();
            this.printQueue();
        }
        this.mCommandLock.release();
    }

    public GATTCommand getCurrent() {
        return this.mCommandQueue.peekFirst();
    }

    void setBluetoothGatt(BluetoothGatt bluetoothGatt) {
        this.mGatt = bluetoothGatt;
    }

    private class CommandRunnable
    implements Runnable {
        private final GATTCommand mCommand;

        CommandRunnable(GATTCommand gATTCommand) {
            this.mCommand = gATTCommand;
        }

        @Override
        public void run() {
            GattQueue.this.mCommandLock.acquireUninterruptibly();
            this.mCommand.execute();
        }
    }

    public static interface GATTCommand {
        public void execute();

        public String getName();
    }

    static interface GattCallback {
        public void onGattCommandFailed(String var1);
    }

    private class ReadGATTCommand
    implements GATTCommand {
        final UUID mCharacteristicUUID;
        final UUID mServiceUUID;

        ReadGATTCommand(UUID uUID, UUID uUID2) {
            this.mServiceUUID = uUID;
            this.mCharacteristicUUID = uUID2;
        }

        @Override
        public void execute() {
            GattQueue.this.readCharacteristic(this.mServiceUUID, this.mCharacteristicUUID);
        }

        @Override
        public String getName() {
            return String.format(Locale.ENGLISH, "Read %s Command", GattUtil.nameFromCharacteristic(this.mCharacteristicUUID));
        }

        public String toString() {
            return String.format("%s: char: %s", this.getName(), this.mCharacteristicUUID.toString());
        }
    }

    private class SetNotificationGATTCommand
    implements GATTCommand {
        final UUID mCharacteristicUUID;
        final UUID mServiceUUID;
        final boolean mValue;

        SetNotificationGATTCommand(UUID uUID, UUID uUID2, boolean bl) {
            this.mServiceUUID = uUID;
            this.mCharacteristicUUID = uUID2;
            this.mValue = bl;
        }

        @Override
        public void execute() {
            GattQueue.this.setNotificationForCharacteristic(this.mServiceUUID, this.mCharacteristicUUID, this.mValue);
        }

        @Override
        public String getName() {
            return "Notification Command";
        }

        public String toString() {
            return String.format("%s: char: %s val: %b", this.getName(), this.mCharacteristicUUID.toString(), this.mValue);
        }
    }

}

