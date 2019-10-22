/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.util.Log
 *  android.util.Pair
 */
package com.firebase.jobdispatcher;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;
import com.firebase.jobdispatcher.GooglePlayJobCallback;
import com.firebase.jobdispatcher.JobCallback;
import java.util.ArrayList;

final class GooglePlayCallbackExtractor {
    private static Boolean shouldReadKeysAsStringsCached = null;

    GooglePlayCallbackExtractor() {
    }

    private static void checkCondition(boolean bl) {
        if (!bl) {
            throw new IllegalStateException();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @SuppressLint(value={"ParcelClassLoader"})
    private static Pair<JobCallback, Bundle> extractWrappedBinderFromParcel(Bundle var0) {
        block24: {
            block23: {
                block22: {
                    var4_8 = new Bundle();
                    var3_9 = GooglePlayCallbackExtractor.toParcel(var0 /* !! */ );
                    try {
                        if (var3_9.readInt() > 0) break block22;
                        Log.w((String)"FJD.GooglePlayReceiver", (String)"No callback received, terminating");
                    }
                    catch (Throwable var0_5) {}
                    var3_9.recycle();
                    return null;
                }
                if (var3_9.readInt() == 1279544898) break block23;
                Log.w((String)"FJD.GooglePlayReceiver", (String)"No callback received, terminating");
                var3_9.recycle();
                return null;
            }
            var2_10 = var3_9.readInt();
            var1_11 = 0;
            var0_1 = null;
            break block24;
            ** GOTO lbl-1000
        }
        do {
            block25: {
                block28: {
                    block27: {
                        block26: {
                            if (var1_11 >= var2_10) ** GOTO lbl67
                            try {
                                var5_12 = GooglePlayCallbackExtractor.readKey(var3_9);
                                if (var5_12 == null) break block25;
                                if (var0_2 != null || !"callback".equals(var5_12)) {
                                    var6_13 = var3_9.readValue(null);
                                    if (var6_13 instanceof String) {
                                        var4_8.putString(var5_12, (String)var6_13);
                                    } else if (var6_13 instanceof Boolean) {
                                        var4_8.putBoolean(var5_12, ((Boolean)var6_13).booleanValue());
                                    } else if (var6_13 instanceof Integer) {
                                        var4_8.putInt(var5_12, ((Integer)var6_13).intValue());
                                    } else if (var6_13 instanceof ArrayList) {
                                        var4_8.putParcelableArrayList(var5_12, (ArrayList)var6_13);
                                    } else if (var6_13 instanceof Bundle) {
                                        var4_8.putBundle(var5_12, (Bundle)var6_13);
                                    } else if (var6_13 instanceof Parcelable) {
                                        var4_8.putParcelable(var5_12, (Parcelable)var6_13);
                                    }
                                    break block25;
                                }
                                if (var3_9.readInt() == 4) break block26;
                                Log.w((String)"FJD.GooglePlayReceiver", (String)"Bad callback received, terminating");
                            }
                            catch (Throwable var0_7) {}
                            var3_9.recycle();
                            return null;
                        }
                        if ("com.google.android.gms.gcm.PendingCallback".equals(var3_9.readString())) break block27;
                        Log.w((String)"FJD.GooglePlayReceiver", (String)"Bad callback received, terminating");
                        var3_9.recycle();
                        return null;
                    }
                    var0_3 = new GooglePlayJobCallback(var3_9.readStrongBinder());
                    break block25;
lbl67:
                    // 1 sources
                    if (var0_2 != null) break block28;
                    Log.w((String)"FJD.GooglePlayReceiver", (String)"No callback received, terminating");
                    var3_9.recycle();
                    return null;
                }
                var0_4 = Pair.create((Object)var0_2, (Object)var4_8);
                var3_9.recycle();
                return var0_4;
lbl-1000:
                // 2 sources
                {
                    var3_9.recycle();
                    throw var0_6;
                }
            }
            ++var1_11;
        } while (true);
    }

    private static String readKey(Parcel object) {
        if (GooglePlayCallbackExtractor.shouldReadKeysAsStrings()) {
            return object.readString();
        }
        if (!((object = object.readValue(null)) instanceof String)) {
            Log.w((String)"FJD.GooglePlayReceiver", (String)"Bad callback received, terminating");
            return null;
        }
        return (String)object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean shouldReadKeysAsStrings() {
        boolean bl = true;
        synchronized (GooglePlayCallbackExtractor.class) {
            if (shouldReadKeysAsStringsCached != null) return shouldReadKeysAsStringsCached;
            Bundle bundle = new Bundle();
            bundle.putString("key", "value");
            bundle = GooglePlayCallbackExtractor.toParcel(bundle);
            try {
                boolean bl2 = bundle.readInt() > 0;
                GooglePlayCallbackExtractor.checkCondition(bl2);
                bl2 = bundle.readInt() == 1279544898;
                GooglePlayCallbackExtractor.checkCondition(bl2);
                bl2 = bundle.readInt() == 1 ? bl : false;
                GooglePlayCallbackExtractor.checkCondition(bl2);
                shouldReadKeysAsStringsCached = "key".equals(bundle.readString());
                return shouldReadKeysAsStringsCached;
            }
            catch (RuntimeException runtimeException) {
                shouldReadKeysAsStringsCached = Boolean.FALSE;
                return shouldReadKeysAsStringsCached;
            }
            finally {
                bundle.recycle();
            }
            return shouldReadKeysAsStringsCached;
        }
    }

    private static Parcel toParcel(Bundle bundle) {
        Parcel parcel = Parcel.obtain();
        bundle.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        return parcel;
    }

    public Pair<JobCallback, Bundle> extractCallback(Bundle bundle) {
        if (bundle == null) {
            Log.e((String)"FJD.GooglePlayReceiver", (String)"No callback received, terminating");
            return null;
        }
        return GooglePlayCallbackExtractor.extractWrappedBinderFromParcel(bundle);
    }
}

