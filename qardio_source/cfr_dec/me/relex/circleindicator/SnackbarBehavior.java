/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 */
package me.relex.circleindicator;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;
import me.relex.circleindicator.CircleIndicator;

public class SnackbarBehavior
extends CoordinatorLayout.Behavior<CircleIndicator> {
    public SnackbarBehavior() {
    }

    public SnackbarBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private float getTranslationYForSnackbar(CoordinatorLayout coordinatorLayout, CircleIndicator circleIndicator) {
        float f = 0.0f;
        List<View> list = coordinatorLayout.getDependencies((View)circleIndicator);
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            View view = list.get(i);
            float f2 = f;
            if (view instanceof Snackbar.SnackbarLayout) {
                f2 = f;
                if (coordinatorLayout.doViewsOverlap((View)circleIndicator, view)) {
                    f2 = Math.min(f, ViewCompat.getTranslationY(view) - (float)view.getHeight());
                }
            }
            f = f2;
        }
        return f;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, CircleIndicator circleIndicator, View view) {
        return view instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, CircleIndicator circleIndicator, View view) {
        circleIndicator.setTranslationY(this.getTranslationYForSnackbar(coordinatorLayout, circleIndicator));
        return true;
    }
}

