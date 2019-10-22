/*
 * Decompiled with CFR 0.147.
 */
package com.prolificinteractive.materialcalendarview;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public interface DayViewDecorator {
    public void decorate(DayViewFacade var1);

    public boolean shouldDecorate(CalendarDay var1);
}

