/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.MotionEvent
 *  android.view.TouchDelegate
 *  android.view.View
 */
package com.getbase.floatingactionbutton;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import java.util.ArrayList;

public class TouchDelegateGroup
extends TouchDelegate {
    private static final Rect USELESS_HACKY_RECT = new Rect();
    private TouchDelegate mCurrentTouchDelegate;
    private boolean mEnabled;
    private final ArrayList<TouchDelegate> mTouchDelegates = new ArrayList();

    public TouchDelegateGroup(View view) {
        super(USELESS_HACKY_RECT, view);
    }

    public void addTouchDelegate(TouchDelegate touchDelegate) {
        this.mTouchDelegates.add(touchDelegate);
    }

    public void clearTouchDelegates() {
        this.mTouchDelegates.clear();
        this.mCurrentTouchDelegate = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        TouchDelegate touchDelegate;
        boolean bl = true;
        if (!this.mEnabled) {
            return false;
        }
        TouchDelegate touchDelegate2 = null;
        block0 : switch (motionEvent.getAction()) {
            default: {
                touchDelegate = touchDelegate2;
                break;
            }
            case 0: {
                int n = 0;
                do {
                    touchDelegate = touchDelegate2;
                    if (n >= this.mTouchDelegates.size()) break block0;
                    touchDelegate = this.mTouchDelegates.get(n);
                    if (touchDelegate.onTouchEvent(motionEvent)) {
                        this.mCurrentTouchDelegate = touchDelegate;
                        return true;
                    }
                    ++n;
                } while (true);
            }
            case 2: {
                touchDelegate = this.mCurrentTouchDelegate;
                break;
            }
            case 1: 
            case 3: {
                touchDelegate = this.mCurrentTouchDelegate;
                this.mCurrentTouchDelegate = null;
            }
        }
        if (touchDelegate == null) return false;
        if (touchDelegate.onTouchEvent(motionEvent)) return bl;
        return false;
    }

    public void setEnabled(boolean bl) {
        this.mEnabled = bl;
    }
}

