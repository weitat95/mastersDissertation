/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Handler
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.baseble.QardioBaseManager;
import com.getqardio.android.datamodel.FirmwareDescription;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.FirmwareUpdateHelper;
import com.getqardio.android.ui.WeightMeasurementCallback;
import com.getqardio.android.ui.activity.QBOnboardingActivity;
import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment;
import com.getqardio.android.ui.fragment.ReminderListFragment;
import com.getqardio.android.ui.fragment.WeightFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.WeightFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.WeightFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.WeightFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.WeightLastMeasurementFragment;
import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment;
import com.getqardio.android.utils.permission.PermissionUtil;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class WeightFragment
extends Fragment
implements WeightMeasurementCallback {
    private static volatile boolean launchedAfterOnboardingSucceeded;
    BroadcastReceiver baseReceiver;
    private BroadcastReceiver bleStateReceiver;
    private LocalBroadcastManager broadcastManager;
    private Handler handler = new Handler();
    private int heightUnit;
    private View historyButton;
    private ImageView historyImage;
    private TextView historyText;
    private boolean isAfterAttach;
    private boolean isBluetoothCancelled = false;
    private boolean isPregnancyFragmentShown;
    private boolean onBoardingCanceled = false;
    private Profile profile;
    private QardioBaseManager qardioBaseManager;
    private boolean readingMeasurement = false;
    private View remindersButton;
    private ReminderListFragment remindersFragment;
    private ImageView remindersImage;
    private TextView remindersText;
    private View rootView;
    private View startButton;
    private ImageView startImage;
    private TextView startText;
    private String userName;
    private int weightUnit;

    public WeightFragment() {
        this.bleStateReceiver = new BroadcastReceiver(){

            public void onReceive(Context context, Intent intent) {
                if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction()) && intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1) == 12) {
                    WeightFragment.this.checkAndProceed();
                }
            }
        };
        this.baseReceiver = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context object, Intent intent) {
                Timber.d("onReceive action - %s", intent.getAction());
                object = intent.getAction();
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 1424235813: {
                        if (!((String)object).equals("com.qardio.base.CONNECTED")) break;
                        n = 0;
                        break;
                    }
                    case 1712737337: {
                        if (!((String)object).equals("com.qardio.base.DEVICE_SERIAL")) break;
                        n = 1;
                        break;
                    }
                    case -165661596: {
                        if (!((String)object).equals("com.qardio.base.SOFTWARE_VERSION")) break;
                        n = 2;
                        break;
                    }
                    case -53910355: {
                        if (!((String)object).equals("com.qardio.base.STATE")) break;
                        n = 3;
                        break;
                    }
                    case 421757567: {
                        if (!((String)object).equals("com.qardio.base.DISCONNECTED")) break;
                        n = 4;
                        break;
                    }
                    case -1160944514: {
                        if (!((String)object).equals("com.qardio.base.QB_ERROR")) break;
                        n = 5;
                        break;
                    }
                    case 814720241: {
                        if (!((String)object).equals("com.qardio.base.QB_CONNECTION_ERROR")) break;
                        n = 6;
                        break;
                    }
                }
                switch (n) {
                    case 0: {
                        if (WeightFragment.this.getActivity() != null) {
                            WeightFragment.this.getActivity().getWindow().addFlags(128);
                        }
                        WeightFragment.this.qardioBaseManager.readState();
                        WeightFragment.this.qardioBaseManager.enableStateNotifications();
                        WeightFragment.this.qardioBaseManager.readSerialNumber();
                        WeightFragment.this.readingMeasurement = false;
                        return;
                    }
                    case 1: {
                        WeightFragment.this.qardioBaseManager.readSoftwareVersion();
                        return;
                    }
                    case 2: {
                        object = intent.getStringExtra("com.qardio.base.DATA");
                        Timber.d("version - %s", object);
                        if ((object = FirmwareUpdateHelper.parseBaseVersion((String)object)) != null && ((FirmwareDescription)object).isFirmwareNewerOrEqual18()) {
                            WeightFragment.this.qardioBaseManager.enableEngineeringNotifications();
                            Timber.d("select userid- %d, email - %s", WeightFragment.this.profile.getEmail().hashCode(), WeightFragment.this.profile.getEmail());
                            WeightFragment.this.qardioBaseManager.selectUser(WeightFragment.this.profile.getEmail().hashCode());
                            return;
                        }
                    }
                    default: {
                        return;
                    }
                    case 3: {
                        n = intent.getIntExtra("com.qardio.base.DATA", 0);
                        Timber.d("state - %d", n);
                        WeightFragment.this.checkState(n);
                        return;
                    }
                    case 4: {
                        WeightFragment.this.doDisconnectAndRestart(3);
                        return;
                    }
                    case 5: {
                        object = intent.getStringExtra("com.qardio.base.QB_ERROR_MSG");
                        Log.e((String)"WeightFragment", (String)("Connection error: " + (String)object));
                        WeightFragment.this.doDisconnectAndRestart(3);
                        return;
                    }
                    case 6: 
                }
                object = intent.getStringExtra("com.qardio.base.QB_ERROR_MSG");
                Log.e((String)"WeightFragment", (String)("Connection error: " + (String)object));
                WeightFragment.this.doDisconnectAndRestart(3);
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    private void checkAndProceed() {
        Activity activity;
        block5: {
            block4: {
                this.qardioBaseManager.stopScan();
                activity = this.getActivity();
                if (activity == null) break block4;
                Timber.d("needsOnBoarding() - %s", this.needsOnBoarding());
                if (!this.needsOnBoarding()) {
                    String string2 = DataHelper.DeviceAddressHelper.getDeviceAddress((Context)activity, this.getUserId());
                    Timber.d("scanAndConnect deviceAddress- %s", string2);
                    this.qardioBaseManager.scanAndConnect(string2);
                }
                boolean bl = PermissionUtil.BlePermissions.hasCourseLocationPermission(activity);
                boolean bl2 = PermissionUtil.BlePermissions.hasFineLocationPermission(activity);
                if (this.needsOnBoarding() && !this.onBoardingCanceled && bl && bl2) break block5;
            }
            return;
        }
        this.startActivityForResult(QBOnboardingActivity.createStartIntent((Context)activity), 1);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void checkState(int n) {
        switch (n) {
            default: {
                return;
            }
            case 6: {
                if (this.readingMeasurement) return;
                this.qardioBaseManager.readMeasurement();
                this.readingMeasurement = true;
                return;
            }
        }
    }

    private void doDisconnectAndRestart(int n) {
        Timber.d("doDisconnectAndRestart", new Object[0]);
        this.qardioBaseManager.stopScan();
        this.handler.removeCallbacksAndMessages(null);
        this.handler.postDelayed(WeightFragment$$Lambda$1.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(n));
    }

    private long getUserId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_ID")) {
            return bundle.getLong("com.getqardio.android.argument.USER_ID");
        }
        return -1L;
    }

    private void init() {
        this.startButton = this.rootView.findViewById(2131820621);
        this.startImage = (ImageView)this.rootView.findViewById(2131820895);
        this.startText = (TextView)this.rootView.findViewById(2131820896);
        this.historyButton = this.rootView.findViewById(2131820897);
        this.historyImage = (ImageView)this.rootView.findViewById(2131820898);
        this.historyText = (TextView)this.rootView.findViewById(2131820899);
        this.remindersButton = this.rootView.findViewById(2131820900);
        this.remindersImage = (ImageView)this.rootView.findViewById(2131820901);
        this.remindersText = (TextView)this.rootView.findViewById(2131820902);
        this.startButton.setOnClickListener(WeightFragment$$Lambda$2.lambdaFactory$(this));
        this.historyButton.setOnClickListener(WeightFragment$$Lambda$3.lambdaFactory$(this));
        this.remindersButton.setOnClickListener(WeightFragment$$Lambda$4.lambdaFactory$(this));
    }

    private boolean needsChangeFragment() {
        if (CustomApplication.getApplication().getQBTab() == 1) {
            return DataHelper.PregnancyDataHelper.isPregnancyModeActive((Context)this.getActivity(), this.getUserId()) ^ this.isPregnancyFragmentShown;
        }
        return false;
    }

    private boolean needsOnBoarding() {
        return this.getActivity() == null || !DataHelper.OnBoardingHelper.isOnboardingFinished((Context)this.getActivity(), this.getUserId());
    }

    public static WeightFragment newInstance(long l, boolean bl, boolean bl2) {
        Bundle bundle = new Bundle(1);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        bundle.putBoolean("com.getqardio.android.argument.LAUNCHED_FROM_ONBOARDING", bl);
        bundle.putBoolean("com.getqardio.android.ui.fragment.EXTRA_OPEN_TAB", bl2);
        WeightFragment weightFragment = new WeightFragment();
        weightFragment.setArguments(bundle);
        return weightFragment;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onTabSelected(int n) {
        switch (n) {
            case 1: {
                this.setLastMeasurementTabSelected(true);
                this.setHistoryTabSelected(false);
                this.setRemindersTabSelected(false);
                if (DataHelper.PregnancyDataHelper.isPregnancyModeActive((Context)this.getActivity(), this.getUserId())) {
                    this.isPregnancyFragmentShown = true;
                    this.showPregnancyMeasurementsFragment();
                } else {
                    this.isPregnancyFragmentShown = false;
                    this.showLastMeasurementFragment();
                }
                CustomApplication.getApplication().setQBTab(n);
                break;
            }
            case 2: {
                this.setHistoryTabSelected(true);
                this.setLastMeasurementTabSelected(false);
                this.setRemindersTabSelected(false);
                this.showMeasurementsFragment(-1);
                CustomApplication.getApplication().setQBTab(n);
                break;
            }
            case 3: {
                this.setRemindersTabSelected(true);
                this.setLastMeasurementTabSelected(false);
                this.setHistoryTabSelected(false);
                this.showRemindersFragment();
                CustomApplication.getApplication().setQBTab(n);
                break;
            }
        }
        this.isAfterAttach = false;
    }

    private void setHistoryTabSelected(boolean bl) {
        this.historyButton.setSelected(bl);
        if (bl) {
            this.historyImage.setImageResource(2130838014);
            this.historyText.setTextColor(this.getResources().getColor(2131689548));
            return;
        }
        this.historyImage.setImageResource(2130837961);
        this.historyText.setTextColor(this.getResources().getColor(2131689655));
    }

    private void setLastMeasurementTabSelected(boolean bl) {
        this.startButton.setSelected(bl);
        if (bl) {
            this.startImage.setImageResource(2130837895);
            this.startText.setTextColor(this.getResources().getColor(2131689548));
            return;
        }
        this.startImage.setImageResource(2130837894);
        this.startText.setTextColor(this.getResources().getColor(2131689655));
    }

    private void setRemindersTabSelected(boolean bl) {
        this.remindersButton.setSelected(bl);
        if (bl) {
            this.remindersImage.setImageResource(2130838018);
            this.remindersText.setTextColor(this.getResources().getColor(2131689548));
            return;
        }
        this.remindersImage.setImageResource(2130837964);
        this.remindersText.setTextColor(this.getResources().getColor(2131689655));
    }

    private void showLastMeasurementFragment() {
        this.getChildFragmentManager().beginTransaction().replace(2131820892, (Fragment)WeightLastMeasurementFragment.newInstance(this.getUserId(), this.weightUnit)).commitAllowingStateLoss();
    }

    private void showMeasurementsFragment(int n) {
        WeightMeasurementHistoryFragment weightMeasurementHistoryFragment = WeightMeasurementHistoryFragment.newInstance(this.getUserId(), this.userName, this.weightUnit, this.heightUnit, n);
        this.getChildFragmentManager().beginTransaction().replace(2131820892, (Fragment)weightMeasurementHistoryFragment).commitAllowingStateLoss();
    }

    private void showPregnancyMeasurementsFragment() {
        PregnancyMeasurementFragment pregnancyMeasurementFragment = PregnancyMeasurementFragment.newInstance(this.getUserId(), this.weightUnit);
        this.getChildFragmentManager().beginTransaction().replace(2131820892, (Fragment)pregnancyMeasurementFragment).commitAllowingStateLoss();
    }

    private void showRemindersFragment() {
        FragmentManager fragmentManager;
        if (this.remindersFragment == null) {
            this.remindersFragment = ReminderListFragment.getInstance("weight");
        }
        if ((fragmentManager = this.getChildFragmentManager()).findFragmentById(this.remindersFragment.getId()) == null || this.isAfterAttach) {
            fragmentManager.beginTransaction().replace(2131820892, (Fragment)this.remindersFragment).commitAllowingStateLoss();
        }
    }

    private void updateProfile() {
        this.profile = DataHelper.ProfileHelper.getProfileForUser((Context)this.getActivity(), this.getUserId());
        if (this.profile != null && this.profile.weightUnit != null) {
            this.weightUnit = this.profile.weightUnit;
        }
        if (this.profile != null && this.profile.heightUnit != null) {
            this.heightUnit = this.profile.heightUnit;
        }
        if (this.profile != null && this.profile.firstName != null && this.profile.lastName != null) {
            this.userName = this.profile.buildFullName();
        }
    }

    /* synthetic */ void lambda$doDisconnectAndRestart$0() {
        Object object = this.getActivity();
        if (object != null) {
            object = DataHelper.DeviceAddressHelper.getDeviceAddress((Context)object, this.getUserId());
            Timber.d("scanAndConnect deviceAddress- %s", object);
            this.qardioBaseManager.scanAndConnect((String)object);
        }
    }

    /* synthetic */ void lambda$init$1(View view) {
        if (!view.isSelected()) {
            this.onTabSelected(1);
        }
    }

    /* synthetic */ void lambda$init$2(View view) {
        if (!view.isSelected()) {
            this.onTabSelected(2);
        }
    }

    /* synthetic */ void lambda$init$3(View view) {
        if (!view.isSelected()) {
            this.onTabSelected(3);
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.broadcastManager = LocalBroadcastManager.getInstance((Context)this.getActivity());
        this.qardioBaseManager = new QardioBaseManager((Context)this.getActivity());
    }

    public void onActivityResult(int n, int n2, Intent intent) {
        boolean bl;
        boolean bl2 = bl = false;
        if (intent != null) {
            bl2 = bl;
            if (intent.getExtras() != null) {
                bl2 = bl;
                if (intent.getBooleanExtra("com.getqardio.android.argument.LAUNCHED_FROM_ONBOARDING", false)) {
                    bl2 = true;
                }
            }
        }
        launchedAfterOnboardingSucceeded = bl2;
        if (n == 1 && n2 == 0) {
            this.isBluetoothCancelled = true;
        }
        if (n == 1) {
            if (n2 == 0) {
                this.onBoardingCanceled = true;
            }
            if (n2 == -1) {
                this.onTabSelected(1);
                this.updateProfile();
            }
        }
    }

    public void onCreate(Bundle bundle) {
        boolean bl = false;
        super.onCreate(bundle);
        this.weightUnit = 0;
        this.heightUnit = 0;
        boolean bl2 = bl;
        if (this.getArguments() != null) {
            bl2 = bl;
            if (this.getArguments().containsKey("com.getqardio.android.argument.LAUNCHED_FROM_ONBOARDING")) {
                bl2 = bl;
                if (this.getArguments().getBoolean("com.getqardio.android.argument.LAUNCHED_FROM_ONBOARDING", false)) {
                    bl2 = true;
                }
            }
        }
        launchedAfterOnboardingSucceeded = bl2;
        this.updateProfile();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968852, viewGroup, false);
        return this.rootView;
    }

    @Override
    public void onMeasurementFailed() {
        Timber.d("onMeasurementFailed", new Object[0]);
        this.doDisconnectAndRestart(5);
    }

    @Override
    public void onMeasurementProcessed() {
        this.qardioBaseManager.disconnect();
    }

    @Override
    public void onMeasurementReceived() {
        Timber.d("onMeasurementReceived", new Object[0]);
        this.doDisconnectAndRestart(5);
    }

    public void onPause() {
        this.broadcastManager.unregisterReceiver(this.baseReceiver);
        this.getActivity().unregisterReceiver(this.bleStateReceiver);
        this.qardioBaseManager.stopScan();
        super.onPause();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onResume() {
        super.onResume();
        Timber.d("onResume " + launchedAfterOnboardingSucceeded + " hash = " + this.hashCode(), new Object[0]);
        this.registerReceiver();
        this.getActivity().registerReceiver(this.bleStateReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        if (launchedAfterOnboardingSucceeded) {
            launchedAfterOnboardingSucceeded = false;
            this.qardioBaseManager.readState();
            this.qardioBaseManager.enableStateNotifications();
            this.readingMeasurement = false;
        } else {
            this.checkAndProceed();
        }
        if (this.getArguments() != null && this.getArguments().containsKey("com.getqardio.android.ui.fragment.EXTRA_OPEN_TAB") && this.getArguments().getBoolean("com.getqardio.android.ui.fragment.EXTRA_OPEN_TAB")) {
            this.onTabSelected(2);
            return;
        } else {
            if (!this.needsChangeFragment()) return;
            {
                this.onTabSelected(1);
                return;
            }
        }
    }

    @Override
    public void onShowSetGoal() {
        this.setLastMeasurementTabSelected(false);
        this.setHistoryTabSelected(true);
        this.showMeasurementsFragment(2131821432);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.init();
        this.isAfterAttach = true;
        this.onTabSelected(CustomApplication.getApplication().getQBTab());
        this.setHasOptionsMenu(true);
    }

    public void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qardio.base.CONNECTED");
        intentFilter.addAction("com.qardio.base.STATE");
        intentFilter.addAction("com.qardio.base.DEVICE_SERIAL");
        intentFilter.addAction("com.qardio.base.SOFTWARE_VERSION");
        intentFilter.addAction("com.qardio.base.QB_ERROR");
        intentFilter.addAction("com.qardio.base.QB_CONNECTION_ERROR");
        intentFilter.addAction("com.qardio.base.DISCONNECTED");
        this.broadcastManager.registerReceiver(this.baseReceiver, intentFilter);
    }

}

