/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.Loader
 *  android.database.Cursor
 *  android.graphics.Typeface
 *  android.os.Bundle
 *  android.os.CountDownTimer
 *  android.os.Handler
 *  android.os.Parcelable
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.WindowManager
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.datamodel.Settings;
import com.getqardio.android.datamodel.Statistic;
import com.getqardio.android.device_related_services.fit.GoogleFitUtils;
import com.getqardio.android.device_related_services.map.LocationDetector;
import com.getqardio.android.device_related_services.map.LocationDetectorImpl;
import com.getqardio.android.device_related_services.map.QLatLng;
import com.getqardio.android.googlefit.GoogleFitApiImpl;
import com.getqardio.android.mvp.common.util.RxUtil;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.LocationClusterManager;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.shealth.ShealthDataHelper;
import com.getqardio.android.ui.activity.EditNoteActivity;
import com.getqardio.android.ui.activity.ImageSlideshowActivity;
import com.getqardio.android.ui.activity.OnBoardingActivity;
import com.getqardio.android.ui.activity.SelectLocationTagActivity;
import com.getqardio.android.ui.activity.SendMeasurementActivity;
import com.getqardio.android.ui.animation.ExpandAnimation;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment$$Lambda$10;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment$$Lambda$11;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment$$Lambda$12;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment$$Lambda$13;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment$$Lambda$14;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment$$Lambda$5;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment$$Lambda$6;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment$$Lambda$7;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment$$Lambda$8;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment$$Lambda$9;
import com.getqardio.android.ui.fragment.EngagementScreenFragment;
import com.getqardio.android.ui.widget.BPResultChart;
import com.getqardio.android.ui.widget.LockableScrollView;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.RateAppManager;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.analytics.BpMeasurementsReadingAnalyticsTracker;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.wizard.OnboardingPrefsManager;
import com.getqardio.shared.utils.BpLevelConstants;
import com.getqardio.shared.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.qardio.ble.bpcollector.mobiledevice.MobileDeviceFactory;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class BPMeasurementsResultFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor>,
GestureDetector.OnGestureListener,
LocationDetector.LocationListener,
EngagementScreenFragment.Callback {
    private static final String ZERO = com.getqardio.android.utils.Utils.formatInteger(0);
    private ActionBar actionBar;
    private BatteryInfoReceiver batteryInfoReceiver;
    private int batteryLevel = -1;
    private BPResultChart bpResultChart;
    private View bpResultTable;
    private BTStateReceiver btStateReceiver;
    private Button cancelMeasurementButton;
    private ImageView chartIndicator;
    private TextView classificationLabel;
    private CountDownTimer countDownTimer;
    private TextView date;
    private SimpleDateFormat dateFormat;
    private View datePanel;
    private DeviceInfoReceiver deviceInfoReceiver;
    private DeviceStateReceiver deviceStateReceiver;
    private int[] diaValues;
    private TextView diaView;
    private CompositeDisposable disposables;
    private EngagementScreenFragment engagementScreenFragment;
    private int errorCode = Integer.MIN_VALUE;
    private BPMeasurement finalMeasurement = new BPMeasurement();
    private GestureDetector gestureDetector;
    private GoogleApiClient googleApiClient;
    private GoogleFitApiImpl googleFitApi;
    private boolean hasFinalMeasurement = false;
    private float horizontalAnimationStep;
    private View ihbPanel;
    private int ihbPanelHeight;
    private boolean ihbValue;
    private boolean isGooglePlayServicesAvailable;
    private boolean isLowBatteryOnBoardingShown = false;
    private boolean isScrolling;
    private LocationDetector locationDetector;
    private MeasurementErrorBroadcastReceiver measurementErrorReceiver;
    private TextView measurementErrorTitle;
    private volatile boolean measurementInProcess;
    private int measurementNumber;
    private TextView measurementNumberOne;
    private TextView measurementNumberThree;
    private TextView measurementNumberTwo;
    private View measurementPanel;
    private MeasurementsBroadcastReceiver measurementsReceiver;
    private EditText note;
    private int[] pulseValues;
    private TextView pulseView;
    private boolean receiverRegistered = false;
    private Button saveMeasurementButton;
    private LockableScrollView scrollView;
    private Button sendMeasurementButton;
    private Settings settings;
    private Runnable slideToChartModeRunnable;
    private Runnable slideToTableModeRunnable;
    private Statistic statistic;
    private int[] sysValues;
    private TextView sysView;
    private ImageView tableIndicator;
    private ImageView tagIcon;
    private SimpleDateFormat timeFormat;
    private TextView timerLabel;
    private TextView timerMessage;
    private View timerPanel;
    private boolean visitorMeasurementSent = false;
    private TextView visitorModeMessageTextView;

    public BPMeasurementsResultFragment() {
        this.btStateReceiver = new BTStateReceiver();
        this.measurementsReceiver = new MeasurementsBroadcastReceiver();
        this.measurementErrorReceiver = new MeasurementErrorBroadcastReceiver();
        this.deviceInfoReceiver = new DeviceInfoReceiver();
        this.batteryInfoReceiver = new BatteryInfoReceiver();
        this.deviceStateReceiver = new DeviceStateReceiver();
        this.locationDetector = LocationDetectorImpl.getInstance();
        this.disposables = new CompositeDisposable();
    }

    static /* synthetic */ BPMeasurement access$2200(BPMeasurementsResultFragment bPMeasurementsResultFragment) {
        return bPMeasurementsResultFragment.finalMeasurement;
    }

    static /* synthetic */ void access$lambda$0(BPMeasurementsResultFragment bPMeasurementsResultFragment) {
        bPMeasurementsResultFragment.showTableResult();
    }

    static /* synthetic */ void access$lambda$1(BPMeasurementsResultFragment bPMeasurementsResultFragment, int n) {
        bPMeasurementsResultFragment.setMeasurementTag(n);
    }

    private int calculateAvg(int[] arrn, int n) {
        float f = 0.0f;
        for (int i = 0; i < n; ++i) {
            f += (float)arrn[i];
        }
        return Math.round(f / (float)n);
    }

    private void cancelMeasurement() {
        Activity activity = this.getActivity();
        if (activity != null) {
            this.onMeasurementCanceled((Context)activity);
            ImageSlideshowActivity.closeImageSlideshowActivity((Context)activity);
            activity.finish();
        }
    }

    private boolean closeEngagementScreen() {
        boolean bl = false;
        FragmentManager fragmentManager = this.getFragmentManager();
        boolean bl2 = bl;
        if (fragmentManager != null) {
            bl2 = bl;
            if (this.engagementScreenFragment != null) {
                fragmentManager.beginTransaction().remove((Fragment)this.engagementScreenFragment).commitAllowingStateLoss();
                this.engagementScreenFragment = null;
                bl2 = true;
            }
        }
        return bl2;
    }

    private void createCountDownTimer() {
        if (this.countDownTimer == null) {
            this.countDownTimer = new CountDownTimer(TimeUnit.SECONDS.toMillis(this.settings.pauseSize.intValue()), 1000L){

                public void onFinish() {
                    BPMeasurementsResultFragment.this.timerPanel.setVisibility(4);
                    BPMeasurementsResultFragment.this.measurementPanel.setVisibility(0);
                    BPMeasurementsResultFragment.this.startMeasurement();
                }

                public void onTick(long l) {
                    BPMeasurementsResultFragment.this.setTimerLabelValue((int)TimeUnit.MILLISECONDS.toSeconds(l));
                }
            };
        }
    }

    private static SimpleDateFormat createDateFormat() {
        return DateUtils.getCurrentDateFormat();
    }

    private void createDateTimeFormats() {
        this.dateFormat = BPMeasurementsResultFragment.createDateFormat();
        this.timeFormat = BPMeasurementsResultFragment.createTimeFormat();
    }

    private void createRunnables() {
        this.slideToTableModeRunnable = new Runnable(){

            @Override
            public void run() {
                if (BPMeasurementsResultFragment.this.bpResultTable.getTranslationX() < 0.0f) {
                    BPMeasurementsResultFragment.this.shiftComponents(Math.max(-BPMeasurementsResultFragment.this.horizontalAnimationStep, BPMeasurementsResultFragment.this.bpResultTable.getTranslationX()));
                    BPMeasurementsResultFragment.this.bpResultTable.post((Runnable)this);
                    return;
                }
                BPMeasurementsResultFragment.this.showTableResult();
            }
        };
        this.slideToChartModeRunnable = new Runnable(){

            @Override
            public void run() {
                if (BPMeasurementsResultFragment.this.bpResultChart.getTranslationX() > 0.0f) {
                    BPMeasurementsResultFragment.this.shiftComponents(Math.min(BPMeasurementsResultFragment.this.horizontalAnimationStep, BPMeasurementsResultFragment.this.bpResultChart.getTranslationX()));
                    BPMeasurementsResultFragment.this.bpResultChart.post((Runnable)this);
                    return;
                }
                BPMeasurementsResultFragment.this.showChartResult();
            }
        };
    }

    private static SimpleDateFormat createTimeFormat() {
        return DateUtils.getCurrentTimeFormat();
    }

    private Observable<Integer> detectLocationTag(QLatLng qLatLng) {
        return Observable.defer(BPMeasurementsResultFragment$$Lambda$14.lambdaFactory$(this, qLatLng)).compose(RxUtil.applySchedulers());
    }

    public static BPMeasurementsResultFragment getInstance(boolean bl, boolean bl2) {
        Bundle bundle = new Bundle(2);
        bundle.putBoolean("com.getqardio.android.argument.VISITOR_MODE", bl);
        bundle.putBoolean("com.getqardio.android.argument.FROM_NOTIFICATION", bl2);
        BPMeasurementsResultFragment bPMeasurementsResultFragment = new BPMeasurementsResultFragment();
        bPMeasurementsResultFragment.setArguments(bundle);
        return bPMeasurementsResultFragment;
    }

    private int getMeasurementsNumber() {
        int n = 1;
        if (this.settings != null) {
            n = this.settings.measurementsNumber;
        }
        return n;
    }

    private int getPause() {
        int n = 0;
        if (this.settings != null) {
            n = this.settings.pauseSize;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void init(View view, Bundle object) {
        this.gestureDetector = new GestureDetector((GestureDetector.OnGestureListener)this);
        this.isGooglePlayServicesAvailable = CustomApplication.getApplication().isGooglePlayServiceAvailableOrNonGoogleBuild();
        this.setHasOptionsMenu(true);
        this.createRunnables();
        this.tagIcon = (ImageView)view.findViewById(2131820933);
        this.datePanel = view.findViewById(2131820932);
        this.date = (TextView)view.findViewById(2131820920);
        this.sysView = (TextView)view.findViewById(2131821141);
        this.diaView = (TextView)view.findViewById(2131821144);
        this.pulseView = (TextView)view.findViewById(2131821127);
        this.tableIndicator = (ImageView)view.findViewById(2131821148);
        this.chartIndicator = (ImageView)view.findViewById(2131821149);
        this.note = (EditText)view.findViewById(2131820885);
        this.saveMeasurementButton = (Button)view.findViewById(2131820943);
        this.cancelMeasurementButton = (Button)view.findViewById(2131820945);
        this.sendMeasurementButton = (Button)view.findViewById(2131820944);
        this.bpResultTable = view.findViewById(2131821140);
        this.bpResultChart = (BPResultChart)view.findViewById(2131820808);
        this.ihbPanel = view.findViewById(2131820937);
        this.measurementNumberOne = (TextView)view.findViewById(2131820934);
        this.measurementNumberOne.setText((CharSequence)com.getqardio.android.utils.Utils.formatInteger(1));
        this.measurementNumberTwo = (TextView)view.findViewById(2131820935);
        this.measurementNumberTwo.setText((CharSequence)com.getqardio.android.utils.Utils.formatInteger(2));
        this.measurementNumberThree = (TextView)view.findViewById(2131820936);
        this.measurementNumberThree.setText((CharSequence)com.getqardio.android.utils.Utils.formatInteger(3));
        this.measurementPanel = view.findViewById(2131820941);
        this.timerPanel = view.findViewById(2131820942);
        this.timerPanel.setVisibility(4);
        this.timerMessage = (TextView)view.findViewById(2131821395);
        this.measurementErrorTitle = (TextView)view.findViewById(2131821394);
        this.timerLabel = (TextView)view.findViewById(2131821396);
        this.visitorModeMessageTextView = (TextView)view.findViewById(2131821151);
        this.classificationLabel = (TextView)view.findViewById(2131821150);
        this.scrollView = (LockableScrollView)view.findViewById(2131820931);
        this.ihbPanel.post(BPMeasurementsResultFragment$$Lambda$2.lambdaFactory$(this));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)view.getContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.horizontalAnimationStep = 15.0f * displayMetrics.density;
        displayMetrics = Typeface.create((String)"sans-serif-light", (int)0);
        this.date.setTypeface((Typeface)displayMetrics);
        ((TextView)view.findViewById(2131821142)).setTypeface((Typeface)displayMetrics);
        ((TextView)view.findViewById(2131821145)).setTypeface((Typeface)displayMetrics);
        ((TextView)view.findViewById(2131821082)).setTypeface((Typeface)displayMetrics);
        ((TextView)view.findViewById(2131821143)).setTypeface((Typeface)displayMetrics);
        ((TextView)view.findViewById(2131821146)).setTypeface((Typeface)displayMetrics);
        ((TextView)view.findViewById(2131821147)).setTypeface((Typeface)displayMetrics);
        ((TextView)view.findViewById(2131820939)).setTypeface((Typeface)displayMetrics);
        boolean bl = false;
        boolean bl2 = false;
        if (object != null) {
            if (object.containsKey("final_measurement")) {
                this.finalMeasurement = (BPMeasurement)object.getParcelable("final_measurement");
                bl2 = true;
            }
            if (object.containsKey("measurement_number")) {
                this.measurementNumber = object.getInt("measurement_number");
            }
            bl = bl2;
            if (object.containsKey("error_code")) {
                this.errorCode = object.getInt("error_code");
                bl = bl2;
            }
        }
        this.finalMeasurement.source = 0;
        Date date = new Date();
        if (this.finalMeasurement.measureDate == null) {
            this.finalMeasurement.measureDate = date;
        }
        if (TextUtils.isEmpty((CharSequence)this.finalMeasurement.timezone)) {
            this.finalMeasurement.timezone = String.format("UTC%s", DateUtils.getTimeZoneOffset(date));
        }
        this.setDate();
        this.bpResultTable.post(BPMeasurementsResultFragment$$Lambda$3.lambdaFactory$(this));
        this.setupScrollListener(view.findViewById(2131821139));
        if (!this.isVisitorMode()) {
            this.note.setOnClickListener(BPMeasurementsResultFragment$$Lambda$4.lambdaFactory$(this));
        } else {
            this.note.setVisibility(8);
            this.visitorModeMessageTextView.setVisibility(0);
        }
        this.saveMeasurementButton.setOnClickListener(BPMeasurementsResultFragment$$Lambda$5.lambdaFactory$(this));
        this.cancelMeasurementButton.setOnClickListener(BPMeasurementsResultFragment$$Lambda$6.lambdaFactory$(this));
        this.sendMeasurementButton.setOnClickListener(BPMeasurementsResultFragment$$Lambda$7.lambdaFactory$(this));
        if (this.errorCode != Integer.MIN_VALUE) {
            this.loadSettings();
            this.updateMeasurementNumbers();
            this.createCountDownTimer();
            this.onMeasurementError(this.errorCode);
        }
        if (bl) {
            this.loadSettings();
            this.updateMeasurementNumbers();
            this.showMeasurementData(this.finalMeasurement);
            this.onMeasurementFinished(false);
            this.getActivity().invalidateOptionsMenu();
        }
    }

    private void initMeasurement() {
        this.startEngagementScreen();
        this.loadSettings();
        this.sysValues = new int[this.settings.measurementsNumber.intValue()];
        this.diaValues = new int[this.settings.measurementsNumber.intValue()];
        this.pulseValues = new int[this.settings.measurementsNumber.intValue()];
        this.ihbValue = false;
        this.createCountDownTimer();
        this.measurementNumber = 1;
        this.measurementInProcess = false;
        this.startMeasurement();
        this.trackMeasurementStarted();
        MobileDeviceFactory.getSerialNumber((Context)this.getActivity());
    }

    private boolean isVisitorMode() {
        Bundle bundle = this.getArguments();
        return bundle != null && bundle.containsKey("com.getqardio.android.argument.VISITOR_MODE") && bundle.getBoolean("com.getqardio.android.argument.VISITOR_MODE");
    }

    static /* synthetic */ void lambda$onMeasurementFinished$6(Boolean bl) throws Exception {
        Timber.d("Status = " + bl, new Object[0]);
    }

    private void loadSettings() {
        Activity activity = this.getActivity();
        long l = CustomApplication.getApplication().getCurrentUserId();
        this.settings = DataHelper.SettingsHelper.getSettings((Context)activity, l);
        if (this.settings == null) {
            this.settings = DataHelper.SettingsHelper.getDefaultSettings(l);
        }
    }

    private boolean needSelectLocationTag() {
        return this.finalMeasurement.latitude != null && this.finalMeasurement.longitude != null && (this.finalMeasurement.tag == null || this.finalMeasurement.tag == 0 || this.finalMeasurement.tag == 6);
    }

    private void onBTStateChanged(int n, int n2) {
        Activity activity;
        if (!this.hasFinalMeasurement && n == 10 && (activity = this.getActivity()) != null) {
            activity.finish();
            ImageSlideshowActivity.closeImageSlideshowActivity((Context)activity);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onChangedMeasurements(int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
        synchronized (this) {
            if (bl2) {
                if (this.measurementInProcess && !this.hasFinalMeasurement) {
                    this.measurementInProcess = false;
                    this.onMeasurementComplete(n, n2, n3, bl);
                }
            } else {
                this.showMeasurementProcessData(n, n2, n3);
            }
            return;
        }
    }

    private void onMeasurementCanceled(Context context) {
        if (this.countDownTimer != null) {
            this.countDownTimer.cancel();
        }
        if (this.statistic != null) {
            this.syncStatistic();
        }
        this.trackMeasurementCancelled();
        this.stopMeasurement(context);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onMeasurementComplete(int n, int n2, int n3, boolean bl) {
        if (!(Utils.isSysValid(n) && Utils.isDiaValid(n2) && Utils.isPulseValid(n3))) {
            this.onMeasurementError(12);
            return;
        }
        this.sysValues[this.measurementNumber - 1] = n;
        this.diaValues[this.measurementNumber - 1] = n2;
        this.pulseValues[this.measurementNumber - 1] = n3;
        bl = this.ihbValue || bl;
        this.ihbValue = bl;
        this.showMeasurementData(this.calculateAvg(this.sysValues, this.measurementNumber), this.calculateAvg(this.diaValues, this.measurementNumber), this.calculateAvg(this.pulseValues, this.measurementNumber));
        ++this.measurementNumber;
        if (this.statistic != null) {
            Statistic statistic = this.statistic;
            ++statistic.measurementsCount;
        }
        if (this.measurementNumber > this.settings.measurementsNumber) {
            this.onMeasurementFinished(true);
        } else {
            this.startTimer(this.getString(2131361981), this.settings.pauseSize);
        }
        this.trackMeasurementCompleted(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onMeasurementError(int n) {
        Object object;
        this.errorCode = n;
        this.trackMeasurementCompleted(true);
        if (this.statistic != null) {
            object = this.statistic;
            ++((Statistic)object).badMeasurementsCount;
            this.syncStatistic();
        }
        Activity activity = this.getActivity();
        if (n == 9) {
            if (activity != null && !this.isLowBatteryOnBoardingShown) {
                this.startActivity(OnBoardingActivity.createLowBatteryStartIntent((Context)this.getActivity()));
                this.getActivity().finish();
                this.isLowBatteryOnBoardingShown = true;
                ImageSlideshowActivity.closeImageSlideshowActivity((Context)activity);
            }
        } else {
            this.classificationLabel.setVisibility(8);
            this.ihbPanel.setVisibility(8);
            int n2 = 0;
            String string2 = "";
            object = string2;
            int n3 = n2;
            switch (n) {
                default: {
                    n3 = n2;
                    object = string2;
                    break;
                }
                case 4: {
                    n3 = 2131362018;
                    object = this.getString(2131362017, new Object[]{n});
                    break;
                }
                case 8: {
                    n3 = 2131361978;
                    object = this.getString(2131362012);
                    break;
                }
                case 12: {
                    n3 = 2131361841;
                    object = this.getString(2131361840, new Object[]{n});
                    break;
                }
                case 11: {
                    n3 = 2131361982;
                    object = this.getString(2131361983);
                    break;
                }
                case 6: {
                    n3 = 2131362000;
                    object = this.getString(2131361999, new Object[]{n});
                    break;
                }
                case 0: 
                case 1: 
                case 2: 
                case 3: 
                case 5: 
                case 7: 
                case 10: {
                    n3 = 2131361978;
                    object = this.getString(2131361979, new Object[]{n});
                }
                case 9: 
            }
            string2 = this.getString(n3);
            if (this.settings.measurementsNumber == 1) {
                this.showErrorMessage(string2, (String)object);
                if (activity != null) {
                    ImageSlideshowActivity.closeImageSlideshowActivity((Context)activity);
                }
            } else {
                this.startTimer(this.getString(2131361980), this.settings.pauseSize);
            }
        }
        this.onMeasurementError();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onMeasurementFinished(boolean bl) {
        if (this.finalMeasurement.sys == null) {
            this.finalMeasurement.sys = this.calculateAvg(this.sysValues, this.sysValues.length);
        }
        if (this.finalMeasurement.dia == null) {
            this.finalMeasurement.dia = this.calculateAvg(this.diaValues, this.diaValues.length);
        }
        if (this.finalMeasurement.pulse == null) {
            this.finalMeasurement.pulse = this.calculateAvg(this.pulseValues, this.pulseValues.length);
        }
        if (this.finalMeasurement.irregularHeartBeat == null) {
            this.finalMeasurement.irregularHeartBeat = this.ihbValue;
        }
        this.hasFinalMeasurement = true;
        if (this.finalMeasurement.irregularHeartBeat.booleanValue()) {
            this.showIHB();
        }
        this.cancelMeasurementButton.setVisibility(4);
        Activity activity = this.getActivity();
        if (activity != null) {
            ImageSlideshowActivity.closeImageSlideshowActivity((Context)activity);
            activity.invalidateOptionsMenu();
        }
        if (!this.isVisitorMode()) {
            this.saveMeasurementButton.setVisibility(0);
            if (bl) {
                Long l = CustomApplication.getApplication().getCurrentUserId();
                MeasurementHelper.BloodPressure.addMeasurement((Context)activity, l, this.finalMeasurement, false);
                ShealthDataHelper.BpMeasurements.requestSaveMeasurement((Context)activity, l, this.finalMeasurement);
                if (this.googleApiClient.isConnected() && this.googleFitApi != null && CustomApplication.getApplication().getCurrentUserId() != null) {
                    this.disposables.add(this.googleFitApi.saveBloodPressureMeasurement((Context)CustomApplication.getApplication(), CustomApplication.getApplication().getCurrentUserId(), this.finalMeasurement).subscribe(BPMeasurementsResultFragment$$Lambda$8.lambdaFactory$(), BPMeasurementsResultFragment$$Lambda$9.lambdaFactory$()));
                }
                if (activity != null) {
                    CustomApplication.getApplication().getRateAppManager((Context)activity).onQAMeasurementComplete((Context)activity);
                }
            }
        } else {
            this.visitorModeMessageTextView.setText(2131362400);
            this.sendMeasurementButton.setVisibility(0);
        }
        if (this.statistic != null) {
            this.statistic.deviceId = this.finalMeasurement.deviceId;
            if (this.batteryLevel != -1) {
                this.statistic.checkBatteriesChanged(this.batteryLevel);
                this.statistic.batteryStatus = this.batteryLevel;
            }
            this.syncStatistic();
        }
    }

    private void onVisitorMeasurementSent() {
        this.saveMeasurementButton.setVisibility(0);
        this.sendMeasurementButton.setVisibility(8);
        this.visitorMeasurementSent = true;
        this.getActivity().invalidateOptionsMenu();
    }

    private void processScrollFinished() {
        if (-this.bpResultTable.getTranslationX() < (float)(this.bpResultTable.getWidth() / 2)) {
            this.bpResultTable.post(this.slideToTableModeRunnable);
            return;
        }
        this.bpResultChart.post(this.slideToChartModeRunnable);
    }

    private void setDate() {
        if (this.finalMeasurement != null && this.finalMeasurement.measureDate != null && this.date != null) {
            String string2 = this.dateFormat.format(this.finalMeasurement.measureDate);
            String string3 = this.timeFormat.format(this.finalMeasurement.measureDate);
            this.date.setText((CharSequence)(string2 + " " + string3));
        }
    }

    private void setMeasurementTag(int n) {
        if (!this.isAdded()) {
            return;
        }
        Timber.d("setMeasurementTag", new Object[0]);
        this.finalMeasurement.tag = n;
        switch (this.finalMeasurement.tag) {
            default: {
                this.tagIcon.setImageResource(2130837740);
                return;
            }
            case 1: {
                this.tagIcon.setImageResource(2130837851);
                return;
            }
            case 2: {
                this.tagIcon.setImageResource(2130837853);
                return;
            }
            case 3: {
                this.tagIcon.setImageResource(2130837857);
                return;
            }
            case 4: {
                this.tagIcon.setImageResource(2130837849);
                return;
            }
            case 5: 
        }
        this.tagIcon.setImageResource(2130837847);
    }

    private void setResultTableBg(int n) {
        int n2 = this.bpResultTable.getPaddingTop();
        int n3 = this.bpResultTable.getPaddingLeft();
        int n4 = this.bpResultTable.getPaddingRight();
        int n5 = this.bpResultTable.getPaddingBottom();
        this.bpResultTable.setBackgroundResource(n);
        this.bpResultTable.setPadding(n3, n2, n4, n5);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setTimerLabelValue(int n) {
        int n2 = n % 60;
        String string2 = (n /= 60) < 10 ? ZERO : "";
        String string3 = n2 < 10 ? ZERO : "";
        this.timerLabel.setText((CharSequence)(string2 + com.getqardio.android.utils.Utils.formatInteger(n) + ":" + string3 + com.getqardio.android.utils.Utils.formatInteger(n2)));
    }

    private void setupScrollListener(View view) {
        view.setOnTouchListener(BPMeasurementsResultFragment$$Lambda$12.lambdaFactory$(this));
    }

    private void shiftComponents(float f) {
        this.bpResultTable.setTranslationX(this.bpResultTable.getTranslationX() - f);
        this.bpResultChart.setTranslationX(this.bpResultChart.getTranslationX() - f);
    }

    private void showChartResult() {
        this.bpResultTable.setTranslationX((float)(-this.bpResultChart.getWidth()));
        this.bpResultChart.setTranslationX(0.0f);
        this.tableIndicator.setImageResource(2130837844);
        this.chartIndicator.setImageResource(2130837843);
        this.note.setVisibility(8);
        this.visitorModeMessageTextView.setVisibility(8);
        this.classificationLabel.setVisibility(0);
    }

    private void showEditNoteDialog() {
        Activity activity = this.getActivity();
        if (activity != null) {
            this.startActivityForResult(EditNoteActivity.getStartIntent((Context)activity, CustomApplication.getApplication().getCurrentUserId(), this.note.getText().toString(), "bp"), 0);
        }
    }

    private void showErrorMessage(String string2, String string3) {
        this.timerMessage.setText((CharSequence)string3);
        this.measurementErrorTitle.setVisibility(0);
        this.measurementErrorTitle.setText((CharSequence)string2);
        this.timerLabel.setVisibility(8);
        this.measurementPanel.setVisibility(4);
        this.timerPanel.setVisibility(0);
    }

    private void showIHB() {
        this.datePanel.setVisibility(8);
        if (this.ihbPanel.getVisibility() != 0) {
            ExpandAnimation expandAnimation = new ExpandAnimation(this.ihbPanel, 0, this.ihbPanelHeight);
            expandAnimation.setAnimationListener(new Animation.AnimationListener(){

                public void onAnimationEnd(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                    BPMeasurementsResultFragment.this.ihbPanel.setVisibility(0);
                }
            });
            expandAnimation.setFillBefore(true);
            expandAnimation.setDuration(300L);
            this.ihbPanel.clearAnimation();
            this.ihbPanel.startAnimation((Animation)expandAnimation);
        }
    }

    private void showMeasurementData(int n, int n2, int n3) {
        this.sysView.setText((CharSequence)com.getqardio.android.utils.Utils.formatInteger(n));
        this.diaView.setText((CharSequence)com.getqardio.android.utils.Utils.formatInteger(n2));
        this.pulseView.setText((CharSequence)com.getqardio.android.utils.Utils.formatInteger(n3));
        this.bpResultChart.setBP(n2, n);
        switch (BpLevelConstants.calculateLevel(n2, n)) {
            default: {
                this.setResultTableBg(2130837891);
                return;
            }
            case 0: {
                this.setResultTableBg(2130837888);
                return;
            }
            case 1: {
                this.setResultTableBg(2130837887);
                return;
            }
            case 2: {
                this.setResultTableBg(2130837893);
                return;
            }
            case 3: {
                this.setResultTableBg(2130837892);
                return;
            }
            case 4: {
                this.setResultTableBg(2130837889);
                return;
            }
            case 5: 
        }
        this.setResultTableBg(2130837890);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showMeasurementData(BPMeasurement bPMeasurement) {
        int n = 0;
        int n2 = bPMeasurement.sys != null ? bPMeasurement.sys : 0;
        int n3 = bPMeasurement.dia != null ? bPMeasurement.dia : 0;
        if (bPMeasurement.pulse != null) {
            n = bPMeasurement.pulse;
        }
        this.showMeasurementData(n2, n3, n);
    }

    private void showMeasurementProcessData(int n, int n2, int n3) {
        this.saveMeasurementButton.setVisibility(4);
        this.cancelMeasurementButton.setVisibility(0);
        this.sysView.setText((CharSequence)com.getqardio.android.utils.Utils.formatInteger(n));
        this.diaView.setText((CharSequence)com.getqardio.android.utils.Utils.formatInteger(n2));
        this.pulseView.setText((CharSequence)com.getqardio.android.utils.Utils.formatInteger(n3));
    }

    private void showPhotoSlideShow(Context context, boolean bl, boolean bl2) {
        this.startActivity(ImageSlideshowActivity.getStartIntent(context, bl, bl2));
    }

    private void showSelectLocationTagActivity() {
        Activity activity = this.getActivity();
        if (activity != null) {
            this.startActivity(SelectLocationTagActivity.getStartIntent((Context)activity, CustomApplication.getApplication().getCurrentUserId(), this.finalMeasurement.measureDate.getTime()));
            activity.finish();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showTableResult() {
        this.bpResultTable.setTranslationX(0.0f);
        this.bpResultChart.setTranslationX((float)this.bpResultTable.getWidth());
        this.tableIndicator.setImageResource(2130837843);
        this.chartIndicator.setImageResource(2130837844);
        if (this.isVisitorMode()) {
            this.visitorModeMessageTextView.setVisibility(0);
        } else {
            this.note.setVisibility(0);
        }
        this.classificationLabel.setVisibility(8);
    }

    private void startEngagementScreen() {
        this.engagementScreenFragment = EngagementScreenFragment.newInstance(this);
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(2131820778, (Fragment)this.engagementScreenFragment);
        fragmentTransaction.commit();
    }

    private boolean startMeasurement() {
        Timber.d("startMeasurement() call", new Object[0]);
        Activity activity = this.getActivity();
        if (activity != null) {
            Timber.d("Start taking measurement", new Object[0]);
            this.updateMeasurementNumbers();
            MobileDeviceFactory.startMeasurement((Context)activity);
            this.setResultTableBg(2130837891);
            this.sysView.setText((CharSequence)ZERO);
            this.diaView.setText((CharSequence)ZERO);
            this.pulseView.setText((CharSequence)ZERO);
            this.measurementInProcess = true;
            return true;
        }
        return false;
    }

    private void startTimer(String string2, int n) {
        this.timerMessage.setText((CharSequence)string2);
        this.setTimerLabelValue(n);
        this.timerLabel.setVisibility(0);
        this.measurementPanel.setVisibility(4);
        this.timerPanel.setVisibility(0);
        this.countDownTimer.start();
    }

    private void startTrackLocation() {
        if (this.isGooglePlayServicesAvailable && this.settings.allowLocation.booleanValue() && !this.isVisitorMode()) {
            this.locationDetector.startTrackLocation(this, this.getActivity());
        }
    }

    private void stopMeasurement(Context context) {
        MobileDeviceFactory.stopMeasurement(context);
    }

    private void stopTrackingLocation() {
        this.locationDetector.stopTrackLocation();
    }

    private void syncStatistic() {
        DataHelper.StatisticHelper.insertStatistic((Context)this.getActivity(), this.statistic);
        DataHelper.StatisticHelper.requestUpdateStatistic((Context)this.getActivity(), CustomApplication.getApplication().getCurrentUserId(), this.statistic.deviceId);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void trackMeasurementCancelled() {
        boolean bl = this.errorCode != Integer.MIN_VALUE;
        BpMeasurementsReadingAnalyticsTracker.trackMeasurementCancelled((Context)this.getActivity(), this.getMeasurementsNumber(), this.getPause(), bl, this.isVisitorMode());
    }

    private void trackMeasurementCompleted(boolean bl) {
        BpMeasurementsReadingAnalyticsTracker.trackMeasurementCompleted((Context)this.getActivity(), this.getMeasurementsNumber(), this.getPause(), bl, this.isVisitorMode());
    }

    private void trackMeasurementStarted() {
        BpMeasurementsReadingAnalyticsTracker.trackMeasurementStarted((Context)this.getActivity(), this.getMeasurementsNumber(), this.getPause(), this.isVisitorMode());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateMeasurementNumbers() {
        int n = 2130837885;
        int n2 = 0;
        if (this.settings.measurementsNumber <= 1) {
            this.measurementNumberOne.setVisibility(8);
            this.measurementNumberTwo.setVisibility(8);
            this.measurementNumberThree.setVisibility(8);
            return;
        }
        this.measurementNumberOne.setVisibility(0);
        this.measurementNumberTwo.setVisibility(0);
        TextView textView = this.measurementNumberThree;
        if (this.settings.measurementsNumber < 3) {
            n2 = 8;
        }
        textView.setVisibility(n2);
        textView = this.measurementNumberTwo;
        n2 = this.measurementNumber >= 2 ? 2130837885 : 2130837886;
        textView.setBackgroundResource(n2);
        textView = this.measurementNumberThree;
        n2 = this.measurementNumber >= 3 ? n : 2130837886;
        textView.setBackgroundResource(n2);
    }

    public EngagementScreenFragment getEngagementScreenFragment() {
        return this.engagementScreenFragment;
    }

    /* synthetic */ ObservableSource lambda$detectLocationTag$9(QLatLng qLatLng) throws Exception {
        Timber.d("detectLocationTag", new Object[0]);
        int n = 6;
        Activity activity = this.getActivity();
        if (activity != null) {
            activity = MeasurementHelper.BloodPressure.getMeasurementsWithLocation((Context)activity, CustomApplication.getApplication().getCurrentUserId(), MeasurementHelper.BloodPressure.DETECT_LOCATION_TAG_PROJECTION);
            n = LocationClusterManager.findTagForMeasurement(qLatLng, (Cursor)activity);
        }
        return Observable.just(n);
        finally {
            activity.close();
        }
    }

    /* synthetic */ void lambda$init$1() {
        if (this.finalMeasurement == null || this.finalMeasurement.irregularHeartBeat == null || !this.finalMeasurement.irregularHeartBeat.booleanValue()) {
            this.ihbPanelHeight = this.ihbPanel.getHeight();
            this.ihbPanel.setVisibility(8);
        }
    }

    /* synthetic */ void lambda$init$2(View view) {
        this.showEditNoteDialog();
    }

    /* synthetic */ void lambda$init$3(View view) {
        block6: {
            block5: {
                view = this.getActivity();
                if (view == null) break block5;
                if (!this.isVisitorMode()) break block6;
                view.finish();
            }
            return;
        }
        if (this.needSelectLocationTag() && this.isGooglePlayServicesAvailable) {
            this.showSelectLocationTagActivity();
            CustomApplication.getApplication().getRateAppManager((Context)view).setHasSuccessfulMeasurement(true);
            return;
        }
        MeasurementHelper.BloodPressure.requestBPMeasurementsSync((Context)view, CustomApplication.getApplication().getCurrentUserId());
        CustomApplication.getApplication().getRateAppManager((Context)view).setHasSuccessfulMeasurement(true);
        MeasurementHelper.BloodPressure.requestBPMeasurementsSync((Context)view, CustomApplication.getApplication().getCurrentUserId());
        if (!OnboardingPrefsManager.isOutroOnboardingDiscovered()) {
            this.startActivity(OnBoardingActivity.createStartIntent((Context)this.getActivity(), true));
            OnboardingPrefsManager.updateOutroOnboardingDiscovered();
        }
        view.finish();
    }

    /* synthetic */ void lambda$init$4(View view) {
        this.cancelMeasurement();
    }

    /* synthetic */ void lambda$init$5(View view) {
        int n = MeasurementHelper.BloodPressure.saveVisitorMeasurement((Context)this.getActivity(), CustomApplication.getApplication().getCurrentUserId(), this.finalMeasurement);
        this.startActivityForResult(SendMeasurementActivity.createStartIntent((Context)this.getActivity(), n), 1);
    }

    /* synthetic */ void lambda$onActivityCreated$0(ConnectionResult connectionResult) {
        this.googleFitApi = null;
    }

    /* synthetic */ void lambda$onEngagementScreenClosed$8(Activity activity, boolean bl, boolean bl2) {
        if (activity != null && this.actionBar != null) {
            this.actionBar.show();
        }
        if (this.closeEngagementScreen() && bl && bl2) {
            this.cancelMeasurement();
        }
    }

    /* synthetic */ boolean lambda$setupScrollListener$7(View view, MotionEvent motionEvent) {
        if (this.gestureDetector.onTouchEvent(motionEvent)) {
            return true;
        }
        if ((motionEvent.getAction() == 1 || motionEvent.getAction() == 3) && this.isScrolling) {
            this.isScrolling = false;
            this.processScrollFinished();
            this.scrollView.unlock();
        }
        return false;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.actionBar = ActivityUtils.getActionBar(this.getActivity());
        this.actionBar.show();
        AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "Blood pressure measurement result");
        if (bundle == null) {
            this.initMeasurement();
            this.startTrackLocation();
        }
        this.googleApiClient = GoogleFitUtils.buildBloodPresureClient((Context)CustomApplication.getApplication());
        this.googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks(){

            @Override
            public void onConnected(Bundle bundle) {
                BPMeasurementsResultFragment.this.googleFitApi = new GoogleFitApiImpl(BPMeasurementsResultFragment.this.googleApiClient);
            }

            @Override
            public void onConnectionSuspended(int n) {
                BPMeasurementsResultFragment.this.googleFitApi = null;
            }
        });
        this.googleApiClient.registerConnectionFailedListener(BPMeasurementsResultFragment$$Lambda$1.lambdaFactory$(this));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n2 != -1) return;
        switch (n) {
            default: {
                return;
            }
            case 0: {
                this.onNoteSelected(EditNoteActivity.extractNoteFromIntent(intent));
                return;
            }
            case 1: 
        }
        this.onVisitorMeasurementSent();
    }

    public void onBackPressed() {
        Activity activity;
        block3: {
            block4: {
                block2: {
                    activity = this.getActivity();
                    if (activity == null) break block2;
                    if (!this.hasFinalMeasurement) break block3;
                    if (!this.needSelectLocationTag() || !this.isGooglePlayServicesAvailable) break block4;
                    this.showSelectLocationTagActivity();
                }
                return;
            }
            MeasurementHelper.BloodPressure.requestBPMeasurementsSync((Context)activity, CustomApplication.getApplication().getCurrentUserId());
            return;
        }
        this.onMeasurementCanceled((Context)activity);
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        if (!TextUtils.isEmpty((CharSequence)this.finalMeasurement.deviceId)) {
            return DataHelper.StatisticHelper.createGetStatisticLoader((Context)this.getActivity(), CustomApplication.getApplication().getCurrentUserId(), this.finalMeasurement.deviceId);
        }
        return null;
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        block3: {
            block4: {
                block2: {
                    menu2.clear();
                    if (!this.hasFinalMeasurement) break block2;
                    if (!this.isVisitorMode()) break block3;
                    if (!this.visitorMeasurementSent) break block4;
                    this.setHasOptionsMenu(false);
                }
                return;
            }
            menuInflater.inflate(2131886098, menu2);
            return;
        }
        menuInflater.inflate(2131886085, menu2);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968648, viewGroup, false);
        this.createDateTimeFormats();
        this.init((View)layoutInflater, bundle);
        return layoutInflater;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.disposables.clear();
    }

    public void onDetach() {
        super.onDetach();
        Activity activity = this.getActivity();
        if (activity != null) {
            this.stopMeasurement((Context)activity);
            if (this.receiverRegistered) {
                activity.unregisterReceiver((BroadcastReceiver)this.btStateReceiver);
                LocalBroadcastManager.getInstance((Context)activity).unregisterReceiver(this.measurementsReceiver);
                LocalBroadcastManager.getInstance((Context)activity).unregisterReceiver(this.measurementErrorReceiver);
                LocalBroadcastManager.getInstance((Context)activity).unregisterReceiver(this.deviceInfoReceiver);
                LocalBroadcastManager.getInstance((Context)activity).unregisterReceiver(this.batteryInfoReceiver);
                LocalBroadcastManager.getInstance((Context)activity).unregisterReceiver(this.deviceStateReceiver);
                this.receiverRegistered = false;
            }
        }
    }

    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onEngagementScreenClosed(boolean bl, boolean bl2) {
        Activity activity = this.getActivity();
        if (activity != null && this.settings.showPhoto.booleanValue() && !bl && !bl2 && this.errorCode == Integer.MIN_VALUE) {
            boolean bl3 = this.settings.useFlickr();
            boolean bl4 = this.settings.usePhotosFromDevice();
            boolean bl5 = bl3;
            boolean bl6 = bl4;
            if (!bl3) {
                bl5 = bl3;
                bl6 = bl4;
                if (!bl4) {
                    bl5 = true;
                    bl6 = true;
                }
            }
            if (this.isVisitorMode()) {
                bl6 = false;
                bl5 = true;
            }
            this.showPhotoSlideShow((Context)this.getActivity(), bl5, bl6);
        }
        new Handler().postDelayed(BPMeasurementsResultFragment$$Lambda$13.lambdaFactory$(this, activity, bl, bl2), 500L);
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            this.statistic = DataHelper.StatisticHelper.parseStatistic(cursor);
            return;
        }
        this.statistic = new Statistic(CustomApplication.getApplication().getCurrentUserId());
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onLocationChanged(QLatLng qLatLng) {
        Long l;
        block5: {
            block4: {
                Timber.d("onLocationChanged - %s", qLatLng);
                this.stopTrackingLocation();
                if (!this.settings.allowLocation.booleanValue()) break block4;
                this.finalMeasurement.latitude = qLatLng.getLatitude();
                this.finalMeasurement.longitude = qLatLng.getLongitude();
                this.setMeasurementTag(6);
                l = CustomApplication.getApplication().getCurrentUserId();
                if (this.getActivity() != null) break block5;
            }
            return;
        }
        BPMeasurement bPMeasurement = MeasurementHelper.BloodPressure.getMeasurementByTimestamp((Context)this.getActivity(), l, this.finalMeasurement.measureDate.getTime());
        Timber.d("saveMeasurement - %s", bPMeasurement);
        if (bPMeasurement != null) {
            Timber.d("deleting result - %d", MeasurementHelper.BloodPressure.deleteMeasurementFromCache((Context)this.getActivity(), l, bPMeasurement._id));
            MeasurementHelper.BloodPressure.addMeasurement((Context)this.getActivity(), l, this.finalMeasurement, false);
        }
        this.detectLocationTag(qLatLng).subscribe(BPMeasurementsResultFragment$$Lambda$10.lambdaFactory$(this), BPMeasurementsResultFragment$$Lambda$11.lambdaFactory$());
    }

    public void onLongPress(MotionEvent motionEvent) {
    }

    public void onMeasurementError() {
        if (this.getActivity() != null) {
            if (this.actionBar != null && !this.actionBar.isShowing()) {
                this.actionBar.show();
            }
            this.closeEngagementScreen();
        }
    }

    public void onNoteSelected(String string2) {
        this.note.setText((CharSequence)string2);
        this.finalMeasurement.note = string2;
        Activity activity = this.getActivity();
        Long l = CustomApplication.getApplication().getCurrentUserId();
        if (this.hasFinalMeasurement && activity != null && l != null) {
            MeasurementHelper.BloodPressure.changeMeasurementNote((Context)activity, l, this.finalMeasurement.measureDate.getTime(), string2, false);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Activity activity = this.getActivity();
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 2131821473: {
                if (activity != null) {
                    if (!this.isVisitorMode()) {
                        MeasurementHelper.BloodPressure.deleteMeasurement((Context)activity, CustomApplication.getApplication().getCurrentUserId(), this.finalMeasurement.measureDate.getTime());
                        if (this.googleApiClient.isConnected() && this.googleFitApi != null && CustomApplication.getApplication().getCurrentUserId() != null) {
                            this.googleFitApi.deleteBloodPressureMeasurements((Context)CustomApplication.getApplication(), this.finalMeasurement.measureDate.getTime(), CustomApplication.getApplication().getCurrentUserId()).subscribe();
                        }
                    }
                    activity.finish();
                }
                return true;
            }
            case 2131821496: 
        }
        if (activity != null) {
            activity.finish();
        }
        return true;
    }

    public void onPause() {
        super.onPause();
        Activity activity = this.getActivity();
        if (activity != null && activity.isFinishing()) {
            this.stopTrackingLocation();
        }
    }

    public void onResume() {
        super.onResume();
        Activity activity = this.getActivity();
        if (!this.receiverRegistered && activity != null) {
            activity.registerReceiver((BroadcastReceiver)this.btStateReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
            LocalBroadcastManager.getInstance((Context)activity).registerReceiver(this.deviceStateReceiver, new IntentFilter("com.qardio.bleservice.Notifications.DEVICE_STATUSES"));
            LocalBroadcastManager.getInstance((Context)activity).registerReceiver(this.measurementsReceiver, new IntentFilter("com.qardio.bleservice.Notifications.MEASUREMENT_VALUES"));
            LocalBroadcastManager.getInstance((Context)activity).registerReceiver(this.measurementErrorReceiver, new IntentFilter("com.qardio.bleservice.Notifications.MEASUREMENT_ERROR"));
            LocalBroadcastManager.getInstance((Context)activity).registerReceiver(this.deviceInfoReceiver, new IntentFilter("DEVICE_INFO"));
            this.receiverRegistered = true;
        }
        this.createDateTimeFormats();
        this.setDate();
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (this.hasFinalMeasurement) {
            bundle.putParcelable("final_measurement", (Parcelable)this.finalMeasurement);
        }
        bundle.putInt("measurement_number", this.measurementNumber);
        if (this.errorCode != Integer.MIN_VALUE) {
            bundle.putInt("error_code", this.errorCode);
        }
        super.onSaveInstanceState(bundle);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        this.isScrolling = true;
        this.scrollView.lock();
        if (f > 0.0f && this.bpResultChart.getTranslationX() - f < 0.0f) {
            f2 = this.bpResultChart.getTranslationX();
        } else {
            f2 = f;
            if (f < 0.0f) {
                f2 = f;
                if (this.bpResultTable.getTranslationX() - f > 0.0f) {
                    f2 = this.bpResultTable.getTranslationX();
                }
            }
        }
        this.shiftComponents(f2);
        return true;
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public void onStart() {
        super.onStart();
        this.googleApiClient.connect();
    }

    public void onStop() {
        super.onStop();
        this.googleApiClient.disconnect();
    }

    private class BTStateReceiver
    extends BroadcastReceiver {
        private BTStateReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            int n = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
            int n2 = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", 10);
            BPMeasurementsResultFragment.this.onBTStateChanged(n, n2);
        }
    }

    private class BatteryInfoReceiver
    extends BroadcastReceiver {
        private BatteryInfoReceiver() {
        }

        public void onReceive(Context context, Intent object) {
            if (object.getAction().equals("DEVICE_INFO") && !BPMeasurementsResultFragment.this.isDetached()) {
                String string2 = object.getStringExtra("DEVICE_INFO_TYPE");
                object = object.getStringExtra("DEVICE_INFO_VALUE");
                if (string2.equals("BATTERY_STATUS_VALUE")) {
                    BPMeasurementsResultFragment.this.batteryLevel = Integer.parseInt((String)object);
                    LocalBroadcastManager.getInstance(context).unregisterReceiver(BPMeasurementsResultFragment.this.batteryInfoReceiver);
                }
            }
        }
    }

    private class DeviceInfoReceiver
    extends BroadcastReceiver {
        private DeviceInfoReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("DEVICE_INFO") && BPMeasurementsResultFragment.this.isAdded() && intent.getStringExtra("DEVICE_INFO_TYPE").equals("SERIAL_NUMBER_VALUE")) {
                BPMeasurementsResultFragment.access$2200((BPMeasurementsResultFragment)BPMeasurementsResultFragment.this).deviceId = intent.getExtras().getString("DEVICE_INFO_VALUE", BPMeasurementsResultFragment.access$2200((BPMeasurementsResultFragment)BPMeasurementsResultFragment.this).deviceId);
                LocalBroadcastManager.getInstance(context).unregisterReceiver(BPMeasurementsResultFragment.this.deviceInfoReceiver);
                BPMeasurementsResultFragment.this.getLoaderManager().restartLoader(0, null, (LoaderManager.LoaderCallbacks)BPMeasurementsResultFragment.this);
                LocalBroadcastManager.getInstance(context).registerReceiver(BPMeasurementsResultFragment.this.batteryInfoReceiver, new IntentFilter("DEVICE_INFO"));
                MobileDeviceFactory.getBatteryStatus(context);
            }
        }
    }

    private class DeviceStateReceiver
    extends BroadcastReceiver {
        private DeviceStateReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("DEVICE_STATUS", 0) == 4 && !BPMeasurementsResultFragment.this.hasFinalMeasurement) {
                BPMeasurementsResultFragment.this.cancelMeasurement();
            }
        }
    }

    private class MeasurementErrorBroadcastReceiver
    extends BroadcastReceiver {
        private MeasurementErrorBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            int n = intent.getIntExtra("BP_ERROR_VALUE", 0);
            Timber.d("measurement error: %d", n);
            BPMeasurementsResultFragment.this.onMeasurementError(n);
        }
    }

    private class MeasurementsBroadcastReceiver
    extends BroadcastReceiver {
        private MeasurementsBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            int n = intent.getIntExtra("BP_DIA_VALUE", 0);
            int n2 = intent.getIntExtra("BP_PULSE_VALUE", 0);
            int n3 = intent.getIntExtra("BP_SYS_VALUE", 0);
            int n4 = intent.getIntExtra("BP_MEASUREMENT_STATUS", 0);
            boolean bl = intent.getBooleanExtra("BP_IHB_STATUS", false);
            boolean bl2 = intent.getBooleanExtra("BP_LASTREADING", false);
            Timber.d("Measurement from broadcast: dia=%d, sys=%d, pulse=%d, iHB=%s, lastReading=%s", n, n3, n2, bl, bl2);
            BPMeasurementsResultFragment.this.onChangedMeasurements(n3, n, n2, n4, bl, bl2);
        }
    }

}

