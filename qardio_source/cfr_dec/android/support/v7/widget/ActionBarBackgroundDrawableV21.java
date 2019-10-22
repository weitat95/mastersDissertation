/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Outline
 *  android.graphics.drawable.Drawable
 */
package android.support.v7.widget;

import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.ActionBarBackgroundDrawable;
import android.support.v7.widget.ActionBarContainer;

class ActionBarBackgroundDrawableV21
extends ActionBarBackgroundDrawable {
    public ActionBarBackgroundDrawableV21(ActionBarContainer actionBarContainer) {
        super(actionBarContainer);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void getOutline(Outline outline) {
        if (this.mContainer.mIsSplit) {
            if (this.mContainer.mSplitBackground == null) return;
            {
                this.mContainer.mSplitBackground.getOutline(outline);
                return;
            }
        } else {
            if (this.mContainer.mBackground == null) return;
            {
                this.mContainer.mBackground.getOutline(outline);
                return;
            }
        }
    }
}

