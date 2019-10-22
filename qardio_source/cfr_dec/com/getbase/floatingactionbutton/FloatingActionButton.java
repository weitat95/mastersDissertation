/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.LinearGradient
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Rect
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.ShapeDrawable
 *  android.graphics.drawable.ShapeDrawable$ShaderFactory
 *  android.graphics.drawable.StateListDrawable
 *  android.graphics.drawable.shapes.OvalShape
 *  android.graphics.drawable.shapes.Shape
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.widget.ImageButton
 *  android.widget.TextView
 */
package com.getbase.floatingactionbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.TextView;
import com.getbase.floatingactionbutton.R;

public class FloatingActionButton
extends ImageButton {
    private float mCircleSize;
    int mColorDisabled;
    int mColorNormal;
    int mColorPressed;
    private int mDrawableSize;
    private int mIcon;
    private Drawable mIconDrawable;
    private float mShadowOffset;
    private float mShadowRadius;
    private int mSize;
    boolean mStrokeVisible;
    String mTitle;

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context, attributeSet);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context, attributeSet);
    }

    private int adjustColorBrightness(int n, float f) {
        float[] arrf = new float[3];
        Color.colorToHSV((int)n, (float[])arrf);
        arrf[2] = Math.min(arrf[2] * f, 1.0f);
        return Color.HSVToColor((int)Color.alpha((int)n), (float[])arrf);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private Drawable createCircleDrawable(int n, float f) {
        void var4_6;
        int n2 = Color.alpha((int)n);
        n = this.opaque(n);
        ShapeDrawable shapeDrawable = new ShapeDrawable((Shape)new OvalShape());
        Drawable[] arrdrawable = shapeDrawable.getPaint();
        arrdrawable.setAntiAlias(true);
        arrdrawable.setColor(n);
        arrdrawable = new Drawable[]{shapeDrawable, this.createInnerStrokesDrawable(n, f)};
        if (n2 == 255 || !this.mStrokeVisible) {
            LayerDrawable layerDrawable = new LayerDrawable(arrdrawable);
        } else {
            TranslucentLayerDrawable translucentLayerDrawable = new TranslucentLayerDrawable(n2, arrdrawable);
        }
        n = (int)(f / 2.0f);
        var4_6.setLayerInset(1, n, n, n, n);
        return var4_6;
    }

    private StateListDrawable createFillDrawable(float f) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable drawable2 = this.createCircleDrawable(this.mColorDisabled, f);
        stateListDrawable.addState(new int[]{-16842910}, drawable2);
        drawable2 = this.createCircleDrawable(this.mColorPressed, f);
        stateListDrawable.addState(new int[]{16842919}, drawable2);
        drawable2 = this.createCircleDrawable(this.mColorNormal, f);
        stateListDrawable.addState(new int[0], drawable2);
        return stateListDrawable;
    }

    private Drawable createInnerStrokesDrawable(final int n, float f) {
        if (!this.mStrokeVisible) {
            return new ColorDrawable(0);
        }
        ShapeDrawable shapeDrawable = new ShapeDrawable((Shape)new OvalShape());
        final int n2 = this.darkenColor(n);
        final int n3 = this.halfTransparent(n2);
        final int n4 = this.lightenColor(n);
        final int n5 = this.halfTransparent(n4);
        Paint paint = shapeDrawable.getPaint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(f);
        paint.setStyle(Paint.Style.STROKE);
        shapeDrawable.setShaderFactory(new ShapeDrawable.ShaderFactory(){

            public Shader resize(int n6, int n22) {
                float f = n6 / 2;
                float f2 = n6 / 2;
                float f3 = n22;
                n6 = n4;
                n22 = n5;
                int n32 = n;
                int n42 = n3;
                int n52 = n2;
                Shader.TileMode tileMode = Shader.TileMode.CLAMP;
                return new LinearGradient(f, 0.0f, f2, f3, new int[]{n6, n22, n32, n42, n52}, new float[]{0.0f, 0.2f, 0.5f, 0.8f, 1.0f}, tileMode);
            }
        });
        return shapeDrawable;
    }

    private Drawable createOuterStrokeDrawable(float f) {
        ShapeDrawable shapeDrawable = new ShapeDrawable((Shape)new OvalShape());
        Paint paint = shapeDrawable.getPaint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(-16777216);
        paint.setAlpha(this.opacityToAlpha(0.02f));
        return shapeDrawable;
    }

    private int darkenColor(int n) {
        return this.adjustColorBrightness(n, 0.9f);
    }

    private int halfTransparent(int n) {
        return Color.argb((int)(Color.alpha((int)n) / 2), (int)Color.red((int)n), (int)Color.green((int)n), (int)Color.blue((int)n));
    }

    private int lightenColor(int n) {
        return this.adjustColorBrightness(n, 1.1f);
    }

    private int opacityToAlpha(float f) {
        return (int)(255.0f * f);
    }

    private int opaque(int n) {
        return Color.rgb((int)Color.red((int)n), (int)Color.green((int)n), (int)Color.blue((int)n));
    }

    @SuppressLint(value={"NewApi"})
    private void setBackgroundCompat(Drawable drawable2) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.setBackground(drawable2);
            return;
        }
        this.setBackgroundDrawable(drawable2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateCircleSize() {
        int n = this.mSize == 0 ? R.dimen.fab_size_normal : R.dimen.fab_size_mini;
        this.mCircleSize = this.getDimension(n);
    }

    private void updateDrawableSize() {
        this.mDrawableSize = (int)(this.mCircleSize + 2.0f * this.mShadowRadius);
    }

    int getColor(int n) {
        return this.getResources().getColor(n);
    }

    public int getColorDisabled() {
        return this.mColorDisabled;
    }

    public int getColorNormal() {
        return this.mColorNormal;
    }

    public int getColorPressed() {
        return this.mColorPressed;
    }

    float getDimension(int n) {
        return this.getResources().getDimension(n);
    }

    Drawable getIconDrawable() {
        if (this.mIconDrawable != null) {
            return this.mIconDrawable;
        }
        if (this.mIcon != 0) {
            return this.getResources().getDrawable(this.mIcon);
        }
        return new ColorDrawable(0);
    }

    TextView getLabelView() {
        return (TextView)this.getTag(R.id.fab_label);
    }

    public int getSize() {
        return this.mSize;
    }

    public String getTitle() {
        return this.mTitle;
    }

    void init(Context context, AttributeSet attributeSet) {
        context = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton, 0, 0);
        this.mColorNormal = context.getColor(R.styleable.FloatingActionButton_fab_colorNormal, this.getColor(17170451));
        this.mColorPressed = context.getColor(R.styleable.FloatingActionButton_fab_colorPressed, this.getColor(17170450));
        this.mColorDisabled = context.getColor(R.styleable.FloatingActionButton_fab_colorDisabled, this.getColor(17170432));
        this.mSize = context.getInt(R.styleable.FloatingActionButton_fab_size, 0);
        this.mIcon = context.getResourceId(R.styleable.FloatingActionButton_fab_icon, 0);
        this.mTitle = context.getString(R.styleable.FloatingActionButton_fab_title);
        this.mStrokeVisible = context.getBoolean(R.styleable.FloatingActionButton_fab_stroke_visible, true);
        context.recycle();
        this.updateCircleSize();
        this.mShadowRadius = this.getDimension(R.dimen.fab_shadow_radius);
        this.mShadowOffset = this.getDimension(R.dimen.fab_shadow_offset);
        this.updateDrawableSize();
        this.updateBackground();
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        this.setMeasuredDimension(this.mDrawableSize, this.mDrawableSize);
    }

    public void setColorDisabled(int n) {
        if (this.mColorDisabled != n) {
            this.mColorDisabled = n;
            this.updateBackground();
        }
    }

    public void setColorDisabledResId(int n) {
        this.setColorDisabled(this.getColor(n));
    }

    public void setColorNormal(int n) {
        if (this.mColorNormal != n) {
            this.mColorNormal = n;
            this.updateBackground();
        }
    }

    public void setColorNormalResId(int n) {
        this.setColorNormal(this.getColor(n));
    }

    public void setColorPressed(int n) {
        if (this.mColorPressed != n) {
            this.mColorPressed = n;
            this.updateBackground();
        }
    }

    public void setColorPressedResId(int n) {
        this.setColorPressed(this.getColor(n));
    }

    public void setIcon(int n) {
        if (this.mIcon != n) {
            this.mIcon = n;
            this.mIconDrawable = null;
            this.updateBackground();
        }
    }

    public void setIconDrawable(Drawable drawable2) {
        if (this.mIconDrawable != drawable2) {
            this.mIcon = 0;
            this.mIconDrawable = drawable2;
            this.updateBackground();
        }
    }

    public void setSize(int n) {
        if (n != 1 && n != 0) {
            throw new IllegalArgumentException("Use @FAB_SIZE constants only!");
        }
        if (this.mSize != n) {
            this.mSize = n;
            this.updateCircleSize();
            this.updateDrawableSize();
            this.updateBackground();
        }
    }

    public void setStrokeVisible(boolean bl) {
        if (this.mStrokeVisible != bl) {
            this.mStrokeVisible = bl;
            this.updateBackground();
        }
    }

    public void setTitle(String string2) {
        this.mTitle = string2;
        TextView textView = this.getLabelView();
        if (textView != null) {
            textView.setText((CharSequence)string2);
        }
    }

    public void setVisibility(int n) {
        TextView textView = this.getLabelView();
        if (textView != null) {
            textView.setVisibility(n);
        }
        super.setVisibility(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    void updateBackground() {
        float f = this.getDimension(R.dimen.fab_stroke_width);
        float f2 = f / 2.0f;
        Resources resources = this.getResources();
        int n = this.mSize == 0 ? R.drawable.fab_bg_normal : R.drawable.fab_bg_mini;
        resources = new LayerDrawable(new Drawable[]{resources.getDrawable(n), this.createFillDrawable(f), this.createOuterStrokeDrawable(f), this.getIconDrawable()});
        n = (int)(this.mCircleSize - this.getDimension(R.dimen.fab_icon_size)) / 2;
        int n2 = (int)this.mShadowRadius;
        int n3 = (int)(this.mShadowRadius - this.mShadowOffset);
        int n4 = (int)(this.mShadowRadius + this.mShadowOffset);
        resources.setLayerInset(1, n2, n3, n2, n4);
        resources.setLayerInset(2, (int)((float)n2 - f2), (int)((float)n3 - f2), (int)((float)n2 - f2), (int)((float)n4 - f2));
        resources.setLayerInset(3, n2 + n, n3 + n, n2 + n, n4 + n);
        this.setBackgroundCompat((Drawable)resources);
    }

    private static class TranslucentLayerDrawable
    extends LayerDrawable {
        private final int mAlpha;

        public TranslucentLayerDrawable(int n, Drawable ... arrdrawable) {
            super(arrdrawable);
            this.mAlpha = n;
        }

        public void draw(Canvas canvas) {
            Rect rect = this.getBounds();
            canvas.saveLayerAlpha((float)rect.left, (float)rect.top, (float)rect.right, (float)rect.bottom, this.mAlpha, 31);
            super.draw(canvas);
            canvas.restore();
        }
    }

}

