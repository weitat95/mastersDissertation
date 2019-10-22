/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothGatt
 *  android.bluetooth.BluetoothGattCallback
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.bluetooth.BluetoothGattDescriptor
 *  android.bluetooth.BluetoothGattService
 *  android.content.Context
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.qardio.ble.bpcollector.mobiledevice;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.getqardio.blelibrary.MeasurementParser;
import com.qardio.ble.bpcollector.DeviceManager;
import com.qardio.ble.bpcollector.mobiledevice.BLEStatus;
import com.qardio.ble.bpcollector.mobiledevice.MobileDevice;
import com.qardio.ble.bpcollector.mobiledevice.MobileDeviceFactory;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class GenericBLEDevice
extends MobileDevice {
    private static final byte[] START_MEASUREMENT_COMMAND = new byte[]{-15, 1};
    private static final byte[] STOP_MEASUREMENT_COMMAND = new byte[]{-15, 2};
    private int connectionState = 0;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCallback mGattCallback;
    private final Object readWriteCharacteristicLock = new Object();
    private String serialNumberCache;
    private volatile int waitForPairingCounter;
    private Timer waitForPairingTimer;

    public GenericBLEDevice(MobileDevice.MobileDeviceCallback mobileDeviceCallback) {
        super(mobileDeviceCallback);
        this.mGattCallback = new QardioARMBTGattCallback();
    }

    static /* synthetic */ int access$704(GenericBLEDevice genericBLEDevice) {
        int n;
        genericBLEDevice.waitForPairingCounter = n = genericBLEDevice.waitForPairingCounter + 1;
        return n;
    }

    private void clearSerialNumberCache() {
        this.serialNumberCache = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean enableNotification(boolean bl, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (this.mBluetoothGatt == null) {
            return false;
        }
        if (!this.mBluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, bl)) return false;
        if ((bluetoothGattCharacteristic = bluetoothGattCharacteristic.getDescriptor(MobileDevice.CCC)) == null) return false;
        if (bl) {
            Log.i((String)"BLELib_BLEDevice", (String)"do enable notification");
            bluetoothGattCharacteristic.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            return this.mBluetoothGatt.writeDescriptor((BluetoothGattDescriptor)bluetoothGattCharacteristic);
        }
        Log.i((String)"BLELib_BLEDevice", (String)"do disable notification");
        bluetoothGattCharacteristic.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        return this.mBluetoothGatt.writeDescriptor((BluetoothGattDescriptor)bluetoothGattCharacteristic);
    }

    private Context getContext() {
        return this.callback.getContext();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void notifyCommandComplete() {
        Object object = this.readWriteCharacteristicLock;
        synchronized (object) {
            this.readWriteCharacteristicLock.notify();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void readCharacteristic(UUID uUID, UUID uUID2) {
        if (this.mBluetoothGatt == null || (uUID = this.mBluetoothGatt.getService(uUID)) == null || (uUID = uUID.getCharacteristic(uUID2)) == null) {
            return;
        }
        if (!this.mBluetoothGatt.readCharacteristic((BluetoothGattCharacteristic)uUID)) {
            Log.i((String)"BLELib_BLEDevice", (String)("cannot read characteristic " + uUID2.toString()));
        }
        this.waitCommandCompletion();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void waitCommandCompletion() {
        Object object = this.readWriteCharacteristicLock;
        synchronized (object) {
            try {
                this.readWriteCharacteristicLock.wait(2000L);
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeCharacteristic(UUID uUID, UUID uUID2, byte[] arrby, UUID uUID3) {
        if (this.mBluetoothGatt == null) {
            Log.e((String)"BLELib_BLEDevice", (String)"BluetoothGatt is null");
            return;
        } else {
            if ((uUID = this.mBluetoothGatt.getService(uUID)) == null || (uUID2 = uUID.getCharacteristic(uUID2)) == null) return;
            {
                uUID2.setValue(arrby);
                this.mBluetoothGatt.writeCharacteristic((BluetoothGattCharacteristic)uUID2);
                if (uUID3 != null) {
                    this.enableNotification(true, uUID.getCharacteristic(uUID3));
                }
                this.waitCommandCompletion();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void cancelConnection() {
        if (this.mBluetoothGatt != null) {
            try {
                this.mBluetoothGatt.disconnect();
                this.mBluetoothGatt.close();
            }
            catch (Exception exception) {
                Log.e((String)"BLELib_BLEDevice", (String)"Error in disconnect() or close()", (Throwable)exception);
            }
        } else {
            Log.w((String)"BLELib_BLEDevice", (String)"mBluetoothGatt not initialized");
        }
        BLEStatus.getInstance(this.getContext()).setBatteryLow(false);
        this.clearSerialNumberCache();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void connect(final BluetoothDevice bluetoothDevice) {
        Object object = this.readWriteCharacteristicLock;
        synchronized (object) {
            this.readWriteCharacteristicLock.notifyAll();
        }
        BLEStatus.getInstance(this.getContext()).setBatteryLow(false);
        this.clearSerialNumberCache();
        this.waitForPairingCounter = 0;
        new Thread(new Runnable(){

            @Override
            public void run() {
                GenericBLEDevice.this.mBluetoothGatt = bluetoothDevice.connectGatt(GenericBLEDevice.this.getContext(), false, GenericBLEDevice.this.mGattCallback);
            }
        }).start();
    }

    @Override
    void discoverServices() {
        Log.i((String)"BLELib_BLEDevice", (String)"discoverServices");
        if (this.mBluetoothGatt != null) {
            this.mBluetoothGatt.discoverServices();
        }
    }

    @Override
    void getBatteryLevel() {
        Log.i((String)"BLELib_BLEDevice", (String)"try to get battery level");
        this.readCharacteristic(MobileDevice.DEVICE_BATTERY_INFORMATION, MobileDevice.BATTERY_STATUS_CHARACTERISTICS);
    }

    @Override
    void getSerialNumber() {
        Log.i((String)"BLELib_BLEDevice", (String)("try to read serial number[serialNumberCache=" + this.serialNumberCache + "]"));
        if (TextUtils.isEmpty((CharSequence)this.serialNumberCache)) {
            this.readCharacteristic(MobileDevice.DEVICE_INFORMATION, MobileDevice.SERIAL_NUMBER_STRING);
            return;
        }
        this.onSerialNumber(this.getContext(), this.serialNumberCache);
    }

    @Override
    public boolean isConnected() {
        return this.connectionState != 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void removeBond() {
        if (this.mBluetoothGatt != null) {
            try {
                this.mBluetoothGatt.disconnect();
                this.mBluetoothGatt.close();
            }
            catch (Exception exception) {
                Log.e((String)"BLELib_BLEDevice", (String)"Error in disconnect() or close()", (Throwable)exception);
            }
        }
        BLEStatus.getInstance(this.getContext()).setBatteryLow(false);
        this.clearSerialNumberCache();
    }

    @Override
    void sendCommand(byte[] arrby) {
        this.writeCharacteristic(MobileDevice.BP_SERVICE, MobileDevice.BPD_PRESSURE_CHARACTERISTICS, arrby, MobileDevice.BPD_PRESSURE_CHARACTERISTICS);
    }

    @Override
    void sendControl(byte[] arrby) {
        this.writeCharacteristic(MobileDevice.BP_SERVICE, MobileDevice.BPD_CONTROL_CHARACTERISTICS, arrby, null);
    }

    @Override
    void setup() {
        Log.i((String)"BLELib_BLEDevice", (String)"do setup");
        this.readCharacteristic(MobileDevice.DEVICE_INFORMATION, MobileDevice.PAIRING_UUID);
    }

    @Override
    void startMeasurement() {
        this.writeCharacteristic(MobileDevice.BP_SERVICE, MobileDevice.BPD_PRESSURE_CHARACTERISTICS, START_MEASUREMENT_COMMAND, MobileDevice.BPD_PRESSURE_VALUES_CHARACTERISTICS);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void startPairing() {
        if (this.mBluetoothGatt != null) {
            this.enableNotification(true, this.mBluetoothGatt.getService(MobileDevice.BP_SERVICE).getCharacteristic(MobileDevice.BPD_PRESSURE_VALUES_CHARACTERISTICS));
            try {
                Thread.sleep(2000L);
            }
            catch (InterruptedException interruptedException) {}
            MobileDeviceFactory.getBatteryStatus(this.getContext());
            this.onReady(this.getContext());
        }
    }

    @Override
    void stopMeasurement() {
        this.writeCharacteristic(MobileDevice.BP_SERVICE, MobileDevice.BPD_PRESSURE_CHARACTERISTICS, STOP_MEASUREMENT_COMMAND, null);
    }

    private class QardioARMBTGattCallback
    extends BluetoothGattCallback {
        private QardioARMBTGattCallback() {
        }

        private String getBluetoothProfileStatusName(int n) {
            switch (n) {
                default: {
                    return "UNKNOWN[" + n + "]";
                }
                case 0: {
                    return "GATT_SUCCESS";
                }
                case 2: {
                    return "GATT_READ_NOT_PERMITTED";
                }
                case 3: {
                    return "GATT_WRITE_NOT_PERMITTED";
                }
                case 5: {
                    return "GATT_INSUFFICIENT_AUTHENTICATION";
                }
                case 6: {
                    return "GATT_REQUEST_NOT_SUPPORTED";
                }
                case 15: {
                    return "GATT_INSUFFICIENT_ENCRYPTION";
                }
                case 7: {
                    return "GATT_INVALID_OFFSET";
                }
                case 13: {
                    return "GATT_INVALID_ATTRIBUTE_LENGTH";
                }
                case 143: {
                    return "GATT_CONNECTION_CONGESTED";
                }
                case 257: 
            }
            return "GATT_FAILURE";
        }

        private String getPairingStatusName(int n) {
            switch (n) {
                default: {
                    return "UNKNOWN[" + n + "]";
                }
                case 0: {
                    return "PAIRING_STATUS_NOT_PAIRED";
                }
                case -1: {
                    return "PAIRING_STATUS_NEED_PAIRING";
                }
                case 2: {
                    return "PAIRING_STATUS_NEED_RESET";
                }
                case 3: 
            }
            return "PAIRING_STATUS_PAIRED";
        }

        private void waitForPairing() {
            if (GenericBLEDevice.this.waitForPairingTimer == null) {
                GenericBLEDevice.this.waitForPairingTimer = new Timer();
            }
            GenericBLEDevice.this.waitForPairingTimer.schedule(new TimerTask(){

                /*
                 * Enabled aggressive block sorting
                 */
                @Override
                public void run() {
                    StringBuilder stringBuilder = new StringBuilder().append("waitForPairing will be runned:  ");
                    boolean bl = BLEStatus.getInstance(GenericBLEDevice.this.getContext()).getBleStatus() == 5;
                    Log.d((String)"BLELib_BLEDevice", (String)stringBuilder.append(bl).append(", [").append(BLEStatus.getInstance(GenericBLEDevice.this.getContext()).getBleStatus()).append("]").append(GenericBLEDevice.this.mBluetoothGatt.getDevice().getBondState()).toString());
                    if (BLEStatus.getInstance(GenericBLEDevice.this.getContext()).getBleStatus() == 5) {
                        GenericBLEDevice.access$704(GenericBLEDevice.this);
                        if (GenericBLEDevice.this.waitForPairingCounter > 10) {
                            GenericBLEDevice.this.cancelConnection();
                            return;
                        }
                        GenericBLEDevice.this.readCharacteristic(MobileDevice.DEVICE_INFORMATION, MobileDevice.PAIRING_UUID);
                        QardioARMBTGattCallback.this.waitForPairing();
                    }
                }
            }, 1000L);
        }

        public void onCharacteristicChanged(BluetoothGatt object, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            Log.i((String)"BLELib_BLEDevice", (String)("onCharacteristicChanged " + bluetoothGattCharacteristic.getUuid()));
            if (bluetoothGattCharacteristic.getUuid().equals(MobileDevice.BPD_PRESSURE_VALUES_CHARACTERISTICS)) {
                Log.i((String)"BLELib_BLEDevice", (String)"onCharacteristicChanged[BPD_PRESSURE_VALUES_CHARACTERISTICS]");
                object = MeasurementParser.parseMeasurementData(bluetoothGattCharacteristic);
                GenericBLEDevice.this.onMeasurement(GenericBLEDevice.this.getContext(), object.dia, object.sys, object.map, object.lastreading, object.pulse, object.iHB, object.measurementStatus);
                return;
            }
            GenericBLEDevice.this.onSendRawData(GenericBLEDevice.this.getContext(), bluetoothGattCharacteristic.getValue());
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onCharacteristicRead(BluetoothGatt object, BluetoothGattCharacteristic bluetoothGattCharacteristic, int n) {
            Log.i((String)"BLELib_BLEDevice", (String)("onCharacteristicRead: " + bluetoothGattCharacteristic.getUuid() + " status=" + this.getBluetoothProfileStatusName(n) + " " + Thread.currentThread().getName()));
            GenericBLEDevice.this.notifyCommandComplete();
            object = bluetoothGattCharacteristic.getUuid();
            if (((UUID)object).equals(MobileDevice.PAIRING_UUID)) {
                GenericBLEDevice.this.waitForPairingCounter = 0;
                if (bluetoothGattCharacteristic.getValue() != null) {
                    n = bluetoothGattCharacteristic.getValue()[0];
                    Log.i((String)"BLELib_BLEDevice", (String)("PAIRING_UUID value: " + n));
                    this.onPairingStatus(n);
                    return;
                }
                Log.i((String)"BLELib_BLEDevice", (String)"PAIRING_UUID value is null");
                GenericBLEDevice.this.removeBond();
                this.onPairingStatus(-1);
                return;
            }
            if (((UUID)object).equals(MobileDevice.BATTERY_STATUS_CHARACTERISTICS)) {
                n = bluetoothGattCharacteristic.getIntValue(17, 0);
                Log.i((String)"BLELib_BLEDevice", (String)("BATTERY_STATUS_CHARACTERISTICS " + n));
                GenericBLEDevice.this.onBatteryStatus(GenericBLEDevice.this.getContext(), n);
                return;
            }
            if (!((UUID)object).equals(MobileDevice.SERIAL_NUMBER_STRING)) return;
            {
                if (bluetoothGattCharacteristic.getValue() != null) {
                    object = bluetoothGattCharacteristic.getStringValue(0);
                    Log.i((String)"BLELib_BLEDevice", (String)("SERIAL_NUMBER_STRING sn=" + (String)object));
                    GenericBLEDevice.this.serialNumberCache = (String)object;
                    GenericBLEDevice.this.onSerialNumber(GenericBLEDevice.this.getContext(), (String)object);
                    return;
                }
            }
            Log.i((String)"BLELib_BLEDevice", (String)"SERIAL_NUMBER_STRING = null");
        }

        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int n) {
            GenericBLEDevice.this.notifyCommandComplete();
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onConnectionStateChange(BluetoothGatt object, int n, int n2) {
            GenericBLEDevice.this.connectionState = n2;
            switch (n2) {
                case 2: {
                    object = BLEStatus.getInstance(GenericBLEDevice.this.getContext());
                    if (((BLEStatus)object).getBleStatus() != 66) {
                        ((BLEStatus)object).setBleStatus(GenericBLEDevice.this.getContext(), 44, true);
                        GenericBLEDevice.this.discoverServices();
                        return;
                    }
                }
                default: {
                    return;
                }
                case 0: 
            }
            BLEStatus.getInstance(GenericBLEDevice.this.getContext()).setBatteryLow(false);
            GenericBLEDevice.this.clearSerialNumberCache();
            BLEStatus.getInstance(GenericBLEDevice.this.getContext()).setBleStatus(GenericBLEDevice.this.getContext(), 4, true);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onPairingStatus(int n) {
            Log.i((String)"BLELib_BLEDevice", (String)("---- PAIRING STATUS == : " + this.getPairingStatusName(n) + ", " + GenericBLEDevice.this.mBluetoothGatt.getDevice().getBondState()));
            if (n == 0) {
                BLEStatus.getInstance(GenericBLEDevice.this.getContext()).setBleStatus(GenericBLEDevice.this.getContext(), 5, true);
                BluetoothGattCharacteristic bluetoothGattCharacteristic = GenericBLEDevice.this.mBluetoothGatt.getService(MobileDevice.BP_SERVICE).getCharacteristic(MobileDevice.BPD_PRESSURE_VALUES_CHARACTERISTICS);
                GenericBLEDevice.this.enableNotification(true, bluetoothGattCharacteristic);
                this.waitForPairing();
                return;
            } else if (n == -1) {
                DeviceManager.getInstance(GenericBLEDevice.this.getContext()).deleteAll();
                BLEStatus.getInstance(GenericBLEDevice.this.getContext()).setBleStatus(GenericBLEDevice.this.getContext(), 2, true);
                GenericBLEDevice.this.cancelConnection();
                if (GenericBLEDevice.this.callback == null) return;
                {
                    GenericBLEDevice.this.callback.startScan();
                    return;
                }
            } else {
                if (n != 2) {
                    GenericBLEDevice.this.startPairing();
                    return;
                }
                BLEStatus.getInstance(GenericBLEDevice.this.getContext()).setBleStatus(GenericBLEDevice.this.getContext(), 3, true);
                GenericBLEDevice.this.cancelConnection();
                if (GenericBLEDevice.this.callback == null) return;
                {
                    GenericBLEDevice.this.callback.startScan();
                    return;
                }
            }
        }

        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int n) {
            if ((bluetoothGatt = bluetoothGatt.getDevice()) != null) {
                GenericBLEDevice.this.setup();
                DeviceManager deviceManager = DeviceManager.getInstance(GenericBLEDevice.this.getContext());
                deviceManager.deleteAll();
                deviceManager.create(bluetoothGatt.getAddress());
            }
        }

    }

}

