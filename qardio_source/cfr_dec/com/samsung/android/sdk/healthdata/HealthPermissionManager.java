/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.samsung.android.sdk.healthdata;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthResultHolder;
import com.samsung.android.sdk.healthdata.IHealth;
import com.samsung.android.sdk.internal.healthdata.ErrorUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HealthPermissionManager {
    private final HealthDataStore a;

    public HealthPermissionManager(HealthDataStore healthDataStore) {
        this.a = healthDataStore;
    }

    private static Bundle a(Set<PermissionKey> object) {
        Object object2;
        HashMap<String, Object> object22 = new HashMap<String, Object>();
        int[] arrn = object.iterator();
        while (arrn.hasNext()) {
            Object object3;
            object2 = (PermissionKey)arrn.next();
            String string2 = ((PermissionKey)object2).getDataType();
            if (string2 == null) {
                throw new IllegalArgumentException("The input argument includes null as a dataType of PermissionKey");
            }
            object = object3 = (ArrayList)object22.get(string2);
            if (object3 == null) {
                object = new ArrayList();
                object22.put(string2, object);
            }
            ((ArrayList)object).add(((PermissionKey)object2).getPermissionType().getValue());
        }
        object = new Bundle();
        for (Map.Entry entry : object22.entrySet()) {
            object2 = (ArrayList)entry.getValue();
            arrn = new int[((ArrayList)object2).size()];
            int n = 0;
            object2 = ((ArrayList)object2).iterator();
            while (object2.hasNext()) {
                arrn[n] = (Integer)object2.next();
                ++n;
            }
            object.putIntArray((String)entry.getKey(), arrn);
        }
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static Map<PermissionKey, Boolean> b(Bundle var0) {
        var4_1 = new HashMap<PermissionKey, Boolean>();
        var5_2 = var0.keySet().iterator();
        block0: do lbl-1000:
        // 3 sources
        {
            if (var5_2.hasNext() == false) return var4_1;
            var6_6 = (String)var5_2.next();
            var7_7 = var0.getIntArray(var6_6);
            if (var7_7 == null) ** GOTO lbl-1000
            var8_8 = PermissionType.values();
            var2_4 = var8_8.length;
            var1_3 = 0;
            do {
                if (var1_3 >= var2_4) continue block0;
                var9_9 = var8_8[var1_3];
                var3_5 = var7_7[var9_9.getValue()];
                if (var3_5 == 0) {
                    var4_1.put(new PermissionKey(var6_6, var9_9), Boolean.FALSE);
                } else if (var3_5 == 1) {
                    var4_1.put(new PermissionKey(var6_6, var9_9), Boolean.TRUE);
                }
                ++var1_3;
            } while (true);
            break;
        } while (true);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Map<PermissionKey, Boolean> isPermissionAcquired(Set<PermissionKey> object) {
        if (object == null) {
            throw new IllegalArgumentException("The input argument is null");
        }
        if (object.isEmpty()) {
            throw new IllegalArgumentException("The input argument has no items");
        }
        IHealth iHealth = HealthDataStore.getInterface(this.a);
        object = HealthPermissionManager.a(object);
        try {
            Log.d((String)"Health.Permission", (String)"Checking the health data permissions are acquired...");
            object = iHealth.isHealthDataPermissionAcquired2(this.a.a().getPackageName(), (Bundle)object);
            if (object != null) return HealthPermissionManager.b(object);
            throw new IllegalStateException("Binder error occurs during getting the result");
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException(ErrorUtil.getRemoteExceptionMessage((Exception)((Object)remoteException)));
        }
    }

    public HealthResultHolder<PermissionResult> requestPermissions(Set<PermissionKey> set) {
        return this.requestPermissions(set, null);
    }

    /*
     * Exception decompiling
     */
    public HealthResultHolder<PermissionResult> requestPermissions(Set<PermissionKey> var1_1, Activity var2_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
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

    public static class PermissionKey {
        private final String a;
        private final PermissionType b;

        public PermissionKey(String string2, PermissionType permissionType) {
            this.a = string2;
            this.b = permissionType;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block3: {
                block2: {
                    if (object == null || !(object instanceof PermissionKey)) break block2;
                    object = (PermissionKey)object;
                    if (this.a != null && ((PermissionKey)object).a != null && this.a.equals(((PermissionKey)object).a) && this.b.getValue() == ((PermissionKey)object).b.getValue()) break block3;
                }
                return false;
            }
            return true;
        }

        public String getDataType() {
            return this.a;
        }

        public PermissionType getPermissionType() {
            return this.b;
        }

        public int hashCode() {
            if (this.a == null) {
                return 0;
            }
            return this.a.hashCode() / 31 + this.b.getValue();
        }
    }

    public static class PermissionResult
    extends HealthResultHolder.BaseResult
    implements Parcelable {
        public static final Parcelable.Creator<PermissionResult> CREATOR = new Parcelable.Creator<PermissionResult>(){

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new PermissionResult(parcel, 0);
            }
        };
        private final Bundle a;

        public PermissionResult(Bundle bundle, int n) {
            super(1, n);
            this.a = bundle;
        }

        private PermissionResult(Parcel parcel) {
            super(parcel);
            this.a = parcel.readBundle();
        }

        /* synthetic */ PermissionResult(Parcel parcel, byte by) {
            this(parcel);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public Map<PermissionKey, Boolean> getResultMap() {
            return HealthPermissionManager.b(this.a);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeBundle(this.a);
        }

    }

    public static enum PermissionType {
        READ(0),
        WRITE(1);

        private final int a;

        private PermissionType(int n2) {
            this.a = n2;
        }

        public final int getValue() {
            return this.a;
        }
    }

}

