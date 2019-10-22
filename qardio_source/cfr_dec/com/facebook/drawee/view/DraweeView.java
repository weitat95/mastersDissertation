/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Bitmap
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ImageView
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.facebook.common.internal.Objects;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.facebook.drawee.view.AspectRatioMeasure;
import com.facebook.drawee.view.DraweeHolder;
import javax.annotation.Nullable;

public class DraweeView<DH extends DraweeHierarchy>
extends ImageView {
    private float mAspectRatio = 0.0f;
    private DraweeHolder<DH> mDraweeHolder;
    private boolean mInitialised = false;
    private final AspectRatioMeasure.Spec mMeasureSpec = new AspectRatioMeasure.Spec();

    public DraweeView(Context context) {
        super(context);
        this.init(context);
    }

    public DraweeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context);
    }

    public DraweeView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context);
    }

    @TargetApi(value=21)
    public DraweeView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.init(context);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void init(Context context) {
        block3: {
            block2: {
                if (this.mInitialised) break block2;
                this.mInitialised = true;
                this.mDraweeHolder = DraweeHolder.create(null, context);
                if (Build.VERSION.SDK_INT >= 21 && (context = this.getImageTintList()) != null) break block3;
            }
            return;
        }
        this.setColorFilter(context.getDefaultColor());
    }

    protected void doAttach() {
        this.mDraweeHolder.onAttach();
    }

    protected void doDetach() {
        this.mDraweeHolder.onDetach();
    }

    public float getAspectRatio() {
        return this.mAspectRatio;
    }

    @Nullable
    public DraweeController getController() {
        return this.mDraweeHolder.getController();
    }

    public DH getHierarchy() {
        return this.mDraweeHolder.getHierarchy();
    }

    @Nullable
    public Drawable getTopLevelDrawable() {
        return this.mDraweeHolder.getTopLevelDrawable();
    }

    public boolean hasController() {
        return this.mDraweeHolder.getController() != null;
    }

    public boolean hasHierarchy() {
        return this.mDraweeHolder.hasHierarchy();
    }

    protected void onAttach() {
        this.doAttach();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.onAttach();
    }

    protected void onDetach() {
        this.doDetach();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.onDetach();
    }

    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        this.onAttach();
    }

    protected void onMeasure(int n, int n2) {
        this.mMeasureSpec.width = n;
        this.mMeasureSpec.height = n2;
        AspectRatioMeasure.updateMeasureSpec(this.mMeasureSpec, this.mAspectRatio, this.getLayoutParams(), this.getPaddingLeft() + this.getPaddingRight(), this.getPaddingTop() + this.getPaddingBottom());
        super.onMeasure(this.mMeasureSpec.width, this.mMeasureSpec.height);
    }

    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        this.onDetach();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mDraweeHolder.onTouchEvent(motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setAspectRatio(float f) {
        if (f == this.mAspectRatio) {
            return;
        }
        this.mAspectRatio = f;
        this.requestLayout();
    }

    public void setController(@Nullable DraweeController draweeController) {
        this.mDraweeHolder.setController(draweeController);
        super.setImageDrawable(this.mDraweeHolder.getTopLevelDrawable());
    }

    public void setHierarchy(DH DH) {
        this.mDraweeHolder.setHierarchy(DH);
        super.setImageDrawable(this.mDraweeHolder.getTopLevelDrawable());
    }

    @Deprecated
    public void setImageBitmap(Bitmap bitmap) {
        this.init(this.getContext());
        this.mDraweeHolder.setController(null);
        super.setImageBitmap(bitmap);
    }

    @Deprecated
    public void setImageDrawable(Drawable drawable2) {
        this.init(this.getContext());
        this.mDraweeHolder.setController(null);
        super.setImageDrawable(drawable2);
    }

    @Deprecated
    public void setImageResource(int n) {
        this.init(this.getContext());
        this.mDraweeHolder.setController(null);
        super.setImageResource(n);
    }

    @Deprecated
    public void setImageURI(Uri uri) {
        this.init(this.getContext());
        this.mDraweeHolder.setController(null);
        super.setImageURI(uri);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        String string2;
        Objects.ToStringHelper toStringHelper = Objects.toStringHelper((Object)this);
        if (this.mDraweeHolder != null) {
            string2 = this.mDraweeHolder.toString();
            do {
                return toStringHelper.add("holder", string2).toString();
                break;
            } while (true);
        }
        string2 = "<no holder set>";
        return toStringHelper.add("holder", string2).toString();
    }
}

