/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Path
 *  android.util.AttributeSet
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.support.transition.PathMotion;
import android.support.transition.Styleable;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import org.xmlpull.v1.XmlPullParser;

public class ArcMotion
extends PathMotion {
    private static final float DEFAULT_MAX_TANGENT = (float)Math.tan(Math.toRadians(35.0));
    private float mMaximumAngle = 70.0f;
    private float mMaximumTangent = DEFAULT_MAX_TANGENT;
    private float mMinimumHorizontalAngle = 0.0f;
    private float mMinimumHorizontalTangent = 0.0f;
    private float mMinimumVerticalAngle = 0.0f;
    private float mMinimumVerticalTangent = 0.0f;

    public ArcMotion() {
    }

    public ArcMotion(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, Styleable.ARC_MOTION);
        attributeSet = (XmlPullParser)attributeSet;
        this.setMinimumVerticalAngle(TypedArrayUtils.getNamedFloat((TypedArray)context, (XmlPullParser)attributeSet, "minimumVerticalAngle", 1, 0.0f));
        this.setMinimumHorizontalAngle(TypedArrayUtils.getNamedFloat((TypedArray)context, (XmlPullParser)attributeSet, "minimumHorizontalAngle", 0, 0.0f));
        this.setMaximumAngle(TypedArrayUtils.getNamedFloat((TypedArray)context, (XmlPullParser)attributeSet, "maximumAngle", 2, 70.0f));
        context.recycle();
    }

    private static float toTangent(float f) {
        if (f < 0.0f || f > 90.0f) {
            throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
        }
        return (float)Math.tan(Math.toRadians(f / 2.0f));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Path getPath(float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.moveTo(f, f2);
        float f5 = f3 - f;
        float f6 = f4 - f2;
        float f7 = f5 * f5 + f6 * f6;
        float f8 = (f + f3) / 2.0f;
        float f9 = (f2 + f4) / 2.0f;
        float f10 = f7 * 0.25f;
        boolean bl = f2 > f4;
        if (Math.abs(f5) < Math.abs(f6)) {
            f5 = Math.abs(f7 / (2.0f * f6));
            if (bl) {
                f5 = f4 + f5;
                f6 = f3;
            } else {
                f5 = f2 + f5;
                f6 = f;
            }
            f7 = this.mMinimumVerticalTangent * f10 * this.mMinimumVerticalTangent;
        } else {
            f5 = f7 / (2.0f * f5);
            if (bl) {
                f6 = f + f5;
                f5 = f2;
            } else {
                f6 = f3 - f5;
                f5 = f4;
            }
            f7 = this.mMinimumHorizontalTangent * f10 * this.mMinimumHorizontalTangent;
        }
        float f11 = f8 - f6;
        float f12 = f9 - f5;
        f12 = f11 * f11 + f12 * f12;
        f10 = this.mMaximumTangent * f10 * this.mMaximumTangent;
        f11 = 0.0f;
        if (!(f12 < f7)) {
            f7 = f11;
            if (f12 > f10) {
                f7 = f10;
            }
        }
        f11 = f6;
        f10 = f5;
        if (f7 != 0.0f) {
            f7 = (float)Math.sqrt(f7 / f12);
            f11 = f8 + (f6 - f8) * f7;
            f10 = f9 + (f5 - f9) * f7;
        }
        path.cubicTo((f + f11) / 2.0f, (f2 + f10) / 2.0f, (f11 + f3) / 2.0f, (f10 + f4) / 2.0f, f3, f4);
        return path;
    }

    public void setMaximumAngle(float f) {
        this.mMaximumAngle = f;
        this.mMaximumTangent = ArcMotion.toTangent(f);
    }

    public void setMinimumHorizontalAngle(float f) {
        this.mMinimumHorizontalAngle = f;
        this.mMinimumHorizontalTangent = ArcMotion.toTangent(f);
    }

    public void setMinimumVerticalAngle(float f) {
        this.mMinimumVerticalAngle = f;
        this.mMinimumVerticalTangent = ArcMotion.toTangent(f);
    }
}

