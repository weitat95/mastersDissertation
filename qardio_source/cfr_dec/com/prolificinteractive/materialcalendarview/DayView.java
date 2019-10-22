/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.RippleDrawable
 *  android.graphics.drawable.ShapeDrawable
 *  android.graphics.drawable.StateListDrawable
 *  android.graphics.drawable.shapes.OvalShape
 *  android.graphics.drawable.shapes.Shape
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.SpannableString
 *  android.text.Spanned
 *  android.widget.CheckedTextView
 */
package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.widget.CheckedTextView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import java.util.Iterator;
import java.util.List;

@SuppressLint(value={"ViewConstructor"})
class DayView
extends CheckedTextView {
    private final Rect circleDrawableRect;
    private Drawable customBackground = null;
    private CalendarDay date;
    private final int fadeTime;
    private DayFormatter formatter = DayFormatter.DEFAULT;
    private boolean isDecoratedDisabled = false;
    private boolean isInMonth = true;
    private boolean isInRange = true;
    private Drawable mCircleDrawable;
    private int selectionColor = -7829368;
    private Drawable selectionDrawable;
    private int showOtherDates = 4;
    private final Rect tempRect = new Rect();

    public DayView(Context context, CalendarDay calendarDay) {
        super(context);
        this.circleDrawableRect = new Rect();
        this.fadeTime = this.getResources().getInteger(17694720);
        this.setSelectionColor(this.selectionColor);
        this.setGravity(17);
        if (Build.VERSION.SDK_INT >= 17) {
            this.setTextAlignment(4);
        }
        this.setDay(calendarDay);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calculateBounds(int n, int n2) {
        int n3 = Math.min(n2, n);
        int n4 = Math.abs(n2 - n) / 2;
        int n5 = Build.VERSION.SDK_INT == 21 ? n4 / 2 : n4;
        if (n >= n2) {
            this.tempRect.set(n4, 0, n3 + n4, n2);
            this.circleDrawableRect.set(n5, 0, n3 + n5, n2);
            return;
        }
        this.tempRect.set(0, n4, n, n3 + n4);
        this.circleDrawableRect.set(0, n5, n, n3 + n5);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Drawable generateBackground(int n, int n2, Rect rect) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.setExitFadeDuration(n2);
        Drawable drawable2 = DayView.generateCircleDrawable(n);
        stateListDrawable.addState(new int[]{16842912}, drawable2);
        if (Build.VERSION.SDK_INT >= 21) {
            rect = DayView.generateRippleDrawable(n, rect);
            stateListDrawable.addState(new int[]{16842919}, (Drawable)rect);
        } else {
            rect = DayView.generateCircleDrawable(n);
            stateListDrawable.addState(new int[]{16842919}, (Drawable)rect);
        }
        rect = DayView.generateCircleDrawable(0);
        stateListDrawable.addState(new int[0], (Drawable)rect);
        return stateListDrawable;
    }

    private static Drawable generateCircleDrawable(int n) {
        ShapeDrawable shapeDrawable = new ShapeDrawable((Shape)new OvalShape());
        shapeDrawable.getPaint().setColor(n);
        return shapeDrawable;
    }

    @TargetApi(value=21)
    private static Drawable generateRippleDrawable(int n, Rect rect) {
        RippleDrawable rippleDrawable = new RippleDrawable(ColorStateList.valueOf((int)n), null, DayView.generateCircleDrawable(-1));
        if (Build.VERSION.SDK_INT == 21) {
            rippleDrawable.setBounds(rect);
        }
        if (Build.VERSION.SDK_INT == 22) {
            n = (rect.left + rect.right) / 2;
            rippleDrawable.setHotspotBounds(n, rect.top, n, rect.bottom);
        }
        return rippleDrawable;
    }

    private void regenerateBackground() {
        if (this.selectionDrawable != null) {
            this.setBackgroundDrawable(this.selectionDrawable);
            return;
        }
        this.mCircleDrawable = DayView.generateBackground(this.selectionColor, this.fadeTime, this.circleDrawableRect);
        this.setBackgroundDrawable(this.mCircleDrawable);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setEnabled() {
        int n = 0;
        int n2 = this.isInMonth && this.isInRange && !this.isDecoratedDisabled ? 1 : 0;
        boolean bl = this.isInRange && !this.isDecoratedDisabled;
        super.setEnabled(bl);
        bl = MaterialCalendarView.showOtherMonths(this.showOtherDates);
        boolean bl2 = MaterialCalendarView.showOutOfRange(this.showOtherDates) || bl;
        boolean bl3 = MaterialCalendarView.showDecoratedDisabled(this.showOtherDates);
        int n3 = n2;
        if (!this.isInMonth) {
            n3 = n2;
            if (bl) {
                n3 = 1;
            }
        }
        n2 = n3;
        if (!this.isInRange) {
            n2 = n3;
            if (bl2) {
                n2 = n3 | this.isInMonth;
            }
        }
        n3 = n2;
        if (this.isDecoratedDisabled) {
            n3 = n2;
            if (bl3) {
                n3 = this.isInMonth && this.isInRange ? 1 : 0;
                n3 = n2 | n3;
            }
        }
        if (!this.isInMonth && n3 != 0) {
            this.setTextColor(this.getTextColors().getColorForState(new int[]{-16842910}, -7829368));
        }
        n2 = n3 != 0 ? n : 4;
        this.setVisibility(n2);
    }

    void applyFacade(DayViewFacade object) {
        this.isDecoratedDisabled = ((DayViewFacade)object).areDaysDisabled();
        this.setEnabled();
        this.setCustomBackground(((DayViewFacade)object).getBackgroundDrawable());
        this.setSelectionDrawable(((DayViewFacade)object).getSelectionDrawable());
        Object object2 = ((DayViewFacade)object).getSpans();
        if (!object2.isEmpty()) {
            object = this.getLabel();
            SpannableString spannableString = new SpannableString((CharSequence)this.getLabel());
            object2 = object2.iterator();
            while (object2.hasNext()) {
                spannableString.setSpan(((DayViewFacade.Span)object2.next()).span, 0, ((String)object).length(), 33);
            }
            this.setText((CharSequence)spannableString);
            return;
        }
        this.setText((CharSequence)this.getLabel());
    }

    public CalendarDay getDate() {
        return this.date;
    }

    public String getLabel() {
        return this.formatter.format(this.date);
    }

    protected void onDraw(Canvas canvas) {
        if (this.customBackground != null) {
            this.customBackground.setBounds(this.tempRect);
            this.customBackground.setState(this.getDrawableState());
            this.customBackground.draw(canvas);
        }
        this.mCircleDrawable.setBounds(this.circleDrawableRect);
        super.onDraw(canvas);
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.calculateBounds(n3 - n, n4 - n2);
        this.regenerateBackground();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCustomBackground(Drawable drawable2) {
        this.customBackground = drawable2 == null ? null : drawable2.getConstantState().newDrawable(this.getResources());
        this.invalidate();
    }

    public void setDay(CalendarDay calendarDay) {
        this.date = calendarDay;
        this.setText((CharSequence)this.getLabel());
    }

    public void setDayFormatter(DayFormatter arrobject) {
        Object object = arrobject;
        if (arrobject == null) {
            object = DayFormatter.DEFAULT;
        }
        this.formatter = object;
        object = this.getText();
        arrobject = null;
        if (object instanceof Spanned) {
            arrobject = ((Spanned)object).getSpans(0, object.length(), Object.class);
        }
        object = new SpannableString((CharSequence)this.getLabel());
        if (arrobject != null) {
            int n = arrobject.length;
            for (int i = 0; i < n; ++i) {
                object.setSpan(arrobject[i], 0, object.length(), 33);
            }
        }
        this.setText((CharSequence)object);
    }

    public void setSelectionColor(int n) {
        this.selectionColor = n;
        this.regenerateBackground();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setSelectionDrawable(Drawable drawable2) {
        this.selectionDrawable = drawable2 == null ? null : drawable2.getConstantState().newDrawable(this.getResources());
        this.regenerateBackground();
    }

    protected void setupSelection(int n, boolean bl, boolean bl2) {
        this.showOtherDates = n;
        this.isInMonth = bl2;
        this.isInRange = bl;
        this.setEnabled();
    }
}

