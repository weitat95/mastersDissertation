/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.datamodel;

import com.getqardio.android.datamodel.BaseEntity;

public class DeviceAssociation
extends BaseEntity {
    public String deviceId;
    public String serialNumber;
    public Integer syncStatus;
    public String timestamp;
    public Long userId;

    public String toString() {
        return "DeviceAssociation{userId=" + this.userId + ", syncStatus=" + this.syncStatus + ", deviceId='" + this.deviceId + '\'' + ", serialNumber='" + this.serialNumber + '\'' + ", timestamp='" + this.timestamp + '\'' + '}';
    }
}

