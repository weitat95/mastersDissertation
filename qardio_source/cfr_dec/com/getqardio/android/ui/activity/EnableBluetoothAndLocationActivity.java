/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 */
package com.getqardio.android.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import com.getqardio.android.device_related_services.map.LocationServiceSettingsChecker;
import com.getqardio.android.device_related_services.map.LocationServiceSettingsCheckerImpl;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.activity.EnableBluetoothAndLocationActivity$$Lambda$1;

public class EnableBluetoothAndLocationActivity
extends BaseActivity {
    private int counter;
    private LocationServiceSettingsChecker locationServiceSettingsChecker = LocationServiceSettingsCheckerImpl.getInstance();

    private void explainUserWhyTheAppNeedLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this, 2131493366);
        builder.setTitle(2131362089);
        builder.setMessage(2131362088).setPositiveButton(2131362087, EnableBluetoothAndLocationActivity$$Lambda$1.lambdaFactory$(this));
        builder.create().show();
    }

    public void checkLocationSettings() {
        if (!this.locationServiceSettingsChecker.isLocationAvailable((Context)this)) {
            this.explainUserWhyTheAppNeedLocationDialog();
        }
    }

    /* synthetic */ void lambda$explainUserWhyTheAppNeedLocationDialog$0(DialogInterface dialogInterface, int n) {
        this.locationServiceSettingsChecker.enableLocation(this, 1111);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onActivityResult(int n, int n2, Intent intent) {
        switch (n) {
            default: {
                return;
            }
            case 1111: 
        }
        switch (n2) {
            case -1: {
                return;
            }
            default: {
                return;
            }
            case 0: 
        }
        ++this.counter;
        if (this.counter >= 3) return;
        this.explainUserWhyTheAppNeedLocationDialog();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.locationServiceSettingsChecker.stopChecking();
    }
}

