/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.ActionBar
 *  android.app.Activity
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.v7.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggleHoneycomb;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ActionBarDrawerToggle
implements DrawerLayout.DrawerListener {
    private final Delegate mActivityImpl;
    private final int mCloseDrawerContentDescRes;
    boolean mDrawerIndicatorEnabled = true;
    private final DrawerLayout mDrawerLayout;
    private boolean mDrawerSlideAnimationEnabled = true;
    private Drawable mHomeAsUpIndicator;
    private final int mOpenDrawerContentDescRes;
    private DrawerArrowDrawable mSlider;
    View.OnClickListener mToolbarNavigationClickListener;
    private boolean mWarnedForDisplayHomeAsUp = false;

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int n, int n2) {
        this(activity, toolbar, drawerLayout, null, n, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    ActionBarDrawerToggle(Activity activity, Toolbar toolbar, DrawerLayout drawerLayout, DrawerArrowDrawable drawerArrowDrawable, int n, int n2) {
        if (toolbar != null) {
            this.mActivityImpl = new ToolbarCompatDelegate(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener(){

                /*
                 * Enabled aggressive block sorting
                 */
                public void onClick(View view) {
                    if (ActionBarDrawerToggle.this.mDrawerIndicatorEnabled) {
                        ActionBarDrawerToggle.this.toggle();
                        return;
                    } else {
                        if (ActionBarDrawerToggle.this.mToolbarNavigationClickListener == null) return;
                        {
                            ActionBarDrawerToggle.this.mToolbarNavigationClickListener.onClick(view);
                            return;
                        }
                    }
                }
            });
        } else {
            this.mActivityImpl = activity instanceof DelegateProvider ? ((DelegateProvider)activity).getDrawerToggleDelegate() : (Build.VERSION.SDK_INT >= 18 ? new JellybeanMr2Delegate(activity) : (Build.VERSION.SDK_INT >= 14 ? new IcsDelegate(activity) : (Build.VERSION.SDK_INT >= 11 ? new HoneycombDelegate(activity) : new DummyDelegate(activity))));
        }
        this.mDrawerLayout = drawerLayout;
        this.mOpenDrawerContentDescRes = n;
        this.mCloseDrawerContentDescRes = n2;
        this.mSlider = drawerArrowDrawable == null ? new DrawerArrowDrawable(this.mActivityImpl.getActionBarThemedContext()) : drawerArrowDrawable;
        this.mHomeAsUpIndicator = this.getThemeUpIndicator();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setPosition(float f) {
        if (f == 1.0f) {
            this.mSlider.setVerticalMirror(true);
        } else if (f == 0.0f) {
            this.mSlider.setVerticalMirror(false);
        }
        this.mSlider.setProgress(f);
    }

    Drawable getThemeUpIndicator() {
        return this.mActivityImpl.getThemeUpIndicator();
    }

    @Override
    public void onDrawerClosed(View view) {
        this.setPosition(0.0f);
        if (this.mDrawerIndicatorEnabled) {
            this.setActionBarDescription(this.mOpenDrawerContentDescRes);
        }
    }

    @Override
    public void onDrawerOpened(View view) {
        this.setPosition(1.0f);
        if (this.mDrawerIndicatorEnabled) {
            this.setActionBarDescription(this.mCloseDrawerContentDescRes);
        }
    }

    @Override
    public void onDrawerSlide(View view, float f) {
        if (this.mDrawerSlideAnimationEnabled) {
            this.setPosition(Math.min(1.0f, Math.max(0.0f, f)));
            return;
        }
        this.setPosition(0.0f);
    }

    @Override
    public void onDrawerStateChanged(int n) {
    }

    void setActionBarDescription(int n) {
        this.mActivityImpl.setActionBarDescription(n);
    }

    void setActionBarUpIndicator(Drawable drawable2, int n) {
        if (!this.mWarnedForDisplayHomeAsUp && !this.mActivityImpl.isNavigationVisible()) {
            Log.w((String)"ActionBarDrawerToggle", (String)"DrawerToggle may not show up because NavigationIcon is not visible. You may need to call actionbar.setDisplayHomeAsUpEnabled(true);");
            this.mWarnedForDisplayHomeAsUp = true;
        }
        this.mActivityImpl.setActionBarUpIndicator(drawable2, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void syncState() {
        if (this.mDrawerLayout.isDrawerOpen(8388611)) {
            this.setPosition(1.0f);
        } else {
            this.setPosition(0.0f);
        }
        if (this.mDrawerIndicatorEnabled) {
            DrawerArrowDrawable drawerArrowDrawable = this.mSlider;
            int n = this.mDrawerLayout.isDrawerOpen(8388611) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes;
            this.setActionBarUpIndicator(drawerArrowDrawable, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void toggle() {
        int n = this.mDrawerLayout.getDrawerLockMode(8388611);
        if (this.mDrawerLayout.isDrawerVisible(8388611) && n != 2) {
            this.mDrawerLayout.closeDrawer(8388611);
            return;
        } else {
            if (n == 1) return;
            {
                this.mDrawerLayout.openDrawer(8388611);
                return;
            }
        }
    }

    public static interface Delegate {
        public Context getActionBarThemedContext();

        public Drawable getThemeUpIndicator();

        public boolean isNavigationVisible();

        public void setActionBarDescription(int var1);

        public void setActionBarUpIndicator(Drawable var1, int var2);
    }

    public static interface DelegateProvider {
        public Delegate getDrawerToggleDelegate();
    }

    static class DummyDelegate
    implements Delegate {
        final Activity mActivity;

        DummyDelegate(Activity activity) {
            this.mActivity = activity;
        }

        @Override
        public Context getActionBarThemedContext() {
            return this.mActivity;
        }

        @Override
        public Drawable getThemeUpIndicator() {
            return null;
        }

        @Override
        public boolean isNavigationVisible() {
            return true;
        }

        @Override
        public void setActionBarDescription(int n) {
        }

        @Override
        public void setActionBarUpIndicator(Drawable drawable2, int n) {
        }
    }

    private static class HoneycombDelegate
    implements Delegate {
        final Activity mActivity;
        ActionBarDrawerToggleHoneycomb.SetIndicatorInfo mSetIndicatorInfo;

        HoneycombDelegate(Activity activity) {
            this.mActivity = activity;
        }

        @Override
        public Context getActionBarThemedContext() {
            return this.mActivity;
        }

        @Override
        public Drawable getThemeUpIndicator() {
            return ActionBarDrawerToggleHoneycomb.getThemeUpIndicator(this.mActivity);
        }

        @Override
        public boolean isNavigationVisible() {
            ActionBar actionBar = this.mActivity.getActionBar();
            return actionBar != null && (actionBar.getDisplayOptions() & 4) != 0;
        }

        @Override
        public void setActionBarDescription(int n) {
            this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarDescription(this.mSetIndicatorInfo, this.mActivity, n);
        }

        @Override
        public void setActionBarUpIndicator(Drawable drawable2, int n) {
            ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowHomeEnabled(true);
                this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarUpIndicator(this.mSetIndicatorInfo, this.mActivity, drawable2, n);
                actionBar.setDisplayShowHomeEnabled(false);
            }
        }
    }

    private static class IcsDelegate
    extends HoneycombDelegate {
        IcsDelegate(Activity activity) {
            super(activity);
        }

        @Override
        public Context getActionBarThemedContext() {
            ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                return actionBar.getThemedContext();
            }
            return this.mActivity;
        }
    }

    private static class JellybeanMr2Delegate
    implements Delegate {
        final Activity mActivity;

        JellybeanMr2Delegate(Activity activity) {
            this.mActivity = activity;
        }

        @Override
        public Context getActionBarThemedContext() {
            ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                return actionBar.getThemedContext();
            }
            return this.mActivity;
        }

        @Override
        public Drawable getThemeUpIndicator() {
            TypedArray typedArray = this.getActionBarThemedContext().obtainStyledAttributes(null, new int[]{16843531}, 16843470, 0);
            Drawable drawable2 = typedArray.getDrawable(0);
            typedArray.recycle();
            return drawable2;
        }

        @Override
        public boolean isNavigationVisible() {
            ActionBar actionBar = this.mActivity.getActionBar();
            return actionBar != null && (actionBar.getDisplayOptions() & 4) != 0;
        }

        @Override
        public void setActionBarDescription(int n) {
            ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeActionContentDescription(n);
            }
        }

        @Override
        public void setActionBarUpIndicator(Drawable drawable2, int n) {
            ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(drawable2);
                actionBar.setHomeActionContentDescription(n);
            }
        }
    }

    static class ToolbarCompatDelegate
    implements Delegate {
        final CharSequence mDefaultContentDescription;
        final Drawable mDefaultUpIndicator;
        final Toolbar mToolbar;

        ToolbarCompatDelegate(Toolbar toolbar) {
            this.mToolbar = toolbar;
            this.mDefaultUpIndicator = toolbar.getNavigationIcon();
            this.mDefaultContentDescription = toolbar.getNavigationContentDescription();
        }

        @Override
        public Context getActionBarThemedContext() {
            return this.mToolbar.getContext();
        }

        @Override
        public Drawable getThemeUpIndicator() {
            return this.mDefaultUpIndicator;
        }

        @Override
        public boolean isNavigationVisible() {
            return true;
        }

        @Override
        public void setActionBarDescription(int n) {
            if (n == 0) {
                this.mToolbar.setNavigationContentDescription(this.mDefaultContentDescription);
                return;
            }
            this.mToolbar.setNavigationContentDescription(n);
        }

        @Override
        public void setActionBarUpIndicator(Drawable drawable2, int n) {
            this.mToolbar.setNavigationIcon(drawable2);
            this.setActionBarDescription(n);
        }
    }

}

