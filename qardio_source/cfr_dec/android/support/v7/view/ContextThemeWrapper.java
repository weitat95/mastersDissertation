/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.AssetManager
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.LayoutInflater
 */
package android.support.v7.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.view.LayoutInflater;

public class ContextThemeWrapper
extends ContextWrapper {
    private LayoutInflater mInflater;
    private Configuration mOverrideConfiguration;
    private Resources mResources;
    private Resources.Theme mTheme;
    private int mThemeResource;

    public ContextThemeWrapper() {
        super(null);
    }

    public ContextThemeWrapper(Context context, int n) {
        super(context);
        this.mThemeResource = n;
    }

    public ContextThemeWrapper(Context context, Resources.Theme theme) {
        super(context);
        this.mTheme = theme;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Resources getResourcesInternal() {
        if (this.mResources != null) return this.mResources;
        if (this.mOverrideConfiguration == null) {
            this.mResources = super.getResources();
            return this.mResources;
        }
        if (Build.VERSION.SDK_INT < 17) return this.mResources;
        this.mResources = this.createConfigurationContext(this.mOverrideConfiguration).getResources();
        return this.mResources;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void initializeTheme() {
        boolean bl = this.mTheme == null;
        if (bl) {
            this.mTheme = this.getResources().newTheme();
            Resources.Theme theme = this.getBaseContext().getTheme();
            if (theme != null) {
                this.mTheme.setTo(theme);
            }
        }
        this.onApplyThemeResource(this.mTheme, this.mThemeResource, bl);
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public AssetManager getAssets() {
        return this.getResources().getAssets();
    }

    public Resources getResources() {
        return this.getResourcesInternal();
    }

    public Object getSystemService(String string2) {
        if ("layout_inflater".equals(string2)) {
            if (this.mInflater == null) {
                this.mInflater = LayoutInflater.from((Context)this.getBaseContext()).cloneInContext((Context)this);
            }
            return this.mInflater;
        }
        return this.getBaseContext().getSystemService(string2);
    }

    public Resources.Theme getTheme() {
        if (this.mTheme != null) {
            return this.mTheme;
        }
        if (this.mThemeResource == 0) {
            this.mThemeResource = R.style.Theme_AppCompat_Light;
        }
        this.initializeTheme();
        return this.mTheme;
    }

    public int getThemeResId() {
        return this.mThemeResource;
    }

    protected void onApplyThemeResource(Resources.Theme theme, int n, boolean bl) {
        theme.applyStyle(n, true);
    }

    public void setTheme(int n) {
        if (this.mThemeResource != n) {
            this.mThemeResource = n;
            this.initializeTheme();
        }
    }
}

