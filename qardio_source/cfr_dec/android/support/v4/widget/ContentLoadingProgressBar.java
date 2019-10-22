/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.ProgressBar
 */
package android.support.v4.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ContentLoadingProgressBar
extends ProgressBar {
    private final Runnable mDelayedHide = new Runnable(){

        @Override
        public void run() {
            ContentLoadingProgressBar.this.mPostedHide = false;
            ContentLoadingProgressBar.this.mStartTime = -1L;
            ContentLoadingProgressBar.this.setVisibility(8);
        }
    };
    private final Runnable mDelayedShow = new Runnable(){

        @Override
        public void run() {
            ContentLoadingProgressBar.this.mPostedShow = false;
            if (!ContentLoadingProgressBar.this.mDismissed) {
                ContentLoadingProgressBar.this.mStartTime = System.currentTimeMillis();
                ContentLoadingProgressBar.this.setVisibility(0);
            }
        }
    };
    boolean mDismissed = false;
    boolean mPostedHide = false;
    boolean mPostedShow = false;
    long mStartTime = -1L;

    public ContentLoadingProgressBar(Context context) {
        this(context, null);
    }

    public ContentLoadingProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    private void removeCallbacks() {
        this.removeCallbacks(this.mDelayedHide);
        this.removeCallbacks(this.mDelayedShow);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.removeCallbacks();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeCallbacks();
    }

}

