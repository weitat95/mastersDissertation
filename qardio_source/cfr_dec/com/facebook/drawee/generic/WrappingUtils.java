/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.PointF
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.generic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import com.facebook.drawee.drawable.DrawableParent;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.drawee.drawable.MatrixDrawable;
import com.facebook.drawee.drawable.Rounded;
import com.facebook.drawee.drawable.RoundedBitmapDrawable;
import com.facebook.drawee.drawable.RoundedColorDrawable;
import com.facebook.drawee.drawable.RoundedCornersDrawable;
import com.facebook.drawee.drawable.ScaleTypeDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.RoundingParams;
import javax.annotation.Nullable;

public class WrappingUtils {
    private static final Drawable sEmptyDrawable = new ColorDrawable(0);

    private static Drawable applyLeafRounding(Drawable object, RoundingParams roundingParams, Resources resources) {
        if (object instanceof BitmapDrawable) {
            object = (BitmapDrawable)object;
            object = new RoundedBitmapDrawable(resources, object.getBitmap(), object.getPaint());
            WrappingUtils.applyRoundingParams((Rounded)object, roundingParams);
            return object;
        }
        if (object instanceof ColorDrawable && Build.VERSION.SDK_INT >= 11) {
            object = RoundedColorDrawable.fromColorDrawable((ColorDrawable)object);
            WrappingUtils.applyRoundingParams((Rounded)object, roundingParams);
            return object;
        }
        return object;
    }

    static void applyRoundingParams(Rounded rounded, RoundingParams roundingParams) {
        rounded.setCircle(roundingParams.getRoundAsCircle());
        rounded.setRadii(roundingParams.getCornersRadii());
        rounded.setBorder(roundingParams.getBorderColor(), roundingParams.getBorderWidth());
        rounded.setPadding(roundingParams.getPadding());
    }

    static DrawableParent findDrawableParentForLeaf(DrawableParent drawableParent) {
        Drawable drawable2;
        while ((drawable2 = drawableParent.getDrawable()) != drawableParent && drawable2 instanceof DrawableParent) {
            drawableParent = (DrawableParent)drawable2;
        }
        return drawableParent;
    }

    static Drawable maybeApplyLeafRounding(@Nullable Drawable drawable2, @Nullable RoundingParams roundingParams, Resources resources) {
        if (drawable2 == null || roundingParams == null || roundingParams.getRoundingMethod() != RoundingParams.RoundingMethod.BITMAP_ONLY) {
            return drawable2;
        }
        if (drawable2 instanceof ForwardingDrawable) {
            DrawableParent drawableParent = WrappingUtils.findDrawableParentForLeaf((ForwardingDrawable)drawable2);
            drawableParent.setDrawable(WrappingUtils.applyLeafRounding(drawableParent.setDrawable(sEmptyDrawable), roundingParams, resources));
            return drawable2;
        }
        return WrappingUtils.applyLeafRounding(drawable2, roundingParams, resources);
    }

    @Nullable
    static Drawable maybeWrapWithMatrix(@Nullable Drawable drawable2, @Nullable Matrix matrix) {
        if (drawable2 == null || matrix == null) {
            return drawable2;
        }
        return new MatrixDrawable(drawable2, matrix);
    }

    static Drawable maybeWrapWithRoundedOverlayColor(@Nullable Drawable drawable2, @Nullable RoundingParams roundingParams) {
        if (drawable2 == null || roundingParams == null || roundingParams.getRoundingMethod() != RoundingParams.RoundingMethod.OVERLAY_COLOR) {
            return drawable2;
        }
        drawable2 = new RoundedCornersDrawable(drawable2);
        WrappingUtils.applyRoundingParams((Rounded)drawable2, roundingParams);
        drawable2.setOverlayColor(roundingParams.getOverlayColor());
        return drawable2;
    }

    @Nullable
    static Drawable maybeWrapWithScaleType(@Nullable Drawable drawable2, @Nullable ScalingUtils.ScaleType scaleType) {
        return WrappingUtils.maybeWrapWithScaleType(drawable2, scaleType, null);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Nullable
    static Drawable maybeWrapWithScaleType(@Nullable Drawable object, @Nullable ScalingUtils.ScaleType object2, @Nullable PointF pointF) {
        ScaleTypeDrawable scaleTypeDrawable;
        void var2_4;
        block3: {
            void var0_1;
            block2: {
                if (object == null || scaleTypeDrawable == null) break block2;
                ScaleTypeDrawable scaleTypeDrawable2 = scaleTypeDrawable = new ScaleTypeDrawable((Drawable)object, (ScalingUtils.ScaleType)((Object)scaleTypeDrawable));
                if (var2_4 != null) break block3;
            }
            return var0_1;
        }
        scaleTypeDrawable.setFocusPoint((PointF)var2_4);
        return scaleTypeDrawable;
    }
}

