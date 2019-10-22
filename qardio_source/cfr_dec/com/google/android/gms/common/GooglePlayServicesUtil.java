/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.res.Resources
 */
package com.google.android.gms.common;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.common.zzf;
import com.google.android.gms.common.zzp;

public final class GooglePlayServicesUtil
extends zzp {
    public static final String GMS_ERROR_DIALOG = "GooglePlayServicesErrorDialog";
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = zzp.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    private GooglePlayServicesUtil() {
    }

    @Deprecated
    public static Dialog getErrorDialog(int n, Activity activity, int n2) {
        return GooglePlayServicesUtil.getErrorDialog(n, activity, n2, null);
    }

    @Deprecated
    public static Dialog getErrorDialog(int n, Activity activity, int n2, DialogInterface.OnCancelListener onCancelListener) {
        int n3 = n;
        if (zzp.zze((Context)activity, n)) {
            n3 = 18;
        }
        return GoogleApiAvailability.getInstance().getErrorDialog(activity, n3, n2, onCancelListener);
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int n, Context context, int n2) {
        return zzp.getErrorPendingIntent(n, context, n2);
    }

    @Deprecated
    public static String getErrorString(int n) {
        return zzp.getErrorString(n);
    }

    public static Context getRemoteContext(Context context) {
        return zzp.getRemoteContext(context);
    }

    public static Resources getRemoteResource(Context context) {
        return zzp.getRemoteResource(context);
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        return zzp.isGooglePlayServicesAvailable(context);
    }

    @Deprecated
    public static boolean isUserRecoverableError(int n) {
        return zzp.isUserRecoverableError(n);
    }

    @Deprecated
    public static boolean showErrorDialogFragment(int n, Activity activity, int n2) {
        return GooglePlayServicesUtil.showErrorDialogFragment(n, activity, n2, null);
    }

    @Deprecated
    public static boolean showErrorDialogFragment(int n, Activity activity, int n2, DialogInterface.OnCancelListener onCancelListener) {
        return GooglePlayServicesUtil.showErrorDialogFragment(n, activity, null, n2, onCancelListener);
    }

    public static boolean showErrorDialogFragment(int n, Activity activity, Fragment fragment, int n2, DialogInterface.OnCancelListener onCancelListener) {
        int n3 = n;
        if (zzp.zze((Context)activity, n)) {
            n3 = 18;
        }
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        if (fragment == null) {
            return googleApiAvailability.showErrorDialogFragment(activity, n3, n2, onCancelListener);
        }
        GoogleApiAvailability.getInstance();
        fragment = GoogleApiAvailability.zza((Context)activity, n3, zzv.zza(fragment, zzf.zza((Context)activity, n3, "d"), n2), onCancelListener);
        if (fragment == null) {
            return false;
        }
        GoogleApiAvailability.zza(activity, (Dialog)fragment, GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    public static void showErrorNotification(int n, Context context) {
        boolean bl;
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        if (!zzp.zze(context, n) && !(bl = n == 9 ? zzp.zzv(context, GOOGLE_PLAY_STORE_PACKAGE) : false)) {
            googleApiAvailability.showErrorNotification(context, n);
            return;
        }
        googleApiAvailability.zzcc(context);
    }
}

