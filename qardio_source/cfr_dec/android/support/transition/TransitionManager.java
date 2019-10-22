/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package android.support.transition;

import android.support.transition.AutoTransition;
import android.support.transition.Scene;
import android.support.transition.Transition;
import android.support.transition.TransitionListenerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TransitionManager {
    private static Transition sDefaultTransition = new AutoTransition();
    private static ArrayList<ViewGroup> sPendingTransitions;
    private static ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>> sRunningTransitions;

    static {
        sRunningTransitions = new ThreadLocal();
        sPendingTransitions = new ArrayList();
    }

    public static void beginDelayedTransition(ViewGroup viewGroup, Transition transition) {
        if (!sPendingTransitions.contains((Object)viewGroup) && ViewCompat.isLaidOut((View)viewGroup)) {
            sPendingTransitions.add(viewGroup);
            Transition transition2 = transition;
            if (transition == null) {
                transition2 = sDefaultTransition;
            }
            transition = transition2.clone();
            TransitionManager.sceneChangeSetup(viewGroup, transition);
            Scene.setCurrentScene((View)viewGroup, null);
            TransitionManager.sceneChangeRunTransition(viewGroup, transition);
        }
    }

    static ArrayMap<ViewGroup, ArrayList<Transition>> getRunningTransitions() {
        WeakReference<ArrayMap<Object, ArrayList<Transition>>> weakReference;
        block3: {
            block2: {
                WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>> weakReference2 = sRunningTransitions.get();
                if (weakReference2 == null) break block2;
                weakReference = weakReference2;
                if (weakReference2.get() != null) break block3;
            }
            weakReference = new WeakReference(new ArrayMap());
            sRunningTransitions.set(weakReference);
        }
        return (ArrayMap)weakReference.get();
    }

    private static void sceneChangeRunTransition(ViewGroup viewGroup, Transition object) {
        if (object != null && viewGroup != null) {
            object = new MultiListener((Transition)object, viewGroup);
            viewGroup.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)object);
            viewGroup.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)object);
        }
    }

    private static void sceneChangeSetup(ViewGroup object, Transition transition) {
        Object object2 = (ArrayList)TransitionManager.getRunningTransitions().get(object);
        if (object2 != null && ((ArrayList)object2).size() > 0) {
            object2 = ((ArrayList)object2).iterator();
            while (object2.hasNext()) {
                ((Transition)object2.next()).pause((View)object);
            }
        }
        if (transition != null) {
            transition.captureValues((ViewGroup)object, true);
        }
        if ((object = Scene.getCurrentScene((View)object)) != null) {
            ((Scene)object).exit();
        }
    }

    private static class MultiListener
    implements View.OnAttachStateChangeListener,
    ViewTreeObserver.OnPreDrawListener {
        ViewGroup mSceneRoot;
        Transition mTransition;

        MultiListener(Transition transition, ViewGroup viewGroup) {
            this.mTransition = transition;
            this.mSceneRoot = viewGroup;
        }

        private void removeListeners() {
            this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
            this.mSceneRoot.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onPreDraw() {
            Object object;
            this.removeListeners();
            if (!sPendingTransitions.remove((Object)this.mSceneRoot)) {
                return true;
            }
            final ArrayMap<ViewGroup, ArrayList<Transition>> arrayMap = TransitionManager.getRunningTransitions();
            ArrayList<Transition> arrayList = (ArrayList<Transition>)arrayMap.get((Object)this.mSceneRoot);
            ArrayList arrayList2 = null;
            if (arrayList == null) {
                object = new ArrayList<Transition>();
                arrayMap.put(this.mSceneRoot, (ArrayList<Transition>)object);
            } else {
                object = arrayList;
                if (arrayList.size() > 0) {
                    arrayList2 = new ArrayList(arrayList);
                    object = arrayList;
                }
            }
            ((ArrayList)object).add(this.mTransition);
            this.mTransition.addListener(new TransitionListenerAdapter(){

                @Override
                public void onTransitionEnd(Transition transition) {
                    ((ArrayList)arrayMap.get((Object)MultiListener.this.mSceneRoot)).remove(transition);
                }
            });
            this.mTransition.captureValues(this.mSceneRoot, false);
            if (arrayList2 != null) {
                object = arrayList2.iterator();
                while (object.hasNext()) {
                    ((Transition)object.next()).resume((View)this.mSceneRoot);
                }
            }
            this.mTransition.playTransition(this.mSceneRoot);
            return true;
        }

        public void onViewAttachedToWindow(View view) {
        }

        public void onViewDetachedFromWindow(View object) {
            this.removeListeners();
            sPendingTransitions.remove((Object)this.mSceneRoot);
            object = (ArrayList)TransitionManager.getRunningTransitions().get((Object)this.mSceneRoot);
            if (object != null && ((ArrayList)object).size() > 0) {
                object = ((ArrayList)object).iterator();
                while (object.hasNext()) {
                    ((Transition)object.next()).resume((View)this.mSceneRoot);
                }
            }
            this.mTransition.clearValues(true);
        }

    }

}

