/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.generic;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.facebook.drawee.R;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import javax.annotation.Nullable;

public class GenericDraweeHierarchyInflater {
    @Nullable
    private static Drawable getDrawable(Context context, TypedArray typedArray, int n) {
        if ((n = typedArray.getResourceId(n, 0)) == 0) {
            return null;
        }
        return context.getResources().getDrawable(n);
    }

    private static RoundingParams getRoundingParams(GenericDraweeHierarchyBuilder genericDraweeHierarchyBuilder) {
        if (genericDraweeHierarchyBuilder.getRoundingParams() == null) {
            genericDraweeHierarchyBuilder.setRoundingParams(new RoundingParams());
        }
        return genericDraweeHierarchyBuilder.getRoundingParams();
    }

    @Nullable
    private static ScalingUtils.ScaleType getScaleTypeFromXml(TypedArray typedArray, int n) {
        switch (typedArray.getInt(n, -2)) {
            default: {
                throw new RuntimeException("XML attribute not specified!");
            }
            case -1: {
                return null;
            }
            case 0: {
                return ScalingUtils.ScaleType.FIT_XY;
            }
            case 1: {
                return ScalingUtils.ScaleType.FIT_START;
            }
            case 2: {
                return ScalingUtils.ScaleType.FIT_CENTER;
            }
            case 3: {
                return ScalingUtils.ScaleType.FIT_END;
            }
            case 4: {
                return ScalingUtils.ScaleType.CENTER;
            }
            case 5: {
                return ScalingUtils.ScaleType.CENTER_INSIDE;
            }
            case 6: {
                return ScalingUtils.ScaleType.CENTER_CROP;
            }
            case 7: 
        }
        return ScalingUtils.ScaleType.FOCUS_CROP;
    }

