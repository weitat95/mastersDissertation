/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.view.MotionEvent
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.MemoryUiTrimmable;
import com.facebook.common.memory.MemoryUiTrimmableRegistry;
import com.facebook.drawee.components.DraweeEventTracker;
import com.facebook.drawee.drawable.VisibilityAwareDrawable;
import com.facebook.drawee.drawable.VisibilityCallback;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import javax.annotation.Nullable;

public class DraweeHolder<DH extends DraweeHierarchy>
implements MemoryUiTrimmable,
VisibilityCallback {
    private DraweeController mController = null;
    private final DraweeEventTracker mEventTracker = DraweeEventTracker.newInstance();
    private DH mHierarchy;
    private boolean mIsControllerAttached = false;
    private boolean mIsHolderAttached = false;
    private boolean mIsVisible = true;
    private boolean mTrimmed = false;

    public DraweeHolder(@Nullable DH DH) {
        if (DH != null) {
            this.setHierarchy(DH);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void attachController() {
        block3: {
            block2: {
                if (this.mIsControllerAttached) break block2;
                this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_ATTACH_CONTROLLER);
                this.mIsControllerAttached = true;
                if (this.mController != null && this.mController.getHierarchy() != null) break block3;
            }
            return;
        }
        this.mController.onAttach();
    }

    private void attachOrDetachController() {
        if (this.mIsHolderAttached && this.mIsVisible && !this.mTrimmed) {
            this.attachController();
            return;
        }
        this.detachController();
    }

    public static <DH extends DraweeHierarchy> DraweeHolder<DH> create(@Nullable DH object, Context context) {
        object = new DraweeHolder<DH>((DH)object);
        ((DraweeHolder)object).registerWithContext(context);
        MemoryUiTrimmableRegistry.registerUiTrimmable((MemoryUiTrimmable)object);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void detachController() {
        block3: {
            block2: {
                if (!this.mIsControllerAttached) break block2;
                this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_DETACH_CONTROLLER);
                this.mIsControllerAttached = false;
                if (this.mController != null) break block3;
            }
            return;
        }
        this.mController.onDetach();
    }

    private void setVisibilityCallback(@Nullable VisibilityCallback visibilityCallback) {
        Drawable drawable2 = this.getTopLevelDrawable();
        if (drawable2 instanceof VisibilityAwareDrawable) {
            ((VisibilityAwareDrawable)drawable2).setVisibilityCallback(visibilityCallback);
        }
    }

    @Nullable
    public DraweeController getController() {
        return this.mController;
    }

    public DH getHierarchy() {
        return (DH)((DraweeHierarchy)Preconditions.checkNotNull(this.mHierarchy));
    }

    public Drawable getTopLevelDrawable() {
        if (this.mHierarchy == null) {
            return null;
        }
        return this.mHierarchy.getTopLevelDrawable();
    }

    public boolean hasHierarchy() {
        return this.mHierarchy != null;
    }

    public void onAttach() {
        this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_HOLDER_ATTACH);
        this.mIsHolderAttached = true;
        this.attachOrDetachController();
    }

    public void onDetach() {
        this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_HOLDER_DETACH);
        this.mIsHolderAttached = false;
        this.attachOrDetachController();
    }

    @Override
    public void onDraw() {
        if (this.mIsControllerAttached) {
            return;
        }
        if (!this.mTrimmed) {
            FLog.wtf(DraweeEventTracker.class, "%x: Draw requested for a non-attached controller %x. %s", System.identityHashCode(this), System.identityHashCode(this.mController), this.toString());
        }
        this.mTrimmed = false;
        this.mIsHolderAttached = true;
        this.mIsVisible = true;
        this.attachOrDetachController();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mController == null) {
            return false;
        }
        return this.mController.onTouchEvent(motionEvent);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onVisibilityChange(boolean bl) {
        if (this.mIsVisible == bl) {
            return;
        }
        DraweeEventTracker draweeEventTracker = this.mEventTracker;
        DraweeEventTracker.Event event = bl ? DraweeEventTracker.Event.ON_DRAWABLE_SHOW : DraweeEventTracker.Event.ON_DRAWABLE_HIDE;
        draweeEventTracker.recordEvent(event);
        this.mIsVisible = bl;
        this.attachOrDetachController();
    }

    public void registerWithContext(Context context) {
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setController(@Nullable DraweeController draweeController) {
        boolean bl = this.mIsControllerAttached;
        if (bl) {
            this.detachController();
        }
        if (this.mController != null) {
            this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_CLEAR_OLD_CONTROLLER);
            this.mController.setHierarchy(null);
        }
        this.mController = draweeController;
        if (this.mController != null) {
            this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_SET_CONTROLLER);
            this.mController.setHierarchy((DraweeHierarchy)this.mHierarchy);
        } else {
            this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_CLEAR_CONTROLLER);
        }
        if (bl) {
            this.attachController();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setHierarchy(DH DH) {
        this.mEventTracker.recordEvent(DraweeEventTracker.Event.ON_SET_HIERARCHY);
        this.setVisibilityCallback(null);
        this.mHierarchy = (DraweeHierarchy)Preconditions.checkNotNull(DH);
        Drawable drawable2 = this.mHierarchy.getTopLevelDrawable();
        boolean bl = drawable2 == null || drawable2.isVisible();
        this.onVisibilityChange(bl);
        this.setVisibilityCallback(this);
        if (this.mController != null) {
            this.mController.setHierarchy((DraweeHierarchy)DH);
        }
    }

    public String toString() {
        return Objects.toStringHelper(this).add("controllerAttached", this.mIsControllerAttached).add("holderAttached", this.mIsHolderAttached).add("drawableVisible", this.mIsVisible).add("trimmed", this.mTrimmed).add("events", this.mEventTracker.toString()).toString();
    }
}

