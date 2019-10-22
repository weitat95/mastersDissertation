/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Rect
 *  android.graphics.Typeface
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 *  android.util.AttributeSet
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 *  android.widget.OverScroller
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;
import com.getqardio.android.R;
import com.getqardio.android.ui.widget.OnGoalValueChangedListener;
import com.getqardio.android.ui.widget.WeightHelper;
import com.getqardio.android.utils.Utils;

public class SetGoalView
extends View
implements GestureDetector.OnGestureListener {
    private static final String NO_WEIGHT_STRING = String.valueOf(0.0f);
    private int backgroundColor;
    private int centerLineColor;
    private int centerLineWidth;
    private int centerY;
    private int centralCircleLineColor;
    private int centralCircleLineWidth;
    private Paint centralCirclePaint;
    private int centralCircleRadius;
    private int centralCircleTextColor;
    private Paint centralCircleTextPaint;
    private int centralCircleTextSize;
    private float currentWeight;
    private Circle currentWeightCircle;
    private int currentWeightCircleLineColor;
    private int currentWeightCircleLineWidth;
    private Paint currentWeightCirclePaint;
    private int currentWeightCircleRadius;
    private Paint currentWeightInnerCirclePaint;
    private int currentWeightTextColor;
    private Paint currentWeightTextPaint;
    private int currentWeightTextSize;
    private GestureDetector detector;
    private float goalWeight;
    private Handler handler = new Handler(new Handler.Callback(){

        public boolean handleMessage(Message message) {
            if (SetGoalView.this.onTutorialDiscoveredListener != null) {
                SetGoalView.this.onTutorialDiscoveredListener.onTutorialDiscovered();
                return true;
            }
            return false;
        }
    });
    private Helper helper;
    private Paint linePaint;
    private Rect measuredRect;
    private float minWeight;
    private Circle newWeightCircle;
    private OnGoalValueChangedListener onGoalValueChangedListener;
    private OnTutorialDiscoveredListener onTutorialDiscoveredListener;
    private OverScroller scroller;
    private Rect textBounds;
    private boolean tutorial = true;
    private int tutorialCircleFillColor;
    private Paint tutorialInnerCirclePaint;
    private float valuesPerPixel;
    private WeightHelper weightHelper;
    private String weightUnit;

    public SetGoalView(Context context) {
        this(context, null);
    }

    public SetGoalView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SetGoalView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context, attributeSet);
    }

    private void changeCurrentWeight() {
        this.goalWeight = Utils.round(Float.valueOf(this.minWeight + (float)this.currentWeightCircle.getLeftEdgeX() * this.valuesPerPixel), 1);
        if (this.onGoalValueChangedListener != null) {
            this.onGoalValueChangedListener.onValueChanged(this.goalWeight);
        }
    }

    private String createWeightString(float f) {
        String string2 = this.helper.valueToWeightString(f);
        String string3 = string2 + " " + this.weightUnit;
        this.centralCircleTextPaint.getTextBounds(string3, 0, string3.length(), this.textBounds);
        if (this.textBounds.width() > this.newWeightCircle.getDiameter()) {
            string3 = string2 + "...";
        }
        return string3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawCurrentWeightCircle(Canvas canvas, float f) {
        String string2 = f != 0.0f ? this.helper.valueToWeightString(f) : NO_WEIGHT_STRING;
        this.textBounds.set(0, 0, 0, 0);
        this.currentWeightTextPaint.getTextBounds(string2, 0, string2.length(), this.textBounds);
        int n = this.currentWeightCircle.centerX;
        int n2 = this.textBounds.width() / 2;
        int n3 = this.centerY;
        int n4 = this.textBounds.height() / 2;
        canvas.drawCircle((float)this.currentWeightCircle.centerX, (float)this.centerY, (float)this.currentWeightCircleRadius, this.currentWeightInnerCirclePaint);
        canvas.drawCircle((float)this.currentWeightCircle.centerX, (float)this.centerY, (float)this.currentWeightCircleRadius, this.currentWeightCirclePaint);
        canvas.drawText(string2, (float)(n - n2), (float)(n3 + n4), this.currentWeightTextPaint);
    }

    private void drawLines(Canvas canvas) {
        canvas.drawLine(0.0f, (float)this.centerY, (float)this.measuredRect.right, (float)this.centerY, this.linePaint);
    }

    private int getCurrentXOffset() {
        return this.currentWeightCircle.centerX;
    }

    private int getMaxXOffset() {
        return this.measuredRect.right - this.currentWeightCircle.radius;
    }

    private int getMinXOffset() {
        return this.currentWeightCircle.radius;
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.currentWeightCircle = new Circle();
        this.newWeightCircle = new Circle();
        this.obtainAttributes(context, attributeSet);
        context = Typeface.create((String)this.getResources().getString(2131362541), (int)0);
        this.measuredRect = new Rect(0, 0, 0, 0);
        this.centralCirclePaint = new Paint();
        this.centralCirclePaint.setStyle(Paint.Style.STROKE);
        this.centralCirclePaint.setStrokeWidth((float)this.centralCircleLineWidth);
        this.centralCirclePaint.setAntiAlias(true);
        this.centralCirclePaint.setColor(this.centralCircleLineColor);
        this.currentWeightCirclePaint = new Paint();
        this.currentWeightCirclePaint.setStyle(Paint.Style.STROKE);
        this.currentWeightCirclePaint.setStrokeWidth((float)this.currentWeightCircleLineWidth);
        this.currentWeightCirclePaint.setColor(this.currentWeightCircleLineColor);
        this.currentWeightCirclePaint.setAntiAlias(true);
        this.linePaint = new Paint();
        this.linePaint.setColor(this.centerLineColor);
        this.linePaint.setStyle(Paint.Style.STROKE);
        this.linePaint.setStrokeWidth((float)this.centerLineWidth);
        this.centralCircleTextPaint = new Paint();
        this.centralCircleTextPaint.setColor(this.centralCircleTextColor);
        this.centralCircleTextPaint.setTextSize((float)this.centralCircleTextSize);
        this.centralCircleTextPaint.setTypeface((Typeface)context);
        this.centralCircleTextPaint.setAntiAlias(true);
        this.currentWeightTextPaint = new Paint();
        this.currentWeightTextPaint.setColor(this.currentWeightTextColor);
        this.currentWeightTextPaint.setTextSize((float)this.currentWeightTextSize);
        this.currentWeightTextPaint.setTypeface((Typeface)context);
        this.currentWeightTextPaint.setAntiAlias(true);
        this.currentWeightInnerCirclePaint = new Paint();
        this.currentWeightInnerCirclePaint.setAntiAlias(true);
        this.currentWeightInnerCirclePaint.setColor(-1);
        this.tutorialInnerCirclePaint = new Paint();
        this.tutorialInnerCirclePaint.setAntiAlias(true);
        this.tutorialInnerCirclePaint.setColor(this.tutorialCircleFillColor);
        this.textBounds = new Rect();
        this.scroller = new OverScroller(this.getContext());
        this.detector = new GestureDetector(this.getContext(), (GestureDetector.OnGestureListener)this);
    }

    private void obtainAttributes(Context context, AttributeSet attributeSet) {
        context = context.obtainStyledAttributes(attributeSet, R.styleable.SetGoalView);
        this.backgroundColor = context.getColor(14, 0);
        this.centerLineColor = context.getColor(0, 0);
        this.centerLineWidth = context.getDimensionPixelOffset(1, 0);
        this.centralCircleLineColor = context.getColor(3, 0);
        this.centralCircleLineWidth = context.getDimensionPixelOffset(4, 0);
        this.centralCircleTextSize = context.getDimensionPixelOffset(5, 0);
        this.centralCircleTextColor = context.getColor(6, 0);
        this.centralCircleRadius = context.getDimensionPixelOffset(2, 0);
        this.currentWeightCircleLineColor = context.getColor(8, 0);
        this.currentWeightCircleLineWidth = context.getDimensionPixelOffset(9, 0);
        this.currentWeightTextSize = context.getDimensionPixelOffset(11, 0);
        this.currentWeightTextColor = context.getColor(10, 0);
        this.currentWeightCircleRadius = context.getDimensionPixelOffset(7, 0);
        this.tutorialCircleFillColor = context.getColor(12, 0);
        this.currentWeightCircle.radius = this.currentWeightCircleRadius;
        context.recycle();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setCurrentXOffset(int n) {
        int n2 = this.getMaxXOffset();
        if (n <= n2) {
            n2 = n;
            if (n < this.getMinXOffset()) {
                n2 = this.getMinXOffset();
            }
        }
        this.currentWeightCircle.centerX = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void computeScroll() {
        super.computeScroll();
        if (this.scroller.computeScrollOffset()) {
            int n = this.scroller.getCurrX() < this.getMinXOffset() ? this.getMinXOffset() : this.scroller.getCurrX();
            this.setCurrentXOffset(n);
            this.changeCurrentWeight();
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public float getGoalWeight() {
        return this.goalWeight;
    }

    public boolean onDown(MotionEvent motionEvent) {
        if (!this.tutorial) {
            this.scroller.forceFinished(true);
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(this.backgroundColor);
        float f = this.tutorial ? this.currentWeight : this.goalWeight;
        String string2 = f != 0.0f ? this.createWeightString(f) : NO_WEIGHT_STRING;
        this.drawLines(canvas);
        if (!this.tutorial) {
            if (this.currentWeight != (float)Math.round(this.goalWeight)) {
                this.drawCurrentWeightCircle(canvas, this.currentWeight);
            }
        } else if (this.currentWeight == 0.0f) {
            this.currentWeightCircle.centerX = this.getMinXOffset();
            this.drawCurrentWeightCircle(canvas, 0.0f);
        }
        this.textBounds.set(0, 0, 0, 0);
        canvas.drawCircle((float)this.newWeightCircle.centerX, (float)this.centerY, (float)this.centralCircleRadius, this.tutorialInnerCirclePaint);
        canvas.drawCircle((float)this.newWeightCircle.centerX, (float)this.centerY, (float)this.centralCircleRadius, this.centralCirclePaint);
        this.centralCircleTextPaint.getTextBounds(string2, 0, string2.length(), this.textBounds);
        this.textBounds.bottom = 0;
        int n = this.newWeightCircle.centerX;
        int n2 = this.textBounds.width() / 2;
        int n3 = this.centerY;
        int n4 = this.textBounds.height() / 2;
        canvas.drawText(string2, (float)(n - n2), (float)(n3 + n4), this.centralCircleTextPaint);
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        this.scroller.forceFinished(true);
        this.scroller.fling(this.getCurrentXOffset(), 0, (int)(-f) / 2, 0, 0, this.getMaxXOffset(), 0, this.getHeight(), 1, 0);
        ViewCompat.postInvalidateOnAnimation(this);
        return true;
    }

    public void onLongPress(MotionEvent motionEvent) {
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        this.measuredRect.set(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
        this.centerY = Math.max(this.centralCircleRadius, this.currentWeightCircleRadius) + 10;
        this.newWeightCircle.set(this.centralCircleRadius, this.measuredRect.width() / 2, this.centerY);
        this.currentWeightCircle.centerY = this.centerY;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.tutorial) {
            this.tutorial = false;
            this.handler.sendEmptyMessageAtTime(1, 1000L);
        }
        f = f > 0.0f ? 0.05f : -0.05f;
        this.setCurrentXOffset((int)((float)this.getCurrentXOffset() + Utils.round(Float.valueOf(f / this.valuesPerPixel), 0)));
        this.changeCurrentWeight();
        ViewCompat.postInvalidateOnAnimation(this);
        return true;
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.currentWeight != 0.0f && this.detector.onTouchEvent(motionEvent);
    }

    public void setCurrentWeight(float f) {
        this.goalWeight = f;
        this.currentWeight = f;
        if (f != 0.0f) {
            float f2 = this.weightHelper.calculateMaxWeight(f);
            this.minWeight = this.weightHelper.calculateMinWeight(f);
            this.valuesPerPixel = (f2 - this.minWeight) / (float)(this.measuredRect.right - this.currentWeightCircle.getDiameter());
            this.setCurrentXOffset((int)((this.goalWeight - this.minWeight) / this.valuesPerPixel));
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void setGoalWeight(float f) {
        this.goalWeight = f;
        this.setCurrentXOffset((int)((float)this.getMinXOffset() + (f - this.minWeight) / this.valuesPerPixel));
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void setHelper(Helper helper) {
        this.helper = helper;
    }

    public void setOnGoalValueChangedListener(OnGoalValueChangedListener onGoalValueChangedListener) {
        this.onGoalValueChangedListener = onGoalValueChangedListener;
    }

    public void setOnTutorialDiscoveredListener(OnTutorialDiscoveredListener onTutorialDiscoveredListener) {
        this.onTutorialDiscoveredListener = onTutorialDiscoveredListener;
    }

    public void setTutorial(boolean bl) {
        this.tutorial = bl;
        if (bl) {
            this.goalWeight = this.currentWeight;
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void setWeightHelper(WeightHelper weightHelper) {
        this.weightHelper = weightHelper;
    }

    public void setWeightUnit(String string2) {
        this.weightUnit = string2;
    }

    private static class Circle {
        int centerX;
        int centerY;
        int radius;

        private Circle() {
        }

        int getDiameter() {
            return this.radius * 2;
        }

        int getLeftEdgeX() {
            return this.centerX - this.radius;
        }

        void set(int n, int n2, int n3) {
            this.radius = n;
            this.centerX = n2;
            this.centerY = n3;
        }
    }

    public static interface Helper {
        public String valueToWeightString(float var1);
    }

    public static interface OnTutorialDiscoveredListener {
        public void onTutorialDiscovered();
    }

}

