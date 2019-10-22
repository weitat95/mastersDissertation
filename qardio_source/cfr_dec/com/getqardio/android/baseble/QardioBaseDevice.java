/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.baseble;

import java.util.UUID;

public class QardioBaseDevice {
    public static final UUID APPEARANCE_CHAR;
    public static final UUID BASE_UUID;
    public static final UUID BATTERY_PERCENTAGE_CHAR;
    public static final UUID DEVICE_BATTERY_SERVICE;
    public static final UUID DEVICE_FIRMWARE_VERSION_CHAR;
    public static final UUID DEVICE_INFORMATION_SERVICE;
    public static final UUID DEVICE_MANUFACTURER_CHAR;
    public static final UUID DEVICE_MODEL_CHAR;
    public static final UUID DEVICE_SERIAL_CHAR;
    public static final UUID DEVICE_SOFTWARE_VERSION_CHAR;
    public static final UUID GENERIC_ACCESS_SERVICE;
    public static final UUID NAME_CHAR;
    public static final UUID QARDIO_BASE_CALIBRATION_CHAR;
    public static final UUID QARDIO_BASE_ENGINEERING_CHAR;
    public static final UUID QARDIO_BASE_FACTORY_CHAR;
    public static final UUID QARDIO_BASE_MEASUREMENT_CHAR;
    public static final UUID QARDIO_BASE_SERVICE;
    public static final UUID QARDIO_BASE_STATE_CHAR;
    public static final UUID QARDIO_BASE_USER_CONFIG_CHAR;
    public static final UUID QARDIO_BASE_WIFI_CONFIG_CHAR;
    public static final UUID QARDIO_BASE_WIFI_SCAN_CHAR;
    public static final UUID QARDIO_BASE_WIFI_STATE_CHAR;

    static {
        BASE_UUID = UUID.fromString("c8219e89-93e0-4169-a3dc-ea7959e866af");
        GENERIC_ACCESS_SERVICE = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");
        NAME_CHAR = UUID.fromString("00002a00-0000-1000-8000-00805f9b34fb");
        APPEARANCE_CHAR = UUID.fromString("00002a01-0000-1000-8000-00805f9b34fb");
        DEVICE_INFORMATION_SERVICE = UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb");
        DEVICE_MANUFACTURER_CHAR = UUID.fromString("00002a29-0000-1000-8000-00805f9b34fb");
        DEVICE_MODEL_CHAR = UUID.fromString("00002a24-0000-1000-8000-00805f9b34fb");
        DEVICE_SERIAL_CHAR = UUID.fromString("00002a25-0000-1000-8000-00805f9b34fb");
        DEVICE_FIRMWARE_VERSION_CHAR = UUID.fromString("00002a26-0000-1000-8000-00805f9b34fb");
        DEVICE_SOFTWARE_VERSION_CHAR = UUID.fromString("00002a28-0000-1000-8000-00805f9b34fb");
        DEVICE_BATTERY_SERVICE = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb");
        BATTERY_PERCENTAGE_CHAR = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");
        QARDIO_BASE_SERVICE = UUID.fromString("c8219e89-93e0-4169-a3dc-ea7959e866af");
        QARDIO_BASE_STATE_CHAR = UUID.fromString("a78af805-8f3f-4e8f-a964-318b768bc38c");
        QARDIO_BASE_WIFI_STATE_CHAR = UUID.fromString("6bdee76b-af2f-4b2c-8b10-20964c9bfb73");
        QARDIO_BASE_WIFI_CONFIG_CHAR = UUID.fromString("7d1e2641-7b2f-4d27-8434-25c54975d550");
        QARDIO_BASE_USER_CONFIG_CHAR = UUID.fromString("9891780f-831e-4741-a2e8-c4a7956cc31e");
        QARDIO_BASE_CALIBRATION_CHAR = UUID.fromString("1ec92a15-14e0-43e7-a990-cb37000990ba");
        QARDIO_BASE_MEASUREMENT_CHAR = UUID.fromString("b24f98be-9cd4-4f82-b935-01f18f104ede");
        QARDIO_BASE_WIFI_SCAN_CHAR = UUID.fromString("7f9b740b-036a-47f7-b39e-d58b0559a471");
        QARDIO_BASE_FACTORY_CHAR = UUID.fromString("3f4b3ed0-b260-450d-a7cc-34f5eb91b69e");
        QARDIO_BASE_ENGINEERING_CHAR = UUID.fromString("9f3f4e1b-37d7-4f95-b374-cf585d808beb");
    }

    public static enum BaseBoolean {
        TRUE(1),
        FALSE(0);

        int value;

        private BaseBoolean(int n2) {
            this.value = n2;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum BaseMode {
        MODE_NORMAL(0),
        MODE_WEIGHT_ONLY(1),
        MODE_SMART(2),
        MODE_PREGNANCY(3),
        MODE_SAFE(4);

        int mode;

        private BaseMode(int n2) {
            this.mode = n2;
        }

        public int getMode() {
            return this.mode;
        }
    }

}

