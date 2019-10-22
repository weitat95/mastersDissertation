/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Rect
 *  android.graphics.Typeface
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.SparseArray
 *  android.view.Display
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.TouchDelegate
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.WindowManager
 *  android.view.animation.Animation
 *  android.view.animation.AnimationUtils
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.getqardio.android.datamodel.MinMaxMeasurement;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.utils.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BPMinMaxPanel
extends LinearLayout
implements GestureDetector.OnGestureListener,
View.OnTouchListener {
    private static SimpleDateFormat DATE_FORMAT;
    private static Typeface ROBOTO_TYPEFACE;
    private static Locale locale;
    private Runnable closeRunnable;
    private GestureDetector gestureDetector;
    private float horizontalAnimationStep;
    private boolean isScrolling;
    private boolean isViewer = false;
    private int maxMonth;
    private SparseArray<MinMaxMeasurement> minMaxMeasurementCache;
    private float minMaxPanelHeight;
    private int minMonth;
    private View noDataPanel;
    private OnShownMonthChangedListener onShownMonthChangedListener;
    private View.OnClickListener onTakeMeasurementClickListener;
    private Runnable openRunnable;
    private View puller;
    private ImageView pullerArrow;
    private Animation pullerArrowDown;
    private int pullerArrowState;
    private Animation pullerArrowUp;
    private int shownMonth;
    private Runnable slideBackRunnable;
    private Runnable slideLeftRunnable;
    private Runnable slideRightRunnable;
    private float startY;
    private float verticalAnimationStep;
    private WidgetsContainer[] widgetsContainers;

    static {
        locale = Utils.getLocale();
        ROBOTO_TYPEFACE = Typeface.create((String)"sans-serif-light", (int)0);
        DATE_FORMAT = new SimpleDateFormat("LLLL\nyyyy", locale);
    }

    public BPMinMaxPanel(Context context) {
        super(context);
        this.init(context);
    }

    public BPMinMaxPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context);
    }

    public BPMinMaxPanel(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context);
    }

    static /* synthetic */ WidgetsContainer[] access$300(BPMinMaxPanel bPMinMaxPanel) {
        return bPMinMaxPanel.widgetsContainers;
    }

    private void applyTranslationY(float f) {
        float f2 = f;
        if (f < 0.0f) {
            f2 = 0.0f;
        }
        f = f2;
        if (f2 > (float)this.widgetsContainers[1].content.getHeight()) {
            f = this.widgetsContainers[1].content.getHeight();
        }
        this.setTranslationY(f);
    }

    private void clearData(long l, int n) {
        this.widgetsContainers[n].date.setText((CharSequence)DATE_FORMAT.format(l).toUpperCase());
        this.widgetsContainers[n].avgDia.setText((CharSequence)"-");
        this.widgetsContainers[n].avgSys.setText((CharSequence)"-");
        this.widgetsContainers[n].avgPulse.setText((CharSequence)"-");
        this.widgetsContainers[n].minDia.setText((CharSequence)"-");
        this.widgetsContainers[n].minSys.setText((CharSequence)"-");
        this.widgetsContainers[n].minPulse.setText((CharSequence)"-");
        this.widgetsContainers[n].maxDia.setText((CharSequence)"-");
        this.widgetsContainers[n].maxSys.setText((CharSequence)"-");
        this.widgetsContainers[n].maxPulse.setText((CharSequence)"-");
    }

    private void createRunnables() {
        this.openRunnable = new Runnable(){

            @Override
            public void run() {
                if ((int)BPMinMaxPanel.this.getTranslationY() == 0) {
                    if (BPMinMaxPanel.this.pullerArrowState == 0) {
                        BPMinMaxPanel.this.pullerArrow.clearAnimation();
                        BPMinMaxPanel.this.pullerArrow.startAnimation(BPMinMaxPanel.this.pullerArrowDown);
                        BPMinMaxPanel.this.pullerArrowState = 1;
                    }
                    return;
                }
                BPMinMaxPanel.this.applyTranslationY(BPMinMaxPanel.this.getTranslationY() - BPMinMaxPanel.this.verticalAnimationStep);
                BPMinMaxPanel.this.post((Runnable)this);
            }
        };
        this.closeRunnable = new Runnable(){

            @Override
            public void run() {
                if ((int)BPMinMaxPanel.this.getTranslationY() == BPMinMaxPanel.access$300((BPMinMaxPanel)BPMinMaxPanel.this)[1].content.getHeight()) {
                    if (BPMinMaxPanel.this.pullerArrowState == 1) {
                        BPMinMaxPanel.this.pullerArrow.clearAnimation();
                        BPMinMaxPanel.this.pullerArrow.startAnimation(BPMinMaxPanel.this.pullerArrowUp);
                        BPMinMaxPanel.this.pullerArrowState = 0;
                    }
                    return;
                }
                BPMinMaxPanel.this.applyTranslationY(BPMinMaxPanel.this.getTranslationY() + BPMinMaxPanel.this.verticalAnimationStep);
                BPMinMaxPanel.this.post((Runnable)this);
            }
        };
        this.slideRightRunnable = new Runnable(){

            @Override
            public void run() {
                if (BPMinMaxPanel.this.shownMonth <= BPMinMaxPanel.this.minMonth) {
                    return;
                }
                if (BPMinMaxPanel.access$300((BPMinMaxPanel)BPMinMaxPanel.this)[1].content.getTranslationX() >= (float)BPMinMaxPanel.access$300((BPMinMaxPanel)BPMinMaxPanel.this)[1].content.getWidth()) {
                    BPMinMaxPanel.this.onSlideRightFinished();
                    return;
                }
                BPMinMaxPanel.this.shiftPanels(-Math.min(BPMinMaxPanel.this.horizontalAnimationStep, (float)BPMinMaxPanel.access$300((BPMinMaxPanel)BPMinMaxPanel.this)[1].content.getWidth() - BPMinMaxPanel.access$300((BPMinMaxPanel)BPMinMaxPanel.this)[1].content.getTranslationX()));
                BPMinMaxPanel.this.post((Runnable)this);
            }
        };
        this.slideLeftRunnable = new Runnable(){

            @Override
            public void run() {
                if (BPMinMaxPanel.this.shownMonth >= BPMinMaxPanel.this.maxMonth) {
                    return;
                }
                if (-BPMinMaxPanel.access$300((BPMinMaxPanel)BPMinMaxPanel.this)[1].content.getTranslationX() >= (float)BPMinMaxPanel.access$300((BPMinMaxPanel)BPMinMaxPanel.this)[1].content.getWidth()) {
                    BPMinMaxPanel.this.onSlideLeftFinished();
                    return;
                }
                BPMinMaxPanel.this.shiftPanels(Math.min(BPMinMaxPanel.this.horizontalAnimationStep, (float)BPMinMaxPanel.access$300((BPMinMaxPanel)BPMinMaxPanel.this)[1].content.getWidth() + BPMinMaxPanel.access$300((BPMinMaxPanel)BPMinMaxPanel.this)[1].content.getTranslationX()));
                BPMinMaxPanel.this.post((Runnable)this);
            }
        };
        this.slideBackRunnable = new Runnable(){

            @Override
            public void run() {
                if (Math.abs(BPMinMaxPanel.access$300((BPMinMaxPanel)BPMinMaxPanel.this)[1].content.getTranslationX()) > BPMinMaxPanel.this.verticalAnimationStep) {
                    BPMinMaxPanel.this.shiftPanels(Math.signum(BPMinMaxPanel.access$300((BPMinMaxPanel)BPMinMaxPanel.this)[1].content.getTranslationX()) * BPMinMaxPanel.this.verticalAnimationStep);
                    BPMinMaxPanel.this.post((Runnable)this);
                    return;
                }
                BPMinMaxPanel.this.shiftPanels(BPMinMaxPanel.access$300((BPMinMaxPanel)BPMinMaxPanel.this)[1].content.getTranslationX());
            }
        };
    }

    private void finishStateChanging() {
        if (this.getTranslationY() < (float)(this.widgetsContainers[1].content.getHeight() / 2)) {
            this.post(this.openRunnable);
            return;
        }
        this.post(this.closeRunnable);
    }

    private void init(Context context) {
        this.minMaxMeasurementCache = new SparseArray();
        this.shownMonth = -1;
        this.minMonth = Integer.MAX_VALUE;
        this.maxMonth = Integer.MIN_VALUE;
        this.widgetsContainers = new WidgetsContainer[3];
        this.widgetsContainers[0] = new WidgetsContainer();
        this.widgetsContainers[1] = new WidgetsContainer();
        this.widgetsContainers[2] = new WidgetsContainer();
        this.gestureDetector = new GestureDetector((GestureDetector.OnGestureListener)this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.minMaxPanelHeight = context.getResources().getDimension(2131427596);
        this.verticalAnimationStep = 5.0f * displayMetrics.density;
        this.horizontalAnimationStep = 15.0f * displayMetrics.density;
        this.pullerArrowState = 0;
        this.pullerArrowUp = AnimationUtils.loadAnimation((Context)context, (int)2131034129);
        this.pullerArrowUp.setFillAfter(true);
        this.pullerArrowDown = AnimationUtils.loadAnimation((Context)context, (int)2131034128);
        this.pullerArrowDown.setFillAfter(true);
        this.createRunnables();
        LayoutInflater.from((Context)context).inflate(2130968650, (ViewGroup)this);
        this.puller = this.findViewById(2131820949);
        this.pullerArrow = (ImageView)this.findViewById(2131820950);
        this.storeWidgetsContainer(this.findViewById(2131820951), 0);
        this.storeWidgetsContainer(this.findViewById(2131820952), 1);
        this.storeWidgetsContainer(this.findViewById(2131820953), 2);
        this.noDataPanel = this.findViewById(2131820946);
        this.pullerArrow.post(new Runnable(){

            @Override
            public void run() {
                BPMinMaxPanel.this.pullerArrow.setPivotX((float)(BPMinMaxPanel.this.pullerArrow.getWidth() / 2));
                BPMinMaxPanel.this.pullerArrow.setPivotY((float)(BPMinMaxPanel.this.pullerArrow.getHeight() / 2));
                BPMinMaxPanel.this.pullerArrow.setRotation(180.0f);
            }
        });
        context = (View)this.puller.getParent();
        context.post(new Runnable((View)context){
            final /* synthetic */ View val$pullerParent;
            {
                this.val$pullerParent = view;
            }

            @Override
            public void run() {
                Rect rect = new Rect();
                BPMinMaxPanel.this.puller.getHitRect(rect);
                rect.top -= BPMinMaxPanel.this.puller.getHeight() * 2;
                rect.bottom += BPMinMaxPanel.this.puller.getHeight();
                this.val$pullerParent.setTouchDelegate(new TouchDelegate(rect, BPMinMaxPanel.this.puller));
            }
        });
        this.puller.setOnTouchListener((View.OnTouchListener)this);
        this.setTranslationY(this.minMaxPanelHeight);
        this.widgetsContainers[1].content.post(new Runnable(){

            @Override
            public void run() {
                BPMinMaxPanel.this.setTranslationY((float)BPMinMaxPanel.access$300((BPMinMaxPanel)BPMinMaxPanel.this)[1].content.getHeight());
            }
        });
        this.widgetsContainers[1].content.post(new Runnable(){

            @Override
            public void run() {
                BPMinMaxPanel.this.setPanelsShift();
            }
        });
        this.setupScrollListener(this.findViewById(2131820940));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onSlideLeftFinished() {
        ++this.shownMonth;
        Object object = this.widgetsContainers[0];
        this.widgetsContainers[0] = this.widgetsContainers[1];
        this.widgetsContainers[1] = this.widgetsContainers[2];
        this.widgetsContainers[2] = object;
        object = (MinMaxMeasurement)this.minMaxMeasurementCache.get(this.shownMonth + 1);
        if (object != null) {
            this.setData((MinMaxMeasurement)object, 2);
        } else {
            this.clearData(Utils.getDateByMonthNumber(this.shownMonth + 1), 2);
        }
        this.setPanelsShift();
        if (this.onShownMonthChangedListener != null) {
            this.onShownMonthChangedListener.onShownMonthChanged(this.shownMonth);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onSlideRightFinished() {
        --this.shownMonth;
        Object object = this.widgetsContainers[2];
        this.widgetsContainers[2] = this.widgetsContainers[1];
        this.widgetsContainers[1] = this.widgetsContainers[0];
        this.widgetsContainers[0] = object;
        object = (MinMaxMeasurement)this.minMaxMeasurementCache.get(this.shownMonth - 1);
        if (object != null) {
            this.setData((MinMaxMeasurement)object, 0);
        } else {
            this.clearData(Utils.getDateByMonthNumber(this.shownMonth - 1), 0);
        }
        this.setPanelsShift();
        if (this.onShownMonthChangedListener != null) {
            this.onShownMonthChangedListener.onShownMonthChanged(this.shownMonth);
        }
    }

    private void processScrollFinished() {
        float f = (float)this.widgetsContainers[1].content.getWidth() * 0.2f;
        if (-this.widgetsContainers[1].content.getTranslationX() > f) {
            this.post(this.slideLeftRunnable);
            return;
        }
        if (this.widgetsContainers[1].content.getTranslationX() > f) {
            this.post(this.slideRightRunnable);
            return;
        }
        this.post(this.slideBackRunnable);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setData(MinMaxMeasurement minMaxMeasurement, int n) {
        TextView textView = this.widgetsContainers[n].date;
        String string2 = minMaxMeasurement.measureDate != null ? DATE_FORMAT.format(minMaxMeasurement.measureDate).toUpperCase() : "";
        textView.setText((CharSequence)string2);
        this.setValueAsInt(this.widgetsContainers[n].avgDia, minMaxMeasurement.avgDia);
        this.setValueAsInt(this.widgetsContainers[n].avgSys, minMaxMeasurement.avgSys);
        this.setValueAsInt(this.widgetsContainers[n].avgPulse, minMaxMeasurement.avgPulse);
        this.setValueAsInt(this.widgetsContainers[n].minDia, minMaxMeasurement.minDia);
        this.setValueAsInt(this.widgetsContainers[n].minSys, minMaxMeasurement.minSys);
        this.setValueAsInt(this.widgetsContainers[n].minPulse, minMaxMeasurement.minPulse);
        this.setValueAsInt(this.widgetsContainers[n].maxDia, minMaxMeasurement.maxDia);
        this.setValueAsInt(this.widgetsContainers[n].maxSys, minMaxMeasurement.maxSys);
        this.setValueAsInt(this.widgetsContainers[n].maxPulse, minMaxMeasurement.maxPulse);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setPanelValues(int n) {
        MinMaxMeasurement minMaxMeasurement = (MinMaxMeasurement)this.minMaxMeasurementCache.get(n);
        if (minMaxMeasurement != null) {
            this.setData(minMaxMeasurement, 1);
        } else {
            this.clearData(Utils.getDateByMonthNumber(n), 1);
        }
        if ((minMaxMeasurement = (MinMaxMeasurement)this.minMaxMeasurementCache.get(n - 1)) != null) {
            this.setData(minMaxMeasurement, 0);
        } else {
            this.clearData(Utils.getDateByMonthNumber(n - 1), 0);
        }
        if ((minMaxMeasurement = (MinMaxMeasurement)this.minMaxMeasurementCache.get(n + 1)) != null) {
            this.setData(minMaxMeasurement, 2);
            return;
        }
        this.clearData(Utils.getDateByMonthNumber(n + 1), 2);
    }

    private void setPanelsShift() {
        this.widgetsContainers[1].content.setTranslationX(0.0f);
        this.widgetsContainers[0].content.setTranslationX((float)(-this.widgetsContainers[0].content.getWidth()));
        this.widgetsContainers[2].content.setTranslationX((float)this.widgetsContainers[1].content.getWidth());
    }

    private void setValueAsInt(TextView textView, Float f) {
        if (f != null) {
            textView.setText((CharSequence)Utils.formatInteger(Math.round(f.floatValue())));
            return;
        }
        textView.setText((CharSequence)"-");
    }

    private void setupScrollListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (BPMinMaxPanel.this.gestureDetector.onTouchEvent(motionEvent)) {
                    return true;
                }
                if ((motionEvent.getAction() == 1 || motionEvent.getAction() == 3) && BPMinMaxPanel.this.isScrolling) {
                    BPMinMaxPanel.this.isScrolling = false;
                    BPMinMaxPanel.this.processScrollFinished();
                }
                return false;
            }
        });
    }

    private void shiftPanels(float f) {
        for (WidgetsContainer widgetsContainer : this.widgetsContainers) {
            widgetsContainer.content.setTranslationX(widgetsContainer.content.getTranslationX() - f);
        }
    }

    private void storeWidgetsContainer(View view, int n) {
        this.widgetsContainers[n].content = view;
        this.widgetsContainers[n].date = (TextView)view.findViewById(2131820866);
        this.widgetsContainers[n].avgDia = (TextView)view.findViewById(2131820955);
        this.widgetsContainers[n].avgSys = (TextView)view.findViewById(2131820954);
        this.widgetsContainers[n].avgPulse = (TextView)view.findViewById(2131820956);
        this.widgetsContainers[n].minDia = (TextView)view.findViewById(2131820958);
        this.widgetsContainers[n].minSys = (TextView)view.findViewById(2131820957);
        this.widgetsContainers[n].minPulse = (TextView)view.findViewById(2131820959);
        this.widgetsContainers[n].maxDia = (TextView)view.findViewById(2131820961);
        this.widgetsContainers[n].maxSys = (TextView)view.findViewById(2131820960);
        this.widgetsContainers[n].maxPulse = (TextView)view.findViewById(2131820962);
    }

    private void updatePullerNoDataView() {
        if (!this.isViewer) {
            this.widgetsContainers[1].content.setVisibility(8);
            this.noDataPanel.setVisibility(0);
            this.findViewById(2131820948).setOnClickListener(this.onTakeMeasurementClickListener);
            ((TextView)this.findViewById(2131820947)).setTypeface(ROBOTO_TYPEFACE);
        }
    }

    private void updatePullerView() {
        this.widgetsContainers[1].content.setVisibility(0);
        this.noDataPanel.setVisibility(8);
    }

    public void changeLocale() {
        locale = Utils.getLocale();
        DATE_FORMAT = new SimpleDateFormat("LLLL\nyyyy", locale);
    }

    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public void onLongPress(MotionEvent motionEvent) {
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (f < 0.0f && this.shownMonth - 1 >= this.minMonth || f > 0.0f && this.shownMonth + 1 <= this.maxMonth) {
            this.isScrolling = true;
            this.shiftPanels(f);
            return true;
        }
        return false;
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (MotionEventCompat.getActionMasked(motionEvent)) {
            default: {
                return false;
            }
            case 0: {
                this.startY = motionEvent.getRawY() - this.getTranslationY();
                return true;
            }
            case 2: {
                this.applyTranslationY(motionEvent.getRawY() - this.startY);
                return true;
            }
            case 1: 
            case 3: 
        }
        this.finishStateChanging();
        return false;
    }

    public void setData(Cursor cursor) {
        this.minMaxMeasurementCache.clear();
        this.minMonth = Integer.MAX_VALUE;
        this.maxMonth = Integer.MIN_VALUE;
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                MinMaxMeasurement minMaxMeasurement = MeasurementHelper.BloodPressure.parseMinMaxMeasurement(cursor);
                if (minMaxMeasurement.measureDate != null) {
                    int n = Utils.getMonthNumber(minMaxMeasurement.measureDate.getTime());
                    this.minMaxMeasurementCache.put(n, (Object)minMaxMeasurement);
                    if (n > this.maxMonth) {
                        this.maxMonth = n;
                    }
                    if (n < this.minMonth) {
                        this.minMonth = n;
                    }
                }
                cursor.moveToNext();
            }
            if (this.shownMonth != -1) {
                this.setPanelValues(this.shownMonth);
            }
            this.updatePullerView();
            return;
        }
        this.updatePullerNoDataView();
    }

    public void setIsViewer(boolean bl) {
        this.isViewer = bl;
    }

    public void setOnShownMonthChangedListener(OnShownMonthChangedListener onShownMonthChangedListener) {
        this.onShownMonthChangedListener = onShownMonthChangedListener;
    }

    public void setOnTakeMeasurementClickListener(View.OnClickListener onClickListener) {
        this.onTakeMeasurementClickListener = onClickListener;
    }

    public void setShownMonth(int n) {
        if (n != this.shownMonth) {
            this.shownMonth = n;
            this.setPanelValues(this.shownMonth);
        }
    }

    public static interface OnShownMonthChangedListener {
        public void onShownMonthChanged(int var1);
    }

    private static class WidgetsContainer {
        public TextView avgDia;
        public TextView avgPulse;
        public TextView avgSys;
        public View content;
        public TextView date;
        public TextView maxDia;
        public TextView maxPulse;
        public TextView maxSys;
        public TextView minDia;
        public TextView minPulse;
        public TextView minSys;

        private WidgetsContainer() {
        }
    }

}

