/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.transition.SidePropagation;
import android.support.transition.Styleable;
import android.support.transition.TransitionPropagation;
import android.support.transition.TransitionValues;
import android.support.transition.TranslationAnimationCreator;
import android.support.transition.Visibility;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

public class Slide
extends Visibility {
    private static final TimeInterpolator sAccelerate;
    private static final CalculateSlide sCalculateBottom;
    private static final CalculateSlide sCalculateEnd;
    private static final CalculateSlide sCalculateLeft;
    private static final CalculateSlide sCalculateRight;
    private static final CalculateSlide sCalculateStart;
    private static final CalculateSlide sCalculateTop;
    private static final TimeInterpolator sDecelerate;
    private CalculateSlide mSlideCalculator = sCalculateBottom;
    private int mSlideEdge = 80;

    static {
        sDecelerate = new DecelerateInterpolator();
        sAccelerate = new AccelerateInterpolator();
        sCalculateLeft = new CalculateSlideHorizontal(){

            @Override
            public float getGoneX(ViewGroup viewGroup, View view) {
                return view.getTranslationX() - (float)viewGroup.getWidth();
            }
        };
        sCalculateStart = new CalculateSlideHorizontal(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public float getGoneX(ViewGroup viewGroup, View view) {
                boolean bl = true;
                if (ViewCompat.getLayoutDirection((View)viewGroup) != 1) {
                    bl = false;
                }
                if (bl) {
                    return view.getTranslationX() + (float)viewGroup.getWidth();
                }
                return view.getTranslationX() - (float)viewGroup.getWidth();
            }
        };
        sCalculateTop = new CalculateSlideVertical(){

            @Override
            public float getGoneY(ViewGroup viewGroup, View view) {
                return view.getTranslationY() - (float)viewGroup.getHeight();
            }
        };
        sCalculateRight = new CalculateSlideHorizontal(){

            @Override
            public float getGoneX(ViewGroup viewGroup, View view) {
                return view.getTranslationX() + (float)viewGroup.getWidth();
            }
        };
        sCalculateEnd = new CalculateSlideHorizontal(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public float getGoneX(ViewGroup viewGroup, View view) {
                boolean bl = true;
                if (ViewCompat.getLayoutDirection((View)viewGroup) != 1) {
                    bl = false;
                }
                if (bl) {
                    return view.getTranslationX() - (float)viewGroup.getWidth();
                }
                return view.getTranslationX() + (float)viewGroup.getWidth();
            }
        };
        sCalculateBottom = new CalculateSlideVertical(){

            @Override
            public float getGoneY(ViewGroup viewGroup, View view) {
                return view.getTranslationY() + (float)viewGroup.getHeight();
            }
        };
    }

    public Slide() {
        this.setSlideEdge(80);
    }

    public Slide(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, Styleable.SLIDE);
        int n = TypedArrayUtils.getNamedInt((TypedArray)context, (XmlPullParser)attributeSet, "slideEdge", 0, 80);
        context.recycle();
        this.setSlideEdge(n);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        int[] arrn = new int[2];
        view.getLocationOnScreen(arrn);
        transitionValues.values.put("android:slide:screenPosition", arrn);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        this.captureValues(transitionValues);
    }

    @Override
    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues arrn, TransitionValues transitionValues) {
        if (transitionValues == null) {
            return null;
        }
        arrn = (int[])transitionValues.values.get("android:slide:screenPosition");
        float f = view.getTranslationX();
        float f2 = view.getTranslationY();
        float f3 = this.mSlideCalculator.getGoneX(viewGroup, view);
        float f4 = this.mSlideCalculator.getGoneY(viewGroup, view);
        return TranslationAnimationCreator.createAnimation(view, transitionValues, arrn[0], arrn[1], f3, f4, f, f2, sDecelerate);
    }

    @Override
    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues arrn) {
        if (transitionValues == null) {
            return null;
        }
        arrn = (int[])transitionValues.values.get("android:slide:screenPosition");
        float f = view.getTranslationX();
        float f2 = view.getTranslationY();
        float f3 = this.mSlideCalculator.getGoneX(viewGroup, view);
        float f4 = this.mSlideCalculator.getGoneY(viewGroup, view);
        return TranslationAnimationCreator.createAnimation(view, transitionValues, arrn[0], arrn[1], f, f2, f3, f4, sAccelerate);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setSlideEdge(int n) {
        switch (n) {
            default: {
                throw new IllegalArgumentException("Invalid slide direction");
            }
            case 3: {
                this.mSlideCalculator = sCalculateLeft;
                break;
            }
            case 48: {
                this.mSlideCalculator = sCalculateTop;
                break;
            }
            case 5: {
                this.mSlideCalculator = sCalculateRight;
                break;
            }
            case 80: {
                this.mSlideCalculator = sCalculateBottom;
                break;
            }
            case 8388611: {
                this.mSlideCalculator = sCalculateStart;
                break;
            }
            case 8388613: {
                this.mSlideCalculator = sCalculateEnd;
            }
        }
        this.mSlideEdge = n;
        SidePropagation sidePropagation = new SidePropagation();
        sidePropagation.setSide(n);
        this.setPropagation(sidePropagation);
    }

    private static interface CalculateSlide {
        public float getGoneX(ViewGroup var1, View var2);

        public float getGoneY(ViewGroup var1, View var2);
    }

    private static abstract class CalculateSlideHorizontal
    implements CalculateSlide {
        private CalculateSlideHorizontal() {
        }

        @Override
        public float getGoneY(ViewGroup viewGroup, View view) {
            return view.getTranslationY();
        }
    }

    private static abstract class CalculateSlideVertical
    implements CalculateSlide {
        private CalculateSlideVertical() {
        }

        @Override
        public float getGoneX(ViewGroup viewGroup, View view) {
            return view.getTranslationX();
        }
    }

}

