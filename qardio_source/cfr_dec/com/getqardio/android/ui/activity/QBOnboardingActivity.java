/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Bundle
 *  android.util.Log
 */
package com.getqardio.android.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.getqardio.android.baseble.QardioBaseManager;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.activity.QBOnboardingActivity$$Lambda$1;
import com.getqardio.android.ui.activity.QBOnboardingActivity$$Lambda$2;
import com.getqardio.android.ui.fragment.OnBoardingDeviceAddressProvider;
import com.getqardio.android.ui.fragment.QBOnboardingFragment;
import com.getqardio.android.utils.permission.PermissionUtil;
import com.getqardio.android.utils.ui.ActivityUtils;
import timber.log.Timber;

public class QBOnboardingActivity
extends BaseActivity
implements OnBoardingDeviceAddressProvider {
    private BroadcastReceiver baseErrorReceiver;
    private BroadcastReceiver bluetoothStateReceiver = new BTStateReceiver();
    private final LocalBroadcastManager broadcastManager;
    private String deviceAddress;
    private AlertDialog exitDialog;
    private boolean notToDisconnect;
    private QBOnboardingFragment onboardingFragment;

    public QBOnboardingActivity() {
        this.baseErrorReceiver = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context object, Intent intent) {
                Timber.d("baseErrorReceiver - %s", intent.getAction());
                object = intent.getAction();
                int n = -1;
                switch (((String)object).hashCode()) {
                    case -1160944514: {
                        if (!((String)object).equals("com.qardio.base.QB_ERROR")) break;
                        n = 0;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        return;
                    }
                    case 0: 
                }
                Log.e((String)"QbOnBoardActivity", (String)intent.getStringExtra("com.qardio.base.QB_ERROR_MSG"));
                QBOnboardingActivity.this.closeOnboarding(0);
            }
        };
        this.broadcastManager = LocalBroadcastManager.getInstance((Context)this);
    }

    private void cancelOnboardingIfNeeded() {
        if (this.finishAfterBluetoothTurnedOn()) {
            this.closeOnboarding(0);
        }
    }

    private void closeOnboarding(int n) {
        this.setResult(n);
        this.finish();
    }

    public static Intent createIntentForBluetooth(Context context) {
        context = QBOnboardingActivity.createStartIntent(context);
        context.putExtra("com.getqardio.android.extra.TURN_ON_BLUETOOTH", true);
        return context;
    }

    public static Intent createStartIntent(Context context) {
        return new Intent(context, QBOnboardingActivity.class);
    }

    private boolean finishAfterBluetoothTurnedOn() {
        boolean bl = false;
        Intent intent = this.getIntent();
        boolean bl2 = bl;
        if (intent != null) {
            bl2 = bl;
            if (intent.hasExtra("com.getqardio.android.extra.TURN_ON_BLUETOOTH")) {
                bl2 = bl;
                if (intent.getBooleanExtra("com.getqardio.android.extra.TURN_ON_BLUETOOTH", false)) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    static /* synthetic */ void lambda$onBackPressed$1(DialogInterface dialogInterface, int n) {
        dialogInterface.dismiss();
    }

    public String getDeviceAddress() {
        return this.deviceAddress;
    }

    /* synthetic */ void lambda$onBackPressed$0(DialogInterface dialogInterface, int n) {
        new QardioBaseManager((Context)this).disableConfigurationMode();
        this.cancelOnboardingIfNeeded();
        QBOnboardingActivity.super.onBackPressed();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = null;
        if (this.onboardingFragment != null && this.onboardingFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
            fragmentManager = this.onboardingFragment.getChildFragmentManager();
        } else if (this.getFragmentManager().getBackStackEntryCount() > 0) {
            fragmentManager = this.getFragmentManager();
        }
        if (fragmentManager != null) {
            fragmentManager.popBackStack();
            return;
        }
        if (this.getIntent().getBooleanExtra("com.getqardio.android.extra.TURN_ON_BLUETOOTH", false)) {
            this.cancelOnboardingIfNeeded();
            QBOnboardingActivity.super.onBackPressed();
            return;
        }
        this.exitDialog = new AlertDialog.Builder((Context)this, 2131493366).setTitle(this.getString(2131362375)).setMessage(this.getString(2131362376)).setPositiveButton(this.getString(2131362233), QBOnboardingActivity$$Lambda$1.lambdaFactory$(this)).setNegativeButton(this.getString(2131362189), QBOnboardingActivity$$Lambda$2.lambdaFactory$()).create();
        this.exitDialog.show();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968772);
        ActivityUtils.changeStatusBarColor(this, -16777216);
        this.getToolbar().setVisibility(8);
        this.onboardingFragment = bundle == null ? QBOnboardingFragment.newInstance() : (QBOnboardingFragment)this.getFragmentManager().getFragment(bundle, "com.getqardio.android.ONBOARDING_FRAGMENT");
        this.getFragmentManager().beginTransaction().replace(2131820778, (Fragment)this.onboardingFragment).commit();
        this.registerReceiver(this.bluetoothStateReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.exitDialog != null && this.exitDialog.isShowing()) {
            this.exitDialog.dismiss();
            this.exitDialog = null;
        }
    }

    @Override
    protected void onPause() {
        Timber.d("onPause", new Object[0]);
        if (!this.notToDisconnect) {
            new QardioBaseManager((Context)this).stopScan();
            Timber.d("Stopping service...", new Object[0]);
            this.notToDisconnect = false;
        }
        super.onPause();
        this.unregisterForActions();
    }

    @Override
    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        if (n == 1 && arrn.length > 0 && arrn[0] == 0) {
            PermissionUtil.BlePermissions.checkFineLocationPermission(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerForActions();
        Timber.d("onResume", new Object[0]);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.getFragmentManager().putFragment(bundle, "com.getqardio.android.ONBOARDING_FRAGMENT", (Fragment)this.onboardingFragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!PermissionUtil.BlePermissions.hasCourseLocationPermission(this)) {
            PermissionUtil.BlePermissions.checkCoarseLocationPermission(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            this.unregisterReceiver(this.bluetoothStateReceiver);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    public void registerForActions() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qardio.base.QB_ERROR");
        this.broadcastManager.registerReceiver(this.baseErrorReceiver, intentFilter);
    }

    @Override
    public void setDeviceAddress(String string2) {
        this.deviceAddress = string2;
    }

    public void setNotToDisconnect(boolean bl) {
        this.notToDisconnect = bl;
        Timber.d("QardioBase setNotToDisconnect", new Object[0]);
    }

    public void unregisterForActions() {
        this.broadcastManager.unregisterReceiver(this.baseErrorReceiver);
    }

    private class BTStateReceiver
    extends BroadcastReceiver {
        private BTStateReceiver() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onReceive(Context context, Intent intent) {
            boolean bl = 12 == intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
            if (bl && QBOnboardingActivity.this.finishAfterBluetoothTurnedOn()) {
                QBOnboardingActivity.this.closeOnboarding(-1);
                return;
            } else {
                if (QBOnboardingActivity.this.onboardingFragment == null) return;
                {
                    QBOnboardingActivity.this.onboardingFragment.onBluetoothStateChange(bl);
                    return;
                }
            }
        }
    }

}

