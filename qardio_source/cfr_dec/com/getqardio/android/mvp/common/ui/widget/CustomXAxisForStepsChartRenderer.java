/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$FontMetrics
 *  android.graphics.Rect
 */
package com.getqardio.android.mvp.common.ui.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class CustomXAxisForStepsChartRenderer
extends XAxisRenderer {
    private static Rect mDrawTextRectBuffer = new Rect();
    private static Paint.FontMetrics mFontMetricsBuffer = new Paint.FontMetrics();

    public CustomXAxisForStepsChartRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer transformer) {
        super(viewPortHandler, xAxis, transformer);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void drawLabel(Canvas canvas, String object, float f, float f2, MPPointF mPPointF, float f3) {
        Paint.Align align;
        Paint paint;
        block11: {
            float f4;
            block13: {
                float f5;
                float f6;
                float f7;
                block12: {
                    block8: {
                        float f8;
                        float f9;
                        float f10;
                        block10: {
                            block9: {
                                paint = this.mAxisLabelPaint;
                                f6 = this.mAxisLabelPaint.getFontMetrics(mFontMetricsBuffer);
                                paint.getTextBounds((String)object, 0, ((String)object).length(), mDrawTextRectBuffer);
                                f5 = 0.0f - (float)CustomXAxisForStepsChartRenderer.mDrawTextRectBuffer.left;
                                f7 = 0.0f + -CustomXAxisForStepsChartRenderer.mFontMetricsBuffer.ascent;
                                align = paint.getTextAlign();
                                paint.setTextAlign(Paint.Align.LEFT);
                                if (((String)object).equalsIgnoreCase("0")) {
                                    paint.setColor(-7829368);
                                } else {
                                    paint.setColor(-3355444);
                                }
                                if (f3 == 0.0f) break block8;
                                f10 = mDrawTextRectBuffer.width();
                                if (mPPointF.x != 0.5f) break block9;
                                f9 = f;
                                f8 = f2;
                                if (mPPointF.y == 0.5f) break block10;
                            }
                            object = Utils.getSizeOfRotatedRectangleByDegrees(mDrawTextRectBuffer.width(), f6, f3);
                            f9 = f - ((FSize)object).width * (mPPointF.x - 0.5f);
                            f8 = f2 - ((FSize)object).height * (mPPointF.y - 0.5f);
                            FSize.recycleInstance((FSize)object);
                        }
                        canvas.save();
                        canvas.translate(f9, f8);
                        canvas.rotate(f3);
                        canvas.drawCircle(5.0f + (f5 - f10 * 0.5f), f7 - 0.5f * f6, 5.0f, paint);
                        canvas.restore();
                        break block11;
                    }
                    if (mPPointF.x != 0.0f) break block12;
                    f4 = f5;
                    f3 = f7;
                    if (mPPointF.y == 0.0f) break block13;
                }
                f4 = f5 - (float)mDrawTextRectBuffer.width() * mPPointF.x;
                f3 = f7 - mPPointF.y * f6;
            }
            canvas.drawCircle(5.0f + (f4 + f), f3 + f2, 5.0f, paint);
        }
        paint.setTextAlign(align);
    }
}

