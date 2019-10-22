/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.ShapeDrawable
 *  android.graphics.drawable.shapes.Shape
 *  android.util.AttributeSet
 */
package com.getbase.floatingactionbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.R;

public class AddFloatingActionButton
extends FloatingActionButton {
    int mPlusColor;

    public AddFloatingActionButton(Context context) {
        this(context, null);
    }

    public AddFloatingActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AddFloatingActionButton(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    @Override
    Drawable getIconDrawable() {
        float f = this.getDimension(R.dimen.fab_icon_size);
        float f2 = f / 2.0f;
        float f3 = this.getDimension(R.dimen.fab_plus_icon_size);
        float f4 = this.getDimension(R.dimen.fab_plus_icon_stroke) / 2.0f;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new Shape((f - f3) / 2.0f, f2, f4, f){
            final /* synthetic */ float val$iconHalfSize;
            final /* synthetic */ float val$iconSize;
            final /* synthetic */ float val$plusHalfStroke;
            final /* synthetic */ float val$plusOffset;
            {
                this.val$plusOffset = f;
                this.val$iconHalfSize = f2;
                this.val$plusHalfStroke = f3;
                this.val$iconSize = f4;
            }

            public void draw(Canvas canvas, Paint paint) {
                float f = this.val$plusOffset;
                float f2 = this.val$iconHalfSize;
                float f3 = this.val$plusHalfStroke;
                float f4 = this.val$iconSize;
                float f5 = this.val$plusOffset;
                float f6 = this.val$iconHalfSize;
                canvas.drawRect(f, f2 - f3, f4 - f5, this.val$plusHalfStroke + f6, paint);
                f = this.val$iconHalfSize;
                f2 = this.val$plusHalfStroke;
                f3 = this.val$plusOffset;
                f4 = this.val$iconHalfSize;
                canvas.drawRect(f - f2, f3, this.val$plusHalfStroke + f4, this.val$iconSize - this.val$plusOffset, paint);
            }
        });
        Paint paint = shapeDrawable.getPaint();
        paint.setColor(this.mPlusColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        return shapeDrawable;
    }

    public int getPlusColor() {
        return this.mPlusColor;
    }

    @Override
    void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.AddFloatingActionButton, 0, 0);
        this.mPlusColor = typedArray.getColor(R.styleable.AddFloatingActionButton_fab_plusIconColor, this.getColor(17170443));
        typedArray.recycle();
        super.init(context, attributeSet);
    }

    @Override
    public void setIcon(int n) {
        throw new UnsupportedOperationException("Use FloatingActionButton if you want to use custom icon");
    }

    public void setPlusColor(int n) {
        if (this.mPlusColor != n) {
            this.mPlusColor = n;
            this.updateBackground();
        }
    }

    public void setPlusColorResId(int n) {
        this.setPlusColor(this.getColor(n));
    }

}

