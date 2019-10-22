/*
 * Decompiled with CFR 0.147.
 */
package com.prolificinteractive.materialcalendarview;

import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

class DecoratorResult {
    public final DayViewDecorator decorator;
    public final DayViewFacade result;

    DecoratorResult(DayViewDecorator dayViewDecorator, DayViewFacade dayViewFacade) {
        this.decorator = dayViewDecorator;
        this.result = dayViewFacade;
    }
}

