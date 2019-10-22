/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import com.google.android.gms.iid.MessengerCompat;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.iid.zzi;
import com.google.firebase.iid.zzs;
import com.google.firebase.iid.zzu;
import com.google.firebase.iid.zzw;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class zzv {
    private static PendingIntent zzicn;
    private static int zzift;
    private final Context zzair;
    private Messenger zzicr;
    private Messenger zzifw;
    private MessengerCompat zzifx;
    private final zzu zznys;
    private final SimpleArrayMap<String, TaskCompletionSource<Bundle>> zznzn = new SimpleArrayMap();

    static {
        zzift = 0;
    }

    public zzv(Context context, zzu zzu2) {
        this.zzair = context;
        this.zznys = zzu2;
        this.zzicr = new Messenger((Handler)new zzw(this, Looper.getMainLooper()));
    }

    static /* synthetic */ void zza(zzv zzv2, Message message) {
        zzv2.zze(message);
    }

    private final Bundle zzae(Bundle bundle) throws IOException {
        Bundle bundle2;
        Bundle bundle3 = bundle2 = this.zzaf(bundle);
        if (bundle2 != null) {
            bundle3 = bundle2;
            if (bundle2.containsKey("google.messenger")) {
                bundle3 = bundle = this.zzaf(bundle);
                if (bundle != null) {
                    bundle3 = bundle;
                    if (bundle.containsKey("google.messenger")) {
                        bundle3 = null;
                    }
                }
            }
        }
        return bundle3;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private final Bundle zzaf(Bundle var1_1) throws IOException {
        block29: {
            block30: {
                var2_7 = zzv.zzavi();
                var3_8 = new TaskCompletionSource();
                var4_10 = this.zznzn;
                // MONITORENTER : var4_10
                this.zznzn.put(var2_7, var3_8);
                // MONITOREXIT : var4_10
                if (this.zznys.zzcjf() == 0) {
                    throw new IOException("MISSING_INSTANCEID_SERVICE");
                }
                var4_10 = new Intent();
                var4_10.setPackage("com.google.android.gms");
                if (this.zznys.zzcjf() == 2) {
                    var4_10.setAction("com.google.iid.TOKEN_REQUEST");
                } else {
                    var4_10.setAction("com.google.android.c2dm.intent.REGISTER");
                }
                var4_10.putExtras(var1_1 /* !! */ );
                zzv.zzd(this.zzair, var4_10);
                var4_10.putExtra("kid", new StringBuilder(String.valueOf(var2_7).length() + 5).append("|ID|").append(var2_7).append("|").toString());
                if (Log.isLoggable((String)"FirebaseInstanceId", (int)3)) {
                    var1_1 /* !! */  = String.valueOf((Object)var4_10.getExtras());
                    Log.d((String)"FirebaseInstanceId", (String)new StringBuilder(String.valueOf((Object)var1_1 /* !! */ ).length() + 8).append("Sending ").append((String)var1_1 /* !! */ ).toString());
                }
                var4_10.putExtra("google.messenger", (Parcelable)this.zzicr);
                if (this.zzifw != null || this.zzifx != null) {
                    var1_1 /* !! */  = Message.obtain();
                    var1_1 /* !! */ .obj = var4_10;
                    try {
                        if (this.zzifw != null) {
                            this.zzifw.send((Message)var1_1 /* !! */ );
                        } else {
                            this.zzifx.send((Message)var1_1 /* !! */ );
                        }
                        break block29;
                    }
                    catch (RemoteException var1_2) {
                        if (!Log.isLoggable((String)"FirebaseInstanceId", (int)3)) break block30;
                        Log.d((String)"FirebaseInstanceId", (String)"Messenger failed, fallback to startService");
                    }
                }
            }
            if (this.zznys.zzcjf() == 2) {
                this.zzair.sendBroadcast(var4_10);
            } else {
                this.zzair.startService(var4_10);
            }
        }
        try {
            var3_8 = (Bundle)Tasks.await(var3_8.getTask(), 30000L, TimeUnit.MILLISECONDS);
            return var3_8;
        }
        catch (InterruptedException var1_3) {}
        ** GOTO lbl-1000
        catch (ExecutionException var1_4) {
            var1_5 = var1_4.getCause();
            if (var1_5 instanceof IOException == false) throw new IOException(var1_5);
            throw (IOException)var1_5;
        }
        catch (TimeoutException var1_6) {}
lbl-1000:
        // 2 sources
        {
            Log.w((String)"FirebaseInstanceId", (String)"No response");
            throw new IOException("TIMEOUT");
        }
        finally {
            var1_1 /* !! */  = this.zznzn;
            // MONITORENTER : var1_1 /* !! */ 
            this.zznzn.remove(var2_7);
            // MONITOREXIT : var1_1 /* !! */ 
        }
    }

    private static String zzavi() {
        synchronized (zzv.class) {
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
    private final void zzbl(String string2, String string3) {
        SimpleArrayMap<String, TaskCompletionSource<Bundle>> simpleArrayMap = this.zznzn;
        synchronized (simpleArrayMap) {
            if (string2 == null) {
                for (int i = 0; i < this.zznzn.size(); ++i) {
                    this.zznzn.valueAt(i).setException(new IOException(string3));
                }
                this.zznzn.clear();
            } else {
                TaskCompletionSource<Bundle> taskCompletionSource = this.zznzn.remove(string2);
                if (taskCompletionSource == null) {
                    string2 = (string2 = String.valueOf(string2)).length() != 0 ? "Missing callback for ".concat(string2) : new String("Missing callback for ");
                    Log.w((String)"FirebaseInstanceId", (String)string2);
                    return;
                }
                taskCompletionSource.setException(new IOException(string3));
            }
            return;
        }
    }

    private static void zzd(Context context, Intent intent) {
        synchronized (zzv.class) {
            if (zzicn == null) {
                Intent intent2 = new Intent();
                intent2.setPackage("com.google.example.invalidpackage");
                zzicn = PendingIntent.getBroadcast((Context)context, (int)0, (Intent)intent2, (int)0);
            }
            intent.putExtra("app", (Parcelable)zzicn);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zze(Message object) {
        Object object2;
        block21: {
            block20: {
                block18: {
                    Intent intent;
                    block22: {
                        block19: {
                            if (object == null || !(((Message)object).obj instanceof Intent)) break block18;
                            object2 = (Intent)((Message)object).obj;
                            object2.setExtrasClassLoader(MessengerCompat.class.getClassLoader());
                            if (object2.hasExtra("google.messenger")) {
                                if ((object2 = object2.getParcelableExtra("google.messenger")) instanceof MessengerCompat) {
                                    this.zzifx = (MessengerCompat)object2;
                                }
                                if (object2 instanceof Messenger) {
                                    this.zzifw = (Messenger)object2;
                                }
                            }
                            if (!"com.google.android.c2dm.intent.REGISTRATION".equals(object = (intent = (Intent)((Message)object).obj).getAction())) {
                                if (!Log.isLoggable((String)"FirebaseInstanceId", (int)3)) return;
                                {
                                    object = ((String)(object = String.valueOf(object))).length() != 0 ? "Unexpected response action: ".concat((String)object) : new String("Unexpected response action: ");
                                    Log.d((String)"FirebaseInstanceId", (String)object);
                                }
                                return;
                            }
                            object = object2 = intent.getStringExtra("registration_id");
                            if (object2 == null) {
                                object = intent.getStringExtra("unregistered");
                            }
                            if (object != null) break block19;
                            object2 = intent.getStringExtra("error");
                            if (object2 == null) {
                                object = String.valueOf((Object)intent.getExtras());
                                Log.w((String)"FirebaseInstanceId", (String)new StringBuilder(String.valueOf(object).length() + 49).append("Unexpected response, no error or registration id ").append((String)object).toString());
                                return;
                            }
                            if (Log.isLoggable((String)"FirebaseInstanceId", (int)3)) {
                                object = String.valueOf(object2);
                                object = ((String)object).length() != 0 ? "Received InstanceID error ".concat((String)object) : new String("Received InstanceID error ");
                                Log.d((String)"FirebaseInstanceId", (String)object);
                            }
                            if (!((String)object2).startsWith("|")) break block20;
                            Object object3 = ((String)object2).split("\\|");
                            if (!"ID".equals(object3[1])) {
                                object = String.valueOf(object2);
                                object = ((String)object).length() != 0 ? "Unexpected structured response ".concat((String)object) : new String("Unexpected structured response ");
                                Log.w((String)"FirebaseInstanceId", (String)object);
                            }
                            if (((String[])object3).length > 2) {
                                String string2 = object3[2];
                                object3 = object3[3];
                                object2 = string2;
                                object = object3;
                                if (((String)object3).startsWith(":")) {
                                    object = ((String)object3).substring(1);
                                    object2 = string2;
                                }
                            } else {
                                object = "UNKNOWN";
                                object2 = null;
                            }
                            intent.putExtra("error", (String)object);
                            break block21;
                        }
                        object2 = Pattern.compile("\\|ID\\|([^|]+)\\|:?+(.*)").matcher((CharSequence)object);
                        if (!((Matcher)object2).matches()) {
                            if (!Log.isLoggable((String)"FirebaseInstanceId", (int)3)) return;
                            {
                                object = ((String)(object = String.valueOf(object))).length() != 0 ? "Unexpected response string: ".concat((String)object) : new String("Unexpected response string: ");
                            }
                        }
                        break block22;
                        Log.d((String)"FirebaseInstanceId", (String)object);
                        return;
                    }
                    object = ((Matcher)object2).group(1);
                    object2 = ((Matcher)object2).group(2);
                    Bundle bundle = intent.getExtras();
                    bundle.putString("registration_id", (String)object2);
                    object2 = this.zznzn;
                    synchronized (object2) {
                        TaskCompletionSource<Bundle> taskCompletionSource = this.zznzn.remove(object);
                        if (taskCompletionSource != null) {
                            taskCompletionSource.setResult(bundle);
                            return;
                        }
                        object = ((String)(object = String.valueOf(object))).length() != 0 ? "Missing callback for ".concat((String)object) : new String("Missing callback for ");
                        Log.w((String)"FirebaseInstanceId", (String)object);
                        return;
                    }
                }
                Log.w((String)"FirebaseInstanceId", (String)"Dropping invalid message");
                return;
            }
            Object var3_8 = null;
            object = object2;
            object2 = var3_8;
        }
        this.zzbl((String)object2, (String)object);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    final Bundle zzad(Bundle var1_1) throws IOException {
        if (this.zznys.zzcji() < 12000000) return this.zzae(var1_1);
        var2_2 = zzi.zzev(this.zzair).zzi(1, var1_1);
        try {
            return Tasks.await(var2_2);
        }
        catch (InterruptedException var2_3) {}
        ** GOTO lbl-1000
        catch (ExecutionException var2_5) {}
lbl-1000:
        // 2 sources
        {
            if (Log.isLoggable((String)"FirebaseInstanceId", (int)3)) {
                var3_6 = String.valueOf(var2_4);
                Log.d((String)"FirebaseInstanceId", (String)new StringBuilder(String.valueOf(var3_6).length() + 22).append("Error making request: ").append(var3_6).toString());
            }
            if (var2_4.getCause() instanceof zzs == false) return null;
            if (((zzs)var2_4.getCause()).getErrorCode() != 4) return null;
            return this.zzae(var1_1);
        }
    }
}

