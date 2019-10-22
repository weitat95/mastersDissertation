/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shealth;

import com.samsung.android.sdk.healthdata.HealthPermissionManager;

public enum ShealthPermissionKeys {
    WRITE(HealthPermissionManager.PermissionType.WRITE),
    READ(HealthPermissionManager.PermissionType.READ);

    HealthPermissionManager.PermissionType permissionType;

    private ShealthPermissionKeys(HealthPermissionManager.PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    public HealthPermissionManager.PermissionKey getBpPermissionKey() {
        return new HealthPermissionManager.PermissionKey("com.samsung.health.blood_pressure", this.permissionType);
    }

    public HealthPermissionManager.PermissionKey getWeightPermissionKey() {
        return new HealthPermissionManager.PermissionKey("com.samsung.health.weight", this.permissionType);
    }
}

