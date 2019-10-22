/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 */
package com.github.mikephil.charting.components;

import android.graphics.Paint;
import com.github.mikephil.charting.components.ComponentBase;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

public class Description
extends ComponentBase {
    private MPPointF mPosition;
    private Paint.Align mTextAlign = Paint.Align.RIGHT;
    private String text = "Description Label";

    public Description() {
        this.mTextSize = Utils.convertDpToPixel(8.0f);
    }

    public MPPointF getPosition() {
        return this.mPosition;
    }

    public String getText() {
        return this.text;
    }

    public Paint.Align getTextAlign() {
        return this.mTextAlign;
    }
}

