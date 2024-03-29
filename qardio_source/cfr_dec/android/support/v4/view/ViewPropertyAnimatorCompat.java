/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.ViewPropertyAnimator
 *  android.view.animation.Interpolator
 */
package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;

public final class ViewPropertyAnimatorCompat {
    Runnable mEndAction = null;
    int mOldLayerType = -1;
    Runnable mStartAction = null;
    private WeakReference<View> mView;

    ViewPropertyAnimatorCompat(View view) {
        this.mView = new WeakReference<View>(view);
    }

    private void setListenerInternal(final View view, final ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        if (viewPropertyAnimatorListener != null) {
            view.animate().setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                public void onAnimationCancel(Animator animator2) {
                    viewPropertyAnimatorListener.onAnimationCancel(view);
                }

                public void onAnimationEnd(Animator animator2) {
                    viewPropertyAnimatorListener.onAnimationEnd(view);
                }

                public void onAnimationStart(Animator animator2) {
                    viewPropertyAnimatorListener.onAnimationStart(view);
                }
            });
            return;
        }
        view.animate().setListener(null);
    }

    public ViewPropertyAnimatorCompat alpha(float f) {
        View view = (View)this.mView.get();
        if (view != null) {
            view.animate().alpha(f);
        }
        return this;
    }

    public void cancel() {
        View view = (View)this.mView.get();
        if (view != null) {
            view.animate().cancel();
        }
    }

    public long getDuration() {
        View view = (View)this.mView.get();
        if (view != null) {
            return view.animate().getDuration();
        }
        return 0L;
    }

    public ViewPropertyAnimatorCompat setDuration(long l) {
        View view = (View)this.mView.get();
        if (view != null) {
            view.animate().setDuration(l);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setInterpolator(Interpolator interpolator) {
        View view = (View)this.mView.get();
        if (view != null) {
            view.animate().setInterpolator((TimeInterpolator)interpolator);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setListener(ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        View view;
        block3: {
            block2: {
                view = (View)this.mView.get();
                if (view == null) break block2;
                if (Build.VERSION.SDK_INT < 16) break block3;
                this.setListenerInternal(view, viewPropertyAnimatorListener);
            }
            return this;
        }
        view.setTag(2113929216, (Object)viewPropertyAnimatorListener);
        this.setListenerInternal(view, new ViewPropertyAnimatorListenerApi14(this));
        return this;
    }

    public ViewPropertyAnimatorCompat setStartDelay(long l) {
        View view = (View)this.mView.get();
        if (view != null) {
            view.animate().setStartDelay(l);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setUpdateListener(final ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener) {
        final View view = (View)this.mView.get();
        if (view != null && Build.VERSION.SDK_INT >= 19) {
            ValueAnimator.AnimatorUpdateListener animatorUpdateListener = null;
            if (viewPropertyAnimatorUpdateListener != null) {
                animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener(){

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        viewPropertyAnimatorUpdateListener.onAnimationUpdate(view);
                    }
                };
            }
            view.animate().setUpdateListener(animatorUpdateListener);
        }
        return this;
    }

    public void start() {
        View view = (View)this.mView.get();
        if (view != null) {
            view.animate().start();
        }
    }

    public ViewPropertyAnimatorCompat translationY(float f) {
        View view = (View)this.mView.get();
        if (view != null) {
            view.animate().translationY(f);
        }
        return this;
    }

    static class ViewPropertyAnimatorListenerApi14
    implements ViewPropertyAnimatorListener {
        boolean mAnimEndCalled;
        ViewPropertyAnimatorCompat mVpa;

        ViewPropertyAnimatorListenerApi14(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
            this.mVpa = viewPropertyAnimatorCompat;
        }

        @Override
        public void onAnimationCancel(View view) {
            Object object = view.getTag(2113929216);
            ViewPropertyAnimatorListener viewPropertyAnimatorListener = null;
            if (object instanceof ViewPropertyAnimatorListener) {
                viewPropertyAnimatorListener = (ViewPropertyAnimatorListener)object;
            }
            if (viewPropertyAnimatorListener != null) {
                viewPropertyAnimatorListener.onAnimationCancel(view);
            }
        }

        @Override
        public void onAnimationEnd(View view) {
            if (this.mVpa.mOldLayerType > -1) {
                view.setLayerType(this.mVpa.mOldLayerType, null);
                this.mVpa.mOldLayerType = -1;
            }
            if (Build.VERSION.SDK_INT >= 16 || !this.mAnimEndCalled) {
                Object object;
                if (this.mVpa.mEndAction != null) {
                    object = this.mVpa.mEndAction;
                    this.mVpa.mEndAction = null;
                    object.run();
                }
                Object object2 = view.getTag(2113929216);
                object = null;
                if (object2 instanceof ViewPropertyAnimatorListener) {
                    object = (ViewPropertyAnimatorListener)object2;
                }
                if (object != null) {
                    object.onAnimationEnd(view);
                }
                this.mAnimEndCalled = true;
            }
        }

        @Override
        public void onAnimationStart(View view) {
            Object object;
            this.mAnimEndCalled = false;
            if (this.mVpa.mOldLayerType > -1) {
                view.setLayerType(2, null);
            }
            if (this.mVpa.mStartAction != null) {
                object = this.mVpa.mStartAction;
                this.mVpa.mStartAction = null;
                object.run();
            }
            Object object2 = view.getTag(2113929216);
            object = null;
            if (object2 instanceof ViewPropertyAnimatorListener) {
                object = (ViewPropertyAnimatorListener)object2;
            }
            if (object != null) {
                object.onAnimationStart(view);
            }
        }
    }

}

