/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.appcompat.R;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

class TooltipPopup {
    private final View mContentView;
    private final Context mContext;
    private final WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();
    private final TextView mMessageView;
    private final int[] mTmpAnchorPos;
    private final int[] mTmpAppPos;
    private final Rect mTmpDisplayFrame = new Rect();

    TooltipPopup(Context context) {
        this.mTmpAnchorPos = new int[2];
        this.mTmpAppPos = new int[2];
        this.mContext = context;
        this.mContentView = LayoutInflater.from((Context)this.mContext).inflate(R.layout.tooltip, null);
        this.mMessageView = (TextView)this.mContentView.findViewById(R.id.message);
        this.mLayoutParams.setTitle((CharSequence)this.getClass().getSimpleName());
        this.mLayoutParams.packageName = this.mContext.getPackageName();
        this.mLayoutParams.type = 1002;
        this.mLayoutParams.width = -2;
        this.mLayoutParams.height = -2;
        this.mLayoutParams.format = -3;
        this.mLayoutParams.windowAnimations = R.style.Animation_AppCompat_Tooltip;
        this.mLayoutParams.flags = 24;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void computePosition(View arrn, int n, int n2, boolean bl, WindowManager.LayoutParams layoutParams) {
        int n3;
        int n4 = this.mContext.getResources().getDimensionPixelOffset(R.dimen.tooltip_precise_anchor_threshold);
        if (arrn.getWidth() < n4) {
            n = arrn.getWidth() / 2;
        }
        if (arrn.getHeight() >= n4) {
            n3 = this.mContext.getResources().getDimensionPixelOffset(R.dimen.tooltip_precise_anchor_extra_offset);
            n4 = n2 + n3;
            n3 = n2 - n3;
            n2 = n4;
            n4 = n3;
        } else {
            n2 = arrn.getHeight();
            n4 = 0;
        }
        layoutParams.gravity = 49;
        Resources resources = this.mContext.getResources();
        n3 = bl ? R.dimen.tooltip_y_offset_touch : R.dimen.tooltip_y_offset_non_touch;
        int n5 = resources.getDimensionPixelOffset(n3);
        resources = TooltipPopup.getAppRootView((View)arrn);
        if (resources == null) {
            Log.e((String)"TooltipPopup", (String)"Cannot find app view");
            return;
        }
        resources.getWindowVisibleDisplayFrame(this.mTmpDisplayFrame);
        if (this.mTmpDisplayFrame.left < 0 && this.mTmpDisplayFrame.top < 0) {
            Resources resources2 = this.mContext.getResources();
            n3 = resources2.getIdentifier("status_bar_height", "dimen", "android");
            n3 = n3 != 0 ? resources2.getDimensionPixelSize(n3) : 0;
            resources2 = resources2.getDisplayMetrics();
            this.mTmpDisplayFrame.set(0, n3, resources2.widthPixels, resources2.heightPixels);
        }
        resources.getLocationOnScreen(this.mTmpAppPos);
        arrn.getLocationOnScreen(this.mTmpAnchorPos);
        arrn = this.mTmpAnchorPos;
        arrn[0] = arrn[0] - this.mTmpAppPos[0];
        arrn = this.mTmpAnchorPos;
        arrn[1] = arrn[1] - this.mTmpAppPos[1];
        layoutParams.x = this.mTmpAnchorPos[0] + n - this.mTmpDisplayFrame.width() / 2;
        n = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        this.mContentView.measure(n, n);
        n = this.mContentView.getMeasuredHeight();
        n4 = this.mTmpAnchorPos[1] + n4 - n5 - n;
        n2 = this.mTmpAnchorPos[1] + n2 + n5;
        if (bl) {
            if (n4 >= 0) {
                layoutParams.y = n4;
                return;
            }
            layoutParams.y = n2;
            return;
        }
        if (n2 + n <= this.mTmpDisplayFrame.height()) {
            layoutParams.y = n2;
            return;
        }
        layoutParams.y = n4;
    }

    private static View getAppRootView(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return ((Activity)context).getWindow().getDecorView();
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return view.getRootView();
    }

    void hide() {
        if (!this.isShowing()) {
            return;
        }
        ((WindowManager)this.mContext.getSystemService("window")).removeView(this.mContentView);
    }

    boolean isShowing() {
        return this.mContentView.getParent() != null;
    }

    void show(View view, int n, int n2, boolean bl, CharSequence charSequence) {
        if (this.isShowing()) {
            this.hide();
        }
        this.mMessageView.setText(charSequence);
        this.computePosition(view, n, n2, bl, this.mLayoutParams);
        ((WindowManager)this.mContext.getSystemService("window")).addView(this.mContentView, (ViewGroup.LayoutParams)this.mLayoutParams);
    }
}

