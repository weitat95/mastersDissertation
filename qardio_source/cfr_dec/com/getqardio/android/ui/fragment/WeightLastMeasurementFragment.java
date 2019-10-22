/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.app.NotificationManager
 *  android.bluetooth.BluetoothAdapter
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.Loader
 *  android.database.Cursor
 *  android.os.Bundle
 *  android.os.Handler
 *  android.text.TextUtils
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.ImageView
 *  android.widget.TextView
 *  org.json.JSONException
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.basealgoritms.BodyComposition;
import com.getqardio.android.datamodel.FirmwareDescription;
import com.getqardio.android.datamodel.Goal;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.datamodel.Statistic;
import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.device_related_services.fit.GoogleFitDataHelper;
import com.getqardio.android.mvp.qardio_base.measurement_details.view.QBMeasurementDetailsActivity;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.FirmwareUpdateHelper;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.shealth.ShealthDataHelper;
import com.getqardio.android.ui.WeightMeasurementCallback;
import com.getqardio.android.ui.activity.AddManualMeasurementActivity;
import com.getqardio.android.ui.activity.EditNoteActivity;
import com.getqardio.android.ui.activity.InviteUserActivity;
import com.getqardio.android.ui.adapter.DateTimeFormatHelper;
import com.getqardio.android.ui.dialog.QBFirmwareUpdateAvailable;
import com.getqardio.android.ui.fragment.WeightLastMeasurementFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.WeightLastMeasurementFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.WeightLastMeasurementFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.WeightLastMeasurementFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.WeightLastMeasurementFragment$$Lambda$5;
import com.getqardio.android.ui.fragment.WeightLastMeasurementFragment$$Lambda$7;
import com.getqardio.android.ui.widget.BmiResultChart;
import com.getqardio.android.utils.CurrentGoalUtils;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.analytics.WeightAddManualMeasurementsAnalyticsTracker;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.ui.Convert;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import timber.log.Timber;

