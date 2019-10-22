/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnScrollChangedListener
 *  android.widget.PopupWindow
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

class AppCompatPopupWindow
extends PopupWindow {
    private static final boolean COMPAT_OVERLAP_ANCHOR;
    private boolean mOverlapAnchor;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = Build.VERSION.SDK_INT < 21;
        COMPAT_OVERLAP_ANCHOR = bl;
    }

    public AppCompatPopupWindow(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context, attributeSet, n, 0);
    }

    public AppCompatPopupWindow(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.init(context, attributeSet, n, n2);
    }

    private void init(Context object, AttributeSet attributeSet, int n, int n2) {
        if (((TintTypedArray)(object = TintTypedArray.obtainStyledAttributes((Context)object, attributeSet, R.styleable.PopupWindow, n, n2))).hasValue(R.styleable.PopupWindow_overlapAnchor)) {
            this.setSupportOverlapAnchor(((TintTypedArray)object).getBoolean(R.styleable.PopupWindow_overlapAnchor, false));
        }
        this.setBackgroundDrawable(((TintTypedArray)object).getDrawable(R.styleable.PopupWindow_android_popupBackground));
        n = Build.VERSION.SDK_INT;
        if (n2 != 0 && n < 11 && ((TintTypedArray)object).hasValue(R.styleable.PopupWindow_android_popupAnimationStyle)) {
            this.setAnimationStyle(((TintTypedArray)object).getResourceId(R.styleable.PopupWindow_android_popupAnimationStyle, -1));
        }
        ((TintTypedArray)object).recycle();
        if (Build.VERSION.SDK_INT < 14) {
            AppCompatPopupWindow.wrapOnScrollChangedListener(this);
        }
    }

    private static void wrapOnScrollChangedListener(final PopupWindow popupWindow) {
        try {
            final Field field = PopupWindow.class.getDeclaredField("mAnchor");
            field.setAccessible(true);
            Field field2 = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
            field2.setAccessible(true);
            field2.set((Object)popupWindow, (Object)new ViewTreeObserver.OnScrollChangedListener((ViewTreeObserver.OnScrollChangedListener)field2.get((Object)popupWindow)){
                final /* synthetic */ ViewTreeObserver.OnScrollChangedListener val$originalListener;
                {
                    this.val$originalListener = onScrollChangedListener;
                }

                public void onScrollChanged() {
                    block4: {
                        WeakReference weakReference = (WeakReference)field.get((Object)popupWindow);
                        if (weakReference == null) break block4;
                        try {
                            if (weakReference.get() == null) {
                                return;
                            }
                            this.val$originalListener.onScrollChanged();
                            return;
                        }
                        catch (IllegalAccessException illegalAccessException) {
                            // empty catch block
                        }
                    }
                }
            });
            return;
        }
        catch (Exception exception) {
            Log.d((String)"AppCompatPopupWindow", (String)"Exception while installing workaround OnScrollChangedListener", (Throwable)exception);
            return;
        }
    }

    public void setSupportOverlapAnchor(boolean bl) {
        if (COMPAT_OVERLAP_ANCHOR) {
            this.mOverlapAnchor = bl;
            return;
        }
        PopupWindowCompat.setOverlapAnchor(this, bl);
    }

    public void showAsDropDown(View view, int n, int n2) {
        int n3 = n2;
        if (COMPAT_OVERLAP_ANCHOR) {
            n3 = n2;
            if (this.mOverlapAnchor) {
                n3 = n2 - view.getHeight();
            }
        }
        super.showAsDropDown(view, n, n3);
    }

    public void showAsDropDown(View view, int n, int n2, int n3) {
        int n4 = n2;
        if (COMPAT_OVERLAP_ANCHOR) {
            n4 = n2;
            if (this.mOverlapAnchor) {
                n4 = n2 - view.getHeight();
            }
        }
        super.showAsDropDown(view, n, n4, n3);
    }

    public void update(View view, int n, int n2, int n3, int n4) {
        int n5 = n2;
        if (COMPAT_OVERLAP_ANCHOR) {
            n5 = n2;
            if (this.mOverlapAnchor) {
                n5 = n2 - view.getHeight();
            }
        }
        super.update(view, n, n5, n3, n4);
    }

}

