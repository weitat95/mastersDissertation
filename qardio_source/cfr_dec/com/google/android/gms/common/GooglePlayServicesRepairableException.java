/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.common;

import android.content.Intent;
import com.google.android.gms.common.UserRecoverableException;

public class GooglePlayServicesRepairableException
extends UserRecoverableException {
    private final int zzecj;

    public GooglePlayServicesRepairableException(int n, String string2, Intent intent) {
        super(string2, intent);
        this.zzecj = n;
    }
}

