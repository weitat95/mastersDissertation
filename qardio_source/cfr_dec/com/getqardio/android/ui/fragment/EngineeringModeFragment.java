/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.getqardio.android.ui.activity.EngineeringConsoleActivity;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.qardio.ble.bpcollector.mobiledevice.BLEStatus;
import com.qardio.ble.bpcollector.mobiledevice.MobileDeviceFactory;
import timber.log.Timber;

public class EngineeringModeFragment
extends Fragment {
    private DeviceStateReceiver deviceStateReceiver = new DeviceStateReceiver();
    private Button enableDisplayMode;
    private boolean isDisplayModeEnabled = false;
    private Button showEngineeringConsole;
    private TextView statusView;

    private void init(View view) {
        ((TextView)view.findViewById(2131821042)).setText((CharSequence)"https://api.getqardio.com");
        this.showEngineeringConsole = (Button)view.findViewById(2131821043);
        this.showEngineeringConsole.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MobileDeviceFactory.enableEngineeringMode((Context)EngineeringModeFragment.this.getActivity());
                EngineeringModeFragment.this.startActivity(EngineeringConsoleActivity.getStartIntent((Context)EngineeringModeFragment.this.getActivity(), EngineeringModeFragment.this.isDisplayModeEnabled));
            }
        });
        this.enableDisplayMode = (Button)view.findViewById(2131821044);
        this.setDisplayModeButtonText();
        this.enableDisplayMode.setOnClickListener(new View.OnClickListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View object) {
                EngineeringModeFragment engineeringModeFragment = EngineeringModeFragment.this;
                boolean bl = !EngineeringModeFragment.this.isDisplayModeEnabled;
                engineeringModeFragment.isDisplayModeEnabled = bl;
                EngineeringModeFragment.this.setDisplayModeButtonText();
            }
        });
        this.statusView = (TextView)view.findViewById(2131821045);
    }

    private void onDeviceStatusChanged(int n) {
        Timber.d("EngineeringModeFragment onDeviceStatusChanged: %d", n);
        switch (n) {
            default: {
                Timber.d("onDeviceStatusChanged: status %d was not processed", n);
                return;
            }
            case 0: {
                this.statusView.setText((CharSequence)"Status: BLE_OFF");
                return;
            }
            case 11: {
                this.statusView.setText((CharSequence)"Status: INIT");
                return;
            }
            case 4: {
                this.statusView.setText((CharSequence)"Status: DISCONNECTED");
                return;
            }
            case 2: {
                this.statusView.setText((CharSequence)"Status: NEED_PAIRING");
                return;
            }
            case 3: {
                this.statusView.setText((CharSequence)"Status: NEED_RESET");
                return;
            }
            case 66: {
                this.statusView.setText((CharSequence)"Status: READY_TO_TAKE_MEASUREMENT");
                return;
            }
            case 44: {
                this.statusView.setText((CharSequence)"Status: CONNECTED");
                return;
            }
            case 5: {
                this.statusView.setText((CharSequence)"Status: PAIRING");
                return;
            }
            case 9: 
        }
        this.statusView.setText((CharSequence)"Status: PAIRED");
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setDisplayModeButtonText() {
        Button button = this.enableDisplayMode;
        String string2 = this.isDisplayModeEnabled ? this.getString(2131362497) : this.getString(2131362492);
        button.setText((CharSequence)this.getString(2131362493, new Object[]{string2}));
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131361916);
        this.setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menu2.clear();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968701, viewGroup, false);
        this.init((View)layoutInflater);
        return layoutInflater;
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).unregisterReceiver(this.deviceStateReceiver);
    }

    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).registerReceiver(this.deviceStateReceiver, new IntentFilter("com.qardio.bleservice.Notifications.DEVICE_STATUSES"));
        this.onDeviceStatusChanged(BLEStatus.getInstance((Context)this.getActivity()).getBleStatus());
    }

    private class DeviceStateReceiver
    extends BroadcastReceiver {
        private DeviceStateReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            int n = intent.getIntExtra("DEVICE_STATUS", 0);
            EngineeringModeFragment.this.onDeviceStatusChanged(n);
        }
    }

}