public class WeightLastMeasurementFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor>,
DateTimeFormatHelper.Callback {
    private BmiResultChart bmiResultChart;
    private TextView bodyFatView;
    private LocalBroadcastManager broadcastManager;
    private WeightMeasurementCallback callback;
    private Float currentProgress;
    private DateTimeFormatHelper dateTimeFormatHelper;
    private String deviceSerialNumber;
    private FirmwareDescription firmwareDescription;
    private TextView guestView;
    private boolean isLastMeasurementFromGuest = false;
    private boolean isPreparingForMeasurement = false;
    private final BroadcastReceiver latestFirmwareReceiver = new BroadcastReceiver(){

        /*
         * Enabled aggressive block sorting
         */
        public void onReceive(Context context, Intent object) {
            Timber.d("onReceive action - %s", object.getAction());
            String string2 = object.getAction();
            int n = -1;
            switch (string2.hashCode()) {
                case 1688339086: {
                    if (!string2.equals("com.getqardio.android.action.NEW_BASE_FIRMWARE")) break;
                    n = 0;
                    break;
                }
            }
            switch (n) {
                case 0: {
                    WeightLastMeasurementFragment.this.broadcastManager.unregisterReceiver(WeightLastMeasurementFragment.this.latestFirmwareReceiver);
                    string2 = object.getStringExtra("com.getqardio.android.extra.FIRMWARE_IP");
                    String string3 = object.getStringExtra("com.getqardio.android.extra.FIRMWARE_VERSION");
                    object = object.getStringExtra("com.getqardio.android.extra.FIRMWARE_DESCRIPTION");
                    Timber.d("new version available %s, %s, %s", string2, string3, object);
                    if (DataHelper.QbUpdateDialogWasShown.isWasShown(context, WeightLastMeasurementFragment.this.getUserId())) break;
                    DataHelper.QbUpdateDialogWasShown.setWasShown(context, WeightLastMeasurementFragment.this.getUserId());
                    QBFirmwareUpdateAvailable.newInstance((String)object, string2, string3).show(WeightLastMeasurementFragment.this.getChildFragmentManager(), "QBFirmwareUpdateAvailable");
                    return;
                }
            }
        }
    };
    private long measureDate;
    private TextView measurementDate;
    private boolean measurementReceived = false;
    BroadcastReceiver measurementReceiver = new BroadcastReceiver(){

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public void onReceive(Context var1_1, Intent var2_3) {
            block31: {
                block29: {
                    block30: {
                        Timber.d("onReceive action - %s", new Object[]{var2_3 /* !! */ .getAction()});
                        var4_4 = var2_3 /* !! */ .getAction();
                        var3_5 = -1;
                        switch (var4_4.hashCode()) {
                            case 1424235813: {
                                if (var4_4.equals("com.qardio.base.CONNECTED")) {
                                    var3_5 = 0;
                                    ** break;
                                }
                                ** GOTO lbl28
                            }
                            case 1712737337: {
                                if (var4_4.equals("com.qardio.base.DEVICE_SERIAL")) {
                                    var3_5 = 1;
                                    ** break;
                                }
                                ** GOTO lbl28
                            }
                            case -165661596: {
                                if (var4_4.equals("com.qardio.base.SOFTWARE_VERSION")) {
                                    var3_5 = 2;
                                    ** break;
                                }
                                ** GOTO lbl28
                            }
                            case -53910355: {
                                if (var4_4.equals("com.qardio.base.STATE")) {
                                    var3_5 = 3;
                                    ** break;
                                }
                                ** GOTO lbl28
                            }
                            case 421757567: {
                                if (var4_4.equals("com.qardio.base.DISCONNECTED")) {
                                    var3_5 = 4;
                                }
                            }
lbl28:
                            // 12 sources
                            default: {
                                break block30;
                            }
                            case -629657166: 
                        }
                        if (var4_4.equals("com.qardio.base.QB_MEASUREMENT")) {
                            var3_5 = 5;
                        }
                    }
                    switch (var3_5) {
                        default: {
                            return;
                        }
                        case 0: {
                            WeightLastMeasurementFragment.access$302(WeightLastMeasurementFragment.this, false);
                            return;
                        }
                        case 1: {
                            WeightLastMeasurementFragment.access$402(WeightLastMeasurementFragment.this, var2_3 /* !! */ .getStringExtra("com.qardio.base.DATA"));
                            Timber.d("deviceSerialNumber - %s", new Object[]{WeightLastMeasurementFragment.access$400(WeightLastMeasurementFragment.this)});
                            return;
                        }
                        case 2: {
                            var1_1 /* !! */  = var2_3 /* !! */ .getStringExtra("com.qardio.base.DATA");
                            WeightLastMeasurementFragment.access$500(WeightLastMeasurementFragment.this, (String)var1_1 /* !! */ );
                            return;
                        }
                        case 3: {
                            var3_5 = var2_3 /* !! */ .getIntExtra("com.qardio.base.DATA", 0);
                            WeightLastMeasurementFragment.access$600(WeightLastMeasurementFragment.this, var3_5);
                            return;
                        }
                        case 4: {
                            WeightLastMeasurementFragment.access$700(WeightLastMeasurementFragment.this);
                            if (!WeightLastMeasurementFragment.access$300(WeightLastMeasurementFragment.this)) {
                                Timber.d("Measurement state: %d", new Object[]{WeightLastMeasurementFragment.access$800(WeightLastMeasurementFragment.this)});
                                if (WeightLastMeasurementFragment.access$900(WeightLastMeasurementFragment.this) != null && !WeightLastMeasurementFragment.this.isDetached()) {
                                    WeightLastMeasurementFragment.access$1000(WeightLastMeasurementFragment.this, WeightLastMeasurementFragment.access$900(WeightLastMeasurementFragment.this));
                                }
                            }
                            WeightLastMeasurementFragment.access$302(WeightLastMeasurementFragment.this, false);
                            WeightLastMeasurementFragment.access$802(WeightLastMeasurementFragment.this, 0);
                            return;
                        }
                        case 5: 
                    }
                    if (WeightLastMeasurementFragment.access$300(WeightLastMeasurementFragment.this) != false) return;
                    var2_3 /* !! */  = var2_3 /* !! */ .getStringExtra("com.qardio.base.DATA");
                    Timber.d("Measurement from base: %s", new Object[]{var2_3 /* !! */ });
                    try {
                        if (WeightLastMeasurementFragment.access$1100(WeightLastMeasurementFragment.this) == null) {
                            Timber.d("No profile found", new Object[0]);
                            return;
                        }
                        var4_4 = QardioBaseUtils.weightMeasurementFromBase((String)var2_3 /* !! */ , WeightLastMeasurementFragment.access$1100(WeightLastMeasurementFragment.this));
                        if (var4_4.qbUserId == null || WeightLastMeasurementFragment.access$1100((WeightLastMeasurementFragment)WeightLastMeasurementFragment.this).refId == null || !WeightLastMeasurementFragment.access$1100((WeightLastMeasurementFragment)WeightLastMeasurementFragment.this).refId.equals(var4_4.qbUserId)) break block29;
                        WeightLastMeasurementFragment.access$1200(WeightLastMeasurementFragment.this, var1_1 /* !! */ , (WeightMeasurement)var4_4);
                        WeightLastMeasurementFragment.access$1300(WeightLastMeasurementFragment.this, false);
                        ** GOTO lbl81
                    }
                    catch (JSONException var1_2) {
                        Timber.e(var1_2, "Returned measurement data not valid json: %s", new Object[]{var1_2.getMessage()});
                        ** GOTO lbl83
lbl81:
                        // 3 sources
                        do {
                            WeightLastMeasurementFragment.access$700(WeightLastMeasurementFragment.this);
lbl83:
                            // 2 sources
                            WeightLastMeasurementFragment.access$302(WeightLastMeasurementFragment.this, true);
                            return;
                            break;
                        } while (true);
                    }
                }
                if (var4_4.qbUserId == null || WeightLastMeasurementFragment.access$1100((WeightLastMeasurementFragment)WeightLastMeasurementFragment.this).refId == null || WeightLastMeasurementFragment.access$1100((WeightLastMeasurementFragment)WeightLastMeasurementFragment.this).refId.equals(var4_4.qbUserId)) break block31;
                Timber.d("ignore that measurement. - %s", new Object[]{var4_4});
                ** GOTO lbl81
            }
            var2_3 /* !! */  = QardioBaseUtils.weightMeasurementGuestFromBase((String)var2_3 /* !! */ );
            WeightLastMeasurementFragment.access$1400(WeightLastMeasurementFragment.this, var1_1 /* !! */ , (WeightMeasurement)var2_3 /* !! */ );
            ** while (true)
        }
    };
    private int measurementState = 0;
    private TextView measurementTime;
    private AlertDialog mlocationPermNeeededDialog;
    private TextView muscleView;
    private TextView note;
    private Profile profile;
    private Button setGoalView;
    private ImageView smileView;
    private TextView targetLossView;
    private TextView targetView;
    private TextView targetWeeklyView;
    private TextView thisWeekDifference;
    private SimpleDateFormat timeFormat;
    private WeightMeasurement weightMeasurement;
    private TextView weightUnitView;
    private TextView weightView;

    static /* synthetic */ void access$1000(WeightLastMeasurementFragment weightLastMeasurementFragment, WeightMeasurement weightMeasurement) {
        weightLastMeasurementFragment.applyData(weightMeasurement);
    }

    static /* synthetic */ Profile access$1100(WeightLastMeasurementFragment weightLastMeasurementFragment) {
        return weightLastMeasurementFragment.profile;
    }

    static /* synthetic */ void access$1200(WeightLastMeasurementFragment weightLastMeasurementFragment, Context context, WeightMeasurement weightMeasurement) {
        weightLastMeasurementFragment.handleNormalMeasurement(context, weightMeasurement);
    }

    static /* synthetic */ void access$1300(WeightLastMeasurementFragment weightLastMeasurementFragment, boolean bl) {
        weightLastMeasurementFragment.showGuestMode(bl);
    }

    static /* synthetic */ void access$1400(WeightLastMeasurementFragment weightLastMeasurementFragment, Context context, WeightMeasurement weightMeasurement) {
        weightLastMeasurementFragment.handleGuestMeasurement(context, weightMeasurement);
    }

    static /* synthetic */ boolean access$300(WeightLastMeasurementFragment weightLastMeasurementFragment) {
        return weightLastMeasurementFragment.measurementReceived;
    }

    static /* synthetic */ boolean access$302(WeightLastMeasurementFragment weightLastMeasurementFragment, boolean bl) {
        weightLastMeasurementFragment.measurementReceived = bl;
        return bl;
    }

    static /* synthetic */ String access$400(WeightLastMeasurementFragment weightLastMeasurementFragment) {
        return weightLastMeasurementFragment.deviceSerialNumber;
    }

    static /* synthetic */ String access$402(WeightLastMeasurementFragment weightLastMeasurementFragment, String string2) {
        weightLastMeasurementFragment.deviceSerialNumber = string2;
        return string2;
    }

    static /* synthetic */ void access$500(WeightLastMeasurementFragment weightLastMeasurementFragment, String string2) {
        weightLastMeasurementFragment.handleSoftwareVersion(string2);
    }

    static /* synthetic */ void access$600(WeightLastMeasurementFragment weightLastMeasurementFragment, int n) {
        weightLastMeasurementFragment.checkState(n);
    }

    static /* synthetic */ void access$700(WeightLastMeasurementFragment weightLastMeasurementFragment) {
        weightLastMeasurementFragment.finishUIMeasurement();
    }

    static /* synthetic */ int access$800(WeightLastMeasurementFragment weightLastMeasurementFragment) {
        return weightLastMeasurementFragment.measurementState;
    }

    static /* synthetic */ int access$802(WeightLastMeasurementFragment weightLastMeasurementFragment, int n) {
        weightLastMeasurementFragment.measurementState = n;
        return n;
    }

    static /* synthetic */ WeightMeasurement access$900(WeightLastMeasurementFragment weightLastMeasurementFragment) {
        return weightLastMeasurementFragment.weightMeasurement;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void applyData(WeightMeasurement weightMeasurement) {
        Object var5_2 = null;
        if (weightMeasurement != null) {
            this.measureDate = weightMeasurement.measureDate.getTime();
            Object object = weightMeasurement.weight;
            Integer n = weightMeasurement.height;
            if (object != null && n != null) {
                this.bmiResultChart.setBodyMassIndex(Float.valueOf(BodyComposition.bmi(((Float)object).floatValue(), n)));
            }
            int n2 = this.getWeightUnit();
            float f = MetricUtils.convertWeight(0, n2, weightMeasurement.weight.floatValue());
            if (!this.isLastMeasurementFromGuest) {
                this.weightView.setText((CharSequence)Convert.floatToString(Float.valueOf(f), 1));
            }
            this.weightUnitView.setText(MetricUtils.getWeightUnitNameRes(n2));
            if (weightMeasurement.measurementSource == 4) {
                object = weightMeasurement.muscle != null ? Float.valueOf(weightMeasurement.muscle.intValue()) : null;
                this.setMusclePercentage((Float)object);
                object = var5_2;
                if (weightMeasurement.fat != null) {
                    object = Float.valueOf(weightMeasurement.fat.intValue());
                }
                this.setFatPercentage((Float)object);
            } else if (weightMeasurement.fat != null && MetricUtils.isPercentageValid(weightMeasurement.fat.intValue())) {
                this.bodyFatView.setText((CharSequence)Utils.formatInteger(weightMeasurement.fat));
                if (weightMeasurement.muscle != null) {
                    this.setMusclePercentage(Float.valueOf(weightMeasurement.muscle.intValue()));
                } else {
                    this.setMusclePercentage(QardioBaseUtils.musclePercentage(weightMeasurement));
                }
            } else {
                this.bodyFatView.setText((CharSequence)this.getString(2131362495));
                this.setMusclePercentage(null);
            }
            object = weightMeasurement.note != null ? weightMeasurement.note : "";
            if (!TextUtils.isEmpty((CharSequence)object)) {
                this.note.setText((CharSequence)object);
            }
            this.note.setTag(object);
            this.note.setEnabled(true);
            this.setDate(weightMeasurement);
        }
        this.getThisWeekDifference();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void applyGoal(Cursor object) {
        if (object != null && object.moveToFirst()) {
            this.hideSetGoal();
            Goal goal = DataHelper.CurrentGoalHelper.parseGoal(object);
            if (goal == null) {
                this.smileView.setImageResource(2130837987);
                this.showSetGoal();
                return;
            }
            int n = CurrentGoalUtils.getFacialResource(this.currentProgress, goal);
            this.smileView.setImageResource(n);
            this.targetView.setText((CharSequence)this.createTargetValueString(2131362414, goal.target.floatValue()));
            this.targetWeeklyView.setText((CharSequence)this.createTargetValueString(2131362416, goal.targetPerWeek.floatValue()));
            float f = this.profile != null && this.profile.weight != null ? this.profile.weight.floatValue() : 0.0f;
            f = goal.target.floatValue() - MetricUtils.convertWeight(this.getWeightUnit(), 0, f);
            n = f > 0.0f ? 2131362409 : 2131362411;
            this.targetLossView.setText((CharSequence)this.createTargetValueString(n, f));
            return;
        }
        this.smileView.setImageResource(2130837987);
        this.showSetGoal();
    }

    private void cancelNotifications() {
        ((NotificationManager)this.getActivity().getSystemService("notification")).cancel(2);
    }

    private void checkBLERadioEnabled() {
        new Handler().postDelayed(WeightLastMeasurementFragment$$Lambda$1.lambdaFactory$(this), 500L);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void checkState(int n) {
        if (!(n != 2 && n != 3 || this.isPreparingForMeasurement)) {
            this.prepareUIForMeasurement();
        } else if (n == 12) {
            this.finishUIMeasurement();
        }
        if (n == 12) {
            this.callback.onMeasurementProcessed();
        }
        this.measurementState = n;
    }

    private String createConvertedValueString(Float f, int n) {
        return Utils.formatFloat(Utils.round(Float.valueOf(MetricUtils.convertWeight(0, n, f.floatValue())), 1));
    }

    private String createTargetValueString(int n, float f) {
        int n2 = this.getWeightUnit();
        String string2 = this.getString(MetricUtils.getWeightUnitNameRes(n2));
        return String.format(this.getString(n), this.createConvertedValueString(Float.valueOf(f), n2) + " " + string2);
    }

    private void finishUIMeasurement() {
        if (this.getView() != null) {
            this.getView().findViewById(2131821412).setVisibility(8);
            this.weightView.setVisibility(0);
            this.weightUnitView.setVisibility(0);
            this.measurementState = 0;
            this.isPreparingForMeasurement = false;
        }
    }

    private void getThisWeekDifference() {
        block3: {
            block2: {
                if (this.getActivity() == null) break block2;
                this.currentProgress = MeasurementHelper.Weight.getThisWeekDifference((Context)this.getActivity(), this.getUserId());
                if (this.currentProgress == null) break block3;
                this.thisWeekDifference.setVisibility(0);
                String string2 = this.getString(MetricUtils.getWeightUnitNameRes(this.getWeightUnit()));
                String string3 = Convert.floatToString(Float.valueOf(MetricUtils.convertWeight(0, this.getWeightUnit(), this.currentProgress.floatValue())), 1);
                String string4 = this.getString(2131362381);
                this.thisWeekDifference.setText((CharSequence)String.format(string4, string3 + " " + string2));
            }
            return;
        }
        this.thisWeekDifference.setVisibility(8);
    }

    private long getUserId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_ID")) {
            return bundle.getLong("com.getqardio.android.argument.USER_ID");
        }
        return -1L;
    }

    private int getWeightUnit() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.WEIGHT_UNIT")) {
            return bundle.getInt("com.getqardio.android.argument.WEIGHT_UNIT");
        }
        return 0;
    }

    private void handleGuestMeasurement(Context context, WeightMeasurement weightMeasurement) {
        Timber.d("handleGuestMeasurement - %s", weightMeasurement);
        weightMeasurement.deviceId = DataHelper.DeviceIdHelper.getDeviceId(context, this.getUserId());
        weightMeasurement.source = "BLEBASE";
        weightMeasurement.deviceSerialNumber = this.deviceSerialNumber;
        this.setFirmwareDescription(weightMeasurement);
        weightMeasurement.memberName = "guest@getqardio.com";
        weightMeasurement.visitor = true;
        MeasurementHelper.Weight.addMeasurement(context, this.getUserId(), weightMeasurement, true);
        this.showGuestMode(true);
        float f = MetricUtils.convertWeight(0, this.getWeightUnit(), weightMeasurement.weight.floatValue());
        this.weightView.setText((CharSequence)Convert.floatToString(Float.valueOf(f), 1));
        this.isLastMeasurementFromGuest = true;
    }

    private void handleNormalMeasurement(Context context, WeightMeasurement weightMeasurement) {
        Timber.d("handleNormalMeasurement - %s", weightMeasurement);
        long l = this.getUserId();
        weightMeasurement.deviceId = DataHelper.DeviceIdHelper.getDeviceId(context, l);
        weightMeasurement.source = "BLEBASE";
        weightMeasurement.deviceSerialNumber = this.deviceSerialNumber;
        this.setFirmwareDescription(weightMeasurement);
        weightMeasurement.visitor = false;
        MeasurementHelper.Weight.addMeasurement(context, l, weightMeasurement, true);
        ShealthDataHelper.WeightMeasurements.requestSaveWeightMeasurement(context, l, weightMeasurement);
        GoogleFitDataHelper.Weight.requestSaveToGoogleFit(context, l, weightMeasurement);
        this.updateProfile((Context)this.getActivity());
    }

    private void handleSoftwareVersion(String string2) {
        Timber.d("handleSoftwareVersion - %s", string2);
        try {
            this.firmwareDescription = FirmwareUpdateHelper.parseBaseVersion(string2);
            FirmwareUpdateHelper.setCurrentQBFirmwareVersion((Context)this.getActivity(), this.deviceSerialNumber, this.firmwareDescription);
            return;
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            Timber.e(stringIndexOutOfBoundsException, "can't parse firmware version - %s", string2);
            return;
        }
    }

    private void hideSetGoal() {
        this.targetView.setVisibility(0);
        this.targetWeeklyView.setVisibility(0);
        this.targetLossView.setVisibility(0);
        this.setGoalView.setVisibility(8);
    }

    static /* synthetic */ void lambda$null$0(BluetoothAdapter bluetoothAdapter, View view) {
        bluetoothAdapter.enable();
    }

    public static WeightLastMeasurementFragment newInstance(long l, int n) {
        Bundle bundle = new Bundle(2);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        bundle.putInt("com.getqardio.android.argument.WEIGHT_UNIT", n);
        WeightLastMeasurementFragment weightLastMeasurementFragment = new WeightLastMeasurementFragment();
        weightLastMeasurementFragment.setArguments(bundle);
        return weightLastMeasurementFragment;
    }

    private void onShowSetGoal() {
        this.callback.onShowSetGoal();
    }

    private void prepareUIForMeasurement() {
        Timber.d("Preparing to receive measurement", new Object[0]);
        this.isPreparingForMeasurement = true;
        if (this.getView() != null) {
            this.getView().findViewById(2131821412).setVisibility(0);
            this.weightView.setVisibility(4);
            this.weightUnitView.setVisibility(4);
            this.bmiResultChart.clearBodyMassIndex();
            this.showGuestMode(false);
            this.note.setText((CharSequence)"");
            this.note.setTag((Object)"");
            this.bodyFatView.setText((CharSequence)"--");
            this.muscleView.setText((CharSequence)"--");
        }
    }

    private void requestReadGoogleFit() {
        Activity activity = this.getActivity();
        if (activity != null) {
            GoogleFitDataHelper.Weight.requestReadFromGoogleFit((Context)activity, this.getUserId());
        }
    }

    private void requestUpdateGoal() {
        DataHelper.CurrentGoalHelper.requestGoalUpdate((Context)this.getActivity(), this.getUserId());
    }

    private void setDate(WeightMeasurement object) {
        if (object != null && ((WeightMeasurement)object).measureDate != null) {
            object = ((WeightMeasurement)object).measureDate;
            if (this.measurementDate != null) {
                this.measurementDate.setText((CharSequence)DateUtils.formatDateInLocaleAndWithTodayAndYesterday((Context)this.getActivity(), (Date)object));
            }
            if (this.measurementTime != null && this.timeFormat != null) {
                this.measurementTime.setText((CharSequence)this.timeFormat.format((Date)object));
            }
        }
    }

    private void setFatPercentage(Float f) {
        if (f != null && MetricUtils.isPercentageValid(f.floatValue())) {
            this.bodyFatView.setText((CharSequence)Convert.floatToString(f, 0));
            return;
        }
        this.bodyFatView.setText(2131362495);
    }

    private void setFirmwareDescription(WeightMeasurement weightMeasurement) {
        if (this.firmwareDescription != null) {
            weightMeasurement.firmwareVersion = this.firmwareDescription.toString();
        }
    }

    private void setMusclePercentage(Float f) {
        if (f != null && MetricUtils.isPercentageValid(f.floatValue())) {
            this.muscleView.setText((CharSequence)Convert.floatToString(f, 0));
            return;
        }
        this.muscleView.setText(2131362495);
    }

    private void showEditNoteScreen() {
        Activity activity = this.getActivity();
        if (activity != null) {
            this.startActivityForResult(EditNoteActivity.getStartIntent((Context)activity, this.getUserId(), this.note.getText().toString(), "weight"), 0);
        }
    }

    private void showGuestMode(boolean bl) {
        if (bl) {
            this.guestView.setVisibility(0);
            return;
        }
        this.guestView.setVisibility(8);
    }

    private void showLastMeasurementDetails() {
        Activity activity = this.getActivity();
        if (this.weightMeasurement != null && activity != null) {
            this.startActivity(QBMeasurementDetailsActivity.getStartIntent((Context)activity, this.getUserId(), this.weightMeasurement.measureDate.getTime(), this.getWeightUnit()));
            this.getActivity().overridePendingTransition(2131034132, 2131034131);
        }
    }

    private void showSetGoal() {
        this.targetView.setVisibility(8);
        this.targetWeeklyView.setVisibility(8);
        this.targetLossView.setVisibility(8);
        this.setGoalView.setVisibility(0);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateProfile(Context context) {
        block6: {
            block5: {
                int n = 0;
                if (this.weightMeasurement == null) return;
                if (this.profile == null) break block5;
                Timber.d("updateProfile weightMeasurement - %s, profile - %s", this.weightMeasurement, this.profile);
                if (this.profile.weight == null) {
                    if (this.profile.weightUnit != null) {
                        n = this.profile.weightUnit;
                    }
                    DataHelper.ProfileHelper.setProfileWeight(context, this.getUserId(), this.weightMeasurement.weight.floatValue(), n, this.profile.syncStatus);
                    return;
                }
                if (Math.abs(MetricUtils.convertWeight(this.profile.weightUnit, 0, this.profile.weight.floatValue()) - this.weightMeasurement.weight.floatValue()) >= 0.2f) break block6;
            }
            return;
        }
        float f = MetricUtils.convertWeight(0, this.profile.weightUnit, this.weightMeasurement.weight.floatValue());
        DataHelper.ProfileHelper.setProfileWeight(context, this.getUserId(), f, this.profile.weightUnit, this.profile.syncStatus);
    }

    /* synthetic */ void lambda$checkBLERadioEnabled$1() {
        BluetoothAdapter bluetoothAdapter;
        if (this.getActivity() != null && !this.getActivity().isFinishing() && (bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()) != null && bluetoothAdapter.getState() == 10) {
            Snackbar.make(this.getActivity().findViewById(2131821383), "Enable Bluetooth", -2).setAction("ENABLE", WeightLastMeasurementFragment$$Lambda$7.lambdaFactory$(bluetoothAdapter)).show();
        }
    }

    /* synthetic */ void lambda$onViewCreated$2(View view) {
        this.showEditNoteScreen();
    }

    /* synthetic */ void lambda$onViewCreated$3(View view) {
        this.onShowSetGoal();
    }

    /* synthetic */ void lambda$onViewCreated$4(View view) {
        this.onShowSetGoal();
    }

    /* synthetic */ void lambda$onViewCreated$5(View view) {
        this.showLastMeasurementDetails();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131362268);
        this.requestUpdateGoal();
        this.requestReadGoogleFit();
        this.setHasOptionsMenu(true);
        this.callback = (WeightMeasurementCallback)this.getParentFragment();
        this.dateTimeFormatHelper = new DateTimeFormatHelper(this);
        this.getLoaderManager().initLoader(2, null, (LoaderManager.LoaderCallbacks)this);
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
        this.getLoaderManager().initLoader(4, null, (LoaderManager.LoaderCallbacks)this);
        this.broadcastManager = LocalBroadcastManager.getInstance((Context)this.getActivity());
    }

    public void onActivityResult(int n, int n2, Intent object) {
        super.onActivityResult(n, n2, (Intent)object);
        if (n == 0 && n2 == -1) {
            object = EditNoteActivity.extractNoteFromIntent((Intent)object);
            this.note.setText((CharSequence)object);
            if (!((String)object).equals(this.note.getTag())) {
                MeasurementHelper.Weight.changeMeasurementNote((Context)this.getActivity(), this.getUserId(), this.measureDate, (String)object, true);
            }
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.cancelNotifications();
    }

    /*
     * Enabled aggressive block sorting
     */
    public Loader<Cursor> onCreateLoader(int n, Bundle object) {
        object = CustomApplication.getApplication().getCurrentUserId();
        switch (n) {
            default: {
                return null;
            }
            case 1: {
                return MeasurementHelper.Weight.getLastMeasurementLoader((Context)this.getActivity(), (Long)object, null);
            }
            case 2: {
                return DataHelper.ProfileHelper.getProfileLoader((Context)this.getActivity(), (Long)object, null);
            }
            case 3: {
                return DataHelper.CurrentGoalHelper.getGoalLoader((Context)this.getActivity(), (Long)object, null);
            }
            case 4: {
                String string2 = DataHelper.DeviceSnHelper.getDeviceSn((Context)this.getActivity(), (Long)object);
                if (TextUtils.isEmpty((CharSequence)string2)) return null;
                return DataHelper.StatisticHelper.createGetStatisticLoader((Context)this.getActivity(), (Long)object, string2);
            }
        }
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menuInflater.inflate(2131886099, menu2);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130968853, viewGroup, false);
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.mlocationPermNeeededDialog != null && this.mlocationPermNeeededDialog.isShowing()) {
            this.mlocationPermNeeededDialog.dismiss();
            this.mlocationPermNeeededDialog = null;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public void onLoadFinished(Loader<Cursor> object, Cursor object2) {
        void var2_4;
        Long l = CustomApplication.getApplication().getCurrentUserId();
        switch (object.getId()) {
            case 1: {
                if (l == null || !var2_4.moveToFirst() || this.isDetached()) return;
                {
                    this.weightMeasurement = MeasurementHelper.Weight.parseMeasurement((Cursor)var2_4);
                    Timber.d("LAST_MEASUREMENT from db - %s", this.weightMeasurement);
                    if (this.getActivity() == null || !this.isAdded()) return;
                    {
                        this.applyData(this.weightMeasurement);
                        this.updateProfile((Context)this.getActivity());
                        return;
                    }
                }
            }
            case 2: {
                this.getLoaderManager().initLoader(3, null, (LoaderManager.LoaderCallbacks)this);
                if (l == null || !var2_4.moveToFirst() || this.isDetached()) return;
                {
                    this.profile = DataHelper.ProfileHelper.parseProfile((Cursor)var2_4);
                    Timber.d("PROFILE_LOADER_ID from db - %s", this.weightMeasurement);
                    return;
                }
            }
            default: {
                return;
            }
            case 3: {
                this.applyGoal((Cursor)var2_4);
                return;
            }
            case 4: 
        }
        Timber.d("DEVICE_STAT_LOADER_ID load finished", new Object[0]);
        if (var2_4 == null || !var2_4.moveToFirst()) return;
        {
            Statistic statistic = DataHelper.StatisticHelper.parseStatistic((Cursor)var2_4);
            Timber.d("statistic - %s", statistic);
            if (statistic == null || statistic.firmware == null) return;
            {
                FirmwareDescription firmwareDescription = FirmwareUpdateHelper.getCurrentQBFirmwareUpdate((Context)this.getActivity());
                FirmwareDescription firmwareDescription2 = FirmwareUpdateHelper.parseBaseVersion(statistic.firmware);
                Timber.d("locally saved firmware - %s, firmware from server - %s", firmwareDescription, firmwareDescription2);
                if (firmwareDescription == null) {
                    String string2 = DataHelper.DeviceSnHelper.getDeviceSn((Context)this.getActivity(), l);
                    FirmwareUpdateHelper.setCurrentQBFirmwareVersion((Context)this.getActivity(), string2, firmwareDescription2);
                }
                FirmwareUpdateHelper.loadQBLatestFirmwareInfo((Context)this.getActivity(), l);
                return;
            }
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131821497: {
                WeightAddManualMeasurementsAnalyticsTracker.trackAddManualMeasurementClick((Context)this.getActivity(), WeightAddManualMeasurementsAnalyticsTracker.Screen.LAST_MEASUREMENT);
                AddManualMeasurementActivity.start((Context)this.getActivity(), this.getUserId(), 2);
            }
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 2131821492: 
        }
        this.startActivity(new Intent((Context)this.getActivity(), InviteUserActivity.class));
        return super.onOptionsItemSelected(menuItem);
    }

    public void onPause() {
        super.onPause();
        this.broadcastManager.unregisterReceiver(this.measurementReceiver);
        this.broadcastManager.unregisterReceiver(this.latestFirmwareReceiver);
    }

    public void onResume() {
        super.onResume();
        this.isLastMeasurementFromGuest = false;
        this.dateTimeFormatHelper.onUpdatePatterns();
        Object object = new IntentFilter();
        object.addAction("com.qardio.base.QB_MEASUREMENT");
        object.addAction("com.qardio.base.DEVICE_SERIAL");
        object.addAction("com.qardio.base.SOFTWARE_VERSION");
        object.addAction("com.qardio.base.STATE");
        object.addAction("com.qardio.base.CONNECTED");
        object.addAction("com.qardio.base.DISCONNECTED");
        this.broadcastManager.registerReceiver(this.measurementReceiver, (IntentFilter)object);
        object = DataHelper.DeviceSnHelper.getDeviceSn((Context)this.getActivity(), this.getUserId());
        if (!TextUtils.isEmpty((CharSequence)object)) {
            Timber.d("deviceSn - %s", object);
            DataHelper.StatisticHelper.requestGetStatistic((Context)this.getActivity(), this.getUserId(), (String)object);
            this.broadcastManager.registerReceiver(this.latestFirmwareReceiver, new IntentFilter("com.getqardio.android.action.NEW_BASE_FIRMWARE"));
        }
    }

    public void onStart() {
        super.onStart();
        this.checkBLERadioEnabled();
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.bmiResultChart = (BmiResultChart)view.findViewById(2131821413);
        this.weightView = (TextView)view.findViewById(2131821407);
        this.weightUnitView = (TextView)view.findViewById(2131821408);
        this.bodyFatView = (TextView)view.findViewById(2131821409);
        this.muscleView = (TextView)view.findViewById(2131821410);
        this.note = (TextView)view.findViewById(2131820923);
        this.targetWeeklyView = (TextView)view.findViewById(2131821450);
        this.targetLossView = (TextView)view.findViewById(2131821451);
        this.targetView = (TextView)view.findViewById(2131821452);
        this.setGoalView = (Button)view.findViewById(2131821453);
        this.smileView = (ImageView)view.findViewById(2131821448);
        this.guestView = (TextView)view.findViewById(2131821411);
        this.note.setOnClickListener(WeightLastMeasurementFragment$$Lambda$2.lambdaFactory$(this));
        view.findViewById(2131821447).setOnClickListener(WeightLastMeasurementFragment$$Lambda$3.lambdaFactory$(this));
        this.setGoalView.setOnClickListener(WeightLastMeasurementFragment$$Lambda$4.lambdaFactory$(this));
        view.findViewById(2131821406).setOnClickListener(WeightLastMeasurementFragment$$Lambda$5.lambdaFactory$(this));
        this.measurementDate = (TextView)view.findViewById(2131821414);
        this.measurementTime = (TextView)view.findViewById(2131821415);
        this.thisWeekDifference = (TextView)view.findViewById(2131821449);
    }

    @Override
    public void setDateFormat(SimpleDateFormat simpleDateFormat) {
    }

    @Override
    public void setTimeFormat(SimpleDateFormat simpleDateFormat) {
        this.timeFormat = simpleDateFormat;
    }

    @Override
    public void updateData() {
        this.setDate(this.weightMeasurement);
    }

}

