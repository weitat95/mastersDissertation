/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.app.Activity
 *  android.app.Fragment
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothManager
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.activity.AddManualMeasurementActivity;
import com.getqardio.android.ui.activity.BPMeasureActivity;
import com.getqardio.android.ui.activity.OnBoardingActivity;
import com.getqardio.android.ui.dialog.RateAppDialog;
import com.getqardio.android.ui.fragment.StartBPFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.StartBPFragment$$Lambda$10;
import com.getqardio.android.ui.fragment.StartBPFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.StartBPFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.StartBPFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.StartBPFragment$$Lambda$5;
import com.getqardio.android.ui.fragment.StartBPFragment$$Lambda$6;
import com.getqardio.android.ui.fragment.StartBPFragment$$Lambda$7;
import com.getqardio.android.ui.fragment.StartBPFragment$$Lambda$8;
import com.getqardio.android.ui.fragment.StartBPFragment$$Lambda$9;
import com.getqardio.android.ui.widget.CustomSwitch;
import com.getqardio.android.ui.widget.StartButton;
import com.getqardio.android.utils.ChooseDeviceUtils;
import com.getqardio.android.utils.RateAppManager;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.analytics.BpAddManualMeasurementsAnalyticsTracker;
import com.getqardio.android.utils.logger.QardioLogger;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.wizard.OnboardingPrefsManager;
import com.getqardio.shared.utils.SingleEvent;
import com.qardio.ble.bpcollector.mobiledevice.BLEStatus;
import com.qardio.ble.bpcollector.mobiledevice.MobileDeviceFactory;
import timber.log.Timber;

