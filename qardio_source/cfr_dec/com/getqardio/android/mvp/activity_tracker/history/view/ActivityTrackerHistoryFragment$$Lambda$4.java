/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.history.view;

import android.support.v4.util.Pair;
import com.getqardio.android.mvp.activity_tracker.history.view.ActivityTrackerHistoryFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class ActivityTrackerHistoryFragment$$Lambda$4
implements Consumer {
    private final ActivityTrackerHistoryFragment arg$1;
    private final CalendarDay arg$2;

    private ActivityTrackerHistoryFragment$$Lambda$4(ActivityTrackerHistoryFragment activityTrackerHistoryFragment, CalendarDay calendarDay) {
        this.arg$1 = activityTrackerHistoryFragment;
        this.arg$2 = calendarDay;
    }

    public static Consumer lambdaFactory$(ActivityTrackerHistoryFragment activityTrackerHistoryFragment, CalendarDay calendarDay) {
        return new ActivityTrackerHistoryFragment$$Lambda$4(activityTrackerHistoryFragment, calendarDay);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        this.arg$1.lambda$null$0(this.arg$2, (Pair)object);
    }
}

