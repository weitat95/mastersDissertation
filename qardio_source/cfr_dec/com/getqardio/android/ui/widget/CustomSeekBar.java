/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.widget.SeekBar
 *  android.widget.SeekBar$OnSeekBarChangeListener
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.widget.SeekBar;
import com.getqardio.android.R;

public class CustomSeekBar
extends AppCompatSeekBar
implements SeekBar.OnSeekBarChangeListener {
    private int incrementStep;
    private OnProgressChangedListener listener;
    private int maxValue;
    private int minValue;
    private int step;

    public CustomSeekBar(Context context) {
        super(context);
        this.init();
    }

    public CustomSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.obtainAttributes(context, attributeSet);
        this.init();
    }

    public CustomSeekBar(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.obtainAttributes(context, attributeSet);
        this.init();
    }

    private void init() {
        if (this.minValue < 0 || this.maxValue < 0 || this.incrementStep < 0) {
            throw new IllegalArgumentException("MinValue, MaxValue, IncrementStep cannot be < 0");
        }
        if (this.minValue == this.maxValue) {
            throw new IllegalArgumentException("minValue = maValue");
        }
        if (this.minValue > this.maxValue) {
            throw new IllegalArgumentException("minValue > maxValue");
        }
        if (this.incrementStep < this.minValue || this.incrementStep > this.maxValue) {
            throw new IllegalArgumentException("Increment step has wrong value");
        }
        this.step = this.getMax() / ((this.maxValue - this.minValue) / this.incrementStep);
        this.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener)this);
    }

    private int normalizeNumber(int n) {
        return Math.round((this.incrementStep / 2 + n) / this.incrementStep) * this.incrementStep;
    }

    private void obtainAttributes(Context context, AttributeSet attributeSet) {
        context = context.obtainStyledAttributes(attributeSet, R.styleable.CustomSeekBar);
        this.minValue = context.getInt(0, 0);
        this.maxValue = context.getInt(1, 0);
        this.incrementStep = context.getInt(2, 0);
        context.recycle();
    }

    public int getCustomProgress() {
        return this.normalizeNumber((this.maxValue - this.minValue) * this.getProgress() / this.getMax() + this.incrementStep);
    }

    public int getIncrementStep() {
        return this.incrementStep;
    }

    public int getMaxValue() {
        return this.maxValue;
    }

    public int getMinValue() {
        return this.minValue;
    }

    public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        int n = (int)(Math.round((double)super.getProgress() / (double)this.step) * (long)this.step);
        this.setProgress(n);
        if (this.listener != null) {
            this.listener.onProgressChanged(this, this.normalizeNumber(n));
        }
    }

    public void resetProgress() {
        this.setCustomProgress(this.minValue);
    }

    public void setCustomProgress(int n) {
        if (this.maxValue != 0 || this.minValue != 0) {
            this.setProgress((n - this.incrementStep) * this.getMax() / (this.maxValue - this.minValue));
            this.onSizeChanged(this.getWidth(), this.getHeight(), 0, 0);
            if (this.listener != null) {
                this.listener.onProgressChanged(this, this.normalizeNumber(n));
            }
        }
    }

    public void setIncrementStep(int n) {
        this.incrementStep = n;
    }

    public void setListener(OnProgressChangedListener onProgressChangedListener) {
        this.listener = onProgressChangedListener;
    }

    public void setMaxValue(int n) {
        this.maxValue = n;
    }

    public void setMinValue(int n) {
        this.minValue = n;
    }

    public static interface OnProgressChangedListener {
        public void onProgressChanged(CustomSeekBar var1, int var2);
    }

}

