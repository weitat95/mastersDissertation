/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.widget.Button
 *  android.widget.FrameLayout
 *  android.widget.TextView
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.R;
import android.support.design.internal.SnackbarContentLayout;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public final class Snackbar
extends BaseTransientBottomBar<Snackbar> {
    private Snackbar(ViewGroup viewGroup, View view, BaseTransientBottomBar.ContentViewCallback contentViewCallback) {
        super(viewGroup, view, contentViewCallback);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static ViewGroup findSuitableParent(View view) {
        ViewGroup viewGroup;
        ViewGroup viewGroup2 = null;
        View view2 = view;
        do {
            if (view2 instanceof CoordinatorLayout) {
                return (ViewGroup)view2;
            }
            viewGroup = viewGroup2;
            if (view2 instanceof FrameLayout) {
                if (view2.getId() == 16908290) {
                    return (ViewGroup)view2;
                }
                viewGroup = (ViewGroup)view2;
            }
            view = view2;
            if (view2 != null && !((view = view2.getParent()) instanceof View)) {
                view = null;
            }
            viewGroup2 = viewGroup;
            view2 = view;
        } while (view != null);
        return viewGroup;
    }

    public static Snackbar make(View view, int n, int n2) {
        return Snackbar.make(view, view.getResources().getText(n), n2);
    }

    public static Snackbar make(View object, CharSequence charSequence, int n) {
        if ((object = Snackbar.findSuitableParent((View)object)) == null) {
            throw new IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.");
        }
        SnackbarContentLayout snackbarContentLayout = (SnackbarContentLayout)LayoutInflater.from((Context)object.getContext()).inflate(R.layout.design_layout_snackbar_include, (ViewGroup)object, false);
        object = new Snackbar((ViewGroup)object, (View)snackbarContentLayout, snackbarContentLayout);
        ((Snackbar)object).setText(charSequence);
        ((BaseTransientBottomBar)object).setDuration(n);
        return object;
    }

    public Snackbar setAction(CharSequence charSequence, final View.OnClickListener onClickListener) {
        Button button = ((SnackbarContentLayout)this.mView.getChildAt(0)).getActionView();
        if (TextUtils.isEmpty((CharSequence)charSequence) || onClickListener == null) {
            button.setVisibility(8);
            button.setOnClickListener(null);
            return this;
        }
        button.setVisibility(0);
        button.setText(charSequence);
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                onClickListener.onClick(view);
                Snackbar.this.dispatchDismiss(1);
            }
        });
        return this;
    }

    public Snackbar setText(CharSequence charSequence) {
        ((SnackbarContentLayout)this.mView.getChildAt(0)).getMessageView().setText(charSequence);
        return this;
    }

    public static final class SnackbarLayout
    extends BaseTransientBottomBar.SnackbarBaseLayout {
        public SnackbarLayout(Context context) {
            super(context);
        }

        public SnackbarLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        protected void onMeasure(int n, int n2) {
            super.onMeasure(n, n2);
            n2 = this.getChildCount();
            int n3 = this.getMeasuredWidth();
            int n4 = this.getPaddingLeft();
            int n5 = this.getPaddingRight();
            for (n = 0; n < n2; ++n) {
                View view = this.getChildAt(n);
                if (view.getLayoutParams().width != -1) continue;
                view.measure(View.MeasureSpec.makeMeasureSpec((int)(n3 - n4 - n5), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)view.getMeasuredHeight(), (int)1073741824));
            }
        }
    }

}

