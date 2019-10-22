/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothAdapter$LeScanCallback
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothManager
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 */
package com.qardio.ble.bpcollector.mobiledevice;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import com.qardio.ble.bpcollector.DeviceManager;
import com.qardio.ble.bpcollector.mobiledevice.BLEStatus;
import com.qardio.ble.bpcollector.mobiledevice.MobileDevice;
import com.qardio.ble.bpcollector.mobiledevice.MobileDeviceFactory;

public class BPService
extends Service
implements MobileDevice.MobileDeviceCallback {
    private static final byte[] CONTINUOUS_MEASUREMENT_COMMAND;
    private static final byte[] ENGINEERING_MODE_COMMAND;
    private BluetoothAdapter.LeScanCallback bleScanCallback = new BluetoothAdapter.LeScanCallback(){

        public void onLeScan(BluetoothDevice bluetoothDevice, int n, byte[] arrby) {
            BPService.this.onDevice(bluetoothDevice, n);
        }
    };
    private BluetoothAdapter btAdapter;
    private MobileDevice mobileDevice = null;
    private NeedPairingTimeout needPairingTimeout;
    private volatile ServiceHandler serviceHandler;
    private volatile Looper serviceLooper;

    static {
        ENGINEERING_MODE_COMMAND = new byte[]{69, 44, 45, 49};
        CONTINUOUS_MEASUREMENT_COMMAND = new byte[]{77, 44, 45, 49};
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onDevice(BluetoothDevice bluetoothDevice, int n) {
        boolean bl;
        boolean bl2 = bl = false;
        if ("QardioARM".equals(bluetoothDevice.getName())) {
            BLEStatus.getInstance((Context)this).setAdvertising(true);
            this.serviceHandler.removeCallbacks((Runnable)this.needPairingTimeout);
            this.serviceHandler.postDelayed((Runnable)this.needPairingTimeout, 10000L);
            DeviceManager deviceManager = DeviceManager.getInstance(this.getApplicationContext());
            boolean bl3 = false;
            if (bluetoothDevice.getAddress() != null) {
                bl3 = deviceManager.isExist(bluetoothDevice.getAddress());
            }
            if (bl3) {
                bl2 = true;
            } else if (n >= -50) {
                bl2 = true;
            } else {
                BLEStatus.getInstance((Context)this).setBleStatus((Context)this, 2, true);
                bl2 = bl;
            }
        }
        if (bl2) {
            this.stopScan();
            this.mobileDevice.connect(bluetoothDevice);
        }
    }

    protected void engineeringMode() {
        this.mobileDevice.sendControl(ENGINEERING_MODE_COMMAND);
    }

    @Override
    public Context getContext() {
        return this;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onCreate() {
        super.onCreate();
        HandlerThread handlerThread = new HandlerThread("Service[" + this.getClass().getName() + "]");
        handlerThread.start();
        this.serviceLooper = handlerThread.getLooper();
        this.serviceHandler = new ServiceHandler(this.serviceLooper);
        this.btAdapter = ((BluetoothManager)this.getSystemService("bluetooth")).getAdapter();
        if (this.btAdapter == null) {
            this.stopSelf();
            return;
        }
        if (this.btAdapter.getState() == 10) {
            BLEStatus.getInstance((Context)this).setBleStatus((Context)this, 0, false);
        } else {
            BLEStatus.getInstance((Context)this).setBleStatus((Context)this, 4, false);
        }
        this.mobileDevice = MobileDeviceFactory.createMobileDevice(this);
        this.needPairingTimeout = new NeedPairingTimeout();
    }

    public void onDestroy() {
        super.onDestroy();
        this.stopScan();
        this.serviceHandler.removeCallbacks((Runnable)this.needPairingTimeout);
        this.serviceLooper.quit();
        if (this.mobileDevice != null) {
            this.mobileDevice.cancelConnection();
        }
        BLEStatus.getInstance((Context)this).setBleStatus((Context)this, 4, false);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void onHandleIntent(Intent arrstring) {
        if (arrstring == null) return;
        if (this.mobileDevice == null) {
            return;
        }
        if (!"com.qardio.bleservice.Notifications.DEVICE_COMMANDS".equals(arrstring.getAction())) return;
        switch (arrstring.getIntExtra("DEVICE_COMMAND", -1)) {
            default: {
                return;
            }
            case 0: {
                this.mobileDevice.getSerialNumber();
                return;
            }
            case 2: {
                this.mobileDevice.startMeasurement();
                return;
            }
            case 3: {
                this.mobileDevice.stopMeasurement();
                return;
            }
            case 1: {
                if (this.mobileDevice.isConnected()) return;
                this.startScan();
                return;
            }
            case 5: {
                DeviceManager.getInstance((Context)this).deleteAll();
                this.mobileDevice.cancelConnection();
                return;
            }
            case 4: {
                this.mobileDevice.getBatteryLevel();
                return;
            }
            case 6: {
                this.mobileDevice.cancelConnection();
                return;
            }
            case 7: {
                this.engineeringMode();
                return;
            }
            case 8: {
                this.mobileDevice.sendCommand(CONTINUOUS_MEASUREMENT_COMMAND);
                return;
            }
            case 9: 
        }
        this.engineeringMode();
        arrstring = arrstring.getStringExtra("COMMAND_VALUE").split(" ");
        byte[] arrby = new byte[arrstring.length];
        int n = arrstring.length;
        int n2 = 0;
        int n3 = 0;
        do {
            if (n2 >= n) {
                this.mobileDevice.sendCommand(arrby);
                return;
            }
            arrby[n3] = (byte)Integer.parseInt(arrstring[n2], 16);
            ++n2;
            ++n3;
        } while (true);
    }

    public int onStartCommand(Intent intent, int n, int n2) {
        Message message = this.serviceHandler.obtainMessage();
        message.what = 0;
        message.arg1 = n2;
        message.obj = intent;
        this.serviceHandler.sendMessage(message);
        return 1;
    }

    @Override
    public void startScan() {
        if (this.btAdapter != null) {
            BLEStatus.getInstance((Context)this).setAdvertising(false);
            this.stopScan();
            this.btAdapter.startLeScan(this.bleScanCallback);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void stopScan() {
        if (this.btAdapter == null) return;
        try {
            this.btAdapter.stopLeScan(this.bleScanCallback);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private final class NeedPairingTimeout
    implements Runnable {
        private NeedPairingTimeout() {
        }

        @Override
        public void run() {
            if (BLEStatus.getInstance((Context)BPService.this).getBleStatus() == 2) {
                BLEStatus.getInstance((Context)BPService.this).setBleStatus((Context)BPService.this, 4, true);
            }
        }
    }

    private final class ServiceHandler
    extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (message != null) {
                BPService.this.onHandleIntent((Intent)message.obj);
            }
        }
    }

}

