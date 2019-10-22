/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.bluetooth.BluetoothAdapter
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.graphics.Bitmap
 *  android.graphics.Typeface
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.getqardio.android.utils.analytics.AnalyticsDevice;
import com.getqardio.android.utils.analytics.GeneralAnalyticsTracker;
import com.getqardio.android.utils.wizard.OnBoardingWizard;
import com.qardio.ble.bpcollector.mobiledevice.MobileDeviceFactory;

public class OnBoardingFragment
extends Fragment
implements View.OnClickListener,
OnBoardingWizard.OnStateChangedListener,
OnBoardingWizard.OnWizardFinishedListener {
    private Activity activity;
    private BTStateReceiver btStateReceiver = new BTStateReceiver();
    private DeviceStateReceiver deviceStateReceiver = new DeviceStateReceiver();
    private ImageView imageView;
    private TextView messageView;
    private Button nextButton;
    private View rootView;
    private Button switchOnBluetoothButton;
    private TextView titleView;
    private OnBoardingWizard wizard;

    private void hideSwitchOnBluetooth() {
        this.nextButton.setVisibility(0);
        this.switchOnBluetoothButton.setVisibility(8);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void initWizard() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean bl = bluetoothAdapter != null && bluetoothAdapter.getState() == 12;
        if (this.wizard == null) {
            void var2_3;
            this.wizard = new OnBoardingWizard((Context)this.getActivity(), this);
            this.wizard.setOnWizardFinishedListener(this);
            if (this.getArguments().containsKey("com.getqardio.android.arguments.LOW_BATTERY")) {
                OnBoardingWizard.InitialState initialState = OnBoardingWizard.InitialState.LOW_BATTERY;
            } else if (this.getArguments().getBoolean("com.getqardio.android.arguments.IS_OUTRO", false)) {
                OnBoardingWizard.InitialState initialState = OnBoardingWizard.InitialState.OUTRO;
            } else {
                OnBoardingWizard.InitialState initialState = OnBoardingWizard.InitialState.WIZARD;
            }
            this.wizard.init((OnBoardingWizard.InitialState)var2_3, bl);
        }
        if (!bl) {
            this.onDeviceStatusChanged(0);
            return;
        }
        this.onDeviceStatusChanged(1);
        this.scanAndConnect();
    }

    public static Fragment newInstance(boolean bl) {
        OnBoardingFragment onBoardingFragment = new OnBoardingFragment();
        Bundle bundle = new Bundle(1);
        bundle.putBoolean("com.getqardio.android.arguments.IS_OUTRO", bl);
        onBoardingFragment.setArguments(bundle);
        return onBoardingFragment;
    }

    public static Fragment newLowBatteryInstance() {
        OnBoardingFragment onBoardingFragment = new OnBoardingFragment();
        Bundle bundle = new Bundle(1);
        bundle.putBoolean("com.getqardio.android.arguments.LOW_BATTERY", true);
        onBoardingFragment.setArguments(bundle);
        return onBoardingFragment;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onDeviceStatusChanged(int n) {
        switch (n) {
            case 0: {
                if (this.wizard.getInitialState() == OnBoardingWizard.InitialState.WIZARD) {
                    this.showSwitchOnBluetooth();
                    this.wizard.setStateBluetoothOff();
                    return;
                }
            }
            default: {
                return;
            }
            case 1: {
                this.hideSwitchOnBluetooth();
                this.wizard.setStateBluetoothOn();
                return;
            }
            case 3: {
                this.wizard.setStateNeedReset();
                return;
            }
            case 66: {
                GeneralAnalyticsTracker.trackPairingCompleted((Context)this.getActivity(), AnalyticsDevice.QA);
                this.wizard.setStateReady();
                return;
            }
            case 5: {
                GeneralAnalyticsTracker.trackPairingStarted((Context)this.getActivity(), AnalyticsDevice.QA);
                this.wizard.setStatePaired(false);
                return;
            }
            case 9: 
        }
        this.wizard.setStatePaired(true);
    }

    private void scanAndConnect() {
        MobileDeviceFactory.scanAndConnect((Context)this.activity);
    }

    private void showSwitchOnBluetooth() {
        this.switchOnBluetoothButton.setVisibility(0);
        this.nextButton.setVisibility(8);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public void onClick(View view) {
        this.wizard.changeState();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968773, viewGroup, false);
        return this.rootView;
    }

    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }

    public void onPause() {
        super.onPause();
        this.getActivity().unregisterReceiver((BroadcastReceiver)this.btStateReceiver);
        LocalBroadcastManager.getInstance((Context)this.activity).unregisterReceiver(this.deviceStateReceiver);
    }

    public void onResume() {
        super.onResume();
        this.getActivity().registerReceiver((BroadcastReceiver)this.btStateReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        LocalBroadcastManager.getInstance((Context)this.activity).registerReceiver(this.deviceStateReceiver, new IntentFilter("com.qardio.bleservice.Notifications.DEVICE_STATUSES"));
        this.initWizard();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onStateChanged(int n, int n2, int n3, int n4) {
        this.titleView.setText(n);
        if (n2 != -1) {
            this.imageView.setImageResource(n2);
        } else {
            this.imageView.setImageBitmap(null);
        }
        this.messageView.setText(n3);
        this.nextButton.setText(n4);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        view = Typeface.create((String)"sans-serif-light", (int)0);
        this.titleView = (TextView)this.rootView.findViewById(2131821180);
        this.titleView.setTypeface((Typeface)view);
        this.imageView = (ImageView)this.rootView.findViewById(2131821181);
        this.messageView = (TextView)this.rootView.findViewById(2131821182);
        this.messageView.setTypeface((Typeface)view);
        this.switchOnBluetoothButton = (Button)this.rootView.findViewById(2131821185);
        this.switchOnBluetoothButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                view = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
                OnBoardingFragment.this.startActivity((Intent)view);
            }
        });
        this.nextButton = (Button)this.rootView.findViewById(2131821184);
        this.nextButton.setOnClickListener((View.OnClickListener)this);
    }

    @Override
    public void onWizardFinished() {
        this.activity.finish();
    }

    private class BTStateReceiver
    extends BroadcastReceiver {
        private BTStateReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10) != 12) {
                OnBoardingFragment.this.onDeviceStatusChanged(0);
                return;
            }
            OnBoardingFragment.this.onDeviceStatusChanged(1);
            OnBoardingFragment.this.scanAndConnect();
        }
    }

    private class DeviceStateReceiver
    extends BroadcastReceiver {
        private DeviceStateReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            int n = intent.getIntExtra("DEVICE_STATUS", 0);
            OnBoardingFragment.this.onDeviceStatusChanged(n);
        }
    }

}

