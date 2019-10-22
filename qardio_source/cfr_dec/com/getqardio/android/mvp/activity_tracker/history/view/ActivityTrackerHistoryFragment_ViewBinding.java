/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.LinearLayout
 *  android.widget.ProgressBar
 *  android.widget.TextView
 */
package com.getqardio.android.mvp.activity_tracker.history.view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.mvp.activity_tracker.history.view.ActivityTrackerHistoryFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class ActivityTrackerHistoryFragment_ViewBinding
implements Unbinder {
    private ActivityTrackerHistoryFragment target;

    public ActivityTrackerHistoryFragment_ViewBinding(ActivityTrackerHistoryFragment activityTrackerHistoryFragment, View view) {
        this.target = activityTrackerHistoryFragment;
        activityTrackerHistoryFragment.calendarView = Utils.findRequiredViewAsType(view, 2131820821, "field 'calendarView'", MaterialCalendarView.class);
        activityTrackerHistoryFragment.stepsChart = Utils.findRequiredViewAsType(view, 2131820850, "field 'stepsChart'", BarChart.class);
        activityTrackerHistoryFragment.totalSteps = Utils.findRequiredViewAsType(view, 2131820855, "field 'totalSteps'", TextView.class);
        activityTrackerHistoryFragment.stepsProgressGoalLayout = Utils.findRequiredViewAsType(view, 2131820856, "field 'stepsProgressGoalLayout'", ProgressBar.class);
        activityTrackerHistoryFragment.motivationMessageTitle = Utils.findRequiredViewAsType(view, 2131820831, "field 'motivationMessageTitle'", TextView.class);
        activityTrackerHistoryFragment.motivationMessageBody = Utils.findRequiredViewAsType(view, 2131820832, "field 'motivationMessageBody'", TextView.class);
        activityTrackerHistoryFragment.motivationCalendarMessageTitle = Utils.findRequiredViewAsType(view, 2131820822, "field 'motivationCalendarMessageTitle'", TextView.class);
        activityTrackerHistoryFragment.motivationCalendarMessageBody = Utils.findRequiredViewAsType(view, 2131820823, "field 'motivationCalendarMessageBody'", TextView.class);
        activityTrackerHistoryFragment.chartXAxisStart = Utils.findRequiredViewAsType(view, 2131820852, "field 'chartXAxisStart'", TextView.class);
        activityTrackerHistoryFragment.chartXAxisCenter = Utils.findRequiredViewAsType(view, 2131820853, "field 'chartXAxisCenter'", TextView.class);
        activityTrackerHistoryFragment.chartXAxisEnd = Utils.findRequiredViewAsType(view, 2131820854, "field 'chartXAxisEnd'", TextView.class);
        activityTrackerHistoryFragment.activityTrackedStepsNoData = Utils.findRequiredViewAsType(view, 2131820851, "field 'activityTrackedStepsNoData'", LinearLayout.class);
        activityTrackerHistoryFragment.stepsCardMonthTitle = Utils.findRequiredViewAsType(view, 2131820849, "field 'stepsCardMonthTitle'", TextView.class);
        activityTrackerHistoryFragment.currentStepsGoal = Utils.findRequiredViewAsType(view, 2131820847, "field 'currentStepsGoal'", TextView.class);
    }
}

