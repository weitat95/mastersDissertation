/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 *  android.view.MenuItem
 *  android.view.SubMenu
 *  android.view.View
 */
package android.support.v4.view;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public abstract class ActionProvider {
    private final Context mContext;
    private SubUiVisibilityListener mSubUiVisibilityListener;
    private VisibilityListener mVisibilityListener;

    public ActionProvider(Context context) {
        this.mContext = context;
    }

    public boolean hasSubMenu() {
        return false;
    }

    public boolean isVisible() {
        return true;
    }

    public abstract View onCreateActionView();

    public View onCreateActionView(MenuItem menuItem) {
        return this.onCreateActionView();
    }

    public boolean onPerformDefaultAction() {
        return false;
    }

    public void onPrepareSubMenu(SubMenu subMenu) {
    }

    public boolean overridesItemVisibility() {
        return false;
    }

    public void reset() {
        this.mVisibilityListener = null;
        this.mSubUiVisibilityListener = null;
    }

    public void setSubUiVisibilityListener(SubUiVisibilityListener subUiVisibilityListener) {
        this.mSubUiVisibilityListener = subUiVisibilityListener;
    }

    public void setVisibilityListener(VisibilityListener visibilityListener) {
        if (this.mVisibilityListener != null && visibilityListener != null) {
            Log.w((String)"ActionProvider(support)", (String)("setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this " + this.getClass().getSimpleName() + " instance while it is still in use somewhere else?"));
        }
        this.mVisibilityListener = visibilityListener;
    }

    public void subUiVisibilityChanged(boolean bl) {
        if (this.mSubUiVisibilityListener != null) {
            this.mSubUiVisibilityListener.onSubUiVisibilityChanged(bl);
        }
    }

    public static interface SubUiVisibilityListener {
        public void onSubUiVisibilityChanged(boolean var1);
    }

    public static interface VisibilityListener {
        public void onActionProviderVisibilityChanged(boolean var1);
    }

}

