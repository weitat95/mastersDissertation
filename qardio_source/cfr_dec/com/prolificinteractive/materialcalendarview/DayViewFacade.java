/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 */
package com.prolificinteractive.materialcalendarview;

import android.graphics.drawable.Drawable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DayViewFacade {
    private Drawable backgroundDrawable = null;
    private boolean daysDisabled = false;
    private boolean isDecorated = false;
    private Drawable selectionDrawable = null;
    private final LinkedList<Span> spans = new LinkedList();

    DayViewFacade() {
    }

    void applyTo(DayViewFacade dayViewFacade) {
        if (this.selectionDrawable != null) {
            dayViewFacade.setSelectionDrawable(this.selectionDrawable);
        }
        if (this.backgroundDrawable != null) {
            dayViewFacade.setBackgroundDrawable(this.backgroundDrawable);
        }
        dayViewFacade.spans.addAll(this.spans);
        dayViewFacade.isDecorated |= this.isDecorated;
        dayViewFacade.daysDisabled = this.daysDisabled;
    }

    public boolean areDaysDisabled() {
        return this.daysDisabled;
    }

    Drawable getBackgroundDrawable() {
        return this.backgroundDrawable;
    }

    Drawable getSelectionDrawable() {
        return this.selectionDrawable;
    }

    List<Span> getSpans() {
        return Collections.unmodifiableList(this.spans);
    }

    boolean isDecorated() {
        return this.isDecorated;
    }

    void reset() {
        this.backgroundDrawable = null;
        this.selectionDrawable = null;
        this.spans.clear();
        this.isDecorated = false;
        this.daysDisabled = false;
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        if (drawable2 == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        this.backgroundDrawable = drawable2;
        this.isDecorated = true;
    }

    public void setDaysDisabled(boolean bl) {
        this.daysDisabled = bl;
        this.isDecorated = true;
    }

    public void setSelectionDrawable(Drawable drawable2) {
        if (drawable2 == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        this.selectionDrawable = drawable2;
        this.isDecorated = true;
    }

    static class Span {
        final Object span;
    }

}

