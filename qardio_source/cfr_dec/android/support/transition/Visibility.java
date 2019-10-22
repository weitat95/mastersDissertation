/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.transition.AnimatorUtils;
import android.support.transition.AnimatorUtilsApi14;
import android.support.transition.Styleable;
import android.support.transition.Transition;
import android.support.transition.TransitionUtils;
import android.support.transition.TransitionValues;
import android.support.transition.ViewGroupOverlayImpl;
import android.support.transition.ViewGroupUtils;
import android.support.transition.ViewUtils;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

public abstract class Visibility
extends Transition {
    private static final String[] sTransitionProperties = new String[]{"android:visibility:visibility", "android:visibility:parent"};
    private int mMode = 3;

    public Visibility() {
    }

    public Visibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, Styleable.VISIBILITY_TRANSITION);
        int n = TypedArrayUtils.getNamedInt((TypedArray)context, (XmlPullParser)((XmlResourceParser)attributeSet), "transitionVisibilityMode", 0, 0);
        context.recycle();
        if (n != 0) {
            this.setMode(n);
        }
    }

    private void captureValues(TransitionValues transitionValues) {
        int n = transitionValues.view.getVisibility();
        transitionValues.values.put("android:visibility:visibility", n);
        transitionValues.values.put("android:visibility:parent", (Object)transitionValues.view.getParent());
        int[] arrn = new int[2];
        transitionValues.view.getLocationOnScreen(arrn);
        transitionValues.values.put("android:visibility:screenLocation", arrn);
    }

    /*
     * Enabled aggressive block sorting
     */
    private VisibilityInfo getVisibilityChangeInfo(TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = new VisibilityInfo();
        visibilityInfo.mVisibilityChange = false;
        visibilityInfo.mFadeIn = false;
        if (transitionValues != null && transitionValues.values.containsKey("android:visibility:visibility")) {
            visibilityInfo.mStartVisibility = (Integer)transitionValues.values.get("android:visibility:visibility");
            visibilityInfo.mStartParent = (ViewGroup)transitionValues.values.get("android:visibility:parent");
        } else {
            visibilityInfo.mStartVisibility = -1;
            visibilityInfo.mStartParent = null;
        }
        if (transitionValues2 != null && transitionValues2.values.containsKey("android:visibility:visibility")) {
            visibilityInfo.mEndVisibility = (Integer)transitionValues2.values.get("android:visibility:visibility");
            visibilityInfo.mEndParent = (ViewGroup)transitionValues2.values.get("android:visibility:parent");
        } else {
            visibilityInfo.mEndVisibility = -1;
            visibilityInfo.mEndParent = null;
        }
        if (transitionValues != null && transitionValues2 != null) {
            if (visibilityInfo.mStartVisibility == visibilityInfo.mEndVisibility && visibilityInfo.mStartParent == visibilityInfo.mEndParent) return visibilityInfo;
            {
                if (visibilityInfo.mStartVisibility != visibilityInfo.mEndVisibility) {
                    if (visibilityInfo.mStartVisibility == 0) {
                        visibilityInfo.mFadeIn = false;
                        visibilityInfo.mVisibilityChange = true;
                        return visibilityInfo;
                    }
                    if (visibilityInfo.mEndVisibility != 0) return visibilityInfo;
                    {
                        visibilityInfo.mFadeIn = true;
                        visibilityInfo.mVisibilityChange = true;
                        return visibilityInfo;
                    }
                } else {
                    if (visibilityInfo.mEndParent == null) {
                        visibilityInfo.mFadeIn = false;
                        visibilityInfo.mVisibilityChange = true;
                        return visibilityInfo;
                    }
                    if (visibilityInfo.mStartParent != null) return visibilityInfo;
                    {
                        visibilityInfo.mFadeIn = true;
                        visibilityInfo.mVisibilityChange = true;
                        return visibilityInfo;
                    }
                }
            }
        }
        if (transitionValues == null && visibilityInfo.mEndVisibility == 0) {
            visibilityInfo.mFadeIn = true;
            visibilityInfo.mVisibilityChange = true;
            return visibilityInfo;
        }
        if (transitionValues2 != null || visibilityInfo.mStartVisibility != 0) {
            return visibilityInfo;
        }
        visibilityInfo.mFadeIn = false;
        visibilityInfo.mVisibilityChange = true;
        return visibilityInfo;
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = this.getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (visibilityInfo.mVisibilityChange && (visibilityInfo.mStartParent != null || visibilityInfo.mEndParent != null)) {
            if (visibilityInfo.mFadeIn) {
                return this.onAppear(viewGroup, transitionValues, visibilityInfo.mStartVisibility, transitionValues2, visibilityInfo.mEndVisibility);
            }
            return this.onDisappear(viewGroup, transitionValues, visibilityInfo.mStartVisibility, transitionValues2, visibilityInfo.mEndVisibility);
        }
        return null;
    }

    public int getMode() {
        return this.mMode;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean isTransitionRequired(TransitionValues object, TransitionValues transitionValues) {
        block3: {
            block2: {
                if (object == null && transitionValues == null || object != null && transitionValues != null && transitionValues.values.containsKey("android:visibility:visibility") != ((TransitionValues)object).values.containsKey("android:visibility:visibility")) break block2;
                object = this.getVisibilityChangeInfo((TransitionValues)object, transitionValues);
                if (((VisibilityInfo)object).mVisibilityChange && (((VisibilityInfo)object).mStartVisibility == 0 || ((VisibilityInfo)object).mEndVisibility == 0)) break block3;
            }
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int n, TransitionValues transitionValues2, int n2) {
        block3: {
            block2: {
                if ((this.mMode & 1) != 1 || transitionValues2 == null) break block2;
                if (transitionValues != null) break block3;
                View view = (View)transitionValues2.view.getParent();
                if (!this.getVisibilityChangeInfo((TransitionValues)this.getMatchedTransitionValues((View)view, (boolean)false), (TransitionValues)this.getTransitionValues((View)view, (boolean)false)).mVisibilityChange) break block3;
            }
            return null;
        }
        return this.onAppear(viewGroup, transitionValues2.view, transitionValues, transitionValues2);
    }

    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public Animator onDisappear(ViewGroup viewGroup, TransitionValues object, int n, TransitionValues transitionValues, int n2) {
        void var6_8;
        void var6_9;
        View view;
        if ((this.mMode & 2) != 2) {
            return null;
        }
        Object object2 = object != null ? ((TransitionValues)object).view : null;
        if (transitionValues != null) {
            View view2 = transitionValues.view;
        } else {
            Object var6_10 = null;
        }
        Object var10_23 = null;
        View view3 = null;
        if (var6_8 == null || var6_8.getParent() == null) {
            if (var6_8 != null) {
                view = view3;
            } else {
                Object var6_11 = var10_23;
                view = view3;
                if (object2 != null) {
                    if (object2.getParent() == null) {
                        int[] arrn = object2;
                        view = view3;
                    } else {
                        Object var6_13 = var10_23;
                        view = view3;
                        if (object2.getParent() instanceof View) {
                            View view4 = (View)object2.getParent();
                            if (!this.getVisibilityChangeInfo((TransitionValues)this.getTransitionValues((View)view4, (boolean)true), (TransitionValues)this.getMatchedTransitionValues((View)view4, (boolean)true)).mVisibilityChange) {
                                View view5 = TransitionUtils.copyViewImage(viewGroup, (View)object2, view4);
                                view = view3;
                            } else {
                                Object var6_15 = var10_23;
                                view = view3;
                                if (view4.getParent() == null) {
                                    n = view4.getId();
                                    Object var6_16 = var10_23;
                                    view = view3;
                                    if (n != -1) {
                                        Object var6_17 = var10_23;
                                        view = view3;
                                        if (viewGroup.findViewById(n) != null) {
                                            Object var6_18 = var10_23;
                                            view = view3;
                                            if (this.mCanRemoveViews) {
                                                Object object3 = object2;
                                                view = view3;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (n2 == 4) {
            view = var6_8;
            Object var6_20 = var10_23;
        } else if (object2 == var6_8) {
            view = var6_8;
            Object var6_21 = var10_23;
        } else {
            int[] arrn = object2;
            view = view3;
        }
        if (var6_9 != null && object != null) {
            object2 = (int[])((TransitionValues)object).values.get("android:visibility:screenLocation");
            n = object2[0];
            n2 = object2[1];
            object2 = new int[2];
            viewGroup.getLocationOnScreen((int[])object2);
            var6_9.offsetLeftAndRight(n - object2[0] - var6_9.getLeft());
            var6_9.offsetTopAndBottom(n2 - object2[1] - var6_9.getTop());
            object2 = ViewGroupUtils.getOverlay(viewGroup);
            object2.add((View)var6_9);
            viewGroup = this.onDisappear(viewGroup, (View)var6_9, (TransitionValues)object, transitionValues);
            if (viewGroup == null) {
                object2.remove((View)var6_9);
                return viewGroup;
            }
            viewGroup.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter((ViewGroupOverlayImpl)object2, (View)var6_9){
                final /* synthetic */ View val$finalOverlayView;
                final /* synthetic */ ViewGroupOverlayImpl val$overlay;
                {
                    this.val$overlay = viewGroupOverlayImpl;
                    this.val$finalOverlayView = view;
                }

                public void onAnimationEnd(Animator animator2) {
                    this.val$overlay.remove(this.val$finalOverlayView);
                }
            });
            return viewGroup;
        }
        if (view == null) {
            return null;
        }
        n = view.getVisibility();
        ViewUtils.setTransitionVisibility(view, 0);
        viewGroup = this.onDisappear(viewGroup, view, (TransitionValues)object, transitionValues);
        if (viewGroup != null) {
            object = new DisappearListener(view, n2, true);
            viewGroup.addListener((Animator.AnimatorListener)object);
            AnimatorUtils.addPauseListener((Animator)viewGroup, (AnimatorListenerAdapter)object);
            this.addListener((Transition.TransitionListener)object);
            return viewGroup;
        }
        ViewUtils.setTransitionVisibility(view, n);
        return viewGroup;
    }

    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    public void setMode(int n) {
        if ((n & 0xFFFFFFFC) != 0) {
            throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
        }
        this.mMode = n;
    }

    private static class DisappearListener
    extends AnimatorListenerAdapter
    implements AnimatorUtilsApi14.AnimatorPauseListenerCompat,
    Transition.TransitionListener {
        boolean mCanceled = false;
        private final int mFinalVisibility;
        private boolean mLayoutSuppressed;
        private final ViewGroup mParent;
        private final boolean mSuppressLayout;
        private final View mView;

        DisappearListener(View view, int n, boolean bl) {
            this.mView = view;
            this.mFinalVisibility = n;
            this.mParent = (ViewGroup)view.getParent();
            this.mSuppressLayout = bl;
            this.suppressLayout(true);
        }

        private void hideViewWhenNotCanceled() {
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
                if (this.mParent != null) {
                    this.mParent.invalidate();
                }
            }
            this.suppressLayout(false);
        }

        private void suppressLayout(boolean bl) {
            if (this.mSuppressLayout && this.mLayoutSuppressed != bl && this.mParent != null) {
                this.mLayoutSuppressed = bl;
                ViewGroupUtils.suppressLayout(this.mParent, bl);
            }
        }

        public void onAnimationCancel(Animator animator2) {
            this.mCanceled = true;
        }

        public void onAnimationEnd(Animator animator2) {
            this.hideViewWhenNotCanceled();
        }

        @Override
        public void onAnimationPause(Animator animator2) {
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
            }
        }

        public void onAnimationRepeat(Animator animator2) {
        }

        @Override
        public void onAnimationResume(Animator animator2) {
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, 0);
            }
        }

        public void onAnimationStart(Animator animator2) {
        }

        @Override
        public void onTransitionEnd(Transition transition) {
            this.hideViewWhenNotCanceled();
            transition.removeListener(this);
        }

        @Override
        public void onTransitionPause(Transition transition) {
            this.suppressLayout(false);
        }

        @Override
        public void onTransitionResume(Transition transition) {
            this.suppressLayout(true);
        }

        @Override
        public void onTransitionStart(Transition transition) {
        }
    }

    private static class VisibilityInfo {
        ViewGroup mEndParent;
        int mEndVisibility;
        boolean mFadeIn;
        ViewGroup mStartParent;
        int mStartVisibility;
        boolean mVisibilityChange;

        private VisibilityInfo() {
        }
    }

}

