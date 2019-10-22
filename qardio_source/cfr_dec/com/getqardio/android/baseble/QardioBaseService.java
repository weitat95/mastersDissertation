/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Service
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothAdapter$LeScanCallback
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothGatt
 *  android.bluetooth.BluetoothGattCallback
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.bluetooth.BluetoothGattDescriptor
 *  android.bluetooth.BluetoothGattService
 *  android.bluetooth.BluetoothManager
 *  android.bluetooth.le.BluetoothLeScanner
 *  android.bluetooth.le.ScanCallback
 *  android.bluetooth.le.ScanFilter
 *  android.bluetooth.le.ScanFilter$Builder
 *  android.bluetooth.le.ScanResult
 *  android.bluetooth.le.ScanSettings
 *  android.bluetooth.le.ScanSettings$Builder
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.ParcelUuid
 *  android.util.Log
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.getqardio.android.baseble;

import android.annotation.TargetApi;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelUuid;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.getqardio.android.baseble.GattQueue;
import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.baseble.QardioBaseService$BaseGattCallback$$Lambda$1;
import com.getqardio.android.baseble.QardioBaseService$BaseScanCallback$$Lambda$1;
import com.getqardio.android.baseble.commands.EngineeringCommand;
import com.getqardio.android.baseble.commands.FirmwareCommand;
import com.getqardio.android.baseble.commands.GetModeCommand;
import com.getqardio.android.baseble.commands.ResetCommand;
import com.getqardio.android.baseble.commands.SelectUserCommand;
import com.getqardio.android.baseble.commands.SetModeCommand;
import com.getqardio.android.baseble.commands.SetTimestampCommand;
import com.getqardio.android.baseble.commands.StateCommand;
import com.getqardio.android.baseble.commands.UniqueIdCommand;
import com.getqardio.android.baseble.commands.ZeroingCommand;
import com.getqardio.android.exceptions.CommandException;
import com.getqardio.android.util.ByteUtil;
import com.getqardio.android.util.GattError;
import com.getqardio.android.util.GattUtil;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class QardioBaseService
extends Service {
    private boolean connecting = false;
    private BaseGattCallback mBaseGattCallback;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothLeScanner mBluetoothLeScanner;
    private BluetoothManager mBluetoothManager;
    private int mConnectionState = 0;
    private String mDeviceFilter;
    private GattQueue mGattQueue;
    private LocalBroadcastManager mLocalBroadcastManager;
    private int mQardioBaseState = 0;
    private BaseScanCallback mScanCallback;
    private String remoteDeviceAddress;
    private final BroadcastReceiver serviceCommandReceiver = new BroadcastReceiver(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onReceive(Context object, Intent intent) {
            object = intent.getAction();
            if (QardioBaseService.this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)String.format("serviceCommandReceiver action - %s", object));
            }
            if (QardioBaseService.this.mBluetoothAdapter == null || !QardioBaseService.this.mBluetoothAdapter.isEnabled()) {
                if (QardioBaseService.this.isDebugOn()) {
                    Log.d((String)"QardioBaseService", (String)"serviceCommandReceiver bluetooth adapter disabled");
                }
                return;
            }
            int n = -1;
            switch (((String)object).hashCode()) {
                case -715069897: {
                    if (!((String)object).equals("com.qardio.base.request.STOP_SCAN")) break;
                    n = 0;
                    break;
                }
                case -71157089: {
                    if (!((String)object).equals("com.qardio.base.request.DISCONNECT")) break;
                    n = 1;
                    break;
                }
                case -306819369: {
                    if (!((String)object).equals("com.qardio.base.request.STATE_NOTIFICATIONS")) break;
                    n = 2;
                    break;
                }
                case 1819579430: {
                    if (!((String)object).equals("com.qardio.base.request.WRITE_PROFILE")) break;
                    n = 3;
                    break;
                }
                case 875070409: {
                    if (!((String)object).equals("com.qardio.base.request.WRITE_WIFI_CONFIG")) break;
                    n = 4;
                    break;
                }
                case -875661350: {
                    if (!((String)object).equals("com.qardio.base.request.READ_FIRMWARE_VERSION")) break;
                    n = 5;
                    break;
                }
                case 1250620358: {
                    if (!((String)object).equals("com.qardio.base.request.READ_SOFTWARE_VERSION")) break;
                    n = 6;
                    break;
                }
                case 560304922: {
                    if (!((String)object).equals("com.qardio.base.request.READ_SERIAL")) break;
                    n = 7;
                    break;
                }
                case -2103600861: {
                    if (!((String)object).equals("com.qardio.base.request.READ_PROFILE")) break;
                    n = 8;
                    break;
                }
                case 1411170886: {
                    if (!((String)object).equals("com.qardio.base.request.READ_WIFI_CONFIG")) break;
                    n = 9;
                    break;
                }
                case 742988632: {
                    if (!((String)object).equals("com.qardio.base.request.WIFI")) break;
                    n = 10;
                    break;
                }
                case 1554440334: {
                    if (!((String)object).equals("com.qardio.base.request.STATE")) break;
                    n = 11;
                    break;
                }
                case -477456842: {
                    if (!((String)object).equals("com.qardio.base.request.READ_MEASUREMENT")) break;
                    n = 12;
                    break;
                }
                case 801269962: {
                    if (!((String)object).equals("com.qardio.base.request.WIFI_STATE")) break;
                    n = 13;
                    break;
                }
                case 890296475: {
                    if (!((String)object).equals("com.qardio.base.request.ENABLE_CONFIG")) break;
                    n = 14;
                    break;
                }
                case -2095165156: {
                    if (!((String)object).equals("com.qardio.base.request.DISABLE_CONFIG")) break;
                    n = 15;
                    break;
                }
                case -145678311: {
                    if (!((String)object).equals("com.qardio.base.request.ENGINEERING_NOTIFICATIONS")) break;
                    n = 16;
                    break;
                }
                case 682492879: {
                    if (!((String)object).equals("com.qardio.base.request.GET_MODE")) break;
                    n = 17;
                    break;
                }
                case 121380419: {
                    if (!((String)object).equals("com.qardio.base.request.SET_MODE")) break;
                    n = 18;
                    break;
                }
                case -1223525493: {
                    if (!((String)object).equals("com.qardio.base.request.SELECT_USER")) break;
                    n = 19;
                    break;
                }
                case -1423797626: {
                    if (!((String)object).equals("com.qardio.base.request.WRITE_RANDOM")) break;
                    n = 20;
                    break;
                }
                case 1020604084: {
                    if (!((String)object).equals("com.qardio.base.request.LEGACY_RESET_BASE")) break;
                    n = 21;
                    break;
                }
                case 2045996516: {
                    if (!((String)object).equals("com.qardio.base.request.RESET_BASE")) break;
                    n = 22;
                    break;
                }
                case 36099150: {
                    if (!((String)object).equals("com.qardio.base.request.UPDATE_FIRMWARE")) break;
                    n = 23;
                    break;
                }
                case 742780341: {
                    if (!((String)object).equals("com.qardio.base.request.PING")) break;
                    n = 24;
                    break;
                }
                case 463755419: {
                    if (!((String)object).equals("com.qardio.base.request.STOP_SERVICE")) break;
                    n = 25;
                    break;
                }
                case 1097085014: {
                    if (!((String)object).equals("com.qardio.base.request.SET_TIMESTAMP")) break;
                    n = 26;
                    break;
                }
                case 672220407: {
                    if (!((String)object).equals("com.qardio.base.request.ZEROING")) break;
                    n = 27;
                    break;
                }
            }
            switch (n) {
                default: {
                    return;
                }
                case 0: {
                    QardioBaseService.this.stopScan();
                    return;
                }
                case 1: {
                    QardioBaseService.this.disconnect();
                    return;
                }
                case 2: {
                    QardioBaseService.this.enableStateNotifications();
                    return;
                }
                case 3: {
                    object = intent.getStringExtra("com.qardio.base.DATA");
                    try {
                        object = new JSONObject((String)object);
                        QardioBaseService.this.writeQardioBaseUserConfig((JSONObject)object);
                        return;
                    }
                    catch (JSONException jSONException) {
                        return;
                    }
                }
                case 4: {
                    object = intent.getStringExtra("com.qardio.base.DATA");
                    try {
                        object = new JSONObject((String)object);
                        QardioBaseService.this.writeQardioBaseWifiConfig((JSONObject)object);
                        return;
                    }
                    catch (JSONException jSONException) {
                        return;
                    }
                }
                case 5: {
                    QardioBaseService.this.readFirmwareVersion();
                    return;
                }
                case 6: {
                    QardioBaseService.this.readSoftwareVersion();
                    return;
                }
                case 7: {
                    QardioBaseService.this.readDeviceSerial();
                    return;
                }
                case 8: {
                    QardioBaseService.this.readQardioBaseUserConfig();
                    return;
                }
                case 9: {
                    QardioBaseService.this.readQardioBaseWifiConfig();
                    return;
                }
                case 10: {
                    QardioBaseService.this.readQardioBaseWifiScan();
                    return;
                }
                case 11: {
                    QardioBaseService.this.readQardioBaseState();
                    return;
                }
                case 12: {
                    QardioBaseService.this.readQardioBaseMeasurement();
                    return;
                }
                case 13: {
                    QardioBaseService.this.readQardioBaseWifiState();
                    return;
                }
                case 14: {
                    QardioBaseService.this.enableConfigurationMode();
                    return;
                }
                case 15: {
                    QardioBaseService.this.disableConfigurationMode();
                    return;
                }
                case 16: {
                    QardioBaseService.this.enableEngineeringNotifications();
                    return;
                }
                case 17: {
                    n = intent.getIntExtra("com.qardio.base.USER_ID", -1);
                    QardioBaseService.this.getMode(n);
                    return;
                }
                case 18: {
                    n = intent.getIntExtra("com.qardio.base.USER_ID", -1);
                    int n2 = intent.getIntExtra("com.qardio.base.DATA", QardioBaseDevice.BaseMode.MODE_WEIGHT_ONLY.getMode());
                    QardioBaseService.this.setMode(n, n2);
                    return;
                }
                case 19: {
                    n = intent.getIntExtra("com.qardio.base.USER_ID", -1);
                    QardioBaseService.this.selectUser(n);
                    return;
                }
                case 20: {
                    boolean bl = intent.getBooleanExtra("com.qardio.base.DATA", false);
                    QardioBaseService.this.writeUniqueId(bl);
                    return;
                }
                case 21: {
                    QardioBaseService.this.legacyReset();
                    return;
                }
                case 22: {
                    QardioBaseService.this.resetBase();
                    return;
                }
                case 23: {
                    object = intent.getStringExtra("com.qardio.base.DATA");
                    QardioBaseService.this.updateFirmware((String)object);
                    return;
                }
                case 24: {
                    QardioBaseService.this.sendPingToBase();
                    return;
                }
                case 25: {
                    QardioBaseService.this.cleanUpService();
                    QardioBaseService.this.stopSelf();
                    return;
                }
                case 26: {
                    QardioBaseService.this.setTimestamp(System.currentTimeMillis());
                    return;
                }
                case 27: 
            }
            QardioBaseService.this.zeroing();
        }
    };
    private boolean started = false;

    private boolean checkConnectedDevices() {
        Iterator iterator = this.mBluetoothManager.getConnectedDevices(7).iterator();
        while (iterator.hasNext()) {
            if (!"QardioBase".equals(((BluetoothDevice)iterator.next()).getName())) continue;
            return true;
        }
        return false;
    }

    private void checkServices() {
        if (this.mBluetoothGatt != null && (this.mBluetoothGatt.getServices() == null || this.mBluetoothGatt.getServices().isEmpty())) {
            this.mGattQueue.addGattCommand(new DiscoverServicesCommand());
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void cleanUpService() {
        if (this.mBluetoothGatt != null) {
            BluetoothDevice bluetoothDevice = this.mBluetoothGatt.getDevice();
            if (bluetoothDevice.getName() == null) {
                String string2 = bluetoothDevice.getAddress();
            } else {
                String string3 = bluetoothDevice.getName();
            }
            if (this.isDebugOn()) {
                void var1_3;
                Log.d((String)"QardioBaseService", (String)("Disconnecting from device: " + (String)var1_3));
            }
            this.mBluetoothGatt.close();
            this.mBluetoothGatt.disconnect();
            this.mBluetoothGatt = null;
        }
        if (this.mGattQueue != null) {
            this.mGattQueue.clearCommands();
        }
        this.unregisterReceivers();
        this.mGattQueue = null;
    }

    private void dequeueCommand() {
        if (this.mGattQueue != null) {
            this.mGattQueue.dequeueCommand();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void disableConfigurationMode() {
        BluetoothGattService bluetoothGattService;
        block3: {
            block2: {
                if (this.mBluetoothGatt == null) break block2;
                bluetoothGattService = this.mBluetoothGatt.getService(QardioBaseDevice.QARDIO_BASE_SERVICE);
                if (this.mGattQueue != null && bluetoothGattService != null && this.mQardioBaseState == 1 && (bluetoothGattService = bluetoothGattService.getCharacteristic(QardioBaseDevice.QARDIO_BASE_ENGINEERING_CHAR)) != null) break block3;
            }
            return;
        }
        this.mGattQueue.addGattCommand(new StateCommand((BluetoothGattCharacteristic)bluetoothGattService, this.mBluetoothGatt, 0));
    }

    private void disconnect() {
        this.disconnect(true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void disconnect(boolean bl) {
        if (this.mGattQueue != null) {
            this.mGattQueue.clearCommands();
        }
        this.stopScan();
        this.mConnectionState = 0;
        this.mQardioBaseState = 0;
        if (this.mBluetoothGatt != null) {
            if (this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)"Closing GATT connection");
            }
            try {
                if (this.checkConnectedDevices()) {
                    this.mBluetoothGatt.disconnect();
                    this.mBluetoothGatt.close();
                }
            }
            catch (NullPointerException nullPointerException) {
                Log.e((String)"QardioBaseService", (String)"The state of the BLE device was not proper to disconnect");
            }
        }
        if (bl) {
            this.mLocalBroadcastManager.sendBroadcast(new Intent("com.qardio.base.DISCONNECTED"));
        }
        this.connecting = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void enableConfigurationMode() {
        BluetoothGattService bluetoothGattService;
        block8: {
            block7: {
                GattQueue.GATTCommand gATTCommand;
                if (this.isDebugOn()) {
                    Log.d((String)"QardioBaseService", (String)"enableConfigurationMode");
                }
                if (this.mBluetoothGatt == null) break block7;
                bluetoothGattService = this.mBluetoothGatt.getService(QardioBaseDevice.QARDIO_BASE_SERVICE);
                if (this.isDebugOn()) {
                    Log.d((String)"QardioBaseService", (String)String.format("enableConfigurationMode mQardioBaseState - %s", this.mQardioBaseState));
                }
                if (this.mQardioBaseState == 1) {
                    this.sendBroadcast(new Intent("com.qardio.base.CONFIGURATION_MODE"));
                }
                if (this.mGattQueue != null && bluetoothGattService != null && this.mQardioBaseState != 1 && (!((gATTCommand = this.mGattQueue.getCurrent()) instanceof StateCommand) || ((StateCommand)gATTCommand).getState() != 1) && (bluetoothGattService = bluetoothGattService.getCharacteristic(QardioBaseDevice.QARDIO_BASE_ENGINEERING_CHAR)) != null) break block8;
            }
            return;
        }
        if (this.isDebugOn()) {
            Log.d((String)"QardioBaseService", (String)"enableConfigurationMode addGattCommand");
        }
        this.mGattQueue.addGattCommand(new StateCommand((BluetoothGattCharacteristic)bluetoothGattService, this.mBluetoothGatt, 1));
    }

    private void enableEngineeringNotifications() {
        if (this.mGattQueue != null) {
            this.mGattQueue.addSetNotificationsCommand(QardioBaseDevice.QARDIO_BASE_ENGINEERING_CHAR, true);
        }
    }

    private void enableStateNotifications() {
        if (this.mGattQueue != null) {
            this.mGattQueue.addSetNotificationsCommand(QardioBaseDevice.QARDIO_BASE_STATE_CHAR, true);
        }
    }

    @TargetApi(value=21)
    private BluetoothLeScanner getBluetoothScanner() {
        return this.mBluetoothAdapter.getBluetoothLeScanner();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void getMode(int n) {
        if (n == -1) {
            Log.e((String)"QardioBaseService", (String)String.format("User Id not valid, current value %d", n));
            return;
        } else {
            if (this.mGattQueue == null) return;
            {
                this.mGattQueue.addGattCommand(new GetModeCommand(this.mBluetoothGatt, n));
                return;
            }
        }
    }

    private boolean initiateBluetooth() {
        if (this.mBluetoothAdapter == null) {
            this.mBluetoothManager = (BluetoothManager)this.getSystemService("bluetooth");
            this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
            if (Build.VERSION.SDK_INT >= 21) {
                this.mBluetoothLeScanner = this.getBluetoothScanner();
            }
        }
        return this.mBluetoothAdapter != null;
    }

    private boolean isDebugOn() {
        return false;
    }

    private void legacyReset() {
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.mBluetoothGatt.getService(QardioBaseDevice.QARDIO_BASE_SERVICE).getCharacteristic(QardioBaseDevice.QARDIO_BASE_ENGINEERING_CHAR);
        this.mGattQueue.addGattCommand(new EngineeringCommand(this.mBluetoothGatt, QardioBaseDevice.QARDIO_BASE_USER_CONFIG_CHAR));
        this.mGattQueue.addGattCommand(new EngineeringCommand(this.mBluetoothGatt, QardioBaseDevice.QARDIO_BASE_WIFI_CONFIG_CHAR));
        this.mGattQueue.addGattCommand(new UniqueIdCommand(bluetoothGattCharacteristic, this.mBluetoothGatt, true));
    }

    private void readDeviceSerial() {
        if (this.mGattQueue != null) {
            this.mGattQueue.addReadGattCommand(QardioBaseDevice.DEVICE_INFORMATION_SERVICE, QardioBaseDevice.DEVICE_SERIAL_CHAR);
        }
    }

    private void readQardioBaseMeasurement() {
        if (this.mGattQueue != null) {
            this.mGattQueue.addReadGattCommand(QardioBaseDevice.QARDIO_BASE_SERVICE, QardioBaseDevice.QARDIO_BASE_MEASUREMENT_CHAR);
        }
    }

    private void readQardioBaseState() {
        if (this.mGattQueue != null) {
            this.mGattQueue.addReadGattCommand(QardioBaseDevice.QARDIO_BASE_SERVICE, QardioBaseDevice.QARDIO_BASE_STATE_CHAR);
        }
    }

    private void readQardioBaseUserConfig() {
        if (this.mGattQueue != null) {
            this.mGattQueue.addReadGattCommand(QardioBaseDevice.QARDIO_BASE_SERVICE, QardioBaseDevice.QARDIO_BASE_USER_CONFIG_CHAR);
        }
    }

    private void readQardioBaseWifiConfig() {
        if (this.mGattQueue != null) {
            this.mGattQueue.addReadGattCommand(QardioBaseDevice.QARDIO_BASE_SERVICE, QardioBaseDevice.QARDIO_BASE_WIFI_CONFIG_CHAR);
        }
    }

    private void readQardioBaseWifiScan() {
        if (this.mGattQueue != null) {
            this.mGattQueue.addReadGattCommand(QardioBaseDevice.QARDIO_BASE_SERVICE, QardioBaseDevice.QARDIO_BASE_WIFI_SCAN_CHAR);
        }
    }

    private void readQardioBaseWifiState() {
        if (this.mGattQueue != null) {
            this.mGattQueue.addReadGattCommand(QardioBaseDevice.QARDIO_BASE_SERVICE, QardioBaseDevice.QARDIO_BASE_WIFI_STATE_CHAR);
        }
    }

    private boolean refreshDeviceCache(BluetoothGatt bluetoothGatt) {
        block3: {
            Method method = bluetoothGatt.getClass().getMethod("refresh", new Class[0]);
            if (method == null) break block3;
            try {
                boolean bl = (Boolean)method.invoke((Object)bluetoothGatt, new Object[0]);
                return bl;
            }
            catch (Exception exception) {
                Log.e((String)"QardioBaseService", (String)"An exception occured while refreshing device");
            }
        }
        return false;
    }

    private void registerReceivers() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qardio.base.request.READ_MEASUREMENT");
        intentFilter.addAction("com.qardio.base.request.SCAN");
        intentFilter.addAction("com.qardio.base.request.STOP_SCAN");
        intentFilter.addAction("com.qardio.base.request.DISCONNECT");
        intentFilter.addAction("com.qardio.base.request.STATE");
        intentFilter.addAction("com.qardio.base.request.READ_SERIAL");
        intentFilter.addAction("com.qardio.base.request.STATE_NOTIFICATIONS");
        intentFilter.addAction("com.qardio.base.request.WIFI");
        intentFilter.addAction("com.qardio.base.request.WRITE_PROFILE");
        intentFilter.addAction("com.qardio.base.request.WRITE_WIFI_CONFIG");
        intentFilter.addAction("com.qardio.base.request.READ_PROFILE");
        intentFilter.addAction("com.qardio.base.request.READ_WIFI_CONFIG");
        intentFilter.addAction("com.qardio.base.request.WIFI_STATE");
        intentFilter.addAction("com.qardio.base.request.STOP_SERVICE");
        intentFilter.addAction("com.qardio.base.request.READ_SOFTWARE_VERSION");
        intentFilter.addAction("com.qardio.base.request.ENABLE_CONFIG");
        intentFilter.addAction("com.qardio.base.request.DISABLE_CONFIG");
        intentFilter.addAction("com.qardio.base.request.ENGINEERING_NOTIFICATIONS");
        intentFilter.addAction("com.qardio.base.request.WRITE_RANDOM");
        intentFilter.addAction("com.qardio.base.request.LEGACY_RESET_BASE");
        intentFilter.addAction("com.qardio.base.request.RESET_BASE");
        intentFilter.addAction("com.qardio.base.request.UPDATE_FIRMWARE");
        intentFilter.addAction("com.qardio.base.request.PING");
        intentFilter.addAction("com.qardio.base.request.SET_MODE");
        intentFilter.addAction("com.qardio.base.request.GET_MODE");
        intentFilter.addAction("com.qardio.base.request.SELECT_USER");
        intentFilter.addAction("com.qardio.base.request.SET_TIMESTAMP");
        intentFilter.addAction("com.qardio.base.request.ZEROING");
        this.mLocalBroadcastManager.registerReceiver(this.serviceCommandReceiver, intentFilter);
    }

    private void resetBase() {
        if (this.mGattQueue != null) {
            BluetoothGattCharacteristic bluetoothGattCharacteristic = this.mBluetoothGatt.getService(QardioBaseDevice.QARDIO_BASE_SERVICE).getCharacteristic(QardioBaseDevice.QARDIO_BASE_ENGINEERING_CHAR);
            this.mGattQueue.addGattCommand(new UniqueIdCommand(bluetoothGattCharacteristic, this.mBluetoothGatt, true));
            this.mGattQueue.addGattCommand(new ResetCommand(this.mBluetoothGatt));
        }
    }

    private void scanAndConnect(String string2) {
        block7: {
            block6: {
                if (this.isDebugOn()) {
                    Log.d((String)"QardioBaseService", (String)String.format("scanAndConnect - %s", string2));
                }
                if (!this.initiateBluetooth()) break block6;
                if (this.mConnectionState != 2) break block7;
                this.mBaseGattCallback.sendBaseBroadcast("com.qardio.base.CONNECTED", this.remoteDeviceAddress);
            }
            return;
        }
        if (this.isDebugOn()) {
            Log.d((String)"QardioBaseService", (String)String.format("%d Time: %d - Scan for BLE devices", ((Object)((Object)this)).hashCode(), System.currentTimeMillis()));
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.scanAndConnectL21(string2);
            return;
        }
        this.scanAndConnectLegacy();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @TargetApi(value=21)
    private void scanAndConnectL21(String var1_1) {
        block10: {
            block9: {
                block7: {
                    block8: {
                        if (this.mBluetoothGatt == null) break block7;
                        var2_3 = this.mBluetoothManager.getConnectedDevices(7);
                        if (var2_3 != null && !var2_3.isEmpty()) break block8;
                        if (this.isDebugOn()) {
                            Log.d((String)"QardioBaseService", (String)(this.hashCode() + " No connected devices"));
                        }
                        break block9;
                    }
                    var3_4 = var2_3.iterator();
                    break block10;
                }
                if (this.isDebugOn()) {
                    Log.d((String)"QardioBaseService", (String)(this.hashCode() + " No connected devices"));
                }
            }
            do {
                if (this.isDebugOn()) {
                    Log.d((String)"QardioBaseService", (String)"Using API Level 21 to Connect");
                }
                if (this.mScanCallback == null) {
                    this.mScanCallback = new BaseScanCallback();
                }
                var2_3 = new ScanSettings.Builder().setScanMode(2).build();
                var4_5 = new ScanFilter.Builder().setServiceUuid(new ParcelUuid(QardioBaseDevice.BASE_UUID)).build();
                var3_4 = new ArrayList<E>();
                var3_4.add(var4_5);
                if (this.mBluetoothLeScanner == null) return;
                if (this.mBluetoothAdapter.isEnabled() == false) return;
                var1_2 = new ScanResult(var1_1 /* !! */ );
                if (this.isDebugOn()) {
                    Log.d((String)"QardioBaseService", (String)"start scan");
                }
                this.mBluetoothLeScanner.startScan((List)var3_4, (ScanSettings)var2_3, (ScanCallback)var1_2);
                return;
                break;
            } while (true);
        }
        do {
            if (!var3_4.hasNext()) ** continue;
            var2_3 = (BluetoothDevice)var3_4.next();
            if (!this.isDebugOn()) ** continue;
            var2_3 = var2_3.getName() == null ? var2_3.getAddress() : var2_3.getName();
            Log.d((String)"QardioBaseService", (String)(this.hashCode() + " Connected device: " + (String)var2_3));
        } while (true);
    }

    private void scanAndConnectLegacy() {
        if (this.isDebugOn()) {
            Log.d((String)"QardioBaseService", (String)"Using Legacy API Level to Connect");
        }
        if (this.mScanCallback == null) {
            this.mScanCallback = new BaseScanCallback();
        }
        if (this.isDebugOn()) {
            Log.d((String)"QardioBaseService", (String)"start scan");
        }
        this.mBluetoothAdapter.startLeScan((BluetoothAdapter.LeScanCallback)this.mScanCallback);
    }

    private void selectUser(int n) {
        if (n == -1) {
            Log.e((String)"QardioBaseService", (String)String.format("User Id not valid, current value %d", n));
            return;
        }
        this.mGattQueue.addGattCommand(new SelectUserCommand(this.mBluetoothGatt, n));
    }

    private void sendPingToBase() {
        if (this.mGattQueue != null) {
            this.mGattQueue.addReadGattCommand(QardioBaseDevice.DEVICE_BATTERY_SERVICE, QardioBaseDevice.BATTERY_PERCENTAGE_CHAR);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setMode(int n, int n2) {
        if (n == -1) {
            Log.e((String)"QardioBaseService", (String)String.format("User Id not valid, current value %d", n));
            return;
        } else {
            if (this.mGattQueue == null) return;
            {
                this.mGattQueue.addGattCommand(new SetModeCommand(this.mBluetoothGatt, n, n2));
                return;
            }
        }
    }

    private void setTimestamp(long l) {
        if (this.mGattQueue != null) {
            this.mBluetoothGatt.getService(QardioBaseDevice.QARDIO_BASE_SERVICE).getCharacteristic(QardioBaseDevice.QARDIO_BASE_ENGINEERING_CHAR);
            this.mGattQueue.addGattCommand(new SetTimestampCommand(this.mBluetoothGatt, l));
        }
    }

    @TargetApi(value=21)
    private void stopScan() {
        if (this.mBluetoothLeScanner != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                this.mBluetoothLeScanner.stopScan(new ScanCallback(){

                    public void onScanResult(int n, android.bluetooth.le.ScanResult scanResult) {
                    }
                });
            }
            return;
        }
        this.mBluetoothAdapter.stopLeScan((BluetoothAdapter.LeScanCallback)this.mScanCallback);
    }

    private void unregisterReceivers() {
        this.mLocalBroadcastManager.unregisterReceiver(this.serviceCommandReceiver);
    }

    private void updateFirmware(String string2) {
        if (this.mGattQueue != null) {
            BluetoothGattCharacteristic bluetoothGattCharacteristic = this.mBluetoothGatt.getService(QardioBaseDevice.QARDIO_BASE_SERVICE).getCharacteristic(QardioBaseDevice.QARDIO_BASE_ENGINEERING_CHAR);
            this.mGattQueue.addGattCommand(new FirmwareCommand(bluetoothGattCharacteristic, this.mBluetoothGatt, string2));
        }
    }

    private void writeQardioBaseUserConfig(JSONObject jSONObject) {
        if (this.mQardioBaseState == 1) {
            this.mGattQueue.addGattCommand(new EngineeringCommand(this.mBluetoothGatt, jSONObject.toString(), QardioBaseDevice.QARDIO_BASE_USER_CONFIG_CHAR));
        }
    }

    private void writeQardioBaseWifiConfig(JSONObject jSONObject) {
        if (this.mQardioBaseState == 1) {
            this.mGattQueue.addGattCommand(new EngineeringCommand(this.mBluetoothGatt, jSONObject.toString(), QardioBaseDevice.QARDIO_BASE_WIFI_CONFIG_CHAR));
        }
    }

    private void writeUniqueId(boolean bl) {
        if (this.mGattQueue != null) {
            BluetoothGattCharacteristic bluetoothGattCharacteristic = this.mBluetoothGatt.getService(QardioBaseDevice.QARDIO_BASE_SERVICE).getCharacteristic(QardioBaseDevice.QARDIO_BASE_ENGINEERING_CHAR);
            this.mGattQueue.addGattCommand(new UniqueIdCommand(bluetoothGattCharacteristic, this.mBluetoothGatt, bl));
        }
    }

    private void zeroing() {
        if (this.mGattQueue != null) {
            this.mGattQueue.addGattCommand(new ZeroingCommand(this.mBluetoothGatt));
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        if (this.isDebugOn()) {
            Log.d((String)"QardioBaseService", (String)"Starting Base Service");
        }
        this.mLocalBroadcastManager = LocalBroadcastManager.getInstance((Context)this);
    }

    public void onDestroy() {
        if (this.isDebugOn()) {
            Log.d((String)"QardioBaseService", (String)("Service destroyed: " + ((Object)((Object)this)).hashCode()));
        }
        this.started = false;
        this.cleanUpService();
    }

    public int onStartCommand(Intent intent, int n, int n2) {
        if (this.isDebugOn()) {
            Log.d((String)"QardioBaseService", (String)("Service started: " + ((Object)((Object)this)).hashCode()));
        }
        if (!this.started) {
            if (intent != null) {
                this.mDeviceFilter = intent.getStringExtra("com.qardio.base.DEVICE_FILTER");
            }
            this.mBaseGattCallback = new BaseGattCallback();
            this.registerReceivers();
            this.mLocalBroadcastManager.sendBroadcast(new Intent("com.qardio.base.SERVICE_READY"));
            this.started = true;
        }
        if (intent != null) {
            this.scanAndConnect(intent.getStringExtra("com.qardio.base.DEVICE_ADDRESS"));
            return 1;
        }
        this.scanAndConnect(null);
        return 1;
    }

    public void readFirmwareVersion() {
        if (this.mGattQueue != null) {
            this.mGattQueue.addReadGattCommand(QardioBaseDevice.DEVICE_INFORMATION_SERVICE, QardioBaseDevice.DEVICE_FIRMWARE_VERSION_CHAR);
        }
    }

    public void readSoftwareVersion() {
        if (this.mGattQueue != null) {
            this.mGattQueue.addReadGattCommand(QardioBaseDevice.DEVICE_INFORMATION_SERVICE, QardioBaseDevice.DEVICE_SOFTWARE_VERSION_CHAR);
        }
    }

    private final class BaseGattCallback
    extends BluetoothGattCallback {
        private BaseGattCallback() {
        }

        static /* synthetic */ void access$lambda$0(BaseGattCallback baseGattCallback, String string2) {
            baseGattCallback.sendConnectionErrorBroadcast(string2);
        }

        private void checkState(int n) {
            if (QardioBaseService.this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)String.format("checkState state - %d", n));
            }
            if (QardioBaseService.this.mQardioBaseState != n) {
                QardioBaseService.this.mQardioBaseState = n;
                if (n == 1) {
                    this.sendBaseBroadcast("com.qardio.base.CONFIGURATION_MODE");
                }
                if (n == 6) {
                    QardioBaseService.this.readQardioBaseMeasurement();
                }
                this.sendBaseBroadcast("com.qardio.base.STATE", n);
            }
        }

        private void handleConnected(int n) {
            if (QardioBaseService.this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)String.format("handleConnected status - %d", n));
            }
            if (n != 0) {
                this.sendConnectionErrorBroadcast(String.format(Locale.ENGLISH, "Error connecting to base: %s", GattError.errorFromState(n)));
                QardioBaseService.this.disconnect(false);
                return;
            }
            if (QardioBaseService.this.mGattQueue != null) {
                QardioBaseService.this.mGattQueue.clearCommands();
                QardioBaseService.this.mGattQueue = null;
            }
            QardioBaseService.this.mConnectionState = 2;
            QardioBaseService.this.mGattQueue = this.createGattQueue();
            QardioBaseService.this.mGattQueue.setBluetoothGatt(QardioBaseService.this.mBluetoothGatt);
            if (QardioBaseService.this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)String.format("%d Time: %d - Device Connected", ((Object)((Object)QardioBaseService.this)).hashCode(), System.currentTimeMillis()));
            }
            QardioBaseService.this.checkServices();
        }

        private void handleConnecting() {
            QardioBaseService.this.mConnectionState = 1;
            if (QardioBaseService.this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)"Connecting to base");
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void handleDisconnect(int n) {
            Log.i((String)"QardioBaseService", (String)"Disconnected from base");
            QardioBaseService.this.mConnectionState = 0;
            QardioBaseService.this.connecting = false;
            QardioBaseService.this.mQardioBaseState = 0;
            if (QardioBaseService.this.mGattQueue != null) {
                QardioBaseService.this.mGattQueue.clearCommands();
                QardioBaseService.this.mGattQueue = null;
            }
            if (n != 0) {
                this.sendConnectionErrorBroadcast(String.format(Locale.ENGLISH, "Error disconnecting from base: %s", GattError.errorFromState(n)));
                QardioBaseService.this.mBluetoothGatt.close();
                return;
            } else {
                if (QardioBaseService.this.mBluetoothGatt == null) return;
                {
                    if (QardioBaseService.this.isDebugOn()) {
                        Log.d((String)"QardioBaseService", (String)"Closing GATT connection");
                    }
                    QardioBaseService.this.mBluetoothGatt.close();
                    return;
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private void handleEngineering(byte[] arrby, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            if (arrby.length < 4) return;
            if (arrby[0] != 0) return;
            if (arrby[1] != 0) return;
            byte by = arrby[2];
            if (QardioBaseService.this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)String.format("handleEngineering command - %d, value[3] - %d", by, arrby[3]));
                Log.d((String)"QardioBaseService", (String)String.format("handleEngineering QARDIO_BASE_ENGINEERING_CHAR new value - %s", ByteUtil.bytesToHex(arrby)));
            }
            switch (by) {
                default: {
                    return;
                }
                case 4: {
                    if (arrby[3] == 0) {
                        this.writeData();
                        return;
                    }
                    String string2 = String.format(Locale.ENGLISH, "EngineeringWrite failed: %d", arrby[3]);
                    Log.e((String)"QardioBaseService", (String)string2);
                    if (QardioBaseDevice.QARDIO_BASE_USER_CONFIG_CHAR.equals(bluetoothGattCharacteristic.getUuid())) {
                        this.sendErrorBroadcast(string2, 10);
                    }
                    if (QardioBaseDevice.QARDIO_BASE_WIFI_CONFIG_CHAR.equals(bluetoothGattCharacteristic.getUuid())) {
                        this.sendErrorBroadcast(string2, 11);
                    }
                    if (QardioBaseDevice.QARDIO_BASE_ENGINEERING_CHAR.equals(bluetoothGattCharacteristic.getUuid()) && arrby[3] == -2) {
                        this.sendErrorBroadcast(string2, 14);
                    }
                    QardioBaseService.this.dequeueCommand();
                    return;
                }
                case 17: {
                    this.handleReset();
                    QardioBaseService.this.dequeueCommand();
                    return;
                }
                case 16: {
                    this.handleUniqueId(arrby);
                    QardioBaseService.this.dequeueCommand();
                    return;
                }
                case 9: {
                    this.handleFirmwareComplete(arrby[3]);
                    QardioBaseService.this.dequeueCommand();
                    return;
                }
                case 21: {
                    this.handleSetMode();
                    QardioBaseService.this.dequeueCommand();
                    return;
                }
                case 22: {
                    this.handleGetMode(arrby[3]);
                    QardioBaseService.this.dequeueCommand();
                    return;
                }
                case 20: {
                    QardioBaseService.this.dequeueCommand();
                    return;
                }
                case 10: {
                    QardioBaseService.this.dequeueCommand();
                    return;
                }
                case 12: {
                    this.handleTimestamp();
                    QardioBaseService.this.dequeueCommand();
                    return;
                }
                case 39: {
                    if (arrby[3] > 0) return;
                    this.handleFirmwareComplete(arrby[3]);
                    return;
                }
                case 25: 
            }
            QardioBaseService.this.dequeueCommand();
        }

        private void handleFirmwareComplete(int n) {
            this.sendBaseBroadcast("com.qardio.base.QB_FIRMWARE_UPDATED", n);
        }

        private void handleGetMode(int n) {
            this.sendBaseBroadcast("com.qardio.base.QB_MODE", n);
        }

        private void handleReset() {
            this.sendBaseBroadcast("com.qardio.base.QB_RESET");
        }

        private void handleSetMode() {
            this.sendBaseBroadcast("com.qardio.base.QB_MODE_WRITTEN");
        }

        private void handleTimestamp() {
            this.sendBaseBroadcast("com.qardio.base.QB_TIMESTAMP");
        }

        private void handleUniqueId(byte[] arrby) {
            byte[] arrby2 = new byte[16];
            System.arraycopy(arrby, 3, arrby2, 0, 16);
            this.sendBaseBroadcast("com.qardio.base.QB_RANDOM_WRITTEN", new String(arrby2));
        }

        private void sendBaseBroadcast(String string2) {
            string2 = new Intent(string2);
            QardioBaseService.this.mLocalBroadcastManager.sendBroadcast((Intent)string2);
        }

        private void sendBaseBroadcast(String string2, int n) {
            string2 = new Intent(string2);
            string2.putExtra("com.qardio.base.DATA", n);
            QardioBaseService.this.mLocalBroadcastManager.sendBroadcast((Intent)string2);
        }

        private void sendBaseBroadcast(String string2, String string3) {
            string2 = new Intent(string2);
            string2.putExtra("com.qardio.base.DATA", string3);
            QardioBaseService.this.mLocalBroadcastManager.sendBroadcast((Intent)string2);
        }

        private void sendConnectionErrorBroadcast(String string2) {
            Intent intent = new Intent("com.qardio.base.QB_CONNECTION_ERROR");
            intent.putExtra("com.qardio.base.QB_ERROR_MSG", string2);
            QardioBaseService.this.mLocalBroadcastManager.sendBroadcast(intent);
        }

        private void sendErrorBroadcast(String string2, int n) {
            Intent intent = new Intent("com.qardio.base.QB_ERROR");
            intent.putExtra("com.qardio.base.QB_ERROR_MSG", string2).putExtra("com.qardio.base.QB_ERROR_CODE", n);
            QardioBaseService.this.mLocalBroadcastManager.sendBroadcast(intent);
        }

        private void writeData() {
            try {
                GattQueue.GATTCommand gATTCommand = QardioBaseService.this.mGattQueue.getCurrent();
                if (QardioBaseService.this.isDebugOn()) {
                    Log.d((String)"QardioBaseService", (String)("Current command in write data: " + gATTCommand.getName()));
                }
                if (gATTCommand instanceof EngineeringCommand && !((EngineeringCommand)(gATTCommand = (EngineeringCommand)gATTCommand)).continueWrite()) {
                    if (((EngineeringCommand)gATTCommand).getDestinationUUID().equals(QardioBaseDevice.QARDIO_BASE_USER_CONFIG_CHAR)) {
                        this.sendBaseBroadcast("com.qardio.base.QB_USER_CONFIG_WRITTEN");
                    }
                    if (((EngineeringCommand)gATTCommand).getDestinationUUID().equals(QardioBaseDevice.QARDIO_BASE_WIFI_CONFIG_CHAR)) {
                        this.sendBaseBroadcast("com.qardio.base.QB_WIFI_CONFIG_WRITTEN");
                    }
                    QardioBaseService.this.dequeueCommand();
                }
                return;
            }
            catch (CommandException commandException) {
                QardioBaseService.this.dequeueCommand();
                return;
            }
        }

        public GattQueue createGattQueue() {
            return new GattQueue(QardioBaseService$BaseGattCallback$$Lambda$1.lambdaFactory$(this));
        }

        public void onCharacteristicChanged(BluetoothGatt object, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            object = bluetoothGattCharacteristic.getUuid();
            if (QardioBaseService.this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)String.format("onCharacteristicChanged uuid - %s", object));
            }
            if (((UUID)object).equals(QardioBaseDevice.QARDIO_BASE_STATE_CHAR)) {
                int n = bluetoothGattCharacteristic.getIntValue(17, 0);
                if (QardioBaseService.this.isDebugOn()) {
                    Log.d((String)"QardioBaseService", (String)String.format("onCharacteristicChanged  QARDIO_BASE_STATE_CHAR state - %d", n));
                }
                this.checkState(n);
                if (n == 1) {
                    QardioBaseService.this.dequeueCommand();
                }
            }
            if (((UUID)object).equals(QardioBaseDevice.QARDIO_BASE_ENGINEERING_CHAR)) {
                this.handleEngineering(bluetoothGattCharacteristic.getValue(), bluetoothGattCharacteristic);
            }
        }

        public void onCharacteristicRead(BluetoothGatt object, BluetoothGattCharacteristic object2, int n) {
            object = object2.getUuid();
            if (QardioBaseService.this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)String.format("Read result from: %s, status - %s", GattUtil.nameFromCharacteristic((UUID)object), n));
            }
            if (n == 129) {
                QardioBaseService.this.disconnect(true);
                return;
            }
            if (n != 0) {
                object2 = GattError.errorFromState(n);
                object = String.format(Locale.ENGLISH, "Error reading characteristic %s, Gatt returned %d: %s", ((UUID)object).toString(), n, object2);
                Log.e((String)"QardioBaseService", (String)object);
                this.sendErrorBroadcast((String)object, 30);
                QardioBaseService.this.dequeueCommand();
                return;
            }
            if (((UUID)object).equals(QardioBaseDevice.NAME_CHAR)) {
                this.sendBaseBroadcast("com.qardio.base.DEVICE_NAME", object2.getStringValue(0));
            }
            if (((UUID)object).equals(QardioBaseDevice.APPEARANCE_CHAR)) {
                this.sendBaseBroadcast("com.qardio.base.APPEARANCE", object2.getIntValue(18, 0));
            }
            if (((UUID)object).equals(QardioBaseDevice.BATTERY_PERCENTAGE_CHAR)) {
                this.sendBaseBroadcast("com.qardio.base.QB_PONG", object2.getIntValue(17, 0));
            }
            if (((UUID)object).equals(QardioBaseDevice.DEVICE_MODEL_CHAR)) {
                this.sendBaseBroadcast("com.qardio.base.DEVICE_MODEL", object2.getStringValue(0));
            }
            if (((UUID)object).equals(QardioBaseDevice.DEVICE_SERIAL_CHAR)) {
                this.sendBaseBroadcast("com.qardio.base.DEVICE_SERIAL", object2.getStringValue(0));
            }
            if (((UUID)object).equals(QardioBaseDevice.DEVICE_FIRMWARE_VERSION_CHAR)) {
                this.sendBaseBroadcast("com.qardio.base.FIRMWARE_VERSION", object2.getStringValue(0));
            }
            if (((UUID)object).equals(QardioBaseDevice.DEVICE_SOFTWARE_VERSION_CHAR)) {
                this.sendBaseBroadcast("com.qardio.base.SOFTWARE_VERSION", object2.getStringValue(0));
            }
            if (((UUID)object).equals(QardioBaseDevice.QARDIO_BASE_STATE_CHAR)) {
                n = object2.getIntValue(17, 0);
                this.checkState(n);
                this.sendBaseBroadcast("com.qardio.base.STATE", n);
            }
            if (((UUID)object).equals(QardioBaseDevice.QARDIO_BASE_WIFI_STATE_CHAR)) {
                this.sendBaseBroadcast("com.qardio.base.QB_WIFI_STATE", object2.getIntValue(17, 0));
            }
            if (((UUID)object).equals(QardioBaseDevice.QARDIO_BASE_WIFI_CONFIG_CHAR)) {
                this.sendBaseBroadcast("com.qardio.base.QB_WIFI_CONFIG", object2.getStringValue(0));
            }
            if (((UUID)object).equals(QardioBaseDevice.QARDIO_BASE_WIFI_SCAN_CHAR)) {
                this.sendBaseBroadcast("com.qardio.base.QB_WIFI_SCAN_RESULTS", object2.getStringValue(0));
            }
            if (((UUID)object).equals(QardioBaseDevice.QARDIO_BASE_USER_CONFIG_CHAR)) {
                this.sendBaseBroadcast("com.qardio.base.QB_USER_CONFIG", object2.getStringValue(0));
            }
            if (((UUID)object).equals(QardioBaseDevice.QARDIO_BASE_MEASUREMENT_CHAR)) {
                this.sendBaseBroadcast("com.qardio.base.QB_MEASUREMENT", object2.getStringValue(0));
            }
            QardioBaseService.this.dequeueCommand();
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public void onCharacteristicWrite(BluetoothGatt object, BluetoothGattCharacteristic object2, int n) {
            void var3_4;
            object = object2.getUuid();
            if (QardioBaseService.this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)String.format("onCharacteristicWrite status - %d, uuid - %s ", (int)var3_4, object));
            }
            if (var3_4 != false) {
                String string2 = GattError.errorFromState((int)var3_4);
                Log.e((String)"QardioBaseService", (String)String.format("Error writing characteristic %s, Gatt returned %d: %s", ((UUID)object).toString(), (int)var3_4, string2));
            }
            if (!((UUID)object).equals(QardioBaseDevice.QARDIO_BASE_ENGINEERING_CHAR)) {
                QardioBaseService.this.dequeueCommand();
                return;
            }
            if (QardioBaseService.this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)"Written engineering mode");
            }
            if ((object = QardioBaseService.this.mGattQueue.getCurrent()) instanceof SetTimestampCommand) {
                QardioBaseService.this.dequeueCommand();
                return;
            } else {
                if (!(object instanceof ZeroingCommand)) return;
                {
                    QardioBaseService.this.dequeueCommand();
                    return;
                }
            }
        }

        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int n, int n2) {
            if (QardioBaseService.this.isDebugOn()) {
                Log.i((String)"QardioBaseService", (String)String.format("Connection State: %d, Gatt Status: %d - %s", n2, n, GattError.errorFromState(n)));
            }
            if (n2 == 2 && QardioBaseService.this.mConnectionState == n2 && n == 0) {
                return;
            }
            switch (n2) {
                default: {
                    return;
                }
                case 0: {
                    this.handleDisconnect(n);
                    return;
                }
                case 2: {
                    this.handleConnected(n);
                    return;
                }
                case 1: 
            }
            this.handleConnecting();
        }

        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int n) {
            if (QardioBaseService.this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)String.format("onDescriptorWrite status - %d", n));
            }
            QardioBaseService.this.dequeueCommand();
        }

        public void onServicesDiscovered(BluetoothGatt object, int n) {
            if (QardioBaseService.this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)String.format("%d Time: %d - Services discovered", ((Object)((Object)QardioBaseService.this)).hashCode(), System.currentTimeMillis()));
            }
            QardioBaseService.this.dequeueCommand();
            if (n == 0) {
                QardioBaseService.this.remoteDeviceAddress = object.getDevice().getAddress();
                if (QardioBaseService.this.isDebugOn()) {
                    Log.d((String)"QardioBaseService", (String)String.format("Connected to base with address: %s", QardioBaseService.this.remoteDeviceAddress));
                }
                this.sendBaseBroadcast("com.qardio.base.CONNECTED", QardioBaseService.this.remoteDeviceAddress);
                return;
            }
            String string2 = GattError.errorFromState(n);
            object = object.getDevice().getAddress();
            this.sendConnectionErrorBroadcast(String.format(Locale.ENGLISH, "Error discovering services for device with address: %s. Gatt returned %d: %s", object, n, string2));
        }
    }

    private final class BaseScanCallback
    implements BluetoothAdapter.LeScanCallback {
        private BaseScanCallback() {
        }

        /*
         * Enabled aggressive block sorting
         */
        private void connectToDevice(BluetoothDevice bluetoothDevice, int n) {
            String string2 = bluetoothDevice.getName() == null ? bluetoothDevice.getAddress() : bluetoothDevice.getName();
            if (QardioBaseService.this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)("device: " + string2 + " " + n));
            }
            new Handler(QardioBaseService.this.getMainLooper()).post(QardioBaseService$BaseScanCallback$$Lambda$1.lambdaFactory$(this, bluetoothDevice));
        }

        /* synthetic */ void lambda$connectToDevice$0(BluetoothDevice bluetoothDevice) {
            QardioBaseService.this.mBluetoothGatt = bluetoothDevice.connectGatt((Context)QardioBaseService.this, false, (BluetoothGattCallback)QardioBaseService.this.mBaseGattCallback);
            QardioBaseService.this.refreshDeviceCache(QardioBaseService.this.mBluetoothGatt);
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public void onLeScan(BluetoothDevice bluetoothDevice, int n, byte[] object) {
            block8: {
                block7: {
                    if (QardioBaseService.this.isDebugOn()) {
                        void var3_5;
                        if (bluetoothDevice.getName() == null) {
                            String string2 = "no name";
                        } else {
                            String string3 = bluetoothDevice.getName();
                        }
                        String string4 = bluetoothDevice.getAddress() == null ? "no address" : bluetoothDevice.getAddress();
                        Log.d((String)"QardioBaseService", (String)String.format("BLE Scan: %s - %s with signal strength %d", var3_5, string4, n));
                    }
                    if (bluetoothDevice.getName() == null || bluetoothDevice.getAddress() == null || !bluetoothDevice.getName().equals("QardioBase")) break block7;
                    if (QardioBaseService.this.mDeviceFilter == null && !QardioBaseService.this.connecting) {
                        QardioBaseService.this.mBluetoothAdapter.stopLeScan((BluetoothAdapter.LeScanCallback)this);
                        QardioBaseService.this.connecting = true;
                        this.connectToDevice(bluetoothDevice, n);
                    }
                    if (!QardioBaseService.this.connecting && QardioBaseService.this.mDeviceFilter != null && bluetoothDevice.getAddress().toUpperCase().equals(QardioBaseService.this.mDeviceFilter)) break block8;
                }
                return;
            }
            QardioBaseService.this.mBluetoothAdapter.stopLeScan((BluetoothAdapter.LeScanCallback)this);
            QardioBaseService.this.connecting = true;
            this.connectToDevice(bluetoothDevice, n);
        }
    }

    private class DiscoverServicesCommand
    implements GattQueue.GATTCommand {
        private DiscoverServicesCommand() {
        }

        @Override
        public void execute() {
            if (QardioBaseService.this.mBluetoothGatt != null) {
                QardioBaseService.this.mBluetoothGatt.discoverServices();
                return;
            }
            Log.e((String)"QardioBaseService", (String)"failed to discover services, no GATT connection");
            QardioBaseService.this.dequeueCommand();
        }

        @Override
        public String getName() {
            return "Discover Services Command";
        }
    }

    @TargetApi(value=21)
    private final class ScanResult
    extends ScanCallback {
        private String deviceAddress;
        private boolean isConnecting = false;

        ScanResult(String string2) {
            this.deviceAddress = string2;
        }

        public void onScanFailed(int n) {
            if (QardioBaseService.this.isDebugOn()) {
                Log.d((String)"QardioBaseService", (String)String.format("onScanFailed errorCode - %d", n));
            }
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public void onScanResult(int n, android.bluetooth.le.ScanResult object) {
            BluetoothDevice bluetoothDevice;
            String string2;
            block8: {
                block7: {
                    if (QardioBaseService.this.isDebugOn()) {
                        Log.d((String)"QardioBaseService", (String)String.format("Callback Type: %s, result - %s", n, object));
                    }
                    if (QardioBaseService.this.mBluetoothAdapter == null) break block7;
                    QardioBaseService.this.mBluetoothAdapter.getBluetoothLeScanner().stopScan((ScanCallback)this);
                    bluetoothDevice = object.getDevice();
                    string2 = bluetoothDevice.getAddress();
                    if (!this.isConnecting) break block8;
                }
                return;
            }
            this.isConnecting = true;
            if (bluetoothDevice.getName() == null) {
                String string3 = bluetoothDevice.getAddress();
            } else {
                String string4 = bluetoothDevice.getName();
            }
            if (QardioBaseService.this.isDebugOn()) {
                void var2_4;
                Log.d((String)"QardioBaseService", (String)String.format("%d Time: %d - Device found: %s MAC: %s", ((Object)((Object)QardioBaseService.this)).hashCode(), System.currentTimeMillis(), var2_4, string2));
            }
            QardioBaseService.this.mBluetoothGatt = bluetoothDevice.connectGatt((Context)QardioBaseService.this, false, (BluetoothGattCallback)QardioBaseService.this.mBaseGattCallback);
            QardioBaseService.this.refreshDeviceCache(QardioBaseService.this.mBluetoothGatt);
        }
    }

}

