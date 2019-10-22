/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.ProgressBar
 *  android.widget.TextView
 */
package com.getqardio.android.mvp.activity_tracker.today.view;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.getqardio.android.mvp.activity_tracker.today.view.ActivityTrackerTodayFragment;
import com.getqardio.android.mvp.common.ui.widget.TimeLineView;
import com.github.mikephil.charting.charts.BarChart;

public class ActivityTrackerTodayFragment_ViewBinding
implements Unbinder {
    private ActivityTrackerTodayFragment target;
    private View view2131820817;
    private View view2131820847;
    private View view2131820877;
    private View view2131820878;
    private View view2131820879;

    public ActivityTrackerTodayFragment_ViewBinding(final ActivityTrackerTodayFragment activityTrackerTodayFragment, View view) {
        this.target = activityTrackerTodayFragment;
        View view2 = Utils.findRequiredView(view, 2131820847, "field 'currentStepGoalView' and method 'showPopupForDailyStepsGoal'");
        activityTrackerTodayFragment.currentStepGoalView = Utils.castView(view2, 2131820847, "field 'currentStepGoalView'", TextView.class);
        this.view2131820847 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                activityTrackerTodayFragment.showPopupForDailyStepsGoal();
            }
        });
        view2 = Utils.findRequiredView(view, 2131820817, "field 'currentActivityTargetView' and method 'showPopupForDailyActiveGoal'");
        activityTrackerTodayFragment.currentActivityTargetView = Utils.castView(view2, 2131820817, "field 'currentActivityTargetView'", TextView.class);
        this.view2131820817 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                activityTrackerTodayFragment.showPopupForDailyActiveGoal();
            }
        });
        activityTrackerTodayFragment.menuFloatingActionButtonLayout = Utils.findRequiredViewAsType(view, 2131820861, "field 'menuFloatingActionButtonLayout'", FrameLayout.class);
        activityTrackerTodayFragment.menuAddNewActivity = Utils.findRequiredViewAsType(view, 2131820876, "field 'menuAddNewActivity'", FloatingActionsMenu.class);
        view2 = Utils.findRequiredView(view, 2131820878, "field 'addNewWeightToActivity' and method 'goToAddManualWeightMeasurement'");
        activityTrackerTodayFragment.addNewWeightToActivity = Utils.castView(view2, 2131820878, "field 'addNewWeightToActivity'", FloatingActionButton.class);
        this.view2131820878 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                activityTrackerTodayFragment.goToAddManualWeightMeasurement();
            }
        });
        view2 = Utils.findRequiredView(view, 2131820877, "field 'addNewGoal' and method 'goToAddNewGoal'");
        activityTrackerTodayFragment.addNewGoal = Utils.castView(view2, 2131820877, "field 'addNewGoal'", FloatingActionButton.class);
        this.view2131820877 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                activityTrackerTodayFragment.goToAddNewGoal();
            }
        });
        view2 = Utils.findRequiredView(view, 2131820879, "field 'addNewBPActivity' and method 'goToAddManualBPMeasurement'");
        activityTrackerTodayFragment.addNewBPActivity = Utils.castView(view2, 2131820879, "field 'addNewBPActivity'", FloatingActionButton.class);
        this.view2131820879 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                activityTrackerTodayFragment.goToAddManualBPMeasurement();
            }
        });
        activityTrackerTodayFragment.stepsProgressGoalLayout = Utils.findRequiredViewAsType(view, 2131820848, "field 'stepsProgressGoalLayout'", LinearLayout.class);
        activityTrackerTodayFragment.dailyActiveGoalBar = Utils.findRequiredViewAsType(view, 2131820809, "field 'dailyActiveGoalBar'", ProgressBar.class);
        activityTrackerTodayFragment.stepsGoalProgressBar = Utils.findRequiredViewAsType(view, 2131820856, "field 'stepsGoalProgressBar'", ProgressBar.class);
        activityTrackerTodayFragment.minutesActive = Utils.findRequiredViewAsType(view, 2131820814, "field 'minutesActive'", TextView.class);
        activityTrackerTodayFragment.timeLineView = Utils.findRequiredViewAsType(view, 2131820864, "field 'timeLineView'", TimeLineView.class);
        activityTrackerTodayFragment.goalCircle = Utils.findRequiredViewAsType(view, 2131820810, "field 'goalCircle'", ImageView.class);
        activityTrackerTodayFragment.goalStar1 = Utils.findRequiredViewAsType(view, 2131820811, "field 'goalStar1'", ImageView.class);
        activityTrackerTodayFragment.goalStar2 = Utils.findRequiredViewAsType(view, 2131820812, "field 'goalStar2'", ImageView.class);
        activityTrackerTodayFragment.goalStar3 = Utils.findRequiredViewAsType(view, 2131820813, "field 'goalStar3'", ImageView.class);
        activityTrackerTodayFragment.stepsChart = Utils.findRequiredViewAsType(view, 2131820850, "field 'stepsChart'", BarChart.class);
        activityTrackerTodayFragment.totalSteps = Utils.findRequiredViewAsType(view, 2131820855, "field 'totalSteps'", TextView.class);
        activityTrackerTodayFragment.goalMotivationTitle = Utils.findRequiredViewAsType(view, 2131820815, "field 'goalMotivationTitle'", TextView.class);
        activityTrackerTodayFragment.goalMotivationBody = Utils.findRequiredViewAsType(view, 2131820816, "field 'goalMotivationBody'", TextView.class);
        activityTrackerTodayFragment.emptyTimeline = Utils.findRequiredViewAsType(view, 2131820865, "field 'emptyTimeline'", LinearLayout.class);
        activityTrackerTodayFragment.chartXAxisStart = Utils.findRequiredViewAsType(view, 2131820852, "field 'chartXAxisStart'", TextView.class);
        activityTrackerTodayFragment.chartXAxisCenter = Utils.findRequiredViewAsType(view, 2131820853, "field 'chartXAxisCenter'", TextView.class);
        activityTrackerTodayFragment.chartXAxisEnd = Utils.findRequiredViewAsType(view, 2131820854, "field 'chartXAxisEnd'", TextView.class);
        activityTrackerTodayFragment.activityTrackedStepsNoData = Utils.findRequiredViewAsType(view, 2131820851, "field 'activityTrackedStepsNoData'", LinearLayout.class);
        activityTrackerTodayFragment.timelineContainer = Utils.findOptionalViewAsType(view, 2131820863, "field 'timelineContainer'", CardView.class);
    }

}

