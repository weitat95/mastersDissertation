/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.util.TypedValue
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.widget.FrameLayout
 */
package android.support.design.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.design.R;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

public class BottomSheetDialog
extends AppCompatDialog {
    private BottomSheetBehavior<FrameLayout> mBehavior;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback(){

        @Override
        public void onSlide(View view, float f) {
        }

        @Override
        public void onStateChanged(View view, int n) {
            if (n == 5) {
                BottomSheetDialog.this.cancel();
            }
        }
    };
    boolean mCancelable = true;
    private boolean mCanceledOnTouchOutside = true;
    private boolean mCanceledOnTouchOutsideSet;

    public BottomSheetDialog(Context context) {
        this(context, 0);
    }

    public BottomSheetDialog(Context context, int n) {
        super(context, BottomSheetDialog.getThemeResId(context, n));
        this.supportRequestWindowFeature(1);
    }

    protected BottomSheetDialog(Context context, boolean bl, DialogInterface.OnCancelListener onCancelListener) {
        super(context, bl, onCancelListener);
        this.supportRequestWindowFeature(1);
        this.mCancelable = bl;
    }

    private static int getThemeResId(Context context, int n) {
        block3: {
            int n2;
            block2: {
                n2 = n;
                if (n != 0) break block2;
                TypedValue typedValue = new TypedValue();
                if (!context.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, typedValue, true)) break block3;
                n2 = typedValue.resourceId;
            }
            return n2;
        }
        return R.style.Theme_Design_Light_BottomSheetDialog;
    }

    /*
     * Enabled aggressive block sorting
     */
    private View wrapInBottomSheet(int n, View view, ViewGroup.LayoutParams layoutParams) {
        FrameLayout frameLayout = (FrameLayout)View.inflate((Context)this.getContext(), (int)R.layout.design_bottom_sheet_dialog, null);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout)frameLayout.findViewById(R.id.coordinator);
        View view2 = view;
        if (n != 0) {
            view2 = view;
            if (view == null) {
                view2 = this.getLayoutInflater().inflate(n, (ViewGroup)coordinatorLayout, false);
            }
        }
        view = (FrameLayout)coordinatorLayout.findViewById(R.id.design_bottom_sheet);
        this.mBehavior = BottomSheetBehavior.from(view);
        this.mBehavior.setBottomSheetCallback(this.mBottomSheetCallback);
        this.mBehavior.setHideable(this.mCancelable);
        if (layoutParams == null) {
            view.addView(view2);
        } else {
            view.addView(view2, layoutParams);
        }
        coordinatorLayout.findViewById(R.id.touch_outside).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (BottomSheetDialog.this.mCancelable && BottomSheetDialog.this.isShowing() && BottomSheetDialog.this.shouldWindowCloseOnTouchOutside()) {
                    BottomSheetDialog.this.cancel();
                }
            }
        });
        ViewCompat.setAccessibilityDelegate(view, new AccessibilityDelegateCompat(){

            @Override
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                if (BottomSheetDialog.this.mCancelable) {
                    accessibilityNodeInfoCompat.addAction(1048576);
                    accessibilityNodeInfoCompat.setDismissable(true);
                    return;
                }
                accessibilityNodeInfoCompat.setDismissable(false);
            }

            @Override
            public boolean performAccessibilityAction(View view, int n, Bundle bundle) {
                if (n == 1048576 && BottomSheetDialog.this.mCancelable) {
                    BottomSheetDialog.this.cancel();
                    return true;
                }
                return super.performAccessibilityAction(view, n, bundle);
            }
        });
        view.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        return frameLayout;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        bundle = this.getWindow();
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                bundle.clearFlags(67108864);
                bundle.addFlags(Integer.MIN_VALUE);
            }
            bundle.setLayout(-1, -1);
        }
    }

    protected void onStart() {
        super.onStart();
        if (this.mBehavior != null) {
            this.mBehavior.setState(4);
        }
    }

    public void setCancelable(boolean bl) {
        super.setCancelable(bl);
        if (this.mCancelable != bl) {
            this.mCancelable = bl;
            if (this.mBehavior != null) {
                this.mBehavior.setHideable(bl);
            }
        }
    }

    public void setCanceledOnTouchOutside(boolean bl) {
        super.setCanceledOnTouchOutside(bl);
        if (bl && !this.mCancelable) {
            this.mCancelable = true;
        }
        this.mCanceledOnTouchOutside = bl;
        this.mCanceledOnTouchOutsideSet = true;
    }

    @Override
    public void setContentView(int n) {
        super.setContentView(this.wrapInBottomSheet(n, null, null));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(this.wrapInBottomSheet(0, view, null));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        super.setContentView(this.wrapInBottomSheet(0, view, layoutParams));
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean shouldWindowCloseOnTouchOutside() {
        if (!this.mCanceledOnTouchOutsideSet) {
            if (Build.VERSION.SDK_INT < 11) {
                this.mCanceledOnTouchOutside = true;
            } else {
                TypedArray typedArray = this.getContext().obtainStyledAttributes(new int[]{16843611});
                this.mCanceledOnTouchOutside = typedArray.getBoolean(0, true);
                typedArray.recycle();
            }
            this.mCanceledOnTouchOutsideSet = true;
        }
        return this.mCanceledOnTouchOutside;
    }

}

