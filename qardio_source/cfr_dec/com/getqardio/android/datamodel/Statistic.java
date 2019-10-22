/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.datamodel;

import com.getqardio.android.datamodel.BaseEntity;

public class Statistic
extends BaseEntity {
    public int badMeasurementsCount;
    public int batteryStatus;
    public int changedBatteriesCount;
    public String deviceId;
    public String firmware;
    public boolean isActive;
    public int measurementsCount;
    public long userId;

    public Statistic() {
        this.userId = -1L;
    }

    public Statistic(long l) {
        this.userId = l;
        this.isActive = true;
    }

    public void checkBatteriesChanged(int n) {
        if (n >= 98 && this.batteryStatus < 98) {
            ++this.changedBatteriesCount;
        }
    }

    public String toString() {
        return "Statistic{userId=" + this.userId + ", deviceId='" + this.deviceId + '\'' + ", measurementsCount=" + this.measurementsCount + ", badMeasurementsCount=" + this.badMeasurementsCount + ", changedBatteriesCount=" + this.changedBatteriesCount + ", batteryStatus=" + this.batteryStatus + ", isActive=" + this.isActive + ", firmware='" + this.firmware + '\'' + '}';
    }
}

