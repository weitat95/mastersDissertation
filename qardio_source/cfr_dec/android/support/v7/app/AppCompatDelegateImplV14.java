/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.Window
 *  android.view.Window$Callback
 */
package android.support.v7.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegateImplBase;
import android.support.v7.app.AppCompatDelegateImplV11;
import android.support.v7.app.ResourcesFlusher;
import android.support.v7.app.TwilightManager;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportActionModeWrapper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.Window;

class AppCompatDelegateImplV14
extends AppCompatDelegateImplV11 {
    private boolean mApplyDayNightCalled;
    private AutoNightModeManager mAutoNightModeManager;
    private boolean mHandleNativeActionModes = true;
    private int mLocalNightMode = -100;

    AppCompatDelegateImplV14(Context context, Window window, AppCompatCallback appCompatCallback) {
        super(context, window, appCompatCallback);
    }

    private void ensureAutoNightModeManager() {
        if (this.mAutoNightModeManager == null) {
            this.mAutoNightModeManager = new AutoNightModeManager(TwilightManager.getInstance(this.mContext));
        }
    }

    private int getNightMode() {
        if (this.mLocalNightMode != -100) {
            return this.mLocalNightMode;
        }
        return AppCompatDelegateImplV14.getDefaultNightMode();
    }

    private boolean shouldRecreateOnNightModeChange() {
        if (this.mApplyDayNightCalled && this.mContext instanceof Activity) {
            PackageManager packageManager = this.mContext.getPackageManager();
            try {
                int n = packageManager.getActivityInfo((ComponentName)new ComponentName((Context)this.mContext, this.mContext.getClass()), (int)0).configChanges;
                return (n & 0x200) == 0;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.d((String)"AppCompatDelegate", (String)"Exception while getting ActivityInfo", (Throwable)nameNotFoundException);
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean updateForNightMode(int n) {
        Resources resources = this.mContext.getResources();
        Configuration configuration = resources.getConfiguration();
        int n2 = configuration.uiMode;
        n = n == 2 ? 32 : 16;
        if ((n2 & 0x30) == n) return false;
        if (this.shouldRecreateOnNightModeChange()) {
            ((Activity)this.mContext).recreate();
            return true;
        }
        configuration = new Configuration(configuration);
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.uiMode = configuration.uiMode & 0xFFFFFFCF | n;
        resources.updateConfiguration(configuration, displayMetrics);
        if (Build.VERSION.SDK_INT >= 26) return true;
        ResourcesFlusher.flush(resources);
        return true;
    }

    @Override
    public boolean applyDayNight() {
        boolean bl = false;
        int n = this.getNightMode();
        int n2 = this.mapNightMode(n);
        if (n2 != -1) {
            bl = this.updateForNightMode(n2);
        }
        if (n == 0) {
            this.ensureAutoNightModeManager();
            this.mAutoNightModeManager.setup();
        }
        this.mApplyDayNightCalled = true;
        return bl;
    }

    @Override
    public boolean isHandleNativeActionModesEnabled() {
        return this.mHandleNativeActionModes;
    }

    int mapNightMode(int n) {
        switch (n) {
            default: {
                return n;
            }
            case 0: {
                this.ensureAutoNightModeManager();
                return this.mAutoNightModeManager.getApplyableNightMode();
            }
            case -100: 
        }
        return -1;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null && this.mLocalNightMode == -100) {
            this.mLocalNightMode = bundle.getInt("appcompat:local_night_mode", -100);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mAutoNightModeManager != null) {
            this.mAutoNightModeManager.cleanup();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.mLocalNightMode != -100) {
            bundle.putInt("appcompat:local_night_mode", this.mLocalNightMode);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        this.applyDayNight();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (this.mAutoNightModeManager != null) {
            this.mAutoNightModeManager.cleanup();
        }
    }

    @Override
    Window.Callback wrapWindowCallback(Window.Callback callback) {
        return new AppCompatWindowCallbackV14(callback);
    }

    class AppCompatWindowCallbackV14
    extends AppCompatDelegateImplBase.AppCompatWindowCallbackBase {
        AppCompatWindowCallbackV14(Window.Callback callback) {
            super(callback);
        }

        @Override
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            if (AppCompatDelegateImplV14.this.isHandleNativeActionModesEnabled()) {
                return this.startAsSupportActionMode(callback);
            }
            return super.onWindowStartingActionMode(callback);
        }

        final android.view.ActionMode startAsSupportActionMode(ActionMode.Callback object) {
            ActionMode actionMode = AppCompatDelegateImplV14.this.startSupportActionMode((ActionMode.Callback)(object = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImplV14.this.mContext, (ActionMode.Callback)object)));
            if (actionMode != null) {
                return ((SupportActionModeWrapper.CallbackWrapper)object).getActionModeWrapper(actionMode);
            }
            return null;
        }
    }

    final class AutoNightModeManager {
        private BroadcastReceiver mAutoTimeChangeReceiver;
        private IntentFilter mAutoTimeChangeReceiverFilter;
        private boolean mIsNight;
        private TwilightManager mTwilightManager;

        AutoNightModeManager(TwilightManager twilightManager) {
            this.mTwilightManager = twilightManager;
            this.mIsNight = twilightManager.isNight();
        }

        final void cleanup() {
            if (this.mAutoTimeChangeReceiver != null) {
                AppCompatDelegateImplV14.this.mContext.unregisterReceiver(this.mAutoTimeChangeReceiver);
                this.mAutoTimeChangeReceiver = null;
            }
        }

        final void dispatchTimeChanged() {
            boolean bl = this.mTwilightManager.isNight();
            if (bl != this.mIsNight) {
                this.mIsNight = bl;
                AppCompatDelegateImplV14.this.applyDayNight();
            }
        }

        final int getApplyableNightMode() {
            this.mIsNight = this.mTwilightManager.isNight();
            if (this.mIsNight) {
                return 2;
            }
            return 1;
        }

        final void setup() {
            this.cleanup();
            if (this.mAutoTimeChangeReceiver == null) {
                this.mAutoTimeChangeReceiver = new BroadcastReceiver(){

                    public void onReceive(Context context, Intent intent) {
                        AutoNightModeManager.this.dispatchTimeChanged();
                    }
                };
            }
            if (this.mAutoTimeChangeReceiverFilter == null) {
                this.mAutoTimeChangeReceiverFilter = new IntentFilter();
                this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIME_SET");
                this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
                this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIME_TICK");
            }
            AppCompatDelegateImplV14.this.mContext.registerReceiver(this.mAutoTimeChangeReceiver, this.mAutoTimeChangeReceiverFilter);
        }

    }

}

