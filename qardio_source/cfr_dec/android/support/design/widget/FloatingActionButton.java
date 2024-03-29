/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ImageView
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.R;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButtonImpl;
import android.support.design.widget.FloatingActionButtonLollipop;
import android.support.design.widget.ShadowViewDelegate;
import android.support.design.widget.ThemeUtils;
import android.support.design.widget.ViewGroupUtils;
import android.support.design.widget.ViewUtils;
import android.support.design.widget.VisibilityAwareImageButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;

@CoordinatorLayout.DefaultBehavior(value=Behavior.class)
public class FloatingActionButton
extends VisibilityAwareImageButton {
    private ColorStateList mBackgroundTint;
    private PorterDuff.Mode mBackgroundTintMode;
    private int mBorderWidth;
    boolean mCompatPadding;
    private AppCompatImageHelper mImageHelper;
    int mImagePadding;
    private FloatingActionButtonImpl mImpl;
    private int mMaxImageSize;
    private int mRippleColor;
    final Rect mShadowPadding = new Rect();
    private int mSize;
    private final Rect mTouchArea = new Rect();

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        ThemeUtils.checkAppCompatTheme(context);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton, n, R.style.Widget_Design_FloatingActionButton);
        this.mBackgroundTint = context.getColorStateList(R.styleable.FloatingActionButton_backgroundTint);
        this.mBackgroundTintMode = ViewUtils.parseTintMode(context.getInt(R.styleable.FloatingActionButton_backgroundTintMode, -1), null);
        this.mRippleColor = context.getColor(R.styleable.FloatingActionButton_rippleColor, 0);
        this.mSize = context.getInt(R.styleable.FloatingActionButton_fabSize, -1);
        this.mBorderWidth = context.getDimensionPixelSize(R.styleable.FloatingActionButton_borderWidth, 0);
        float f = context.getDimension(R.styleable.FloatingActionButton_elevation, 0.0f);
        float f2 = context.getDimension(R.styleable.FloatingActionButton_pressedTranslationZ, 0.0f);
        this.mCompatPadding = context.getBoolean(R.styleable.FloatingActionButton_useCompatPadding, false);
        context.recycle();
        this.mImageHelper = new AppCompatImageHelper((ImageView)this);
        this.mImageHelper.loadFromAttributes(attributeSet, n);
        this.mMaxImageSize = (int)this.getResources().getDimension(R.dimen.design_fab_image_size);
        this.getImpl().setBackgroundDrawable(this.mBackgroundTint, this.mBackgroundTintMode, this.mRippleColor, this.mBorderWidth);
        this.getImpl().setElevation(f);
        this.getImpl().setPressedTranslationZ(f2);
    }

    private FloatingActionButtonImpl createImpl() {
        if (Build.VERSION.SDK_INT >= 21) {
            return new FloatingActionButtonLollipop(this, new ShadowDelegateImpl());
        }
        return new FloatingActionButtonImpl(this, new ShadowDelegateImpl());
    }

    private FloatingActionButtonImpl getImpl() {
        if (this.mImpl == null) {
            this.mImpl = this.createImpl();
        }
        return this.mImpl;
    }

    private int getSizeDimension(int n) {
        Resources resources = this.getResources();
        switch (n) {
            default: {
                return resources.getDimensionPixelSize(R.dimen.design_fab_size_normal);
            }
            case -1: {
                if (Math.max(resources.getConfiguration().screenWidthDp, resources.getConfiguration().screenHeightDp) < 470) {
                    return this.getSizeDimension(1);
                }
                return this.getSizeDimension(0);
            }
            case 1: 
        }
        return resources.getDimensionPixelSize(R.dimen.design_fab_size_mini);
    }

    private static int resolveAdjustedSize(int n, int n2) {
        int n3 = View.MeasureSpec.getMode((int)n2);
        n2 = View.MeasureSpec.getSize((int)n2);
        switch (n3) {
            default: {
                return n;
            }
            case 0: {
                return n;
            }
            case Integer.MIN_VALUE: {
                return Math.min(n, n2);
            }
            case 1073741824: 
        }
        return n2;
    }

    private FloatingActionButtonImpl.InternalVisibilityChangedListener wrapOnVisibilityChangedListener(final OnVisibilityChangedListener onVisibilityChangedListener) {
        if (onVisibilityChangedListener == null) {
            return null;
        }
        return new FloatingActionButtonImpl.InternalVisibilityChangedListener(){

            @Override
            public void onHidden() {
                onVisibilityChangedListener.onHidden(FloatingActionButton.this);
            }

            @Override
            public void onShown() {
                onVisibilityChangedListener.onShown(FloatingActionButton.this);
            }
        };
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.getImpl().onDrawableStateChanged(this.getDrawableState());
    }

    public ColorStateList getBackgroundTintList() {
        return this.mBackgroundTint;
    }

    public PorterDuff.Mode getBackgroundTintMode() {
        return this.mBackgroundTintMode;
    }

    public float getCompatElevation() {
        return this.getImpl().getElevation();
    }

    public Drawable getContentBackground() {
        return this.getImpl().getContentBackground();
    }

    public boolean getContentRect(Rect rect) {
        boolean bl = false;
        if (ViewCompat.isLaidOut((View)this)) {
            rect.set(0, 0, this.getWidth(), this.getHeight());
            rect.left += this.mShadowPadding.left;
            rect.top += this.mShadowPadding.top;
            rect.right -= this.mShadowPadding.right;
            rect.bottom -= this.mShadowPadding.bottom;
            bl = true;
        }
        return bl;
    }

    public int getRippleColor() {
        return this.mRippleColor;
    }

    public int getSize() {
        return this.mSize;
    }

    int getSizeDimension() {
        return this.getSizeDimension(this.mSize);
    }

    public boolean getUseCompatPadding() {
        return this.mCompatPadding;
    }

    public void hide() {
        this.hide(null);
    }

    public void hide(OnVisibilityChangedListener onVisibilityChangedListener) {
        this.hide(onVisibilityChangedListener, true);
    }

    void hide(OnVisibilityChangedListener onVisibilityChangedListener, boolean bl) {
        this.getImpl().hide(this.wrapOnVisibilityChangedListener(onVisibilityChangedListener), bl);
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        this.getImpl().jumpDrawableToCurrentState();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.getImpl().onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.getImpl().onDetachedFromWindow();
    }

    protected void onMeasure(int n, int n2) {
        int n3 = this.getSizeDimension();
        this.mImagePadding = (n3 - this.mMaxImageSize) / 2;
        this.getImpl().updatePadding();
        n = Math.min(FloatingActionButton.resolveAdjustedSize(n3, n), FloatingActionButton.resolveAdjustedSize(n3, n2));
        this.setMeasuredDimension(this.mShadowPadding.left + n + this.mShadowPadding.right, this.mShadowPadding.top + n + this.mShadowPadding.bottom);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            default: {
                return super.onTouchEvent(motionEvent);
            }
            case 0: {
                if (!this.getContentRect(this.mTouchArea) || this.mTouchArea.contains((int)motionEvent.getX(), (int)motionEvent.getY())) return super.onTouchEvent(motionEvent);
                return false;
            }
        }
    }

    public void setBackgroundColor(int n) {
        Log.i((String)"FloatingActionButton", (String)"Setting a custom background is not supported.");
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        Log.i((String)"FloatingActionButton", (String)"Setting a custom background is not supported.");
    }

    public void setBackgroundResource(int n) {
        Log.i((String)"FloatingActionButton", (String)"Setting a custom background is not supported.");
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        if (this.mBackgroundTint != colorStateList) {
            this.mBackgroundTint = colorStateList;
            this.getImpl().setBackgroundTintList(colorStateList);
        }
    }

    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.mBackgroundTintMode != mode) {
            this.mBackgroundTintMode = mode;
            this.getImpl().setBackgroundTintMode(mode);
        }
    }

    public void setCompatElevation(float f) {
        this.getImpl().setElevation(f);
    }

    public void setImageResource(int n) {
        this.mImageHelper.setImageResource(n);
    }

    public void setRippleColor(int n) {
        if (this.mRippleColor != n) {
            this.mRippleColor = n;
            this.getImpl().setRippleColor(n);
        }
    }

    public void setSize(int n) {
        if (n != this.mSize) {
            this.mSize = n;
            this.requestLayout();
        }
    }

    public void setUseCompatPadding(boolean bl) {
        if (this.mCompatPadding != bl) {
            this.mCompatPadding = bl;
            this.getImpl().onCompatShadowChanged();
        }
    }

    public void show() {
        this.show(null);
    }

    public void show(OnVisibilityChangedListener onVisibilityChangedListener) {
        this.show(onVisibilityChangedListener, true);
    }

    void show(OnVisibilityChangedListener onVisibilityChangedListener, boolean bl) {
        this.getImpl().show(this.wrapOnVisibilityChangedListener(onVisibilityChangedListener), bl);
    }

    public static class Behavior
    extends CoordinatorLayout.Behavior<FloatingActionButton> {
        private static final boolean AUTO_HIDE_DEFAULT = true;
        private boolean mAutoHideEnabled;
        private OnVisibilityChangedListener mInternalAutoHideListener;
        private Rect mTmpRect;

        public Behavior() {
            this.mAutoHideEnabled = true;
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton_Behavior_Layout);
            this.mAutoHideEnabled = context.getBoolean(R.styleable.FloatingActionButton_Behavior_Layout_behavior_autoHide, true);
            context.recycle();
        }

        private static boolean isBottomSheet(View view) {
            if ((view = view.getLayoutParams()) instanceof CoordinatorLayout.LayoutParams) {
                return ((CoordinatorLayout.LayoutParams)view).getBehavior() instanceof BottomSheetBehavior;
            }
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void offsetIfNeeded(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton) {
            Rect rect = floatingActionButton.mShadowPadding;
            if (rect != null && rect.centerX() > 0 && rect.centerY() > 0) {
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)floatingActionButton.getLayoutParams();
                int n = 0;
                int n2 = 0;
                if (floatingActionButton.getRight() >= coordinatorLayout.getWidth() - layoutParams.rightMargin) {
                    n2 = rect.right;
                } else if (floatingActionButton.getLeft() <= layoutParams.leftMargin) {
                    n2 = -rect.left;
                }
                if (floatingActionButton.getBottom() >= coordinatorLayout.getHeight() - layoutParams.bottomMargin) {
                    n = rect.bottom;
                } else if (floatingActionButton.getTop() <= layoutParams.topMargin) {
                    n = -rect.top;
                }
                if (n != 0) {
                    ViewCompat.offsetTopAndBottom((View)floatingActionButton, n);
                }
                if (n2 != 0) {
                    ViewCompat.offsetLeftAndRight((View)floatingActionButton, n2);
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean shouldUpdateVisibility(View view, FloatingActionButton floatingActionButton) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)floatingActionButton.getLayoutParams();
            return this.mAutoHideEnabled && layoutParams.getAnchorId() == view.getId() && floatingActionButton.getUserSetVisibility() == 0;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean updateFabVisibilityForAppBarLayout(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, FloatingActionButton floatingActionButton) {
            if (!this.shouldUpdateVisibility((View)appBarLayout, floatingActionButton)) {
                return false;
            }
            if (this.mTmpRect == null) {
                this.mTmpRect = new Rect();
            }
            Rect rect = this.mTmpRect;
            ViewGroupUtils.getDescendantRect(coordinatorLayout, (View)appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                floatingActionButton.hide(this.mInternalAutoHideListener, false);
                do {
                    return true;
                    break;
                } while (true);
            }
            floatingActionButton.show(this.mInternalAutoHideListener, false);
            return true;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean updateFabVisibilityForBottomSheet(View view, FloatingActionButton floatingActionButton) {
            if (!this.shouldUpdateVisibility(view, floatingActionButton)) {
                return false;
            }
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)floatingActionButton.getLayoutParams();
            if (view.getTop() < floatingActionButton.getHeight() / 2 + layoutParams.topMargin) {
                floatingActionButton.hide(this.mInternalAutoHideListener, false);
                do {
                    return true;
                    break;
                } while (true);
            }
            floatingActionButton.show(this.mInternalAutoHideListener, false);
            return true;
        }

        @Override
        public boolean getInsetDodgeRect(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, Rect rect) {
            coordinatorLayout = floatingActionButton.mShadowPadding;
            rect.set(floatingActionButton.getLeft() + ((Rect)coordinatorLayout).left, floatingActionButton.getTop() + ((Rect)coordinatorLayout).top, floatingActionButton.getRight() - ((Rect)coordinatorLayout).right, floatingActionButton.getBottom() - ((Rect)coordinatorLayout).bottom);
            return true;
        }

        public boolean isAutoHideEnabled() {
            return this.mAutoHideEnabled;
        }

        @Override
        public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
            if (layoutParams.dodgeInsetEdges == 0) {
                layoutParams.dodgeInsetEdges = 80;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
            if (view instanceof AppBarLayout) {
                this.updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout)view, floatingActionButton);
                return false;
            }
            if (!Behavior.isBottomSheet(view)) return false;
            this.updateFabVisibilityForBottomSheet(view, floatingActionButton);
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, int n) {
            View view;
            List<View> list = coordinatorLayout.getDependencies((View)floatingActionButton);
            int n2 = list.size();
            for (int i = 0; i < n2 && !(!((view = list.get(i)) instanceof AppBarLayout) ? Behavior.isBottomSheet(view) && this.updateFabVisibilityForBottomSheet(view, floatingActionButton) : this.updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout)view, floatingActionButton)); ++i) {
            }
            coordinatorLayout.onLayoutChild((View)floatingActionButton, n);
            this.offsetIfNeeded(coordinatorLayout, floatingActionButton);
            return true;
        }

        public void setAutoHideEnabled(boolean bl) {
            this.mAutoHideEnabled = bl;
        }

        void setInternalAutoHideListener(OnVisibilityChangedListener onVisibilityChangedListener) {
            this.mInternalAutoHideListener = onVisibilityChangedListener;
        }
    }

    public static abstract class OnVisibilityChangedListener {
        public void onHidden(FloatingActionButton floatingActionButton) {
        }

        public void onShown(FloatingActionButton floatingActionButton) {
        }
    }

    private class ShadowDelegateImpl
    implements ShadowViewDelegate {
        ShadowDelegateImpl() {
        }

        @Override
        public float getRadius() {
            return (float)FloatingActionButton.this.getSizeDimension() / 2.0f;
        }

        @Override
        public boolean isCompatPaddingEnabled() {
            return FloatingActionButton.this.mCompatPadding;
        }

        @Override
        public void setBackgroundDrawable(Drawable drawable2) {
            FloatingActionButton.super.setBackgroundDrawable(drawable2);
        }

        @Override
        public void setShadowPadding(int n, int n2, int n3, int n4) {
            FloatingActionButton.this.mShadowPadding.set(n, n2, n3, n4);
            FloatingActionButton.this.setPadding(FloatingActionButton.this.mImagePadding + n, FloatingActionButton.this.mImagePadding + n2, FloatingActionButton.this.mImagePadding + n3, FloatingActionButton.this.mImagePadding + n4);
        }
    }

}

