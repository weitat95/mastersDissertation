/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.ObjectAnimator
 *  android.animation.TimeInterpolator
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.Fragment
 *  android.content.Context
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
 *  android.view.animation.BounceInterpolator
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.ProgressBar
 *  android.widget.TextView
 */
package com.getqardio.android.mvp.activity_tracker.today.view;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.googlefit.ActivityDataBucket;
import com.getqardio.android.googlefit.GoogleClientObservable;
import com.getqardio.android.googlefit.GoogleClientObserver;
import com.getqardio.android.googlefit.GoogleFitApi;
import com.getqardio.android.googlefit.GoogleFitApiImpl;
import com.getqardio.android.googlefit.StepDataBucket;
import com.getqardio.android.mvp.activity.Util.ActivityUtils;
import com.getqardio.android.mvp.activity_tracker.goals.view.ActivityTrackerAddGoalsActivity;
import com.getqardio.android.mvp.activity_tracker.today.view.ActivityTrackerTodayFragment$$Lambda$1;
import com.getqardio.android.mvp.activity_tracker.today.view.ActivityTrackerTodayFragment$$Lambda$10;
import com.getqardio.android.mvp.activity_tracker.today.view.ActivityTrackerTodayFragment$$Lambda$2;
import com.getqardio.android.mvp.activity_tracker.today.view.ActivityTrackerTodayFragment$$Lambda$3;
import com.getqardio.android.mvp.activity_tracker.today.view.ActivityTrackerTodayFragment$$Lambda$4;
import com.getqardio.android.mvp.activity_tracker.today.view.ActivityTrackerTodayFragment$$Lambda$5;
import com.getqardio.android.mvp.activity_tracker.today.view.ActivityTrackerTodayFragment$$Lambda$6;
import com.getqardio.android.mvp.activity_tracker.today.view.ActivityTrackerTodayFragment$$Lambda$7;
import com.getqardio.android.mvp.activity_tracker.today.view.ActivityTrackerTodayFragment$$Lambda$8;
import com.getqardio.android.mvp.activity_tracker.today.view.ActivityTrackerTodayFragment$$Lambda$9;
import com.getqardio.android.mvp.activity_tracker.util.StepsXAxisValueFormatter;
import com.getqardio.android.mvp.common.ui.widget.CustomXAxisForStepsChartRenderer;
import com.getqardio.android.mvp.common.ui.widget.RoundedBarchart;
import com.getqardio.android.mvp.common.ui.widget.TimeLineView;
import com.getqardio.android.ui.activity.AddManualMeasurementActivity;
import com.getqardio.android.utils.analytics.WeightAddManualMeasurementsAnalyticsTracker;
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
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class ActivityTrackerTodayFragment
extends Fragment
implements GoogleClientObserver {
    @BindView
    LinearLayout activityTrackedStepsNoData;
    @BindView
    FloatingActionButton addNewBPActivity;
    @BindView
    FloatingActionButton addNewGoal;
    @BindView
    FloatingActionButton addNewWeightToActivity;
    @BindView
    TextView chartXAxisCenter;
    @BindView
    TextView chartXAxisEnd;
    @BindView
    TextView chartXAxisStart;
    private CompositeDisposable compositeDisposable;
    @BindView
    TextView currentActivityTargetView;
    @BindView
    TextView currentStepGoalView;
    @BindView
    ProgressBar dailyActiveGoalBar;
    private Dialog dialog;
    @BindView
    LinearLayout emptyTimeline;
    @BindView
    ImageView goalCircle;
    @BindView
    TextView goalMotivationBody;
    @BindView
    TextView goalMotivationTitle;
    @BindView
    ImageView goalStar1;
    @BindView
    ImageView goalStar2;
    @BindView
    ImageView goalStar3;
    private GoogleClientObservable googleClientObservable;
    @BindView
    FloatingActionsMenu menuAddNewActivity;
    @BindView
    FrameLayout menuFloatingActionButtonLayout;
    @BindView
    TextView minutesActive;
    @BindView
    BarChart stepsChart;
    @BindView
    ProgressBar stepsGoalProgressBar;
    @BindView
    LinearLayout stepsProgressGoalLayout;
    @BindView
    TimeLineView timeLineView;
    @BindView
    CardView timelineContainer;
    @BindView
    TextView totalSteps;
    private View view;

    private void animateActivityProgress(ProgressBar progressBar, int n) {
        progressBar = ObjectAnimator.ofInt((Object)progressBar, (String)"secondaryProgress", (int[])new int[]{n});
        progressBar.setDuration(1500L);
        progressBar.setInterpolator((TimeInterpolator)new BounceInterpolator());
        progressBar.start();
    }

    private void animateStepProgress(ProgressBar progressBar, int n) {
        progressBar = ObjectAnimator.ofInt((Object)progressBar, (String)"progress", (int[])new int[]{n});
        progressBar.setDuration(1500L);
        progressBar.setInterpolator((TimeInterpolator)new BounceInterpolator());
        progressBar.start();
    }

    private int getHourFromTimestamp(long l) {
        Date date = new Date(l);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(11);
    }

    private long getUserId() {
        return CustomApplication.getApplication().getCurrentUserId();
    }

    public static ActivityTrackerTodayFragment newInstance(long l) {
        Bundle bundle = new Bundle(1);
        bundle.putLong("com.getqardio.android.argument.ACTIVITY_DATE", l);
        ActivityTrackerTodayFragment activityTrackerTodayFragment = new ActivityTrackerTodayFragment();
        activityTrackerTodayFragment.setArguments(bundle);
        return activityTrackerTodayFragment;
    }

    private boolean sameDay(long l) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(new Date(l));
        calendar2.setTime(new Date());
        return calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6);
    }

    private void setupStepsChart(boolean[] arrbl, List<BarEntry> object) {
        Object object2 = this.getActivity();
        if (object2 != null) {
            object = new BarDataSet((List<BarEntry>)object, "");
            ((BaseDataSet)object).setColor(object2.getResources().getColor(2131689548));
            ((BaseDataSet)object).setDrawValues(false);
            object2 = new BarData(new IBarDataSet[]{object});
            ((BarData)object2).setBarWidth(0.5f);
            this.stepsChart.setRenderer(new RoundedBarchart(this.stepsChart, this.stepsChart.getAnimator(), this.stepsChart.getViewPortHandler()));
            this.stepsChart.getAxisLeft().setDrawAxisLine(false);
            this.stepsChart.getAxisLeft().setDrawZeroLine(false);
            this.stepsChart.getAxisLeft().setAxisMinimum(0.0f);
            this.stepsChart.getLegend().setEnabled(false);
            this.stepsChart.setScaleEnabled(false);
            this.stepsChart.setDescription(null);
            this.stepsChart.setData(object2);
            ((BarData)this.stepsChart.getData()).setHighlightEnabled(false);
            this.stepsChart.getXAxis().setLabelCount(24);
            this.stepsChart.getXAxis().setDrawGridLines(false);
            this.stepsChart.getXAxis().setDrawAxisLine(false);
            this.stepsChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            this.stepsChart.getXAxis().setValueFormatter(new StepsXAxisValueFormatter(arrbl));
            this.stepsChart.getAxisRight().setDrawLabels(false);
            this.stepsChart.getAxisRight().setEnabled(false);
            this.stepsChart.getAxisRight().setDrawAxisLine(false);
            this.stepsChart.setXAxisRenderer(new CustomXAxisForStepsChartRenderer(this.stepsChart.getViewPortHandler(), this.stepsChart.getXAxis(), this.stepsChart.getTransformer(((BaseDataSet)object).getAxisDependency())));
            this.stepsChart.invalidate();
            this.chartXAxisStart.setText(2131362160);
            this.chartXAxisCenter.setText(2131362159);
            this.chartXAxisEnd.setText(2131362158);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showMotivationMessage(int n) {
        if (n == 0) {
            this.goalMotivationTitle.setText(2131362125);
            this.goalMotivationBody.setText(2131362117);
            return;
        } else {
            if (n > 0 && n < 50) {
                this.goalMotivationTitle.setText(2131362128);
                this.goalMotivationBody.setText(2131362120);
                return;
            }
            if (n == 50) {
                this.goalMotivationTitle.setText(2131362129);
                this.goalMotivationBody.setText(2131362121);
                return;
            }
            if (n > 50 && n < 100) {
                this.goalMotivationTitle.setText(2131362130);
                this.goalMotivationBody.setText(2131362122);
                return;
            }
            if (n == 100) {
                this.goalMotivationTitle.setText(2131362126);
                this.goalMotivationBody.setText(2131362118);
                return;
            }
            if (n <= 100) return;
            {
                this.goalMotivationTitle.setText(2131362127);
                this.goalMotivationBody.setText(2131362119);
                return;
            }
        }
    }

    public void dismissDialog() {
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    @OnClick
    public void goToAddManualBPMeasurement() {
        AddManualMeasurementActivity.start((Context)this.getActivity(), this.getUserId(), 1);
        this.menuAddNewActivity.collapse();
    }

    @OnClick
    public void goToAddManualWeightMeasurement() {
        WeightAddManualMeasurementsAnalyticsTracker.trackAddManualMeasurementClick((Context)this.getActivity(), WeightAddManualMeasurementsAnalyticsTracker.Screen.LAST_MEASUREMENT);
        AddManualMeasurementActivity.start((Context)this.getActivity(), this.getUserId(), 2);
        this.menuAddNewActivity.collapse();
    }

    @OnClick
    public void goToAddNewGoal() {
        ActivityTrackerAddGoalsActivity.start((Context)this.getActivity(), 6);
        this.menuAddNewActivity.collapse();
    }

    public void hideEditGoalIconIfNotToday(long l) {
        if (!this.sameDay(l)) {
            this.currentActivityTargetView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            this.currentActivityTargetView.setClickable(false);
            this.currentStepGoalView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            this.currentStepGoalView.setClickable(false);
        }
    }

    /* synthetic */ void lambda$onConnected$0(List list) throws Exception {
        long l = 0L;
        for (ActivityDataBucket activityDataBucket : list) {
            if (!GoogleFitApiImpl.isSupportedActivity(activityDataBucket.getActivityType())) continue;
            l += activityDataBucket.getDuration();
        }
        l = TimeUnit.MILLISECONDS.toMinutes(l);
        Timber.d("totalMinutes = " + l, new Object[0]);
        this.showTotalMinutesAndProgress((int)l, 30);
        this.showTimeLine(list);
    }

    /* synthetic */ void lambda$onConnected$1(List list) throws Exception {
        Timber.d("steps bucket = " + list.size(), new Object[0]);
        this.showStepsChart(list);
    }

    /* synthetic */ void lambda$onConnected$2(List object) throws Exception {
        Timber.d("steps bucket = " + object.size(), new Object[0]);
        int n = 0;
        object = object.iterator();
        while (object.hasNext()) {
            n += ((StepDataBucket)object.next()).getSteps();
        }
        this.showTotalSteps(n, 5000);
        this.dismissDialog();
    }

    /* synthetic */ void lambda$onConnected$3(List list) throws Exception {
        Timber.d("steps bucket = " + list.size(), new Object[0]);
        this.showStepsChart(list);
    }

    /* synthetic */ void lambda$onConnected$4(Integer n) throws Exception {
        this.showTotalSteps(n.intValue(), 5000);
    }

    /* synthetic */ void lambda$onConnected$5(List list) throws Exception {
        long l = 0L;
        for (ActivityDataBucket activityDataBucket : list) {
            if (!GoogleFitApiImpl.isSupportedActivity(activityDataBucket.getActivityType())) continue;
            l += activityDataBucket.getDuration();
        }
        Timber.d("totalMinutes = " + (l /= 60000L), new Object[0]);
        this.showTotalMinutesAndProgress((int)l, 30);
        this.showTimeLine(list);
        this.dismissDialog();
    }

    @Override
    public void onConnected(GoogleFitApi googleFitApi) {
        this.showDialog();
        if (this.getArguments().containsKey("com.getqardio.android.argument.ACTIVITY_DATE") && !ActivityUtils.isSameDay(this.getArguments().getLong("com.getqardio.android.argument.ACTIVITY_DATE"), System.currentTimeMillis())) {
            long l = this.getArguments().getLong("com.getqardio.android.argument.ACTIVITY_DATE");
            long l2 = l + 86400000L;
            Timber.d("start Date = " + DateFormat.getDateTimeInstance().format(new Date(l)), new Object[0]);
            Timber.d("end Date = " + DateFormat.getDateTimeInstance().format(new Date(l2)), new Object[0]);
            this.compositeDisposable.add(googleFitApi.fetchDayHistory(l, l2).subscribe(ActivityTrackerTodayFragment$$Lambda$1.lambdaFactory$(this), ActivityTrackerTodayFragment$$Lambda$2.lambdaFactory$()));
            this.compositeDisposable.add(googleFitApi.fetchDayStepsTimeline(l, l2).subscribe(ActivityTrackerTodayFragment$$Lambda$3.lambdaFactory$(this)));
            this.compositeDisposable.add(googleFitApi.fetchDayStepsTimeline(l, l2).subscribe(ActivityTrackerTodayFragment$$Lambda$4.lambdaFactory$(this), ActivityTrackerTodayFragment$$Lambda$5.lambdaFactory$()));
            return;
        }
        this.compositeDisposable.add(googleFitApi.fetchCurrentDayStepsTimeline().subscribe(ActivityTrackerTodayFragment$$Lambda$6.lambdaFactory$(this)));
        this.compositeDisposable.add(googleFitApi.fetchCurrentDaySteps().subscribe(ActivityTrackerTodayFragment$$Lambda$7.lambdaFactory$(this), ActivityTrackerTodayFragment$$Lambda$8.lambdaFactory$()));
        this.compositeDisposable.add(googleFitApi.fetchCurrentDayActivity().subscribe(ActivityTrackerTodayFragment$$Lambda$9.lambdaFactory$(this), ActivityTrackerTodayFragment$$Lambda$10.lambdaFactory$()));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.compositeDisposable = new CompositeDisposable();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(2130968629, viewGroup, false);
        ButterKnife.bind(this, this.view);
        this.hideEditGoalIconIfNotToday(this.getArguments().getLong("com.getqardio.android.argument.ACTIVITY_DATE"));
        layoutInflater = this.getParentFragment();
        if (layoutInflater != null && layoutInflater instanceof GoogleClientObservable) {
            this.googleClientObservable = (GoogleClientObservable)layoutInflater;
            do {
                return this.view;
                break;
            } while (true);
        }
        if (!(this.getActivity() instanceof GoogleClientObservable)) throw new RuntimeException("not an instance of GoogleClientObservable");
        this.googleClientObservable = (GoogleClientObservable)this.getActivity();
        return this.view;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.compositeDisposable.dispose();
        this.compositeDisposable.clear();
        if (this.googleClientObservable != null) {
            this.googleClientObservable.unregisterForGoogleClientChanges(this);
        }
        this.dismissDialog();
        this.dialog = null;
    }

    @Override
    public void onDisconneced() {
        this.compositeDisposable.dispose();
        this.compositeDisposable.clear();
    }

    public void onResume() {
        super.onResume();
        long l = this.getArguments().getLong("com.getqardio.android.argument.ACTIVITY_DATE");
        if (this.timelineContainer != null) {
            this.timelineContainer.invalidate();
        }
        if (ActivityUtils.isSameDay(l, System.currentTimeMillis())) {
            // empty if block
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.googleClientObservable.registerForGoogleClientChanges(this);
        this.menuAddNewActivity.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener(){

            @Override
            public void onMenuCollapsed() {
                ActivityTrackerTodayFragment.this.menuFloatingActionButtonLayout.setBackgroundColor(ContextCompat.getColor((Context)ActivityTrackerTodayFragment.this.getActivity(), 2131689608));
            }

            @Override
            public void onMenuExpanded() {
                ActivityTrackerTodayFragment.this.menuFloatingActionButtonLayout.setBackgroundColor(ContextCompat.getColor((Context)ActivityTrackerTodayFragment.this.getActivity(), 2131689557));
            }
        });
        this.menuAddNewActivity.setVisibility(8);
    }

    public void showDialog() {
        this.dialog = new Dialog((Context)this.getActivity());
        this.dialog.getWindow().setLayout(-1, -1);
        this.dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
        this.dialog.requestWindowFeature(1);
        this.dialog.setCancelable(true);
        View view = this.dialog.getLayoutInflater().inflate(2130968694, null, false);
        ((ProgressBar)view.findViewById(2131821032)).getIndeterminateDrawable().setColorFilter(this.getResources().getColor(2131689548), PorterDuff.Mode.MULTIPLY);
        this.dialog.setContentView(view);
        this.dialog.show();
    }

    @OnClick
    public void showPopupForDailyActiveGoal() {
    }

    @OnClick
    public void showPopupForDailyStepsGoal() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public void showStepsChart(List<StepDataBucket> list) {
        int n;
        if (list.size() <= 0) {
            this.activityTrackedStepsNoData.setVisibility(0);
            this.stepsProgressGoalLayout.setVisibility(8);
        } else {
            this.stepsProgressGoalLayout.setVisibility(0);
            this.activityTrackedStepsNoData.setVisibility(8);
        }
        boolean[] arrbl = new boolean[24];
        ArrayList<BarEntry> arrayList = new ArrayList<BarEntry>();
        for (n = 0; n < 24; ++n) {
            arrayList.add(new BarEntry(n, 0.0f));
        }
        n = 0;
        do {
            if (n >= list.size()) {
                this.setupStepsChart(arrbl, arrayList);
                return;
            }
            StepDataBucket stepDataBucket = list.get(n);
            int n2 = this.getHourFromTimestamp(stepDataBucket.getStartTimeStamp());
            long l = stepDataBucket.getSteps();
            arrayList.add(new BarEntry(n2, l));
            arrbl[n2] = true;
            ++n;
        } while (true);
    }

    public void showTimeLine(List<ActivityDataBucket> list) {
        boolean bl = false;
        int n = 0;
        do {
            if (n >= list.size() || (bl = GoogleFitApiImpl.isSupportedActivity(list.get(n).getActivityType()))) {
                if (list.size() <= 0 || !bl) break;
                this.timeLineView.setActivitiesForTimeline(list);
                this.timeLineView.setTotalMinutesForBaseTimeLine(0L);
                this.emptyTimeline.setVisibility(8);
                this.timeLineView.setVisibility(0);
                return;
            }
            ++n;
        } while (true);
        this.timeLineView.setVisibility(8);
        this.emptyTimeline.setVisibility(0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void showTotalMinutesAndProgress(int n, int n2) {
        if (n2 <= 0) {
            return;
        }
        if (n2 > 0 && this.getActivity() != null) {
            String string2 = String.format("%s %s", NumberFormat.getInstance(Locale.getDefault()).format(n2), this.getString(2131362098));
            this.currentActivityTargetView.setText((CharSequence)string2);
        }
        n2 = Math.round(n * 100 / n2);
        if (n <= 0) {
            this.currentActivityTargetView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            this.currentActivityTargetView.setClickable(false);
        } else if (this.sameDay(this.getArguments().getLong("com.getqardio.android.argument.ACTIVITY_DATE"))) {
            this.currentActivityTargetView.setClickable(true);
        }
        this.minutesActive.setText((CharSequence)String.valueOf(n));
        if (n2 <= 100) {
            this.goalCircle.setImageResource(2130837620);
            this.goalStar1.setVisibility(4);
            this.goalStar2.setVisibility(4);
            this.goalStar3.setVisibility(4);
            this.animateActivityProgress(this.dailyActiveGoalBar, n2);
            if (n2 == 100) {
                this.goalStar1.setVisibility(0);
                this.goalCircle.setImageResource(2130837619);
            }
        } else if (n2 <= 200) {
            this.goalStar1.setVisibility(0);
            this.animateActivityProgress(this.dailyActiveGoalBar, 100);
            this.goalCircle.setImageResource(2130837619);
            if (n2 == 200) {
                this.goalStar2.setVisibility(0);
            }
        } else {
            this.goalStar1.setVisibility(0);
            this.goalStar2.setVisibility(0);
            this.animateActivityProgress(this.dailyActiveGoalBar, 100);
            this.goalCircle.setImageResource(2130837619);
            if (n2 >= 300) {
                this.goalStar3.setVisibility(0);
                this.animateActivityProgress(this.dailyActiveGoalBar, 100);
            }
        }
        this.showMotivationMessage(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void showTotalSteps(long l, int n) {
        String string2;
        if (n > 0 && this.getActivity() != null) {
            string2 = String.format("%s %s", NumberFormat.getInstance(Locale.getDefault()).format(n), this.getString(2131362131));
            this.currentStepGoalView.setText((CharSequence)string2);
        }
        if (l == 0L) {
            this.totalSteps.setText((CharSequence)"--");
            this.stepsGoalProgressBar.setProgress(0);
            this.currentStepGoalView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            this.currentStepGoalView.setClickable(false);
            return;
        } else {
            string2 = NumberFormat.getInstance(Locale.getDefault()).format(l);
            this.totalSteps.setText((CharSequence)string2);
            if (n == 0) {
                this.stepsGoalProgressBar.setVisibility(4);
                return;
            }
            n = Math.round(100L * l / (long)n);
            this.animateStepProgress(this.stepsGoalProgressBar, n);
            this.stepsGoalProgressBar.setVisibility(0);
            if (!this.sameDay(this.getArguments().getLong("com.getqardio.android.argument.ACTIVITY_DATE"))) return;
            {
                this.currentStepGoalView.setClickable(true);
                return;
            }
        }
    }

}

