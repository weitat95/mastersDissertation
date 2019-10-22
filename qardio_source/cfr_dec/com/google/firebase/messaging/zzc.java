/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.firebase.messaging;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.internal.zzfjr;
import com.google.android.gms.internal.zzfkt;
import com.google.android.gms.internal.zzfku;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.messaging.zzb;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class zzc {
    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Object zza(zzfku object, String object2, zzb zzb2) {
        Bundle bundle;
        Object var3_9;
        Class<?> class_;
        Field field = null;
        try {
            class_ = Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty");
            bundle = zzc.zzaz(((zzfku)object).zzpri, ((zzfku)object).zzprj);
            var3_9 = class_.getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception exception) {
            void var0_2;
            var3_9 = null;
            Log.e((String)"FirebaseAbtUtil", (String)"Could not complete the operation due to an internal error.", (Throwable)var0_2);
            return var3_9;
        }
        class_.getField("mOrigin").set(var3_9, object2);
        class_.getField("mCreationTimestamp").set(var3_9, ((zzfku)object).zzprk);
        class_.getField("mName").set(var3_9, ((zzfku)object).zzpri);
        class_.getField("mValue").set(var3_9, ((zzfku)object).zzprj);
        object2 = TextUtils.isEmpty((CharSequence)((zzfku)object).zzprl) ? field : ((zzfku)object).zzprl;
        class_.getField("mTriggerEventName").set(var3_9, object2);
        field = class_.getField("mTimedOutEventName");
        object2 = !TextUtils.isEmpty((CharSequence)((zzfku)object).zzprq) ? ((zzfku)object).zzprq : zzb2.zzbqu();
        field.set(var3_9, object2);
        class_.getField("mTimedOutEventParams").set(var3_9, (Object)bundle);
        class_.getField("mTriggerTimeout").set(var3_9, ((zzfku)object).zzprm);
        field = class_.getField("mTriggeredEventName");
        object2 = !TextUtils.isEmpty((CharSequence)((zzfku)object).zzpro) ? ((zzfku)object).zzpro : zzb2.zzbqt();
        field.set(var3_9, object2);
        class_.getField("mTriggeredEventParams").set(var3_9, (Object)bundle);
        class_.getField("mTimeToLive").set(var3_9, ((zzfku)object).zzghq);
        object2 = class_.getField("mExpiredEventName");
        object = !TextUtils.isEmpty((CharSequence)((zzfku)object).zzprr) ? ((zzfku)object).zzprr : zzb2.zzbqv();
        ((Field)object2).set(var3_9, object);
        class_.getField("mExpiredEventParams").set(var3_9, (Object)bundle);
        return var3_9;
        {
            catch (Exception exception) {}
        }
    }

    private static String zza(zzfku zzfku2, zzb zzb2) {
        if (zzfku2 != null && !TextUtils.isEmpty((CharSequence)zzfku2.zzprp)) {
            return zzfku2.zzprp;
        }
        return zzb2.zzbqw();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static List<Object> zza(AppMeasurement list, String string2) {
        ArrayList arrayList = new ArrayList();
        try {
            Method method = AppMeasurement.class.getDeclaredMethod("getConditionalUserProperties", String.class, String.class);
            method.setAccessible(true);
            list = (List)method.invoke(list, string2, "");
        }
        catch (Exception exception) {
            Log.e((String)"FirebaseAbtUtil", (String)"Could not complete the operation due to an internal error.", (Throwable)exception);
            list = arrayList;
        }
        if (Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) {
            int n = list.size();
            Log.v((String)"FirebaseAbtUtil", (String)new StringBuilder(String.valueOf(string2).length() + 55).append("Number of currently set _Es for origin: ").append(string2).append(" is ").append(n).toString());
        }
        return list;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void zza(Context object, String object2, String string2, String string3, String string4) {
        Object object3;
        if (Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) {
            object3 = ((String)(object3 = String.valueOf(object3))).length() != 0 ? "_CE(experimentId) called by ".concat((String)object3) : new String("_CE(experimentId) called by ");
            Log.v((String)"FirebaseAbtUtil", (String)object3);
        }
        if (!zzc.zzet(object)) {
            return;
        }
        AppMeasurement appMeasurement = zzc.zzdc(object);
        try {
            void var4_6;
            void var3_5;
            void var2_4;
            object3 = AppMeasurement.class.getDeclaredMethod("clearConditionalUserProperty", String.class, String.class, Bundle.class);
            ((AccessibleObject)object3).setAccessible(true);
            if (Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) {
                Log.v((String)"FirebaseAbtUtil", (String)new StringBuilder(String.valueOf(var2_4).length() + 17 + String.valueOf(var3_5).length()).append("Clearing _E: [").append((String)var2_4).append(", ").append((String)var3_5).append("]").toString());
            }
            ((Method)object3).invoke(appMeasurement, new Object[]{var2_4, var4_6, zzc.zzaz((String)var2_4, (String)var3_5)});
            return;
        }
        catch (Exception exception) {
            Log.e((String)"FirebaseAbtUtil", (String)"Could not complete the operation due to an internal error.", (Throwable)exception);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void zza(Context object, String string2, byte[] object2, zzb zzb2, int n) {
        Object object3;
        if (Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) {
            object3 = String.valueOf(string2);
            object3 = ((String)object3).length() != 0 ? "_SE called by ".concat((String)object3) : new String("_SE called by ");
            Log.v((String)"FirebaseAbtUtil", (String)object3);
        }
        if (!zzc.zzet(object)) return;
        object3 = zzc.zzdc(object);
        if ((object2 = zzc.zzam(object2)) == null) {
            if (!Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) return;
            {
                Log.v((String)"FirebaseAbtUtil", (String)"_SE failed; either _P was not set, or we couldn't deserialize the _P.");
                return;
            }
        }
        try {
            Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty");
            Iterator<Object> iterator = zzc.zza((AppMeasurement)object3, string2).iterator();
            n = 0;
            do {
                boolean bl;
                String string3;
                long l;
                String string4;
                block17: {
                    zzfkt[] arrzzfkt;
                    block19: {
                        block20: {
                            block18: {
                                if (!iterator.hasNext()) break block18;
                                arrzzfkt = iterator.next();
                                string3 = zzc.zzba(arrzzfkt);
                                string4 = zzc.zzbb(arrzzfkt);
                                l = (Long)Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty").getField("mCreationTimestamp").get(arrzzfkt);
                                if (!object2.zzpri.equals(string3) || !object2.zzprj.equals(string4)) break block19;
                                if (Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) {
                                    Log.v((String)"FirebaseAbtUtil", (String)new StringBuilder(String.valueOf(string3).length() + 23 + String.valueOf(string4).length()).append("_E is already set. [").append(string3).append(", ").append(string4).append("]").toString());
                                }
                                break block20;
                            }
                            if (n == 0) {
                                zzc.zza((AppMeasurement)object3, object, string2, (zzfku)object2, zzb2, 1);
                                return;
                            }
                            if (!Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) {
                                return;
                            }
                            object = object2.zzpri;
                            string2 = object2.zzprj;
                            Log.v((String)"FirebaseAbtUtil", (String)new StringBuilder(String.valueOf(object).length() + 44 + String.valueOf(string2).length()).append("_E is already set. Not setting it again [").append((String)object).append(", ").append(string2).append("]").toString());
                            return;
                        }
                        n = 1;
                        continue;
                    }
                    boolean bl2 = false;
                    arrzzfkt = object2.zzprt;
                    int n2 = arrzzfkt.length;
                    int n3 = 0;
                    do {
                        bl = bl2;
                        if (n3 >= n2) break block17;
                        if (arrzzfkt[n3].zzpri.equals(string3)) {
                            if (!Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) break;
                            Log.v((String)"FirebaseAbtUtil", (String)new StringBuilder(String.valueOf(string3).length() + 33 + String.valueOf(string4).length()).append("_E is found in the _OE list. [").append(string3).append(", ").append(string4).append("]").toString());
                            break;
                        }
                        ++n3;
                    } while (true);
                    bl = true;
                }
                if (bl) continue;
                if (object2.zzprk > l) {
                    if (Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) {
                        Log.v((String)"FirebaseAbtUtil", (String)new StringBuilder(String.valueOf(string3).length() + 115 + String.valueOf(string4).length()).append("Clearing _E as it was not in the _OE list, andits start time is older than the start time of the _E to be set. [").append(string3).append(", ").append(string4).append("]").toString());
                    }
                    zzc.zza(object, string2, string3, string4, zzc.zza((zzfku)object2, zzb2));
                    continue;
                }
                if (!Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) continue;
                Log.v((String)"FirebaseAbtUtil", (String)new StringBuilder(String.valueOf(string3).length() + 109 + String.valueOf(string4).length()).append("_E was not found in the _OE list, but not clearing it as it has a new start time than the _E to be set.  [").append(string3).append(", ").append(string4).append("]").toString());
            } while (true);
        }
        catch (Exception exception) {
            Log.e((String)"FirebaseAbtUtil", (String)"Could not complete the operation due to an internal error.", (Throwable)exception);
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void zza(AppMeasurement object, Context object2, String object3, zzfku zzfku2, zzb zzb2, int n) {
        block12: {
            Object object4;
            String string2;
            Iterator iterator;
            if (Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) {
                iterator = zzfku2.zzpri;
                string2 = zzfku2.zzprj;
                Log.v((String)"FirebaseAbtUtil", (String)new StringBuilder(String.valueOf(iterator).length() + 7 + String.valueOf(string2).length()).append("_SEI: ").append((String)((Object)iterator)).append(" ").append(string2).toString());
            }
            Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty");
            iterator = zzc.zza((AppMeasurement)object, (String)object3);
            n = zzc.zzb((AppMeasurement)object, (String)object3);
            if (zzc.zza((AppMeasurement)object, (String)object3).size() >= n) {
                n = zzfku2.zzprs != 0 ? zzfku2.zzprs : 1;
                if (n != 1) {
                    if (!Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) return;
                    object = zzfku2.zzpri;
                    object2 = zzfku2.zzprj;
                    Log.v((String)"FirebaseAbtUtil", (String)new StringBuilder(String.valueOf(object).length() + 44 + String.valueOf(object2).length()).append("_E won't be set due to overflow policy. [").append((String)object).append(", ").append((String)object2).append("]").toString());
                    return;
                }
                object4 = iterator.get(0);
                string2 = zzc.zzba(object4);
                object4 = zzc.zzbb(object4);
                if (Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) {
                    Log.v((String)"FirebaseAbtUtil", (String)new StringBuilder(String.valueOf(string2).length() + 38).append("Clearing _E due to overflow policy: [").append(string2).append("]").toString());
                }
                zzc.zza((Context)object2, (String)object3, string2, (String)object4, zzc.zza(zzfku2, zzb2));
            }
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                object4 = iterator.next();
                string2 = zzc.zzba(object4);
                object4 = zzc.zzbb(object4);
                if (!string2.equals(zzfku2.zzpri) || ((String)object4).equals(zzfku2.zzprj) || !Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) continue;
                Log.v((String)"FirebaseAbtUtil", (String)new StringBuilder(String.valueOf(string2).length() + 77 + String.valueOf(object4).length()).append("Clearing _E, as only one _V of the same _E can be set atany given time: [").append(string2).append(", ").append((String)object4).append("].").toString());
                zzc.zza((Context)object2, (String)object3, string2, (String)object4, zzc.zza(zzfku2, zzb2));
            }
            object2 = zzc.zza(zzfku2, (String)object3, zzb2);
            if (object2 != null) break block12;
            if (!Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) return;
            object = zzfku2.zzpri;
            object2 = zzfku2.zzprj;
            Log.v((String)"FirebaseAbtUtil", (String)new StringBuilder(String.valueOf(object).length() + 42 + String.valueOf(object2).length()).append("Could not create _CUP for: [").append((String)object).append(", ").append((String)object2).append("]. Skipping.").toString());
            return;
            {
                catch (Exception exception) {
                    Log.e((String)"FirebaseAbtUtil", (String)"Could not complete the operation due to an internal error.", (Throwable)exception);
                    return;
                }
            }
        }
        try {
            object3 = AppMeasurement.class.getDeclaredMethod("setConditionalUserProperty", Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty"));
            ((AccessibleObject)object3).setAccessible(true);
            ((Method)object3).invoke(object, object2);
            return;
        }
        catch (Exception exception) {
            Log.e((String)"FirebaseAbtUtil", (String)"Could not complete the operation due to an internal error.", (Throwable)exception);
            return;
        }
    }

    private static zzfku zzam(byte[] object) {
        try {
            object = zzfku.zzbi(object);
            return object;
        }
        catch (zzfjr zzfjr2) {
            return null;
        }
    }

    private static Bundle zzaz(String string2, String string3) {
        Bundle bundle = new Bundle();
        bundle.putString(string2, string3);
        return bundle;
    }

    private static int zzb(AppMeasurement appMeasurement, String string2) {
        try {
            Method method = AppMeasurement.class.getDeclaredMethod("getMaxUserProperties", String.class);
            method.setAccessible(true);
            int n = (Integer)method.invoke(appMeasurement, string2);
            return n;
        }
        catch (Exception exception) {
            Log.e((String)"FirebaseAbtUtil", (String)"Could not complete the operation due to an internal error.", (Throwable)exception);
            return 20;
        }
    }

    private static String zzba(Object object) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return (String)Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty").getField("mName").get(object);
    }

    private static String zzbb(Object object) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return (String)Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty").getField("mValue").get(object);
    }

    private static AppMeasurement zzdc(Context object) {
        try {
            object = AppMeasurement.getInstance(object);
            return object;
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean zzet(Context context) {
        if (zzc.zzdc(context) == null) {
            if (!Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) return false;
            {
                Log.v((String)"FirebaseAbtUtil", (String)"Firebase Analytics not available");
                return false;
            }
        }
        try {
            Class.forName("com.google.android.gms.measurement.AppMeasurement$ConditionalUserProperty");
            return true;
        }
        catch (ClassNotFoundException classNotFoundException) {
            if (!Log.isLoggable((String)"FirebaseAbtUtil", (int)2)) return false;
            Log.v((String)"FirebaseAbtUtil", (String)"Firebase Analytics library is missing support for abt. Please update to a more recent version.");
            return false;
        }
    }
}

