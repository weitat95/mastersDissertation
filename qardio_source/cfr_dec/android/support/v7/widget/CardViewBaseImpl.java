/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardViewDelegate;
import android.support.v7.widget.CardViewImpl;
import android.support.v7.widget.RoundRectDrawableWithShadow;

class CardViewBaseImpl
implements CardViewImpl {
    private final RectF mCornerRect = new RectF();

    CardViewBaseImpl() {
    }

    private RoundRectDrawableWithShadow createBackground(Context context, ColorStateList colorStateList, float f, float f2, float f3) {
        return new RoundRectDrawableWithShadow(context.getResources(), colorStateList, f, f2, f3);
    }

    private RoundRectDrawableWithShadow getShadowBackground(CardViewDelegate cardViewDelegate) {
        return (RoundRectDrawableWithShadow)cardViewDelegate.getCardBackground();
    }

    @Override
    public ColorStateList getBackgroundColor(CardViewDelegate cardViewDelegate) {
        return this.getShadowBackground(cardViewDelegate).getColor();
    }

    @Override
    public float getElevation(CardViewDelegate cardViewDelegate) {
        return this.getShadowBackground(cardViewDelegate).getShadowSize();
    }

    @Override
    public float getMaxElevation(CardViewDelegate cardViewDelegate) {
        return this.getShadowBackground(cardViewDelegate).getMaxShadowSize();
    }

    @Override
    public float getMinHeight(CardViewDelegate cardViewDelegate) {
        return this.getShadowBackground(cardViewDelegate).getMinHeight();
    }

    @Override
    public float getMinWidth(CardViewDelegate cardViewDelegate) {
        return this.getShadowBackground(cardViewDelegate).getMinWidth();
    }

    @Override
    public float getRadius(CardViewDelegate cardViewDelegate) {
        return this.getShadowBackground(cardViewDelegate).getCornerRadius();
    }

    @Override
    public void initStatic() {
        RoundRectDrawableWithShadow.sRoundRectHelper = new RoundRectDrawableWithShadow.RoundRectHelper(){

            @Override
            public void drawRoundRect(Canvas canvas, RectF rectF, float f, Paint paint) {
                float f2 = f * 2.0f;
                float f3 = rectF.width() - f2 - 1.0f;
                float f4 = rectF.height();
                if (f >= 1.0f) {
                    float f5 = f + 0.5f;
                    CardViewBaseImpl.this.mCornerRect.set(-f5, -f5, f5, f5);
                    int n = canvas.save();
                    canvas.translate(rectF.left + f5, rectF.top + f5);
                    canvas.drawArc(CardViewBaseImpl.this.mCornerRect, 180.0f, 90.0f, true, paint);
                    canvas.translate(f3, 0.0f);
                    canvas.rotate(90.0f);
                    canvas.drawArc(CardViewBaseImpl.this.mCornerRect, 180.0f, 90.0f, true, paint);
                    canvas.translate(f4 - f2 - 1.0f, 0.0f);
                    canvas.rotate(90.0f);
                    canvas.drawArc(CardViewBaseImpl.this.mCornerRect, 180.0f, 90.0f, true, paint);
                    canvas.translate(f3, 0.0f);
                    canvas.rotate(90.0f);
                    canvas.drawArc(CardViewBaseImpl.this.mCornerRect, 180.0f, 90.0f, true, paint);
                    canvas.restoreToCount(n);
                    canvas.drawRect(rectF.left + f5 - 1.0f, rectF.top, 1.0f + (rectF.right - f5), rectF.top + f5, paint);
                    canvas.drawRect(rectF.left + f5 - 1.0f, rectF.bottom - f5, 1.0f + (rectF.right - f5), rectF.bottom, paint);
                }
                canvas.drawRect(rectF.left, rectF.top + f, rectF.right, rectF.bottom - f, paint);
            }
        };
    }

    @Override
    public void initialize(CardViewDelegate cardViewDelegate, Context object, ColorStateList colorStateList, float f, float f2, float f3) {
        object = this.createBackground((Context)object, colorStateList, f, f2, f3);
        ((RoundRectDrawableWithShadow)((Object)object)).setAddPaddingForCorners(cardViewDelegate.getPreventCornerOverlap());
        cardViewDelegate.setCardBackground((Drawable)object);
        this.updatePadding(cardViewDelegate);
    }

    @Override
    public void onCompatPaddingChanged(CardViewDelegate cardViewDelegate) {
    }

    @Override
    public void onPreventCornerOverlapChanged(CardViewDelegate cardViewDelegate) {
        this.getShadowBackground(cardViewDelegate).setAddPaddingForCorners(cardViewDelegate.getPreventCornerOverlap());
        this.updatePadding(cardViewDelegate);
    }

    @Override
    public void setBackgroundColor(CardViewDelegate cardViewDelegate, ColorStateList colorStateList) {
        this.getShadowBackground(cardViewDelegate).setColor(colorStateList);
    }

    @Override
    public void setElevation(CardViewDelegate cardViewDelegate, float f) {
        this.getShadowBackground(cardViewDelegate).setShadowSize(f);
    }

    @Override
    public void setMaxElevation(CardViewDelegate cardViewDelegate, float f) {
        this.getShadowBackground(cardViewDelegate).setMaxShadowSize(f);
        this.updatePadding(cardViewDelegate);
    }

    @Override
    public void setRadius(CardViewDelegate cardViewDelegate, float f) {
        this.getShadowBackground(cardViewDelegate).setCornerRadius(f);
        this.updatePadding(cardViewDelegate);
    }

    @Override
    public void updatePadding(CardViewDelegate cardViewDelegate) {
        Rect rect = new Rect();
        this.getShadowBackground(cardViewDelegate).getMaxShadowAndCornerPadding(rect);
        cardViewDelegate.setMinWidthHeightInternal((int)Math.ceil(this.getMinWidth(cardViewDelegate)), (int)Math.ceil(this.getMinHeight(cardViewDelegate)));
        cardViewDelegate.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

}

