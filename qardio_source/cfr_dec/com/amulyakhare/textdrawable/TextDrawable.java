/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$Style
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.graphics.drawable.ShapeDrawable
 *  android.graphics.drawable.shapes.OvalShape
 *  android.graphics.drawable.shapes.RectShape
 *  android.graphics.drawable.shapes.RoundRectShape
 *  android.graphics.drawable.shapes.Shape
 */
package com.amulyakhare.textdrawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

public class TextDrawable
extends ShapeDrawable {
    private final Paint borderPaint;
    private final int borderThickness;
    private final int color;
    private final int fontSize;
    private final int height;
    private final float radius;
    private final RectShape shape;
    private final String text;
    private final Paint textPaint;
    private final int width;

    /*
     * Enabled aggressive block sorting
     */
    private TextDrawable(Builder builder) {
        super((Shape)builder.shape);
        this.shape = builder.shape;
        this.height = builder.height;
        this.width = builder.width;
        this.radius = builder.radius;
        String string2 = builder.toUpperCase ? builder.text.toUpperCase() : builder.text;
        this.text = string2;
        this.color = builder.color;
        this.fontSize = builder.fontSize;
        this.textPaint = new Paint();
        this.textPaint.setColor(builder.textColor);
        this.textPaint.setAntiAlias(true);
        this.textPaint.setFakeBoldText(builder.isBold);
        this.textPaint.setStyle(Paint.Style.FILL);
        this.textPaint.setTypeface(builder.font);
        this.textPaint.setTextAlign(Paint.Align.CENTER);
        this.textPaint.setStrokeWidth((float)builder.borderThickness);
        this.borderThickness = builder.borderThickness;
        this.borderPaint = new Paint();
        this.borderPaint.setColor(this.getDarkerShade(this.color));
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth((float)this.borderThickness);
        this.getPaint().setColor(this.color);
    }

    public static IShapeBuilder builder() {
        return new Builder();
    }

    private void drawBorder(Canvas canvas) {
        RectF rectF = new RectF(this.getBounds());
        rectF.inset((float)(this.borderThickness / 2), (float)(this.borderThickness / 2));
        if (this.shape instanceof OvalShape) {
            canvas.drawOval(rectF, this.borderPaint);
            return;
        }
        if (this.shape instanceof RoundRectShape) {
            canvas.drawRoundRect(rectF, this.radius, this.radius, this.borderPaint);
            return;
        }
        canvas.drawRect(rectF, this.borderPaint);
    }

    private int getDarkerShade(int n) {
        return Color.rgb((int)((int)((float)Color.red((int)n) * 0.9f)), (int)((int)((float)Color.green((int)n) * 0.9f)), (int)((int)((float)Color.blue((int)n) * 0.9f)));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Rect rect = this.getBounds();
        if (this.borderThickness > 0) {
            this.drawBorder(canvas);
        }
        int n = canvas.save();
        canvas.translate((float)rect.left, (float)rect.top);
        int n2 = this.width < 0 ? rect.width() : this.width;
        int n3 = this.height < 0 ? rect.height() : this.height;
        int n4 = this.fontSize < 0 ? Math.min(n2, n3) / 2 : this.fontSize;
        this.textPaint.setTextSize((float)n4);
        canvas.drawText(this.text, (float)(n2 / 2), (float)(n3 / 2) - (this.textPaint.descent() + this.textPaint.ascent()) / 2.0f, this.textPaint);
        canvas.restoreToCount(n);
    }

    public int getIntrinsicHeight() {
        return this.height;
    }

    public int getIntrinsicWidth() {
        return this.width;
    }

    public int getOpacity() {
        return -3;
    }

    public void setAlpha(int n) {
        this.textPaint.setAlpha(n);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.textPaint.setColorFilter(colorFilter);
    }

    public static class Builder
    implements IBuilder,
    IConfigBuilder,
    IShapeBuilder {
        private int borderThickness = 0;
        private int color = -7829368;
        private Typeface font;
        private int fontSize = -1;
        private int height = -1;
        private boolean isBold = false;
        public float radius;
        private RectShape shape = new RectShape();
        private String text = "";
        public int textColor = -1;
        private boolean toUpperCase = false;
        private int width = -1;

        private Builder() {
            this.font = Typeface.create((String)"sans-serif-light", (int)0);
        }

        @Override
        public IConfigBuilder beginConfig() {
            return this;
        }

        @Override
        public TextDrawable build(String string2, int n) {
            this.color = n;
            this.text = string2;
            return new TextDrawable(this);
        }

        @Override
        public IShapeBuilder endConfig() {
            return this;
        }

        @Override
        public IConfigBuilder fontSize(int n) {
            this.fontSize = n;
            return this;
        }

        @Override
        public IBuilder round() {
            this.shape = new OvalShape();
            return this;
        }

        @Override
        public IConfigBuilder toUpperCase() {
            this.toUpperCase = true;
            return this;
        }
    }

    public static interface IBuilder {
        public TextDrawable build(String var1, int var2);
    }

    public static interface IConfigBuilder {
        public IShapeBuilder endConfig();

        public IConfigBuilder fontSize(int var1);

        public IConfigBuilder toUpperCase();
    }

    public static interface IShapeBuilder {
        public IConfigBuilder beginConfig();

        public IBuilder round();
    }

}

