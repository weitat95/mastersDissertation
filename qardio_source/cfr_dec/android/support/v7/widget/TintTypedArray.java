/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

public class TintTypedArray {
    private final Context mContext;
    private TypedValue mTypedValue;
    private final TypedArray mWrapped;

    private TintTypedArray(Context context, TypedArray typedArray) {
        this.mContext = context;
        this.mWrapped = typedArray;
    }

    public static TintTypedArray obtainStyledAttributes(Context context, int n, int[] arrn) {
        return new TintTypedArray(context, context.obtainStyledAttributes(n, arrn));
    }

    public static TintTypedArray obtainStyledAttributes(Context context, AttributeSet attributeSet, int[] arrn) {
        return new TintTypedArray(context, context.obtainStyledAttributes(attributeSet, arrn));
    }

    public static TintTypedArray obtainStyledAttributes(Context context, AttributeSet attributeSet, int[] arrn, int n, int n2) {
        return new TintTypedArray(context, context.obtainStyledAttributes(attributeSet, arrn, n, n2));
    }

    public boolean getBoolean(int n, boolean bl) {
        return this.mWrapped.getBoolean(n, bl);
    }

    public int getColor(int n, int n2) {
        return this.mWrapped.getColor(n, n2);
    }

    public ColorStateList getColorStateList(int n) {
        int n2;
        ColorStateList colorStateList;
        if (this.mWrapped.hasValue(n) && (n2 = this.mWrapped.getResourceId(n, 0)) != 0 && (colorStateList = AppCompatResources.getColorStateList(this.mContext, n2)) != null) {
            return colorStateList;
        }
        return this.mWrapped.getColorStateList(n);
    }

    public int getDimensionPixelOffset(int n, int n2) {
        return this.mWrapped.getDimensionPixelOffset(n, n2);
    }

    public int getDimensionPixelSize(int n, int n2) {
        return this.mWrapped.getDimensionPixelSize(n, n2);
    }

    public Drawable getDrawable(int n) {
        int n2;
        if (this.mWrapped.hasValue(n) && (n2 = this.mWrapped.getResourceId(n, 0)) != 0) {
            return AppCompatResources.getDrawable(this.mContext, n2);
        }
        return this.mWrapped.getDrawable(n);
    }

    public Drawable getDrawableIfKnown(int n) {
        if (this.mWrapped.hasValue(n) && (n = this.mWrapped.getResourceId(n, 0)) != 0) {
            return AppCompatDrawableManager.get().getDrawable(this.mContext, n, true);
        }
        return null;
    }

    public float getFloat(int n, float f) {
        return this.mWrapped.getFloat(n, f);
    }

    public Typeface getFont(int n, int n2, TextView textView) {
        if ((n = this.mWrapped.getResourceId(n, 0)) == 0) {
            return null;
        }
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        return ResourcesCompat.getFont(this.mContext, n, this.mTypedValue, n2, textView);
    }

    public int getInt(int n, int n2) {
        return this.mWrapped.getInt(n, n2);
    }

    public int getInteger(int n, int n2) {
        return this.mWrapped.getInteger(n, n2);
    }

    public int getLayoutDimension(int n, int n2) {
        return this.mWrapped.getLayoutDimension(n, n2);
    }

    public int getResourceId(int n, int n2) {
        return this.mWrapped.getResourceId(n, n2);
    }

    public String getString(int n) {
        return this.mWrapped.getString(n);
    }

    public CharSequence getText(int n) {
        return this.mWrapped.getText(n);
    }

    public CharSequence[] getTextArray(int n) {
        return this.mWrapped.getTextArray(n);
    }

    public boolean hasValue(int n) {
        return this.mWrapped.hasValue(n);
    }

    public void recycle() {
        this.mWrapped.recycle();
    }
}

