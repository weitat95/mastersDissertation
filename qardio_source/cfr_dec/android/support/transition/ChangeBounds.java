/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Path
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.transition.ObjectAnimatorUtils;
import android.support.transition.PathMotion;
import android.support.transition.PropertyValuesHolderUtils;
import android.support.transition.RectEvaluator;
import android.support.transition.Styleable;
import android.support.transition.Transition;
import android.support.transition.TransitionListenerAdapter;
import android.support.transition.TransitionUtils;
import android.support.transition.TransitionValues;
import android.support.transition.ViewGroupUtils;
import android.support.transition.ViewUtils;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

public class ChangeBounds
extends Transition {
    private static final Property<View, PointF> BOTTOM_RIGHT_ONLY_PROPERTY;
    private static final Property<ViewBounds, PointF> BOTTOM_RIGHT_PROPERTY;
    private static final Property<Drawable, PointF> DRAWABLE_ORIGIN_PROPERTY;
    private static final Property<View, PointF> POSITION_PROPERTY;
    private static final Property<View, PointF> TOP_LEFT_ONLY_PROPERTY;
    private static final Property<ViewBounds, PointF> TOP_LEFT_PROPERTY;
    private static RectEvaluator sRectEvaluator;
    private static final String[] sTransitionProperties;
    private boolean mReparent = false;
    private boolean mResizeClip = false;
    private int[] mTempLocation = new int[2];

    static {
        sTransitionProperties = new String[]{"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};
        DRAWABLE_ORIGIN_PROPERTY = new Property<Drawable, PointF>(PointF.class, "boundsOrigin"){
            private Rect mBounds = new Rect();

            public PointF get(Drawable drawable2) {
                drawable2.copyBounds(this.mBounds);
                return new PointF((float)this.mBounds.left, (float)this.mBounds.top);
            }

            public void set(Drawable drawable2, PointF pointF) {
                drawable2.copyBounds(this.mBounds);
                this.mBounds.offsetTo(Math.round(pointF.x), Math.round(pointF.y));
                drawable2.setBounds(this.mBounds);
            }
        };
        TOP_LEFT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "topLeft"){

            public PointF get(ViewBounds viewBounds) {
                return null;
            }

            public void set(ViewBounds viewBounds, PointF pointF) {
                viewBounds.setTopLeft(pointF);
            }
        };
        BOTTOM_RIGHT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "bottomRight"){

            public PointF get(ViewBounds viewBounds) {
                return null;
            }

            public void set(ViewBounds viewBounds, PointF pointF) {
                viewBounds.setBottomRight(pointF);
            }
        };
        BOTTOM_RIGHT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "bottomRight"){

            public PointF get(View view) {
                return null;
            }

            public void set(View view, PointF pointF) {
                ViewUtils.setLeftTopRightBottom(view, view.getLeft(), view.getTop(), Math.round(pointF.x), Math.round(pointF.y));
            }
        };
        TOP_LEFT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "topLeft"){

            public PointF get(View view) {
                return null;
            }

            public void set(View view, PointF pointF) {
                ViewUtils.setLeftTopRightBottom(view, Math.round(pointF.x), Math.round(pointF.y), view.getRight(), view.getBottom());
            }
        };
        POSITION_PROPERTY = new Property<View, PointF>(PointF.class, "position"){

            public PointF get(View view) {
                return null;
            }

            public void set(View view, PointF pointF) {
                int n = Math.round(pointF.x);
                int n2 = Math.round(pointF.y);
                ViewUtils.setLeftTopRightBottom(view, n, n2, n + view.getWidth(), n2 + view.getHeight());
            }
        };
        sRectEvaluator = new RectEvaluator();
    }

    public ChangeBounds() {
    }

    public ChangeBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, Styleable.CHANGE_BOUNDS);
        boolean bl = TypedArrayUtils.getNamedBoolean((TypedArray)context, (XmlPullParser)((XmlResourceParser)attributeSet), "resizeClip", 0, false);
        context.recycle();
        this.setResizeClip(bl);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (ViewCompat.isLaidOut(view) || view.getWidth() != 0 || view.getHeight() != 0) {
            transitionValues.values.put("android:changeBounds:bounds", (Object)new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            transitionValues.values.put("android:changeBounds:parent", (Object)transitionValues.view.getParent());
            if (this.mReparent) {
                transitionValues.view.getLocationInWindow(this.mTempLocation);
                transitionValues.values.put("android:changeBounds:windowX", this.mTempLocation[0]);
                transitionValues.values.put("android:changeBounds:windowY", this.mTempLocation[1]);
            }
            if (this.mResizeClip) {
                transitionValues.values.put("android:changeBounds:clip", (Object)ViewCompat.getClipBounds(view));
            }
        }
    }

    private boolean parentMatches(View view, View view2) {
        TransitionValues transitionValues;
        block3: {
            block4: {
                block2: {
                    if (!this.mReparent) break block2;
                    transitionValues = this.getMatchedTransitionValues(view, true);
                    if (transitionValues != null) break block3;
                    if (view != view2) break block4;
                }
                return true;
            }
            return false;
        }
        return view2 == transitionValues.view;
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public Animator createAnimator(ViewGroup object, TransitionValues transitionValues, TransitionValues object2) {
        View view;
        Bitmap bitmap;
        Object object3;
        block22: {
            void var1_6;
            int n;
            ViewGroup viewGroup;
            Rect rect;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            int n10;
            ViewGroup viewGroup2;
            int n11;
            int n12;
            int n13;
            block27: {
                int n14;
                block26: {
                    block24: {
                        block25: {
                            block23: {
                                if (bitmap == null) return null;
                                if (object3 == null) {
                                    return null;
                                }
                                viewGroup = bitmap.values;
                                viewGroup2 = object3.values;
                                viewGroup = (ViewGroup)viewGroup.get("android:changeBounds:parent");
                                viewGroup2 = (ViewGroup)viewGroup2.get("android:changeBounds:parent");
                                if (viewGroup == null) return null;
                                if (viewGroup2 == null) {
                                    return null;
                                }
                                view = object3.view;
                                if (!this.parentMatches((View)viewGroup, (View)viewGroup2)) break block22;
                                Rect rect2 = (Rect)bitmap.values.get("android:changeBounds:bounds");
                                viewGroup = (Rect)object3.values.get("android:changeBounds:bounds");
                                n3 = rect2.left;
                                n7 = viewGroup.left;
                                n9 = rect2.top;
                                n8 = viewGroup.top;
                                n = rect2.right;
                                n6 = viewGroup.right;
                                n11 = rect2.bottom;
                                n5 = viewGroup.bottom;
                                n10 = n - n3;
                                n2 = n11 - n9;
                                n4 = n6 - n7;
                                n13 = n5 - n8;
                                rect = (Rect)bitmap.values.get("android:changeBounds:clip");
                                viewGroup = (Rect)object3.values.get("android:changeBounds:clip");
                                int n15 = 0;
                                n12 = 0;
                                if (n10 != 0 && n2 != 0) break block23;
                                n14 = n15;
                                if (n4 == 0) break block24;
                                n14 = n15;
                                if (n13 == 0) break block24;
                            }
                            if (n3 != n7 || n9 != n8) {
                                n12 = 0 + 1;
                            }
                            if (n != n6) break block25;
                            n14 = n12;
                            if (n11 == n5) break block24;
                        }
                        n14 = n12 + 1;
                    }
                    if (rect != null && !rect.equals((Object)viewGroup)) break block26;
                    n12 = n14;
                    if (rect != null) break block27;
                    n12 = n14;
                    if (viewGroup == null) break block27;
                }
                n12 = n14 + 1;
            }
            if (n12 <= 0) return null;
            if (!this.mResizeClip) {
                ViewUtils.setLeftTopRightBottom(view, n3, n9, n, n11);
                if (n12 == 2) {
                    if (n10 == n4 && n2 == n13) {
                        Path path = this.getPathMotion().getPath(n3, n9, n7, n8);
                        ObjectAnimator objectAnimator = ObjectAnimatorUtils.ofPointF(view, POSITION_PROPERTY, path);
                    } else {
                        object3 = new ViewBounds(view);
                        Path path = this.getPathMotion().getPath(n3, n9, n7, n8);
                        ObjectAnimator objectAnimator = ObjectAnimatorUtils.ofPointF(object3, TOP_LEFT_PROPERTY, path);
                        bitmap = this.getPathMotion().getPath(n, n11, n6, n5);
                        viewGroup = ObjectAnimatorUtils.ofPointF(object3, BOTTOM_RIGHT_PROPERTY, (Path)bitmap);
                        bitmap = new AnimatorSet();
                        bitmap.playTogether(new Animator[]{objectAnimator, viewGroup});
                        Bitmap bitmap2 = bitmap;
                        bitmap.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter((ViewBounds)object3){
                            private ViewBounds mViewBounds;
                            final /* synthetic */ ViewBounds val$viewBounds;
                            {
                                this.val$viewBounds = viewBounds;
                                this.mViewBounds = this.val$viewBounds;
                            }
                        });
                    }
                } else if (n3 != n7 || n9 != n8) {
                    Path path = this.getPathMotion().getPath(n3, n9, n7, n8);
                    ObjectAnimator objectAnimator = ObjectAnimatorUtils.ofPointF(view, TOP_LEFT_ONLY_PROPERTY, path);
                } else {
                    Path path = this.getPathMotion().getPath(n, n11, n6, n5);
                    ObjectAnimator objectAnimator = ObjectAnimatorUtils.ofPointF(view, BOTTOM_RIGHT_ONLY_PROPERTY, path);
                }
            } else {
                void var1_19;
                void var1_16;
                ViewUtils.setLeftTopRightBottom(view, n3, n9, n3 + Math.max(n10, n4), n9 + Math.max(n2, n13));
                bitmap = null;
                if (n3 != n7 || n9 != n8) {
                    bitmap = this.getPathMotion().getPath(n3, n9, n7, n8);
                    bitmap = ObjectAnimatorUtils.ofPointF(view, POSITION_PROPERTY, (Path)bitmap);
                }
                object3 = rect;
                if (rect == null) {
                    object3 = new Rect(0, 0, n10, n2);
                }
                ViewGroup viewGroup3 = viewGroup;
                if (viewGroup == null) {
                    Rect rect3 = new Rect(0, 0, n4, n13);
                }
                viewGroup2 = var1_16;
                Object var1_17 = null;
                if (!object3.equals((Object)viewGroup2)) {
                    ViewCompat.setClipBounds(view, (Rect)object3);
                    ObjectAnimator objectAnimator = ObjectAnimator.ofObject((Object)view, (String)"clipBounds", (TypeEvaluator)sRectEvaluator, (Object[])new Object[]{object3, viewGroup2});
                    objectAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter((Rect)viewGroup, n7, n8, n6, n5){
                        private boolean mIsCanceled;
                        final /* synthetic */ int val$endBottom;
                        final /* synthetic */ int val$endLeft;
                        final /* synthetic */ int val$endRight;
                        final /* synthetic */ int val$endTop;
                        final /* synthetic */ Rect val$finalClip;
                        {
                            this.val$finalClip = rect;
                            this.val$endLeft = n;
                            this.val$endTop = n2;
                            this.val$endRight = n3;
                            this.val$endBottom = n4;
                        }

                        public void onAnimationCancel(Animator animator2) {
                            this.mIsCanceled = true;
                        }

                        public void onAnimationEnd(Animator animator2) {
                            if (!this.mIsCanceled) {
                                ViewCompat.setClipBounds(view, this.val$finalClip);
                                ViewUtils.setLeftTopRightBottom(view, this.val$endLeft, this.val$endTop, this.val$endRight, this.val$endBottom);
                            }
                        }
                    });
                }
                Animator animator2 = TransitionUtils.mergeAnimators((Animator)bitmap, (Animator)var1_19);
            }
            bitmap = var1_6;
            if (!(view.getParent() instanceof ViewGroup)) return bitmap;
            bitmap = (ViewGroup)view.getParent();
            ViewGroupUtils.suppressLayout((ViewGroup)bitmap, true);
            this.addListener(new TransitionListenerAdapter((ViewGroup)bitmap){
                boolean mCanceled = false;
                final /* synthetic */ ViewGroup val$parent;
                {
                    this.val$parent = viewGroup;
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (!this.mCanceled) {
                        ViewGroupUtils.suppressLayout(this.val$parent, false);
                    }
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    ViewGroupUtils.suppressLayout(this.val$parent, false);
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    ViewGroupUtils.suppressLayout(this.val$parent, true);
                }
            });
            return var1_6;
        }
        int n = (Integer)bitmap.values.get("android:changeBounds:windowX");
        int n16 = (Integer)bitmap.values.get("android:changeBounds:windowY");
        int n17 = (Integer)object3.values.get("android:changeBounds:windowX");
        int n18 = (Integer)object3.values.get("android:changeBounds:windowY");
        if (n == n17) {
            if (n16 == n18) return null;
        }
        object.getLocationInWindow(this.mTempLocation);
        bitmap = Bitmap.createBitmap((int)view.getWidth(), (int)view.getHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        bitmap = new BitmapDrawable(bitmap);
        float f = ViewUtils.getTransitionAlpha(view);
        ViewUtils.setTransitionAlpha(view, 0.0f);
        ViewUtils.getOverlay((View)object).add((Drawable)bitmap);
        object3 = this.getPathMotion().getPath(n - this.mTempLocation[0], n16 - this.mTempLocation[1], n17 - this.mTempLocation[0], n18 - this.mTempLocation[1]);
        object3 = ObjectAnimator.ofPropertyValuesHolder((Object)bitmap, (PropertyValuesHolder[])new PropertyValuesHolder[]{PropertyValuesHolderUtils.ofPointF(DRAWABLE_ORIGIN_PROPERTY, object3)});
        object3.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter((ViewGroup)object, (BitmapDrawable)bitmap, view, f){
            final /* synthetic */ BitmapDrawable val$drawable;
            final /* synthetic */ ViewGroup val$sceneRoot;
            final /* synthetic */ float val$transitionAlpha;
            final /* synthetic */ View val$view;
            {
                this.val$sceneRoot = viewGroup;
                this.val$drawable = bitmapDrawable;
                this.val$view = view;
                this.val$transitionAlpha = f;
            }

            public void onAnimationEnd(Animator animator2) {
                ViewUtils.getOverlay((View)this.val$sceneRoot).remove((Drawable)this.val$drawable);
                ViewUtils.setTransitionAlpha(this.val$view, this.val$transitionAlpha);
            }
        });
        return object3;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setResizeClip(boolean bl) {
        this.mResizeClip = bl;
    }

    private static class ViewBounds {
        private int mBottom;
        private int mBottomRightCalls;
        private int mLeft;
        private int mRight;
        private int mTop;
        private int mTopLeftCalls;
        private View mView;

        ViewBounds(View view) {
            this.mView = view;
        }

        private void setLeftTopRightBottom() {
            ViewUtils.setLeftTopRightBottom(this.mView, this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mTopLeftCalls = 0;
            this.mBottomRightCalls = 0;
        }

        void setBottomRight(PointF pointF) {
            this.mRight = Math.round(pointF.x);
            this.mBottom = Math.round(pointF.y);
            ++this.mBottomRightCalls;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                this.setLeftTopRightBottom();
            }
        }

        void setTopLeft(PointF pointF) {
            this.mLeft = Math.round(pointF.x);
            this.mTop = Math.round(pointF.y);
            ++this.mTopLeftCalls;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                this.setLeftTopRightBottom();
            }
        }
    }

}

