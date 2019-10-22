/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.app.KeyguardManager
 *  android.app.Notification
 *  android.app.Notification$BigTextStyle
 *  android.app.Notification$Builder
 *  android.app.Notification$Style
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.graphics.Color
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.media.RingtoneManager
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.os.Process
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONException
 */
package com.google.firebase.messaging;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.common.util.zzq;
import com.google.firebase.iid.zzx;
import com.google.firebase.messaging.FirebaseMessagingService;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;

final class zza {
    private static zza zznzz;
    private final Context mContext;
    private Bundle zzfwe;
    private Method zzoaa;
    private Method zzoab;
    private final AtomicInteger zzoac = new AtomicInteger((int)SystemClock.elapsedRealtime());

    private zza(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @TargetApi(value=26)
    private final Notification zza(CharSequence charSequence, String string2, int n, Integer n2, Uri uri, PendingIntent pendingIntent, PendingIntent pendingIntent2, String string3) {
        Notification.Builder builder;
        block18: {
            block17: {
                builder = new Notification.Builder(this.mContext).setAutoCancel(true).setSmallIcon(n);
                if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                    builder.setContentTitle(charSequence);
                }
                if (!TextUtils.isEmpty((CharSequence)string2)) {
                    builder.setContentText((CharSequence)string2);
                    builder.setStyle((Notification.Style)new Notification.BigTextStyle().bigText((CharSequence)string2));
                }
                if (n2 != null) {
                    builder.setColor(n2.intValue());
                }
                if (uri != null) {
                    builder.setSound(uri);
                }
                if (pendingIntent != null) {
                    builder.setContentIntent(pendingIntent);
                }
                if (pendingIntent2 != null) {
                    builder.setDeleteIntent(pendingIntent2);
                }
                if (string3 == null) break block17;
                if (this.zzoaa == null) {
                    this.zzoaa = zza.zzrr("setChannelId");
                }
                if (this.zzoaa == null) {
                    this.zzoaa = zza.zzrr("setChannel");
                }
                if (this.zzoaa != null) break block18;
                Log.e((String)"FirebaseMessaging", (String)"Error while setting the notification channel");
            }
            do {
                return builder.build();
                break;
            } while (true);
        }
        try {
            this.zzoaa.invoke((Object)builder, string3);
            return builder.build();
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)"FirebaseMessaging", (String)"Error while setting the notification channel", (Throwable)illegalAccessException);
            return builder.build();
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.e((String)"FirebaseMessaging", (String)"Error while setting the notification channel", (Throwable)invocationTargetException);
            return builder.build();
        }
        catch (SecurityException securityException) {
            Log.e((String)"FirebaseMessaging", (String)"Error while setting the notification channel", (Throwable)securityException);
            return builder.build();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.e((String)"FirebaseMessaging", (String)"Error while setting the notification channel", (Throwable)illegalArgumentException);
            return builder.build();
        }
    }

    private static void zza(Intent intent, Bundle bundle) {
        for (String string2 : bundle.keySet()) {
            if (!string2.startsWith("google.c.a.") && !string2.equals("from")) continue;
            intent.putExtra(string2, bundle.getString(string2));
        }
    }

    static boolean zzag(Bundle bundle) {
        return "1".equals(zza.zze(bundle, "gcm.n.e")) || zza.zze(bundle, "gcm.n.icon") != null;
    }

    static Uri zzah(Bundle bundle) {
        String string2;
        String string3 = string2 = zza.zze(bundle, "gcm.n.link_android");
        if (TextUtils.isEmpty((CharSequence)string2)) {
            string3 = zza.zze(bundle, "gcm.n.link");
        }
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            return Uri.parse((String)string3);
        }
        return null;
    }

    static String zzai(Bundle bundle) {
        String string2;
        String string3 = string2 = zza.zze(bundle, "gcm.n.sound2");
        if (TextUtils.isEmpty((CharSequence)string2)) {
            string3 = zza.zze(bundle, "gcm.n.sound");
        }
        return string3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final Bundle zzauu() {
        if (this.zzfwe != null) {
            return this.zzfwe;
        }
        ApplicationInfo applicationInfo = null;
        try {
            ApplicationInfo applicationInfo2;
            applicationInfo = applicationInfo2 = this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {}
        if (applicationInfo != null && applicationInfo.metaData != null) {
            this.zzfwe = applicationInfo.metaData;
            return this.zzfwe;
        }
        return Bundle.EMPTY;
    }

    static String zze(Bundle bundle, String string2) {
        String string3;
        String string4 = string3 = bundle.getString(string2);
        if (string3 == null) {
            string4 = bundle.getString(string2.replace("gcm.n.", "gcm.notification."));
        }
        return string4;
    }

    static zza zzex(Context object) {
        synchronized (zza.class) {
            if (zznzz == null) {
                zznzz = new zza((Context)object);
            }
            object = zznzz;
            return object;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static String zzh(Bundle bundle, String string2) {
        string2 = String.valueOf(string2);
        String string3 = String.valueOf("_loc_key");
        if (string3.length() != 0) {
            string2 = string2.concat(string3);
            do {
                return zza.zze(bundle, string2);
                break;
            } while (true);
        }
        string2 = new String(string2);
        return zza.zze(bundle, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Object[] zzi(Bundle arrobject, String string2) {
        Object object = String.valueOf(string2);
        String string3 = String.valueOf("_loc_args");
        object = string3.length() != 0 ? object.concat(string3) : new String((String)object);
        string3 = zza.zze((Bundle)arrobject, (String)object);
        if (TextUtils.isEmpty((CharSequence)string3)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(string3);
            object = new String[jSONArray.length()];
            int n = 0;
            do {
                arrobject = object;
                if (n >= ((String[])object).length) return arrobject;
                object[n] = jSONArray.opt(n);
                ++n;
            } while (true);
        }
        catch (JSONException jSONException) {
            String string4 = String.valueOf(string2);
            string2 = String.valueOf("_loc_args");
            string4 = string2.length() != 0 ? string4.concat(string2) : new String(string4);
            string4 = string4.substring(6);
            Log.w((String)"FirebaseMessaging", (String)new StringBuilder(String.valueOf(string4).length() + 41 + String.valueOf(string3).length()).append("Malformed ").append(string4).append(": ").append(string3).append("  Default value will be used.").toString());
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @TargetApi(value=26)
    private final boolean zzid(int n) {
        if (Build.VERSION.SDK_INT != 26) return true;
        {
            try {
                Drawable drawable2 = this.mContext.getResources().getDrawable(n, null);
                if (drawable2.getBounds().height() != 0 && drawable2.getBounds().width() != 0) return true;
                {
                    Log.e((String)"FirebaseMessaging", (String)new StringBuilder(72).append("Icon with id: ").append(n).append(" uses an invalid gradient. Using fallback icon.").toString());
                    return false;
                }
            }
            catch (Resources.NotFoundException notFoundException) {
                return false;
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final String zzj(Bundle object, String string2) {
        String string3;
        String string4 = zza.zze((Bundle)object, string3);
        if (!TextUtils.isEmpty((CharSequence)string4)) {
            return string4;
        }
        string4 = zza.zzh((Bundle)object, string3);
        if (TextUtils.isEmpty((CharSequence)string4)) {
            return null;
        }
        Resources resources = this.mContext.getResources();
        int n = resources.getIdentifier(string4, "string", this.mContext.getPackageName());
        if (n == 0) {
            void var1_4;
            String string5 = String.valueOf(string3);
            string3 = String.valueOf("_loc_key");
            if (string3.length() != 0) {
                String string6 = string5.concat(string3);
            } else {
                String string7 = new String(string5);
            }
            String string8 = var1_4.substring(6);
            Log.w((String)"FirebaseMessaging", (String)new StringBuilder(String.valueOf(string8).length() + 49 + String.valueOf(string4).length()).append(string8).append(" resource not found: ").append(string4).append(" Default value will be used.").toString());
            return null;
        }
        Object[] arrobject = zza.zzi((Bundle)object, string3);
        if (arrobject == null) {
            return resources.getString(n);
        }
        try {
            return resources.getString(n, arrobject);
        }
        catch (MissingFormatArgumentException missingFormatArgumentException) {
            String string9 = Arrays.toString(arrobject);
            Log.w((String)"FirebaseMessaging", (String)new StringBuilder(String.valueOf(string4).length() + 58 + String.valueOf(string9).length()).append("Missing format argument for ").append(string4).append(": ").append(string9).append(" Default value will be used.").toString(), (Throwable)missingFormatArgumentException);
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @TargetApi(value=26)
    private static Method zzrr(String object) {
        try {
            return Notification.Builder.class.getMethod((String)object, String.class);
        }
        catch (SecurityException securityException) {
            do {
                return null;
                break;
            } while (true);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final Integer zzrs(String string2) {
        int n;
        if (Build.VERSION.SDK_INT < 21) return null;
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            try {
                int n2 = Color.parseColor((String)string2);
                return n2;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.w((String)"FirebaseMessaging", (String)new StringBuilder(String.valueOf(string2).length() + 54).append("Color ").append(string2).append(" not valid. Notification will use default color.").toString());
            }
        }
        if ((n = this.zzauu().getInt("com.google.firebase.messaging.default_notification_color", 0)) == 0) {
            return null;
        }
        try {
            n = ContextCompat.getColor(this.mContext, n);
            return n;
        }
        catch (Resources.NotFoundException notFoundException) {
            Log.w((String)"FirebaseMessaging", (String)"Cannot find the color resource referenced in AndroidManifest.");
            return null;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @TargetApi(value=26)
    private final String zzrt(String class_) {
        void var2_11;
        if (!zzq.isAtLeastO()) {
            return var2_11;
        }
        NotificationManager notificationManager = (NotificationManager)this.mContext.getSystemService(NotificationManager.class);
        try {
            if (this.zzoab == null) {
                this.zzoab = notificationManager.getClass().getMethod("getNotificationChannel", String.class);
            }
            if (!TextUtils.isEmpty(class_)) {
                Class<?> class_2 = class_;
                if (this.zzoab.invoke((Object)notificationManager, class_) != null) return var2_11;
                Log.w((String)"FirebaseMessaging", (String)new StringBuilder(String.valueOf(class_).length() + 122).append("Notification Channel requested (").append((String)((Object)class_)).append(") has not been created by the app. Manifest configuration, or default, value will be used.").toString());
            }
            if (!TextUtils.isEmpty((CharSequence)((Object)(class_ = this.zzauu().getString("com.google.firebase.messaging.default_notification_channel_id"))))) {
                Class<?> class_3 = class_;
                if (this.zzoab.invoke((Object)notificationManager, class_) != null) return var2_11;
                Log.w((String)"FirebaseMessaging", (String)"Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
            } else {
                Log.w((String)"FirebaseMessaging", (String)"Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
            }
            if (this.zzoab.invoke((Object)notificationManager, "fcm_fallback_notification_channel") != null) return "fcm_fallback_notification_channel";
            class_ = Class.forName("android.app.NotificationChannel");
            Object obj = class_.getConstructor(String.class, CharSequence.class, Integer.TYPE).newInstance("fcm_fallback_notification_channel", this.mContext.getString(R.string.fcm_fallback_notification_channel_label), 3);
            notificationManager.getClass().getMethod("createNotificationChannel", class_).invoke((Object)notificationManager, obj);
            return "fcm_fallback_notification_channel";
        }
        catch (InstantiationException instantiationException) {
            Log.e((String)"FirebaseMessaging", (String)"Error while setting the notification channel", (Throwable)instantiationException);
            return null;
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.e((String)"FirebaseMessaging", (String)"Error while setting the notification channel", (Throwable)invocationTargetException);
            return null;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.e((String)"FirebaseMessaging", (String)"Error while setting the notification channel", (Throwable)noSuchMethodException);
            return null;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)"FirebaseMessaging", (String)"Error while setting the notification channel", (Throwable)illegalAccessException);
            return null;
        }
        catch (ClassNotFoundException classNotFoundException) {
            Log.e((String)"FirebaseMessaging", (String)"Error while setting the notification channel", (Throwable)classNotFoundException);
            return null;
        }
        catch (SecurityException securityException) {
            Log.e((String)"FirebaseMessaging", (String)"Error while setting the notification channel", (Throwable)securityException);
            return null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.e((String)"FirebaseMessaging", (String)"Error while setting the notification channel", (Throwable)illegalArgumentException);
            return null;
        }
        catch (LinkageError linkageError) {
            Log.e((String)"FirebaseMessaging", (String)"Error while setting the notification channel", (Throwable)linkageError);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private final PendingIntent zzt(Bundle object) {
        Object object2;
        String string2 = zza.zze((Bundle)object, "gcm.n.click_action");
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            string2 = new Intent(string2);
            string2.setPackage(this.mContext.getPackageName());
            string2.setFlags(268435456);
        } else {
            object2 = zza.zzah((Bundle)object);
            if (object2 != null) {
                string2 = new Intent("android.intent.action.VIEW");
                string2.setPackage(this.mContext.getPackageName());
                string2.setData((Uri)object2);
            } else {
                string2 = this.mContext.getPackageManager().getLaunchIntentForPackage(this.mContext.getPackageName());
                if (string2 == null) {
                    Log.w((String)"FirebaseMessaging", (String)"No activity found to launch app");
                }
            }
        }
        if (string2 == null) {
            return null;
        }
        string2.addFlags(67108864);
        object = new Bundle((Bundle)object);
        FirebaseMessagingService.zzq((Bundle)object);
        string2.putExtras((Bundle)object);
        object = object.keySet().iterator();
        while (object.hasNext()) {
            object2 = (String)object.next();
            if (!((String)object2).startsWith("gcm.n.") && !((String)object2).startsWith("gcm.notification.")) continue;
            string2.removeExtra((String)object2);
        }
        return PendingIntent.getActivity((Context)this.mContext, (int)this.zzoac.incrementAndGet(), (Intent)string2, (int)1073741824);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    final boolean zzs(Bundle var1_1) {
        block31: {
            block36: {
                block35: {
                    block34: {
                        block33: {
                            block30: {
                                block32: {
                                    if ("1".equals(zza.zze(var1_1 /* !! */ , "gcm.n.noui"))) {
                                        return true;
                                    }
                                    if (((KeyguardManager)this.mContext.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) ** GOTO lbl-1000
                                    if (!zzq.zzamn()) {
                                        SystemClock.sleep((long)10L);
                                    }
                                    var2_5 = Process.myPid();
                                    var6_6 = ((ActivityManager)this.mContext.getSystemService("activity")).getRunningAppProcesses();
                                    if (var6_6 != null) {
                                        for (ActivityManager.RunningAppProcessInfo var7_19 : var6_6) {
                                            if (var7_19.pid != var2_5) continue;
                                            if (var7_19.importance == 100) {
                                                return false;
                                            }
                                            var2_5 = 0;
                                            break;
                                        }
                                    } else lbl-1000:
                                    // 3 sources
                                    {
                                        var2_5 = 0;
                                    }
                                    if (var2_5 != 0) {
                                        return false;
                                    }
                                    var7_20 = var6_9 = this.zzj(var1_1 /* !! */ , "gcm.n.title");
                                    if (TextUtils.isEmpty((CharSequence)var6_9)) {
                                        var7_21 = this.mContext.getApplicationInfo().loadLabel(this.mContext.getPackageManager());
                                    }
                                    var10_24 = this.zzj(var1_1 /* !! */ , "gcm.n.body");
                                    var6_10 = zza.zze(var1_1 /* !! */ , "gcm.n.icon");
                                    if (TextUtils.isEmpty((CharSequence)var6_10)) break block30;
                                    var8_25 = this.mContext.getResources();
                                    var2_5 = var8_25.getIdentifier(var6_10, "drawable", this.mContext.getPackageName());
                                    if (var2_5 != 0 && this.zzid(var2_5)) break block31;
                                    var3_35 = var8_25.getIdentifier(var6_10, "mipmap", this.mContext.getPackageName());
                                    if (var3_35 == 0) break block32;
                                    var2_5 = var3_35;
                                    if (this.zzid(var3_35)) break block31;
                                }
                                Log.w((String)"FirebaseMessaging", (String)new StringBuilder(String.valueOf(var6_10).length() + 61).append("Icon resource ").append(var6_10).append(" not found. Notification will use default icon.").toString());
                            }
                            if ((var3_35 = this.zzauu().getInt("com.google.firebase.messaging.default_notification_icon", 0)) == 0) break block33;
                            var2_5 = var3_35;
                            if (this.zzid(var3_35)) break block34;
                        }
                        var2_5 = this.mContext.getApplicationInfo().icon;
                    }
                    if (var2_5 == 0) break block35;
                    var3_35 = var2_5;
                    if (this.zzid(var2_5)) break block36;
                }
                var3_35 = 17301651;
            }
            var2_5 = var3_35;
        }
        var11_32 = this.zzrs(zza.zze(var1_1 /* !! */ , "gcm.n.color"));
        var6_11 = zza.zzai(var1_1 /* !! */ );
        if (TextUtils.isEmpty((CharSequence)var6_11)) {
            var6_12 = null;
        } else if (!"default".equals(var6_11) && this.mContext.getResources().getIdentifier(var6_11, "raw", this.mContext.getPackageName()) != 0) {
            var8_31 = this.mContext.getPackageName();
            var6_16 = Uri.parse((String)new StringBuilder(String.valueOf("android.resource://").length() + 5 + String.valueOf(var8_31).length() + String.valueOf(var6_11).length()).append("android.resource://").append(var8_31).append("/raw/").append(var6_11).toString());
        } else {
            var6_17 = RingtoneManager.getDefaultUri((int)2);
        }
        var8_27 = this.zzt(var1_1 /* !! */ );
        if (FirebaseMessagingService.zzaj(var1_1 /* !! */ )) {
            var9_33 = new Intent("com.google.firebase.messaging.NOTIFICATION_OPEN");
            zza.zza(var9_33, var1_1 /* !! */ );
            var9_33.putExtra("pending_intent", (Parcelable)var8_27);
            var8_28 = zzx.zza(this.mContext, this.zzoac.incrementAndGet(), var9_33, 1073741824);
            var9_33 = new Intent("com.google.firebase.messaging.NOTIFICATION_DISMISS");
            zza.zza(var9_33, var1_1 /* !! */ );
            var9_33 = zzx.zza(this.mContext, this.zzoac.incrementAndGet(), var9_33, 1073741824);
        } else {
            var9_33 = null;
        }
        if (zzq.isAtLeastO() && this.mContext.getApplicationInfo().targetSdkVersion > 25) {
            var6_14 = this.zza((CharSequence)var7_22, var10_24, var2_5, var11_32, (Uri)var6_13, (PendingIntent)var8_29, (PendingIntent)var9_33, this.zzrt(zza.zze(var1_1 /* !! */ , "gcm.n.android_channel_id")));
        } else {
            var12_36 = new NotificationCompat.Builder(this.mContext).setAutoCancel(true).setSmallIcon(var2_5);
            if (!TextUtils.isEmpty((CharSequence)var7_22)) {
                var12_36.setContentTitle((CharSequence)var7_22);
            }
            if (!TextUtils.isEmpty((CharSequence)var10_24)) {
                var12_36.setContentText(var10_24);
                var12_36.setStyle(new NotificationCompat.BigTextStyle().bigText(var10_24));
            }
            if (var11_32 != null) {
                var12_36.setColor(var11_32);
            }
            if (var6_13 != null) {
                var12_36.setSound((Uri)var6_13);
            }
            if (var8_29 != null) {
                var12_36.setContentIntent((PendingIntent)var8_29);
            }
            if (var9_33 != null) {
                var12_36.setDeleteIntent((PendingIntent)var9_33);
            }
            var6_18 = var12_36.build();
        }
        var7_23 = zza.zze(var1_1 /* !! */ , "gcm.n.tag");
        if (Log.isLoggable((String)"FirebaseMessaging", (int)3)) {
            Log.d((String)"FirebaseMessaging", (String)"Showing notification");
        }
        var8_30 = (NotificationManager)this.mContext.getSystemService("notification");
        var1_2 = var7_23;
        if (TextUtils.isEmpty((CharSequence)var7_23)) {
            var4_34 = SystemClock.uptimeMillis();
            var1_3 = new StringBuilder(37).append("FCM-Notification:").append(var4_34).toString();
        }
        var8_30.notify((String)var1_4, 0, (Notification)var6_15);
        return true;
    }
}

