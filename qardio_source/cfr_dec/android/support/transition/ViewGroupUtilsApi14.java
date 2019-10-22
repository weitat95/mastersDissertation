/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.LayoutTransition
 *  android.util.Log
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.support.transition.R;
import android.support.transition.ViewGroupOverlayApi14;
import android.support.transition.ViewGroupOverlayImpl;
import android.support.transition.ViewGroupUtilsImpl;
import android.util.Log;
import android.view.ViewGroup;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewGroupUtilsApi14
implements ViewGroupUtilsImpl {
    private static Method sCancelMethod;
    private static boolean sCancelMethodFetched;
    private static LayoutTransition sEmptyLayoutTransition;
    private static Field sLayoutSuppressedField;
    private static boolean sLayoutSuppressedFieldFetched;

    ViewGroupUtilsApi14() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void cancelLayoutTransition(LayoutTransition layoutTransition) {
        if (!sCancelMethodFetched) {
            try {
                sCancelMethod = LayoutTransition.class.getDeclaredMethod("cancel", new Class[0]);
                sCancelMethod.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)"ViewGroupUtilsApi14", (String)"Failed to access cancel method by reflection");
            }
            sCancelMethodFetched = true;
        }
        if (sCancelMethod == null) return;
        try {
            sCancelMethod.invoke((Object)layoutTransition, new Object[0]);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.i((String)"ViewGroupUtilsApi14", (String)"Failed to access cancel method by reflection");
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.i((String)"ViewGroupUtilsApi14", (String)"Failed to invoke cancel method by reflection");
            return;
        }
    }

    @Override
    public ViewGroupOverlayImpl getOverlay(ViewGroup viewGroup) {
        return ViewGroupOverlayApi14.createFrom(viewGroup);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void suppressLayout(ViewGroup viewGroup, boolean bl) {
        LayoutTransition layoutTransition;
        if (sEmptyLayoutTransition == null) {
            sEmptyLayoutTransition = new LayoutTransition(){

                public boolean isChangingLayout() {
                    return true;
                }
            };
            sEmptyLayoutTransition.setAnimator(2, null);
            sEmptyLayoutTransition.setAnimator(0, null);
            sEmptyLayoutTransition.setAnimator(1, null);
            sEmptyLayoutTransition.setAnimator(3, null);
            sEmptyLayoutTransition.setAnimator(4, null);
        }
        if (bl) {
            LayoutTransition layoutTransition2 = viewGroup.getLayoutTransition();
            if (layoutTransition2 != null) {
                if (layoutTransition2.isRunning()) {
                    ViewGroupUtilsApi14.cancelLayoutTransition(layoutTransition2);
                }
                if (layoutTransition2 != sEmptyLayoutTransition) {
                    viewGroup.setTag(R.id.transition_layout_save, (Object)layoutTransition2);
                }
            }
            viewGroup.setLayoutTransition(sEmptyLayoutTransition);
            return;
        }
        viewGroup.setLayoutTransition(null);
        if (!sLayoutSuppressedFieldFetched) {
            try {
                sLayoutSuppressedField = ViewGroup.class.getDeclaredField("mLayoutSuppressed");
                sLayoutSuppressedField.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.i((String)"ViewGroupUtilsApi14", (String)"Failed to access mLayoutSuppressed field by reflection");
            }
            sLayoutSuppressedFieldFetched = true;
        }
        boolean bl2 = false;
        bl = false;
        if (sLayoutSuppressedField != null) {
            bl = bl2;
            try {
                bl = bl2 = sLayoutSuppressedField.getBoolean((Object)viewGroup);
                if (bl2) {
                    bl = bl2;
                    sLayoutSuppressedField.setBoolean((Object)viewGroup, false);
                    bl = bl2;
                }
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.i((String)"ViewGroupUtilsApi14", (String)"Failed to get mLayoutSuppressed field by reflection");
            }
        }
        if (bl) {
            viewGroup.requestLayout();
        }
        if ((layoutTransition = (LayoutTransition)viewGroup.getTag(R.id.transition_layout_save)) == null) return;
        {
            viewGroup.setTag(R.id.transition_layout_save, null);
            viewGroup.setLayoutTransition(layoutTransition);
            return;
        }
    }

}