public class StartBPFragment
extends Fragment {
    private Button addManualMeasurement;
    private ValueAnimator animVisitorMode;
    private BatteryInfoReceiver batteryInfoReceiver;
    private TextView batteryLevelView;
    private BTStateReceiver btStateReceiver;
    private DeviceInfoReceiver deviceInfoReceiver;
    private DeviceStateReceiver deviceStateReceiver = new DeviceStateReceiver();
    private Handler handler;
    private QardioLogger logger;
    private View rootView;
    private SingleEvent singleEvent;
    private StartButton startButton;
    private boolean statisticSynced;
    private CustomSwitch visitorMode;
    private int visitorModeLabelHeight;
    private View visitorModeLabelLayout;
    private TextView visitorModeLabelText;
    private TextView visitorModeTitle;

    public StartBPFragment() {
        this.btStateReceiver = new BTStateReceiver();
        this.deviceInfoReceiver = new DeviceInfoReceiver();
        this.batteryInfoReceiver = new BatteryInfoReceiver();
        this.singleEvent = new SingleEvent();
    }

    private void checkBLERadioEnabled() {
        new Handler().postDelayed(StartBPFragment$$Lambda$7.lambdaFactory$(this), 500L);
    }

    private void hideVisitorModeLabel(boolean bl) {
        if (!bl) {
            ViewGroup.LayoutParams layoutParams = this.visitorModeLabelLayout.getLayoutParams();
            layoutParams.height = 0;
            this.visitorModeLabelLayout.setAlpha(0.0f);
            this.visitorModeLabelLayout.setLayoutParams(layoutParams);
            return;
        }
        ViewGroup.LayoutParams layoutParams = this.visitorModeLabelLayout.getLayoutParams();
        if (this.animVisitorMode != null) {
            this.animVisitorMode.cancel();
        }
        float f = 1.0f * (float)layoutParams.height / (float)this.visitorModeLabelHeight;
        this.animVisitorMode = ValueAnimator.ofFloat((float[])new float[]{f, 0.0f});
        this.animVisitorMode.addUpdateListener(StartBPFragment$$Lambda$6.lambdaFactory$(this, layoutParams));
        this.animVisitorMode.setDuration((long)((int)(300.0f * f)));
        this.animVisitorMode.start();
    }

    private void init(View view) {
        Typeface typeface = Typeface.create((String)"sans-serif-light", (int)0);
        ((TextView)view.findViewById(2131821390)).setTypeface(typeface);
        view.findViewById(2131821391).setOnClickListener(StartBPFragment$$Lambda$3.lambdaFactory$(this));
        this.addManualMeasurement = (Button)view.findViewById(2131821387);
        this.addManualMeasurement.setTypeface(typeface);
        this.addManualMeasurement.setOnClickListener(StartBPFragment$$Lambda$4.lambdaFactory$(this));
    }

    private boolean isBluetoothOn() {
        BluetoothAdapter bluetoothAdapter = ((BluetoothManager)this.getActivity().getSystemService("bluetooth")).getAdapter();
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    private boolean isFromNotification() {
        Bundle bundle = this.getArguments();
        return bundle != null && bundle.containsKey("com.getqardio.android.argument.FROM_NOTIFICATION") && bundle.getBoolean("com.getqardio.android.argument.FROM_NOTIFICATION");
    }

    static /* synthetic */ void lambda$null$6(BluetoothAdapter bluetoothAdapter, View view) {
        bluetoothAdapter.enable();
    }

    public static StartBPFragment newInstance(boolean bl) {
        StartBPFragment startBPFragment = new StartBPFragment();
        Bundle bundle = new Bundle(1);
        bundle.putBoolean("com.getqardio.android.argument.FROM_NOTIFICATION", bl);
        startBPFragment.setArguments(bundle);
        return startBPFragment;
    }

    private void onBTStateChanged(int n, int n2) {
        if (n == 12) {
            this.logger.logBluetoothOnEvent();
            this.onDeviceStatusChanged(BLEStatus.getInstance((Context)this.getActivity()).getBleStatus());
            return;
        }
        this.logger.logBluetoothOffEvent();
        this.onDeviceStatusChanged(0);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onDeviceStatusChanged(int n) {
        int n2 = 0;
        Timber.d("onDeviceStatusChanged: %d", n);
        boolean bl = false;
        this.batteryLevelView.setVisibility(8);
        boolean bl2 = bl;
        switch (n) {
            default: {
                Timber.e("onDeviceStatusChanged: status %d was not processed", n);
                bl2 = bl;
                break;
            }
            case 4: 
            case 11: {
                this.onDisconnectedEvent();
                bl2 = bl;
                break;
            }
            case 2: {
                this.onNeedPairingEvent();
                bl2 = bl;
                break;
            }
            case 3: {
                this.onNeedResetEvent();
                bl2 = bl;
            }
            case 0: 
            case 44: {
                break;
            }
            case 66: {
                this.onReadyTakeMeasurementEvent();
                bl2 = true;
                this.setQardioArmKnown();
            }
        }
        this.startButton.setButtonEnabled(bl2);
        Button button = this.addManualMeasurement;
        n = n2;
        if (bl2) {
            n = 4;
        }
        button.setVisibility(n);
    }

    private void onDisconnectedEvent() {
        this.scanAndConnect();
        this.logger.logDisconnectedEvent();
    }

    private void onGetStatistic() {
        Activity activity = this.getActivity();
        if (activity != null) {
            LocalBroadcastManager.getInstance((Context)activity).registerReceiver(this.deviceInfoReceiver, new IntentFilter("DEVICE_INFO"));
            MobileDeviceFactory.getSerialNumber((Context)activity);
        }
    }

    private void onNeedPairingEvent() {
        this.logger.logNeedPairingEvent();
    }

    private void onNeedResetEvent() {
        this.logger.logNeedResetEvent();
    }

    private void onReadyTakeMeasurementEvent() {
        Activity activity = this.getActivity();
        if (activity != null) {
            MobileDeviceFactory.getBatteryStatus((Context)activity);
        }
        if (!this.statisticSynced) {
            this.onGetStatistic();
        }
    }

    private void scanAndConnect() {
        Activity activity = this.getActivity();
        if (activity != null) {
            MobileDeviceFactory.scanAndConnect((Context)activity);
        }
    }

    private void setQardioArmKnown() {
        Long l = CustomApplication.getApplication().getCurrentUserId();
        Activity activity = this.getActivity();
        if (l != null && activity != null) {
            ChooseDeviceUtils.setQardioArmKnown((Context)activity, l, true);
        }
    }

    private void setVisitorModeLabel(boolean bl, boolean bl2) {
        if (bl) {
            this.showVisitorModeLabel(bl2);
            return;
        }
        this.hideVisitorModeLabel(bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showVisitorModeLabel(boolean bl) {
        if (!bl) {
            ViewGroup.LayoutParams layoutParams = this.visitorModeLabelLayout.getLayoutParams();
            layoutParams.height = this.visitorModeLabelHeight;
            this.visitorModeLabelLayout.setAlpha(1.0f);
            this.visitorModeLabelLayout.setLayoutParams(layoutParams);
            return;
        } else {
            ViewGroup.LayoutParams layoutParams = this.visitorModeLabelLayout.getLayoutParams();
            if (layoutParams.height == this.visitorModeLabelHeight) return;
            {
                if (this.animVisitorMode != null) {
                    this.animVisitorMode.cancel();
                }
                float f = (float)layoutParams.height * 1.0f / (float)this.visitorModeLabelHeight;
                this.animVisitorMode = ValueAnimator.ofFloat((float[])new float[]{f, 1.0f});
                this.animVisitorMode.addUpdateListener(StartBPFragment$$Lambda$5.lambdaFactory$(this, layoutParams));
                this.animVisitorMode.setDuration((long)((int)((1.0f - f) * 300.0f)));
                this.animVisitorMode.start();
                return;
            }
        }
    }

    private void startMeasurementActivity() {
        this.singleEvent.run(StartBPFragment$$Lambda$8.lambdaFactory$(this));
    }

    private void startOnBoarding() {
        this.startActivity(OnBoardingActivity.createStartIntent((Context)this.getActivity(), false));
    }

    private void updateBatteryLevelView(int n) {
        block5: {
            block4: {
                if (!this.isAdded()) break block4;
                if (n >= 60) break block5;
                this.updateBatteryLevelView(2131361879, 2130837736);
            }
            return;
        }
        if (n < 67) {
            this.updateBatteryLevelView(2131361880, 2130837737);
            return;
        }
        this.batteryLevelView.setVisibility(8);
    }

    private void updateBatteryLevelView(int n, int n2) {
        this.batteryLevelView.setVisibility(0);
        this.batteryLevelView.setText(n);
        Drawable drawable2 = this.getResources().getDrawable(n2);
        this.batteryLevelView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable2, null);
    }

    /* synthetic */ void lambda$checkBLERadioEnabled$7() {
        BluetoothAdapter bluetoothAdapter;
        if (this.getActivity() != null && !this.getActivity().isFinishing() && (bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()) != null && bluetoothAdapter.getState() == 10) {
            Snackbar.make(this.getActivity().findViewById(2131821383), "Enable Bluetooth", -2).setAction("ENABLE", StartBPFragment$$Lambda$10.lambdaFactory$(bluetoothAdapter)).show();
        }
    }

    /* synthetic */ void lambda$hideVisitorModeLabel$5(ViewGroup.LayoutParams layoutParams, ValueAnimator valueAnimator) {
        float f = ((Float)valueAnimator.getAnimatedValue()).floatValue();
        layoutParams.height = (int)((float)this.visitorModeLabelHeight * f);
        this.visitorModeLabelLayout.setLayoutParams(layoutParams);
        this.visitorModeLabelLayout.setAlpha(f);
    }

    /* synthetic */ void lambda$init$2(View view) {
        this.startActivity(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"));
    }

    /* synthetic */ void lambda$init$3(View view) {
        Long l;
        view = this.getActivity();
        if (view != null && (l = CustomApplication.getApplication().getCurrentUserId()) != null) {
            BpAddManualMeasurementsAnalyticsTracker.trackAddManualMeasurementClick((Context)view, BpAddManualMeasurementsAnalyticsTracker.Screen.START);
            AddManualMeasurementActivity.start((Context)view, l, 1);
        }
    }

    /* synthetic */ void lambda$null$8() {
        if (this.visitorMode != null) {
            this.visitorMode.setChecked(false);
        }
    }

    /* synthetic */ void lambda$onActivityCreated$0(CompoundButton compoundButton, boolean bl) {
        this.setVisitorModeLabel(bl, true);
    }

    /* synthetic */ void lambda$onActivityCreated$1(View view) {
        if (this.startButton.isButtonEnabled()) {
            this.startMeasurementActivity();
            return;
        }
        this.startOnBoarding();
    }

    /* synthetic */ void lambda$showVisitorModeLabel$4(ViewGroup.LayoutParams layoutParams, ValueAnimator valueAnimator) {
        float f = ((Float)valueAnimator.getAnimatedValue()).floatValue();
        layoutParams.height = (int)((float)this.visitorModeLabelHeight * f);
        this.visitorModeLabelLayout.setLayoutParams(layoutParams);
        this.visitorModeLabelLayout.setAlpha(f);
    }

    /* synthetic */ void lambda$startMeasurementActivity$9() {
        Activity activity = this.getActivity();
        if (activity != null) {
            this.startActivity(BPMeasureActivity.getStartIntent((Context)activity, this.visitorMode.isChecked(), this.isFromNotification()));
            this.handler.postDelayed(StartBPFragment$$Lambda$9.lambdaFactory$(this), 500L);
        }
    }

    public void onActivityCreated(Bundle object) {
        super.onActivityCreated((Bundle)object);
        object = ActivityUtils.getActionBar(this.getActivity());
        AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "Start Blood pressure measurement");
        ((ActionBar)object).setTitle(2131361885);
        ((ActionBar)object).setDisplayShowCustomEnabled(true);
        this.visitorModeLabelHeight = this.getResources().getDimensionPixelOffset(2131427695);
        this.visitorMode = (CustomSwitch)this.rootView.findViewById(2131821204);
        this.visitorModeLabelLayout = this.rootView.findViewById(2131821385);
        this.visitorModeTitle = (TextView)this.rootView.findViewById(2131821384);
        this.visitorModeTitle.setTypeface(Typeface.create((String)"sans-serif-light", (int)0));
        this.visitorModeLabelText = (TextView)this.rootView.findViewById(2131821386);
        this.visitorModeLabelText.setTypeface(Typeface.create((String)"sans-serif-light", (int)0));
        this.visitorMode.setChecked(false);
        this.setVisitorModeLabel(false, false);
        this.visitorMode.setOnCheckedChangeListener(StartBPFragment$$Lambda$1.lambdaFactory$(this));
        this.startButton = (StartButton)this.rootView.findViewById(2131821388);
        this.startButton.setOnClickListener(StartBPFragment$$Lambda$2.lambdaFactory$(this));
        this.batteryLevelView = (TextView)this.rootView.findViewById(2131821389);
        if (!OnboardingPrefsManager.isOnboardingDiscovered()) {
            this.startActivity(OnBoardingActivity.createStartIntent((Context)this.getActivity(), false));
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.singleEvent.setInterval(1000L);
        this.logger = new QardioLogger((Context)this.getActivity());
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menu2.clear();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968835, viewGroup, false);
        this.setHasOptionsMenu(true);
        this.init(this.rootView);
        this.handler = new Handler();
        return this.rootView;
    }

    public void onDestroy() {
        super.onDestroy();
        this.logger.close();
    }

    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).unregisterReceiver(this.deviceInfoReceiver);
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance((Context)this.getActivity());
        this.getActivity().unregisterReceiver((BroadcastReceiver)this.btStateReceiver);
        localBroadcastManager.unregisterReceiver(this.deviceStateReceiver);
        localBroadcastManager.unregisterReceiver(this.batteryInfoReceiver);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onResume() {
        super.onResume();
        if (this.isBluetoothOn()) {
            this.onDeviceStatusChanged(BLEStatus.getInstance((Context)this.getActivity()).getBleStatus());
        } else {
            this.startButton.setButtonEnabled(false);
            this.addManualMeasurement.setVisibility(0);
        }
        Object object = LocalBroadcastManager.getInstance((Context)this.getActivity());
        ((LocalBroadcastManager)object).registerReceiver(this.deviceStateReceiver, new IntentFilter("com.qardio.bleservice.Notifications.DEVICE_STATUSES"));
        ((LocalBroadcastManager)object).registerReceiver(this.batteryInfoReceiver, new IntentFilter("DEVICE_INFO"));
        this.getActivity().registerReceiver((BroadcastReceiver)this.btStateReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        object = CustomApplication.getApplication().getRateAppManager((Context)this.getActivity());
        if (((RateAppManager)object).hasSuccessfulMeasurement() && ((RateAppManager)object).shouldShowRateDialog()) {
            RateAppDialog.newInstance((Context)this.getActivity()).show();
        }
        ((RateAppManager)object).setHasSuccessfulMeasurement(false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.checkBLERadioEnabled();
    }

    private class BTStateReceiver
    extends BroadcastReceiver {
        private BTStateReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            int n = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
            int n2 = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", 10);
            StartBPFragment.this.onBTStateChanged(n, n2);
        }
    }

    private class BatteryInfoReceiver
    extends BroadcastReceiver {
        private BatteryInfoReceiver() {
        }

        public void onReceive(Context object, Intent object2) {
            if (object2.getAction().equals("DEVICE_INFO") && StartBPFragment.this.isAdded()) {
                object = object2.getStringExtra("DEVICE_INFO_TYPE");
                object2 = object2.getStringExtra("DEVICE_INFO_VALUE");
                if (((String)object).equals("BATTERY_STATUS_VALUE")) {
                    int n = Integer.parseInt((String)object2);
                    StartBPFragment.this.updateBatteryLevelView(n);
                }
            }
        }
    }

    private class DeviceInfoReceiver
    extends BroadcastReceiver {
        private DeviceInfoReceiver() {
        }

        public void onReceive(Context context, Intent object) {
            if ("DEVICE_INFO".equals(object.getAction()) && "SERIAL_NUMBER_VALUE".equals(object.getStringExtra("DEVICE_INFO_TYPE"))) {
                LocalBroadcastManager.getInstance(context).unregisterReceiver(StartBPFragment.this.deviceInfoReceiver);
                StartBPFragment.this.statisticSynced = true;
                object = object.getStringExtra("DEVICE_INFO_VALUE");
                StartBPFragment.this.logger.logSerialNumberEvent((String)object);
                Long l = CustomApplication.getApplication().getCurrentUserId();
                if (l != null) {
                    DataHelper.StatisticHelper.requestUpdateStatistic(context, l, (String)object);
                    DataHelper.StatisticHelper.requestGetStatistic(context, l, (String)object);
                }
            }
        }
    }

    private class DeviceStateReceiver
    extends BroadcastReceiver {
        private DeviceStateReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            int n = intent.getIntExtra("DEVICE_STATUS", 0);
            StartBPFragment.this.onDeviceStatusChanged(n);
        }
    }

}

