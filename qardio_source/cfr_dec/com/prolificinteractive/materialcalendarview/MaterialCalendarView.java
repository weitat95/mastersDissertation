/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.SparseArray
 *  android.util.TypedValue
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.CalendarPager;
import com.prolificinteractive.materialcalendarview.CalendarPagerAdapter;
import com.prolificinteractive.materialcalendarview.DayView;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DirectionButton;
import com.prolificinteractive.materialcalendarview.MonthPagerAdapter;
import com.prolificinteractive.materialcalendarview.MonthView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.R;
import com.prolificinteractive.materialcalendarview.TitleChanger;
import com.prolificinteractive.materialcalendarview.WeekPagerAdapter;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MaterialCalendarView
extends ViewGroup {
    private static final TitleFormatter DEFAULT_TITLE_FORMATTER = new DateFormatTitleFormatter();
    private int accentColor = 0;
    private CalendarPagerAdapter<?> adapter;
    private boolean allowClickDaysOutsideCurrentMonth = true;
    private int arrowColor = -16777216;
    private final DirectionButton buttonFuture;
    private final DirectionButton buttonPast;
    CharSequence calendarContentDescription;
    private CalendarMode calendarMode;
    private CalendarDay currentMonth;
    private final ArrayList<DayViewDecorator> dayViewDecorators = new ArrayList<E>();
    private int firstDayOfWeek;
    private Drawable leftArrowMask;
    private OnDateSelectedListener listener;
    private boolean mDynamicHeightEnabled;
    private CalendarDay maxDate = null;
    private CalendarDay minDate = null;
    private OnMonthChangedListener monthListener;
    private final View.OnClickListener onClickListener = new View.OnClickListener(){

        /*
         * Enabled aggressive block sorting
         */
        public void onClick(View view) {
            if (view == MaterialCalendarView.this.buttonFuture) {
                MaterialCalendarView.this.pager.setCurrentItem(MaterialCalendarView.this.pager.getCurrentItem() + 1, true);
                return;
            } else {
                if (view != MaterialCalendarView.this.buttonPast) return;
                {
                    MaterialCalendarView.this.pager.setCurrentItem(MaterialCalendarView.this.pager.getCurrentItem() - 1, true);
                    return;
                }
            }
        }
    };
    private final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrollStateChanged(int n) {
        }

        @Override
        public void onPageScrolled(int n, float f, int n2) {
        }

        @Override
        public void onPageSelected(int n) {
            MaterialCalendarView.this.titleChanger.setPreviousMonth(MaterialCalendarView.this.currentMonth);
            MaterialCalendarView.this.currentMonth = MaterialCalendarView.this.adapter.getItem(n);
            MaterialCalendarView.this.updateUi();
            MaterialCalendarView.this.dispatchOnMonthChanged(MaterialCalendarView.this.currentMonth);
        }
    };
    private final CalendarPager pager;
    private OnRangeSelectedListener rangeListener;
    private Drawable rightArrowMask;
    private int selectionMode = 1;
    private State state;
    private int tileHeight = -10;
    private int tileWidth = -10;
    private final TextView title;
    private final TitleChanger titleChanger;
    private LinearLayout topbar;

    public MaterialCalendarView(Context context) {
        this(context, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public MaterialCalendarView(Context var1_1, AttributeSet var2_4) {
        super((Context)var1_1, var2_4);
        if (Build.VERSION.SDK_INT >= 19) {
            this.setClipToPadding(false);
            this.setClipChildren(false);
        } else {
            this.setClipChildren(true);
            this.setClipToPadding(true);
        }
        this.buttonPast = new DirectionButton(this.getContext());
        this.buttonPast.setContentDescription((CharSequence)this.getContext().getString(R.string.previous));
        this.title = new TextView(this.getContext());
        this.buttonFuture = new DirectionButton(this.getContext());
        this.buttonFuture.setContentDescription((CharSequence)this.getContext().getString(R.string.next));
        this.pager = new CalendarPager(this.getContext());
        this.buttonPast.setOnClickListener(this.onClickListener);
        this.buttonFuture.setOnClickListener(this.onClickListener);
        this.titleChanger = new TitleChanger(this.title);
        this.titleChanger.setTitleFormatter(MaterialCalendarView.DEFAULT_TITLE_FORMATTER);
        this.pager.setOnPageChangeListener(this.pageChangeListener);
        this.pager.setPageTransformer(false, new ViewPager.PageTransformer(){

            @Override
            public void transformPage(View view, float f) {
                view.setAlpha((float)Math.sqrt(1.0f - Math.abs(f)));
            }
        });
        var5_5 = var1_1.getTheme().obtainStyledAttributes(var2_4, R.styleable.MaterialCalendarView, 0, 0);
        try {
            var3_6 = var5_5.getInteger(R.styleable.MaterialCalendarView_mcv_calendarMode, 0);
            this.firstDayOfWeek = var5_5.getInteger(R.styleable.MaterialCalendarView_mcv_firstDayOfWeek, -1);
            this.titleChanger.setOrientation(var5_5.getInteger(R.styleable.MaterialCalendarView_mcv_titleAnimationOrientation, 0));
            if (this.firstDayOfWeek < 0) {
                this.firstDayOfWeek = Calendar.getInstance().getFirstDayOfWeek();
            }
            this.newState().setFirstDayOfWeek(this.firstDayOfWeek).setCalendarDisplayMode(CalendarMode.values()[var3_6]).commit();
            var3_6 = var5_5.getLayoutDimension(R.styleable.MaterialCalendarView_mcv_tileSize, -10);
            if (var3_6 > -10) {
                this.setTileSize(var3_6);
            }
            if ((var3_6 = var5_5.getLayoutDimension(R.styleable.MaterialCalendarView_mcv_tileWidth, -10)) > -10) {
                this.setTileWidth(var3_6);
            }
            if ((var3_6 = var5_5.getLayoutDimension(R.styleable.MaterialCalendarView_mcv_tileHeight, -10)) > -10) {
                this.setTileHeight(var3_6);
            }
            this.setArrowColor(var5_5.getColor(R.styleable.MaterialCalendarView_mcv_arrowColor, -16777216));
            var4_7 = var5_5.getDrawable(R.styleable.MaterialCalendarView_mcv_leftArrowMask);
            var2_4 = var4_7;
            if (var4_7 == null) {
                var2_4 = this.getResources().getDrawable(R.drawable.mcv_action_previous);
            }
            this.setLeftArrowMask((Drawable)var2_4);
            var4_7 = var5_5.getDrawable(R.styleable.MaterialCalendarView_mcv_rightArrowMask);
            var2_4 = var4_7;
            if (var4_7 == null) {
                var2_4 = this.getResources().getDrawable(R.drawable.mcv_action_next);
            }
            this.setRightArrowMask((Drawable)var2_4);
            this.setSelectionColor(var5_5.getColor(R.styleable.MaterialCalendarView_mcv_selectionColor, MaterialCalendarView.getThemeAccentColor((Context)var1_1)));
            var1_1 = var5_5.getTextArray(R.styleable.MaterialCalendarView_mcv_weekDayLabels);
            if (var1_1 != null) {
                this.setWeekDayFormatter(new ArrayWeekDayFormatter((CharSequence[])var1_1));
            }
            if ((var1_1 = var5_5.getTextArray(R.styleable.MaterialCalendarView_mcv_monthLabels)) != null) {
                this.setTitleFormatter(new MonthArrayTitleFormatter((CharSequence[])var1_1));
            }
            this.setHeaderTextAppearance(var5_5.getResourceId(R.styleable.MaterialCalendarView_mcv_headerTextAppearance, R.style.TextAppearance_MaterialCalendarWidget_Header));
            this.setWeekDayTextAppearance(var5_5.getResourceId(R.styleable.MaterialCalendarView_mcv_weekDayTextAppearance, R.style.TextAppearance_MaterialCalendarWidget_WeekDay));
            this.setDateTextAppearance(var5_5.getResourceId(R.styleable.MaterialCalendarView_mcv_dateTextAppearance, R.style.TextAppearance_MaterialCalendarWidget_Date));
            this.setShowOtherDates(var5_5.getInteger(R.styleable.MaterialCalendarView_mcv_showOtherDates, 4));
            this.setAllowClickDaysOutsideCurrentMonth(var5_5.getBoolean(R.styleable.MaterialCalendarView_mcv_allowClickDaysOutsideCurrentMonth, true));
        }
        catch (Exception var1_2) {
            var1_2.printStackTrace();
        }
lbl71:
        // 3 sources
        do {
            this.adapter.setTitleFormatter(MaterialCalendarView.DEFAULT_TITLE_FORMATTER);
            this.setupChildren();
            this.currentMonth = CalendarDay.today();
            this.setCurrentDate(this.currentMonth);
            if (this.isInEditMode() == false) return;
            this.removeView((View)this.pager);
            var1_1 = new MonthView(this, this.currentMonth, this.getFirstDayOfWeek());
            var1_1.setSelectionColor(this.getSelectionColor());
            var1_1.setDateTextAppearance(this.adapter.getDateTextAppearance());
            var1_1.setWeekDayTextAppearance(this.adapter.getWeekDayTextAppearance());
            var1_1.setShowOtherDates(this.getShowOtherDates());
            this.addView((View)var1_1, (ViewGroup.LayoutParams)new LayoutParams(this.calendarMode.visibleWeeksCount + 1));
            return;
            break;
        } while (true);
        finally {
            var5_5.recycle();
            ** continue;
        }
    }

    private static int clampSize(int n, int n2) {
        int n3;
        int n4 = View.MeasureSpec.getMode((int)n2);
        n2 = n3 = View.MeasureSpec.getSize((int)n2);
        switch (n4) {
            default: {
                n2 = n;
            }
            case 1073741824: {
                return n2;
            }
            case Integer.MIN_VALUE: 
        }
        return Math.min(n, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void commit(State object) {
        Object object2;
        block9: {
            CalendarDay calendarDay;
            CalendarDay calendarDay2;
            block10: {
                block11: {
                    calendarDay = null;
                    object2 = calendarDay;
                    if (this.adapter == null) break block9;
                    object2 = calendarDay;
                    if (!((State)object).cacheCurrentPosition) break block9;
                    calendarDay2 = this.adapter.getItem(this.pager.getCurrentItem());
                    object2 = calendarDay2;
                    if (this.calendarMode == ((State)object).calendarMode) break block9;
                    calendarDay = this.getSelectedDate();
                    if (this.calendarMode != CalendarMode.MONTHS || calendarDay == null) break block10;
                    object2 = calendarDay2.getCalendar();
                    ((Calendar)object2).add(2, 1);
                    CalendarDay calendarDay3 = CalendarDay.from((Calendar)object2);
                    if (calendarDay.equals(calendarDay2)) break block11;
                    object2 = calendarDay2;
                    if (!calendarDay.isAfter(calendarDay2)) break block9;
                    object2 = calendarDay2;
                    if (!calendarDay.isBefore(calendarDay3)) break block9;
                }
                object2 = calendarDay;
                break block9;
            }
            object2 = calendarDay2;
            if (this.calendarMode == CalendarMode.WEEKS) {
                object2 = calendarDay2.getCalendar();
                ((Calendar)object2).add(7, 6);
                object2 = CalendarDay.from((Calendar)object2);
                if (calendarDay != null && (calendarDay.equals(calendarDay2) || calendarDay.equals(object2) || calendarDay.isAfter(calendarDay2) && calendarDay.isBefore((CalendarDay)object2))) {
                    object2 = calendarDay;
                }
            }
        }
        this.state = object;
        this.calendarMode = ((State)object).calendarMode;
        this.firstDayOfWeek = ((State)object).firstDayOfWeek;
        this.minDate = ((State)object).minDate;
        this.maxDate = ((State)object).maxDate;
        switch (4.$SwitchMap$com$prolificinteractive$materialcalendarview$CalendarMode[this.calendarMode.ordinal()]) {
            default: {
                throw new IllegalArgumentException("Provided display mode which is not yet implemented");
            }
            case 1: {
                object = new MonthPagerAdapter(this);
                break;
            }
            case 2: {
                object = new WeekPagerAdapter(this);
            }
        }
        this.adapter = this.adapter == null ? object : this.adapter.migrateStateAndReturn((CalendarPagerAdapter<?>)object);
        this.pager.setAdapter(this.adapter);
        this.setRangeDates(this.minDate, this.maxDate);
        this.pager.setLayoutParams((ViewGroup.LayoutParams)new LayoutParams(this.calendarMode.visibleWeeksCount + 1));
        object = this.selectionMode == 1 && !this.adapter.getSelectedDates().isEmpty() ? this.adapter.getSelectedDates().get(0) : CalendarDay.today();
        this.setCurrentDate((CalendarDay)object);
        if (object2 != null) {
            this.pager.setCurrentItem(this.adapter.getIndexForDay((CalendarDay)object2));
        }
        this.invalidateDecorators();
        this.updateUi();
    }

    private int dpToPx(int n) {
        return (int)TypedValue.applyDimension((int)1, (float)n, (DisplayMetrics)this.getResources().getDisplayMetrics());
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int getThemeAccentColor(Context context) {
        int n = Build.VERSION.SDK_INT >= 21 ? 16843829 : context.getResources().getIdentifier("colorAccent", "attr", context.getPackageName());
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(n, typedValue, true);
        return typedValue.data;
    }

    private int getWeekCountBasedOnMode() {
        int n;
        int n2 = n = this.calendarMode.visibleWeeksCount;
        if (this.calendarMode.equals((Object)CalendarMode.MONTHS)) {
            n2 = n;
            if (this.mDynamicHeightEnabled) {
                n2 = n;
                if (this.adapter != null) {
                    n2 = n;
                    if (this.pager != null) {
                        Calendar calendar = (Calendar)this.adapter.getItem(this.pager.getCurrentItem()).getCalendar().clone();
                        calendar.set(5, calendar.getActualMaximum(5));
                        calendar.setFirstDayOfWeek(this.getFirstDayOfWeek());
                        n2 = calendar.get(4);
                    }
                }
            }
        }
        return n2 + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setRangeDates(CalendarDay calendarDay, CalendarDay calendarDay2) {
        CalendarDay calendarDay3 = this.currentMonth;
        this.adapter.setRangeDates(calendarDay, calendarDay2);
        this.currentMonth = calendarDay3;
        if (calendarDay != null) {
            if (!calendarDay.isAfter(this.currentMonth)) {
                calendarDay = this.currentMonth;
            }
            this.currentMonth = calendarDay;
        }
        int n = this.adapter.getIndexForDay(calendarDay3);
        this.pager.setCurrentItem(n, false);
        this.updateUi();
    }

    private void setupChildren() {
        this.topbar = new LinearLayout(this.getContext());
        this.topbar.setOrientation(0);
        this.topbar.setClipChildren(false);
        this.topbar.setClipToPadding(false);
        this.addView((View)this.topbar, (ViewGroup.LayoutParams)new LayoutParams(1));
        this.buttonPast.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        this.topbar.addView((View)this.buttonPast, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, -1, 1.0f));
        this.title.setGravity(17);
        this.topbar.addView((View)this.title, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, -1, 5.0f));
        this.buttonFuture.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        this.topbar.addView((View)this.buttonFuture, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, -1, 1.0f));
        this.pager.setId(R.id.mcv_pager);
        this.pager.setOffscreenPageLimit(1);
        this.addView((View)this.pager, (ViewGroup.LayoutParams)new LayoutParams(this.calendarMode.visibleWeeksCount + 1));
    }

    public static boolean showDecoratedDisabled(int n) {
        return (n & 4) != 0;
    }

    public static boolean showOtherMonths(int n) {
        return (n & 1) != 0;
    }

    public static boolean showOutOfRange(int n) {
        return (n & 2) != 0;
    }

    private void updateUi() {
        this.titleChanger.change(this.currentMonth);
        this.buttonPast.setEnabled(this.canGoBack());
        this.buttonFuture.setEnabled(this.canGoForward());
    }

    public void addDecorators(Collection<? extends DayViewDecorator> collection) {
        if (collection == null) {
            return;
        }
        this.dayViewDecorators.addAll(collection);
        this.adapter.setDecorators(this.dayViewDecorators);
    }

    public void addDecorators(DayViewDecorator ... arrdayViewDecorator) {
        this.addDecorators(Arrays.asList(arrdayViewDecorator));
    }

    public boolean allowClickDaysOutsideCurrentMonth() {
        return this.allowClickDaysOutsideCurrentMonth;
    }

    public boolean canGoBack() {
        return this.pager.getCurrentItem() > 0;
    }

    public boolean canGoForward() {
        return this.pager.getCurrentItem() < this.adapter.getCount() - 1;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void clearSelection() {
        Object object = this.getSelectedDates();
        this.adapter.clearSelections();
        object = object.iterator();
        while (object.hasNext()) {
            this.dispatchOnDateSelected((CalendarDay)object.next(), false);
        }
    }

    protected void dispatchOnDateSelected(CalendarDay calendarDay, boolean bl) {
        OnDateSelectedListener onDateSelectedListener = this.listener;
        if (onDateSelectedListener != null) {
            onDateSelectedListener.onDateSelected(this, calendarDay, bl);
        }
    }

    protected void dispatchOnMonthChanged(CalendarDay calendarDay) {
        OnMonthChangedListener onMonthChangedListener = this.monthListener;
        if (onMonthChangedListener != null) {
            onMonthChangedListener.onMonthChanged(this, calendarDay);
        }
    }

    protected void dispatchOnRangeSelected(CalendarDay object, CalendarDay calendarDay) {
        OnRangeSelectedListener onRangeSelectedListener = this.rangeListener;
        ArrayList<CalendarDay> arrayList = new ArrayList<CalendarDay>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(((CalendarDay)object).getDate());
        object = Calendar.getInstance();
        ((Calendar)object).setTime(calendarDay.getDate());
        while (calendar.before(object) || calendar.equals(object)) {
            calendarDay = CalendarDay.from(calendar);
            this.adapter.setDateSelected(calendarDay, true);
            arrayList.add(calendarDay);
            calendar.add(5, 1);
        }
        if (onRangeSelectedListener != null) {
            onRangeSelectedListener.onRangeSelected(this, arrayList);
        }
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchThawSelfOnly(sparseArray);
    }

    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchFreezeSelfOnly(sparseArray);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(1);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(1);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(1);
    }

    public int getArrowColor() {
        return this.arrowColor;
    }

    public CharSequence getCalendarContentDescription() {
        if (this.calendarContentDescription != null) {
            return this.calendarContentDescription;
        }
        return this.getContext().getString(R.string.calendar);
    }

    public CalendarDay getCurrentDate() {
        return this.adapter.getItem(this.pager.getCurrentItem());
    }

    public int getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }

    public Drawable getLeftArrowMask() {
        return this.leftArrowMask;
    }

    public CalendarDay getMaximumDate() {
        return this.maxDate;
    }

    public CalendarDay getMinimumDate() {
        return this.minDate;
    }

    public Drawable getRightArrowMask() {
        return this.rightArrowMask;
    }

    public CalendarDay getSelectedDate() {
        List<CalendarDay> list = this.adapter.getSelectedDates();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public List<CalendarDay> getSelectedDates() {
        return this.adapter.getSelectedDates();
    }

    public int getSelectionColor() {
        return this.accentColor;
    }

    public int getSelectionMode() {
        return this.selectionMode;
    }

    public int getShowOtherDates() {
        return this.adapter.getShowOtherDates();
    }

    public int getTileHeight() {
        return this.tileHeight;
    }

    @Deprecated
    public int getTileSize() {
        return Math.max(this.tileHeight, this.tileWidth);
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getTitleAnimationOrientation() {
        return this.titleChanger.getOrientation();
    }

    public boolean getTopbarVisible() {
        return this.topbar.getVisibility() == 0;
    }

    public void goToNext() {
        if (this.canGoForward()) {
            this.pager.setCurrentItem(this.pager.getCurrentItem() + 1, true);
        }
    }

    public void goToPrevious() {
        if (this.canGoBack()) {
            this.pager.setCurrentItem(this.pager.getCurrentItem() - 1, true);
        }
    }

    public void invalidateDecorators() {
        this.adapter.invalidateDecorators();
    }

    public StateBuilder newState() {
        return new StateBuilder();
    }

    protected void onDateClicked(CalendarDay list, boolean bl) {
        switch (this.selectionMode) {
            default: {
                this.adapter.clearSelections();
                this.adapter.setDateSelected((CalendarDay)((Object)list), true);
                this.dispatchOnDateSelected((CalendarDay)((Object)list), true);
                return;
            }
            case 2: {
                this.adapter.setDateSelected((CalendarDay)((Object)list), bl);
                this.dispatchOnDateSelected((CalendarDay)((Object)list), bl);
                return;
            }
            case 3: 
        }
        this.adapter.setDateSelected((CalendarDay)((Object)list), bl);
        if (this.adapter.getSelectedDates().size() > 2) {
            this.adapter.clearSelections();
            this.adapter.setDateSelected((CalendarDay)((Object)list), bl);
            this.dispatchOnDateSelected((CalendarDay)((Object)list), bl);
            return;
        }
        if (this.adapter.getSelectedDates().size() == 2) {
            list = this.adapter.getSelectedDates();
            if (((CalendarDay)list.get(0)).isAfter((CalendarDay)list.get(1))) {
                this.dispatchOnRangeSelected((CalendarDay)list.get(1), (CalendarDay)list.get(0));
                return;
            }
            this.dispatchOnRangeSelected((CalendarDay)list.get(0), (CalendarDay)list.get(1));
            return;
        }
        this.adapter.setDateSelected((CalendarDay)((Object)list), bl);
        this.dispatchOnDateSelected((CalendarDay)((Object)list), bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDateClicked(DayView dayView) {
        CalendarDay calendarDay = this.getCurrentDate();
        CalendarDay calendarDay2 = dayView.getDate();
        int n = calendarDay.getMonth();
        int n2 = calendarDay2.getMonth();
        if (this.calendarMode == CalendarMode.MONTHS && this.allowClickDaysOutsideCurrentMonth && n != n2) {
            if (calendarDay.isAfter(calendarDay2)) {
                this.goToPrevious();
            } else if (calendarDay.isBefore(calendarDay2)) {
                this.goToNext();
            }
        }
        calendarDay = dayView.getDate();
        boolean bl = !dayView.isChecked();
        this.onDateClicked(calendarDay, bl);
    }

    protected void onDateUnselected(CalendarDay calendarDay) {
        this.dispatchOnDateSelected(calendarDay, false);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)MaterialCalendarView.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)MaterialCalendarView.class.getName());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5 = this.getChildCount();
        int n6 = this.getPaddingLeft();
        int n7 = this.getPaddingRight();
        n4 = this.getPaddingTop();
        n2 = 0;
        while (n2 < n5) {
            View view = this.getChildAt(n2);
            if (view.getVisibility() != 8) {
                int n8 = view.getMeasuredWidth();
                int n9 = view.getMeasuredHeight();
                int n10 = n6 + (n3 - n - n6 - n7 - n8) / 2;
                view.layout(n10, n4, n10 + n8, n4 + n9);
                n4 += n9;
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        block16: {
            int n10;
            block18: {
                int n11;
                block17: {
                    block15: {
                        n4 = View.MeasureSpec.getSize((int)n);
                        n9 = View.MeasureSpec.getMode((int)n);
                        n6 = View.MeasureSpec.getSize((int)n2);
                        n11 = View.MeasureSpec.getMode((int)n2);
                        n5 = this.getPaddingLeft();
                        n10 = this.getPaddingRight();
                        n8 = this.getPaddingTop();
                        n3 = this.getPaddingBottom();
                        n7 = this.getWeekCountBasedOnMode();
                        if (this.getTopbarVisible()) {
                            ++n7;
                        }
                        n4 = (n4 - n5 - n10) / 7;
                        n8 = (n6 - n8 - n3) / n7;
                        n3 = -1;
                        n5 = -1;
                        n10 = -1;
                        if (this.tileWidth == -10 && this.tileHeight == -10) break block15;
                        n6 = this.tileWidth > 0 ? this.tileWidth : n4;
                        if (this.tileHeight > 0) {
                            n9 = this.tileHeight;
                            n4 = n3;
                        } else {
                            n9 = n8;
                            n4 = n3;
                        }
                        break block16;
                    }
                    if (n9 != 1073741824 && n9 != Integer.MIN_VALUE) break block17;
                    if (n11 == 1073741824) {
                        n4 = Math.min(n4, n8);
                        n9 = n10;
                        n6 = n5;
                    } else {
                        n9 = n10;
                        n6 = n5;
                    }
                    break block16;
                }
                if (n11 == 1073741824) break block18;
                n9 = n10;
                n4 = n3;
                n6 = n5;
                if (n11 != Integer.MIN_VALUE) break block16;
            }
            n4 = n8;
            n9 = n10;
            n6 = n5;
        }
        if (n4 > 0) {
            n3 = n4;
            n8 = n4;
        } else {
            n3 = n9;
            n8 = n6;
            if (n4 <= 0) {
                n4 = n6;
                if (n6 <= 0) {
                    n4 = this.dpToPx(44);
                }
                n3 = n9;
                n8 = n4;
                if (n9 <= 0) {
                    n3 = this.dpToPx(44);
                    n8 = n4;
                }
            }
        }
        n4 = this.getPaddingLeft();
        n9 = this.getPaddingRight();
        n6 = this.getPaddingTop();
        n5 = this.getPaddingBottom();
        this.setMeasuredDimension(MaterialCalendarView.clampSize(n8 * 7 + (n4 + n9), n), MaterialCalendarView.clampSize(n3 * n7 + (n6 + n5), n2));
        n2 = this.getChildCount();
        n = 0;
        while (n < n2) {
            View view = this.getChildAt(n);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            view.measure(View.MeasureSpec.makeMeasureSpec((int)(n8 * 7), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)(layoutParams.height * n3), (int)1073741824));
            ++n;
        }
        return;
    }

    protected void onRestoreInstanceState(Parcelable object) {
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.newState().setFirstDayOfWeek(object.firstDayOfWeek).setCalendarDisplayMode(object.calendarMode).setMinimumDate(object.minDate).setMaximumDate(object.maxDate).isCacheCalendarPositionEnabled(object.cacheCurrentPosition).commit();
        this.setSelectionColor(object.color);
        this.setDateTextAppearance(object.dateTextAppearance);
        this.setWeekDayTextAppearance(object.weekDayTextAppearance);
        this.setShowOtherDates(object.showOtherDates);
        this.setAllowClickDaysOutsideCurrentMonth(object.allowClickDaysOutsideCurrentMonth);
        this.clearSelection();
        Iterator<CalendarDay> iterator = object.selectedDates.iterator();
        while (iterator.hasNext()) {
            this.setDateSelected(iterator.next(), true);
        }
        this.setTitleAnimationOrientation(object.orientation);
        this.setTileWidth(object.tileWidthPx);
        this.setTileHeight(object.tileHeightPx);
        this.setTopbarVisible(object.topbarVisible);
        this.setSelectionMode(object.selectionMode);
        this.setDynamicHeightEnabled(object.dynamicHeightEnabled);
        this.setCurrentDate(object.currentMonth);
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.color = this.getSelectionColor();
        savedState.dateTextAppearance = this.adapter.getDateTextAppearance();
        savedState.weekDayTextAppearance = this.adapter.getWeekDayTextAppearance();
        savedState.showOtherDates = this.getShowOtherDates();
        savedState.allowClickDaysOutsideCurrentMonth = this.allowClickDaysOutsideCurrentMonth();
        savedState.minDate = this.getMinimumDate();
        savedState.maxDate = this.getMaximumDate();
        savedState.selectedDates = this.getSelectedDates();
        savedState.firstDayOfWeek = this.getFirstDayOfWeek();
        savedState.orientation = this.getTitleAnimationOrientation();
        savedState.selectionMode = this.getSelectionMode();
        savedState.tileWidthPx = this.getTileWidth();
        savedState.tileHeightPx = this.getTileHeight();
        savedState.topbarVisible = this.getTopbarVisible();
        savedState.calendarMode = this.calendarMode;
        savedState.dynamicHeightEnabled = this.mDynamicHeightEnabled;
        savedState.currentMonth = this.currentMonth;
        savedState.cacheCurrentPosition = this.state.cacheCurrentPosition;
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.pager.dispatchTouchEvent(motionEvent);
    }

    public void setAllowClickDaysOutsideCurrentMonth(boolean bl) {
        this.allowClickDaysOutsideCurrentMonth = bl;
    }

    public void setArrowColor(int n) {
        if (n == 0) {
            return;
        }
        this.arrowColor = n;
        this.buttonPast.setColor(n);
        this.buttonFuture.setColor(n);
        this.invalidate();
    }

    public void setContentDescriptionArrowFuture(CharSequence charSequence) {
        this.buttonFuture.setContentDescription(charSequence);
    }

    public void setContentDescriptionArrowPast(CharSequence charSequence) {
        this.buttonPast.setContentDescription(charSequence);
    }

    public void setContentDescriptionCalendar(CharSequence charSequence) {
        this.calendarContentDescription = charSequence;
    }

    public void setCurrentDate(CalendarDay calendarDay) {
        this.setCurrentDate(calendarDay, true);
    }

    public void setCurrentDate(CalendarDay calendarDay, boolean bl) {
        if (calendarDay == null) {
            return;
        }
        int n = this.adapter.getIndexForDay(calendarDay);
        this.pager.setCurrentItem(n, bl);
        this.updateUi();
    }

    public void setCurrentDate(Calendar calendar) {
        this.setCurrentDate(CalendarDay.from(calendar));
    }

    public void setCurrentDate(Date date) {
        this.setCurrentDate(CalendarDay.from(date));
    }

    public void setDateSelected(CalendarDay calendarDay, boolean bl) {
        if (calendarDay == null) {
            return;
        }
        this.adapter.setDateSelected(calendarDay, bl);
    }

    public void setDateSelected(Calendar calendar, boolean bl) {
        this.setDateSelected(CalendarDay.from(calendar), bl);
    }

    public void setDateSelected(Date date, boolean bl) {
        this.setDateSelected(CalendarDay.from(date), bl);
    }

    public void setDateTextAppearance(int n) {
        this.adapter.setDateTextAppearance(n);
    }

    public void setDayFormatter(DayFormatter dayFormatter) {
        CalendarPagerAdapter<?> calendarPagerAdapter = this.adapter;
        DayFormatter dayFormatter2 = dayFormatter;
        if (dayFormatter == null) {
            dayFormatter2 = DayFormatter.DEFAULT;
        }
        calendarPagerAdapter.setDayFormatter(dayFormatter2);
    }

    public void setDynamicHeightEnabled(boolean bl) {
        this.mDynamicHeightEnabled = bl;
    }

    public void setHeaderTextAppearance(int n) {
        this.title.setTextAppearance(this.getContext(), n);
    }

    public void setLeftArrowMask(Drawable drawable2) {
        this.leftArrowMask = drawable2;
        this.buttonPast.setImageDrawable(drawable2);
    }

    public void setOnDateChangedListener(OnDateSelectedListener onDateSelectedListener) {
        this.listener = onDateSelectedListener;
    }

    public void setOnMonthChangedListener(OnMonthChangedListener onMonthChangedListener) {
        this.monthListener = onMonthChangedListener;
    }

    public void setOnRangeSelectedListener(OnRangeSelectedListener onRangeSelectedListener) {
        this.rangeListener = onRangeSelectedListener;
    }

    public void setOnTitleClickListener(View.OnClickListener onClickListener) {
        this.title.setOnClickListener(onClickListener);
    }

    public void setPagingEnabled(boolean bl) {
        this.pager.setPagingEnabled(bl);
        this.updateUi();
    }

    public void setRightArrowMask(Drawable drawable2) {
        this.rightArrowMask = drawable2;
        this.buttonFuture.setImageDrawable(drawable2);
    }

    public void setSelectedDate(CalendarDay calendarDay) {
        this.clearSelection();
        if (calendarDay != null) {
            this.setDateSelected(calendarDay, true);
        }
    }

    public void setSelectedDate(Calendar calendar) {
        this.setSelectedDate(CalendarDay.from(calendar));
    }

    public void setSelectedDate(Date date) {
        this.setSelectedDate(CalendarDay.from(date));
    }

    public void setSelectionColor(int n) {
        int n2 = n;
        if (n == 0) {
            if (!this.isInEditMode()) {
                return;
            }
            n2 = -7829368;
        }
        this.accentColor = n2;
        this.adapter.setSelectionColor(n2);
        this.invalidate();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void setSelectionMode(int var1_1) {
        var3_2 = false;
        var2_3 = this.selectionMode;
        this.selectionMode = var1_1;
        switch (var1_1) {
            default: {
                this.selectionMode = 0;
                if (var2_3 != 0) {
                    this.clearSelection();
                    break;
                }
                ** GOTO lbl13
            }
            case 3: {
                this.clearSelection();
            }
lbl13:
            // 3 sources
            case 2: {
                break;
            }
            case 1: {
                if (var2_3 != 2 && var2_3 != 3 || this.getSelectedDates().isEmpty()) break;
                this.setSelectedDate(this.getSelectedDate());
            }
        }
        var4_4 = this.adapter;
        if (this.selectionMode != 0) {
            var3_2 = true;
        }
        var4_4.setSelectionEnabled(var3_2);
    }

    public void setShowOtherDates(int n) {
        this.adapter.setShowOtherDates(n);
    }

    public void setTileHeight(int n) {
        this.tileHeight = n;
        this.requestLayout();
    }

    public void setTileHeightDp(int n) {
        this.setTileHeight(this.dpToPx(n));
    }

    public void setTileSize(int n) {
        this.tileWidth = n;
        this.tileHeight = n;
        this.requestLayout();
    }

    public void setTileSizeDp(int n) {
        this.setTileSize(this.dpToPx(n));
    }

    public void setTileWidth(int n) {
        this.tileWidth = n;
        this.requestLayout();
    }

    public void setTileWidthDp(int n) {
        this.setTileWidth(this.dpToPx(n));
    }

    public void setTitleAnimationOrientation(int n) {
        this.titleChanger.setOrientation(n);
    }

    public void setTitleFormatter(TitleFormatter titleFormatter) {
        TitleFormatter titleFormatter2 = titleFormatter;
        if (titleFormatter == null) {
            titleFormatter2 = DEFAULT_TITLE_FORMATTER;
        }
        this.titleChanger.setTitleFormatter(titleFormatter2);
        this.adapter.setTitleFormatter(titleFormatter2);
        this.updateUi();
    }

    public void setTitleMonths(int n) {
        this.setTitleMonths(this.getResources().getTextArray(n));
    }

    public void setTitleMonths(CharSequence[] arrcharSequence) {
        this.setTitleFormatter(new MonthArrayTitleFormatter(arrcharSequence));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setTopbarVisible(boolean bl) {
        LinearLayout linearLayout = this.topbar;
        int n = bl ? 0 : 8;
        linearLayout.setVisibility(n);
        this.requestLayout();
    }

    public void setWeekDayFormatter(WeekDayFormatter weekDayFormatter) {
        CalendarPagerAdapter<?> calendarPagerAdapter = this.adapter;
        WeekDayFormatter weekDayFormatter2 = weekDayFormatter;
        if (weekDayFormatter == null) {
            weekDayFormatter2 = WeekDayFormatter.DEFAULT;
        }
        calendarPagerAdapter.setWeekDayFormatter(weekDayFormatter2);
    }

    public void setWeekDayLabels(int n) {
        this.setWeekDayLabels(this.getResources().getTextArray(n));
    }

    public void setWeekDayLabels(CharSequence[] arrcharSequence) {
        this.setWeekDayFormatter(new ArrayWeekDayFormatter(arrcharSequence));
    }

    public void setWeekDayTextAppearance(int n) {
        this.adapter.setWeekDayTextAppearance(n);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    protected static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public LayoutParams(int n) {
            super(-1, n);
        }
    }

    public static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean allowClickDaysOutsideCurrentMonth;
        boolean cacheCurrentPosition;
        CalendarMode calendarMode;
        int color;
        CalendarDay currentMonth;
        int dateTextAppearance;
        boolean dynamicHeightEnabled;
        int firstDayOfWeek;
        CalendarDay maxDate;
        CalendarDay minDate;
        int orientation;
        List<CalendarDay> selectedDates;
        int selectionMode;
        int showOtherDates;
        int tileHeightPx;
        int tileWidthPx;
        boolean topbarVisible;
        int weekDayTextAppearance;

        /*
         * Enabled aggressive block sorting
         */
        private SavedState(Parcel parcel) {
            boolean bl = true;
            super(parcel);
            this.color = 0;
            this.dateTextAppearance = 0;
            this.weekDayTextAppearance = 0;
            this.showOtherDates = 4;
            this.allowClickDaysOutsideCurrentMonth = true;
            this.minDate = null;
            this.maxDate = null;
            this.selectedDates = new ArrayList<CalendarDay>();
            this.firstDayOfWeek = 1;
            this.orientation = 0;
            this.tileWidthPx = -1;
            this.tileHeightPx = -1;
            this.topbarVisible = true;
            this.selectionMode = 1;
            this.dynamicHeightEnabled = false;
            this.calendarMode = CalendarMode.MONTHS;
            this.currentMonth = null;
            this.color = parcel.readInt();
            this.dateTextAppearance = parcel.readInt();
            this.weekDayTextAppearance = parcel.readInt();
            this.showOtherDates = parcel.readInt();
            boolean bl2 = parcel.readByte() != 0;
            this.allowClickDaysOutsideCurrentMonth = bl2;
            ClassLoader classLoader = CalendarDay.class.getClassLoader();
            this.minDate = (CalendarDay)parcel.readParcelable(classLoader);
            this.maxDate = (CalendarDay)parcel.readParcelable(classLoader);
            parcel.readTypedList(this.selectedDates, CalendarDay.CREATOR);
            this.firstDayOfWeek = parcel.readInt();
            this.orientation = parcel.readInt();
            this.tileWidthPx = parcel.readInt();
            this.tileHeightPx = parcel.readInt();
            bl2 = parcel.readInt() == 1;
            this.topbarVisible = bl2;
            this.selectionMode = parcel.readInt();
            bl2 = parcel.readInt() == 1;
            this.dynamicHeightEnabled = bl2;
            CalendarMode calendarMode = parcel.readInt() == 1 ? CalendarMode.WEEKS : CalendarMode.MONTHS;
            this.calendarMode = calendarMode;
            this.currentMonth = (CalendarDay)parcel.readParcelable(classLoader);
            bl2 = parcel.readByte() != 0 ? bl : false;
            this.cacheCurrentPosition = bl2;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
            this.color = 0;
            this.dateTextAppearance = 0;
            this.weekDayTextAppearance = 0;
            this.showOtherDates = 4;
            this.allowClickDaysOutsideCurrentMonth = true;
            this.minDate = null;
            this.maxDate = null;
            this.selectedDates = new ArrayList<CalendarDay>();
            this.firstDayOfWeek = 1;
            this.orientation = 0;
            this.tileWidthPx = -1;
            this.tileHeightPx = -1;
            this.topbarVisible = true;
            this.selectionMode = 1;
            this.dynamicHeightEnabled = false;
            this.calendarMode = CalendarMode.MONTHS;
            this.currentMonth = null;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void writeToParcel(Parcel parcel, int n) {
            int n2 = 1;
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.color);
            parcel.writeInt(this.dateTextAppearance);
            parcel.writeInt(this.weekDayTextAppearance);
            parcel.writeInt(this.showOtherDates);
            n = this.allowClickDaysOutsideCurrentMonth ? 1 : 0;
            parcel.writeByte((byte)n);
            parcel.writeParcelable((Parcelable)this.minDate, 0);
            parcel.writeParcelable((Parcelable)this.maxDate, 0);
            parcel.writeTypedList(this.selectedDates);
            parcel.writeInt(this.firstDayOfWeek);
            parcel.writeInt(this.orientation);
            parcel.writeInt(this.tileWidthPx);
            parcel.writeInt(this.tileHeightPx);
            n = this.topbarVisible ? 1 : 0;
            parcel.writeInt(n);
            parcel.writeInt(this.selectionMode);
            n = this.dynamicHeightEnabled ? 1 : 0;
            parcel.writeInt(n);
            n = this.calendarMode == CalendarMode.WEEKS ? 1 : 0;
            parcel.writeInt(n);
            parcel.writeParcelable((Parcelable)this.currentMonth, 0);
            n = this.cacheCurrentPosition ? n2 : 0;
            parcel.writeByte((byte)n);
        }

    }

    public class State {
        private final boolean cacheCurrentPosition;
        private final CalendarMode calendarMode;
        private final int firstDayOfWeek;
        private final CalendarDay maxDate;
        private final CalendarDay minDate;

        private State(StateBuilder stateBuilder) {
            this.calendarMode = stateBuilder.calendarMode;
            this.firstDayOfWeek = stateBuilder.firstDayOfWeek;
            this.minDate = stateBuilder.minDate;
            this.maxDate = stateBuilder.maxDate;
            this.cacheCurrentPosition = stateBuilder.cacheCurrentPosition;
        }
    }

    public class StateBuilder {
        private boolean cacheCurrentPosition = false;
        private CalendarMode calendarMode = CalendarMode.MONTHS;
        private int firstDayOfWeek = Calendar.getInstance().getFirstDayOfWeek();
        private CalendarDay maxDate = null;
        private CalendarDay minDate = null;

        public void commit() {
            MaterialCalendarView.this.commit(new State(this));
        }

        public StateBuilder isCacheCalendarPositionEnabled(boolean bl) {
            this.cacheCurrentPosition = bl;
            return this;
        }

        public StateBuilder setCalendarDisplayMode(CalendarMode calendarMode) {
            this.calendarMode = calendarMode;
            return this;
        }

        public StateBuilder setFirstDayOfWeek(int n) {
            this.firstDayOfWeek = n;
            return this;
        }

        public StateBuilder setMaximumDate(CalendarDay calendarDay) {
            this.maxDate = calendarDay;
            return this;
        }

        public StateBuilder setMinimumDate(CalendarDay calendarDay) {
            this.minDate = calendarDay;
            return this;
        }
    }

}