    public static GenericDraweeHierarchyBuilder inflateBuilder(Context context, @Nullable AttributeSet attributeSet) {
        return GenericDraweeHierarchyInflater.updateBuilder(new GenericDraweeHierarchyBuilder(context.getResources()), context, attributeSet);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static GenericDraweeHierarchyBuilder updateBuilder(GenericDraweeHierarchyBuilder var0, Context var1_2, @Nullable AttributeSet var2_3) {
        block51: {
            block53: {
                block50: {
                    var7_4 = 0;
                    var9_5 = 0;
                    var8_6 = 0;
                    var10_7 = 0;
                    var16_8 = true;
                    var20_9 = true;
                    var17_10 = true;
                    var21_11 = true;
                    var14_12 = true;
                    var18_13 = true;
                    var15_14 = true;
                    var19_15 = true;
                    if (var2_3 == null) break block53;
                    var2_3 = var1_2.obtainStyledAttributes(var2_3, R.styleable.GenericDraweeHierarchy);
                    try {
                        var12_16 = var2_3.getIndexCount();
                        var11_17 = 0;
                        var8_6 = var10_7;
                        var17_10 = var21_11;
                        var16_8 = var20_9;
                        var15_14 = var19_15;
                        var14_12 = var18_13;
                        var7_4 = var9_5;
                        var9_5 = var11_17;
lbl25:
                        // 2 sources
                        do {
                            if (var9_5 >= var12_16) break block50;
                            var13_18 = var2_3.getIndex(var9_5);
                            if (var13_18 == R.styleable.GenericDraweeHierarchy_actualImageScaleType) {
                                var0.setActualImageScaleType(GenericDraweeHierarchyInflater.getScaleTypeFromXml((TypedArray)var2_3, var13_18));
                                var10_7 = var7_4;
                                var18_13 = var14_12;
                                var19_15 = var15_14;
                                var20_9 = var16_8;
                                var21_11 = var17_10;
                                var11_17 = var8_6;
                                break block51;
                            }
                            if (var13_18 == R.styleable.GenericDraweeHierarchy_placeholderImage) {
                                var0.setPlaceholderImage(GenericDraweeHierarchyInflater.getDrawable((Context)var1_2, (TypedArray)var2_3, var13_18));
                                var10_7 = var7_4;
                                var18_13 = var14_12;
                                var19_15 = var15_14;
                                var20_9 = var16_8;
                                var21_11 = var17_10;
                                var11_17 = var8_6;
                                break block51;
                            }
                            if (var13_18 != R.styleable.GenericDraweeHierarchy_pressedStateOverlayImage) break;
                            var0.setPressedStateOverlay(GenericDraweeHierarchyInflater.getDrawable((Context)var1_2, (TypedArray)var2_3, var13_18));
                            var10_7 = var7_4;
                            var18_13 = var14_12;
                            var19_15 = var15_14;
                            var20_9 = var16_8;
                            var21_11 = var17_10;
                            var11_17 = var8_6;
                            break block51;
                            break;
                        } while (true);
                    }
                    catch (Throwable var0_1) {
                        var2_3.recycle();
                        throw var0_1;
                    }
                    if (var13_18 == R.styleable.GenericDraweeHierarchy_progressBarImage) {
                        var0.setProgressBarImage(GenericDraweeHierarchyInflater.getDrawable((Context)var1_2, (TypedArray)var2_3, var13_18));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_fadeDuration) {
                        var0.setFadeDuration(var2_3.getInt(var13_18, 0));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_viewAspectRatio) {
                        var0.setDesiredAspectRatio(var2_3.getFloat(var13_18, 0.0f));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_placeholderImageScaleType) {
                        var0.setPlaceholderImageScaleType(GenericDraweeHierarchyInflater.getScaleTypeFromXml((TypedArray)var2_3, var13_18));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_retryImage) {
                        var0.setRetryImage(GenericDraweeHierarchyInflater.getDrawable((Context)var1_2, (TypedArray)var2_3, var13_18));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_retryImageScaleType) {
                        var0.setRetryImageScaleType(GenericDraweeHierarchyInflater.getScaleTypeFromXml((TypedArray)var2_3, var13_18));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_failureImage) {
                        var0.setFailureImage(GenericDraweeHierarchyInflater.getDrawable((Context)var1_2, (TypedArray)var2_3, var13_18));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_failureImageScaleType) {
                        var0.setFailureImageScaleType(GenericDraweeHierarchyInflater.getScaleTypeFromXml((TypedArray)var2_3, var13_18));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_progressBarImageScaleType) {
                        var0.setProgressBarImageScaleType(GenericDraweeHierarchyInflater.getScaleTypeFromXml((TypedArray)var2_3, var13_18));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_progressBarAutoRotateInterval) {
                        var10_7 = var2_3.getInteger(var13_18, var7_4);
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_backgroundImage) {
                        var0.setBackground(GenericDraweeHierarchyInflater.getDrawable((Context)var1_2, (TypedArray)var2_3, var13_18));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_overlayImage) {
                        var0.setOverlay(GenericDraweeHierarchyInflater.getDrawable((Context)var1_2, (TypedArray)var2_3, var13_18));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_roundAsCircle) {
                        GenericDraweeHierarchyInflater.getRoundingParams(var0).setRoundAsCircle(var2_3.getBoolean(var13_18, false));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_roundedCornerRadius) {
                        var11_17 = var2_3.getDimensionPixelSize(var13_18, var8_6);
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_roundTopLeft) {
                        var20_9 = var2_3.getBoolean(var13_18, var16_8);
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_roundTopRight) {
                        var21_11 = var2_3.getBoolean(var13_18, var17_10);
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_roundBottomLeft) {
                        var18_13 = var2_3.getBoolean(var13_18, var14_12);
                        var10_7 = var7_4;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_roundBottomRight) {
                        var19_15 = var2_3.getBoolean(var13_18, var15_14);
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_roundWithOverlayColor) {
                        GenericDraweeHierarchyInflater.getRoundingParams(var0).setOverlayColor(var2_3.getColor(var13_18, 0));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_roundingBorderWidth) {
                        GenericDraweeHierarchyInflater.getRoundingParams(var0).setBorderWidth(var2_3.getDimensionPixelSize(var13_18, 0));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else if (var13_18 == R.styleable.GenericDraweeHierarchy_roundingBorderColor) {
                        GenericDraweeHierarchyInflater.getRoundingParams(var0).setBorderColor(var2_3.getColor(var13_18, 0));
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                    } else {
                        var10_7 = var7_4;
                        var18_13 = var14_12;
                        var19_15 = var15_14;
                        var20_9 = var16_8;
                        var21_11 = var17_10;
                        var11_17 = var8_6;
                        if (var13_18 == R.styleable.GenericDraweeHierarchy_roundingBorderPadding) {
                            GenericDraweeHierarchyInflater.getRoundingParams(var0).setPadding(var2_3.getDimensionPixelSize(var13_18, 0));
                            var10_7 = var7_4;
                            var18_13 = var14_12;
                            var19_15 = var15_14;
                            var20_9 = var16_8;
                            var21_11 = var17_10;
                            var11_17 = var8_6;
                        }
                    }
                    break block51;
                }
                var2_3.recycle();
            }
            if (var0.getProgressBarImage() != null && var7_4 > 0) {
                var0.setProgressBarImage(new AutoRotateDrawable(var0.getProgressBarImage(), var7_4));
            }
            if (var8_6 <= 0) return var0;
            var1_2 = GenericDraweeHierarchyInflater.getRoundingParams(var0);
            var3_19 = var16_8 != false ? (float)var8_6 : 0.0f;
            var4_20 = var17_10 != false ? (float)var8_6 : 0.0f;
            var5_21 = var15_14 != false ? (float)var8_6 : 0.0f;
            var6_22 = var14_12 != false ? (float)var8_6 : 0.0f;
            var1_2.setCornersRadii(var3_19, var4_20, var5_21, var6_22);
            return var0;
        }
        ++var9_5;
        var7_4 = var10_7;
        var14_12 = var18_13;
        var15_14 = var19_15;
        var16_8 = var20_9;
        var17_10 = var21_11;
        var8_6 = var11_17;
        ** while (true)
    }
}

