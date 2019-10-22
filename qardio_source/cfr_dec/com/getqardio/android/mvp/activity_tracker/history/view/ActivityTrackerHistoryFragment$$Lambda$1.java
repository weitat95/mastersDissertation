/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.history.view;

import com.getqardio.android.mvp.activity_tracker.history.view.ActivityTrackerHistoryFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import java.lang.invoke.LambdaForm;

final class ActivityTrackerHistoryFragment$$Lambda$1
implements OnMonthChangedListener {
    private final ActivityTrackerHistoryFragment arg$1;

    private ActivityTrackerHistoryFragment$$Lambda$1(ActivityTrackerHistoryFragment activityTrackerHistoryFragment) {
        this.arg$1 = activityTrackerHistoryFragment;
    }

    public static OnMonthChangedListener lambdaFactory$(ActivityTrackerHistoryFragment activityTrackerHistoryFragment) {
        return new ActivityTrackerHistoryFragment$$Lambda$1(activityTrackerHistoryFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void onMonthChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
        this.arg$1.lambda$showStepsChart$1(materialCalendarView, calendarDay);
    }
}

