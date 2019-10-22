/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.widget.LinearLayout
 *  android.widget.ProgressBar
 *  android.widget.TextView
 */
package com.getqardio.android.mvp.activity_tracker.history.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.getqardio.android.googlefit.ActivityDataBucket;
import com.getqardio.android.googlefit.DataBucket;
import com.getqardio.android.googlefit.GoogleClientObservable;
import com.getqardio.android.googlefit.GoogleClientObserver;
import com.getqardio.android.googlefit.GoogleFitApi;
import com.getqardio.android.googlefit.GoogleFitApiImpl;
import com.getqardio.android.googlefit.StepDataBucket;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerDetailFromHistoryActivity;
import com.getqardio.android.mvp.activity_tracker.history.model.local.ActivityTrackedGroupedByDay;
import com.getqardio.android.mvp.activity_tracker.history.view.ActivityTrackerHistoryFragment$$Lambda$1;
import com.getqardio.android.mvp.activity_tracker.history.view.ActivityTrackerHistoryFragment$$Lambda$2;
import com.getqardio.android.mvp.activity_tracker.history.view.ActivityTrackerHistoryFragment$$Lambda$3;
import com.getqardio.android.mvp.activity_tracker.history.view.ActivityTrackerHistoryFragment$$Lambda$4;
import com.getqardio.android.mvp.activity_tracker.util.StepsXAxisValueFormatter;
import com.getqardio.android.mvp.common.ui.widget.CustomXAxisForStepsChartRenderer;
import com.getqardio.android.mvp.common.ui.widget.RoundedBarchart;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BaseDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class ActivityTrackerHistoryFragment
extends Fragment
implements GoogleClientObserver {
    @BindView
    LinearLayout activityTrackedStepsNoData;
    @BindView
    MaterialCalendarView calendarView;
    @BindView
    TextView chartXAxisCenter;
    @BindView
    TextView chartXAxisEnd;
    @BindView
    TextView chartXAxisStart;
    private CompositeDisposable compositeDisposable;
    @BindView
    TextView currentStepsGoal;
    HashMap<Integer, ActivityTrackedGroupedByDay> dayHashMap = new HashMap();
    private Dialog dialog;
    private GoogleClientObservable googleClientObservable;
    private GoogleFitApi googleFitApi;
    private boolean[] hasDataTohigligthXAxisDots;
    @BindView
    TextView motivationCalendarMessageBody;
    @BindView
    TextView motivationCalendarMessageTitle;
    @BindView
    TextView motivationMessageBody;
    @BindView
    TextView motivationMessageTitle;
    @BindView
    TextView stepsCardMonthTitle;
    @BindView
    BarChart stepsChart;
    @BindView
    ProgressBar stepsProgressGoalLayout;
    @BindView
    TextView totalSteps;

    /*
     * Enabled aggressive block sorting
     */
    private void convert(Pair<List<StepDataBucket>, List<ActivityDataBucket>> object) {
        Object object2 = (List)((Pair)object).first;
        Object object3 = (List)((Pair)object).second;
        Calendar calendar = Calendar.getInstance();
        object2 = object2.iterator();
        while (object2.hasNext()) {
            StepDataBucket stepDataBucket = (StepDataBucket)object2.next();
            calendar.setTimeInMillis(stepDataBucket.getStartTimeStamp());
            object = this.dayHashMap.get(calendar.get(5));
            if (object != null) {
                ((ActivityTrackedGroupedByDay)object).realmSet$endTimestamp(stepDataBucket.getEndTimeStamp());
            } else {
                object = new ActivityTrackedGroupedByDay();
                ((ActivityTrackedGroupedByDay)object).realmSet$goalActivity(30);
                ((ActivityTrackedGroupedByDay)object).realmSet$goalSteps(5000);
                ((ActivityTrackedGroupedByDay)object).realmSet$startTimestamp(stepDataBucket.getStartTimeStamp());
                ((ActivityTrackedGroupedByDay)object).realmSet$endTimestamp(stepDataBucket.getEndTimeStamp());
                this.dayHashMap.put(calendar.get(5), (ActivityTrackedGroupedByDay)object);
            }
            ((ActivityTrackedGroupedByDay)object).realmSet$totalSteps(((ActivityTrackedGroupedByDay)object).realmGet$totalSteps() + (long)stepDataBucket.getSteps());
        }
        object3 = object3.iterator();
        while (object3.hasNext()) {
            object2 = (ActivityDataBucket)object3.next();
            if (!GoogleFitApiImpl.isSupportedActivity(((ActivityDataBucket)object2).getActivityType())) continue;
            calendar.setTimeInMillis(((DataBucket)object2).getStartTimeStamp());
            object = this.dayHashMap.get(calendar.get(5));
            if (object != null) {
                ((ActivityTrackedGroupedByDay)object).realmSet$endTimestamp(((DataBucket)object2).getEndTimeStamp());
            } else {
                object = new ActivityTrackedGroupedByDay();
                ((ActivityTrackedGroupedByDay)object).realmSet$goalActivity(30);
                ((ActivityTrackedGroupedByDay)object).realmSet$goalSteps(5000);
                ((ActivityTrackedGroupedByDay)object).realmSet$startTimestamp(((DataBucket)object2).getStartTimeStamp());
                ((ActivityTrackedGroupedByDay)object).realmSet$endTimestamp(((DataBucket)object2).getEndTimeStamp());
                this.dayHashMap.put(calendar.get(5), (ActivityTrackedGroupedByDay)object);
            }
            ((ActivityTrackedGroupedByDay)object).realmSet$endTimestamp(((DataBucket)object2).getEndTimeStamp());
            ((ActivityTrackedGroupedByDay)object).realmSet$totalSecondsOfActivity((long)((double)((ActivityTrackedGroupedByDay)object).realmGet$totalSecondsOfActivity() + Math.ceil((float)((ActivityDataBucket)object2).getDuration() / 1000.0f)));
        }
        return;
    }

    private int getDayFromTimestamp(long l) {
        Date date = new Date(l);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(5);
    }

    public static ActivityTrackerHistoryFragment newInstance() {
        return new ActivityTrackerHistoryFragment();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showMotivationMessage(int n) {
        int n2 = (int)((float)n / 30.0f * 100.0f);
        if (n == 0) {
            this.motivationCalendarMessageTitle.setText(2131362112);
            this.motivationCalendarMessageBody.setText(2131362107);
            return;
        } else {
            if (n2 > 1 && n2 < 50) {
                this.motivationCalendarMessageTitle.setText(2131362114);
                this.motivationCalendarMessageBody.setText((CharSequence)String.format(this.getString(2131362109), n));
                return;
            }
            if (n2 >= 50 && n2 < 75) {
                this.motivationCalendarMessageTitle.setText(2131362115);
                this.motivationCalendarMessageBody.setText((CharSequence)String.format(this.getString(2131362109), n));
                return;
            }
            if (n2 >= 75 && n2 < 100) {
                this.motivationCalendarMessageTitle.setText(2131362116);
                this.motivationCalendarMessageBody.setText((CharSequence)String.format(this.getString(2131362109), n));
                return;
            }
            if (n2 != 100) return;
            {
                this.motivationCalendarMessageTitle.setText(2131362113);
                this.motivationCalendarMessageBody.setText(2131362108);
                return;
            }
        }
    }

    public void dismissDialog() {
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    /* synthetic */ void lambda$null$0(CalendarDay calendarDay, Pair object) throws Exception {
        this.convert((Pair<List<StepDataBucket>, List<ActivityDataBucket>>)object);
        int n = 0;
        object = ((List)((Pair)object).first).iterator();
        while (object.hasNext()) {
            n += ((StepDataBucket)object.next()).getSteps();
        }
        this.showTotalSteps(n);
        this.showStepsChart(new ArrayList<ActivityTrackedGroupedByDay>(this.dayHashMap.values()), calendarDay.getCalendar().getTime());
        this.dayHashMap.clear();
        this.dismissDialog();
    }

    /* synthetic */ void lambda$onConnected$3(Pair object) throws Exception {
        this.convert((Pair<List<StepDataBucket>, List<ActivityDataBucket>>)object);
        int n = 0;
        object = ((List)((Pair)object).first).iterator();
        while (object.hasNext()) {
            n += ((StepDataBucket)object.next()).getSteps();
        }
        this.showTotalSteps(n);
        this.showStepsChart(new ArrayList<ActivityTrackedGroupedByDay>(this.dayHashMap.values()), this.calendarView.getCurrentDate().getCalendar().getTime());
        this.dayHashMap.clear();
        this.dismissDialog();
    }

    /* synthetic */ void lambda$showStepsChart$1(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
        this.showDialog();
        this.compositeDisposable.add(this.googleFitApi.fetchMonthHistory(calendarDay.getCalendar().getTimeInMillis()).subscribe(ActivityTrackerHistoryFragment$$Lambda$4.lambdaFactory$(this, calendarDay)));
    }

    /* synthetic */ void lambda$showStepsChart$2(MaterialCalendarView materialCalendarView, CalendarDay calendarDay, boolean bl) {
        this.startActivity(ActivityTrackerDetailFromHistoryActivity.start((Context)this.getActivity(), calendarDay.getDate().getTime()));
        this.getActivity().overridePendingTransition(2131034132, 2131034131);
    }

    @Override
    public void onConnected(GoogleFitApi googleFitApi) {
        this.googleFitApi = googleFitApi;
        this.compositeDisposable.add(googleFitApi.fetchMonthHistory(this.calendarView.getCurrentDate().getCalendar().getTimeInMillis()).subscribe(ActivityTrackerHistoryFragment$$Lambda$3.lambdaFactory$(this)));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.compositeDisposable = new CompositeDisposable();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968623, viewGroup, false);
        ButterKnife.bind(this, (View)layoutInflater);
        this.stepsProgressGoalLayout.setVisibility(8);
        this.currentStepsGoal.setVisibility(8);
        this.motivationMessageTitle.setText(this.getText(2131362110));
        this.motivationMessageBody.setText(this.getText(2131362111));
        viewGroup = this.getParentFragment();
        if (viewGroup != null && viewGroup instanceof GoogleClientObservable) {
            this.googleClientObservable = (GoogleClientObservable)viewGroup;
            return layoutInflater;
        }
        if (this.getActivity() instanceof GoogleClientObservable) {
            this.googleClientObservable = (GoogleClientObservable)this.getActivity();
            return layoutInflater;
        }
        throw new RuntimeException("not an instance of GoogleClientObservable");
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.googleClientObservable.unregisterForGoogleClientChanges(this);
        this.compositeDisposable.dispose();
        this.compositeDisposable.clear();
    }

    @Override
    public void onDisconneced() {
        this.compositeDisposable.dispose();
        this.compositeDisposable.clear();
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.googleClientObservable.registerForGoogleClientChanges(this);
    }

    public void showDialog() {
        this.dialog = new Dialog((Context)this.getActivity());
        this.dialog.setCancelable(true);
        this.dialog.getWindow().setLayout(-1, -1);
        this.dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
        this.dialog.requestWindowFeature(1);
        this.dialog.setCancelable(true);
        View view = this.dialog.getLayoutInflater().inflate(2130968694, null, false);
        ((ProgressBar)view.findViewById(2131821032)).getIndeterminateDrawable().setColorFilter(this.getResources().getColor(2131689548), PorterDuff.Mode.MULTIPLY);
        this.dialog.setContentView(view);
        this.dialog.show();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void showStepsChart(List<ActivityTrackedGroupedByDay> object, Date object2) {
        int n;
        int n2 = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date)object2);
        object2 = new SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(calendar.getTime());
        this.stepsCardMonthTitle.setText((CharSequence)object2);
        if (object.size() <= 0) {
            this.activityTrackedStepsNoData.setVisibility(0);
        } else {
            this.activityTrackedStepsNoData.setVisibility(4);
        }
        int n3 = calendar.getActualMaximum(5);
        this.hasDataTohigligthXAxisDots = new boolean[n3 + 1];
        object2 = new ArrayList<BarEntry>();
        for (n = 0; n < n3; ++n) {
            object2.add(new BarEntry(n, 0.0f));
        }
        ArrayList<CalendarDay> arrayList = new ArrayList<CalendarDay>();
        ArrayList<CalendarDay> arrayList2 = new ArrayList<CalendarDay>();
        n = 0;
        do {
            if (n >= object.size()) {
                this.calendarView.setOnMonthChangedListener(ActivityTrackerHistoryFragment$$Lambda$1.lambdaFactory$(this));
                this.calendarView.setOnDateChangedListener(ActivityTrackerHistoryFragment$$Lambda$2.lambdaFactory$(this));
                this.calendarView.setSelectionMode(2);
                this.calendarView.addDecorators(new TodayDecorator(), new DisableDateDecorator(), new GoalDecorator(arrayList), new NonGoalDecorator(arrayList2));
                object = new BarDataSet((List<BarEntry>)object2, "");
                ((BaseDataSet)object).setColor(this.getActivity().getResources().getColor(2131689548));
                ((BaseDataSet)object).setDrawValues(false);
                object2 = new BarData(new IBarDataSet[]{object});
                ((BarData)object2).setBarWidth(0.5f);
                this.stepsChart.setRenderer(new RoundedBarchart(this.stepsChart, this.stepsChart.getAnimator(), this.stepsChart.getViewPortHandler()));
                this.stepsChart.setData(object2);
                this.stepsChart.setXAxisRenderer(new CustomXAxisForStepsChartRenderer(this.stepsChart.getViewPortHandler(), this.stepsChart.getXAxis(), this.stepsChart.getTransformer(((BaseDataSet)object).getAxisDependency())));
                this.stepsChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                this.stepsChart.getXAxis().setValueFormatter(new StepsXAxisValueFormatter(this.hasDataTohigligthXAxisDots));
                this.stepsChart.getAxisRight().setDrawLabels(false);
                this.stepsChart.getAxisRight().setDrawAxisLine(false);
                this.stepsChart.getXAxis().setDrawGridLines(false);
                this.stepsChart.getXAxis().setDrawAxisLine(false);
                this.stepsChart.getXAxis().setLabelCount(24);
                this.stepsChart.getAxisLeft().setLabelCount(4);
                this.stepsChart.getAxisLeft().setDrawAxisLine(false);
                this.stepsChart.getLegend().setEnabled(false);
                ((BarData)this.stepsChart.getData()).setHighlightEnabled(false);
                this.stepsChart.setScaleEnabled(false);
                this.stepsChart.setDescription(null);
                this.stepsChart.getAxisLeft().setDrawZeroLine(false);
                this.stepsChart.getAxisLeft().setAxisMinimum(0.0f);
                this.stepsChart.getAxisRight().setEnabled(false);
                this.stepsChart.invalidate();
                this.showMotivationMessage(n2);
                this.chartXAxisStart.setText(2131362454);
                this.chartXAxisCenter.setText(2131362453);
                this.chartXAxisEnd.setText((CharSequence)String.valueOf(calendar.getActualMaximum(5)));
                return;
            }
            ActivityTrackedGroupedByDay activityTrackedGroupedByDay = (ActivityTrackedGroupedByDay)object.get(n);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(activityTrackedGroupedByDay.realmGet$startTimestamp());
            n3 = this.getDayFromTimestamp(activityTrackedGroupedByDay.realmGet$startTimestamp()) - 1;
            long l = activityTrackedGroupedByDay.realmGet$totalSteps();
            object2.add(new BarEntry(n3, l));
            this.hasDataTohigligthXAxisDots[n3] = true;
            Date date = new Date(activityTrackedGroupedByDay.realmGet$startTimestamp());
            Timber.d(calendar2.get(5) + " total duration = " + activityTrackedGroupedByDay.realmGet$totalSecondsOfActivity() / 60L, new Object[0]);
            Timber.d(calendar2.get(5) + " total steps = " + activityTrackedGroupedByDay.realmGet$totalSteps(), new Object[0]);
            if (activityTrackedGroupedByDay.realmGet$goalActivity() != 0 && activityTrackedGroupedByDay.realmGet$goalSteps() != 0 && TimeUnit.SECONDS.toMinutes(activityTrackedGroupedByDay.realmGet$totalSecondsOfActivity()) >= (long)activityTrackedGroupedByDay.realmGet$goalActivity() || activityTrackedGroupedByDay.realmGet$totalSteps() >= (long)activityTrackedGroupedByDay.realmGet$goalSteps()) {
                arrayList.add(CalendarDay.from(date));
                ++n2;
            } else {
                arrayList2.add(CalendarDay.from(date));
            }
            ++n;
        } while (true);
    }

    public void showTotalSteps(long l) {
        if (l == 0L) {
            this.totalSteps.setText((CharSequence)"--");
            return;
        }
        String string2 = NumberFormat.getInstance(Locale.getDefault()).format(l);
        this.totalSteps.setText((CharSequence)string2);
    }

    private class DisableDateDecorator
    implements DayViewDecorator {
        DisableDateDecorator() {
        }

        @Override
        public void decorate(DayViewFacade dayViewFacade) {
            dayViewFacade.setDaysDisabled(true);
        }

        @Override
        public boolean shouldDecorate(CalendarDay calendarDay) {
            return calendarDay.isAfter(CalendarDay.from(new Date()));
        }
    }

    private class GoalDecorator
    implements DayViewDecorator {
        private final Drawable backgroudnDrawable;
        private final List<CalendarDay> daysGoalAccomplished;

        GoalDecorator(List<CalendarDay> list) {
            this.daysGoalAccomplished = list;
            this.backgroudnDrawable = ActivityTrackerHistoryFragment.this.getResources().getDrawable(2130837606);
        }

        @Override
        public void decorate(DayViewFacade dayViewFacade) {
            dayViewFacade.setBackgroundDrawable(this.backgroudnDrawable);
            dayViewFacade.setDaysDisabled(false);
        }

        @Override
        public boolean shouldDecorate(CalendarDay calendarDay) {
            return this.daysGoalAccomplished.contains(calendarDay);
        }
    }

    private class NonGoalDecorator
    implements DayViewDecorator {
        private final Drawable backgroudnDrawable;
        private final List<CalendarDay> daysGoalAccomplished;

        NonGoalDecorator(List<CalendarDay> list) {
            this.daysGoalAccomplished = list;
            this.backgroudnDrawable = ActivityTrackerHistoryFragment.this.getResources().getDrawable(2130837607);
        }

        @Override
        public void decorate(DayViewFacade dayViewFacade) {
            dayViewFacade.setBackgroundDrawable(this.backgroudnDrawable);
            dayViewFacade.setDaysDisabled(false);
        }

        @Override
        public boolean shouldDecorate(CalendarDay calendarDay) {
            return this.daysGoalAccomplished.contains(calendarDay) && calendarDay.isBefore(CalendarDay.from(new Date()));
        }
    }

    private class TodayDecorator
    implements DayViewDecorator {
        private final Drawable backgroundDrawable;
        private final CalendarDay today = CalendarDay.today();

        TodayDecorator() {
            this.backgroundDrawable = ActivityTrackerHistoryFragment.this.getResources().getDrawable(2130837605);
        }

        @Override
        public void decorate(DayViewFacade dayViewFacade) {
            dayViewFacade.setBackgroundDrawable(this.backgroundDrawable);
        }

        @Override
        public boolean shouldDecorate(CalendarDay calendarDay) {
            return this.today.equals(calendarDay);
        }
    }

}

