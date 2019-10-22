/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.ContextThemeWrapper
 *  android.view.KeyCharacterMap
 *  android.view.KeyEvent
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.Window
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 */
package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.app.ActionBar;
import android.support.v7.appcompat.R;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WindowDecorActionBar
extends ActionBar
implements ActionBarOverlayLayout.ActionBarVisibilityCallback {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Interpolator sHideInterpolator;
    private static final Interpolator sShowInterpolator;
    ActionModeImpl mActionMode;
    private Activity mActivity;
    ActionBarContainer mContainerView;
    boolean mContentAnimations = true;
    View mContentView;
    Context mContext;
    ActionBarContextView mContextView;
    private int mCurWindowVisibility = 0;
    ViewPropertyAnimatorCompatSet mCurrentShowAnim;
    DecorToolbar mDecorToolbar;
    ActionMode mDeferredDestroyActionMode;
    ActionMode.Callback mDeferredModeDestroyCallback;
    private Dialog mDialog;
    private boolean mDisplayHomeAsUpSet;
    private boolean mHasEmbeddedTabs;
    boolean mHiddenByApp;
    boolean mHiddenBySystem;
    final ViewPropertyAnimatorListener mHideListener;
    boolean mHideOnContentScroll;
    private boolean mLastMenuVisibility;
    private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners;
    private boolean mNowShowing = true;
    ActionBarOverlayLayout mOverlayLayout;
    private int mSavedTabPosition = -1;
    private boolean mShowHideAnimationEnabled;
    final ViewPropertyAnimatorListener mShowListener;
    private boolean mShowingForMode;
    ScrollingTabContainerView mTabScrollView;
    private ArrayList<Object> mTabs = new ArrayList();
    private Context mThemedContext;
    final ViewPropertyAnimatorUpdateListener mUpdateListener;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !WindowDecorActionBar.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        sHideInterpolator = new AccelerateInterpolator();
        sShowInterpolator = new DecelerateInterpolator();
    }

    public WindowDecorActionBar(Activity activity, boolean bl) {
        this.mMenuVisibilityListeners = new ArrayList();
        this.mHideListener = new ViewPropertyAnimatorListenerAdapter(){

            @Override
            public void onAnimationEnd(View view) {
                if (WindowDecorActionBar.this.mContentAnimations && WindowDecorActionBar.this.mContentView != null) {
                    WindowDecorActionBar.this.mContentView.setTranslationY(0.0f);
                    WindowDecorActionBar.this.mContainerView.setTranslationY(0.0f);
                }
                WindowDecorActionBar.this.mContainerView.setVisibility(8);
                WindowDecorActionBar.this.mContainerView.setTransitioning(false);
                WindowDecorActionBar.this.mCurrentShowAnim = null;
                WindowDecorActionBar.this.completeDeferredDestroyActionMode();
                if (WindowDecorActionBar.this.mOverlayLayout != null) {
                    ViewCompat.requestApplyInsets((View)WindowDecorActionBar.this.mOverlayLayout);
                }
            }
        };
        this.mShowListener = new ViewPropertyAnimatorListenerAdapter(){

            @Override
            public void onAnimationEnd(View view) {
                WindowDecorActionBar.this.mCurrentShowAnim = null;
                WindowDecorActionBar.this.mContainerView.requestLayout();
            }
        };
        this.mUpdateListener = new ViewPropertyAnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(View view) {
                ((View)WindowDecorActionBar.this.mContainerView.getParent()).invalidate();
            }
        };
        this.mActivity = activity;
        activity = activity.getWindow().getDecorView();
        this.init((View)activity);
        if (!bl) {
            this.mContentView = activity.findViewById(16908290);
        }
    }

    public WindowDecorActionBar(Dialog dialog) {
        this.mMenuVisibilityListeners = new ArrayList();
        this.mHideListener = new /* invalid duplicate definition of identical inner class */;
        this.mShowListener = new /* invalid duplicate definition of identical inner class */;
        this.mUpdateListener = new /* invalid duplicate definition of identical inner class */;
        this.mDialog = dialog;
        this.init(dialog.getWindow().getDecorView());
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean checkShowingFlags(boolean bl, boolean bl2, boolean bl3) {
        return bl3 || !bl && !bl2;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private DecorToolbar getDecorToolbar(View object) {
        void var1_3;
        if (object instanceof DecorToolbar) {
            return (DecorToolbar)object;
        }
        if (object instanceof Toolbar) {
            return ((Toolbar)((Object)object)).getWrapper();
        }
        if ("Can't make a decor toolbar out of " + object != null) {
            String string2 = object.getClass().getSimpleName();
            do {
                throw new IllegalStateException((String)var1_3);
                break;
            } while (true);
        }
        String string3 = "null";
        throw new IllegalStateException((String)var1_3);
    }

    private void hideForActionMode() {
        if (this.mShowingForMode) {
            this.mShowingForMode = false;
            if (this.mOverlayLayout != null) {
                this.mOverlayLayout.setShowingForActionMode(false);
            }
            this.updateVisibility(false);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void init(View object) {
        this.mOverlayLayout = (ActionBarOverlayLayout)object.findViewById(R.id.decor_content_parent);
        if (this.mOverlayLayout != null) {
            this.mOverlayLayout.setActionBarVisibilityCallback(this);
        }
        this.mDecorToolbar = this.getDecorToolbar(object.findViewById(R.id.action_bar));
        this.mContextView = (ActionBarContextView)object.findViewById(R.id.action_context_bar);
        this.mContainerView = (ActionBarContainer)object.findViewById(R.id.action_bar_container);
        if (this.mDecorToolbar == null || this.mContextView == null || this.mContainerView == null) {
            throw new IllegalStateException(this.getClass().getSimpleName() + " can only be used " + "with a compatible window decor layout");
        }
        this.mContext = this.mDecorToolbar.getContext();
        int n = (this.mDecorToolbar.getDisplayOptions() & 4) != 0 ? 1 : 0;
        if (n != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        boolean bl = ((ActionBarPolicy)(object = ActionBarPolicy.get(this.mContext))).enableHomeButtonByDefault() || n != 0;
        this.setHomeButtonEnabled(bl);
        this.setHasEmbeddedTabs(((ActionBarPolicy)object).hasEmbeddedTabs());
        object = this.mContext.obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
        if (object.getBoolean(R.styleable.ActionBar_hideOnContentScroll, false)) {
            this.setHideOnContentScrollEnabled(true);
        }
        if ((n = object.getDimensionPixelSize(R.styleable.ActionBar_elevation, 0)) != 0) {
            this.setElevation(n);
        }
        object.recycle();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setHasEmbeddedTabs(boolean bl) {
        boolean bl2 = true;
        this.mHasEmbeddedTabs = bl;
        if (!this.mHasEmbeddedTabs) {
            this.mDecorToolbar.setEmbeddedTabView(null);
            this.mContainerView.setTabContainer(this.mTabScrollView);
        } else {
            this.mContainerView.setTabContainer(null);
            this.mDecorToolbar.setEmbeddedTabView(this.mTabScrollView);
        }
        boolean bl3 = this.getNavigationMode() == 2;
        if (this.mTabScrollView != null) {
            if (bl3) {
                this.mTabScrollView.setVisibility(0);
                if (this.mOverlayLayout != null) {
                    ViewCompat.requestApplyInsets((View)this.mOverlayLayout);
                }
            } else {
                this.mTabScrollView.setVisibility(8);
            }
        }
        Object object = this.mDecorToolbar;
        bl = !this.mHasEmbeddedTabs && bl3;
        object.setCollapsible(bl);
        object = this.mOverlayLayout;
        bl = !this.mHasEmbeddedTabs && bl3 ? bl2 : false;
        ((ActionBarOverlayLayout)object).setHasNonEmbeddedTabs(bl);
    }

    private boolean shouldAnimateContextView() {
        return ViewCompat.isLaidOut((View)this.mContainerView);
    }

    private void showForActionMode() {
        if (!this.mShowingForMode) {
            this.mShowingForMode = true;
            if (this.mOverlayLayout != null) {
                this.mOverlayLayout.setShowingForActionMode(true);
            }
            this.updateVisibility(false);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateVisibility(boolean bl) {
        if (WindowDecorActionBar.checkShowingFlags(this.mHiddenByApp, this.mHiddenBySystem, this.mShowingForMode)) {
            if (this.mNowShowing) return;
            {
                this.mNowShowing = true;
                this.doShow(bl);
                return;
            }
        } else {
            if (!this.mNowShowing) return;
            {
                this.mNowShowing = false;
                this.doHide(bl);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void animateToMode(boolean bl) {
        if (bl) {
            this.showForActionMode();
        } else {
            this.hideForActionMode();
        }
        if (this.shouldAnimateContextView()) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2;
            if (bl) {
                viewPropertyAnimatorCompat = this.mDecorToolbar.setupAnimatorToVisibility(4, 100L);
                viewPropertyAnimatorCompat2 = this.mContextView.setupAnimatorToVisibility(0, 200L);
            } else {
                viewPropertyAnimatorCompat2 = this.mDecorToolbar.setupAnimatorToVisibility(0, 200L);
                viewPropertyAnimatorCompat = this.mContextView.setupAnimatorToVisibility(8, 100L);
            }
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
            viewPropertyAnimatorCompatSet.playSequentially(viewPropertyAnimatorCompat, viewPropertyAnimatorCompat2);
            viewPropertyAnimatorCompatSet.start();
            return;
        }
        if (bl) {
            this.mDecorToolbar.setVisibility(4);
            this.mContextView.setVisibility(0);
            return;
        }
        this.mDecorToolbar.setVisibility(0);
        this.mContextView.setVisibility(8);
    }

    @Override
    public boolean collapseActionView() {
        if (this.mDecorToolbar != null && this.mDecorToolbar.hasExpandedActionView()) {
            this.mDecorToolbar.collapseActionView();
            return true;
        }
        return false;
    }

    void completeDeferredDestroyActionMode() {
        if (this.mDeferredModeDestroyCallback != null) {
            this.mDeferredModeDestroyCallback.onDestroyActionMode(this.mDeferredDestroyActionMode);
            this.mDeferredDestroyActionMode = null;
            this.mDeferredModeDestroyCallback = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void dispatchMenuVisibilityChanged(boolean bl) {
        if (bl != this.mLastMenuVisibility) {
            this.mLastMenuVisibility = bl;
            int n = this.mMenuVisibilityListeners.size();
            for (int i = 0; i < n; ++i) {
                this.mMenuVisibilityListeners.get(i).onMenuVisibilityChanged(bl);
            }
        }
    }

    public void doHide(boolean bl) {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
        }
        if (this.mCurWindowVisibility == 0 && (this.mShowHideAnimationEnabled || bl)) {
            Object object;
            float f;
            this.mContainerView.setAlpha(1.0f);
            this.mContainerView.setTransitioning(true);
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
            float f2 = f = (float)(-this.mContainerView.getHeight());
            if (bl) {
                object = new int[]{0, 0};
                this.mContainerView.getLocationInWindow((int[])object);
                f2 = f - (float)object[1];
            }
            object = ViewCompat.animate((View)this.mContainerView).translationY(f2);
            ((ViewPropertyAnimatorCompat)object).setUpdateListener(this.mUpdateListener);
            viewPropertyAnimatorCompatSet.play((ViewPropertyAnimatorCompat)object);
            if (this.mContentAnimations && this.mContentView != null) {
                viewPropertyAnimatorCompatSet.play(ViewCompat.animate(this.mContentView).translationY(f2));
            }
            viewPropertyAnimatorCompatSet.setInterpolator(sHideInterpolator);
            viewPropertyAnimatorCompatSet.setDuration(250L);
            viewPropertyAnimatorCompatSet.setListener(this.mHideListener);
            this.mCurrentShowAnim = viewPropertyAnimatorCompatSet;
            viewPropertyAnimatorCompatSet.start();
            return;
        }
        this.mHideListener.onAnimationEnd(null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void doShow(boolean bl) {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
        }
        this.mContainerView.setVisibility(0);
        if (this.mCurWindowVisibility == 0 && (this.mShowHideAnimationEnabled || bl)) {
            Object object;
            float f;
            this.mContainerView.setTranslationY(0.0f);
            float f2 = f = (float)(-this.mContainerView.getHeight());
            if (bl) {
                object = new int[]{0, 0};
                this.mContainerView.getLocationInWindow((int[])object);
                f2 = f - (float)object[1];
            }
            this.mContainerView.setTranslationY(f2);
            object = new ViewPropertyAnimatorCompatSet();
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate((View)this.mContainerView).translationY(0.0f);
            viewPropertyAnimatorCompat.setUpdateListener(this.mUpdateListener);
            ((ViewPropertyAnimatorCompatSet)object).play(viewPropertyAnimatorCompat);
            if (this.mContentAnimations && this.mContentView != null) {
                this.mContentView.setTranslationY(f2);
                ((ViewPropertyAnimatorCompatSet)object).play(ViewCompat.animate(this.mContentView).translationY(0.0f));
            }
            ((ViewPropertyAnimatorCompatSet)object).setInterpolator(sShowInterpolator);
            ((ViewPropertyAnimatorCompatSet)object).setDuration(250L);
            ((ViewPropertyAnimatorCompatSet)object).setListener(this.mShowListener);
            this.mCurrentShowAnim = object;
            ((ViewPropertyAnimatorCompatSet)object).start();
        } else {
            this.mContainerView.setAlpha(1.0f);
            this.mContainerView.setTranslationY(0.0f);
            if (this.mContentAnimations && this.mContentView != null) {
                this.mContentView.setTranslationY(0.0f);
            }
            this.mShowListener.onAnimationEnd(null);
        }
        if (this.mOverlayLayout != null) {
            ViewCompat.requestApplyInsets((View)this.mOverlayLayout);
        }
    }

    @Override
    public void enableContentAnimations(boolean bl) {
        this.mContentAnimations = bl;
    }

    @Override
    public int getDisplayOptions() {
        return this.mDecorToolbar.getDisplayOptions();
    }

    public int getHeight() {
        return this.mContainerView.getHeight();
    }

    @Override
    public int getHideOffset() {
        return this.mOverlayLayout.getActionBarHideOffset();
    }

    public int getNavigationMode() {
        return this.mDecorToolbar.getNavigationMode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Context getThemedContext() {
        block4: {
            block3: {
                if (this.mThemedContext != null) break block3;
                TypedValue typedValue = new TypedValue();
                this.mContext.getTheme().resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
                int n = typedValue.resourceId;
                if (n == 0) break block4;
                this.mThemedContext = new ContextThemeWrapper(this.mContext, n);
            }
            do {
                return this.mThemedContext;
                break;
            } while (true);
        }
        this.mThemedContext = this.mContext;
        return this.mThemedContext;
    }

    @Override
    public void hide() {
        if (!this.mHiddenByApp) {
            this.mHiddenByApp = true;
            this.updateVisibility(false);
        }
    }

    @Override
    public void hideForSystem() {
        if (!this.mHiddenBySystem) {
            this.mHiddenBySystem = true;
            this.updateVisibility(true);
        }
    }

    @Override
    public boolean isShowing() {
        int n = this.getHeight();
        return this.mNowShowing && (n == 0 || this.getHideOffset() < n);
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        this.setHasEmbeddedTabs(ActionBarPolicy.get(this.mContext).hasEmbeddedTabs());
    }

    @Override
    public void onContentScrollStarted() {
        if (this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
            this.mCurrentShowAnim = null;
        }
    }

    @Override
    public void onContentScrollStopped() {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onKeyShortcut(int n, KeyEvent keyEvent) {
        Menu menu2;
        if (this.mActionMode == null || (menu2 = this.mActionMode.getMenu()) == null) {
            return false;
        }
        int n2 = keyEvent != null ? keyEvent.getDeviceId() : -1;
        boolean bl = KeyCharacterMap.load((int)n2).getKeyboardType() != 1;
        menu2.setQwertyMode(bl);
        return menu2.performShortcut(n, keyEvent, 0);
    }

    @Override
    public void onWindowVisibilityChanged(int n) {
        this.mCurWindowVisibility = n;
    }

    @Override
    public void setDefaultDisplayHomeAsUpEnabled(boolean bl) {
        if (!this.mDisplayHomeAsUpSet) {
            this.setDisplayHomeAsUpEnabled(bl);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setDisplayHomeAsUpEnabled(boolean bl) {
        int n = bl ? 4 : 0;
        this.setDisplayOptions(n, 4);
    }

    public void setDisplayOptions(int n, int n2) {
        int n3 = this.mDecorToolbar.getDisplayOptions();
        if ((n2 & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.mDecorToolbar.setDisplayOptions(n & n2 | ~n2 & n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setDisplayShowCustomEnabled(boolean bl) {
        int n = bl ? 16 : 0;
        this.setDisplayOptions(n, 16);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setDisplayShowHomeEnabled(boolean bl) {
        int n = bl ? 2 : 0;
        this.setDisplayOptions(n, 2);
    }

    @Override
    public void setElevation(float f) {
        ViewCompat.setElevation((View)this.mContainerView, f);
    }

    @Override
    public void setHideOnContentScrollEnabled(boolean bl) {
        if (bl && !this.mOverlayLayout.isInOverlayMode()) {
            throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
        }
        this.mHideOnContentScroll = bl;
        this.mOverlayLayout.setHideOnContentScrollEnabled(bl);
    }

    @Override
    public void setHomeActionContentDescription(int n) {
        this.mDecorToolbar.setNavigationContentDescription(n);
    }

    @Override
    public void setHomeAsUpIndicator(int n) {
        this.mDecorToolbar.setNavigationIcon(n);
    }

    @Override
    public void setHomeAsUpIndicator(Drawable drawable2) {
        this.mDecorToolbar.setNavigationIcon(drawable2);
    }

    @Override
    public void setHomeButtonEnabled(boolean bl) {
        this.mDecorToolbar.setHomeButtonEnabled(bl);
    }

    @Override
    public void setIcon(int n) {
        this.mDecorToolbar.setIcon(n);
    }

    @Override
    public void setShowHideAnimationEnabled(boolean bl) {
        this.mShowHideAnimationEnabled = bl;
        if (!bl && this.mCurrentShowAnim != null) {
            this.mCurrentShowAnim.cancel();
        }
    }

    @Override
    public void setTitle(int n) {
        this.setTitle(this.mContext.getString(n));
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.mDecorToolbar.setTitle(charSequence);
    }

    @Override
    public void setWindowTitle(CharSequence charSequence) {
        this.mDecorToolbar.setWindowTitle(charSequence);
    }

    @Override
    public void show() {
        if (this.mHiddenByApp) {
            this.mHiddenByApp = false;
            this.updateVisibility(false);
        }
    }

    @Override
    public void showForSystem() {
        if (this.mHiddenBySystem) {
            this.mHiddenBySystem = false;
            this.updateVisibility(true);
        }
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback object) {
        if (this.mActionMode != null) {
            this.mActionMode.finish();
        }
        this.mOverlayLayout.setHideOnContentScrollEnabled(false);
        this.mContextView.killMode();
        object = new ActionModeImpl(this.mContextView.getContext(), (ActionMode.Callback)object);
        if (((ActionModeImpl)object).dispatchOnCreate()) {
            this.mActionMode = object;
            ((ActionModeImpl)object).invalidate();
            this.mContextView.initForMode((ActionMode)object);
            this.animateToMode(true);
            this.mContextView.sendAccessibilityEvent(32);
            return object;
        }
        return null;
    }

    public class ActionModeImpl
    extends ActionMode
    implements MenuBuilder.Callback {
        private final Context mActionModeContext;
        private ActionMode.Callback mCallback;
        private WeakReference<View> mCustomView;
        private final MenuBuilder mMenu;

        public ActionModeImpl(Context context, ActionMode.Callback callback) {
            this.mActionModeContext = context;
            this.mCallback = callback;
            this.mMenu = new MenuBuilder(context).setDefaultShowAsAction(1);
            this.mMenu.setCallback(this);
        }

        public boolean dispatchOnCreate() {
            this.mMenu.stopDispatchingItemsChanged();
            try {
                boolean bl = this.mCallback.onCreateActionMode(this, this.mMenu);
                return bl;
            }
            finally {
                this.mMenu.startDispatchingItemsChanged();
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void finish() {
            if (WindowDecorActionBar.this.mActionMode != this) {
                return;
            }
            if (!WindowDecorActionBar.checkShowingFlags(WindowDecorActionBar.this.mHiddenByApp, WindowDecorActionBar.this.mHiddenBySystem, false)) {
                WindowDecorActionBar.this.mDeferredDestroyActionMode = this;
                WindowDecorActionBar.this.mDeferredModeDestroyCallback = this.mCallback;
            } else {
                this.mCallback.onDestroyActionMode(this);
            }
            this.mCallback = null;
            WindowDecorActionBar.this.animateToMode(false);
            WindowDecorActionBar.this.mContextView.closeMode();
            WindowDecorActionBar.this.mDecorToolbar.getViewGroup().sendAccessibilityEvent(32);
            WindowDecorActionBar.this.mOverlayLayout.setHideOnContentScrollEnabled(WindowDecorActionBar.this.mHideOnContentScroll);
            WindowDecorActionBar.this.mActionMode = null;
        }

        @Override
        public View getCustomView() {
            if (this.mCustomView != null) {
                return (View)this.mCustomView.get();
            }
            return null;
        }

        @Override
        public Menu getMenu() {
            return this.mMenu;
        }

        @Override
        public MenuInflater getMenuInflater() {
            return new SupportMenuInflater(this.mActionModeContext);
        }

        @Override
        public CharSequence getSubtitle() {
            return WindowDecorActionBar.this.mContextView.getSubtitle();
        }

        @Override
        public CharSequence getTitle() {
            return WindowDecorActionBar.this.mContextView.getTitle();
        }

        @Override
        public void invalidate() {
            if (WindowDecorActionBar.this.mActionMode != this) {
                return;
            }
            this.mMenu.stopDispatchingItemsChanged();
            try {
                this.mCallback.onPrepareActionMode(this, this.mMenu);
                return;
            }
            finally {
                this.mMenu.startDispatchingItemsChanged();
            }
        }

        @Override
        public boolean isTitleOptional() {
            return WindowDecorActionBar.this.mContextView.isTitleOptional();
        }

        @Override
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            if (this.mCallback != null) {
                return this.mCallback.onActionItemClicked(this, menuItem);
            }
            return false;
        }

        @Override
        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (this.mCallback == null) {
                return;
            }
            this.invalidate();
            WindowDecorActionBar.this.mContextView.showOverflowMenu();
        }

        @Override
        public void setCustomView(View view) {
            WindowDecorActionBar.this.mContextView.setCustomView(view);
            this.mCustomView = new WeakReference<View>(view);
        }

        @Override
        public void setSubtitle(int n) {
            this.setSubtitle(WindowDecorActionBar.this.mContext.getResources().getString(n));
        }

        @Override
        public void setSubtitle(CharSequence charSequence) {
            WindowDecorActionBar.this.mContextView.setSubtitle(charSequence);
        }

        @Override
        public void setTitle(int n) {
            this.setTitle(WindowDecorActionBar.this.mContext.getResources().getString(n));
        }

        @Override
        public void setTitle(CharSequence charSequence) {
            WindowDecorActionBar.this.mContextView.setTitle(charSequence);
        }

        @Override
        public void setTitleOptionalHint(boolean bl) {
            super.setTitleOptionalHint(bl);
            WindowDecorActionBar.this.mContextView.setTitleOptional(bl);
        }
    }

}

