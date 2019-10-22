/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.MenuInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 */
package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegateImplN;
import android.support.v7.app.AppCompatDelegateImplV11;
import android.support.v7.app.AppCompatDelegateImplV14;
import android.support.v7.app.AppCompatDelegateImplV23;
import android.support.v7.app.AppCompatDelegateImplV9;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public abstract class AppCompatDelegate {
    private static boolean sCompatVectorFromResourcesEnabled;
    private static int sDefaultNightMode;

    static {
        sDefaultNightMode = -1;
        sCompatVectorFromResourcesEnabled = false;
    }

    AppCompatDelegate() {
    }

    public static AppCompatDelegate create(Activity activity, AppCompatCallback appCompatCallback) {
        return AppCompatDelegate.create((Context)activity, activity.getWindow(), appCompatCallback);
    }

    public static AppCompatDelegate create(Dialog dialog, AppCompatCallback appCompatCallback) {
        return AppCompatDelegate.create(dialog.getContext(), dialog.getWindow(), appCompatCallback);
    }

    private static AppCompatDelegate create(Context context, Window window, AppCompatCallback appCompatCallback) {
        if (Build.VERSION.SDK_INT >= 24) {
            return new AppCompatDelegateImplN(context, window, appCompatCallback);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            return new AppCompatDelegateImplV23(context, window, appCompatCallback);
        }
        if (Build.VERSION.SDK_INT >= 14) {
            return new AppCompatDelegateImplV14(context, window, appCompatCallback);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            return new AppCompatDelegateImplV11(context, window, appCompatCallback);
        }
        return new AppCompatDelegateImplV9(context, window, appCompatCallback);
    }

    public static int getDefaultNightMode() {
        return sDefaultNightMode;
    }

    public static boolean isCompatVectorFromResourcesEnabled() {
        return sCompatVectorFromResourcesEnabled;
    }

    public static void setCompatVectorFromResourcesEnabled(boolean bl) {
        sCompatVectorFromResourcesEnabled = bl;
    }

    public abstract void addContentView(View var1, ViewGroup.LayoutParams var2);

    public abstract boolean applyDayNight();

    public abstract <T extends View> T findViewById(int var1);

    public abstract ActionBarDrawerToggle.Delegate getDrawerToggleDelegate();

    public abstract MenuInflater getMenuInflater();

    public abstract ActionBar getSupportActionBar();

    public abstract void installViewFactory();

    public abstract void invalidateOptionsMenu();

    public abstract void onConfigurationChanged(Configuration var1);

    public abstract void onCreate(Bundle var1);

    public abstract void onDestroy();

    public abstract void onPostCreate(Bundle var1);

    public abstract void onPostResume();

    public abstract void onSaveInstanceState(Bundle var1);

    public abstract void onStart();

    public abstract void onStop();

    public abstract boolean requestWindowFeature(int var1);

    public abstract void setContentView(int var1);

    public abstract void setContentView(View var1);

    public abstract void setContentView(View var1, ViewGroup.LayoutParams var2);

    public abstract void setSupportActionBar(Toolbar var1);

    public abstract void setTitle(CharSequence var1);

    public abstract ActionMode startSupportActionMode(ActionMode.Callback var1);
}

