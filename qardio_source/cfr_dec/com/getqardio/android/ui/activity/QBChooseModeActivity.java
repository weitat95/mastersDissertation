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
 *  android.view.MenuItem
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
import android.view.MenuItem;
import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.fragment.AbstractQBChooseModeListFragment;
import com.getqardio.android.ui.fragment.QBChooseModeListFragment;
import com.getqardio.android.utils.ui.ActivityUtils;
import java.io.Serializable;
import java.util.HashMap;

public class QBChooseModeActivity
extends BaseActivity
implements AbstractQBChooseModeListFragment.Callback {
    private void checkBluetooth() {
        if (!((BluetoothManager)this.getSystemService("bluetooth")).getAdapter().isEnabled()) {
            this.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 1);
        }
    }

    public static Intent createStartIntent(Context context) {
        return new Intent(context, QBChooseModeActivity.class);
    }

    @Override
    protected void onActivityResult(int n, int n2, Intent intent) {
        if (n == 1 && n2 != -1) {
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (this.getFragmentManager().getBackStackEntryCount() != 0) {
            this.getFragmentManager().popBackStack();
            return;
        }
        this.finish();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968662);
        ActivityUtils.changeStatusBarColor(this, -16777216);
        this.getToolbar().setVisibility(8);
        if (bundle == null) {
            this.getFragmentManager().beginTransaction().replace(2131820778, (Fragment)QBChooseModeListFragment.newInstance()).commit();
        }
    }

    @Override
    public void onModeItemClicked(QardioBaseDevice.BaseMode baseMode) {
    }

    @Override
    public void onModeSaved(QardioBaseDevice.BaseMode baseMode, HashMap<String, Object> hashMap) {
        Intent intent = new Intent();
        intent.putExtra("com.getqardio.android.extra.SELECTED_MODE", (Serializable)((Object)baseMode));
        if (hashMap != null && baseMode == QardioBaseDevice.BaseMode.MODE_PREGNANCY) {
            intent.putExtra("com.getqardio.android.extra.HIDE_WEIGHT", (Serializable)((Boolean)hashMap.get("com.getqardio.android.extra.HIDE_WEIGHT")));
        }
        this.setResult(-1, intent);
        this.finish();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                do {
                    return super.onOptionsItemSelected(menuItem);
                    break;
                } while (true);
            }
            case 16908332: 
        }
        this.onBackPressed();
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onStart() {
        this.checkBluetooth();
        super.onStart();
    }
}

