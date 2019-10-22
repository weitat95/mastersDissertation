/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.transition;

import android.support.transition.TransitionPropagation;
import android.support.transition.TransitionValues;
import android.view.View;
import java.util.Map;

public abstract class VisibilityPropagation
extends TransitionPropagation {
    private static final String[] VISIBILITY_PROPAGATION_VALUES = new String[]{"android:visibilityPropagation:visibility", "android:visibilityPropagation:center"};

    private static int getViewCoordinate(TransitionValues arrn, int n) {
        if (arrn == null) {
            return -1;
        }
        arrn = (int[])arrn.values.get("android:visibilityPropagation:center");
        if (arrn == null) {
            return -1;
        }
        return arrn[n];
    }

    @Override
    public void captureValues(TransitionValues transitionValues) {
        int[] arrn;
        View view = transitionValues.view;
        int[] arrn2 = arrn = (int[])transitionValues.values.get("android:visibility:visibility");
        if (arrn == null) {
            arrn2 = view.getVisibility();
        }
        transitionValues.values.put("android:visibilityPropagation:visibility", arrn2);
        arrn2 = new int[2];
        view.getLocationOnScreen(arrn2);
        arrn2[0] = arrn2[0] + Math.round(view.getTranslationX());
        arrn2[0] = arrn2[0] + view.getWidth() / 2;
        arrn2[1] = arrn2[1] + Math.round(view.getTranslationY());
        arrn2[1] = arrn2[1] + view.getHeight() / 2;
        transitionValues.values.put("android:visibilityPropagation:center", arrn2);
    }

    @Override
    public String[] getPropagationProperties() {
        return VISIBILITY_PROPAGATION_VALUES;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int getViewVisibility(TransitionValues object) {
        if (object == null || (object = (Integer)((TransitionValues)object).values.get("android:visibilityPropagation:visibility")) == null) {
            return 8;
        }
        return (Integer)object;
    }

    public int getViewX(TransitionValues transitionValues) {
        return VisibilityPropagation.getViewCoordinate(transitionValues, 0);
    }

    public int getViewY(TransitionValues transitionValues) {
        return VisibilityPropagation.getViewCoordinate(transitionValues, 1);
    }
}

