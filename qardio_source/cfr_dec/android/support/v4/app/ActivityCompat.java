/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.SharedElementCallback
 *  android.app.SharedElementCallback$OnSharedElementsReadyListener
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.content.pm.PackageManager
 *  android.graphics.Matrix
 *  android.graphics.RectF
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.view.View
 */
package android.support.v4.app;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.content.ContextCompat;
import android.view.View;
import java.util.List;
import java.util.Map;

public class ActivityCompat
extends ContextCompat {
    protected ActivityCompat() {
    }

    public static void finishAffinity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.finishAffinity();
            return;
        }
        activity.finish();
    }

    public static void finishAfterTransition(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.finishAfterTransition();
            return;
        }
        activity.finish();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static Uri getReferrer(Activity object) {
        Uri uri;
        void var0_2;
        if (Build.VERSION.SDK_INT >= 22) {
            Uri uri2 = object.getReferrer();
            return var0_2;
        }
        Intent intent = object.getIntent();
        Uri uri3 = uri = (Uri)intent.getParcelableExtra("android.intent.extra.REFERRER");
        if (uri != null) return var0_2;
        {
            String string2 = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
            if (string2 == null) return null;
            return Uri.parse((String)string2);
        }
    }

    public static boolean invalidateOptionsMenu(Activity activity) {
        activity.invalidateOptionsMenu();
        return true;
    }

    public static void postponeEnterTransition(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.postponeEnterTransition();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void requestPermissions(final Activity activity, final String[] arrstring, final int n) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity instanceof RequestPermissionsRequestCodeValidator) {
                ((RequestPermissionsRequestCodeValidator)activity).validateRequestPermissionsRequestCode(n);
            }
            activity.requestPermissions(arrstring, n);
            return;
        } else {
            if (!(activity instanceof OnRequestPermissionsResultCallback)) return;
            {
                new Handler(Looper.getMainLooper()).post(new Runnable(){

                    @Override
                    public void run() {
                        int[] arrn = new int[arrstring.length];
                        PackageManager packageManager = activity.getPackageManager();
                        String string2 = activity.getPackageName();
                        int n2 = arrstring.length;
                        for (int i = 0; i < n2; ++i) {
                            arrn[i] = packageManager.checkPermission(arrstring[i], string2);
                        }
                        ((OnRequestPermissionsResultCallback)activity).onRequestPermissionsResult(n, arrstring, arrn);
                    }
                });
                return;
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void setEnterSharedElementCallback(Activity activity, SharedElementCallback sharedElementCallback) {
        Object var3_2 = null;
        Object var2_3 = null;
        if (Build.VERSION.SDK_INT >= 23) {
            void var2_5;
            if (sharedElementCallback != null) {
                SharedElementCallback23Impl sharedElementCallback23Impl = new SharedElementCallback23Impl(sharedElementCallback);
            }
            activity.setEnterSharedElementCallback((android.app.SharedElementCallback)var2_5);
            return;
        } else {
            if (Build.VERSION.SDK_INT < 21) return;
            {
                void var2_8;
                Object var2_6 = var3_2;
                if (sharedElementCallback != null) {
                    SharedElementCallback21Impl sharedElementCallback21Impl = new SharedElementCallback21Impl(sharedElementCallback);
                }
                activity.setEnterSharedElementCallback((android.app.SharedElementCallback)var2_8);
                return;
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void setExitSharedElementCallback(Activity activity, SharedElementCallback sharedElementCallback) {
        Object var3_2 = null;
        Object var2_3 = null;
        if (Build.VERSION.SDK_INT >= 23) {
            void var2_5;
            if (sharedElementCallback != null) {
                SharedElementCallback23Impl sharedElementCallback23Impl = new SharedElementCallback23Impl(sharedElementCallback);
            }
            activity.setExitSharedElementCallback((android.app.SharedElementCallback)var2_5);
            return;
        } else {
            if (Build.VERSION.SDK_INT < 21) return;
            {
                void var2_8;
                Object var2_6 = var3_2;
                if (sharedElementCallback != null) {
                    SharedElementCallback21Impl sharedElementCallback21Impl = new SharedElementCallback21Impl(sharedElementCallback);
                }
                activity.setExitSharedElementCallback((android.app.SharedElementCallback)var2_8);
                return;
            }
        }
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String string2) {
        if (Build.VERSION.SDK_INT >= 23) {
            return activity.shouldShowRequestPermissionRationale(string2);
        }
        return false;
    }

    public static void startActivityForResult(Activity activity, Intent intent, int n, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.startActivityForResult(intent, n, bundle);
            return;
        }
        activity.startActivityForResult(intent, n);
    }

    public static void startIntentSenderForResult(Activity activity, IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.startIntentSenderForResult(intentSender, n, intent, n2, n3, n4, bundle);
            return;
        }
        activity.startIntentSenderForResult(intentSender, n, intent, n2, n3, n4);
    }

    public static void startPostponedEnterTransition(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.startPostponedEnterTransition();
        }
    }

    public static interface OnRequestPermissionsResultCallback {
        public void onRequestPermissionsResult(int var1, String[] var2, int[] var3);
    }

    public static interface RequestPermissionsRequestCodeValidator {
        public void validateRequestPermissionsRequestCode(int var1);
    }

    private static class SharedElementCallback21Impl
    extends android.app.SharedElementCallback {
        protected SharedElementCallback mCallback;

        public SharedElementCallback21Impl(SharedElementCallback sharedElementCallback) {
            this.mCallback = sharedElementCallback;
        }

        public Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF) {
            return this.mCallback.onCaptureSharedElementSnapshot(view, matrix, rectF);
        }

        public View onCreateSnapshotView(Context context, Parcelable parcelable) {
            return this.mCallback.onCreateSnapshotView(context, parcelable);
        }

        public void onMapSharedElements(List<String> list, Map<String, View> map) {
            this.mCallback.onMapSharedElements(list, map);
        }

        public void onRejectSharedElements(List<View> list) {
            this.mCallback.onRejectSharedElements(list);
        }

        public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
            this.mCallback.onSharedElementEnd(list, list2, list3);
        }

        public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
            this.mCallback.onSharedElementStart(list, list2, list3);
        }
    }

    private static class SharedElementCallback23Impl
    extends SharedElementCallback21Impl {
        public SharedElementCallback23Impl(SharedElementCallback sharedElementCallback) {
            super(sharedElementCallback);
        }

        public void onSharedElementsArrived(List<String> list, List<View> list2, final SharedElementCallback.OnSharedElementsReadyListener onSharedElementsReadyListener) {
            this.mCallback.onSharedElementsArrived(list, list2, new SharedElementCallback.OnSharedElementsReadyListener(){

                @Override
                public void onSharedElementsReady() {
                    onSharedElementsReadyListener.onSharedElementsReady();
                }
            });
        }

    }

}

