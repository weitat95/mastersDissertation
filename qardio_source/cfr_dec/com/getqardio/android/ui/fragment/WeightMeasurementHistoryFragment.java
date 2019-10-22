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
 *  android.os.Bundle
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
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
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
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.PregnancyData;
import com.getqardio.android.device_related_services.fit.GoogleFitDataHelper;
import com.getqardio.android.mvp.qardio_base.measurement_details.view.QBMeasurementDetailsActivity;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.shealth.ShealthDataHelper;
import com.getqardio.android.ui.activity.AddManualMeasurementActivity;
import com.getqardio.android.ui.activity.InviteUserActivity;
import com.getqardio.android.ui.activity.PregnancyMeasurementDetailsActivity;
import com.getqardio.android.ui.adapter.ClaimMeasurementsAdapter;
import com.getqardio.android.ui.adapter.DailyDateHelper;
import com.getqardio.android.ui.adapter.DateTimeFormatHelper;
import com.getqardio.android.ui.adapter.MonthlyDateHelper;
import com.getqardio.android.ui.adapter.WeeklyDateHelper;
import com.getqardio.android.ui.adapter.WeightChartAdapter;
import com.getqardio.android.ui.adapter.WeightMeasurementsAdapter;
import com.getqardio.android.ui.fragment.SetGoalFragment;
import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment$$Lambda$5;
import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment$$Lambda$6;
import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment$$Lambda$7;
import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment$$Lambda$8;
import com.getqardio.android.ui.widget.MeasurementsChart;
import com.getqardio.android.ui.widget.MeasurementsChartAdapter;
import com.getqardio.android.ui.widget.calendar.view.QardioCalendarView;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.HelperUtils;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.analytics.WeightAddManualMeasurementsAnalyticsTracker;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.ui.BackPanelListViewHelper;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeightMeasurementHistoryFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor>,
ClaimMeasurementsAdapter.OnClaimOperationListener,
WeightMeasurementsAdapter.OnDeleteListener,
MeasurementsChart.OnChartScrollListener {
    private MenuItem addManualMeasurementMenuItem;
    private QardioCalendarView calendar;
    private MeasurementsChart chart;
    private View chartContainer;
    private int chartPeriod;
    private boolean chartScrolled;
    private int chartType;
    private ClaimMeasurementsAdapter claimMeasurementsAdapter;
    private ClaimNetworkListener claimNetworkListener;
    private SwipeRefreshLayout claimRefreshLayout;
    private WeightChartAdapter dailyWeightAdapter;
    private DateTimeFormatHelper dateTimeFormatHelper;
    private View goalContainer;
    private boolean isViewer;
    private WeightMeasurementsAdapter measurementsAdapter;
    private WeightChartAdapter monthlyWeightAdapter;
    private PregnancyData[] pregnancyHistory;
    private RadioGroup tabs;
    private View userActivity;
    private WeightChartAdapter weeklyWeightAdapter;
    private WeightNetworkListener weightNetworkListener = new WeightNetworkListener();
    private SwipeRefreshLayout weightRefreshLayout;

    public WeightMeasurementHistoryFragment() {
        this.claimNetworkListener = new ClaimNetworkListener();
    }

    static /* synthetic */ void access$lambda$0(WeightMeasurementHistoryFragment weightMeasurementHistoryFragment) {
        weightMeasurementHistoryFragment.requestFullHistorySync();
    }

    static /* synthetic */ void access$lambda$1(WeightMeasurementHistoryFragment weightMeasurementHistoryFragment) {
        weightMeasurementHistoryFragment.requestClaimMeasurementsSync();
    }

    private void deleteMeasurement(long l) {
        Activity activity = this.getActivity();
        if (activity != null) {
            long l2 = this.getUserId();
            MeasurementHelper.Claim.deleteMeasurement((Context)activity, l2, l);
            GoogleFitDataHelper.Weight.requestDeleteFromGoogleFit((Context)activity, l, l2);
            MeasurementHelper.Weight.deleteMeasurement((Context)activity, l2, l);
            ShealthDataHelper.WeightMeasurements.deleteWeightMeasurement((Context)activity, CustomApplication.getApplication().getHealthDataStore(), l);
        }
    }

    private MeasurementsChartAdapter getCurrentChartAdapter() {
        switch (this.chartPeriod) {
            default: {
                return null;
            }
            case 0: {
                return this.dailyWeightAdapter;
            }
            case 1: {
                return this.weeklyWeightAdapter;
            }
            case 2: 
        }
        return this.monthlyWeightAdapter;
    }

    private int getHeightUnit() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.HEIGHT_UNIT")) {
            return bundle.getInt("com.getqardio.android.argument.HEIGHT_UNIT");
        }
        return 0;
    }

    private PregnancyData getPregnancyForMeasurement(long l) {
        if (this.pregnancyHistory != null) {
            for (PregnancyData pregnancyData : this.pregnancyHistory) {
                if (pregnancyData.startDate.getTime() > l || pregnancyData.endDate != null && pregnancyData.endDate.getTime() < l) continue;
                return pregnancyData;
            }
        }
        return null;
    }

    private long getUserId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_ID")) {
            return bundle.getLong("com.getqardio.android.argument.USER_ID");
        }
        return -1L;
    }

    private String getUserName() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_NAME")) {
            return bundle.getString("com.getqardio.android.argument.USER_NAME");
        }
        return "";
    }

    private int getWeightUnit() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.WEIGHT_UNIT")) {
            return bundle.getInt("com.getqardio.android.argument.WEIGHT_UNIT");
        }
        return 0;
    }

    private void hideSetGoal() {
        FragmentTransaction fragmentTransaction = this.getChildFragmentManager().beginTransaction();
        Fragment fragment = this.getChildFragmentManager().findFragmentById(2131821442);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
        this.goalContainer.setVisibility(8);
    }

    private void init(View view) {
        this.dailyWeightAdapter = new WeightChartAdapter(new DailyDateHelper());
        this.dailyWeightAdapter.setChartNumber(1);
        this.weeklyWeightAdapter = new WeightChartAdapter(new WeeklyDateHelper());
        this.dailyWeightAdapter.setChartNumber(1);
        this.monthlyWeightAdapter = new WeightChartAdapter(new MonthlyDateHelper());
        this.dailyWeightAdapter.setChartNumber(1);
        this.chartContainer = view.findViewById(2131820913);
        this.userActivity = view.findViewById(2131821441);
        ListView listView = (ListView)view.findViewById(2131821434);
        this.weightRefreshLayout = (SwipeRefreshLayout)view.findViewById(2131821433);
        ListView listView2 = (ListView)view.findViewById(2131821440);
        this.claimRefreshLayout = (SwipeRefreshLayout)view.findViewById(2131821439);
        this.goalContainer = view.findViewById(2131821442);
        this.chart = (MeasurementsChart)view.findViewById(2131820916);
        this.chart.setOnChartScrollListener(this);
        this.chart.setChartColor(-12147389);
        this.chartPeriod = 0;
        this.chartType = -1;
        this.chartScrolled = false;
        this.chart.setAdapter(this.dailyWeightAdapter, 1);
        this.calendar = (QardioCalendarView)view.findViewById(2131820965);
        this.calendar.init(new Date(), new ArrayList<Calendar>());
        RadioGroup radioGroup = (RadioGroup)view.findViewById(2131821435);
        radioGroup.setOnCheckedChangeListener(WeightMeasurementHistoryFragment$$Lambda$1.lambdaFactory$(this));
        int n = CustomApplication.getApplication().getQbHistoryTab();
        int n2 = this.getArguments().getInt("com.getqardio.android.argument.SELECTED_TAB");
        if (n2 != -1) {
            n = n2;
        }
        this.tabs = (RadioGroup)view.findViewById(2131821425);
        if (this.isViewer) {
            this.tabs.findViewById(2131821428).setVisibility(8);
            this.tabs.findViewById(2131821429).setVisibility(8);
            this.tabs.findViewById(2131821432).setVisibility(8);
            this.tabs.findViewById(2131821431).setVisibility(8);
        }
        this.tabs.check(n);
        this.showSelectedTab(n);
        this.tabs.setOnCheckedChangeListener(WeightMeasurementHistoryFragment$$Lambda$2.lambdaFactory$(this));
        this.measurementsAdapter = new WeightMeasurementsAdapter((Context)this.getActivity(), this.getWeightUnit());
        this.measurementsAdapter.setOnDeleteListener(this);
        listView.setAdapter((ListAdapter)this.measurementsAdapter);
        listView.setOnItemClickListener(WeightMeasurementHistoryFragment$$Lambda$3.lambdaFactory$(this));
        if (!this.isViewer) {
            new BackPanelListViewHelper(listView).setCallbacks(WeightMeasurementHistoryFragment$$Lambda$4.lambdaFactory$());
        }
        this.weightRefreshLayout.setOnRefreshListener(WeightMeasurementHistoryFragment$$Lambda$5.lambdaFactory$(this));
        this.dateTimeFormatHelper = new DateTimeFormatHelper(this.measurementsAdapter);
        this.claimMeasurementsAdapter = new ClaimMeasurementsAdapter((Context)this.getActivity(), this.getWeightUnit(), this.getUserName());
        this.claimMeasurementsAdapter.setOnClaimOperationListener(this);
        listView2.setAdapter((ListAdapter)this.claimMeasurementsAdapter);
        new BackPanelListViewHelper(listView2).setCallbacks(WeightMeasurementHistoryFragment$$Lambda$6.lambdaFactory$());
        this.claimRefreshLayout.setOnRefreshListener(WeightMeasurementHistoryFragment$$Lambda$7.lambdaFactory$(this));
        listView = ArrayAdapter.createFromResource((Context)view.getContext(), (int)2131755010, (int)2130968638);
        listView2 = (Spinner)view.findViewById(2131820915);
        view.findViewById(2131820914).setOnClickListener(WeightMeasurementHistoryFragment$$Lambda$8.lambdaFactory$((Spinner)listView2));
        listView2.setAdapter((SpinnerAdapter)listView);
        listView2.setDropDownWidth(-1);
        listView.setDropDownViewResource(2130968840);
        listView2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                WeightMeasurementHistoryFragment.this.setChartPeriod(n);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        radioGroup.check(2131821436);
    }

    private void initCalendar(Cursor cursor) {
        this.calendar.init(new Date(), MeasurementHelper.Weight.getMeasurementDates(cursor));
    }

    static /* synthetic */ boolean lambda$init$3(int n) {
        return true;
    }

    static /* synthetic */ boolean lambda$init$4(int n) {
        return true;
    }

    static /* synthetic */ void lambda$init$5(Spinner spinner, View view) {
        spinner.performClick();
    }

    public static WeightMeasurementHistoryFragment newInstance(long l, String string2, int n, int n2, int n3) {
        WeightMeasurementHistoryFragment weightMeasurementHistoryFragment = new WeightMeasurementHistoryFragment();
        Bundle bundle = new Bundle(5);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        bundle.putInt("com.getqardio.android.argument.WEIGHT_UNIT", n);
        bundle.putInt("com.getqardio.android.argument.HEIGHT_UNIT", n2);
        bundle.putString("com.getqardio.android.argument.USER_NAME", string2);
        bundle.putInt("com.getqardio.android.argument.SELECTED_TAB", n3);
        weightMeasurementHistoryFragment.setArguments(bundle);
        return weightMeasurementHistoryFragment;
    }

    public static WeightMeasurementHistoryFragment newInstance(long l, String string2, int n, int n2, int n3, boolean bl) {
        WeightMeasurementHistoryFragment weightMeasurementHistoryFragment = new WeightMeasurementHistoryFragment();
        Bundle bundle = new Bundle(6);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        bundle.putInt("com.getqardio.android.argument.WEIGHT_UNIT", n);
        bundle.putInt("com.getqardio.android.argument.HEIGHT_UNIT", n2);
        bundle.putString("com.getqardio.android.argument.USER_NAME", string2);
        bundle.putInt("com.getqardio.android.argument.SELECTED_TAB", n3);
        bundle.putBoolean("com.getqardio.android.argument.IS_VIEWER", bl);
        weightMeasurementHistoryFragment.setArguments(bundle);
        return weightMeasurementHistoryFragment;
    }

    private void onChartDataChanged() {
        if (!this.chartScrolled) {
            this.chart.scrollToEnd();
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private static PregnancyData parsePregnancyData(Cursor object) {
        void var0_2;
        PregnancyData pregnancyData = new PregnancyData();
        pregnancyData.id = HelperUtils.getLong(object, "_id", (Long)0L);
        pregnancyData.startDate = DateUtils.getStartOfTheDay(new Date(HelperUtils.getLong(object, "start_date", (Long)0L)));
        pregnancyData.dueDate = new Date(HelperUtils.getLong(object, "due_date", (Long)0L));
        if (HelperUtils.isNull(object, "end_date")) {
            Object var0_1 = null;
        } else {
            Date date = new Date(HelperUtils.getLong(object, "end_date", (Long)0L));
        }
        pregnancyData.endDate = var0_2;
        return pregnancyData;
    }

    private void requestClaimMeasurementsSync() {
        CustomApplication customApplication = CustomApplication.getApplication();
        long l = this.getUserId();
        MeasurementHelper.Claim.requestClaimMeasurementsUpdate((Context)customApplication, l, DataHelper.DeviceIdHelper.getDeviceId((Context)customApplication, l));
    }

    private void requestFullHistorySync() {
        MeasurementHelper.Weight.requestWeightMeasurementsUpdate((Context)CustomApplication.getApplication(), this.getUserId());
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
            this.chartType = n;
            n = 1;
            switch (this.chartType) {
                case 0: {
                    n = 1;
                    break;
                }
                case 1: {
                    n = 2;
                    break;
                }
                case 2: {
                    n = 0;
                    break;
                }
            }
            this.dailyWeightAdapter.setChartNumber(n);
            this.weeklyWeightAdapter.setChartNumber(n);
            this.monthlyWeightAdapter.setChartNumber(n);
        }
    }

    private void setClaimListInvisible() {
        this.claimRefreshLayout.setRefreshing(false);
        this.claimRefreshLayout.setVisibility(8);
    }

    private void setTitle(int n) {
        this.setTitle(this.getString(n));
    }

    private void setTitle(String string2) {
        ActionBar actionBar = ActivityUtils.getActionBar(this.getActivity());
        if (actionBar != null) {
            if (this.isViewer) {
                string2 = this.getUserName();
            }
            actionBar.setTitle(string2);
        }
    }

    private void setWeightListInvisible() {
        this.weightRefreshLayout.setRefreshing(false);
        this.weightRefreshLayout.setVisibility(8);
    }

    private void showActivity() {
        this.setTitle(2131362174);
        this.chartContainer.setVisibility(4);
        this.setWeightListInvisible();
        this.setClaimListInvisible();
        this.userActivity.setVisibility(0);
        this.hideSetGoal();
    }

    private void showBaseMeasurementsList() {
        this.setTitle(2131362177);
        this.chartContainer.setVisibility(4);
        this.weightRefreshLayout.setVisibility(0);
        this.setClaimListInvisible();
        this.userActivity.setVisibility(8);
        this.hideSetGoal();
    }

    private void showChart() {
        this.setTitle(2131362175);
        this.chartContainer.setVisibility(0);
        this.setWeightListInvisible();
        this.setClaimListInvisible();
        this.userActivity.setVisibility(8);
        this.hideSetGoal();
    }

    private void showClaimList() {
        this.setTitle(2131362176);
        this.requestClaimMeasurementsSync();
        this.chartContainer.setVisibility(4);
        this.setWeightListInvisible();
        this.claimRefreshLayout.setVisibility(0);
        this.userActivity.setVisibility(8);
        this.hideSetGoal();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showSelectedTab(int n) {
        boolean bl = false;
        if (CustomApplication.getApplication().getQbHistoryTab() == 2131821428) {
            this.requestFullHistorySync();
        }
        boolean bl2 = bl;
        switch (n) {
            default: {
                bl2 = bl;
                break;
            }
            case 2131821426: {
                this.showChart();
                AnalyticsScreenTracker.sendScreenWithMeasurementType((Context)this.getActivity(), "Chart", "weight");
                bl2 = bl;
                break;
            }
            case 2131821427: {
                bl2 = true;
                this.showBaseMeasurementsList();
                AnalyticsScreenTracker.sendScreenWithMeasurementType((Context)this.getActivity(), "QB history list", "weight");
                break;
            }
            case 2131821428: {
                this.showClaimList();
                AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "Claim");
                bl2 = bl;
                break;
            }
            case 2131821430: {
                this.showActivity();
                AnalyticsScreenTracker.sendScreenWithMeasurementType((Context)this.getActivity(), "Activity", "weight");
                bl2 = bl;
                break;
            }
            case 2131821432: {
                this.showSetGoal();
                AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "Set goal");
                bl2 = bl;
            }
            case 2131821429: 
            case 2131821431: 
        }
        CustomApplication.getApplication().setQbHistoryTab(n);
        if (this.addManualMeasurementMenuItem != null) {
            MenuItem menuItem = this.addManualMeasurementMenuItem;
            boolean bl3 = bl2 && QardioBaseUtils.needsOnBoarding((Context)this.getActivity()) && !this.isViewer;
            menuItem.setVisible(bl3);
        }
    }

    private void showSetGoal() {
        Fragment fragment;
        this.setTitle(2131362179);
        Fragment fragment2 = fragment = this.getChildFragmentManager().findFragmentById(2131821442);
        if (fragment == null) {
            fragment2 = SetGoalFragment.newInstance(this.getUserId(), this.getWeightUnit(), this.getHeightUnit());
        }
        fragment = this.getChildFragmentManager().beginTransaction();
        fragment.replace(2131821442, fragment2);
        fragment.commit();
        this.chartContainer.setVisibility(4);
        this.setWeightListInvisible();
        this.setClaimListInvisible();
        this.userActivity.setVisibility(8);
        this.goalContainer.setVisibility(0);
    }

    /* synthetic */ void lambda$init$0(RadioGroup radioGroup, int n) {
        switch (n) {
            default: {
                return;
            }
            case 2131821437: {
                this.chart.setChartColor(-11823149);
                this.setChartType(0);
                return;
            }
            case 2131821438: {
                this.chart.setChartColor(-872393);
                this.setChartType(1);
                return;
            }
            case 2131821436: 
        }
        this.chart.setChartColor(-12147389);
        this.setChartType(2);
    }

    /* synthetic */ void lambda$init$1(RadioGroup radioGroup, int n) {
        this.showSelectedTab(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$init$2(AdapterView adapterView, View object, int n, long l) {
        adapterView = this.getActivity();
        long l2 = WeightMeasurementsAdapter.extractDate(object);
        if (adapterView != null && l2 != -1L) {
            PregnancyData pregnancyData = this.getPregnancyForMeasurement(l2);
            if (pregnancyData != null && !this.isViewer) {
                this.startActivity(PregnancyMeasurementDetailsActivity.getStartIntent((Context)adapterView, this.getUserId(), this.getWeightUnit(), pregnancyData.id, new Date(l2)));
            } else {
                long l3 = this.getUserId();
                int n2 = this.getWeightUnit();
                boolean bl = !this.isViewer;
                this.startActivity(QBMeasurementDetailsActivity.getStartIntent((Context)adapterView, l3, l2, n2, bl));
            }
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
        this.getLoaderManager().initLoader(6, null, (LoaderManager.LoaderCallbacks)this);
        this.getLoaderManager().initLoader(7, null, (LoaderManager.LoaderCallbacks)this);
    }

    @Override
    public void onChartScroll(int n) {
        this.chartScrolled = true;
        this.chart.setOnChartScrollListener(null);
    }

    @Override
    public void onClaim(int n) {
        MeasurementHelper.Claim.claimMeasurement((Context)this.getActivity(), this.getUserId(), n, this.getUserName());
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.isViewer = this.getArguments().getBoolean("com.getqardio.android.argument.IS_VIEWER", false);
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: {
                return MeasurementHelper.Weight.getMeasurementsLoader((Context)this.getActivity(), this.getUserId(), null);
            }
            case 2: {
                return MeasurementHelper.Weight.getDailyMeasurementsLoader((Context)this.getActivity(), this.getUserId(), MeasurementHelper.Weight.PERIODIC_WEIGHT_MEASUREMENTS_PROJECTION);
            }
            case 3: {
                return MeasurementHelper.Weight.getWeeklyMeasurementsLoader((Context)this.getActivity(), this.getUserId(), MeasurementHelper.Weight.PERIODIC_WEIGHT_MEASUREMENTS_PROJECTION);
            }
            case 4: {
                return MeasurementHelper.Weight.getMonthlyMeasurementsLoader((Context)this.getActivity(), this.getUserId(), MeasurementHelper.Weight.PERIODIC_WEIGHT_MEASUREMENTS_PROJECTION);
            }
            case 5: {
                return MeasurementHelper.Claim.getAllClaimMeasurements((Context)this.getActivity(), this.getUserId(), MeasurementHelper.Claim.CLAIM_MEASUREMENTS_PROJECTION);
            }
            case 6: 
        }
        return MeasurementHelper.Weight.getPregnancyHistory((Context)this.getActivity(), this.getUserId());
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        boolean bl = false;
        menuInflater.inflate(2131886100, menu2);
        this.addManualMeasurementMenuItem = menu2.findItem(2131821497);
        if (QardioBaseUtils.needsOnBoarding((Context)this.getActivity()) && !this.isViewer) {
            menu2 = this.addManualMeasurementMenuItem;
            if (this.tabs.getCheckedRadioButtonId() == 2131821427) {
                bl = true;
            }
            menu2.setVisible(bl);
            return;
        }
        this.addManualMeasurementMenuItem.setVisible(false);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.setHasOptionsMenu(true);
        layoutInflater = layoutInflater.cloneInContext((Context)new ContextThemeWrapper((Context)this.getActivity(), 2131493124)).inflate(2130968856, viewGroup, false);
        this.init((View)layoutInflater);
        return layoutInflater;
    }

    @Override
    public void onDeleteClaim(long l) {
        this.deleteMeasurement(l);
    }

    @Override
    public void onDeleteMeasurement(long l) {
        this.deleteMeasurement(l);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onLoadFinished(Loader<Cursor> arrpregnancyData, Cursor cursor) {
        int n;
        block10: {
            switch (arrpregnancyData.getId()) {
                default: {
                    return;
                }
                case 1: {
                    this.measurementsAdapter.swapCursor(cursor);
                    return;
                }
                case 2: {
                    this.dailyWeightAdapter.setCursor(cursor, this.getWeightUnit());
                    this.initCalendar(cursor);
                    this.onChartDataChanged();
                    return;
                }
                case 3: {
                    this.weeklyWeightAdapter.setCursor(cursor, this.getWeightUnit());
                    this.onChartDataChanged();
                    return;
                }
                case 4: {
                    this.monthlyWeightAdapter.setCursor(cursor, this.getWeightUnit());
                    this.onChartDataChanged();
                    return;
                }
                case 5: {
                    this.claimMeasurementsAdapter.swapCursor(cursor);
                    return;
                }
                case 6: {
                    arrpregnancyData = new PregnancyData[cursor.getCount()];
                    n = 0;
                    if (cursor == null) return;
                    if (cursor.getCount() > 0 && cursor.moveToFirst()) break block10;
                }
            }
            return;
        }
        do {
            arrpregnancyData[n] = WeightMeasurementHistoryFragment.parsePregnancyData(cursor);
            if (!cursor.moveToNext()) {
                this.pregnancyHistory = arrpregnancyData;
                return;
            }
            ++n;
        } while (true);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            default: {
                return;
            }
            case 1: {
                this.measurementsAdapter.swapCursor(null);
                return;
            }
            case 2: {
                this.dailyWeightAdapter.setCursor(null, this.getWeightUnit());
                return;
            }
            case 3: {
                this.weeklyWeightAdapter.setCursor(null, this.getWeightUnit());
                return;
            }
            case 4: {
                this.monthlyWeightAdapter.setCursor(null, this.getWeightUnit());
                return;
            }
            case 5: 
        }
        this.claimMeasurementsAdapter.swapCursor(null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131821497: {
                WeightAddManualMeasurementsAnalyticsTracker.trackAddManualMeasurementClick((Context)this.getActivity(), WeightAddManualMeasurementsAnalyticsTracker.Screen.HISTORY);
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
        LocalBroadcastManager.getInstance((Context)this.getActivity()).unregisterReceiver(this.weightNetworkListener);
        LocalBroadcastManager.getInstance((Context)this.getActivity()).unregisterReceiver(this.claimNetworkListener);
        this.weightRefreshLayout.setRefreshing(false);
        this.claimRefreshLayout.setRefreshing(false);
    }

    public void onResume() {
        super.onResume();
        this.dailyWeightAdapter.changeLocale();
        this.weeklyWeightAdapter.changeLocale();
        this.monthlyWeightAdapter.changeLocale();
        this.dateTimeFormatHelper.onUpdatePatterns();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).registerReceiver(this.weightNetworkListener, new IntentFilter("com.getqardio.android.NetworkNotification.WEIGHT_GET_FINISHED"));
        LocalBroadcastManager.getInstance((Context)this.getActivity()).registerReceiver(this.claimNetworkListener, new IntentFilter("com.getqardio.android.NetworkNotification.CLAIM_GET_FINISHED"));
    }

    private class ClaimNetworkListener
    extends BroadcastReceiver {
        private ClaimNetworkListener() {
        }

        public void onReceive(Context context, Intent intent) {
            WeightMeasurementHistoryFragment.this.claimRefreshLayout.setRefreshing(false);
        }
    }

    private class WeightNetworkListener
    extends BroadcastReceiver {
        private WeightNetworkListener() {
        }

        public void onReceive(Context context, Intent intent) {
            WeightMeasurementHistoryFragment.this.weightRefreshLayout.setRefreshing(false);
        }
    }

}

