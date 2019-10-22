/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.util.AndroidRuntimeException
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.transition.Styleable;
import android.support.transition.Transition;
import android.support.transition.TransitionListenerAdapter;
import android.support.transition.TransitionValues;
import android.support.transition.TransitionValuesMaps;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class TransitionSet
extends Transition {
    private int mCurrentListeners;
    private boolean mPlayTogether = true;
    private boolean mStarted = false;
    private ArrayList<Transition> mTransitions = new ArrayList();

    public TransitionSet() {
    }

    public TransitionSet(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, Styleable.TRANSITION_SET);
        this.setOrdering(TypedArrayUtils.getNamedInt((TypedArray)context, (XmlPullParser)((XmlResourceParser)attributeSet), "transitionOrdering", 0, 0));
        context.recycle();
    }

    static /* synthetic */ int access$106(TransitionSet transitionSet) {
        int n;
        transitionSet.mCurrentListeners = n = transitionSet.mCurrentListeners - 1;
        return n;
    }

    private void setupStartEndListeners() {
        TransitionSetListener transitionSetListener = new TransitionSetListener(this);
        Iterator<Transition> iterator = this.mTransitions.iterator();
        while (iterator.hasNext()) {
            iterator.next().addListener(transitionSetListener);
        }
        this.mCurrentListeners = this.mTransitions.size();
    }

    @Override
    public TransitionSet addListener(Transition.TransitionListener transitionListener) {
        return (TransitionSet)super.addListener(transitionListener);
    }

    public TransitionSet addTransition(Transition transition) {
        this.mTransitions.add(transition);
        transition.mParent = this;
        if (this.mDuration >= 0L) {
            transition.setDuration(this.mDuration);
        }
        return this;
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        if (this.isValidTarget(transitionValues.view)) {
            for (Transition transition : this.mTransitions) {
                if (!transition.isValidTarget(transitionValues.view)) continue;
                transition.captureEndValues(transitionValues);
                transitionValues.mTargetedTransitions.add(transition);
            }
        }
    }

    @Override
    void capturePropagationValues(TransitionValues transitionValues) {
        super.capturePropagationValues(transitionValues);
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            this.mTransitions.get(i).capturePropagationValues(transitionValues);
        }
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        if (this.isValidTarget(transitionValues.view)) {
            for (Transition transition : this.mTransitions) {
                if (!transition.isValidTarget(transitionValues.view)) continue;
                transition.captureStartValues(transitionValues);
                transitionValues.mTargetedTransitions.add(transition);
            }
        }
    }

    @Override
    public Transition clone() {
        TransitionSet transitionSet = (TransitionSet)super.clone();
        transitionSet.mTransitions = new ArrayList();
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            transitionSet.addTransition(this.mTransitions.get(i).clone());
        }
        return transitionSet;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void createAnimators(ViewGroup viewGroup, TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2, ArrayList<TransitionValues> arrayList, ArrayList<TransitionValues> arrayList2) {
        long l = this.getStartDelay();
        int n = this.mTransitions.size();
        int n2 = 0;
        while (n2 < n) {
            Transition transition = this.mTransitions.get(n2);
            if (l > 0L && (this.mPlayTogether || n2 == 0)) {
                long l2 = transition.getStartDelay();
                if (l2 > 0L) {
                    transition.setStartDelay(l + l2);
                } else {
                    transition.setStartDelay(l);
                }
            }
            transition.createAnimators(viewGroup, transitionValuesMaps, transitionValuesMaps2, arrayList, arrayList2);
            ++n2;
        }
        return;
    }

    @Override
    public void pause(View view) {
        super.pause(view);
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            this.mTransitions.get(i).pause(view);
        }
    }

    @Override
    public TransitionSet removeListener(Transition.TransitionListener transitionListener) {
        return (TransitionSet)super.removeListener(transitionListener);
    }

    @Override
    public void resume(View view) {
        super.resume(view);
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            this.mTransitions.get(i).resume(view);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void runAnimators() {
        if (this.mTransitions.isEmpty()) {
            this.start();
            this.end();
            return;
        } else {
            this.setupStartEndListeners();
            if (!this.mPlayTogether) {
                for (int i = 1; i < this.mTransitions.size(); ++i) {
                    this.mTransitions.get(i - 1).addListener(new TransitionListenerAdapter(this.mTransitions.get(i)){
                        final /* synthetic */ Transition val$nextTransition;
                        {
                            this.val$nextTransition = transition;
                        }

                        @Override
                        public void onTransitionEnd(Transition transition) {
                            this.val$nextTransition.runAnimators();
                            transition.removeListener(this);
                        }
                    });
                }
                Transition transition = this.mTransitions.get(0);
                if (transition == null) return;
                {
                    transition.runAnimators();
                    return;
                }
            } else {
                Iterator<Transition> iterator = this.mTransitions.iterator();
                while (iterator.hasNext()) {
                    iterator.next().runAnimators();
                }
            }
        }
    }

    @Override
    public TransitionSet setDuration(long l) {
        super.setDuration(l);
        if (this.mDuration >= 0L) {
            int n = this.mTransitions.size();
            for (int i = 0; i < n; ++i) {
                this.mTransitions.get(i).setDuration(l);
            }
        }
        return this;
    }

    @Override
    public TransitionSet setInterpolator(TimeInterpolator timeInterpolator) {
        return (TransitionSet)super.setInterpolator(timeInterpolator);
    }

    public TransitionSet setOrdering(int n) {
        switch (n) {
            default: {
                throw new AndroidRuntimeException("Invalid parameter for TransitionSet ordering: " + n);
            }
            case 1: {
                this.mPlayTogether = false;
                return this;
            }
            case 0: 
        }
        this.mPlayTogether = true;
        return this;
    }

    @Override
    public TransitionSet setStartDelay(long l) {
        return (TransitionSet)super.setStartDelay(l);
    }

    @Override
    String toString(String string2) {
        String string3 = super.toString(string2);
        for (int i = 0; i < this.mTransitions.size(); ++i) {
            string3 = string3 + "\n" + this.mTransitions.get(i).toString(string2 + "  ");
        }
        return string3;
    }

    static class TransitionSetListener
    extends TransitionListenerAdapter {
        TransitionSet mTransitionSet;

        TransitionSetListener(TransitionSet transitionSet) {
            this.mTransitionSet = transitionSet;
        }

        @Override
        public void onTransitionEnd(Transition transition) {
            TransitionSet.access$106(this.mTransitionSet);
            if (this.mTransitionSet.mCurrentListeners == 0) {
                this.mTransitionSet.mStarted = false;
                this.mTransitionSet.end();
            }
            transition.removeListener(this);
        }

        @Override
        public void onTransitionStart(Transition transition) {
            if (!this.mTransitionSet.mStarted) {
                this.mTransitionSet.start();
                this.mTransitionSet.mStarted = true;
            }
        }
    }

}

