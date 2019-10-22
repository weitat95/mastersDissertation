/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.ConditionVariable
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcelable
 *  android.os.Process
 *  android.os.RemoteException
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.iid;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;
import com.google.android.gms.iid.MessengerCompat;
import com.google.android.gms.iid.zzm;
import com.google.android.gms.iid.zzn;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public final class zzl {
    private static String zzifp = null;
    private static boolean zzifq = false;
    private static int zzifr = 0;
    private static int zzifs = 0;
    private static int zzift = 0;
    private static BroadcastReceiver zzifu = null;
    private Context zzair;
    private PendingIntent zzicn;
    private Messenger zzicr;
    private Map<String, Object> zzifv = new HashMap<String, Object>();
    private Messenger zzifw;
    private MessengerCompat zzifx;
    private long zzify;
    private long zzifz;
    private int zziga;
    private int zzigb;
    private long zzigc;

    public zzl(Context context) {
        this.zzair = context;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String zza(KeyPair object, String ... arrobject) {
        byte[] arrby;
        try {
            arrby = TextUtils.join((CharSequence)"\n", (Object[])arrobject).getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Log.e((String)"InstanceID/Rpc", (String)"Unable to encode string", (Throwable)unsupportedEncodingException);
            return null;
        }
        try {}
        catch (GeneralSecurityException generalSecurityException) {
            Log.e((String)"InstanceID/Rpc", (String)"Unable to sign registration request", (Throwable)generalSecurityException);
            return null;
        }
        PrivateKey privateKey = ((KeyPair)object).getPrivate();
        object = privateKey instanceof RSAPrivateKey ? "SHA256withRSA" : "SHA256withECDSA";
        object = Signature.getInstance((String)object);
        ((Signature)object).initSign(privateKey);
        ((Signature)object).update(arrby);
        return InstanceID.zzo(((Signature)object).sign());
    }

    private static boolean zza(PackageManager packageManager) {
        Iterator iterator = packageManager.queryBroadcastReceivers(new Intent("com.google.iid.TOKEN_REQUEST"), 0).iterator();
        while (iterator.hasNext()) {
            if (!zzl.zza(packageManager, ((ResolveInfo)iterator.next()).activityInfo.packageName, "com.google.iid.TOKEN_REQUEST")) continue;
            zzifq = true;
            return true;
        }
        return false;
    }

    private static boolean zza(PackageManager packageManager, String string2, String string3) {
        if (packageManager.checkPermission("com.google.android.c2dm.permission.SEND", string2) == 0) {
            return zzl.zzb(packageManager, string2);
        }
        Log.w((String)"InstanceID/Rpc", (String)new StringBuilder(String.valueOf(string2).length() + 56 + String.valueOf(string3).length()).append("Possible malicious package ").append(string2).append(" declares ").append(string3).append(" without permission").toString());
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzae(Object object) {
        Class<?> class_ = this.getClass();
        synchronized (class_) {
            Iterator<String> iterator = this.zzifv.keySet().iterator();
            while (iterator.hasNext()) {
                String string2 = iterator.next();
                Object object2 = this.zzifv.get(string2);
                this.zzifv.put(string2, object);
                zzl.zze(object2, object);
            }
            return;
        }
    }

    private final void zzavh() {
        if (this.zzicr != null) {
            return;
        }
        zzl.zzdp(this.zzair);
        this.zzicr = new Messenger((Handler)new zzm(this, Looper.getMainLooper()));
    }

    private static String zzavi() {
        synchronized (zzl.class) {
            int n = zzift;
            zzift = n + 1;
            String string2 = Integer.toString(n);
            return string2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final Intent zzb(Bundle object, KeyPair object2) throws IOException {
        ConditionVariable conditionVariable;
        String string2;
        block28: {
            boolean bl;
            Object object3;
            block29: {
                conditionVariable = new ConditionVariable();
                string2 = zzl.zzavi();
                object3 = this.getClass();
                synchronized (object3) {
                    this.zzifv.put(string2, (Object)conditionVariable);
                }
                long l = SystemClock.elapsedRealtime();
                if (this.zzigc != 0L && l <= this.zzigc) {
                    long l2 = this.zzigc;
                    int n = this.zzigb;
                    Log.w((String)"InstanceID/Rpc", (String)new StringBuilder(78).append("Backoff mode, next request attempt: ").append(l2 - l).append(" interval: ").append(n).toString());
                    throw new IOException("RETRY_LATER");
                }
                this.zzavh();
                if (zzifp == null) {
                    throw new IOException("MISSING_INSTANCEID_SERVICE");
                }
                this.zzify = SystemClock.elapsedRealtime();
                object3 = zzifq ? "com.google.iid.TOKEN_REQUEST" : "com.google.android.c2dm.intent.REGISTER";
                object3 = new Intent((String)object3);
                object3.setPackage(zzifp);
                object.putString("gmsv", Integer.toString(zzl.zzdq(this.zzair)));
                object.putString("osv", Integer.toString(Build.VERSION.SDK_INT));
                object.putString("app_ver", Integer.toString(InstanceID.zzdm(this.zzair)));
                object.putString("app_ver_name", InstanceID.zzdn(this.zzair));
                object.putString("cliv", "iid-11910000");
                object.putString("appid", InstanceID.zza((KeyPair)object2));
                String string3 = InstanceID.zzo(((KeyPair)object2).getPublic().getEncoded());
                object.putString("pub2", string3);
                object.putString("sig", zzl.zza((KeyPair)object2, this.zzair.getPackageName(), string3));
                object3.putExtras(object);
                this.zzi((Intent)object3);
                this.zzify = SystemClock.elapsedRealtime();
                object3.putExtra("kid", new StringBuilder(String.valueOf(string2).length() + 5).append("|ID|").append(string2).append("|").toString());
                object3.putExtra("X-kid", new StringBuilder(String.valueOf(string2).length() + 5).append("|ID|").append(string2).append("|").toString());
                bl = "com.google.android.gsf".equals(zzifp);
                object = object3.getStringExtra("useGsf");
                if (object != null) {
                    bl = "1".equals(object);
                }
                if (Log.isLoggable((String)"InstanceID/Rpc", (int)3)) {
                    object = String.valueOf((Object)object3.getExtras());
                    Log.d((String)"InstanceID/Rpc", (String)new StringBuilder(String.valueOf(object).length() + 8).append("Sending ").append((String)object).toString());
                }
                if (this.zzifw != null) {
                    object3.putExtra("google.messenger", (Parcelable)this.zzicr);
                    object = Message.obtain();
                    object.obj = object3;
                    try {
                        this.zzifw.send((Message)object);
                        break block28;
                    }
                    catch (RemoteException remoteException) {
                        if (!Log.isLoggable((String)"InstanceID/Rpc", (int)3)) break block29;
                        Log.d((String)"InstanceID/Rpc", (String)"Messenger failed, fallback to startService");
                    }
                }
            }
            if (bl) {
                synchronized (this) {
                    if (zzifu == null) {
                        zzifu = new zzn(this);
                        if (Log.isLoggable((String)"InstanceID/Rpc", (int)3)) {
                            Log.d((String)"InstanceID/Rpc", (String)"Registered GSF callback receiver");
                        }
                        object = new IntentFilter("com.google.android.c2dm.intent.REGISTRATION");
                        object.addCategory(this.zzair.getPackageName());
                        this.zzair.registerReceiver(zzifu, (IntentFilter)object, "com.google.android.c2dm.permission.SEND", null);
                    }
                }
                this.zzair.sendBroadcast((Intent)object3);
            } else {
                block30: {
                    object3.putExtra("google.messenger", (Parcelable)this.zzicr);
                    object3.putExtra("messenger2", "1");
                    if (this.zzifx != null) {
                        object = Message.obtain();
                        object.obj = object3;
                        try {
                            this.zzifx.send((Message)object);
                            break block28;
                        }
                        catch (RemoteException remoteException) {
                            if (!Log.isLoggable((String)"InstanceID/Rpc", (int)3)) break block30;
                            Log.d((String)"InstanceID/Rpc", (String)"Messenger failed, fallback to startService");
                        }
                    }
                }
                if (zzifq) {
                    this.zzair.sendBroadcast((Intent)object3);
                } else {
                    this.zzair.startService((Intent)object3);
                }
            }
        }
        conditionVariable.block(30000L);
        object = this.getClass();
        synchronized (object) {
            object2 = this.zzifv.remove(string2);
            if (object2 instanceof Intent) {
                return (Intent)object2;
            }
            if (object2 instanceof String) {
                throw new IOException((String)object2);
            }
            object2 = String.valueOf(object2);
            Log.w((String)"InstanceID/Rpc", (String)new StringBuilder(String.valueOf(object2).length() + 12).append("No response ").append((String)object2).toString());
            throw new IOException("TIMEOUT");
        }
    }

    private static boolean zzb(PackageManager packageManager, String string2) {
        try {
            packageManager = packageManager.getApplicationInfo(string2, 0);
            zzifp = packageManager.packageName;
            zzifs = packageManager.uid;
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String zzdp(Context context) {
        if (zzifp != null) {
            return zzifp;
        }
        zzifr = Process.myUid();
        context = context.getPackageManager();
        if (!zzq.isAtLeastO()) {
            boolean bl;
            block7: {
                Iterator iterator = context.queryIntentServices(new Intent("com.google.android.c2dm.intent.REGISTER"), 0).iterator();
                while (iterator.hasNext()) {
                    if (!zzl.zza((PackageManager)context, ((ResolveInfo)iterator.next()).serviceInfo.packageName, "com.google.android.c2dm.intent.REGISTER")) continue;
                    zzifq = false;
                    bl = true;
                    break block7;
                }
                bl = false;
            }
            if (bl) {
                return zzifp;
            }
        }
        if (zzl.zza((PackageManager)context)) {
            return zzifp;
        }
        Log.w((String)"InstanceID/Rpc", (String)"Failed to resolve IID implementation package, falling back");
        if (zzl.zzb((PackageManager)context, "com.google.android.gms")) {
            zzifq = zzq.isAtLeastO();
            return zzifp;
        }
        if (!zzq.zzamn() && zzl.zzb((PackageManager)context, "com.google.android.gsf")) {
            zzifq = false;
            return zzifp;
        }
        Log.w((String)"InstanceID/Rpc", (String)"Google Play services is missing, unable to get tokens");
        return null;
    }

    private static int zzdq(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int n = packageManager.getPackageInfo((String)zzl.zzdp((Context)context), (int)0).versionCode;
            return n;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return -1;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void zze(Object object, Object object2) {
        if (object instanceof ConditionVariable) {
            ((ConditionVariable)object).open();
        }
        if (!(object instanceof Messenger)) return;
        object = (Messenger)object;
        Message message = Message.obtain();
        message.obj = object2;
        try {
            object.send(message);
            return;
        }
        catch (RemoteException remoteException) {
            String string2 = String.valueOf((Object)remoteException);
            Log.w((String)"InstanceID/Rpc", (String)new StringBuilder(String.valueOf(string2).length() + 24).append("Failed to send response ").append(string2).toString());
            return;
        }
    }

    private final void zzi(Intent intent) {
        synchronized (this) {
            if (this.zzicn == null) {
                Intent intent2 = new Intent();
                intent2.setPackage("com.google.example.invalidpackage");
                this.zzicn = PendingIntent.getBroadcast((Context)this.zzair, (int)0, (Intent)intent2, (int)0);
            }
            intent.putExtra("app", (Parcelable)this.zzicn);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzi(String string2, Object object) {
        Class<?> class_ = this.getClass();
        synchronized (class_) {
            Object object2 = this.zzifv.get(string2);
            this.zzifv.put(string2, object);
            zzl.zze(object2, object);
            return;
        }
    }

    static String zzj(Intent object) throws IOException {
        String string2;
        if (object == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String string3 = string2 = object.getStringExtra("registration_id");
        if (string2 == null) {
            string3 = object.getStringExtra("unregistered");
        }
        object.getLongExtra("Retry-After", 0L);
        if (string3 == null) {
            string3 = object.getStringExtra("error");
            if (string3 != null) {
                throw new IOException(string3);
            }
            object = String.valueOf((Object)object.getExtras());
            Log.w((String)"InstanceID/Rpc", (String)new StringBuilder(String.valueOf(object).length() + 29).append("Unexpected response from GCM ").append((String)object).toString(), (Throwable)new Throwable());
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        return string3;
    }

    final Intent zza(Bundle bundle, KeyPair keyPair) throws IOException {
        Intent intent;
        Intent intent2 = intent = this.zzb(bundle, keyPair);
        if (intent != null) {
            intent2 = intent;
            if (intent.hasExtra("google.messenger")) {
                bundle = this.zzb(bundle, keyPair);
                intent2 = bundle;
                if (bundle != null) {
                    intent2 = bundle;
                    if (bundle.hasExtra("google.messenger")) {
                        intent2 = null;
                    }
                }
            }
        }
        return intent2;
    }

    public final void zzc(Message message) {
        if (message == null) {
            return;
        }
        if (message.obj instanceof Intent) {
            Intent intent = (Intent)message.obj;
            intent.setExtrasClassLoader(MessengerCompat.class.getClassLoader());
            if (intent.hasExtra("google.messenger")) {
                if ((intent = intent.getParcelableExtra("google.messenger")) instanceof MessengerCompat) {
                    this.zzifx = (MessengerCompat)intent;
                }
                if (intent instanceof Messenger) {
                    this.zzifw = (Messenger)intent;
                }
            }
            this.zzk((Intent)message.obj);
            return;
        }
        Log.w((String)"InstanceID/Rpc", (String)"Dropping invalid message");
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzk(Intent object) {
        long l;
        Object object2;
        String string2;
        block27: {
            block26: {
                block25: {
                    if (object == null) {
                        if (!Log.isLoggable((String)"InstanceID/Rpc", (int)3)) return;
                        {
                            Log.d((String)"InstanceID/Rpc", (String)"Unexpected response: null");
                            return;
                        }
                    }
                    object2 = object.getAction();
                    if (!"com.google.android.c2dm.intent.REGISTRATION".equals(object2) && !"com.google.android.gms.iid.InstanceID".equals(object2)) {
                        if (!Log.isLoggable((String)"InstanceID/Rpc", (int)3)) return;
                        {
                            object = ((String)(object = String.valueOf(object.getAction()))).length() != 0 ? "Unexpected response ".concat((String)object) : new String("Unexpected response ");
                            Log.d((String)"InstanceID/Rpc", (String)object);
                            return;
                        }
                    }
                    object2 = object.getStringExtra("registration_id");
                    if (object2 == null) {
                        object2 = object.getStringExtra("unregistered");
                    }
                    if (object2 != null) break block25;
                    string2 = object.getStringExtra("error");
                    if (string2 == null) {
                        object = String.valueOf((Object)object.getExtras());
                        Log.w((String)"InstanceID/Rpc", (String)new StringBuilder(String.valueOf(object).length() + 49).append("Unexpected response, no error or registration id ").append((String)object).toString());
                        return;
                    }
                    if (Log.isLoggable((String)"InstanceID/Rpc", (int)3)) {
                        object2 = String.valueOf(string2);
                        object2 = ((String)object2).length() != 0 ? "Received InstanceID error ".concat((String)object2) : new String("Received InstanceID error ");
                        Log.d((String)"InstanceID/Rpc", (String)object2);
                    }
                    if (!string2.startsWith("|")) break block26;
                    Object object3 = string2.split("\\|");
                    if (!"ID".equals(object3[1])) {
                        object2 = String.valueOf(string2);
                        object2 = ((String)object2).length() != 0 ? "Unexpected structured response ".concat((String)object2) : new String("Unexpected structured response ");
                        Log.w((String)"InstanceID/Rpc", (String)object2);
                    }
                    if (((String[])object3).length > 2) {
                        String string3 = object3[2];
                        object3 = object3[3];
                        string2 = string3;
                        object2 = object3;
                        if (((String)object3).startsWith(":")) {
                            object2 = ((String)object3).substring(1);
                            string2 = string3;
                        }
                    } else {
                        object2 = "UNKNOWN";
                        string2 = null;
                    }
                    object.putExtra("error", (String)object2);
                    break block27;
                }
                this.zzify = SystemClock.elapsedRealtime();
                this.zzigc = 0L;
                this.zziga = 0;
                this.zzigb = 0;
                String string4 = null;
                if (((String)object2).startsWith("|")) {
                    Object object4 = ((String)object2).split("\\|");
                    if (!"ID".equals(object4[1])) {
                        object2 = ((String)(object2 = String.valueOf(object2))).length() != 0 ? "Unexpected structured response ".concat((String)object2) : new String("Unexpected structured response ");
                        Log.w((String)"InstanceID/Rpc", (String)object2);
                    }
                    string4 = object4[2];
                    if (((String[])object4).length > 4) {
                        if ("SYNC".equals(object4[3])) {
                            object2 = this.zzair;
                            Intent intent = new Intent("com.google.android.gms.iid.InstanceID");
                            intent.putExtra("CMD", "SYNC");
                            intent.setClassName((Context)object2, "com.google.android.gms.gcm.GcmReceiver");
                            object2.sendBroadcast(intent);
                        } else if ("RST".equals(object4[3])) {
                            object2 = this.zzair;
                            InstanceID.getInstance(this.zzair);
                            InstanceIDListenerService.zza((Context)object2, InstanceID.zzavg());
                            object.removeExtra("registration_id");
                            this.zzi(string4, object);
                            return;
                        }
                    }
                    object2 = object4 = object4[((String[])object4).length - 1];
                    if (((String)object4).startsWith(":")) {
                        object2 = ((String)object4).substring(1);
                    }
                    object.putExtra("registration_id", (String)object2);
                }
                if (string4 == null) {
                    this.zzae(object);
                    return;
                }
                this.zzi(string4, object);
                return;
            }
            Object var7_9 = null;
            object2 = string2;
            string2 = var7_9;
        }
        if (string2 == null) {
            this.zzae(object2);
        } else {
            this.zzi(string2, object2);
        }
        if ((l = object.getLongExtra("Retry-After", 0L)) > 0L) {
            this.zzifz = SystemClock.elapsedRealtime();
            this.zzigb = (int)l * 1000;
            this.zzigc = SystemClock.elapsedRealtime() + (long)this.zzigb;
            int n = this.zzigb;
            Log.w((String)"InstanceID/Rpc", (String)new StringBuilder(52).append("Explicit request from server to backoff: ").append(n).toString());
            return;
        }
        if (!"SERVICE_NOT_AVAILABLE".equals(object2) && !"AUTHENTICATION_FAILED".equals(object2) || !"com.google.android.gsf".equals(zzifp)) return;
        {
            ++this.zziga;
            if (this.zziga < 3) return;
            {
                if (this.zziga == 3) {
                    this.zzigb = new Random().nextInt(1000) + 1000;
                }
                this.zzigb <<= 1;
                this.zzigc = SystemClock.elapsedRealtime() + (long)this.zzigb;
                int n = this.zzigb;
                Log.w((String)"InstanceID/Rpc", (String)new StringBuilder(String.valueOf(object2).length() + 31).append("Backoff due to ").append((String)object2).append(" for ").append(n).toString());
                return;
            }
        }
    }
}

