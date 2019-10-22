/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.interfaces;

import android.view.MotionEvent;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import javax.annotation.Nullable;

public interface DraweeController {
    @Nullable
    public DraweeHierarchy getHierarchy();

    public void onAttach();

    public void onDetach();

    public boolean onTouchEvent(MotionEvent var1);

    public void setHierarchy(@Nullable DraweeHierarchy var1);
}

