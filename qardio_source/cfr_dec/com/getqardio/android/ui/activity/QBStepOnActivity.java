/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothManager
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.Window
 */
package com.getqardio.android.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import com.getqardio.android.baseble.QardioBaseManager;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.activity.QBOnboardingActivity;
import com.getqardio.android.ui.fragment.OnBoardingDeviceAddressProvider;
import com.getqardio.android.ui.fragment.QBInstallFirmwareUpdateFragment;
import com.getqardio.android.ui.fragment.QBStepOnModeHostFragment;
import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment;
import com.getqardio.android.utils.ui.ActivityUtils;

public class QBStepOnActivity
extends BaseActivity
implements OnBoardingDeviceAddressProvider {
    private String deviceAddress;
    private Intent mIntentreceived;

    private boolean checkBluetooth() {
        return ((BluetoothManager)this.getSystemService("bluetooth")).getAdapter().isEnabled();
    }

    private Fragment createFragment(Intent intent) {
        if (intent == null) {
            return null;
        }
        switch (intent.getIntExtra("com.getqardio.android.extra.ACTION_ID", -1)) {
            default: {
                return null;
            }
            case 0: {
                return QBStepOnSettingHostFragment.newInstanceForSettings(intent.getBooleanExtra("com.getqardio.android.extra.HAPTIC", true));
            }
            case 4: {
                return QBStepOnSettingHostFragment.newInstanceForWifi(intent.getStringExtra("com.getqardio.android.extra.WIFIAP"), intent.getStringExtra("com.getqardio.android.extra.WIFI_PASSWORD"), intent.getBooleanExtra("com.getqardio.android.extra.WIFI_IS_SECURE", false));
            }
            case 1: {
                return QBStepOnModeHostFragment.newInstance();
            }
            case 2: {
                return QBStepOnSettingHostFragment.newInstanceForGoal(intent.getFloatExtra("com.getqardio.android.extra.TARGET", 0.0f), intent.getFloatExtra("com.getqardio.android.extra.RATE", 0.0f));
            }
            case 3: {
                return QBStepOnSettingHostFragment.newInstanceForReset();
            }
            case 5: {
                return QBStepOnSettingHostFragment.newInstanceForFirmwareUpdate(intent.getStringExtra("com.getqardio.android.extra.IP_ADDRESS"), intent.getStringExtra("com.getqardio.android.extra.FIRMWARE_VERSION"), intent.getStringExtra("com.getqardio.android.extra.FIRMWARE_DESCRIPTION"));
            }
            case 6: 
        }
        return QBInstallFirmwareUpdateFragment.newInstance(intent.getStringExtra("com.getqardio.android.extra.FIRMWARE_VERSION"), "", true);
    }

    public static Intent createStartIntentForFirmwareUpdate(Context context, String string2, String string3, String string4) {
        context = new Intent(context, QBStepOnActivity.class);
        context.putExtra("com.getqardio.android.extra.ACTION_ID", 5);
        context.putExtra("com.getqardio.android.extra.IP_ADDRESS", string2);
        context.putExtra("com.getqardio.android.extra.FIRMWARE_VERSION", string3);
        context.putExtra("com.getqardio.android.extra.FIRMWARE_DESCRIPTION", string4);
        return context;
    }

    public static Intent createStartIntentForMode(Context context) {
        context = new Intent(context, QBStepOnActivity.class);
        context.putExtra("com.getqardio.android.extra.ACTION_ID", 1);
        return context;
    }

    public static Intent createStartIntentForNoFirmwareUpdate(Context context, String string2) {
        context = new Intent(context, QBStepOnActivity.class);
        context.putExtra("com.getqardio.android.extra.ACTION_ID", 6);
        context.putExtra("com.getqardio.android.extra.FIRMWARE_VERSION", string2);
        return context;
    }

    public static Intent createStartIntentForReset(Context context) {
        context = new Intent(context, QBStepOnActivity.class);
        context.putExtra("com.getqardio.android.extra.ACTION_ID", 3);
        return context;
    }

    public static Intent createStartIntentForWifi(Context context, String string2, String string3, boolean bl) {
        context = new Intent(context, QBStepOnActivity.class);
        context.putExtra("com.getqardio.android.extra.ACTION_ID", 4);
        context.putExtra("com.getqardio.android.extra.WIFIAP", string2);
        context.putExtra("com.getqardio.android.extra.WIFI_PASSWORD", string3);
        context.putExtra("com.getqardio.android.extra.WIFI_IS_SECURE", bl);
        return context;
    }

    @Override
    protected void onActivityResult(int n, int n2, Intent intent) {
        if (n2 != -1) {
            this.finish();
        }
        switch (n) {
            default: {
                this.finish();
                return;
            }
            case 1: 
        }
        intent = this.createFragment(this.mIntentreceived);
        if (intent != null) {
            this.getFragmentManager().beginTransaction().replace(2131820778, (Fragment)intent).commit();
            return;
        }
        this.finish();
    }

    @Override
    public void onBackPressed() {
        new QardioBaseManager((Context)this).disableConfigurationMode();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        block3: {
            block4: {
                block2: {
                    super.onCreate(bundle);
                    this.setContentView(2130968799);
                    ActivityUtils.changeStatusBarColor(this, -16777216);
                    this.getToolbar().setVisibility(8);
                    this.getWindow().addFlags(128);
                    if (bundle != null) break block2;
                    this.mIntentreceived = this.getIntent();
                    if (!this.checkBluetooth()) break block3;
                    bundle = this.createFragment(this.mIntentreceived);
                    if (bundle == null) break block4;
                    this.getFragmentManager().beginTransaction().replace(2131820778, (Fragment)bundle).commit();
                }
                return;
            }
            this.finish();
            return;
        }
        this.startActivityForResult(QBOnboardingActivity.createIntentForBluetooth((Context)this), 1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        new QardioBaseManager((Context)this).stopScan();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        this.setResult(-1);
        super.onStop();
    }

    @Override
    public void setDeviceAddress(String string2) {
        this.deviceAddress = string2;
    }
}

