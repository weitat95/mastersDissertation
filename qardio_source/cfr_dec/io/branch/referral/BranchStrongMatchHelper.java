/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.text.TextUtils
 *  android.util.Log
 */
package io.branch.referral;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import io.branch.referral.BranchUtil;
import io.branch.referral.Defines;
import io.branch.referral.DeviceInfo;
import io.branch.referral.PrefHelper;
import io.branch.referral.SystemObserver;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

class BranchStrongMatchHelper {
    private static int StrongMatchUrlHitDelay = 750;
    private static BranchStrongMatchHelper branchStrongMatchHelper_;
    Class<?> CustomServiceTabConnectionClass;
    Class<?> CustomTabsCallbackClass;
    Class<?> CustomTabsClientClass;
    Class<?> CustomTabsSessionClass;
    Class<?> ICustomTabsServiceClass;
    private boolean isCustomTabsAvailable_ = true;
    boolean isStrongMatchUrlLaunched = false;
    Object mClient_ = null;
    private final Handler timeOutHandler_;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private BranchStrongMatchHelper() {
        try {
            this.CustomTabsClientClass = Class.forName("android.support.customtabs.CustomTabsClient");
            this.CustomServiceTabConnectionClass = Class.forName("android.support.customtabs.CustomTabsServiceConnection");
            this.CustomTabsCallbackClass = Class.forName("android.support.customtabs.CustomTabsCallback");
            this.CustomTabsSessionClass = Class.forName("android.support.customtabs.CustomTabsSession");
            this.ICustomTabsServiceClass = Class.forName("android.support.customtabs.ICustomTabsService");
        }
        catch (Throwable throwable) {
            this.isCustomTabsAvailable_ = false;
        }
        this.timeOutHandler_ = new Handler();
    }

    /*
     * Enabled aggressive block sorting
     */
    private Uri buildStrongMatchUrl(String object, DeviceInfo object2, PrefHelper prefHelper, SystemObserver object3, Context context) {
        String string2 = null;
        if (TextUtils.isEmpty((CharSequence)object)) return string2;
        object = "https://" + (String)object + "/_strong_match?os=" + ((DeviceInfo)object2).getOsName();
        string2 = (String)object + "&" + Defines.Jsonkey.HardwareID.getKey() + "=" + ((DeviceInfo)object2).getHardwareID();
        object = ((DeviceInfo)object2).isHardwareIDReal() ? Defines.Jsonkey.HardwareIDTypeVendor.getKey() : Defines.Jsonkey.HardwareIDTypeRandom.getKey();
        string2 = string2 + "&" + Defines.Jsonkey.HardwareIDType.getKey() + "=" + (String)object;
        object = string2;
        if (((SystemObserver)object3).GAIDString_ != null) {
            object = string2;
            if (!BranchUtil.isTestModeEnabled(context)) {
                object = string2 + "&" + Defines.Jsonkey.GoogleAdvertisingID.getKey() + "=" + ((SystemObserver)object3).GAIDString_;
            }
        }
        object3 = object;
        if (!prefHelper.getDeviceFingerPrintID().equals("bnc_no_value")) {
            object3 = (String)object + "&" + Defines.Jsonkey.DeviceFingerprintID.getKey() + "=" + prefHelper.getDeviceFingerPrintID();
        }
        object = object3;
        if (!((DeviceInfo)object2).getAppVersion().equals("bnc_no_value")) {
            object = (String)object3 + "&" + Defines.Jsonkey.AppVersion.getKey() + "=" + ((DeviceInfo)object2).getAppVersion();
        }
        object2 = object;
        if (prefHelper.getBranchKey().equals("bnc_no_value")) return Uri.parse((String)((String)object2 + "&sdk=android2.12.1"));
        object2 = (String)object + "&" + Defines.Jsonkey.BranchKey.getKey() + "=" + prefHelper.getBranchKey();
        return Uri.parse((String)((String)object2 + "&sdk=android2.12.1"));
    }

    public static BranchStrongMatchHelper getInstance() {
        if (branchStrongMatchHelper_ == null) {
            branchStrongMatchHelper_ = new BranchStrongMatchHelper();
        }
        return branchStrongMatchHelper_;
    }

