/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 *  android.widget.FrameLayout
 */
package android.support.transition;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.transition.GhostViewImpl;
import android.support.transition.R;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

@SuppressLint(value={"ViewConstructor"})
class GhostViewApi14
extends View
implements GhostViewImpl {
    Matrix mCurrentMatrix;
    private int mDeltaX;
    private int mDeltaY;
    private final Matrix mMatrix = new Matrix();
    private final ViewTreeObserver.OnPreDrawListener mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener(){

        public boolean onPreDraw() {
            GhostViewApi14.this.mCurrentMatrix = GhostViewApi14.this.mView.getMatrix();
            ViewCompat.postInvalidateOnAnimation(GhostViewApi14.this);
            if (GhostViewApi14.this.mStartParent != null && GhostViewApi14.this.mStartView != null) {
                GhostViewApi14.this.mStartParent.endViewTransition(GhostViewApi14.this.mStartView);
                ViewCompat.postInvalidateOnAnimation((View)GhostViewApi14.this.mStartParent);
                GhostViewApi14.this.mStartParent = null;
                GhostViewApi14.this.mStartView = null;
            }
            return true;
        }
    };
    int mReferences;
    ViewGroup mStartParent;
    View mStartView;
    final View mView;

    GhostViewApi14(View view) {
        super(view.getContext());
        this.mView = view;
        this.setLayerType(2, null);
    }

    static GhostViewApi14 getGhostView(View view) {
        return (GhostViewApi14)view.getTag(R.id.ghost_view);
    }

    private static void setGhostView(View view, GhostViewApi14 ghostViewApi14) {
        view.setTag(R.id.ghost_view, (Object)ghostViewApi14);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        GhostViewApi14.setGhostView(this.mView, this);
        int[] arrn = new int[2];
        int[] arrn2 = new int[2];
        this.getLocationOnScreen(arrn);
        this.mView.getLocationOnScreen(arrn2);
        arrn2[0] = (int)((float)arrn2[0] - this.mView.getTranslationX());
        arrn2[1] = (int)((float)arrn2[1] - this.mView.getTranslationY());
        this.mDeltaX = arrn2[0] - arrn[0];
        this.mDeltaY = arrn2[1] - arrn[1];
        this.mView.getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        this.mView.setVisibility(4);
    }

    protected void onDetachedFromWindow() {
        this.mView.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
        this.mView.setVisibility(0);
        GhostViewApi14.setGhostView(this.mView, null);
        super.onDetachedFromWindow();
    }

    protected void onDraw(Canvas canvas) {
        this.mMatrix.set(this.mCurrentMatrix);
        this.mMatrix.postTranslate((float)this.mDeltaX, (float)this.mDeltaY);
        canvas.setMatrix(this.mMatrix);
        this.mView.draw(canvas);
    }

    @Override
    public void reserveEndViewTransition(ViewGroup viewGroup, View view) {
        this.mStartParent = viewGroup;
        this.mStartView = view;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setVisibility(int n) {
        super.setVisibility(n);
        View view = this.mView;
        n = n == 0 ? 4 : 0;
        view.setVisibility(n);
    }

    static class Creator
    implements GhostViewImpl.Creator {
        Creator() {
        }

        private static FrameLayout findFrameLayout(ViewGroup viewGroup) {
            while (!(viewGroup instanceof FrameLayout)) {
                if ((viewGroup = viewGroup.getParent()) instanceof ViewGroup) continue;
                return null;
            }
            return (FrameLayout)viewGroup;
        }

        @Override
        public GhostViewImpl addGhost(View view, ViewGroup viewGroup, Matrix object) {
            GhostViewApi14 ghostViewApi14 = GhostViewApi14.getGhostView(view);
            object = ghostViewApi14;
            if (ghostViewApi14 == null) {
                if ((viewGroup = Creator.findFrameLayout(viewGroup)) == null) {
                    return null;
                }
                object = new GhostViewApi14(view);
                viewGroup.addView((View)object);
            }
            ++object.mReferences;
            return object;
        }

        @Override
        public void removeGhost(View view) {
            if ((view = GhostViewApi14.getGhostView(view)) != null) {
                ViewParent viewParent;
                --view.mReferences;
                if (view.mReferences <= 0 && (viewParent = view.getParent()) instanceof ViewGroup) {
                    viewParent = (ViewGroup)viewParent;
                    viewParent.endViewTransition(view);
                    viewParent.removeView(view);
                }
            }
        }
    }

}

