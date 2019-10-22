/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.history.view;

import com.getqardio.android.mvp.activity_tracker.history.view.ActivityTrackerHistoryFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import java.lang.invoke.LambdaForm;

final class ActivityTrackerHistoryFragment$$Lambda$2
implements OnDateSelectedListener {
    private final ActivityTrackerHistoryFragment arg$1;

    private ActivityTrackerHistoryFragment$$Lambda$2(ActivityTrackerHistoryFragment activityTrackerHistoryFragment) {
        this.arg$1 = activityTrackerHistoryFragment;
    }

    public static OnDateSelectedListener lambdaFactory$(ActivityTrackerHistoryFragment activityTrackerHistoryFragment) {
        return new ActivityTrackerHistoryFragment$$Lambda$2(activityTrackerHistoryFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void onDateSelected(MaterialCalendarView materialCalendarView, CalendarDay calendarDay, boolean bl) {
        this.arg$1.lambda$showStepsChart$2(materialCalendarView, calendarDay, bl);
    }
}