    private void updateStrongMatchCheckFinished(final StrongMatchCheckEvents strongMatchCheckEvents, boolean bl) {
        block3: {
            block2: {
                if (strongMatchCheckEvents == null) break block2;
                if (!bl) break block3;
                new Handler().postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        strongMatchCheckEvents.onStrongMatchCheckFinished();
                    }
                }, (long)StrongMatchUrlHitDelay);
            }
            return;
        }
        strongMatchCheckEvents.onStrongMatchCheckFinished();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void checkForStrongMatch(Context context, String string2, DeviceInfo object, PrefHelper prefHelper, SystemObserver object2, final StrongMatchCheckEvents strongMatchCheckEvents) {
        this.isStrongMatchUrlLaunched = false;
        if (System.currentTimeMillis() - prefHelper.getLastStrongMatchTime() < 2592000000L) {
            this.updateStrongMatchCheckFinished(strongMatchCheckEvents, this.isStrongMatchUrlLaunched);
            return;
        }
        if (!this.isCustomTabsAvailable_) {
            this.updateStrongMatchCheckFinished(strongMatchCheckEvents, this.isStrongMatchUrlLaunched);
            return;
        }
        try {
            if (((DeviceInfo)object).getHardwareID() != null) {
                if ((string2 = this.buildStrongMatchUrl(string2, (DeviceInfo)object, prefHelper, (SystemObserver)object2, context)) != null) {
                    this.timeOutHandler_.postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            BranchStrongMatchHelper.this.updateStrongMatchCheckFinished(strongMatchCheckEvents, BranchStrongMatchHelper.this.isStrongMatchUrlLaunched);
                        }
                    }, 500L);
                    this.CustomTabsClientClass.getMethod("bindCustomTabsService", Context.class, String.class, this.CustomServiceTabConnectionClass);
                    object = this.CustomTabsClientClass.getMethod("warmup", Long.TYPE);
                    object2 = this.CustomTabsClientClass.getMethod("newSession", this.CustomTabsCallbackClass);
                    Method method = this.CustomTabsSessionClass.getMethod("mayLaunchUrl", Uri.class, Bundle.class, List.class);
                    Intent intent = new Intent("android.support.customtabs.action.CustomTabsService");
                    intent.setPackage("com.android.chrome");
                    context.bindService(intent, (ServiceConnection)new MockCustomTabServiceConnection((Method)object, (Method)object2, (Uri)string2, method, prefHelper, strongMatchCheckEvents){
                        final /* synthetic */ StrongMatchCheckEvents val$callback;
                        final /* synthetic */ Method val$mayLaunchUrlMethod;
                        final /* synthetic */ Method val$newSessionMethod;
                        final /* synthetic */ PrefHelper val$prefHelper;
                        final /* synthetic */ Uri val$strongMatchUri;
                        final /* synthetic */ Method val$warmupMethod;
                        {
                            this.val$warmupMethod = method;
                            this.val$newSessionMethod = method2;
                            this.val$strongMatchUri = uri;
                            this.val$mayLaunchUrlMethod = method3;
                            this.val$prefHelper = prefHelper;
                            this.val$callback = strongMatchCheckEvents;
                        }

                        /*
                         * WARNING - void declaration
                         * Enabled force condition propagation
                         * Lifted jumps to return sites
                         */
                        @Override
                        public void onCustomTabsServiceConnected(ComponentName object, Object object2) {
                            Object object3;
                            void var2_4;
                            BranchStrongMatchHelper.this.mClient_ = BranchStrongMatchHelper.this.CustomTabsClientClass.cast(var2_4);
                            if (BranchStrongMatchHelper.this.mClient_ == null) return;
                            try {
                                this.val$warmupMethod.invoke(BranchStrongMatchHelper.this.mClient_, 0);
                                object3 = this.val$newSessionMethod.invoke(BranchStrongMatchHelper.this.mClient_, new Object[]{null});
                                if (object3 == null) return;
                            }
                            catch (Throwable throwable) {
                                BranchStrongMatchHelper.this.mClient_ = null;
                                BranchStrongMatchHelper.this.updateStrongMatchCheckFinished(this.val$callback, BranchStrongMatchHelper.this.isStrongMatchUrlLaunched);
                                return;
                            }
                            PrefHelper.Debug("BranchSDK", "Strong match request " + (Object)this.val$strongMatchUri);
                            this.val$mayLaunchUrlMethod.invoke(object3, new Object[]{this.val$strongMatchUri, null, null});
                            this.val$prefHelper.saveLastStrongMatchTime(System.currentTimeMillis());
                            BranchStrongMatchHelper.this.isStrongMatchUrlLaunched = true;
                        }

                        public void onServiceDisconnected(ComponentName componentName) {
                            BranchStrongMatchHelper.this.mClient_ = null;
                            BranchStrongMatchHelper.this.updateStrongMatchCheckFinished(this.val$callback, BranchStrongMatchHelper.this.isStrongMatchUrlLaunched);
                        }
                    }, 33);
                    return;
                }
                this.updateStrongMatchCheckFinished(strongMatchCheckEvents, this.isStrongMatchUrlLaunched);
                return;
            }
        }
        catch (Throwable throwable) {
            this.updateStrongMatchCheckFinished(strongMatchCheckEvents, this.isStrongMatchUrlLaunched);
            return;
        }
        this.updateStrongMatchCheckFinished(strongMatchCheckEvents, this.isStrongMatchUrlLaunched);
        Log.d((String)"BranchSDK", (String)"Cannot use cookie-based matching since device id is not available");
    }

    private abstract class MockCustomTabServiceConnection
    implements ServiceConnection {
        public abstract void onCustomTabsServiceConnected(ComponentName var1, Object var2);

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                Constructor<?> constructor = BranchStrongMatchHelper.this.CustomTabsClientClass.getDeclaredConstructor(BranchStrongMatchHelper.this.ICustomTabsServiceClass, ComponentName.class);
                constructor.setAccessible(true);
                this.onCustomTabsServiceConnected(componentName, constructor.newInstance(new Object[]{Class.forName("android.support.customtabs.ICustomTabsService$Stub").getMethod("asInterface", IBinder.class).invoke(null, new Object[]{iBinder}), componentName}));
                return;
            }
            catch (Throwable throwable) {
                this.onCustomTabsServiceConnected(null, null);
                return;
            }
        }
    }

    static interface StrongMatchCheckEvents {
        public void onStrongMatchCheckFinished();
    }

}

