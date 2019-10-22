/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.app.FragmentManager
 *  android.app.NotificationChannel
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.View
 *  android.widget.ProgressBar
 */
package com.google.android.gms.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.SupportErrorDialogFragment;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.internal.zzbx;
import com.google.android.gms.common.api.internal.zzby;
import com.google.android.gms.common.api.internal.zzcf;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.common.zzf;
import com.google.android.gms.common.zzp;

public class GoogleApiAvailability
extends zzf {
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final Object mLock;
    private static final GoogleApiAvailability zzfku;
    private String zzfkv;

    static {
        mLock = new Object();
        zzfku = new GoogleApiAvailability();
        GOOGLE_PLAY_SERVICES_VERSION_CODE = zzf.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    GoogleApiAvailability() {
    }

    public static GoogleApiAvailability getInstance() {
        return zzfku;
    }

    public static Dialog zza(Activity activity, DialogInterface.OnCancelListener onCancelListener) {
        ProgressBar progressBar = new ProgressBar((Context)activity, null, 16842874);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(0);
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)activity);
        builder.setView((View)progressBar);
        builder.setMessage((CharSequence)zzu.zzi((Context)activity, 18));
        builder.setPositiveButton((CharSequence)"", null);
        progressBar = builder.create();
        GoogleApiAvailability.zza(activity, (Dialog)progressBar, "GooglePlayServicesUpdatingDialog", onCancelListener);
        return progressBar;
    }

    static Dialog zza(Context object, int n, zzv zzv2, DialogInterface.OnCancelListener object2) {
        AlertDialog.Builder builder = null;
        if (n == 0) {
            return null;
        }
        TypedValue typedValue = new TypedValue();
        object.getTheme().resolveAttribute(16843529, typedValue, true);
        if ("Theme.Dialog.Alert".equals(object.getResources().getResourceEntryName(typedValue.resourceId))) {
            builder = new AlertDialog.Builder(object, 5);
        }
        typedValue = builder;
        if (builder == null) {
            typedValue = new AlertDialog.Builder(object);
        }
        typedValue.setMessage((CharSequence)zzu.zzi(object, n));
        if (object2 != null) {
            typedValue.setOnCancelListener(object2);
        }
        if ((object2 = zzu.zzk(object, n)) != null) {
            typedValue.setPositiveButton((CharSequence)object2, (DialogInterface.OnClickListener)zzv2);
        }
        if ((object = zzu.zzg(object, n)) != null) {
            typedValue.setTitle((CharSequence)object);
        }
        return typedValue.create();
    }

    public static zzbx zza(Context context, zzby zzby2) {
        Object object = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        object.addDataScheme("package");
        zzbx zzbx2 = new zzbx(zzby2);
        context.registerReceiver((BroadcastReceiver)zzbx2, object);
        zzbx2.setContext(context);
        object = zzbx2;
        if (!zzp.zzv(context, "com.google.android.gms")) {
            zzby2.zzahg();
            zzbx2.unregister();
            object = null;
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=26)
    private final String zza(Context object, NotificationManager notificationManager) {
        String string2;
        zzbq.checkState(zzq.isAtLeastO());
        String string3 = string2 = this.zzafx();
        if (string2 != null) return string3;
        {
            string3 = "com.google.android.gms.availability";
            string2 = notificationManager.getNotificationChannel("com.google.android.gms.availability");
            object = zzu.zzcn((Context)object);
            if (string2 == null) {
                notificationManager.createNotificationChannel(new NotificationChannel("com.google.android.gms.availability", (CharSequence)object, 4));
                return string3;
            } else {
                if (((String)object).equals(string2.getName())) return string3;
                {
                    string2.setName((CharSequence)object);
                    notificationManager.createNotificationChannel((NotificationChannel)string2);
                    return "com.google.android.gms.availability";
                }
            }
        }
    }

    static void zza(Activity object, Dialog dialog, String string2, DialogInterface.OnCancelListener onCancelListener) {
        if (object instanceof FragmentActivity) {
            object = ((FragmentActivity)object).getSupportFragmentManager();
            SupportErrorDialogFragment.newInstance(dialog, onCancelListener).show((FragmentManager)object, string2);
            return;
        }
        object = object.getFragmentManager();
        ErrorDialogFragment.newInstance(dialog, onCancelListener).show((android.app.FragmentManager)object, string2);
    }

    /*
     * Exception decompiling
     */
    @TargetApi(value=20)
    private final void zza(Context var1_1, int var2_2, String var3_3, PendingIntent var4_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous, and can't clone.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:509)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final String zzafx() {
        Object object = mLock;
        synchronized (object) {
            return this.zzfkv;
        }
    }

    public Dialog getErrorDialog(Activity activity, int n, int n2, DialogInterface.OnCancelListener onCancelListener) {
        return GoogleApiAvailability.zza((Context)activity, n, zzv.zza(activity, zzf.zza((Context)activity, n, "d"), n2), onCancelListener);
    }

    @Override
    public PendingIntent getErrorResolutionPendingIntent(Context context, int n, int n2) {
        return super.getErrorResolutionPendingIntent(context, n, n2);
    }

    public PendingIntent getErrorResolutionPendingIntent(Context context, ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            return connectionResult.getResolution();
        }
        return ((zzf)this).getErrorResolutionPendingIntent(context, connectionResult.getErrorCode(), 0);
    }

    @Override
    public final String getErrorString(int n) {
        return super.getErrorString(n);
    }

    @Override
    public int isGooglePlayServicesAvailable(Context context) {
        return super.isGooglePlayServicesAvailable(context);
    }

    @Override
    public final boolean isUserResolvableError(int n) {
        return super.isUserResolvableError(n);
    }

    public boolean showErrorDialogFragment(Activity activity, int n, int n2, DialogInterface.OnCancelListener onCancelListener) {
        Dialog dialog = this.getErrorDialog(activity, n, n2, onCancelListener);
        if (dialog == null) {
            return false;
        }
        GoogleApiAvailability.zza(activity, dialog, "GooglePlayServicesErrorDialog", onCancelListener);
        return true;
    }

    public void showErrorNotification(Context context, int n) {
        this.zza(context, n, null, this.zza(context, n, 0, "n"));
    }

    public final boolean zza(Activity activity, zzcf zzcf2, int n, int n2, DialogInterface.OnCancelListener onCancelListener) {
        if ((zzcf2 = GoogleApiAvailability.zza((Context)activity, n, zzv.zza(zzcf2, zzf.zza((Context)activity, n, "d"), 2), onCancelListener)) == null) {
            return false;
        }
        GoogleApiAvailability.zza(activity, (Dialog)zzcf2, "GooglePlayServicesErrorDialog", onCancelListener);
        return true;
    }

    public final boolean zza(Context context, ConnectionResult connectionResult, int n) {
        PendingIntent pendingIntent = this.getErrorResolutionPendingIntent(context, connectionResult);
        if (pendingIntent != null) {
            this.zza(context, connectionResult.getErrorCode(), null, GoogleApiActivity.zza(context, pendingIntent, n));
            return true;
        }
        return false;
    }

    final void zzcc(Context context) {
        new zza(this, context).sendEmptyMessageDelayed(1, 120000L);
    }

    @SuppressLint(value={"HandlerLeak"})
    final class zza
    extends Handler {
        private final Context mApplicationContext;
        private /* synthetic */ GoogleApiAvailability zzfkw;

        /*
         * Enabled aggressive block sorting
         */
        public zza(GoogleApiAvailability googleApiAvailability, Context context) {
            this.zzfkw = googleApiAvailability;
            googleApiAvailability = Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper();
            super((Looper)googleApiAvailability);
            this.mApplicationContext = context.getApplicationContext();
        }

        /*
         * Enabled aggressive block sorting
         */
        public final void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    int n = message.what;
                    Log.w((String)"GoogleApiAvailability", (String)new StringBuilder(50).append("Don't know how to handle this message: ").append(n).toString());
                    return;
                }
                case 1: {
                    int n = ((zzf)this.zzfkw).isGooglePlayServicesAvailable(this.mApplicationContext);
                    if (!((zzf)this.zzfkw).isUserResolvableError(n)) return;
                    this.zzfkw.showErrorNotification(this.mApplicationContext, n);
                    return;
                }
            }
        }
    }

}

