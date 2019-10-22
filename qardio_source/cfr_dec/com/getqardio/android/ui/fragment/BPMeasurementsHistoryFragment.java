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
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.view.ContextThemeWrapper
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.RadioGroup
 *  android.widget.RadioGroup$OnCheckedChangeListener
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
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
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.device_related_services.fit.GoogleFitUtils;
import com.getqardio.android.googlefit.GoogleFitApiImpl;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.shealth.ShealthDataHelper;
import com.getqardio.android.ui.activity.AddManualMeasurementActivity;
import com.getqardio.android.ui.activity.BPMeasurementsHistoryDetailsActivity;
import com.getqardio.android.ui.activity.SendHistoryActivity;
import com.getqardio.android.ui.adapter.BPMeasurementsListAdapter;
import com.getqardio.android.ui.adapter.DailyBPChartAdapter;
import com.getqardio.android.ui.adapter.DailyPulseChartAdapter;
import com.getqardio.android.ui.adapter.DateTimeFormatHelper;
import com.getqardio.android.ui.adapter.MonthlyBPChartAdapter;
import com.getqardio.android.ui.adapter.MonthlyPulseChartAdapter;
import com.getqardio.android.ui.adapter.WeeklyBPChartAdapter;
import com.getqardio.android.ui.adapter.WeeklyPulseChartAdapter;
import com.getqardio.android.ui.fragment.BPMeasurementsHistoryFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.BPMeasurementsHistoryFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.BPMeasurementsHistoryFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.BPMeasurementsHistoryFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.LocationFragment;
import com.getqardio.android.ui.widget.BPMinMaxPanel;
import com.getqardio.android.ui.widget.MeasurementsChart;
import com.getqardio.android.ui.widget.MeasurementsChartAdapter;
import com.getqardio.android.ui.widget.calendar.view.QardioCalendarView;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.analytics.BpAddManualMeasurementsAnalyticsTracker;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.ui.BackPanelListViewHelper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.qardio.ble.bpcollector.mobiledevice.BLEStatus;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BPMeasurementsHistoryFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor>,
SwipeRefreshLayout.OnRefreshListener,
View.OnClickListener,
BPMeasurementsListAdapter.OnDeleteListener,
BPMinMaxPanel.OnShownMonthChangedListener,
MeasurementsChart.OnChartScrollListener,
MeasurementsChart.OnFirstVisibleDateListener {
    private static final int[] DIA_GRADIENT_POSITIONS;
    private static final int[] GRADIENT_COLORS;
    private static final int[] SYS_GRADIENT_POSITIONS;
    private QardioCalendarView calendar;
    private Callback callback;
    private MeasurementsChart chart;
    private View chartContainer;
    private int chartPeriod;
    private boolean chartScrolled;
    private int chartType;
    private DailyBPChartAdapter dailyBPAdapter;
    private DailyPulseChartAdapter dailyPulseAdapter;
    private DateTimeFormatHelper dateTimeFormatHelper;
    private boolean displayListOptionsMenu;
    private CompositeDisposable disposables;
    private GoogleApiClient googleApiClient;
    private HealthDataStore healthDataStore;
    private boolean isViewer;
    private BPMeasurementsListAdapter listAdapter;
    private View locationFragmentContainer;
    private BPMinMaxPanel minMaxPanel;
    private MonthlyBPChartAdapter monthlyBPAdapter;
    private MonthlyPulseChartAdapter monthlyPulseAdapter;
    private NetworkListener networkListener = new NetworkListener();
    private SwipeRefreshLayout refreshLayout;
    private View userActivity;
    private WeeklyBPChartAdapter weeklyBPAdapter;
    private WeeklyPulseChartAdapter weeklyPulseAdapter;

    static {
        GRADIENT_COLORS = new int[]{-1019802, -28795, -866463, -9850842, -11878959};
        SYS_GRADIENT_POSITIONS = new int[]{180, 150, 130, 120, 40};
        DIA_GRADIENT_POSITIONS = new int[]{110, 90, 85, 80, 40};
    }

    public BPMeasurementsHistoryFragment() {
        this.disposables = new CompositeDisposable();
    }

    private void createCalendar(Cursor cursor) {
        Date date = new Date();
        this.calendar.init(date, MeasurementHelper.BloodPressure.getMeasurementDates(cursor));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private MeasurementsChartAdapter getCurrentChartAdapter() {
        if (this.chartType == 1) {
            switch (this.chartPeriod) {
                default: {
                    do {
                        return null;
                        break;
                    } while (true);
                }
                case 0: {
                    return this.dailyPulseAdapter;
                }
                case 1: {
                    return this.weeklyPulseAdapter;
                }
                case 2: 
            }
            return this.monthlyPulseAdapter;
        }
        switch (this.chartPeriod) {
            default: {
                return null;
            }
            case 0: {
                return this.dailyBPAdapter;
            }
            case 1: {
                return this.weeklyBPAdapter;
            }
            case 2: 
        }
        return this.monthlyBPAdapter;
    }

    private String getFriendEmail() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_MAIL")) {
            return bundle.getString("com.getqardio.android.argument.USER_MAIL");
        }
        return "";
    }

    private long getUserId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_ID")) {
            return bundle.getLong("com.getqardio.android.argument.USER_ID");
        }
        return -1L;
    }

    private void hideChart() {
        this.chartContainer.setVisibility(4);
        this.chartContainer.requestLayout();
    }

    private void hideLocation() {
        FragmentTransaction fragmentTransaction = this.getChildFragmentManager().beginTransaction();
        Fragment fragment = this.getChildFragmentManager().findFragmentById(2131820910);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        this.locationFragmentContainer.setVisibility(4);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void init(View view) {
        this.isViewer = this.getArguments().getBoolean("com.getqardio.android.argument.IS_VIEWER", true);
        this.chartType = 0;
        this.chartPeriod = -1;
        this.chartScrolled = false;
        this.healthDataStore = CustomApplication.getApplication().getHealthDataStore();
        this.locationFragmentContainer = view.findViewById(2131820910);
        this.chartContainer = view.findViewById(2131820913);
        this.chart = (MeasurementsChart)view.findViewById(2131820916);
        this.chart.setOnFirstVisibleDateListener(this);
        this.chart.setOnChartScrollListener(this);
        this.chart.setChartColor(-12147389);
        this.dailyPulseAdapter = new DailyPulseChartAdapter();
        this.weeklyPulseAdapter = new WeeklyPulseChartAdapter();
        this.monthlyPulseAdapter = new MonthlyPulseChartAdapter();
        this.monthlyBPAdapter = new MonthlyBPChartAdapter();
        this.weeklyBPAdapter = new WeeklyBPChartAdapter();
        this.dailyBPAdapter = new DailyBPChartAdapter();
        this.chart.setChartGradient(1, GRADIENT_COLORS, DIA_GRADIENT_POSITIONS);
        this.chart.setChartGradient(0, GRADIENT_COLORS, SYS_GRADIENT_POSITIONS);
        this.chart.setAdapter(this.dailyBPAdapter, 1);
        this.minMaxPanel = (BPMinMaxPanel)view.findViewById(2131820917);
        this.minMaxPanel.setOnShownMonthChangedListener(this);
        this.minMaxPanel.setOnTakeMeasurementClickListener(this);
        this.minMaxPanel.setIsViewer(this.isViewer);
        this.setupList(view);
        if (this.isViewer || !CustomApplication.getApplication().isGooglePlayServiceAvailableOrNonGoogleBuild()) {
            view.findViewById(2131820907).setVisibility(8);
            view.findViewById(2131820908).setVisibility(8);
        }
        this.userActivity = view.findViewById(2131820918);
        int n = CustomApplication.getApplication().getBpHistoryTab();
        if (this.isViewer) {
            n = 2131820905;
        }
        if (n == -1) {
            n = 2131820905;
        }
        RadioGroup radioGroup = (RadioGroup)view.findViewById(2131820903);
        radioGroup.check(n);
        this.showSelectedTab(n);
        radioGroup.setOnCheckedChangeListener(BPMeasurementsHistoryFragment$$Lambda$1.lambdaFactory$(this));
        radioGroup = ArrayAdapter.createFromResource((Context)view.getContext(), (int)2131755010, (int)2130968638);
        Spinner spinner = (Spinner)view.findViewById(2131820915);
        view.findViewById(2131820914).setOnClickListener(BPMeasurementsHistoryFragment$$Lambda$2.lambdaFactory$(spinner));
        spinner.setAdapter((SpinnerAdapter)radioGroup);
        radioGroup.setDropDownViewResource(2130968840);
        spinner.setDropDownWidth(-1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                BPMeasurementsHistoryFragment.this.setChartPeriod(n);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        radioGroup = Typeface.create((String)"sans-serif-light", (int)0);
        ((TextView)view.findViewById(2131820963)).setTypeface((Typeface)radioGroup);
        ((TextView)view.findViewById(2131820964)).setTypeface((Typeface)radioGroup);
        this.calendar = (QardioCalendarView)view.findViewById(2131820965);
    }

    static /* synthetic */ void lambda$init$1(Spinner spinner, View view) {
        spinner.performClick();
    }

    static /* synthetic */ boolean lambda$setupList$2(int n) {
        return true;
    }

    public static BPMeasurementsHistoryFragment newInstance(long l, String object, boolean bl) {
        Bundle bundle = new Bundle();
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        bundle.putBoolean("com.getqardio.android.argument.IS_VIEWER", bl);
        if (!TextUtils.isEmpty((CharSequence)object)) {
            bundle.putString("com.getqardio.android.argument.USER_MAIL", (String)object);
        }
        object = new BPMeasurementsHistoryFragment();
        object.setArguments(bundle);
        return object;
    }

    public static BPMeasurementsHistoryFragment newInstance(long l, boolean bl) {
        return BPMeasurementsHistoryFragment.newInstance(l, null, bl);
    }

    private void onChartDataChanged() {
        if (!this.chartScrolled) {
            this.chart.scrollToEnd();
        }
    }

    private void redrawMenu(boolean bl) {
        this.displayListOptionsMenu = bl;
        this.getActivity().invalidateOptionsMenu();
    }

    private void refreshMinMaxPanel() {
        long l = this.chart.getFirstVisibleDate();
        if (l != -1L) {
            this.updateFirstVisibleDate(l);
        }
    }

    private void requestFullHistorySync() {
        MeasurementHelper.BloodPressure.requestBPMeasurementsUpdate((Context)CustomApplication.getApplication(), this.getUserId());
    }

    private void setChartPeriod(int n) {
        if (this.chartPeriod != n) {
            this.chartPeriod = n;
            MeasurementsChartAdapter measurementsChartAdapter = this.getCurrentChartAdapter();
            if (measurementsChartAdapter != null) {
                this.chart.setAdapter(measurementsChartAdapter, 3);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setChartType(int n) {
        if (this.chartType != n) {
            MeasurementsChartAdapter measurementsChartAdapter;
            this.chartType = n;
            if (this.chartType == 1) {
                this.chart.clearChartGradients();
            } else {
                this.chart.setChartGradient(1, GRADIENT_COLORS, DIA_GRADIENT_POSITIONS);
                this.chart.setChartGradient(0, GRADIENT_COLORS, SYS_GRADIENT_POSITIONS);
            }
            if ((measurementsChartAdapter = this.getCurrentChartAdapter()) != null) {
                this.chart.setAdapter(measurementsChartAdapter, 0);
            }
        }
    }

    private void setHistoryTab(int n) {
        if (!this.isViewer) {
            CustomApplication.getApplication().setBpHistoryTab(n);
        }
    }

    private void setListInvisible() {
        this.refreshLayout.setRefreshing(false);
        this.refreshLayout.setVisibility(4);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setTitle(int n) {
        ActionBar actionBar = ActivityUtils.getActionBar(this.getActivity());
        if (actionBar != null) {
            String string2 = this.isViewer ? this.getFriendEmail() : this.getString(n);
            actionBar.setTitle(string2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setupList(View view) {
        this.refreshLayout = (SwipeRefreshLayout)view.findViewById(2131820911);
        view = (ListView)view.findViewById(2131820912);
        view.setSelector((Drawable)new ColorDrawable(0));
        Activity activity = this.getActivity();
        boolean bl = !this.isViewer;
        this.listAdapter = new BPMeasurementsListAdapter((Context)activity, bl);
        this.listAdapter.setOnDeleteListener(this);
        view.setAdapter((ListAdapter)this.listAdapter);
        this.dateTimeFormatHelper = new DateTimeFormatHelper(this.listAdapter);
        if (!this.isViewer) {
            new BackPanelListViewHelper((ListView)view).setCallbacks(BPMeasurementsHistoryFragment$$Lambda$3.lambdaFactory$());
        }
        view.setOnItemClickListener(BPMeasurementsHistoryFragment$$Lambda$4.lambdaFactory$(this));
        this.refreshLayout.setOnRefreshListener(this);
    }

    private void showBPChart() {
        this.refreshMinMaxPanel();
        this.setTitle(2131362190);
        this.chartContainer.setVisibility(0);
        this.setListInvisible();
        this.userActivity.setVisibility(4);
        this.hideLocation();
        this.setChartType(0);
        this.redrawMenu(false);
    }

    private void showList() {
        this.setTitle(2131362283);
        this.refreshLayout.setVisibility(0);
        this.userActivity.setVisibility(4);
        this.hideLocation();
        this.hideChart();
        this.redrawMenu(true);
    }

    private void showLocation() {
        Fragment fragment;
        this.setTitle(2131362310);
        Fragment fragment2 = fragment = this.getChildFragmentManager().findFragmentById(2131820910);
        if (fragment == null) {
            fragment2 = LocationFragment.newInstance(this.getUserId());
        }
        fragment = this.getChildFragmentManager().beginTransaction();
        fragment.replace(2131820910, fragment2);
        fragment.commit();
        this.locationFragmentContainer.setVisibility(0);
        this.userActivity.setVisibility(4);
        this.setListInvisible();
        this.hideChart();
        this.redrawMenu(false);
    }

    private void showPulseChart() {
        this.setTitle(2131362251);
        this.refreshMinMaxPanel();
        this.chartContainer.setVisibility(0);
        this.setListInvisible();
        this.userActivity.setVisibility(4);
        this.hideLocation();
        this.setChartType(1);
        this.redrawMenu(false);
    }

    private void showUserActivity() {
        this.setTitle(2131362094);
        this.userActivity.setVisibility(0);
        this.setListInvisible();
        this.hideLocation();
        this.hideChart();
        this.redrawMenu(false);
    }

    private void updateFirstVisibleDate(long l) {
        int n = Utils.getMonthNumber(l);
        this.minMaxPanel.setShownMonth(n);
    }

    /* synthetic */ void lambda$init$0(RadioGroup radioGroup, int n) {
        this.showSelectedTab(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$setupList$3(AdapterView adapterView, View view, int n, long l) {
        adapterView = this.getActivity();
        l = BPMeasurementsListAdapter.extractDate(view);
        if (adapterView != null && l != -1L) {
            long l2 = this.getUserId();
            boolean bl = !this.isViewer;
            this.startActivity(BPMeasurementsHistoryDetailsActivity.getStartIntent((Context)adapterView, l2, l, bl));
            this.getActivity().overridePendingTransition(2131034132, 2131034131);
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.requestFullHistorySync();
        this.getLoaderManager().initLoader(2, null, (LoaderManager.LoaderCallbacks)this);
        this.getLoaderManager().initLoader(3, null, (LoaderManager.LoaderCallbacks)this);
        this.getLoaderManager().initLoader(4, null, (LoaderManager.LoaderCallbacks)this);
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
        this.getLoaderManager().initLoader(5, null, (LoaderManager.LoaderCallbacks)this);
    }

    @Override
    public void onChartScroll(int n) {
        this.chartScrolled = true;
        this.chart.setOnChartScrollListener(null);
    }

    public void onClick(View view) {
        if (this.callback != null) {
            this.callback.onTakeMeasurementsClick();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.callback = (Callback)this.getParentFragment();
        this.googleApiClient = GoogleFitUtils.buildBloodPresureClient((Context)this.getActivity());
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: {
                return MeasurementHelper.BloodPressure.getMeasurementsLoader((Context)this.getActivity(), this.getUserId(), MeasurementHelper.BloodPressure.MEASUREMENTS_LIST_PROJECTION);
            }
            case 2: {
                return MeasurementHelper.BloodPressure.getDailyMeasurementsLoader((Context)this.getActivity(), this.getUserId(), MeasurementHelper.BloodPressure.PERIODIC_BP_MEASUREMENTS_PROJECTION);
            }
            case 3: {
                return MeasurementHelper.BloodPressure.getWeeklyMeasurementsLoader((Context)this.getActivity(), this.getUserId(), MeasurementHelper.BloodPressure.PERIODIC_BP_MEASUREMENTS_PROJECTION);
            }
            case 4: {
                return MeasurementHelper.BloodPressure.getMonthlyMeasurementsLoader((Context)this.getActivity(), this.getUserId(), MeasurementHelper.BloodPressure.PERIODIC_BP_MEASUREMENTS_PROJECTION);
            }
            case 5: 
        }
        return MeasurementHelper.BloodPressure.getMonthlyMeasurementsLoader((Context)this.getActivity(), this.getUserId(), MeasurementHelper.BloodPressure.MIN_MAX_AVG_BP_MEASUREMENTS_PROJECTION);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        boolean bl = true;
        menu2.clear();
        if (!this.displayListOptionsMenu) {
            menu2.clear();
            return;
        }
        if (!this.getArguments().getBoolean("com.getqardio.android.argument.IS_VIEWER", true)) {
            menuInflater.inflate(2131886083, menu2);
            int n = BLEStatus.getInstance((Context)this.getActivity()).getBleStatus();
            menu2 = menu2.findItem(2131821470);
            if (n == 66) {
                bl = false;
            }
            menu2.setVisible(bl);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.setHasOptionsMenu(true);
        layoutInflater = layoutInflater.cloneInContext((Context)new ContextThemeWrapper((Context)this.getActivity(), 2131493124)).inflate(2130968644, viewGroup, false);
        this.init((View)layoutInflater);
        return layoutInflater;
    }

    @Override
    public void onDelete(long l) {
        Object object = this.getActivity();
        if (object != null) {
            MeasurementHelper.BloodPressure.deleteMeasurement((Context)object, this.getUserId(), l);
            ShealthDataHelper.BpMeasurements.deleteMeasurement((Context)object, this.healthDataStore, l);
            if (this.googleApiClient != null && this.googleApiClient.isConnected() && this.getUserId() != -1L) {
                object = new GoogleFitApiImpl(this.googleApiClient);
                this.disposables.add(object.deleteBloodPressureMeasurements((Context)CustomApplication.getApplication(), l, this.getUserId()).subscribe());
            }
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.disposables.clear();
    }

    @Override
    public void onFirstVisibleDateChanged(long l) {
        this.updateFirstVisibleDate(l);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            default: {
                return;
            }
            case 1: {
                this.listAdapter.swapCursor(cursor);
                return;
            }
            case 2: {
                this.dailyPulseAdapter.setCursor(cursor);
                this.dailyBPAdapter.setCursor(cursor);
                this.onChartDataChanged();
                this.createCalendar(cursor);
                return;
            }
            case 3: {
                this.weeklyBPAdapter.setCursor(cursor);
                this.weeklyPulseAdapter.setCursor(cursor);
                this.onChartDataChanged();
                return;
            }
            case 4: {
                this.monthlyBPAdapter.setCursor(cursor);
                this.monthlyPulseAdapter.setCursor(cursor);
                this.onChartDataChanged();
                return;
            }
            case 5: 
        }
        this.minMaxPanel.setData(cursor);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            default: {
                return;
            }
            case 1: {
                this.listAdapter.swapCursor(null);
                return;
            }
            case 2: {
                this.dailyBPAdapter.setCursor(null);
                this.dailyPulseAdapter.setCursor(null);
                return;
            }
            case 3: {
                this.weeklyBPAdapter.setCursor(null);
                this.weeklyPulseAdapter.setCursor(null);
                return;
            }
            case 4: {
                this.monthlyBPAdapter.setCursor(null);
                this.monthlyPulseAdapter.setCursor(null);
                return;
            }
            case 5: 
        }
        this.minMaxPanel.setData(null);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean bl = super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()) {
            default: {
                return bl;
            }
            case 2131821471: {
                this.startActivity(SendHistoryActivity.getStartIntent((Context)this.getActivity(), this.getUserId()));
                this.getActivity().overridePendingTransition(2131034132, 2131034131);
                return bl;
            }
            case 2131821470: 
        }
        BpAddManualMeasurementsAnalyticsTracker.trackAddManualMeasurementClick((Context)this.getActivity(), BpAddManualMeasurementsAnalyticsTracker.Screen.HISTORY);
        AddManualMeasurementActivity.start((Context)this.getActivity(), this.getUserId(), 1);
        this.getActivity().overridePendingTransition(2131034132, 2131034131);
        return bl;
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).unregisterReceiver(this.networkListener);
        this.refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        this.requestFullHistorySync();
    }

    public void onResume() {
        super.onResume();
        this.dailyBPAdapter.changeLocale();
        this.dailyPulseAdapter.changeLocale();
        this.monthlyBPAdapter.changeLocale();
        this.monthlyPulseAdapter.changeLocale();
        this.weeklyBPAdapter.changeLocale();
        this.weeklyPulseAdapter.changeLocale();
        this.minMaxPanel.changeLocale();
        this.dateTimeFormatHelper.onUpdatePatterns();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).registerReceiver(this.networkListener, new IntentFilter("com.getqardio.android.NetworkNotification.BP_GET_FINISHED"));
    }

    @Override
    public void onShownMonthChanged(int n) {
        this.chart.setMonthVisible(n);
    }

    public void onStart() {
        super.onStart();
        this.googleApiClient.connect();
    }

    public void onStop() {
        super.onStop();
        this.googleApiClient.disconnect();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void showSelectedTab(int n) {
        String string2;
        String string3 = string2 = "";
        switch (n) {
            default: {
                string3 = string2;
                break;
            }
            case 2131820904: {
                this.setHistoryTab(2131820904);
                this.showBPChart();
                string3 = "Chart";
                break;
            }
            case 2131820905: {
                this.setHistoryTab(2131820905);
                this.showList();
                string3 = "QA history list";
                break;
            }
            case 2131820909: {
                this.setHistoryTab(2131820909);
                this.showPulseChart();
                string3 = "Heart Rate";
                break;
            }
            case 2131820906: {
                this.setHistoryTab(2131820906);
                this.showUserActivity();
                string3 = "Activity";
            }
            case 2131820908: {
                break;
            }
            case 2131820907: {
                this.setHistoryTab(2131820907);
                this.showLocation();
                string3 = "Places";
            }
        }
        AnalyticsScreenTracker.sendScreenWithMeasurementType((Context)this.getActivity(), string3, "bp");
    }

    public static interface Callback {
        public void onTakeMeasurementsClick();
    }

    private class NetworkListener
    extends BroadcastReceiver {
        private NetworkListener() {
        }

        public void onReceive(Context context, Intent intent) {
            BPMeasurementsHistoryFragment.this.refreshLayout.setRefreshing(false);
        }
    }

}

