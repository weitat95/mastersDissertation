/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.Drawable$ConstantState
 */
package com.bumptech.glide.request.target;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;

public class SquaringDrawable
extends GlideDrawable {
    private boolean mutated;
    private State state;
    private GlideDrawable wrapped;

    public SquaringDrawable(GlideDrawable glideDrawable, int n) {
        this(new State(glideDrawable.getConstantState(), n), glideDrawable, null);
    }

    SquaringDrawable(State state, GlideDrawable glideDrawable, Resources resources) {
        this.state = state;
        if (glideDrawable == null) {
            if (resources != null) {
                this.wrapped = (GlideDrawable)state.wrapped.newDrawable(resources);
                return;
            }
            this.wrapped = (GlideDrawable)state.wrapped.newDrawable();
            return;
        }
        this.wrapped = glideDrawable;
    }

    public void clearColorFilter() {
        this.wrapped.clearColorFilter();
    }

    public void draw(Canvas canvas) {
        this.wrapped.draw(canvas);
    }

    @TargetApi(value=19)
    public int getAlpha() {
        return this.wrapped.getAlpha();
    }

    @TargetApi(value=11)
    public Drawable.Callback getCallback() {
        return this.wrapped.getCallback();
    }

    public int getChangingConfigurations() {
        return this.wrapped.getChangingConfigurations();
    }

    public Drawable.ConstantState getConstantState() {
        return this.state;
    }

    public Drawable getCurrent() {
        return this.wrapped.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.state.side;
    }

    public int getIntrinsicWidth() {
        return this.state.side;
    }

    public int getMinimumHeight() {
        return this.wrapped.getMinimumHeight();
    }

    public int getMinimumWidth() {
        return this.wrapped.getMinimumWidth();
    }

    public int getOpacity() {
        return this.wrapped.getOpacity();
    }

    public boolean getPadding(Rect rect) {
        return this.wrapped.getPadding(rect);
    }

    public void invalidateSelf() {
        super.invalidateSelf();
        this.wrapped.invalidateSelf();
    }

    @Override
    public boolean isAnimated() {
        return this.wrapped.isAnimated();
    }

    public boolean isRunning() {
        return this.wrapped.isRunning();
    }

    public Drawable mutate() {
        if (!this.mutated && super.mutate() == this) {
            this.wrapped = (GlideDrawable)this.wrapped.mutate();
            this.state = new State(this.state);
            this.mutated = true;
        }
        return this;
    }

    public void scheduleSelf(Runnable runnable, long l) {
        super.scheduleSelf(runnable, l);
        this.wrapped.scheduleSelf(runnable, l);
    }

    public void setAlpha(int n) {
        this.wrapped.setAlpha(n);
    }

    public void setBounds(int n, int n2, int n3, int n4) {
        super.setBounds(n, n2, n3, n4);
        this.wrapped.setBounds(n, n2, n3, n4);
    }

    public void setBounds(Rect rect) {
        super.setBounds(rect);
        this.wrapped.setBounds(rect);
    }

    public void setChangingConfigurations(int n) {
        this.wrapped.setChangingConfigurations(n);
    }

    public void setColorFilter(int n, PorterDuff.Mode mode) {
        this.wrapped.setColorFilter(n, mode);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.wrapped.setColorFilter(colorFilter);
    }

    public void setDither(boolean bl) {
        this.wrapped.setDither(bl);
    }

    public void setFilterBitmap(boolean bl) {
        this.wrapped.setFilterBitmap(bl);
    }

    @Override
    public void setLoopCount(int n) {
        this.wrapped.setLoopCount(n);
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        return this.wrapped.setVisible(bl, bl2);
    }

    public void start() {
        this.wrapped.start();
    }

    public void stop() {
        this.wrapped.stop();
    }

    public void unscheduleSelf(Runnable runnable) {
        super.unscheduleSelf(runnable);
        this.wrapped.unscheduleSelf(runnable);
    }

    static class State
    extends Drawable.ConstantState {
        private final int side;
        private final Drawable.ConstantState wrapped;

        State(Drawable.ConstantState constantState, int n) {
            this.wrapped = constantState;
            this.side = n;
        }

        State(State state) {
            this(state.wrapped, state.side);
        }

        public int getChangingConfigurations() {
            return 0;
        }

        public Drawable newDrawable() {
            return this.newDrawable(null);
        }

        public Drawable newDrawable(Resources resources) {
            return new SquaringDrawable(this, null, resources);
        }
    }

}

