/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 */
package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableWrapperApi14;

class DrawableWrapperApi19
extends DrawableWrapperApi14 {
    DrawableWrapperApi19(Drawable drawable2) {
        super(drawable2);
    }

    DrawableWrapperApi19(DrawableWrapperApi14.DrawableWrapperState drawableWrapperState, Resources resources) {
        super(drawableWrapperState, resources);
    }

    public boolean isAutoMirrored() {
        return this.mDrawable.isAutoMirrored();
    }

    @Override
    DrawableWrapperApi14.DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateKitKat(this.mState, null);
    }

    public void setAutoMirrored(boolean bl) {
        this.mDrawable.setAutoMirrored(bl);
    }

    private static class DrawableWrapperStateKitKat
    extends DrawableWrapperApi14.DrawableWrapperState {
        DrawableWrapperStateKitKat(DrawableWrapperApi14.DrawableWrapperState drawableWrapperState, Resources resources) {
            super(drawableWrapperState, resources);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new DrawableWrapperApi19(this, resources);
        }
    }

}

