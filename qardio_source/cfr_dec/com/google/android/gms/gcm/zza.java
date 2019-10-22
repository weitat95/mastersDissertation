/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.app.KeyguardManager
 *  android.app.Notification
 *  android.app.Notification$BigTextStyle
 *  android.app.Notification$Builder
 *  android.app.Notification$Style
 *  android.app.NotificationChannel
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.media.RingtoneManager
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Process
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONException
 */
package com.google.android.gms.gcm;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.gcm.GcmListenerService;
import java.util.Iterator;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;

final class zza {
    static zza zzibw;
    private final Context mContext;
    private String zzibx;
    private final AtomicInteger zziby = new AtomicInteger((int)SystemClock.elapsedRealtime());

    private zza(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final Bundle zzauu() {
        ApplicationInfo applicationInfo = null;
        try {
            ApplicationInfo applicationInfo2;
            applicationInfo = applicationInfo2 = this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {}
        if (applicationInfo != null && applicationInfo.metaData != null) {
            return applicationInfo.metaData;
        }
        return Bundle.EMPTY;
    }

    static zza zzdj(Context object) {
        synchronized (zza.class) {
            if (zzibw == null) {
                zzibw = new zza((Context)object);
            }
            object = zzibw;
            return object;
        }
    }

    static boolean zzdk(Context object) {
        if (((KeyguardManager)object.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            return false;
        }
        int n = Process.myPid();
        if ((object = ((ActivityManager)object.getSystemService("activity")).getRunningAppProcesses()) != null) {
            object = object.iterator();
            while (object.hasNext()) {
                ActivityManager.RunningAppProcessInfo runningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)object.next();
                if (runningAppProcessInfo.pid != n) continue;
                return runningAppProcessInfo.importance == 100;
            }
        }
        return false;
    }

    static String zze(Bundle bundle, String string2) {
        String string3;
        String string4 = string3 = bundle.getString(string2);
        if (string3 == null) {
            string4 = bundle.getString(string2.replace("gcm.n.", "gcm.notification."));
        }
        return string4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final String zzf(Bundle object, String string2) {
        String string3 = zza.zze((Bundle)object, string2);
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            return string3;
        }
        string3 = String.valueOf(string2);
        String string4 = String.valueOf("_loc_key");
        string3 = string4.length() != 0 ? string3.concat(string4) : new String(string3);
        string4 = zza.zze((Bundle)object, string3);
        if (TextUtils.isEmpty((CharSequence)string4)) {
            return null;
        }
        Resources resources = this.mContext.getResources();
        int n = resources.getIdentifier(string4, "string", this.mContext.getPackageName());
        if (n == 0) {
            object = String.valueOf(string2);
            string2 = String.valueOf("_loc_key");
            object = string2.length() != 0 ? ((String)object).concat(string2) : new String((String)object);
            object = ((String)object).substring(6);
            Log.w((String)"GcmNotification", (String)new StringBuilder(String.valueOf(object).length() + 49 + String.valueOf(string4).length()).append((String)object).append(" resource not found: ").append(string4).append(" Default value will be used.").toString());
            return null;
        }
        string3 = String.valueOf(string2);
        Object[] arrobject = String.valueOf("_loc_args");
        string3 = arrobject.length() != 0 ? string3.concat((String)arrobject) : new String(string3);
        string3 = zza.zze((Bundle)object, string3);
        if (TextUtils.isEmpty((CharSequence)string3)) {
            return resources.getString(n);
        }
        try {
            object = new JSONArray(string3);
            arrobject = new String[object.length()];
            int n2 = 0;
            do {
                if (n2 >= arrobject.length) {
                    return resources.getString(n, arrobject);
                }
                arrobject[n2] = object.opt(n2);
                ++n2;
            } while (true);
        }
        catch (JSONException jSONException) {
            String string5 = String.valueOf(string2);
            string2 = String.valueOf("_loc_args");
            string5 = string2.length() != 0 ? string5.concat(string2) : new String(string5);
            string5 = string5.substring(6);
            Log.w((String)"GcmNotification", (String)new StringBuilder(String.valueOf(string5).length() + 41 + String.valueOf(string3).length()).append("Malformed ").append(string5).append(": ").append(string3).append("  Default value will be used.").toString());
            return null;
        }
        catch (MissingFormatArgumentException missingFormatArgumentException) {
            Log.w((String)"GcmNotification", (String)new StringBuilder(String.valueOf(string4).length() + 58 + String.valueOf(string3).length()).append("Missing format argument for ").append(string4).append(": ").append(string3).append(" Default value will be used.").toString(), (Throwable)missingFormatArgumentException);
            return null;
        }
    }

    static void zzr(Bundle bundle) {
        String string2;
        Bundle bundle2 = new Bundle();
        Iterator iterator = bundle.keySet().iterator();
        while (iterator.hasNext()) {
            String string3 = (String)iterator.next();
            String string4 = bundle.getString(string3);
            string2 = string3;
            if (string3.startsWith("gcm.notification.")) {
                string2 = string3.replace("gcm.notification.", "gcm.n.");
            }
            if (!string2.startsWith("gcm.n.")) continue;
            if (!"gcm.n.e".equals(string2)) {
                bundle2.putString(string2.substring(6), string4);
            }
            iterator.remove();
        }
        string2 = bundle2.getString("sound2");
        if (string2 != null) {
            bundle2.remove("sound2");
            bundle2.putString("sound", string2);
        }
        if (!bundle2.isEmpty()) {
            bundle.putBundle("notification", bundle2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private final PendingIntent zzt(Bundle object) {
        String string2 = zza.zze((Bundle)object, "gcm.n.click_action");
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            string2 = new Intent(string2);
            string2.setPackage(this.mContext.getPackageName());
            string2.setFlags(268435456);
        } else {
            string2 = this.mContext.getPackageManager().getLaunchIntentForPackage(this.mContext.getPackageName());
            if (string2 == null) {
                Log.w((String)"GcmNotification", (String)"No activity found to launch app");
                return null;
            }
        }
        object = new Bundle((Bundle)object);
        GcmListenerService.zzq((Bundle)object);
        string2.putExtras((Bundle)object);
        object = object.keySet().iterator();
        while (object.hasNext()) {
            String string3 = (String)object.next();
            if (!string3.startsWith("gcm.n.") && !string3.startsWith("gcm.notification.")) continue;
            string2.removeExtra(string3);
        }
        return PendingIntent.getActivity((Context)this.mContext, (int)this.zziby.getAndIncrement(), (Intent)string2, (int)1073741824);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    final boolean zzs(Bundle object) {
        void var1_4;
        Object object2;
        String string2;
        block33: {
            PendingIntent pendingIntent;
            String string3;
            void var8_8;
            int n;
            String string4;
            block25: {
                block26: {
                    block32: {
                        block30: {
                            block31: {
                                block29: {
                                    block27: {
                                        String string5;
                                        block28: {
                                            block24: {
                                                int n2;
                                                block23: {
                                                    object2 = null;
                                                    String string6 = this.zzf((Bundle)object, "gcm.n.title");
                                                    if (TextUtils.isEmpty((CharSequence)string6)) {
                                                        CharSequence charSequence = this.mContext.getApplicationInfo().loadLabel(this.mContext.getPackageManager());
                                                    }
                                                    string3 = this.zzf((Bundle)object, "gcm.n.body");
                                                    string2 = zza.zze(object, "gcm.n.icon");
                                                    if (TextUtils.isEmpty((CharSequence)string2)) break block23;
                                                    Resources resources = this.mContext.getResources();
                                                    n = resources.getIdentifier(string2, "drawable", this.mContext.getPackageName());
                                                    if (n != 0 || (n = resources.getIdentifier(string2, "mipmap", this.mContext.getPackageName())) != 0) break block24;
                                                    Log.w((String)"GcmNotification", (String)new StringBuilder(String.valueOf(string2).length() + 57).append("Icon resource ").append(string2).append(" not found. Notification will use app icon.").toString());
                                                }
                                                n = n2 = this.mContext.getApplicationInfo().icon;
                                                if (n2 == 0) {
                                                    n = 17301651;
                                                }
                                            }
                                            string4 = zza.zze(object, "gcm.n.color");
                                            string2 = zza.zze(object, "gcm.n.sound2");
                                            if (TextUtils.isEmpty((CharSequence)string2)) {
                                                string2 = null;
                                            } else if (!"default".equals(string2) && this.mContext.getResources().getIdentifier(string2, "raw", this.mContext.getPackageName()) != 0) {
                                                String string7 = this.mContext.getPackageName();
                                                string2 = Uri.parse((String)new StringBuilder(String.valueOf("android.resource://").length() + 5 + String.valueOf(string7).length() + String.valueOf(string2).length()).append("android.resource://").append(string7).append("/raw/").append(string2).toString());
                                            } else {
                                                string2 = RingtoneManager.getDefaultUri((int)2);
                                            }
                                            pendingIntent = this.zzt((Bundle)object);
                                            if (!zzq.isAtLeastO() || this.mContext.getApplicationInfo().targetSdkVersion <= 25) break block25;
                                            string5 = zza.zze(object, "gcm.n.android_channel_id");
                                            if (!zzq.isAtLeastO()) break block26;
                                            object2 = (NotificationManager)this.mContext.getSystemService(NotificationManager.class);
                                            if (TextUtils.isEmpty((CharSequence)string5)) break block27;
                                            if (object2.getNotificationChannel(string5) == null) break block28;
                                            object2 = string5;
                                            break block26;
                                        }
                                        Log.w((String)"GcmNotification", (String)new StringBuilder(String.valueOf(string5).length() + 122).append("Notification Channel requested (").append(string5).append(") has not been created by the app. Manifest configuration, or default, value will be used.").toString());
                                    }
                                    if (this.zzibx == null) break block29;
                                    object2 = this.zzibx;
                                    break block26;
                                }
                                this.zzibx = this.zzauu().getString("com.google.android.gms.gcm.default_notification_channel_id");
                                if (TextUtils.isEmpty((CharSequence)this.zzibx)) break block30;
                                if (object2.getNotificationChannel(this.zzibx) == null) break block31;
                                object2 = this.zzibx;
                                break block26;
                            }
                            Log.w((String)"GcmNotification", (String)"Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
                            break block32;
                        }
                        Log.w((String)"GcmNotification", (String)"Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
                    }
                    if (object2.getNotificationChannel("fcm_fallback_notification_channel") == null) {
                        object2.createNotificationChannel(new NotificationChannel("fcm_fallback_notification_channel", (CharSequence)this.mContext.getString(R.string.gcm_fallback_notification_channel_label), 3));
                    }
                    this.zzibx = "fcm_fallback_notification_channel";
                    object2 = this.zzibx;
                }
                Notification.Builder builder = new Notification.Builder(this.mContext).setAutoCancel(true).setSmallIcon(n);
                if (!TextUtils.isEmpty((CharSequence)var8_8)) {
                    builder.setContentTitle((CharSequence)var8_8);
                }
                if (!TextUtils.isEmpty((CharSequence)string3)) {
                    builder.setContentText((CharSequence)string3);
                    builder.setStyle((Notification.Style)new Notification.BigTextStyle().bigText((CharSequence)string3));
                }
                if (!TextUtils.isEmpty((CharSequence)string4)) {
                    builder.setColor(Color.parseColor((String)string4));
                }
                if (string2 != null) {
                    builder.setSound((Uri)string2);
                }
                if (pendingIntent != null) {
                    builder.setContentIntent(pendingIntent);
                }
                if (object2 != null) {
                    builder.setChannelId((String)object2);
                }
                object2 = builder.build();
                break block33;
            }
            object2 = new NotificationCompat.Builder(this.mContext).setAutoCancel(true).setSmallIcon(n);
            if (!TextUtils.isEmpty((CharSequence)var8_8)) {
                ((NotificationCompat.Builder)object2).setContentTitle((CharSequence)var8_8);
            }
            if (!TextUtils.isEmpty((CharSequence)string3)) {
                ((NotificationCompat.Builder)object2).setContentText(string3);
            }
            if (!TextUtils.isEmpty((CharSequence)string4)) {
                ((NotificationCompat.Builder)object2).setColor(Color.parseColor((String)string4));
            }
            if (string2 != null) {
                ((NotificationCompat.Builder)object2).setSound((Uri)string2);
            }
            if (pendingIntent != null) {
                ((NotificationCompat.Builder)object2).setContentIntent(pendingIntent);
            }
            object2 = ((NotificationCompat.Builder)object2).build();
        }
        string2 = zza.zze(object, "gcm.n.tag");
        if (Log.isLoggable((String)"GcmNotification", (int)3)) {
            Log.d((String)"GcmNotification", (String)"Showing notification");
        }
        NotificationManager notificationManager = (NotificationManager)this.mContext.getSystemService("notification");
        String string8 = string2;
        if (TextUtils.isEmpty((CharSequence)string2)) {
            long l = SystemClock.uptimeMillis();
            String string9 = new StringBuilder(37).append("GCM-Notification:").append(l).toString();
        }
        notificationManager.notify((String)var1_4, 0, (Notification)object2);
        return true;
    }
}

