/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.ActionProvider
 *  android.view.ActionProvider$VisibilityListener
 *  android.view.MenuItem
 *  android.view.View
 */
package android.support.v7.view.menu;

import android.content.Context;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.support.v7.view.menu.MenuItemWrapperICS;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;

class MenuItemWrapperJB
extends MenuItemWrapperICS {
    MenuItemWrapperJB(Context context, SupportMenuItem supportMenuItem) {
        super(context, supportMenuItem);
    }

    @Override
    MenuItemWrapperICS.ActionProviderWrapper createActionProviderWrapper(android.view.ActionProvider actionProvider) {
        return new ActionProviderWrapperJB(this.mContext, actionProvider);
    }

    class ActionProviderWrapperJB
    extends MenuItemWrapperICS.ActionProviderWrapper
    implements ActionProvider.VisibilityListener {
        ActionProvider.VisibilityListener mListener;

        public ActionProviderWrapperJB(Context context, android.view.ActionProvider actionProvider) {
            super(context, actionProvider);
        }

        @Override
        public boolean isVisible() {
            return this.mInner.isVisible();
        }

        public void onActionProviderVisibilityChanged(boolean bl) {
            if (this.mListener != null) {
                this.mListener.onActionProviderVisibilityChanged(bl);
            }
        }

        @Override
        public View onCreateActionView(MenuItem menuItem) {
            return this.mInner.onCreateActionView(menuItem);
        }

        @Override
        public boolean overridesItemVisibility() {
            return this.mInner.overridesItemVisibility();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void setVisibilityListener(ActionProvider.VisibilityListener object) {
            this.mListener = object;
            android.view.ActionProvider actionProvider = this.mInner;
            object = object != null ? this : null;
            actionProvider.setVisibilityListener((ActionProvider.VisibilityListener)object);
        }
    }

}

